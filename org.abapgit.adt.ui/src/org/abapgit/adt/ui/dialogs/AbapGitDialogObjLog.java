package org.abapgit.adt.ui.dialogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class AbapGitDialogObjLog extends TitleAreaDialog implements IResourceChangeListener {

	TreeViewer abapObjTable;
	private PatternFilter objLogFilter;
	public FilteredTree tree;
	public final List<IObject> abapObjects;
	private final IRepository repodata;

	private final Image warningImage;
	private final Image errorImage;
	private final Image successImage;
	private final Image infoImage;
	private Action actionCopy;
	private final static String ERROR_FLAG = "E"; //$NON-NLS-1$
	private final static String WARNING_FLAG = "W"; //$NON-NLS-1$
	private final static String INFO_FLAG = "I"; //$NON-NLS-1$
	private final static String SUCCESS_FLAG = "S"; //$NON-NLS-1$

	public AbapGitDialogObjLog(Shell parentShell, List<IObject> pullObjects, IRepository repository) {
		super(parentShell);
		this.abapObjects = pullObjects;
		this.repodata = repository;

		//Icons
		this.warningImage = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jdt.ui", "icons/full/obj16/warning_obj.png") //$NON-NLS-1$//$NON-NLS-2$
				.createImage();
		this.errorImage = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.jdt.ui", "icons/full/obj16/error_obj.png").createImage(); //$NON-NLS-1$ //$NON-NLS-2$
		this.successImage = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/obj16/activity.png").createImage(); //$NON-NLS-1$//$NON-NLS-2$
		this.infoImage = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/obj16/info_tsk.png").createImage(); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Override
	public void create() {
		super.create();
		setTitle(Messages.AbapGitDialogPageObjLog_title);

		int dialogMessageIcon = IMessageProvider.INFORMATION;
		String dialogStatusFlag = this.repodata.getStatusFlag();
		if (dialogStatusFlag != null && dialogStatusFlag.equals(WARNING_FLAG)) {
			dialogMessageIcon = IMessageProvider.WARNING;
		}

		if (dialogStatusFlag != null && (dialogStatusFlag.equals(ERROR_FLAG) || dialogStatusFlag.equals("A"))) { //$NON-NLS-1$
			dialogMessageIcon = IMessageProvider.ERROR;
		}

		if (dialogStatusFlag != null && dialogStatusFlag.equals(SUCCESS_FLAG)) {
		}
		setMessage(this.repodata.getStatusText() + "\n " + Messages.AbapGitDialogPageObjLog_description, dialogMessageIcon); //$NON-NLS-1$
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		GridLayout gl = new GridLayout();
		ObjectTreeContentProvider contProv = new ObjectTreeContentProvider();
		parent.setLayout(gl);

		ToolBar bar = new ToolBar(parent, SWT.FLAT);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.END, SWT.CENTER).applyTo(bar);
		RowLayoutFactory.fillDefaults().pack(true).applyTo(bar);

		this.objLogFilter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(final Viewer viewer, final Object element) {
				TreeViewer treeViewer = (TreeViewer) viewer;
				int numberOfColumns = treeViewer.getTree().getColumnCount();
				for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
					ColumnLabelProvider labelProvider = (ColumnLabelProvider) treeViewer.getLabelProvider(columnIndex);
					String labelText = labelProvider.getText(element);
					if (wordMatches(labelText)) {
						return true;
					}
				}
				return false;
			}

			@Override
			protected boolean isParentMatch(Viewer viewer, Object element) {
				return super.isParentMatch(viewer, element);
			}

		};

		ToolItem exportToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);

		exportToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_export_log_tooltip);
		exportToolItem.setImage(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.views.log", "icons/elcl16/export_log.png") //$NON-NLS-1$//$NON-NLS-2$
				.createImage());
		exportToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Display display = AbapGitDialogObjLog.this.tree.getViewer().getControl().getDisplay();
				Shell shell = new Shell(display);
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "*.log" }); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] { "*.log" }); //$NON-NLS-1$
				dialog.setFilterPath("c:\\"); // Windows path //$NON-NLS-1$
				dialog.setFileName(Messages.AbapGitDialogPageObjLog_default_filename);

				String dialogResult = dialog.open();
