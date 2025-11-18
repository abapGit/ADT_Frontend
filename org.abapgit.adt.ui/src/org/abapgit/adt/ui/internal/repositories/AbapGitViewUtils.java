package org.abapgit.adt.ui.internal.repositories;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class AbapGitViewUtils {
    private static AbapGitViewUtils instance;

    private AbapGitViewUtils() {}

    public static synchronized AbapGitViewUtils getInstance() {
        if (instance == null) {
            instance = new AbapGitViewUtils();
        }
        return instance;
    }

    public void refreshView() {
		org.eclipse.swt.widgets.Display.getDefault().asyncExec(() -> {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
					? PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					: null;
			if (page != null) {
				IViewPart view = page.findView(AbapGitView.ID);
				if (view instanceof AbapGitView) {
					((AbapGitView) view).refresh();
				}
            }
		});
    }

}