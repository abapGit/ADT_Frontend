/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Git File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl#getLocalState <em>Local State</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl#getRemoteState <em>Remote State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitFileImpl extends MinimalEObjectImpl.Container implements IAbapGitFile {
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
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocalState() <em>Local State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalState()
	 * @generated
	 * @ordered
	 */
	protected static final char LOCAL_STATE_EDEFAULT = '\u0000';

	/**
	 * The cached value of the '{@link #getLocalState() <em>Local State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalState()
	 * @generated
	 * @ordered
	 */
	protected char localState = LOCAL_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemoteState() <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteState()
	 * @generated
	 * @ordered
	 */
	protected static final char REMOTE_STATE_EDEFAULT = '\u0000';

	/**
	 * The cached value of the '{@link #getRemoteState() <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteState()
	 * @generated
	 * @ordered
	 */
	protected char remoteState = REMOTE_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingPackage.Literals.ABAP_GIT_FILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__NAME, oldName, this.name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = this.path;
		this.path = newPath;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__PATH, oldPath, this.path));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public char getLocalState() {
		return this.localState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalState(char newLocalState) {
		char oldLocalState = this.localState;
		this.localState = newLocalState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE, oldLocalState, this.localState));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public char getRemoteState() {
		return this.remoteState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteState(char newRemoteState) {
		char oldRemoteState = this.remoteState;
		this.remoteState = newRemoteState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE, oldRemoteState, this.remoteState));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				return getName();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				return getPath();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				return getLocalState();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				return getRemoteState();
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				setName((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				setPath((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				setLocalState((Character)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				setRemoteState((Character)newValue);
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				setLocalState(LOCAL_STATE_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				setRemoteState(REMOTE_STATE_EDEFAULT);
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				return PATH_EDEFAULT == null ? this.path != null : !PATH_EDEFAULT.equals(this.path);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				return this.localState != LOCAL_STATE_EDEFAULT;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				return this.remoteState != REMOTE_STATE_EDEFAULT;
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
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(this.name);
		result.append(", path: "); //$NON-NLS-1$
		result.append(this.path);
		result.append(", localState: "); //$NON-NLS-1$
		result.append(this.localState);
		result.append(", remoteState: "); //$NON-NLS-1$
		result.append(this.remoteState);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IAbapGitFile) {
			IAbapGitFile file = (IAbapGitFile) obj;
			if ((this.getName().equals(file.getName())) && (this.getPath().equals(file.getPath()))) {
				return true;
			}
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
		return result;
	}

} //AbapGitFileImpl
