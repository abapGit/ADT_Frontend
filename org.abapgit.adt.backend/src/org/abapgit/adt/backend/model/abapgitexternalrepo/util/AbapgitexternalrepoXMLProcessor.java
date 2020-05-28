/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitexternalrepoXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitexternalrepoXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapgitexternalrepoPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapgitexternalrepoResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapgitexternalrepoResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapgitexternalrepoResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapgitexternalrepoXMLProcessor
