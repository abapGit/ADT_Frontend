package org.abapgit.adt.ui.internal.repositories.actions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IAbapGitRepositoriesView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Action to "Open repository in an external browser"
 */
public class OpenRepositoryAction extends Action {
	private final IViewPart view;
	private static final String REFS_HEADS = "refs/heads/"; //$NON-NLS-1$
	private static final String GITHUB_DOMAIN = "github.com"; //$NON-NLS-1$
	private static final String GITLAB_DOMAIN = "gitlab.com"; //$NON-NLS-1$
	private static final String GITHUB_WDF_SAP_DOMAIN = "github.wdf.sap.corp"; //$NON-NLS-1$
	private static final String GITHUB_INFRA_HANA_DOMAIN = "github.infra.hana.ondemand.com"; //$NON-NLS-1$
	private static final String GITHUB_TOOLS_DOMAIN = "github.tools.sap"; //$NON-NLS-1$
	private static final String BIT_BUCKET_DOMAIN = "bitbucket.org"; //$NON-NLS-1$

	/**
	 * @param view
	 *            abapGit repositories view or abapGit staging view
	 */
	public OpenRepositoryAction(IViewPart view) {
		super(Messages.AbapGitView_action_open_repo);
		setToolTipText(Messages.AbapGitView_action_open_repo_tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/etool/external_browser.png")); //$NON-NLS-1$
		this.view = view;
	}

	@Override
	public void run() {
		IRepository repository = getRepository();
		if (repository != null) {
			try {
				// Get Repository link
				String repoLink = getLink(repository);
				// Open the link in default external browser
				URI repositoryUri = new URI(repoLink);
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(repositoryUri.toURL());
			} catch (PartInitException | MalformedURLException | URISyntaxException exception) {
				MessageDialog.openError(this.view.getViewSite().getShell(), Messages.AbapGitView_action_open_repo_error_dialog_title,
						exception.getMessage());
				AbapGitUIPlugin.getDefault().getLog()
						.log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, exception.getMessage(), exception));
			}
		}
	}

	public String getLink(IRepository repository) throws URISyntaxException {
		// Get Connected branch
		String repoLink = repository.getUrl();
		URI repoURI = new URI(repository.getUrl());
		String domain = repoURI.getHost();
		String path = getPath(repoURI);
		String branch = repository.getBranchName();
		if (isGithubDomain(domain) || isGitlabDomain(domain)) {
			branch = branch.replace(REFS_HEADS, "/tree/"); //$NON-NLS-1$
			repoLink = constructRepoBranchURI(repoURI, path + branch);
		} else if (isBitbucketDomain(domain)) {
			branch = branch.replace(REFS_HEADS, "/src/"); //$NON-NLS-1$
			repoLink = constructRepoBranchURI(repoURI, path + branch);
		}
		// return the concatenated link that redirects to the branch
		return repoLink;
	}

	// method to construct URI from repository URI with new path and branch details
	private String constructRepoBranchURI(URI repoURI, String path) throws URISyntaxException {
		URI reconstructedRepoURI = new URI(repoURI.getScheme(), null, repoURI.getHost(), repoURI.getPort(), path, null, null); // not taking the username for the link
		return reconstructedRepoURI.toString();
	}

	// method to check if the domain is a github domain
	private boolean isGithubDomain(String domain) {
		if (domain.equals(GITHUB_DOMAIN) || domain.equals(GITHUB_TOOLS_DOMAIN) || domain.equals(GITHUB_INFRA_HANA_DOMAIN)
				|| domain.equals(GITHUB_WDF_SAP_DOMAIN)) {
			return true;
		}
		return false;
	}

	// method to check if the domain is a gitlab domain
	private boolean isGitlabDomain(String domain) {
		if (domain.equals(GITLAB_DOMAIN)) {
			return true;
		}
		return false;
	}

	// method to check if the domain is a bitbucket domain
	private boolean isBitbucketDomain(String domain) {
		if (domain.equals(BIT_BUCKET_DOMAIN)) {
			return true;
		}
		return false;
	}

	private String getPath(URI repoURI) {
		String path = repoURI.getPath();
		if (path.endsWith(".git")) { //$NON-NLS-1$
			path = path.substring(0, path.length() - 4); // Remove ".git"
		}
		return path;
	}

	private IRepository getRepository() {
		if (this.view instanceof IAbapGitStagingView) {
			//abapGit staging view
			return ((IAbapGitStagingView) this.view).getRepository();
		} else {
			//abapGit repositories view
			return ((IAbapGitRepositoriesView) this.view).getRepositorySelection();
		}
	}
}
