package org.abapgit.adt.ui.i18n;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.abapgit.adt.ui.i18n.messages"; //$NON-NLS-1$
	public static String AbapGitView_action_clone;
	public static String AbapGitView_action_refresh;
	public static String AbapGitView_column_branch;
	public static String AbapGitView_column_firstcommitat;
	public static String AbapGitView_column_package;
	public static String AbapGitView_column_url;
	public static String AbapGitView_column_user;
	public static String AbapGitView_context_dialog_unlink_message;
	public static String AbapGitView_context_dialog_unlink_title;
	public static String AbapGitView_context_unlink;
	public static String AbapGitView_context_unlink_error;
	public static String AbapGitView_task_fetch_repos;
	public static String AbapGitView_task_fetch_repos_error;
	public static String AbapGitWizard_title;
	public static String AbapGitWizardPageBranchAndPackage_btn_browse;
	public static String AbapGitWizardPageBranchAndPackage_combobox_branch_message;
	public static String AbapGitWizardPageBranchAndPackage_description;
	public static String AbapGitWizardPageBranchAndPackage_label_branch;
	public static String AbapGitWizardPageBranchAndPackage_label_package;
	public static String AbapGitWizardPageBranchAndPackage_task_package_validation_error_message;
	public static String AbapGitWizardPageBranchAndPackage_task_package_validation_message;
	public static String AbapGitWizardPageBranchAndPackage_text_package_message;
	public static String AbapGitWizardPageBranchAndPackage_title;
	public static String AbapGitWizardPageRepositoryAndCredentials_description;
	public static String AbapGitWizardPageRepositoryAndCredentials_label_password;
	public static String AbapGitWizardPageRepositoryAndCredentials_label_url;
	public static String AbapGitWizardPageRepositoryAndCredentials_label_user;
	public static String AbapGitWizardPageRepositoryAndCredentials_repo_in_use_error;
	public static String AbapGitWizardPageRepositoryAndCredentials_repo_is_private;
	public static String AbapGitWizardPageRepositoryAndCredentials_task_fetch_repo_info;
	public static String AbapGitWizardPageRepositoryAndCredentials_task_repo_fetch;
	public static String AbapGitWizardPageRepositoryAndCredentials_title;
	public static String AbapGitWizardPageRepositoryAndCredentials_validate_password_error;
	public static String AbapGitWizardPageRepositoryAndCredentials_validate_url_error;
	public static String AbapGitWizardPageRepositoryAndCredentials_validate_user_error;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
