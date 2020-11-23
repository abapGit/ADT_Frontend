/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest;

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
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot#getAbapgitpullrequest <em>Abapgitpullrequest</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getDocumentRoot()
 * @model extendedMetaData="kind='mixed' name=''"
 * @generated
 */
public interface IDocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getDocumentRoot_Mixed()
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
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getDocumentRoot_XMLNSPrefixMap()
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
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Abapgitpullrequest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abapgitpullrequest</em>' containment reference.
	 * @see #setAbapgitpullrequest(IAbapGitPullRequest)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getDocumentRoot_Abapgitpullrequest()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapgitpullrequest'"
	 * @generated
	 */
	IAbapGitPullRequest getAbapgitpullrequest();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot#getAbapgitpullrequest <em>Abapgitpullrequest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abapgitpullrequest</em>' containment reference.
	 * @see #getAbapgitpullrequest()
	 * @generated
	 */
	void setAbapgitpullrequest(IAbapGitPullRequest value);

} // IDocumentRoot
