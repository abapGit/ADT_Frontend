package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitexternalrepo.util.AbapgitexternalrepoResourceFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class ExternalRepositoryInfoResponseContentHandlerV1 implements IContentHandler<IExternalRepositoryInfo> {
	private static final String CONTENT_TYPE = "application/abapgit.adt.repo.info.ext.response.v2+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.agitextRepo"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IExternalRepositoryInfo deserialize(IMessageBody body, Class<? extends IExternalRepositoryInfo> dataType) {
		try {
			Resource resource = new AbapgitexternalrepoResourceFactoryImpl().createResource(this.virtualResourceUri);
			resource.load(body.getContent(), null);
			IExternalRepositoryInfo extRepoInfo = loadEmf(resource);
			return extRepoInfo;
		} catch (IOException e) {
			throw new ContentHandlerException("Error while parsing external repository info ", e); //$NON-NLS-1$
		}
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Class<IExternalRepositoryInfo> getSupportedDataType() {
		return IExternalRepositoryInfo.class;
	}

	@Override
	public IMessageBody serialize(IExternalRepositoryInfo externalRepositoryInfoRequest, Charset charset) {
		throw new UnsupportedOperationException();
	}

	public IExternalRepositoryInfo loadEmf(Resource resource) {
		//use some virtual resource name
		EObject documentRoot = resource.getContents().get(0);
		if (documentRoot != null) {
			for (EObject element : documentRoot.eContents()) {
				if (element instanceof IExternalRepositoryInfo) {
					return (IExternalRepositoryInfo) element;
				}
			}
		}
		throw new IllegalArgumentException("Invalid XML content - root model entity not found"); //$NON-NLS-1$

	}
}
