package org.abapgit.adt.ui.internal.repositories.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.dialogs.AbapGitDialogObjLog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;

public class GetObjLogAction extends Action {

	private final IRepository repository;
	private final AbapGitView view;

	public GetObjLogAction(IRepository repository, AbapGitView view) {
		this.repository = repository;
		this.view = view;
		setText(Messages.AbapGitView_context_object_log);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
	}

	@Override
	public void run() {
		List<IAbapObject> objectLogItems = new LinkedList<>();

		try {
			if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.view.getLastProject()).isOK()) {
				return;
			}
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					IAbapObjects abapObjects = RepositoryServiceFactory
							.createRepositoryService(
									GetObjLogAction.this.view.getAbapGitService()
											.getDestination(GetObjLogAction.this.view.getLastProject()),
									monitor)
							.getRepoObjLog(monitor, GetObjLogAction.this.repository);

					objectLogItems
							.addAll(AbapGitUIServiceFactory.createAbapGitObjLogService().renderObjLogInput(abapObjects).getAbapObjects());

				}
			});

		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, "Check status error", e.getTargetException()), //$NON-NLS-1$
					StatusManager.SHOW);
		} catch (InterruptedException e) {
		}

		TitleAreaDialog objLogDialog = new AbapGitDialogObjLog(GetObjLogAction.this.view.getTableViewer().getControl().getShell(),
				objectLogItems,
				GetObjLogAction.this.repository);
		objLogDialog.open();

	}

}