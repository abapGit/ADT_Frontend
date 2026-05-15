package org.abapgit.adt.ui.internal.repositories.actions;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class OpenPackageAction extends Action {

	private final AbapGitView view;

	public OpenPackageAction(AbapGitView view) {
		super(Messages.AbapGitView_action_open);
		this.view = view;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	}

	@Override
	public void run() {
		if (!AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(this.view.getLastProject()).isOK()) {
			return;
		}

		IRepository currRepository = null;
		Object firstElement = this.view.getTableViewer().getStructuredSelection().getFirstElement();

		if (firstElement instanceof IRepository) {
			currRepository = ((IRepository) firstElement);
		}

		if (currRepository != null) {
			IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
			String destinationId = this.view.getAbapGitService().getDestination(this.view.getLastProject());
			List<IAdtObjectReference> pkgRefs = packageServiceUI.find(destinationId, currRepository.getPackage(), null);
			IProject currProject = this.view.getLastProject();
			if (!pkgRefs.isEmpty()) {
				IAdtObjectReference gitPackageRef = pkgRefs.stream().findFirst().get();
				if (gitPackageRef != null) {
					AdtNavigationServiceFactory.createNavigationService().navigate(currProject, gitPackageRef, true);
				}
			}
		}
	}
}
