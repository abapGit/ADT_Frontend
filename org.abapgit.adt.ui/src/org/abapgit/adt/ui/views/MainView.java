package org.abapgit.adt.ui.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import com.sap.adt.tools.core.project.IAbapProject;

import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.abapgit.adt.Repository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.dialogs.CreateDialog;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.SWT;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MainView extends ViewPart {

	public static final String ID = "org.abapgit.adt.ui.views.MainView";

	private TableViewer viewer;
	private Action actionPull, actionDelete, actionRefresh, actionCreate;

	/**
	 * The constructor.
	 */
	public MainView() {
	}

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
			return AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_TOOL_REDO).createImage();
		}

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		setupViewer(parent);

		// Create the help context id for the viewer's control
		// this.workbench.getHelpSystem().setHelp(this.tableViewer.getControl(),
		// "org.abapgit.adt.ui.viewer");

		// getSite().setSelectionProvider(this.viewer);

		makeActions();

		hookContextMenu();

		contributeToActionBars();
	}

	private void setupViewer(Composite parent) {
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		Table table = this.viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createColumns();

		this.viewer.setContentProvider(ArrayContentProvider.getInstance());
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

		createTableViewerColumn("Branch", 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getBranch();
			}
		});
		
		createTableViewerColumn("User", 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getUser();
			}
		});
		
		createTableViewerColumn("First commit timestamp", 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getFirstCommit();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
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
		this.viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(actionRefresh);
		toolBarManager.add(actionCreate);
	}

	private void fillContextMenu(IMenuManager manager) {
//		manager.add(actionPull);
//		manager.add(actionDelete);
	}

	private void makeActions() {
		this.actionPull = new Action() {
			public void run() {
				Repository repo = (Repository) viewer.getStructuredSelection().getFirstElement();
				repo.pull();
			}
		};
		this.actionPull.setText("Pull");
		this.actionPull.setToolTipText("Pull");
		this.actionPull.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_TOOL_UP));

		this.actionDelete = new Action() {
			public void run() {
				showMessage("delete, todo");
			}
		};
		this.actionDelete.setText("Delete");
		this.actionDelete.setToolTipText("Delete");
		this.actionDelete
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

		this.actionRefresh = new Action() {
			public void run() {
				viewer.setInput(Repository.list());
			}
		};
		this.actionRefresh.setText("Refresh");
		this.actionRefresh.setToolTipText("Refresh");
		this.actionRefresh
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_TOOL_REDO));

		this.actionCreate = new Action() {
			public void run() {
				CreateDialog dialog = new CreateDialog(viewer.getControl().getShell());
				dialog.create();

				if (dialog.open() == Window.OK) {
					
					
//					if(dialog.getUrl().isEmpty() == true) {
//						showInfoDialog("Empty field", "Please input git url first.");
//						return ;
//					}
//					
//					
//					String giturl = dialog.getUrl();
//					String branch = dialog.getBranch();
//					String devpackage = dialog.getDevclass();
//					String user = dialog.getUser();
//					String pwd = dialog.getPwd();
//					String transport = dialog.getBranch();
					
//					if(giturl == "") {
//						showInfoDialog("Empty field", "CLONE MESSAGEBOX TEXT");
//						dialog.create();
//					}
					
					
//					Map<String, String> dialogInputs = new HashMap<String, String>();
//					dialogInputs.put("Git Url", giturl);
//					dialogInputs.put("Branch", branch);
//					dialogInputs.put("Package", devpackage);
//					dialogInputs.put("Username", user);
//					dialogInputs.put("Password", pwd);
//					dialogInputs.put("Transport request", transport);
					
					Repository.create(dialog.getUrl(), dialog.getBranch(), dialog.getDevclass(), dialog.getUser(), dialog.getPwd(), dialog.getTrname());
					viewer.setInput(Repository.list());
					
					
					//showInfoDialog("CLONE MESSAGEBOX TITLE", "CLONE MESSAGEBOX TEXT");
				}
			}
		};
		this.actionCreate.setText("Create");
		this.actionCreate.setToolTipText("Create");
		this.actionCreate
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Info", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
		viewer.setInput(Repository.list());
	}
	
	/** 
	 * Show an information dialog with the title and message provided.
	 * @param title   Dialog window title
	 * @param message Dialog message
	 */
	protected void showInfoDialog(String title,String message){
	  MessageBox mb = new MessageBox(viewer.getControl().getShell(),SWT.OK | SWT.ICON_WARNING);
	  mb.setText(title);
	  mb.setMessage(message);
	  mb.open();
	}

}
