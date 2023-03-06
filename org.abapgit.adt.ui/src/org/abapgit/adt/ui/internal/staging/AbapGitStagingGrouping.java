package org.abapgit.adt.ui.internal.staging;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;

public class AbapGitStagingGrouping implements IAbapGitStagingGrouping {

	List<IAbapGitStagingGroupNode> stagedGroupObjects;
	List<IAbapGitStagingGroupNode> unStagedGroupObjects;


	public AbapGitStagingGrouping() {
		super();
		this.stagedGroupObjects = new ArrayList<>();
		this.unStagedGroupObjects = new ArrayList<>();
	}

	@Override
	public List<IAbapGitStagingGroupNode> getUnstagedGroupObjects() {
		return this.unStagedGroupObjects;
	}

	@Override
	public List<IAbapGitStagingGroupNode> getStagedGroupObjects() {
		return this.stagedGroupObjects;
	}

}
