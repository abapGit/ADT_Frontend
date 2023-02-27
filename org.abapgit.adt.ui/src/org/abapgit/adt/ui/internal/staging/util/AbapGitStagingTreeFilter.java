package org.abapgit.adt.ui.internal.staging.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.eclipse.emf.ecore.EObject;
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
			//If AbapObject name matches pattern
			if (this.pattern.matcher(name).find()) {
				return true;
			}
			
			//If GroupNode name matches the pattern
			if (parentElement instanceof IAbapGitStagingGroupNode
					&& this.pattern.matcher(((IAbapGitStagingGroupNode)parentElement).getValue()).find()) {
				return true;
			}
			
			//If one of the file names matches the pattern
			return hasVisibleChildren((IAbapGitObject) element);
		} else if (element instanceof IAbapGitFile) {
			String name = ((IAbapGitFile) element).getName();
			//If the file name matches the pattern
			if (this.pattern.matcher(name).find()) {
				return true;
			}
			//If the name of abapObject container of file matches the pattern
			if (parentElement instanceof IAbapGitObject && this.pattern.matcher(((IAbapGitObject) parentElement).getName()).find()) {
				return true;
			}
			if (parentElement instanceof IAbapGitObject) {
				EObject parentOfParent = ((IAbapGitObject) parentElement).eContainer();

				//If the name of group node of the abapObject container of file matches the pattern
				if (parentOfParent instanceof IAbapGitStagingGroupNode
						&& this.pattern.matcher(((IAbapGitStagingGroupNode) parentOfParent).getValue()).find()) {
					return true;
				}
			}
			return false;
		} else if (element instanceof IAbapGitStagingGroupNode) {
			String name = ((IAbapGitStagingGroupNode) element).getValue();
			//If GroupNode name matches the pattern
			if (this.pattern.matcher(name).find()) {
				return true;
			}
			
			//If name of any object or file under the group node, matches the pattern
			return hasVisibleChildren((IAbapGitStagingGroupNode) element);
		}
		return false;
	}

	private boolean hasVisibleChildren(IAbapGitStagingGroupNode stagingGroupNode) {
		for (Object object : stagingGroupNode.getAbapgitobjects()) {
			String name = ((IAbapGitObject) object).getName();
			if (this.pattern.matcher(name).find()) {
				return true;
			}
			if (hasVisibleChildren((IAbapGitObject) object)) {
				return true;
			} else {
				continue;
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
