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
		IAbapObject newObj_type = IAbapObjectsFactory.eINSTANCE.createAbapObject();

		int m = deserializedAbapObjects.getAbapObjects().size();

		for (int j = 0; j < m; j++) {

			IAbapObject abapobject = deserializedAbapObjects.getAbapObjects().get(j);
			// object type already exists = use existing type & add child

			if (ObjLogInput.getAbapObjects().stream().anyMatch(r -> r.getType().equals(abapobject.getType()))) {
				IAbapObject existingObj = ObjLogInput.getAbapObjects().stream().filter(b -> b.getType().equals(abapobject.getType()))
						.findFirst().get();
				existingObj.getAbapLogObjectChildren().add(abapobject);
			}

			// new object type = create new type & add child
			else {
				newObj_type = IAbapObjectsFactory.eINSTANCE.createAbapObject();
				newObj_type.setType(abapobject.getType());
				newObj_type.setName(newObj_type.getType());
				newObj_type.getAbapLogObjectChildren().add(abapobject);
				ObjLogInput.getAbapObjects().add(newObj_type);
			}

		}

		return ObjLogInput;

	}

}
