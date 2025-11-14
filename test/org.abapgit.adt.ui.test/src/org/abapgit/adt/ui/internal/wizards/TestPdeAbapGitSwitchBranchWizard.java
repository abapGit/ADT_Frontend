package org.abapgit.adt.ui.internal.wizards;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.junit.Before;
import org.junit.Test;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.ui.packages.IAdtPackageServiceUI;


public class TestPdeAbapGitSwitchBranchWizard {

    // Declare fields for mocks and test data
    private IProject mockProject;
    private String testDestination;
    private final String testRepoPackageName = "TEST_AAR_MOD";
    private final String testRepoUrl = "https://dummy-repo.git";

    // Mocks to be injected
    private IAdtPackageServiceUI mockPackageServiceUI;
    private IExternalRepositoryInfoService mockExternalRepoInfoService;
    private IRepositoryService mockRepoService;
    
    @Before
    public void setUp() throws CoreException {
        // Create project and test data
    	TestPdeAbapGitWizardUtil util = new TestPdeAbapGitWizardUtil();
        mockProject = util.createDummyAbapProject(this.getClass().getName());
        testDestination = AdtProjectServiceFactory.createProjectService().getDestinationId(mockProject);

        // Create fresh mocks for each test
        mockPackageServiceUI = createMock(IAdtPackageServiceUI.class);
        mockExternalRepoInfoService = createMock(IExternalRepositoryInfoService.class);
        mockRepoService = createMock(IRepositoryService.class); // The new mock we need
    }

    @Test
    public void testPublicRepoShouldNavigateToBranchPage() throws Exception {
    	/*
    	 * Test case : When repository is PRIVATE
    	 * flow tested : switch branch in the comboViewer and change package name to check functionality
    	*/
    	IBranch mockMainBranch = createNiceMock(IBranch.class);
    	IBranch mockNewMainBranch = createNiceMock(IBranch.class);
    	expect(mockMainBranch.getName()).andReturn("main").anyTimes();
    	expect(mockMainBranch.getIsHead()).andReturn("X").anyTimes();
    	// for new main branch
    	expect(mockNewMainBranch.getName()).andReturn("newMain").anyTimes();
    	expect(mockNewMainBranch.getIsHead()).andReturn("X").anyTimes();
    	EList<IBranch> branchList = new BasicEList<>();
        branchList.add(mockMainBranch);
        branchList.add(mockNewMainBranch);
        IRepository mockSelRepo = createNiceMock(IRepository.class);
        IExternalRepositoryInfo mockExtRepoInfo = createNiceMock(IExternalRepositoryInfo.class);
        IAdtObjectReference mockAdtObjectRef = createNiceMock(IAdtObjectReference.class);
        IRepositories mockRepositories = createMock(IRepositories.class);
        expect(mockRepoService.getRepositories(anyObject()))
            .andReturn(mockRepositories).anyTimes();
        IRepositories realRepositories = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
        EList<IRepository> emptyRepositoryEList = realRepositories.getRepositories();
        expect(mockRepositories.getRepositories()).andReturn(emptyRepositoryEList).anyTimes();
        expect(mockSelRepo.getPackage()).andReturn(testRepoPackageName).anyTimes();
        expect(mockSelRepo.getUrl()).andReturn(testRepoUrl).anyTimes();
        expect(mockSelRepo.getBranchName()).andReturn("main").anyTimes();

        expect(mockPackageServiceUI.packageExists(anyString(), anyString(), anyObject())).andReturn(true).anyTimes();
        List<IAdtObjectReference> packageRefsList = Collections.singletonList(mockAdtObjectRef);
        expect(mockPackageServiceUI.find(anyString(), anyString(), anyObject())).andReturn(packageRefsList).anyTimes();
        expect(mockAdtObjectRef.getName()).andReturn(testRepoPackageName).anyTimes();

        expect(mockExternalRepoInfoService.getExternalRepositoryInfo(anyString(), anyString(), anyString(), anyObject()))
            .andReturn(mockExtRepoInfo).anyTimes();
        expect(mockExtRepoInfo.getBranches()).andReturn(branchList).anyTimes();
        expect(mockExtRepoInfo.getAccessMode()).andReturn(AccessMode.PUBLIC).anyTimes();
        // ================= REPLAY: Activate all mocks =================
        replay(mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService, mockSelRepo, mockExtRepoInfo, mockAdtObjectRef, mockRepositories, mockMainBranch, mockNewMainBranch);
    	AbapGitWizardSwitchBranch wizard = new AbapGitWizardSwitchBranch(mockProject, mockSelRepo, testDestination,
                mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService); // Pass in the new mock
    	TestWizardDialog dialog = new TestWizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
    	dialog.setBlockOnOpen(false);
    	dialog.open();
    	
    	try {
            assertTrue("Page should be Branch and Package", dialog.getCurrentPage() instanceof AbapGitWizardPageSwitchBranchAndPackage);            
            AbapGitWizardPageSwitchBranchAndPackage page = (AbapGitWizardPageSwitchBranchAndPackage) dialog.getCurrentPage();
            assertEquals("main", page.comboBranches.getCombo().getText());
            // switch branch from dropDown
            assertEquals(page.comboBranches.getCombo().getItemCount(),2);
            page.comboBranches.getCombo().select(1); // select the second branch
            assertEquals(page.comboBranches.getCombo().getText(), "newMain");
            assertEquals(testRepoPackageName, page.txtPackage.getTextWidget().getText());
            // not testing package field as it would be disabled
            
        } finally {
            dialog.close();
            verify(mockRepoService);
        }
    }
    
