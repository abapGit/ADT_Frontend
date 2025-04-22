package org.abapgit.adt.ui.internal.wizards;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;


import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardSwitchBranch;
import org.eclipse.core.resources.IProject;

import org.junit.Before;
import org.junit.Test;


import com.sap.adt.tools.core.project.IAbapProject;

public class TestPdeAbapGitRepositoriesSelectionWizard {

    private AbapGitWizardSwitchBranch wizard;
    private IProject mockProject;
    private IRepository mockRepo;

    @Before
    public void setUp() {
        // Create mocks using EasyMock
        mockProject = createMock(IProject.class);
        mockRepo = createMock(IRepository.class);

        // Mock the getAdapter method for the IProject mock
        IAbapProject mockAbapProject = createMock(IAbapProject.class); // Mock IAbapProject
        expect(mockProject.getAdapter(IAbapProject.class)).andReturn(mockAbapProject); // Mock the return value

        expect(mockRepo.getUrl()).andReturn("https://repo.url");
        expect(mockRepo.getBranchName()).andReturn("main");
        expect(mockRepo.getPackage()).andReturn("$TEMP_PKG");
        replay(mockRepo); 
        
        // Set up the wizard with the mocks
        wizard = new AbapGitWizardSwitchBranch(mockProject, mockRepo, "SYS_00_useren_000");
    }

    @Test
    public void testWizardInitialization() {
        // Check if the wizard initializes correctly
        assertNotNull("Wizard should not be null", wizard);
        assertEquals("Expected URL", "https://repo.url", wizard.cloneData.url);
        assertEquals("Expected branch", "main", wizard.cloneData.branch);
        
    }

}
