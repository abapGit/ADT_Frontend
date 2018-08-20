package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.ui.AbapGitUIPlugin;
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

	private WizardPageOne one;
	private WizardPageThree three;
	private IAdtTransportService transportService;
	private IAdtTransportSelectionWizardPage transportPage;

	public AbapGitWizard(IProject project) {
		this.project = project;
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		cloneData = new CloneData();

		setWindowTitle("Clone git repository");

		setNeedsProgressMonitor(true);

		setDefaultPageImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID,
				"icons/wizban/abapgit_import_wizban.png"));
	}

	@Override
	public void addPages() {
		one = new WizardPageOne(destination, cloneData);
		three = new WizardPageThree(project, destination, cloneData);
		transportService = AdtTransportServiceFactory.createTransportService(destination);
		this.transportPage = AdtTransportSelectionWizardPageFactory.createTransportSelectionPage(transportService);
		addPage(one);
		addPage(three);
		addPage(transportPage);
	}

	@Override
	public boolean performFinish() {
		try {
			String transportRequestNumber = transportPage.getTransportRequestNumber();
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destination,
							monitor);
					repoService.cloneRepository(cloneData.url, cloneData.branch, cloneData.packageRef.getName(),
							transportRequestNumber, cloneData.user, cloneData.pass, monitor);
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

			final WizardPage currentPage = (WizardPage) event.getCurrentPage();
			final WizardPage targetPage = (WizardPage) event.getTargetPage();

			if (currentPage == one && targetPage == three) { // NOPMD
				if (!one.validateAll()) {
					event.doit = false;
					return;
				}
			} else if (currentPage == three && targetPage == transportPage) { // NOPMD
				if (!three.validateAll()) {
					event.doit = false;
					return;
				}
				try {
					// The transport service requires URIs to objects we want to create in the
					// target package.
					// However, we do not know these URIs from the client.
					// Instead, give it the URI of the package in which we clone.
					IAdtObjectReference packageRef = cloneData.packageRef;
					IAdtObjectReference checkRef = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
					checkRef.setUri(packageRef.getUri());
					IAdtTransportCheckData checkData = transportService.check(checkRef, packageRef.getPackageName(),
							true);
					transportPage.setCheckData(checkData);
				} catch (Exception e) {
					currentPage.setMessage(e.getMessage(), DialogPage.ERROR);
				}
			}
		}
	}

	/**
	 * Simple data exchange object for the wizard and its pages. This might be
	 * refined in the future, e.g. by using databinding.
	 */
	static class CloneData {
		public IRepositories repositories;
		public IExternalRepositoryInfo externalRepoInfo;
		public IAdtObjectReference packageRef;
		public String branch;
		public String url;
		public String user;
		public String pass;
	}

}
