/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git Pull Request</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getOverwriteObjects <em>Overwrite Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getPackageWarningObjects <em>Package Warning Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getBranchName <em>Branch Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getTransportRequest <em>Transport Request</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getUser <em>User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getPassword <em>Password</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest()
 * @model extendedMetaData="kind='elementOnly' name='abapgitpullrequest'"
 * @generated
 */
public interface IAbapGitPullRequest extends EObject {
	/**
	 * Returns the value of the '<em><b>Overwrite Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overwrite Objects</em>' containment reference.
	 * @see #setOverwriteObjects(IOverwriteObjects)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_OverwriteObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='http://www.sap.com/adt/abapgit/agitpullmodifiedobjects' name='overwriteObjects'"
	 * @generated
	 */
	IOverwriteObjects getOverwriteObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getOverwriteObjects <em>Overwrite Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overwrite Objects</em>' containment reference.
	 * @see #getOverwriteObjects()
	 * @generated
	 */
	void setOverwriteObjects(IOverwriteObjects value);

	/**
	 * Returns the value of the '<em><b>Package Warning Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Warning Objects</em>' containment reference.
	 * @see #setPackageWarningObjects(IPackageWarningObjects)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_PackageWarningObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='http://www.sap.com/adt/abapgit/agitpullmodifiedobjects' name='packageWarningObjects'"
	 * @generated
	 */
	IPackageWarningObjects getPackageWarningObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getPackageWarningObjects <em>Package Warning Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Warning Objects</em>' containment reference.
	 * @see #getPackageWarningObjects()
	 * @generated
	 */
	void setPackageWarningObjects(IPackageWarningObjects value);

	/**
	 * Returns the value of the '<em><b>Branch Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch Name</em>' attribute.
	 * @see #setBranchName(String)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_BranchName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='branchName'"
	 * @generated
	 */
	String getBranchName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getBranchName <em>Branch Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch Name</em>' attribute.
	 * @see #getBranchName()
	 * @generated
	 */
	void setBranchName(String value);

	/**
	 * Returns the value of the '<em><b>Transport Request</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transport Request</em>' attribute.
	 * @see #setTransportRequest(String)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_TransportRequest()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='transportRequest'"
	 * @generated
	 */
	String getTransportRequest();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getTransportRequest <em>Transport Request</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transport Request</em>' attribute.
	 * @see #getTransportRequest()
	 * @generated
	 */
	void setTransportRequest(String value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_User()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='user'"
	 * @generated
	 */
	String getUser();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getUser <em>User</em>}' attribute.
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
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#getAbapGitPullRequest_Password()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='password'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

} // IAbapGitPullRequest
