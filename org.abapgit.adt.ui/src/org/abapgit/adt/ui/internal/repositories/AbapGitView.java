package org.abapgit.adt.ui.internal.repositories;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.dialogs.AbapGitDialogObjLog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPull;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.osgi.framework.Bundle;

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
	private Action actionRefresh, actionWizard, actionCopy, actionOpen, actionShowMyRepos, actionPullWizard;
	private ISelection lastSelection;
	private IProject lastProject;
	private ViewerFilter searchFilter;

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
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		setupViewer(parent);

		makeActions();
		hookContextMenu();
		contributeToActionBars();
		updateView(false);

		AbapGitView.this.searchFilter = new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				String destinationId = getDestination(AbapGitView.this.lastProject);
				IProject currProject = AdtProjectServiceFactory.createProjectService().findProject(destinationId);

				IAbapProject currAbapProject = currProject.getAdapter(IAbapProject.class);
				IDestinationData ProjectDestData = currAbapProject.getDestinationData();

				String searchMyRepos = ProjectDestData.getUser();
				if (searchMyRepos.isEmpty()) {
					return true;
				}

				if (element instanceof IRepository) {
					IRepository repo = (IRepository) element;

					return repo.getCreatedBy().equalsIgnoreCase(ProjectDestData.getUser());
				}
				return false;
			}
		};

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
				IRepository repo = (IRepository) element;

				String lastChangedBy = repo.getDeserializedEmail();
				if (lastChangedBy == null || lastChangedBy.isEmpty()) {
					lastChangedBy = repo.getCreatedEmail();
				}

				if (lastChangedBy == null || lastChangedBy.isEmpty()) {
					lastChangedBy = repo.getCreatedBy();
				}

				return lastChangedBy;
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_firstcommitat, 150).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {

				IRepository repo = (IRepository) element;
				String lastChangedAt = repo.getDeserializedAt();
				if (lastChangedAt == null || lastChangedAt.equals("0.0")) { //$NON-NLS-1$
					lastChangedAt = repo.getFirstCommit();
				}

				Date date;
				try {
					date = new SimpleDateFormat("yyyyMMddHHmmss").parse(lastChangedAt); //$NON-NLS-1$
				} catch (ParseException e) {
					return lastChangedAt;
				}

				String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date); //$NON-NLS-1$
				return formattedDate;
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_repo_status, 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				String statusText = p.getStatusText();
				if (statusText == null) {
					return ""; //$NON-NLS-1$
				}

				return statusText;
			}

			@Override
			public Image getImage(Object element) {
				String statusFlag = ((IRepository) element).getStatusFlag();
				if (statusFlag != null) {
					switch (statusFlag) {
					case "W": //$NON-NLS-1$
						return ResourceLocator.imageDescriptorFromBundle("org.eclipse.jdt.ui", "icons/full/obj16/warning_obj.png").get() //$NON-NLS-1$//$NON-NLS-2$
								.createImage();
					case "E": //$NON-NLS-1$
						return ResourceLocator.imageDescriptorFromBundle("org.eclipse.jdt.ui", "icons/full/obj16/error_obj.png").get() //$NON-NLS-1$//$NON-NLS-2$
								.createImage();
					case "A": //$NON-NLS-1$
						return ResourceLocator.imageDescriptorFromBundle("org.eclipse.ui", "icons/full/elcl16/stop.png").get() //$NON-NLS-1$//$NON-NLS-2$
								.createImage();
					case "S": //$NON-NLS-1$
						return ResourceLocator.imageDescriptorFromBundle("org.eclipse.ui", "icons/full/eview16/tasks_tsk.png").get() //$NON-NLS-1$//$NON-NLS-2$
								.createImage();
					case "R": //$NON-NLS-1$
						return ResourceLocator.imageDescriptorFromBundle("org.eclipse.ui", "icons/full/elcl16/up_nav.png").get() //$NON-NLS-1$//$NON-NLS-2$
								.createImage();
					}
				}
				return null;
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
						IRepository repository = (IRepository) firstElement;
						//link action
						manager.add(AbapGitView.this.actionWizard);
						//separator
						manager.add(new Separator());
						//pull action
						if (repository.getPullLink(IRepositoryService.RELATION_PULL) != null) {
							manager.add(AbapGitView.this.actionPullWizard);
						}
						//stage action
						if (repository.getStageLink(IRepositoryService.RELATION_STAGE) != null) {
							manager.add(new OpenStagingViewAction(AbapGitView.this.lastProject, repository));
						}
						//object log action
						if (repository.getLogLink(IRepositoryService.RELATION_LOG) != null) {
							manager.add(new GetObjLogAction(AbapGitView.this.lastProject, repository));
						}
						//separator
						manager.add(new Separator());
						//open package action
						manager.add(AbapGitView.this.actionOpen);
						//copy to clip-board action
						manager.add(AbapGitView.this.actionCopy);
						//unlink action
						if (repository.getPullLink(IRepositoryService.RELATION_PULL) != null) {
							manager.add(new Separator());
							manager.add(new UnlinkAction(AbapGitView.this.lastProject, repository));
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
				updateView(true);
			}
		};
		this.actionRefresh.setText(Messages.AbapGitView_action_refresh);
		this.actionRefresh.setToolTipText(Messages.AbapGitView_action_refresh);
		this.actionRefresh
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/refresh.png")); //$NON-NLS-1$

		this.actionShowMyRepos = new Action(null, Action.AS_CHECK_BOX) {
			public void run() {
				//-> Add filter if action is selected
				AbapGitView.this.viewer.addFilter(AbapGitView.this.searchFilter);

				//-> Remove filter if action is deselected
				if (!AbapGitView.this.actionShowMyRepos.isChecked()) {
					AbapGitView.this.viewer.resetFilters();
				}
			}

		};
		Bundle actionShowMyReposBundle = Platform.getBundle("com.sap.adt.projectexplorer.ui"); //$NON-NLS-1$
		URL actionShowMyReposImgUrl = FileLocator.find(actionShowMyReposBundle, new Path("icons/obj/package_obj_user.png"), //$NON-NLS-1$
				null);
		this.actionShowMyRepos.setText(Messages.AbapGitView_action_showMyRepos);
		this.actionShowMyRepos.setToolTipText(Messages.AbapGitView_action_showMyRepos);
		this.actionShowMyRepos.setImageDescriptor(ImageDescriptor.createFromURL(actionShowMyReposImgUrl));

		this.actionCopy = new Action() {
			public void run() {
				copy();
			}
		};
		this.actionCopy.setText(Messages.AbapGitView_action_copy);
		this.actionCopy.setToolTipText(Messages.AbapGitView_action_copy);

		this.actionCopy.setAccelerator(SWT.ALT | 'C');
		this.actionCopy.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));

		this.actionOpen = new Action() {
			public void run() {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(AbapGitView.this.lastProject).isOK()) {
					return;
				}

				IRepository currRepository = null;
				Object firstElement = AbapGitView.this.viewer.getStructuredSelection().getFirstElement();

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
				}

			}
		};
		this.actionOpen.setText(Messages.AbapGitView_action_open);
		this.actionOpen.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));

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

					Object viewerInput = AbapGitView.this.viewer.getInput();
					List<IRepository> allRepositories = new ArrayList<IRepository>();
					if (viewerInput != null && viewerInput instanceof List<?>) {
						@SuppressWarnings("unchecked")
						List<IRepository> viewerRepositories = (List<IRepository>) viewerInput;
						allRepositories.addAll(viewerRepositories);
					}

					WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
							new AbapGitWizardPull(AbapGitView.this.lastProject, this.selRepo, allRepositories));

					// customized MessageDialog with configured buttons
					MessageDialog dialog = new MessageDialog(getSite().getShell(), Messages.AbapGitView_ConfDialog_title, null,
							Messages.AbapGitView_ConfDialog_MsgText,
							MessageDialog.CONFIRM,
							new String[] { Messages.AbapGitView_ConfDialog_Btn_confirm, Messages.AbapGitView_ConfDialog_Btn_cancel }, 0);
					int confirmResult = dialog.open();
					if (confirmResult == 0) {
						wizardDialog.open();
					}

				}

				updateView(true);
			}
		};
		this.actionPullWizard.setText(Messages.AbapGitView_context_pull);
		this.actionPullWizard
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/view/abapgit.png")); //$NON-NLS-1$
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

		List<IRepository> repos = getRepositories(destinationId, false);

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
		this.actionShowMyRepos.setEnabled(enabled);
		this.actionWizard.setEnabled(enabled);
		this.actionCopy.setEnabled(enabled);
		this.actionOpen.setEnabled(enabled);
