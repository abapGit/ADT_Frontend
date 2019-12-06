package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;

public class AbapGitAuthenticationInfo {
	private static IExternalRepositoryInfoRequest repositoryCredentials;

	protected static IExternalRepositoryInfoRequest getRepoCredentials() {
		return repositoryCredentials;
	}

	public static void setRepositoryCredentials(IExternalRepositoryInfoRequest credentials) {
		repositoryCredentials = credentials;
	}
}
