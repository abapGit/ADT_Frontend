package org.abapgit.adt.backend.internal;

import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfo;

public class ExternalRepositoryInfo implements IExternalRepositoryInfo {

	private AccessMode accessMode;
	private final List<IBranch> branches = new LinkedList<>();

	@Override
	public AccessMode getAccessMode() {
		return this.accessMode;
	}

	@Override
	public List<IBranch> getBranches() {
		return this.branches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.accessMode == null) ? 0 : this.accessMode.hashCode());
		result = prime * result + ((this.branches == null) ? 0 : this.branches.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExternalRepositoryInfo other = (ExternalRepositoryInfo) obj;
		if (this.accessMode != other.accessMode) {
			return false;
		}
		if (this.branches == null) {
			if (other.branches != null) {
				return false;
			}
		} else if (!this.branches.equals(other.branches)) {
			return false;
		}
		return true;
	}

	public void addBranch(IBranch branch) {
		this.branches.add(branch);
	}

	public void setAccessMode(AccessMode accessMode) {
		this.accessMode = accessMode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExternalRepositoryInfo [accessMode="); //$NON-NLS-1$
		builder.append(this.accessMode);
		builder.append(", branches="); //$NON-NLS-1$
		builder.append(this.branches);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}
}
