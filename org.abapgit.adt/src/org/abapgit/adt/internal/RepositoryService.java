package org.abapgit.adt.internal;

import java.net.URI;

import org.abapgit.adt.IRepositories;
import org.abapgit.adt.IRepositoryService;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;

public class RepositoryService implements IRepositoryService {

	private final String destinationId;
	private final URI uri;

	public RepositoryService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IRepositories getRepositories(IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(this.uri, this.destinationId);

		// Client supports 2 backends:
		// - V1 might be XML
		// - V2 might be JSON
		// but both are able to create instances of IRepositories
		IContentHandler<IRepositories> responseContentHandlerV1 = new RepositoriesContentHandlerV1();
		// IContentHandler<IRepositories> responseContentHandlerV2 = new
		// RepositoriesContentHandler(); // of course this would be a different class
		restResource.addContentHandler(responseContentHandlerV1);

		// The following automates:
		// - Adding of "Accept: " header, based on the content type which the content
		// handler supports
		// - Handling of any ResourceException which means that the client is outdated
		// by throwing an OutDatedClientException
		// IAdtCompatibleRestResourceFilter compatibilityFilter =
		// AdtCompatibleRestResourceFilterFactory.createFilter(responseContentHandlerV2,
		// responseContentHandlerV1);
		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IRepositories.class);
	}

}
