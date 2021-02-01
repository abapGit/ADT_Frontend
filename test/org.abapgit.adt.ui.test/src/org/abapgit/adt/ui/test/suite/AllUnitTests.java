package org.abapgit.adt.ui.test.suite;

import org.abapgit.adt.ui.internal.util.TestsUnitAbapGitPullService;
import org.abapgit.adt.ui.internal.util.TestsUnitAbapGitService;
import org.abapgit.adt.ui.internal.util.TestsUnitRepositoryUtil;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({//
	TestsUnitRepositoryUtil.class,
	TestsUnitAbapGitService.class,
	TestsUnitAbapGitPullService.class
	
})
public class AllUnitTests {

}
