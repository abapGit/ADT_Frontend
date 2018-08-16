package org.abapgit.adt.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WizardPageTwo extends WizardPage {
    private Text txtUser;
    private Text txtPwd;

    public WizardPageTwo() {
        super("Second Page");
        setTitle("Git Repository Credentials");
        setDescription("Enter your repository credentials, if it's private");
        setControl(txtUser);
    }

    @Override
    public void createControl(Composite parent) {
    	
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        container.setLayout(layout);
        layout.numColumns = 2;
        
        Label lblUser = new Label(container, SWT.NONE);
        lblUser.setText("User");   
        txtUser = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtUser.setText("");
        
        Label lblPwd = new Label(container, SWT.NONE);
        lblPwd.setText("Password");  
        txtPwd = new Text(container, SWT.BORDER | SWT.SINGLE);
		txtPwd.setEchoChar('*');
        txtPwd.setText("");      
        
                       
		txtUser.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!txtUser.getText().isEmpty()) {
                    setPageComplete(true);
                }
            }

        });
		
		txtPwd.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!txtPwd.getText().isEmpty()) {
                    setPageComplete(true);
                }
            }

        });
		
		
        txtUser.setLayoutData(gd);
        txtPwd.setLayoutData(gd);

        // required to avoid an error in the system
        setControl(container);
//        setPageComplete(false);
        setPageComplete(true);
    }
    
    public String getTxtUser() {
        return txtUser.getText();
    }
    
    public String getTxtPwd() {
        return txtPwd.getText();
    }

}
