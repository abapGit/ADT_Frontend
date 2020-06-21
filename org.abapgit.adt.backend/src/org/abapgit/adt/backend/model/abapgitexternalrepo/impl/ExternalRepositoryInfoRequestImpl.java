/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.impl;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Repository Info Request</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl#getUser <em>User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl#getPassword <em>Password</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExternalRepositoryInfoRequestImpl extends MinimalEObjectImpl.Container implements IExternalRepositoryInfoRequest {
	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

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
	protected ExternalRepositoryInfoRequestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitexternalrepoPackage.Literals.EXTERNAL_REPOSITORY_INFO_REQUEST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUrl() {
		return this.url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUrl(String newUrl) {
		String oldUrl = this.url;
		this.url = newUrl;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__URL, oldUrl, this.url));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUser() {
		return this.user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUser(String newUser) {
		String oldUser = this.user;
		this.user = newUser;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__USER, oldUser, this.user));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPassword(String newPassword) {
		String oldPassword = this.password;
		this.password = newPassword;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD, oldPassword, this.password));
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
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__URL:
				return getUrl();
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__USER:
				return getUser();
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD:
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
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__URL:
				setUrl((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__USER:
				setUser((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD:
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
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__URL:
				setUrl(URL_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__USER:
				setUser(USER_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD:
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
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__URL:
				return URL_EDEFAULT == null ? this.url != null : !URL_EDEFAULT.equals(this.url);
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__USER:
				return USER_EDEFAULT == null ? this.user != null : !USER_EDEFAULT.equals(this.user);
			case IAbapgitexternalrepoPackage.EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD:
				return PASSWORD_EDEFAULT == null ? this.password != null : !PASSWORD_EDEFAULT.equals(this.password);
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
		result.append(" (url: ");
		result.append(this.url);
		result.append(", user: ");
		result.append(this.user);
		result.append(", password: ");
		result.append(this.password);
		result.append(')');
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
		result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
		result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExternalRepositoryInfoRequestImpl other = (ExternalRepositoryInfoRequestImpl) obj;
		if (this.password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!this.password.equals(other.password)) {
			return false;
		}
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}

} //ExternalRepositoryInfoRequestImpl
