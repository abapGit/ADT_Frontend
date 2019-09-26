package org.abapgit.adt.ui.internal.views;

import static org.easymock.EasyMock.anyObject;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.util.IAbapGitStagingService;
import org.abapgit.adt.ui.internal.views.AbapGitStagingView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestsPdeAbapGitStaging {
	
	private static AbapGitStagingView view;
	private static IProject project;
	private static TestsPdeAbapGitStagingUtil testUtil;
	
	@BeforeClass
	public static void setUp() throws CoreException{
		testUtil = new TestsPdeAbapGitStagingUtil();
		view = testUtil.initializeView();
		project = testUtil.createDummyAbapProject();
		view.project = project;
	}	
	
	@AfterClass
	public static void disposeControls() throws CoreException {
		project.delete(true, true, null);
		view.dispose();
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
	public void openPublicRepository() {
		view.resetStagingView();
		
		//create mocks
		
		//repository
		IRepository repository = createNiceMock(IRepository.class);
		expect(repository.getUrl()).andReturn("https://github.com/AbapGit-Push/lorem_ipsum").anyTimes();
		expect(repository.getBranch()).andReturn("master");
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
		expect(stagingService.isLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingService = stagingService;
		
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
		
		//test copy action
		copy();
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
		e.stateMask = SWT.ALT;
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
		expect(repository.getBranch()).andReturn("master");
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
		expect(stagingService.isLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingService = stagingService;
		
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
		expect(repository.getBranch()).andReturn("master");
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
		expect(stagingService.isLoggedOn(anyObject())).andReturn(true);
		replay(stagingService);
		
		//inject mocks
		view.repoExternaService = externalRepositoryService;
		view.repoService = repositoryService;
		view.stagingService = stagingService;
		
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
