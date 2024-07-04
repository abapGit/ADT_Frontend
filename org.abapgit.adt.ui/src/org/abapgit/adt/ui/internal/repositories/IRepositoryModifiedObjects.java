package org.abapgit.adt.ui.internal.repositories;

import java.util.List;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;

/**
 * This interface helps to maintain the modified objects for each repository.
 * This acts as the input type for the TreeViewer for selection of modified
 * objects page<strong>(AbapGitWizardPageObjectsSelectionForPull)</strong>.
 * <p>
 * These modified objects is a list of <strong>IAbapGitObject</strong> which can
 * either be OverwriteObjects or PackageWarningObjects in case of
 * <strong>Selective pull</strong>.
 * <p>
 * Each IRepositoryModifiedObjects object, has the modified objects for a
 * repository and a repository url which maps repository to the modified
 * objects.
 * <p>
 * Valid from 2015 back end version with selective pull feature
 *
 * @author I517012
 *
 */
public interface IRepositoryModifiedObjects {

	/**
	 *
	 * @return URL of the Repository for which modified/selected objects are
	 *         maintained.
	 *
	 */
	String getRepositoryURL();

	/**
	 *
	 * @return Modified Objects .
	 *
	 */
	List<IOverwriteObject> getModifiedObjects();
}
