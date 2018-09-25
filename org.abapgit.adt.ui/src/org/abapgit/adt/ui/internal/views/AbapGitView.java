package org.abapgit.adt.ui.internal.views;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;

import com.sap.adt.destinations.logon.AdtLogonServiceFactory;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitView extends ViewPart {

	public static final String ID = "org.abapgit.adt.ui.views.AbapGitView"; //$NON-NLS-1$

	private TableViewer viewer;
	private Action actionRefresh, actionWizard, actionCopy;
	private ISelection lastSelection;
	private IProject lastProject;

	private final ISelectionListener selectionListener = new ISelectionListener() {
		private boolean isUpdatingSelection = false;

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (this.isUpdatingSelection) {
				return;
			}

			try {
				this.isUpdatingSelection = true;
				if (AbapGitView.this == part) {
					return;
				}

				if (selection instanceof IStructuredSelection) {
					IStructuredSelection structSelection = (IStructuredSelection) selection;
					AbapGitView.this.lastSelection = structSelection;

					if (!checkIfPageIsVisible()) {
						return;
					}

					showLastSelectedElement();
				}
			} finally {
				this.isUpdatingSelection = false;
			}
		}
	};

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		ISelection selection = getSite().getPage().getSelection();
		this.lastSelection = selection;
	}

	private boolean checkIfPageIsVisible() {
		return getViewSite().getPage().isPartVisible(this);
	}

	private void showLastSelectedElement() {
		IProject currentProject = ProjectUtil.getActiveAdtCoreProject(this.lastSelection, null, null, null);
		if (currentProject != this.lastProject) {
			this.lastProject = currentProject;
			updateView(false);
		}

		this.lastSelection = null;

	}

	static class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
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
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_REDO).createImage();
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		setupViewer(parent);

		makeActions();
		hookContextMenu();
		contributeToActionBars();

		updateView(false);

		// add listener for selections
		getSite().getPage().addPostSelectionListener(this.selectionListener);
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
		createTableViewerColumn(Messages.AbapGitView_column_package, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getPackage();
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_url, 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getUrl();
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_branch, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getBranch();
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_user, 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getCreatedBy();
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_firstcommitat, 150).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {

				IRepository repo = (IRepository) element;

				Date date;
				try {
					date = new SimpleDateFormat("yyyyMMddHHmmss").parse(repo.getFirstCommit()); //$NON-NLS-1$
				} catch (ParseException e) {
					return repo.getFirstCommit();
				}

				String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date); //$NON-NLS-1$
				return formattedDate;
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		TableViewerColumn viewerColumn = new TableViewerColumn(this.viewer, SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				if (AbapGitView.this.viewer.getStructuredSelection().size() == 1) {
					Object firstElement = AbapGitView.this.viewer.getStructuredSelection().getFirstElement();
					if (firstElement instanceof IRepository) {
						manager.add(new UnlinkAction(AbapGitView.this.lastProject, (IRepository) firstElement));

						manager.add(new Separator());

						manager.add(AbapGitView.this.actionCopy);
					}
				}
			}
		});
		Menu menu = menuMgr.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
	}

	private void contributeToActionBars() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(this.actionRefresh);
		toolBarManager.add(this.actionWizard);
	}

	private void makeActions() {
		this.actionRefresh = new Action() {
			public void run() {
				updateView(true);
			}
		};
		this.actionRefresh.setText(Messages.AbapGitView_action_refresh);
		this.actionRefresh.setToolTipText(Messages.AbapGitView_action_refresh);
		this.actionRefresh
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/refresh.png")); //$NON-NLS-1$

		this.actionCopy = new Action() {
			public void run() {
				Table table = AbapGitView.this.viewer.getTable();
				copy(table);
			}
		};
		this.actionCopy.setText(Messages.AbapGitView_action_copy);
		this.actionCopy.setToolTipText(Messages.AbapGitView_action_copy);

		this.actionCopy.setAccelerator(SWT.ALT | 'C');

//		this.actionCopy
//				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/refresh.png")); //$NON-NLS-1$

		this.actionWizard = new Action() {
			public void run() {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(AbapGitView.this.lastProject).isOK()) {
					return;
				}
				WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
						new AbapGitWizard(AbapGitView.this.lastProject));

				wizardDialog.open();
				updateView(true);
			}
		};
		this.actionWizard.setText(Messages.AbapGitView_action_clone);
		this.actionWizard.setToolTipText(Messages.AbapGitView_action_clone);
		this.actionWizard.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	private List<IRepository> getRepositories(String destinationId) {
		List<IRepository> repos = new LinkedList<>();
		boolean[] isSupported = new boolean[1];
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitView_task_fetch_repos, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destinationId, monitor);
					if (repoService == null) {
						return;
					}
					isSupported[0] = true;
					repos.addAll(repoService.getRepositories(monitor).getRepositories());
				}
			});

		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
					Messages.AbapGitView_task_fetch_repos_error, e.getTargetException()), StatusManager.SHOW);
		} catch (InterruptedException e) {
		}
		if (isSupported[0]) {
			return repos;
		} else {
			return null;
		}
	}

	private static String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

	private void updateView(boolean ensureLogon) {
		if (this.lastProject == null) {
			setContentDescription(Messages.AbapGitView_no_abap_project);
			setControlsEnabled(false);
			this.viewer.setInput(null);
			return;
		}

		setControlsEnabled(true);
		String destinationId = getDestination(this.lastProject);
		if (!AdtLogonServiceFactory.createLogonService().isLoggedOn(destinationId)
				&& (!ensureLogon || !AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.lastProject).isOK())) {
			setContentDescription(NLS.bind(Messages.AbapGitView_repos_not_loaded, this.lastProject.getName()));
			this.viewer.setInput(null);
			return;
		}

		List<IRepository> repos = getRepositories(destinationId);

		if (repos != null) {
			setContentDescription(NLS.bind(Messages.AbapGitView_repos_in_project, this.lastProject.getName()));
			setControlsEnabled(true);
			this.viewer.setInput(repos);
		} else {
			setContentDescription(NLS.bind(Messages.AbapGitView_not_supported, this.lastProject.getName()));
			setControlsEnabled(false);
			this.viewer.setInput(null);
		}
	}

	private void setControlsEnabled(boolean enabled) {
		this.viewer.getControl().setEnabled(enabled);
		this.actionRefresh.setEnabled(enabled);
		this.actionWizard.setEnabled(enabled);
		this.actionCopy.setEnabled(enabled);
	}

	/**
	 * Copies the current selection to the clipboard.
	 *
	 * @param table
	 *            the data source
	 */
	protected void copy(Table table) {
		if (canCopy(table)) {
			final StringBuilder data = new StringBuilder();

			for (int row = 0; row < table.getSelectionCount(); row++) {
//					data.append(table.getSelection()[row]);

				for (int j = 0; j <= table.getColumnCount() - 1; j++) {
					data.append(table.getItem(row).getText(j) + " "); //$NON-NLS-1$
				}

			}
			final Clipboard clipboard = new Clipboard(table.getDisplay());
			clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
			clipboard.dispose();
		}
	}

	protected boolean canCopy(final Table table) {
		return table.getColumnCount() > 0 && table.getSelectionCount() > 0;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();

		if (this.lastSelection != null) {
			showLastSelectedElement();
		}
	}

	@Override
	public void dispose() {
		getSite().getPage().removePostSelectionListener(this.selectionListener);

		super.dispose();
	}

	private class UnlinkAction extends Action {

		private final IProject project;
		private final IRepository repository;

		public UnlinkAction(IProject project, IRepository repository) {
			this.project = project;
			this.repository = repository;
			setText(Messages.AbapGitView_context_unlink);
		}

		@Override
		public void run() {
			if (!MessageDialog.openConfirm(getSite().getShell(), Messages.AbapGitView_context_dialog_unlink_title,
					NLS.bind(Messages.AbapGitView_context_dialog_unlink_message, this.repository.getUrl(), this.repository.getPackage()))) {
				return;
			}
			try {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.project).isOK()) {
					return;
				}
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						RepositoryServiceFactory.createRepositoryService(getDestination(UnlinkAction.this.project), monitor)
								.unlinkRepository(UnlinkAction.this.repository.getKey(), monitor);
					}
				});
				updateView(true);
			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
						Messages.AbapGitView_context_unlink_error, e.getTargetException()), StatusManager.SHOW);
			} catch (InterruptedException e) {
			}
		}

	}

}
