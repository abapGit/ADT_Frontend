package org.abapgit.adt.ui.internal.repositories;

import java.util.List;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;

/**
 * Implements IRepositoryModifiedObjects
 *
 * @author I517012
 */
public class RepositoryModifiedObjects implements IRepositoryModifiedObjects {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.repositoryURL == null) ? 0 : this.repositoryURL.hashCode());
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
		RepositoryModifiedObjects other = (RepositoryModifiedObjects) obj;
		if (this.repositoryURL == null) {
			if (other.repositoryURL != null) {
				return false;
			}
		} else if (!this.repositoryURL.equals(other.repositoryURL)) {
			return false;
		}
		return true;
	}

	private final String repositoryURL;
	private final List<IOverwriteObject> modifiedObjects;

	public RepositoryModifiedObjects(String repositoryURL, List<IOverwriteObject> modifiedObjects) {
		this.repositoryURL = repositoryURL;
		this.modifiedObjects = modifiedObjects;
	}

	@Override
	public String getRepositoryURL() {
		return this.repositoryURL;
	}

	@Override
	public List<IOverwriteObject> getModifiedObjects() {
		return this.modifiedObjects;
	}

}
