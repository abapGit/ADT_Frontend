package org.abapgit.adt.ui.internal.wizards;

import java.util.List;

import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
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

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.AdtPackageServiceUIFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class AbapGitWizardPageApack extends WizardPage {

	private static final String PAGE_NAME = AbapGitWizardPageApack.class.getName();

	private final String destination;
	private final CloneData cloneData;

	private Label organizationIdContent;
	private Label packageIdContent;
	private Label versionContent;
	private Label licenseContent;
	private Label descriptionContent;
	private Label gitUrlContent;
	private Table table;

	public AbapGitWizardPageApack(String destination, CloneData cloneData) {
		super(PAGE_NAME);
		this.destination = destination;
		this.cloneData = cloneData;

		setTitle(Messages.AbapGitWizardPageApack_title);
		setDescription(Messages.AbapGitWizardPageApack_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		container.setLayout(layout);

		Label organizationIdLabel = new Label(container, SWT.NONE);
		organizationIdLabel.setText(Messages.AbapGitWizardPageApack_label_organization_id);
		GridDataFactory.swtDefaults().applyTo(organizationIdLabel);
		this.organizationIdContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.organizationIdContent);
		this.organizationIdContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label packageIdLabel = new Label(container, SWT.NONE);
		packageIdLabel.setText(Messages.AbapGitWizardPageApack_label_package_id);
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

		Label licenseLabel = new Label(container, SWT.NONE);
		licenseLabel.setText(Messages.AbapGitWizardPageApack_label_license);
		GridDataFactory.swtDefaults().applyTo(licenseLabel);
		this.licenseContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.licenseContent);
		this.licenseContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label descriptionLabel = new Label(container, SWT.NONE);
		descriptionLabel.setText(Messages.AbapGitWizardPageApack_label_description);
		GridDataFactory.swtDefaults().applyTo(descriptionLabel);
		this.descriptionContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.descriptionContent);
		this.descriptionContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

		Label gitUrlLabel = new Label(container, SWT.NONE);
		gitUrlLabel.setText(Messages.AbapGitWizardPageApack_label_git_repository_url);
		GridDataFactory.swtDefaults().applyTo(gitUrlLabel);
		this.gitUrlContent = new Label(container, SWT.NONE);
		GridDataFactory.swtDefaults().span(2, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(this.gitUrlContent);
		this.gitUrlContent.setText(Messages.AbapGitWizardPageApack_text_no_information_available);

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
		String[] titles = { Messages.AbapGitWizardPageApack_table_header_organization_id, Messages.AbapGitWizardPageApack_table_header_package_id, Messages.AbapGitWizardPageApack_table_header_git_repository_url, Messages.AbapGitWizardPageApack_table_header_package_name };
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
			if (!manifestDescriptor.getOrganizationId().isEmpty()) {
				this.organizationIdContent.setText(manifestDescriptor.getOrganizationId());
			}
			if (!manifestDescriptor.getPackageId().isEmpty()) {
				this.packageIdContent.setText(manifestDescriptor.getPackageId());
			}
			if (!manifestDescriptor.getVersion().isEmpty()) {
				this.versionContent.setText(manifestDescriptor.getVersion());
			}
			if (!manifestDescriptor.getLicense().isEmpty()) {
				this.licenseContent.setText(manifestDescriptor.getLicense());
			}
			if (!manifestDescriptor.getDescription().isEmpty()) {
				this.descriptionContent.setText(manifestDescriptor.getDescription());
			}
			if (!manifestDescriptor.getGitUrl().isEmpty()) {
				this.gitUrlContent.setText(manifestDescriptor.getGitUrl());
			}
			if (!manifestDescriptor.getDependencies().isEmpty() && this.table.getItemCount() == 0) {
				List<IApackDependency> dependencies = manifestDescriptor.getDependencies();
				for (IApackDependency dependency : dependencies) {
					final int packageColumnIndex = 3;
					TableItem tableItem = new TableItem(this.table, SWT.NONE);
					tableItem.setText(
							new String[] { dependency.getOrganizationId(), dependency.getPackageId(), dependency.getGitUrl(), dependency.getTargetPackageName() });

					if (dependency.getTargetPackageName().isEmpty()) {
						addThePackageButton(dependency, packageColumnIndex, tableItem);
					}

				}
				packTheTable();
			}
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
					tableItem.setText(packageColumnIndex, selectedPackages[0].getName());
					dependency.setTargetPackageName(selectedPackages[0].getName());
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

}
