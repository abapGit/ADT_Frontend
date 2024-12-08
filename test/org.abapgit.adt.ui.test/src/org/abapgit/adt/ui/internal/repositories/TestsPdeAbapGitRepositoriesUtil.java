package org.abapgit.adt.ui.internal.repositories;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
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
import com.sap.adt.tools.core.project.IAbapProjectService;

public class TestsPdeAbapGitRepositoriesUtil {

	public AbapGitView initializeView() throws PartInitException {
		AbapGitView view;
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IPerspectiveDescriptor abapPerspective = PlatformUI.getWorkbench()
				.getPerspectiveRegistry()
				.findPerspectiveWithId("com.sap.adt.ui.AbapPerspective"); //$NON-NLS-1$
		activePage.setPerspective(abapPerspective);
		view = ((AbapGitView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().showView(AbapGitView.ID));
		view.init(view.getViewSite());
		Shell shell = new Shell(Display.getDefault().getActiveShell());
		Composite parent = new Composite(shell, SWT.NONE);
		view.createPartControl(parent);				
		return view;
	}

	protected IProject createDummyAbapProject() throws CoreException{
		String projectName = "ABAPGIT_TEST_PROJECT";
		String destinationId = "ABAPGIT_TEST_PROJECT";
		
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
	
	public IRepository createDummyRepository() {
		IRepository dummy = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		dummy.setUrl("https://github.com/dummy_url");
		dummy.setPackage("$AP_GITHUB");
		dummy.setCreatedEmail("dummy_user_one@email.com");
		dummy.setBranchName("refs/heads/master");
		dummy.setDeserializedAt("20200322180503");
		dummy.setStatusText("dummy_status");
		return dummy;
	}

}
