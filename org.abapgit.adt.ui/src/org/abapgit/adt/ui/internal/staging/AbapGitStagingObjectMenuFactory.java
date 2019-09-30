package org.abapgit.adt.ui.internal.staging;

import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.actions.CompareAction;
import org.abapgit.adt.ui.internal.staging.actions.CopyNameAction;
import org.abapgit.adt.ui.internal.staging.actions.OpenObjectAction;
import org.abapgit.adt.ui.internal.staging.util.AbapGitStagingService;
import org.abapgit.adt.ui.internal.staging.util.IAbapGitStagingService;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
	private final IAbapGitStagingService stagingService;

	private StageAction stageAction;
	private UnstageAction unstageAction;
	private OpenObjectAction openAction;
	private CopyNameAction copyAction;
	private CompareAction compareAction;

	public AbapGitStagingObjectMenuFactory(TreeViewer treeViewer, boolean unstaged, AbapGitStagingView view) {
		this.treeViewer = treeViewer;
		this.view = view;
		this.unstaged = unstaged;
		this.stagingService = AbapGitStagingService.getInstance();

		createActions();

		//create the context menu
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

	/**
	 * Creates all the actions which are required for building the context menu
	 */
	private void createActions() {
		this.stageAction = new StageAction(Messages.AbapGitStaging_action_stage);
		this.treeViewer.addSelectionChangedListener(this.stageAction);

		this.unstageAction = new UnstageAction(Messages.AbapGitStaging_action_unstage);
		this.treeViewer.addSelectionChangedListener(this.unstageAction);

		this.openAction = new OpenObjectAction(this.view, this.treeViewer);
		this.treeViewer.addSelectionChangedListener(this.openAction);

		this.copyAction = new CopyNameAction(this.treeViewer);
		this.treeViewer.addSelectionChangedListener(this.copyAction);

		this.compareAction = new CompareAction(this.view, this.treeViewer);
		this.treeViewer.addSelectionChangedListener(this.compareAction);
	}

	private void buildContextMenu(IMenuManager menuManager) {
		TreeSelection selection = (TreeSelection) this.treeViewer.getSelection();

		//open object action
		this.menuManager.add(this.openAction);
		this.menuManager.add(new Separator());
		if (this.unstaged) {
			//stage objects action
			this.menuManager.add(this.stageAction);
		} else {
			//unstage objects action
			this.menuManager.add(this.unstageAction);
		}
		//TODO: remove this check after the 2002 upgrade
		if (this.stagingService.isFileCompareSupported(selection.getFirstElement())) {
			//file compare
			this.menuManager.add(this.compareAction);
		}
		if (selection.size() == 1) {
			this.menuManager.add(new Separator());
			//copy name to clipboard action
			this.menuManager.add(this.copyAction);
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

	public void dispose() {
		if (this.openAction != null) {
			this.openAction = null;
		}
		if (this.stageAction != null) {
			this.stageAction = null;
		}
		if (this.unstageAction != null) {
			this.unstageAction = null;
		}
		if (this.compareAction != null) {
			this.compareAction = null;
		}
		if (this.copyAction != null) {
			this.copyAction = null;
		}
	}

}
