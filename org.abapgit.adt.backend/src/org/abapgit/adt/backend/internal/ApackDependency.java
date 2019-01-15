package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IApackManifest.IApackDependency;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public class ApackDependency implements IApackDependency {

	private String organizationId;
	private String packageId;
	private String gitUrl;
	private IAdtObjectReference targetPackage;
	private boolean requiresClone;

	public ApackDependency() {
		this.requiresClone = true;
	}

	@Override
	public String getOrganizationId() {
		return this.organizationId;
	}

	@Override
	public String getPackageId() {
		return this.packageId;
	}

	@Override
	public String getGitUrl() {
		return this.gitUrl;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public void setGitUrl(String homeUrl) {
		this.gitUrl = homeUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dependency [organizationId="); //$NON-NLS-1$
		builder.append(this.organizationId);
		builder.append(", packageId="); //$NON-NLS-1$
		builder.append(this.packageId);
		builder.append(", gitUrl="); //$NON-NLS-1$
		builder.append(this.gitUrl);
		builder.append(", targetPackage="); //$NON-NLS-1$
		builder.append(this.targetPackage);
		builder.append(", requiresClone="); //$NON-NLS-1$
		builder.append(this.requiresClone);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.organizationId == null) ? 0 : this.organizationId.hashCode());
		result = prime * result + ((this.packageId == null) ? 0 : this.packageId.hashCode());
		result = prime * result + ((this.gitUrl == null) ? 0 : this.gitUrl.hashCode());
		result = prime * result + ((this.targetPackage == null) ? 0 : this.targetPackage.hashCode());
		result = prime * result + (this.requiresClone ? 1337 : 7331);
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
		if (this.organizationId == null) {
			if (other.organizationId != null) {
				return false;
			}
		} else if (!this.organizationId.equals(other.organizationId)) {
			return false;
		}
		if (this.packageId == null) {
			if (other.packageId != null) {
				return false;
			}
		} else if (!this.packageId.equals(other.packageId)) {
			return false;
		}
		if (this.gitUrl == null) {
			if (other.gitUrl != null) {
				return false;
			}
		} else if (!this.gitUrl.equals(other.gitUrl)) {
			return false;
		}
		if (this.targetPackage == null) {
			if (other.targetPackage != null) {
				return false;
			}
		} else if (!this.targetPackage.equals(other.targetPackage)) {
			return false;
		}
		return this.requiresClone == other.requiresClone;
	}

	@Override
	public boolean isEmpty() {
		return (this.organizationId == null || this.organizationId.isEmpty()) && (this.packageId == null || this.packageId.isEmpty())
				&& (this.gitUrl == null || this.gitUrl.isEmpty() && this.targetPackage == null && !this.requiresClone);
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
	public boolean requiresClone() {
		return this.requiresClone;
	}

	public void setRequiresClone(boolean requiresClone) {
		this.requiresClone = requiresClone;
	}

}
