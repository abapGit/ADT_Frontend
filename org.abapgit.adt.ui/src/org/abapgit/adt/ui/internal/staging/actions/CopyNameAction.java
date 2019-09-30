package org.abapgit.adt.ui.internal.staging.actions;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class CopyNameAction extends BaseSelectionListenerAction {

	private final TreeViewer treeViewer;

	public CopyNameAction(TreeViewer treeViewer) {
		super(Messages.AbapGitStaging_action_copy);
		updateSelection((IStructuredSelection) treeViewer.getSelection());
		setToolTipText(Messages.AbapGitStaging_action_copy);
		setActionDefinitionId(ActionFactory.COPY.getCommandId());
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		this.treeViewer = treeViewer;
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) this.treeViewer.getSelection();
		copyTextToClipboard(selection.getFirstElement());
	}

	protected void copyTextToClipboard(Object object) {
		final StringBuilder data = new StringBuilder();

		if (object instanceof IAbapGitObject) {
			data.append(((IAbapGitObject) object).getName());
		} else if (object instanceof IAbapGitFile) {
			data.append(((IAbapGitFile) object).getName());
		}

		final Clipboard clipboard = new Clipboard(Display.getDefault());
		clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
		clipboard.dispose();
	}

}
