/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.*;

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
public class AgitpullmodifiedobjectsFactoryImpl extends EFactoryImpl implements IAgitpullmodifiedobjectsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAgitpullmodifiedobjectsFactory init() {
		try {
			IAgitpullmodifiedobjectsFactory theAgitpullmodifiedobjectsFactory = (IAgitpullmodifiedobjectsFactory)EPackage.Registry.INSTANCE.getEFactory(IAgitpullmodifiedobjectsPackage.eNS_URI);
			if (theAgitpullmodifiedobjectsFactory != null) {
				return theAgitpullmodifiedobjectsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AgitpullmodifiedobjectsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgitpullmodifiedobjectsFactoryImpl() {
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
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS: return createAbapGitPullModifiedObjects();
			case IAgitpullmodifiedobjectsPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECTS: return createOverwriteObjects();
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS: return createPackageWarningObjects();
			case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT: return createOverwriteObject();
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
	public IAbapGitPullModifiedObjects createAbapGitPullModifiedObjects() {
		AbapGitPullModifiedObjectsImpl abapGitPullModifiedObjects = new AbapGitPullModifiedObjectsImpl();
		return abapGitPullModifiedObjects;
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
	public IOverwriteObjects createOverwriteObjects() {
		OverwriteObjectsImpl overwriteObjects = new OverwriteObjectsImpl();
		return overwriteObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IPackageWarningObjects createPackageWarningObjects() {
		PackageWarningObjectsImpl packageWarningObjects = new PackageWarningObjectsImpl();
		return packageWarningObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IOverwriteObject createOverwriteObject() {
		OverwriteObjectImpl overwriteObject = new OverwriteObjectImpl();
		return overwriteObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAgitpullmodifiedobjectsPackage getAgitpullmodifiedobjectsPackage() {
		return (IAgitpullmodifiedobjectsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAgitpullmodifiedobjectsPackage getPackage() {
		return IAgitpullmodifiedobjectsPackage.eINSTANCE;
	}

} //AgitpullmodifiedobjectsFactoryImpl
