/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git Staging Group Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getValue <em>Value</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getUri <em>Uri</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getAbapgitobjects <em>Abapgitobjects</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getAbapGitStagingGroupNode()
 * @model extendedMetaData="name='abapgitstaginggroupnode' kind='elementOnly'"
 * @generated
 */
public interface IAbapGitStagingGroupNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getAbapGitStagingGroupNode_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getAbapGitStagingGroupNode_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getAbapGitStagingGroupNode_Uri()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Abapgitobjects</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abapgitobjects</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getAbapGitStagingGroupNode_Abapgitobjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='abapgitobjects' namespace='http://www.w3.org/2005/Atom'"
	 * @generated
	 */
	EList<IAbapGitObject> getAbapgitobjects();

} // IAbapGitStagingGroupNode
