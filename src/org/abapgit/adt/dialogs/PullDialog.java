package org.abapgit.adt.dialogs;

import org.abapgit.adt.core.Repository;
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

public class PullDialog extends TitleAreaDialog {

	private Text txtURL;
	private Text txtBranch;
	private Text txtDevclass;
	private Text txtUser;
	private Text txtPwd;

	private String url;
	private String branch;
	private String devclass;

	public PullDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Pull");
		setMessage("Pull", IMessageProvider.INFORMATION);
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

		pullURL(container);
		pullBranch(container);
		pullDevclass(container);
		pullUser(container);
		pullPwd(container);

		return area;
	}

	private void pullURL(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("URL");

		GridData dataURL = new GridData();
		dataURL.grabExcessHorizontalSpace = true;
		dataURL.horizontalAlignment = GridData.FILL;

		txtURL = new Text(container, SWT.BORDER);
		txtURL.setLayoutData(dataURL);
		txtURL.setText("https://github.com/larshp/DOMA.git");
		txtURL.setEnabled(false);
	}

	private void pullBranch(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Branch");

		GridData dataBranch = new GridData();
		dataBranch.grabExcessHorizontalSpace = true;
		dataBranch.horizontalAlignment = GridData.FILL;
		txtBranch = new Text(container, SWT.BORDER);
		txtBranch.setLayoutData(dataBranch);
		txtBranch.setText("refs/heads/master");
		txtBranch.setEnabled(false);
	}

	private void pullDevclass(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Package");

		GridData dataDevclass = new GridData();
		dataDevclass.grabExcessHorizontalSpace = true;
		dataDevclass.horizontalAlignment = GridData.FILL;
		txtDevclass = new Text(container, SWT.BORDER);
		txtDevclass.setLayoutData(dataDevclass);
		txtDevclass.setText("$ONLINE");
		txtDevclass.setEnabled(false);
	}

	private void pullUser(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("User");

		GridData dataUser = new GridData();
		dataUser.grabExcessHorizontalSpace = true;
		dataUser.horizontalAlignment = GridData.FILL;
		txtUser = new Text(container, SWT.BORDER);
		txtUser.setLayoutData(dataUser);
		txtUser.setText(Repository.list().get(0).getUser());
//		txtUser.setText("_SAPD012345");
	}

	private void pullPwd(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Password");

		GridData dataPwd = new GridData();
		dataPwd.grabExcessHorizontalSpace = true;
		dataPwd.horizontalAlignment = GridData.FILL;
		txtPwd = new Text(container, SWT.BORDER);
		txtPwd.setLayoutData(dataPwd);
		txtPwd.setText("123456ADMIN");
		txtPwd.setEchoChar('*');
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
//		String user = txtUser.getText();
//		String pwd = txtPwd.getText();
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