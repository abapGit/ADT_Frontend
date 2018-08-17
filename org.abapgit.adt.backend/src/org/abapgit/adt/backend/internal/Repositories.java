package org.abapgit.adt.backend.internal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;

public class Repositories implements IRepositories {

	private final List<IRepository> repositories = new LinkedList<>();

	public void addList(List<IRepository> repositories) {
		this.repositories.addAll(repositories);
	}

	@Override
	public List<IRepository> getRepositories() {
		return Collections.unmodifiableList(this.repositories);
	}

	public void add(IRepository repository) {
		this.repositories.add(repository);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Repositories [repositories=");
		builder.append(repositories);
		builder.append("]");
		return builder.toString();
	}

}
