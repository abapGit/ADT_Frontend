package org.abapgit.adt.backend.internal;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.abapgit.adt.backend.IRepositories;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.ByteArrayMessageBody;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoryContentHandlerV2 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE_V2 = "application/abapgit.adt.repo.v2+xml"; //$NON-NLS-1$

	@Override
	public IRepositories deserialize(IMessageBody arg0, Class<? extends IRepositories> arg1) {
		return null;
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V2;
	}

	@Override
	public Class<IRepositories> getSupportedDataType() {
		return IRepositories.class;
	}

	@Override
	public IMessageBody serialize(IRepositories repositories, Charset charset) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLStreamWriter xmlStreamWriter = null;
		try {
			String encoding = charset == null ? null : charset.name();
			xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream, encoding);
			xmlStreamWriter.writeStartDocument(null, encoding);
			new RepositoriesSerializer().serializeRepositories(repositories, xmlStreamWriter, getSupportedContentType());
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.flush();
			return new ByteArrayMessageBody(getSupportedContentType(), outputStream.toByteArray());
		} catch (XMLStreamException e) {
			throw new ContentHandlerException("Error serializing repositories", e); //$NON-NLS-1$
		} finally {
			if (xmlStreamWriter != null) {
				try {
					xmlStreamWriter.close();
				} catch (XMLStreamException e) {
					// ignore
				}
			}
		}
	}

}
