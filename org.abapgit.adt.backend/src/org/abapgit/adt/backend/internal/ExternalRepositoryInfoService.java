package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoFactoryImpl;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;

public class ExternalRepositoryInfoService implements IExternalRepositoryInfoService {

	private final String destinationId;
	private final URI uri;

	public ExternalRepositoryInfoService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IExternalRepositoryInfo getExternalRepositoryInfo(String url, String user, String password,
			IProgressMonitor monitor) {

		IExternalRepositoryInfoRequest externalInfoRequest = AbapgitexternalrepoFactoryImpl.eINSTANCE.createExternalRepositoryInfoRequest();
		externalInfoRequest.setUrl(url);
		externalInfoRequest.setUser(user);
		externalInfoRequest.setPassword(password);

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(this.uri, this.destinationId);

		IContentHandler<?> requestContentHandlerV1 = new ExternalRepositoryInfoRequestContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);
		IContentHandler<?> responseContentHandlerV1 = new ExternalRepositoryInfoResponseContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.post(monitor, IExternalRepositoryInfo.class, externalInfoRequest);
	}

}
