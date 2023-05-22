package org.abapgit.adt.backend;

import java.net.URI;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IExternalRepositoryInfoService {

	IExternalRepositoryInfo getExternalRepositoryInfo(String url, String user, String password, IProgressMonitor monitor);

	String getFolderLogicFromBranchInfo(URI uri, String url, String user, String password, String branch, String packageName);
}
