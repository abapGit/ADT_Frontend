package org.abapgit.adt.backend;

import org.abapgit.adt.backend.internal.AbapGitAuthenticationInfo;

public class AbapGitAuthenticationService {
	public static void setRepositoryCredentials(IExternalRepositoryInfoRequest credentials) {
		AbapGitAuthenticationInfo.setRepositoryCredentials(credentials);
	}
}
