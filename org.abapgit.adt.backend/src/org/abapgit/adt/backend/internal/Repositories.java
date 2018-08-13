package org.abapgit.adt.backend.internal;

import java.util.Collections;
import java.util.List;

import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.Repository;

public class Repositories implements IRepositories {
	private List<IRepository> repositories;
	
	public void addList(List<IRepository> repositories) {
		this.repositories = repositories;
	}
	
	@Override
	public List<IRepository> getRepositories() {
		return Collections.unmodifiableList(this.repositories);
	}

	public void add(Repository repository) {
		 this.repositories.add(repository);
	}
}
