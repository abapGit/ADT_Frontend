package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ExternalRepositoryInfoRequestSerializer {

	public void serializeExternalRepositoryInfoRequest(IExternalRepositoryInfoRequest externalRepositoryInfoRequest,
			XMLStreamWriter xmlStreamWriter, String supportedContentType) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("repository_ext"); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "url", externalRepositoryInfoRequest.getUrl()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "user", externalRepositoryInfoRequest.getUser()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "password", externalRepositoryInfoRequest.getPassword()); //$NON-NLS-1$
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
