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
	private static final String GITHUB_DOMAIN = "github.com"; //$NON-NLS-1$
	private static final String GITLAB_DOMAIN = "gitlab.com"; //$NON-NLS-1$
	private static final String GIT_WDF_SAP_DOMAIN = "github.wdf.sap.corp"; //$NON-NLS-1$
	private static final String GITHUB_INFRA_DOMAIN = "github.infra.hana.ondemand.com"; //$NON-NLS-1$
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getLink(IRepository repository) {
		String repoLink = repository.getUrl();
		repoLink = repoLink.replace(".git", ""); //$NON-NLS-1$ //$NON-NLS-2$
		// Remove protocol (http:// or https://)
		String domain = repoLink.replaceFirst("https?://", ""); //$NON-NLS-1$ //$NON-NLS-2$

		// Extract domain by splitting with '/'
		domain = domain.split("/")[0]; //$NON-NLS-1$
		// Get connected branch
		String branch = repository.getBranchName();
		// handle domain based repoLink generation
		if (domain.equals(GITHUB_DOMAIN) || domain.equals(GITLAB_DOMAIN) || domain.equals(GITHUB_TOOLS_DOMAIN)
				|| domain.equals(GITHUB_INFRA_DOMAIN) || domain.equals(GIT_WDF_SAP_DOMAIN)) {
			branch = branch.replace("refs/heads/", "/tree/"); //$NON-NLS-1$//$NON-NLS-2$
		} else if (domain.endsWith(BIT_BUCKET_DOMAIN)) {
			repoLink = repoLink.replaceAll("https://.*?@", "https://"); //$NON-NLS-1$ //$NON-NLS-2$
			branch = branch.replace("refs/heads/", "/src/"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// return the concatenated link that redirects to the branch
		return repoLink + branch;
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
