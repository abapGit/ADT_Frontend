package org.abapgit.adt.ui.internal.staging;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
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

	/**
	 * Returns the current repository which is loaded. Null if staging view is
	 * not loaded with any repository
	 */
	public IRepository getRepository();

	/**
	 * Returns the project of the repository currently loaded. Null if no
	 * repository has been loaded.
	 */
	public IProject getProject();

	/**
	 * Returns ExternalRepositoryInfo of the current repository
	 *
	 */

	public IExternalRepositoryInfo getExternalRepositoryInfo();
}
