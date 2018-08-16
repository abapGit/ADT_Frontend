package org.abapgit.adt.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.transport.AdtTransportServiceFactory;
import com.sap.adt.transport.ui.wizard.AdtTransportSelectionWizardPageFactory;
import com.sap.adt.transport.ui.wizard.IAdtTransportSelectionWizardPage;

public class AbapGitWizard extends Wizard {

	private final IProject project;
	private final String destination;
	private PageChangeListener pageChangeListener;

	private WizardPageOne one;
	private WizardPageTwo two;
	private WizardPageThree three;
	// private WizardPageFour four;
	private IAdtTransportSelectionWizardPage transportPage;

	public AbapGitWizard(IProject project) {
		this.project = project;
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);

		setNeedsProgressMonitor(true);

		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		URL url = FileLocator.find(bundle, new Path("icons/wizban/abapgit_import_wizban.png"), null);
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url);
		setDefaultPageImageDescriptor(imageDescriptor);
	}

	@Override
	public String getWindowTitle() {
		return "abapGit Wizard";
	}

	@Override
	public void addPages() {
		one = new WizardPageOne();
		two = new WizardPageTwo();
		three = new WizardPageThree(project, destination);
		this.transportPage = AdtTransportSelectionWizardPageFactory
				.createTransportSelectionPage(AdtTransportServiceFactory.createTransportService(destination));
		addPage(one);
		addPage(two);
		addPage(three);
		addPage(transportPage);
	}

	@Override
	public boolean performFinish() {
		IStatus logonStatus = AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project);
		if (!logonStatus.isOK()) {
			return false;
		}

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destination,
							monitor);

					repoService.cloneRepository(one.getTxtUrl(), three.getTxtBranch(), three.getTxtPackage(),
							transportPage.getTransportRequestNumber(), two.getTxtUser(), two.getTxtPwd(), monitor);
				}
			});
			return true;
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO
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

			final IWizardPage currentPage = (IWizardPage) event.getCurrentPage();
			// final IWizardPage targetPage = (IWizardPage) event.getTargetPage();

			try {
				getContainer().run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						if (currentPage == three) { // NOPMD
							three.callValidateInputFinal(monitor);
						}
					}
				});
			} catch (InvocationTargetException e) {
				event.doit = false;
			} catch (InterruptedException e) {
				event.doit = false;
			}
		}
	}

}
