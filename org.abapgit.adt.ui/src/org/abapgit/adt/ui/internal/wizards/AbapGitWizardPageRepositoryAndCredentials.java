package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sap.adt.util.ui.swt.AdtSWTUtilFactory;

public class AbapGitWizardPageRepositoryAndCredentials extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageRepositoryAndCredentials.class.getName();

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;

	private Text txtURL;
	private Text txtUser;
	private Text txtPwd;
	private Label lblUser;
	private Label lblPwd;
	private Pattern r;
	private String ptrn;
	private String newUrl;

	private final Boolean pullAction;
	private boolean wasVisibleBefore;

	public AbapGitWizardPageRepositoryAndCredentials(IProject project, String destination, CloneData cloneData, Boolean pullAction) {
		super(PAGE_NAME);
		this.project = project;
		this.destination = destination;
		this.cloneData = cloneData;
		this.pullAction = pullAction;
		setTitle(Messages.AbapGitWizardPageRepositoryAndCredentials_title);
		setDescription(Messages.AbapGitWizardPageRepositoryAndCredentials_description);

		if (this.cloneData.url != null) {
			setTitle(Messages.AbapGitWizardPull_title);
		}
	}

	@Override
	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		container.setLayout(layout);
		layout.numColumns = 2;

		Label lblUrl = new Label(container, SWT.NONE);
		lblUrl.setText(Messages.AbapGitWizardPageRepositoryAndCredentials_label_url);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblUrl, true);

		this.txtURL = new Text(container, SWT.BORDER | SWT.SINGLE);
		this.txtURL.setText(""); //$NON-NLS-1$

		this.ptrn = "((git@|https:\\/\\/)([\\w]+)(\\/|:))([\\w,\\-,\\_]+)\\@"; //$NON-NLS-1$
		this.r = Pattern.compile(this.ptrn);

		this.txtURL.addModifyListener(event -> {
			//remove trailing blanks from URL
			this.cloneData.url = this.txtURL.getText().trim();
			this.cloneData.externalRepoInfo = null;
			validateClientOnly();
		});
		this.txtURL.setLayoutData(gd);


		this.lblUser = new Label(container, SWT.NONE);
		this.lblUser.setText(Messages.AbapGitWizardPageRepositoryAndCredentials_label_user);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(this.lblUser, true);
		this.txtUser = new Text(container, SWT.BORDER | SWT.SINGLE);

		this.lblPwd = new Label(container, SWT.NONE);
		this.lblPwd.setText(Messages.AbapGitWizardPageRepositoryAndCredentials_label_password);
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

		if (this.cloneData.url != null) {
			this.txtURL.setText(this.cloneData.url);
			this.txtURL.setEnabled(false);
			this.cloneData.externalRepoInfo = null;
			validateAll();
		}

	}

	@Override
	public void setVisible(boolean visible) {
		//Navigate to transport request page if repo is public
		if (this.cloneData.externalRepoInfo != null && this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PUBLIC
				&& this.pullAction) {
			getContainer().showPage(getNextPage());
			getContainer().getCurrentPage().setVisible(visible);
			getContainer().getCurrentPage().setPreviousPage(getContainer().getCurrentPage());
			return;
		}

		if (visible && !this.wasVisibleBefore) {

			this.wasVisibleBefore = true;
			boolean isSupported[] = new boolean[1];
			try {
				getContainer().run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask(Messages.AbapGitWizardPageRepositoryAndCredentials_task_check_compatibility,
								IProgressMonitor.UNKNOWN);
						isSupported[0] = RepositoryServiceFactory
								.createRepositoryService(AbapGitWizardPageRepositoryAndCredentials.this.destination, monitor) != null;
					}
				});
				if (!isSupported[0]) {
					setPageComplete(false);
					setMessage(NLS.bind(Messages.AbapGitView_not_supported, this.project.getName()), DialogPage.ERROR);
					return;
				}
			} catch (InvocationTargetException | InterruptedException e) {
				setPageComplete(false);
				setMessage(e.getMessage(), DialogPage.ERROR);
				return;
			}

		}

		super.setVisible(visible);

	}

	private boolean validateClientOnly() {
		setMessage(null);
		setPageComplete(true);

		if (this.txtURL.getText().isEmpty() || !this.txtURL.getText().startsWith("https://")) { //$NON-NLS-1$
			setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_validate_url_error, DialogPage.INFORMATION);
			setPageComplete(false);
			return false;
		}

		// user and pw
		if (this.cloneData.externalRepoInfo != null && this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			setUserAndPassControlsVisible(true);
			if (this.txtUser.getText().isEmpty()) {
				setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_validate_user_error, DialogPage.INFORMATION);
				setPageComplete(false);
				return false;
			}
			if (this.txtPwd.getText().isEmpty()) {
				setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_validate_password_error, DialogPage.INFORMATION);
				setPageComplete(false);
				return false;
			}
		}

		Matcher matcher = this.r.matcher(this.txtURL.getText());
		if (matcher.find() && this.txtURL.getText().endsWith(".git")) { //$NON-NLS-1$
			this.newUrl = this.txtURL.getText();
			this.txtURL.setText(this.newUrl.replaceAll(this.ptrn, "https://")); //$NON-NLS-1$


			setUserAndPassControlsVisible(true);

			this.txtUser.setText(matcher.group(3));
			this.txtPwd.setText(matcher.group(5));

			setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_repo_user_pass, DialogPage.INFORMATION);
			setPageComplete(true);
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
				.anyMatch(r -> r.getUrl().toString().equals(this.txtURL.getText())) && !this.pullAction) {
			setPageComplete(false);
			setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_repo_in_use_error, DialogPage.ERROR);
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
				setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_repo_is_private, DialogPage.INFORMATION);
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
					monitor.beginTask(Messages.AbapGitWizardPageRepositoryAndCredentials_task_repo_fetch, IProgressMonitor.UNKNOWN);
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
					monitor.beginTask(Messages.AbapGitWizardPageRepositoryAndCredentials_task_fetch_repo_info, IProgressMonitor.UNKNOWN);
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
