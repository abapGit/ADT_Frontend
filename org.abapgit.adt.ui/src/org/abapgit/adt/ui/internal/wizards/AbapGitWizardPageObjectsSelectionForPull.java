package org.abapgit.adt.ui.internal.wizards;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.repositories.RepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page showing modified objects for pull with lazy loading on scroll and
 * persistent selection by object instance.
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();
	private static final double SCROLL_THRESHOLD_LAZY_LOAD = 0.9;
	private static final int SEARCH_DEBOUNCE_DELAY_MS = 300;
	private static boolean isLoading = false;

	private final Set<IRepositoryModifiedObjects> repoToModifiedObjects;
	protected CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private TreeColumnLayout treeColumnLayout;
	private Text searchField;
	private String searchText = "";
	private Job searchJob;

	// Tracks loaded counts per repository for incremental lazy loading
	private final Map<IRepositoryModifiedObjects, Integer> itemsLoadedPerRepo = new HashMap<>();

	private final Map<String, Set<IOverwriteObject>> selectedObjectsByRepo = new HashMap<>();

	// Store filtered repositories for search
	private Set<IRepositoryModifiedObjects> filteredRepositories;

	public AbapGitWizardPageObjectsSelectionForPull(Set<IRepositoryModifiedObjects> repoToModifiedOverwriteObjects, String message,
			int messageType) {
		super(PAGE_NAME);
		setTitle(Messages.AbapGitWizardPageObjectsSelectionForPull_Title);
		setMessage(message, messageType);
		this.repoToModifiedObjects = repoToModifiedOverwriteObjects;
	}

	@Override
	public void createControl(Composite parent) {
		// Main container with GridLayout
		Composite mainContainer = new Composite(parent, SWT.NONE);
		GridLayout mainLayout = new GridLayout(1, false);
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		mainContainer.setLayout(mainLayout);
		mainContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Search field
		this.searchField = new Text(mainContainer, SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		this.searchField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.searchField.setMessage(Messages.AbapGitWizardPageObjectsSelectionForPull_SearchField_Placeholder);

		// Tree container with TreeColumnLayout
		this.treeColumnLayout = new TreeColumnLayout();
		this.container = new Composite(mainContainer, SWT.NONE);
		this.container.setLayout(this.treeColumnLayout);
		this.container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.modifiedObjTreeViewer = new CheckboxTreeViewer(this.container,
				SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);

		this.modifiedObjTreeViewer.setContentProvider(new ModifiedObjectTreeContentProvider());
		this.modifiedObjTreeViewer.getTree().setFocus();
		this.modifiedObjTreeViewer.getTree().setHeaderVisible(true);
		this.modifiedObjTreeViewer.getTree().setLinesVisible(true);
		this.modifiedObjTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

		createColumns();
		this.filteredRepositories = this.repoToModifiedObjects;
		this.modifiedObjTreeViewer.setInput(this.filteredRepositories);
		setControl(mainContainer);
		setPageComplete(true);
		addListeners();
		addSearchListener();
	}

	private void createColumns() {
		createTreeViewerColumn("Name", 300).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IRepositoryModifiedObjects) {
					return "Repository: " + RepositoryUtil.getRepoNameFromUrl(((IRepositoryModifiedObjects) element).getRepositoryURL()); //$NON-NLS-1$
				} else if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getName();
				}
				return ""; //$NON-NLS-1$
			}
		});

		createTreeViewerColumn("Package", 200).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getPackageName();
				}
				return ""; //$NON-NLS-1$
			}
		});

		createTreeViewerColumn("Type", 100).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					return ((IOverwriteObject) element).getType();
				}
				return ""; //$NON-NLS-1$
			}
		});

		/**
		 * TODO: Change this column to Description and add another column with
		 * Action and add an icon.
		 */
		createTreeViewerColumn("Action", 150).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					String actionDescription = ((IOverwriteObject) element).getActionDescription();
					if (actionDescription == null || actionDescription.trim().isEmpty()) {
						return "Modify object locally"; //$NON-NLS-1$
					}
					return actionDescription;
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	private TreeViewerColumn createTreeViewerColumn(String title, int bound) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this.modifiedObjTreeViewer, SWT.NONE);
		viewerColumn.getColumn().setText(title);
		this.treeColumnLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(20, bound, true));
		return viewerColumn;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			this.itemsLoadedPerRepo.clear();
			this.modifiedObjTreeViewer.refresh();
			// Expand all root nodes (repositories) to show initially loaded children
			for (IRepositoryModifiedObjects repository : this.repoToModifiedObjects) {
				this.modifiedObjTreeViewer.expandToLevel(repository, 1);
			}
			if (this.repoToModifiedObjects.isEmpty()) {
				getContainer().showPage(getNextPage());
			}
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return getNextPage() != null;
	}

	@Override
	public IWizardPage getNextPage() {
		return super.getNextPage();
	}

	@SuppressWarnings("unchecked")
	public Set<IRepositoryModifiedObjects> getSelectedObjects() {
		Set<IRepositoryModifiedObjects> checkedObjectsForRepository = new HashSet<>();
		Set<IRepositoryModifiedObjects> input = (Set<IRepositoryModifiedObjects>) this.modifiedObjTreeViewer.getInput();

		for (IRepositoryModifiedObjects repository : input) {
			String repoURL = repository.getRepositoryURL();
			Set<IOverwriteObject> selectedObjsSet = this.selectedObjectsByRepo.getOrDefault(repoURL, Set.of());

			if (!selectedObjsSet.isEmpty()) {
				List<IOverwriteObject> selectedObjs = repository.getModifiedObjects().stream()
						.filter(selectedObjsSet::contains).collect(Collectors.toList());
				if (!selectedObjs.isEmpty()) {
					checkedObjectsForRepository.add(new RepositoryModifiedObjects(repoURL, selectedObjs));
				}
			}
		}
		return checkedObjectsForRepository;
	}

	protected class ModifiedObjectTreeContentProvider implements ITreeContentProvider {
		private static final int BATCH_SIZE = 100;

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects repository = (IRepositoryModifiedObjects) parentElement;
				List<IOverwriteObject> modifiedObjectsForRepository = repository.getModifiedObjects();

				int loadedElementsCount = AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo
						.getOrDefault(repository, 0);
				if (loadedElementsCount == 0) {
					loadedElementsCount = Math.min(BATCH_SIZE, modifiedObjectsForRepository.size());
					AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo.put(repository, loadedElementsCount);
				}
				return modifiedObjectsForRepository.subList(0, loadedElementsCount).toArray();
			}
			return new Object[0];
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof IRepositoryModifiedObjects) {
				return !((IRepositoryModifiedObjects) element).getModifiedObjects().isEmpty();
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}
	}

	protected void selectAllObjectsForRepo(IRepositoryModifiedObjects repo, String repoUrl) {
		Set<IOverwriteObject> allModifiedObjects = new HashSet<>(repo.getModifiedObjects());
		this.selectedObjectsByRepo.put(repoUrl, allModifiedObjects);
	}


	private void addListeners() {
		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				boolean checked = event.getChecked();

				if (element instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects repository = (IRepositoryModifiedObjects) element;
					String repoUrl = repository.getRepositoryURL();

					if (checked) {
						selectAllObjectsForRepo(repository, repoUrl);
					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo.remove(repoUrl);
					}

					for (IOverwriteObject obj : repository.getModifiedObjects().subList(0,
							AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo.getOrDefault(repository,
									0))) {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, checked);
					}

				} else if (element instanceof IOverwriteObject) {
					IOverwriteObject child = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(child);
					if (parent == null) {
						return;
					}
					String repoUrl = parent.getRepositoryURL();
					AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo.putIfAbsent(repoUrl, new HashSet<>());
					Set<IOverwriteObject> selectedObjects = AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo
							.get(repoUrl);

					if (checked) {
						selectedObjects.add(child);
					} else {
						selectedObjects.removeIf(o -> o.getName().equals(child.getName()) && o.getType().equals(child.getType()));
					}

					boolean allChecked = parent.getModifiedObjects().stream()
							.allMatch(selectedObjects::contains);
					AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(parent, allChecked);
				}
			}

			public IRepositoryModifiedObjects findParent(IOverwriteObject obj) {
				for (IRepositoryModifiedObjects repo : AbapGitWizardPageObjectsSelectionForPull.this.repoToModifiedObjects) {
					if (repo.getModifiedObjects().contains(obj)) {
						return repo;
					}
				}
				return null;
			}
		});

		this.modifiedObjTreeViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects modifiedObjectsForRepository = (IRepositoryModifiedObjects) element;
					Set<IOverwriteObject> selectedObjects = AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo
							.get(modifiedObjectsForRepository.getRepositoryURL());
					if (selectedObjects == null) {
						return false;
					}
					return selectedObjects.size() == modifiedObjectsForRepository.getModifiedObjects().size();
				}
				if (element instanceof IOverwriteObject) {
					IOverwriteObject obj = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(obj);
					if (parent == null) {
						return false;
					}
					Set<IOverwriteObject> selectedObjects = AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo
							.get(parent.getRepositoryURL());
					return selectedObjects != null && selectedObjects.contains(obj);
				}
				return false;
			}

			private IRepositoryModifiedObjects findParent(IOverwriteObject obj) {
				for (IRepositoryModifiedObjects repo : AbapGitWizardPageObjectsSelectionForPull.this.repoToModifiedObjects) {
					if (repo.getModifiedObjects().contains(obj)) {
						return repo;
					}
				}
				return null;
			}
		});

		var tree = this.modifiedObjTreeViewer.getTree();
		ScrollBar verticalScrollBar = tree.getVerticalBar();
		tree.addListener(SWT.MouseWheel, e -> tryLazyLoad(verticalScrollBar));
		tree.addListener(SWT.Resize, e -> tryLazyLoad(verticalScrollBar));
		tree.addListener(SWT.MouseUp, e -> tryLazyLoad(verticalScrollBar));
		//add listener for scrollbar to trigger lazy loading if the scroll is in bottom
		verticalScrollBar.addListener(SWT.Selection, e -> tryLazyLoad(verticalScrollBar));

	}

	private void tryLazyLoad(ScrollBar verticalScrollBar) {
		if (verticalScrollBar == null || verticalScrollBar.getMaximum() == 0) {
			return;
		}

		int scrollPosition = verticalScrollBar.getSelection() + verticalScrollBar.getThumb();
		int threshold = (int) (verticalScrollBar.getMaximum() * SCROLL_THRESHOLD_LAZY_LOAD);

		if (scrollPosition >= threshold) {
			Object[] expandedElements = this.modifiedObjTreeViewer.getExpandedElements();

			for (Object parent : expandedElements) {
				if (parent instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects modifiedObjectsForRepository = (IRepositoryModifiedObjects) parent;
					loadNextBatchOfModifiedObjects(modifiedObjectsForRepository);
				}
			}
		}
	}

	protected void loadNextBatchOfModifiedObjects(IRepositoryModifiedObjects modifiedObjectsForRepository) {
		//guard for null scenario for modified objects
		if (modifiedObjectsForRepository.getModifiedObjects() == null) {
			return;
		}
		// adding debounce logic to avoid multiple simultaneous loads
		if (isLoading) {
			return;
		}
		isLoading = true;
		List<IOverwriteObject> modifiedObjects = modifiedObjectsForRepository.getModifiedObjects();
		int itemsAlreadyLoaded = this.itemsLoadedPerRepo.getOrDefault(modifiedObjectsForRepository, 0);

		if (itemsAlreadyLoaded >= modifiedObjects.size()) {
			return;
		}

		int itemsToLoadNext = Math.min(ModifiedObjectTreeContentProvider.BATCH_SIZE, modifiedObjects.size() - itemsAlreadyLoaded);

		List<IOverwriteObject> nextBatch = modifiedObjects.subList(itemsAlreadyLoaded, itemsAlreadyLoaded + itemsToLoadNext);

		this.itemsLoadedPerRepo.put(modifiedObjectsForRepository, itemsToLoadNext + itemsAlreadyLoaded);

		this.modifiedObjTreeViewer.getControl().setRedraw(false);
		try {

			for (IOverwriteObject obj : nextBatch) {
				this.modifiedObjTreeViewer.add(modifiedObjectsForRepository, obj);
				if (this.modifiedObjTreeViewer.getChecked(modifiedObjectsForRepository)) {
					this.modifiedObjTreeViewer.setChecked(obj, true);
				}
			}
		} finally {
			this.modifiedObjTreeViewer.getControl().setRedraw(true);
			isLoading = false;
		}
	}

	/**
	 * Add search listener with debouncing to prevent UI freeze.
	 */
	private void addSearchListener() {
		this.searchField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (AbapGitWizardPageObjectsSelectionForPull.this.searchJob != null) {
					AbapGitWizardPageObjectsSelectionForPull.this.searchJob.cancel();
				}
				AbapGitWizardPageObjectsSelectionForPull.this.searchJob = new Job("Search Modified Objects") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						final String[] currentSearchText = new String[1];
						AbapGitWizardPageObjectsSelectionForPull.this.searchField.getDisplay()
								.syncExec(() -> currentSearchText[0] = AbapGitWizardPageObjectsSelectionForPull.this.searchField.getText());

						AbapGitWizardPageObjectsSelectionForPull.this.searchText = currentSearchText[0] != null ? currentSearchText[0].toLowerCase().trim() : "";
						// Perform filtering
						filterRepositories();

						// Update UI in UI thread
						AbapGitWizardPageObjectsSelectionForPull.this.searchField.getDisplay().asyncExec(() -> {
							if (!AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.getControl().isDisposed()) {
								// Clear loaded counts to force reload with filtered data
								AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo.clear();

								// Update input with filtered repositories
								AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setInput(
										AbapGitWizardPageObjectsSelectionForPull.this.filteredRepositories);
								AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.refresh();

								// Expand all repositories to show filtered results
								for (IRepositoryModifiedObjects repository : AbapGitWizardPageObjectsSelectionForPull.this.filteredRepositories) {
									AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.expandToLevel(repository, 1);
								}
							}
						});

						return Status.OK_STATUS;
					}
				};

				// Schedule the job with debounce delay
				AbapGitWizardPageObjectsSelectionForPull.this.searchJob.schedule(SEARCH_DEBOUNCE_DELAY_MS);
			}
		});
	}

	/**
	 * Filter repositories and their objects based on search text.
	 */
	private void filterRepositories() {
		if (this.searchText.isEmpty()) {
			// No filter - show all repositories
			this.filteredRepositories = this.repoToModifiedObjects;
		} else {
			// Filter repositories and objects based on search text
			Set<IRepositoryModifiedObjects> filtered = new HashSet<>();

			for (IRepositoryModifiedObjects repository : this.repoToModifiedObjects) {
				// Filter objects within this repository
				List<IOverwriteObject> filteredObjects = repository.getModifiedObjects().stream()
						.filter(obj -> matchesSearch(obj))
						.collect(Collectors.toList());

				// Only include repository if it has matching objects
				if (!filteredObjects.isEmpty()) {
					filtered.add(new RepositoryModifiedObjects(repository.getRepositoryURL(), filteredObjects));
				}
			}

			this.filteredRepositories = filtered;
		}
	}

	/**
	 * Check if an object name matches the search text.
	 */
	private boolean matchesSearch(IOverwriteObject obj) {
		if (this.searchText.isEmpty()) {
			return true;
		}

		String name = obj.getName() != null ? obj.getName().toLowerCase() : "";

		return name.contains(this.searchText);
	}
}
