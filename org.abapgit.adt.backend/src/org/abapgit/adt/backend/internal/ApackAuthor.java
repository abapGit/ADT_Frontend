package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IApackManifest.IApackAuthor;

public class ApackAuthor implements IApackAuthor {

	private String name;

	private String eMail;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getEMail() {
		return this.eMail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Author [name="); //$NON-NLS-1$
		builder.append(this.name);
		builder.append(", E-Mail="); //$NON-NLS-1$
		builder.append(this.eMail);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.eMail == null) ? 0 : this.eMail.hashCode());
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
		ApackAuthor other = (ApackAuthor) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.eMail == null) {
			if (other.eMail != null) {
				return false;
			}
		} else if (!this.eMail.equals(other.eMail)) {
			return false;
		}
		return true;
	}

	public boolean isEmpty() {
		return (this.name == null || this.name.isEmpty()) && (this.eMail == null || this.eMail.isEmpty());
	}

}
