package org.abapgit.adt.ui.internal.staging;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;

public class AbapGitStagingGroupNode implements IAbapGitStagingGroupNode {

	public String getType() {
		return this.type;
	}

	public String getUri() {
		return this.uri;
	}

	public List<IAbapGitObject> getAbapGitObjects() {
		return this.abapGitObjects;
	}

	private final String type;
	private final String value;
	private final String uri;
	private final List<IAbapGitObject> abapGitObjects;

	public AbapGitStagingGroupNode(String type, String value, String uri, List<IAbapGitObject> abapGitObjects) {
		super();
		this.type = type;
		this.uri = uri;
		this.value = value;
		this.abapGitObjects = abapGitObjects;
	}

	public String getValue() {
		return this.value;
	}
}
