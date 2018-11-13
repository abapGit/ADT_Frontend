package org.abapgit.adt.backend.internal;

import java.net.URI;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.abapgit.adt.backend.IRepository;

// TODO: serialization and deserialization is still asymmetric (different use cases, use separate interfaces?)
public class RepositorySerializer {

	public IRepository deserializeRepository(XMLStreamReader xmlReader, String contentType) throws XMLStreamException {
		Repository repository = new Repository();

		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "key": //$NON-NLS-1$
					repository.setKey(xmlReader.getElementText());
					break;
				case "url": //$NON-NLS-1$
					repository.setUrl(xmlReader.getElementText());
					break;
				case "branch_name": //$NON-NLS-1$
					repository.setBranch(xmlReader.getElementText());
					break;
				case "created_by": //$NON-NLS-1$
					repository.setCreatedBy(xmlReader.getElementText());
					break;
				case "created_at": //$NON-NLS-1$
					repository.setFirstCommit(xmlReader.getElementText());
					break;
				case "package": //$NON-NLS-1$
					repository.setPackage(xmlReader.getElementText());
					break;
				case "link": //$NON-NLS-1$
					repository.addLink(xmlReader.getAttributeValue(null, "rel"), URI.create(xmlReader.getAttributeValue(null, "href")));
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
		xmlStreamWriter.writeStartElement("repository"); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "branch", repository.getBranch()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "created_at", repository.getFirstCommit()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "key", repository.getKey()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "package", repository.getPackage()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "password", repository.getRemotePassword()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "transportRequest", repository.getTransportRequest()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "url", repository.getUrl()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "created_by", repository.getCreatedBy()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "user", repository.getRemoteUser()); //$NON-NLS-1$
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
