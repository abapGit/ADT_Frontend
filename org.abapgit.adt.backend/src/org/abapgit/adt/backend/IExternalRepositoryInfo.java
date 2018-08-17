package org.abapgit.adt.backend;

import java.util.Set;

public interface IExternalRepositoryInfo {

	boolean requiresAuthentication();

	boolean getUri();

	Set<String> getBranches();

}
