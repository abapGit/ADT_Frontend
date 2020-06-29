package org.abapgit.adt.backend;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IExternalRepositoryInfoService {

	IExternalRepositoryInfo getExternalRepositoryInfo(String url, String user, String password, IProgressMonitor monitor);

}
