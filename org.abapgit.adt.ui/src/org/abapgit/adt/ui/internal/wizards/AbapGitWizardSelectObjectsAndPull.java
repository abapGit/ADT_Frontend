package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.ModifiedObjectsForRepository;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.sap.adt.tools.core.project.AdtProjectServiceFactory;

public class AbapGitWizardSelectObjectsAndPull extends Wizard {

	private final String destination;
	private final CloneData cloneData;
	Map<String, IAbapGitPullModifiedObjects> repoToSelectedObjectsMap;
	private AbapGitWizardPageObjectsSelectionForPull pageOverwriteObjectsSelection;
	private AbapGitWizardPageObjectsSelectionForPull pagePackageWarningObjectsSelection;
	String transportRequest;

	public AbapGitWizardSelectObjectsAndPull(IProject project, CloneData cloneData,
			String transportRequest) {
		this.destination = AdtProjectServiceFactory.createProjectService().getDestinationId(project);
		this.cloneData = cloneData;
		this.repoToSelectedObjectsMap = new HashMap<String, IAbapGitPullModifiedObjects>();
		this.transportRequest = transportRequest;
		fetchModifiedObjects(); //fetch the modified objects for repository and its dependencies

		setWindowTitle(Messages.AbapGitWizardPull_title);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin(AbapGitUIPlugin.PLUGIN_ID, "icons/wizban/abapGit_import_wizban.png")); //$NON-NLS-1$
	}

	private void fetchModifiedObjects() {
		IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());
		IRepositories repositories = repoService.getRepositories(new NullProgressMonitor());

		IAbapGitPullModifiedObjects abapPullModifiedObjects = repoService.getModifiedObjects(new NullProgressMonitor(),
				repoService.getRepositoryByURL(repositories, this.cloneData.url),
				this.cloneData.user, this.cloneData.pass);

		if (!abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects().isEmpty()) {
			this.cloneData.repoToModifiedOverwriteObjects.add(new ModifiedObjectsForRepository(this.cloneData.url,
					new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects())));
		}

		if (!abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects().isEmpty()) {
			this.cloneData.repoToModifiedPackageWarningObjects.add(new ModifiedObjectsForRepository(this.cloneData.url,
					new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects())));
		}

		if (this.cloneData.hasDependencies()) {
			for (IApackDependency apackDependency : this.cloneData.apackManifest.getDescriptor().getDependencies()) {
				IRepository dependencyRepository = repoService.getRepositoryByURL(repositories, apackDependency.getGitUrl());
				if (dependencyRepository != null) {
					abapPullModifiedObjects = repoService.getModifiedObjects(new NullProgressMonitor(),
							dependencyRepository, this.cloneData.user,
							this.cloneData.pass);

					if (!abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects().isEmpty()) {
						this.cloneData.repoToModifiedOverwriteObjects.add(new ModifiedObjectsForRepository(dependencyRepository.getUrl(),
								new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects())));
					}

					if (!abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects().isEmpty()) {
						this.cloneData.repoToModifiedPackageWarningObjects.add(new ModifiedObjectsForRepository(
								dependencyRepository.getUrl(),
								new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects())));
					}

				}
			}
		}

		}

	@Override
	public void addPages() {

		this.pageOverwriteObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(this.cloneData.repoToModifiedOverwriteObjects,
				Messages.AbapGitWizardPullSelectedObjects_OverwriteObjectsMessage,
				IMessageProvider.INFORMATION);
		this.pagePackageWarningObjectsSelection = new AbapGitWizardPageObjectsSelectionForPull(
				this.cloneData.repoToModifiedPackageWarningObjects,
				Messages.AbapGitWizardPullSelectedObjects_PackageWarningObjectsMessage,
				IMessageProvider.WARNING);

		addPage(this.pageOverwriteObjectsSelection);
		addPage(this.pagePackageWarningObjectsSelection);

	}

	@Override
	public boolean performFinish() {

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.AbapGitWizard_task_pulling_repository, IProgressMonitor.UNKNOWN);
					IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(AbapGitWizardSelectObjectsAndPull.this.destination,
							monitor);
					IRepositories repositories = repoService.getRepositories(monitor);

					getSelectedObjectsToPull();

					IRepository repository = repoService.getRepositoryByURL(repositories,
							AbapGitWizardSelectObjectsAndPull.this.cloneData.url);
					repoService.pullRepository(repository, repository.getBranchName(),
							AbapGitWizardSelectObjectsAndPull.this.transportRequest,
							AbapGitWizardSelectObjectsAndPull.this.cloneData.user, AbapGitWizardSelectObjectsAndPull.this.cloneData.pass,
							AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap
									.get(repository.getUrl()),
							monitor);

					if (AbapGitWizardSelectObjectsAndPull.this.cloneData.hasDependencies()) {
						pullDependencies(monitor, repoService);
					}
				}

				private void getSelectedObjectsToPull() {
					//To create a map from repository to the selected IAbapGitPullModifiedObjects (including both Overwrite and package Warning Objects)

					List<ModifiedObjectsForRepository> overwriteObjectsSelectedToPull = AbapGitWizardSelectObjectsAndPull.this.pageOverwriteObjectsSelection
							.getSelectedObjects();
					List<ModifiedObjectsForRepository> packageWarningObjectsSelectedToPull = AbapGitWizardSelectObjectsAndPull.this.pagePackageWarningObjectsSelection
							.getSelectedObjects();

					//Loop over selected overwrite objects for all repositories and insert in repoToSelectedObjectsMap
					for (ModifiedObjectsForRepository obj : overwriteObjectsSelectedToPull) {
						IAbapGitPullModifiedObjects objectsToPull = IAgitpullmodifiedobjectsFactory.eINSTANCE
								.createAbapGitPullModifiedObjects();

						if (!obj.getModifiedObjects().isEmpty()) {
							IOverwriteObjects overwriteObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObjects();
							overwriteObjects.getAbapgitobjects().addAll(obj.getModifiedObjects());
							objectsToPull.setOverwriteObjects(overwriteObjects);
						}

						AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap.put(obj.getRepositoryURL(), objectsToPull);
					}

					//Loop over selected package warning objects for all repositories and insert in repoToSelectedObjectsMap
					for (ModifiedObjectsForRepository obj : packageWarningObjectsSelectedToPull) {
						IAbapGitPullModifiedObjects objectsToPull = IAgitpullmodifiedobjectsFactory.eINSTANCE
								.createAbapGitPullModifiedObjects();

						if (!obj.getModifiedObjects().isEmpty()) {
							IPackageWarningObjects packageWarningObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE
									.createPackageWarningObjects();
							packageWarningObjects.getAbapgitobjects().addAll(obj.getModifiedObjects());
							objectsToPull.setPackageWarningObjects(packageWarningObjects);

							// if the repoToSelectedObjectsMap doesn't already have an entry for the repository from filling in overwrite objects, create an entry in the map
							// else fill in the package warning objects
							if (AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap.get(obj.getRepositoryURL()) == null) {
								AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap.put(obj.getRepositoryURL(), objectsToPull);
							} else {
								AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap.get(obj.getRepositoryURL())
										.setPackageWarningObjects(packageWarningObjects);
							}

						}

					}

	}

				private void pullDependencies(IProgressMonitor monitor, IRepositoryService repoService) {
					for (IApackDependency apackDependency : AbapGitWizardSelectObjectsAndPull.this.cloneData.apackManifest.getDescriptor()
							.getDependencies()) {
						if (apackDependency.requiresSynchronization()) {
							IRepository dependencyRepository = RepositoryServiceFactory.createRepositoryService(AbapGitWizardSelectObjectsAndPull.this.destination,
									monitor).getRepositoryByURL(repoService.getRepositories(monitor), apackDependency.getGitUrl());
							if (dependencyRepository != null) {
								repoService.pullRepository(dependencyRepository, IApackManifest.MASTER_BRANCH,
										AbapGitWizardSelectObjectsAndPull.this.transportRequest,
										AbapGitWizardSelectObjectsAndPull.this.cloneData.user,
										AbapGitWizardSelectObjectsAndPull.this.cloneData.pass,
										AbapGitWizardSelectObjectsAndPull.this.repoToSelectedObjectsMap.get(dependencyRepository.getUrl()),
										monitor);
							}
						}
					}
				}
			});

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

		if (wizardContainer != null) {
			Assert.isLegal(wizardContainer instanceof WizardDialog, "Wizard container must be of type WizardDialog"); //$NON-NLS-1$

		}
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {

		if (page.equals(this.pageOverwriteObjectsSelection)) {
			if (this.cloneData.repoToModifiedPackageWarningObjects == null
					|| this.cloneData.repoToModifiedPackageWarningObjects.isEmpty()) {
				return null;
			} else {
				return this.pagePackageWarningObjectsSelection;
			}
		}

			return super.getNextPage(page);
	}

}

