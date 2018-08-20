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
		return sha1;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public boolean isHead() {
		return isHead;
	}

	@Override
	public String getDisplayName() {
		return displayName;
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
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + (isHead ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sha1 == null) ? 0 : sha1.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { // NOPMD
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (isHead != other.isHead)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sha1 == null) {
			if (other.sha1 != null)
				return false;
		} else if (!sha1.equals(other.sha1))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Branch [sha1=");
		builder.append(sha1);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", isHead=");
		builder.append(isHead);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append("]");
		return builder.toString();
	}

}
