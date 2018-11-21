package org.abapgit.adt.backend;

import java.util.List;

public interface IApackManifest {

	public interface IApackAuthor {

		String getName();

		String getEMail();

	}

	public interface IApackDependency {

		String getOrganizationId();

		String getPackageId();

		String getGitUrl();

	}

	interface IApackManifestDescriptor {

		String getOrganizationId();

		String getPackageId();

		String getVersion();

		String getLicense();

		String getDescription();

		List<IApackAuthor> getAuthors();

		String getGitUrl();

		List<IApackDependency> getDependencies();

	}

	IApackManifestDescriptor getDescriptor();

}
