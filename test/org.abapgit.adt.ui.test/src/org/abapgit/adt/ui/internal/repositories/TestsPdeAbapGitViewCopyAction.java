package org.abapgit.adt.ui.internal.repositories;

import static org.junit.Assert.*;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestsPdeAbapGitViewCopyAction {
    private AbapGitView abapGitView;
    private TestsPdeAbapGitRepositoriesUtil util;
    private Display display;

    @Before
    public void setUp() throws Exception {
        util = new TestsPdeAbapGitRepositoriesUtil();
        abapGitView = util.initializeView();
        display = Display.getDefault();
    }

    @After
    public void tearDown() {
        if (abapGitView != null && abapGitView.viewer != null) {
            abapGitView.viewer.getControl().dispose();
        }
    }

    @Test
    public void testCopyActionRowSelection() {
        IRepository repo = util.createDummyRepository();
        abapGitView.viewer.setInput(java.util.Arrays.asList(repo));
        Table table = abapGitView.viewer.getTable();
        table.select(0);
        TableItem item = table.getItem(0);
        item.setData(repo);
        abapGitView.focusedColumnIndex = -1; // Simulate row selection
        abapGitView.copy();
        Clipboard clipboard = new Clipboard(display);
        String result = (String) clipboard.getContents(TextTransfer.getInstance());
        assertTrue(result.contains("$AP_GITHUB"));
        assertTrue(result.contains("https://github.com/dummy_url"));
        assertTrue(result.contains("refs/heads/master"));
        assertTrue(result.contains("dummy_user_one@email.com"));
        assertTrue(result.contains("2020-03-22 18:05:03"));
        assertTrue(result.contains("dummy_status"));
        clipboard.dispose();
    }

    @Test
    public void testCopyActionCellSelection() {
        IRepository repo = util.createDummyRepository();
        abapGitView.viewer.setInput(java.util.Arrays.asList(repo));
        Table table = abapGitView.viewer.getTable();
        table.select(0);
        TableItem item = table.getItem(0);
        item.setData(repo);
        abapGitView.focusedColumnIndex = 0; // Simulate cell selection (Package column)
        abapGitView.copy();
        Clipboard clipboard = new Clipboard(display);
        String result = (String) clipboard.getContents(TextTransfer.getInstance());
        assertEquals("$AP_GITHUB", result);
        clipboard.dispose();
    }
}