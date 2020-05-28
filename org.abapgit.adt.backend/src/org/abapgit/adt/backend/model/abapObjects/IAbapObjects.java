/**
 */
package org.abapgit.adt.backend.model.abapObjects;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Objects</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObjects#getAbapObjects <em>Abap Objects</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObjects()
 * @model extendedMetaData="kind='elementOnly' name='abapObjects'"
 * @generated
 */
public interface IAbapObjects extends EObject {
	/**
	 * Returns the value of the '<em><b>Abap Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapObjects.IAbapObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abap Objects</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObjects_AbapObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapObject'"
	 * @generated
	 */
	EList<IAbapObject> getAbapObjects();

} // IAbapObjects
