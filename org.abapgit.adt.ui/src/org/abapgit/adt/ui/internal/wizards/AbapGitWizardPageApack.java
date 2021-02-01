package org.abapgit.adt.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;
import com.sap.adt.transport.IAdtTransportCheckData;
import com.sap.adt.transport.IAdtTransportService;

public class AbapGitWizardPageApack extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageApack.class.getName();

	private final String destination;
	private final CloneData cloneData;
	private final IAdtTransportService transportService;
	private final boolean pullScenario;

	private Label organizationIdContent;
	private Label packageIdContent;
	private Label versionContent;
	private Label gitUrlContent;
	private Table table;
	private Button pullAllCheckBox;

	public AbapGitWizardPageApack(String destination, CloneData cloneData, IAdtTransportService transportService, boolean pullScenario) {
		super(PAGE_NAME);
		this.destination = destination;
		this.cloneData = cloneData;
		this.transportService = transportService;
		this.pullScenario = pullScenario;

		setTitle(Messages.AbapGitWizardPageApack_title);
		setDescription(Messages.AbapGitWizardPageApack_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		Label organizationIdLabel = new Label(container, SWT.NONE);
		organizationIdLabel.setText(Messages.AbapGitWizardPageApack_label_group_id);
		GridDataFactory.swtDefaults().applyTo(organizationIdLabel);
		this.organizationIdContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.organizationIdContent);
		this.organizationIdContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label packageIdLabel = new Label(container, SWT.NONE);
		packageIdLabel.setText(Messages.AbapGitWizardPageApack_label_artifact_id);
		GridDataFactory.swtDefaults().applyTo(packageIdLabel);
		this.packageIdContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.packageIdContent);
		this.packageIdContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label versionLabel = new Label(container, SWT.NONE);
		versionLabel.setText(Messages.AbapGitWizardPageApack_label_version);
		GridDataFactory.swtDefaults().applyTo(versionLabel);
		this.versionContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.versionContent);
		this.versionContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label gitUrlLabel = new Label(container, SWT.NONE);
		gitUrlLabel.setText(Messages.AbapGitWizardPageApack_label_git_repository_url);
		GridDataFactory.swtDefaults().applyTo(gitUrlLabel);
		this.gitUrlContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.gitUrlContent);
		this.gitUrlContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		if (this.pullScenario) {
			this.pullAllCheckBox = new Button(container, SWT.CHECK);
			this.pullAllCheckBox.setText(Messages.AbapGitWizardPageApack_checkbox_pull_all_dependencies);
			this.pullAllCheckBox.setSelection(true);
			GridDataFactory.swtDefaults().span(3, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.pullAllCheckBox);
		}

		Label dependenciesLabel = new Label(container, SWT.NONE);
		dependenciesLabel.setText(Messages.AbapGitWizardPageApack_label_dependencies);
		GridDataFactory.swtDefaults().span(3, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(dependenciesLabel);

		TableViewer tableViewer = new TableViewer(container, SWT.NONE);
		this.table = tableViewer.getTable();
		this.table.setLinesVisible(true);
		this.table.setHeaderVisible(true);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		this.table.setLayoutData(gridData);
		String[] titles = { Messages.AbapGitWizardPageApack_table_header_group_id, Messages.AbapGitWizardPageApack_table_header_artifact_id,
				Messages.AbapGitWizardPageApack_table_header_git_repository_url,
				Messages.AbapGitWizardPageApack_table_header_package_name };
		for (String title : titles) {
			TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
			TableColumn column = viewerColumn.getColumn();
			column.setText(title);
			column.pack();
		}

		setControl(container);

	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible && this.cloneData.apackManifest != null) {
			IApackManifestDescriptor manifestDescriptor = this.cloneData.apackManifest.getDescriptor();
			if (manifestDescriptor == null) {
				return;
			}
			setTextOrganisationId(manifestDescriptor);
			setTextPackageId(manifestDescriptor);
			setTextVersion(manifestDescriptor);
			setTextGitUrl(manifestDescriptor);
			buildDependencyTable(manifestDescriptor);
		}

	}

	private void buildDependencyTable(IApackManifestDescriptor manifestDescriptor) {
		if (!manifestDescriptor.getDependencies().isEmpty() && this.table.getItemCount() == 0) {
			List<IApackDependency> dependencies = manifestDescriptor.getDependencies();
			for (IApackDependency dependency : dependencies) {
				final int packageColumnIndex = 3;
				TableItem tableItem = new TableItem(this.table, SWT.NONE);
				tableItem.setText(new String[] { dependency.getGroupId(), dependency.getArtifactId(), dependency.getGitUrl(),
						dependency.getTargetPackage().getName() });

				if (dependency.getTargetPackage() == null || dependency.getTargetPackage().getName() == null
						|| dependency.getTargetPackage().getName().isEmpty()) {
					addThePackageButton(dependency, packageColumnIndex, tableItem);
				}

			}
			packTheTable();
		}
	}

	private void setTextGitUrl(IApackManifestDescriptor manifestDescriptor) {
		if (!manifestDescriptor.getGitUrl().isEmpty()) {
			this.gitUrlContent.setText(manifestDescriptor.getGitUrl());
		}
	}

	private void setTextVersion(IApackManifestDescriptor manifestDescriptor) {
		if (!manifestDescriptor.getVersion().isEmpty()) {
			this.versionContent.setText(manifestDescriptor.getVersion());
		}
	}

	private void setTextPackageId(IApackManifestDescriptor manifestDescriptor) {
		if (!manifestDescriptor.getPackageId().isEmpty()) {
			this.packageIdContent.setText(manifestDescriptor.getPackageId());
		}
	}

	private void setTextOrganisationId(IApackManifestDescriptor manifestDescriptor) {
		if (!manifestDescriptor.getGroupId().isEmpty()) {
			this.organizationIdContent.setText(manifestDescriptor.getGroupId());
		}
	}

	private void addThePackageButton(IApackDependency dependency, final int packageColumnIndex, TableItem tableItem) {
		Button button = new Button(this.table, SWT.NONE);
		button.setText("..."); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
				IAdtObjectReference[] selectedPackages = packageServiceUI.openPackageSelectionDialog(e.display.getActiveShell(), false,
						AbapGitWizardPageApack.this.destination, tableItem.getText(packageColumnIndex));
				if (selectedPackages != null && selectedPackages.length > 0) {
					setPageComplete(true);
					setMessage(null);
					tableItem.setText(packageColumnIndex, selectedPackages[0].getName());
					dependency.setTargetPackage(selectedPackages[0]);
					packTheTable();
				}
			}
		});
		button.pack();
		TableEditor editor = new TableEditor(this.table);
		editor.horizontalAlignment = SWT.RIGHT;
		editor.minimumWidth = button.getSize().x;
		editor.setEditor(button, tableItem, packageColumnIndex);
		editor.layout();
	}

	private void packTheTable() {
		for (int i = 0; i < this.table.getColumnCount(); i++) {
			this.table.getColumn(i).pack();
		}
	}

	public boolean validateAll() {
		if (this.cloneData.apackManifest.hasDependencies()) {
			// Check if all dependencies have valid packages assigned
			for (IApackDependency apackDependency : this.cloneData.apackManifest.getDescriptor().getDependencies()) {
				if (!validatePackageNotEmpty(apackDependency)) {
					return false;
				}
				if (!validatePackageExisting(apackDependency)) {
					return false;
				}
				if (this.pullScenario && this.pullAllCheckBox.getSelection()) {
					apackDependency.setRequiresSynchronization(true);
				}
			}

			List<IApackDependency> apackDependencies = this.cloneData.apackManifest.getDescriptor().getDependencies();
			if (!validatePackagesUnique(apackDependencies)) {
				return false;
			}

			// Check if all packages have the same transport layer and software component
			IAdtTransportCheckData[] dependencyCheckData = buildPackageCheckData(apackDependencies);
			if (!validateCheckData(apackDependencies, dependencyCheckData)) {
				return false;
			}
			return validatePackageTransportLayer(dependencyCheckData);
		}
		return true;
	}

	private boolean validatePackagesUnique(List<IApackDependency> apackDependencies) {
		List<String> usedPackages = new ArrayList<String>();
		usedPackages.add(this.cloneData.packageRef.getName());
		for (IApackDependency apackDependency : apackDependencies) {
			String newPackageName = apackDependency.getTargetPackage().getName();
			if (usedPackages.contains(newPackageName)) {
				setMessage(NLS.bind(Messages.AbapGitWizardPageApack_task_package_used_multiple_times, newPackageName), DialogPage.ERROR);
				setPageComplete(false);
				return false;
			} else {
				usedPackages.add(newPackageName);
			}
		}
		return true;
	}

	private boolean validatePackageTransportLayer(IAdtTransportCheckData[] dependencyCheckData) {
		IAdtTransportCheckData referenceCheckData = dependencyCheckData[0];
		for (int i = 1; i < dependencyCheckData.length; i++) {
			if (!referenceCheckData.getPackageSoftwareComponent().equals(dependencyCheckData[i].getPackageSoftwareComponent())
					|| !referenceCheckData.getPackageTransportLayer().equals(dependencyCheckData[i].getPackageTransportLayer())) {
				setMessage(NLS.bind(Messages.AbapGitWizardPageApack_task_transport_layers_differ, referenceCheckData.getObjectName(),
						dependencyCheckData[i].getObjectName()), DialogPage.ERROR);
				setPageComplete(false);
				return false;
			}
		}
		return true;
	}

	private boolean validateCheckData(List<IApackDependency> apackDependencies, IAdtTransportCheckData[] dependencyCheckData) {
		if ((dependencyCheckData.length - 1) != apackDependencies.size()) {
			setMessage(Messages.AbapGitWizardPageApack_task_error_retrieving_transport_data, DialogPage.ERROR);
			setPageComplete(false);
			return false;
		}
		return true;
	}

	private IAdtTransportCheckData[] buildPackageCheckData(List<IApackDependency> apackDependencies) {
		IAdtObjectReference packageRef = this.cloneData.packageRef;
		IAdtObjectReference checkReference = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
		checkReference.setUri(packageRef.getUri());
		List<IAdtObjectReference> dependencyReferences = new ArrayList<IAdtObjectReference>();
		dependencyReferences.add(checkReference);
		for (IApackDependency apackDependency : apackDependencies) {
			checkReference = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
			checkReference.setUri(apackDependency.getTargetPackage().getUri());
			dependencyReferences.add(checkReference);
		}
		IAdtTransportCheckData[] dependencyCheckData = this.transportService.check(
				dependencyReferences.toArray(new IAdtObjectReference[dependencyReferences.size()]), packageRef.getPackageName(), true,
				this.destination);
		return dependencyCheckData;
	}

	private boolean validatePackageExisting(IApackDependency apackDependency) {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					String packageName = apackDependency.getTargetPackage().getName();
					monitor.beginTask(NLS.bind(Messages.AbapGitWizardPageApack_task_validating_package, packageName),
							IProgressMonitor.UNKNOWN);
					IAdtPackageServiceUI packageServiceUI = AdtPackageServiceUIFactory.getOrCreateAdtPackageServiceUI();
					if (packageServiceUI.packageExists(AbapGitWizardPageApack.this.destination, packageName, monitor)) {
						List<IAdtObjectReference> packageReferences = packageServiceUI.find(AbapGitWizardPageApack.this.destination,
								packageName, monitor);
						if (packageReferences == null || packageReferences.isEmpty()) {
							setMessage(NLS.bind(Messages.AbapGitWizardPageApack_task_package_not_existing, packageName), DialogPage.ERROR);
							setPageComplete(false);
						}
					}
				}
			});
			if (!isPageComplete()) {
				return false;
			}
		} catch (InvocationTargetException e) {
			setMessage(e.getTargetException().getMessage(), DialogPage.ERROR);
			setPageComplete(false);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private boolean validatePackageNotEmpty(IApackDependency apackDependency) {
		if (apackDependency.getTargetPackage() == null || apackDependency.getTargetPackage().getName() == null
				|| apackDependency.getTargetPackage().getName().isEmpty()) {
			setMessage(NLS.bind(Messages.AbapGitWizardPageApack_task_invalid_package_assignment,
					apackDependency.getGroupId() + "-" + apackDependency.getArtifactId()), DialogPage.ERROR); //$NON-NLS-1$
			setPageComplete(false);
			return false;
		}
		return true;
	}

	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	@Override
	public IWizardPage getNextPage() {

		// fetch modified objects for each dependency (repository)
		if (this.pullScenario == true && this.pullAllCheckBox.getSelection() && this.cloneData.hasDependencies()) {

			IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
			IRepositoryService repoService = RepositoryServiceFactory.createRepositoryService(this.destination, new NullProgressMonitor());

			for (IApackDependency apackDependency : this.cloneData.apackManifest.getDescriptor().getDependencies()) {
					IRepository dependencyRepository = RepositoryServiceFactory.createRepositoryService(this.destination,
							new NullProgressMonitor()).getRepositoryByURL(this.cloneData.repositories, apackDependency.getGitUrl()) ;

					//This is valid only for back end versions from 2105 where selective pull is supported.
					// If selectivePull is not supported, fetching modified objects is not required and all objects are to be pulled
					if (dependencyRepository != null && abapGitService.isSelectivePullSupported(dependencyRepository)) {
						RepositoryUtil.fetchAndExtractModifiedObjectsToPull(dependencyRepository, repoService, this.cloneData);
					}
			}
		}
		return super.getNextPage();
	}

}
