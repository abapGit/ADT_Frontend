package org.abapgit.adt.backend.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.util.AgitpullmodifiedobjectsResourceFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.sap.adt.communication.content.ContentHandlerException;
import com.sap.adt.communication.content.IContentHandler;
import com.sap.adt.communication.message.IMessageBody;

public class AbapGitPullModifiedObjectsContentHandlerV1 implements IContentHandler<IAbapGitPullModifiedObjects> {

	private static final String CONTENT_TYPE = "application/abapgit.adt.repo.pull.modified.objs.v1+xml"; //$NON-NLS-1$
	private static final String RESOURCE_NAME = "resource.agitpullmodifiedobjects"; //$NON-NLS-1$
	private final org.eclipse.emf.common.util.URI virtualResourceUri = org.eclipse.emf.common.util.URI.createURI(RESOURCE_NAME);

	@Override
	public IAbapGitPullModifiedObjects deserialize(IMessageBody body, Class<? extends IAbapGitPullModifiedObjects> dataType) {
		try {
			InputStream content = body.getContent();
			Resource resource = new AgitpullmodifiedobjectsResourceFactoryImpl().createResource(this.virtualResourceUri);
			resource.load(content, null);
			IAbapGitPullModifiedObjects objs = loadEmf(resource);
			return objs;

		} catch (IOException e) {
			throw new ContentHandlerException("Error parsing abapgit pull modified objects", e); //$NON-NLS-1$
		}
	}

	public IAbapGitPullModifiedObjects loadEmf(Resource resource) {
		//use some virtual resource name
		EObject documentRoot = resource.getContents().get(0);
		if (documentRoot != null) {
			for (EObject element : documentRoot.eContents()) {
				if (element instanceof IAbapGitPullModifiedObjects) {
					return (IAbapGitPullModifiedObjects) element;
				}
			}
		}
		throw new IllegalArgumentException("Invalid XML content - root model entity not found"); //$NON-NLS-1$

	}

	@Override
	public IMessageBody serialize(IAbapGitPullModifiedObjects dataObject, Charset charset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSupportedContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public Class<IAbapGitPullModifiedObjects> getSupportedDataType() {
		return IAbapGitPullModifiedObjects.class;

	}

}
