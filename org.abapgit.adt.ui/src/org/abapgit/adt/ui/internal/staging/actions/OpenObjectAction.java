package org.abapgit.adt.ui.internal.staging.actions;

import org.abapgit.adt.backend.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.ErrorHandlingService;
import org.abapgit.adt.ui.internal.util.GitCredentialsService;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;
import com.sap.adt.util.ui.AdtUtilUiPlugin;

public class OpenObjectAction extends BaseSelectionListenerAction {

	private final TreeViewer treeViewer;
	private final IAbapGitStagingView view;
	private IExternalRepositoryInfoRequest credentials;

	public OpenObjectAction(IAbapGitStagingView view, TreeViewer treeViewer) {
		super(Messages.AbapGitStaging_action_open);
		setEnabled(updateSelection((IStructuredSelection) treeViewer.getSelection()));

		this.view = view;
		this.treeViewer = treeViewer;
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		//open action should not be enabled for the node "non code and meta files"
		//in case only that node is selected by the user
		if (selection.size() == 1) {
			if (selection.getFirstElement() instanceof IAbapGitObject) {
				if (((IAbapGitObject) selection.getFirstElement()).getType() == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void run() {
		IRepository repo = this.view.getRepository();
		Shell shell = ((AbapGitStagingView) this.view).getSite().getShell();

		if (GitCredentialsService.checkIfCredentialsRequired(this.view.getExternalRepositoryInfo())) {
			if (getGitCredentialsAndValidate(shell, repo.getUrl()).equals(Status.CANCEL_STATUS)) {
				//reset if user cancels refresh
				this.credentials = null;
				return;
			}
		} else {
			openObjectInEditor();
		}

	}

	private void openObjectInEditor() {
		IStructuredSelection selection = (IStructuredSelection) this.treeViewer.getSelection();
		for (Object object : selection.toList()) {
			AbapGitUIServiceFactory.createAbapGitStagingService().openEditor(object, this.view.getProject(), this.credentials);
		}
	}

	private void repositoryChecks(IProgressMonitor monitor, IExternalRepositoryInfoRequest credentials) {
		//perform repository checks
		if (this.view.getRepository().getChecksLink(IRepositoryService.RELATION_CHECK) != null) {
			monitor.beginTask(Messages.AbapGitStaging_check_job_title, IProgressMonitor.UNKNOWN);
			IRepositoryService repoService = AbapGitUIServiceFactory.createRepositoryService(this.view.getProject());
			repoService.repositoryChecks(monitor, credentials, this.view.getRepository());
		}
	}

	private IStatus getGitCredentials(Shell shell, String repositoryURL, String errorMessage) {
		//get credentials
		this.credentials = GitCredentialsService.getCredentialsFromUser(shell, repositoryURL, errorMessage);
		if (this.credentials == null) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	private void handleException(Exception e, String repoURL, Shell shell) {
		//invalid credentials : this condition check is only valid from 2002
		if (e instanceof ResourceException && GitCredentialsService.isAuthenticationIssue((ResourceException) e)) {
			ErrorHandlingService.handleExceptionAndDisplayInErrorDialog(e, shell);
			if (getGitCredentials(shell, repoURL, e.getLocalizedMessage()).equals(Status.OK_STATUS)) {
				validateCredsAndOpenObject(shell, repoURL);
			}
		} else {
			ErrorHandlingService.openErrorDialog("Error", e.getLocalizedMessage(), shell, true); //$NON-NLS-1$
		}
	}

	private void validateCredsAndOpenObject(Shell shell, String repositoryURL) {
				try {
					//perform repository checks
			repositoryChecks(new NullProgressMonitor(), OpenObjectAction.this.credentials);
			openObjectInEditor();
				} catch (CommunicationException | ResourceException | OperationCanceledException e) {
					handleException(e, repositoryURL, shell);

				} catch (OutDatedClientException e) {
					AdtUtilUiPlugin.getDefault().getAdtStatusService().handle(e, null);
				}
	}

	private IStatus getGitCredentialsAndValidate(Shell shell, String repositoryURL) {
		if (getGitCredentials(shell, repositoryURL, null).equals(Status.CANCEL_STATUS)) {
			return Status.CANCEL_STATUS;
		}

		validateCredsAndOpenObject(shell, repositoryURL);
		return Status.OK_STATUS;
	}

}
