package org.abapgit.adt.ui.internal.staging.compare;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

import com.sap.adt.tools.core.project.IAbapProject;

public class AbapGitCompareInput extends CompareEditorInput{

	private final AbapGitCompareItem left;
	private final AbapGitCompareItem right;

	public AbapGitCompareInput(AbapGitCompareItem left, AbapGitCompareItem right, String editorTitle, IProject project) {
		super(new CompareConfiguration());

		this.left = left;
		this.right = right;

		IAbapProject abapProject = project.getAdapter(IAbapProject.class);
		this.setTitle("[" + abapProject.getSystemId() + "] " + editorTitle); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		CompareConfiguration configuration = getCompareConfiguration();
		configuration.setLeftLabel(this.left.getName());
		configuration.setRightLabel(this.right.getName());
		return new DiffNode(null, Differencer.CHANGE, null, this.left, this.right);
	}
}
