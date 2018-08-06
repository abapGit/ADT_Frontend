package org.abapgit.adt.ui.wizards;

import java.net.HttpURLConnection;
import java.net.URI;

import org.abapgit.adt.IAdtAbapGitConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.message.HeadersFactory;
import com.sap.adt.communication.message.IHeaders;
import com.sap.adt.communication.message.IHeaders.IField;
import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.message.IResponse.IErrorInfo;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.IAdtCoreProject;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.project.IAbapProject;

public class AbapGitRequest {
	
	private IWorkbenchWindow window;
	private ITreeSelection selection;
	private String body;
	
	public AbapGitRequest(IWorkbenchWindow window,
			ITreeSelection selection, String xmlBody) {
		this.window = window;
		this.selection = selection;
		this.body = xmlBody;
	}
	
//	private URI getResourceUri(String destination) {
//		IAdtDiscovery discovery = AdtDiscoveryFactory.createDiscovery(
//				destination,
//				URI.create(IAdtTransportUtilsConstants.DISCOVERY_URI));
//
//		IAdtDiscoveryCollectionMember collectionMember = discovery
//				.getCollectionMember(
//						IAdtTransportUtilsConstants.TOC_RESOURCE_SCHEME,
//						IAdtTransportUtilsConstants.TOC_TERM,
//						new NullProgressMonitor());
//
//		if (collectionMember == null) {
//			return null;
//		} else {
//			return collectionMember.getUri();
//		}
//	}
	
	private URI getResourceUri(String destination) {
		URI abapGitUri = URI.create("/sap/bc/adt/abapgit/repos");
		return abapGitUri;	
	}
	
	public void executePost() {
		String destination = this.getAbapProjectDestination();
		URI agitResourceUri = this.getResourceUri(destination);
		
		if (agitResourceUri != null) {
			this.executePost(destination, agitResourceUri, body);
		} else {
			MessageDialog.openError(this.window.getShell(),
					"ABAP Git Utils Error",
					"Necessary backend resource could not be found. ");
		}
	}
	
	
	private void executePost(String destination, URI agitResourceUri, String body) {
		IRestResource agitRessource = AdtRestResourceFactory
				.createRestResourceFactory()
				.createResourceWithStatelessSession(agitResourceUri, destination);

//		IRequest abapGitRequest = (IRequest) this.selection.getFirstElement();
		
		String xmlVersion = IAdtAbapGitConstants.QUERY_PARAMETER_XML_VERSION;
		IField headerAccept = HeadersFactory.newField("Accept", xmlVersion);
		IField headerCT = HeadersFactory.newField("Content-Type", xmlVersion);
	    IHeaders requestHeader = HeadersFactory.newHeaders();
	    requestHeader.addField(headerAccept);
	    requestHeader.addField(headerCT);
		
//        System.out.println("Request Header");
//        System.out.println(requestHeader);   
		

		try {
			IResponse response = agitRessource
					.post(null, requestHeader, IResponse.class, body);
			int status = response.getStatus();
			if (status != HttpURLConnection.HTTP_OK) {
				IErrorInfo errorInfo = response.getErrorInfo();
				MessageDialog
						.openError(
								this.window.getShell(),
								"ABAP Git Utils Error",
								"An error occured during ABAP git clone action! The error was: "
										+ errorInfo.getMessage());
			}
		} catch (RuntimeException e) {
			MessageDialog
					.openError(
							this.window.getShell(),
							"ABAP Git Utils Error",
							"An exception occured during ABAP git clone action! Exception: "
									+ e.getMessage());
		}

	}
	
	private String getAbapProjectDestination() {
		IProject project = ProjectUtil.getActiveAdtCoreProject(this.selection,
				null, null, IAdtCoreProject.ABAP_PROJECT_NATURE);
		IAbapProject abapProject = (IAbapProject) project
				.getAdapter(IAbapProject.class);
		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(
				abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());
		String destination = abapProject.getDestinationId();
		return destination;
	}

}
