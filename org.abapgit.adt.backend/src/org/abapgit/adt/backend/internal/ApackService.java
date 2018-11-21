package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.eclipse.core.runtime.IProgressMonitor;

public class ApackService implements IRepositoryService {

	private final String destinationId;
	private final URI uri;

	public ApackService(String destinationId, URI uri) {
		this.destinationId = destinationId;
		this.uri = uri;
	}

	@Override
	public IRepositories getRepositories(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cloneRepository(String url, String branch, String targetPackage, String transportRequest, String user, String password,
			IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlinkRepository(String key, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pullRepository(IRepository existingRepository, String branch, String transportRequest, String user, String password,
			IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
