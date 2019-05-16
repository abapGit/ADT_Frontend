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
import org.eclipse.swt.layout.GridData;
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


public class AbapGitDialogObjLog extends TitleAreaDialog {

	TreeViewer abapObjTable;
	private PatternFilter objLogFilter;
	public FilteredTree tree;
	public final List<IObject> abapObjects;
	private final IRepository repodata;
	private final Bundle jdtDocUserBundle;
	private final Image warningImage;
	private final Image errorImage;
	private final Image successImage;
	private final Image infoImage;

	public AbapGitDialogObjLog(Shell parentShell, List<IObject> pullObjects, IRepository repository) {
		super(parentShell);
		this.abapObjects = pullObjects;
		this.repodata = repository;

		//Icons
		this.jdtDocUserBundle = Platform.getBundle("org.eclipse.jdt.doc.user"); //$NON-NLS-1$
		URL imgUrlWarning = FileLocator.find(this.jdtDocUserBundle, new Path("images/org.eclipse.jdt.ui/obj16/warning_obj.png"), null); //$NON-NLS-1$
		URL imgUrlError = FileLocator.find(this.jdtDocUserBundle, new Path("images/org.eclipse.jdt.ui/obj16/error_obj.png"), null); //$NON-NLS-1$
		URL imgUrlSuccess = FileLocator.find(this.jdtDocUserBundle, new Path("images/org.eclipse.jdt.junit/obj16/testok.png"), null); //$NON-NLS-1$
		URL imgUrlInfo = FileLocator.find(this.jdtDocUserBundle, new Path("images/org.eclipse.jdt.ui/obj16/info_obj.png"), null); //$NON-NLS-1$

		this.warningImage = ImageDescriptor.createFromURL(imgUrlWarning).createImage();
		this.errorImage = ImageDescriptor.createFromURL(imgUrlError).createImage();
		this.successImage = ImageDescriptor.createFromURL(imgUrlSuccess).createImage();
		this.infoImage = ImageDescriptor.createFromURL(imgUrlInfo).createImage();

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

		GridLayout gl = new GridLayout();
		parent.setLayout(gl);

		ToolBar bar = new ToolBar(parent, SWT.FLAT);
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

		ToolItem expandAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		expandAllToolItem.setText(Messages.AbapGitDialogPageObjLog_filter_expand_all);
		expandAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_expand_all_tooltip);

		URL expandAllToolItemimgUrl = FileLocator.find(this.jdtDocUserBundle, new Path("images/org.eclipse.debug.ui/elcl16/expandall.png"), //$NON-NLS-1$
				null);
		expandAllToolItem.setImage(ImageDescriptor.createFromURL(expandAllToolItemimgUrl).createImage());
		expandAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.tree.getViewer().expandAll();
			}
		});

		ToolItem collapseAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		collapseAllToolItem.setText(Messages.AbapGitDialogPageObjLog_filter_collapse_all);
		collapseAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_collapse_all_tooltip);
		collapseAllToolItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
		collapseAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.tree.getViewer().collapseAll();
			}
		});

		ToolItem filterErrorToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterErrorToolItem.setText(Messages.AbapGitDialogPageObjLog_filter_error);
		filterErrorToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_error_tooltip);
		filterErrorToolItem.setImage(AbapGitDialogObjLog.this.errorImage);
		filterErrorToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern("Error"); //$NON-NLS-1$
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterWarningToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterWarningToolItem.setText(Messages.AbapGitDialogPageObjLog_filter_warning);
		filterWarningToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_warning_tooltip);
		filterWarningToolItem.setImage(AbapGitDialogObjLog.this.warningImage);
		filterWarningToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern("Warning"); //$NON-NLS-1$
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		ToolItem filterAllToolItem = new ToolItem(bar, SWT.PUSH | SWT.FLAT);
		filterAllToolItem.setText(Messages.AbapGitDialogPageObjLog_filter_all);
		filterAllToolItem.setToolTipText(Messages.AbapGitDialogPageObjLog_filter_all_tooltip);
		filterAllToolItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
		filterAllToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AbapGitDialogObjLog.this.objLogFilter.setPattern(null);
				AbapGitDialogObjLog.this.abapObjTable.refresh();
			}
		});

		this.tree = new FilteredTree(parent, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, this.objLogFilter, true);

		this.abapObjTable = this.tree.getViewer();
		this.abapObjTable.setContentProvider(new ObjectTreeContentProvider());
		Tree table = this.abapObjTable.getTree();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.tree);

		table.setFocus();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns();

		this.abapObjTable.setInput(this.abapObjects);

		return parent;
	}

	private void createColumns() {

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_name, 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IObject p = (IObject) element;

				int objCounter = p.countChildren();
				String result = p.getObjName();
				if (objCounter > 0) {
					result = result + " (" + p.countChildren() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
				}

				return result;
			}

//			@Override
//			public Image getImage(Object element) {
//
//				if (element instanceof IObject) {
//					IObject abapObj = (IObject) element;
//
//					if (abapObj.countChildren() != 0) {
//						abapObj = abapObj.listChildObjects().get(0);
//					}
//
//					IAdtObjectReference objRef = abapObj.getAdtObjRef();
//					IAdtObjectTypeInfoUi objectType = null;
//
//					if (objRef != null) {
//						objectType = AbapCoreUi.getObjectTypeRegistry().getObjectTypeByGlobalWorkbenchType(objRef.getType());
//					}
//
//					if (objectType != null) {
//						return objectType.getImage();
//					}
//					return AbapCoreUi.getSharedImages().getImage(com.sap.adt.tools.core.ui.ISharedImages.IMG_OBJECT_VISUAL_INTEGRATED);
//
//				}
//				return null;
//			}
		});

		createTableViewerColumn(Messages.AbapGitDialogImport_column_obj_status, 50).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				IObject p = (IObject) element;
				String objStatus = p.getObjStatus();

				Bundle bundle = AbapGitDialogObjLog.this.jdtDocUserBundle;
				URL imgUrl = null;

				if (objStatus != null && objStatus.equals("W")) { //$NON-NLS-1$
					return AbapGitDialogObjLog.this.warningImage;
				}

				if (objStatus != null && objStatus.equals("E")) { //$NON-NLS-1$
					return AbapGitDialogObjLog.this.errorImage;
				}

				if (objStatus != null && objStatus.equals("A")) { //$NON-NLS-1$
					imgUrl = FileLocator.find(bundle, new Path("images/org.eclipse.ltk.ui.refactoring/obj16/fatalerror_obj.png"), null); //$NON-NLS-1$
				}

				if (objStatus != null && objStatus.equals("S")) { //$NON-NLS-1$
					return AbapGitDialogObjLog.this.successImage;
				}

				if (objStatus != null && objStatus.equals("I")) { //$NON-NLS-1$
					return AbapGitDialogObjLog.this.infoImage;
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