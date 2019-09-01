package org.abapgit.adt.ui.internal.views;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class AbapGitStagingObjectMenuFactory {

	private final MenuManager menuManager;
	private final TreeViewer treeViewer;
	private final AbapGitStagingView view;
	private final boolean unstaged;

	private StageAction stageAction;
	private UnstageAction unstageAction;
	private OpenAction openAction;
	private CopyAction copyAction;

	public AbapGitStagingObjectMenuFactory(TreeViewer treeViewer, boolean unstaged, AbapGitStagingView view) {
		this.treeViewer = treeViewer;
		this.view = view;
		this.unstaged = unstaged;

		createActions();

		this.menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		this.menuManager.setRemoveAllWhenShown(true);
		this.menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				buildContextMenu(manager);
			}
		});
		Menu menu = this.menuManager.createContextMenu(treeViewer.getControl());
		treeViewer.getControl().setMenu(menu);
	}

	private void createActions() {
		this.stageAction = new StageAction(Messages.AbapGitStaging_action_stage);
		this.treeViewer.addSelectionChangedListener(this.stageAction);

		this.unstageAction = new UnstageAction(Messages.AbapGitStaging_action_unstage);
		this.treeViewer.addSelectionChangedListener(this.unstageAction);

		this.openAction = new OpenAction(Messages.AbapGitStaging_action_open);
		this.treeViewer.addSelectionChangedListener(this.openAction);

		this.copyAction = new CopyAction(Messages.AbapGitView_action_copy);
		this.treeViewer.addSelectionChangedListener(this.copyAction);
	}

	private void buildContextMenu(IMenuManager menuManager) {
		TreeSelection selection = (TreeSelection) this.treeViewer.getSelection();

		if (selection.size() == 1) {
			if (this.openAction != null) {
				this.menuManager.add(this.openAction);
			}
		}

		if (this.unstaged) {
			if (this.stageAction != null) {
				this.menuManager.add(this.stageAction);
			}
		} else {
			if (this.unstageAction != null) {
				this.menuManager.add(this.unstageAction);
			}
		}

		if (selection.size() == 1) {
			this.menuManager.add(new Separator());
			if (this.copyAction != null) {
				this.menuManager.add(this.copyAction);
			}
		}
	}

	/**
	 * Stage the selected objects
	 */
	private class StageAction extends BaseSelectionListenerAction {
		protected StageAction(String text) {
			super(text);
			setToolTipText(Messages.AbapGitStaging_action_stage_xtol);
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection();
			AbapGitStagingObjectMenuFactory.this.view.stageSelectedObjects(selection);
		}
	}

	/**
	 * Unstage the selected objects
	 */
	private class UnstageAction extends BaseSelectionListenerAction {
		protected UnstageAction(String text) {
			super(text);
			setToolTipText(Messages.AbapGitStaging_action_unstage_xtol);
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/unstage.png")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection();
			AbapGitStagingObjectMenuFactory.this.view.unstageSelectedObjects(selection);
		}
	}

	/**
	 * Opens the native editor for abap objects
	 */
	private class OpenAction extends BaseSelectionListenerAction {
		protected OpenAction(String text) {
			super(text);
			setEnabled(updateSelection((IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection()));
		}

		@Override
		protected boolean updateSelection(IStructuredSelection selection) {
			Object object = selection.getFirstElement();
			//open action is enabled for only for abap objects except for the node "non code and meta files" and file nodes
			if (object instanceof IAbapGitObject) {
				if (((IAbapGitObject) object).getType() != null) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection();
			if (selection.getFirstElement() instanceof IAbapGitObject) {
				AbapGitStagingObjectMenuFactory.this.view.openAbapObject((IAbapGitObject) selection.getFirstElement());
			}
		}
	}

	/**
	 * Copies the name of the current selection to the clipboard.
	 */
	private class CopyAction extends BaseSelectionListenerAction {
		protected CopyAction(String text) {
			super(text);
			updateSelection((IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection());
			setToolTipText(Messages.AbapGitView_action_copy);
			setAccelerator(SWT.ALT | 'C');
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		}

		@Override
		public void run() {
			IStructuredSelection selection = (IStructuredSelection) AbapGitStagingObjectMenuFactory.this.treeViewer.getSelection();
			AbapGitStagingObjectMenuFactory.this.view.copy(selection.getFirstElement());
		}
	}

	public void dispose() {
		if (this.openAction != null) {
			this.openAction = null;
		}
		if (this.copyAction != null) {
			this.openAction = null;
		}
		if (this.stageAction != null) {
			this.openAction = null;
		}
		if (this.unstageAction != null) {
			this.openAction = null;
		}
	}

}
