package org.abapgit.adt.ui.internal.wizards;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.*;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPageObjectsSelectionForPull;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class TestPdeAbapGitObjectSelectionPage {
    private IRepositoryModifiedObjects mockRepo;
    private List<IOverwriteObject> mockObjects;
    private AbapGitWizardPageObjectsSelectionForPull wizardPage;
    private final String repoUrl = "https://dummy-repo.git";

    @Before
    public void setUp() {
        mockRepo = createMock(IRepositoryModifiedObjects.class);
        mockObjects = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            IOverwriteObject obj = createMock(IOverwriteObject.class);
            expect(obj.getName()).andStubReturn("OBJ" + i);
            expect(obj.getType()).andStubReturn("TYPE");
            expect(obj.getPackageName()).andStubReturn("PKG");
            expect(obj.getActionDescription()).andStubReturn("Modify object locally");
            replay(obj);
            mockObjects.add(obj);
        }
        expect(mockRepo.getRepositoryURL()).andStubReturn(repoUrl);
        expect(mockRepo.getModifiedObjects()).andStubReturn(mockObjects);
        replay(mockRepo);
        Set<IRepositoryModifiedObjects> repoSet = new HashSet<>();
        repoSet.add(mockRepo);
        wizardPage = new AbapGitWizardPageObjectsSelectionForPull(repoSet, "Test", 0);
        Shell shell = new Shell(Display.getDefault());
        wizardPage.createControl(shell);
    }

    @Test
    public void testParentCheckSelectsAllChildren() {
        // Simulate checking the parent
        wizardPage.modifiedObjTreeViewer.setChecked(mockRepo, true);
        // The selection logic is handled by a CheckStateListener, which updates the internal state.
        // Calling setChecked alone does NOT trigger the listener, so we must fire the event manually.
        String repoUrl = mockRepo.getRepositoryURL();
        wizardPage.selectAllObjectsForRepo(mockRepo, repoUrl);
        // getSelectedObjects should now return all children
        Set<IRepositoryModifiedObjects> selected = wizardPage.getSelectedObjects();
        assertEquals(1, selected.size()); 
        IRepositoryModifiedObjects repo = selected.iterator().next();
        assertEquals(repoUrl, repo.getRepositoryURL()); 
        assertEquals(150, repo.getModifiedObjects().size());
    }

    @Test
    public void testLazyLoadingBehaviorProgramatically() {
        // Initially, only the first batch should be loaded
        Object[] children = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
            wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(mockRepo);
        assertEquals(100, children.length);
        // Simulate lazy load trigger
        wizardPage.loadNextBatchOfModifiedObjects(mockRepo);
        children = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
            wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(mockRepo);
        assertEquals(150, children.length);
    }

    @Test
    public void testLazyLoadViaScrollControl() {
        // Expand the parent node so the tree is ready for scrolling
        wizardPage.modifiedObjTreeViewer.expandToLevel(mockRepo, 1);
        // Get the tree and its vertical scrollbar
        org.eclipse.swt.widgets.Tree tree = wizardPage.modifiedObjTreeViewer.getTree();
        org.eclipse.swt.widgets.ScrollBar verticalScrollBar = tree.getVerticalBar();
        // Simulate scrolling near the bottom to trigger lazy loading
        if (verticalScrollBar != null) {
            verticalScrollBar.setSelection((int)(verticalScrollBar.getMaximum() * 0.95));
            // Fire a mouse wheel event
            org.eclipse.swt.widgets.Event event = new org.eclipse.swt.widgets.Event();
            event.type = org.eclipse.swt.SWT.MouseWheel;
            event.widget = tree;
            tree.notifyListeners(org.eclipse.swt.SWT.MouseWheel, event);
        }
        // After lazy load, verify that all objects are loaded
        Object[] children = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
            wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(mockRepo);
        assertEquals(150, children.length);
    }

    @Test
    public void testLazyLoadViaScrollBarSelectionListener() {
        // Expand the parent node so the tree is ready for scrolling
        wizardPage.modifiedObjTreeViewer.expandToLevel(mockRepo, 1);
        // Get the tree and its vertical scrollbar
        org.eclipse.swt.widgets.Tree tree = wizardPage.modifiedObjTreeViewer.getTree();
        org.eclipse.swt.widgets.ScrollBar verticalScrollBar = tree.getVerticalBar();
        // Simulate pulling the scroll bar to the bottom
        if (verticalScrollBar != null) {
            verticalScrollBar.setSelection(verticalScrollBar.getMaximum());
            // Fire a selection event to trigger the selection listener
            org.eclipse.swt.widgets.Event event = new org.eclipse.swt.widgets.Event();
            event.type = org.eclipse.swt.SWT.Selection;
            event.widget = verticalScrollBar;
            verticalScrollBar.notifyListeners(org.eclipse.swt.SWT.Selection, event);
        }
        // After lazy load, verify that all objects are loaded
        Object[] children = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
            wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(mockRepo);
        assertEquals(150, children.length);
    }
}