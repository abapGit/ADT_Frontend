package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IRepositories;

public class RepositoriesSerializer {

	public IRepositories deserializeRepositories(XMLStreamReader xmlReader, String contentType)
			throws XMLStreamException {
		Repositories repositories = new Repositories();

		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "repository":
					repositories.add(new RepositorySerializer().deserializeRepository(xmlReader, contentType));
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return repositories;
	}

}
