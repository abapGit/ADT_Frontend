package org.abapgit.adt.ui.internal.util;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
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
	
	@Test
	public void fetchAndExtractModifiedObjectsToPull() {
		
		//Prepare Test data
		IRepository repository = IAbapgitrepositoriesFactory.eINSTANCE.createRepository();
		repository.setUrl("dummyRepositoryURL");
		
		IAbapGitPullModifiedObjects abapGitPullModifiedObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitPullModifiedObjects();
		
		IOverwriteObjects overwriteObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObjects();
		IPackageWarningObjects packageWarningObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createPackageWarningObjects();
		
		IOverwriteObject packageWarningObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObject();
		packageWarningObject1.setName("PackageWarningObject1");

		IOverwriteObject overwriteObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObject();
		overwriteObject1.setName("OverwriteObject1");
		
		overwriteObjects.getAbapgitobjects().add(overwriteObject1);
		packageWarningObjects.getAbapgitobjects().add(packageWarningObject1);
		
		abapGitPullModifiedObjects.setOverwriteObjects(overwriteObjects);
		abapGitPullModifiedObjects.setPackageWarningObjects(packageWarningObjects);
		
		//Mock RepositoryService
		IRepositoryService repoService = createNiceMock(IRepositoryService.class);
		expect(repoService.getModifiedObjects(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(abapGitPullModifiedObjects);
		replay(repoService);
		

		// Expecting data filled in clone Data
		CloneData cloneData =  new CloneData();
		
		//execute code under test.
		RepositoryUtil.fetchAndExtractModifiedObjectsToPull(repository, repoService, cloneData);
		
		//verify if cloneData is filled appropriately.
		for (IRepositoryModifiedObjects obj : cloneData.repoToModifiedOverwriteObjects) {
			Assert.assertEquals(overwriteObject1.getName(), obj.getModifiedObjects().get(0).getName());
		}
		for (IRepositoryModifiedObjects obj : cloneData.repoToModifiedPackageWarningObjects) {
			Assert.assertEquals(packageWarningObject1.getName(), obj.getModifiedObjects().get(0).getName());
		}

		
	}

}
