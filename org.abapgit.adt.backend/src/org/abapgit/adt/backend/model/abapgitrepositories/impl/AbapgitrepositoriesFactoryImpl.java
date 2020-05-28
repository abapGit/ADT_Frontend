/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories.impl;

import org.abapgit.adt.backend.model.abapgitrepositories.*;

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
public class AbapgitrepositoriesFactoryImpl extends EFactoryImpl implements IAbapgitrepositoriesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAbapgitrepositoriesFactory init() {
		try {
			IAbapgitrepositoriesFactory theAbapgitrepositoriesFactory = (IAbapgitrepositoriesFactory)EPackage.Registry.INSTANCE.getEFactory(IAbapgitrepositoriesPackage.eNS_URI);
			if (theAbapgitrepositoriesFactory != null) {
				return theAbapgitrepositoriesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AbapgitrepositoriesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitrepositoriesFactoryImpl() {
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
			case IAbapgitrepositoriesPackage.REPOSITORY: return createRepository();
			case IAbapgitrepositoriesPackage.REPOSITORIES: return createRepositories();
			case IAbapgitrepositoriesPackage.DOCUMENT_ROOT: return createDocumentRoot();
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
	public IRepository createRepository() {
		RepositoryImpl repository = new RepositoryImpl();
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IRepositories createRepositories() {
		RepositoriesImpl repositories = new RepositoriesImpl();
		return repositories;
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
	public IAbapgitrepositoriesPackage getAbapgitrepositoriesPackage() {
		return (IAbapgitrepositoriesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAbapgitrepositoriesPackage getPackage() {
		return IAbapgitrepositoriesPackage.eINSTANCE;
	}

} //AbapgitrepositoriesFactoryImpl
