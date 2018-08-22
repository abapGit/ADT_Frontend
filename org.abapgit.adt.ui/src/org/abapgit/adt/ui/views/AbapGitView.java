package org.abapgit.adt.ui.views;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.wizards.AbapGitWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitView extends ViewPart {

	public static final String ID = "org.abapgit.adt.ui.views.AbapGitView";

	private TableViewer viewer;
	private Action actionRefresh, actionWizard;
	private ISelection lastSelection;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		ISelection selection = getSite().getPage().getSelection();
		this.lastSelection = selection;
	}

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

	private IProject lastProject;

	private boolean checkIfPageIsVisible() {
		return getViewSite().getPage().isPartVisible(this);
	}

	private void showLastSelectedElement() {
		IProject currentProject = ProjectUtil.getActiveAdtCoreProject(this.lastSelection, null, null, null);
		if (currentProject != this.lastProject) {
			this.lastProject = currentProject;
			updateView();
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
			return AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_TOOL_REDO).createImage();
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
		// add listener for selections
		getSite().getPage().addPostSelectionListener(this.selectionListener);
	}

	private void setupViewer(Composite parent) {
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.viewer.getControl().setEnabled(false);
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
				IRepository p = (IRepository) element;
				return p.getPackage();
			}
		});

		createTableViewerColumn("URL", 400).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getUrl();
			}
		});

		createTableViewerColumn("Branch", 200).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getBranch();
			}
		});

		createTableViewerColumn("User", 100).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IRepository p = (IRepository) element;
				return p.getCreatedBy();
			}
		});

		createTableViewerColumn("First Commit At", 150).setLabelProvider(new ColumnLabelProvider() {
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
				updateView();
			}
		};
		this.actionRefresh.setText("Refresh");
		this.actionRefresh.setToolTipText("Refresh");
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		URL url = FileLocator.find(bundle, new Path("icons/etool/refresh.png"), null); //$NON-NLS-1$
		ImageDescriptor imageDescriptorRefresh = ImageDescriptor.createFromURL(url);
		this.actionRefresh.setImageDescriptor(imageDescriptorRefresh);
		this.actionRefresh.setEnabled(false);

		this.actionWizard = new Action() {
			public void run() {

				WizardDialog wizardDialog = new WizardDialog(AbapGitView.this.viewer.getControl().getShell(),
						new AbapGitWizard(AbapGitView.this.lastProject));

				wizardDialog.open();
				updateView();
			}
		};
		this.actionWizard.setText("Clone abapGit Repository");
		this.actionWizard.setToolTipText("Clone abapGit Repository");
		this.actionWizard
				.setImageDescriptor(AbapGitUIPlugin.getDefault().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		this.actionWizard.setEnabled(false);
	}

	private List<IRepository> getRepositories() {
		if (this.lastProject == null) {
			return null;
		}

		IStatus logonStatus = AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.lastProject);
		if (!logonStatus.isOK()) {
			return null;
		}

		String destinationId = getDestination(this.lastProject);
		List<IRepository> repos = new LinkedList<>();
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Fetching abapGit Repositories", IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destinationId,
							monitor);
					if (repoService == null) {
						return;
					}
					repos.addAll(repoService.getRepositories(monitor).getRepositories());
				}
			});

		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
					"Error fetching abapGit Repositories", e.getTargetException()), StatusManager.SHOW);
		} catch (InterruptedException e) {
		}
		return repos;
	}

	private static String getDestination(IProject project) {
		return AdtProjectServiceFactory.createProjectService().getDestinationId(project);
	}

	private void updateView() {
		List<IRepository> repos = getRepositories();

		if (repos != null && !repos.isEmpty()) {
			this.viewer.getControl().setEnabled(true);
			this.actionRefresh.setEnabled(true);
			this.actionWizard.setEnabled(true);
			this.viewer.setInput(repos);
		} else {
			this.viewer.getControl().setEnabled(false);
			this.actionRefresh.setEnabled(false);
			this.actionWizard.setEnabled(true);
			this.viewer.setInput(null);
		}
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
			setText("Unlink");
		}

		@Override
		public void run() {
			if (!MessageDialog.openConfirm(getSite().getShell(), "Unlink abapGit Repository",
					MessageFormat.format("Do you really want to unlink the abapGit Repository {0} from Package {1}?",
							this.repository.getUrl(), this.repository.getPackage()))) {
				return;
			}
			try {
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						RepositoryServiceFactory.createRepositoryService(getDestination(UnlinkAction.this.project), monitor)
								.unlinkRepository(UnlinkAction.this.repository.getKey(), monitor);
					}
				});
				updateView();
			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID,
						"Error unlinking abapGit Repository", e.getTargetException()), StatusManager.SHOW);
			} catch (InterruptedException e) {
			}
		}

	}

}
