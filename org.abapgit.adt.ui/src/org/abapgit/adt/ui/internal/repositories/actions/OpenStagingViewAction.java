package org.abapgit.adt.ui.internal.repositories.actions;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class OpenStagingViewAction extends Action {

	private final IProject project;
	private final IRepository repository;

	public OpenStagingViewAction(IProject project, IRepository repository) {
		this.project = project;
		this.repository = repository;
		setText(Messages.AbapGitView_context_staging);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/view/abapgit_staging.png")); //$NON-NLS-1$
	}

	@Override
	public void run() {
		try {
			IAbapGitStagingView stagingView = ((IAbapGitStagingView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(AbapGitStagingView.VIEW_ID));
			stagingView.openStagingView(this.repository, this.project);
		} catch (PartInitException e) {
			AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
	}

}
