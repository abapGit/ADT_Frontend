package org.abapgit.adt.ui.internal.util;

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
}
