package org.abapgit.adt.ui.internal.repositories.wizards;

import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPageBranchAndPackage;
import org.eclipse.core.resources.IProject;

public class AbapGitWizardPageBranchSelection extends AbapGitWizardPageBranchAndPackage {


	public AbapGitWizardPageBranchSelection(IProject project, String destination, CloneData cloneData, Boolean pullAction) {
		super(project, destination, cloneData, pullAction);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			this.comboBranches.getCombo().setEnabled(true);
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

}
