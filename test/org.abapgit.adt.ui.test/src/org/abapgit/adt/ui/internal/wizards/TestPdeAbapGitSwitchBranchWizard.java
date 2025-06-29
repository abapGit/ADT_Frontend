package org.abapgit.adt.ui.internal.wizards;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.abapgit.adt.backend.IExternalRepositoryInfoService;
import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.junit.Before;
import org.junit.Test;

import com.sap.adt.tools.core.base.test.services.AdtPdeTestProjectUtil;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;
import com.sap.adt.tools.core.model.adtcore.impl.AdtObjectReferenceImpl;
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
        mockProject = AdtPdeTestProjectUtil.createTestProject(this.getClass().getName());
        testDestination = AdtProjectServiceFactory.createProjectService().getDestinationId(mockProject);

        // Create fresh mocks for each test
        mockPackageServiceUI = createMock(IAdtPackageServiceUI.class);
        mockExternalRepoInfoService = createMock(IExternalRepositoryInfoService.class);
        mockRepoService = createMock(IRepositoryService.class); // The new mock we need
    }

    @Test
    public void testPublicRepoShouldNavigateToBranchPage() throws Exception {
    	IBranch mockHeadBranch = createNiceMock(IBranch.class);
    	expect(mockHeadBranch.getName()).andReturn("HEAD").anyTimes();
    	EList<IBranch> branchList = new BasicEList<>();
        branchList.add(mockHeadBranch);
        IRepository mockSelRepo = createNiceMock(IRepository.class);
        IExternalRepositoryInfo mockExtRepoInfo = createNiceMock(IExternalRepositoryInfo.class);
        IAdtObjectReference mockAdtObjectRef = createNiceMock(AdtObjectReferenceImpl.class);
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
        replay(mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService, mockSelRepo, mockExtRepoInfo, mockAdtObjectRef, mockRepositories, mockHeadBranch);
    	AbapGitWizardSwitchBranch wizard = new AbapGitWizardSwitchBranch(mockProject, mockSelRepo, testDestination,
                mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService); // Pass in the new mock
    	TestWizardDialog dialog = new TestWizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
    	dialog.setBlockOnOpen(false);
    	dialog.open();
    	
    	try {
            assertTrue("Page should be Branch and Package", dialog.getCurrentPage() instanceof AbapGitWizardPageSwitchBranchAndPackage);
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
    	IBranch mockHeadBranch = createNiceMock(IBranch.class);
    	expect(mockHeadBranch.getName()).andReturn("HEAD").anyTimes();
    	EList<IBranch> branchList = new BasicEList<>();
        branchList.add(mockHeadBranch);  	
        IRepository mockSelRepo = createNiceMock(IRepository.class);
        IExternalRepositoryInfo mockExtRepoInfo = createNiceMock(IExternalRepositoryInfo.class);
        IAdtObjectReference mockAdtObjectRef = createNiceMock(AdtObjectReferenceImpl.class);
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
        replay(mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService, mockSelRepo, mockExtRepoInfo, mockAdtObjectRef, mockRepositories, mockHeadBranch);
    	AbapGitWizardSwitchBranch wizard = new AbapGitWizardSwitchBranch(mockProject, mockSelRepo, testDestination,
                mockPackageServiceUI, mockExternalRepoInfoService, mockRepoService); // Pass in the new mock
    	TestWizardDialog dialog = new TestWizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
    	dialog.setBlockOnOpen(false);
    	dialog.open();
    	
    	try {
            assertTrue("Page should be Credentials page", dialog.getCurrentPage() instanceof AbapGitWizardPageSwitchBranchCredentials);
            AbapGitWizardPageSwitchBranchCredentials page = (AbapGitWizardPageSwitchBranchCredentials) dialog.getCurrentPage();
            page.txtUser.setText("NewUser");
            page.txtPwd.setText("password");
            dialog.nextPressed();
            
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