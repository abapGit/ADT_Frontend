/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl;

import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.*;

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
public class AbapgitstagingobjectgroupingFactoryImpl extends EFactoryImpl implements IAbapgitstagingobjectgroupingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAbapgitstagingobjectgroupingFactory init() {
		try {
			IAbapgitstagingobjectgroupingFactory theAbapgitstagingobjectgroupingFactory = (IAbapgitstagingobjectgroupingFactory)EPackage.Registry.INSTANCE.getEFactory(IAbapgitstagingobjectgroupingPackage.eNS_URI);
			if (theAbapgitstagingobjectgroupingFactory != null) {
				return theAbapgitstagingobjectgroupingFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AbapgitstagingobjectgroupingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingobjectgroupingFactoryImpl() {
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
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE: return createAbapGitStagingGroupNode();
			case IAbapgitstagingobjectgroupingPackage.DOCUMENT_ROOT: return createDocumentRoot();
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
	public IAbapGitStagingGroupNode createAbapGitStagingGroupNode() {
		AbapGitStagingGroupNodeImpl abapGitStagingGroupNode = new AbapGitStagingGroupNodeImpl();
		return abapGitStagingGroupNode;
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
	public IAbapgitstagingobjectgroupingPackage getAbapgitstagingobjectgroupingPackage() {
		return (IAbapgitstagingobjectgroupingPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAbapgitstagingobjectgroupingPackage getPackage() {
		return IAbapgitstagingobjectgroupingPackage.eINSTANCE;
	}

} //AbapgitstagingobjectgroupingFactoryImpl
