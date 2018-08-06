package org.abapgit.adt.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.IRepositories;
import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoriesContentHandlerV1 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE_V1 = "application/abapgit.adt.repo.v1+xml"; //$NON-NLS-1$

	@Override
	public IRepositories deserialize(IMessageBody body, Class<? extends IRepositories> dataType) {
		Repositories result = new Repositories();

		try {
			InputStream content = body.getContent();
			// parse content via XML parser
			// ...
			
			// result.add(new Repository("PACKAGE", "url"));

			// ...

			return result;
		} catch (IOException e) {
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
