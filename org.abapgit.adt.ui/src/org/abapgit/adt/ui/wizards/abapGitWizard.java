package org.abapgit.adt.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

public class abapGitWizard extends Wizard {
	
    protected WizardPageOne one;
    protected WizardPageTwo two;

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
        addPage(one);
        addPage(two);
    }

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
