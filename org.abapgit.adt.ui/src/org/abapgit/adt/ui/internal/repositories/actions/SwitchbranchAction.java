package org.abapgit.adt.ui.internal.repositories.actions;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.repositories.exceptions.PackageRefNotFoundException;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardSwitchBranch;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class SwitchbranchAction extends Action {

	private IRepository selRepo;
	private final AbapGitView AbapGitView;
	private IProject project;

	public SwitchbranchAction(AbapGitView view) {
		super(Messages.AbapGitView_action_switch_branch);
		setToolTipText(Messages.AbapGitView_action_switch_branch);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "/icons/etool/branches_obj.png")); //$NON-NLS-1$
		this.AbapGitView = view;
	}

	@Override
	public void run() {
		this.project = getProject();
		this.selRepo = getRepository();
		if (this.selRepo != null) {
			String destination = AdtProjectServiceFactory.createProjectService().getDestinationId(this.project);
			if (getPackageAndRepoType(destination)) {
				WizardDialog dialog = new WizardDialog(this.AbapGitView.getViewSite().getShell(),
						new AbapGitWizardSwitchBranch(this.project, this.selRepo, destination));
				dialog.open();
			}
		}
		this.AbapGitView.refresh();

	}

	private IRepository getRepository() {
		return this.AbapGitView.getRepositorySelection();
	}

	private IProject getProject() {
		return this.AbapGitView.getProject();
	}

	private void getPackageRef(String packageName, String destination, IProgressMonitor monitor) {
		IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
		if (!packageServiceUI.packageExists(destination, packageName, monitor)) {
			// Throw PackageRefNotFound Exception if packagerServiceUI can't find package
			String error = NLS.bind(Messages.AbapGitWizardSwitch_branch_package_ref_not_found_error, packageName);
			throw new PackageRefNotFoundException(error);
		}
	}

	public boolean getPackageAndRepoType(String destination) {
		try {
			String packageName = this.selRepo.getPackage();
			// checking if the package ref exsist
			getPackageRef(packageName, destination, null);
			return true;
		} catch (PackageRefNotFoundException e) {
			Throwable cause = e.getCause() != null ? e.getCause() : e;
			String message = (cause.getMessage() != null) ? cause.getMessage() : "An unknown error has occurred"; //$NON-NLS-1$
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(shell, "Error", message); //$NON-NLS-1$
			return false;
		}

	}
}
