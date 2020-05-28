package org.abapgit.adt.backend.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.util.AbapgitrepositoriesResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.AbstractMessageBody;
import com.sap.adt.communication.message.IMessageBody;
import com.sap.adt.communication.util.FileUtils;

public class RepositoryContentHandlerV2 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE_V4 = "application/abapgit.adt.repo.v4+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "agit.repos"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IRepositories deserialize(IMessageBody arg0, Class<? extends IRepositories> arg1) {
		return null;
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE_V4;
	}

	@Override
	public Class<IRepositories> getSupportedDataType() {
		return IRepositories.class;
	}

	@Override
	public IMessageBody serialize(IRepositories repositories, Charset charset) {

		Resource resource = new AbapgitrepositoriesResourceFactoryImpl().createResource(this.virtualResourceUri);
		resource.getContents().add(repositories);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(FileUtils.DEFAULT_BUF_SIZE);

		try {
			resource.save(outputStream, null);
		} catch (IOException e) {
			throw new ContentHandlerException("Error while serializing repositories ", e); //$NON-NLS-1$
		}
		return new MessageBody(outputStream);
	}

	private static class MessageBody extends AbstractMessageBody {
		ByteArrayInputStream stream = null;

		protected MessageBody(ByteArrayOutputStream outputStream) {
			super(CONTENT_TYPE_V4);
			this.stream = new ByteArrayInputStream(outputStream.toByteArray(), 0, outputStream.size());
		}

		@Override
		public InputStream getContent() throws IOException {
			return this.stream;
		}
	}

}
