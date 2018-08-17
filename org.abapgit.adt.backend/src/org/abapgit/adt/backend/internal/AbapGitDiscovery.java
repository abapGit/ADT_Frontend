package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.compatibility.discovery.AdtDiscoveryFactory;
import com.sap.adt.compatibility.discovery.IAdtDiscovery;
import com.sap.adt.compatibility.discovery.IAdtDiscoveryCollectionMember;

public class AbapGitDiscovery {

	private static final String SCHEME = "http://www.sap.com/adt/categories/abapgit"; //$NON-NLS-1$
	private static final String TERM_REPOS = "repos"; //$NON-NLS-1$
	private static final String TERM_EXTERNAL_REPO_INFO = "externalrepoinfo"; //$NON-NLS-1$

	public URI getReposUri(String destination, IProgressMonitor monitor) {
		return getUri(destination, monitor, SCHEME, TERM_REPOS);
	}

	public URI getExternalRepoInfoUri(String destination, IProgressMonitor monitor) {
		return getUri(destination, monitor, SCHEME, TERM_EXTERNAL_REPO_INFO);
	}

	private URI getUri(String destination, IProgressMonitor monitor, String scheme, String term) {
		IAdtDiscovery discovery = AdtDiscoveryFactory.createDiscovery(destination, AdtDiscoveryFactory.RESOURCE_URI);

		IAdtDiscoveryCollectionMember repoMember = discovery.getCollectionMember(scheme, term, monitor);
		if (repoMember == null) {
			return null;
		}

		return repoMember.getUri();
	}

}