//		this.actionObjectLog.setEnabled(enabled);
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
			//-> 1902 check for an empty status column
			String repoStatusString = currRepository.getStatusText() == null ? "" : currRepository.getStatusText(); //$NON-NLS-1$

			data.append(currRepository.getPackage() + " " + currRepository.getUrl() + " " + currRepository.getBranch() + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ currRepository.getCreatedBy() + " " + repoStatusString); //$NON-NLS-1$
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
				updateView(true);
			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
						Messages.AbapGitView_context_unlink_error, e.getTargetException()), StatusManager.SHOW);
			} catch (InterruptedException e) {
			}
		}

	}

	private class GetObjLogAction extends Action {

		private final IProject project;
		private final IRepository repository;

		public GetObjLogAction(IProject project, IRepository repository) {
			this.project = project;
			this.repository = repository;
			setText(Messages.AbapGitView_context_object_log);
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
		}

		@Override
		public void run() {
			List<IObject> objectLogItems = new LinkedList<>();

			try {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.project).isOK()) {
					return;
				}
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

						List<IObject> abapObjects = RepositoryServiceFactory
								.createRepositoryService(getDestination(GetObjLogAction.this.project), monitor)
								.getRepoObjLog(monitor, GetObjLogAction.this.repository).getObjects();

						objectLogItems.addAll(abapObjects);

					}
				});

			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(
						new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, "Check status error", e.getTargetException()), //$NON-NLS-1$
						StatusManager.SHOW);
			} catch (InterruptedException e) {
			}

			TitleAreaDialog objLogDialog = new AbapGitDialogObjLog(AbapGitView.this.viewer.getControl().getShell(), objectLogItems,
					GetObjLogAction.this.repository);
			objLogDialog.open();


		}

	}

	private static class OpenStagingViewAction extends Action {

		private final IProject project;
		private final IRepository repository;

		public OpenStagingViewAction(IProject project, IRepository repository) {
			this.project = project;
			this.repository = repository;
			setText(Messages.AbapGitView_context_staging);
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/view/abapgit_staging.png")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			try {
				IAbapGitStagingView stagingView = ((IAbapGitStagingView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(AbapGitStagingView.VIEW_ID));
				stagingView.openStagingView(this.repository, this.project);
			} catch (PartInitException e) {
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}

	}

}
