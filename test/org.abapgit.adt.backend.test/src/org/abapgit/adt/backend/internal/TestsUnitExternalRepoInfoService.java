package org.abapgit.adt.backend.internal;

import java.net.URI;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.adt.tools.core.model.atom.IAtomFactory;
import com.sap.adt.tools.core.model.atom.IAtomLink;


public class TestsUnitExternalRepoInfoService {
	
	private static IExternalRepositoryInfoService extRepoService;
	
	@BeforeClass
	public static void setUp() throws CoreException {
		extRepoService = new ExternalRepositoryInfoService("dummy_destination", URI.create("dummy_ext_repo_uri"));
	}
	
	private static IExternalRepositoryInfo getExternalRepoInfoObject() {
		IExternalRepositoryInfo externalRepositoryInfo = IAbapgitexternalrepoFactory.eINSTANCE.createExternalRepositoryInfo();
		externalRepositoryInfo.setAccessMode(AccessMode.PUBLIC);
		
		IBranch branch = IAbapgitexternalrepoFactory.eINSTANCE.createBranch();
		branch.setDisplayName("master");
		branch.setName("refs/head/master");
		branch.setType("HD");
		branch.setIsHead("X");
		
		IAtomLink branchInfoLink = 	IAtomFactory.eINSTANCE.createAtomLink();
		branchInfoLink.setRel(IExternalRepositoryInfoService.RELATION_BRANCH_INFO);
		branchInfoLink.setHref("/sap/bc/adt/abapgit/externalrepoinfo/branchinfo");
		branch.getLinks().add(branchInfoLink);
		
		externalRepositoryInfo.getBranches().add(branch);
		
		return externalRepositoryInfo;
	
	}

	@Test
	public void TestGetURIForBranchInfo() {
		
		//Prepare Test Data
		IExternalRepositoryInfo externalRepositoryInfo = getExternalRepoInfoObject();
		
		URI uri = extRepoService.getURIForBranchInfo(externalRepositoryInfo, "refs/head/master");
		
		//Asserts
		Assert.assertTrue(uri.getPath().equalsIgnoreCase("/sap/bc/adt/abapgit/externalrepoinfo/branchinfo"));
		
		

		
	}

}
