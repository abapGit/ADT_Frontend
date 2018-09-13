package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IRepositoryService {
	IRepositories getRepositories(IProgressMonitor monitor);

	void cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user,
			String password, IProgressMonitor monitor);

	void unlinkRepository(String key, IProgressMonitor monitor);
}
