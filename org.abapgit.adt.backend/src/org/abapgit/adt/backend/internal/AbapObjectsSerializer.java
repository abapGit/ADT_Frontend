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

					////-> Object Type already exists = use existing type & add child
					if (!objects.getObjects().isEmpty() && newObj_type.getObjType() != null
							&& newObj_type.getObjType().equals(deserializedObj.getObjType())) {


						newObj_type.addChild(deserializedObj);

					}
					////-> New Object Type = create new type & add child
					else {
						newObj_type = new AbapObject();
						newObj_type.setObjType(deserializedObj.getObjType());

						newObj_type.addChild(deserializedObj);
						objects.add(newObj_type);
					}

					//-> combine name / type data
					deserializedObj.setObjType(deserializedObj.getObjName() + " | " + deserializedObj.getObjType() + ""); //$NON-NLS-1$//$NON-NLS-2$

					//-> set object status based on message flag
					if (objStatus != null && (objStatus.equals("W") || objStatus.equals("E") || objStatus.equals("I"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						newObj_type.setObjStatus(objStatus);
					}

					//-> only 1 message is present
					if (deserializedObj.listMessages().size() <= 1) {
						deserializedObj.setMsgType(objStatus);
						deserializedObj.setMsgText(deserializedObj.listMessages().get(0).getMsgText());
						deserializedObj.resetChildren();
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
