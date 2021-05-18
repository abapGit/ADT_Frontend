package org.abapgit.adt.ui.internal.util;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.sap.adt.destinations.logon.AdtLogonServiceFactory;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.model.atom.IAtomLink;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitService implements IAbapGitService {

	@Override
	public boolean isLoggedOn(IProject project) {
		return AdtLogonServiceFactory.createLogonService().isLoggedOn(getDestination(project));
	}

	@Override
	public boolean ensureLoggedOn(IProject project) {
		if (AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project).isOK()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

	@Override
	public String getHrfFromAtomLink(Object object, String relation) {
		if (object instanceof IAbapGitFile) {
			IAbapGitFile file = (IAbapGitFile) object;
			for (IAtomLink link : file.getLinks()) {
				if (link.getRel().equalsIgnoreCase(relation)) {
					return link.getHref();
				}
			}
		} else if (object instanceof IAbapGitObject) {
			IAbapGitObject abapObject = (IAbapGitObject) object;
			for (IAtomLink link : abapObject.getLinks()) {
				if (link.getRel().equalsIgnoreCase(relation)) {
					return link.getHref();
				}
			}
		}
		return null;
	}

	@Override
	public boolean isAbapGitSupported(IProject project) {
		IRepositoryService service = RepositoryServiceFactory.createRepositoryService(getDestination(project), new NullProgressMonitor());
		return service != null ? true : false;
	}

	//TODO: Remove after 2105 back end release supporting selective pull reaches all customers
	@Override
	public boolean isSelectivePullSupported(IRepository repository) {
		/*
		 * Disable the selective pull temporarily because of the incident https://support.wdf.sap.corp/sap/support/message/2170159859. 
		 * Selective pull backend call runs into error while PULL as it somehow involves package creation which requires a transport request to be passed on.
		 */

//		for (IAtomLink link : repository.getLinks()) {
//			if (link.getRel().equalsIgnoreCase(IRepositoryService.RELATION_MODIFIED_OBJECTS)) {
//				return true;
//			}
//		}
		return false;
	}

}
