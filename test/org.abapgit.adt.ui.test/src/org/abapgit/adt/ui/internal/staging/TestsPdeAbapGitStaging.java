package org.abapgit.adt.ui.internal.staging;

import static org.easymock.EasyMock.anyObject;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IFileService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.abapgit.adt.ui.internal.staging.actions.CompareAction;
import org.abapgit.adt.ui.internal.staging.compare.AbapGitCompareInput;
import org.abapgit.adt.ui.internal.staging.compare.AbapGitCompareItem;
import org.abapgit.adt.ui.internal.staging.util.IAbapGitStagingService;
import org.abapgit.adt.ui.internal.staging.util.SwitchRepositoryMenuCreator;
import org.abapgit.adt.ui.internal.util.AbapGitUIServiceFactory;
import org.abapgit.adt.ui.internal.util.RepositoryUtil;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.service.resolver.StateHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sap.adt.tools.core.project.IAbapProject;

public class TestsPdeAbapGitStaging {
	
	private static AbapGitStagingView view;
	private static IProject project;
	private static TestsPdeAbapGitStagingUtil testUtil;
	
	@BeforeClass
	public static void setUp() throws CoreException{
		testUtil = new TestsPdeAbapGitStagingUtil();
		view = testUtil.initializeView();
		project = testUtil.createDummyAbapProject("ABAPGIT_TEST_PROJECT");
		view.project = project;
	}	
	
	@AfterClass
	public static void disposeControls() throws CoreException {
		view.dispose();
		project.delete(true, true, null);
	}
	
	@Test
	public void initialViewStateCheck() {
		view.resetStagingView();
		
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 0);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
		
		Assert.assertEquals(view.mainForm.getText(), Messages.AbapGitStaging_no_repository_selected);
		
		Assert.assertTrue(view.commitMessageTextViewer.getTextWidget().getText().isEmpty());
		Assert.assertTrue(view.authorText.getText().isEmpty());
		Assert.assertTrue(view.committerText.getText().isEmpty());
		