//				System.out.println(Messages.AbapGitDialogPageObjLog_default_path + dialogResult);

				if (dialogResult != null) {
					saveObjectLog(AbapGitDialogObjLog.this.tree.getViewer(), dialogResult);
				}

				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
				display.dispose();

			}
		});

		ToolItem expandAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		expandAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_expand_all_tooltip);
		expandAllToolItem
				.setImage(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/elcl16/expandall.png").createImage()); //$NON-NLS-1$ //$NON-NLS-2$
		expandAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.tree.setRedraw(false);
				AbapGitDialogObjLog.this.tree.getViewer().expandAll();
				AbapGitDialogObjLog.this.tree.setRedraw(true);
			}
		});

		ToolItem collapseAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		collapseAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_collapse_all_tooltip);
		collapseAllToolItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
		collapseAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.tree.setRedraw(false);
				AbapGitDialogObjLog.this.tree.getViewer().collapseAll();
				AbapGitDialogObjLog.this.tree.setRedraw(true);
			}
		});

		ToolItem filterErrorToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterErrorToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_error_tooltip);
		filterErrorToolItem.setImage(this.errorImage);
		filterErrorToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contProv.filter(AbapGitDialogObjLog.ERROR_FLAG);
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterWarningToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterWarningToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_warning_tooltip);
		filterWarningToolItem.setImage(this.warningImage);
		filterWarningToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contProv.filter(AbapGitDialogObjLog.WARNING_FLAG);
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_all_tooltip);
		filterAllToolItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
		filterAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				contProv.filter(null);
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		this.tree = new FilteredTree(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, this.objLogFilter, true);

		this.abapObjTable = this.tree.getViewer();
		this.abapObjTable.setContentProvider(contProv);
		Tree table = this.abapObjTable.getTree();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.tree);

		table.setFocus();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns(contProv);

		this.abapObjTable.setInput(this.abapObjects);

		makeActions();

		return parent;
	}

	private void saveObjectLog(TreeViewer treeViewer, String pathname) {

		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(pathname)), "UTF-8"))) { //$NON-NLS-1$
			writer.println(Messages.AbapGitDialogPageObjLog_export_error_msg + this.repodata.getStatusText());
			if (!this.abapObjects.isEmpty()) {
				writer.write(String.format("%-50s %-20s %-10s %-20s%n", Messages.AbapGitDialogPageObjLog_export_log_obj_name, //$NON-NLS-1$
						Messages.AbapGitDialogPageObjLog_export_log_obj_type, Messages.AbapGitDialogPageObjLog_export_log_obj_flag,
						Messages.AbapGitDialogPageObjLog_export_log_obj_msg));
			}

			for (IObject typeNode : this.abapObjects) {

				String logObjType = typeNode.getObjName();
				writer.println(
						"______________________________________________________________________________________________________________"); //$NON-NLS-1$

				writer.write(String.format("%-50s %-20s %-10s %-20s%n", logObjType + "(" + typeNode.countChildren() + ")", "", "", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

				List<IObject> logObjects = typeNode.listChildObjects();

				for (IObject logObject : logObjects) {
					String logObjName = logObject.getObjName();
					if (logObjName != null) {
						writer.write(String.format("%-50s %-20s %-10s %-20s%n", "   " + logObjName, logObjType, //$NON-NLS-1$ //$NON-NLS-2$
								logObject.getMsgType(), logObject.getMsgText()));
					}
				}

			}
		} catch (FileNotFoundException ex) {
			System.out.println("File not found exception: " + ex.getMessage()); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private void createColumns(ObjectTreeContentProvider contProv) {

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_name, 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;

				int objCounter = p.countChildren();
				long filteredCounter = objCounter;
				if (contProv.filter != null) {
					filteredCounter = p.listChildObjects().stream().filter(o -> o.getObjStatus().equals(contProv.filter)).count();
				}
				String result = p.getObjName();
				if (objCounter > 0) {
					result = result + " ("; //$NON-NLS-1$
					if (filteredCounter != objCounter) {
						result += filteredCounter + "/"; //$NON-NLS-1$

					}
					result += p.countChildren() + ")"; //$NON-NLS-1$
				}

				return result;
			}
		});
		hookContextMenu(this.abapObjTable);

		createTableViewerColumn(Messages.AbapGitDialogImport_column_msg_type, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				String textMsgType = p.getMsgType();

				if (p.listChildObjects().toArray().length > 0 && textMsgType != null) {
					return ""; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.WARNING_FLAG)) {
					return "Warning"; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.ERROR_FLAG)) {
					return "Error"; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.SUCCESS_FLAG)) {
					return "Success"; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.INFO_FLAG)) {
					return "Info"; //$NON-NLS-1$
				}

				return textMsgType;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;

				//-> Only for TYPE level objects
				if (!p.listChildObjects().isEmpty()) {

					//-> Set any TYPE object status
					String childObjStatus = p.listChildObjects().get(0).getObjStatus();
					p.setObjStatus(childObjStatus);
					p.setMsgType(childObjStatus);

					//-> Set TYPE object status to error/warning/info if any child has this status
					if (p.listChildObjects().stream().anyMatch(o -> o.getObjStatus().equals(AbapGitDialogObjLog.WARNING_FLAG))) {
						p.setObjStatus(AbapGitDialogObjLog.WARNING_FLAG);
						p.setMsgType(AbapGitDialogObjLog.WARNING_FLAG);
					}
					if (p.listChildObjects().stream().anyMatch(o -> o.getObjStatus().equals(AbapGitDialogObjLog.ERROR_FLAG))) {
						p.setObjStatus(AbapGitDialogObjLog.ERROR_FLAG);
						p.setMsgType(AbapGitDialogObjLog.ERROR_FLAG);
					}
					if (p.listChildObjects().stream().anyMatch(o -> o.getObjStatus().equals(AbapGitDialogObjLog.INFO_FLAG))) {
						p.setObjStatus(AbapGitDialogObjLog.INFO_FLAG);
						p.setMsgType(AbapGitDialogObjLog.INFO_FLAG);
					}
				}

				String textMsgType = p.getMsgType();

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.WARNING_FLAG)) {
					return AbapGitDialogObjLog.this.warningImage;
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.ERROR_FLAG)) {
					return AbapGitDialogObjLog.this.errorImage;
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.INFO_FLAG)) {
					return AbapGitDialogObjLog.this.infoImage;
				}

				if (textMsgType != null && textMsgType.equals(AbapGitDialogObjLog.SUCCESS_FLAG)) {
					return AbapGitDialogObjLog.this.successImage;
				}

				return null;
			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_msg_text, 700).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getMsgText();
			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_type, 1).setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getObjType();
			}

		});

	}

	private TreeViewerColumn createTableViewerColumn(String title, int bound) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this.abapObjTable, SWT.NONE);
		TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		//-> still present for proper search
		if (column.getText().equals("Type")) { //$NON-NLS-1$
			column.setWidth(0);
			column.setResizable(false);
		}
		return viewerColumn;
	}

	private void hookContextMenu(TreeViewer treeViewer) {
		final MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);

		Menu menu = menuManager.createContextMenu(treeViewer.getTree());
		treeViewer.getTree().setMenu(menu);

		menuManager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}

			private void fillContextMenu(IMenuManager manager) {

				IStructuredSelection selection = (IStructuredSelection) AbapGitDialogObjLog.this.tree.getViewer().getSelection();
				if (selection.size() != 1) {
					return;
				}
				menuManager.add(AbapGitDialogObjLog.this.actionCopy);

			}
		});

	}

	private void makeActions() {
		this.actionCopy = new Action() {
			public void run() {
				copy();
			}
		};
		this.actionCopy.setText(Messages.AbapGitView_action_copy);
		this.actionCopy.setToolTipText(Messages.AbapGitView_action_copy);

		this.actionCopy.setAccelerator(SWT.ALT | 'C');
		this.actionCopy.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	/**
	 * Copies the current selection to the clipboard.
	 *
	 * @param table
	 *            the data source
	 */
	protected void copy() {

		Object firstElement = AbapGitDialogObjLog.this.tree.getViewer().getStructuredSelection().getFirstElement();
		final StringBuilder data = new StringBuilder();

		IObject selectedAbapObj = (IObject) firstElement;
		data.append(selectedAbapObj.getObjName() + " | " + selectedAbapObj.getObjType() + " | " + selectedAbapObj.getMsgType() + " | " //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
				+ selectedAbapObj.getMsgText());

		final Clipboard clipboard = new Clipboard(AbapGitDialogObjLog.this.tree.getViewer().getControl().getDisplay());
		clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
		clipboard.dispose();

	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

class ObjectTreeContentProvider implements ITreeContentProvider {

	public String filter;

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {

		if (this.filter != null) {
			return ((List<IObject>) inputElement).stream()
					.filter(o -> o.listChildObjects().stream().anyMatch(c -> c.getObjStatus().equals(this.filter))).toArray();
		}
		return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	public void filter(String string) {
		this.filter = string;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		IObject abapObj = (IObject) parentElement;

		if (this.filter != null) {
			return abapObj.listChildObjects().stream().filter(o -> o.getObjStatus().equals(this.filter)).toArray();
		}

		return abapObj.listChildObjects().toArray();
	}

	@Override
	public Object getParent(Object element) {
		IObject abapObj = (IObject) element;
		return abapObj.getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IObject) {
			IObject line = (IObject) element;
			return line.listChildObjects().size() != 0;
		}
		return false;
	}

}