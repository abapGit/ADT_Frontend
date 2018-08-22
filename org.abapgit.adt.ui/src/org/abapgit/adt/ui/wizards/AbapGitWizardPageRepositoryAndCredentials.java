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
		setTitle("Clone abapGit Repository");
		setDescription("Specify a abapGit Repository");
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

		this.txtURL = new Text(container, SWT.BORDER | SWT.SINGLE);
		this.txtURL.setText(""); //$NON-NLS-1$

		this.txtURL.addModifyListener(event -> {
			this.cloneData.url = this.txtURL.getText();
			this.cloneData.externalRepoInfo = null;
			setUserAndPassControlsVisible(false);
			validateClientOnly();
		});
		this.txtURL.setLayoutData(gd);

		this.lblUser = new Label(container, SWT.NONE);
		this.lblUser.setText("User:");
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(this.lblUser, true);
		this.txtUser = new Text(container, SWT.BORDER | SWT.SINGLE);

		this.lblPwd = new Label(container, SWT.NONE);
		this.lblPwd.setText("Password:");
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(this.lblPwd, true);

		this.txtPwd = new Text(container, SWT.BORDER | SWT.SINGLE);
		this.txtPwd.setEchoChar('*');

		this.txtUser.addModifyListener(event -> {
			this.cloneData.user = this.txtUser.getText();
			validateClientOnly();
		});
		this.txtPwd.addModifyListener(event -> {
			this.cloneData.pass = this.txtPwd.getText();
			validateClientOnly();
		});

		this.txtUser.setLayoutData(gd);
		this.txtPwd.setLayoutData(gd);

		setUserAndPassControlsVisible(false);

		setControl(container);
		setPageComplete(false);
	}

	private boolean validateClientOnly() {
		setMessage(null);
		setPageComplete(true);
		if (this.txtURL.getText().isEmpty() || !this.txtURL.getText().startsWith("https://") //$NON-NLS-1$
				|| !this.txtURL.getText().endsWith(".git")) { //$NON-NLS-1$
			setMessage("Please enter a valid git URL.", DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}

		// user and pw
		if (this.cloneData.externalRepoInfo != null && this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			setUserAndPassControlsVisible(true);
			if (this.txtUser.getText().isEmpty()) {
				setMessage("Please specify user", DialogPage.INFORMATION);
				setPageComplete(false);
				return false;
			}
			if (this.txtPwd.getText().isEmpty()) {
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

		if (this.cloneData.repositories == null) {
			fetchRepositories();
			if (this.cloneData.repositories == null) {
				return false;
			}
		}
		if (this.cloneData.repositories.getRepositories().stream()
				.anyMatch(r -> r.getUrl().toString().equals(this.txtURL.getText()))) {
			setPageComplete(false);
			setMessage("The repository is already in use", DialogPage.ERROR);
			return false;
		}

		if (this.cloneData.externalRepoInfo == null) {
			fetchExternalRepoInfo();
			if (this.cloneData.externalRepoInfo == null) {
				return false;
			}
		}
		if (this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			if (!this.txtUser.isVisible()) {
				setUserAndPassControlsVisible(true);
				this.txtUser.setFocus();
				setPageComplete(false);
				setMessage("The abapGit repository is private. Please provide user and password", DialogPage.ERROR);
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
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizardPageRepositoryAndCredentials.this.destination,
							monitor);

					AbapGitWizardPageRepositoryAndCredentials.this.cloneData.repositories = repoService.getRepositories(monitor);
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
							.createExternalRepositoryInfoService(AbapGitWizardPageRepositoryAndCredentials.this.destination, monitor);
					AbapGitWizardPageRepositoryAndCredentials.this.cloneData.externalRepoInfo = externalRepoInfoService.getExternalRepositoryInfo(AbapGitWizardPageRepositoryAndCredentials.this.cloneData.url,
							AbapGitWizardPageRepositoryAndCredentials.this.cloneData.user, AbapGitWizardPageRepositoryAndCredentials.this.cloneData.pass, monitor);
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
		this.txtUser.setVisible(visible);
		this.txtPwd.setVisible(visible);
		this.lblUser.setVisible(visible);
		this.lblPwd.setVisible(visible);
		if (!visible) {
			this.txtUser.setText(""); //$NON-NLS-1$
			this.txtPwd.setText(""); //$NON-NLS-1$
		}
	}

}
