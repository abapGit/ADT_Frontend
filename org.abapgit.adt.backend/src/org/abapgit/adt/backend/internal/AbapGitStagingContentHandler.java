package org.abapgit.adt.backend.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.util.AbapgitstagingResourceFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.AbstractMessageBody;
import com.sap.adt.communication.message.IMessageBody;
import com.sap.adt.communication.util.FileUtils;

public class AbapGitStagingContentHandler implements IContentHandler<IAbapGitStaging> {

	private static final String STAGING_CONTENT_TYPE = "application/abapgit.adt.repos.staging.v1+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.agitstage"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IAbapGitStaging deserialize(IMessageBody body, Class<? extends IAbapGitStaging> dataType) {
		try {
			InputStream content = body.getContent();
			Resource resource = new AbapgitstagingResourceFactoryImpl().createResource(this.virtualResourceUri);
			resource.load(content, null);
			return loadEmf(resource);
		} catch (IOException e) {
			//TODO : better exception messages
			throw new ContentHandlerException("Error during deserialization of XML", e); //$NON-NLS-1$
		}
	}

	public IAbapGitStaging loadEmf(Resource resource) {
		//use some virtual resource name
		EObject documentRoot = resource.getContents().get(0);
		if (documentRoot != null) {
			for (EObject element : documentRoot.eContents()) {
				if (element instanceof IAbapGitStaging) {
					return (IAbapGitStaging) element;
				}
			}
		}
		throw new IllegalArgumentException("Invalid XML content - root model entity not found"); //$NON-NLS-1$

	}

	@Override
	public IMessageBody serialize(IAbapGitStaging dataObject, Charset charset) {
		Resource resource = dataObject.eResource();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(FileUtils.DEFAULT_BUF_SIZE);
		try {
			resource.save(outputStream, null);
		} catch (IOException e) {
			//TODO : better exception messages
			throw new ContentHandlerException("Error during serialization of EMF model", e); //$NON-NLS-1$
		}
		return new MessageBody(outputStream);
	}

	private class MessageBody extends AbstractMessageBody {
		ByteArrayInputStream stream = null;

		protected MessageBody(ByteArrayOutputStream outputStream) {
			super(STAGING_CONTENT_TYPE);
			this.stream = new ByteArrayInputStream(outputStream.toByteArray(), 0, outputStream.size());
		}

		@Override
		public InputStream getContent() throws IOException {
			return this.stream;
		}
	}

	@Override
	public String getSupportedContentType() {
		return STAGING_CONTENT_TYPE;
	}

	@Override
	public Class<IAbapGitStaging> getSupportedDataType() {
		return IAbapGitStaging.class;
	}

}
