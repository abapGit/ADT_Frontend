/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitstagingobjectgroupingXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingobjectgroupingXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapgitstagingobjectgroupingPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapgitstagingobjectgroupingResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapgitstagingobjectgroupingResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapgitstagingobjectgroupingResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapgitstagingobjectgroupingXMLProcessor
