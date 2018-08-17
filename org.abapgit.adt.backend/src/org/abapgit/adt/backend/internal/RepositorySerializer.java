package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.abapgit.adt.backend.IRepository;

// TODO: serialization and deserialization is still asymmetric (different use cases, use separate interfaces?)
public class RepositorySerializer {

	public IRepository deserializeRepository(XMLStreamReader xmlReader, String contentType) throws XMLStreamException {
		IRepository repository = new Repository();

		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "key":
					repository.setKey(xmlReader.getElementText());
					break;
				case "url":
					repository.setUrl(xmlReader.getElementText());
					break;
				case "branch_name":
					repository.setBranch(xmlReader.getElementText());
					break;
				case "created_by":
					repository.setCreatedBy(xmlReader.getElementText());
					break;
				case "created_at":
					repository.setFirstCommit(xmlReader.getElementText());
					break;
				case "package":
					repository.setPackage(xmlReader.getElementText());
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

		return repository;
	}

	public void serializeRepository(IRepository repository, XMLStreamWriter xmlStreamWriter,
			String supportedContentType) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("repository");
		writeElementIfNonEmpty(xmlStreamWriter, "branch", repository.getBranch());
		writeElementIfNonEmpty(xmlStreamWriter, "created_at", repository.getFirstCommit());
		writeElementIfNonEmpty(xmlStreamWriter, "key", repository.getKey());
		writeElementIfNonEmpty(xmlStreamWriter, "package", repository.getPackage());
		writeElementIfNonEmpty(xmlStreamWriter, "password", repository.getRemotePassword());
		writeElementIfNonEmpty(xmlStreamWriter, "transportRequest", repository.getTransportRequest());
		writeElementIfNonEmpty(xmlStreamWriter, "url", repository.getUrl());
		writeElementIfNonEmpty(xmlStreamWriter, "created_by", repository.getCreatedBy());
		writeElementIfNonEmpty(xmlStreamWriter, "user", repository.getRemoteUser());
		xmlStreamWriter.writeEndElement();
	}

	private void writeElementIfNonEmpty(XMLStreamWriter xmlStreamWriter, String elementName, String content)
			throws XMLStreamException {
		if (content != null && !content.isEmpty()) {
			xmlStreamWriter.writeStartElement(elementName);
			xmlStreamWriter.writeCharacters(content);
			xmlStreamWriter.writeEndElement();
		}
	}

}
