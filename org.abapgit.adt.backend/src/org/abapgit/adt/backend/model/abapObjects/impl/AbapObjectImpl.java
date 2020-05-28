/**
 */
package org.abapgit.adt.backend.model.abapObjects.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getMsgType <em>Msg Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getMsgText <em>Msg Text</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectImpl#getAbapLogObjectChildren <em>Abap Log Object Children</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapObjectImpl extends MinimalEObjectImpl.Container implements IAbapObject {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMsgType() <em>Msg Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgType()
	 * @generated
	 * @ordered
	 */
	protected static final String MSG_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMsgType() <em>Msg Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgType()
	 * @generated
	 * @ordered
	 */
	protected String msgType = MSG_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMsgText() <em>Msg Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgText()
	 * @generated
	 * @ordered
	 */
	protected static final String MSG_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMsgText() <em>Msg Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMsgText()
	 * @generated
	 * @ordered
	 */
	protected String msgText = MSG_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected String status = STATUS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAbapLogObjectChildren() <em>Abap Log Object Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbapLogObjectChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbapObject> abapLogObjectChildren;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapObjectsPackage.Literals.ABAP_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__PACKAGE, oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMsgType() {
		return msgType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMsgType(String newMsgType) {
		String oldMsgType = msgType;
		msgType = newMsgType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__MSG_TYPE, oldMsgType, msgType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMsgText() {
		return msgText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMsgText(String newMsgText) {
		String oldMsgText = msgText;
		msgText = newMsgText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__MSG_TEXT, oldMsgText, msgText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(String newStatus) {
		String oldStatus = status;
		status = newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapObjectsPackage.ABAP_OBJECT__STATUS, oldStatus, status));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAbapObject> getAbapLogObjectChildren() {
		if (abapLogObjectChildren == null) {
			abapLogObjectChildren = new EObjectResolvingEList<IAbapObject>(IAbapObject.class, this, IAbapObjectsPackage.ABAP_OBJECT__ABAP_LOG_OBJECT_CHILDREN);
		}
		return abapLogObjectChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IAbapObjectsPackage.ABAP_OBJECT__TYPE:
				return getType();
			case IAbapObjectsPackage.ABAP_OBJECT__PACKAGE:
				return getPackage();
			case IAbapObjectsPackage.ABAP_OBJECT__NAME:
				return getName();
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TYPE:
				return getMsgType();
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TEXT:
				return getMsgText();
			case IAbapObjectsPackage.ABAP_OBJECT__STATUS:
				return getStatus();
			case IAbapObjectsPackage.ABAP_OBJECT__ABAP_LOG_OBJECT_CHILDREN:
				return getAbapLogObjectChildren();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IAbapObjectsPackage.ABAP_OBJECT__TYPE:
				setType((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__PACKAGE:
				setPackage((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__NAME:
				setName((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TYPE:
				setMsgType((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TEXT:
				setMsgText((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__STATUS:
				setStatus((String)newValue);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__ABAP_LOG_OBJECT_CHILDREN:
				getAbapLogObjectChildren().clear();
				getAbapLogObjectChildren().addAll((Collection<? extends IAbapObject>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IAbapObjectsPackage.ABAP_OBJECT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TYPE:
				setMsgType(MSG_TYPE_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TEXT:
				setMsgText(MSG_TEXT_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case IAbapObjectsPackage.ABAP_OBJECT__ABAP_LOG_OBJECT_CHILDREN:
				getAbapLogObjectChildren().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IAbapObjectsPackage.ABAP_OBJECT__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case IAbapObjectsPackage.ABAP_OBJECT__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
			case IAbapObjectsPackage.ABAP_OBJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TYPE:
				return MSG_TYPE_EDEFAULT == null ? msgType != null : !MSG_TYPE_EDEFAULT.equals(msgType);
			case IAbapObjectsPackage.ABAP_OBJECT__MSG_TEXT:
				return MSG_TEXT_EDEFAULT == null ? msgText != null : !MSG_TEXT_EDEFAULT.equals(msgText);
			case IAbapObjectsPackage.ABAP_OBJECT__STATUS:
				return STATUS_EDEFAULT == null ? status != null : !STATUS_EDEFAULT.equals(status);
			case IAbapObjectsPackage.ABAP_OBJECT__ABAP_LOG_OBJECT_CHILDREN:
				return abapLogObjectChildren != null && !abapLogObjectChildren.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", package: ");
		result.append(package_);
		result.append(", name: ");
		result.append(name);
		result.append(", msgType: ");
		result.append(msgType);
		result.append(", msgText: ");
		result.append(msgText);
		result.append(", status: ");
		result.append(status);
		result.append(')');
		return result.toString();
	}

} //AbapObjectImpl
