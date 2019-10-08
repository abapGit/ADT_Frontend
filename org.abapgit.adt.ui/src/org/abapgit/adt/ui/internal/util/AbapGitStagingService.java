package org.abapgit.adt.ui.internal.util;

import org.eclipse.core.resources.IProject;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;

public class AbapGitStagingService implements IAbapGitStagingService {
	private static IAbapGitStagingService instance;

	//singleton
	private AbapGitStagingService() {
	}

	public static IAbapGitStagingService getInstance() {
		if (instance == null) {
			instance = new AbapGitStagingService();
		}
		return instance;
	}

	public boolean isLoggedOn(IProject project) {
		if (AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project).isOK()) {
			return true;
		}
		return false;
	}
}
