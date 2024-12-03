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
	private static final String REFS_HEADS = "refs/heads/"; //$NON-NLS-1$
	private final IViewPart view;
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
				URI uri = new URI(repoLink);
				PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(uri.toURL());
			} catch (PartInitException | MalformedURLException exception) {
				MessageDialog.openError(this.view.getViewSite().getShell(), Messages.AbapGitView_action_open_repo_error_dialog_title,
						exception.getMessage());
				AbapGitUIPlugin.getDefault().getLog()
						.log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, exception.getMessage(), exception));
			} catch (URISyntaxException e) {
				MessageDialog.openError(this.view.getViewSite().getShell(), Messages.AbapGitView_action_open_repo_error_dialog_title,
						e.getMessage());
				AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
			}
		}
	}

	public String getLink(IRepository repository) {
		// Get Connected branch
		URI repoURI;
		String repoLink = repository.getUrl();
		String branch;
		try {
			repoURI = new URI(repository.getUrl());
			String domain = repoURI.getHost();
			// Remove the username and `.git` suffix
			String path = repoURI.getPath();
			if (path.endsWith(".git")) { //$NON-NLS-1$
				path = path.substring(0, path.length() - 4); // Remove ".git"
			}
			branch = repository.getBranchName();
			if (domain.equals(GITHUB_DOMAIN) || domain.equals(GITLAB_DOMAIN) || domain.equals(GITHUB_TOOLS_DOMAIN)
					|| domain.equals(GITHUB_INFRA_HANA_DOMAIN) || domain.equals(GITHUB_WDF_SAP_DOMAIN)) {
				branch = branch.replace(REFS_HEADS, "/tree/"); //$NON-NLS-1$
				path += branch;
				URI gitURI = new URI(repoURI.getScheme(), null, repoURI.getHost(), repoURI.getPort(), path, null, null);
				repoLink = gitURI.toString();
			} else if (domain.endsWith(BIT_BUCKET_DOMAIN)) {
				branch = branch.replace(REFS_HEADS, "/src/"); //$NON-NLS-1$
				path += branch;
				URI bucketURI = new URI(repoURI.getScheme(), null, repoURI.getHost(), repoURI.getPort(), path, null, null); // not taking the username for the link
				repoLink = bucketURI.toString();
			}
		} catch (URISyntaxException e) {
			MessageDialog.openError(this.view.getViewSite().getShell(), Messages.AbapGitView_action_open_repo_error_dialog_title,
					e.getMessage());
			AbapGitUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e));
		}
		// return the concatenated link that redirects to the branch
		return repoLink;
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
