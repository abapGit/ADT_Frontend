package org.abapgit.adt.backend;

import java.net.URI;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IExternalRepositoryInfoService {

	String RELATION_BRANCH_INFO = "http://www.sap.com/adt/abapgit/relations/branchinfo"; //$NON-NLS-1$

	IExternalRepositoryInfo getExternalRepositoryInfo(String url, String user, String password, IProgressMonitor monitor);

	IBranch getBranchInfo(IExternalRepositoryInfo externalRepositoryInfo, String url, String user, String password, String branch,
			String packageName);

	URI getURIForBranchInfo(IExternalRepositoryInfo externalRepoInfo, String branch);

}
