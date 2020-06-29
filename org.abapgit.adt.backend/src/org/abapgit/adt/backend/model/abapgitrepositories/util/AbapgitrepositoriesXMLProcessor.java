/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories.util;

import java.util.Map;

import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitrepositoriesXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitrepositoriesXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAbapgitrepositoriesPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AbapgitrepositoriesResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AbapgitrepositoriesResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AbapgitrepositoriesResourceFactoryImpl());
		}
		return registrations;
	}

} //AbapgitrepositoriesXMLProcessor
