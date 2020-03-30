package org.abapgit.adt.ui.internal.util;

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
}
