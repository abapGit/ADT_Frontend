/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import com.sap.adt.tools.core.model.atom.IAtomLink;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git Staging</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getUnstagedObjects <em>Unstaged Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getStagedObjects <em>Staged Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getIgnoredObjects <em>Ignored Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getCommitMessage <em>Commit Message</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging()
 * @model extendedMetaData="name='abapgitstaging' kind='elementOnly'"
 * @generated
 */
public interface IAbapGitStaging extends EObject {
	/**
	 * Returns the value of the '<em><b>Unstaged Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unstaged Objects</em>' containment reference.
	 * @see #setUnstagedObjects(IUnstagedObjects)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging_UnstagedObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='unstaged_objects'"
	 * @generated
	 */
	IUnstagedObjects getUnstagedObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getUnstagedObjects <em>Unstaged Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unstaged Objects</em>' containment reference.
	 * @see #getUnstagedObjects()
	 * @generated
	 */
	void setUnstagedObjects(IUnstagedObjects value);

	/**
	 * Returns the value of the '<em><b>Staged Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Staged Objects</em>' containment reference.
	 * @see #setStagedObjects(IStagedObjects)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging_StagedObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='staged_objects'"
	 * @generated
	 */
	IStagedObjects getStagedObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getStagedObjects <em>Staged Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Staged Objects</em>' containment reference.
	 * @see #getStagedObjects()
	 * @generated
	 */
	void setStagedObjects(IStagedObjects value);

	/**
	 * Returns the value of the '<em><b>Ignored Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ignored Objects</em>' containment reference.
	 * @see #setIgnoredObjects(IIgnoredObjects)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging_IgnoredObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='ignored_objects'"
	 * @generated
	 */
	IIgnoredObjects getIgnoredObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getIgnoredObjects <em>Ignored Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ignored Objects</em>' containment reference.
	 * @see #getIgnoredObjects()
	 * @generated
	 */
	void setIgnoredObjects(IIgnoredObjects value);

	/**
	 * Returns the value of the '<em><b>Commit Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit Message</em>' containment reference.
	 * @see #setCommitMessage(IAbapGitCommitMessage)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging_CommitMessage()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapgit_comment'"
	 * @generated
	 */
	IAbapGitCommitMessage getCommitMessage();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getCommitMessage <em>Commit Message</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit Message</em>' containment reference.
	 * @see #getCommitMessage()
	 * @generated
	 */
	void setCommitMessage(IAbapGitCommitMessage value);

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.sap.adt.tools.core.model.atom.IAtomLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitStaging_Links()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='link' namespace='http://www.w3.org/2005/Atom'"
	 * @generated
	 */
	EList<IAtomLink> getLinks();

} // IAbapGitStaging
