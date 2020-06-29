/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Repository Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getBranches <em>Branches</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getAccessMode <em>Access Mode</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfo()
 * @model extendedMetaData="kind='elementOnly' name='externalRepoInfo'"
 * @generated
 */
public interface IExternalRepositoryInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Branches</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branches</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfo_Branches()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='branch'"
	 * @generated
	 */
	EList<IBranch> getBranches();

	/**
	 * Returns the value of the '<em><b>Access Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Mode</em>' attribute.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode
	 * @see #setAccessMode(AccessMode)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfo_AccessMode()
	 * @model extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	AccessMode getAccessMode();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getAccessMode <em>Access Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Mode</em>' attribute.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode
	 * @see #getAccessMode()
	 * @generated
	 */
	void setAccessMode(AccessMode value);

} // IExternalRepositoryInfo
