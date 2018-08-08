package org.abapgit.adt.ui.wizards;

import java.util.List;

import org.abapgit.adt.backend.AbapGitRequest;
import org.abapgit.adt.backend.Repository;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class WizardPageThree extends WizardPage {

    private Text txtBranch;
    private Text txtPackage;
	private List<Repository> avRepos;
    
    public WizardPageThree() {
        super("Third Page");
        setTitle("Branch and package selection");
        setDescription("Please define repository branch and abap package below");
        setControl(txtBranch);		
        
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ITreeSelection selection = (ITreeSelection) window
		.getSelectionService().getSelection(); 
		Shell currShell = super.getShell();
		
		avRepos = new AbapGitRequest(currShell, selection, "").executeGet();
    }

	@Override
	public void createControl(Composite parent) {
		
		 IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		 ITreeSelection selection = (ITreeSelection) window.getSelectionService().getSelection(); 
		 Shell currShell = super.getShell();
		
		 avRepos = new AbapGitRequest(currShell, selection, "").executeGet();
		
		 Composite container = new Composite(parent, SWT.NONE);
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
	                // TODO Auto-generated method stub
				}
				
	            @Override
	            public void keyReleased(KeyEvent e) {
	            	
	    			setPageComplete(false);
					setMessage("", NONE);
					if (!txtPackage.getText().isEmpty()) {

						setMessage("You can use this package", INFORMATION);
						setPageComplete(true);

						
						String SearchString = avRepos.toString();
						String[] parts = SearchString.split(" ");
						for(int i = 0; i < parts.length; i++) {
						    if(parts[i].equals(txtPackage.getText())) {
						    	setMessage("This package is already in use", WARNING);
								setPageComplete(false);						    	
						    }
						}

					}
	            	
	            }

	        });
			txtPackage.setLayoutData(gd);  
			
	        // required to avoid an error in the system
	        setControl(container);
	        setPageComplete(false);
		
	}
	
    public String getTxtBranch() {
        return txtBranch.getText();
    }
    
    public String getTxtPackage() {
        return txtPackage.getText();
    }
}
