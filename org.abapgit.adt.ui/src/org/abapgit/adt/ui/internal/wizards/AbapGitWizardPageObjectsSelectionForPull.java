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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * Wizard page showing modified objects for pull with lazy loading on scroll and
 * persistent selection by object instance.
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();
	private static final double LAZY_LOAD_THRESHOLD = 0.9;
	private static boolean isLoading = false;

	private final Set<IRepositoryModifiedObjects> repoToModifiedObjects;
	protected CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private TreeColumnLayout treeColumnLayout;

	// Tracks loaded counts per repository for incremental lazy loading
	private final Map<IRepositoryModifiedObjects, Integer> itemsLoadedPerRepo = new HashMap<>();

	private final Map<String, Set<IOverwriteObject>> selectedObjectsByRepo = new HashMap<>();

	public AbapGitWizardPageObjectsSelectionForPull(Set<IRepositoryModifiedObjects> repoToModifiedOverwriteObjects, String message,
			int messageType) {
		super(PAGE_NAME);
		setTitle(Messages.AbapGitWizardPageObjectsSelectionForPull_Title);
		setMessage(message, messageType);
		this.repoToModifiedObjects = repoToModifiedOverwriteObjects;
	}

	@Override
	public void createControl(Composite parent) {
		this.treeColumnLayout = new TreeColumnLayout();

		this.container = new Composite(parent, SWT.NONE);
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
		this.modifiedObjTreeViewer.setInput(this.repoToModifiedObjects);
		setControl(this.container);
		setPageComplete(true);
		addListeners();
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

		for (IRepositoryModifiedObjects modifiedObjectsForRepository : input) {
			String repoURL = modifiedObjectsForRepository.getRepositoryURL();
			Set<IOverwriteObject> selectedObjsSet = this.selectedObjectsByRepo.getOrDefault(repoURL, Set.of());

			if (!selectedObjsSet.isEmpty()) {
				List<IOverwriteObject> selectedObjs = modifiedObjectsForRepository.getModifiedObjects().stream()
						.filter(selectedObjsSet::contains).collect(Collectors.toList());
				if (!selectedObjs.isEmpty()) {
					checkedObjectsForRepository.add(new RepositoryModifiedObjects(repoURL, selectedObjs));
				}
			}
		}
		return checkedObjectsForRepository;
	}

	private class ModifiedObjectTreeContentProvider implements ITreeContentProvider {
		private static final int BATCH_SIZE = 100;

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects modifiedObjectsForRepository = (IRepositoryModifiedObjects) parentElement;
				List<IOverwriteObject> allObjects = modifiedObjectsForRepository.getModifiedObjects();

				int loadedElementsCount = AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo
						.getOrDefault(modifiedObjectsForRepository, 0);
				if (loadedElementsCount == 0) {
					loadedElementsCount = Math.min(BATCH_SIZE, allObjects.size());
					AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo.put(modifiedObjectsForRepository, loadedElementsCount);
				}
				return allObjects.subList(0, loadedElementsCount).toArray();
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

	private void addListeners() {
		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();
				boolean checked = event.getChecked();

				if (element instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects modifiedObjectsForRepository = (IRepositoryModifiedObjects) element;
					String repoUrl = modifiedObjectsForRepository.getRepositoryURL();

					if (checked) {
						Set<IOverwriteObject> allModifiedObjects = new HashSet<>(modifiedObjectsForRepository.getModifiedObjects());
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo.put(repoUrl, allModifiedObjects);
					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsByRepo.remove(repoUrl);
					}

					for (IOverwriteObject obj : modifiedObjectsForRepository.getModifiedObjects().subList(0,
							AbapGitWizardPageObjectsSelectionForPull.this.itemsLoadedPerRepo.getOrDefault(modifiedObjectsForRepository,
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

			private IRepositoryModifiedObjects findParent(IOverwriteObject obj) {
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
		tree.addListener(SWT.MouseWheel, e -> tryLazyLoad());
		tree.addListener(SWT.Selection, e -> tryLazyLoad());
		tree.addListener(SWT.Resize, e -> tryLazyLoad());
		tree.addListener(SWT.MouseUp, e -> tryLazyLoad());
	}

	private void tryLazyLoad() {
		var tree = this.modifiedObjTreeViewer.getTree();
		ScrollBar verticalScrollBar = tree.getVerticalBar();
		if (verticalScrollBar == null || verticalScrollBar.getMaximum() == 0) {
			return;
		}

		int scrollPosition = verticalScrollBar.getSelection() + verticalScrollBar.getThumb();
		int threshold = (int) (verticalScrollBar.getMaximum() * LAZY_LOAD_THRESHOLD);

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

	private void loadNextBatchOfModifiedObjects(IRepositoryModifiedObjects modifiedObjectsForRepository) {
		//guard for null scenario for modified objects
		if (modifiedObjectsForRepository.getModifiedObjects() == null) {
			return;
		}
		// adding debounce logic to avoid multiple simultaneous loads
		if (isLoading) {
			return;
		}
		isLoading = true;
		// fetch repoURL and the already selected objects for that repo
		String repoUrl = modifiedObjectsForRepository.getRepositoryURL();
		Set<IOverwriteObject> selectedObjectsForCurrentRepo = this.selectedObjectsByRepo.getOrDefault(repoUrl, Set.of());
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
				if (selectedObjectsForCurrentRepo.contains(obj)) {
					this.modifiedObjTreeViewer.setChecked(obj, true);
				}
			}
		} finally {
			this.modifiedObjTreeViewer.getControl().setRedraw(true);
			isLoading = false;
		}
	}
}
