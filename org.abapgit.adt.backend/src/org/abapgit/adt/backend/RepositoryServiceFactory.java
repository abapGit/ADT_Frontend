package org.abapgit.adt;

import java.net.URI;

import org.abapgit.adt.internal.AbapGitDiscovery;
import org.abapgit.adt.internal.RepositoryService;
import org.eclipse.core.runtime.IProgressMonitor;

public class RepositoryServiceFactory {
	private RepositoryServiceFactory() {
	}
	
	public static IRepositoryService createRepositoryService(String destination, IProgressMonitor monitor) {
		URI uri = new AbapGitDiscovery().getReposUri(destination, monitor);
		if (uri == null) {
			return null; // might also throw ServiceNotAvailableException() 
		} else {
			return new RepositoryService(destination, uri);
		}
	}
}
