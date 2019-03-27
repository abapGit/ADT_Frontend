package org.abapgit.adt.backend.internal;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.EApackRepositoryType;
import org.abapgit.adt.backend.IApackManifest;
import org.abapgit.adt.backend.IApackManifest.IApackDependency;
import org.abapgit.adt.backend.IApackManifest.IApackManifestDescriptor;

import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public class ApackGitManifestDeserializer {

	private static final String ADT_CORE_NAMESPACE = "http://www.sap.com/adt/core"; //$NON-NLS-1$

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
				case "group_id": //$NON-NLS-1$
					manifestDescriptor.setGroupId(xmlReader.getElementText());
					break;
				case "artifact_id": //$NON-NLS-1$
					manifestDescriptor.setPackageId(xmlReader.getElementText());
					break;
				case "version": //$NON-NLS-1$
					manifestDescriptor.setVersion(xmlReader.getElementText());
					break;
				case "git_url": //$NON-NLS-1$
					manifestDescriptor.setGitUrl(xmlReader.getElementText());
					break;
				case "repository_type": //$NON-NLS-1$
					manifestDescriptor.setRepositoryType(EApackRepositoryType.fromString(xmlReader.getElementText()));
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
				case "group_id": //$NON-NLS-1$
					apackDependency.setGroupId(xmlReader.getElementText());
					break;
				case "artifact_id": //$NON-NLS-1$
					apackDependency.setArtifactId(xmlReader.getElementText());
					break;
				case "git_url": //$NON-NLS-1$
					apackDependency.setGitUrl(xmlReader.getElementText());
					break;
				case "target_package": //$NON-NLS-1$
					// Manually deserialize until we have found an open ADT Core API
					IAdtObjectReference objectReference = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
					String targetPackageUri = xmlReader.getAttributeValue(ADT_CORE_NAMESPACE, "uri"); //$NON-NLS-1$
					String targetPackageName = xmlReader.getAttributeValue(ADT_CORE_NAMESPACE, "name"); //$NON-NLS-1$
					objectReference.setUri(targetPackageUri);
					objectReference.setName(targetPackageName);
					if (targetPackageName != null && !targetPackageName.isEmpty() || (targetPackageUri != null && !targetPackageUri.isEmpty())) {
						// Synchronization only required if there is no backend package (= no previous sync)
						apackDependency.setRequiresSynchronization(false);
					}
					apackDependency.setTargetPackage(objectReference);
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

}
