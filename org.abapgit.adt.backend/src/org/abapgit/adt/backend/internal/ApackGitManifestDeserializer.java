package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackAuthor;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;

public class ApackGitManifestDeserializer {

	public IApackManifest deserializeApackManifest(XMLStreamReader xmlReader) throws XMLStreamException {
		ApackManifest apackManifest = new ApackManifest();

		while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "descriptor": //$NON-NLS-1$
					apackManifest.setDescriptor(deserializeDescriptor(xmlReader));
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}

		return apackManifest;
	}

	private IApackManifestDescriptor deserializeDescriptor(XMLStreamReader xmlReader) throws XMLStreamException {
		ApackManifestDescriptor manifestDescriptor = new ApackManifestDescriptor();
		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "organization_id": //$NON-NLS-1$
					manifestDescriptor.setOrganizationId(xmlReader.getElementText());
					break;
				case "package_id": //$NON-NLS-1$
					manifestDescriptor.setPackageId(xmlReader.getElementText());
					break;
				case "version": //$NON-NLS-1$
					manifestDescriptor.setVersion(xmlReader.getElementText());
					break;
				case "license": //$NON-NLS-1$
					manifestDescriptor.setLicense(xmlReader.getElementText());
					break;
				case "description": //$NON-NLS-1$
					manifestDescriptor.setDescription(xmlReader.getElementText());
					break;
				case "git_url": //$NON-NLS-1$
					manifestDescriptor.setGitUrl(xmlReader.getElementText());
					break;
				case "author": //$NON-NLS-1$
					IApackAuthor apackAuthor = deserializeApackAuthor(xmlReader);
					if (!apackAuthor.isEmpty()) {
						manifestDescriptor.addAuthor(apackAuthor);
					}
					break;
				case "dependency": //$NON-NLS-1$
					IApackDependency apackDependency = deserializeApackDependency(xmlReader);
					if (!apackDependency.isEmpty()) {
						manifestDescriptor.addDependency(apackDependency);
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
		return manifestDescriptor;
	}

	private IApackDependency deserializeApackDependency(XMLStreamReader xmlReader) throws XMLStreamException {
		ApackDependency apackDependency = new ApackDependency();

		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "organization_id": //$NON-NLS-1$
					apackDependency.setOrganizationId(xmlReader.getElementText());
					break;
				case "package_id": //$NON-NLS-1$
					apackDependency.setPackageId(xmlReader.getElementText());
					break;
				case "git_url": //$NON-NLS-1$
					apackDependency.setGitUrl(xmlReader.getElementText());
					break;
				case "target_package_name": //$NON-NLS-1$
					apackDependency.setTargetPackageName(xmlReader.getElementText());
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

		return apackDependency;
	}

	private IApackAuthor deserializeApackAuthor(XMLStreamReader xmlReader) throws XMLStreamException {
		ApackAuthor apackAuthor = new ApackAuthor();

		int depth = 0;
		loop: while (xmlReader.hasNext()) {
			int next = xmlReader.next();
			switch (next) {
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlReader.getLocalName()) {
				case "name": //$NON-NLS-1$
					apackAuthor.setName(xmlReader.getElementText());
					break;
				case "e_mail": //$NON-NLS-1$
					apackAuthor.setEMail(xmlReader.getElementText());
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

		return apackAuthor;
	}

}
