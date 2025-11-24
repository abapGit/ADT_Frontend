package org.abapgit.adt.ui.internal.wizards;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitWizardSelectivePullAfterLink extends Wizard {

	private final String destination;
	private final CloneData cloneData;
	Map<String, IAbapGitPullModifiedObjects> repoToSelectedObjectsMap;
	private AbapGitWizardPageObjectsSelectionForPull pageOverwriteObjectsSelection;
	private AbapGitWizardPageObjectsSelectionForPull pagePackageWarningObjectsSelection;
	String transportRequest;
	IRepositoryService repoService;

	public AbapGitWizardSelectivePullAfterLink(IProject project, CloneData cloneData,
			String transportRequest) throws ResourceException {
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		this.cloneData = cloneData;
		this.repoToSelectedObjectsMap = new HashMap<String, IAbapGitPullModifiedObjects>();
		this.transportRequest = transportRequest;
		this.repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());

		setWindowTitle(Messages.AbapGitWizardPull_title);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {

		this.pageOverwriteObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(this.cloneData.repoToModifiedOverwriteObjects,
				Messages.AbapGitWizardPullSelectedObjects_OverwriteObjectsMessage,
				IMessageProvider.INFORMATION);
		this.pagePackageWarningObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(
				this.cloneData.repoToModifiedPackageWarningObjects,
				Messages.AbapGitWizardPullSelectedObjects_PackageWarningObjectsMessage,
				IMessageProvider.WARNING);

		addPage(this.pageOverwriteObjectsSelection);
		addPage(this.pagePackageWarningObjectsSelection);

	}

	@Override
	public boolean performFinish() {

		Set<IRepositoryModifiedObjects> overwriteObjects = AbapGitWizardSelectivePullAfterLink.this.pageOverwriteObjectsSelection
				.getSelectedObjects();
		Set<IRepositoryModifiedObjects> packageWarningObjects = AbapGitWizardSelectivePullAfterLink.this.pagePackageWarningObjectsSelection
				.getSelectedObjects();

		String repoUrl = AbapGitWizardSelectivePullAfterLink.this.cloneData.url;
		String user = AbapGitWizardSelectivePullAfterLink.this.cloneData.user;
		String pass = AbapGitWizardSelectivePullAfterLink.this.cloneData.pass;
		String transportRequest = AbapGitWizardSelectivePullAfterLink.this.transportRequest;

		Job pullRepoJob = new Job(Messages.AbapGitWizard_task_pulling_repository) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = AbapGitWizardSelectivePullAfterLink.this.repoService;
					IRepositories repositories = repoService.getRepositories(monitor);
					AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap = AbapGitUIServiceFactory.createAbapGitPullService()
							.getSelectedObjectsToPullforRepo(overwriteObjects, packageWarningObjects);

					IRepository repository = repoService.getRepositoryByURL(repositories, repoUrl);
					repoService.pullRepository(repository, repository.getBranchName(), transportRequest, user, pass,
							AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap.get(repository.getUrl()), monitor);

					// Pull dependencies if any
					if (AbapGitWizardSelectivePullAfterLink.this.cloneData.hasDependencies()) {
						pullDependencies(monitor, repoService);
					}

					return Status.OK_STATUS;
				} catch (Exception e) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
						WizardPage page = (WizardPage) AbapGitWizardSelectivePullAfterLink.this.getContainer().getCurrentPage();
						page.setPageComplete(false);
						page.setMessage(e.getMessage(), DialogPage.ERROR);
					});
					return new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getMessage(), e);
				}
			}

			private void pullDependencies(IProgressMonitor monitor, IRepositoryService repoService) {
				for (IApackDependency apackDependency : AbapGitWizardSelectivePullAfterLink.this.cloneData.apackManifest.getDescriptor()
						.getDependencies()) {
					if (!apackDependency.requiresSynchronization()) {
						continue;
					}
					IRepository dependencyRepository = RepositoryServiceFactory
							.createRepositoryService(AbapGitWizardSelectivePullAfterLink.this.destination, monitor)
							.getRepositoryByURL(repoService.getRepositories(monitor), apackDependency.getGitUrl());

					if (dependencyRepository != null) {
						repoService.pullRepository(dependencyRepository, IApackManifest.MASTER_BRANCH, transportRequest, user, pass,
								AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap.get(dependencyRepository.getUrl()),
								monitor);
					}
				}
			}
		};

		pullRepoJob.setUser(true); // show in Progress View / UI
		pullRepoJob.schedule();
		return true;
	}


	@Override
	public void setContainer(IWizardContainer wizardContainer) {
		super.setContainer(wizardContainer);

		if (wizardContainer != null) {
			Assert.isLegal(wizardContainer instanceof WizardDialog, "Wizard container must be of type WizardDialog"); //$NON-NLS-1$

		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page.equals(this.pageOverwriteObjectsSelection)) {
			if (this.cloneData.repoToModifiedPackageWarningObjects == null
					|| this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
				return null;
			} else {
				return this.pagePackageWarningObjectsSelection;
			}
		}

			return super.getNextPage(page);
	}

}

