package org.abapgit.adt.backend.internal;

import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfo;

public class ExternalRepositoryInfo implements IExternalRepositoryInfo {

	private AccessMode accessMode;
	private final List<IBranch> branches = new LinkedList<>();

	@Override
	public AccessMode getAccessMode() {
		return accessMode;
	}

	@Override
	public List<IBranch> getBranches() {
		return branches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessMode == null) ? 0 : accessMode.hashCode());
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalRepositoryInfo other = (ExternalRepositoryInfo) obj;
		if (accessMode != other.accessMode)
			return false;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		return true;
	}

	public void addBranch(IBranch branch) {
		branches.add(branch);
	}

	public void setAccessMode(AccessMode accessMode) {
		this.accessMode = accessMode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExternalRepositoryInfo [accessMode=");
		builder.append(accessMode);
		builder.append(", branches=");
		builder.append(branches);
		builder.append("]");
		return builder.toString();
	}
}
