package org.abapgit.adt.ui.internal.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.dialogs.AbapGitStagingCredentialsDialog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.equinox.security.storage.provider.IProviderHints;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogTray;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.tools.core.ui.AbapCoreUi;
import com.sap.adt.tools.core.ui.BrowserUtil;
import com.sap.adt.tools.core.ui.browsers.IHtmlUtil;
import com.sap.adt.tools.core.ui.dialogs.ErrorDialogWithDetails;

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
			if (userCredentialsDialog.open() == IDialogConstants.OK_ID) {

				repositoryCredentials = ((AbapGitStagingCredentialsDialog) userCredentialsDialog).getExternalRepoInfo();
				if (repositoryCredentials != null && ((AbapGitStagingCredentialsDialog) userCredentialsDialog).storeInSecureStorage()) {
					//store the credentials in secure storage
					storeCredentialsInSecureStorage(repositoryCredentials, repositoryURL);
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
			String hashedURL = getMD5HashForUrl(repositoryURL);

			//Disable security questions
			Map<String, Boolean> options = new HashMap<String, Boolean>();
			options.put(IProviderHints.PROMPT_USER, false);

			ISecurePreferences preferences = null;
			try {
				preferences = SecurePreferencesFactory.open(null, options);
			} catch (IOException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}

			if (preferences != null && hashedURL != null) {
				ISecurePreferences node = preferences.node(hashedURL);
				try {
					node.put("user", repositoryCredentials.getUser(), false); //$NON-NLS-1$
					node.put("password", repositoryCredentials.getPassword(), true); //$NON-NLS-1$
				} catch (StorageException e) {
					AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}

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
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		String hashedURL = getMD5HashForUrl(repositoryURL);
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
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		String hashedURL = getMD5HashForUrl(repositoryURL);
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
	 * Returns MD5 hash value of the given repository URL. Used as a node value
	 * in the secure storage, due limitations of using actual repository url as
	 * node value in secure storage. Secure storage limits the length of node
	 * value and also limits the use of special characters.
	 *
	 * @param url
	 * @return MD5 hash value of the URL
	 */
	public static String getMD5HashForUrl(String url) {
		String hashedURL = null;

		if (url != null) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
				messageDigest.update(url.getBytes());
				byte[] digest = messageDigest.digest();
				hashedURL = DatatypeConverter.printHexBinary(digest).toUpperCase();
			} catch (NoSuchAlgorithmException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}
		return hashedURL;

	}

	/**
	 * @param dialog
	 *            Display longText in the DialogTray of the given dialog
	 */
	public static void handleExceptionAndDisplayInDialogTray(Exception e, TrayDialog dialog) {
		if (e instanceof ResourceException && GitCredentialsService.isAuthenticationIssue((ResourceException) e)) {

			final String longText = ((ResourceException) e).getExceptionLongtext();
			if (longText != null) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						showErrorInDialogTray(longText, dialog);
					}

					public void showErrorInDialogTray(String longtext, TrayDialog dialog) {
						GC gc = new GC(dialog.getShell());
						gc.setFont(JFaceResources.getDialogFont());
						FontMetrics fontMetrics = gc.getFontMetrics();
						gc.dispose();

						if (dialog.getTray() != null) {
							dialog.closeTray();
						}

						DialogTray tray = new DialogTray() {
							@Override
							protected Control createContents(Composite parent) {
								Composite root = new Composite(parent, SWT.NO_FOCUS);
								GridLayoutFactory.fillDefaults().spacing(0, 0).applyTo(root);
								Browser browser = BrowserUtil.createBrowserBasedOnPreferenceSettings(root);
								browser.setText(addStyleSheetTo(longtext));
								int xHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics, 200);
								GridDataFactory.fillDefaults().grab(true, true).hint(xHint, SWT.DEFAULT).applyTo(browser);
								return root;
							}

							private String addStyleSheetTo(String htmlPage) {
								IHtmlUtil htmlUtil = AbapCoreUi.getInstance().getHtmlUtil();
								return htmlUtil.getHtmlPage(htmlPage, htmlUtil.getDefaultStyleSheetForDialogs());
							}
						};

						dialog.openTray(tray);
					}

				});
			}

		}

	}


	public static void handleExceptionAndDisplayInErrorDialog(Exception e, Shell shell) {
		//invalid credentials : this condition check is only valid from 2002
		if (e instanceof ResourceException && GitCredentialsService.isAuthenticationIssue((ResourceException) e)) {
			String errorMessage = e.getMessage();
			final String longText = ((ResourceException) e).getExceptionLongtext();
			if (longText != null) {
				openErrorDialog(Messages.AbapGitStagingView_authentication_error, errorMessage, true, longText, shell);
			}
		}
	}

// Open Error Message in ErrorDialog and display longText in details area
	private static void openErrorDialog(String title, String errorMessage, boolean runInUIThread, String longText, Shell shell) {
		if (runInUIThread) {

			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					Dialog errorDialog = new ErrorDialogWithDetails(shell, AbapGitUIPlugin.PLUGIN_ID, IStatus.ERROR, title,
							errorMessage, longText);
					errorDialog.open();
				}
			});
		} else {
			Dialog errorDialog = new ErrorDialogWithDetails(shell, AbapGitUIPlugin.PLUGIN_ID, IStatus.ERROR, title,
					errorMessage, longText);
			errorDialog.open();
		}

	}

	/**
	 * Opens an error dialog box
	 *
	 * @param title
	 *            Title for the error dialog box
	 * @param message
	 *            Message to be displayed in the dialog box
	 * @param runInUIThread
	 *            Set it to true, if this method is called from a non UI thread
	 */

	public static void openErrorDialog(String title, String message, Shell shell, boolean runInUIThread) {
		if (runInUIThread) {
			Display.getDefault().syncExec(() -> MessageDialog.openError(shell, title, message));
		} else {
			MessageDialog.openError(shell, title, message);
		}
	}

}
