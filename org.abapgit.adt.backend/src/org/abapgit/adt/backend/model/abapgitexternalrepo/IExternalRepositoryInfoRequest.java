/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Repository Info Request</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUrl <em>Url</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUser <em>User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPassword <em>Password</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPackage <em>Package</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getBranch <em>Branch</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest()
 * @model extendedMetaData="kind='elementOnly' name='externalRepoInfoRequest'"
 * @generated
 */
public interface IExternalRepositoryInfoRequest extends EObject {
	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest_Url()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest_User()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUser();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest_Password()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest_Package()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#getExternalRepositoryInfoRequest_Branch()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

} // IExternalRepositoryInfoRequest
