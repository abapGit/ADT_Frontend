/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitstagingXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapgitstagingPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapgitstagingResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapgitstagingResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapgitstagingResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapgitstagingXMLProcessor
