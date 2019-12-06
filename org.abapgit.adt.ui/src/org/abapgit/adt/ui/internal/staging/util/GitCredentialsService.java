package org.abapgit.adt.ui.internal.staging.util;

import org.abapgit.adt.backend.AbapGitAuthenticationService;
import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.ui.internal.dialogs.AbapGitStagingCredentialsDialog;
import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.sap.adt.communication.resources.ResourceException;

/**
 * Utility class for handling git credentials
 */
public class GitCredentialsService {
	private static IExternalRepositoryInfoRequest repositoryCredentials;
	private static final String keyhttpStatus = "http_status"; //$NON-NLS-1$

	/**
	 * Checks if the credentials are required for a repository while loading.
	 * Credentials are required while loading, if the repository is private.
	 *
	 * @return Returns true if the repository is private
	 */
	public static boolean checkIfCredentialsRequired(IExternalRepositoryInfo repositoryExternalInfo) {
		//credentials are required for loading the staging details if the repo is private
		if (repositoryExternalInfo.getAccessMode() == AccessMode.PRIVATE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Opens a dialog for the user to enter the credentials for the git account
	 * with the error message from the previous try
	 */
	public static IExternalRepositoryInfoRequest getCredentialsFromUser(Shell shell, String errorMessage) {
		//open the user credentials pop-up
		repositoryCredentials = null;
		Display.getDefault().syncExec(() -> {
			Dialog userCredentialsDialog = new AbapGitStagingCredentialsDialog(shell, errorMessage);
			if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {
				repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog)
						.getExternalRepoInfo();
				if (repositoryCredentials != null) {
					//set the credentials
					AbapGitAuthenticationService.setRepositoryCredentials(repositoryCredentials);
				}
			}
		});
		return repositoryCredentials;
	}

	/**
	 * Opens a dialog for the user to enter the credentials for the git account
	 */
	public static IExternalRepositoryInfoRequest getCredentialsFromUser(Shell shell) {
		return getCredentialsFromUser(shell, null);
	}

	/**
	 * Checks whether the exception is of type authentication error
	 *
	 * @param httpStatus
	 *            Http status code from the exception
	 * @return true if the error code is of type authentication issue
	 */
	public static boolean isAuthenticationIssue(ResourceException exception) {
		if (exception.getExceptionProperties() != null) {
			String httpStatusValue = exception.getExceptionProperties().get(keyhttpStatus);
			if (httpStatusValue != null && httpStatusValue.length() > 0) {
				int httpStatus = Integer.parseInt(httpStatusValue);
				/*
				 * Authentication issues will result in error codes 401, 403 and 404
				 * Check the following documentation from Guthub :
				 * "There are two ways to authenticate through GitHub API v3. Requests that require authentication will return  404 Not Found ,
				 *  instead of  403 Forbidden , in some places. This is to prevent the accidental leakage of private repositories to unauthorized users."
				 */
				if (httpStatus == 401 || httpStatus == 403 || httpStatus == 404) {
					return true;
				}
			}
		}
		return false;
	}
}
