package org.abapgit.adt.backend;

import java.net.URI;

import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;

public interface IRepositoryService {

	String RELATION_PULL = "http://www.sap.com/adt/abapgit/relations/pull"; //$NON-NLS-1$
	String RELATION_STATUS = "http://www.sap.com/adt/abapgit/relations/status"; //$NON-NLS-1$
	String RELATION_LOG = "http://www.sap.com/adt/abapgit/relations/log"; //$NON-NLS-1$
	String RELATION_STAGE = "http://www.sap.com/adt/abapgit/relations/stage"; //$NON-NLS-1$
	String RELATION_PUSH = "http://www.sap.com/adt/abapgit/relations/push"; //$NON-NLS-1$
	String RELATION_CHECK = "http://www.sap.com/adt/abapgit/relations/check"; //$NON-NLS-1$

	IRepositories getRepositories(IProgressMonitor monitor);

	IAbapObjects cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user, String password,
			IProgressMonitor monitor);

	void cloneRepositories(IRepositories repositories, IProgressMonitor monitor);

	void unlinkRepository(String key, IProgressMonitor monitor);

	IAbapObjects pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor);

	IAbapObjects getRepoObjLog(IProgressMonitor monitor, IRepository currRepository);

	/**
	 * Returns the staging data for the given repository
	 *
	 * @param repository
	 *            Repository for which the staging data has to be loaded
	 * @param credentials
	 * 			  Repository credentials
	 * @param monitor
	 *            Progress monitor
	 * @return Staging model for the given repository
	 */
	IAbapGitStaging stage(IRepository repository, IExternalRepositoryInfoRequest credentials, IProgressMonitor monitor)
			throws CommunicationException, ResourceException, OperationCanceledException, OutDatedClientException;

	/**
	 * Commits the staged changes to the repository
	 *
	 * @param monitor
	 *            Progress monitor
	 * @param staging
	 *            Model which contains the staged objects which are to be
	 *            committed
	 * @param credentials
	 * 			  Repository credentials
	 * @param repository
	 *            Repository to which the commit has to happen
	 */
	void push(IProgressMonitor monitor, IAbapGitStaging staging, IExternalRepositoryInfoRequest credentials, IRepository repository)
			throws CommunicationException, ResourceException, OperationCanceledException, OutDatedClientException;

	/**
	 * Performs basic checks on repository eg: connection checks, credentials
	 * checks ...
	 *
	 * @param monitor
	 *            Progress monitor
	 * @param credentials
	 *			  Repository credentials
	 * @param repository
	 *            Repository to which the commit has to happen
	 */
	void repositoryChecks(IProgressMonitor monitor, IExternalRepositoryInfoRequest credentials, IRepository repository)
			throws CommunicationException, ResourceException, OperationCanceledException, OutDatedClientException;

	/**
	 * Returns Repository for the URL given
	 *
	 * @param repositories
	 *            All linked repositories
	 * @param url
	 *            Repository url
	 * @return Repository matching the given url
	 */
	IRepository getRepositoryByURL(IRepositories repositories, String url);

	/**
	 * Returns the href from the atom link based on the relation
	 *
	 * @param repository
	 *            Repository object
	 * @param relation
	 *            Relation of the atom link which has to be retrieved
	 * @return URI from the atom link found or null if no atom link is present
	 *         with the given relation
	 */
	URI getURIFromAtomLink(IRepository repository, String relation);

}
