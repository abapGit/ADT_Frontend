package org.abapgit.adt.ui.internal.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.sap.adt.destinations.model.AdtDestinationDataFactory;
import com.sap.adt.destinations.model.IDestinationData;
import com.sap.adt.destinations.model.IDestinationDataWritable;
import com.sap.adt.tools.core.internal.AbapProject;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProjectService;

public class TestPdeAbapGitWizardUtil {
	
	public IProject createDummyAbapProject(String projectName) throws CoreException {
        String destinationId = projectName;
        IDestinationDataWritable data = AdtDestinationDataFactory.newDestinationData(destinationId);
        data.setUser("TEST_DUMMY_USER");
        data.setClient("777");
        data.setLanguage("DE");
        data.setPassword("TEST_DUMMY_PW");

        String projectDestinationId = AdtProjectServiceFactory.createProjectService().createDestinationId(projectName);
        final IDestinationData destinationData = data.getReadOnlyClone(projectDestinationId);
        
        

        final IAbapProjectService abapProjectService = AdtProjectServiceFactory.createProjectService();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final AbapProject[] projects = new AbapProject[1];

        // Clean up if project already exists
        IProject project = workspace.getRoot().getProject(projectName);
        if (project.exists()) {
            project.delete(true, true, null);
        }

        workspace.run(new IWorkspaceRunnable() {
            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                projects[0] = (AbapProject) abapProjectService.createAbapProject(projectName, destinationData, monitor);
            }
        }, new NullProgressMonitor());
        return projects[0].getProject();
    }

}
