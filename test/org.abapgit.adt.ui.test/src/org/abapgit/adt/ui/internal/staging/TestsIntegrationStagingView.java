package org.abapgit.adt.ui.internal.staging;

import java.io.IOException;

import org.abapgit.adt.backend.FileServiceFactory;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.RepositoryServiceFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesFactory;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot;
import org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects;
import org.abapgit.adt.backend.model.abapgitstaging.util.AbapgitstagingResourceFactoryImpl;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
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
public class TestsIntegrationStagingView {
	private static String   destinationId;
	private static IProject testProject;
	private static IRepositoryService repoService;
	private static IExternalRepositoryInfoService externalRepoService;
	private static IFileService fileService; 
	private static IRepository repoWithInvalidDetails;
	private static IExternalRepositoryInfoRequest credentials;
	private static IAbapGitStaging staging;

	@BeforeClass
	public static void classSetup() throws CoreException {
		//create a test project
		testProject = AdtIntegrationTestProjectUtil.createTestProjectWithUniqueName();
		destinationId = testProject.getAdapter(IAbapProject.class).getDestinationId();
		
		//Service objects
		repoService =  RepositoryServiceFactory.createRepositoryService(destinationId, new NullProgressMonitor());
		externalRepoService = RepositoryServiceFactory.createExternalRepositoryInfoService(destinationId, new NullProgressMonitor());
		fileService = FileServiceFactory.createFileService();
		
		repoWithInvalidDetails = createRepoWithInvalidDetails();
		
		credentials = createCredentialsFromRepo();
		
		staging = createStagingObject();
	}
	
	private static IAbapGitStaging createStagingObject() {
		Resource resource = new AbapgitstagingResourceFactoryImpl().createResource(org.eclipse.emf.common.util.URI.createURI("resource.agitstage"));

		IAbapGitStaging abapGITStaging = IAbapgitstagingFactory.eINSTANCE.createAbapGitStaging();
		IDocumentRoot documentRoot = IAbapgitstagingFactory.eINSTANCE.createDocumentRoot();
		
		IAuthor author = IAbapgitstagingFactory.eINSTANCE.createAuthor();
		author.setName("A2ETest");
		author.setEmail("a2etest@foomail.com");

		ICommitter commiter = IAbapgitstagingFactory.eINSTANCE.createCommitter();
		commiter.setName("A2ETest");
		commiter.setEmail("a2etest@foomail.com");

		IAbapGitCommitMessage commitMessage = IAbapgitstagingFactory.eINSTANCE.createAbapGitCommitMessage();
		commitMessage.setAuthor(author);
		commitMessage.setCommitter(commiter);

		IAbapGitObject abapGITObject = IAbapgitstagingFactory.eINSTANCE.createAbapGitObject();
		abapGITObject.setName("Dummy Staged Object");
		
		IStagedObjects stagedObjects = IAbapgitstagingFactory.eINSTANCE.createStagedObjects();
		stagedObjects.getAbapgitobject().add(abapGITObject);

		IUnstagedObjects unstagedObjects = IAbapgitstagingFactory.eINSTANCE.createUnstagedObjects();
		unstagedObjects.getAbapgitobject().add(abapGITObject);

		IIgnoredObjects ignoredObjects = IAbapgitstagingFactory.eINSTANCE.createIgnoredObjects();
		ignoredObjects.getAbapgitobject().add(abapGITObject);
		
		abapGITStaging.setStagedObjects(stagedObjects);
		abapGITStaging.setCommitMessage(commitMessage);
		abapGITStaging.setUnstagedObjects(unstagedObjects);
		abapGITStaging.setIgnoredObjects(ignoredObjects);
		
		documentRoot.setAbapgitstaging(abapGITStaging);
		
		resource.getContents().add(documentRoot);
		
		return abapGITStaging;
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
		
		IAtomLink stageLink = IAtomFactory.eINSTANCE.createAtomLink();
		stageLink.setRel(IRepositoryService.RELATION_STAGE);
		stageLink.setHref("/sap/bc/adt/abapgit/repos/xyz/stage");
		repo.getLinks().add(stageLink);		
		
		IAtomLink pushLink = IAtomFactory.eINSTANCE.createAtomLink();
		pushLink.setRel(IRepositoryService.RELATION_PUSH);
		pushLink.setHref("/sap/bc/adt/abapgit/repos/xyz/push");
		repo.getLinks().add(pushLink);
		
		IAtomLink logLink = IAtomFactory.eINSTANCE.createAtomLink();
		logLink.setRel(IRepositoryService.RELATION_LOG);
		logLink.setHref("/sap/bc/adt/abapgit/repos/xyz/log/abc");
		repo.getLinks().add(logLink);		

		IAtomLink statusLink = IAtomFactory.eINSTANCE.createAtomLink();
		statusLink.setRel(IRepositoryService.RELATION_STATUS);
		statusLink.setHref("/sap/bc/adt/abapgit/repos/xyz/status");
		repo.getLinks().add(statusLink);		
}

	private static void assertResponseForInvalidURL(ResourceException exception) {
		Assert.assertEquals("Malformed URL", exception.getMessage());
	}

	private static void assertResponseForInvalidRepoKey(ResourceException exception) {
		Assert.assertEquals("repo not found, get", exception.getMessage());
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
	public void smokeTestFetchFiles() {
		
		if(fileService != null) {
			try {
				IAtomLink filesLink = IAtomFactory.eINSTANCE.createAtomLink();
				filesLink.setRel(IFileService.RELATION_FILE_FETCH_LOCAL);
				filesLink.setHref("/sap/bc/adt/abapgit/repos/xyz/files?filename=dummyName.dummyType.xml&version=local");

				IAbapGitFile abapGitFile = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
				abapGitFile.setName("Dummy File");
				abapGitFile.getLinks().add(filesLink);
				
				try {
					fileService.readLocalFileContents(abapGitFile,
							destinationId);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}
			
		}
	}

	@Test
	public void smokeTestStage() {
		if(repoService != null) {
			try {
				repoService.stage(repoWithInvalidDetails, credentials, new NullProgressMonitor());
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}
		}
	}

	@Test
	public void smokeTestPush() {
		if(repoService != null) {
			try {
				repoService.push(new NullProgressMonitor(), staging , credentials, repoWithInvalidDetails);
			}  catch (ResourceException e) {
				assertResponseForInvalidRepoKey(e);
			}			
		}
	}
	
}
