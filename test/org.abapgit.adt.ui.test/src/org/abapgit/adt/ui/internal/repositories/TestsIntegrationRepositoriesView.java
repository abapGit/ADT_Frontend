package org.abapgit.adt.ui.internal.repositories;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sap.adt.communication.resources.ResourceException;
import com.sap.adt.test.services.destinations.DestinationTestUtil;
import com.sap.adt.test.services.suites.AdtIntegrationTest;
import com.sap.adt.test.services.suites.RunWithDestination;
import com.sap.adt.tools.core.model.atom.IAtomFactory;
import com.sap.adt.tools.core.model.atom.IAtomLink;
import com.sap.adt.tools.core.project.IAbapProject;
import com.sap.adt.tools.core.test.services.AdtIntegrationTestProjectUtil;

@RunWith(AdtIntegrationTest.class)
@RunWithDestination(DestinationTestUtil.HTTP_SKS)
public class TestsIntegrationRepositoriesView {
	private static String   destinationId;
	private static IProject testProject;
	private static IRepositoryService repoService;
	private static IExternalRepositoryInfoService externalRepoService;
	private static IRepository repoWithInvalidDetails;
	private static IExternalRepositoryInfoRequest credentials;

	@BeforeClass
	public static void classSetup() throws CoreException {
		//create a test project
		testProject = AdtIntegrationTestProjectUtil.createTestProjectWithUniqueName();
		destinationId = testProject.getAdapter(IAbapProject.class).getDestinationId();
		
		//Service objects
		repoService =  RepositoryServiceFactory.createRepositoryService(destinationId, new NullProgressMonitor());
		externalRepoService = RepositoryServiceFactory.createExternalRepositoryInfoService(destinationId, new NullProgressMonitor());
		
		repoWithInvalidDetails = createRepoWithInvalidDetails();
		
		credentials = createCredentialsFromRepo();
	}
	
	private static IExternalRepositoryInfoRequest createCredentialsFromRepo() {
		IExternalRepositoryInfoRequest credentials =  IAbapgitexternalrepoFactory.eINSTANCE.createExternalRepositoryInfoRequest();
		credentials.setUrl(repoWithInvalidDetails.getUrl());
		credentials.setUser(repoWithInvalidDetails.getRemoteUser());
		credentials.setPassword(repoWithInvalidDetails.getRemotePassword());
		
		return credentials;
	}

	private static IRepository createRepoWithInvalidDetails() {
		IRepository repo = IAbapgitrepositoriesFactory.eINSTANCE.createRepository();
		
		repo.setUrl("invalid URL");
		repo.setKey("xyz");
		repo.setRemoteUser("A2ETest");
		repo.setRemotePassword("Password");
		repo.setBranchName("main");
		repo.setPackage("ZABAPGIT_INTEGRATION_TESTS");
		repo.setFolderLogic("FULL");
		repo.setTransportRequest("SKS12345");
		
		addLinksToRepo(repo);
		
		return repo;
	}

	private static void addLinksToRepo(IRepository repo) {
		IAtomLink checksLink = IAtomFactory.eINSTANCE.createAtomLink();
		checksLink.setRel(IRepositoryService.RELATION_CHECK);
		checksLink.setHref("/sap/bc/adt/abapgit/repos/xyz/checks");
		repo.getLinks().add(checksLink);
		
		IAtomLink statusLink = IAtomFactory.eINSTANCE.createAtomLink();
		statusLink.setRel(IRepositoryService.RELATION_STATUS);
		statusLink.setHref("/sap/bc/adt/abapgit/repos/xyz/status");
		repo.getLinks().add(statusLink);	
		
		IAtomLink pullLink = IAtomFactory.eINSTANCE.createAtomLink();
		pullLink.setRel(IRepositoryService.RELATION_PULL);
		pullLink.setHref("/sap/bc/adt/abapgit/repos/xyz/pull");
		repo.getLinks().add(pullLink);
		
		IAtomLink modifiedObjectsLink = IAtomFactory.eINSTANCE.createAtomLink();
		modifiedObjectsLink.setRel(IRepositoryService.RELATION_MODIFIED_OBJECTS);
		modifiedObjectsLink.setHref("/sap/bc/adt/abapgit/repos/xyz/pull/modifiedobjects");
		repo.getLinks().add(modifiedObjectsLink);
		
		IAtomLink logLink = IAtomFactory.eINSTANCE.createAtomLink();
		logLink.setRel(IRepositoryService.RELATION_LOG);
		logLink.setHref("/sap/bc/adt/abapgit/repos/xyz/log/abc");
		repo.getLinks().add(logLink);
		
	}

	private static void assertResponseForInvalidURL(ResourceException exception) {
		Assert.assertEquals("Malformed URL", exception.getMessage());
	}

	private static void assertResponseForInvalidRepoKey(ResourceException exception) {
		Assert.assertEquals("repo not found, get", exception.getMessage());
	}

	@Test
	public void smokeTestReadRepositoriesFromBackend() {		
		if(repoService != null) {
			IRepositories repositories = repoService.getRepositories(new NullProgressMonitor());
			Assert.assertNotNull(repositories);
		}
	}

	@Test
	public void smokeTestLinkRepository() {
		if(repoService != null) {
			try {
				repoService.cloneRepository(repoWithInvalidDetails.getUrl(), repoWithInvalidDetails.getBranchName(), repoWithInvalidDetails.getPackage(),repoWithInvalidDetails.getFolderLogic(),repoWithInvalidDetails.getTransportRequest(), repoWithInvalidDetails.getRemoteUser(), repoWithInvalidDetails.getRemotePassword(), new NullProgressMonitor());						
			}  catch (ResourceException e) {
				assertResponseForInvalidURL(e);
			}
		}
	}

	@Test
	public void smokeTestExternalRepoInfoURI() {
		if(repoService != null) {
			try {
				externalRepoService.getExternalRepositoryInfo(repoWithInvalidDetails.getUrl(), repoWithInvalidDetails.getRemoteUser(), repoWithInvalidDetails.getRemotePassword(), new NullProgressMonitor());						
			}  catch (ResourceException e) {
				assertResponseForInvalidURL(e);
			}
		}
	}

	@Test
	public void smokeTestPullURI() {
		if(repoService != null) {
			try {
				IAbapGitPullModifiedObjects pullModifiedObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitPullModifiedObjects();
				repoService.pullRepository(repoWithInvalidDetails, repoWithInvalidDetails.getBranchName(), repoWithInvalidDetails.getTransportRequest(), repoWithInvalidDetails.getRemoteUser(), repoWithInvalidDetails.getRemotePassword(), pullModifiedObjects, new NullProgressMonitor());
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}			
		}
	}

	@Test
	public void smokeTestPullModifiedObjectsURI() {
		if(repoService != null) {
			try {
				repoService.getModifiedObjects(new NullProgressMonitor(), repoWithInvalidDetails, repoWithInvalidDetails.getRemoteUser(), repoWithInvalidDetails.getRemotePassword());
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}
		}
	}

	@Test
	public void smokeTestChecksURI() {
		
		if(repoService != null) {
			try {
				repoService.repositoryChecks(new NullProgressMonitor(), credentials, repoWithInvalidDetails);
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}
		}
	}

	@Test
	public void smokeTestObjectsLogURI() {

		if(repoService != null) {
			try {
				repoService.getRepoObjLog(new NullProgressMonitor(), repoWithInvalidDetails);
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}
		}

	}

}
