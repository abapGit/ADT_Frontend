package org.abapgit.adt.backend.internal;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.ByteArrayMessageBody;
import com.sap.adt.communication.message.IMessageBody;

public class ExternalRepositoryInfoRequestContentHandlerV1 implements IContentHandler<IExternalRepositoryInfoRequest> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repo.info.ext.request.v1+xml"; //$NON-NLS-1$

	@Override
	public IExternalRepositoryInfoRequest deserialize(IMessageBody arg0,
			Class<? extends IExternalRepositoryInfoRequest> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IExternalRepositoryInfoRequest> getSupportedDataType() {
		return IExternalRepositoryInfoRequest.class;
	}

	@Override
	public IMessageBody serialize(IExternalRepositoryInfoRequest externalRepositoryInfoRequest, Charset charset) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlStreamWriter;
		try {
			String encoding = charset == null ? null : charset.name();
			xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(outputStream, encoding);
			xmlStreamWriter.writeStartDocument(null, encoding);
			new ExternalRepositoryInfoRequestSerializer().serializeExternalRepositoryInfoRequest(
					externalRepositoryInfoRequest, xmlStreamWriter, getSupportedContentType());
			xmlStreamWriter.writeEndDocument();
			return new ByteArrayMessageBody(getSupportedContentType(), outputStream.toByteArray());
		} catch (XMLStreamException e) {
			throw new ContentHandlerException("Error serializing external repository info request", e);
		}
	}

}
