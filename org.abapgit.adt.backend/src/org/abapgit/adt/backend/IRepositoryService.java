package org.abapgit.adt.backend;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;

public interface IRepositoryService {

	String RELATION_PULL = "http://www.sap.com/adt/abapgit/relations/pull"; //$NON-NLS-1$
	String RELATION_STATUS = "http://www.sap.com/adt/abapgit/relations/status"; //$NON-NLS-1$
	String RELATION_LOG = "http://www.sap.com/adt/abapgit/relations/log"; //$NON-NLS-1$
	String RELATION_STAGE = "http://www.sap.com/adt/abapgit/relations/stage"; //$NON-NLS-1$
	String RELATION_PUSH = "http://www.sap.com/adt/abapgit/relations/push"; //$NON-NLS-1$

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
	 * @param externalRepo
	 *            External repository credentials, if the repository is private
	 * @param monitor
	 *            Progress monitor
	 * @return Staging model for the given repository
	 */
	IAbapGitStaging stage(IRepository repository, IExternalRepositoryInfoRequest externalRepo, IProgressMonitor monitor)
			throws CommunicationException, ResourceException, OperationCanceledException;

	/**
	 * Commits the staged changes to the repository
	 *
	 * @param monitor
	 *            Progress monitor
	 * @param staging
	 *            Model which contains the staged objects which are to be
	 *            committed
	 * @param repository
	 *            Repository to which the commit has to happen
	 * @param externalRepo
	 *            External repository credentials
	 */
	void push(IProgressMonitor monitor, IAbapGitStaging staging, IRepository repository, IExternalRepositoryInfoRequest externalRepo)
			throws CommunicationException, ResourceException, OperationCanceledException;

}
