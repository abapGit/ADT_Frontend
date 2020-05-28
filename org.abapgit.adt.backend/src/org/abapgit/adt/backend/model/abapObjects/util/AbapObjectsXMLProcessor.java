/**
 */
package org.abapgit.adt.backend.model.abapObjects.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapObjectsXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapObjectsXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapObjectsPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapObjectsResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapObjectsResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapObjectsResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapObjectsXMLProcessor
