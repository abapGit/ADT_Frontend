package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.abapgit.adt.backend.IRepositories;
import org.abapgit.adt.backend.parser.RepositoriesHandler;
import org.xml.sax.SAXException;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoriesContentHandlerV1 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repos.v1+xml"; //$NON-NLS-1$

	@Override
	public IRepositories deserialize(IMessageBody body, Class<? extends IRepositories> dataType) {
		Repositories result = new Repositories();

		try {
			InputStream content = body.getContent();
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			RepositoriesHandler handler = new RepositoriesHandler();
			saxParser.parse(content, handler);

			result.addList(handler.getRepositories());
			return result;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new ContentHandlerException("Error parsing repositories", e);
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
