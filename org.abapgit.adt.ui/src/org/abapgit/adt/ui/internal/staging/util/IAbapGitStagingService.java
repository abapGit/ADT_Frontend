package org.abapgit.adt.ui.internal.staging.util;

import org.abapgit.adt.backend.IFileService;
import org.eclipse.core.resources.IProject;

public interface IAbapGitStagingService {
	/**
	 * Checks if the abap project is logged on
	 *
	 * @param project
	 *            Project to be checked
	 * @return Returns true if the project is logged on
	 */
	boolean isLoggedOn(IProject project);

	/**
	 * @return Destination Id for the given abap project
	 */
	String getDestination(IProject project);

	/**
	 * Returns the href from the atom link based on the relation
	 *
	 * @param object
	 *            Object can be IAbapGitObject or IAbapGitFile
	 * @param relation
	 *            Relation of the atom link which has to be retrieved
	 * @return Href from the atom link found or null if no atom link is present
	 *         with the given relation
	 */
	String getHrfFromAtomLink(Object object, String relation);

	/**
	 * Utility method for opening the editor for AbapGit Object and AbapGit
	 * Files
	 *
	 * @param object
	 *            Instance of <b>AbapGitObject</b> or <b>AbapGitFile</b> to be
	 *            opened.
	 */
	void openEditor(Object object, IProject project);

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
