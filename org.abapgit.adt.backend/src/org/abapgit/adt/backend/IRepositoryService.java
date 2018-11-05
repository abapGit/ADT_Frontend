package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IRepositoryService {

	String RELATION_PULL = "http://www.sap.com/adt/abapgit/relations/pull"; //$NON-NLS-1$

	IRepositories getRepositories(IProgressMonitor monitor);

	void cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user,
			String password, IProgressMonitor monitor);

	void unlinkRepository(String key, IProgressMonitor monitor);

	void pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor);

}
