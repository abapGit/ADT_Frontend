package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.compatibility.discovery.AdtDiscoveryFactory;
import com.sap.adt.compatibility.discovery.IAdtDiscovery;
import com.sap.adt.compatibility.discovery.IAdtDiscoveryCollectionMember;

public class ApackDiscovery {

	private static final String SCHEME = "http://www.sap.com/adt/categories/apack"; //$NON-NLS-1$
	private static final String TERM_GIT_MANIFESTS = "gitmanifests"; //$NON-NLS-1$

	public URI getGitManifestsUri(String destination, IProgressMonitor progressMonitor) {
		return getUri(destination, progressMonitor, SCHEME, TERM_GIT_MANIFESTS);
	}

	private URI getUri(String destination, IProgressMonitor monitor, String scheme, String term) {
		IAdtDiscovery discovery = AdtDiscoveryFactory.createDiscovery(destination, AdtDiscoveryFactory.RESOURCE_URI);

		IAdtDiscoveryCollectionMember collectionMember = discovery.getCollectionMember(scheme, term, monitor);
		if (collectionMember == null) {
			return null;
		}

		return collectionMember.getUri();
	}

}