		Assert.assertEquals(view.actionRefresh.isEnabled(), false);
		Assert.assertEquals(view.actionStage.isEnabled(), false);
		Assert.assertEquals(view.actionUnstage.isEnabled(), false);
		Assert.assertEquals(view.commitAndPushButton.getEnabled(), false);
	}
	

	@Test
	public void repositorySwitchAction() throws CoreException {
		//reset view
		view.resetStagingView();
		
		//create mocks
		
		//create mock projects for condition checks
		IProject project_not_loggedon = testUtil.createDummyAbapProject("ABAPGIT_TEST_PROJECT_2");
		IProject project_not_support_abapgit = testUtil.createDummyAbapProject("ABAPGIT_TEST_PROJECT_3");
		
		//repository
		IRepository repository1 = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository1.setUrl("https://github.com/AbapGit-Push/test_repo_1");		
		repository1.setPackage("TEST_PACKAGE_1");
		repository1.setCreatedBy(testUtil.getLoggedOnUser(project));
		repository1.setBranchName("master");

		IRepository repository2 = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository2.setUrl("https://github.com/AbapGit-Push/test_repo_2");		
		repository2.setPackage("TEST_PACKAGE_2");
		repository2.setCreatedBy("TEST_USER_2");
		repository2.setBranchName("master");

		IRepository repository3 = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repository3.setUrl("https://github.com/AbapGit-Push/abcd_repo");		
		repository3.setPackage("TEST_PACKAGE_3");
		repository3.setCreatedBy("TEST_USER_2");
		repository3.setBranchName("master");

		//external repository
		IExternalRepositoryInfo externalRepositoryInfo = createNiceMock(IExternalRepositoryInfo.class);
		expect(externalRepositoryInfo.getAccessMode()).andReturn(AccessMode.PUBLIC);
		replay(externalRepositoryInfo);
		
		//repository service
		IRepositories repos = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
		repos.getRepositories().add(repository1);
		repos.getRepositories().add(repository2);
		repos.getRepositories().add(repository3);
		
		IRepositoryService repositoryService = createNiceMock(IRepositoryService.class);
		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getStagingTestData()).anyTimes();
		expect(repositoryService.getRepositories(anyObject())).andReturn(repos);
		replay(repositoryService);
		
		//external repository service
		IExternalRepositoryInfoService externalRepositoryService = createNiceMock(IExternalRepositoryInfoService.class);
		expect(externalRepositoryService.getExternalRepositoryInfo(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(externalRepositoryInfo).anyTimes();
		replay(externalRepositoryService);
		
		//staging service
		IAbapGitStagingService stagingService = createNiceMock(IAbapGitStagingService.class);
		expect(stagingService.ensureLoggedOn(anyObject())).andReturn(true).anyTimes();
		
		//project which is logged on and which support abapgit
		expect(stagingService.isLoggedOn(project)).andReturn(true).anyTimes();
		expect(stagingService.isAbapGitSupported(project)).andReturn(true);
		//project which is not logged on and which support abapgit
		expect(stagingService.isLoggedOn(project_not_loggedon)).andReturn(false);
		expect(stagingService.isAbapGitSupported(project_not_loggedon)).andReturn(true);
		//project which is logged on and which does not support abapgit
		expect(stagingService.isLoggedOn(project_not_support_abapgit)).andReturn(true);
		expect(stagingService.isAbapGitSupported(project_not_support_abapgit)).andReturn(false);
		
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingUtil = stagingService;
		
		//open repository in staging view
		view.openStagingView(repository1, project);
		testUtil.waitInUI(3000);
		
		//create repository switch menu creator instance
		SwitchRepositoryMenuCreator repoSwitchMenuCreator = new SwitchRepositoryMenuCreator(view, stagingService);
		//inject repository service
		repoSwitchMenuCreator.injectRepositoryService(repositoryService);
		//get view toolbar
		ToolBarManager toolBarManager = (ToolBarManager) view.getViewSite().getActionBars().getToolBarManager();
		ToolBar toolBar = toolBarManager.getControl();
		
		//create menu
		Menu menu = repoSwitchMenuCreator.getMenu(toolBar);
		//menu item count will be 1, it contains one for project "ABAPGIT_TEST_PROJECT"
		//project "ABAPGIT_TEST_PROJECT_3" will not be part of menu as it does not support abapgit
		//project "ABAPGIT_TEST_PROJECT_2" will not be part of menu as it is not logged on
		Assert.assertEquals(1, menu.getItemCount());
		
		MenuItem menuItem = menu.getItem(0);
		Assert.assertEquals(project.getName(), menuItem.getText());
		if(menuItem.getData() instanceof ActionContributionItem) {
			IAction action = ((ActionContributionItem)menuItem.getData()).getAction();
			Assert.assertEquals(IAction.AS_DROP_DOWN_MENU, action.getStyle());
			
			//test sub menu
			IMenuCreator repoMenuCreator = action.getMenuCreator();
			Menu repoMenu = repoMenuCreator.getMenu(menuItem.getMenu());
			//there will be 3 menu items as the mock project contains three repositories
			Assert.assertEquals(3, repoMenu.getItemCount());
			
			//test sort order.
			
			menuItem = repoMenu.getItem(0);
			//test_repo_1 will come before abcd_repo as the owner of the repo is same as the logged on user ( my_repository )
			Assert.assertEquals(RepositoryUtil.getRepoNameFromUrl("https://github.com/AbapGit-Push/test_repo_1"), menuItem.getText());
			if(menuItem.getData() instanceof ActionContributionItem) {
				action = ((ActionContributionItem)menuItem.getData()).getAction();
				Assert.assertEquals(IAction.AS_RADIO_BUTTON, action.getStyle());
				//since the view is already loaded with repository1, the menu item should be checked
				Assert.assertEquals(true, action.isChecked());
			}
			
			menuItem = repoMenu.getItem(1);
			Assert.assertEquals(RepositoryUtil.getRepoNameFromUrl("https://github.com/AbapGit-Push/abcd_repo"), menuItem.getText());
			
			menuItem = repoMenu.getItem(2);
			Assert.assertEquals(RepositoryUtil.getRepoNameFromUrl("https://github.com/AbapGit-Push/test_repo_2"), menuItem.getText());
			if(menuItem.getData() instanceof ActionContributionItem) {
				action = ((ActionContributionItem)menuItem.getData()).getAction();
				Assert.assertEquals(IAction.AS_RADIO_BUTTON, action.getStyle());
				//action should not be checked
				Assert.assertEquals(false, action.isChecked());
				//run action
				action.run();
				//wait till all jobs are done
				testUtil.waitInUI(3000);
				//now the view will be loaded with repository2 which is "test_repo_2"
				Assert.assertEquals(view.mainForm.getText(), "test_repo_2 [master] [ABAPGIT_TEST_PROJECT]");
			}
		}
		//delete projects
		project_not_loggedon.delete(true, new NullProgressMonitor());
		project_not_support_abapgit.delete(true, new NullProgressMonitor());
	}
	
	@Test
	public void openPublicRepository() throws IOException {
		view.resetStagingView();
		
		//create mocks
		
		//repository
		IRepository repository = createNiceMock(IRepository.class);
		expect(repository.getUrl()).andReturn("https://github.com/AbapGit-Push/lorem_ipsum").anyTimes();
		expect(repository.getBranchName()).andReturn("master");
		replay(repository);
		
		//external repository
		IExternalRepositoryInfo externalRepositoryInfo = createNiceMock(IExternalRepositoryInfo.class);
		expect(externalRepositoryInfo.getAccessMode()).andReturn(AccessMode.PUBLIC);
		replay(externalRepositoryInfo);
		
		//repository service
		IRepositoryService repositoryService = createNiceMock(IRepositoryService.class);
		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getStagingTestData());
		replay(repositoryService);
		
		//external repository service
		IExternalRepositoryInfoService externalRepositoryService = createNiceMock(IExternalRepositoryInfoService.class);
		expect(externalRepositoryService.getExternalRepositoryInfo(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(externalRepositoryInfo);
		replay(externalRepositoryService);
		
		IAbapGitStagingService stagingService = createNiceMock(IAbapGitStagingService.class);
		expect(stagingService.ensureLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingUtil = stagingService;
		
		//open repository in staging view
		view.openStagingView(repository, project);
		testUtil.waitInUI(3000);
		
		Assert.assertEquals(view.mainForm.getText(), "lorem_ipsum [master] [ABAPGIT_TEST_PROJECT]");
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 4);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
		
		Assert.assertEquals(view.actionRefresh.isEnabled(), true);
		Assert.assertEquals(view.actionStage.isEnabled(), true);
		Assert.assertEquals(view.actionUnstage.isEnabled(), false);
		Assert.assertEquals(view.commitAndPushButton.getEnabled(), false);
		
		//check sort order
		Assert.assertEquals(((IAbapGitObject)view.unstagedTreeViewer.getTree().getItem(0).getData()), testUtil.getNonCodeMock());
		Assert.assertEquals(((IAbapGitObject)view.unstagedTreeViewer.getTree().getItem(1).getData()), testUtil.getClassMock());
		Assert.assertEquals(((IAbapGitObject)view.unstagedTreeViewer.getTree().getItem(2).getData()), testUtil.getTableMock());
		Assert.assertEquals(((IAbapGitObject)view.unstagedTreeViewer.getTree().getItem(3).getData()), testUtil.getInterfaceMock());
		
		//check commit section
		Assert.assertTrue(view.commitMessageTextViewer.getTextWidget().getText().isEmpty());
		Assert.assertEquals(view.authorText.getText(), "Lorem Ipsum <lorem.ipsum@xxx.com>");
		Assert.assertEquals(view.committerText.getText(), "Lorem Ipsum <lorem.ipsum@xxx.com>");
		
		//test satge and unstage
		stageAndUnstage();
		
		//test filter
		filter();
		
		//test refresh action
		refresh(repositoryService);
		
		//test compare action
		compare();
		
		//test copy action
		copy();
	
	}
	
	/**
	 * test file compare action
	 */
	@SuppressWarnings("restriction")
	private void compare() throws IOException {
		//set a selection in unstaged tree viewer on an xml file	
		view.unstagedTreeViewer.expandAll();
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getFile1ForClassMock()), true);
		
		//create file service mock
		IFileService fileService = createNiceMock(IFileService.class);
		expect(fileService.readLocalFileContents(anyObject(), anyObject())).andReturn("hello");
		expect(fileService.readRemoteFileContents(anyObject(), anyObject(), anyObject())).andReturn("world");
		replay(fileService);
		
		//run file compare action
		CompareAction compare = new CompareAction(view, view.unstagedTreeViewer);
		compare.setFileService(fileService);
		compare.run();
		
		//wait untill the editor is opened
		testUtil.waitInUI(3000);
		
		//find the editor reference from the workbench window
		IEditorReference[] editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for(IEditorReference editorReference: editorReferences) {
			IEditorPart editorPart = editorReference.getEditor(false);
			if(editorPart instanceof CompareEditor) {
				CompareEditor editor = (CompareEditor) editorPart;
				Assert.assertEquals("[" + project.getAdapter(IAbapProject.class).getSystemId() + "] " + "CL_DUMMY_CLAS", editor.getTitle());
				AbapGitCompareInput input = (AbapGitCompareInput) editor.getEditorInput();
				Assert.assertTrue(input instanceof AbapGitCompareInput);
				DiffNode diffNode = (DiffNode) input.getCompareResult();
				Assert.assertNotNull(diffNode);
				ITypedElement left = diffNode.getLeft();
				ITypedElement right = diffNode.getRight();
				Assert.assertTrue(left instanceof AbapGitCompareItem);
				Assert.assertTrue(right instanceof AbapGitCompareItem);
				Assert.assertEquals(left.getName(), "file1.xml" + " (Local)");
				Assert.assertEquals(right.getName(), "file1.xml" + " (Remote)");
				Assert.assertEquals(left.getType(), ITypedElement.TEXT_TYPE);
				Assert.assertEquals(right.getType(), ITypedElement.TEXT_TYPE);
				editor.getEditorSite().getPage().closeEditor(editor, false);
			}
		}
		
		//set a selection in unstaged tree viewer on a source file	
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getFile2ForClassMock()), true);
		
		fileService = createNiceMock(IFileService.class);
		expect(fileService.readLocalFileContents(anyObject(), anyObject())).andReturn("hello");
		expect(fileService.readRemoteFileContents(anyObject(), anyObject(), anyObject())).andReturn("world");
		replay(fileService);
		
		//run compare action
		compare = new CompareAction(view, view.unstagedTreeViewer);
		compare.setFileService(fileService);
		compare.run();
		
		//wait until the editor is opened
		testUtil.waitInUI(3000);
		
		//find the compare editor reference
		editorReferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for(IEditorReference editorReference: editorReferences) {
			IEditorPart editorPart = editorReference.getEditor(false);
			if(editorPart instanceof CompareEditor) {
				CompareEditor editor = (CompareEditor) editorPart;
				Assert.assertEquals("[" + project.getAdapter(IAbapProject.class).getSystemId() + "] " + "CL_DUMMY_CLAS", editor.getTitle());
				AbapGitCompareInput input = (AbapGitCompareInput) editor.getEditorInput();
				Assert.assertTrue(input instanceof AbapGitCompareInput);
				DiffNode diffNode = (DiffNode) input.getCompareResult();
				ITypedElement left = diffNode.getLeft();
				ITypedElement right = diffNode.getRight();
				Assert.assertTrue(left instanceof AbapGitCompareItem);
				Assert.assertTrue(right instanceof AbapGitCompareItem);
				Assert.assertEquals(left.getName(), "file1.abap" + " (Local)");
				Assert.assertEquals(right.getName(), "file1.abap" + " (Remote)");
//				Assert.assertEquals(left.getType(), "aclass");
//				Assert.assertEquals(right.getType(), "aclass");
				editor.getEditorSite().getPage().closeEditor(editor, false);
			}
		}
	}
	
	private void stageAndUnstage() {
		
		//set a selection in unstaged tree viewer		
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getClassMock()), true);
		//stage the selection
		view.actionStage.run(); //move class object to staged
		
		//check if unstage toolbar action is enabled
		Assert.assertEquals(view.actionUnstage.isEnabled(), true);
		Assert.assertEquals(view.commitAndPushButton.getEnabled(), true);
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
		
		//set a new selection in unstaged tree viewer
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getInterfaceMock()), true);
		//stage the selection
		view.actionStage.run(); // move interface object to staged 
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 2);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 2);
		
		//check sort order
		Assert.assertEquals(((IAbapGitObject)view.stagedTreeViewer.getTree().getItem(0).getData()), testUtil.getClassMock());
		Assert.assertEquals(((IAbapGitObject)view.stagedTreeViewer.getTree().getItem(1).getData()), testUtil.getInterfaceMock());
		
		//set a new selection in staged tree viewer
		view.stagedTreeViewer.setSelection(new StructuredSelection(testUtil.getInterfaceMock()), true);
		//unstage the selection
		view.actionUnstage.run(); // move interface object to unstaged
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
		
		//set selection on a file under non-code and meta files node in unstaged tree viewer
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getNonCodeMock()), true);
		view.unstagedTreeViewer.setExpandedState(testUtil.getNonCodeMock(), true);
		
		//test non-code and meta file
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();	
		file.setName(".abapgit.xml"); 
		file.setPath("/src/"); 
		file.setLocalState("A");
		
		view.unstagedTreeViewer.setSelection(new StructuredSelection(file), true);
		//stage the selected file
		view.actionStage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 2);
		
		//unstage the file
		view.stagedTreeViewer.setSelection(new StructuredSelection(file), true);
		view.actionUnstage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
		
		
		//test single file stage and unstage under an object
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getInterfaceMock()), true);
		view.unstagedTreeViewer.setExpandedState(testUtil.getInterfaceMock(), true);
		
		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file2.abap");
		file.setPath("/src/"); 
		file.setLocalState("M");
		
		view.unstagedTreeViewer.setSelection(new StructuredSelection(file), true);
		view.actionStage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3); //existing node is retained with the remaining files
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 2); //new node created with the file which is moved
		
		//move the second file under the same object
		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file2.xml");
		file.setPath("/src/");
		file.setLocalState("M");
		
		view.unstagedTreeViewer.setSelection(new StructuredSelection(file), true);
		view.actionStage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 2); //object is removed as all the files are moved to staged
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 2); //file is added to the existing node in the staged tree viewer
		
		//check unstaging for the same steps
		view.stagedTreeViewer.setSelection(new StructuredSelection(file), true);
		view.actionUnstage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 2);
		
		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file2.abap");
		file.setPath("/src/"); 
		file.setLocalState("M");
		
		view.stagedTreeViewer.setSelection(new StructuredSelection(file), true);
		view.actionUnstage.run();
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
	}
	
	private void filter() {
		//test filter box
		view.filterText.setText("IF_DUMMY_INTF");
		view.filterText.notifyListeners(SWT.Modify, new Event());
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 1);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
		
		view.filterText.setText("CL_DUMMY_CLAS");
		view.filterText.notifyListeners(SWT.Modify, new Event());
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 0);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
		
		view.filterText.setText("");
		view.filterText.notifyListeners(SWT.Modify, new Event());
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 3);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 1);
	}
	
	private void refresh(IRepositoryService repositoryService) {
		//check if refresh action updates the changes in the UI, if there are any
		repositoryService = createNiceMock(IRepositoryService.class);
		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getStagingDataForRefresh());
		replay(repositoryService);
		view.repoService = repositoryService;
		view.actionRefresh.run();
		testUtil.waitInUI(3000);
		
		//check if tree viewers are loaded properly
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 2);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
	}
	
	private void copy() {
		//test copy action for an object
		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getClassMock()));

		Event e = new Event();
		e.keyCode = 99;
		e.doit = true;
		e.character = 'C' | 'c';
		
		String OS = System.getProperty("os.name").toLowerCase();
		if(OS.contains("win"))
		e.stateMask = SWT.CTRL;
		
		if(OS.contains("mac"))
			e.stateMask = SWT.COMMAND;
		
		e.widget = view.unstagedTreeViewer.getTree();
		KeyEvent keyEvent = new KeyEvent(e);
		Listener[] listeners = view.unstagedTreeViewer.getTree().getListeners(SWT.KeyDown);
		for(Listener listener : listeners) {
			if(listener instanceof TypedListener) {
				KeyListener keyListener = (KeyListener) ((TypedListener) listener).getEventListener();
				keyListener.keyPressed(keyEvent);
				final Clipboard clipboard = new Clipboard(view.getViewSite().getShell().getDisplay());
				String content = (String) clipboard.getContents(TextTransfer.getInstance());
				Assert.assertEquals(content, "CL_DUMMY_CLAS");
			}
		}
		
		view.unstagedTreeViewer.setExpandedState(testUtil.getClassMock(), true);
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file1.xml"); 
		file.setPath("/src/"); 
		file.setLocalState("A");
		
		//test copy action for a file
		view.unstagedTreeViewer.setSelection(new StructuredSelection(file));

		listeners = view.unstagedTreeViewer.getTree().getListeners(SWT.KeyDown);
		for(Listener listener : listeners) {
			if(listener instanceof TypedListener) {
				KeyListener keyListener = (KeyListener) ((TypedListener) listener).getEventListener();
				keyListener.keyPressed(keyEvent);
				final Clipboard clipboard = new Clipboard(view.getViewSite().getShell().getDisplay());
				String content = (String) clipboard.getContents(TextTransfer.getInstance());
				Assert.assertEquals(content, "file1.xml");
			}
		}
	}
	
	@Test 
	public void openEmptyRepository() {
		view.resetStagingView();
		IRepository repository = createNiceMock(IRepository.class);
		expect(repository.getUrl()).andReturn("https://github.com/AbapGit-Push/lorem_ipsum").anyTimes();
		expect(repository.getBranchName()).andReturn("master");
		replay(repository);
		
		IExternalRepositoryInfo externalRepositoryInfo = createNiceMock(IExternalRepositoryInfo.class);
		expect(externalRepositoryInfo.getAccessMode()).andReturn(AccessMode.PUBLIC);
		replay(externalRepositoryInfo);
		
		IRepositoryService repositoryService = createNiceMock(IRepositoryService.class);
		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getEmptyStagingData());
		replay(repositoryService);
		
		IExternalRepositoryInfoService externalRepositoryService = createNiceMock(IExternalRepositoryInfoService.class);
		expect(externalRepositoryService.getExternalRepositoryInfo(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(externalRepositoryInfo);
		replay(externalRepositoryService);
		
		IAbapGitStagingService stagingService = createNiceMock(IAbapGitStagingService.class);
		expect(stagingService.ensureLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingUtil = stagingService;
		
		view.openStagingView(repository, project);
		testUtil.waitInUI(10000);
		
		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 0);
		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
		
		Assert.assertEquals(view.mainForm.getText(), "lorem_ipsum [master] [ABAPGIT_TEST_PROJECT]");
		
		Assert.assertTrue(view.commitMessageTextViewer.getTextWidget().getText().isEmpty());
		Assert.assertEquals(view.authorText.getText(), "Lorem Ipsum <lorem.ipsum@xxx.com>");
		Assert.assertEquals(view.committerText.getText(), "Lorem Ipsum <lorem.ipsum@xxx.com>");
		
		Assert.assertEquals(view.actionRefresh.isEnabled(), true);
		Assert.assertEquals(view.actionStage.isEnabled(), false);
		Assert.assertEquals(view.actionUnstage.isEnabled(), false);
		Assert.assertEquals(view.commitAndPushButton.getEnabled(), false);
	}
	
	@Test
	public void openPrivateRepository() {
		//TODO : need to implement. first need to figure out a way to hide the credentials dialog.
	}
	
	@Test
	public void validateInput() {
		view.resetStagingView();
		IRepository repository = createNiceMock(IRepository.class);
		expect(repository.getUrl()).andReturn("https://github.com/AbapGit-Push/lorem_ipsum").anyTimes();
		expect(repository.getBranchName()).andReturn("master");
		replay(repository);
		
		IExternalRepositoryInfo externalRepositoryInfo = createNiceMock(IExternalRepositoryInfo.class);
		expect(externalRepositoryInfo.getAccessMode()).andReturn(AccessMode.PUBLIC);
		replay(externalRepositoryInfo);
		
		IRepositoryService repositoryService = createNiceMock(IRepositoryService.class);
		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getEmptyStagingData());
		replay(repositoryService);
		
		IExternalRepositoryInfoService externalRepositoryService = createNiceMock(IExternalRepositoryInfoService.class);
		expect(externalRepositoryService.getExternalRepositoryInfo(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(externalRepositoryInfo);
		replay(externalRepositoryService);
		
		IAbapGitStagingService stagingService = createNiceMock(IAbapGitStagingService.class);
		expect(stagingService.ensureLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingUtil = stagingService;
		
		view.openStagingView(repository, project);
		testUtil.waitInUI(3000);
		
		//case 1 : invalid commit message
		checkCommitMessage();	
		//case 2: invalid author
		checkAuthor();	
		//case 3: invalid committer
		checkCommitter();
		//case 4: all inputs valid
		assertEquals(view.validateInputs(), 0);	
	}
	
	private void checkCommitMessage() {
		view.commitMessageTextViewer.getTextWidget().setText("message header\nsecond line content");
		int errorCode =  view.validateInputs();
		assertEquals(errorCode, 1);
		
		//reset commit message
		view.commitMessageTextViewer.getTextWidget().setText("");
	}
	
	private void checkAuthor() {
		view.authorText.setText("Lorem Ipsum <lorem.ipsum@xxx.com");
		int errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("Lorem Ipsum lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("<lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("Lorem Ipsum<lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("Lorem Ipsum");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("Lorem Ipsum <lorem.ipsum@xxx.com> more characters");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		view.authorText.setText("Lorem Ipsum >lorem.ipsum@xxx.com<");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 2);
		
		//reset author
		view.authorText.setText("Lorem Ipsum <lorem.ipsum@xxx.com>");
	}
	
	private void checkCommitter() {
		view.committerText.setText("Lorem Ipsum <lorem.ipsum@xxx.com");
		int errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("Lorem Ipsum lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("<lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("Lorem Ipsum<lorem.ipsum@xxx.com>");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("Lorem Ipsum");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("Lorem Ipsum <lorem.ipsum@xxx.com> more characters");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		view.committerText.setText("Lorem Ipsum >lorem.ipsum@xxx.com<");
		errorCode =  view.validateInputs();
		assertEquals(errorCode, 3);
		
		//reset committer
		view.committerText.setText("Lorem Ipsum <lorem.ipsum@xxx.com>");
	}
	
	@Test
	public void commitAndPush() {
//		view.resetStagingView();
//		
//		addShellActivateListener();
//		
//		IRepository repository = createNiceMock(IRepository.class);
//		expect(repository.getUrl()).andReturn("https://github.com/AbapGit-Push/lorem_ipsum").anyTimes();
//		expect(repository.getBranch()).andReturn("master");
//		replay(repository);
//		
//		IExternalRepositoryInfo externalRepositoryInfo = createNiceMock(IExternalRepositoryInfo.class);
//		expect(externalRepositoryInfo.getAccessMode()).andReturn(AccessMode.PUBLIC);
//		replay(externalRepositoryInfo);
//		
//		IRepositoryService repositoryService = createNiceMock(IRepositoryService.class);
//		repositoryService.push(anyObject(), anyObject(), anyObject(), anyObject());
//		EasyMock.expectLastCall();
//		expect(repositoryService.stage(anyObject(), anyObject(), anyObject())).andReturn(testUtil.getEmptyStagingData());
//		replay(repositoryService);
//		
//		IExternalRepositoryInfoService externalRepositoryService = createNiceMock(IExternalRepositoryInfoService.class);
//		expect(externalRepositoryService.getExternalRepositoryInfo(anyObject(), anyObject(), anyObject(), anyObject())).andReturn(externalRepositoryInfo);
//		replay(externalRepositoryService);
//		
//		view.repoExternaService = externalRepositoryService;
//		view.repoService = repositoryService;
//		
//		view.openStagingView(repository, project);
//		TestUtilEclipseUI.waitInUI(3000);
//		
//		//set a selection in unstaged tree viewer		
//		view.unstagedTreeViewer.setSelection(new StructuredSelection(testUtil.getClassMock()), true);
//		//stage the selection
//		view.actionStage.run(); //move class object to staged
//		
//		view.commitMessageTextViewer.getTextWidget().setText("lorem ipsum");
//		
//		view.commitAndPushButton.notifyListeners(SWT.Selection, new Event());
//		
//		Assert.assertEquals(view.unstagedTreeViewer.getTree().getItemCount(), 0);
//		Assert.assertEquals(view.stagedTreeViewer.getTree().getItemCount(), 0);
	}
	
//	public void addShellActivateListener() {
//		Display.getDefault().addFilter(SWT.Activate, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				if (event.widget instanceof Shell) {
//					final Shell shell = (Shell) event.widget;
//					if (shell.isDisposed()) {
//						return;
//					}
//					if(Messages.AbapGitStaging_push_job_successful.equals(shell.getText())) {
//						Display.getDefault().syncExec(new Runnable() {
//							@Override
//							public void run() {
//								//shell.close();
//							}
//						});
//					}
//					if(shell.getData() instanceof AbapGitStagingCredentialsDialog) {
//						AbapGitStagingCredentialsDialog dialog = (AbapGitStagingCredentialsDialog) shell.getData();
//						dialog.usernameField.setText("lorem_ipsum");
//						dialog.passwordField.setText("lorem_ipsum");
//						dialog.okPressed();
//					}
//				}
//			}
//		});	
//	}

}
