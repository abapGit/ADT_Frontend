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

public class ApackGitManifestRequestContentHandlerV1 implements IContentHandler<IApackGitManifestRequest> {

	private static final String CONTENT_TYPE_V1 = "application/apack.adt.gitmanifest.request.v1+xml"; //$NON-NLS-1$

	@Override
	public IApackGitManifestRequest deserialize(IMessageBody body, Class<? extends IApackGitManifestRequest> dataType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IMessageBody serialize(IApackGitManifestRequest gitManifestRequest, Charset charset) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		XMLStreamWriter xmlStreamWriter = null;
		try {
			String encoding = charset == null ? null : charset.name();
			xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream, encoding);
			xmlStreamWriter.writeStartDocument(null, encoding);
			new ApackGitManifestRequestSerializer().serializeGitManifestRequest(gitManifestRequest, xmlStreamWriter,
					getSupportedContentType());
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.flush();
			return new ByteArrayMessageBody(getSupportedContentType(), outputStream.toByteArray());
		} catch (XMLStreamException e) {
			throw new ContentHandlerException("Error serializing Git manifest request", e); //$NON-NLS-1$
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

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IApackGitManifestRequest> getSupportedDataType() {
		return IApackGitManifestRequest.class;
	}

}
