/**
 */
package org.abapgit.adt.backend.model.abapObjects.impl;

import org.abapgit.adt.backend.model.abapObjects.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapObjectsFactoryImpl extends EFactoryImpl implements IAbapObjectsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAbapObjectsFactory init() {
		try {
			IAbapObjectsFactory theAbapObjectsFactory = (IAbapObjectsFactory)EPackage.Registry.INSTANCE.getEFactory(IAbapObjectsPackage.eNS_URI);
			if (theAbapObjectsFactory != null) {
				return theAbapObjectsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AbapObjectsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapObjectsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IAbapObjectsPackage.ABAP_OBJECT: return createAbapObject();
			case IAbapObjectsPackage.ABAP_OBJECTS: return createAbapObjects();
			case IAbapObjectsPackage.DOCUMENT_ROOT: return createDocumentRoot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapObject createAbapObject() {
		AbapObjectImpl abapObject = new AbapObjectImpl();
		return abapObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapObjects createAbapObjects() {
		AbapObjectsImpl abapObjects = new AbapObjectsImpl();
		return abapObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapObjectsPackage getAbapObjectsPackage() {
		return (IAbapObjectsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAbapObjectsPackage getPackage() {
		return IAbapObjectsPackage.eINSTANCE;
	}

} //AbapObjectsFactoryImpl
