/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Git Pull Modified Objects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl#getOverwriteObjects <em>Overwrite Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl#getPackageWarningObjects <em>Package Warning Objects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitPullModifiedObjectsImpl extends MinimalEObjectImpl.Container implements IAbapGitPullModifiedObjects {
	/**
	 * The cached value of the '{@link #getOverwriteObjects() <em>Overwrite Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverwriteObjects()
	 * @generated
	 * @ordered
	 */
	protected IOverwriteObjects overwriteObjects;

	/**
	 * The cached value of the '{@link #getPackageWarningObjects() <em>Package Warning Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageWarningObjects()
	 * @generated
	 * @ordered
	 */
	protected IPackageWarningObjects packageWarningObjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitPullModifiedObjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAgitpullmodifiedobjectsPackage.Literals.ABAP_GIT_PULL_MODIFIED_OBJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IOverwriteObjects getOverwriteObjects() {
		return overwriteObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOverwriteObjects(IOverwriteObjects newOverwriteObjects, NotificationChain msgs) {
		IOverwriteObjects oldOverwriteObjects = overwriteObjects;
		overwriteObjects = newOverwriteObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS, oldOverwriteObjects, newOverwriteObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOverwriteObjects(IOverwriteObjects newOverwriteObjects) {
		if (newOverwriteObjects != overwriteObjects) {
			NotificationChain msgs = null;
			if (overwriteObjects != null)
				msgs = ((InternalEObject)overwriteObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS, null, msgs);
			if (newOverwriteObjects != null)
				msgs = ((InternalEObject)newOverwriteObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS, null, msgs);
			msgs = basicSetOverwriteObjects(newOverwriteObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS, newOverwriteObjects, newOverwriteObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IPackageWarningObjects getPackageWarningObjects() {
		return packageWarningObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPackageWarningObjects(IPackageWarningObjects newPackageWarningObjects, NotificationChain msgs) {
		IPackageWarningObjects oldPackageWarningObjects = packageWarningObjects;
		packageWarningObjects = newPackageWarningObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS, oldPackageWarningObjects, newPackageWarningObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackageWarningObjects(IPackageWarningObjects newPackageWarningObjects) {
		if (newPackageWarningObjects != packageWarningObjects) {
			NotificationChain msgs = null;
			if (packageWarningObjects != null)
				msgs = ((InternalEObject)packageWarningObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS, null, msgs);
			if (newPackageWarningObjects != null)
				msgs = ((InternalEObject)newPackageWarningObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS, null, msgs);
			msgs = basicSetPackageWarningObjects(newPackageWarningObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS, newPackageWarningObjects, newPackageWarningObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS:
				return basicSetOverwriteObjects(null, msgs);
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS:
				return basicSetPackageWarningObjects(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS:
				return getOverwriteObjects();
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS:
				return getPackageWarningObjects();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS:
				setOverwriteObjects((IOverwriteObjects)newValue);
				return;
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS:
				setPackageWarningObjects((IPackageWarningObjects)newValue);
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
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS:
				setOverwriteObjects((IOverwriteObjects)null);
				return;
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS:
				setPackageWarningObjects((IPackageWarningObjects)null);
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
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS:
				return overwriteObjects != null;
			case IAgitpullmodifiedobjectsPackage.ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS:
				return packageWarningObjects != null;
		}
		return super.eIsSet(featureID);
	}

} //AbapGitPullModifiedObjectsImpl
