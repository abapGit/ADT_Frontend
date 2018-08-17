package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IExternalRepositoryInfoService {

	IExternalRepositoryInfo getExternalRepositoryInfo(IProgressMonitor monitor);

}
