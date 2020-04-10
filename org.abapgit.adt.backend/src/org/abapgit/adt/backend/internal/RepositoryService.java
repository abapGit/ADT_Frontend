package org.abapgit.adt.backend.internal;

import java.net.URI;
import java.nio.charset.StandardCharsets;
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
	public IAbapGitStaging stage(IRepository repository, IExternalRepositoryInfoRequest credentials, IProgressMonitor monitor) {
		IHeaders headers = null;
		IRestResource restResource = getStageRestResource(repository);
		if (credentials != null) {
			headers = getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword());
		}
		return restResource.get(monitor, headers, IAbapGitStaging.class);
	}

	private IRestResource getStageRestResource(IRepository repository) {
		URI stagingUri = repository.getStageLink(IRepositoryService.RELATION_STAGE);
		return getRestResource(stagingUri);
	}

	@Override
	public void push(IProgressMonitor monitor, IAbapGitStaging staging, IExternalRepositoryInfoRequest credentials,
			IRepository repository) {
		URI pushUri = repository.getPushLink(IRepositoryService.RELATION_PUSH);
		IRestResource restResource = getRestResource(pushUri);
		restResource.post(monitor, getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword()), null, staging);
	}

	@Override
	public void repositoryChecks(IProgressMonitor monitor, IExternalRepositoryInfoRequest credentials, IRepository repository) {
		IHeaders headers = null;
		URI checkUri = repository.getChecksLink(IRepositoryService.RELATION_CHECK);
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(checkUri,
				this.destinationId);
		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter("text/plain"); //$NON-NLS-1$
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);
		if (credentials != null) {
			headers = getHttpHeadersForCredentials(credentials.getUser(),
					credentials.getPassword());
		}
		restResource.post(monitor, headers, null);
	}

	/**
	 * Returns the request headers for sending git account user name and
	 * password to back-end
	 */
	private IHeaders getHttpHeadersForCredentials(String username, String password) {
		IHeaders headers = HeadersFactory.newHeaders();
		IHeaders.IField userField = HeadersFactory.newField("Username", username); //$NON-NLS-1$
		headers.addField(userField);
		Base64.Encoder encoder = Base64.getMimeEncoder();
		IHeaders.IField passwordField = HeadersFactory.newField("Password", //$NON-NLS-1$
				encoder.encodeToString(password.getBytes(StandardCharsets.UTF_8)));
		headers.addField(passwordField);
		return headers;
	}

	/**
	 * Returns the rest resource for REST calls from AbapGit Staging view
	 */
	private IRestResource getRestResource(URI uri) {
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
