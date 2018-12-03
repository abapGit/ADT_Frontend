package org.abapgit.adt.ui.internal.handlers;

import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.views.AbapGitView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class AbapGitPullHandler extends AbstractHandler {


	public AbapGitPullHandler() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.ICON_INFORMATION | SWT.OK);
			messageBox.setMessage(Messages.AbapGitPull_not_yet_available);
			messageBox.open();

			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AbapGitView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();

			MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.ICON_ERROR | SWT.OK);
			messageBox.setText(Messages.AbapGitView_open_error);
			messageBox.open();

			return false;
		}

		return true;
	}

}
