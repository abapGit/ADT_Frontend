package org.abapgit.adt.backend;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IRepositoryService {
	public IRepositories getRepositories(IProgressMonitor monitor); 
}
