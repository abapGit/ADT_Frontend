package org.abapgit.adt.ui.internal.util;

import java.util.Map;
import java.util.Set;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;

/**
 * To handle all utility and helper methods involved in Pull Action in abapGit.
 *
 * @author I517012
 *
 */
public interface IAbapGitPullService {

	/**
	 *
	 * Return a map of repository to the selected objects to be pulled, both
	 * overWrite objects and packageWarning Objects, put together in
	 * IAbapGitPullModifiedObjects.
	 * <p>
	 * This is required to extract the selected overwrite warning objects and
	 * package warning objects into IAbapGitPullModifiedObjects, to be sent to
	 * the back end, in the request to pull.
	 *
	 * @param overWriteObjectsSelectedToPull
	 *            The objects that are selected to be pulled in the page,
	 *            AbapGitWizardPageObjectsSelectionForPull for overWrite
	 *            Objects.
	 * @param packageWarningObjectsSelectedToPull
	 *            The objects that are selected to be pulled in the page,
	 *            AbapGitWizardPageObjectsSelectionForPull for package warning
	 *            Objects.
	 * @return A map of repository to the selected objects to be pulled, both
	 *         overWrite objects and packageWarning Objects, put together in
	 *         IAbapGitPullModifiedObjects
	 *
	 */
	public Map<String, IAbapGitPullModifiedObjects> getSelectedObjectsToPullforRepo(
			Set<IRepositoryModifiedObjects> overWriteObjectsSelectedToPull,
			Set<IRepositoryModifiedObjects> packageWarningObjectsSelectedToPull);

}
