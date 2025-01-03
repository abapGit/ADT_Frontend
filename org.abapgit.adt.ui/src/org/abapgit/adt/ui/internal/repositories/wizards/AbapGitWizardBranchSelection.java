package org.abapgit.adt.ui.internal.repositories.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPageRepositoryAndCredentials;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class AbapGitWizardBranchSelection extends Wizard {

	private final IProject project;
	final CloneData cloneData;
	public IRepository selRepoData;
	private final String destination;
	private IAbapGitService abapGitService;
	private PageChangeListener pageChangeListener;
	AbapGitWizardPageRepositoryAndCredentials pageCredentials;
	AbapGitWizardPageBranchSelection pageBranchAndPackage;

	public AbapGitWizardBranchSelection(IProject project, IRepository selRepo, String destination) {
		this.project = project;
		this.cloneData = new CloneData();
		this.destination = destination;
		this.selRepoData = selRepo;
		this.cloneData.url = selRepo.getUrl();
		this.cloneData.branch = selRepo.getBranchName();
		if (this.abapGitService == null) {
			this.abapGitService = AbapGitUIServiceFactory.createAbapGitService();
		}
		getPackageAndRepoType();

		setWindowTitle(Messages.AbapGitView_action_select_branch);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	public Object getProject() {
		return this.project;
	}

	public Object getSelectedRepository() {
		return this.selRepoData;
	}

	public boolean getPackageAndRepoType() {

		try {
			String packageName = AbapGitWizardBranchSelection.this.selRepoData.getPackage();
			PlatformUI.getWorkbench().getProgressService().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_message, IProgressMonitor.UNKNOWN);

					//Get repository type (public / private)
					IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
							.createExternalRepositoryInfoService(AbapGitWizardBranchSelection.this.destination, null);
					AbapGitWizardBranchSelection.this.cloneData.externalRepoInfo = externalRepoInfoService
							.getExternalRepositoryInfo(AbapGitWizardBranchSelection.this.selRepoData.getUrl(), "", "", null); //$NON-NLS-1$ //$NON-NLS-2$

					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					if (packageServiceUI.packageExists(AbapGitWizardBranchSelection.this.destination, packageName, monitor)) {
						List<IAdtObjectReference> packageRefs = packageServiceUI.find(AbapGitWizardBranchSelection.this.destination,
								packageName, monitor);
						AbapGitWizardBranchSelection.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
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
	public void setContainer(IWizardContainer wizardContainer) {
		super.setContainer(wizardContainer);

		if (this.pageChangeListener == null && wizardContainer != null) {
			Assert.isLegal(wizardContainer instanceof WizardDialog, "Wizard container must be of type WizardDialog"); //$NON-NLS-1$

			this.pageChangeListener = new PageChangeListener();
			((WizardDialog) wizardContainer).addPageChangingListener(this.pageChangeListener);

		}
	}

	@Override
	public void addPages() {
		this.pageCredentials = new AbapGitWizardPageBranchSelectionCredentials(this.project, this.destination, this.cloneData);
		this.pageBranchAndPackage = new AbapGitWizardPageBranchSelection(this.project, this.destination, this.cloneData, false);
		addPage(this.pageCredentials);
		addPage(this.pageBranchAndPackage);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					// Unlink
					RepositoryServiceFactory
							.createRepositoryService(AbapGitWizardBranchSelection.this.abapGitService
									.getDestination(AbapGitWizardBranchSelection.this.project), monitor)
							.unlinkRepository(AbapGitWizardBranchSelection.this.selRepoData.getKey(), monitor);
					// Relink
					IRepositoryService repoService = RepositoryServiceFactory
							.createRepositoryService(AbapGitWizardBranchSelection.this.destination, monitor);
					repoService.cloneRepository(AbapGitWizardBranchSelection.this.selRepoData.getUrl(),
							AbapGitWizardBranchSelection.this.cloneData.branch, AbapGitWizardBranchSelection.this.selRepoData.getPackage(),
							AbapGitWizardBranchSelection.this.selRepoData.getFolderLogic(),
							AbapGitWizardBranchSelection.this.selRepoData.getTransportRequest(),
							AbapGitWizardBranchSelection.this.cloneData.user, AbapGitWizardBranchSelection.this.cloneData.pass, monitor)
							.getAbapObjects();
//
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	final class PageChangeListener implements IPageChangingListener {
		@Override
		public void handlePageChanging(final PageChangingEvent event) {
			//-> Credentials page -> Branch & Package page
			if (event.getCurrentPage() == AbapGitWizardBranchSelection.this.pageCredentials
					&& event.getTargetPage() == AbapGitWizardBranchSelection.this.pageBranchAndPackage) {
				if (!AbapGitWizardBranchSelection.this.pageCredentials.validateAll()) {
					event.doit = false;
					return;
				}

			}

			//-> Branch & Package page -> Credentials page
			if (event.getCurrentPage() == AbapGitWizardBranchSelection.this.pageBranchAndPackage
					&& event.getTargetPage() == AbapGitWizardBranchSelection.this.pageCredentials) {
				if (AbapGitWizardBranchSelection.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}
		}
	}
}
