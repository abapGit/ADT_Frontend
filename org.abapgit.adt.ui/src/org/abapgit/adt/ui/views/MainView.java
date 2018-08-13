package org.abapgit.adt.ui.views;

import java.util.List;

import org.abapgit.adt.backend.AbapGitRequest;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.Repository;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.wizards.AbapGitWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.communication.resources.ResourceForbiddenException;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class MainView extends ViewPart {

	public static final String ID = "org.abapgit.adt.ui.views.MainView";

	private TableViewer viewer;
	// private Action actionPull, actionDelete;
	private Action actionSync, actionWizard;

	/**
	 * The constructor.
	 */

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
				return p.getUrl();
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

		createTableViewerColumn("First commit timestamp", 150).setLabelProvider(new ColumnLabelProvider() {
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
				// MainView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
		toolBarManager.add(actionSync);
		// toolBarManager.add(actionCreate);
		toolBarManager.add(actionWizard);

	}

	private void makeActions() {
		// this.actionPull = new Action() {
		// public void run() {
		// Repository repo = (Repository)
		// viewer.getStructuredSelection().getFirstElement();
		// repo.pull();
		// }
		// };
		// this.actionPull.setText("Pull");
		// this.actionPull.setToolTipText("Pull");
		// this.actionPull.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_TOOL_UP));
		//
		// this.actionDelete = new Action() {
		// public void run() {
		// showMessage("delete, todo");
		// }
		// };
		// this.actionDelete.setText("Delete");
		// this.actionDelete.setToolTipText("Delete");
		// this.actionDelete
		// .setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

		this.actionSync = new Action() {
			public void run() {
				fetchRepos();
				// viewer.setInput(Repository.list());
				// viewer.setInput(getRepoList());
				// viewer.setInput(getRepositories());
			}
		};
		this.actionSync.setText("Syncronize");
		this.actionSync.setToolTipText("Syncronize");
		this.actionSync
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

		// this.actionCreate = new Action() {
		// public void run() {
		// CreateDialog dialog = new CreateDialog(viewer.getControl().getShell());
		// dialog.create();
		//
		// if (dialog.open() == Window.OK) {
		//
		// Repository.create(dialog.getUrl(), dialog.getBranch(), dialog.getDevclass(),
		// dialog.getUser(), dialog.getPwd(), dialog.getTrname());
		//
		//// viewer.setInput(Repository.list());
		// viewer.setInput(getRepoList());
		//// viewer.setInput(getRepositories());
		//
		// }
		// }
		// };
		// this.actionCreate.setText("Create");
		// this.actionCreate.setToolTipText("Create");
		// this.actionCreate
		// .setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

		this.actionWizard = new Action() {
			public void run() {

				WizardDialog wizardDialog = new WizardDialog(viewer.getControl().getShell(), new AbapGitWizard());

				if (wizardDialog.open() == Window.OK) {
					fetchRepos();
				} else {
					// System.out.println("Cancel pressed");
					fetchRepos();
				}
			}
		};
		this.actionWizard.setText("Clone Repository");
		this.actionWizard.setToolTipText("Clone Repository");
		this.actionWizard
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	public List<IRepository> getRepositories() {
		IProgressMonitor monitor = null;

		// example
		IProject project = ProjectUtil.getActiveAdtCoreProject(null, null, null, IAbapProject.ABAP_PROJECT_NATURE);

		if (project == null) {
			return null;
		}

		IStatus logonStatus = AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project);
		if (!logonStatus.isOK()) {
			return null;
		}

		String destinationId = AdtProjectServiceFactory.createProjectService().getDestinationId(project);

		IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destinationId, monitor);
		List<IRepository> repos = null;
		try {
			repos = repoService.getRepositories(monitor).getRepositories();
		
		} catch (OutDatedClientException e) {
			// bring up popup about too old client, ...
			System.out.println(e.getMessage());
			return null;
		} catch (ResourceForbiddenException e) { // 403
			String subtype = e.getErrorInfo().getExceptionData().getSubtype();
			System.out.println(subtype);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		} catch (CommunicationException e) {
			// something else went wrong (network layer)
			System.out.println(e.getMessage());
		}
		return repos;

	}

	private void fetchRepos() {
		try {
			List<IRepository> repos = getRepositories();
			
			if (repos != null) {
				this.viewer.setInput(repos);
			}
		} catch (ContentHandlerException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
		this.fetchRepos();
	}

}
