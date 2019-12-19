package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IObject;
import org.abapgit.adt.backend.IObjects;

public class AbapObjectsSerializer {
	public IObjects deserializeAbapObjects(XMLStreamReader xmlReader, String contentType) throws XMLStreamException {
		AbapObjects objects = new AbapObjects();
		IObject newObj_type = new AbapObject();
		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "object": //$NON-NLS-1$
					//deserialize abap object
					IObject deserializedObj = new AbapObjectSerializer().deserializeAbapObject(xmlReader, contentType);

					// object type already exists = use existing type & add child
					if (objects.getObjects().stream().anyMatch(r -> r.getObjType().equals(deserializedObj.getObjType()))) {
						IObject existingObj = objects.getObjects().stream().filter(b -> b.getObjType().equals(deserializedObj.getObjType()))
								.findFirst().get();
						existingObj.addChild(deserializedObj);
					}
					// new object type = create new type & add child
					else {
						newObj_type = new AbapObject();
						newObj_type.setObjType(deserializedObj.getObjType());
						newObj_type.setObjName(newObj_type.getObjType());
						newObj_type.addChild(deserializedObj);
						objects.add(newObj_type);
					}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return objects;
	}
}
