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
					IObject deserializedObj = new AbapObjectSerializer().deserializeAbapObject(xmlReader, contentType);
					String objStatus = deserializedObj.getObjStatus();

					//-> Object Type already exists = use existing type & add child
					if (!objects.getObjects().isEmpty() && newObj_type.getObjType() != null
							&& newObj_type.getObjType().equals(deserializedObj.getObjType())) {


						if (objStatus != null && (objStatus.equals("W") || objStatus.equals("E") || objStatus.equals("I"))) {
							newObj_type.setObjStatus(objStatus);
						}

						newObj_type.addChild(deserializedObj);

					}
					//-> New Object Type = create new type & add child
					else {
						newObj_type = new AbapObject();
						newObj_type.setObjType(deserializedObj.getObjType());

						if (objStatus != null && (objStatus.equals("W") || objStatus.equals("E") || objStatus.equals("I"))) {
							newObj_type.setObjStatus(objStatus);
						}

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
