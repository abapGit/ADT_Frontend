package org.abapgit.adt.backend;

import java.util.List;

public interface IRepositories {

	List<IRepository> getRepositories();

	void add(IRepository repository);
}
