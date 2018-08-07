package org.abapgit.adt.ui.wizards;

import org.abapgit.adt.AbapGitRequest;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class AbapGitWizard extends Wizard {
	
    protected WizardPageOne one;
    protected WizardPageTwo two;
    protected WizardPageThree three;
    protected WizardPageFour four;
    


    public AbapGitWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    @Override
    public String getWindowTitle() {
        return "abapGit Wizard";
    }

    @Override
    public void addPages() {
        one = new WizardPageOne(); 
        two = new WizardPageTwo(); 
        three = new WizardPageThree();
        four = new WizardPageFour();
        addPage(one);
        addPage(two);
        addPage(three);
        addPage(four);
    }
    
	public String createPostXML() {
		// quick and dirty
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<asx:abap xmlns:asx=\"http://www.sap.com/abapxml\" version=\"1.0\">" + "<asx:values>" + "<ROOT>"
				+ "<URL>" + one.getTxtUrl() + "</URL>" + "<BRANCH_NAME>" + three.getTxtBranch() + "</BRANCH_NAME>" + "<PACKAGE>" + three.getTxtPackage()
				+ "</PACKAGE>" + "<TR_NAME>" + four.getTxtTr() + "</TR_NAME>" + "<USER>" + two.getTxtUser() + "</USER>" + "<PWD>" + two.getTxtPwd() + "</PWD>" + "</ROOT>" + "</asx:values></asx:abap>";
		
		return xml;

	}

	@Override
	public boolean performFinish() {

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ITreeSelection selection = (ITreeSelection) window
				.getSelectionService().getSelection();

//		System.out.println(window);
//		System.out.println(selection);
		
		new AbapGitRequest(window, selection, createPostXML()).executePost();

		
		return false;
	}
	

}
