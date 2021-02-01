/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitpullrequestXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitpullrequestXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapgitpullrequestPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapgitpullrequestResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapgitpullrequestResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapgitpullrequestResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapgitpullrequestXMLProcessor
