package org.abapgit.adt.ui.test.suite;

import org.abapgit.adt.ui.internal.dialogs.TestsPdeAbapGitStagingCredentialsDialog;
import org.abapgit.adt.ui.internal.staging.TestsPdeAbapGitStaging;
import org.abapgit.adt.ui.internal.wizards.TestPdeAbapGitSwitchBranchWizard;
import org.abapgit.adt.ui.internal.wizards.TestPdeAbapGitSwitchBranchWizardPackageValidations;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({//
	TestsPdeAbapGitStaging.class,
	TestsPdeAbapGitStagingCredentialsDialog.class,
	TestPdeAbapGitSwitchBranchWizard.class,
	TestPdeAbapGitSwitchBranchWizardPackageValidations.class
})
public class AllPdeTests {

}
