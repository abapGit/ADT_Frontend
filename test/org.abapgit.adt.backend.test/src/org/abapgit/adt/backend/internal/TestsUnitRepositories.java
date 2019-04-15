package org.abapgit.adt.backend.internal;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.junit.Test;

public class TestsUnitRepositories {
	
	@Test
	public void avoidDuplicateRepositoriesAddSingle() {
		Repositories allRepositories = new Repositories();
		Repository repositoryOne = new Repository();
		repositoryOne.setUrl("https://github.com/SAP/abap-platform-jak.git");
		allRepositories.add(repositoryOne);
		Repository repositoryTwo = new Repository();
		repositoryTwo.setUrl("https://github.com/SAP/abap-platform-jak.git");
		allRepositories.add(repositoryTwo);
		
		List<IRepository> actualRepositories = allRepositories.getRepositories();
		assertEquals(1, actualRepositories.size());
	}

}
