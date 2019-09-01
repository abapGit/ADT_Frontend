package org.abapgit.adt.ui.internal.util;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

//TODO Refactor code
public class FileStagingModelUtil {

	public static void stageObjects(TreeViewer unstagedTreeViewer, IStructuredSelection selection, IAbapGitStaging model,
			List<Object> expandedNodes) {
		IAbapGitObject abapObject;
		IAbapGitFile file;

		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				abapObject = (IAbapGitObject) object;
				if (unstagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObject);
				}
				stageObject(model, abapObject);
			} else if (object instanceof IAbapGitFile) {
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				file = (IAbapGitFile) object;
				expandedNodes.add(abapObject);
				stageFile(model, abapObject, file);
			}
		}
	}

	protected static void stageObject(IAbapGitStaging model, IAbapGitObject abapObject) {
		//check if the staged changes already contains the object
		List<IAbapGitObject> stagedObjects = model.getStagedObjects().getAbapgitobject();
		boolean objectFound = false;
		for (IAbapGitObject stagedObject : stagedObjects) {
			if (stagedObject.equals(abapObject)) {
				//staged changes already contains the object
				//add only the files to the staged changes
				stagedObject.getFiles().addAll(abapObject.getFiles());
				//remove object from unstaged changes
				model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
				objectFound = true;
				break;
			}
		}
		if (!objectFound) {
			model.getStagedObjects().getAbapgitobject().add(abapObject);
			model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
		}
	}

	protected static void stageFile(IAbapGitStaging model, IAbapGitObject abapObject, IAbapGitFile file) {
		//check if the staged changes already contains the object
		List<IAbapGitObject> stagedObjects = model.getStagedObjects().getAbapgitobject();
		boolean objectFound = false;
		for (IAbapGitObject stagedObject : stagedObjects) {
			if (stagedObject.equals(abapObject)) {
				//staged changes already contains the object
				//add the file being staged to the staged changes
				stagedObject.getFiles().add(file);
				//remove the file from unstaged changes
				abapObject.getFiles().remove(file);
				//remove the object from unstaged changes if the object contains no files after the operation
				if (abapObject.getFiles().size() == 0) {
					model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
				}
				objectFound = true;
				break;
			}
		}
		if (!objectFound) {
			//if parent object contains only one file, move the object to the staged changes
			if (abapObject.getFiles().size() == 1) {
				model.getStagedObjects().getAbapgitobject().add(abapObject);
				model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
			} else {
				//create a copy of the parent object with only the file being staged as child
				IAbapGitObject objectCopy = EcoreUtil.copy(abapObject);
				objectCopy.getFiles().clear();
				objectCopy.getFiles().add(file);
				//add the object copy to staged changes
				model.getStagedObjects().getAbapgitobject().add(objectCopy);
				//remove file from unstaged changes
				abapObject.getFiles().remove(file);
				//remove the object from unstaged changes if the object contains no files after the operation
				if (abapObject.getFiles().size() == 0) {
					model.getUnstagedObjects().getAbapgitobject().remove(abapObject);
				}
			}
		}
	}

	public static void unstageObjects(TreeViewer stagedTreeViewer, IStructuredSelection selection, IAbapGitStaging model,
			List<Object> expandedNodes) {
		IAbapGitObject abapObject;
		IAbapGitFile file;

		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				abapObject = (IAbapGitObject) object;
				if (stagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObject);
				}
				unstageObject(model, abapObject);
			} else if (object instanceof IAbapGitFile) {
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				file = (IAbapGitFile) object;
				expandedNodes.add(abapObject);
				unstageFile(model, abapObject, file);
			}
		}
	}

	protected static void unstageObject(IAbapGitStaging model, IAbapGitObject abapObject) {
		//check if the unstaged changes already contains the object
		List<IAbapGitObject> unstagedObjects = model.getUnstagedObjects().getAbapgitobject();
		boolean objectFound = false;
		for (IAbapGitObject unstagedObject : unstagedObjects) {
			if (unstagedObject.equals(abapObject)) {
				//unstaged changes already contains the object
				//add only the files to the staged changes
				unstagedObject.getFiles().addAll(abapObject.getFiles());
				//remove object from staged changes
				model.getStagedObjects().getAbapgitobject().remove(abapObject);
				objectFound = true;
				break;
			}
		}
		if (!objectFound) {
			model.getUnstagedObjects().getAbapgitobject().add(abapObject);
			model.getStagedObjects().getAbapgitobject().remove(abapObject);
		}
	}

	protected static void unstageFile(IAbapGitStaging model, IAbapGitObject abapObject, IAbapGitFile file) {
		//check if the staged changes already contains the object
		List<IAbapGitObject> unstagedObjects = model.getUnstagedObjects().getAbapgitobject();
		boolean objectFound = false;
		for (IAbapGitObject unstagedObject : unstagedObjects) {
			if (unstagedObject.equals(abapObject)) {
				//unstaged changes already contains the object
				//add the file being staged to the staged changes
				unstagedObject.getFiles().add(file);
				//remove the file from staged changes
				abapObject.getFiles().remove(file);
				//remove the object from staged changes if the object contains no files after the operation
				if (abapObject.getFiles().size() == 0) {
					model.getStagedObjects().getAbapgitobject().remove(abapObject);
				}
				objectFound = true;
				break;
			}
		}
		if (!objectFound) {
			//if parent object contains only one file, move the object to the unstaged changes
			if (abapObject.getFiles().size() == 1) {
				model.getUnstagedObjects().getAbapgitobject().add(abapObject);
				model.getStagedObjects().getAbapgitobject().remove(abapObject);
			} else {
				//create a copy of the parent object with only the file being unstaged as child
				IAbapGitObject objectCopy = EcoreUtil.copy(abapObject);
				objectCopy.getFiles().clear();
				objectCopy.getFiles().add(file);
				//add the object copy to unstaged changes
				model.getUnstagedObjects().getAbapgitobject().add(objectCopy);
				//remove file from staged changes
				abapObject.getFiles().remove(file);
				//remove the object from staged changes if the object contains no files after the operation
				if (abapObject.getFiles().size() == 0) {
					model.getStagedObjects().getAbapgitobject().remove(abapObject);
				}
			}
		}
	}

}
