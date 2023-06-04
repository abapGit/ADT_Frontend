package org.abapgit.adt.ui.internal.util;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.junit.Assert;
import org.junit.Test;

import com.sap.adt.tools.core.model.atom.IAtomFactory;
import com.sap.adt.tools.core.model.atom.IAtomLink;

public class TestsUnitAbapGitService {
	@Test
	public void isSelectivePullSupported() {
		// Prepare test data.
		// Create Atom Link for modified objects
		IAtomLink modifiedObjects = IAtomFactory.eINSTANCE.createAtomLink();
		modifiedObjects.setType("modifiedobjects_link");
		modifiedObjects.setRel(IRepositoryService.RELATION_MODIFIED_OBJECTS);

		// repository with atom link returned from backend for modified objects,
		// supports selective pull
		IRepository repositoryWithModifiedObjectsLink = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repositoryWithModifiedObjectsLink.setUrl("https://github.com/url_one");
		repositoryWithModifiedObjectsLink.setPackage("dummy_package_one");
		repositoryWithModifiedObjectsLink.setCreatedEmail("dummy_user@email.com");
		repositoryWithModifiedObjectsLink.setBranchName("master");
		repositoryWithModifiedObjectsLink.setDeserializedAt("20200322180503");
		repositoryWithModifiedObjectsLink.setStatusText("dummy_status");
		repositoryWithModifiedObjectsLink.getLinks().add(modifiedObjects);

		// repository with no atom link for modified objects, older backend versions
		// than 2105 don't support selective pull
		IRepository repositoryWithOutModifiedObjectsLink = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repositoryWithOutModifiedObjectsLink.setUrl("https://github.com/url_one");
		repositoryWithOutModifiedObjectsLink.setPackage("dummy_package_one");
		repositoryWithOutModifiedObjectsLink.setCreatedEmail("dummy_user@email.com");
		repositoryWithOutModifiedObjectsLink.setBranchName("master");
		repositoryWithOutModifiedObjectsLink.setDeserializedAt("20200322180503");
		repositoryWithOutModifiedObjectsLink.setStatusText("dummy_status");

		// Execute code under test.
		IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();
		
		Assert.assertEquals(true, abapGitService.isSelectivePullSupported(repositoryWithModifiedObjectsLink));		
		Assert.assertEquals(false, abapGitService.isSelectivePullSupported(repositoryWithOutModifiedObjectsLink));
	}
	
	@Test
	public void isBranchInfoSupported() {

		IExternalRepositoryInfo externalRepositoryInfoWithBranchInfo = IAbapgitexternalrepoFactory.eINSTANCE.createExternalRepositoryInfo();
		externalRepositoryInfoWithBranchInfo.setAccessMode(AccessMode.PUBLIC);
		
		IBranch branch = IAbapgitexternalrepoFactory.eINSTANCE.createBranch();
		branch.setDisplayName("master");
		branch.setName("refs/head/master");
		branch.setType("HD");
		branch.setIsHead("X");
		
		IAtomLink branchInfoLink = 	IAtomFactory.eINSTANCE.createAtomLink();
		branchInfoLink.setRel(IExternalRepositoryInfoService.RELATION_BRANCH_INFO);
		branchInfoLink.setHref("/sap/bc/adt/abapgit/externalrepoinfo/branchinfo");
		branch.getLinks().add(branchInfoLink);
		
		externalRepositoryInfoWithBranchInfo.getBranches().add(branch);

		IExternalRepositoryInfo externalRepositoryInfoWithOutBranchInfo = IAbapgitexternalrepoFactory.eINSTANCE.createExternalRepositoryInfo();
		externalRepositoryInfoWithOutBranchInfo.setAccessMode(AccessMode.PUBLIC);
		
		IBranch branch2 = IAbapgitexternalrepoFactory.eINSTANCE.createBranch();
		branch2.setDisplayName("master");
		branch2.setName("refs/head/master");
		branch2.setType("HD");
		branch2.setIsHead("X");
		
		externalRepositoryInfoWithOutBranchInfo.getBranches().add(branch2);

		// Execute code under test.
		IAbapGitService abapGitService = AbapGitUIServiceFactory.createAbapGitService();

		Assert.assertEquals(true, abapGitService.isBranchInfoSupported(externalRepositoryInfoWithBranchInfo));		
		Assert.assertEquals(false, abapGitService.isBranchInfoSupported(externalRepositoryInfoWithOutBranchInfo));

	}
}
