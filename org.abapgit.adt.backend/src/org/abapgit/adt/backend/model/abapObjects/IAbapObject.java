/**
 */
package org.abapgit.adt.backend.model.abapObjects;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getPackage <em>Package</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getMsgType <em>Msg Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getMsgText <em>Msg Text</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getStatus <em>Status</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getAbapLogObjectChildren <em>Abap Log Object Children</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject()
 * @model extendedMetaData="kind='elementOnly' name='abapObject'"
 * @generated
 */
public interface IAbapObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='type'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_Package()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='package'"
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Msg Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg Type</em>' attribute.
	 * @see #setMsgType(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_MsgType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='msgType'"
	 * @generated
	 */
	String getMsgType();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getMsgType <em>Msg Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg Type</em>' attribute.
	 * @see #getMsgType()
	 * @generated
	 */
	void setMsgType(String value);

	/**
	 * Returns the value of the '<em><b>Msg Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg Text</em>' attribute.
	 * @see #setMsgText(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_MsgText()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='msgText'"
	 * @generated
	 */
	String getMsgText();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getMsgText <em>Msg Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg Text</em>' attribute.
	 * @see #getMsgText()
	 * @generated
	 */
	void setMsgText(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_Status()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='status'"
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapObjects.IAbapObject#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

	/**
	 * Returns the value of the '<em><b>Abap Log Object Children</b></em>' reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapObjects.IAbapObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abap Log Object Children</em>' reference list.
	 * @see org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage#getAbapObject_AbapLogObjectChildren()
	 * @model extendedMetaData="kind='element' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<IAbapObject> getAbapLogObjectChildren();

} // IAbapObject
