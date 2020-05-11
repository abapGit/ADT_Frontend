package org.abapgit.adt.ui.internal.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.dialogs.AbapGitStagingCredentialsDialog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.security.storage.EncodingUtils;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.equinox.security.storage.provider.IProviderHints;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.sap.adt.communication.resources.ResourceException;

/**
 * Utility class for handling git credentials
 */
public class GitCredentialsService {
	private static IExternalRepositoryInfoRequest repositoryCredentials;
	private static final String keyhttpStatus = "http_status"; //$NON-NLS-1$
	private static final String abapGitPathPrefix = "/abapGit/"; //$NON-NLS-1$

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
	 * with the error message from the previous try.
	 * Retrieves credentials from secure storage
	 */
	public static IExternalRepositoryInfoRequest getCredentialsFromUser(Shell shell,
			String repositoryURL, String errorMessage) {
		//get credentials from the secure storage if present
		repositoryCredentials = getRepoCredentialsFromSecureStorage(repositoryURL);

		//open the user credentials pop-up
		Display.getDefault().syncExec(() -> {
			Dialog userCredentialsDialog = new AbapGitStagingCredentialsDialog(shell, errorMessage, repositoryCredentials);
			if (repositoryCredentials != null) {
				((AbapGitStagingCredentialsDialog) userCredentialsDialog).setStoreInSecureStorage(true);
			}
			if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {
				repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog).getExternalRepoInfo();
				if (repositoryCredentials != null) {

					if (((AbapGitStagingCredentialsDialog) userCredentialsDialog).storeInSecureStorage()) {
						//store the credentials in secure storage
						storeCredentialsInSecureStorage(repositoryCredentials, repositoryURL);
					} else {
						deleteCredentialsFromSecureStorage(repositoryURL);
					}
				}
			}
			else {
				repositoryCredentials = null;
			}
		});
		return repositoryCredentials;
	}

	/**
	 * Opens a dialog for the user to enter the credentials for the git account to access the given repository URL
	 */
	public static IExternalRepositoryInfoRequest getCredentialsFromUser(Shell shell, String repositoryURL) {
		return getCredentialsFromUser(shell, repositoryURL, null);
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

	/**
	 *
	 * @param repositoryCredentials
	 * @param repositoryURL
	 *            store repositoryCredentials in Secure Store for the given
	 *            repositoryURL
	 */
	public static void storeCredentialsInSecureStorage(IExternalRepositoryInfoRequest repositoryCredentials, String repositoryURL) {
		if (repositoryCredentials != null && repositoryURL != null) {
			String hashedURL = getUrlForNodePath(repositoryURL);

			//Disable security questions
			Map<String, Boolean> options = new HashMap<String, Boolean>();
			options.put(IProviderHints.PROMPT_USER, false);

			try {
				ISecurePreferences preferences = SecurePreferencesFactory.open(null, options);
				if (preferences != null && hashedURL != null) {
					ISecurePreferences node = preferences.node(hashedURL);
					node.put("user", repositoryCredentials.getUser(), false); //$NON-NLS-1$
					node.put("password", repositoryCredentials.getPassword(), true); //$NON-NLS-1$
				}
			} catch (IOException | StorageException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}

	}

	/**
	 *
	 * @param repositoryURL
	 *            delete credentials for the given repository url from Secure
	 *            Store, if they already exist in the Secure Store.
	 */
	public static void deleteCredentialsFromSecureStorage(String repositoryURL) {
		if (repositoryURL == null) {
			return;
		}
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		String hashedURL = getUrlForNodePath(repositoryURL);
		if (hashedURL != null && preferences.nodeExists(hashedURL)) {
			ISecurePreferences node = preferences.node(hashedURL);
			node.removeNode();
		}
	}

	/**
	 *
	 * @param repositoryURL
	 * @return the credentials from Secure Store for the given repository url
	 */

	private static IExternalRepositoryInfoRequest getRepoCredentialsFromSecureStorage(String repositoryURL) {
		if (repositoryURL == null) {
			return null;
		}
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		String hashedURL = getUrlForNodePath(repositoryURL);
		if (hashedURL != null && preferences.nodeExists(hashedURL)) {
			ISecurePreferences node = preferences.node(hashedURL);
			return new IExternalRepositoryInfoRequest() {
				@Override
				public String getUser() {
					try {
						return node.get("user", null); //$NON-NLS-1$
					} catch (StorageException e) {
						AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
					}
					return null;
				}

				@Override
				public String getUrl() {
					return repositoryURL;
				}

				@Override
				public String getPassword() {
					try {
						return node.get("password", null); //$NON-NLS-1$
					} catch (StorageException e) {
						AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
					}

					return null;
				}
			};
		}

		return null;
	}

	/**
	 * Returns slash encoded value of the given repository URL. Used as a node
	 * path value in the secure storage, due limitations of using actual
	 * repository url as node value in secure storage. Secure storage limits the
	 * length of node value and also limits the use of special characters.
	 *
	 * @param url
	 * @return node path for url
	 */
	public static String getUrlForNodePath(String uri) {
		if (uri == null) {
			return null;
		}
		String nodePath = abapGitPathPrefix + EncodingUtils.encodeSlashes(uri.toString());
		return nodePath;
	}

	//Open a popup asking if user wants to store the credentials in secure store
	public static boolean showPopUpAskingToStoreCredentials(Shell shell, String url, String user, String pass) {
		//since the credentials are proper ask if they should be stored in secure store
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setText(Messages.AbapGitWizardPageRepositoryAndCredentials_credentials_manager_popup_title);
		messageBox.setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_store_creds_in_secure_storage);
		int rc = messageBox.open();

		if (rc == SWT.YES) {
			//Store credentials in Secure Store if user chose to in credentials page
			IExternalRepositoryInfoRequest credentials = new IExternalRepositoryInfoRequest() {

				@Override
				public String getUser() {
					return user;
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public String getPassword() {
					return pass;
				}
			};
			GitCredentialsService.storeCredentialsInSecureStorage(credentials, url);
		} else {

			//delete credentials from secure store
			GitCredentialsService.deleteCredentialsFromSecureStorage(url);
		}

		return true;
	}

}
