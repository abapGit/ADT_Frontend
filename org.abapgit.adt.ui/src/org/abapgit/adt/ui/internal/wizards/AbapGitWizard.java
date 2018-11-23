package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IRepositories;
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

		setDefaultPageImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID,
				"icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		this.pageRepo = new AbapGitWizardPageRepositoryAndCredentials(this.project, this.destination, this.cloneData);
		this.pageBranchAndPackage = new AbapGitWizardPageBranchAndPackage(this.project, this.destination, this.cloneData);
		this.pageApack = new AbapGitWizardPageApack(this.destination, this.cloneData);
		this.transportService = AdtTransportServiceFactory.createTransportService(this.destination);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(this.transportService);
		addPage(this.pageRepo);
		addPage(this.pageBranchAndPackage);
		addPage(this.pageApack);
		addPage(this.transportPage);
	}

	@Override
	public boolean performFinish() {
		try {
			String transportRequestNumber = this.transportPage.getTransportRequestNumber();
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizard_task_cloning_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizard.this.destination,
							monitor);
					repoService.cloneRepository(AbapGitWizard.this.cloneData.url, AbapGitWizard.this.cloneData.branch, AbapGitWizard.this.cloneData.packageRef.getName(),
							transportRequestNumber, AbapGitWizard.this.cloneData.user, AbapGitWizard.this.cloneData.pass, monitor);
				}
			});
			return true;
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			((WizardPage) getContainer().getCurrentPage()).setPageComplete(false);
			((WizardPage) getContainer().getCurrentPage()).setMessage(e.getTargetException().getMessage(),
					DialogPage.ERROR);
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

			if (event.getCurrentPage() == AbapGitWizard.this.pageRepo && event.getTargetPage() == AbapGitWizard.this.pageBranchAndPackage) {
				if (!AbapGitWizard.this.pageRepo.validateAll()) {
					event.doit = false;
					return;
				}
			}
			if (event.getCurrentPage() == AbapGitWizard.this.pageBranchAndPackage) {
				if (!AbapGitWizard.this.pageBranchAndPackage.validateAll()) {
					event.doit = false;
					return;
				}
			}
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
		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page.equals(this.pageBranchAndPackage) && this.cloneData.apackManifest != null
				&& !this.cloneData.apackManifest.hasDependencies()) {
			// If we don't have APACK dependencies, we can skip the APACK-related page
			return this.transportPage;
		} else {
			return super.getNextPage(page);
		}

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
	}

}
