package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IObject;

public class AbapObjectSerializer {
	public IObject deserializeAbapObject(XMLStreamReader xmlReader, String contentType) throws XMLStreamException {
		IObject object = new AbapObject();
		IObject object_msg = new AbapObject();
		boolean message_exists = false;

		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "obj_type": //$NON-NLS-1$
					object.setObjType(xmlReader.getElementText());
					object_msg.setObjType("Message"); //$NON-NLS-1$
					message_exists = false;
					break;
				case "obj_name": //$NON-NLS-1$
					object.setObjName(xmlReader.getElementText());
					break;
				case "obj_status": //$NON-NLS-1$
					object.setObjStatus(xmlReader.getElementText());
					break;
				case "package": //$NON-NLS-1$
					object.setPackage(xmlReader.getElementText());
					break;
				case "msg_type": //$NON-NLS-1$
//					object.setMsgType(xmlReader.getElementText());
					object_msg.setMsgType(xmlReader.getElementText());
					message_exists = true;
					break;
				case "msg_text": //$NON-NLS-1$
//					object.setMsgText(xmlReader.getElementText());
					object_msg.setMsgText(xmlReader.getElementText());
					if (message_exists) {
						object.addChild(object_msg);
					}
					break;
				default:
					depth++;
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				if (depth == 0) {
					break loop;
				}
				depth--;
				break;
			default:
				break;
			}
		}

		return object;
	}
}