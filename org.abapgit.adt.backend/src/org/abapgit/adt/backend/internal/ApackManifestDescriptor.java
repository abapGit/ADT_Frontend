package org.abapgit.adt.backend.internal;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.EApackRepositoryType;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;

public class ApackManifestDescriptor implements IApackManifestDescriptor {

	private String groupId;
	private String artifactId;
	private String version;
	private EApackRepositoryType repositoryType;
	private String gitUrl;
	private List<IApackDependency> dependencies = new ArrayList<IApackDependency>();

	@Override
	public String getGroupId() {
		return this.groupId;
	}

	@Override
	public String getPackageId() {
		return this.artifactId;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public String getGitUrl() {
		return this.gitUrl;
	}

	@Override
	public List<IApackDependency> getDependencies() {
		return this.dependencies;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setPackageId(String packageId) {
		this.artifactId = packageId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setGitUrl(String homeUrl) {
		this.gitUrl = homeUrl;
	}

	@Override
	public void setDependencies(List<IApackDependency> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public void addDependency(IApackDependency dependency) {
		this.dependencies.add(dependency);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APACK Manifest [groupId="); //$NON-NLS-1$
		builder.append(this.groupId);
		builder.append(", packageId="); //$NON-NLS-1$
		builder.append(this.artifactId);
		builder.append(", version="); //$NON-NLS-1$
		builder.append(this.version);
		builder.append(", gitUrl="); //$NON-NLS-1$
		builder.append(this.gitUrl);
		builder.append(", repositoryType="); //$NON-NLS-1$
		builder.append(this.repositoryType);
		builder.append(", dependencies="); //$NON-NLS-1$
		builder.append(this.dependencies);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.groupId == null) ? 0 : this.groupId.hashCode());
		result = prime * result + ((this.artifactId == null) ? 0 : this.artifactId.hashCode());
		result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
		result = prime * result + ((this.gitUrl == null) ? 0 : this.gitUrl.hashCode());
		result = prime * result + ((this.repositoryType == null) ? 0 : this.repositoryType.hashCode());
		result = prime * result + ((this.dependencies == null) ? 0 : this.dependencies.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { // NOPMD
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ApackManifestDescriptor other = (ApackManifestDescriptor) obj;
		if (this.groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!this.groupId.equals(other.groupId)) {
			return false;
		}
		if (this.artifactId == null) {
			if (other.artifactId != null) {
				return false;
			}
		} else if (!this.artifactId.equals(other.artifactId)) {
			return false;
		}
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
			return false;
		}
		if (this.gitUrl == null) {
			if (other.gitUrl != null) {
				return false;
			}
		} else if (!this.gitUrl.equals(other.gitUrl)) {
			return false;
		}
		if (this.dependencies == null) {
			if (other.dependencies != null) {
				return false;
			}
		} else if (!this.dependencies.equals(other.dependencies)) {
			return false;
		}
		return true;
	}

	@Override
	public EApackRepositoryType getRepositoryType() {
		return this.repositoryType;
	}

	public void setRepositoryType(EApackRepositoryType repositoryType) {
		this.repositoryType = repositoryType;
	}

}
