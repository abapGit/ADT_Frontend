package org.abapgit.adt.ui.internal.util;

import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogTray;
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

public class ErrorHandlingService {
	/**
	 * @param dialog
	 *            Display longText in the DialogTray of the given dialog
	 */
	public static void handleExceptionAndDisplayInDialogTray(Exception e, TrayDialog dialog) {
		if (e instanceof ResourceException) {

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
		if (e instanceof ResourceException) {
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

			Display.getDefault().syncExec(() -> {
				Dialog errorDialog = new ErrorDialogWithDetails(shell, AbapGitUIPlugin.PLUGIN_ID, IStatus.ERROR, title, errorMessage,
						longText);
				errorDialog.open();
			});
		} else {
			Dialog errorDialog = new ErrorDialogWithDetails(shell, AbapGitUIPlugin.PLUGIN_ID, IStatus.ERROR, title, errorMessage, longText);
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
