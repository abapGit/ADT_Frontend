package org.abapgit.adt.ui.internal.wizards;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.sap.adt.communication.resources.ResourceException;

public class AbapGitWizardPageFolderLogic extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageFolderLogic.class.getName();

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;
	private Label lblFolderLogic;
	private ComboViewer comboFolderLogic;

	private final Boolean pullAction;
	private boolean backButtonEnabled = true;

	public AbapGitWizardPageFolderLogic(IProject project, String destination, CloneData cloneData, Boolean pullAction) {
		super(PAGE_NAME);
		this.project = project;
		this.destination = destination;
		this.cloneData = cloneData;
		this.pullAction = pullAction;

		setTitle("Folder Logic selection");
		setDescription("Select folder logic to be used while linking repository");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		// if the page is added for repository link wizard
		if (!this.pullAction) {
			//read atom link for folder logic. if present create the controls
			this.lblFolderLogic = new Label(container, SWT.NONE);
			this.lblFolderLogic.setText(Messages.AbapGitWizardPageBranchAndPackage_label_folder_logic);
			GridDataFactory.swtDefaults().applyTo(this.lblFolderLogic);

			this.comboFolderLogic = new ComboViewer(container, SWT.BORDER | SWT.READ_ONLY);
			GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false)
					.applyTo(this.comboFolderLogic.getControl());
			this.comboFolderLogic.setContentProvider(ArrayContentProvider.getInstance());
			this.comboFolderLogic.setInput(IRepository.FolderLogic.values());

			if (this.cloneData.folderLogic != null) {
				if (this.cloneData.folderLogic.equalsIgnoreCase(IRepository.FolderLogic.FULL.name())) {
					this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.FULL));
				} else {
					this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.PREFIX));
				}
			} else {
				this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.FULL));
				this.cloneData.folderLogic = IRepository.FolderLogic.FULL.name();
			}

			this.comboFolderLogic.addSelectionChangedListener(
					event -> this.cloneData.folderLogic = this.comboFolderLogic.getStructuredSelection().getFirstElement().toString());
			this.comboFolderLogic.getCombo().addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					setMessage(null);
					setMessage(Messages.AbapGitWizardPageBranchAndPackageAndFolderLogic_description);
				}

				@Override
				public void focusGained(FocusEvent e) {
					setMessage(null);
					setMessage(Messages.AbapGitWizardPageBranchAndPackage_folder_logic_info, INFORMATION);
				}
			});

		}
		setControl(container);

		setPageComplete(true);
		if (!validateClientOnly()) {
			setPageComplete(false);
		}
	}

	public void setBackButtonEnabled(boolean enabled) {
		this.backButtonEnabled = enabled;
		getContainer().updateButtons();
	}

	@Override
	public IWizardPage getPreviousPage() {
		if (!this.backButtonEnabled) {
			return null;
		}
		this.cloneData.folderLogic = null;
		return super.getPreviousPage();
	}

	private boolean validateClientOnly() {
		setMessage(null);
		return true;
	}

	public boolean validateAll() {
		setPageComplete(true);
		if (!validateClientOnly()) {
			setPageComplete(false);
			return false;
		}
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

			setFolderLogicControlVisibility();
	}

	private void setFolderLogicControlVisibility() {
		if (this.cloneData.repositories != null) {
			// read atom link from repositories and check for feature availability for folder logic support
			if (!AbapGitUIServiceFactory.createAbapGitService().isFolderLogicSupportedWhileLink(this.cloneData.repositories)) {
				if (this.lblFolderLogic != null) {
					this.lblFolderLogic.setVisible(false);
					this.comboFolderLogic.getCombo().setVisible(false);
					((GridData) this.lblFolderLogic.getLayoutData()).exclude = true;
					((GridData) this.comboFolderLogic.getCombo().getLayoutData()).exclude = true;
					this.lblFolderLogic.getParent().layout();
				}
			} else {
				if (!this.pullAction) {
					if(this.cloneData.folderLogic == null) {
						setDescription(null);
						setDescription("Select folder logic");
					} else {
					this.lblFolderLogic.setVisible(true);
					this.comboFolderLogic.getCombo().setVisible(true);
					this.comboFolderLogic.getCombo().setEnabled(false);
					setDescription(null);
					if (this.cloneData.folderLogic.equalsIgnoreCase(IRepository.FolderLogic.FULL.name())) {
						this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.FULL));
						setDescription(
								"Folder logic 'FULL' has already been set for the selected branch.\nRepository will be linked with 'FULL' folder logic");
					} else {
						this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.PREFIX));
						setDescription(
								"Folder logic 'PREFIX' has already been set for the selected branch.\nRepository will be linked with 'PREFIX' folder logic");
					}

					}
				}
			}
		}
	}


	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		// fetch modified objects when next button is pressed
		if (this.pullAction == true) {
			IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
			IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());
			IRepository repository = repoService.getRepositoryByURL(this.cloneData.repositories, this.cloneData.url);

			// This is valid only for back end versions from 2105 where selective pull is supported.
			// If selectivePull is not supported, fetching modified objects is not required and all objects are to be pulled
			if (abapGitService.isSelectivePullSupported(repository)) {
				try {
					RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, repoService, this.cloneData);
				} catch (ResourceException e) {
					setMessage(e.getMessage(), DialogPage.ERROR);
					setPageComplete(false);
					return null;
				}
			}
		}
		return super.getNextPage();
	}

}
