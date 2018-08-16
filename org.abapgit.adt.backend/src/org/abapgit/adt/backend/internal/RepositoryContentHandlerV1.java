package org.abapgit.adt.backend.internal;

import java.io.StringWriter;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.Repository;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.ByteArrayMessageBody;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoryContentHandlerV1 implements IContentHandler<IRepository> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repo.v1+xml"; //$NON-NLS-1$

	@Override
	public IRepository deserialize(IMessageBody arg0, Class<? extends IRepository> arg1) {
		return null;
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V1;
	}

	@Override
	public Class<IRepository> getSupportedDataType() {
		return IRepository.class;
	}

	@Override
	public IMessageBody serialize(IRepository arg0, Charset arg1) {

		JAXBContext jaxbContext;
		String xmlString = null;
		try {
			jaxbContext = JAXBContext.newInstance(Repository.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(arg0, sw);
			xmlString = sw.toString();
			System.out.println(xmlString);
		} catch (JAXBException e) {
			throw new ContentHandlerException(e.getMessage(), e);
		}
		
		return new ByteArrayMessageBody(CONTENT_TYPE_V1, xmlString.getBytes());
	}

}
