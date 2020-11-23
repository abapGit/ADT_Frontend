package org.abapgit.adt.backend.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest;
import org.abapgit.adt.backend.model.abapgitpullrequest.util.AbapgitpullrequestResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.AbstractMessageBody;
import com.sap.adt.communication.message.IMessageBody;
import com.sap.adt.communication.util.FileUtils;

public class AbapGitPullRequestContentHandler implements IContentHandler<IAbapGitPullRequest> {
	private static final String CONTENT_TYPE = "application/abapgit.adt.repo.pull.req.v1+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.abapgitpullreq"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IAbapGitPullRequest deserialize(IMessageBody body, Class<? extends IAbapGitPullRequest> dataType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IMessageBody serialize(IAbapGitPullRequest dataObject, Charset charset) {
		Resource resource = new AbapgitpullrequestResourceFactoryImpl().createResource(this.virtualResourceUri);
		resource.getContents().add(dataObject);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(FileUtils.DEFAULT_BUF_SIZE);

		try {
			resource.save(outputStream, null);
		} catch (IOException e) {
			throw new ContentHandlerException("Error during serialization of EMF model for Pull request during POST call", e); //$NON-NLS-1$
		}
		return new MessageBody(outputStream);
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Class<IAbapGitPullRequest> getSupportedDataType() {
		return IAbapGitPullRequest.class;
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
