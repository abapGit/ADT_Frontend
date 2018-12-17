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
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPull;
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
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
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
import com.sap.adt.destinations.model.IDestinationData;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;
import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class AbapGitView extends ViewPart {

	public static final String ID = "org.abapgit.adt.ui.views.AbapGitView"; //$NON-NLS-1$

	private TableViewer viewer;
	private Action actionRefresh, actionWizard, actionCopy, actionShowMyRepos;
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

	private Action actionPullWizard;

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
			updateView(false, false);
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

		updateView(false, false);

		this.viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				IRepository currRepository;
				currRepository = null;

				IStructuredSelection selection = (IStructuredSelection) event.getSelection();

				Object firstElement = selection.getFirstElement();
				if (firstElement instanceof IRepository) {
					currRepository = ((IRepository) firstElement);
				}

				if (currRepository != null) {

					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					String destinationId = getDestination(AbapGitView.this.lastProject);
					List<IAdtObjectReference> pkgRefs = packageServiceUI.find(destinationId, currRepository.getPackage(), null);
					IProject currProject = AbapGitView.this.lastProject;
					if (!pkgRefs.isEmpty()) {
						IAdtObjectReference gitPackageRef = pkgRefs.stream().findFirst().get();
						if (gitPackageRef != null) {
							AdtNavigationServiceFactory.createNavigationService().navigate(currProject, gitPackageRef, true);
						}
					}

//					IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
//					IFile file = workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
//					if (file == null) {
//						System.out.println("FileNotFoundException");
//					}
//					expandProjectExlorer(file);

				}

			}
		});

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
				String pattern = "((git@|https:\\/\\/)([\\w]+)(\\/|:))([\\w,\\-,\\_]+)\\@"; //$NON-NLS-1$
				return p.getUrl().replaceAll(pattern, "$1*****@"); //$NON-NLS-1$
//				return p.getUrl();
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

						//Check if repos are created by current user
						String destinationId = getDestination(AbapGitView.this.lastProject);
						IProject currProject = AdtProjectServiceFactory.createProjectService().findProject(destinationId);
						IAbapProject currAbapProject = currProject.getAdapter(IAbapProject.class);
						IDestinationData ProjectDestData = currAbapProject.getDestinationData();

						manager.add(AbapGitView.this.actionCopy);

						if ((((IRepository) firstElement).getCreatedBy().equalsIgnoreCase(ProjectDestData.getUser()))) {

							if (((IRepository) firstElement).getLink(IRepositoryService.RELATION_PULL) != null) {
								manager.removeAll();
								manager.add(AbapGitView.this.actionPullWizard);
								manager.add(AbapGitView.this.actionCopy);
								manager.add(new Separator());
								manager.add(new UnlinkAction(AbapGitView.this.lastProject, (IRepository) firstElement));
							}

						}



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
		toolBarManager.add(this.actionShowMyRepos);
		toolBarManager.add(this.actionWizard);
	}

	private void makeActions() {
		this.actionRefresh = new Action() {
			public void run() {
				updateView(true, false);
			}
		};
		this.actionRefresh.setText(Messages.AbapGitView_action_refresh);
		this.actionRefresh.setToolTipText(Messages.AbapGitView_action_refresh);
		this.actionRefresh
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/refresh.png")); //$NON-NLS-1$

		this.actionShowMyRepos = new Action() {
			public void run() {
				updateView(true, true);
			}
		};
		this.actionShowMyRepos.setText(Messages.AbapGitView_action_showMyRepos);
		this.actionShowMyRepos.setToolTipText(Messages.AbapGitView_action_showMyRepos);
		this.actionShowMyRepos
				.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_HOME_NAV));

		this.actionCopy = new Action() {
			public void run() {
				copy();
			}
		};
		this.actionCopy.setText(Messages.AbapGitView_action_copy);
		this.actionCopy.setToolTipText(Messages.AbapGitView_action_copy);

		this.actionCopy.setAccelerator(SWT.ALT | 'C');
		this.actionCopy.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		this.actionWizard = new Action() {
			public void run() {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(AbapGitView.this.lastProject).isOK()) {
					return;
				}
				WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
						new AbapGitWizard(AbapGitView.this.lastProject));

				wizardDialog.open();
				updateView(true, false);
			}
		};
		this.actionWizard.setText(Messages.AbapGitView_action_clone);
		this.actionWizard.setToolTipText(Messages.AbapGitView_action_clone);
		this.actionWizard.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

		this.actionPullWizard = new Action() {

			private IRepository selRepo;

			public void run() {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(AbapGitView.this.lastProject).isOK()) {
					return;
				}

				Object firstElement = AbapGitView.this.viewer.getStructuredSelection().getFirstElement();

				if (firstElement instanceof IRepository) {
					this.selRepo = ((IRepository) firstElement);
				}

				if (this.selRepo != null) {
					WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
							new AbapGitWizardPull(AbapGitView.this.lastProject, this.selRepo));
					wizardDialog.open();

				}

				updateView(true, false);
			}
		};
		this.actionPullWizard.setText(Messages.AbapGitView_context_pull);
