package org.abapgit.adt.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateDialog extends TitleAreaDialog {

	private Text txtURL;
	private Text txtBranch;
	private Text txtDevclass;

	private String url;
	private String branch;
	private String devclass;

	public CreateDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Create");
		setMessage("Create", IMessageProvider.INFORMATION);
	}

	public boolean isHelpAvailable() {
		return false;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createURL(container);
		createBranch(container);
		createDevclass(container);

		return area;
	}

	private void createURL(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("URL");

		GridData dataFirstName = new GridData();
		dataFirstName.grabExcessHorizontalSpace = true;
		dataFirstName.horizontalAlignment = GridData.FILL;

		txtURL = new Text(container, SWT.BORDER);
		txtURL.setLayoutData(dataFirstName);
		txtURL.setText("https://github.com/larshp/DOMA.git");
	}

	private void createBranch(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Branch");

		GridData dataLastName = new GridData();
		dataLastName.grabExcessHorizontalSpace = true;
		dataLastName.horizontalAlignment = GridData.FILL;
		txtBranch = new Text(container, SWT.BORDER);
		txtBranch.setLayoutData(dataLastName);
		txtBranch.setText("refs/heads/master");
	}

	private void createDevclass(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Package");

		GridData dataLastName = new GridData();
		dataLastName.grabExcessHorizontalSpace = true;
		dataLastName.horizontalAlignment = GridData.FILL;
		txtDevclass = new Text(container, SWT.BORDER);
		txtDevclass.setLayoutData(dataLastName);
		txtDevclass.setText("$ONLINE");
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		url = txtURL.getText();
		branch = txtBranch.getText();
		devclass = txtDevclass.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getUrl() {
		return url;
	}

	public String getBranch() {
		return branch;
	}

	public String getDevclass() {
		return devclass;
	}

}