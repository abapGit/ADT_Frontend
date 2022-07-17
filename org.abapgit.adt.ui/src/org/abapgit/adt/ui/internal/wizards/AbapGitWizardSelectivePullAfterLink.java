package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
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

		fetchModifiedObjectsinCloneData(); //fetch the modified objects for repository and its dependencies

		//If no modified objects, set message
		if (this.cloneData.repoToModifiedOverwriteObjects.isEmpty() && this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
			getContainer().getCurrentPage().setDescription("No modified objects. All objects will be pulled."); //$NON-NLS-1$
		}
		setWindowTitle(Messages.AbapGitWizardPull_title);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	/**
	 * Fetch the modified objects for the main repository and dependencies (if
	 * any) Then maintain the overWrite objects and warning package objects,
	 * separately in the clone data object
	 */
	private void fetchModifiedObjectsinCloneData() {
		IRepositories repositories = this.repoService.getRepositories(new NullProgressMonitor());

		IRepository repository = this.repoService.getRepositoryByURL(repositories, this.cloneData.url);
		RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, this.repoService, this.cloneData);

		if (this.cloneData.hasDependencies()) {
			for (IApackDependency apackDependency : this.cloneData.apackManifest.getDescriptor().getDependencies()) {
				IRepository dependencyRepository = this.repoService.getRepositoryByURL(repositories, apackDependency.getGitUrl());
				if (dependencyRepository != null) {
					RepositoryUtil.fetchAndExtractModifiedObjectsToPull(dependencyRepository, this.repoService, this.cloneData);
				}
			}
		}
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

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositories repositories = AbapGitWizardSelectivePullAfterLink.this.repoService.getRepositories(monitor);

					AbapGitWizardSelectivePullAfterLink.this.repoToSelectedObjectsMap = AbapGitUIServiceFactory.createAbapGitPullService()
							.getSelectedObjectsToPullforRepo(
									AbapGitWizardSelectivePullAfterLink.this.pageOverwriteObjectsSelection.getSelectedObjects(),
									AbapGitWizardSelectivePullAfterLink.this.pagePackageWarningObjectsSelection.getSelectedObjects());

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
			});

			return true;

		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			((WizardPage) getContainer().getCurrentPage()).setPageComplete(false);
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
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

