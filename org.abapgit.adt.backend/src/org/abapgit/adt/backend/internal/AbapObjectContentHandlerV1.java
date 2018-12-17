package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IObjects;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class AbapObjectContentHandlerV1 implements IContentHandler<IObjects> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repo.object.v1+xml"; //$NON-NLS-1$

	@Override
	public IObjects deserialize(IMessageBody body, Class<? extends IObjects> dataType) {
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(body.getContent());
			return new AbapObjectsSerializer().deserializeAbapObjects(xmlStreamReader, getSupportedContentType());
		} catch (IOException | XMLStreamException e) {
			throw new ContentHandlerException("Error parsing repositories", e); //$NON-NLS-1$
		}
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IObjects> getSupportedDataType() {
		return IObjects.class;
	}

	@Override
	public IMessageBody serialize(IObjects arg0, Charset arg1) {
		return null;
	}

}
