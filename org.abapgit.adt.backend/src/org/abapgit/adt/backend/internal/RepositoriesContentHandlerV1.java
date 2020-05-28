package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.util.AbapgitrepositoriesResourceFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class RepositoriesContentHandlerV1 implements IContentHandler<IRepositories> {
	private static final String CONTENT_TYPE = "application/abapgit.adt.repos.v2+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.repos"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IRepositories deserialize(IMessageBody body, Class<? extends IRepositories> dataType) {
		try {
			InputStream content = body.getContent();
			Resource resource = new AbapgitrepositoriesResourceFactoryImpl().createResource(this.virtualResourceUri);
			resource.load(content, null);
			IRepositories repos = loadEmf(resource);
			return repos;
		} catch (IOException e) {
			throw new ContentHandlerException("Error parsing repositories", e); //$NON-NLS-1$
		}
	}

	public IRepositories loadEmf(Resource resource) {
		//use some virtual resource name
		EObject documentRoot = resource.getContents().get(0);
		if (documentRoot != null) {
			for (EObject element : documentRoot.eContents()) {
				if (element instanceof IRepositories) {
					return (IRepositories) element;
				}
			}
		}
		throw new IllegalArgumentException("Invalid XML content - root model entity not found"); //$NON-NLS-1$

	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Class<IRepositories> getSupportedDataType() {
		return IRepositories.class;
	}


	@Override
	public IMessageBody serialize(IRepositories dataObject, Charset charset) {
		// TODO Auto-generated method stub
		return null;
	}

}
