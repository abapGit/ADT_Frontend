package org.abapgit.adt.ui.internal.staging.actions;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class OpenObjectAction extends BaseSelectionListenerAction {

	private final TreeViewer treeViewer;
	private final AbapGitStagingView view;

	public OpenObjectAction(AbapGitStagingView view, TreeViewer treeViewer) {
		super(Messages.AbapGitStaging_action_open);
		setEnabled(updateSelection((IStructuredSelection) treeViewer.getSelection()));

		this.view = view;
		this.treeViewer = treeViewer;
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		//open action should not be enabled for the node "non code and meta files"
		//in case only that node is selected by the user
		if (selection.size() == 1) {
			if (selection.getFirstElement() instanceof IAbapGitObject) {
				if (((IAbapGitObject) selection.getFirstElement()).getType() == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) this.treeViewer.getSelection();
		for (Object object : selection.toList()) {
			AbapGitStagingService.getInstance().openEditor(object, this.view.getProject());
		}
	}
}
