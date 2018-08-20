package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfo.IBranch;
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

	private TextViewer txtPackage;
	private ComboViewer comboBranches;

	private IAdtObjectReference packageRef;

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

//		txtBranch = new TextViewer(parent, SWT.SINGLE | SWT.BORDER);
//		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false)
//				.applyTo(txtBranch.getTextWidget());
//		txtBranch.getTextWidget().setText("refs/heads/master");
//
//		txtBranch.getTextWidget().addModifyListener(new ModifyListener() {
//			@Override
//			public void modifyText(ModifyEvent e) {
//				callValidateInputOnChange();
//			}
//		});

		this.comboBranches = new ComboViewer(container, SWT.BORDER);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false)
				.applyTo(comboBranches.getControl());
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
		this.comboBranches.getCombo().addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				validateInputOnChange();
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
				validateInputOnChange();
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

		validateInputOnChange();
	}

	public String getTxtBranch() {
		return comboBranches.getCombo().getText();
	}

	public String getTxtPackage() {
		return txtPackage.getTextWidget().getText();
	}

	private boolean validateInputOnChange() {
		setPageComplete(true);
		setMessage(null);
		packageRef = null;

		if (getTxtBranch().isEmpty()) {
			setMessage("Specify a branch", DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}

		if (getTxtPackage().isEmpty()) {
			setMessage("Specify a package", DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}
		return true;
	}

	public boolean validateInputFinal() {
		if (!validateInputOnChange()) {
			return false;
		}
		try {
			String packageName = getTxtPackage();
			boolean packageExists[] = new boolean[1];
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					// here perform the final, possibly long running checks
					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					packageExists[0] = packageServiceUI.packageExists(destination, packageName, monitor);
					if (packageExists[0]) {
						List<IAdtObjectReference> packageRefs = packageServiceUI.find(destination, packageName,
								monitor);
						packageRef = packageRefs.stream().findFirst().orElse(null);
					}
				}
			});
			if (!packageExists[0]) {
				setMessage("Package does not exist", DialogPage.ERROR);
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
		return true;
	}

	public void setBranches(List<IBranch> branches) {
		this.comboBranches.setInput(branches);
		if (!branches.isEmpty()) {
			IBranch selectedBranch = branches.stream().filter(b -> b.isHead()).findFirst()
					.orElse(branches.stream().findFirst().get());
			this.comboBranches.setSelection(new StructuredSelection(selectedBranch));
		}
	}

	public IAdtObjectReference getPackageRef() {
		return packageRef;
	}

}
