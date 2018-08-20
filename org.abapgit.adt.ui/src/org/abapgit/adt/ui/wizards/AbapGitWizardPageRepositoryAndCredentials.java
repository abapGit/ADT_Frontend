package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sap.adt.util.ui.swt.AdtSWTUtilFactory;

public class AbapGitWizardPageRepositoryAndCredentials extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageRepositoryAndCredentials.class.getName();

	private final String destination;
	private final CloneData cloneData;

	private Text txtURL;
	private Text txtUser;
	private Text txtPwd;
	private Label lblUser;
	private Label lblPwd;

	public AbapGitWizardPageRepositoryAndCredentials(String destination, CloneData cloneData) {
		super(PAGE_NAME);
		this.destination = destination;
		this.cloneData = cloneData;
		setTitle("Git repository");
		setDescription("Specify git repository");
	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(layout);
		layout.numColumns = 2;

		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setText("URL:");
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblUrl, true);

		txtURL = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtURL.setText("");

		txtURL.addModifyListener(event -> {
			cloneData.url = txtURL.getText();
			cloneData.externalRepoInfo = null;
			setUserAndPassControlsVisible(false);
			validateClientOnly();
		});
		txtURL.setLayoutData(gd);

		lblUser = new Label(container, SWT.NONE);
		lblUser.setText("User:");
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblUser, true);
		txtUser = new Text(container, SWT.BORDER | SWT.SINGLE);

		lblPwd = new Label(container, SWT.NONE);
		lblPwd.setText("Password:");
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblPwd, true);

		txtPwd = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtPwd.setEchoChar('*');

		txtUser.addModifyListener(event -> {
			cloneData.user = txtUser.getText();
			validateClientOnly();
		});
		txtPwd.addModifyListener(event -> {
			cloneData.pass = txtPwd.getText();
			validateClientOnly();
		});

		txtUser.setLayoutData(gd);
		txtPwd.setLayoutData(gd);

		setUserAndPassControlsVisible(false);

		setControl(container);
		setPageComplete(false);
	}

	private boolean validateClientOnly() {
		setMessage(null);
		setPageComplete(true);
		if (txtURL.getText().isEmpty() || !txtURL.getText().startsWith("https://")
				|| !txtURL.getText().endsWith(".git")) {
			setMessage("Please enter a valid git URL.", DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}

		// user and pw
		if (cloneData.externalRepoInfo != null && cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			setUserAndPassControlsVisible(true);
			if (txtUser.getText().isEmpty()) {
				setMessage("Please specify user", DialogPage.INFORMATION);
				setPageComplete(false);
				return false;
			}
			if (txtPwd.getText().isEmpty()) {
				setMessage("Please specify password", DialogPage.INFORMATION);
				setPageComplete(false);
				return false;
			}
		}
		return true;
	}

	public boolean validateAll() {
		if (!validateClientOnly()) {
			return false;
		}

		if (cloneData.repositories == null) {
			fetchRepositories();
			if (cloneData.repositories == null) {
				return false;
			}
		}
		if (cloneData.repositories.getRepositories().stream()
				.anyMatch(r -> r.getUrl().toString().equals(txtURL.getText()))) {
			setPageComplete(false);
			setMessage("The repository is already in use", DialogPage.ERROR);
			return false;
		}

		if (cloneData.externalRepoInfo == null) {
			fetchExternalRepoInfo();
			if (cloneData.externalRepoInfo == null) {
				return false;
			}
		}
		if (cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			if (!txtUser.isVisible()) {
				setUserAndPassControlsVisible(true);
				txtUser.setFocus();
				setPageComplete(false);
				setMessage("The repository is private. Please provide user and password", DialogPage.ERROR);
				return false;
			} else {
				// update the info, now that we have proper user/password
				if (!fetchExternalRepoInfo()) {
					return false;
				}
			}
		}

		return true;
	}

	private void fetchRepositories() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Fetching repositories...", IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destination,
							monitor);

					cloneData.repositories = repoService.getRepositories(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			setPageComplete(false);
			setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
		} catch (InterruptedException e) {
			// process was aborted
		}
	}

	private boolean fetchExternalRepoInfo() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Fetching repository information...", IProgressMonitor.UNKNOWN);
					IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
							.createExternalRepositoryInfoService(destination, monitor);
					cloneData.externalRepoInfo = externalRepoInfoService.getExternalRepositoryInfo(cloneData.url,
							cloneData.user, cloneData.pass, monitor);
				}
			});
			setPageComplete(true);
			setMessage(null);
			return true;
		} catch (InvocationTargetException e) {
			setPageComplete(false);
			setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
			return false;
		} catch (InterruptedException e) {
			// process was aborted
			return false;
		}
	}

	private void setUserAndPassControlsVisible(boolean visible) {
		txtUser.setVisible(visible);
		txtPwd.setVisible(visible);
		lblUser.setVisible(visible);
		lblPwd.setVisible(visible);
		if (!visible) {
			txtUser.setText("");
			txtPwd.setText("");
		}
	}

}
