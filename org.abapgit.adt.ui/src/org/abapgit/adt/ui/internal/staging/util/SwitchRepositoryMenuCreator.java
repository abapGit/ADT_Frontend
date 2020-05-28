package org.abapgit.adt.ui.internal.staging.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.osgi.framework.Bundle;

import com.sap.adt.project.AdtCoreProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

/**
 * Repository Switch Menu Creator for AbapGit Staging view
 */
public class SwitchRepositoryMenuCreator implements IMenuCreator {
	private final IAbapGitStagingView view;
	private final IAbapGitStagingService stagingUtil;
	private Menu menu;
	private ImageDescriptor myRepositoryImageDescriptor;

	private IAction menuItem;
	private ActionContributionItem item;

	//testing
	private IRepositoryService repositoryService;

	public SwitchRepositoryMenuCreator(IAbapGitStagingView view, IAbapGitStagingService stagingUtil) {
		this.view = view;
		this.stagingUtil = stagingUtil;
	}

	@Override
	public Menu getMenu(Control ctrl) {
		//dispose menu if already created
		if (this.menu != null) {
			this.menu.dispose();
			this.menu = null;
		}
		//create new menu for the control
		this.menu = new Menu(ctrl);
		//Create the first level menu with the available project in the workspace
		//-----------------------------------------------------------------------
		//get the list of accessible projects - existing and open projects
		IProject[] projects = AdtCoreProjectServiceFactory.createCoreProjectService().getAvailableAdtCoreProjects();
		for (IProject project : projects) {
			//we will show the project in the menu only if it is logged on
			if (this.stagingUtil.isLoggedOn(project)) {
				//check if abapgit is supported by the project
				if (!this.stagingUtil.isAbapGitSupported(project)) {
					//do not show projects which does not support abapgit
					continue;
				}
				//create a drop down menu item for the project
				this.menuItem = new Action(project.getName(), IAction.AS_DROP_DOWN_MENU) {
				};
				//add the sub menu creator for the project, which will create the menu
				//with the list of available repositories
				this.menuItem.setMenuCreator(new RespositoryListMenuCreator(project));
				this.item = new ActionContributionItem(this.menuItem);
				this.item.fill(this.menu, -1);
			}
		}
		//if no logged on and supported systems are available, add a dummy menu contribution
		if (this.menu.getItemCount() == 0) {
			this.menuItem = new Action(Messages.AbapGitStaging_switch_repository_no_supported_systems_xmg) {
			};
			//disable the menu
			this.menuItem.setEnabled(false);
			this.item = new ActionContributionItem(this.menuItem);
			this.item.fill(this.menu, -1);
		}
		return this.menu;
	}

