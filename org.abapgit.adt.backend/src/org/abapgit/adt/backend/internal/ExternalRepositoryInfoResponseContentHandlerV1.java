package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.abapgit.adt.backend.IExternalRepositoryInfo;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class ExternalRepositoryInfoResponseContentHandlerV1 implements IContentHandler<IExternalRepositoryInfo> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repo.info.ext.response.v1+xml"; //$NON-NLS-1$

	@Override
	public IExternalRepositoryInfo deserialize(IMessageBody body, Class<? extends IExternalRepositoryInfo> dataType) {
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(body.getContent());
			return new ExternalRepositoryInfoDeserializer().deserializeExternalRepositoryInfo(xmlStreamReader);
		} catch (IOException | XMLStreamException e) {
			throw new ContentHandlerException("Error parsing external repository info", e); //$NON-NLS-1$
		}
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IExternalRepositoryInfo> getSupportedDataType() {
		return IExternalRepositoryInfo.class;
	}

	@Override
	public IMessageBody serialize(IExternalRepositoryInfo externalRepositoryInfoRequest, Charset charset) {
		throw new UnsupportedOperationException();
	}

}
