/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import com.sap.adt.tools.core.model.atom.IAtomLink;
import java.util.Collection;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl#getLinks <em>Links</em>}</li>
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
	protected static final String LOCAL_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocalState() <em>Local State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalState()
	 * @generated
	 * @ordered
	 */
	protected String localState = LOCAL_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemoteState() <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteState()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOTE_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRemoteState() <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteState()
	 * @generated
	 * @ordered
	 */
	protected String remoteState = REMOTE_STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<IAtomLink> links;

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
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalState() {
		return localState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalState(String newLocalState) {
		String oldLocalState = localState;
		localState = newLocalState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE, oldLocalState, localState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRemoteState() {
		return remoteState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteState(String newRemoteState) {
		String oldRemoteState = remoteState;
		remoteState = newRemoteState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE, oldRemoteState, remoteState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAtomLink> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<IAtomLink>(IAtomLink.class, this, IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				return getName();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				return getPath();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				return getLocalState();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				return getRemoteState();
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS:
				return getLinks();
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__NAME:
				setName((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				setPath((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				setLocalState((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				setRemoteState((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends IAtomLink>)newValue);
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
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS:
				getLinks().clear();
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
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LOCAL_STATE:
				return LOCAL_STATE_EDEFAULT == null ? localState != null : !LOCAL_STATE_EDEFAULT.equals(localState);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__REMOTE_STATE:
				return REMOTE_STATE_EDEFAULT == null ? remoteState != null : !REMOTE_STATE_EDEFAULT.equals(remoteState);
			case IAbapgitstagingPackage.ABAP_GIT_FILE__LINKS:
				return links != null && !links.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", path: ");
		result.append(path);
		result.append(", localState: ");
		result.append(localState);
		result.append(", remoteState: ");
		result.append(remoteState);
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
