/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl;

import java.util.Collection;
import java.util.Objects;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage;
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
 * An implementation of the model object '<em><b>Abap Git Staging Group Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl#getAbapgitobjects <em>Abapgitobjects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitStagingGroupNodeImpl extends MinimalEObjectImpl.Container implements IAbapGitStagingGroupNode {
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
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAbapgitobjects() <em>Abapgitobjects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbapgitobjects()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbapGitObject> abapgitobjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitStagingGroupNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingobjectgroupingPackage.Literals.ABAP_GIT_STAGING_GROUP_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return this.type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = this.type;
		this.type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__TYPE, oldType, this.type));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__VALUE, oldValue, this.value));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUri() {
		return this.uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUri(String newUri) {
		String oldUri = this.uri;
		this.uri = newUri;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__URI, oldUri, this.uri));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAbapGitObject> getAbapgitobjects() {
		if (this.abapgitobjects == null) {
			this.abapgitobjects = new EObjectContainmentEList<IAbapGitObject>(IAbapGitObject.class, this, IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS);
		}
		return this.abapgitobjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS:
				return ((InternalEList<?>)getAbapgitobjects()).basicRemove(otherEnd, msgs);
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
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__TYPE:
				return getType();
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__VALUE:
				return getValue();
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__URI:
				return getUri();
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS:
				return getAbapgitobjects();
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
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__TYPE:
				setType((String)newValue);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__VALUE:
				setValue((String)newValue);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__URI:
				setUri((String)newValue);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS:
				getAbapgitobjects().clear();
				getAbapgitobjects().addAll((Collection<? extends IAbapGitObject>)newValue);
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
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__URI:
				setUri(URI_EDEFAULT);
				return;
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS:
				getAbapgitobjects().clear();
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
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__TYPE:
				return TYPE_EDEFAULT == null ? this.type != null : !TYPE_EDEFAULT.equals(this.type);
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__VALUE:
				return VALUE_EDEFAULT == null ? this.value != null : !VALUE_EDEFAULT.equals(this.value);
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__URI:
				return URI_EDEFAULT == null ? this.uri != null : !URI_EDEFAULT.equals(this.uri);
			case IAbapgitstagingobjectgroupingPackage.ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS:
				return this.abapgitobjects != null && !this.abapgitobjects.isEmpty();
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
		result.append(" (type: ");
		result.append(this.type);
		result.append(", value: ");
		result.append(this.value);
		result.append(", uri: ");
		result.append(this.uri);
		result.append(')');
		return result.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.uri, this.value);
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
		AbapGitStagingGroupNodeImpl other = (AbapGitStagingGroupNodeImpl) obj;
		return Objects.equals(this.type, other.type) && Objects.equals(this.uri, other.uri) && Objects.equals(this.value, other.value);
	}

} //AbapGitStagingGroupNodeImpl
