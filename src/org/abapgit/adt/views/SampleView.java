package org.abapgit.adt.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.abapgit.adt.core.Repository;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.SWT;
import javax.inject.Inject;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.abapgit.adt.views.SampleView";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action actionPull;
	private Action actionRefresh;
	private Action actionAdd;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			// return "blah";
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
		createColumns(viewer);
		viewer.getTable().setHeaderVisible(true);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
//		viewer.setInput(Repository.list());
		// viewer.setInput(new String[] { "One", "Two", "Three" });
		// viewer.setLabelProvider(new ViewLabelProvider());
	}

	private void createColumns(final TableViewer viewer) {
		createTableViewerColumn("Key", 100, 0).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getKey();
			}
		});

		createTableViewerColumn("URL", 100, 1).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getURL();
			}
		});

		createTableViewerColumn("Package", 100, 2).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Repository p = (Repository) element;
				return p.getDevclass();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
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
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(actionPull);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(actionRefresh);
		manager.add(actionAdd);
	}

	private void makeActions() {
		actionPull = new Action() {
			public void run() {
				System.out.println(viewer.getStructuredSelection().size());
				showMessage("pull, todo");
			}
		};
		actionPull.setText("Pull");
		actionPull.setToolTipText("Pull");
		actionPull.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_UP));

		actionRefresh = new Action() {
			public void run() {
				viewer.setInput(Repository.list());
			}
		};
		actionRefresh.setText("Refresh");
		actionRefresh.setToolTipText("Refresh");
		actionRefresh.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
		
		actionAdd = new Action() {
			public void run() {
				showMessage("add, todo");
			}
		};
		actionAdd.setText("Add");
		actionAdd.setToolTipText("Add");
		actionAdd.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Sample View", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
