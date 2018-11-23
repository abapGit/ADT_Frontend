package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IApackGitManifestService;
import org.abapgit.adt.backend.IApackManifest;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;

public class ApackGitManifestService implements IApackGitManifestService {

	private final String destinationId;
	private final URI uri;

	public ApackGitManifestService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IApackManifest getManifest(String url, String branch, String user, String password, IProgressMonitor monitor) {
		ApackGitManifestRequest gitManifestRequest = new ApackGitManifestRequest();
		gitManifestRequest.setUrl(url);
		gitManifestRequest.setBranch(branch);
		gitManifestRequest.setUser(user);
		gitManifestRequest.setPassword(password);

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(this.uri, this.destinationId);

		IContentHandler<?> requestContentHandlerV1 = new ApackGitManifestRequestContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);
		IContentHandler<?> responseContentHandlerV1 = new ApackGitManifestResponseContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		IApackManifest apackManifest = restResource.post(monitor, IApackManifest.class, gitManifestRequest);
		if (apackManifest == null) {
			apackManifest = new ApackManifest();
		}
		return apackManifest;
	}

}
