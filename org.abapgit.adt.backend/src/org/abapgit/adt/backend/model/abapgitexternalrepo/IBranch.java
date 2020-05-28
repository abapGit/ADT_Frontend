/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getSha1 <em>Sha1</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getIsHead <em>Is Head</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch()
 * @model extendedMetaData="kind='elementOnly' name='branch'"
 * @generated
 */
public interface IBranch extends EObject {
	/**
	 * Returns the value of the '<em><b>Sha1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sha1</em>' attribute.
	 * @see #setSha1(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch_Sha1()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='sha1'"
	 * @generated
	 */
	String getSha1();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getSha1 <em>Sha1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sha1</em>' attribute.
	 * @see #getSha1()
	 * @generated
	 */
	void setSha1(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Is Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Head</em>' attribute.
	 * @see #setIsHead(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch_IsHead()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='isHead'"
	 * @generated
	 */
	String getIsHead();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getIsHead <em>Is Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Head</em>' attribute.
	 * @see #getIsHead()
	 * @generated
	 */
	void setIsHead(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='type'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getBranch_DisplayName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='displayName'"
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

} // IBranch
