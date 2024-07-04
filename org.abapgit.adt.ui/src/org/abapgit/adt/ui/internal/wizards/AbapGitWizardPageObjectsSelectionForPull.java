package org.abapgit.adt.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

/**
 * A wizard page, which lists all the modified objects for a repository and
 * provides a check box to select the objects which should be pulled from remote
 * repository and overwrite local versions..
 *
 * @author I517012
 *
 */
public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();

	Set<IRepositoryModifiedObjects> repoToModifiedObjects;
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
		this.modifiedObjTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

		addListeners();
	}

	private void createColumns() {

		// TYPE/OBJECT COLUMN
		createTreeViewerColumn("Name", 300).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				String result = ""; //$NON-NLS-1$

				if (element instanceof IRepositoryModifiedObjects) {
					result = "Repository: " + RepositoryUtil.getRepoNameFromUrl(((IRepositoryModifiedObjects) element).getRepositoryURL()); //$NON-NLS-1$

				} else if (element instanceof IOverwriteObject) {
					result = ((IOverwriteObject) element).getName();
				}
				return result;
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

		createTreeViewerColumn("Type", 20).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
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
		createTreeViewerColumn("Action", 100).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IOverwriteObject) {
					String actionDescription = ((IOverwriteObject) element).getActionDescription();
					if (actionDescription == null || actionDescription.isBlank()) {
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

		// if there are no modified Objects (overwrite/package warning objects), move to next page in the wizard
		if (this.repoToModifiedObjects.isEmpty()) {
			getContainer().showPage(getNextPage());
		}

		this.modifiedObjTreeViewer.refresh();
	}

	@Override
	public boolean canFlipToNextPage() {
		if (getNextPage() == null) {
			return false;
		}
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		//set the selected Objects on clicking next
		this.selectedObjectsForRepository = this.modifiedObjTreeViewer.getCheckedElements();
		return super.getNextPage();
	}

	/**
	 * Returns the selected objects to be pulled as a set of
	 * IRepositoryModifiedObjects
	 *
	 * @return checkedObjectsForRepository
	 */
	@SuppressWarnings("unchecked")
	public Set<IRepositoryModifiedObjects> getSelectedObjects() {
		Set<IRepositoryModifiedObjects> checkedObjectsForRepository = new HashSet<IRepositoryModifiedObjects>();
		Set<IRepositoryModifiedObjects> input = (Set<IRepositoryModifiedObjects>) this.modifiedObjTreeViewer.getInput();

		// Loop over the input for the checkboxTreeViewer (modifiedobjects)
		for (IRepositoryModifiedObjects modifiedObjectsForRepository : input) {
			List<IOverwriteObject> objects = new ArrayList<IOverwriteObject>();
			String repositoryURL = modifiedObjectsForRepository.getRepositoryURL();

			for (IOverwriteObject obj : modifiedObjectsForRepository.getModifiedObjects()) {
				//If the object is in the list of checked/selected objects ,
				// add it to the list of objects to pull and map it to the corresponding repo
				if (Arrays.asList(this.selectedObjectsForRepository).contains(obj)) {
					objects.add(obj);
				}
			}

			// map list of objects to pull  to the corresponding repo
			if (!objects.isEmpty()) {
				checkedObjectsForRepository.add(new RepositoryModifiedObjects(repositoryURL, objects));
			}
		}
		return checkedObjectsForRepository;
	}

	private class ModifiedObjectTreeContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects modifiedObjectsForRepo = (IRepositoryModifiedObjects) parentElement;
				return modifiedObjectsForRepo.getModifiedObjects().toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof IRepositoryModifiedObjects) {
				IRepositoryModifiedObjects modifiedObjectsForRepo = (IRepositoryModifiedObjects) element;
				return modifiedObjectsForRepo.getModifiedObjects().size() == 0 ? false : true;
			}
			return false;
		}

	}

	private void addListeners() {
		// Check the sub tree if parent is checked
		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getElement() instanceof IRepositoryModifiedObjects) {
					if (event.getChecked()) {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setSubtreeChecked(event.getElement(), true);
					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setSubtreeChecked(event.getElement(), false);

						//keep the objects to be added locally checked always
						IRepositoryModifiedObjects modifiedObjects = (IRepositoryModifiedObjects) event.getElement();
						List<IOverwriteObject> objectsToAddLocally = modifiedObjects.getModifiedObjects().stream()
								.filter((obj) -> obj.getAction() != null && obj.getAction().equals("1")).collect(Collectors.toList()); //$NON-NLS-1$
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer
								.setCheckedElements(objectsToAddLocally.toArray());
					}
				}

				if (event.getElement() instanceof IOverwriteObject) {
					IOverwriteObject modifiedObject = (IOverwriteObject) event.getElement();
					if (modifiedObject.getAction() != null && modifiedObject.getAction().equals("1")) { //$NON-NLS-1$
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setChecked(modifiedObject, true);
					}
				}

				AbapGitWizardPageObjectsSelectionForPull.this.selectedObjectsForRepository = AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer
						.getCheckedElements();

			}
		});

		// Check elements which have action as 1 i.e. Objects to be added
		this.modifiedObjTreeViewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof IOverwriteObject) {
					IOverwriteObject object = (IOverwriteObject) element;
					if (object.getAction() != null && object.getAction().equals("1")) { //$NON-NLS-1$
						return true;
					}
				}
				return false;
			}
		});
	}
}

