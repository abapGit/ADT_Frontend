package org.abapgit.adt.backend.internal;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl;
import org.junit.Test;

public class TestsUnitRepositories {
	
	@Test
	public void avoidDuplicateRepositoriesAddSingle() {
		//This test seems to be irrelevant for EMF model as we can't set the repository to contain only
		//unique items in EMF
		
		IRepositories allRepositories = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepositories();
		IRepository repositoryOne = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repositoryOne.setUrl("https://github.com/SAP/abap-platform-jak.git");
		allRepositories.getRepositories().add(repositoryOne);
		IRepository repositoryTwo = AbapgitrepositoriesFactoryImpl.eINSTANCE.createRepository();
		repositoryTwo.setUrl("https://github.com/SAP/abap-platform-jak.git");
		allRepositories.getRepositories().add(repositoryTwo);
		
		List<IRepository> actualRepositories = allRepositories.getRepositories();
		assertEquals(2, actualRepositories.size());
	}

}
