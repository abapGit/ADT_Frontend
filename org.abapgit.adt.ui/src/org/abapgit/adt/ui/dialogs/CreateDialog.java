package org.abapgit.adt.ui.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreateDialog extends TitleAreaDialog {

	private Text txtURL;
	private Text txtBranch;
	private Text txtDevclass;
	private Text txtUser;
	private Text txtPwd;
	private Text txtTrname;

	private String url;
	private String branch;
	private String devclass;
	private String user;
	private String pwd;
	private String trname;

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
		createTrname(container);
		checkPrivate(container);
  		createUser(container);
		createPwd(container);

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
<<<<<<< HEAD

=======
>>>>>>> Added compatibility with ABAP in SAP CP  (#9)
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
	
	private void createTrname(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Transport request");

		GridData dataTrname = new GridData();
		dataTrname.grabExcessHorizontalSpace = true;
		dataTrname.horizontalAlignment = GridData.FILL;
		txtTrname = new Text(container, SWT.BORDER);
		txtTrname.setLayoutData(dataTrname);
		txtTrname.setText("DEVK900001");
	}	
		
	private void createUser(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("User");
		
		GridData dataUser = new GridData();
		dataUser.grabExcessHorizontalSpace = true;
		dataUser.horizontalAlignment = GridData.FILL;
		txtUser = new Text(container, SWT.BORDER);
		txtUser.setLayoutData(dataUser);
		txtUser.setText("John_Smith");
		txtUser.setVisible(false);
	}
	
	private void createPwd(Composite container) {
		Label lblPwd = new Label(container, SWT.NONE);
		lblPwd.setText("Password");

		GridData dataPwd = new GridData();
		dataPwd.grabExcessHorizontalSpace = true;
		dataPwd.horizontalAlignment = GridData.FILL;
		txtPwd = new Text(container, SWT.BORDER);
		txtPwd.setLayoutData(dataPwd);
		txtPwd.setText("123456ADMIN");
		txtPwd.setEchoChar('*');
		txtPwd.setVisible(false);
	}	
	
	private void checkPrivate(Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setText("Private repository?");

		GridData dataCheckPrivate = new GridData();
		dataCheckPrivate.grabExcessHorizontalSpace = true;
		dataCheckPrivate.horizontalAlignment = GridData.FILL;

		// Create private repo ceckbox
		Button privateRepo = new Button(container, SWT.CHECK);
		privateRepo.setLayoutData(dataCheckPrivate);
		    
		privateRepo.addSelectionListener(new SelectionAdapter(){
		        @Override public void widgetSelected(    SelectionEvent e){
		          boolean selected = privateRepo.getSelection();
		          if(selected == true){
		      		txtPwd.setVisible(true);
		      		txtUser.setVisible(true);
		          }
		          else {
			        txtPwd.setVisible(false);
			      	txtUser.setVisible(false);
		          }
		        }
		});    
		    
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
		user = txtUser.getText();
		pwd = txtPwd.getText();
		trname = txtTrname.getText();
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

	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}

	public String getTrname() {
		return trname;
	}


}