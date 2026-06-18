package org.abapgit.adt.ui.internal.wizards;

import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

public class AbapGitWizardPageSwitchBranchAndPackage extends AbapGitWizardPageBranchAndPackage {

	public AbapGitWizardPageSwitchBranchAndPackage(IProject project, String destination, CloneData cloneData) {
		super(project, destination, cloneData, false);
		setTitle(Messages.AbapGitWizardPageSwitch_branch_selection_title);
		setDescription(Messages.AbapGitWizardPageSwitch_select_branch_description);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), "org.abapgit.adt.ui.switch_branch_selection"); //$NON-NLS-1$
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.abapgit.adt.ui.switch_branch_selection"); //$NON-NLS-1$
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
