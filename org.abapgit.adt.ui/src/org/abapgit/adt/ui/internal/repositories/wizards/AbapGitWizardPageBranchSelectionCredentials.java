package org.abapgit.adt.ui.internal.repositories.wizards;

import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPageRepositoryAndCredentials;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.TrayDialog;

public class AbapGitWizardPageBranchSelectionCredentials extends AbapGitWizardPageRepositoryAndCredentials {

	private final CloneData cloneData;

	public AbapGitWizardPageBranchSelectionCredentials(IProject project, String destination, CloneData cloneData) {
		super(project, destination, cloneData, false);
		this.cloneData = cloneData;
		setTitle(Messages.AbapGitWizardPageRepositoryAndCredentials_select_branch_title);
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
		//Close the tray of the dialog if it was open
		TrayDialog dialog = (TrayDialog) getContainer();

		if (dialog.getTray() != null) {
			dialog.closeTray();
		}

		return true;
	}

}
