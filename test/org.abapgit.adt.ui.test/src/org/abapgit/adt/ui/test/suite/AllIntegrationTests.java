package org.abapgit.adt.ui.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sap.adt.test.services.destinations.DestinationTestUtil;
import com.sap.adt.test.services.suites.AdtIntegrationTestSuite;
import com.sap.adt.test.services.suites.RunWithDestination;
import org.abapgit.adt.ui.internal.repositories.*;
import org.abapgit.adt.ui.internal.staging.TestsIntegrationStagingView;

@RunWith(AdtIntegrationTestSuite.class)
@RunWithDestination(DestinationTestUtil.HTTP_SKS)
@Suite.SuiteClasses({//
	TestsIntegrationRepositoriesView.class,
	TestsIntegrationStagingView.class
})

public class AllIntegrationTests {
}
