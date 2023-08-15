package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.ErrorHandlingService;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.communication.resources.ResourceException;
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
	private String transportRequest;
	private PageChangeListener pageChangeListener;

	private AbapGitWizardPageRepositoryAndCredentials pageRepo;
	private AbapGitWizardPageBranchAndPackage pageBranchAndPackage;
	private AbapGitWizardPageFolderLogic pageFolderlogic;
	private AbapGitWizardPageApack pageApack;
	private IAdtTransportService transportService;
	private IAdtTransportSelectionWizardPage transportPage;

	private final String ErrorMessageRetrievingLinkedRepo = Messages.AbapGitWizard_errorRetrievingLinkedRepo;

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
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData,
				false);
		this.pageFolderlogic = new AbapGitWizardPageFolderLogic(this.project, this.destination, this.cloneData);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.pageApack = new AbapGitWizardPageApack(this.destination, this.cloneData, this.transportService, false);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);
		addPage(this.pageRepo);
		addPage(this.pageBranchAndPackage);
		addPage(this.pageFolderlogic);
		addPage(this.pageApack);
		addPage(this.transportPage);
	}

	@Override
	public boolean performFinish() {

		//Validate the APACK page (As it could be the last page)
		if (AbapGitWizard.this.cloneData.hasDependencies() && !this.pageApack.validateAll()) {
			return false;
		}
		List<IAbapObject> cloneObjects = new LinkedList<>();

		try {

			this.transportRequest = this.transportPage.getTransportRequestNumber();
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					Boolean sequenceLnp = AbapGitWizard.this.pageBranchAndPackage.getLnpSequence();

					monitor.beginTask(Messages.AbapGitWizard_task_cloning_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizard.this.destination,
							monitor);
					IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
					if (AbapGitWizard.this.cloneData.hasDependencies()) {

						IRepositories repositoriesToLink = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
						repositoriesToLink.getRepositories()
								.add(createRepository(AbapGitWizard.this.cloneData.url, AbapGitWizard.this.cloneData.branch,
										AbapGitWizard.this.cloneData.packageRef.getName(), AbapGitWizard.this.transportRequest,
								AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass));
						for (IApackDependency apackDependency : AbapGitWizard.this.cloneData.apackManifest.getDescriptor()
								.getDependencies()) {
							if (apackDependency.requiresSynchronization()) {
								repositoriesToLink.getRepositories()
										.add(createRepository(apackDependency.getGitUrl(), IApackManifest.MASTER_BRANCH,
												apackDependency.getTargetPackage().getName(), AbapGitWizard.this.transportRequest,
										AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass));
							}
						}
						repoService.cloneRepositories(repositoriesToLink, monitor);
						if (sequenceLnp) {
							//-> Get linked repository by url
							IRepository linkedRepo = repoService.getRepositoryByURL(repoService.getRepositories(monitor),
									AbapGitWizard.this.cloneData.url);

							if (linkedRepo == null) {
								//if due to some reason the linkedRepo is not returned by the URL
								throw new InterruptedException(AbapGitWizard.this.ErrorMessageRetrievingLinkedRepo);
							}
							//TODO: Remove
							//This is valid only for back end versions before 2105 where selective pull is not supported.
							//Required for Compatibility handling for Selective Pull
							// If selectivePull is not supported pull all objects from the repositories.
							// Otherwise a new wizard is opened to allow selective pulling of objects
							if (abapGitService.isSelectivePullSupported(linkedRepo)
									&& fillModifiedObjectsinCloneData(repoService, linkedRepo)) {
								openSelectivePullWizard();
							} else {
								//Pull all objects for linked repositories
								pullLinkedRepositories(monitor, repoService, repositoriesToLink);
							}
						}
					} else {
						List<IAbapObject> abapObjects = repoService.cloneRepository(AbapGitWizard.this.cloneData.url,
								AbapGitWizard.this.cloneData.branch, AbapGitWizard.this.cloneData.packageRef.getName(),
								AbapGitWizard.this.cloneData.folderLogic,
								AbapGitWizard.this.transportRequest, AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass,
								monitor)
								.getAbapObjects();
						cloneObjects.addAll(abapObjects);

						//-> Check if link and pull sequence is set
						if (sequenceLnp) {

							//-> Get linked repository by url
							IRepository linkedRepo = repoService.getRepositoryByURL(repoService.getRepositories(monitor),
									AbapGitWizard.this.cloneData.url);

							if (linkedRepo == null) {
								//if due to some reason the linkedRepo is not returned by the URL
								throw new InterruptedException(AbapGitWizard.this.ErrorMessageRetrievingLinkedRepo);
							}

							//TODO: Remove
							//This is valid only for back end versions before 2105 where selective pull is not supported.
							//Required for Compatibility handling for Selective Pull
							// If selectivePull is not supported, pull all objects from the repository.
							// Otherwise a new wizard is opened to allow selective pulling of objects
							if (abapGitService.isSelectivePullSupported(linkedRepo)
									&& fillModifiedObjectsinCloneData(repoService, linkedRepo)) {
								openSelectivePullWizard();
							} else {
								//-> Pull newly linked repository
								repoService.pullRepository(linkedRepo, AbapGitWizard.this.cloneData.branch,
										AbapGitWizard.this.transportPage.getTransportRequestNumber(), AbapGitWizard.this.cloneData.user,
										AbapGitWizard.this.cloneData.pass, monitor);
							}

						}
					}
				}

				private void pullLinkedRepositories(IProgressMonitor monitor, IRepositoryService repoService, IRepositories repositoriesToLink) {
					// Need to retrieve linked repositories as only they contain the PULL link needed to continue...
					IRepositories linkedRepositories = repoService.getRepositories(monitor);
					for (IRepository repository : repositoriesToLink.getRepositories()) {
						IRepository linkedRepository = repoService.getRepositoryByURL(linkedRepositories, repository.getUrl());
						if (linkedRepository != null) {
							repoService.pullRepository(linkedRepository, linkedRepository.getBranchName(),
									AbapGitWizard.this.transportPage.getTransportRequestNumber(),
									AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass, monitor);
						}
					}
				}
			});

			return true;
		} catch (InterruptedException e) {
			((WizardPage) getContainer().getCurrentPage()).setPageComplete(false);
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getMessage(), DialogPage.ERROR);
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

			//-> Any Page -> Folder Logic page 
			//(If only linking a repository and no APACK dependencies, no need to show transport page and finish wizard )
			if (event.getTargetPage() == AbapGitWizard.this.pageFolderlogic && !AbapGitWizard.this.pageBranchAndPackage.getLnpSequence() && !AbapGitWizard.this.cloneData.hasDependencies() ) {
					AbapGitWizard.this.transportPage.setPageComplete(true);
					AbapGitWizard.this.pageFolderlogic.setPageComplete(true);
				}
			
			// -> Folder Logic page -> Branch & Package page
			if (event.getCurrentPage() == AbapGitWizard.this.pageFolderlogic
					&& event.getTargetPage() == AbapGitWizard.this.pageBranchAndPackage) {
				AbapGitWizard.this.transportPage.setPageComplete(false);
			}

			//-> Any Page -> APACK page 
			//(If only linking a repository, no need to show transport page and finish wizard)
			if (event.getTargetPage() == AbapGitWizard.this.pageApack && !AbapGitWizard.this.pageBranchAndPackage.getLnpSequence()) {
				AbapGitWizard.this.transportPage.setPageComplete(true);
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

		if (page.equals(this.pageBranchAndPackage)) {
			if(!AbapGitUIServiceFactory.createAbapGitService().isFolderLogicSupportedWhileLink(this.cloneData.repositories)) {
				if (this.cloneData.hasDependencies()) {
					return this.pageApack;
				} else {
					return this.transportPage;
				}
			}
		}

		if (page.equals(this.pageFolderlogic) && !this.cloneData.hasDependencies()) {
				// If we don't have APACK dependencies, we can skip the APACK-related page
				return this.transportPage;
		}
	return super.getNextPage(page);

	}

	private IRepository createRepository(String url, String branch, String targetPackage, String transportRequest, String userName,
			String password) {
		IRepository repository = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository.setUrl(url);
		repository.setBranchName(branch);
		repository.setPackage(targetPackage);
		repository.setTransportRequest(transportRequest);
		repository.setRemoteUser(userName);
		repository.setRemotePassword(password);
		return repository;
	}

	/**
	 * Simple data exchange object for the wizard and its pages. This might be
	 * refined in the future, e.g. by using data binding.
	 */
	public static class CloneData {
		public CloneData() {
			this.repoToModifiedOverwriteObjects = new HashSet<IRepositoryModifiedObjects>();
			this.repoToModifiedPackageWarningObjects = new HashSet<IRepositoryModifiedObjects>();
		}
		public IRepositories repositories;
		public IExternalRepositoryInfo externalRepoInfo;
		public IAdtObjectReference packageRef;
		public String folderLogic;
		public boolean folderLogicExistsInAbapGitFile = false;
		public String branch;
		public String url;
		public String user;
		public String pass;
		public IApackManifest apackManifest;
		public Set<IRepositoryModifiedObjects> repoToModifiedOverwriteObjects; // List of all repositories (including dependencies), mapped to their locally modified Overwrite Objects
		public Set<IRepositoryModifiedObjects> repoToModifiedPackageWarningObjects; // List of all repositories (including dependencies), mapped to their locally modified PackageWarning Objects

		public boolean hasDependencies() {
			return this.apackManifest != null && this.apackManifest.hasDependencies();
		}
	}

	/**
	 * Fetch the modified objects for the main repository and dependencies (if
	 * any) Then maintain the overWrite objects and warning package objects,
	 * separately in the clone data object
	 *
	 * Return true in case there are modified objects, otherwise return false.
	 */
	private boolean fillModifiedObjectsinCloneData(IRepositoryService repoService, IRepository repository) {

		IRepositories repositories = repoService.getRepositories(new NullProgressMonitor());

		RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, repoService, this.cloneData);

		if (this.cloneData.hasDependencies()) {
			for (IApackDependency apackDependency : this.cloneData.apackManifest.getDescriptor().getDependencies()) {
				IRepository dependencyRepository = repoService.getRepositoryByURL(repositories, apackDependency.getGitUrl());
				if (dependencyRepository != null) {
					RepositoryUtil.fetchAndExtractModifiedObjectsToPull(dependencyRepository, repoService, this.cloneData);
				}
			}
		}

		//In case no modified objects present
		if (this.cloneData.repoToModifiedOverwriteObjects.isEmpty() && this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * For pull after link. Opens a wizard to allow selection of objects to be
	 * pulled for the linked repository. In case the linked repository has APACK
	 * dependencies, the wizard allows to select objects to be pulled for each
	 * of these repositories.
	 */
	private void openSelectivePullWizard() {

		AbapGitWizardSelectivePullAfterLink selectivePullWizard = new AbapGitWizardSelectivePullAfterLink(this.project, this.cloneData,
				this.transportRequest);

		Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					try {
					WizardDialog wizardDialog = new WizardDialog(Display.getDefault().getActiveShell(), selectivePullWizard);
					wizardDialog.open();
				} catch (ResourceException e) {
					ErrorHandlingService.openErrorDialog(Messages.AbapGitView_context_pull_error, e.getMessage(), getShell(), true);
				}
				}
			});

	}

}
