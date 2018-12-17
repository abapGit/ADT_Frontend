package org.abapgit.adt.ui.dialogs;

import java.util.List;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import com.sap.adt.tools.core.ui.Activator;

public class AbapGitDialogImport extends TitleAreaDialog {

	private TreeViewer abapObjTable;
	public final List<IObject> abapObjects;

	public AbapGitDialogImport(Shell parentShell, List<IObject> pullObjects) {
		super(parentShell);
		this.abapObjects = pullObjects;
	}

	@Override
	public void create() {
		super.create();
		setTitle("ABAP objects import log");
		setMessage("Please check status and message for all imported ABAP objects", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {



//		Composite area = (Composite) super.createDialogArea(parent);
		Composite area = new Composite(parent, SWT.NONE);
		area.setLayout(new GridLayout(1, true));

		PatternFilter filter = new PatternFilter() {
			@Override
			protected boolean isLeafMatch(final Viewer viewer, final Object element) {
				TreeViewer treeViewer = (TreeViewer) viewer;
				int numberOfColumns = treeViewer.getTree().getColumnCount();
				boolean isMatch = false;
				for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
					ColumnLabelProvider labelProvider = (ColumnLabelProvider) treeViewer.getLabelProvider(columnIndex);
					String labelText = labelProvider.getText(element);
					isMatch |= wordMatches(labelText);
				}
				return isMatch;
			}

			@Override
			protected boolean isParentMatch(Viewer viewer, Object element) {

				return super.isParentMatch(viewer, element);
			}

		};
		FilteredTree tree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, filter, true);

		this.abapObjTable = tree.getViewer();
		this.abapObjTable.setContentProvider(new ObjectTreeContentProvider());

		Tree table = this.abapObjTable.getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns();

		this.abapObjTable.setInput(this.abapObjects);
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

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_name, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getObjName();
			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_status, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
//				return p.getObjStatus();
				return null;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String objStatus = p.getObjStatus();
				String objType = p.getObjType();
				Image Status_warning = Activator.getDefault().getImage(Activator.EXCEPTION);
				Image Status_error = Activator.getDefault().getImage(Activator.ERROR);
				Image Status_info = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);

				if (objStatus != null && objStatus.equals("W")) { //$NON-NLS-1$
					return Status_warning;
				}

				if (objStatus != null && objStatus.equals("E")) { //$NON-NLS-1$
					return Status_error;
				}

				if (objStatus != null && objStatus.equals("S")) { //$NON-NLS-1$
					return null;
				}

				if (objStatus != null && objStatus.equals("I")) { //$NON-NLS-1$
					return Status_info;
				}

				if (objType.equals("Message")) { //$NON-NLS-1$
					return null;
				}

				return Status_warning;
			}

		});

		createTableViewerColumn(Messages.AbapGitView_column_package, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				return p.getPackage();
			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_msg_type, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;
				String textMsgType = p.getMsgType();

				if (textMsgType != null && textMsgType.equals("W")) { //$NON-NLS-1$
					return "Warning";
				}

				if (textMsgType != null && textMsgType.equals("E")) { //$NON-NLS-1$
					return "Error";
				}

				if (textMsgType != null && textMsgType.equals("S")) { //$NON-NLS-1$
					return null;
				}

				if (textMsgType != null && textMsgType.equals("I")) { //$NON-NLS-1$
					return "Info";
				}

				return textMsgType;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String textMsgType = p.getMsgType();
				Image Warning = Activator.getDefault().getImage(Activator.WARNING);
				Image Error = Activator.getDefault().getImage(Activator.ERROR);
//				com.sap.adt.tools.core.ui.Activator.getDefault().getImage(com.sap.adt.tools.core.ui.Activator.ERROR);
//				Image Success = Activator.getDefault().getImage(com.sap.adt.tools.core.ui.Activator.CHECKED);
				Image Info = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);

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

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

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
		return abapObj.getMsgParent();
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