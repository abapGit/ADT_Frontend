package org.abapgit.adt.backend.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.sap.adt.communication.batch.AdtBatchResourceFactory;
import com.sap.adt.communication.batch.IAdtBatchPartDelayedResponse;
import com.sap.adt.communication.batch.IAdtBatchResource;
import com.sap.adt.communication.message.HeadersFactory;
import com.sap.adt.communication.message.IHeaders;
import com.sap.adt.communication.message.IMessageBody;
import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.communication.resources.IRestResourceRequestFilter;
import com.sap.adt.communication.resources.IRestResourceResponseFilter;
import com.sap.adt.communication.session.AdtSystemSessionFactory;
import com.sap.adt.communication.session.ISystemSession;
import com.sap.adt.compatibility.batch.AdtBatchUriDiscoveryFactory;
import com.sap.adt.compatibility.filter.AdtCompatibleRestResourceFilterFactory;
import com.sap.adt.compatibility.filter.IAdtCompatibleRestResourceFilter;
import com.sap.adt.tools.core.model.atom.IAtomLink;

@SuppressWarnings("restriction")
public class FileService implements IFileService {

	@Override
	public String readLocalFileContents(IAbapGitFile file, String destinationId)
			throws IOException {
		return readFileContents(file, LOCAL_VERSION, AbapGitAuthenticationInfo.getRepoCredentials(), destinationId);
	}

	@Override
	public String readRemoteFileContents(IAbapGitFile file, String destinationId)
			throws IOException {
		return readFileContents(file, REMOTE_VERSION, AbapGitAuthenticationInfo.getRepoCredentials(), destinationId);
	}

	private String readFileContents(IAbapGitFile file, String version, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException {
		URI uri;
		if (version.equals(LOCAL_VERSION)) {
			uri = getHrefFromAtomLink(file, IFileService.RELATION_FILE_FETCH_LOCAL);
		} else {
			uri = getHrefFromAtomLink(file, IFileService.RELATION_FILE_FETCH_REMOTE);
		}
		if (uri != null) {
			IRestResource restResource = AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(uri,
					destinationId);
			IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
					.createFilter(IFileService.MIME_TEXT_PLAIN);
			restResource.addRequestFilter(compatibilityFilter);
			restResource.addResponseFilter(compatibilityFilter);
			IHeaders headers = null;
			if (credentials != null) {
				headers = getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword());
			}
			IMessageBody body = restResource.get(new NullProgressMonitor(), headers, IMessageBody.class);
			if (body != null) {
				return convert(body.getContent(), StandardCharsets.UTF_8);
			}
		}
		return null;
	}

	@Override
	public List<String> readFileContentsBatch(IAbapGitFile file, String destinationId)
			throws IOException {
		URI defaultBatchUri = AdtBatchUriDiscoveryFactory.createBatchUriDiscovery(destinationId)
				.getDefaultBatchURI(new NullProgressMonitor());
		ISystemSession session = AdtSystemSessionFactory.createSystemSessionFactory().createStatelessSession(destinationId);
		IAdtBatchResource resource = AdtBatchResourceFactory.createBatchResource(session, defaultBatchUri);
		//add local file fetch request to batch
		IAdtBatchPartDelayedResponse<IResponse> localFileResponse = readLocalFileContent(resource, file,
				AbapGitAuthenticationInfo.getRepoCredentials());
		//add remote file fetch request to batch
		IAdtBatchPartDelayedResponse<IResponse> remoteFileResponse = readRemoteFileContent(resource, file,
				AbapGitAuthenticationInfo.getRepoCredentials());
		//submit batch request
		resource.submit(new NullProgressMonitor());

		List<String> fileContents = new ArrayList<>();
		//read local file contents from batch response
		IMessageBody body = localFileResponse.getResult().getBody();
		if (body != null) {
			fileContents.add(convert(body.getContent(), StandardCharsets.UTF_8));
		}
		//read remote file contents from batch response
		body = remoteFileResponse.getResult().getBody();
		if (body != null) {
			fileContents.add(convert(body.getContent(), StandardCharsets.UTF_8));
		}
		return fileContents;
	}

	private IAdtBatchPartDelayedResponse<IResponse> readLocalFileContent(IAdtBatchResource resource, IAbapGitFile file,
			IExternalRepositoryInfoRequest credentials) {
		return readFileContentBatch(getHrefFromAtomLink(file, IFileService.RELATION_FILE_FETCH_LOCAL), credentials, resource);
	}

	private IAdtBatchPartDelayedResponse<IResponse> readRemoteFileContent(IAdtBatchResource resource, IAbapGitFile file,
			IExternalRepositoryInfoRequest credentials) {
		return readFileContentBatch(getHrefFromAtomLink(file, IFileService.RELATION_FILE_FETCH_REMOTE), credentials, resource);
	}

	private IAdtBatchPartDelayedResponse<IResponse> readFileContentBatch(URI uri, IExternalRepositoryInfoRequest credentials,
			IAdtBatchResource resource) {
		IAdtCompatibleRestResourceFilter compatibilityFilter = AdtCompatibleRestResourceFilterFactory
				.createFilter(IFileService.MIME_TEXT_PLAIN);
		List<IRestResourceRequestFilter> reqFilters = new ArrayList<>();
		reqFilters.add(compatibilityFilter);

		List<IRestResourceResponseFilter> respFilters = new ArrayList<>();
		respFilters.add(compatibilityFilter);

		IHeaders headers = null;
		if (credentials != null) {
			headers = getHttpHeadersForCredentials(credentials.getUser(), credentials.getPassword());
		}

		IAdtBatchPartDelayedResponse<IResponse> response = resource.addGetRequestPart(uri, IResponse.class,
				headers, null, null, reqFilters,
				respFilters);
		return response;
	}

	/**
	 * Converts input stream from the back-end request response to string
	 */
	private String convert(InputStream inputStream, Charset charset) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	/**
	 * Returns the request headers for sending github account user name and
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

	private URI getHrefFromAtomLink(IAbapGitFile file, String relation) {
		for (IAtomLink link : file.getLinks()) {
			if (link.getRel().equalsIgnoreCase(relation)) {
				return link.getHrefAsUri();
			}
		}
		return null;
	}

}
