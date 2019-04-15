package org.abapgit.adt.backend.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public class TestsUnitApackDependency {

	@Test
	public void sameDependency() {
		ApackDependency apackDependencyOne = new ApackDependency();
		apackDependencyOne.setGroupId("sap.com");
		apackDependencyOne.setArtifactId("my-fancy-component");
		apackDependencyOne.setGitUrl("https://github.com/SAP/abap-platform-fancy-component");
		apackDependencyOne.setRequiresSynchronization(true);
		IAdtObjectReference targetPackageOne = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
		apackDependencyOne.setTargetPackage(targetPackageOne);
		
		ApackDependency apackDependencyTwo = new ApackDependency();
		apackDependencyTwo.setGroupId("sap.com");
		apackDependencyTwo.setArtifactId("my-fancy-component");
		apackDependencyTwo.setGitUrl("https://github.com/SAP/abap-platform-fancy-component");
		apackDependencyTwo.setRequiresSynchronization(true);
		IAdtObjectReference targetPackageTwo = IAdtCoreFactory.eINSTANCE.createAdtObjectReference();
		apackDependencyTwo.setTargetPackage(targetPackageTwo);
		
		assertEquals(apackDependencyOne, apackDependencyTwo);
		
	}
}
