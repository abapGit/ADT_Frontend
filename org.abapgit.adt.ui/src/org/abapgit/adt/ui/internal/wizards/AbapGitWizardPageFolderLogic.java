package org.abapgit.adt.ui.internal.wizards;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
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
import org.eclipse.swt.widgets.Link;

public class AbapGitWizardPageFolderLogic extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageFolderLogic.class.getName();

	private final CloneData cloneData;
	private Label lblFolderLogic;
	private ComboViewer comboFolderLogic;
	private Label infoImage;
	private Link infoText;

	private boolean backButtonEnabled = true;

	public AbapGitWizardPageFolderLogic(IProject project, String destination, CloneData cloneData) {
		super(PAGE_NAME);
		this.cloneData = cloneData;

		setTitle(Messages.AbapGitWizardPageFolderLogic_title);
		setDescription(Messages.AbapGitWizardPageFolderLogic_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

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
		}

		this.comboFolderLogic.addSelectionChangedListener(
				event -> this.cloneData.folderLogic = this.comboFolderLogic.getStructuredSelection().getFirstElement().toString());
		this.comboFolderLogic.getCombo().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setMessage(null);
				setMessage(Messages.AbapGitWizardPageFolderLogic_description);
			}

			@Override
			public void focusGained(FocusEvent e) {
				setMessage(null);
				setMessage(Messages.AbapGitWizardPageBranchAndPackage_folder_logic_info, INFORMATION);
			}
		});

		Label dummyLabel = new Label(container, SWT.NONE);

		this.infoImage = new Label(container, SWT.NONE);
		this.infoImage.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
		GridData gd = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		this.infoImage.setLayoutData(gd);

		this.infoText = new Link(container, SWT.WRAP);
		this.infoText.setFont(container.getFont());

		gd = new GridData(GridData.BEGINNING, GridData.FILL, true, true);
		this.infoText.setLayoutData(gd);

		setControl(container);
		setPageComplete(true);
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
		return super.getPreviousPage();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (this.cloneData.folderLogic == null || this.cloneData.folderLogicExistsInAbapGitFile == false) {
			this.comboFolderLogic.getCombo().setEnabled(true);
			this.infoImage.setVisible(false);
			this.infoText.setVisible(false);
		} else {
			this.lblFolderLogic.setVisible(true);
			this.comboFolderLogic.getCombo().setVisible(true);
			this.comboFolderLogic.getCombo().setEnabled(false);
			this.infoImage.setVisible(true);
			this.infoText.setVisible(true);
			if (this.cloneData.folderLogic.equalsIgnoreCase(IRepository.FolderLogic.FULL.name())) {
				this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.FULL));
				this.infoText.setText(Messages.AbapGitWizardPageFolderLogic_AlreadyExists_Full);
				this.infoText.getParent().layout();
			} else {
				this.comboFolderLogic.setSelection(new StructuredSelection(IRepository.FolderLogic.PREFIX));
				this.infoText.setText(Messages.AbapGitWizardPageFolderLogic_AlreadyExists_Prefix);
				this.infoText.getParent().layout();
			}

		}

	}

	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		this.cloneData.folderLogic = this.comboFolderLogic.getStructuredSelection().getFirstElement().toString();
		return super.getNextPage();
	}

}
