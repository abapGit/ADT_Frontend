package org.abapgit.adt.backend.internal;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.IApackManifest.IApackAuthor;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;

public class ApackManifestDescriptor implements IApackManifestDescriptor {

	private String organizationId;
	private String packageId;
	private String version;
	private String license;
	private String description;
	private List<IApackAuthor> authors = new ArrayList<IApackAuthor>();
	private String gitUrl;
	private List<IApackDependency> dependencies = new ArrayList<IApackDependency>();

	@Override
	public String getOrganizationId() {
		return this.organizationId;
	}

	@Override
	public String getPackageId() {
		return this.packageId;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public String getLicense() {
		return this.license;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public List<IApackAuthor> getAuthors() {
		return this.authors;
	}

	@Override
	public String getGitUrl() {
		return this.gitUrl;
	}

	@Override
	public List<IApackDependency> getDependencies() {
		return this.dependencies;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAuthors(List<IApackAuthor> authors) {
		this.authors = authors;
	}

	public void addAuthor(IApackAuthor author) {
		this.authors.add(author);
	}

	public void setHomeUrl(String homeUrl) {
		this.gitUrl = homeUrl;
	}

	public void setDependencies(List<IApackDependency> dependencies) {
		this.dependencies = dependencies;
	}

	public void addDependency(IApackDependency dependency) {
		this.dependencies.add(dependency);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APACK Manifest [organizationId="); //$NON-NLS-1$
		builder.append(this.organizationId);
		builder.append(", packageId="); //$NON-NLS-1$
		builder.append(this.packageId);
		builder.append(", version="); //$NON-NLS-1$
		builder.append(this.version);
		builder.append(", license="); //$NON-NLS-1$
		builder.append(this.license);
		builder.append(", description="); //$NON-NLS-1$
		builder.append(this.description);
		builder.append(", authors="); //$NON-NLS-1$
		builder.append(this.authors);
		builder.append(", gitUrl="); //$NON-NLS-1$
		builder.append(this.gitUrl);
		builder.append(", dependencies="); //$NON-NLS-1$
		builder.append(this.dependencies);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.organizationId == null) ? 0 : this.organizationId.hashCode());
		result = prime * result + ((this.packageId == null) ? 0 : this.packageId.hashCode());
		result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
		result = prime * result + ((this.license == null) ? 0 : this.license.hashCode());
		result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + ((this.authors == null) ? 0 : this.authors.hashCode());
		result = prime * result + ((this.gitUrl == null) ? 0 : this.gitUrl.hashCode());
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
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
			return false;
		}
		if (this.license == null) {
			if (other.license != null) {
				return false;
			}
		} else if (!this.license.equals(other.license)) {
			return false;
		}
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.authors == null) {
			if (other.authors != null) {
				return false;
			}
		} else if (!this.authors.equals(other.authors)) {
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

}
