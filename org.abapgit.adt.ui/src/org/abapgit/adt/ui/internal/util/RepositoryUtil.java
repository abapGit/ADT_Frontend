package org.abapgit.adt.ui.internal.util;

import java.util.ArrayList;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.ui.internal.repositories.RepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizard.CloneData;
import org.eclipse.core.runtime.NullProgressMonitor;

public class RepositoryUtil {

	private RepositoryUtil() {
		//prevent instantiation
	}

	/**
	 * Get repository name from repository url
	 */
	public static String getRepoNameFromUrl(String repoURL) {
		if (repoURL != null && !repoURL.isEmpty()) {
			String[] tokens = repoURL.split("/"); //$NON-NLS-1$
			String repoName = tokens[tokens.length - 1];
			if (repoName.endsWith(".git")) { //$NON-NLS-1$
				repoName = repoName.replace(".git", ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return repoName;
		}
		return null;
	}

	/**
	 * Get the branch name from the branch reference
	 */
	public static String getBranchNameFromRef(String branchRef) {
		if (branchRef != null && !branchRef.isEmpty()) {
			String[] tokens = branchRef.split("/"); //$NON-NLS-1$
			return tokens[tokens.length - 1];
		}
		return null;
	}

	/**
	 * Fetches modified Objects from back end for the given repository and
	 * extracts packageWarningObjects and OverWriteObjects into cloneData.
	 *
	 * @param repository
	 *            Repository for which modified objects are to be fetched
	 * @param repoService
	 *            Repository Service instance
	 * @param cloneData
	 *            Clone Data in which the packageWarningObjects and
	 *            OverwriteObjects are to be set which will be listed for pull
	 */

	public static void fetchAndExtractModifiedObjectsToPull(IRepository repository, IRepositoryService repoService, CloneData cloneData) {
		IAbapGitPullModifiedObjects abapPullModifiedObjects = repoService.getModifiedObjects(new NullProgressMonitor(), repository,
				cloneData.user, cloneData.pass);

		if (!abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects().isEmpty()) {
			cloneData.repoToModifiedOverwriteObjects.add(new RepositoryModifiedObjects(repository.getUrl(),
					new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getOverwriteObjects().getAbapgitobjects())));
		}

		if (!abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects().isEmpty()) {
			cloneData.repoToModifiedPackageWarningObjects.add(new RepositoryModifiedObjects(repository.getUrl(),
					new ArrayList<IAbapGitObject>(abapPullModifiedObjects.getPackageWarningObjects().getAbapgitobjects())));
		}

	}
}
