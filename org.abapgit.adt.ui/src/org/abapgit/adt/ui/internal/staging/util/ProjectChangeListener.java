package org.abapgit.adt.ui.internal.staging.util;

import org.abapgit.adt.ui.internal.staging.AbapGitStagingView;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.swt.widgets.Display;

/**
 * Listener for project delete and close event. This is to reset the abapgit
 * staging view in case the selected project is closed or deleted.
 */
public class ProjectChangeListener implements IResourceChangeListener {

	private final IWorkspace workspace;
	private final AbapGitStagingView view;
	private IProject project;

	public ProjectChangeListener(AbapGitStagingView view, IWorkspace workspace) {
		this.view = view;
		this.workspace = workspace;
		this.workspace.addResourceChangeListener(this, IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.PRE_CLOSE);
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		IResource resource = event.getResource();
		if (resource instanceof IProject) {
			if (event.getType() == IResourceChangeEvent.PRE_DELETE || event.getType() == IResourceChangeEvent.PRE_CLOSE) {
				if (this.project.equals(resource)) {
					//reset the staging view to initial
					Display.getDefault().syncExec(() -> ProjectChangeListener.this.view.resetStagingView());
				}
			}
		}
	}
}
