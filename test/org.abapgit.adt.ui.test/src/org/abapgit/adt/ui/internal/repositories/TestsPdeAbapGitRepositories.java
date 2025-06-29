package org.abapgit.adt.ui.internal.repositories;

import static org.easymock.EasyMock.anyObject;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import java.io.IOException;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.Event;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsPdeAbapGitRepositories {
	
	private static AbapGitView view;
	private static IProject project;
	private static TestsPdeAbapGitRepositoriesUtil testUtil;
	
	@BeforeClass
	public static void setUp() throws CoreException{
		testUtil = new TestsPdeAbapGitRepositoriesUtil();
		view = testUtil.initializeView();
		project = testUtil.createDummyAbapProject();
		view.lastProject = project;
	}	
	
	@AfterClass
	public static void disposeControls() throws CoreException {
		project.delete(true, true, null);
		view.dispose();
	}

	@Test
	public void testFilterBox() throws IOException {
		//create mocks
		
		//github repository 1
		IRepository githubRepositoryOne = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		githubRepositoryOne.setUrl("https://github.com/url_one");		
		githubRepositoryOne.setPackage("dummy_package_one");
		githubRepositoryOne.setCreatedEmail("dummy_user@email.com");
		githubRepositoryOne.setBranchName("master");
		githubRepositoryOne.setDeserializedAt("20200322180503");
		githubRepositoryOne.setStatusText("dummy_status");

		
		//github repository 2
		IRepository githubRepositoryTwo = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		githubRepositoryTwo.setUrl("https://github.com/url_two");		
		githubRepositoryTwo.setPackage("dummy_package_two");
		githubRepositoryTwo.setCreatedEmail("dummy_user_one@email.com");
		githubRepositoryTwo.setBranchName("master");
		githubRepositoryTwo.setDeserializedAt("20200322180503");
		githubRepositoryTwo.setStatusText("dummy_status");


		//gitlab repository
		IRepository gitlabRepository = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		gitlabRepository.setUrl("https://gitlab.com/dummy_url");		
		gitlabRepository.setPackage("package_gitlab_one");
		gitlabRepository.setCreatedEmail("dummy_user_one@email.com");
		gitlabRepository.setBranchName("master");
		gitlabRepository.setDeserializedAt("20200322180503");
		gitlabRepository.setStatusText("dummy_status");

		
		IRepositories repos =  AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
			
		repos.getRepositories().add(githubRepositoryOne);		
		repos.getRepositories().add(githubRepositoryTwo);
		repos.getRepositories().add(gitlabRepository);
		

		//Repository Service
		IRepositoryService repoService = createNiceMock(IRepositoryService.class);
		expect(repoService.getRepositories(anyObject())).andReturn(repos);
		replay(repoService);
		
		//Repositories View Service
		IAbapGitService abapGitService = createNiceMock(IAbapGitService.class);
		expect(abapGitService.isLoggedOn(anyObject())).andReturn(true);
		expect(abapGitService.ensureLoggedOn(anyObject())).andReturn(true);
		replay(abapGitService);
		
		//inject mocks
		
		view.repoService = repoService;
		view.abapGitService = abapGitService;
		
		refresh();
				
		//check if the repositories view has all 3 repositories
		Assert.assertEquals(3, view.viewer.getTable().getItemCount());

		//test filter box
		filter();
			
	}
	
	
	private void refresh() {
		view.actionRefresh.run();
		testUtil.waitInUI(3000);		
	}

	private void filter() {

		//filter matching user
		view.searchText.setText("user_one");
		view.searchText.notifyListeners(SWT.Modify, new Event());
		
		//check if the repositories view has 2 repositories
		Assert.assertEquals(2, view.viewer.getTable().getItemCount());

		//filter matching package
		view.searchText.setText("PACKAGE*ONE");
		view.searchText.notifyListeners(SWT.Modify, new Event());

		//check if the repositories view has 2 repositories
		Assert.assertEquals(2, view.viewer.getTable().getItemCount());

		//filter matching url
		view.searchText.setText("github");
		view.searchText.notifyListeners(SWT.Modify, new Event());
	
		//check if the repositories view has 2 repositories
		Assert.assertEquals(2, view.viewer.getTable().getItemCount());
		
		//filter matching url
		view.searchText.setText("dummy_url");
		view.searchText.notifyListeners(SWT.Modify, new Event());
	
		//check if the repositories view has 1 repository
		Assert.assertEquals(1, view.viewer.getTable().getItemCount());
		
	}
	
	

	

}
