package org.abapgit.adt.ui.internal.staging.util;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

public class StagingDragListener extends DragSourceAdapter {
	private final ISelectionProvider provider;
	private final boolean unstaged;

	public StagingDragListener(ISelectionProvider provider, ArrayContentProvider contentProvider, boolean unstaged) {
		this.provider = provider;
		this.unstaged = unstaged;
	}

	public void dragStart(DragSourceEvent event) {
		event.doit = !this.provider.getSelection().isEmpty();
	}

	public void dragFinished(DragSourceEvent event) {
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.dataType)) {
			LocalSelectionTransfer.getTransfer().setSelection(null);
		}
	}

	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) this.provider.getSelection();
		if (selection.isEmpty()) {
			return;
		}
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.dataType)) {
			LocalSelectionTransfer.getTransfer().setSelection(new StagingDragSelection(selection, this.unstaged));
			return;
		}
	}
}
