/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage
 * @generated
 */
public interface IAbapgitrepositoriesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitrepositoriesFactory eINSTANCE = org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository</em>'.
	 * @generated
	 */
	IRepository createRepository();

	/**
	 * Returns a new object of class '<em>Repositories</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repositories</em>'.
	 * @generated
	 */
	IRepositories createRepositories();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	IDocumentRoot createDocumentRoot();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IAbapgitrepositoriesPackage getAbapgitrepositoriesPackage();

} //IAbapgitrepositoriesFactory
