package org.abapgit.adt.ui.internal.util;

import java.util.List;

import org.eclipse.core.resources.IProject;

public interface IAbapGitService {
	/**
	 * Checks if the abap project is logged on
	 *
	 * @param project
	 *            Project to be checked
	 * @return Returns true if the project is logged on
	 */
	boolean isLoggedOn(IProject project);

	/**
	 * Logon to an abap project
	 *
	 * @param project
	 *            Project to logon
	 * @return Returns true if logon is successful, else false
	 */
	boolean ensureLoggedOn(IProject project);

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
	 * Checks whether the given project supports AbapGit. Currently abapGit is
	 * enabled only for steampunk projects.
	 */
	boolean isAbapGitSupported(IProject project);

	/**
	 * Get the accepted content types from the discovery
	 *
	 * @return The accepted content types for the project
	 *
	 */

	List<String> getAcceptedContentTypes(IProject project);
}
