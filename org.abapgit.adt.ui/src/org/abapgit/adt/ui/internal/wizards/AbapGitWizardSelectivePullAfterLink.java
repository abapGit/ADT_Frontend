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
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
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

		Set<IRepositoryModifiedObjects> overwriteObjects = this.pageOverwriteObjectsSelection.getSelectedObjects();
		Set<IRepositoryModifiedObjects> packageWarningObjects = this.pagePackageWarningObjectsSelection.getSelectedObjects();
		try {
			Job pullJob = new Job(Messages.AbapGitWizard_task_pulling_repository) {

				@Override
					protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositories repositories = AbapGitWizardSelectivePullAfterLink.this.repoService.getRepositories(monitor);

					AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap = AbapGitUIServiceFactory.createAbapGitPullService()
							.getSelectedObjectsToPullforRepo(
										overwriteObjects, packageWarningObjects);

					IRepository repository = AbapGitWizardSelectivePullAfterLink.this.repoService.getRepositoryByURL(repositories,
							AbapGitWizardSelectivePullAfterLink.this.cloneData.url);
					AbapGitWizardSelectivePullAfterLink.this.repoService.pullRepository(repository, repository.getBranchName(),
							AbapGitWizardSelectivePullAfterLink.this.transportRequest,
							AbapGitWizardSelectivePullAfterLink.this.cloneData.user, AbapGitWizardSelectivePullAfterLink.this.cloneData.pass,
							AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap
									.get(repository.getUrl()),
							monitor);

					if (AbapGitWizardSelectivePullAfterLink.this.cloneData.hasDependencies()) {
						pullDependencies(monitor, AbapGitWizardSelectivePullAfterLink.this.repoService);
					}

						return Status.OK_STATUS;
				}

				private void pullDependencies(IProgressMonitor monitor, IRepositoryService repoService) {
					for (IApackDependency apackDependency : AbapGitWizardSelectivePullAfterLink.this.cloneData.apackManifest.getDescriptor()
							.getDependencies()) {
						if (apackDependency.requiresSynchronization()) {
							IRepository dependencyRepository = RepositoryServiceFactory.createRepositoryService(AbapGitWizardSelectivePullAfterLink.this.destination,
									monitor).getRepositoryByURL(repoService.getRepositories(monitor), apackDependency.getGitUrl());
							if (dependencyRepository != null) {
								repoService.pullRepository(dependencyRepository, IApackManifest.MASTER_BRANCH,
										AbapGitWizardSelectivePullAfterLink.this.transportRequest,
										AbapGitWizardSelectivePullAfterLink.this.cloneData.user,
										AbapGitWizardSelectivePullAfterLink.this.cloneData.pass,
										AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap.get(dependencyRepository.getUrl()),
										monitor);
							}
						}
					}
				}
				};

				pullJob.setUser(true);
				pullJob.schedule();

			return true;

			} catch (Exception e) {
				Display.getDefault().asyncExec(() -> {
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageDialog.openError(shell, "Error", e.getMessage()); //$NON-NLS-1$
				});
			return false;
		}
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

