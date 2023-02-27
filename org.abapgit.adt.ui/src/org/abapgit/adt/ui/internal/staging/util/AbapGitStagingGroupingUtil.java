package org.abapgit.adt.ui.internal.staging.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingFactoryImpl;
import org.abapgit.adt.ui.internal.i18n.Messages;
import org.abapgit.adt.ui.internal.staging.AbapGitStagingGrouping;
import org.abapgit.adt.ui.internal.staging.IAbapGitStagingGrouping;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.sap.adt.tools.core.model.atom.IAtomLink;

public class AbapGitStagingGroupingUtil {

	private final static String packageGroupNodeForObjectsNotAssignedToAPackage = "Not assigned to a package"; //$NON-NLS-1$
	private final static String transportGroupNodeForObjectsNotAssignedToATransport = "Not assigned to a transport"; //$NON-NLS-1$

	public static IAbapGitStagingGrouping groupObjects(IAbapGitStaging model, String groupingSelection) {

		if (groupingSelection == Messages.AbapGitStagingView_GroupByPackageAction) {
			return groupObjectsUnderPackage(model);
		}

		if (groupingSelection == Messages.AbapGitStagingView_GroupByTransportAction) {
			return groupObjectsUnderTransports(model);
		}
		return null;

	}

	private static IAbapGitStagingGrouping groupObjectsUnderTransports(IAbapGitStaging model) {
		Map<String, List<IAbapGitObject>> transportToStagedObjects = mapAbapGitObjectsToTransports(
				model.getStagedObjects().getAbapgitobject());
		Map<String, List<IAbapGitObject>> transportToUnstagedObjects = mapAbapGitObjectsToTransports(
				model.getUnstagedObjects().getAbapgitobject());

		IAbapGitStagingGrouping groupingModel = new AbapGitStagingGrouping();
		groupingModel.getStagedGroupObjects().addAll(prepareTransportGroupNodes(transportToStagedObjects));
		groupingModel.getUnstagedGroupObjects().addAll(prepareTransportGroupNodes(transportToUnstagedObjects));

		return groupingModel;
	}

	private static IAbapGitStagingGrouping groupObjectsUnderPackage(IAbapGitStaging model) {

		Map<String, List<IAbapGitObject>> packageToStagedObjects = mapAbapGitObjectsToPackages(
				model.getStagedObjects().getAbapgitobject());
		Map<String, List<IAbapGitObject>> packageToUnstagedObjects = mapAbapGitObjectsToPackages(
				model.getUnstagedObjects().getAbapgitobject());

		IAbapGitStagingGrouping groupingModel = new AbapGitStagingGrouping();
		groupingModel.getStagedGroupObjects().addAll(preparePackageGroupNodes(packageToStagedObjects));
		groupingModel.getUnstagedGroupObjects().addAll(preparePackageGroupNodes(packageToUnstagedObjects));

		return groupingModel;
	}

	private static Map<String, List<IAbapGitObject>> mapAbapGitObjectsToPackages(EList<IAbapGitObject> objects) {

		Map<String, List<IAbapGitObject>> packageToObjects = new TreeMap<>();

		//Loop over all objects and set the map
		for (IAbapGitObject obj : objects) {

			if (obj.getPackageName() == null || obj.getPackageName().isEmpty()) {
				if (!packageToObjects.containsKey(packageGroupNodeForObjectsNotAssignedToAPackage)) {
					packageToObjects.put(packageGroupNodeForObjectsNotAssignedToAPackage, new ArrayList<>());
				}
				packageToObjects.get(packageGroupNodeForObjectsNotAssignedToAPackage).add(obj);

			} else {
				if (!packageToObjects.containsKey(obj.getPackageName())) {
					packageToObjects.put(obj.getPackageName(), new ArrayList<>());
				}
				packageToObjects.get(obj.getPackageName()).add(obj);
			}
		}


		return packageToObjects;

	}

	private static Map<String, List<IAbapGitObject>> mapAbapGitObjectsToTransports(EList<IAbapGitObject> objects) {

		Map<String, List<IAbapGitObject>> transportToObjects = new TreeMap<>();

		//Loop over all objects and set the map
		for (IAbapGitObject obj : objects) {

			if (obj.getTransport() == null || obj.getTransport().getNumber() == null || obj.getTransport().getNumber().isEmpty()) {
				if (!transportToObjects.containsKey(transportGroupNodeForObjectsNotAssignedToATransport)) {
					transportToObjects.put(transportGroupNodeForObjectsNotAssignedToATransport, new ArrayList<>());
				}
				transportToObjects.get(transportGroupNodeForObjectsNotAssignedToATransport).add(obj);

			} else {
				if (!transportToObjects.containsKey(obj.getTransport().getNumber())) {
					transportToObjects.put(obj.getTransport().getNumber(), new ArrayList<>());
				}
				transportToObjects.get(obj.getTransport().getNumber()).add(obj);
			}
		}

		return transportToObjects;

	}

	private static List<IAbapGitStagingGroupNode> prepareTransportGroupNodes(
			Map<String, List<IAbapGitObject>> transportToAbapGitObjectsMap) {
		List<IAbapGitStagingGroupNode> transportGroupNodes = new ArrayList<>();

		for (Entry<String, List<IAbapGitObject>> entry : transportToAbapGitObjectsMap.entrySet()) {
			String transportURIStaged = ""; //$NON-NLS-1$
			for (IAbapGitObject obj : entry.getValue()) {

				if (obj.getTransport() != null) {
					for (IAtomLink link : obj.getTransport().getLinks()) {
						if (link.getRel().equals(Messages.AbapGitStagingView_TransportRel)) {
							transportURIStaged = link.getHref();
							break;
						}
					}

				}

			}
			//Prepare a Group node for transport
			IAbapGitStagingGroupNode groupNode = AbapgitstagingobjectgroupingFactoryImpl.eINSTANCE.createAbapGitStagingGroupNode();
			groupNode.setType(Messages.AbapGitStagingView_TransportGroupNode);
			groupNode.setValue(entry.getKey());
			groupNode.setUri(transportURIStaged);
			groupNode.getAbapgitobjects().addAll(EcoreUtil.copyAll(entry.getValue()));

			transportGroupNodes.add(groupNode);
		}

		return transportGroupNodes;

	}

	private static List<IAbapGitStagingGroupNode> preparePackageGroupNodes(Map<String, List<IAbapGitObject>> packageToAbapGitObjectsMap) {
		List<IAbapGitStagingGroupNode> packageGroupNodes = new ArrayList<>();

		for (Entry<String, List<IAbapGitObject>> entry : packageToAbapGitObjectsMap.entrySet()) {
			String packageURIStaged = ""; //$NON-NLS-1$
			for (IAbapGitObject obj : entry.getValue()) {
				for (IAtomLink link : obj.getLinks()) {
					if (link.getRel().equals(Messages.AbapGitStagingView_PackageRel)) {
						packageURIStaged = link.getHref();
						break;
					}
				}

			}
			//Prepare a Group node for package
			IAbapGitStagingGroupNode groupNode = AbapgitstagingobjectgroupingFactoryImpl.eINSTANCE.createAbapGitStagingGroupNode();
			groupNode.setType(Messages.AbapGitStagingView_PackageGroupNode);
			groupNode.setValue(entry.getKey());
			groupNode.setUri(packageURIStaged);
			groupNode.getAbapgitobjects().addAll(EcoreUtil.copyAll(entry.getValue()));

			packageGroupNodes.add(groupNode);
		}

		return packageGroupNodes;

	}

}
