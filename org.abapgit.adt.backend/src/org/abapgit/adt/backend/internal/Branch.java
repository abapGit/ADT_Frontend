package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IExternalRepositoryInfo.IBranch;

public class Branch implements IBranch {

	private String sha1;
	private String name;
	private String type;
	private boolean isHead;
	private String displayName;

	@Override
	public String getSha1() {
		return this.sha1;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public boolean isHead() {
		return this.isHead;
	}

	@Override
	public String getDisplayName() {
		return this.displayName;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIsHead(boolean isHead) {
		this.isHead = isHead;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.displayName == null) ? 0 : this.displayName.hashCode());
		result = prime * result + (this.isHead ? 1231 : 1237);
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.sha1 == null) ? 0 : this.sha1.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
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
		Branch other = (Branch) obj;
		if (this.displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!this.displayName.equals(other.displayName)) {
			return false;
		}
		if (this.isHead != other.isHead) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.sha1 == null) {
			if (other.sha1 != null) {
				return false;
			}
		} else if (!this.sha1.equals(other.sha1)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Branch [sha1="); //$NON-NLS-1$
		builder.append(this.sha1);
		builder.append(", name="); //$NON-NLS-1$
		builder.append(this.name);
		builder.append(", type="); //$NON-NLS-1$
		builder.append(this.type);
		builder.append(", isHead="); //$NON-NLS-1$
		builder.append(this.isHead);
		builder.append(", displayName="); //$NON-NLS-1$
		builder.append(this.displayName);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

}
