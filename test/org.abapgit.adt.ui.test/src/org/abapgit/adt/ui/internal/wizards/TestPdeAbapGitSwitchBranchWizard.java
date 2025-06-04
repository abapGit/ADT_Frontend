// TestPdeAbapGitSwitchAction.java
package org.abapgit.adt.ui.internal.wizards;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService; // << IMPORT
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo; // << IMPORT
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.repositories.exceptions.PackageRefNotFoundException;
import org.abapgit.adt.ui.internal.wizards.AbapGitWizardSwitchBranch;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.junit.Before;
import org.junit.Test;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;

public class TestPdeAbapGitSwitchBranchWizard { // Consider renaming to TestAbapGitWizardSwitchBranchLogic

    private AbapGitWizardSwitchBranch wizard;
    private IAdtPackageServiceUI mockPackageServiceUI;
    private IExternalRepositoryInfoService mockExternalRepoInfoService; // << ADD MOCK FIELD
    private IProject mockProject;
    private IRepository mockSelRepo;
    private String testDestination = "TEST_DEST";
    private String testPackageNameArg = "Z_TARGET_PACKAGE";
    private String testRepoPackageName = "Z_REPO_PACKAGE";
    private String testRepoUrl = "http://dummyurl.com";
    private String testBranch = "main-setup";
    private String testRepoKey = "REPO_KEY_CONSOLIDATED";
    
    private IWizardContainer mockWizardContainer;
    private WizardDialog mockWizardDialog;
    private IExternalRepositoryInfo mockExtRepoInfoFromSetup;
    private IAdtObjectReference mockAdtObjectRefFromSetup;

    @Before
    public void setUp() {
        mockProject = createNiceMock(IProject.class);
        mockSelRepo = createNiceMock(IRepository.class);
        mockPackageServiceUI = createMock(IAdtPackageServiceUI.class);
        mockExternalRepoInfoService = createMock(IExternalRepositoryInfoService.class);

        mockWizardContainer = createNiceMock(IWizardContainer.class); // Mock the interface
        // DO NOT mock WizardDialog here if it's causing the error:
        // mockWizardDialog = createNiceMock(WizardDialog.class);

        mockExtRepoInfoFromSetup = createNiceMock(IExternalRepositoryInfo.class);
        mockAdtObjectRefFromSetup = createNiceMock(IAdtObjectReference.class);

        // ... (rest of your expectations for selRepo, packageService, externalRepoInfoService) ...
        expect(mockSelRepo.getPackage()).andReturn(testRepoPackageName).anyTimes();
        expect(mockSelRepo.getUrl()).andReturn(testRepoUrl).anyTimes();
        expect(mockSelRepo.getBranchName()).andReturn("main").anyTimes(); // Corrected from "main-setup" if "main" is intended
        expect(mockSelRepo.getKey()).andReturn(testRepoKey).anyTimes();
        expect(mockSelRepo.getFolderLogic()).andReturn("FULL").anyTimes();
        expect(mockSelRepo.getTransportRequest()).andReturn("DK9SETUP").anyTimes();

        expect(mockPackageServiceUI.packageExists(eq(testDestination), eq(testRepoPackageName), anyObject(IProgressMonitor.class)))
            .andReturn(true).times(1);
        List<IAdtObjectReference> initialPackageRefsList = Collections.singletonList(mockAdtObjectRefFromSetup); // Use the field
        expect(mockPackageServiceUI.find(eq(testDestination), eq(testRepoPackageName), anyObject(IProgressMonitor.class)))
            .andReturn(initialPackageRefsList).times(1);

        expect(mockExternalRepoInfoService.getExternalRepositoryInfo(eq(testRepoUrl), eq(""), eq(""), anyObject()))
            .andReturn(mockExtRepoInfoFromSetup).times(1); // Use the field

        // Replay mocks used by the constructor
        replay(mockSelRepo, mockPackageServiceUI, mockExternalRepoInfoService, mockProject,
               mockExtRepoInfoFromSetup, mockAdtObjectRefFromSetup, mockWizardContainer);

        wizard = new AbapGitWizardSwitchBranch(mockProject, mockSelRepo, testDestination,
                                              mockPackageServiceUI, mockExternalRepoInfoService);

        reset(mockPackageServiceUI, mockExternalRepoInfoService, mockWizardContainer);
    }

    @Test
    public void getPackageRef_whenPackageDoesNotExist_throwsPackageRefNotFoundException() {
        IProgressMonitor monitor = new NullProgressMonitor();

        expect(mockPackageServiceUI.packageExists(
                eq(testDestination),
                eq(testPackageNameArg),
                same(monitor)))
            .andReturn(false);

        replay(mockPackageServiceUI);

        try {
            wizard.getPackageRef(testPackageNameArg, monitor);
            fail("PackageRefNotFoundException was expected");
        } catch (PackageRefNotFoundException e) {
            String expectedErrorMessage = NLS.bind(Messages.AbapGitWizardSwitch_branch_package_ref_not_found_error, testRepoPackageName);
            assertEquals(expectedErrorMessage, e.getMessage());
        }

        verify(mockPackageServiceUI);
    }

    @Test
    public void getPackageRef_whenPackageExists_setsPackageRef() throws PackageRefNotFoundException {
        IProgressMonitor monitor = new NullProgressMonitor();
        IAdtObjectReference mockAdtObjectRef = createNiceMock(IAdtObjectReference.class);
        List<IAdtObjectReference> mockPackageRefsList = Collections.singletonList(mockAdtObjectRef);

        expect(mockPackageServiceUI.packageExists(
                eq(testDestination),
                eq(testPackageNameArg),
                same(monitor)))
            .andReturn(true);
        expect(mockPackageServiceUI.find(
                eq(testDestination),
                eq(testPackageNameArg),
                same(monitor)))
            .andReturn(mockPackageRefsList);

        replay(mockPackageServiceUI, mockAdtObjectRef);

        wizard.getPackageRef(testPackageNameArg, monitor);

        assertNotNull(wizard.getCloneData().packageRef);
        assertEquals(mockPackageRefsList.get(0), wizard.getCloneData().packageRef);

        verify(mockPackageServiceUI);
    }

    @Test
    public void wizardInitialization_setsWindowTitleAndProgressMonitor() {
        assertEquals(Messages.AbapGitWizardSwitch_branch_wizard_title, wizard.getWindowTitle());
        assertTrue(wizard.needsProgressMonitor());
        assertNotNull(wizard.getDefaultPageImage());
    }
    
    @Test
    public void addPages_createsAndAddsWizardPages() {
        wizard.addPages(); // This creates the actual pages

        assertNotNull("Credentials page should be created", wizard.pageCredentials);
        assertNotNull("Branch and Package page should be created", wizard.pageBranchAndPackage);
        assertEquals("Should have 2 pages", 2, wizard.getPageCount());
        // Assuming page order and accessibility for this check
        if (wizard.getPages().length == 2) {
            assertSame("First page should be credentials page", wizard.pageCredentials, wizard.getPages()[0]);
            assertSame("Second page should be branch/package page", wizard.pageBranchAndPackage, wizard.getPages()[1]);
        }
    }
    
    @Test
    public void getCloneData_returnsInternalCloneData() {
        assertNotNull("CloneData should not be null", wizard.getCloneData());

    }
}