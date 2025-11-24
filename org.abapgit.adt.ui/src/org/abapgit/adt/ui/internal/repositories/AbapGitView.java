package org.abapgit.adt.ui.internal.repositories;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.dialogs.AbapGitDialogObjLog;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.actions.OpenRepositoryAction;
import org.abapgit.adt.ui.internal.repositories.actions.SwitchbranchAction;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.GitCredentialsService;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPull;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.Bundle;

import com.sap.adt.destinations.model.IDestinationData;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;
import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;
import com.sap.adt.tools.core.ui.userinfo.AdtUserInfoFormatterFactory;
import com.sap.adt.tools.core.ui.userinfo.IAdtUserInfoFormatter;

public class AbapGitView extends ViewPart implements IAbapGitRepositoriesView {

	public static final String ID = "org.abapgit.adt.ui.views.AbapGitView"; //$NON-NLS-1$

	protected TableViewer viewer;
	protected Action actionRefresh, actionWizard, actionCopy, actionOpen, actionShowMyRepos, actionPullWizard, actionOpenRepository,
			actionSwitchBranch;
	private ISelection lastSelection;
	protected IProject lastProject;
	private ViewerFilter searchFilter;
	private TablePatternFilter tableFilter;
	protected Text searchText;
	protected IAbapGitService abapGitService;
	protected IRepositoryService repoService;

	//key binding for copy text
	private static final KeyStroke KEY_STROKE_COPY = KeyStroke.getInstance(SWT.MOD1, 'C' | 'c');

	public void refresh() {
		updateView(true);
	}

	private final ISelectionListener selectionListener = new ISelectionListener() {
		private boolean isUpdatingSelection = false;
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			//check whether another refresh job is running
			if (this.isUpdatingSelection) {
				return;
			}
			try {
				this.isUpdatingSelection = true;
				//check whether the selection change is on the AbapGit Repositories view
				if (AbapGitView.this == part) {
					return;
				}
				//update selection
				AbapGitView.this.lastSelection = selection;
				//check whether AbapGit Repositories view is visible in the workbench
				if (!checkIfPageIsVisible()) {
					return;
				}
				//refresh view
				showLastSelectedElement();
			} finally {
				this.isUpdatingSelection = false;
			}
		}
	};

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

