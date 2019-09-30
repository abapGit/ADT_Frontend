package org.abapgit.adt.ui.internal.staging.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class AbapGitStagingTreeFilter extends ViewerFilter {
	private Pattern pattern;

	public void setPattern(String filter) {
		this.pattern = wildcardToRegex(filter);
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.pattern == null) {
			return true;
		}
		if (element instanceof IAbapGitObject) {
			String name = ((IAbapGitObject) element).getName();
			if (this.pattern.matcher(name).find()) {
				return true;
			}
			return hasVisibleChildren((IAbapGitObject) element);
		} else if (element instanceof IAbapGitFile) {
			String name = ((IAbapGitFile) element).getName();
			if (this.pattern.matcher(name).find()) {
				return true;
			}
		}
		return false;
	}

	boolean hasVisibleChildren(IAbapGitObject object) {
		for (Object file : object.getFiles()) {
			String name = ((IAbapGitFile) file).getName();
			if (this.pattern.matcher(name).find()) {
				return true;
			}
		}
		return false;
	}

	private Pattern wildcardToRegex(String filter) {
		String trimmed = filter.trim();
		if (trimmed.isEmpty()) {
			return null;
		}
		String regex = (trimmed.contains("*") ? "^" : "") + "\\Q"//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ trimmed.replaceAll("\\*", //$NON-NLS-1$
						Matcher.quoteReplacement("\\E.*?\\Q")) //$NON-NLS-1$
				+ "\\E";//$NON-NLS-1$
		// remove potentially empty quotes at begin or end
		regex = regex.replaceAll(Pattern.quote("\\Q\\E"), ""); //$NON-NLS-1$ //$NON-NLS-2$
		return Pattern.compile(regex);
	}
}
