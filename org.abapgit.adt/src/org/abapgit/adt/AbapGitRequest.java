package org.abapgit.adt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.widgets.Shell;
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
	
	private Shell currShell;
	private ITreeSelection selection;
	private String body;
	
	public AbapGitRequest(Shell currShell,
			ITreeSelection selection, String xmlBody) {
		this.currShell = currShell;
//		this.selection = selection;
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
	
	private URI getResourceUri() {
		URI abapGitUri = URI.create("/sap/bc/adt/abapgit/repos");
		return abapGitUri;	
	}
	
	public void executePost() {
		String destination = this.getAbapProjectDestination();
		URI agitResourceUri = this.getResourceUri();
		
		if (agitResourceUri != null) {
			this.executePost(destination, agitResourceUri, body);
		} else {
			MessageDialog.openError(currShell,
					"ABAP Git Utils Error",
					"Necessary backend resource could not be found. ");
		}
	}
	
	public List<Repository> executeGet() {
		
		String destination = this.getAbapProjectDestination();
		URI agitResourceUri = this.getResourceUri();
		
		IResponse getResponse = null;
		if (agitResourceUri != null) {
			getResponse = this.executeGet(destination, agitResourceUri);
		} else {
			MessageDialog.openError(currShell,
					"ABAP Git Utils Error",
					"Necessary backend resource could not be found. ");
		}
		

		List<Repository> repoList = new ArrayList<Repository>();
		repoList.add(new Repository("No packages found...", "", "", "", ""));
		if(getResponse != null) {
			try {
				repoList = REST.parseListRepositories(getResponse);
			} catch (IOException | XMLStreamException e) {
				e.printStackTrace();
				System.out.println("error parsing!");
			}	
		}
		
		return repoList;
		
	}
	
	
	private void executePost(String destination, URI agitResourceUri, String body) {
		IRestResource agitRessource = AdtRestResourceFactory
				.createRestResourceFactory()
				.createResourceWithStatelessSession(agitResourceUri, destination);
		
		String xmlVersion = IAdtAbapGitConstants.QUERY_PARAMETER_XML_VERSION;
		IField headerAccept = HeadersFactory.newField("Accept", xmlVersion);
		IField headerCT = HeadersFactory.newField("Content-Type", xmlVersion);
	    IHeaders requestHeader = HeadersFactory.newHeaders();
	    requestHeader.addField(headerAccept);
	    requestHeader.addField(headerCT);
		
		try {
			IResponse response = agitRessource
					.post(null, requestHeader, IResponse.class, body);
			int status = response.getStatus();
			if (status != HttpURLConnection.HTTP_OK) {
				IErrorInfo errorInfo = response.getErrorInfo();
				MessageDialog
						.openError(
								currShell,
								"ABAP Git Utils Error",
								"An error occured during ABAP git clone action! The error was: "
										+ errorInfo.getMessage());
			}
		} catch (RuntimeException e) {
			MessageDialog
					.openError(
							currShell,
							"ABAP Git Utils Error",
							"An exception occured during ABAP git clone action! Exception: "
									+ e.getMessage());
		}

	}
	

	private IResponse executeGet(String destination, URI agitResourceUri) {
		IRestResource agitRessource = AdtRestResourceFactory
				.createRestResourceFactory()
				.createResourceWithStatelessSession(agitResourceUri, destination);
		
		String xmlVersion = IAdtAbapGitConstants.QUERY_PARAMETER_XML_VERSION;
		IField headerAccept = HeadersFactory.newField("Accept", xmlVersion);
		IField headerCT = HeadersFactory.newField("Content-Type", xmlVersion);
	    IHeaders requestHeader = HeadersFactory.newHeaders();
	    requestHeader.addField(headerAccept);
	    requestHeader.addField(headerCT);
	    
	    IResponse response = null;	    
		try {
			response = agitRessource
					.get(null, requestHeader, IResponse.class);
			
			int status = response.getStatus();
			if (status != HttpURLConnection.HTTP_OK) {
				IErrorInfo errorInfo = response.getErrorInfo();
				MessageDialog
						.openError(
								currShell,
								"ABAP Git Utils Error",
								"An error occured during ABAP git refresh action! The error was: "
										+ errorInfo.getMessage());
			}
		} catch (RuntimeException e) {
			MessageDialog
					.openError(
							currShell,
							"ABAP Git Utils Error",
							"An exception occured during ABAP git refresh action! Exception: "
									+ e.getMessage());
		}
		
		return response;
		
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
