/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.sap.adt.tools.core.model.adtcore.impl.AdtObjectReferenceImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Overwrite Object</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl#getAction
 * <em>Action</em>}</li>
 * <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl#getActionDescription
 * <em>Action Description</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OverwriteObjectImpl extends AdtObjectReferenceImpl implements IOverwriteObject {
	/**
	 * The default value of the '{@link #getAction() <em>Action</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected String action = ACTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getActionDescription() <em>Action
	 * Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getActionDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActionDescription() <em>Action
	 * Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getActionDescription()
	 * @generated
	 * @ordered
	 */
	protected String actionDescription = ACTION_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected OverwriteObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAgitpullmodifiedobjectsPackage.Literals.OVERWRITE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getAction() {
		return this.action;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAction(String newAction) {
		String oldAction = this.action;
		this.action = newAction;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION, oldAction,
					this.action));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getActionDescription() {
		return this.actionDescription;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setActionDescription(String newActionDescription) {
		String oldActionDescription = this.actionDescription;
		this.actionDescription = newActionDescription;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION_DESCRIPTION,
					oldActionDescription, this.actionDescription));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION:
			return getAction();
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION_DESCRIPTION:
			return getActionDescription();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION:
			setAction((String) newValue);
			return;
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION_DESCRIPTION:
			setActionDescription((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION:
			setAction(ACTION_EDEFAULT);
			return;
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION_DESCRIPTION:
			setActionDescription(ACTION_DESCRIPTION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION:
			return ACTION_EDEFAULT == null ? this.action != null : !ACTION_EDEFAULT.equals(this.action);
		case IAgitpullmodifiedobjectsPackage.OVERWRITE_OBJECT__ACTION_DESCRIPTION:
			return ACTION_DESCRIPTION_EDEFAULT == null ? this.actionDescription != null
					: !ACTION_DESCRIPTION_EDEFAULT.equals(this.actionDescription);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (action: ");
		result.append(this.action);
		result.append(", actionDescription: ");
		result.append(this.actionDescription);
		result.append(')');
		return result.toString();
	}

} //OverwriteObjectImpl
