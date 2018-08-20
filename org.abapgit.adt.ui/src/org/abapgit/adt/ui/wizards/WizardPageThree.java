package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.IAbapProject;
import com.sap.adt.tools.core.ui.packages.AdtPackageProposalProviderFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageProposalProvider;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class WizardPageThree extends WizardPage {

	private final IProject project;
	private final String destination;

	private TextViewer txtBranch;
	private TextViewer txtPackage;

	public WizardPageThree(IProject project, String destination) {
		super("");
		this.project = project;
		this.destination = destination;

		setTitle("Branch and package selection");
		setDescription("Please define repository branch and abap package");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		/////// BRANCH INPUT
		Label lblBranch = new Label(container, SWT.NONE);
		lblBranch.setText("Branch");
		GridDataFactory.swtDefaults().applyTo(lblBranch);

		txtBranch = new TextViewer(container, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false)
				.applyTo(txtBranch.getTextWidget());
		txtBranch.getTextWidget().setText("refs/heads/master");

		txtBranch.getTextWidget().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				callValidateInputOnChange();
			}
		});

		/////// Package INPUT
		Label lblPackage = new Label(container, SWT.NONE);
		lblPackage.setText("Package");
		GridDataFactory.swtDefaults().applyTo(lblPackage);

		txtPackage = new TextViewer(container, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(txtPackage.getTextWidget());
		txtPackage.getTextWidget().setText("");

		txtPackage.getTextWidget().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				callValidateInputOnChange();
			}
		});

		IAdtPackageProposalProvider packageProposalProvider = AdtPackageProposalProviderFactory
				.createPackageProposalProvider(txtPackage);
		packageProposalProvider.setProject(project);

		Button btnPackage = new Button(container, SWT.PUSH);
		GridDataFactory.swtDefaults().applyTo(btnPackage);
		btnPackage.setText("Browse...");

		btnPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IAbapProject abapProject = project.getAdapter(IAbapProject.class);
				IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
				IAdtObjectReference[] selectedPackages = packageServiceUI.openPackageSelectionDialog(
						e.display.getActiveShell(), false, abapProject.getDestinationId(),
						txtPackage.getTextWidget().getText());
				if (selectedPackages != null && selectedPackages.length > 0) {
					String text = selectedPackages[0].getName();
					txtPackage.getTextWidget().setText(text);
				}
			}
		});

		setControl(container);

		callValidateInputOnChange();
	}

	public String getTxtBranch() {
		return txtBranch.getTextWidget().getText();
	}

	public String getTxtPackage() {
		return txtPackage.getTextWidget().getText();
	}

	private void callValidateInputOnChange() {
		setPageComplete(true);
		setErrorMessage(null);
		setMessage(null);

		try {
			validateInputOnChange();
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			setPageComplete(false);
		}
	}

	private void validateInputOnChange() {
		// here only perform a fast input check
		if (getTxtBranch().isEmpty()) {
			throw new IllegalArgumentException("Specify a branch");
		}
		if (getTxtPackage().isEmpty()) {
			throw new IllegalArgumentException("Specify a package");
		}
	}

	public void callValidateInputFinal(IProgressMonitor monitor) throws InvocationTargetException {
		setPageComplete(true);
		setErrorMessage(null);
		setMessage(null);

		try {
			validateInputFinal(monitor);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			setPageComplete(false);
			throw new InvocationTargetException(e);
		}
	}

	protected void validateInputFinal(IProgressMonitor monitor) {
		validateInputOnChange();

		// here perform the final, possibly long running checks
		IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
		boolean packageExists = packageServiceUI.packageExists(destination, getTxtPackage(), monitor);
		if (!packageExists) {
			throw new IllegalArgumentException("Package does not exist");
		}
	}
}
