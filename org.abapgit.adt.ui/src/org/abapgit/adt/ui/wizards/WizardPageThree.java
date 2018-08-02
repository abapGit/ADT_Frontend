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

public class WizardPageThree extends WizardPage {

	private Text text1;
    private Text txtBranch;
    private Text txtPackage;
    private Composite container;
    
    public WizardPageThree() {
        super("Third Page");
        setTitle("Third Page");
        setDescription("Now this is the third page");
        setControl(text1);
    }

	@Override
	public void createControl(Composite parent) {
		
		 container = new Composite(parent, SWT.NONE);
	        GridLayout layout = new GridLayout();
	        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	        container.setLayout(layout);
	        layout.numColumns = 2;

	        /////// BRANCH INPUT         
			Label lblBranch = new Label(container, SWT.NONE);
			lblBranch.setText("Branch");

			txtBranch = new Text(container, SWT.BORDER | SWT.SINGLE);
			txtBranch.setText("refs/heads/master");

			txtBranch.addKeyListener(new KeyListener() {

	            @Override
	            public void keyPressed(KeyEvent e) {
	            }

	            @Override
	            public void keyReleased(KeyEvent e) {
	                if (!txtBranch.getText().isEmpty()) {
	                    setPageComplete(true);
	                }
	            }

	        });
			txtBranch.setLayoutData(gd);
			
	        /////// Package INPUT 
	        Label lblPackage = new Label(container, SWT.NONE);
			lblPackage.setText("Package");

			txtPackage = new Text(container, SWT.BORDER | SWT.SINGLE);
			txtPackage.setText("");        

			txtPackage.addKeyListener(new KeyListener() {

	            @Override
	            public void keyPressed(KeyEvent e) {
	            }

	            @Override
	            public void keyReleased(KeyEvent e) {
	                if (!txtPackage.getText().isEmpty()) {
	                    setPageComplete(true);

	                }
	            }

	        });
			txtPackage.setLayoutData(gd);  
			
	        // required to avoid an error in the system
	        setControl(container);
	        setPageComplete(false);
		
	}
	
    public String getText1() {
        return text1.getText();
    }
}
