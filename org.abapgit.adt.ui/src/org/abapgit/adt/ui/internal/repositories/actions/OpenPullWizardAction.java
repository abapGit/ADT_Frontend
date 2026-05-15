package org.abapgit.adt.ui.internal.repositories.actions;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPull;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;

public class OpenPullWizardAction extends Action {

	private final AbapGitView view;
	private IRepository selRepo;

	public OpenPullWizardAction(AbapGitView view) {
		super(Messages.AbapGitView_context_pull);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/view/abapgit.png")); //$NON-NLS-1$
		this.view = view;
	}

	@Override
	public void run() {
		if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.view.getLastProject()).isOK()) {
			return;
		}

		Object firstElement = this.view.getTableViewer().getStructuredSelection().getFirstElement();

		if (firstElement instanceof IRepository) {
			this.selRepo = ((IRepository) firstElement);
		}

		if (this.selRepo != null) {

			Object viewerInput = this.view.getTableViewer().getInput();
			List<IRepository> allRepositories = new ArrayList<IRepository>();
			if (viewerInput != null && viewerInput instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<IRepository> viewerRepositories = (List<IRepository>) viewerInput;
				allRepositories.addAll(viewerRepositories);
			}

			WizardDialog wizardDialog = new WizardDialog(this.view.getTableViewer().getControl().getShell(),
					new AbapGitWizardPull(this.view.getLastProject(), this.selRepo, allRepositories));
			wizardDialog.open();
		}

		this.view.refresh();
	}
}
