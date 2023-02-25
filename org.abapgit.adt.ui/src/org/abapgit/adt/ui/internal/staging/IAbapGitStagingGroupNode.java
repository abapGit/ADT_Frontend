package org.abapgit.adt.ui.internal.staging;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;

/**
 * Group node for the staging view tree hierarchy to group AbapGitObjects
 * according to selection
 */
public interface IAbapGitStagingGroupNode {

	/**
	 * Get the type of the group node
	 */
	public String getType();

	/**
	 * Get the value of the group node
	 */
	public String getValue();

	/**
	 * Get the ADT URI of the group node
	 */
	public String getUri();

	/**
	 * Get all the abapGitObjects grouped under this node
	 */
	public List<IAbapGitObject> getAbapGitObjects();
}
