package org.abapgit.adt.backend;

import java.net.URI;

import org.abapgit.adt.backend.internal.ApackDiscovery;
import org.abapgit.adt.backend.internal.ApackGitManifestService;
import org.eclipse.core.runtime.IProgressMonitor;

public class ApackServiceFactory {

	private ApackServiceFactory() {
		// Service class with static methods only...
	}

	public static IApackGitManifestService createApackGitManifestService(String destinationId, IProgressMonitor monitor) {
		URI uri = new ApackDiscovery().getGitManifestsUri(destinationId, monitor);
		if (uri == null) {
			return null;
		} else {
			return new ApackGitManifestService(destinationId, uri);
		}
	}

}
