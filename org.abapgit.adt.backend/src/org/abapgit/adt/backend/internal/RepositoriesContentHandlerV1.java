package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IRepositories;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoriesContentHandlerV1 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repos.v1+xml"; //$NON-NLS-1$

	@Override
	public IRepositories deserialize(IMessageBody body, Class<? extends IRepositories> dataType) {
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(body.getContent());
			return new RepositoriesSerializer().deserializeRepositories(xmlStreamReader, getSupportedContentType());
		} catch (IOException | XMLStreamException e) {
			throw new ContentHandlerException("Error parsing repositories", e); //$NON-NLS-1$
		}
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IRepositories> getSupportedDataType() {
		return IRepositories.class;
	}

	@Override
	public IMessageBody serialize(IRepositories arg0, Charset arg1) {
		return null;
	}

}
