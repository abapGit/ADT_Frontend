package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.dialogs.AbapGitDialogImport;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
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

public class AbapGitWizardPull extends Wizard {

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;
	private PageChangeListener pageChangeListener;

	private AbapGitWizardPageRepositoryAndCredentials pageCredentials;
	private AbapGitWizardPageBranchAndPackage pageBranchAndPackage;
	public IAdtTransportService transportService;
	public IAdtTransportSelectionWizardPage transportPage;
	public IRepository selRepoData;

	public AbapGitWizardPull(IProject project, IRepository selRepo) {
		this.project = project;
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		this.cloneData = new CloneData();
		this.selRepoData = selRepo;
		this.cloneData.url = selRepo.getUrl();

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

		this.pageCredentials = new AbapGitWizardPageRepositoryAndCredentials(this.project, this.destination, this.cloneData);
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);

		addPage(this.pageCredentials);
		addPage(this.pageBranchAndPackage);
		addPage(this.transportPage);

	}

//	@Override
//	public boolean needsPreviousAndNextButtons() {
//
//		return this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PRIVATE;
//
//	}

	@Override
	public boolean performFinish() {
		List<IObject> pullObjects = new LinkedList<>();

		try {
			String transportRequestNumber = this.transportPage.getTransportRequestNumber();
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizardPull.this.destination,
							monitor);

					List<IObject> abapObjects = repoService.pullRepository(AbapGitWizardPull.this.selRepoData,
							AbapGitWizardPull.this.selRepoData.getBranch(), transportRequestNumber, AbapGitWizardPull.this.cloneData.user,
							AbapGitWizardPull.this.cloneData.pass, monitor).getObjects();
					pullObjects.addAll(abapObjects);
				}
			});

			TitleAreaDialog dialog = new AbapGitDialogImport(getShell(), pullObjects);
			int dialogResult = dialog.open();

			if (dialogResult == dialog.CANCEL) {
				System.out.println("CANCEL presed");
				return false;
			}

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

//	private final class PageChangeListener implements IPageChangingListener {
//		@Override
//		public void handlePageChanging(final PageChangingEvent event) {
//
//			if (event.getCurrentPage() == AbapGitWizardPull.this.pageBranchAndPackage
//					&& event.getTargetPage() == AbapGitWizardPull.this.transportPage) {
//
//				if (!AbapGitWizardPull.this.pageBranchAndPackage.validateAll()) {
//					event.doit = false;
//					return;
//				}
//
//				try {
//					// The transport service requires URIs to objects we want to create in the
//					// target package.
//					// However, we do not know these URIs from the client.
//					// Instead, give it the URI of the package in which we clone.
//					IAdtObjectReference packageRef = AbapGitWizardPull.this.cloneData.packageRef;
//					IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
//					checkRef.setUri(packageRef.getUri());
//
//					IAdtTransportCheckData checkData = AbapGitWizardPull.this.transportService.check(checkRef, packageRef.getPackageName(),
//							true);
//					AbapGitWizardPull.this.transportPage.setCheckData(checkData);
//				} catch (Exception e) {
//					AbapGitWizardPull.this.transportPage.setErrorMessage(e.getMessage());
//				}
//
//			}
//		}
//
//	}

	private final class PageChangeListener implements IPageChangingListener {
		@Override
		public void handlePageChanging(final PageChangingEvent event) {

			if (event.getCurrentPage() == AbapGitWizardPull.this.pageCredentials
					&& event.getTargetPage() == AbapGitWizardPull.this.pageBranchAndPackage) {
				if (!AbapGitWizardPull.this.pageCredentials.validateAll()) {
					event.doit = false;
					return;
				}
			} else if (event.getCurrentPage() == AbapGitWizardPull.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardPull.this.transportPage) {
				if (!AbapGitWizardPull.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
				try {
					// The transport service requires URIs to objects we want to create in the
					// target package.
					// However, we do not know these URIs from the client.
					// Instead, give it the URI of the package in which we clone.
					IAdtObjectReference packageRef = AbapGitWizardPull.this.cloneData.packageRef;
					IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
					checkRef.setUri(packageRef.getUri());
					IAdtTransportCheckData checkData = AbapGitWizardPull.this.transportService.check(checkRef, packageRef.getPackageName(),
							true);
					AbapGitWizardPull.this.transportPage.setCheckData(checkData);
				} catch (Exception e) {
					AbapGitWizardPull.this.pageBranchAndPackage.setMessage(e.getMessage(), DialogPage.ERROR);
			}
		}

	}
	}

}
