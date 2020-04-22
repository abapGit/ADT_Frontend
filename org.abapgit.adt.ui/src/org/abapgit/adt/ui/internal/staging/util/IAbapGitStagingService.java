package org.abapgit.adt.ui.internal.staging.util;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.eclipse.core.resources.IProject;

public interface IAbapGitStagingService extends IAbapGitService {

	/**
	 * Utility method for opening the editor for AbapGit Object and AbapGit
	 * Files
	 *
	 * @param object
	 *            Instance of <b>AbapGitObject</b> or <b>AbapGitFile</b> to be
	 *            opened.
	 */
	void openEditor(Object object, IProject project, IExternalRepositoryInfoRequest credentials);

	/**
	 * Checks whether abapgit file comparison is supported by the backend. If
	 * supported, an atom link with relation
	 * {@link IFileService#RELATION_FILE_FETCH_LOCAL} will be present in an
	 * abapgit file object.
	 */
	boolean isFileCompareSupported(Object object);

	/**
	 * Checks whether opening abap/properties files are supported by the
	 * backend. If supported, an atom link with relation
	 * {@link IFileService#RELATION_FILE_FETCH_LOCAL} will be present in an
	 * abapgit file object.
	 */
	boolean isFetchFileContentSupported(Object object);

}
