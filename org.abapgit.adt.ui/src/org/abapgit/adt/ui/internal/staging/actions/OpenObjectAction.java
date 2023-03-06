package org.abapgit.adt.ui.internal.staging.actions;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class OpenObjectAction extends BaseSelectionListenerAction {

	private final TreeViewer treeViewer;
	private final IAbapGitStagingView view;

	public OpenObjectAction(IAbapGitStagingView view, TreeViewer treeViewer) {
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
			//open action should not be enabled for the node "Not assigned to any package or transport" while grouping is enabled
			//in case only that node is selected by the user
			if (selection.getFirstElement() instanceof IAbapGitStagingGroupNode) {
				if (((IAbapGitStagingGroupNode) selection.getFirstElement()).getType() == null) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void run() {
			openObjectInEditor();
	}

	private void openObjectInEditor() {
		IStructuredSelection selection = (IStructuredSelection) this.treeViewer.getSelection();
		for (Object object : selection.toList()) {
			AbapGitUIServiceFactory.createAbapGitStagingService().openEditor(object, this.view.getProject());
		}
	}

}
