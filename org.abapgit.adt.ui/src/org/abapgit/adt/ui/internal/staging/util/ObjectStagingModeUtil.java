package org.abapgit.adt.ui.internal.staging.util;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

//TODO Refactor code
public class ObjectStagingModeUtil {

	/**
	 * Move selected objects from unstaged changes to staged changes
	 *
	 * @param selection
	 *            selection from unstaged tree viewer
	 * @param model
	 *            view model
	 * @param expandedNodes
	 *            Will contain the list of expanded objects in the selection
	 *            after the method execution
	 */
	public static void stageObjects(TreeViewer unstagedTreeViewer, IStructuredSelection selection, IAbapGitStaging model,
			List<Object> expandedNodes) {

		IAbapGitObject abapObject = null;
		IAbapGitFile file = null;
		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				//if selection is an object
				abapObject = (IAbapGitObject) object;
				if (abapObject.getType() == null) {
					//if non-code and meta files node, enable single file drag and drop mode
					FileStagingModeUtil.stageObject(model, abapObject);
				} else {
					//move the object from unstaged changes to the staged changes
					model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
					model.getStagedObjects().getAbapgitobject().add(abapObject);
				}
			} else if (object instanceof IAbapGitFile) {
				//if selection is a file
				file = (IAbapGitFile) object;
				//get parent object
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				if (abapObject.getType() == null) {
					//if non-code and meta files node, enable single file drag and drop mode
					FileStagingModeUtil.stageFile(model, abapObject, file);
				} else {
					//move the object from unstaged changes to the staged changes
					model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
					model.getStagedObjects().getAbapgitobject().add(abapObject);
				}
			}
			//get expanded state of the selected object
			if (unstagedTreeViewer.getExpandedState(abapObject)) {
				expandedNodes.add(abapObject);
			}
		}

	}

	/**
	 * Move selected objects from staged changes to unstaged changes
	 *
	 * @param selection
	 *            selection from staged tree viewer
	 * @param model
	 *            view model
	 * @param expandedNodes
	 *            Will contain the list of expanded objects in the selection
	 *            after the method execution
	 */
	public static void unstageObjects(TreeViewer stagedTreeViewer, IStructuredSelection selection, IAbapGitStaging model,
			List<Object> expandedNodes) {

		IAbapGitObject abapObject = null;
		IAbapGitFile file = null;
		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				//if selection is an object
				abapObject = (IAbapGitObject) object;
				if (abapObject.getType() == null) {
					//if non-code and meta files node, enable single file drag and drop mode
					FileStagingModeUtil.unstageObject(model, abapObject);
				} else {
					//move the object from staged changes to the unstaged changes
					model.getStagedObjects().getAbapgitobject().remove(abapObject);
					model.getUnstagedObjects().getAbapgitobject().add(abapObject);
				}
			} else if (object instanceof IAbapGitFile) {
				//if selection is a file
				file = (IAbapGitFile) object;
				//get parent object
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				if (abapObject.getType() == null) {
					//if non-code and meta files node, enable single file drag and drop mode
					FileStagingModeUtil.unstageFile(model, abapObject, file);
				} else {
					//move the object from staged changes to the unstaged changes
					model.getStagedObjects().getAbapgitobject().remove(abapObject);
					model.getUnstagedObjects().getAbapgitobject().add(abapObject);
				}
			}
			//get expanded state of the selected object
			if (stagedTreeViewer.getExpandedState(abapObject)) {
				expandedNodes.add(abapObject);
			}
		}
	}

}
