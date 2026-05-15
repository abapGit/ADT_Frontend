package org.abapgit.adt.ui.internal.repositories.actions;

import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;

public class OpenAbapgitWizardAction extends Action {

	private final AbapGitView view;

	public OpenAbapgitWizardAction(AbapGitView view) {
		super(Messages.AbapGitView_action_clone);
		setToolTipText(Messages.AbapGitView_action_clone);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		this.view = view;
	}

	@Override
	public void run() {
		if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.view.getLastProject()).isOK()) {
			return;
		}
			AbapGitWizard abapGitWizard = new AbapGitWizard(this.view.getLastProject());
			WizardDialog dialog = new WizardDialog(this.view.getTableViewer().getControl().getShell(), abapGitWizard);

			dialog.open();
			this.view.refresh();
	}
}
