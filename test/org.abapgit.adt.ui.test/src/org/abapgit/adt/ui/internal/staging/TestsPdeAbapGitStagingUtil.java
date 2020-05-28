package org.abapgit.adt.ui.internal.staging;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.destinations.model.AdtDestinationDataFactory;
import com.sap.adt.destinations.model.IDestinationData;
import com.sap.adt.destinations.model.IDestinationDataWritable;
import com.sap.adt.tools.core.internal.AbapProject;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;
import com.sap.adt.tools.core.project.IAbapProjectService;

public class TestsPdeAbapGitStagingUtil {	
	
	public AbapGitStagingView initializeView() throws PartInitException{
		AbapGitStagingView view;
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IPerspectiveDescriptor abapPerspective = PlatformUI.getWorkbench()
				.getPerspectiveRegistry()
				.findPerspectiveWithId("com.sap.adt.ui.AbapPerspective"); //$NON-NLS-1$
		activePage.setPerspective(abapPerspective);
		view = ((AbapGitStagingView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().showView(AbapGitStagingView.VIEW_ID));
		view.init(view.getViewSite());
		Shell shell = new Shell(Display.getDefault().getActiveShell());
		Composite parent = new Composite(shell, SWT.NONE);
		view.createPartControl(parent);				
		return view;
	}
	
	protected IProject createDummyAbapProject(String projectName) throws CoreException{
		String destinationId = projectName;
		
		IDestinationDataWritable data = AdtDestinationDataFactory.newDestinationData(destinationId);
		data.setUser("TEST_DUMMY_USER"); 
		data.setClient("777"); 
		data.setLanguage("DE"); 
		data.setPassword("TEST_DUMMY_PW"); 
		
		String projectDestinationId = AdtProjectServiceFactory.createProjectService().createDestinationId(projectName);
		final IDestinationData destinationData = data.getReadOnlyClone(projectDestinationId);
		
		final IAbapProjectService abapProjectService = AdtProjectServiceFactory.createProjectService();
		//IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final AbapProject[] projects = new AbapProject[1];
		workspace.run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				projects[0] = (AbapProject) abapProjectService.createAbapProject(projectName, destinationData, monitor);
			}
		}, new NullProgressMonitor());
		return projects[0].getProject();
	}
	
	protected void waitInUI(long timeout){
		Display display = Display.getCurrent();
		final long start = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - start > timeout) {
				return;
			}
			try {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			} catch (Exception e) {
			}
		}
	}
	
	public IAbapGitStaging getEmptyStagingData() {
		IAbapGitStaging staging = getStaging();
		staging.setCommitMessage(getCommitMessageMock());
		return staging;
	}
	
	public IAbapGitStaging getStagingTestData() {
		IAbapGitStaging staging = getStaging();
		
		//test data for commit section
		staging.setCommitMessage(getCommitMessageMock());
		//Non-code and meta files
		staging.getUnstagedObjects().getAbapgitobject().add(getNonCodeMock());
		//class
		staging.getUnstagedObjects().getAbapgitobject().add(getClassMock());
		//interface
		staging.getUnstagedObjects().getAbapgitobject().add(getInterfaceMock());
		//table
		staging.getUnstagedObjects().getAbapgitobject().add(getTableMock());

		return staging;
	}
	
	public IAbapGitStaging getStaging() {
		IAbapGitStaging staging = IAbapgitstagingFactory.eINSTANCE.createAbapGitStaging();
		IUnstagedObjects unstaged = IAbapgitstagingFactory.eINSTANCE.createUnstagedObjects();
		IStagedObjects staged = IAbapgitstagingFactory.eINSTANCE.createStagedObjects();
		staging.setUnstagedObjects(unstaged);
		staging.setStagedObjects(staged);
		
		return staging;
	}
	
	public IAbapGitCommitMessage getCommitMessageMock() {
		IAbapGitCommitMessage commitMessage = IAbapgitstagingFactory.eINSTANCE.createAbapGitCommitMessage();
		IAuthor author = IAbapgitstagingFactory.eINSTANCE.createAuthor();
		ICommitter committer = IAbapgitstagingFactory.eINSTANCE.createCommitter();
		committer.setName("Lorem Ipsum");
		committer.setEmail("lorem.ipsum@xxx.com");
		commitMessage.setCommitter(committer);
		author.setName("Lorem Ipsum");
		author.setEmail("lorem.ipsum@xxx.com");
		commitMessage.setAuthor(author);
		
		return commitMessage;
	}
	
	public IAbapGitObject getNonCodeMock() {
		IAbapGitObject object = IAbapgitstagingFactory.eINSTANCE.createAbapGitObject();
		object.setName("non-code and meta files"); 
		
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName(".abapgit.xml"); 
		file.setPath("/src/"); 
		file.setLocalState("A");;
		object.getFiles().add(file);

		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("license.xml");
		file.setPath("/src/"); 
		file.setLocalState("A");
		object.getFiles().add(file);
		
		return object;
	}
	
	public IAbapGitObject getClassMock() {
		IAbapGitObject object = IAbapgitstagingFactory.eINSTANCE.createAbapGitObject();
		object.setName("CL_DUMMY_CLAS"); 
		object.setType("CLAS"); 
		object.setWbkey("CLAS/OC");
		object.setUri("/sap/bc/adt/oo/classes/CL_DUMMY_CLAS");

		object.getFiles().add(getFile1ForClassMock());
		object.getFiles().add(getFile2ForClassMock());
		
		return object;
	}
	
	public IAbapGitFile getFile1ForClassMock() {
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file1.xml"); 
		file.setPath("/src/"); 
		file.setLocalState("A");
		return file;
	}
	
	public IAbapGitFile getFile2ForClassMock() {
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file1.abap");
		file.setPath("/src/"); 
		file.setLocalState("M");
		return file;
	}
	
	public IAbapGitObject getInterfaceMock() {
		IAbapGitObject object = IAbapgitstagingFactory.eINSTANCE.createAbapGitObject();
		object.setName("IF_DUMMY_INTF");
		object.setType("INTF"); 
		object.setWbkey("INTF/OI");
		object.setUri("/sap/bc/adt/interfaces/IF_DUMMY_INTF");

		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file2.xml");
		file.setPath("/src/");
		file.setLocalState("M");
		object.getFiles().add(file);

		file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file2.abap");
		file.setPath("/src/");
		file.setLocalState("M");
		object.getFiles().add(file);
		
		return object;
	}
	
	public IAbapGitObject getTableMock() {
		IAbapGitObject object = IAbapgitstagingFactory.eINSTANCE.createAbapGitObject();
		object.setName("DUMMY_TABL");
		object.setType("TABL");
		object.setWbkey("TABL/DT");
		object.setUri("/sap/bc/adt/table/DUMMY_TABL");
	
		IAbapGitFile file = IAbapgitstagingFactory.eINSTANCE.createAbapGitFile();
		file.setName("file3.xml");
		file.setPath("/src/");
		file.setLocalState("D");
		object.getFiles().add(file);
		
		return object;
	}
	
	public IAbapGitStaging getStagingDataForRefresh() {
		IAbapGitStaging staging = getStaging();
		
		//commit message test data
		staging.setCommitMessage(getCommitMessageMock());
		//Non-code and meta files
		staging.getUnstagedObjects().getAbapgitobject().add(getNonCodeMock());
		//class
		staging.getUnstagedObjects().getAbapgitobject().add(getClassMock());

		return staging;
	}
	
	protected IRepository getRepositoryMock(String url, String branch, String linkedPackage, String createdBy) {
		IRepository repository = createNiceMock(IRepository.class);
		expect(repository.getUrl()).andReturn(url).anyTimes();
		expect(repository.getBranchName()).andReturn(branch).anyTimes();
		expect(repository.getPackage()).andReturn(linkedPackage).anyTimes();
		expect(repository.getCreatedBy()).andReturn(createdBy).anyTimes();
		replay(repository);
		return repository;
	}
	
	protected String getLoggedOnUser(IProject project) {
		IAbapProject abapProject = project.getAdapter(IAbapProject.class);
		return abapProject.getDestinationData().getUser();
	}
}
