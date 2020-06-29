package org.abapgit.adt.backend.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.abapgit.adt.backend.model.abapgitexternalrepo.util.AbapgitexternalrepoResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.AbstractMessageBody;
import com.sap.adt.communication.message.IMessageBody;
import com.sap.adt.communication.util.FileUtils;

public class ExternalRepositoryInfoRequestContentHandlerV1 implements IContentHandler<IExternalRepositoryInfoRequest> {
	private static final String CONTENT_TYPE = "application/abapgit.adt.repo.info.ext.request.v2+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "agit.extRepoReq"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IExternalRepositoryInfoRequest deserialize(IMessageBody arg0, Class<? extends IExternalRepositoryInfoRequest> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Class<IExternalRepositoryInfoRequest> getSupportedDataType() {
		return IExternalRepositoryInfoRequest.class;
	}

	@Override
	public IMessageBody serialize(IExternalRepositoryInfoRequest externalRepositoryInfoRequest, Charset charset) {

		Resource resource = new AbapgitexternalrepoResourceFactoryImpl().createResource(this.virtualResourceUri);
		resource.getContents().add(externalRepositoryInfoRequest);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(FileUtils.DEFAULT_BUF_SIZE);

		try {
			resource.save(outputStream, null);
		} catch (IOException e) {
			throw new ContentHandlerException("Error while serializing external Repository Request ", e);
		}
		return new MessageBody(outputStream);

	}

	private static class MessageBody extends AbstractMessageBody {
		ByteArrayInputStream stream = null;

		protected MessageBody(ByteArrayOutputStream outputStream) {
			super(CONTENT_TYPE);
			this.stream = new ByteArrayInputStream(outputStream.toByteArray(), 0, outputStream.size());
		}

		@Override
		public InputStream getContent() throws IOException {
			return this.stream;
		}
	}
}
