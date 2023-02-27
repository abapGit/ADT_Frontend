package org.abapgit.adt.ui.internal.staging.util;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingGrouping;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

//TODO Refactor code
public class FileStagingModeUtilForGroupNode {

	private static List<IAbapGitObject> movedObjects = new ArrayList<IAbapGitObject>();

	public static void stageObjects(TreeViewer unstagedTreeViewer, IStructuredSelection selection, IAbapGitStagingGrouping model,
			List<Object> expandedNodes) {
		IAbapGitObject abapObject;
		IAbapGitFile file;
		IAbapGitStagingGroupNode groupNode;

		movedObjects.clear();
		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				abapObject = (IAbapGitObject) object;
				if (unstagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObject);
				}
				if (abapObject.eContainer() != null) {
					expandedNodes.add(abapObject.eContainer());
				}
				stageObject(unstagedTreeViewer, model, abapObject, expandedNodes);
			} else if (object instanceof IAbapGitFile) {
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				groupNode = (IAbapGitStagingGroupNode) abapObject.eContainer();
				file = (IAbapGitFile) object;
				if (movedObjects.contains(abapObject)) {
					continue;
				}
				expandedNodes.add(groupNode);
				expandedNodes.add(abapObject);

				stageFile(unstagedTreeViewer, model, abapObject, file, groupNode, expandedNodes);
			} else if (object instanceof IAbapGitStagingGroupNode) {
				groupNode = (IAbapGitStagingGroupNode) object;
				if (unstagedTreeViewer.getExpandedState(groupNode)) {
					expandedNodes.add(groupNode);
				}

				for (IAbapGitObject unstagedObject : groupNode.getAbapgitobjects()) {
					if (unstagedTreeViewer.getExpandedState(unstagedObject)) {
						expandedNodes.add(unstagedObject);
					}
				}

				stageGroupNode(unstagedTreeViewer, model, groupNode, expandedNodes);

			}
		}
	}

	protected static void stageGroupNode(TreeViewer unstagedTreeViewer, IAbapGitStagingGrouping model,
			IAbapGitStagingGroupNode groupNode, List<Object> expandedNodes) {

		//Check if the group node already exists in the staged changes
		//If it exists
		//For all objects under the group node, check if they already exist in the staged section
		//If an object exists,
		//a. Copy all files from this object in unstaged area to object in staged area
		//IF an object does not exist
		//a. Copy this object to the group node in staged section
		//Remove the group node in unstaged section
		boolean groupNodeFound = false;

		//Check if the group node already exists in the staged changes
		//If it exists
		for (IAbapGitStagingGroupNode groupNodeStaged : model.getStagedGroupObjects()) {
			if (groupNodeStaged.equals(groupNode)) {
				//Group Node exists in the staged changes
				//For all objects under the group node, check if they already exist in the staged section
				for (IAbapGitObject unStagedAbapGitObject : groupNode.getAbapgitobjects()) {
					boolean unStagedObjectFound = false;
					for (IAbapGitObject stagedAbapGitObject : groupNodeStaged.getAbapgitobjects()) {
						if (stagedAbapGitObject.equals(unStagedAbapGitObject)) {
							unStagedObjectFound = true;
							//If an object exists,
							//a. Copy all files from this object in unstaged area to object in staged area
							stagedAbapGitObject.getFiles().addAll(unStagedAbapGitObject.getFiles());
							break;
						}
					}

					if (unStagedObjectFound == false) {
						//IF an object does not exist
						//a. Copy this object to the group node in staged section
						IAbapGitObject unStagedAbapGitObjectCopy = EcoreUtil.copy(unStagedAbapGitObject);
						groupNodeStaged.getAbapgitobjects().add(unStagedAbapGitObjectCopy);
						if (unstagedTreeViewer.getExpandedState(unStagedAbapGitObject)) {
							expandedNodes.add(unStagedAbapGitObjectCopy);
						}

					}

				}
				//Remove the group node in unstaged section
				for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
					if (unstagedGroupNode.equals(groupNode)) {
						model.getUnstagedGroupObjects().remove(unstagedGroupNode);
						break;
					}
				}

				groupNodeFound = true;
				break;
			}
		}


		//If the group node does not exists in staged changes
		//Copy this group node to staged changes
		//Remove the group node from unstaged changes

		//If the group node does not exists in staged changes
		if (groupNodeFound == false) {
			//Copy this group node to staged changes
			model.getStagedGroupObjects().add(groupNode);
			//Remove the group node from unstaged changes
			for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
				if (unstagedGroupNode.equals(groupNode)) {
					model.getUnstagedGroupObjects().remove(unstagedGroupNode);
					break;
				}
			}
		}

	}

	protected static void stageObject(TreeViewer unstagedTreeViewer, IAbapGitStagingGrouping model,
			IAbapGitObject abapObject, List<Object> expandedNodes) {

		//1. If object already exists in staged changes
		//a. Move files to staged object
		//b. Remove object from its group node
		//c. If no more objects in group node, remove the group node as well

		//2. If object does not exist

		//a. If group node exists
		//a.i. Move object to this group node
		//a.ii. If no more objects in unstaged group node, remove the unstaged group node as well

		//b. If group node does not exist
		//b.i. Make a copy of unstaged group node
		//b.ii. Remove all abapobjects
		//b.iii. Add object to be staged to group node copy
		//b.iv. If no more objects in unstaged group node, remove the unstaged group node as well

		//check if the staged changes already contains the object
		boolean objectFound = false;
		for (IAbapGitStagingGroupNode groupNode : model.getStagedGroupObjects()) {
			for(IAbapGitObject stagedObject : groupNode.getAbapgitobjects()) {
				if(stagedObject.equals(abapObject)) {
					//staged changes already contains the object
					//add only the files to the staged changes
					stagedObject.getFiles().addAll(abapObject.getFiles());

					//remove object from unstaged changes

					for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
						for(IAbapGitObject unStagedObject : unstagedGroupNode.getAbapgitobjects()) {
							if(unStagedObject.equals(abapObject)) {
								unstagedGroupNode.getAbapgitobjects().remove(unStagedObject);
								break;
							}
						}
						if(unstagedGroupNode.getAbapgitobjects().isEmpty()) {
							//Also remove the group node if no more objects under the group node
							model.getUnstagedGroupObjects().remove(unstagedGroupNode);
							break;
						}
					}

					objectFound = true;
					break;
				}
			}

		}

		if (!objectFound) {
			//Add object to staged changes
			IAbapGitStagingGroupNode groupNodeParent = (IAbapGitStagingGroupNode) abapObject.eContainer();

			boolean groupNodeFound = false;

			for (IAbapGitStagingGroupNode groupNode : model.getStagedGroupObjects()) {
				if (groupNode.equals(groupNodeParent)) {
					//Group Node exists in the staged changes
					//Add object under this group node
					groupNode.getAbapgitobjects().add(abapObject);
					groupNodeFound = true;
					break;
				}
			}

			if (groupNodeFound == false) {
				//Create copy of the groupNode
				IAbapGitStagingGroupNode groupNodeCopy = EcoreUtil.copy(groupNodeParent);

				//Add to expanded nodes
				if (unstagedTreeViewer.getExpandedState(groupNodeParent)) {
					expandedNodes.add(groupNodeCopy);
				}

				groupNodeCopy.getAbapgitobjects().clear();
				//Add Object to this copy
				groupNodeCopy.getAbapgitobjects().add(abapObject);
				//Add this copy to the staged changes
				model.getStagedGroupObjects().add(groupNodeCopy);
			}

			//remove object from unstaged changes
			for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
				for (IAbapGitObject unStagedObject : unstagedGroupNode.getAbapgitobjects()) {
					if (unStagedObject.equals(abapObject)) {
						unstagedGroupNode.getAbapgitobjects().remove(unStagedObject);
						break;
					}
				}
				if (unstagedGroupNode.getAbapgitobjects().isEmpty()) {
					//Also remove the group node if no more objects under the group node
					model.getUnstagedGroupObjects().remove(unstagedGroupNode);
					break;
				}
			}

		}
		movedObjects.add(abapObject);
	}

	protected static void stageFile(TreeViewer unstagedTreeViewer, IAbapGitStagingGrouping model,
			IAbapGitObject abapObject, IAbapGitFile file,
			IAbapGitStagingGroupNode groupNode, List<Object> expandedNodes) {

		//check if the staged changes already contains the object
		boolean objectFound = false;
		for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
			for(IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
				if(stagedObject.equals(abapObject)) {
					//staged changes already contains the object
					//add the file being staged to the staged changes
					stagedObject.getFiles().add(file);
					//remove the file from unstaged changes
					abapObject.getFiles().remove(file);
					//remove the object from unstaged changes if the object contains no files after the operation
					if (abapObject.getFiles().size() == 0) {

						for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
							for(IAbapGitObject unStagedObject : unstagedGroupNode.getAbapgitobjects()) {
								if(unStagedObject.equals(abapObject)) {
									unstagedGroupNode.getAbapgitobjects().remove(unStagedObject);
									break;
								}
							}
							if(unstagedGroupNode.getAbapgitobjects().isEmpty()) {
								//Also remove the group node if no more objects under the group node
								model.getUnstagedGroupObjects().remove(unstagedGroupNode);
								break;
							}
						}
					}
					objectFound = true;
					break;
				}
			}
		}

		if (objectFound == false) {
			boolean groupNodeFound = false;

			//Check if the parent group node of the object exists in the staged section
			//If exists,
			//Copy object
			//Add the file to this object copy
			//Remove file from the abap object
			//Add this copy object to to this group node
			//Remove the object from unstaged section in case no more files
			//If no more objects in the group node, remove the group node as well

			for (IAbapGitStagingGroupNode groupNodeStaged : model.getStagedGroupObjects()) {
				if (groupNode.equals(groupNodeStaged)) {
					//Group Node exists in the staged changes
					//Copy object
					IAbapGitObject abapObjectCopy = EcoreUtil.copy(abapObject);

					if (unstagedTreeViewer.getExpandedState(abapObject)) {
						expandedNodes.add(abapObjectCopy);
					}

					abapObjectCopy.getFiles().clear();
					//Add the file to this object copy
					abapObjectCopy.getFiles().add(file);
					//Remove file from the abap object
					abapObject.getFiles().remove(file);
					//Add object under this group node
					groupNodeStaged.getAbapgitobjects().add(abapObjectCopy);
					//Remove the object from unstaged section in case no more files
					for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
						for (IAbapGitObject unStagedObject : unstagedGroupNode.getAbapgitobjects()) {
							if (unStagedObject.equals(abapObject) && unStagedObject.getFiles().isEmpty()) {
								unstagedGroupNode.getAbapgitobjects().remove(unStagedObject);
								break;
							}
						}
						if (unstagedGroupNode.getAbapgitobjects().isEmpty()) {
							//Also remove the group node if no more objects under the group node
							model.getUnstagedGroupObjects().remove(unstagedGroupNode);
							break;
						}
					}

					groupNodeFound = true;
					break;
				}
			}

			//If parent group node does not exists
			//Copy group node
			//Copy object
			//Add the file to this object copy
			//Remove file from the abap object
			//Add this copy object to to this group node copy
			//Remove the object from unstaged section in case no more files
			//If no more objects in the group node, remove the group node as well
			if (groupNodeFound == false) {
				//Create copy of the groupNode
				IAbapGitStagingGroupNode groupNodeCopy = EcoreUtil.copy(groupNode);
				if (unstagedTreeViewer.getExpandedState(groupNode)) {
					expandedNodes.add(groupNodeCopy);
				}

				groupNodeCopy.getAbapgitobjects().clear();
				//Copy object
				IAbapGitObject abapObjectCopy = EcoreUtil.copy(abapObject);
				if (unstagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObjectCopy);
				}

				abapObjectCopy.getFiles().clear();
				//Add the file to this object copy
				abapObjectCopy.getFiles().add(file);
				//Remove file from the abap object
				abapObject.getFiles().remove(file);
				//Add Object to this copy
				groupNodeCopy.getAbapgitobjects().add(abapObjectCopy);

				//Remove the object from unstaged section in case no more files
				for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
					for (IAbapGitObject unStagedObject : unstagedGroupNode.getAbapgitobjects()) {
						if (unStagedObject.equals(abapObject) && unStagedObject.getFiles().isEmpty()) {
							unstagedGroupNode.getAbapgitobjects().remove(unStagedObject);
							break;
						}
					}
					if (unstagedGroupNode.getAbapgitobjects().isEmpty()) {
						//Also remove the group node if no more objects under the group node
						model.getUnstagedGroupObjects().remove(unstagedGroupNode);
						break;
					}
				}
				//Add this copy to the staged changes
				model.getStagedGroupObjects().add(groupNodeCopy);
			}

		}

	}

	public static void unstageObjects(TreeViewer stagedTreeViewer, IStructuredSelection selection, IAbapGitStagingGrouping model,
			List<Object> expandedNodes) {
		IAbapGitObject abapObject;
		IAbapGitFile file;
		IAbapGitStagingGroupNode groupNode;

		movedObjects.clear();
		for (Object object : selection.toList()) {
			abapObject = null;
			file = null;
			if (object instanceof IAbapGitObject) {
				abapObject = (IAbapGitObject) object;
				if (stagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObject);
				}
				if (abapObject.eContainer() != null) {
					expandedNodes.add(abapObject.eContainer());
				}
				unstageObject(stagedTreeViewer, model, abapObject, expandedNodes);
			} else if (object instanceof IAbapGitFile) {
				abapObject = (IAbapGitObject) ((IAbapGitFile) object).eContainer();
				groupNode = (IAbapGitStagingGroupNode) abapObject.eContainer();
				file = (IAbapGitFile) object;
				if (movedObjects.contains(abapObject)) {
					continue;
				}
				expandedNodes.add(groupNode);
				expandedNodes.add(abapObject);
				unstageFile(stagedTreeViewer, model, abapObject, file, groupNode, expandedNodes);
			} else if (object instanceof IAbapGitStagingGroupNode) {
				groupNode = (IAbapGitStagingGroupNode) object;
				if (stagedTreeViewer.getExpandedState(groupNode)) {
					expandedNodes.add(groupNode);
				}

				for (IAbapGitObject stagedObject : groupNode.getAbapgitobjects()) {
					if (stagedTreeViewer.getExpandedState(stagedObject)) {
						expandedNodes.add(stagedObject);
					}
				}

				unstageGroupNode(stagedTreeViewer, model, groupNode, expandedNodes);

			}

		}
	}

	protected static void unstageGroupNode(TreeViewer stagedTreeViewer, IAbapGitStagingGrouping model, IAbapGitStagingGroupNode groupNode,
			List<Object> expandedNodes) {

		//Check if the group node already exists in the unstaged changes
		//If it exists
		//For all objects under the group node, check if they already exist in the unstaged section
		//If an object exists,
		//a. Copy all files from this object in staged area to object in unstaged area
		//IF an object does not exist
		//a. Copy this object to the group node in unstaged section
		//Remove the group node in staged section
		boolean groupNodeFound = false;

		//Check if the group node already exists in the unstaged changes
		//If it exists
		for (IAbapGitStagingGroupNode groupNodeUnStaged : model.getUnstagedGroupObjects()) {
			if (groupNodeUnStaged.equals(groupNode)) {
				//Group Node exists in the unstaged changes
				//For all objects under the group node, check if they already exist in the unstaged section
				for (IAbapGitObject stagedAbapGitObject : groupNode.getAbapgitobjects()) {
					boolean stagedObjectFound = false;
					for (IAbapGitObject unstagedAbapGitObject : groupNodeUnStaged.getAbapgitobjects()) {
						if (unstagedAbapGitObject.equals(stagedAbapGitObject)) {
							stagedObjectFound = true;
							//If an object exists,
							//a. Copy all files from this object in staged area to object in unstaged area
							unstagedAbapGitObject.getFiles().addAll(stagedAbapGitObject.getFiles());
							break;
						}
					}

					if (stagedObjectFound == false) {
						//IF an object does not exist
						//a. Copy this object to the group node in unstaged section
						IAbapGitObject stagedAbapGitObjectCopy = EcoreUtil.copy(stagedAbapGitObject);
						groupNodeUnStaged.getAbapgitobjects().add(stagedAbapGitObjectCopy);
						if (stagedTreeViewer.getExpandedState(stagedAbapGitObject)) {
							expandedNodes.add(stagedAbapGitObjectCopy);
						}

					}

				}
				//Remove the group node in staged section
				for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
					if (stagedGroupNode.equals(groupNode)) {
						model.getStagedGroupObjects().remove(stagedGroupNode);
						break;
					}
				}

				groupNodeFound = true;
				break;
			}
		}

		//If the group node does not exists in unstaged changes
		//Copy this group node to unstaged changes
		//Remove the group node from staged changes

		//If the group node does not exists in unstaged changes
		if (groupNodeFound == false) {
			//Copy this group node to unstaged changes
			model.getUnstagedGroupObjects().add(groupNode);
			//Remove the group node from staged changes
			for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
				if (stagedGroupNode.equals(groupNode)) {
					model.getStagedGroupObjects().remove(stagedGroupNode);
					break;
				}
			}
		}

	}

	protected static void unstageObject(TreeViewer stagedTreeViewer, IAbapGitStagingGrouping model, IAbapGitObject abapObject,
			List<Object> expandedNodes) {

		//1. If object already exists in unstaged changes
		//a. Move files to unstaged object
		//b. Remove object from its group node
		//c. If no more objects in group node, remove the group node as well

		//2. If object does not exist

		//a. If group node exists
		//a.i. Move object to this group node
		//a.ii. If no more objects in staged group node, remove the staged group node as well

		//b. If group node does not exist
		//b.i. Make a copy of staged group node
		//b.ii. Remove all abapobjects
		//b.iii. Add object to be unstaged to group node copy
		//b.iv. If no more objects in staged group node, remove the staged group node as well

		//check if the unstaged changes already contains the object
		boolean objectFound = false;
		for (IAbapGitStagingGroupNode groupNode : model.getUnstagedGroupObjects()) {
			for (IAbapGitObject unstagedObject : groupNode.getAbapgitobjects()) {
				if (unstagedObject.equals(abapObject)) {
					//unstaged changes already contains the object
					//add only the files to the unstaged changes
					unstagedObject.getFiles().addAll(abapObject.getFiles());

					//remove object from staged changes

					for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
						for (IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
							if (stagedObject.equals(abapObject)) {
								stagedGroupNode.getAbapgitobjects().remove(stagedObject);
								break;
							}
						}
						if (stagedGroupNode.getAbapgitobjects().isEmpty()) {
							//Also remove the group node if no more objects under the group node
							model.getStagedGroupObjects().remove(stagedGroupNode);
							break;
						}
					}

					objectFound = true;
					break;
				}
			}

		}

		if (!objectFound) {
			//Add object to unstaged changes
			IAbapGitStagingGroupNode groupNodeParent = (IAbapGitStagingGroupNode) abapObject.eContainer();

			boolean groupNodeFound = false;

			for (IAbapGitStagingGroupNode groupNode : model.getUnstagedGroupObjects()) {
				if (groupNode.equals(groupNodeParent)) {
					//Group Node exists in the unstaged changes
					//Add object under this group node
					groupNode.getAbapgitobjects().add(abapObject);
					groupNodeFound = true;
					break;
				}
			}

			if (groupNodeFound == false) {
				//Create copy of the groupNode
				IAbapGitStagingGroupNode groupNodeCopy = EcoreUtil.copy(groupNodeParent);

				//Add to expanded nodes
				if (stagedTreeViewer.getExpandedState(groupNodeParent)) {
					expandedNodes.add(groupNodeCopy);
				}

				groupNodeCopy.getAbapgitobjects().clear();
				//Add Object to this copy
				groupNodeCopy.getAbapgitobjects().add(abapObject);
				//Add this copy to the unstaged changes
				model.getUnstagedGroupObjects().add(groupNodeCopy);
			}

			//remove object from staged changes
			for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
				for (IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
					if (stagedObject.equals(abapObject)) {
						stagedGroupNode.getAbapgitobjects().remove(stagedObject);
						break;
					}
				}
				if (stagedGroupNode.getAbapgitobjects().isEmpty()) {
					//Also remove the group node if no more objects under the group node
					model.getStagedGroupObjects().remove(stagedGroupNode);
					break;
				}
			}

		}
		movedObjects.add(abapObject);
	}

	protected static void unstageFile(TreeViewer stagedTreeViewer, IAbapGitStagingGrouping model, IAbapGitObject abapObject,
			IAbapGitFile file, IAbapGitStagingGroupNode groupNode, List<Object> expandedNodes) {

		//check if the unstaged changes already contains the object
		boolean objectFound = false;
		for (IAbapGitStagingGroupNode unstagedGroupNode : model.getUnstagedGroupObjects()) {
			for (IAbapGitObject unstagedObject : unstagedGroupNode.getAbapgitobjects()) {
				if (unstagedObject.equals(abapObject)) {
					//unstaged changes already contains the object
					//add the file being unstaged to the unstaged changes
					unstagedObject.getFiles().add(file);
					//remove the file from staged changes
					abapObject.getFiles().remove(file);
					//remove the object from staged changes if the object contains no files after the operation
					if (abapObject.getFiles().size() == 0) {
						for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
							for (IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
								if (stagedObject.equals(abapObject)) {
									stagedGroupNode.getAbapgitobjects().remove(stagedObject);
									break;
								}
							}
							if (stagedGroupNode.getAbapgitobjects().isEmpty()) {
								//Also remove the group node if no more objects under the group node
								model.getStagedGroupObjects().remove(stagedGroupNode);
								break;
							}
						}
					}
					objectFound = true;
					break;
				}
			}
		}

		if (objectFound == false) {
			boolean groupNodeFound = false;

			//Check if the parent group node of the object exists in the unstaged section
			//If exists,
			//Copy object
			//Add the file to this object copy
			//Remove file from the abap object
			//Add this copy object to to this group node
			//Remove the object from staged section in case no more files
			//If no more objects in the group node, remove the group node as well

			for (IAbapGitStagingGroupNode groupNodeUnStaged : model.getUnstagedGroupObjects()) {
				if (groupNode.equals(groupNodeUnStaged)) {
					//Group Node exists in the unstaged changes
					//Copy object
					IAbapGitObject abapObjectCopy = EcoreUtil.copy(abapObject);

					if (stagedTreeViewer.getExpandedState(abapObject)) {
						expandedNodes.add(abapObjectCopy);
					}

					abapObjectCopy.getFiles().clear();
					//Add the file to this object copy
					abapObjectCopy.getFiles().add(file);
					//Remove file from the abap object
					abapObject.getFiles().remove(file);
					//Add object under this group node
					groupNodeUnStaged.getAbapgitobjects().add(abapObjectCopy);
					//Remove the object from staged section in case no more files
					for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
						for (IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
							if (stagedObject.equals(abapObject) && stagedObject.getFiles().isEmpty()) {
								stagedGroupNode.getAbapgitobjects().remove(stagedObject);
								break;
							}
						}
						if (stagedGroupNode.getAbapgitobjects().isEmpty()) {
							//Also remove the group node if no more objects under the group node
							model.getStagedGroupObjects().remove(stagedGroupNode);
							break;
						}
					}

					groupNodeFound = true;
					break;
				}
			}

			//If parent group node does not exists
			//Copy group node
			//Copy object
			//Add the file to this object copy
			//Remove file from the abap object
			//Add this copy object to to this group node copy
			//Remove the object from staged section in case no more files
			//If no more objects in the group node, remove the group node as well
			if (groupNodeFound == false) {
				//Create copy of the groupNode
				IAbapGitStagingGroupNode groupNodeCopy = EcoreUtil.copy(groupNode);
				if (stagedTreeViewer.getExpandedState(groupNode)) {
					expandedNodes.add(groupNodeCopy);
				}

				groupNodeCopy.getAbapgitobjects().clear();
				//Copy object
				IAbapGitObject abapObjectCopy = EcoreUtil.copy(abapObject);
				if (stagedTreeViewer.getExpandedState(abapObject)) {
					expandedNodes.add(abapObjectCopy);
				}

				abapObjectCopy.getFiles().clear();
				//Add the file to this object copy
				abapObjectCopy.getFiles().add(file);
				//Remove file from the abap object
				abapObject.getFiles().remove(file);
				//Add Object to this copy
				groupNodeCopy.getAbapgitobjects().add(abapObjectCopy);

				//Remove the object from staged section in case no more files
				for (IAbapGitStagingGroupNode stagedGroupNode : model.getStagedGroupObjects()) {
					for (IAbapGitObject stagedObject : stagedGroupNode.getAbapgitobjects()) {
						if (stagedObject.equals(abapObject) && stagedObject.getFiles().isEmpty()) {
							stagedGroupNode.getAbapgitobjects().remove(stagedObject);
							break;
						}
					}
					if (stagedGroupNode.getAbapgitobjects().isEmpty()) {
						//Also remove the group node if no more objects under the group node
						model.getStagedGroupObjects().remove(stagedGroupNode);
						break;
					}
				}
				//Add this copy to the unstaged changes
				model.getUnstagedGroupObjects().add(groupNodeCopy);
			}

		}

	}

}
