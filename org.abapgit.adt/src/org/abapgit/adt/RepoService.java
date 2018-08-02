package org.abapgit.adt;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.IAdtCoreProject;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class RepoService {

	private RepoService() {
	}

	// Initialization-on-demand holder idiom
	private static class Holder {
		private static final RepoService INSTANCE = new RepoService();
	}

	public static RepoService getInstance() {
		return Holder.INSTANCE;
	}

	// public List getAvailableProjects(IProject project, IProgressMonitor monitor)
	// {
	// String destination =
	// AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	//
	// return null;
	//
	// }
	//
	// public void getAvailableProjects(String destinationId) {
	//
	// }
}
