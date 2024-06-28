/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Overwrite Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getAction <em>Action</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getActionDescription <em>Action Description</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getOverwriteObject()
 * @model extendedMetaData="kind='elementOnly' name='overwriteObject'"
 * @generated
 */
public interface IOverwriteObject extends IAdtObjectReference {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' attribute.
	 * @see #setAction(String)
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getOverwriteObject_Action()
	 * @model extendedMetaData="kind='attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAction();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getAction <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' attribute.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(String value);

	/**
	 * Returns the value of the '<em><b>Action Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Description</em>' attribute.
	 * @see #setActionDescription(String)
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getOverwriteObject_ActionDescription()
	 * @model extendedMetaData="kind='attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	String getActionDescription();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getActionDescription <em>Action Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Description</em>' attribute.
	 * @see #getActionDescription()
	 * @generated
	 */
	void setActionDescription(String value);

} // IOverwriteObject
