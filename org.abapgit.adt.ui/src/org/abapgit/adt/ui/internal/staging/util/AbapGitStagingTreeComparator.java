package org.abapgit.adt.ui.internal.staging.util;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class AbapGitStagingTreeComparator extends ViewerComparator {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return compareElements(viewer, e1, e2);
	}

	private int compareElements(Viewer viewer, Object e1, Object e2) {
		TreeViewer treeViewer = (TreeViewer) viewer;
		if (e1 instanceof IAbapGitObject && e2 instanceof IAbapGitObject) {
			IAbapGitObject object1 = (IAbapGitObject) e1;
			IAbapGitObject object2 = (IAbapGitObject) e2;
			//set "non-code and meta files" as the first node
			if (object1.getType() == null) {
				return treeViewer.getTree().getSortDirection() == SWT.UP ? -1 : 1;
			}
			if (object2.getType() == null) {
				return treeViewer.getTree().getSortDirection() == SWT.UP ? 1 : -1;
			}
			int result = object1.getName().compareToIgnoreCase(object2.getName());
			return treeViewer.getTree().getSortDirection() == SWT.UP ? result : -result;
		}
		return 0;
	}
}
