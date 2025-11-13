package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.compatibility.exceptions.OutDatedClientException;
import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;
import com.sap.adt.transport.AdtTransportServiceFactory;
import com.sap.adt.transport.IAdtTransportCheckData;
import com.sap.adt.transport.IAdtTransportService;
import com.sap.adt.transport.ui.wizard.AdtTransportSelectionWizardPageFactory;
import com.sap.adt.transport.ui.wizard.IAdtTransportSelectionWizardPage;
import com.sap.adt.util.ui.AdtUtilUiPlugin;

/**
 * This is wizard that handles pull action on a repository. Includes selective
 * pull.
 *
 */
public class AbapGitWizardPull extends Wizard {

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;
	private PageChangeListener pageChangeListener;
	private final Boolean pullAction = true;
	Map<String, IAbapGitPullModifiedObjects> repoToSelectedObjects;
	private AbapGitWizardPageRepositoryAndCredentials pageCredentials;
	private AbapGitWizardPageBranchAndPackage pageBranchAndPackage;
	private AbapGitWizardPageApack pageApack;
	private AbapGitWizardPageObjectsSelectionForPull pageOverwriteObjectsSelection;
	private AbapGitWizardPageObjectsSelectionForPull pagePackageWarningObjectsSelection;
	public IAdtTransportService transportService;
	public IAdtTransportSelectionWizardPage transportPage;
	public IRepository selRepoData;
	public List<IRepository> allRepositories;

