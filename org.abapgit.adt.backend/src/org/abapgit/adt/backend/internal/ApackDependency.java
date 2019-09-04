package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackVersionDependency;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public class ApackDependency implements IApackDependency {

	private String groupId;
	private String artifactId;
	private IApackVersionDependency version;
	private String gitUrl;
	private IAdtObjectReference targetPackage;
	private boolean requiresSynchronization;

	public ApackDependency() {
		this.requiresSynchronization = true;
	}

	@Override
	public String getGroupId() {
		return this.groupId;
	}

	@Override
	public String getArtifactId() {
		return this.artifactId;
	}

	@Override
	public String getGitUrl() {
		return this.gitUrl;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public void setGitUrl(String homeUrl) {
		this.gitUrl = homeUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dependency [groupId="); //$NON-NLS-1$
		builder.append(this.groupId);
		builder.append(", artifactId="); //$NON-NLS-1$
		builder.append(this.artifactId);
		builder.append(", version="); //$NON-NLS-1$
		builder.append(this.version);
		builder.append(", gitUrl="); //$NON-NLS-1$
		builder.append(this.gitUrl);
		builder.append(", targetPackage="); //$NON-NLS-1$
		builder.append(this.targetPackage);
		builder.append(", requiresSynchronization="); //$NON-NLS-1$
		builder.append(this.requiresSynchronization);
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
		// No target package as it doesn't implement a correct equals and is also not really relevant for comparison ;)
		result = prime * result + (this.requiresSynchronization ? 1337 : 7331);
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
		ApackDependency other = (ApackDependency) obj;
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
		// No target package as it doesn't implement a correct equals and is also not really relevant for comparison ;)
		return this.requiresSynchronization == other.requiresSynchronization;
	}

	@Override
	public boolean isEmpty() {
		return (this.groupId == null || this.groupId.isEmpty()) && (this.artifactId == null || this.artifactId.isEmpty())
				&& (this.gitUrl == null || this.gitUrl.isEmpty() && this.targetPackage == null && !this.requiresSynchronization);
	}

	@Override
	public void setTargetPackage(IAdtObjectReference targetPackage) {
		this.targetPackage = targetPackage;
	}

	@Override
	public IAdtObjectReference getTargetPackage() {
		return this.targetPackage;
	}

	@Override
	public boolean requiresSynchronization() {
		return this.requiresSynchronization;
	}

	@Override
	public void setRequiresSynchronization(boolean requiresSynchronization) {
		this.requiresSynchronization = requiresSynchronization;
	}

	@Override
	public IApackVersionDependency getVersion() {
		return this.version;
	}

	public void setVersion(IApackVersionDependency version) {
		this.version = version;
	}

}
