package org.abapgit.adt.ui.internal.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestsUnitRepositoryUtil {
	
	@BeforeClass
	public static void setUp() {
	}
	
	@Test
	public void getRepoNameFromUrl() {
		String url = "https://github.com/AbapGit-Push/test_repo_1";
		Assert.assertEquals("test_repo_1", RepositoryUtil.getRepoNameFromUrl(url));
		
		url = "https://github.com/AbapGit-Push/test_repo_1.git";
		Assert.assertEquals("test_repo_1", RepositoryUtil.getRepoNameFromUrl(url));
	}
	
	@Test
	public void getBranchNameFromRef() {
		String branchRef = "ref/head/master";
		Assert.assertEquals("master", RepositoryUtil.getBranchNameFromRef(branchRef));
	}
}
