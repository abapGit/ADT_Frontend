package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IRepositoryService {
	public IRepositories getRepositories(IProgressMonitor monitor);

	public void cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user,
			String password, IProgressMonitor monitor);
}
