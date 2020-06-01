package org.abapgit.adt.ui.internal.repositories.actions;

import java.net.MalformedURLException;
import java.net.URL;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IAbapGitRepositoriesView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Action to "Open repository in an external browser"
 */
public class OpenRepositoryAction extends Action {
	private final IViewPart view;

	public OpenRepositoryAction(IViewPart view) {
		super(Messages.AbapGitView_action_open_repo);
		setToolTipText(Messages.AbapGitView_action_open_repo_tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/external_browser.png")); //$NON-NLS-1$
		this.view = view;
	}

	@Override
	public void run() {
		IRepository repository = getRepository();
		if (repository != null) {
			try {
				// Open default external browser
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(repository.getUrl()));
			} catch (PartInitException | MalformedURLException exception) {
				MessageDialog.openError(this.view.getViewSite().getShell(), Messages.AbapGitView_action_open_repo_error_dialog_title,
						exception.getMessage());
				AbapGitUIPlugin.getDefault().getLog()
						.log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, exception.getMessage(), exception));
			}
		}
	}

	private IRepository getRepository() {
		if (this.view instanceof IAbapGitStagingView) {
			//abapGit staging view
			return ((IAbapGitStagingView) this.view).getRepository();
		} else {
			//abapGit repositories view
			return ((IAbapGitRepositoriesView) this.view).getRepository();
		}
	}
}
