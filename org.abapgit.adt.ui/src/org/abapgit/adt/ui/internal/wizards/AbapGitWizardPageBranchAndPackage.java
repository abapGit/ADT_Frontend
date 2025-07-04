package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.abapgit.adt.backend.ApackServiceFactory;
import org.abapgit.adt.backend.IApackGitManifestService;
import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository.FolderLogic;
import org.abapgit.adt.ui.AbapGitUIPlugin;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.sap.adt.communication.exceptions.CommunicationException;
import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageProposalProviderFactory;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageProposalProvider;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;
import com.sap.adt.util.ui.swt.AdtSWTUtilFactory;

public class AbapGitWizardPageBranchAndPackage extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageBranchAndPackage.class.getName();

	private final IProject project;
	private final String destination;
	private final CloneData cloneData;
	private final String pullBranch;

	protected Button checkbox_lnp;
	private Boolean chboxLinkAndPull;
	protected TextViewer txtPackage;
	protected ComboViewer comboBranches;

	private final Boolean pullAction;
	private boolean backButtonEnabled = true;
	private final ApackParameters lastApackCall;

	private ProgressBar progressBar;

	private Label progressBarLabel;

	public AbapGitWizardPageBranchAndPackage(IProject project, String destination, CloneData cloneData, Boolean pullAction) {
		super(PAGE_NAME);
		this.project = project;
		this.destination = destination;
		this.cloneData = cloneData;
		this.pullBranch = cloneData.branch;
		this.pullAction = pullAction;
		this.lastApackCall = new ApackParameters();
		this.chboxLinkAndPull = false;

		setTitle(Messages.AbapGitWizardPageBranchAndPackage_title);
		setDescription(Messages.AbapGitWizardPageBranchAndPackage_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		/////// BRANCH INPUT
		Label lblBranch = new Label(container, SWT.NONE);
		lblBranch.setText(Messages.AbapGitWizardPageBranchAndPackage_label_branch);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblBranch, true);
		GridDataFactory.swtDefaults().applyTo(lblBranch);

		this.comboBranches = new ComboViewer(container, SWT.BORDER);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.comboBranches.getControl());
		this.comboBranches.setContentProvider(ArrayContentProvider.getInstance());
		this.comboBranches.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof IBranch) {
					return ((IBranch) element).getName();
				}
				return super.getText(element);
			}

		});
		this.comboBranches.getCombo().addModifyListener(event -> {
			this.cloneData.branch = this.comboBranches.getCombo().getText();
			setPageComplete(true);
			if (!validateClientOnly()) {
				setPageComplete(false);
			}
			fetchApackManifest();
		});

		/////// Package INPUT
		Label lblPackage = new Label(container, SWT.NONE);
		lblPackage.setText(Messages.AbapGitWizardPageBranchAndPackage_label_package);
		AdtSWTUtilFactory.getOrCreateSWTUtil().setMandatory(lblPackage, true);
		GridDataFactory.swtDefaults().applyTo(lblPackage);

		this.txtPackage = new TextViewer(container, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.txtPackage.getTextWidget());
		this.txtPackage.getTextWidget().setText(""); //$NON-NLS-1$

		this.txtPackage.getTextWidget().addModifyListener(event -> {
			this.cloneData.packageRef = null;
			setPageComplete(true);
			if (!validateClientOnly()) {
				setPageComplete(false);
			}
		});

		IAdtPackageProposalProvider packageProposalProvider = AdtPackageProposalProviderFactory
				.createPackageProposalProvider(this.txtPackage);
		packageProposalProvider.setProject(this.project);

		Button btnPackage = new Button(container, SWT.PUSH);
		GridDataFactory.swtDefaults().applyTo(btnPackage);
		btnPackage.setText(Messages.AbapGitWizardPageBranchAndPackage_btn_browse);

		btnPackage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
				IAdtObjectReference[] selectedPackages = packageServiceUI.openPackageSelectionDialog(e.display.getActiveShell(), false,
						AbapGitWizardPageBranchAndPackage.this.destination,
						AbapGitWizardPageBranchAndPackage.this.txtPackage.getTextWidget().getText());
				if (selectedPackages != null && selectedPackages.length > 0) {
					AbapGitWizardPageBranchAndPackage.this.txtPackage.getTextWidget().setText(selectedPackages[0].getName());
					AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef = selectedPackages[0];
				}
			}
		});

		// if the page is added for repository link wizard
		if (!this.pullAction) {
			// checkbox for executing pull after repository linking
			this.checkbox_lnp = new Button(container, SWT.CHECK);
			this.checkbox_lnp.setText(Messages.AbapGitWizardPageBranchAndPackage_chbox_activate);
			this.checkbox_lnp.setToolTipText(Messages.AbapGitWizardPageBranchAndPackage_chbox_activate_tooltip);
			GridDataFactory.swtDefaults().applyTo(this.checkbox_lnp);

			this.checkbox_lnp.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					Button chbox = (Button) event.getSource();
					setLnpSequence(chbox.getSelection());
					getContainer().updateButtons();
				}
			});
		}

		createProgressBarComposite(container);

		setControl(container);

		if (this.cloneData.url != null) {

			this.comboBranches.getCombo().setEnabled(false);
			this.txtPackage.getTextWidget().setText(this.cloneData.packageRef.getName());
			AdtSWTUtilFactory.getOrCreateSWTUtil().setEditable(this.txtPackage.getControl(), false);

			btnPackage.setEnabled(false);

			//-> Disable back navigation if repo is public and we're in pull wizard
			if (this.cloneData.externalRepoInfo != null && this.cloneData.externalRepoInfo.getAccessMode() == AccessMode.PUBLIC) {
				setBackButtonEnabled(false);
			}
		}

		setPageComplete(true);
		if (!validateClientOnly()) {
			setPageComplete(false);
		}
	}

	private void createProgressBarComposite(Composite container) {
		Composite progressBarComposite = new Composite(container, SWT.NONE);
		progressBarComposite.setLayout(new GridLayout(1, false)); // 1 column layout

		this.progressBarLabel = new Label(progressBarComposite, SWT.NONE);
		this.progressBarLabel.setText(Messages.AbapGitWizardPageBranchAndPackage_FetchingModifiedObjectsForPull);
		this.progressBarLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.END, true, false));

		this.progressBar = new ProgressBar(progressBarComposite, SWT.INDETERMINATE);
		GridData pBGD = new GridData(SWT.FILL, SWT.END, true, false);
		this.progressBar.setLayoutData(pBGD);

		GridData pbCompGD = new GridData(SWT.FILL, SWT.END, true, true);
		pbCompGD.horizontalSpan = 3;
		progressBarComposite.setLayoutData(pbCompGD);

		setProgressVisible(false);
	}

	public void setProgressVisible(boolean visible) {
		if (this.progressBar != null && !this.progressBar.isDisposed()) {
			this.progressBar.setVisible(visible);
		}

		if (this.progressBarLabel != null && !this.progressBarLabel.isDisposed()) {
			this.progressBarLabel.setVisible(visible);
		}
	}

	private void setLnpSequence(boolean chboxValue) {
		this.chboxLinkAndPull = chboxValue;
	}

	public boolean getLnpSequence() {
		return this.chboxLinkAndPull;
	}

	public void setBackButtonEnabled(boolean enabled) {
		this.backButtonEnabled = enabled;
		getContainer().updateButtons();
	}

	@Override
	public IWizardPage getPreviousPage() {
		if (!this.backButtonEnabled) {
			return null;
		}
		return super.getPreviousPage();
	}

	private boolean validateClientOnly() {
		setMessage(null);

		if (this.comboBranches.getCombo().getText().isEmpty()) {
			setMessage(Messages.AbapGitWizardPageBranchAndPackage_combobox_branch_message, DialogPage.INFORMATION);
			return false;
		}

		if (this.txtPackage.getTextWidget().getText().isEmpty()) {
			setMessage(Messages.AbapGitWizardPageBranchAndPackage_text_package_message, DialogPage.INFORMATION);
			return false;
		}
		return true;
	}

	public boolean validateAll() {
		setPageComplete(true);
		if (!validateClientOnly()) {
			setPageComplete(false);
			return false;
		}
		if (this.cloneData.packageRef == null) {
			try {
				String packageName = this.txtPackage.getTextWidget().getText();
				getContainer().run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_message,
								IProgressMonitor.UNKNOWN);
						IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
						if (packageServiceUI.packageExists(AbapGitWizardPageBranchAndPackage.this.destination, packageName, monitor)) {
							List<IAdtObjectReference> packageRefs = packageServiceUI
									.find(AbapGitWizardPageBranchAndPackage.this.destination, packageName, monitor);
							AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef = packageRefs.stream()
									.filter((p) -> p.getName().equalsIgnoreCase(packageName)).findFirst().orElse(null);
						}
					}
				});
				if (this.cloneData.packageRef == null) {
					setErrorMessage(Messages.AbapGitWizardPageBranchAndPackage_task_package_validation_error_message);
					setPageComplete(false);
					return false;
				}
			} catch (InvocationTargetException e) {
				setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
				setPageComplete(false);
				return false;
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			this.comboBranches.setInput(null);
			this.comboBranches.setSelection(StructuredSelection.EMPTY);
			if (this.cloneData.externalRepoInfo != null) {
				List<IBranch> branches = this.cloneData.externalRepoInfo.getBranches();
				branches.removeIf(b -> b.getName().equals("HEAD")); //$NON-NLS-1$
				this.comboBranches.setInput(branches);
				if (!branches.isEmpty()) {
					IBranch selectedBranch = branches.stream().filter(b -> b.getIsHead().equals("X")).findFirst() //$NON-NLS-1$
							.orElse(branches.stream().findFirst().get());

					//PULL branch is pre populated
					if (this.pullBranch != null && selectedBranch.getName() != this.pullBranch) {
						selectedBranch = branches.stream().filter(b -> b.getName().equals(this.pullBranch)).findFirst().get();
					}

					this.comboBranches.setSelection(new StructuredSelection(selectedBranch));
				}
			}
			fetchApackManifest();
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return true && isPageComplete();
	}

	@Override
	public IWizardPage getNextPage() {
		// fetch modified objects when next button is pressed
		if (this.pullAction == true) {
			IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
			IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());
			IRepository repository = repoService.getRepositoryByURL(this.cloneData.repositories, this.cloneData.url);

			// This is valid only for back end versions from 2105 where selective pull is supported.
			// If selectivePull is not supported, fetching modified objects is not required and all objects are to be pulled
			if (abapGitService.isSelectivePullSupported(repository)) {
				setPageComplete(false);
				setProgressVisible(true);

				Job myRequestJob = new Job(Messages.AbapGitWizardPageBranchAndPackage_FetchingModifiedObjectsForPull) {

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						try {
							if (monitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}
							RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, repoService,
									AbapGitWizardPageBranchAndPackage.this.cloneData);

							if (monitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}
						} catch (CommunicationException e) {
							return new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getLocalizedMessage(), e);
						} catch (ResourceException e) {
							return new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getLocalizedMessage(), e);
						} catch (OperationCanceledException e) {
							return new Status(IStatus.ERROR, AbapGitUIPlugin.PLUGIN_ID, e.getLocalizedMessage(), e);
						} finally {
							monitor.done();
						}
						return Status.OK_STATUS;
					}
				};

				myRequestJob.setUser(true);
				myRequestJob.schedule();

				// wait for the job to finish
				Display display = Display.getDefault();
				while (myRequestJob.getResult() == null) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}

				setProgressVisible(false);

				if (myRequestJob.getResult().getSeverity() != Status.OK && myRequestJob.getResult().getSeverity() != Status.INFO) {
					setErrorMessage(myRequestJob.getResult().getMessage());
					setPageComplete(false);
					return null;
				}
			}
		} else {
			if (validateAll()) {
				IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
				IExternalRepositoryInfoService externalRepositoryInfoService = RepositoryServiceFactory
						.createExternalRepositoryInfoService(this.destination, new NullProgressMonitor());

				if (abapGitService.isBranchInfoSupported(this.cloneData.externalRepoInfo)) {
					//Call the resource to fetch folder logic
					try {
						getContainer().run(true, true, new IRunnableWithProgress() {

							@Override
							public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
								monitor.beginTask(Messages.AbapGitWizardPageBranchAndPackage_task_fetching_branch_info_message,
										IProgressMonitor.UNKNOWN);
								IBranch branchInfo = externalRepositoryInfoService.getBranchInfo(
										AbapGitWizardPageBranchAndPackage.this.cloneData.externalRepoInfo,
										AbapGitWizardPageBranchAndPackage.this.cloneData.url,
										AbapGitWizardPageBranchAndPackage.this.cloneData.user,
										AbapGitWizardPageBranchAndPackage.this.cloneData.pass,
										AbapGitWizardPageBranchAndPackage.this.cloneData.branch,
										AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef.getName());

								AbapGitWizardPageBranchAndPackage.this.cloneData.folderLogic = branchInfo.getFolderLogic();
								if (AbapGitWizardPageBranchAndPackage.this.cloneData.folderLogic != null
										&& !AbapGitWizardPageBranchAndPackage.this.cloneData.folderLogic.isBlank()) {
									AbapGitWizardPageBranchAndPackage.this.cloneData.folderLogicExistsInAbapGitFile = true;
								}
							}
						});
					} catch (InvocationTargetException e) {
						//Fallback to link-fetch folder logic-delete, from client
						//TODO remove this fallback after 2311 upgrade by users.
						deleteRepo(this.cloneData.url);
						this.cloneData.folderLogic = getFolderLogicFromRemoteDotAbapGIT();
					} catch (InterruptedException e) {
						setMessage(e.getMessage(), DialogPage.ERROR);
						e.printStackTrace();
						return null;
					}
				}

			}

		}
		return super.getNextPage();
	}

	private void deleteRepo(String url) {
		IRepositoryService repoService = RepositoryServiceFactory
				.createRepositoryService(AbapGitWizardPageBranchAndPackage.this.destination, new NullProgressMonitor());

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Fetching repositories", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
					AbapGitWizardPageBranchAndPackage.this.cloneData.repositories = repoService.getRepositories(monitor);

					IRepository repo = repoService.getRepositoryByURL(AbapGitWizardPageBranchAndPackage.this.cloneData.repositories,
							AbapGitWizardPageBranchAndPackage.this.cloneData.url);

					if (repo != null) {
						repoService.unlinkRepository(repo.getKey(), monitor);
					}
				}

			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private IRepository linkRepo(String folderLogic) {

		IRepositoryService repoService = RepositoryServiceFactory
				.createRepositoryService(AbapGitWizardPageBranchAndPackage.this.destination, new NullProgressMonitor());

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					monitor.beginTask("Fetching remote folder logic", IProgressMonitor.UNKNOWN); //$NON-NLS-1$

					repoService.cloneRepository(AbapGitWizardPageBranchAndPackage.this.cloneData.url,
							AbapGitWizardPageBranchAndPackage.this.cloneData.branch,
							AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef.getName(),
							folderLogic, null,
							AbapGitWizardPageBranchAndPackage.this.cloneData.user, AbapGitWizardPageBranchAndPackage.this.cloneData.pass,
							monitor).getAbapObjects();

					AbapGitWizardPageBranchAndPackage.this.cloneData.repositories = repoService.getRepositories(monitor);

				}

			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			deleteRepo(AbapGitWizardPageBranchAndPackage.this.cloneData.url);
			return null;
		} catch (InterruptedException e) {
			setPageComplete(false);
			e.printStackTrace();
			return null;
		}
		return repoService.getRepositoryByURL(AbapGitWizardPageBranchAndPackage.this.cloneData.repositories,
				AbapGitWizardPageBranchAndPackage.this.cloneData.url);

	}

	private void deleteRepo(IRepository repository) {

		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					IRepositoryService repoService = RepositoryServiceFactory
							.createRepositoryService(AbapGitWizardPageBranchAndPackage.this.destination, new NullProgressMonitor());

					if (repository != null && !repository.getUrl().isBlank()) {
						repoService.unlinkRepository(repository.getKey(), monitor);
					}

				}

			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private String getFolderLogicFromRemoteDotAbapGIT() {
		String folderLogicFromRemoteDotAbapGIT;

		//Link repo with FULL folder logic
		IRepository repo = linkRepo(FolderLogic.FULL.toString());

		if (repo == null) {
			return null;
		}

		//Check the folder logic
		folderLogicFromRemoteDotAbapGIT = repo.getFolderLogic();
		//Delete the repo
		deleteRepo(repo);
		//If retrieved folder logic not the same as FULL, set this.clonedata.folderLogicExistsInAbapGITFile and  return
		if (!folderLogicFromRemoteDotAbapGIT.equalsIgnoreCase(FolderLogic.FULL.toString())) {
			this.cloneData.folderLogicExistsInAbapGitFile = true;
			return folderLogicFromRemoteDotAbapGIT;
		}

		//Link repo with PREFIX folder logic
		repo = linkRepo(FolderLogic.PREFIX.toString());
		if (repo == null) {
			return null;
		}

		//Check the folder logic
		folderLogicFromRemoteDotAbapGIT = repo.getFolderLogic();
		//Delete the repo
		deleteRepo(repo);

		if (!folderLogicFromRemoteDotAbapGIT.equalsIgnoreCase(FolderLogic.PREFIX.toString())) {
			this.cloneData.folderLogicExistsInAbapGitFile = true;
			return folderLogicFromRemoteDotAbapGIT;
		}

		return ""; //$NON-NLS-1$
	}

	private void fetchApackManifest() {
		if (this.cloneData.url.isEmpty() || this.cloneData.branch.isEmpty()) {
			return;
		}
		if (this.cloneData.url.equals(this.lastApackCall.url) && this.cloneData.branch.equals(this.lastApackCall.branch)) {
			return;
		}
		this.cloneData.apackManifest = null;
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					final HashMap<String, Boolean> dependencyCoverage = new HashMap<String, Boolean>();
					final List<IApackDependency> retrievedDependencies = new ArrayList<IApackDependency>();

					ApackParameters nextApackCall = new ApackParameters();
					nextApackCall.url = AbapGitWizardPageBranchAndPackage.this.cloneData.url;
					nextApackCall.branch = AbapGitWizardPageBranchAndPackage.this.cloneData.branch;

					AbapGitWizardPageBranchAndPackage.this.cloneData.apackManifest = retrieveApackManifest(monitor, dependencyCoverage,
							retrievedDependencies, nextApackCall);

					AbapGitWizardPageBranchAndPackage.this.lastApackCall.url = AbapGitWizardPageBranchAndPackage.this.cloneData.url;
					AbapGitWizardPageBranchAndPackage.this.lastApackCall.branch = AbapGitWizardPageBranchAndPackage.this.cloneData.branch;
				}

				private IApackManifest retrieveApackManifest(IProgressMonitor monitor, final HashMap<String, Boolean> dependencyCoverage,
						final List<IApackDependency> retrievedDependencies, ApackParameters apackParameters) {

					monitor.beginTask(NLS.bind(Messages.AbapGitWizardPageBranchAndPackage_task_apack_manifest_message,
							AbapGitWizardPageBranchAndPackage.this.cloneData.url), IProgressMonitor.UNKNOWN);
					IApackGitManifestService manifestService = ApackServiceFactory
							.createApackGitManifestService(AbapGitWizardPageBranchAndPackage.this.destination, monitor);
					IApackManifest myManifest = null;
					if (manifestService != null) {
						myManifest = manifestService.getManifest(apackParameters.url, apackParameters.branch,
								AbapGitWizardPageBranchAndPackage.this.cloneData.user,
								AbapGitWizardPageBranchAndPackage.this.cloneData.pass, monitor);
						dependencyCoverage.put(apackParameters.url, true);
						if (myManifest.hasDependencies()) {
							for (IApackDependency dependency : myManifest.getDescriptor().getDependencies()) {
								retrievedDependencies.add(dependency);
								retrieveDependentManifests(ApackParameters.createFromDependency(dependency), dependencyCoverage,
										retrievedDependencies, manifestService, monitor);
							}
							myManifest.getDescriptor().setDependencies(retrievedDependencies);
						}
					}
					return myManifest;

				}

				private void retrieveDependentManifests(ApackParameters apackParameters, final HashMap<String, Boolean> dependencyCoverage,
						final List<IApackDependency> retrievedDependencies, IApackGitManifestService manifestService,
						IProgressMonitor monitor) {
					monitor.beginTask(NLS.bind(Messages.AbapGitWizardPageBranchAndPackage_task_apack_manifest_message, apackParameters.url),
							IProgressMonitor.UNKNOWN);
					IApackManifest myManifest = manifestService.getManifest(apackParameters.url, apackParameters.branch,
							AbapGitWizardPageBranchAndPackage.this.cloneData.user, AbapGitWizardPageBranchAndPackage.this.cloneData.pass,
							monitor);
					dependencyCoverage.put(apackParameters.url, true);
					if (myManifest.hasDependencies()) {
						for (IApackDependency myDependency : myManifest.getDescriptor().getDependencies()) {
							if (!retrievedDependencies.contains(myDependency)) {
								retrievedDependencies.add(myDependency);
							}
							if (!dependencyCoverage.getOrDefault(myDependency.getGitUrl(), false)) {
								retrieveDependentManifests(ApackParameters.createFromDependency(myDependency), dependencyCoverage,
										retrievedDependencies, manifestService, monitor);
							}
						}
					}
				}
			});
			setPageComplete(true);
			setMessage(null);
		} catch (InvocationTargetException e) {
			setPageComplete(false);
			setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
		} catch (InterruptedException e) {
			// Call was aborted - no dependencies will be retrieved and used in the import
			setPageComplete(true);
		}
	}

	private static class ApackParameters {

		public String url;
		public String branch;

		public static ApackParameters createFromDependency(IApackDependency dependency) {
			ApackParameters apackParameters = new ApackParameters();
			apackParameters.url = dependency.getGitUrl();
			apackParameters.branch = IApackManifest.MASTER_BRANCH;
			return apackParameters;
		}

	}

}
