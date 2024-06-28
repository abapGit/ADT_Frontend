package org.abapgit.adt.backend.internal;

import static org.junit.Assert.assertEquals;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.util.AgitpullmodifiedobjectsResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

public class TestsUnitPullModifiedObjectsContentHandler {
	
	@Test
	public void TestLoadEMFFromResource() {
		
		//Prepare Test Data
		Resource resource = new AgitpullmodifiedobjectsResourceFactoryImpl().createResource( org.eclipse.emf.common.util.URI.createURI("resource.agitpullmodifiedobjects"));
		
		IDocumentRoot documentRoot = IAgitpullmodifiedobjectsFactory.eINSTANCE.createDocumentRoot();
		IAbapGitPullModifiedObjects abapGitPullModifiedObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitPullModifiedObjects();
		
		IOverwriteObjects overwriteObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObjects();
		IPackageWarningObjects packageWarningObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createPackageWarningObjects();
		
		IOverwriteObject packageWarningObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObject();
		packageWarningObject1.setName("PackageWarningObject1");

		IOverwriteObject overwriteObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObject();
		overwriteObject1.setName("OverwriteObject1");
		
		overwriteObjects.getAbapgitobjects().add(overwriteObject1);
		packageWarningObjects.getAbapgitobjects().add(packageWarningObject1);
		
		abapGitPullModifiedObjects.setOverwriteObjects(overwriteObjects);
		abapGitPullModifiedObjects.setPackageWarningObjects(packageWarningObjects);
		
		documentRoot.setAbapgitpullmodifiedobjects(abapGitPullModifiedObjects);
		resource.getContents().add(documentRoot);
		
		//Create object of Code Under Test.
		
		AbapGitPullModifiedObjectsContentHandlerV1 abapGitPullModifiedObjectsContentHandlerV1 = 
				new AbapGitPullModifiedObjectsContentHandlerV1();
		
		//Execute code under test
		IAbapGitPullModifiedObjects loadedabapGitPullModifiedObjects = abapGitPullModifiedObjectsContentHandlerV1.loadEmf(resource);
		
		//Asserts
		assertEquals(abapGitPullModifiedObjects, loadedabapGitPullModifiedObjects);
		
		

		
	}

}
