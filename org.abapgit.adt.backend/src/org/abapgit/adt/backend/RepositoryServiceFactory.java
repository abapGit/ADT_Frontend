package org.abapgit.adt.backend;

import java.net.URI;

import org.abapgit.adt.backend.internal.AbapGitDiscovery;
import org.abapgit.adt.backend.internal.ExternalRepositoryInfoService;
import org.abapgit.adt.backend.internal.RepositoryService;
import org.eclipse.core.runtime.IProgressMonitor;

public class RepositoryServiceFactory {
	private RepositoryServiceFactory() {
	}

	public static IRepositoryService createRepositoryService(String destinationId, IProgressMonitor monitor) {
		URI uri = new AbapGitDiscovery().getReposUri(destinationId, monitor);
		if (uri == null) {
			return null;
		} else {
			return new RepositoryService(destinationId, uri);
		}
	}

	public static IExternalRepositoryInfoService createExternalRepositoryInfoService(String destinationId,
			IProgressMonitor monitor) {
		URI uri = new AbapGitDiscovery().getExternalRepoInfoUri(destinationId, monitor);
		if (uri == null) {
			return null;
		} else {
			return new ExternalRepositoryInfoService(destinationId, uri);
		}
	}
}