	public AbapGitWizardPull(IProject project, IRepository selRepo, List<IRepository> allRepositories) {
		this.project = project;
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		this.cloneData = new CloneData();
		this.selRepoData = selRepo;
		this.allRepositories = allRepositories;
		this.cloneData.url = selRepo.getUrl();
		this.cloneData.branch = selRepo.getBranchName();
		this.repoToSelectedObjects = new HashMap<String, IAbapGitPullModifiedObjects>();

		getPackageAndRepoType();

		setWindowTitle(Messages.AbapGitWizardPull_title);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	private boolean getPackageAndRepoType() {

		try {
			String packageName = AbapGitWizardPull.this.selRepoData.getPackage();
			PlatformUI.getWorkbench().getProgressService().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_message, IProgressMonitor.UNKNOWN);

					//Get repository type (public / private)
					IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
							.createExternalRepositoryInfoService(AbapGitWizardPull.this.destination, null);
					AbapGitWizardPull.this.cloneData.externalRepoInfo = externalRepoInfoService
							.getExternalRepositoryInfo(AbapGitWizardPull.this.selRepoData.getUrl(), "", "", null); //$NON-NLS-1$ //$NON-NLS-2$

					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					if (packageServiceUI.packageExists(AbapGitWizardPull.this.destination, packageName, monitor)) {
						List<IAdtObjectReference> packageRefs = packageServiceUI.find(AbapGitWizardPull.this.destination, packageName,
								monitor);
						AbapGitWizardPull.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
					}

				}
			});
			return true;
		} catch (InvocationTargetException e) {
			return false;
		} catch (InterruptedException e) {
			((WizardPage) getContainer().getCurrentPage()).setPageComplete(false);
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getMessage(), DialogPage.ERROR);
			return false;
		}

	}

	@Override
	public void addPages() {

		this.pageCredentials = new AbapGitWizardPageRepositoryAndCredentials(this.project, this.destination, this.cloneData,
				this.pullAction);
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData, this.pullAction);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.pageApack = new AbapGitWizardPageApack(this.destination, this.cloneData, this.transportService, true);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);
		this.pageOverwriteObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(this.cloneData.repoToModifiedOverwriteObjects,
				Messages.AbapGitWizardPullSelectedObjects_OverwriteObjectsMessage, IMessageProvider.INFORMATION);
		this.pagePackageWarningObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(
				this.cloneData.repoToModifiedPackageWarningObjects, Messages.AbapGitWizardPullSelectedObjects_PackageWarningObjectsMessage,
				IMessageProvider.WARNING);

		addPage(this.pageCredentials);
		addPage(this.pageBranchAndPackage);
		addPage(this.pageApack);
		addPage(this.pageOverwriteObjectsSelection);
		addPage(this.pagePackageWarningObjectsSelection);
		addPage(this.transportPage);

	}

	@Override
	public boolean performFinish() {

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizardPull.this.destination,
							monitor);

					//get the selected objects to be pulled
					AbapGitWizardPull.this.repoToSelectedObjects = AbapGitUIServiceFactory.createAbapGitPullService()
							.getSelectedObjectsToPullforRepo(AbapGitWizardPull.this.pageOverwriteObjectsSelection.getSelectedObjects(),
									AbapGitWizardPull.this.pagePackageWarningObjectsSelection.getSelectedObjects());

					//pull the selected objects
					repoService.pullRepository(AbapGitWizardPull.this.selRepoData, AbapGitWizardPull.this.selRepoData.getBranchName(),
							AbapGitWizardPull.this.transportPage.getTransportRequestNumber(), AbapGitWizardPull.this.cloneData.user,
							AbapGitWizardPull.this.cloneData.pass,
							AbapGitWizardPull.this.repoToSelectedObjects.get(AbapGitWizardPull.this.selRepoData.getUrl()), monitor);

					if (AbapGitWizardPull.this.cloneData.hasDependencies()) {
						pullDependencies(monitor, repoService);
					}
				}

				private void pullDependencies(IProgressMonitor monitor, IRepositoryService repoService) {
					for (IApackDependency apackDependency : AbapGitWizardPull.this.cloneData.apackManifest.getDescriptor()
							.getDependencies()) {
						if (apackDependency.requiresSynchronization()) {
							IRepository dependencyRepository = repoService.getRepositoryByURL(AbapGitWizardPull.this.cloneData.repositories,
									apackDependency.getGitUrl());
							if (dependencyRepository != null) {
								repoService.pullRepository(dependencyRepository, IApackManifest.MASTER_BRANCH,
										AbapGitWizardPull.this.transportPage.getTransportRequestNumber(),
										AbapGitWizardPull.this.cloneData.user, AbapGitWizardPull.this.cloneData.pass,
										AbapGitWizardPull.this.repoToSelectedObjects.get(dependencyRepository.getUrl()), monitor);
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
		} catch (ResourceException e) {
			((WizardPage) getContainer().getCurrentPage()).setPageComplete(false);
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getMessage(), DialogPage.ERROR);
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

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page.equals(this.pageBranchAndPackage)) {
			if (!this.cloneData.hasDependencies()) {
				if (this.cloneData.repoToModifiedOverwriteObjects == null || this.cloneData.repoToModifiedOverwriteObjects.isEmpty()) {
					if (this.cloneData.repoToModifiedPackageWarningObjects == null
							|| this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
						return this.transportPage;
					} else {
						return this.pagePackageWarningObjectsSelection;
					}
				} else {
					return this.pageOverwriteObjectsSelection;
				}
			}
			return super.getNextPage(page);
		}

		else if (page.equals(this.pageApack)) {
			if (this.cloneData.repoToModifiedOverwriteObjects == null || this.cloneData.repoToModifiedOverwriteObjects.isEmpty()) {
				if (this.cloneData.repoToModifiedPackageWarningObjects == null
						|| this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
					return this.transportPage;
				} else {
					return this.pagePackageWarningObjectsSelection;
				}
			} else {
				return super.getNextPage(page);
			}
		}

		else if (page.equals(this.pageOverwriteObjectsSelection)) {
			if (this.cloneData.repoToModifiedPackageWarningObjects == null
					|| this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
				return this.transportPage;
			} else {
				return super.getNextPage(page);
			}
		} else {
			return super.getNextPage(page);
		}
	}

	private final class PageChangeListener implements IPageChangingListener {
		@Override
		public void handlePageChanging(final PageChangingEvent event) {

			//-> Credentials page -> Branch & Package page
			if (event.getCurrentPage() == AbapGitWizardPull.this.pageCredentials
					&& event.getTargetPage() == AbapGitWizardPull.this.pageBranchAndPackage) {
				if (!AbapGitWizardPull.this.pageCredentials.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Credentials page
			if (event.getCurrentPage() == AbapGitWizardPull.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardPull.this.pageCredentials) {
				if (!AbapGitWizardPull.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Any page
			if (event.getCurrentPage() == AbapGitWizardPull.this.pageBranchAndPackage) {
				if (!AbapGitWizardPull.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Transport page
			if (event.getCurrentPage() == AbapGitWizardPull.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardPull.this.transportPage) {
				if (!AbapGitWizardPull.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Prepare transport page
			if (event.getTargetPage() == AbapGitWizardPull.this.transportPage) {
				IAdtObjectReference packageRef = AbapGitWizardPull.this.cloneData.packageRef;
				IAdtTransportCheckData transportCheckData = getTransportCheckData(packageRef);
				AbapGitWizardPull.this.transportPage.setCheckData(transportCheckData);
			}


			//-> APACK page -> Any page
			if (event.getCurrentPage() == AbapGitWizardPull.this.pageApack) {
				if (!AbapGitWizardPull.this.pageApack.validateAll()) {
					event.doit = false;
					return;
				}
			}
		}

		private IAdtTransportCheckData getTransportCheckData(IAdtObjectReference packageRef) {

			IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
			checkRef.setUri(packageRef.getUri());

			final IAdtTransportCheckData[] checkData = new IAdtTransportCheckData[1];

			try {
				getContainer().run(true, true, monitor -> {
					monitor.beginTask(Messages.AbapGitWizardPageTransportSelection_transport_check, IProgressMonitor.UNKNOWN);
					checkData[0] = AbapGitWizardPull.this.transportService.check(checkRef, packageRef.getPackageName(), true);
				});
				return checkData[0];

			} catch (Exception e) {
				// Catches InterruptedException and other general exceptions
				WizardPage currentPage = ((WizardPage) getContainer().getCurrentPage());
				handleException(e, currentPage);
				return null;
			}
		}

		private void handleOutdatedClientException(Throwable e) {

			Display.getDefault().asyncExec(() -> {
				WizardDialog d = (WizardDialog) getContainer();

				if (d != null) {
					// Check if the dialog is still open before attempting to close
					if (d.getShell() != null && !d.getShell().isDisposed()) {
						d.close();
						AdtUtilUiPlugin.getDefault().getAdtStatusService().handle(e, null);
					}
				}
			});

		}

		private void handleException(Exception exception, WizardPage page) {

			page.setPageComplete(false);
			Throwable cause = exception instanceof InvocationTargetException ? exception.getCause() : exception;
			if (cause != null && cause instanceof OutDatedClientException) {
				handleOutdatedClientException(cause);
			} else {
				page.setMessage(cause != null ? cause.getMessage() : exception.getMessage(), DialogPage.ERROR);
			}
		}

	}
}
