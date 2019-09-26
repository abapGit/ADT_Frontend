package org.abapgit.adt.ui.internal.views;

import org.abapgit.adt.backend.IRepository;
import org.eclipse.core.resources.IProject;

public interface IAbapGitStagingView {
	/**
	 * Opens AbapGit Staging view for the given repository
	 *
	 * @param repository
	 *            repository whose details are to be loaded in the staging view
	 * @param project
	 *            abap project which contains the package linked to the given
	 *            repository
	 */
	public void openStagingView(IRepository repository, IProject project);
}
