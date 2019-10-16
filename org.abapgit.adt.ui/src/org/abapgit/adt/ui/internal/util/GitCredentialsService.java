package org.abapgit.adt.ui.internal.util;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.ui.dialogs.AbapGitStagingCredentialsDialog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Utility class for handling git credentials
 */
public class GitCredentialsService {
	private static IExternalRepositoryInfoRequest repositoryCredentials;

	/**
	 * Checks if the credentials are required for a repository while loading.
	 * Credentials are required while loading, if the repository is private.
	 *
	 * @return Returns true if the repository is private
	 */
	public static boolean checkIfCredentialsRequired(IExternalRepositoryInfo repositoryExternalInfo, IProgressMonitor monitor) {
		//credentials are required for loading the staging details if the repo is private
		if (repositoryExternalInfo.getAccessMode() == AccessMode.PRIVATE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Opens a dialog for the user to enter the credentials for the git account
	 */
	public static IExternalRepositoryInfoRequest getUserCredentialsFromUser(Shell shell) {
		//open the user credentials pop-up
		repositoryCredentials = null;
		Display.getDefault().syncExec(() -> {
			Dialog userCredentialsDialog = new AbapGitStagingCredentialsDialog(shell);
			if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {
				repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog)
						.getExternalRepoInfo();
			}
		});
		return repositoryCredentials;
	}
}
