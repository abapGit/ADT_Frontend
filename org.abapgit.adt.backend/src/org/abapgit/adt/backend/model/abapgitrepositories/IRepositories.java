/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repositories</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepositories#getRepositories <em>Repositories</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepositories()
 * @model extendedMetaData="kind='elementOnly' name='repositories'"
 * @generated
 */
public interface IRepositories extends EObject {
	/**
	 * Returns the value of the '<em><b>Repositories</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repositories</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepositories_Repositories()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='repository'"
	 * @generated
	 */
	EList<IRepository> getRepositories();

} // IRepositories
