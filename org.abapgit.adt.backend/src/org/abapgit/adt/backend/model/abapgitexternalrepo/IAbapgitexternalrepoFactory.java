/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage
 * @generated
 */
public interface IAbapgitexternalrepoFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitexternalrepoFactory eINSTANCE = org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Branch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Branch</em>'.
	 * @generated
	 */
	IBranch createBranch();

	/**
	 * Returns a new object of class '<em>External Repository Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Repository Info</em>'.
	 * @generated
	 */
	IExternalRepositoryInfo createExternalRepositoryInfo();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	IDocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>External Repository Info Request</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Repository Info Request</em>'.
	 * @generated
	 */
	IExternalRepositoryInfoRequest createExternalRepositoryInfoRequest();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IAbapgitexternalrepoPackage getAbapgitexternalrepoPackage();

} //IAbapgitexternalrepoFactory
