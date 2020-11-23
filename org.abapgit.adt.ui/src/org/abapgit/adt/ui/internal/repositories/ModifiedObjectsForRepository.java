package org.abapgit.adt.ui.internal.repositories;

import java.util.List;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitObject;

public class ModifiedObjectsForRepository {
	private String repositoryURL;
	private List<IAbapGitObject> modifiedObjects;

	public String getRepositoryURL() {
		return this.repositoryURL;
	}

	public void setRepositoryURL(String repositoryURL) {
		this.repositoryURL = repositoryURL;
	}

	public List<IAbapGitObject> getModifiedObjects() {
		return this.modifiedObjects;
	}

	public void setModifiedObjects(List<IAbapGitObject> modifiedObjects) {
		this.modifiedObjects = modifiedObjects;
	}

	public ModifiedObjectsForRepository(String repositoryURL, List<IAbapGitObject> modifiedObjects) {
		super();
		this.repositoryURL = repositoryURL;
		this.modifiedObjects = modifiedObjects;
	}

}
