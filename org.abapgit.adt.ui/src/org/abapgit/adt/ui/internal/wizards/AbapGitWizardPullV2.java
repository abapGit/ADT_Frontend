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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

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

/**
 * This is wizard that handles selective pull and is valid for back end versions
 * from 2105 where selective pull is supported.
 *
 * To be renamed to AbapGitWizardPullV2 after 2105 release reaches all customers
 *
 * @author I517012
 *
 */
public class AbapGitWizardPullV2 extends Wizard {

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

	public AbapGitWizardPullV2(IProject project, IRepository selRepo, List<IRepository> allRepositories) {
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
			String packageName = AbapGitWizardPullV2.this.selRepoData.getPackage();
			PlatformUI.getWorkbench().getProgressService().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_message, IProgressMonitor.UNKNOWN);

					//Get repository type (public / private)
					IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
							.createExternalRepositoryInfoService(AbapGitWizardPullV2.this.destination, null);
					AbapGitWizardPullV2.this.cloneData.externalRepoInfo = externalRepoInfoService
							.getExternalRepositoryInfo(AbapGitWizardPullV2.this.selRepoData.getUrl(), "", "", null); //$NON-NLS-1$ //$NON-NLS-2$

					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					if (packageServiceUI.packageExists(AbapGitWizardPullV2.this.destination, packageName, monitor)) {
						List<IAdtObjectReference> packageRefs = packageServiceUI.find(AbapGitWizardPullV2.this.destination, packageName,
								monitor);
						AbapGitWizardPullV2.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
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
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData,
				this.pullAction);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.pageApack = new AbapGitWizardPageApack(this.destination, this.cloneData, this.transportService, true);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);
		this.pageOverwriteObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(
				this.cloneData.repoToModifiedOverwriteObjects, Messages.AbapGitWizardPullSelectedObjects_OverwriteObjectsMessage,
				IMessageProvider.INFORMATION);
		this.pagePackageWarningObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(
				this.cloneData.repoToModifiedPackageWarningObjects,
				Messages.AbapGitWizardPullSelectedObjects_PackageWarningObjectsMessage,
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
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizardPullV2.this.destination,
							monitor);

					//get the selected objects to be pulled
					AbapGitWizardPullV2.this.repoToSelectedObjects = AbapGitUIServiceFactory.createAbapGitPullService()
							.getSelectedObjectsToPullforRepo(AbapGitWizardPullV2.this.pageOverwriteObjectsSelection.getSelectedObjects(),
									AbapGitWizardPullV2.this.pagePackageWarningObjectsSelection.getSelectedObjects());

					//pull the selected objects
					repoService.pullRepository(AbapGitWizardPullV2.this.selRepoData, AbapGitWizardPullV2.this.selRepoData.getBranchName(),
							AbapGitWizardPullV2.this.transportPage.getTransportRequestNumber(), AbapGitWizardPullV2.this.cloneData.user,
							AbapGitWizardPullV2.this.cloneData.pass,
							AbapGitWizardPullV2.this.repoToSelectedObjects.get(AbapGitWizardPullV2.this.selRepoData.getUrl()), monitor);

					if (AbapGitWizardPullV2.this.cloneData.hasDependencies()) {
						pullDependencies(monitor, repoService);
					}
				}

				private void pullDependencies(IProgressMonitor monitor, IRepositoryService repoService) {
					for (IApackDependency apackDependency : AbapGitWizardPullV2.this.cloneData.apackManifest.getDescriptor()
							.getDependencies()) {
						if (apackDependency.requiresSynchronization()) {
							IRepository dependencyRepository = repoService
									.getRepositoryByURL(AbapGitWizardPullV2.this.cloneData.repositories, apackDependency.getGitUrl());
							if (dependencyRepository != null) {
								repoService.pullRepository(dependencyRepository, IApackManifest.MASTER_BRANCH,
										AbapGitWizardPullV2.this.transportPage.getTransportRequestNumber(),
										AbapGitWizardPullV2.this.cloneData.user, AbapGitWizardPullV2.this.cloneData.pass,
										AbapGitWizardPullV2.this.repoToSelectedObjects.get(dependencyRepository.getUrl()),
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
			if (event.getCurrentPage() == AbapGitWizardPullV2.this.pageCredentials
					&& event.getTargetPage() == AbapGitWizardPullV2.this.pageBranchAndPackage) {
				if (!AbapGitWizardPullV2.this.pageCredentials.validateAll()) {
					event.doit = false;
					return;
				}

			}

			//-> Branch & Package page -> Credentials page
			if (event.getCurrentPage() == AbapGitWizardPullV2.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardPullV2.this.pageCredentials) {
				if (!AbapGitWizardPullV2.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Any page
			if (event.getCurrentPage() == AbapGitWizardPullV2.this.pageBranchAndPackage) {
				if (!AbapGitWizardPullV2.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}

			//-> Branch & Package page -> Transport page
			if (event.getCurrentPage() == AbapGitWizardPullV2.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardPullV2.this.transportPage) {
				if (!AbapGitWizardPullV2.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}


			//-> Prepare transport page
			if (event.getTargetPage() == AbapGitWizardPullV2.this.transportPage) {
				try {
					// The transport service requires URIs to objects we want to create in the
					// target package.
					// However, we do not know these URIs from the client.
					// Instead, give it the URI of the package in which we clone.
					IAdtObjectReference packageRef = AbapGitWizardPullV2.this.cloneData.packageRef;
					IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
					checkRef.setUri(packageRef.getUri());
					IAdtTransportCheckData checkData = AbapGitWizardPullV2.this.transportService.check(checkRef, packageRef.getPackageName(),
							true);
					AbapGitWizardPullV2.this.transportPage.setCheckData(checkData);
				} catch (Exception e) {
					AbapGitWizardPullV2.this.pageBranchAndPackage.setMessage(e.getMessage(), DialogPage.ERROR);
				}
			}

			//-> APACK page -> Any page
			if (event.getCurrentPage() == AbapGitWizardPullV2.this.pageApack) {
				if(!AbapGitWizardPullV2.this.pageApack.validateAll()) {
				event.doit = false;
				return;
			}
		}
	}

	}
}
