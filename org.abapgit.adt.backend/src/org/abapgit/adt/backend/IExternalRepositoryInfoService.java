package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IExternalRepositoryInfoService {

	IExternalRepositoryInfo getExternalRepositoryInfo(String url, String user, String password, IProgressMonitor monitor);

}
