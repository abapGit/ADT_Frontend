package org.abapgit.adt.backend;

import java.util.List;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public interface IApackManifest {

	public static final String MASTER_BRANCH = "refs/heads/master"; //$NON-NLS-1$

	public interface IApackDependency {

		String getGroupId();

		String getArtifactId();

		String getGitUrl();

		boolean isEmpty();

		void setTargetPackage(IAdtObjectReference targetPackage);

		IAdtObjectReference getTargetPackage();

		boolean requiresSynchronization();

		void setRequiresSynchronization(boolean requiresSynchronization);

	}

	interface IApackManifestDescriptor {

		String getGroupId();

		String getPackageId();

		String getVersion();

		EApackRepositoryType getRepositoryType();

		String getGitUrl();

		List<IApackDependency> getDependencies();

		void setDependencies(List<IApackDependency> dependencies);

		void addDependency(IApackDependency dependency);

	}

	IApackManifestDescriptor getDescriptor();

	boolean hasDependencies();

}
