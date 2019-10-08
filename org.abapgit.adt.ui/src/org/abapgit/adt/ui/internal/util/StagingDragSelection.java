package org.abapgit.adt.ui.internal.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

public class StagingDragSelection implements IStructuredSelection {
	private final IStructuredSelection selection;
	private final boolean fromUnstaged;

	public StagingDragSelection(IStructuredSelection selection, boolean fromUnstaged) {
		this.selection = selection;
		this.fromUnstaged = fromUnstaged;
	}

	public boolean isEmpty() {
		return this.selection.isEmpty();
	}

	public Object getFirstElement() {
		return this.selection.getFirstElement();
	}

	public Iterator<?> iterator() {
		return this.selection.iterator();
	}

	public int size() {
		return this.selection.size();
	}

	public Object[] toArray() {
		return this.selection.toArray();
	}

	public List<?> toList() {
		return this.selection.toList();
	}

	public boolean isFromUnstaged() {
		return this.fromUnstaged;
	}
}
