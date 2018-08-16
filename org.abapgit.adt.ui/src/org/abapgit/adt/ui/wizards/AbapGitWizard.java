package org.abapgit.adt.ui.wizards;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.Repository;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.wizard.Wizard;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.project.ui.util.ProjectUtil;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

public class AbapGitWizard extends Wizard {

	protected WizardPageOne one;
	protected WizardPageTwo two;
	protected WizardPageThree three;
	protected WizardPageFour four;
	// avRepos = Repository.list();

	public AbapGitWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "abapGit Wizard";
	}

	@Override
	public void addPages() {
		one = new WizardPageOne();
		two = new WizardPageTwo();
		three = new WizardPageThree();
		four = new WizardPageFour();
		addPage(one);
		addPage(two);
		addPage(three);
		addPage(four);
	}

	public String createPostXML() {
		// quick and dirty
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<asx:abap xmlns:asx=\"http://www.sap.com/abapxml\" version=\"1.0\">" + "<asx:values>" + "<ROOT>"
				+ "<URL>" + one.getTxtUrl() + "</URL>" + "<BRANCH_NAME>" + three.getTxtBranch() + "</BRANCH_NAME>"
				+ "<PACKAGE>" + three.getTxtPackage() + "</PACKAGE>" + "<TR_NAME>" + four.getTxtTr() + "</TR_NAME>"
				+ "<USER>" + two.getTxtUser() + "</USER>" + "<PWD>" + two.getTxtPwd() + "</PWD>" + "</ROOT>"
				+ "</asx:values></asx:abap>";

		return xml;

	}

	@Override
	public boolean performFinish() {
		IProgressMonitor monitor = null;

		IProject project = ProjectUtil.getActiveAdtCoreProject(null, null, null, IAbapProject.ABAP_PROJECT_NATURE);

		if (project == null) {
			return false;
		}

		IStatus logonStatus = AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(project);
		if (!logonStatus.isOK()) {
			return false;
		}

		String destinationId = AdtProjectServiceFactory.createProjectService().getDestinationId(project);

		IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(destinationId, monitor);

		IRepository repo = new Repository();
		repo.setUrl(one.getTxtUrl());
		repo.setBranch(three.getTxtBranch());
		repo.setPackage(three.getTxtPackage());
		repo.setTransportRequest(four.getTxtTr());

		if (!two.getTxtUser().isEmpty() && !two.getTxtPwd().isEmpty()) {
			repo.setUser(two.getTxtUser());
			repo.setPassword(two.getTxtPwd());
		}

		repoService.cloneRepository(repo, monitor);

		return true;
	}

}
