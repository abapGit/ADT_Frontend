package org.abapgit.adt.ui.internal.staging.actions;

import java.util.List;

import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingView;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.sap.adt.tools.core.ui.navigation.AdtNavigationServiceFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class OpenPackageAction extends BaseSelectionListenerAction {

	private final IAbapGitStagingView view;
	private final IAbapGitService service;

	public OpenPackageAction(IAbapGitStagingView view) {
		super(Messages.AbapGitView_action_open);
		setToolTipText(Messages.AbapGitView_action_open_xtol);
		setImageDescriptor(
				com.sap.adt.tools.core.ui.Activator.getDefault().getImageDescriptor(com.sap.adt.tools.core.ui.ISharedImages.PACKAGE));

		this.view = view;
		this.service = AbapGitUIServiceFactory.createAbapGitService();
	}

	public void run() {
		if (this.view.getRepository() != null && this.view.getProject() != null) {
			if (!this.service.ensureLoggedOn(this.view.getProject())) {
				return;
			}
			openPackage();
		}
	}

	private void openPackage() {
		IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
		List<com.sap.adt.tools.core.model.adtcore.IAdtObjectReference> pkgRefs = packageServiceUI.find(
				this.service.getDestination(this.view.getProject()), this.view.getRepository().getPackage(), null);
		if (!pkgRefs.isEmpty()) {
			com.sap.adt.tools.core.model.adtcore.IAdtObjectReference gitPackageRef = pkgRefs.stream().findFirst().get();
			if (gitPackageRef != null) {
				AdtNavigationServiceFactory.createNavigationService().navigate(this.view.getProject(), gitPackageRef, true);
			}
		}
	}

}
