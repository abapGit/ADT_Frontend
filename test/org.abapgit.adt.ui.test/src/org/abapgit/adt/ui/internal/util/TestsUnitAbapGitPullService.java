package org.abapgit.adt.ui.internal.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.abapgit.adt.backend.IRepositoryService;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;
import org.abapgit.adt.ui.internal.repositories.RepositoryModifiedObjects;
import org.junit.Assert;
import org.junit.Test;

import com.sap.adt.tools.core.model.adtcore.IAdtCoreFactory;
import com.sap.adt.tools.core.model.atom.IAtomFactory;
import com.sap.adt.tools.core.model.atom.IAtomLink;


public class TestsUnitAbapGitPullService {
	
	@Test
	public void getSelectedObjectsToPullforRepo() {

	 //Prepare test data.	
		//repo1 
		IAbapGitObject repo1overwriteObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo1overwriteObject1.setName("repo1OverwriteObject1");
		IAbapGitObject repo1overwriteObject2 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo1overwriteObject2.setName("repo1OverwriteObject2");
		
		IAbapGitObject repo1packageWarningObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo1packageWarningObject1.setName("repo1PackageWarningObject1");

		//repo2 
		IAbapGitObject repo2overwriteObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo2overwriteObject1.setName("repo2OverwriteObject1");

		IAbapGitObject repo2packageWarningObject1 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo2packageWarningObject1.setName("repo2PackageWarningObject1");
		IAbapGitObject repo2packageWarningObject2 = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitObject();
		repo2packageWarningObject2.setName("repo2PackageWarningObject2");

		// parameters for getSelectedObjectsToPullForRepo
		Set<IRepositoryModifiedObjects> overWriteObjectsSelectedToPull = new HashSet<IRepositoryModifiedObjects>();
		Set<IRepositoryModifiedObjects> packageWarningObjectsSelectedToPull = new HashSet<IRepositoryModifiedObjects>();

		overWriteObjectsSelectedToPull.add(new RepositoryModifiedObjects("repo1", Arrays.asList(repo1overwriteObject1)));
		overWriteObjectsSelectedToPull.add(new RepositoryModifiedObjects("repo2", Arrays.asList(repo2overwriteObject1)));

		packageWarningObjectsSelectedToPull.add(new RepositoryModifiedObjects("repo1", Arrays.asList(repo1packageWarningObject1)));
		packageWarningObjectsSelectedToPull.add(new RepositoryModifiedObjects("repo2", Arrays.asList(repo2packageWarningObject1, repo2packageWarningObject2)));

		
	 // Execute code under test.
		 Map<String, IAbapGitPullModifiedObjects> selectedRepoPullModifiedObjects =	AbapGitUIServiceFactory.createAbapGitPullService().getSelectedObjectsToPullforRepo(overWriteObjectsSelectedToPull, packageWarningObjectsSelectedToPull);

	 //Asserts	 
		IAbapGitPullModifiedObjects selectedPullModifiedObjectsForRepo1 =  selectedRepoPullModifiedObjects.get("repo1");
		IAbapGitPullModifiedObjects selectedPullModifiedObjectsForRepo2 =  selectedRepoPullModifiedObjects.get("repo2");

		Assert.assertTrue(selectedPullModifiedObjectsForRepo1.getOverwriteObjects().getAbapgitobjects().contains(repo1overwriteObject1));
		Assert.assertFalse(selectedPullModifiedObjectsForRepo1.getOverwriteObjects().getAbapgitobjects().contains(repo1overwriteObject2));
		Assert.assertTrue(selectedPullModifiedObjectsForRepo1.getPackageWarningObjects().getAbapgitobjects().contains(repo1packageWarningObject1));
		
		Assert.assertTrue(selectedPullModifiedObjectsForRepo2.getOverwriteObjects().getAbapgitobjects().contains(repo2overwriteObject1));
		Assert.assertTrue(selectedPullModifiedObjectsForRepo2.getPackageWarningObjects().getAbapgitobjects().contains(repo2packageWarningObject1));
		Assert.assertTrue(selectedPullModifiedObjectsForRepo2.getPackageWarningObjects().getAbapgitobjects().contains(repo2packageWarningObject2));

	}
	
}
