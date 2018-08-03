package org.abapgit.adt.ui.wizards;

import org.abapgit.adt.Repository;
import org.eclipse.jface.wizard.Wizard;

public class abapGitWizard extends Wizard {
	
    protected WizardPageOne one;
    protected WizardPageTwo two;
    protected WizardPageThree three;
    protected WizardPageFour four;

    public abapGitWizard() {
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
   
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
//		this.four.getTxtUrl();
//		System.out.println(this.four.getTxtUrl());
//    	System.out.println(Repository.list());
    	return false;
	}

}
