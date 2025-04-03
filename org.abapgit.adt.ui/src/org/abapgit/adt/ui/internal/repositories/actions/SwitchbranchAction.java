package org.abapgit.adt.ui.internal.repositories.actions;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.repositories.IAbapGitRepositoriesView;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardBranchSelection;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class SwitchbranchAction extends Action {

	private IRepository selRepo;
	private final IViewPart view;
	private IProject project;

	public SwitchbranchAction(IViewPart view) {
		super(Messages.AbapGitView_action_switch_branch);
		setToolTipText(Messages.AbapGitView_action_switch_branch);

		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "/icons/etool/branches_obj.png")); //$NON-NLS-1$
		this.view = view;
	}

	@Override
	public void run() {
		this.project = getProject();
		this.selRepo = getRepository();
		if (this.selRepo != null) {
			String destination = AdtProjectServiceFactory.createProjectService().getDestinationId(this.project);
			WizardDialog dialog = new WizardDialog(this.view.getViewSite().getShell(),
					new AbapGitWizardBranchSelection(this.project, this.selRepo, destination));
			dialog.open();
		}

		((AbapGitView) this.view).refresh();

	}

	private IRepository getRepository() {
		return ((IAbapGitRepositoriesView) this.view).getRepositorySelection();
	}

	private IProject getProject() {
		return ((IAbapGitRepositoriesView) this.view).getProject();
	}
}
