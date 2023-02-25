package org.abapgit.adt.ui.internal.staging.util;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingGroupNode;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

//TODO Refactor code
public class FileStagingModeUtil {

	private static List<IAbapGitObject> movedObjects = new ArrayList<IAbapGitObject>();

	private static AbapGitStagingGroupNode get_parent(Viewer viewer, IAbapGitObject abapGitObject) {
		TreeViewer treeViewer = (TreeViewer) viewer;

		Object[] input = (Object[]) treeViewer.getInput();

		for (Object obj : input) {
			if (obj instanceof AbapGitStagingGroupNode) {
				if (((AbapGitStagingGroupNode) obj).getAbapGitObjects().contains(abapGitObject)) {
					return (AbapGitStagingGroupNode) obj;
				}
			}
		}

		return null;
	}

	public static void stageObjects(TreeViewer unstagedTreeViewer, IStructuredSelection selection, IAbapGitStaging model,
			List<Object> expandedNodes) {
		IAbapGitObject abapObject;
		IAbapGitFile file;
		AbapGitStagingGroupNode groupNode;

		movedObjects.clear();
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
				groupNode = FileStagingModeUtil.get_parent(unstagedTreeViewer, abapObject);
				file = (IAbapGitFile) object;
				if (movedObjects.contains(abapObject)) {
					continue;
				}
				if (groupNode != null) {
					expandedNodes.add(groupNode);
				}
				expandedNodes.add(abapObject);
				if (groupNode != null) {
					stageFile(model, abapObject, file, groupNode);

				} else {
					stageFile(model, abapObject, file);

				}
			} else if (object instanceof AbapGitStagingGroupNode) {
				groupNode = (AbapGitStagingGroupNode) object;
				if (unstagedTreeViewer.getExpandedState(groupNode)) {
					expandedNodes.add(groupNode);
				}
				stageGroupNode(model, groupNode);

			}
		}
	}

	protected static void stageGroupNode(IAbapGitStaging model, AbapGitStagingGroupNode groupNode) {
		//check if the staged changes already contains all the objects
		List<IAbapGitObject> stagedObjects = model.getStagedObjects().getAbapgitobject();
		List<IAbapGitObject> groupNodeObjects = groupNode.getAbapGitObjects();

		for (IAbapGitObject groupNodeObject : groupNodeObjects) {
			boolean objectFound = false;
			for (IAbapGitObject stagedObject : stagedObjects) {
				if (stagedObject.equals(groupNodeObject)) {
					objectFound = true;
					//add only the files to the staged changes
					stagedObject.getFiles().addAll(groupNodeObject.getFiles());
					//remove object from unstaged changes
					model.getUnstagedObjects().getAbapgitobject().remove(groupNodeObject);
					break;
				}
			}
			if(objectFound == false) {
				model.getStagedObjects().getAbapgitobject().add(groupNodeObject);
				model.getUnstagedObjects().getAbapgitobject().remove(groupNodeObject);

				movedObjects.add(groupNodeObject);
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
		movedObjects.add(abapObject);
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

	protected static void stageFile(IAbapGitStaging model, IAbapGitObject abapObject, IAbapGitFile file,
			AbapGitStagingGroupNode groupNode) {
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

		movedObjects.clear();
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
				if (movedObjects.contains(abapObject)) {
					continue;
				}
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
		movedObjects.add(abapObject);
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
