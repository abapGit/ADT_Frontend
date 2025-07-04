package org.abapgit.adt.ui.internal.wizards;

import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.DialogPage;

public class AbapGitWizardPageSwitchBranchCredentials extends AbapGitWizardPageRepositoryAndCredentials {

	private final CloneData cloneData;

	public AbapGitWizardPageSwitchBranchCredentials(IProject project, String destination, CloneData cloneData) {
		super(project, destination, cloneData, false);
		this.cloneData = cloneData;
		setTitle(Messages.AbapGitWizardPageSwitch_branch_credentials_title);
		setDescription(Messages.AbapGitWizardPageSwitch_credentials_description);
	}

	@Override
	public void setVisible(boolean visible) {
		//Navigate to branch selection page if repo is public
		if (this.cloneData.externalRepoInfo != null && this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PUBLIC) {
			getContainer().showPage(getNextPage());
			getContainer().getCurrentPage().setVisible(visible);
			getContainer().getCurrentPage().setPreviousPage(getContainer().getCurrentPage());
			return;
		}

		super.setVisible(visible);
	}

	@Override
	public boolean validateAll() {
		if (!validateClientOnly()) {
			return false;
		}

		fetchRepositories();
		if (this.cloneData.repositories == null) {
			return false;
		}

		if (this.cloneData.externalRepoInfo == null) {
			fetchExternalRepoInfo();
			if (this.cloneData.externalRepoInfo == null) {
				return false;
			}
		}
		if (this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE) {
			if (!this.txtUser.isVisible()) {
				setUserAndPassControlsVisible(true);
				this.txtUser.setFocus();
				setPageComplete(false);
				setMessage(Messages.AbapGitWizardPageRepositoryAndCredentials_repo_is_private, DialogPage.INFORMATION);
				return false;
			} else {
				// update the info, now that we have proper user/password
				if (!fetchExternalRepoInfo()) {
					return false;
				}
			}
		}

		return true;
	}

}
