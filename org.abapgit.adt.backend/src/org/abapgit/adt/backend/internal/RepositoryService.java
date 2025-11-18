package org.abapgit.adt.backend.internal;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest;
import org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapgitpullrequestFactoryImpl;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.communication.background.AdtBackgroundRestResourceFactory;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.HeadersFactory;
import com.sap.adt.communication.message.IHeaders;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.UriBuilder;
import com.sap.adt.compatibility.background.AdtBackgroundRunUriDiscoveryFactory;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;
import com.sap.adt.tools.core.model.atom.IAtomLink;

public class RepositoryService implements IRepositoryService {

	private final String destinationId;
	private final URI uri;
	private String result;

	public RepositoryService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IAbapObjects getRepoObjLog(IProgressMonitor monitor, IRepository currRepository) {

		URI uriToRepoObjLog = getURIFromAtomLink(currRepository, IRepositoryService.RELATION_LOG);

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepoObjLog,
				this.destinationId);

		IContentHandler<IAbapObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, IAbapObjects.class);
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
	public IAbapObjects cloneRepository(String url, String branch, String targetPackage, String folderLogic, String transportRequest,
			String user, String password, IProgressMonitor monitor) {

		IRepository repository = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository.setUrl(url);
		repository.setPackage(targetPackage);
		repository.setFolderLogic(folderLogic);
		repository.setBranchName(branch);
		if (user != null && !user.isEmpty()) {
			repository.setRemoteUser(user);
		}
		if (password != null && !password.isEmpty()) {
			repository.setRemotePassword(password);
		}
		repository.setTransportRequest(transportRequest);
		repository.setPackage(targetPackage);

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(this.uri,
				this.destinationId);

		IContentHandler<IRepository> requestContentHandlerV1 = new RepositoryContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);

		IContentHandler<IAbapObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);

		return restResource.post(monitor, IAbapObjects.class, repository);
	}

	/*
	 * TODO: To be deleted after 2105 back end release (with selective pull feature) reaches all customers
	 */
	@Override
	public IAbapObjects pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor) {

		URI uriToRepo = getURIFromAtomLink(existingRepository, IRepositoryService.RELATION_PULL);
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepo,
				this.destinationId);

		IContentHandler<IRepository> requestContentHandlerV1 = new RepositoryContentHandlerV1();
		restResource.addContentHandler(requestContentHandlerV1);

		IRepository repository = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository.setBranchName(branch);
		repository.setTransportRequest(transportRequest);

		if (user != null && !user.isEmpty()) {
			repository.setRemoteUser(user);
		}

		if (password != null && !password.isEmpty()) {
			repository.setRemotePassword(password);
		}

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter(new IContentHandler[0]);
		restResource.addRequestFilter(compatibilityFilter);

		IContentHandler<IAbapObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter responseCompatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(new IContentHandler[0]);
		restResource.addResponseFilter(responseCompatibilityFilter);

		return restResource.post(monitor, IAbapObjects.class, repository);
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
		URI stagingUri = getURIFromAtomLink(repository, IRepositoryService.RELATION_STAGE);
		return getRestResource(stagingUri);
	}

	@Override
	public void push(IProgressMonitor monitor, IAbapGitStaging staging, IExternalRepositoryInfoRequest credentials,
			IRepository repository) {
		URI pushUri = getURIFromAtomLink(repository, IRepositoryService.RELATION_PUSH);
		IRestResource restResource = getRestResource(pushUri);
		restResource.post(monitor, getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword()), null, staging);
	}

	@Override
	public void repositoryChecks(IProgressMonitor monitor, IExternalRepositoryInfoRequest credentials, IRepository repository) {
		IHeaders headers = null;
		URI checkUri = getURIFromAtomLink(repository, IRepositoryService.RELATION_CHECK);
		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(checkUri,
				this.destinationId);
		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter("text/plain"); //$NON-NLS-1$
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);
		if (credentials != null) {
			headers = getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword());
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
		Base64.Encoder encoder = Base64.getEncoder();
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

	public IRepository getRepositoryByURL(IRepositories repositories, String url) {

		for (IRepository repo : repositories.getRepositories()) {
			if (repo.getUrl().equals(url)) {
				return repo;
			}

		}

		return null;

	}

	@Override
	public URI getURIFromAtomLink(IRepository repository, String relation) {

		for (IAtomLink link : repository.getLinks()) {
			if (link.getRel().equalsIgnoreCase(relation)) {
				return URI.create(link.getHref());
			}
		}
		return null;
	}

	@Override
	public IAbapGitPullModifiedObjects getModifiedObjects(IProgressMonitor monitor, IRepository currRepository, String user,
			String password) throws OutDatedClientException {
		URI uriToModifiedObjects = getURIFromAtomLink(currRepository, IRepositoryService.RELATION_MODIFIED_OBJECTS);

		IHeaders headers = null;
		if (user != null && password != null) {
			headers = getHttpHeadersForCredentials(user, password);
		}

		IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(uriToModifiedObjects, this.destinationId);

		IContentHandler<IAbapGitPullModifiedObjects> responseContentHandlerV1 = new AbapGitPullModifiedObjectsContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, headers, IAbapGitPullModifiedObjects.class);

	}

	// CHNG: Change it back to original pull relation
	@Override
	public IAbapObjects pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IAbapGitPullModifiedObjects selectedObjectsToPull, IProgressMonitor monitor) {
		IRestResource restResource = null;
		URI uriToRepo = null;
		// check if background job framework is available and is accessible by the user.
		if (isBackgroundJobSupported(monitor)) {
			// get new pull without background resource URI
			uriToRepo = getURIFromAtomLink(existingRepository, IRepositoryService.RELATION_PULL_WITHOUT_BG);
			// check whether URI is available in the system (this depends on the back end version)
			if (uriToRepo != null) {
				restResource = getBackgroundRestResource(uriToRepo.getPath(), this.destinationId, monitor);
			}
		}
		// if rest resource is still null, rollback to normal pull URI
		if (restResource == null) {
			uriToRepo = getURIFromAtomLink(existingRepository, IRepositoryService.RELATION_PULL);
			restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uriToRepo,
					this.destinationId);
		}
		if (restResource == null) {
			// if rest resource could not be created, throw exception
			throw new IllegalStateException("Unable to create REST resource for pull operation."); //$NON-NLS-1$
		}
		IContentHandler<IAbapGitPullRequest> requestContentHandlerV1 = new AbapGitPullRequestContentHandler();
		restResource.addContentHandler(requestContentHandlerV1);

		IAbapGitPullRequest abapGitPullReq = AbapgitpullrequestFactoryImpl.eINSTANCE.createAbapGitPullRequest();

		abapGitPullReq.setBranchName(branch);
		abapGitPullReq.setTransportRequest(transportRequest);

		if (user != null && !user.isEmpty()) {
			abapGitPullReq.setUser(user);
		}

		if (password != null && !password.isEmpty()) {
			abapGitPullReq.setPassword(password);
		}

		if (selectedObjectsToPull != null) {
			abapGitPullReq.setPackageWarningObjects(selectedObjectsToPull.getPackageWarningObjects());
			abapGitPullReq.setOverwriteObjects(selectedObjectsToPull.getOverwriteObjects());
		}

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory.createFilter(new IContentHandler[0]);
		restResource.addRequestFilter(compatibilityFilter);

		IContentHandler<IAbapObjects> responseContentHandlerV1 = new AbapObjectContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter responseCompatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(new IContentHandler[0]);
		restResource.addResponseFilter(responseCompatibilityFilter);

		return restResource.post(monitor, IAbapObjects.class, abapGitPullReq);
	}

	@Override
	public boolean isBackgroundJobSupported(IProgressMonitor monitor) {
		// check whether background job framework is available and is accessible by the user.
		// if not available or not authorized the uri would be null.
		if (AdtBackgroundRunUriDiscoveryFactory.createBackgroundRunUriDiscovery(this.destinationId)
				.getBackgroundRunUriIfAuthorized(monitor) != null) {
			return true;
		}
		return false;
	}

	private IRestResource getBackgroundRestResource(String uri, String destinationId, IProgressMonitor monitor) {
		URI backgroundUri = AdtBackgroundRunUriDiscoveryFactory.createBackgroundRunUriDiscovery(destinationId)
				.getBackgroundRunUriIfAuthorized(monitor);
		if (backgroundUri != null) {
			return AdtBackgroundRestResourceFactory.createBackgroundRestResourceFactory().createResourceWithStatelessSession(backgroundUri,
					URI.create(uri), destinationId);
		}
		return AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(URI.create(uri), destinationId);
	}

	@Override
	public IAbapGitPullModifiedObjects getModifiedObjectsWithBackgroundJob(IProgressMonitor monitor, IRepository currRepository,
			String user, String password) {
		URI uriToModifiedObjects = getURIFromAtomLink(currRepository, IRepositoryService.RELATION_MODIFIED_OBJECTS);

		IHeaders headers = null;
		if (user != null && password != null) {
			headers = getHttpHeadersForCredentials(user, password);
		}

		IRestResource restResource = getBackgroundRestResource(uriToModifiedObjects.getPath(), this.destinationId, monitor);

		IContentHandler<IAbapGitPullModifiedObjects> responseContentHandlerV1 = new AbapGitPullModifiedObjectsContentHandlerV1();
		restResource.addContentHandler(responseContentHandlerV1);

		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(responseContentHandlerV1);
		restResource.addRequestFilter(compatibilityFilter);
		restResource.addResponseFilter(compatibilityFilter);

		return restResource.get(monitor, headers, IAbapGitPullModifiedObjects.class);
	}

}
