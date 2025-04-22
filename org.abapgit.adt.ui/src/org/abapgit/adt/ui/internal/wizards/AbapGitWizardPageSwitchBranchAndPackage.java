package org.abapgit.adt.ui.internal.wizards;

import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;

public class AbapGitWizardPageSwitchBranchAndPackage extends AbapGitWizardPageBranchAndPackage {

	public AbapGitWizardPageSwitchBranchAndPackage(IProject project, String destination, CloneData cloneData, Boolean pullAction) {
		super(project, destination, cloneData, pullAction);
		setTitle(Messages.AbapGitWizardPageSwitch_branch_selection_title);
		setDescription(Messages.AbapGitWizardPageSwitch_select_branch_description);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			this.comboBranches.getCombo().setEnabled(true);
			// hiding the pull after link checkbox
			this.checkbox_lnp.setVisible(false);
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

}
