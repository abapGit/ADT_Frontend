package org.abapgit.adt.ui.internal.util;

import java.util.List;

import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjectsFactory;

public class AbapGitObjLogService implements IAbapGitObjLogService {
	public int countChildren(IAbapObject node) {
		return node.getAbapLogObjectChildren().size();
	}

	public List<IAbapObject> listChildObjects(IAbapObject node) {
		return node.getAbapLogObjectChildren();
	}

	public IAbapObjects renderObjLogInput(IAbapObjects deserializedAbapObjects) {

		IAbapObjects ObjLogInput = IAbapObjectsFactory.eINSTANCE.createAbapObjects();
		IAbapObject newObjType = IAbapObjectsFactory.eINSTANCE.createAbapObject();

		for (IAbapObject abapObject : deserializedAbapObjects.getAbapObjects()) {
			// object type already exists = use existing type & add child

			if (ObjLogInput.getAbapObjects().stream().anyMatch(r -> r.getType().equals(abapObject.getType()))) {
				IAbapObject existingObj = ObjLogInput.getAbapObjects().stream().filter(b -> b.getType().equals(abapObject.getType()))
						.findFirst().get();
				existingObj.getAbapLogObjectChildren().add(abapObject);
			}

			// new object type = create new type & add child
			else {
				newObjType = IAbapObjectsFactory.eINSTANCE.createAbapObject();
				newObjType.setType(abapObject.getType());
				newObjType.setName(newObjType.getType());
				newObjType.getAbapLogObjectChildren().add(abapObject);
				ObjLogInput.getAbapObjects().add(newObjType);
			}

		}

		return ObjLogInput;

	}

}
