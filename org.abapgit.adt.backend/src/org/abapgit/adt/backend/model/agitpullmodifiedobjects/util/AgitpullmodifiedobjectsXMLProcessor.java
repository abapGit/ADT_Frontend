/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.util;

import java.util.Map;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AgitpullmodifiedobjectsXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgitpullmodifiedobjectsXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		IAgitpullmodifiedobjectsPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the AgitpullmodifiedobjectsResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AgitpullmodifiedobjectsResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AgitpullmodifiedobjectsResourceFactoryImpl());
		}
		return registrations;
	}

} //AgitpullmodifiedobjectsXMLProcessor
