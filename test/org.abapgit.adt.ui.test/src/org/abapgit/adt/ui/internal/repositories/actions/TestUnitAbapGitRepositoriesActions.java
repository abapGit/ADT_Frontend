package org.abapgit.adt.ui.internal.repositories.actions;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.ui.internal.repositories.AbapGitView;
import org.abapgit.adt.ui.internal.repositories.TestsPdeAbapGitRepositoriesUtil;
import org.eclipse.core.runtime.CoreException;

public class TestUnitAbapGitRepositoriesActions {

	private static OpenRepositoryAction openAction;
	private static AbapGitView view;
	private static IRepository dummyGitSelection;
	private static IRepository dummyBitSelection;
	private static TestsPdeAbapGitRepositoriesUtil utils;
	
	@BeforeClass
	public static void setup() throws CoreException {
		utils = new TestsPdeAbapGitRepositoriesUtil();
		view = utils.initializeView();
		// git based host
		dummyGitSelection = utils.createDummyRepository();
		// bitbucket host
		dummyBitSelection = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		dummyBitSelection.setUrl("https://user1234@bitbucket.org/user1234/dummy.git");
		dummyBitSelection.setPackage("$AP_GITHUB");
		dummyBitSelection.setCreatedEmail("dummy_user_one@email.com");
		dummyBitSelection.setBranchName("refs/heads/master");
		dummyBitSelection.setDeserializedAt("20200322180503");
		dummyBitSelection.setStatusText("dummy_status");
		openAction = new OpenRepositoryAction(view);
	}
	
	@Test
	public void testOpenRepositoryInBrowserAction() {
		String actualGitLink = openAction.getLink(dummyGitSelection);
		String expectedGitLink = "https://github.com/dummy_url/tree/master";
		Assert.assertEquals(actualGitLink, expectedGitLink);
		
		String actualBitLink = openAction.getLink(dummyBitSelection);
		String expectedBitLink = "https://bitbucket.org/user1234/dummy/src/master";
		Assert.assertEquals(actualBitLink, expectedBitLink);
	}
}
