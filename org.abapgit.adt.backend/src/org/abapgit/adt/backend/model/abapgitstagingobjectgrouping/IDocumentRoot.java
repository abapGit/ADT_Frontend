/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getAbapgitstaginggroupnode <em>Abapgitstaginggroupnode</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface IDocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Abapgitstaginggroupnode</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abapgitstaginggroupnode</em>' containment reference.
	 * @see #setAbapgitstaginggroupnode(IAbapGitStagingGroupNode)
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#getDocumentRoot_Abapgitstaginggroupnode()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='abapgitstaginggroupnode' namespace='##targetNamespace'"
	 * @generated
	 */
	IAbapGitStagingGroupNode getAbapgitstaginggroupnode();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getAbapgitstaginggroupnode <em>Abapgitstaginggroupnode</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abapgitstaginggroupnode</em>' containment reference.
	 * @see #getAbapgitstaginggroupnode()
	 * @generated
	 */
	void setAbapgitstaginggroupnode(IAbapGitStagingGroupNode value);

} // IDocumentRoot
