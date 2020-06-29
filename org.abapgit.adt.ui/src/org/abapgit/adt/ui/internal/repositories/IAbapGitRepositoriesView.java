package org.abapgit.adt.ui.internal.repositories;

import org.abapgit.adt.backend.IRepository;
import org.eclipse.core.resources.IProject;

/**
 * Interface for <b>AbapGit Repositories</b> view
 */
public interface IAbapGitRepositoriesView {
	/**
	 * Returns the repository currently selected.</br>
	 * Null if no selection is available.
	 */
	public IRepository getRepositorySelection();

	/**
	 * Returns the project currently loaded.
	 */
	public IProject getProject();
}
