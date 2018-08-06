package org.abapgit.adt.internal;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.compatibility.discovery.AdtDiscoveryFactory;
import com.sap.adt.compatibility.discovery.IAdtDiscovery;
import com.sap.adt.compatibility.discovery.IAdtDiscoveryCollectionMember;

public class AbapGitDiscovery {

	private static final String SCHEME = "http://.../abapgit"; //$NON-NLS-1$
	private static final String TERM_REPOS = "repos"; //$NON-NLS-1$

	public URI getReposUri(String destination, IProgressMonitor monitor) {
		IAdtDiscovery discovery = AdtDiscoveryFactory.createDiscovery(destination,
				AdtDiscoveryFactory.CORE_RESOURCE_URI);
		IAdtDiscoveryCollectionMember repoMember = discovery.getCollectionMember(SCHEME, TERM_REPOS, monitor);
		if (repoMember == null) {
			return null; // Alternativ z.B. throw new ServiceNotAvailableException();
		}
		return repoMember.getUri();
	}
}
