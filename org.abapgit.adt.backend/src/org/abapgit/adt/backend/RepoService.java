package org.abapgit.adt.backend;

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
