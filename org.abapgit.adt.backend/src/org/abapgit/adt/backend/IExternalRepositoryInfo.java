package org.abapgit.adt.backend;

import java.util.List;

public interface IExternalRepositoryInfo {

	public enum AccessMode {
		PUBLIC, PRIVATE;
	}

	public interface IBranch {
		String getSha1();

		String getName();

		String getType();

		boolean isHead();

		String getDisplayName();
	}

	AccessMode getAccessMode();

	List<IBranch> getBranches();

}
