package org.abapgit.adt.ui.dialogs;

import java.net.URL;
import java.util.List;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.osgi.framework.Bundle;

import com.sap.adt.tools.core.ui.AbapCoreUi;

public class AbapGitDialogObjLog extends TitleAreaDialog {

	private TreeViewer abapObjTable;
	private PatternFilter objLogFilter;
	public final List<IObject> abapObjects;
	private final IRepository repodata;

	public AbapGitDialogObjLog(Shell parentShell, List<IObject> pullObjects, IRepository repository) {
		super(parentShell);
		this.abapObjects = pullObjects;
		this.repodata = repository;

	}

	@Override
	public void create() {
		super.create();
		setTitle(Messages.AbapGitDialogPageObjLog_title);

		int dialogMessageIcon = IMessageProvider.INFORMATION;
		String dialogStatusFlag = this.repodata.getStatusFlag();
		if (dialogStatusFlag != null && dialogStatusFlag.equals("W")) { //$NON-NLS-1$
			dialogMessageIcon = IMessageProvider.WARNING;
		}

		if (dialogStatusFlag != null && (dialogStatusFlag.equals("E") || dialogStatusFlag.equals("A"))) { //$NON-NLS-1$ //$NON-NLS-2$
			dialogMessageIcon = IMessageProvider.ERROR;
		}

		if (dialogStatusFlag != null && dialogStatusFlag.equals("S")) { //$NON-NLS-1$
		}
		setMessage(this.repodata.getStatusText() + "\n " + Messages.AbapGitDialogPageObjLog_description, dialogMessageIcon); //$NON-NLS-1$
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Composite area = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout();
//		gl.marginHeight = 0;
//		gl.marginWidth = 100;
		area.setLayout(gl);
		GridDataFactory.swtDefaults().grab(true, true).applyTo(area);
//		area.setLayout(new GridLayout(1, true));

		// create filter buttons by default
//		Label lblFilters = new Label(area, SWT.RIGHT);
//		lblFilters.setText("Filters: ");

		ToolBar bar = new ToolBar(area, SWT.FLAT);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.END, SWT.END).applyTo(bar);
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

		ToolItem filterErrorToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterErrorToolItem.setText("Error");
		filterErrorToolItem.setToolTipText("Filter Error messages");
		filterErrorToolItem.setImage(AbapCoreUi.getSharedImages().getImage(com.sap.adt.tools.core.ui.ISharedImages.ERROR));
		filterErrorToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern("Error");
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterWarningToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterWarningToolItem.setText("Warning");
		filterWarningToolItem.setToolTipText("Filter Warning messages");
		filterWarningToolItem.setImage(AbapCoreUi.getSharedImages().getImage(com.sap.adt.tools.core.ui.ISharedImages.WARNING));
		filterWarningToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern("Warning");
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterAllToolItem.setText("All");
		filterAllToolItem.setToolTipText("Filter All messages");
		filterAllToolItem.setImage(AbapCoreUi.getSharedImages().getImage(com.sap.adt.tools.core.ui.ISharedImages.ABAP_LOG));
//		filterSuccessToolItem.setImage(PDEPluginImages.DESC_WARNING_CO.createImage());
		filterAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern(null);
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		//-> CLEANUP

		FilteredTree tree = new FilteredTree(area, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, this.objLogFilter, true);

		this.abapObjTable = tree.getViewer();
		this.abapObjTable.setContentProvider(new ObjectTreeContentProvider());
		Tree table = this.abapObjTable.getTree();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);

		table.setFocus();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
//		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns();

		this.abapObjTable.setInput(this.abapObjects);
		table.setSortColumn(table.getColumn(1));
		table.setSortDirection(SWT.UP);

		return area;
	}



	private void createColumns() {
		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_type, 200).setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getObjType();
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String objType = p.getObjType();
				Image Message = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);

				if (objType != null && objType.equals("Message")) { //$NON-NLS-1$
					return Message;
				}

				return null;
			}

		});

//		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_name, 200).setLabelProvider(new ColumnLabelProvider() {
//			@Override
//			public String getText(Object element) {
//				IObject p = (IObject) element;
//				return p.getObjName();
//			}
//		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_status, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
//				IObject p = (IObject) element;
//				return p.getObjStatus();
				return null;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String objStatus = p.getObjStatus();
//				String objType = p.getObjType();

				Bundle bundle = Platform.getBundle("org.eclipse.jdt.doc.user"); //$NON-NLS-1$
				URL imgUrl = null;

				if (objStatus != null && objStatus.equals("W")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.jdt.ui/obj16/warning_obj.png"), null); //$NON-NLS-1$
				}

				if (objStatus != null && objStatus.equals("E")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.jdt.ui/obj16/error_obj.png"), null); //$NON-NLS-1$
				}

				if (objStatus != null && objStatus.equals("A")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.ltk.ui.refactoring/obj16/fatalerror_obj.png"), null); //$NON-NLS-1$
				}

				if (objStatus != null && objStatus.equals("S")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.jdt.junit/obj16/testok.png"), null); //$NON-NLS-1$
				}

				if (objStatus != null && objStatus.equals("I")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.jdt.ui/obj16/info_obj.png"), null); //$NON-NLS-1$
				}

//				if (objType != null && objType.equals("Message")) { //$NON-NLS-1$
//					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.jdt.ui/obj16/file_obj.png"), null); //$NON-NLS-1$
//				}

				if (imgUrl == null) {
					bundle = Platform.getBundle("org.eclipse.ui"); //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("icons/full/obj16/blank.png"), null); //$NON-NLS-1$
				}

				ImageDescriptor imageDesc = ImageDescriptor.createFromURL(imgUrl);
				return imageDesc.createImage();
			}

		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_msg_type, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				String textMsgType = p.getMsgType();

				if (textMsgType != null && textMsgType.equals("W")) { //$NON-NLS-1$
					return "Warning"; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals("E")) { //$NON-NLS-1$
					return "Error"; //$NON-NLS-1$
				}

				if (textMsgType != null && textMsgType.equals("S")) { //$NON-NLS-1$
					return null;
				}

				if (textMsgType != null && textMsgType.equals("I")) { //$NON-NLS-1$
					return "Info"; //$NON-NLS-1$
				}

				return textMsgType;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String textMsgType = p.getMsgType();

				Image Warning = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
				Image Error = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);

				if (textMsgType != null && textMsgType.equals("W")) { //$NON-NLS-1$
					return Warning;
				}

				if (textMsgType != null && textMsgType.equals("E")) { //$NON-NLS-1$
					return Error;
				}

				if (textMsgType != null && textMsgType.equals("S")) { //$NON-NLS-1$
					return null;
				}

				return null;
			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_msg_text, 500).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getMsgText();
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
		return viewerColumn;
	}


	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

}


class ObjectTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		IObject abapObj = (IObject) parentElement;
		return abapObj.listMessages().toArray();
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
			return line.listMessages().size() != 0;
		}
		return false;
	}

}