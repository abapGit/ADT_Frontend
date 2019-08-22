package org.abapgit.adt.backend.internal;

import java.net.URI;
import java.util.Base64;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.IObjects;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.HeadersFactory;
import com.sap.adt.communication.message.IHeaders;
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
	public IObjects getRepoObjLog(IProgressMonitor monitor, IRepository currRepository) {

		URI uriToRepoObjLog = currRepository.getLogLink(IRepositoryService.RELATION_LOG);

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepoObjLog,
				this.destinationId);

		IContentHandler<IObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IObjects.class);
	}

	@Override
	public IRepositories getRepositories(IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(this.uri,
				this.destinationId);

		IContentHandler<IRepositories> responseContentHandlerV1 = new RepositoriesContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IRepositories.class);
	}

	@Override
	public IObjects cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user, String password,
			IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(this.uri,
				this.destinationId);

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

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter(new IContentHandler[0]);

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

		URI uriToRepo = existingRepository.getPullLink(IRepositoryService.RELATION_PULL);
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
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepo,
				this.destinationId);

		restResource.delete(monitor);
	}

	@Override
	public void cloneRepositories(IRepositories repositories, IProgressMonitor monitor) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(this.uri,
				this.destinationId);

		IContentHandler<IRepositories> requestContentHandlerV2 = new RepositoryContentHandlerV2();
		restResource.addContentHandler(requestContentHandlerV2);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter(new IContentHandler[0]);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		restResource.post(monitor, null, repositories);

	}

	@Override
	public IAbapGitStaging getStagingInfo(IRepository repository, IProgressMonitor monitor) {
		IRestResource restResource = getStagingRestResource(repository);
		return restResource.get(monitor, IAbapGitStaging.class);
	}

	@Override
	public IAbapGitStaging getStagingInfo(IRepository repository, IExternalRepositoryInfoRequest externalRepo,
			IProgressMonitor monitor) {
		IRestResource restResource = getStagingRestResource(repository);
		return restResource.get(monitor, getHttpHeadersForCredentials(externalRepo.getUser(), externalRepo.getPassword()),
				IAbapGitStaging.class);
	}

	private IRestResource getStagingRestResource(IRepository repository) {
		URI stagingUri = repository.getStageLink(IRepositoryService.RELATION_STAGE);
		return getRestResourceForAbapGitStaging(stagingUri);
	}

	@Override
	public void commit(IProgressMonitor monitor, IAbapGitStaging staging, IRepository repository,
			IExternalRepositoryInfoRequest externalRepo) {
		URI commitUri = repository.getCommitLink(IRepositoryService.RELATION_COMMIT);
		IRestResource restResource = getRestResourceForAbapGitStaging(commitUri);
		restResource.post(monitor, getHttpHeadersForCredentials(externalRepo.getUser(), externalRepo.getPassword()), null, staging);
	}

	/**
	 * Returns the request headers for sending github account user name and
	 * password to back-end
	 */
	private IHeaders getHttpHeadersForCredentials(String username, String password) {
		IHeaders headers = HeadersFactory.newHeaders();
		IHeaders.IField userField = HeadersFactory.newField("Github-Username", username); //$NON-NLS-1$
		headers.addField(userField);
		Base64.Encoder encoder = Base64.getMimeEncoder();
		IHeaders.IField passwordField = HeadersFactory.newField("Github-Password", //$NON-NLS-1$
				encoder.encodeToString(password.getBytes()));
		headers.addField(passwordField);
		return headers;
	}

	/**
	 * Returns the rest resource for REST calls from AbapGit Staging view
	 */
	private IRestResource getRestResourceForAbapGitStaging(URI uri) {
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uri,
				this.destinationId);

		IContentHandler<IAbapGitStaging> responseContentHandlerV1 = new AbapGitStagingContentHandler();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);
		return restResource;
	}

}
