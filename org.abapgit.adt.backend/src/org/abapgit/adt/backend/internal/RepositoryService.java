package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IObjects;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.UriBuilder;
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

		IContentHandler<IRepositories> responseContentHandlerV1 = new RepositoriesContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IRepositories.class);
	}

	@Override
	public IObjects cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user,
			String password, IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(this.uri, this.destinationId);

		IContentHandler<IRepository> requestContentHandlerV1 = new RepositoryContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);

		IRepository repository = new Repository();
		repository.setUrl(url);
		repository.setPackage(targetPackage);
		repository.setBranch(branch);

		if (user != null && !user.isEmpty()) {
			repository.setRemoteUser(user);
		}

		if (password != null && !password.isEmpty()) {
			repository.setPassword(password);
		}

		repository.setTransportRequest(transportRequest);
		repository.setPackage(targetPackage);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(new IContentHandler[0]);

		IContentHandler<IObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter responseCompatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(new IContentHandler[0]);

		restResource.addResponseFilter(responseCompatibilityFilter);
		restResource.addRequestFilter(compatibilityFilter);

		return restResource.post(monitor, IObjects.class, repository);
	}

	@Override
	public IObjects pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor) {

		URI uriToRepo = existingRepository.getLink(IRepositoryService.RELATION_PULL);
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepo,
				this.destinationId);

		IContentHandler<IRepository> requestContentHandlerV1 = new RepositoryContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);

		IRepository repository = new Repository();
		repository.setBranch(branch);
		repository.setTransportRequest(transportRequest);

		if (user != null && !user.isEmpty()) {
			repository.setRemoteUser(user);
		}

		if (password != null && !password.isEmpty()) {
			repository.setPassword(password);
		}

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter(new IContentHandler[0]);
		restResource.addRequestFilter(compatibilityFilter);

		IContentHandler<IObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter responseCompatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(new IContentHandler[0]);
		restResource.addResponseFilter(responseCompatibilityFilter);

		return restResource.post(monitor, IObjects.class, repository);
	}

	@Override
	public void unlinkRepository(String key, IProgressMonitor monitor) {
		URI uriToRepo = new UriBuilder(this.uri).addPathSegments(key).getUri();
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(uriToRepo, this.destinationId);

		restResource.delete(monitor);
	}

}