    @Test
    public void testPrivateRespoShouldNavigateToCredentialPage() {
    	/*
    	 * Test case : When repository is PRIVATE
    	 * flow tested : user enters user name and password and click next
    	 * Expected result : Credential Page should open and on clicking next Switch branch and package page should be open
    	*/
    	IBranch mockMainBranch = createNiceMock(IBranch.class);
    	
    	// for main branch
    	expect(mockMainBranch.getName()).andReturn("main").anyTimes();
    	expect(mockMainBranch.getIsHead()).andReturn("X").anyTimes();
    	
    	EList<IBranch> branchList = new BasicEList<>();
        branchList.add(mockMainBranch); 
        
        IRepository mockSelRepo = createNiceMock(IRepository.class);
        IExternalRepositoryInfo mockExtRepoInfo = createNiceMock(IExternalRepositoryInfo.class);
        IAdtObjectReference mockAdtObjectRef = createNiceMock(IAdtObjectReference.class);
        IRepositories mockRepositories = createMock(IRepositories.class);
        expect(mockRepoService.getRepositories(anyObject()))
            .andReturn(mockRepositories).anyTimes();
        IRepositories realRepositories = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
        EList<IRepository> emptyRepositoryEList = realRepositories.getRepositories();
        expect(mockRepositories.getRepositories()).andReturn(emptyRepositoryEList).anyTimes();
        expect(mockSelRepo.getPackage()).andReturn(testRepoPackageName).anyTimes();
        expect(mockSelRepo.getUrl()).andReturn(testRepoUrl).anyTimes();
        expect(mockSelRepo.getBranchName()).andReturn("main").anyTimes();

        expect(mockPackageServiceUI.packageExists(anyString(), anyString(), anyObject())).andReturn(true).anyTimes();
        List<IAdtObjectReference> packageRefsList = Collections.singletonList(mockAdtObjectRef);
        expect(mockPackageServiceUI.find(anyString(), anyString(), anyObject())).andReturn(packageRefsList).anyTimes();
        expect(mockAdtObjectRef.getName()).andReturn(testRepoPackageName).anyTimes();

        expect(mockExternalRepoInfoService.getExternalRepositoryInfo(anyString(), anyString(), anyString(), anyObject()))
            .andReturn(mockExtRepoInfo).anyTimes();
        expect(mockExtRepoInfo.getBranches()).andReturn(branchList).anyTimes();
        expect(mockExtRepoInfo.getAccessMode()).andReturn(AccessMode.PRIVATE).anyTimes();
        // ================= REPLAY: Activate all mocks =================
        replay(mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService, mockSelRepo, mockExtRepoInfo, mockAdtObjectRef, mockRepositories, mockMainBranch);
    	AbapGitWizardSwitchBranch wizard = new AbapGitWizardSwitchBranch(mockProject, mockSelRepo, testDestination,
                mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService); // Pass in the new mock
    	TestWizardDialog dialog = new TestWizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
    	dialog.setBlockOnOpen(false);
    	dialog.open();
    	
    	try {
            assertTrue("Page should be Credentials page", dialog.getCurrentPage() instanceof AbapGitWizardPageSwitchBranchCredentials);
            AbapGitWizardPageSwitchBranchCredentials page = (AbapGitWizardPageSwitchBranchCredentials) dialog.getCurrentPage();
            page.gitCredentialsService = null; // Set to null to avoid call to open store credentials dialog
            page.txtUser.setText("NewUser");
            page.txtPwd.setText("password");
            dialog.nextPressed();
            assertTrue("Page should be Branch and Package", dialog.getCurrentPage() instanceof AbapGitWizardPageSwitchBranchAndPackage);            
            
        } finally {
            dialog.close();
            verify(mockRepoService);
        }
    }
    
    private static class TestWizardDialog extends WizardDialog{

		public TestWizardDialog(Shell parentShell, IWizard newWizard) {
			super(parentShell, newWizard);
		}
		
		@Override
		public void nextPressed() {
			super.nextPressed();
		}
    	
    }
}