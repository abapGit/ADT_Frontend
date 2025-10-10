package org.abapgit.adt.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page showing modified objects for pull with lazy loading and
 * parent-child check synchronization.
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();

	private final Set<IRepositoryModifiedObjects> repoToModifiedObjects;
	protected CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private Object[] selectedObjectsForRepository;
	private TreeColumnLayout treeColumnLayout;

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
				} else if (element instanceof LoadMoreNode) {
					return element.toString();
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
		if (this.repoToModifiedObjects.isEmpty()) {
			getContainer().showPage(getNextPage());
		}
		this.modifiedObjTreeViewer.refresh();
	}

	@Override
	public boolean canFlipToNextPage() {
		return getNextPage() != null;
	}

	@Override
	public IWizardPage getNextPage() {
		this.selectedObjectsForRepository = this.modifiedObjTreeViewer.getCheckedElements();
		return super.getNextPage();
	}

	@SuppressWarnings("unchecked")
	public Set<IRepositoryModifiedObjects> getSelectedObjects() {
		Set<IRepositoryModifiedObjects> checkedObjectsForRepository = new HashSet<IRepositoryModifiedObjects>();
		Set<IRepositoryModifiedObjects> input = (Set<IRepositoryModifiedObjects>) this.modifiedObjTreeViewer.getInput();

		for (IRepositoryModifiedObjects modifiedObjectsForRepository : input) {
			List<IOverwriteObject> objects = new ArrayList<IOverwriteObject>();
			String repositoryURL = modifiedObjectsForRepository.getRepositoryURL();

			for (IOverwriteObject obj : modifiedObjectsForRepository.getModifiedObjects()) {
				if (Arrays.asList(this.selectedObjectsForRepository).contains(obj)) {
					objects.add(obj);
				}
			}
			if (!objects.isEmpty()) {
				checkedObjectsForRepository.add(new RepositoryModifiedObjects(repositoryURL, objects));
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
				IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) parentElement;
				List<IOverwriteObject> all = repo.getModifiedObjects();

				if (all.size() <= BATCH_SIZE) {
					return all.toArray();
				} else {
					List<Object> partial = new ArrayList<Object>(all.subList(0, BATCH_SIZE));
					partial.add(new LoadMoreNode(repo, BATCH_SIZE));
					return partial.toArray();
				}
			}
			return new Object[0];
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof IRepositoryModifiedObjects) {
				return !((IRepositoryModifiedObjects) element).getModifiedObjects().isEmpty();
			} else if (element instanceof LoadMoreNode) {
				return false; // flat expansion – "Load more" node is not a real parent
			}
			return false;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}
	}

	private static class LoadMoreNode {
		IRepositoryModifiedObjects repo;
		int offset;

		LoadMoreNode(IRepositoryModifiedObjects repo, int offset) {
			this.repo = repo;
			this.offset = offset;
		}

		@Override
		public String toString() {
			return "Load more..."; //$NON-NLS-1$
		}
	}

	private void addListeners() {

		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Object element = event.getElement();

				if (element instanceof IRepositoryModifiedObjects) {
					IRepositoryModifiedObjects repo = (IRepositoryModifiedObjects) element;
					boolean checked = event.getChecked();
					for (IOverwriteObject obj : repo.getModifiedObjects()) {
						if (!"1".equals(obj.getAction())) { //$NON-NLS-1$
							AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, checked);
						} else {
							AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(obj, true);
						}
					}
				} else if (element instanceof IOverwriteObject) {
					IOverwriteObject child = (IOverwriteObject) element;
					IRepositoryModifiedObjects parent = findParent(child);

					if (parent != null) {
						List<IOverwriteObject> all = parent.getModifiedObjects();
						boolean allChecked = true;
						for (IOverwriteObject obj : all) {
							if (!AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.getChecked(obj)
									&& !"1".equals(obj.getAction())) { //$NON-NLS-1$
								allChecked = false;
								break;
							}
						}
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(parent, allChecked);
					}

					if ("1".equals(child.getAction())) { //$NON-NLS-1$
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(child, true);
					}
				}

				AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsForRepository = AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer
						.getCheckedElements();
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
				if (element instanceof IOverwriteObject) {
					IOverwriteObject obj = (IOverwriteObject) element;
					return "1".equals(obj.getAction()); //$NON-NLS-1$
				}
				return false;
			}
		});

		this.modifiedObjTreeViewer.addDoubleClickListener(event -> {
			Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if (element instanceof LoadMoreNode) {
				LoadMoreNode node = (LoadMoreNode) element;
				IRepositoryModifiedObjects repo = node.repo;

				List<IOverwriteObject> all = repo.getModifiedObjects();
				int start = node.offset;
				int end = Math.min(start + ModifiedObjectTreeContentProvider.BATCH_SIZE, all.size());

				List<Object> nextBatch = new ArrayList<Object>(all.subList(start, end));
				if (end < all.size()) {
					nextBatch.add(new LoadMoreNode(repo, end));
				}

				this.modifiedObjTreeViewer.getControl().setRedraw(false);
				try {
					this.modifiedObjTreeViewer.remove(node);
					for (Object obj : nextBatch) {
						this.modifiedObjTreeViewer.add(repo, obj);
						if (obj instanceof IOverwriteObject && "1".equals(((IOverwriteObject) obj).getAction())) { //$NON-NLS-1$
							this.modifiedObjTreeViewer.setChecked(obj, true);
						}
					}
				} finally {
					this.modifiedObjTreeViewer.getControl().setRedraw(true);
				}
			}
		});
	}
}
