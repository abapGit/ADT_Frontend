package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.AbapGitModelFactory;
import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.transport.AdtTransportServiceFactory;
import com.sap.adt.transport.IAdtTransportCheckData;
import com.sap.adt.transport.IAdtTransportService;
import com.sap.adt.transport.ui.wizard.AdtTransportSelectionWizardPageFactory;
import com.sap.adt.transport.ui.wizard.IAdtTransportSelectionWizardPage;

public class AbapGitWizard extends Wizard {

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;
	private PageChangeListener pageChangeListener;

	private AbapGitWizardPageRepositoryAndCredentials pageRepo;
	private AbapGitWizardPageBranchAndPackage pageBranchAndPackage;
	private AbapGitWizardPageApack pageApack;
	private IAdtTransportService transportService;
	private IAdtTransportSelectionWizardPage transportPage;

	public AbapGitWizard(IProject project) {
		this.project = project;
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		this.cloneData = new CloneData();

		setWindowTitle(Messages.AbapGitWizard_title);

		setNeedsProgressMonitor(true);

		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		this.pageRepo = new AbapGitWizardPageRepositoryAndCredentials(this.project, this.destination, this.cloneData, false);
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData, false);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.pageApack = new AbapGitWizardPageApack(this.destination, this.cloneData, this.transportService, false);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);
		addPage(this.pageRepo);
		addPage(this.pageBranchAndPackage);
		addPage(this.pageApack);
		addPage(this.transportPage);
	}

	@Override
	public boolean performFinish() {

		List<IObject> cloneObjects = new LinkedList<>();

		try {
			String transportRequestNumber = this.transportPage.getTransportRequestNumber();
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					Boolean sequenceLnp = AbapGitWizard.this.pageBranchAndPackage.getLnpSequence();

					monitor.beginTask(Messages.AbapGitWizard_task_cloning_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizard.this.destination,
							monitor);
					if (AbapGitWizard.this.cloneData.hasDependencies()) {

						IRepositories repositoriesToLink = AbapGitModelFactory.createRepositories();
						repositoriesToLink.add(createRepository(AbapGitWizard.this.cloneData.url, AbapGitWizard.this.cloneData.branch,
								AbapGitWizard.this.cloneData.packageRef.getName(), transportRequestNumber,
								AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass));
						for (IApackDependency apackDependency : AbapGitWizard.this.cloneData.apackManifest.getDescriptor()
								.getDependencies()) {
							if (apackDependency.requiresSynchronization()) {
								repositoriesToLink.add(createRepository(apackDependency.getGitUrl(), IApackManifest.MASTER_BRANCH,
										apackDependency.getTargetPackage().getName(), transportRequestNumber,
										AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass));
							}
						}
						repoService.cloneRepositories(repositoriesToLink, monitor);
						if (sequenceLnp) {
							pullLinkedRepositories(monitor, repoService, repositoriesToLink);
						}
					} else {
						List<IObject> abapObjects = repoService.cloneRepository(AbapGitWizard.this.cloneData.url,
								AbapGitWizard.this.cloneData.branch, AbapGitWizard.this.cloneData.packageRef.getName(),
								transportRequestNumber, AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass, monitor)
								.getObjects();
						cloneObjects.addAll(abapObjects);

						//-> Check if link and pull sequence is set
						if (sequenceLnp) {

							//-> Get linked repository by url
							IRepository linkedRepo = repoService.getRepositories(monitor).getRepository(AbapGitWizard.this.cloneData.url);

							//-> Pull newly linked repository
							repoService.pullRepository(linkedRepo, AbapGitWizard.this.cloneData.branch,
									AbapGitWizard.this.transportPage.getTransportRequestNumber(), AbapGitWizard.this.cloneData.user,
									AbapGitWizard.this.cloneData.pass, monitor);
						}
					}
				}

				private void pullLinkedRepositories(IProgressMonitor monitor, IRepositoryService repoService, IRepositories repositoriesToLink) {
					// Need to retrieve linked repositories as only they contain the PULL link needed to continue...
					IRepositories linkedRepositories = repoService.getRepositories(monitor);
					for (IRepository repository : repositoriesToLink.getRepositories()) {
						IRepository linkedRepository = linkedRepositories.getRepository(repository.getUrl());
						if (linkedRepository != null) {
							repoService.pullRepository(linkedRepository, linkedRepository.getBranch(),
									AbapGitWizard.this.transportPage.getTransportRequestNumber(),
									AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass, monitor);
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

		if (this.pageChangeListener == null && wizardContainer != null) {
			Assert.isLegal(wizardContainer instanceof WizardDialog, "Wizard container must be of type WizardDialog"); //$NON-NLS-1$

			this.pageChangeListener = new PageChangeListener();
			((WizardDialog) wizardContainer).addPageChangingListener(this.pageChangeListener);
		}
	}

	private final class PageChangeListener implements IPageChangingListener {
		@Override
		public void handlePageChanging(final PageChangingEvent event) {

			//-> Credentials page -> Branch & Package page
			if (event.getCurrentPage() == AbapGitWizard.this.pageRepo && event.getTargetPage() == AbapGitWizard.this.pageBranchAndPackage
					&& !AbapGitWizard.this.pageRepo.validateAll()) {
				event.doit = false;
				return;
			}

			//-> Branch & Package page -> Credentials page
			if (event.getCurrentPage() == AbapGitWizard.this.pageBranchAndPackage && event.getTargetPage() == AbapGitWizard.this.pageRepo) {
				event.doit = true;
				return;
			}

			//-> Branch & Package page -> Transport page
			if (event.getCurrentPage() == AbapGitWizard.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizard.this.transportPage) {
				if (!AbapGitWizard.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Any page
			if (event.getCurrentPage() == AbapGitWizard.this.pageBranchAndPackage
					&& !AbapGitWizard.this.pageBranchAndPackage.validateAll()) {
				event.doit = false;
				return;
			}

			//-> Prepare transport page
			if (event.getTargetPage() == AbapGitWizard.this.transportPage) {
				try {
					// The transport service requires URIs to objects we want to create in the
					// target package.
					// However, we do not know these URIs from the client.
					// Instead, give it the URI of the package in which we clone.
					IAdtObjectReference packageRef = AbapGitWizard.this.cloneData.packageRef;
					IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
					checkRef.setUri(packageRef.getUri());
					IAdtTransportCheckData checkData = AbapGitWizard.this.transportService.check(checkRef, packageRef.getPackageName(),
							true);
					AbapGitWizard.this.transportPage.setCheckData(checkData);
				} catch (Exception e) {
					AbapGitWizard.this.pageBranchAndPackage.setMessage(e.getMessage(), DialogPage.ERROR);
				}
			}

			//-> APACK page -> Any page
			if (event.getCurrentPage() == AbapGitWizard.this.pageApack && !AbapGitWizard.this.pageApack.validateAll()) {
				event.doit = false;
				return;
			}
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page.equals(this.pageBranchAndPackage) && !this.cloneData.hasDependencies()) {
			// If we don't have APACK dependencies, we can skip the APACK-related page
			return this.transportPage;
		} else {
			return super.getNextPage(page);
		}

	}

	private IRepository createRepository(String url, String branch, String targetPackage, String transportRequest, String userName,
			String password) {
		IRepository repository = AbapGitModelFactory.createRepository();
		repository.setUrl(url);
		repository.setBranch(branch);
		repository.setPackage(targetPackage);
		repository.setTransportRequest(transportRequest);
		repository.setRemoteUser(userName);
		repository.setPassword(password);
		return repository;
	}

	/**
	 * Simple data exchange object for the wizard and its pages. This might be
	 * refined in the future, e.g. by using data binding.
	 */
	static class CloneData {
		public IRepositories repositories;
		public IExternalRepositoryInfo externalRepoInfo;
		public IAdtObjectReference packageRef;
		public String branch;
		public String url;
		public String user;
		public String pass;
		public IApackManifest apackManifest;

		public boolean hasDependencies() {
			return this.apackManifest != null && this.apackManifest.hasDependencies();
		}
	}

}
