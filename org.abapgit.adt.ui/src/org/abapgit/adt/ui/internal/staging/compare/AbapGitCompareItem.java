package org.abapgit.adt.ui.internal.staging.compare;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

import com.sap.adt.tools.core.urimapping.AdtUriMappingServiceFactory;
import com.sap.adt.tools.core.urimapping.IAdtUriMappingService;

public class AbapGitCompareItem implements IStreamContentAccessor, ITypedElement {
	private final String contents, name, extension;

	protected IAdtUriMappingService uriMappingService = AdtUriMappingServiceFactory.createUriMappingService();

	public AbapGitCompareItem(String name, String extension, String contents) {
		this.name = name;
		this.extension = extension;
		this.contents = contents;
	}

	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(this.contents.getBytes());
	}

	public Image getImage() {
		return null;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.extension;
	}
}
