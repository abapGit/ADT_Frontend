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
 * persistent selection by object name.
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();

	private final Set<IRepositoryModifiedObjects> repoToModifiedObjects;
	protected CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private TreeColumnLayout treeColumnLayout;

	// Tracks loaded counts per repository for incremental lazy loading
	private final Map<IRepositoryModifiedObjects, Integer> repoOffsets = new HashMap<>();

	// Persistent logical selection: repoURL → set of selected object names
	private final Map<String, Set<String>> selectedObjectNamesByRepo = new HashMap<>();

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
			this.repoOffsets.clear();
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
		Set<IRepositoryModifiedObjects> checkedObjectsForRepository = new HashSet<IRepositoryModifiedObjects>();
		Set<IRepositoryModifiedObjects> input = (Set<IRepositoryModifiedObjects>) this.modifiedObjTreeViewer.getInput();

		for (IRepositoryModifiedObjects modifiedObjectsForRepository : input) {
			String repoURL = modifiedObjectsForRepository.getRepositoryURL();
			Set<String> selectedNames = this.selectedObjectNamesByRepo.getOrDefault(repoURL, Set.of());

			if (!selectedNames.isEmpty()) {
				List<IOverwriteObject> selectedObjs = modifiedObjectsForRepository.getModifiedObjects().stream()
						.filter(obj -> selectedNames.contains(obj.getName())).collect(Collectors.toList());
				if (!selectedObjs.isEmpty()) {
					checkedObjectsForRepository.add(new RepositoryModifiedObjects(repoURL, selectedObjs));
				}
			}
		}
		return checkedObjectsForRepository;
	}

	/**
	 * Content provider with lazy loading per repository in batches.
	 */
	private class ModifiedObjectTreeContentProvider implements ITreeContentProvider {
		private static final int BATCH_SIZE = 100;

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) parentElement;
				List<IOverwriteObject> all = repo.getModifiedObjects();

				int loaded = AbapGitWizardPageObjectsSelectionForPull.this.repoOffsets.getOrDefault(repo, 0);
				if (loaded == 0) {
					loaded = Math.min(BATCH_SIZE, all.size());
					AbapGitWizardPageObjectsSelectionForPull.this.repoOffsets.put(repo, loaded);
				}

				return all.subList(0, loaded).toArray();
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
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) element;
					String repoUrl = repo.getRepositoryURL();

					if (checked) {
						Set<String> allNames = repo.getModifiedObjects().stream().map(IOverwriteObject::getName)
								.collect(Collectors.toSet());
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.put(repoUrl, allNames);
					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.remove(repoUrl);
					}

					// Update only currently visible children
					for (IOverwriteObject obj : repo.getModifiedObjects().subList(0,
							AbapGitWizardPageObjectsSelectionForPull.this.repoOffsets.getOrDefault(repo, 0))) {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, checked);
					}

					AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(repo, checked);

				} else if (element instanceof IOverwriteObject) {
					IOverwriteObject child = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(child);
					if (parent == null) {
						return;
					}

					String repoUrl = parent.getRepositoryURL();
					AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.putIfAbsent(repoUrl, new HashSet<>());

					if (checked) {
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.get(repoUrl).add(child.getName());
					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.get(repoUrl).remove(child.getName());
					}

					// Determine if all children selected → check parent
					boolean allChecked = parent.getModifiedObjects().stream()
							.allMatch(o -> AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo.get(repoUrl)
									.contains(o.getName()));
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
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) element;
					Set<String> selectedNames = AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo
							.get(repo.getRepositoryURL());
					if (selectedNames == null) {
						return false;
					}
					return selectedNames.size() == repo.getModifiedObjects().size();
				}
				if (element instanceof IOverwriteObject) {
					IOverwriteObject obj = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(obj);
					if (parent == null) {
						return false;
					}
					Set<String> selectedNames = AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectNamesByRepo
							.get(parent.getRepositoryURL());
					return selectedNames != null && selectedNames.contains(obj.getName());
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

		// Scroll-based lazy loading
		var tree = this.modifiedObjTreeViewer.getTree();
		tree.addListener(SWT.MouseWheel, e -> tryLazyLoad());
		tree.addListener(SWT.Selection, e -> tryLazyLoad());
		tree.addListener(SWT.Resize, e -> tryLazyLoad());
		tree.addListener(SWT.MouseUp, e -> tryLazyLoad());
	}

	private void tryLazyLoad() {
		var tree = this.modifiedObjTreeViewer.getTree();
		ScrollBar vBar = tree.getVerticalBar();
		if (vBar == null || vBar.getMaximum() == 0) {
			return;
		}

		int scrollPosition = vBar.getSelection() + vBar.getThumb();
		// Trigger loading when scrolled to 90% of the maximum for smoother experience
		int threshold = (int) (vBar.getMaximum() * 0.9);

		if (scrollPosition >= threshold) {
			Object[] expandedElements = this.modifiedObjTreeViewer.getExpandedElements();

			for (Object parent : expandedElements) {
				if (parent instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) parent;
					loadNextBatch(repo);
				}
			}
		}
	}

	private void loadNextBatch(IRepositoryModifiedObjects repo) {
		List<IOverwriteObject> all = repo.getModifiedObjects();
		int currentOffset = this.repoOffsets.getOrDefault(repo, 0);

		if (currentOffset >= all.size()) {
			return;
		}

		int nextOffset = Math.min(currentOffset + ModifiedObjectTreeContentProvider.BATCH_SIZE, all.size());
		List<IOverwriteObject> nextBatch = all.subList(currentOffset, nextOffset);
		this.repoOffsets.put(repo, nextOffset);

		this.modifiedObjTreeViewer.getControl().setRedraw(false);
		try {
			for (IOverwriteObject obj : nextBatch) {
				this.modifiedObjTreeViewer.add(repo, obj);
				String repoUrl = repo.getRepositoryURL();
				// checking the objects in UI if its selected previously by checking parent.
				if (this.selectedObjectNamesByRepo.getOrDefault(repoUrl, Set.of()).contains(obj.getName())) {
					this.modifiedObjTreeViewer.setChecked(obj, true);
				}
			}
		} finally {
			this.modifiedObjTreeViewer.getControl().setRedraw(true);
		}
	}
}
