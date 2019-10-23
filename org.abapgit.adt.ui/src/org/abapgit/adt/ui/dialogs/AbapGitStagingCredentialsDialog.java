package org.abapgit.adt.ui.dialogs;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AbapGitStagingCredentialsDialog extends TitleAreaDialog {

	protected Text usernameField;
	protected Text passwordField;

	private String username;
	private String password;

	private String errorMessage;

	public AbapGitStagingCredentialsDialog(Shell parentShell, String errorMessage) {
		super(parentShell);
		this.errorMessage = errorMessage;
	}

	public AbapGitStagingCredentialsDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle(Messages.AbapGitStaging_credentials_dialog_title);
		if (this.errorMessage != null) {
			setMessage(this.errorMessage, IMessageProvider.ERROR);
		} else {
			setMessage(Messages.AbapGitStaging_credentials_dialog_desc, IMessageProvider.INFORMATION);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite credentialsComposite = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		credentialsComposite.setLayoutData(gd);
		GridLayout layout = new GridLayout(2, false);
		credentialsComposite.setLayout(layout);

		Label usernameLabel = new Label(credentialsComposite, SWT.NONE);
		usernameLabel.setText(Messages.AbapGitStaging_credentials_dialog_username);

		this.usernameField = new Text(credentialsComposite, SWT.SINGLE);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		this.usernameField.setLayoutData(data);

		Label passwordLabel = new Label(credentialsComposite, SWT.NONE);
		passwordLabel.setText(Messages.AbapGitStaging_credentials_dialog_password);

		this.passwordField = new Text(credentialsComposite, SWT.SINGLE | SWT.PASSWORD);
		data = new GridData(GridData.FILL_HORIZONTAL);
		this.passwordField.setLayoutData(data);

		return credentialsComposite;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	public void okPressed() {
		if (validateAndSetMessage()) {
			super.okPressed();
		}
	}

	private boolean validateAndSetMessage() {
		this.username = this.usernameField.getText().trim();
		if (this.username.isEmpty()) {
			setMessage(Messages.AbapGitStaging_credentials_dialog_error_invalid_username, IMessageProvider.ERROR);
			return false;
		}

		this.password = this.passwordField.getText();
		if (this.password.isEmpty()) {
			setMessage(Messages.AbapGitStaging_credentials_dialog_error_invalid_password, IMessageProvider.ERROR);
			return false;
		}

		setMessage(null);
		return true;
	}

	public IExternalRepositoryInfoRequest getExternalRepoInfo() {
		IExternalRepositoryInfoRequest info = new IExternalRepositoryInfoRequest() {
			@Override
			public String getUser() {
				return AbapGitStagingCredentialsDialog.this.username;
			}

			@Override
			public String getUrl() {
				return null;
			}

			@Override
			public String getPassword() {
				return AbapGitStagingCredentialsDialog.this.password;
			}
		};
		return info;
	}

}
