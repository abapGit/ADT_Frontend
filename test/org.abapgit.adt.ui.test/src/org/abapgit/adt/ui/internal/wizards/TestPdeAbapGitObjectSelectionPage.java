package org.abapgit.adt.ui.internal.wizards;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.*;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardPageObjectsSelectionForPull;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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

    @Test
    public void testSearchFilterByObjectName() throws InterruptedException {
        // Get the search field using reflection to access the private field
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        assertNotNull("Search field should be created", searchField);
        
        // Type search text to filter objects
        searchField.setText("OBJ1");
        
        // Wait for debounce delay (300ms + buffer)
        Thread.sleep(400);
        
        // Process UI events
        processUIEvents();
        
        // Verify filtered results - should match OBJ1, OBJ10-19, OBJ100-149
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        assertNotNull("Filtered repositories should not be null", filtered);
        assertEquals("Should have 1 repository", 1, filtered.size());
        
        IRepositoryModifiedObjects filteredRepo = filtered.iterator().next();
        List<IOverwriteObject> filteredObjects = filteredRepo.getModifiedObjects();
        
        // OBJ1, OBJ10-19, OBJ100-149 = 1 + 10 + 50 = 61 objects
        assertEquals("Should have 61 filtered objects", 61, filteredObjects.size());
        
        // Verify all filtered objects contain "1"
        for (IOverwriteObject obj : filteredObjects) {
            assertTrue("Object name should contain '1'", obj.getName().toLowerCase().contains("1"));
        }
    }

    @Test
    public void testSearchFilterNoResults() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Search for non-existent object
        searchField.setText("NONEXISTENT");
        
        // Wait for debounce
        Thread.sleep(400);
        processUIEvents();
        
        // Verify no results
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        assertNotNull("Filtered repositories should not be null", filtered);
        assertTrue("Should have no repositories with matching objects", filtered.isEmpty());
    }

    @Test
    public void testSearchFilterClear() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Apply filter
        searchField.setText("OBJ1");
        Thread.sleep(400);
        processUIEvents();
        
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        assertTrue("Should have filtered results", !filtered.isEmpty());
        
        IRepositoryModifiedObjects filteredRepo = filtered.iterator().next();
        int filteredCount = filteredRepo.getModifiedObjects().size();
        assertTrue("Should have fewer objects after filter", filteredCount < 150);
        
        // Clear search
        searchField.setText("");
        Thread.sleep(400);
        processUIEvents();
        
        // Verify all objects are shown again
        Set<IRepositoryModifiedObjects> allRepos = getFilteredRepositories();
        assertEquals("Should have 1 repository", 1, allRepos.size());
        
        IRepositoryModifiedObjects repo = allRepos.iterator().next();
        assertEquals("Should have all 150 objects", 150, repo.getModifiedObjects().size());
    }

    @Test
    public void testSearchDebouncing() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Rapidly type multiple characters (simulating fast typing)
        searchField.setText("O");
        Thread.sleep(50);
        searchField.setText("OB");
        Thread.sleep(50);
        searchField.setText("OBJ");
        Thread.sleep(50);
        searchField.setText("OBJ1");
        
        // Wait for debounce to complete (should only execute once after last keystroke)
        Thread.sleep(400);
        processUIEvents();
        
        // Verify search was executed and results are correct
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        assertNotNull("Filtered repositories should not be null", filtered);
        assertEquals("Should have 1 repository", 1, filtered.size());
        
        IRepositoryModifiedObjects filteredRepo = filtered.iterator().next();
        assertEquals("Should have 61 filtered objects", 61, filteredRepo.getModifiedObjects().size());
    }

    @Test
    public void testSearchFilterCaseInsensitive() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Search with lowercase
        searchField.setText("obj1");
        Thread.sleep(400);
        processUIEvents();
        
        Set<IRepositoryModifiedObjects> filteredLower = getFilteredRepositories();
        int lowerCount = filteredLower.iterator().next().getModifiedObjects().size();
        
        // Search with uppercase
        searchField.setText("OBJ1");
        Thread.sleep(400);
        processUIEvents();
        
        Set<IRepositoryModifiedObjects> filteredUpper = getFilteredRepositories();
        int upperCount = filteredUpper.iterator().next().getModifiedObjects().size();
        
        // Both should return same results
        assertEquals("Case insensitive search should return same results", lowerCount, upperCount);
    }

    @Test
    public void testSearchFilterWithSpaces() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Search with leading/trailing spaces (should be trimmed)
        searchField.setText("  OBJ1  ");
        Thread.sleep(400);
        processUIEvents();
        
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        assertNotNull("Filtered repositories should not be null", filtered);
        assertEquals("Should have 1 repository", 1, filtered.size());
        
        IRepositoryModifiedObjects filteredRepo = filtered.iterator().next();
        assertEquals("Should have 61 filtered objects", 61, filteredRepo.getModifiedObjects().size());
    }

    @Test
    public void testSearchFilterResetsLazyLoadCount() throws InterruptedException {
        org.eclipse.swt.widgets.Text searchField = getSearchField();
        
        // Trigger lazy load to load all objects
        wizardPage.loadNextBatchOfModifiedObjects(mockRepo);
        
        Object[] childrenBefore = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
            wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(mockRepo);
        assertEquals("Should have 150 objects loaded", 150, childrenBefore.length);
        
        // Apply filter
        searchField.setText("OBJ1");
        Thread.sleep(400);
        processUIEvents();
        
        // Verify lazy load count was reset - only first batch should be loaded for filtered results
        Set<IRepositoryModifiedObjects> filtered = getFilteredRepositories();
        if (!filtered.isEmpty()) {
            IRepositoryModifiedObjects filteredRepo = filtered.iterator().next();
            Object[] childrenAfter = ((AbapGitWizardPageObjectsSelectionForPull.ModifiedObjectTreeContentProvider)
                wizardPage.modifiedObjTreeViewer.getContentProvider()).getChildren(filteredRepo);
            
            // Should load first batch (up to 100) of filtered results
            assertTrue("Should have loaded first batch of filtered objects", childrenAfter.length > 0);
            assertTrue("Should not exceed batch size initially", childrenAfter.length <= 100);
        }
    }

    // Helper methods

    private Text getSearchField() {
        try {
            java.lang.reflect.Field field = AbapGitWizardPageObjectsSelectionForPull.class.getDeclaredField("searchField");
            field.setAccessible(true);
            return (org.eclipse.swt.widgets.Text) field.get(wizardPage);
        } catch (Exception e) {
            fail("Failed to access search field: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Set<IRepositoryModifiedObjects> getFilteredRepositories() {
        try {
            java.lang.reflect.Field field = AbapGitWizardPageObjectsSelectionForPull.class.getDeclaredField("filteredRepositories");
            field.setAccessible(true);
            return (Set<IRepositoryModifiedObjects>) field.get(wizardPage);
        } catch (Exception e) {
            fail("Failed to access filtered repositories: " + e.getMessage());
            return null;
        }
    }

    private void processUIEvents() {
        Display display = Display.getDefault();
        while (display.readAndDispatch()) {
            // Process all pending UI events
        }
    }
}