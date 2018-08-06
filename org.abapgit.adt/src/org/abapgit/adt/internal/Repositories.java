package org.abapgit.adt.internal;

import java.util.Collections;
import java.util.List;

import org.abapgit.adt.IRepositories;
import org.abapgit.adt.IRepository;
import org.abapgit.adt.Repository;

public class Repositories implements IRepositories{
	private List<IRepository> repositories;

	@Override
	public List<IRepository> getRepositories() {
		return Collections.unmodifiableList(this.repositories);
	}

	public void add(Repository repository) {
		this.repositories.add(repository);
	}
}
