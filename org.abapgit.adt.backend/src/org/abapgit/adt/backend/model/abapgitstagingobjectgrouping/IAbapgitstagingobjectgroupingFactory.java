/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage
 * @generated
 */
public interface IAbapgitstagingobjectgroupingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitstagingobjectgroupingFactory eINSTANCE = org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Abap Git Staging Group Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Staging Group Node</em>'.
	 * @generated
	 */
	IAbapGitStagingGroupNode createAbapGitStagingGroupNode();

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
	IAbapgitstagingobjectgroupingPackage getAbapgitstagingobjectgroupingPackage();

} //IAbapgitstagingobjectgroupingFactory
