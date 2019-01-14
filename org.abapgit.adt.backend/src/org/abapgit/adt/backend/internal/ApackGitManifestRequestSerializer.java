package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ApackGitManifestRequestSerializer {

	public void serializeGitManifestRequest(IApackGitManifestRequest gitManifestRequest,
			XMLStreamWriter xmlStreamWriter, String supportedContentType) throws XMLStreamException {
		xmlStreamWriter.writeStartElement("git_manifest"); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "url", gitManifestRequest.getUrl()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "branch", gitManifestRequest.getBranch()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "user", gitManifestRequest.getUser()); //$NON-NLS-1$
		writeElementIfNonEmpty(xmlStreamWriter, "password", gitManifestRequest.getPassword()); //$NON-NLS-1$
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
