package org.abapgit.adt.ui.internal.staging.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PlatformUI;

public class AbapGitFileEditorInput implements IStorageEditorInput {

	private final IAbapGitFile file;
	private final String fileContents;

	public AbapGitFileEditorInput(IAbapGitFile file, String fileContents) {
		this.file = file;
		this.fileContents = fileContents;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}

	@Override
	public String getName() {
		return this.file.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return new AbapGitFileStorage();
	}

	public IAbapGitFile getEditorInputFile() {
		return this.file;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbapGitFileEditorInput) {
			IAbapGitFile file = ((AbapGitFileEditorInput) obj).getEditorInputFile();
			if ((this.getName().equals(file.getName())) && (this.file.getPath().equals(file.getPath()))) {
				return true;
			}
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.file.getName() == null) ? 0 : this.file.getName().hashCode());
		result = prime * result + ((this.file.getPath() == null) ? 0 : this.file.getPath().hashCode());
		return result;
	}

	class AbapGitFileStorage implements IStorage {
		@Override
		public InputStream getContents() throws CoreException {
			return new ByteArrayInputStream(AbapGitFileEditorInput.this.fileContents.getBytes(StandardCharsets.UTF_8));
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			return null;
		}

		@Override
		public IPath getFullPath() {
			return null;
		}

		@Override
		public String getName() {
			return AbapGitFileEditorInput.this.file.getName();
		}

		@Override
		public boolean isReadOnly() {
			return true;
		}
	}

}
