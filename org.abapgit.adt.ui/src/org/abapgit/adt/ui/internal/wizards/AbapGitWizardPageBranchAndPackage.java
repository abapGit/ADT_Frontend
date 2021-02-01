package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.abapgit.adt.backend.ApackServiceFactory;
import org.abapgit.adt.backend.IApackGitManifestService;
import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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

	private Button checkbox_lnp;
	private Boolean chboxLinkAndPull;
	private TextViewer txtPackage;
	private ComboViewer comboBranches;

	private final Boolean pullAction;
	private boolean backButtonEnabled = true;
	private final ApackParameters lastApackCall;

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

		//-> Show checkbox only in link wizard
		if (!this.pullAction) {
			/////// CHECKBOX Link & Pull

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
							AbapGitWizardPageBranchAndPackage.this.cloneData.packageRef = packageRefs.stream().findFirst().orElse(null);
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
		return true;
	}

	@Override
	public IWizardPage getNextPage() {
		// fetch modified objects when next button is pressed
		if (this.pullAction == true) {
			IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
			IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());
			IRepository repository = repoService.getRepositoryByURL(this.cloneData.repositories, this.cloneData.url);

			//This is valid only for back end versions from 2105 where selective pull is supported.
			// If selectivePull is not supported, fetching modified objects is not required and all objects are to be pulled
			if (abapGitService.isSelectivePullSupported(repository)) {
				RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, repoService, this.cloneData);
			}
		}
		return super.getNextPage();
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
