package org.abapgit.adt.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.abapgit.adt.core.Repository;
import org.abapgit.adt.dialogs.CreateDialog;
import org.abapgit.adt.dialogs.PullDialog;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.SWT;
import javax.inject.Inject;

public class MainView extends ViewPart {

	public static final String ID = "org.abapgit.adt.views.MainView";

	@Inject
	IWorkbench workbench;
 
	private TableViewer viewer;
	private Action actionPull;
	private Action actionDelete;
	private Action actionChangeBranch;
	private Action actionRefresh;
	private Action actionCreate;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}

	@Override
	public void createPartControl(Composite parent) {
		setupViewer(parent);

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "org.abapgit.adt.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		contributeToActionBars();
	}

	private void setupViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		createColumns();
		viewer.getTable().setHeaderVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
	}

	private void createColumns() {
		createTableViewerColumn("Package", 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getPackage();
			}
		});

		createTableViewerColumn("URL", 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getURL();
			}
		});
		
		createTableViewerColumn("User", 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getUser();
			}
		});
		
		createTableViewerColumn("Last commit", 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getLastCommit();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				if (viewer.getStructuredSelection().size() == 0) {
					return;
				}
				MainView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IToolBarManager manager = getViewSite().getActionBars().getToolBarManager();
		manager.add(actionRefresh);
		manager.add(actionCreate);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionPull);
		manager.add(actionDelete);
		manager.add(actionChangeBranch);
	}

	private void makeActions() {
		actionPull = new Action() {
			public void run() {
//				Repository repo = (Repository) viewer.getStructuredSelection().getFirstElement();
//				repo.pull();				
				
				PullDialog dialog = new PullDialog(viewer.getControl().getShell());
				dialog.create();
				if (dialog.open() == Window.OK) {
					
				}
			}
		};
		actionPull.setText("Pull");
		actionPull.setToolTipText("Pull from Git repository");
		actionPull.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_UP));

		actionDelete = new Action() {
			public void run() {
				showMessage("Removal confirmation, todo");
			}
		};
		actionDelete.setText("Delete");
		actionDelete.setToolTipText("Delete package");
		actionDelete.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

		actionChangeBranch = new Action() {
			public void run() {
				showMessage("Branch change confirmation, todo");
			}
		};
		actionChangeBranch.setText("Change branch");
		actionChangeBranch.setToolTipText("Change branch for package");
		actionChangeBranch.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

		actionRefresh = new Action() {
			public void run() {
				viewer.setInput(Repository.list());
			}
		};
		actionRefresh.setText("Refresh");
		actionRefresh.setToolTipText("Refresh package list");
		actionRefresh.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_REDO));

		actionCreate = new Action() {
			public void run() {
				CreateDialog dialog = new CreateDialog(viewer.getControl().getShell());
				dialog.create();
				if (dialog.open() == Window.OK) {
//					Repository.create(dialog.getUrl(), dialog.getBranch(), dialog.getDevclass());
				}
			}
		};
		actionCreate.setText("Clone");
		actionCreate.setToolTipText("Clone Git repository");
		actionCreate.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Info", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		viewer.setInput(Repository.list());
	}
}
