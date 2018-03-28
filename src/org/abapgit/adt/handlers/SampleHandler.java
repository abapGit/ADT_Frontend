package org.abapgit.adt.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import java.net.URI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceFactory;
import com.sap.adt.communication.resources.ResourceNotFoundException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	private static final String SAMPLE_FLIGHT_RESOURCE_URI = "/sap/bc/adt/abapgit/repos";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*
		 * IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		 * MessageDialog.openInformation( window.getShell(), "abapGit ADT plugin",
		 * "Hello, Eclipse world"); return null;
		 */

		// Create resource factory
		IRestResourceFactory restResourceFactory = AdtRestResourceFactory.createRestResourceFactory();
		// Get available projects in the workspace
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();
		// Use the first project in the workspace for the demo
		// to keep the example simple
		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

		// Trigger logon dialog if necessary
		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());
		// Create REST resource for given destination and URI
		String destination = abapProject.getDestinationId();
		URI flightUri = URI.create(SAMPLE_FLIGHT_RESOURCE_URI);
		IRestResource flightResource = restResourceFactory.createResourceWithStatelessSession(flightUri, destination);
		
		try {
			// Trigger GET request on resource data
			IResponse response = flightResource.get(null, IResponse.class);

			openDialogWindow("Flight exists! HTTP-status:" + String.valueOf(response.getStatus()),
					"Flight Confirmation");
		} catch (ResourceNotFoundException e) {
			displayError("No flight data found");
		} catch (RuntimeException e) {
			displayError(e.getMessage());
		}
		return null;
	}

	private void displayError(String messageText) {
		String dialogTitle = "Flight Exception";
		openDialogWindow(messageText, dialogTitle);
	}

	protected void openDialogWindow(String dialogText, String dialogTitle) {
		String[] DIALOG_BUTTON_LABELS = new String[] { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), dialogTitle, null, dialogText, MessageDialog.INFORMATION,
				DIALOG_BUTTON_LABELS, 0);
		dialog.open();
	}

	protected Shell getShell() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return shell;
	}

}
