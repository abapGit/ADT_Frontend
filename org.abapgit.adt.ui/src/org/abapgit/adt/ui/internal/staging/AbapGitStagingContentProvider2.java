package org.abapgit.adt.ui.internal.staging;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class AbapGitStagingContentProvider2 implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		if (inputElement instanceof List<?>) {
			List<IAbapGitObject> objects = (List<IAbapGitObject>) inputElement;
			return objects.toArray();
		}
		if (inputElement instanceof IAbapGitObject) {
			return getChildren(inputElement);
		} else {
			return ArrayContentProvider.getInstance().getElements(inputElement);

		}
//		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAbapGitObject) {
			return ((IAbapGitObject) parentElement).getFiles().toArray();
		}
		if (parentElement instanceof AbapGitStagingGroupNode) {
			return ((AbapGitStagingGroupNode) parentElement).getAbapGitObjects().toArray();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IAbapGitFile) {
			return ((IAbapGitFile) element).eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof AbapGitStagingGroupNode) {
			return true;
		}
		if (element instanceof IAbapGitObject) {
			return true;
		}
		return false;
	}

}
