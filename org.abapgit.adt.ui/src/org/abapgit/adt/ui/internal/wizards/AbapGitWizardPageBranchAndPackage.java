package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfo.IBranch;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageProposalProviderFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageProposalProvider;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;
import com.sap.adt.util.ui.swt.AdtSWTUtilFactory;

public class AbapGitWizardPageBranchAndPackage extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageRepositoryAndCredentials.class.getName();

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;

//	private Button checkbox_Tta;
	private TextViewer txtPackage;
	private ComboViewer comboBranches;

	public AbapGitWizardPageBranchAndPackage(IProject project, String destination, CloneData cloneData) {
		super(PAGE_NAME);
		this.project = project;
		this.destination = destination;
		this.cloneData = cloneData;

		setTitle(Messages.AbapGitWizardPageBranchAndPackage_title);
		setDescription(Messages.AbapGitWizardPageBranchAndPackage_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		/////// BRANCH INPUT
		Label lblBranch = new Label(container, SWT.NONE);
		lblBranch.setText(Messages.AbapGitWizardPageBranchAndPackage_label_branch);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblBranch, true);
		GridDataFactory.swtDefaults().applyTo(lblBranch);

		this.comboBranches = new ComboViewer(container, SWT.BORDER);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.comboBranches.getControl());
		this.comboBranches.setContentProvider(ArrayContentProvider.getInstance());
		this.comboBranches.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof IBranch) {
					return ((IBranch) element).getName();
				}
				return super.getText(element);
			}

		});
		this.comboBranches.getCombo().addModifyListener(event -> {
			this.cloneData.branch = this.comboBranches.getCombo().getText();
			validateClientOnly();
		});

		/////// Package INPUT
		Label lblPackage = new Label(container, SWT.NONE);
		lblPackage.setText(Messages.AbapGitWizardPageBranchAndPackage_label_package);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblPackage, true);
		GridDataFactory.swtDefaults().applyTo(lblPackage);

		this.txtPackage = new TextViewer(container, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.txtPackage.getTextWidget());
		this.txtPackage.getTextWidget().setText(""); //$NON-NLS-1$

		this.txtPackage.getTextWidget().addModifyListener(event -> {
			this.cloneData.packageRef = null;
			validateClientOnly();
		});

		IAdtPackageProposalProvider packageProposalProvider = AdtPackageProposalProviderFactory
				.createPackageProposalProvider(this.txtPackage);
		packageProposalProvider.setProject(this.project);

		Button btnPackage = new Button(container, SWT.PUSH);
		GridDataFactory.swtDefaults().applyTo(btnPackage);
		btnPackage.setText(Messages.AbapGitWizardPageBranchAndPackage_btn_browse);

		btnPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
				IAdtObjectReference[] selectedPackages = packageServiceUI.openPackageSelectionDialog(e.display.getActiveShell(), false,
						AbapGitWizardPageBranchAndPackage.this.destination,
						AbapGitWizardPageBranchAndPackage.this.txtPackage.getTextWidget().getText());
				if (selectedPackages != null && selectedPackages.length > 0) {
					AbapGitWizardPageBranchAndPackage.this.txtPackage.getTextWidget().setText(selectedPackages[0].getName());
					AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef = selectedPackages[0];
				}
			}
		});

//		/////// CHECKBOX Try to Activate
//		Label lblTta = new Label(container, SWT.NONE);
//		lblTta.setText(Messages.AbapGitWizardPageBranchAndPackage_chbox_activate);
//		lblTta.setToolTipText(Messages.AbapGitWizardPageBranchAndPackage_chbox_activate_tooltip);
//		GridDataFactory.swtDefaults().applyTo(lblTta);
//
//
//		this.checkbox_Tta = new Button(container, SWT.CHECK);
//		GridDataFactory.swtDefaults().applyTo(this.checkbox_Tta);
//
//		this.checkbox_Tta.addSelectionListener(new SelectionAdapter() {
//
//			@Override
//			public void widgetSelected(SelectionEvent event) {
//				Button chbox = (Button) event.getSource();
//				System.out.println(chbox.getSelection());
//			}
//		});

		setControl(container);

		validateClientOnly();
	}

	private boolean validateClientOnly() {
		setPageComplete(true);
		setMessage(null);

		if (this.comboBranches.getCombo().getText().isEmpty()) {
			setMessage(Messages.AbapGitWizardPageBranchAndPackage_combobox_branch_message, DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}

		if (this.txtPackage.getTextWidget().getText().isEmpty()) {
			setMessage(Messages.AbapGitWizardPageBranchAndPackage_text_package_message, DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}
		return true;
	}

	public boolean validateAll() {
		if (!validateClientOnly()) {
			return false;
		}
		if (this.cloneData.packageRef == null) {
			try {
				String packageName = this.txtPackage.getTextWidget().getText();
				getContainer().run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_message,
								IProgressMonitor.UNKNOWN);
						IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
						if (packageServiceUI.packageExists(AbapGitWizardPageBranchAndPackage.this.destination, packageName, monitor)) {
							List<IAdtObjectReference> packageRefs = packageServiceUI
									.find(AbapGitWizardPageBranchAndPackage.this.destination, packageName, monitor);
							AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
						}
					}
				});
				if (this.cloneData.packageRef == null) {
					setMessage(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_error_message, DialogPage.ERROR);
					setPageComplete(false);
					return false;
				}
			} catch (InvocationTargetException e) {
				setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
				setPageComplete(false);
				return false;
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			this.comboBranches.setInput(null);
			this.comboBranches.setSelection(StructuredSelection.EMPTY);
			if (this.cloneData.externalRepoInfo != null) {
				List<IBranch> branches = this.cloneData.externalRepoInfo.getBranches();
				this.comboBranches.setInput(branches);
				if (!branches.isEmpty()) {
					IBranch selectedBranch = branches.stream().filter(b -> b.isHead()).findFirst()
							.orElse(branches.stream().findFirst().get());
					this.comboBranches.setSelection(new StructuredSelection(selectedBranch));
				}
			}
		}
	}

}
