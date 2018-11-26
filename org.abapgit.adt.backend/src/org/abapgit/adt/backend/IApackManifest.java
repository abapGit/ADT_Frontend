package org.abapgit.adt.backend;

import java.util.List;

public interface IApackManifest {

	public interface IApackAuthor {

		String getName();

		String getEMail();

		boolean isEmpty();

	}

	public interface IApackDependency {

		String getOrganizationId();

		String getPackageId();

		String getGitUrl();

		boolean isEmpty();

		void setTargetPackageName(String packageName);

		String getTargetPackageName();

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

		void setDependencies(List<IApackDependency> dependencies);

		void addDependency(IApackDependency dependency);

	}

	IApackManifestDescriptor getDescriptor();

	boolean hasDependencies();

}
