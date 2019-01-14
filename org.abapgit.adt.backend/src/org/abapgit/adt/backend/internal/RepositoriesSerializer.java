package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.IRepository;

public class RepositoriesSerializer {

	public IRepositories deserializeRepositories(XMLStreamReader xmlReader, String contentType)
			throws XMLStreamException {
		Repositories repositories = new Repositories();

		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "repository": //$NON-NLS-1$
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

	public void serializeRepositories(IRepositories repositories, XMLStreamWriter xmlStreamWriter, String contentType) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("repositories"); //$NON-NLS-1$
		RepositorySerializer repositorySerializer = new RepositorySerializer();
		for (IRepository repository : repositories.getRepositories()) {
			repositorySerializer.serializeRepository(repository, xmlStreamWriter, contentType);
		}
		xmlStreamWriter.writeEndElement();
	}

}
