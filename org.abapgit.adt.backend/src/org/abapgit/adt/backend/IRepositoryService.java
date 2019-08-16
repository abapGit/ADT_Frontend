package org.abapgit.adt.backend;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IRepositoryService {

	String RELATION_PULL = "http://www.sap.com/adt/abapgit/relations/pull"; //$NON-NLS-1$
	String RELATION_STATUS = "http://www.sap.com/adt/abapgit/relations/status"; //$NON-NLS-1$
	String RELATION_LOG = "http://www.sap.com/adt/abapgit/relations/log"; //$NON-NLS-1$
	String RELATION_STAGE = "http://www.sap.com/adt/abapgit/relations/staging"; //$NON-NLS-1$

	IRepositories getRepositories(IProgressMonitor monitor);

	IObjects cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user, String password,
			IProgressMonitor monitor);

	void cloneRepositories(IRepositories repositories, IProgressMonitor monitor);

	void unlinkRepository(String key, IProgressMonitor monitor);

	IObjects pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor);

	IObjects getRepoObjLog(IProgressMonitor monitor, IRepository currRepository);

	/**
	 * Returns the staging data for the given repository
	 *
	 * @param repository
	 *            Repository for which the staging data has to be loaded
	 * @param monitor
	 *            Progress monitor
	 * @return Staging model for the given repository
	 */
	IAbapGitStaging getRepositoryStaging(IRepository repository, IProgressMonitor monitor);

}
