package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IApackManifest;

public class ApackManifest implements IApackManifest {

	private IApackManifestDescriptor descriptor;

	@Override
	public IApackManifestDescriptor getDescriptor() {
		return this.descriptor;
	}

	public void setDescriptor(IApackManifestDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("APACK Manifest [descriptor="); //$NON-NLS-1$
		builder.append(this.descriptor);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.descriptor == null) ? 0 : this.descriptor.hashCode());
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
		ApackManifest other = (ApackManifest) obj;
		if (this.descriptor == null) {
			if (other.descriptor != null) {
				return false;
			}
		} else if (!this.descriptor.equals(other.descriptor)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasDependencies() {
		if (this.descriptor != null && this.descriptor.getDependencies() != null) {
			return this.descriptor.getDependencies().size() > 0;
		}
		return false;
	}

}
