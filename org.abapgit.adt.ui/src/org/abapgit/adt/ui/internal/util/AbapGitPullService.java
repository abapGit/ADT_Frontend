package org.abapgit.adt.ui.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;
import org.abapgit.adt.ui.internal.repositories.IRepositoryModifiedObjects;

public class AbapGitPullService implements IAbapGitPullService {

	@Override
	public Map<String, IAbapGitPullModifiedObjects> getSelectedObjectsToPullforRepo(
			Set<IRepositoryModifiedObjects> overWriteObjectsSelectedToPull,
			Set<IRepositoryModifiedObjects> packageWarningObjectsSelectedToPull) {

		Map<String, IAbapGitPullModifiedObjects> repoToSelectedObjects = new HashMap<String, IAbapGitPullModifiedObjects>();

		//Loop over selected overwrite objects for all repositories and insert in repoToSelectedObjectsMap
		for (IRepositoryModifiedObjects obj : overWriteObjectsSelectedToPull) {
			IAbapGitPullModifiedObjects objectsToPull = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitPullModifiedObjects();

			if (!obj.getModifiedObjects().isEmpty()) {
				IOverwriteObjects overwriteObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createOverwriteObjects();
				overwriteObjects.getAbapgitobjects().addAll(obj.getModifiedObjects());
				objectsToPull.setOverwriteObjects(overwriteObjects);
			}

			repoToSelectedObjects.put(obj.getRepositoryURL(), objectsToPull);
		}

		//Loop over selected package warning objects for all repositories and insert in repoToSelectedObjectsMap
		for (IRepositoryModifiedObjects obj : packageWarningObjectsSelectedToPull) {
			IAbapGitPullModifiedObjects objectsToPull = IAgitpullmodifiedobjectsFactory.eINSTANCE.createAbapGitPullModifiedObjects();

			if (!obj.getModifiedObjects().isEmpty()) {
				IPackageWarningObjects packageWarningObjects = IAgitpullmodifiedobjectsFactory.eINSTANCE.createPackageWarningObjects();
				packageWarningObjects.getAbapgitobjects().addAll(obj.getModifiedObjects());
				objectsToPull.setPackageWarningObjects(packageWarningObjects);

				// if the repoToSelectedObjectsMap doesn't already have an entry for the repository from filling in overwrite objects, create an entry in the map
				// else fill in the package warning objects
				if (repoToSelectedObjects.get(obj.getRepositoryURL()) == null) {
					repoToSelectedObjects.put(obj.getRepositoryURL(), objectsToPull);
				} else {
					repoToSelectedObjects.get(obj.getRepositoryURL())
							.setPackageWarningObjects(packageWarningObjects);
				}

			}

		}

		return repoToSelectedObjects;

	}

}
