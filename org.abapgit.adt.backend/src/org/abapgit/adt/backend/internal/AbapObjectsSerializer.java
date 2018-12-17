package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IObjects;

public class AbapObjectsSerializer {
	public IObjects deserializeAbapObjects(XMLStreamReader xmlReader, String contentType) throws XMLStreamException {
		AbapObjects objects = new AbapObjects();

		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "object": //$NON-NLS-1$
					objects.add(new AbapObjectSerializer().deserializeAbapObject(xmlReader, contentType));
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