//		this.actionPullWizard
//				.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
	}

	private List<IRepository> getRepositories(String destinationId, Boolean byCurrUser) {
		List<IRepository> repos = new LinkedList<>();
		List<IRepository> myrepos = new LinkedList<>();
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
					List<IRepository> templist = repoService.getRepositories(monitor).getRepositories();
					repos.addAll(templist);

					if (byCurrUser) {
						//Check if repos are created by current user
						IProject currProject = AdtProjectServiceFactory.createProjectService().findProject(destinationId);
						IAbapProject currAbapProject = currProject.getAdapter(IAbapProject.class);
						IDestinationData ProjectDestData = currAbapProject.getDestinationData();

						for (IRepository r : repos) {
							if (r.getCreatedBy().equalsIgnoreCase(ProjectDestData.getUser())) {
								myrepos.add(r);
							}
						}
					}

					if (!myrepos.isEmpty()) {
						repos.clear();
						repos.addAll(myrepos);
					}

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

	private void updateView(boolean ensureLogon, boolean byCurrUser) {
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

		List<IRepository> repos = getRepositories(destinationId, false);

		if (byCurrUser) {
			repos = getRepositories(destinationId, true);
		}

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

//	public void expandProjectExlorer(IFile file) {
//		if (file == null) {
//			return;
//		}
//		try {
//			IWorkbenchPage page = getSite().getWorkbenchWindow().getActivePage();
//			IViewPart view = page.showView(IPageLayout.ID_PROJECT_EXPLORER);
//			if (view instanceof ISetSelectionTarget) {
//
//				AbapGitView.this.viewer.getControl().getShell().getDisplay().asyncExec(new Runnable() {
//					@Override
//					public void run() {
//
//						ISelection selection = new StructuredSelection(file);
//
//						System.out.println(selection);
//						((ISetSelectionTarget) view).selectReveal(selection);
//					}
//				});
//
//			}
//		} catch (PartInitException e) {
//			System.out.println(e.getMessage());
//		}
//	}

	private void setControlsEnabled(boolean enabled) {
		this.viewer.getControl().setEnabled(enabled);
		this.actionRefresh.setEnabled(enabled);
		this.actionShowMyRepos.setEnabled(enabled);
		this.actionWizard.setEnabled(enabled);
		this.actionCopy.setEnabled(enabled);
	}

	/**
	 * Copies the current selection to the clipboard.
	 *
	 * @param table
	 *            the data source
	 */
	protected void copy() {

		IRepository currRepository;
		Object firstElement = AbapGitView.this.viewer.getStructuredSelection().getFirstElement();
		currRepository = null;

		if (firstElement instanceof IRepository) {
			currRepository = ((IRepository) firstElement);
		}

		final StringBuilder data = new StringBuilder();

			if (currRepository != null) {
			data.append(currRepository.getPackage() + " " + currRepository.getUrl() + " " + currRepository.getBranch() + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ currRepository.getCreatedBy());
			}

		final Clipboard clipboard = new Clipboard(AbapGitView.this.viewer.getControl().getDisplay());
			clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
			clipboard.dispose();

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
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));
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
				updateView(true, false);
			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
						Messages.AbapGitView_context_unlink_error, e.getTargetException()), StatusManager.SHOW);
			} catch (InterruptedException e) {
			}
		}

	}

}
