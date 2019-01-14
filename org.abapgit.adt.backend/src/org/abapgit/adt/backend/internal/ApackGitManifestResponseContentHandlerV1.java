package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IApackManifest;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class ApackGitManifestResponseContentHandlerV1 implements IContentHandler<IApackManifest> {
	private static final String CONTENT_TYPE_V1 = "application/apack.adt.gitmanifest.response.v1+xml"; //$NON-NLS-1$

	@Override
	public IApackManifest deserialize(IMessageBody body, Class<? extends IApackManifest> dataType) {
		XMLStreamReader xmlStreamReader = null;
		try {
			xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(body.getContent());
			return new ApackGitManifestDeserializer().deserializeApackManifest(xmlStreamReader);
		} catch (IOException | XMLStreamException e) {
			throw new ContentHandlerException("Error parsing external repository info", e); //$NON-NLS-1$
		} finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				} catch (XMLStreamException e) {
					// ignore
				}
			}
		}
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IApackManifest> getSupportedDataType() {
		return IApackManifest.class;
	}

	@Override
	public IMessageBody serialize(IApackManifest dataObject, Charset charset) {
		throw new UnsupportedOperationException();
	}


}
