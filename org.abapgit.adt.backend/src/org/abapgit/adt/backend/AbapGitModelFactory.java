package org.abapgit.adt.backend;

import org.abapgit.adt.backend.internal.Repositories;
import org.abapgit.adt.backend.internal.Repository;

public class AbapGitModelFactory {

	private AbapGitModelFactory() {
		// Service class with static methods only...
	}

	public static IRepository createRepository() {
		return new Repository();
	}

	public static IRepositories createRepositories() {
		return new Repositories();
	}

}
