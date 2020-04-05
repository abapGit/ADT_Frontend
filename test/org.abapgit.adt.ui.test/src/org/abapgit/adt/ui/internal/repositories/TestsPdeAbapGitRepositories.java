package org.abapgit.adt.ui.internal.repositories;

import static org.easymock.EasyMock.anyObject;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import java.io.IOException;
import org.abapgit.adt.backend.AbapGitModelFactory;
import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.ui.internal.util.IAbapGitService;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
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
		//repositories
		IRepositories repositories = AbapGitModelFactory.createRepositories();
		
		//create mocks
		
		//github repository 1
		IRepository githubRepositoryOne = createNiceMock(IRepository.class);
		expect(githubRepositoryOne.getUrl()).andReturn("https://github.com/url_one").anyTimes();		
		expect(githubRepositoryOne.getPackage()).andReturn("dummy_package_one").anyTimes();
		expect(githubRepositoryOne.getCreatedEmail()).andReturn("dummy_user@email.com").anyTimes();
		expect(githubRepositoryOne.getBranch()).andReturn("master").anyTimes();
		expect(githubRepositoryOne.getDeserializedAt()).andReturn("20200322180503").anyTimes();
		expect(githubRepositoryOne.getStatusText()).andReturn("dummy_status").anyTimes();

		replay(githubRepositoryOne);
				
		repositories.add(githubRepositoryOne);

		//github repository 2
		IRepository githubRepositoryTwo = createNiceMock(IRepository.class);
		expect(githubRepositoryTwo.getUrl()).andReturn("https://github.com/url_two").anyTimes();		
		expect(githubRepositoryTwo.getPackage()).andReturn("dummy_package_two").anyTimes();
		expect(githubRepositoryTwo.getCreatedEmail()).andReturn("dummy_user_one@email.com").anyTimes();
		expect(githubRepositoryTwo.getBranch()).andReturn("master").anyTimes();
		expect(githubRepositoryTwo.getDeserializedAt()).andReturn("20200322180503").anyTimes();
		expect(githubRepositoryTwo.getStatusText()).andReturn("dummy_status").anyTimes();

		replay(githubRepositoryTwo);
				
		repositories.add(githubRepositoryTwo);

		//gitlab repository
		IRepository gitlabRepository = createNiceMock(IRepository.class);
		expect(gitlabRepository.getUrl()).andReturn("https://gitlab.com/dummy_url").anyTimes();		
		expect(gitlabRepository.getPackage()).andReturn("package_gitlab_one").anyTimes();
		expect(gitlabRepository.getCreatedEmail()).andReturn("dummy_user_one@email.com").anyTimes();
		expect(gitlabRepository.getBranch()).andReturn("master").anyTimes();
		expect(gitlabRepository.getDeserializedAt()).andReturn("20200322180503").anyTimes();
		expect(gitlabRepository.getStatusText()).andReturn("dummy_status").anyTimes();

		replay(gitlabRepository);
				
		repositories.add(gitlabRepository);

		//Repository Service
		IRepositoryService repoService = createNiceMock(IRepositoryService.class);
		expect(repoService.getRepositories(anyObject())).andReturn(repositories);
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
