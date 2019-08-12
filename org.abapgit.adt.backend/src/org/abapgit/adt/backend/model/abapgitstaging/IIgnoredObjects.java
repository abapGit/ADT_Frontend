/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ignored Objects</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects#getAbapgitobject <em>Abapgitobject</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getIgnoredObjects()
 * @model extendedMetaData="name='ignored_objects' kind='elementOnly'"
 * @generated
 */
public interface IIgnoredObjects extends EObject {
	/**
	 * Returns the value of the '<em><b>Abapgitobject</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abapgitobject</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getIgnoredObjects_Abapgitobject()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapgitobject'"
	 * @generated
	 */
	EList<IAbapGitObject> getAbapgitobject();

} // IIgnoredObjects
