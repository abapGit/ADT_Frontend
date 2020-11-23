package org.abapgit.adt.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitObject;
import org.abapgit.adt.ui.internal.repositories.ModifiedObjectsForRepository;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class AbapGitWizardPageObjectsSelectionForPull extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageObjectsSelectionForPull.class.getName();

	List<ModifiedObjectsForRepository> repoToModifiedObjects;
	private CheckboxTreeViewer modifiedObjTreeViewer;
	private Composite container;
	private Object[] selectedObjectsForRepository;

	public AbapGitWizardPageObjectsSelectionForPull(List<ModifiedObjectsForRepository> repoToModifiedObjects, String message,
			int messageType) {
		super(PAGE_NAME);
		setTitle("Locally Modified Objects."); //$NON-NLS-1$
		setMessage(message, messageType);
		this.repoToModifiedObjects = repoToModifiedObjects;
	}

	@Override
	public void createControl(Composite parent) {

		this.container = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		this.container.setLayout(layout);

		this.container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.modifiedObjTreeViewer = new CheckboxTreeViewer(this.container,
				SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);

		this.modifiedObjTreeViewer.setContentProvider(new ModifiedObjectTreeContentProvider());

		this.modifiedObjTreeViewer.getTree().setFocus();
		this.modifiedObjTreeViewer.getTree().setHeaderVisible(true);
		this.modifiedObjTreeViewer.getTree().setLinesVisible(true);
		this.modifiedObjTreeViewer.getTree().setLayout(layout);
		this.modifiedObjTreeViewer.getTree().setLayoutData(this.container.getLayoutData());

		createColumns();

		this.modifiedObjTreeViewer.setInput(this.repoToModifiedObjects);

		setControl(this.container);
		setPageComplete(true);

		// Check the sub tree if parent is checked
		this.modifiedObjTreeViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getElement() instanceof ModifiedObjectsForRepository) {
					if (event.getChecked()) {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setSubtreeChecked(event.getElement(), true);

					} else {
						AbapGitWizardPageObjectsSelectionForPull.this.modifiedObjTreeViewer.setSubtreeChecked(event.getElement(), false);
					}
				}

			}
		});

	}

	private void createColumns() {

		// TYPE/OBJECT COLUMN
		createTableViewerColumn("Name", 400).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				String result = ""; //$NON-NLS-1$

				if (element instanceof ModifiedObjectsForRepository) {
					result = ((ModifiedObjectsForRepository) element).getRepositoryURL();

				} else if (element instanceof IAbapGitObject) {
					result = ((IAbapGitObject) element).getName();
				}
				return result;
			}

		});


		createTableViewerColumn("Package", 200).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IAbapGitObject) {
					return ((IAbapGitObject) element).getPackageName();
				}
				return ""; //$NON-NLS-1$

			}

		});

		createTableViewerColumn("Type", 100).setLabelProvider(new ColumnLabelProvider() { //$NON-NLS-1$
			@Override
			public String getText(Object element) {
				if (element instanceof IAbapGitObject) {
					return ((IAbapGitObject) element).getType();
				}
				return ""; //$NON-NLS-1$
			}
		});

	}

	private TreeViewerColumn createTableViewerColumn(String title, int bound) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this.modifiedObjTreeViewer, SWT.NONE);
		viewerColumn.getColumn().setText(title);
		viewerColumn.getColumn().setWidth(bound);
		viewerColumn.getColumn().setResizable(true);
		viewerColumn.getColumn().setMoveable(true);
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

	@SuppressWarnings("unchecked")
	public List<ModifiedObjectsForRepository> getSelectedObjects() {

		List<ModifiedObjectsForRepository> checkedObjectsForRepository = new ArrayList<ModifiedObjectsForRepository>();
		List<ModifiedObjectsForRepository> input = (List<ModifiedObjectsForRepository>) this.modifiedObjTreeViewer.getInput();

		for (ModifiedObjectsForRepository modifiedObjectsForRepository : input) {
			List<IAbapGitObject> objects = new ArrayList<IAbapGitObject>();
			String repositoryURL = modifiedObjectsForRepository.getRepositoryURL();

			for (IAbapGitObject obj : modifiedObjectsForRepository.getModifiedObjects()) {
				if (Arrays.asList(this.selectedObjectsForRepository).contains(obj)) {
					objects.add(obj);
				}
			}

			if (!objects.isEmpty()) {
				checkedObjectsForRepository.add(new ModifiedObjectsForRepository(repositoryURL, objects));
			}

		}
		return checkedObjectsForRepository;
	}

	private class ModifiedObjectTreeContentProvider implements ITreeContentProvider {

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ModifiedObjectsForRepository) {
				ModifiedObjectsForRepository modifiedObjectsForRepo = (ModifiedObjectsForRepository) parentElement;
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
			if (element instanceof ModifiedObjectsForRepository) {
				ModifiedObjectsForRepository modifiedObjectsForRepo = (ModifiedObjectsForRepository) element;
				return modifiedObjectsForRepo.getModifiedObjects().size() == 0 ? false : true;
			}
			return false;
		}

	}

}

