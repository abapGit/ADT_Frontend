package org.abapgit.adt.backend;

import java.io.IOException;
import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;

public interface IFileService {

	public final static String RELATION_FILE_FETCH_LOCAL = "http://www.sap.com/adt/abapgit/file/relations/fetch/localversion"; //$NON-NLS-1$
	public final static String RELATION_FILE_FETCH_REMOTE = "http://www.sap.com/adt/abapgit/file/relations/fetch/remoteversion"; //$NON-NLS-1$
	public final static String LOCAL_VERSION = "local"; //$NON-NLS-1$
	public final static String REMOTE_VERSION = "remote"; //$NON-NLS-1$
	public final static String MIME_TEXT_PLAIN = "text/plain"; //$NON-NLS-1$

	/**
	 * Fetches the remote file contents of the given abapgit file from the
	 * backend
	 *
	 * @param file
	 *            File whose remote version to be read
	 * @param credentials
	 *            Repository credentials
	 * @param destinationId
	 *            Destination ID of the abap project
	 *
	 * @return
	 * @throws IOException
	 */
	String readRemoteFileContents(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId) throws IOException;

	/**
	 * Fetches the local file contents of the given abapgit file from the
	 * backend
	 *
	 * @param file
	 *            File whose local version to be read
	 * @param credentials
	 *            Repository credentials
	 * @param destinationId
	 *            Destination ID of the abap project
	 *
	 * @return
	 * @throws IOException
	 */
	String readLocalFileContents(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException;

	/**
	 * Fetches the contents of the local and remote versions of the specified
	 * abapgit file
	 *
	 * @param file
	 *            File whose contents are to be read
	 * @param credentials
	 *            Repository credentials
	 * @param destinationId
	 *            Destination ID of the abap project
	 * @return
	 * @throws IOException
	 */
	List<String> readFileContentsBatch(IAbapGitFile file, IExternalRepositoryInfoRequest credentials, String destinationId)
			throws IOException;

}
