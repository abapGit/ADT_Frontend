/**
 */
package org.abapgit.adt.backend.model.abapObjects;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage
 * @generated
 */
public interface IAbapObjectsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapObjectsFactory eINSTANCE = org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Abap Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Object</em>'.
	 * @generated
	 */
	IAbapObject createAbapObject();

	/**
	 * Returns a new object of class '<em>Abap Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Objects</em>'.
	 * @generated
	 */
	IAbapObjects createAbapObjects();

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
	IAbapObjectsPackage getAbapObjectsPackage();

} //IAbapObjectsFactory
