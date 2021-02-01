/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage
 * @generated
 */
public interface IAbapgitpullrequestFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitpullrequestFactory eINSTANCE = org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapgitpullrequestFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	IDocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Abap Git Pull Request</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Pull Request</em>'.
	 * @generated
	 */
	IAbapGitPullRequest createAbapGitPullRequest();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IAbapgitpullrequestPackage getAbapgitpullrequestPackage();

} //IAbapgitpullrequestFactory
