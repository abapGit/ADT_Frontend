/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories;

import com.sap.adt.tools.core.model.atom.IAtomLink;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getLinks <em>Links</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getKey <em>Key</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getPackage <em>Package</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getUrl <em>Url</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getBranchName <em>Branch Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedBy <em>Created By</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedEmail <em>Created Email</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedAt <em>Created At</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedAt <em>Deserialized At</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedEmail <em>Deserialized Email</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedBy <em>Deserialized By</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatus <em>Status</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatusText <em>Status Text</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemoteUser <em>Remote User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemotePassword <em>Remote Password</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getTransportRequest <em>Transport Request</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository()
 * @model extendedMetaData="kind='elementOnly' name='repository'"
 * @generated
 */
public interface IRepository extends EObject {
	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.sap.adt.tools.core.model.atom.IAtomLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_Links()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='http://www.w3.org/2005/Atom' name='link'"
	 * @generated
	 */
	EList<IAtomLink> getLinks();

	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_Key()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='key'"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_Package()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='package'"
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_Url()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='url'"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * Returns the value of the '<em><b>Branch Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch Name</em>' attribute.
	 * @see #setBranchName(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_BranchName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='branchName'"
	 * @generated
	 */
	String getBranchName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getBranchName <em>Branch Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch Name</em>' attribute.
	 * @see #getBranchName()
	 * @generated
	 */
	void setBranchName(String value);

	/**
	 * Returns the value of the '<em><b>Created By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created By</em>' attribute.
	 * @see #setCreatedBy(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_CreatedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='createdBy'"
	 * @generated
	 */
	String getCreatedBy();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedBy <em>Created By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created By</em>' attribute.
	 * @see #getCreatedBy()
	 * @generated
	 */
	void setCreatedBy(String value);

	/**
	 * Returns the value of the '<em><b>Created Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Email</em>' attribute.
	 * @see #setCreatedEmail(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_CreatedEmail()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='createdEmail'"
	 * @generated
	 */
	String getCreatedEmail();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedEmail <em>Created Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created Email</em>' attribute.
	 * @see #getCreatedEmail()
	 * @generated
	 */
	void setCreatedEmail(String value);

	/**
	 * Returns the value of the '<em><b>Created At</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created At</em>' attribute.
	 * @see #setCreatedAt(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_CreatedAt()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='createdAt'"
	 * @generated
	 */
	String getCreatedAt();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedAt <em>Created At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created At</em>' attribute.
	 * @see #getCreatedAt()
	 * @generated
	 */
	void setCreatedAt(String value);

	/**
	 * Returns the value of the '<em><b>Deserialized At</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deserialized At</em>' attribute.
	 * @see #setDeserializedAt(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_DeserializedAt()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='deserializedAt'"
	 * @generated
	 */
	String getDeserializedAt();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedAt <em>Deserialized At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deserialized At</em>' attribute.
	 * @see #getDeserializedAt()
	 * @generated
	 */
	void setDeserializedAt(String value);

	/**
	 * Returns the value of the '<em><b>Deserialized Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deserialized Email</em>' attribute.
	 * @see #setDeserializedEmail(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_DeserializedEmail()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='deserializedEmail'"
	 * @generated
	 */
	String getDeserializedEmail();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedEmail <em>Deserialized Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deserialized Email</em>' attribute.
	 * @see #getDeserializedEmail()
	 * @generated
	 */
	void setDeserializedEmail(String value);

	/**
	 * Returns the value of the '<em><b>Deserialized By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deserialized By</em>' attribute.
	 * @see #setDeserializedBy(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_DeserializedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='deserializedBy'"
	 * @generated
	 */
	String getDeserializedBy();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedBy <em>Deserialized By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deserialized By</em>' attribute.
	 * @see #getDeserializedBy()
	 * @generated
	 */
	void setDeserializedBy(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_Status()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='status'"
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

	/**
	 * Returns the value of the '<em><b>Status Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status Text</em>' attribute.
	 * @see #setStatusText(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_StatusText()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='statusText'"
	 * @generated
	 */
	String getStatusText();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatusText <em>Status Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status Text</em>' attribute.
	 * @see #getStatusText()
	 * @generated
	 */
	void setStatusText(String value);

	/**
	 * Returns the value of the '<em><b>Remote User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote User</em>' attribute.
	 * @see #setRemoteUser(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_RemoteUser()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='remoteUser'"
	 * @generated
	 */
	String getRemoteUser();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemoteUser <em>Remote User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote User</em>' attribute.
	 * @see #getRemoteUser()
	 * @generated
	 */
	void setRemoteUser(String value);

	/**
	 * Returns the value of the '<em><b>Remote Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Password</em>' attribute.
	 * @see #setRemotePassword(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_RemotePassword()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='remotePassword'"
	 * @generated
	 */
	String getRemotePassword();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemotePassword <em>Remote Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Password</em>' attribute.
	 * @see #getRemotePassword()
	 * @generated
	 */
	void setRemotePassword(String value);

	/**
	 * Returns the value of the '<em><b>Transport Request</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transport Request</em>' attribute.
	 * @see #setTransportRequest(String)
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage#getRepository_TransportRequest()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='transportRequest'"
	 * @generated
	 */
	String getTransportRequest();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getTransportRequest <em>Transport Request</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transport Request</em>' attribute.
	 * @see #getTransportRequest()
	 * @generated
	 */
	void setTransportRequest(String value);

} // IRepository