	@Override
	public void dispose() {
		if (this.menu != null) {
			this.menu.dispose();
			this.menu = null;
		}
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	/**
	 * Menu creator for repositories. Creates the submenu under a project menu
	 * item with the list of repositories available in that system
	 */
	private class RespositoryListMenuCreator implements IMenuCreator {
		private final IProject project;

		RespositoryListMenuCreator(IProject project) {
			this.project = project;
		}

		@Override
		public Menu getMenu(Menu parent) {
			//check if project is logged on
			if (!SwitchRepositoryMenuCreator.this.stagingUtil.isLoggedOn(this.project)) {
				return null;
			}

			//get an instance of the repository service for the
			IRepositoryService service = getRepositoryService(this.project);
			if (service == null) { //abapgit not supported
				return null;
			}

			//create sub menu
			Menu repositoryMenu = new Menu(parent);
			//fetch the repositories available in the system
			IRepositories repositories = service.getRepositories(new NullProgressMonitor());
			//get the logged on user
			String loggedOnUser = getLoggedOnUser(this.project);
			//sort repositories
			List<IRepository> repos = sortRepositories(repositories, loggedOnUser);

			//create menu contribution for each repository
			for (IRepository repository : repos) {
				String repoName = RepositoryUtil.getRepoNameFromUrl(repository.getUrl());
				SwitchRepositoryMenuCreator.this.menuItem = new Action(repoName, IAction.AS_RADIO_BUTTON) {
					@Override
					public void run() {
						//open the selected repository in the staging view
						if (!RespositoryListMenuCreator.this.project.equals(SwitchRepositoryMenuCreator.this.view.getProject())
								|| !repository.equals(SwitchRepositoryMenuCreator.this.view.getRepository())) {
							SwitchRepositoryMenuCreator.this.view.openStagingView(repository, RespositoryListMenuCreator.this.project);
						}
					}
				};
				//set an image for the menu contribution if the repository belongs to the logged on user
				if (repository.getCreatedBy().equalsIgnoreCase(loggedOnUser)) {
					SwitchRepositoryMenuCreator.this.menuItem.setImageDescriptor(getMyRepositoryImageDescriptor());
				}
				//set the linked package name as tooltip
				SwitchRepositoryMenuCreator.this.menuItem.setToolTipText(repository.getPackage());
				//check the current selected repository
				if (SwitchRepositoryMenuCreator.this.view.getRepository() != null) {
					if (SwitchRepositoryMenuCreator.this.view.getProject().equals(this.project)
							&& repository.equals(SwitchRepositoryMenuCreator.this.view.getRepository())) {
						SwitchRepositoryMenuCreator.this.menuItem.setChecked(true);
					}
				}
				SwitchRepositoryMenuCreator.this.item = new ActionContributionItem(SwitchRepositoryMenuCreator.this.menuItem);
				SwitchRepositoryMenuCreator.this.item.fill(repositoryMenu, -1);
			}
			//if no abapgit repositories are available, add a dummy menu contribution
			if (repositoryMenu.getItemCount() == 0) {
				SwitchRepositoryMenuCreator.this.menuItem = new Action(Messages.AbapGitStaging_switch_repository_no_repositories_xmsg) {
				};
				//disable the menu
				SwitchRepositoryMenuCreator.this.menuItem.setEnabled(false);
				SwitchRepositoryMenuCreator.this.item = new ActionContributionItem(SwitchRepositoryMenuCreator.this.menuItem);
				SwitchRepositoryMenuCreator.this.item.fill(repositoryMenu, -1);
			}
			return repositoryMenu;
		}

		@Override
		public void dispose() {
		}

		@Override
		public Menu getMenu(Control parent) {
			return null;
		}
	}

	/**
	 * Get the logged on user for the given project
	 */
	private String getLoggedOnUser(IProject project) {
		IAbapProject abapProject = project.getAdapter(IAbapProject.class);
		return abapProject.getDestinationData().getUser();
	}

	/**
	 * Sort repositories on alphabetical order. If the repositories are from the
	 * logged on user, it should appear on the top.
	 */
	private List<IRepository> sortRepositories(IRepositories repositoryList, String loggedOnUser) {
		List<IRepository> repositories = new ArrayList<IRepository>(repositoryList.getRepositories());
		Collections.sort(repositories, new Comparator<IRepository>() {
			@Override
			public int compare(IRepository repo1, IRepository repo2) {
				if (repo1.getCreatedBy().equalsIgnoreCase(loggedOnUser) && !repo2.getCreatedBy().equalsIgnoreCase(loggedOnUser)) {
					return -1;
				} else if (!repo1.getCreatedBy().equalsIgnoreCase(loggedOnUser) && repo2.getCreatedBy().equalsIgnoreCase(loggedOnUser)) {
					return 1;
				}
				return RepositoryUtil.getRepoNameFromUrl(repo1.getUrl()).toLowerCase(Locale.ENGLISH)
						.compareTo(RepositoryUtil.getRepoNameFromUrl(repo2.getUrl()).toLowerCase(Locale.ENGLISH));
			}
		});
		return repositories;
	}

	/**
	 * Get image for my repositories
	 */
	private ImageDescriptor getMyRepositoryImageDescriptor() {
		if (this.myRepositoryImageDescriptor == null) {
			Bundle actionShowMyReposBundle = Platform.getBundle("com.sap.adt.projectexplorer.ui"); //$NON-NLS-1$
			if (actionShowMyReposBundle != null) {
				URL actionShowMyReposImgUrl = FileLocator.find(actionShowMyReposBundle, new Path("icons/obj/package_obj_user.png"), null); //$NON-NLS-1$
				this.myRepositoryImageDescriptor = ImageDescriptor.createFromURL(actionShowMyReposImgUrl);
			}
		}
		return this.myRepositoryImageDescriptor;
	}

	private IRepositoryService getRepositoryService(IProject project) {
		if (this.repositoryService == null) {
			return AbapGitUIServiceFactory.createRepositoryService(project);
		}
		return this.repositoryService;
	}

	//testing
	public void injectRepositoryService(IRepositoryService service) {
		this.repositoryService = service;
	}

}
