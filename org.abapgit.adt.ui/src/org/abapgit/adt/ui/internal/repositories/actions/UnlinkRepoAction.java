package org.abapgit.adt.ui.internal.repositories.actions;

import java.lang.reflect.InvocationTargetException;

import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.util.GitCredentialsService;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;

public class UnlinkRepoAction extends Action {

	private final IRepository repository;
	private final AbapGitView view;

	public UnlinkRepoAction(IRepository repository, AbapGitView view) {
		this.repository = repository;
		this.view = view;
		setText(Messages.AbapGitView_context_unlink);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
	}

	@Override
	public void run() {
		if (!MessageDialog.openConfirm(this.view.getSite().getShell(), Messages.AbapGitView_context_dialog_unlink_title,
				NLS.bind(Messages.AbapGitView_context_dialog_unlink_message, this.repository.getUrl(), this.repository.getPackage()))) {
			return;
		}
		try {
			if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.view.getLastProject()).isOK()) {
				return;
			}
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					RepositoryServiceFactory
							.createRepositoryService(
									UnlinkRepoAction.this.view.getAbapGitService()
											.getDestination(UnlinkRepoAction.this.view.getLastProject()),
									monitor)
							.unlinkRepository(UnlinkRepoAction.this.repository.getKey(), monitor);
				}
			});

			//Delete credentials from Secure storage
			GitCredentialsService.deleteCredentialsFromSecureStorage(this.repository.getUrl());
			this.view.refresh();
		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, Messages.AbapGitView_context_unlink_error, e.getTargetException()),
					StatusManager.SHOW);
		} catch (InterruptedException e) {
		}
	}

}
