package org.abapgit.adt.backend.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
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
	public String readLocalFileContents(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException {
		return readFileContents(file, LOCAL_VERSION, credentials, destinationId);
	}

	@Override
	public String readRemoteFileContents(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException {
		return readFileContents(file, REMOTE_VERSION, credentials, destinationId);
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
	public List<String> readFileContentsBatch(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException {
		URI defaultBatchUri = AdtBatchUriDiscoveryFactory.createBatchUriDiscovery(destinationId)
				.getDefaultBatchURI(new NullProgressMonitor());
		ISystemSession session = AdtSystemSessionFactory.createSystemSessionFactory().createStatelessSession(destinationId);
		IAdtBatchResource resource = AdtBatchResourceFactory.createBatchResource(session, defaultBatchUri);
		//add local file fetch request to batch
		IAdtBatchPartDelayedResponse<IResponse> localFileResponse = readLocalFileContent(resource, file,
				credentials);
		//add remote file fetch request to batch
		IAdtBatchPartDelayedResponse<IResponse> remoteFileResponse = readRemoteFileContent(resource, file,
				credentials);
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

	/**
	 * Get the URI from atom link given the relation
	 */
	private URI getHrefFromAtomLink(IAbapGitFile file, String relation) {
		for (IAtomLink link : file.getLinks()) {
			if (link.getRel().equalsIgnoreCase(relation)) {
				return generateURIFromString(file, link.getHref());
			}
		}
		return null;
	}

	/**
	 * Create URI from a uri string
	 */
	private URI generateURIFromString(IAbapGitFile file, String uriString) {
		//Till 2011 SAP ABAP in Cloud release, the backend does not send proper encoded URL for file handling.
		//If the file name contains some special character then the URI generation will lead to an error.
		//To prevent this do a client side encoding for the value for filename query parameter.
		//This handling can be removed once the upgrade to 2011 happens.
		String encodedFileName = encodeFileName(file.getName());
		if (encodedFileName != null && !file.getName().equals(encodedFileName)) {
			String encodedUriString = uriString.replace(file.getName(), encodedFileName);
			return URI.create(encodedUriString);
		}
		return URI.create(uriString);
	}

	/**
	 * Encode the file name
	 */
	private String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
