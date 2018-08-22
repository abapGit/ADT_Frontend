package org.abapgit.adt.backend.internal;

import java.util.Locale;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IExternalRepositoryInfo;
import org.abapgit.adt.backend.IExternalRepositoryInfo.AccessMode;
import org.abapgit.adt.backend.IExternalRepositoryInfo.IBranch;

import com.sap.adt.communication.content.ContentHandlingException;

public class ExternalRepositoryInfoDeserializer {

	public IExternalRepositoryInfo deserializeExternalRepositoryInfo(XMLStreamReader xmlReader)
			throws XMLStreamException {
		ExternalRepositoryInfo externalRepositoryInfo = new ExternalRepositoryInfo();

		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "branch": //$NON-NLS-1$
					externalRepositoryInfo.addBranch(deserializeBranch(xmlReader));
					break;
				case "access_mode": //$NON-NLS-1$
					externalRepositoryInfo.setAccessMode(getAccessModeFromString(xmlReader.getElementText()));
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return externalRepositoryInfo;
	}

	private IBranch deserializeBranch(XMLStreamReader xmlReader) throws XMLStreamException {
		Branch branch = new Branch();
		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "sha1": //$NON-NLS-1$
					branch.setSha1(xmlReader.getElementText());
					break;
				case "name": //$NON-NLS-1$
					branch.setName(xmlReader.getElementText());
					break;
				case "type": //$NON-NLS-1$
					branch.setType(xmlReader.getElementText());
					break;
				case "display_name": //$NON-NLS-1$
					branch.setDisplayName(xmlReader.getElementText());
					break;
				case "is_head": //$NON-NLS-1$
					branch.setIsHead("X".equals(xmlReader.getElementText())); //$NON-NLS-1$
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return branch;
	}

	private AccessMode getAccessModeFromString(String accessModeString) {
		switch (accessModeString.toLowerCase(Locale.ROOT)) {
		case "private": //$NON-NLS-1$
			return AccessMode.PRIVATE;
		case "public": //$NON-NLS-1$
			return AccessMode.PUBLIC;
		default:
			throw new ContentHandlingException("Unsupported access mode: " + accessModeString);
		}
	}
}
