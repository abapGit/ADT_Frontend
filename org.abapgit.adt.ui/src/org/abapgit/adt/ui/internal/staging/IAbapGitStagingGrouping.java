package org.abapgit.adt.ui.internal.staging;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;

/**
 * Model for grouping of IAbapGitObjects under IAbapGitStagingGroupNodes in
 * abapgit staging view.
 *
 * The model holds staged and unstaged group nodes.
 *
 * @author I517012
 *
 */
public interface IAbapGitStagingGrouping {

	/**
	 * Get the unStaged group objects from the model
	 *
	 * @return unstaged group objects
	 */
	List<IAbapGitStagingGroupNode> getUnstagedGroupObjects();

	/**
	 * Get the staged group objects from the model
	 *
	 * @return staged group objects
	 */
	List<IAbapGitStagingGroupNode> getStagedGroupObjects();

}
