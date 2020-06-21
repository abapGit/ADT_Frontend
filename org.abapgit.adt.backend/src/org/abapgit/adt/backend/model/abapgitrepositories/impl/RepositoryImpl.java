/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.sap.adt.tools.core.model.atom.IAtomLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getBranchName <em>Branch Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getCreatedBy <em>Created By</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getCreatedEmail <em>Created Email</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getCreatedAt <em>Created At</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getDeserializedAt <em>Deserialized At</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getDeserializedEmail <em>Deserialized Email</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getDeserializedBy <em>Deserialized By</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getStatusText <em>Status Text</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getRemoteUser <em>Remote User</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getRemotePassword <em>Remote Password</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl#getTransportRequest <em>Transport Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoryImpl extends MinimalEObjectImpl.Container implements IRepository {
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
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

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
	 * The default value of the '{@link #getCreatedBy() <em>Created By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedBy() <em>Created By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedBy()
	 * @generated
	 * @ordered
	 */
	protected String createdBy = CREATED_BY_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreatedEmail() <em>Created Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATED_EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedEmail() <em>Created Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedEmail()
	 * @generated
	 * @ordered
	 */
	protected String createdEmail = CREATED_EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreatedAt() <em>Created At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedAt()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATED_AT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedAt() <em>Created At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedAt()
	 * @generated
	 * @ordered
	 */
	protected String createdAt = CREATED_AT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeserializedAt() <em>Deserialized At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedAt()
	 * @generated
	 * @ordered
	 */
	protected static final String DESERIALIZED_AT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeserializedAt() <em>Deserialized At</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedAt()
	 * @generated
	 * @ordered
	 */
	protected String deserializedAt = DESERIALIZED_AT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeserializedEmail() <em>Deserialized Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedEmail()
	 * @generated
	 * @ordered
	 */
	protected static final String DESERIALIZED_EMAIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeserializedEmail() <em>Deserialized Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedEmail()
	 * @generated
	 * @ordered
	 */
	protected String deserializedEmail = DESERIALIZED_EMAIL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeserializedBy() <em>Deserialized By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String DESERIALIZED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeserializedBy() <em>Deserialized By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeserializedBy()
	 * @generated
	 * @ordered
	 */
	protected String deserializedBy = DESERIALIZED_BY_EDEFAULT;

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
	 * The default value of the '{@link #getStatusText() <em>Status Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusText()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatusText() <em>Status Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusText()
	 * @generated
	 * @ordered
	 */
	protected String statusText = STATUS_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemoteUser() <em>Remote User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteUser()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOTE_USER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRemoteUser() <em>Remote User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteUser()
	 * @generated
	 * @ordered
	 */
	protected String remoteUser = REMOTE_USER_EDEFAULT;

	/**
	 * The default value of the '{@link #getRemotePassword() <em>Remote Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemotePassword()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOTE_PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRemotePassword() <em>Remote Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemotePassword()
	 * @generated
	 * @ordered
	 */
	protected String remotePassword = REMOTE_PASSWORD_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitrepositoriesPackage.Literals.REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAtomLink> getLinks() {
		if (this.links == null) {
			this.links = new EObjectContainmentEList<IAtomLink>(IAtomLink.class, this, IAbapgitrepositoriesPackage.REPOSITORY__LINKS);
		}
		return this.links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKey() {
		return this.key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		String oldKey = this.key;
		this.key = newKey;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__KEY, oldKey, this.key));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPackage() {
		return this.package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackage(String newPackage) {
		String oldPackage = this.package_;
		this.package_ = newPackage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__PACKAGE, oldPackage, this.package_));
		}
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
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__URL, oldUrl, this.url));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getBranchName() {
		return this.branchName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBranchName(String newBranchName) {
		String oldBranchName = this.branchName;
		this.branchName = newBranchName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__BRANCH_NAME, oldBranchName, this.branchName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreatedBy(String newCreatedBy) {
		String oldCreatedBy = this.createdBy;
		this.createdBy = newCreatedBy;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__CREATED_BY, oldCreatedBy, this.createdBy));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreatedEmail() {
		return this.createdEmail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreatedEmail(String newCreatedEmail) {
		String oldCreatedEmail = this.createdEmail;
		this.createdEmail = newCreatedEmail;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__CREATED_EMAIL, oldCreatedEmail, this.createdEmail));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreatedAt(String newCreatedAt) {
		String oldCreatedAt = this.createdAt;
		this.createdAt = newCreatedAt;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__CREATED_AT, oldCreatedAt, this.createdAt));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDeserializedAt() {
		return this.deserializedAt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeserializedAt(String newDeserializedAt) {
		String oldDeserializedAt = this.deserializedAt;
		this.deserializedAt = newDeserializedAt;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_AT, oldDeserializedAt, this.deserializedAt));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDeserializedEmail() {
		return this.deserializedEmail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeserializedEmail(String newDeserializedEmail) {
		String oldDeserializedEmail = this.deserializedEmail;
		this.deserializedEmail = newDeserializedEmail;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_EMAIL, oldDeserializedEmail, this.deserializedEmail));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDeserializedBy() {
		return this.deserializedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeserializedBy(String newDeserializedBy) {
		String oldDeserializedBy = this.deserializedBy;
		this.deserializedBy = newDeserializedBy;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_BY, oldDeserializedBy, this.deserializedBy));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatus() {
		return this.status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatus(String newStatus) {
		String oldStatus = this.status;
		this.status = newStatus;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__STATUS, oldStatus, this.status));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStatusText() {
		return this.statusText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStatusText(String newStatusText) {
		String oldStatusText = this.statusText;
		this.statusText = newStatusText;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__STATUS_TEXT, oldStatusText, this.statusText));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRemoteUser() {
		return this.remoteUser;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRemoteUser(String newRemoteUser) {
		String oldRemoteUser = this.remoteUser;
		this.remoteUser = newRemoteUser;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_USER, oldRemoteUser, this.remoteUser));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRemotePassword() {
		return this.remotePassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRemotePassword(String newRemotePassword) {
		String oldRemotePassword = this.remotePassword;
		this.remotePassword = newRemotePassword;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_PASSWORD, oldRemotePassword, this.remotePassword));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTransportRequest() {
		return this.transportRequest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransportRequest(String newTransportRequest) {
		String oldTransportRequest = this.transportRequest;
		this.transportRequest = newTransportRequest;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitrepositoriesPackage.REPOSITORY__TRANSPORT_REQUEST, oldTransportRequest, this.transportRequest));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitrepositoriesPackage.REPOSITORY__LINKS:
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
			case IAbapgitrepositoriesPackage.REPOSITORY__LINKS:
				return getLinks();
			case IAbapgitrepositoriesPackage.REPOSITORY__KEY:
				return getKey();
			case IAbapgitrepositoriesPackage.REPOSITORY__PACKAGE:
				return getPackage();
			case IAbapgitrepositoriesPackage.REPOSITORY__URL:
				return getUrl();
			case IAbapgitrepositoriesPackage.REPOSITORY__BRANCH_NAME:
				return getBranchName();
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_BY:
				return getCreatedBy();
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_EMAIL:
				return getCreatedEmail();
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_AT:
				return getCreatedAt();
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_AT:
				return getDeserializedAt();
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_EMAIL:
				return getDeserializedEmail();
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_BY:
				return getDeserializedBy();
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS:
				return getStatus();
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS_TEXT:
				return getStatusText();
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_USER:
				return getRemoteUser();
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_PASSWORD:
				return getRemotePassword();
			case IAbapgitrepositoriesPackage.REPOSITORY__TRANSPORT_REQUEST:
				return getTransportRequest();
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
			case IAbapgitrepositoriesPackage.REPOSITORY__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends IAtomLink>)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__KEY:
				setKey((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__PACKAGE:
				setPackage((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__URL:
				setUrl((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__BRANCH_NAME:
				setBranchName((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_BY:
				setCreatedBy((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_EMAIL:
				setCreatedEmail((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_AT:
				setCreatedAt((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_AT:
				setDeserializedAt((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_EMAIL:
				setDeserializedEmail((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_BY:
				setDeserializedBy((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS:
				setStatus((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS_TEXT:
				setStatusText((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_USER:
				setRemoteUser((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_PASSWORD:
				setRemotePassword((String)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__TRANSPORT_REQUEST:
				setTransportRequest((String)newValue);
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
			case IAbapgitrepositoriesPackage.REPOSITORY__LINKS:
				getLinks().clear();
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__URL:
				setUrl(URL_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__BRANCH_NAME:
				setBranchName(BRANCH_NAME_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_BY:
				setCreatedBy(CREATED_BY_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_EMAIL:
				setCreatedEmail(CREATED_EMAIL_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_AT:
				setCreatedAt(CREATED_AT_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_AT:
				setDeserializedAt(DESERIALIZED_AT_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_EMAIL:
				setDeserializedEmail(DESERIALIZED_EMAIL_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_BY:
				setDeserializedBy(DESERIALIZED_BY_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS_TEXT:
				setStatusText(STATUS_TEXT_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_USER:
				setRemoteUser(REMOTE_USER_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_PASSWORD:
				setRemotePassword(REMOTE_PASSWORD_EDEFAULT);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORY__TRANSPORT_REQUEST:
				setTransportRequest(TRANSPORT_REQUEST_EDEFAULT);
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
			case IAbapgitrepositoriesPackage.REPOSITORY__LINKS:
				return this.links != null && !this.links.isEmpty();
			case IAbapgitrepositoriesPackage.REPOSITORY__KEY:
				return KEY_EDEFAULT == null ? this.key != null : !KEY_EDEFAULT.equals(this.key);
			case IAbapgitrepositoriesPackage.REPOSITORY__PACKAGE:
				return PACKAGE_EDEFAULT == null ? this.package_ != null : !PACKAGE_EDEFAULT.equals(this.package_);
			case IAbapgitrepositoriesPackage.REPOSITORY__URL:
				return URL_EDEFAULT == null ? this.url != null : !URL_EDEFAULT.equals(this.url);
			case IAbapgitrepositoriesPackage.REPOSITORY__BRANCH_NAME:
				return BRANCH_NAME_EDEFAULT == null ? this.branchName != null : !BRANCH_NAME_EDEFAULT.equals(this.branchName);
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_BY:
				return CREATED_BY_EDEFAULT == null ? this.createdBy != null : !CREATED_BY_EDEFAULT.equals(this.createdBy);
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_EMAIL:
				return CREATED_EMAIL_EDEFAULT == null ? this.createdEmail != null : !CREATED_EMAIL_EDEFAULT.equals(this.createdEmail);
			case IAbapgitrepositoriesPackage.REPOSITORY__CREATED_AT:
				return CREATED_AT_EDEFAULT == null ? this.createdAt != null : !CREATED_AT_EDEFAULT.equals(this.createdAt);
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_AT:
				return DESERIALIZED_AT_EDEFAULT == null ? this.deserializedAt != null : !DESERIALIZED_AT_EDEFAULT.equals(this.deserializedAt);
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_EMAIL:
				return DESERIALIZED_EMAIL_EDEFAULT == null ? this.deserializedEmail != null : !DESERIALIZED_EMAIL_EDEFAULT.equals(this.deserializedEmail);
			case IAbapgitrepositoriesPackage.REPOSITORY__DESERIALIZED_BY:
				return DESERIALIZED_BY_EDEFAULT == null ? this.deserializedBy != null : !DESERIALIZED_BY_EDEFAULT.equals(this.deserializedBy);
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS:
				return STATUS_EDEFAULT == null ? this.status != null : !STATUS_EDEFAULT.equals(this.status);
			case IAbapgitrepositoriesPackage.REPOSITORY__STATUS_TEXT:
				return STATUS_TEXT_EDEFAULT == null ? this.statusText != null : !STATUS_TEXT_EDEFAULT.equals(this.statusText);
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_USER:
				return REMOTE_USER_EDEFAULT == null ? this.remoteUser != null : !REMOTE_USER_EDEFAULT.equals(this.remoteUser);
			case IAbapgitrepositoriesPackage.REPOSITORY__REMOTE_PASSWORD:
				return REMOTE_PASSWORD_EDEFAULT == null ? this.remotePassword != null : !REMOTE_PASSWORD_EDEFAULT.equals(this.remotePassword);
			case IAbapgitrepositoriesPackage.REPOSITORY__TRANSPORT_REQUEST:
				return TRANSPORT_REQUEST_EDEFAULT == null ? this.transportRequest != null : !TRANSPORT_REQUEST_EDEFAULT.equals(this.transportRequest);
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
		result.append(" (key: ");
		result.append(this.key);
		result.append(", package: ");
		result.append(this.package_);
		result.append(", url: ");
		result.append(this.url);
		result.append(", branchName: ");
		result.append(this.branchName);
		result.append(", createdBy: ");
		result.append(this.createdBy);
		result.append(", createdEmail: ");
		result.append(this.createdEmail);
		result.append(", createdAt: ");
		result.append(this.createdAt);
		result.append(", deserializedAt: ");
		result.append(this.deserializedAt);
		result.append(", deserializedEmail: ");
		result.append(this.deserializedEmail);
		result.append(", deserializedBy: ");
		result.append(this.deserializedBy);
		result.append(", status: ");
		result.append(this.status);
		result.append(", statusText: ");
		result.append(this.statusText);
		result.append(", remoteUser: ");
		result.append(this.remoteUser);
		result.append(", remotePassword: ");
		result.append(this.remotePassword);
		result.append(", transportRequest: ");
		result.append(this.transportRequest);
		result.append(')');
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.branchName == null) ? 0 : this.branchName.hashCode());
		result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
		result = prime * result + ((this.createdBy == null) ? 0 : this.createdBy.hashCode());
		result = prime * result + ((this.createdEmail == null) ? 0 : this.createdEmail.hashCode());
		result = prime * result + ((this.deserializedAt == null) ? 0 : this.deserializedAt.hashCode());
		result = prime * result + ((this.deserializedBy == null) ? 0 : this.deserializedBy.hashCode());
		result = prime * result + ((this.deserializedEmail == null) ? 0 : this.deserializedEmail.hashCode());
		result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
		result = prime * result + ((this.links == null) ? 0 : this.links.hashCode());
		result = prime * result + ((this.package_ == null) ? 0 : this.package_.hashCode());
		result = prime * result + ((this.remotePassword == null) ? 0 : this.remotePassword.hashCode());
		result = prime * result + ((this.remoteUser == null) ? 0 : this.remoteUser.hashCode());
		result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
		result = prime * result + ((this.statusText == null) ? 0 : this.statusText.hashCode());
		result = prime * result + ((this.transportRequest == null) ? 0 : this.transportRequest.hashCode());
		result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
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
		RepositoryImpl other = (RepositoryImpl) obj;
		if (this.branchName == null) {
			if (other.branchName != null) {
				return false;
			}
		} else if (!this.branchName.equals(other.branchName)) {
			return false;
		}
		if (this.createdAt == null) {
			if (other.createdAt != null) {
				return false;
			}
		} else if (!this.createdAt.equals(other.createdAt)) {
			return false;
		}
		if (this.createdBy == null) {
			if (other.createdBy != null) {
				return false;
			}
		} else if (!this.createdBy.equals(other.createdBy)) {
			return false;
		}
		if (this.createdEmail == null) {
			if (other.createdEmail != null) {
				return false;
			}
		} else if (!this.createdEmail.equals(other.createdEmail)) {
			return false;
		}
		if (this.deserializedAt == null) {
			if (other.deserializedAt != null) {
				return false;
			}
		} else if (!this.deserializedAt.equals(other.deserializedAt)) {
			return false;
		}
		if (this.deserializedBy == null) {
			if (other.deserializedBy != null) {
				return false;
			}
		} else if (!this.deserializedBy.equals(other.deserializedBy)) {
			return false;
		}
		if (this.deserializedEmail == null) {
			if (other.deserializedEmail != null) {
				return false;
			}
		} else if (!this.deserializedEmail.equals(other.deserializedEmail)) {
			return false;
		}
		if (this.key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!this.key.equals(other.key)) {
			return false;
		}
		if (this.links == null) {
			if (other.links != null) {
				return false;
			}
		} else if (!this.links.equals(other.links)) {
			return false;
		}
		if (this.package_ == null) {
			if (other.package_ != null) {
				return false;
			}
		} else if (!this.package_.equals(other.package_)) {
			return false;
		}
		if (this.remotePassword == null) {
			if (other.remotePassword != null) {
				return false;
			}
		} else if (!this.remotePassword.equals(other.remotePassword)) {
			return false;
		}
		if (this.remoteUser == null) {
			if (other.remoteUser != null) {
				return false;
			}
		} else if (!this.remoteUser.equals(other.remoteUser)) {
			return false;
		}
		if (this.status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!this.status.equals(other.status)) {
			return false;
		}
		if (this.statusText == null) {
			if (other.statusText != null) {
				return false;
			}
		} else if (!this.statusText.equals(other.statusText)) {
			return false;
		}
		if (this.transportRequest == null) {
			if (other.transportRequest != null) {
				return false;
			}
		} else if (!this.transportRequest.equals(other.transportRequest)) {
			return false;
		}
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		return true;
	}

} //RepositoryImpl
