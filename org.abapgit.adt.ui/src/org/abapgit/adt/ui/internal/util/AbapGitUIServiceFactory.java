package org.abapgit.adt.ui.internal.util;

import org.abapgit.adt.backend.FileServiceFactory;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingService;
import org.abapgit.adt.ui.internal.staging.util.IAbapGitStagingService;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitUIServiceFactory {

	private AbapGitUIServiceFactory() {
	}

	public static IAbapGitService createAbapGitService() {
		return new AbapGitService();
	}

	public static IAbapGitStagingService createAbapGitStagingService() {
		return new AbapGitStagingService();
	}

	public static IRepositoryService createRepositoryService(IProject project) {
		return RepositoryServiceFactory.createRepositoryService(getDestination(project), new NullProgressMonitor());
	}

	public static IFileService createFileService() {
		return FileServiceFactory.createFileService();
	}

	private static String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

}
