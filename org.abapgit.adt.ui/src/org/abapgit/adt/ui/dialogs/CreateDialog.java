package org.abapgit.adt.ui.dialogs;


import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

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
	private Group privateRepoGroup;

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
		createUserPwd(container);

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

	private void createUserPwd(Composite container) {

	    GridLayout gridLayout = new GridLayout();
	    GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
	    privateRepoGroup = new Group(container, SWT.NULL);
//	    privateRepoGroup.setText("Owner Info");
	    gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        privateRepoGroup.setLayout(gridLayout);
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.horizontalSpan = 2;
        privateRepoGroup.setLayoutData(gridData);
        
        
        new Label(privateRepoGroup, SWT.NULL).setText("User");
        txtUser = new Text(privateRepoGroup, SWT.SINGLE | SWT.BORDER);
        txtUser.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtUser.setText("John_Smith");
     
        new Label(privateRepoGroup, SWT.NULL).setText("Password");
        txtPwd = new Text(privateRepoGroup, SWT.SINGLE | SWT.BORDER);
        txtPwd.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txtPwd.setText("123456ADMIN");
		txtPwd.setEchoChar('*');
		

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
		privateRepo.setSelection(true);
		    
		privateRepo.addSelectionListener(new SelectionAdapter(){
		        @Override public void widgetSelected(    SelectionEvent e){
		          boolean selected = privateRepo.getSelection();
		          if(selected){
		        	privateRepoGroup.setVisible(true);
		          }
		          else {
			        privateRepoGroup.setVisible(false);
			      	txtPwd.setText("");
		    		txtUser.setText("");
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