// Create a new abapGit service when AbapGit Repositories View is opened.
		if (this.abapGitService == null) {
			this.abapGitService = AbapGitUIServiceFactory.createAbapGitService();
		}

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
			this.repoService = null;  //reset the repository Service if project is changed
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
		final Composite composite = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		composite.setLayoutData(gd);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		setupFilterBox(composite);
		setupViewer(composite);

		//Add filter to the viewer
		this.tableFilter = new TablePatternFilter();
		this.viewer.addFilter(this.tableFilter);

		makeActions();
		hookContextMenu();
		contributeToActionBars();
		updateView(false);


		AbapGitView.this.searchFilter = new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				String destinationId = AbapGitView.this.abapGitService.getDestination(AbapGitView.this.lastProject);
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

	private void setupFilterBox(Composite parent) {
		//Create Filter Text Box
		this.searchText = new Text(parent, SWT.SEARCH | SWT.CANCEL);
		this.searchText.setMessage(Messages.AbapGitView_Type_Filter_Text);
		this.searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		//Add Listener to the Filter Text Box
		this.searchText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				AbapGitView.this.tableFilter.setSearchText(AbapGitView.this.searchText.getText());
				AbapGitView.this.viewer.refresh();
			}
		});

	}

	private void setupViewer(Composite parent) {
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		this.viewer.getControl().setLayoutData(parent.getLayoutData());
		Table table = this.viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createColumns();
		this.viewer.setContentProvider(ArrayContentProvider.getInstance());
		addKeyListeners();
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

		createTableViewerColumn(Messages.AbapGitView_column_branch, 180).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getBranchName();
			}
		});

		//Create the folder logic column, but set the width to 0
		//The width will be updated once the repositories are loaded
		//TODO: Set the width to 180, once the 2302 back-ends are updated by all
		createTableViewerColumn(Messages.AbapGitView_column_folder_logic, 0).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getFolderLogic();
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_user, 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository repo = (IRepository) element;

				String lastChangedBy = getLastChangedBy(repo);
				return lastChangedBy;
			}
		});

		createTableViewerColumn(Messages.AbapGitView_column_firstcommitat, 150).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository repo = (IRepository) element;
				String lastChangedAt = repo.getDeserializedAt();
				if (lastChangedAt == null || lastChangedAt.equals("0.0")) { //$NON-NLS-1$
					lastChangedAt = repo.getCreatedAt();
				}
				return getFormattedDate(lastChangedAt);
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
				String statusFlag = ((IRepository) element).getStatus();
				if (statusFlag != null) {
					switch (statusFlag) {
					case "W": //$NON-NLS-1$
						return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
					case "E": //$NON-NLS-1$
						return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
					case "A": //$NON-NLS-1$
						return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_STOP);
					case "S": //$NON-NLS-1$
						return PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJS_TASK_TSK);
					case "R": //$NON-NLS-1$
						return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_UP);
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
						//pull action
						if (AbapGitView.this.repoService.getURIFromAtomLink(repository, IRepositoryService.RELATION_PULL) != null) {
							manager.add(AbapGitView.this.actionPullWizard);
						}
						//stage action
						if (AbapGitView.this.repoService.getURIFromAtomLink(repository, IRepositoryService.RELATION_STAGE) != null) {
							manager.add(new OpenStagingViewAction(AbapGitView.this.lastProject, repository));
						}
						//object log action
						if (AbapGitView.this.repoService.getURIFromAtomLink(repository, IRepositoryService.RELATION_LOG) != null) {
							manager.add(new GetObjLogAction(AbapGitView.this.lastProject, repository));
						}
						//separator
						manager.add(new Separator());
						//open package action
						manager.add(AbapGitView.this.actionOpen);
						//open repository in external browser
						if (repository.getUrl() != null) {
							manager.add(AbapGitView.this.actionOpenRepository);
						}
						//separator
						manager.add(new Separator());
						//switch Branch Action
						manager.add(AbapGitView.this.actionSwitchBranch);
						//separator
						manager.add(new Separator());
						//copy to clip-board action
						manager.add(AbapGitView.this.actionCopy);
						//unlink action
						if (AbapGitView.this.repoService.getURIFromAtomLink(repository, IRepositoryService.RELATION_PULL) != null) {
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

	private void addKeyListeners() {
		this.viewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				//add key listener for text copy
				if (e.keyCode == KEY_STROKE_COPY.getNaturalKey() && e.stateMask == KEY_STROKE_COPY.getModifierKeys()) {
					copy();
				}
			}
		});
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
		this.actionCopy.setActionDefinitionId(ActionFactory.COPY.getCommandId());
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
					String destinationId = AbapGitView.this.abapGitService.getDestination(AbapGitView.this.lastProject);
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
				AbapGitWizard abapGitWizard = new AbapGitWizard(AbapGitView.this.lastProject);
				WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
						abapGitWizard);
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
							new AbapGitWizardPull(AbapGitView.this.lastProject, this.selRepo, allRepositories, AbapGitView.this));
					wizardDialog.open();

				}
			}
		};
		this.actionPullWizard.setText(Messages.AbapGitView_context_pull);
		this.actionPullWizard
				.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/view/abapgit.png")); //$NON-NLS-1$

		//Open repository in external browser
		this.actionOpenRepository = new OpenRepositoryAction(this);

		//Switch Branches
		this.actionSwitchBranch = new SwitchbranchAction(this);
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
					if (AbapGitView.this.repoService == null) {
						AbapGitView.this.repoService = RepositoryServiceFactory.createRepositoryService(destinationId, monitor);
					}
					if (AbapGitView.this.repoService == null) {
						return;
					}
					isSupported[0] = true;
					List<IRepository> templist = AbapGitView.this.repoService.getRepositories(monitor).getRepositories();
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

	private void updateView(boolean ensureLogon) {
		if (this.lastProject == null) {
			setContentDescription(Messages.AbapGitView_no_abap_project);
			setControlsEnabled(false);
			this.viewer.setInput(null);
			return;
		}

		setControlsEnabled(true);
		String destinationId = this.abapGitService.getDestination(this.lastProject);
		if (!this.abapGitService.isLoggedOn(this.lastProject) && (!ensureLogon || !this.abapGitService.ensureLoggedOn(this.lastProject))) {
			setContentDescription(NLS.bind(Messages.AbapGitView_repos_not_loaded, this.lastProject.getName()));
			this.viewer.setInput(null);
			return;
		}

		List<IRepository> repos = getRepositories(destinationId, false);

		if (repos != null) {
			setContentDescription(NLS.bind(Messages.AbapGitView_repos_in_project, this.lastProject.getName()));
			setControlsEnabled(true);

			//TODO: Remove this check once the 2302 back-end is updated
			if(this.abapGitService.isFolderLogicAvailable(repos)) {
				setFolderLogicColumnVisible(true);
			} else {
				setFolderLogicColumnVisible(false);
			}

			this.viewer.setInput(repos);
		} else {
			setContentDescription(NLS.bind(Messages.AbapGitView_not_supported, this.lastProject.getName()));
			setControlsEnabled(false);
			this.viewer.setInput(null);
		}

	}

	/**
	 * Toggle whether the folder logic column should be visible or not
	 *
	 * @param visible
	 */
	private void setFolderLogicColumnVisible(boolean visible) {
		TableColumn[] columns = this.viewer.getTable().getColumns();

		for (TableColumn column : columns) {
			if (column.getText().equals(Messages.AbapGitView_column_folder_logic)) {
				column.setWidth(visible ? 150 : 0);
			}
		}
	}

	private void setControlsEnabled(boolean enabled) {
		this.viewer.getControl().setEnabled(enabled);
		this.actionRefresh.setEnabled(enabled);
		this.actionShowMyRepos.setEnabled(enabled);
		this.actionWizard.setEnabled(enabled);
		this.actionCopy.setEnabled(enabled);
		this.actionOpen.setEnabled(enabled);
	}

	/**
	 * Copies the current selection to the clipboard.
	 *
	 * @param table
	 *            the data source
	 */
	protected void copy() {
		Object selection = AbapGitView.this.viewer.getStructuredSelection().getFirstElement();
		if (selection != null && selection instanceof IRepository) {
			IRepository currRepository = ((IRepository) selection);

			String repoStatusString = currRepository.getStatusText() == null ? "" : currRepository.getStatusText(); //$NON-NLS-1$
			String repoUserString = currRepository.getCreatedEmail() == null ? currRepository.getCreatedBy()
					: currRepository.getCreatedEmail();

			String lastChangedAt = currRepository.getDeserializedAt();
			if (lastChangedAt == null || lastChangedAt.equals("0.0")) { //$NON-NLS-1$
				lastChangedAt = currRepository.getCreatedAt();
			}
			String repoLastChangedString = getFormattedDate(lastChangedAt);

			final StringBuilder data = new StringBuilder();
			data.append(currRepository.getPackage() + " " + currRepository.getUrl() + " " + currRepository.getBranchName() + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ repoUserString + " " + repoLastChangedString + " " + repoStatusString); //$NON-NLS-1$ //$NON-NLS-2$

			final Clipboard clipboard = new Clipboard(AbapGitView.this.viewer.getControl().getDisplay());
			clipboard.setContents(new String[] { data.toString() }, new TextTransfer[] { TextTransfer.getInstance() });
			clipboard.dispose();
		}
	}

	private String getFormattedDate(String lastChangedAt) {
		Date date;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(lastChangedAt); //$NON-NLS-1$
		} catch (ParseException e) {
			return lastChangedAt;
		}

		String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date); //$NON-NLS-1$
		return formattedDate;
	}

	private String getLastChangedBy(IRepository repo) {
		String lastChangedBy = repo.getDeserializedEmail();
		if (lastChangedBy == null || lastChangedBy.isEmpty()) {
			lastChangedBy = repo.getCreatedEmail();
		}

		if (lastChangedBy == null || lastChangedBy.isEmpty()) {
			lastChangedBy = repo.getCreatedBy();

			// format the user name to display the full name along with the user id
			String destinationId = AbapGitView.this.abapGitService.getDestination(AbapGitView.this.lastProject);
			lastChangedBy = AdtUserInfoFormatterFactory.createAdtUserInfoFormatter().format(new NullProgressMonitor(), destinationId,
					lastChangedBy, IAdtUserInfoFormatter.FormatStyle.ID_AND_USERNAME_IN_BRACKETS);
		}
		return lastChangedBy;
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

	//Pattern Filter for Table Viewer
	private class TablePatternFilter extends PatternFilter {
		private String searchString;

		public void setSearchText(String s) {
			this.searchString = "*" + s.trim().toLowerCase(Locale.ENGLISH) + "*"; //$NON-NLS-1$ //$NON-NLS-2$
			setPattern(this.searchString);
		}

		@Override
		protected boolean isLeafMatch(Viewer viewer, Object element) {
			if (element instanceof IRepository) {
				IRepository repo = (IRepository) element;

				//match search string with url
				if (wordMatches(repo.getUrl().toLowerCase(Locale.ENGLISH))) {
					return true;
				}

				String lastChangedBy = getLastChangedBy(repo);
				//match search string with user
				if (wordMatches(lastChangedBy.toLowerCase(Locale.ENGLISH))) {
					return true;
				}

				//match search string with package name
				if (wordMatches(repo.getPackage().toLowerCase(Locale.ENGLISH))) {
					return true;
				}
			}
			return false;
		}

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
						RepositoryServiceFactory
								.createRepositoryService(AbapGitView.this.abapGitService.getDestination(UnlinkAction.this.project),
										monitor)
								.unlinkRepository(UnlinkAction.this.repository.getKey(), monitor);
					}
				});

				//Delete credentials from Secure storage
				GitCredentialsService.deleteCredentialsFromSecureStorage(this.repository.getUrl());
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
			List<IAbapObject> objectLogItems = new LinkedList<>();

			try {
				if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.project).isOK()) {
					return;
				}
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

						IAbapObjects abapObjects = RepositoryServiceFactory
								.createRepositoryService(
										AbapGitView.this.abapGitService.getDestination(GetObjLogAction.this.project), monitor)
								.getRepoObjLog(monitor, GetObjLogAction.this.repository);

						objectLogItems.addAll(
								AbapGitUIServiceFactory.createAbapGitObjLogService().renderObjLogInput(abapObjects).getAbapObjects());

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

	@Override
	public IRepository getRepositorySelection() {
		Object selObject = this.viewer.getStructuredSelection().getFirstElement();
		if (selObject != null && selObject instanceof IRepository) {
			return (IRepository) selObject;
		}
		return null;
	}

	@Override
	public IProject getProject() {
		return this.lastProject;
	}

}