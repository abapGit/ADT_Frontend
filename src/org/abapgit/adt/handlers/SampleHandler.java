package org.abapgit.adt.handlers;

import org.abapgit.adt.core.Repository;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.resources.ResourceNotFoundException;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
//			Repository repo = Repository.get("000000000003");
//			openDialogWindow(repo.getDevclass(), "Title");
			
			Repository[] list = Repository.list();
			for (Repository repo : list) {
				System.out.println(repo.toString());
			}
		} catch (ResourceNotFoundException e) {
			displayError("Error, ResourceNotFound");
		} catch (RuntimeException e) {
			displayError(e.getMessage());
		}
		return null;
	}

	private void displayError(String messageText) {
		String dialogTitle = "Error";
		openDialogWindow(messageText, dialogTitle);
	}

	protected void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };

		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null, dialogText, MessageDialog.INFORMATION,
				DIALOG_BUTTON_LABELS, 0);

		dialog.open();
	}

	protected Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}
