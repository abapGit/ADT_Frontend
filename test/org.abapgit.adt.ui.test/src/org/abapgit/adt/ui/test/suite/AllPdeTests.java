package org.abapgit.adt.ui.test.suite;

import org.abapgit.adt.ui.dialogs.TestsPdeAbapGitStagingCredentialsDialog;
import org.abapgit.adt.ui.internal.views.TestsPdeAbapGitStaging;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({//
	TestsPdeAbapGitStaging.class,
	TestsPdeAbapGitStagingCredentialsDialog.class
})
public class AllPdeTests {

}
