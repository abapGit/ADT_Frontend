/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest.impl;

import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest;
import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage;

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
 * An implementation of the model object '<em><b>Abap Git Pull Request</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getOverwriteObjects <em>Overwrite Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getPackageWarningObjects <em>Package Warning Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getBranchName <em>Branch Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getTransportRequest <em>Transport Request</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getUser <em>User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitpullrequest.impl.AbapGitPullRequestImpl#getPassword <em>Password</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitPullRequestImpl extends MinimalEObjectImpl.Container implements IAbapGitPullRequest {
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
	 * The default value of the '{@link #getBranchName() <em>Branch Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranchName()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANCH_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBranchName() <em>Branch Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranchName()
	 * @generated
	 * @ordered
	 */
	protected String branchName = BRANCH_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTransportRequest() <em>Transport Request</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransportRequest()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSPORT_REQUEST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransportRequest() <em>Transport Request</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransportRequest()
	 * @generated
	 * @ordered
	 */
	protected String transportRequest = TRANSPORT_REQUEST_EDEFAULT;

	/**
	 * The default value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected String user = USER_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitPullRequestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitpullrequestPackage.Literals.ABAP_GIT_PULL_REQUEST;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS, oldOverwriteObjects, newOverwriteObjects);
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
				msgs = ((InternalEObject)overwriteObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS, null, msgs);
			if (newOverwriteObjects != null)
				msgs = ((InternalEObject)newOverwriteObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS, null, msgs);
			msgs = basicSetOverwriteObjects(newOverwriteObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS, newOverwriteObjects, newOverwriteObjects));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS, oldPackageWarningObjects, newPackageWarningObjects);
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
				msgs = ((InternalEObject)packageWarningObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS, null, msgs);
			if (newPackageWarningObjects != null)
				msgs = ((InternalEObject)newPackageWarningObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS, null, msgs);
			msgs = basicSetPackageWarningObjects(newPackageWarningObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS, newPackageWarningObjects, newPackageWarningObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBranchName() {
		return branchName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBranchName(String newBranchName) {
		String oldBranchName = branchName;
		branchName = newBranchName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__BRANCH_NAME, oldBranchName, branchName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTransportRequest() {
		return transportRequest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransportRequest(String newTransportRequest) {
		String oldTransportRequest = transportRequest;
		transportRequest = newTransportRequest;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST, oldTransportRequest, transportRequest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUser() {
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUser(String newUser) {
		String oldUser = user;
		user = newUser;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__USER, oldUser, user));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS:
				return basicSetOverwriteObjects(null, msgs);
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS:
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
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS:
				return getOverwriteObjects();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS:
				return getPackageWarningObjects();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__BRANCH_NAME:
				return getBranchName();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST:
				return getTransportRequest();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__USER:
				return getUser();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PASSWORD:
				return getPassword();
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
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS:
				setOverwriteObjects((IOverwriteObjects)newValue);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS:
				setPackageWarningObjects((IPackageWarningObjects)newValue);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__BRANCH_NAME:
				setBranchName((String)newValue);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST:
				setTransportRequest((String)newValue);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__USER:
				setUser((String)newValue);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PASSWORD:
				setPassword((String)newValue);
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
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS:
				setOverwriteObjects((IOverwriteObjects)null);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS:
				setPackageWarningObjects((IPackageWarningObjects)null);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__BRANCH_NAME:
				setBranchName(BRANCH_NAME_EDEFAULT);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST:
				setTransportRequest(TRANSPORT_REQUEST_EDEFAULT);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__USER:
				setUser(USER_EDEFAULT);
				return;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
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
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS:
				return overwriteObjects != null;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS:
				return packageWarningObjects != null;
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__BRANCH_NAME:
				return BRANCH_NAME_EDEFAULT == null ? branchName != null : !BRANCH_NAME_EDEFAULT.equals(branchName);
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST:
				return TRANSPORT_REQUEST_EDEFAULT == null ? transportRequest != null : !TRANSPORT_REQUEST_EDEFAULT.equals(transportRequest);
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__USER:
				return USER_EDEFAULT == null ? user != null : !USER_EDEFAULT.equals(user);
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
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
		result.append(" (branchName: ");
		result.append(branchName);
		result.append(", transportRequest: ");
		result.append(transportRequest);
		result.append(", user: ");
		result.append(user);
		result.append(", password: ");
		result.append(password);
		result.append(')');
		return result.toString();
	}

} //AbapGitPullRequestImpl
