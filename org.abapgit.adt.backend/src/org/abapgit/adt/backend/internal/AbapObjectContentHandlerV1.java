package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapObjects.util.AbapObjectsResourceFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class AbapObjectContentHandlerV1 implements IContentHandler<IAbapObjects> {
	private static final String CONTENT_TYPE = "application/abapgit.adt.repo.object.v2+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.logObject"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IAbapObjects deserialize(IMessageBody body, Class<? extends IAbapObjects> dataType) {
		try {
			InputStream content = body.getContent();
			Resource resource = new AbapObjectsResourceFactoryImpl().createResource(this.virtualResourceUri);
			resource.load(content, null);
			IAbapObjects objs = loadEmf(resource);
			return objs;

		} catch (IOException e) {
			throw new ContentHandlerException("Error parsing abapObject Log", e); //$NON-NLS-1$
		}
	}

	public IAbapObjects loadEmf(Resource resource) {
		//use some virtual resource name
		EObject documentRoot = resource.getContents().get(0);
		if (documentRoot != null) {
			for (EObject element : documentRoot.eContents()) {
				if (element instanceof IAbapObjects) {
					return (IAbapObjects) element;
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
	public Class<IAbapObjects> getSupportedDataType() {
		return IAbapObjects.class;
	}

	@Override
	public IMessageBody serialize(IAbapObjects arg0, Charset arg1) {
		return null;
	}


}
