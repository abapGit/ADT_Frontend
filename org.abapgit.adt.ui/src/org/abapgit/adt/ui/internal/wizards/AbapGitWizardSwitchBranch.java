package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.exceptions.PackageRefNotFoundException;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class AbapGitWizardSwitchBranch extends Wizard {

	private final IProject project;
	final CloneData cloneData;
	public IRepository selRepoData;
	private final String destination;
	private PageChangeListener pageChangeListener;
	AbapGitWizardPageRepositoryAndCredentials pageCredentials;
	AbapGitWizardPageSwitchBranchAndPackage pageBranchAndPackage;

	public AbapGitWizardSwitchBranch(IProject project, IRepository selRepo, String destination) throws PackageRefNotFoundException {
		this.project = project;
		this.cloneData = new CloneData();
		this.destination = destination;
		this.selRepoData = selRepo;
		this.cloneData.url = selRepo.getUrl();
		this.cloneData.branch = selRepo.getBranchName();

		if (!getPackageAndRepoType() && this.cloneData.packageRef == null) {
			String error = NLS.bind(Messages.AbapGitWizardSwitch_branch_package_ref_not_found_error, this.selRepoData.getPackage());
			throw new PackageRefNotFoundException(error);
		}

		setWindowTitle(Messages.AbapGitWizardSwitch_branch_wizard_title);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	private void getRepositoryAccessMode() {
		//Get external repo info for repository type (public / private)
		IExternalRepositoryInfoService externalRepoInfoService = RepositoryServiceFactory
				.createExternalRepositoryInfoService(AbapGitWizardSwitchBranch.this.destination, null);
		AbapGitWizardSwitchBranch.this.cloneData.externalRepoInfo = externalRepoInfoService
				.getExternalRepositoryInfo(AbapGitWizardSwitchBranch.this.selRepoData.getUrl(), "", "", null); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void getPackageRef(String packageName, IProgressMonitor monitor) {
		IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
		if (packageServiceUI.packageExists(AbapGitWizardSwitchBranch.this.destination, packageName, monitor)) {
			List<IAdtObjectReference> packageRefs = packageServiceUI.find(AbapGitWizardSwitchBranch.this.destination, packageName, monitor);
			AbapGitWizardSwitchBranch.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
		}
		else {
			String error = NLS.bind(Messages.AbapGitWizardSwitch_branch_package_ref_not_found_error, this.selRepoData.getPackage());
			throw new PackageRefNotFoundException(error);
		}
	}

	public boolean getPackageAndRepoType() {
		String packageName = AbapGitWizardSwitchBranch.this.selRepoData.getPackage();
		//get package refs
		getPackageRef(packageName, new NullProgressMonitor());
		// fetches repository access mode
		getRepositoryAccessMode();
		return true;
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
		this.pageCredentials = new AbapGitWizardPageSwitchBranchCredentials(this.project, this.destination, this.cloneData);
		this.pageBranchAndPackage = new AbapGitWizardPageSwitchBranchAndPackage(this.project, this.destination, this.cloneData);
		addPage(this.pageCredentials);
		addPage(this.pageBranchAndPackage);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					IRepositoryService repoService = RepositoryServiceFactory
							.createRepositoryService(AbapGitWizardSwitchBranch.this.destination, monitor);

					// Unlink
					repoService.unlinkRepository(AbapGitWizardSwitchBranch.this.selRepoData.getKey(), monitor);

					// Relink
					repoService.cloneRepository(AbapGitWizardSwitchBranch.this.selRepoData.getUrl(),
							AbapGitWizardSwitchBranch.this.cloneData.branch, AbapGitWizardSwitchBranch.this.selRepoData.getPackage(),
							AbapGitWizardSwitchBranch.this.selRepoData.getFolderLogic(),
							AbapGitWizardSwitchBranch.this.selRepoData.getTransportRequest(),
							AbapGitWizardSwitchBranch.this.cloneData.user, AbapGitWizardSwitchBranch.this.cloneData.pass, monitor)
							.getAbapObjects();
//
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getMessage(), DialogPage.ERROR);
		}
		return true;
	}

	final class PageChangeListener implements IPageChangingListener {
		@Override
		public void handlePageChanging(final PageChangingEvent event) {
			//-> Credentials page -> Branch & Package page
			if (event.getCurrentPage() == AbapGitWizardSwitchBranch.this.pageCredentials
					&& event.getTargetPage() == AbapGitWizardSwitchBranch.this.pageBranchAndPackage) {
				if (!AbapGitWizardSwitchBranch.this.pageCredentials.validateAll()) {
					event.doit = false;
					return;
				}
			}
		}
	}
}
