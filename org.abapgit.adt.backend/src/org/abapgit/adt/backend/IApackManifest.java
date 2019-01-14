package org.abapgit.adt.backend;

import java.util.List;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public interface IApackManifest {

	public static final String MASTER_BRANCH = "refs/heads/master"; //$NON-NLS-1$

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

		void setTargetPackage(IAdtObjectReference targetPackage);

		IAdtObjectReference getTargetPackage();

		boolean requiresClone();

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
