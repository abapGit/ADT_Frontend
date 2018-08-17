package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositories;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;

public class ExternalRepositoryInfoService implements IExternalRepositoryInfoService {

	private String destinationId;
	private URI uri;

	public ExternalRepositoryInfoService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IExternalRepositoryInfo getExternalRepositoryInfo(IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(this.uri, this.destinationId);

		IContentHandler<IRepositories> responseContentHandlerV1 = new RepositoriesContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IExternalRepositoryInfo.class);
	}

}
