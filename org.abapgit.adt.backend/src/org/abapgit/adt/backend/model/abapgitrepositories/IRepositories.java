/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories;

import com.sap.adt.tools.core.model.atom.IAtomLink;
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
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepositories#getLinks <em>Links</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.sap.adt.tools.core.model.atom.IAtomLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepositories_Links()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='http://www.w3.org/2005/Atom' name='link'"
	 * @generated
	 */
	EList<IAtomLink> getLinks();

} // IRepositories
