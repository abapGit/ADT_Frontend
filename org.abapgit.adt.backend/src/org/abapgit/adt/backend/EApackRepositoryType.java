package org.abapgit.adt.backend;

public enum EApackRepositoryType {
	abapGit;

	public static EApackRepositoryType fromString(String rawString) {
		// There is only one possibility at this time...
		return EApackRepositoryType.abapGit;
	}
}
