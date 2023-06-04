/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.impl;

import java.util.Collection;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
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
 * An implementation of the model object '<em><b>Branch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getSha1 <em>Sha1</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getIsHead <em>Is Head</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl#getFolderLogic <em>Folder Logic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BranchImpl extends MinimalEObjectImpl.Container implements IBranch {
	/**
	 * The default value of the '{@link #getSha1() <em>Sha1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSha1()
	 * @generated
	 * @ordered
	 */
	protected static final String SHA1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSha1() <em>Sha1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSha1()
	 * @generated
	 * @ordered
	 */
	protected String sha1 = SHA1_EDEFAULT;

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
	 * The default value of the '{@link #getIsHead() <em>Is Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsHead()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_HEAD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsHead() <em>Is Head</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsHead()
	 * @generated
	 * @ordered
	 */
	protected String isHead = IS_HEAD_EDEFAULT;

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
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<com.sap.adt.tools.core.model.atom.IAtomLink> links;

	/**
	 * The default value of the '{@link #getFolderLogic() <em>Folder Logic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolderLogic()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_LOGIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolderLogic() <em>Folder Logic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolderLogic()
	 * @generated
	 * @ordered
	 */
	protected String folderLogic = FOLDER_LOGIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitexternalrepoPackage.Literals.BRANCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSha1() {
		return sha1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSha1(String newSha1) {
		String oldSha1 = sha1;
		sha1 = newSha1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__SHA1, oldSha1, sha1));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIsHead() {
		return isHead;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsHead(String newIsHead) {
		String oldIsHead = isHead;
		isHead = newIsHead;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__IS_HEAD, oldIsHead, isHead));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<com.sap.adt.tools.core.model.atom.IAtomLink> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<com.sap.adt.tools.core.model.atom.IAtomLink>(com.sap.adt.tools.core.model.atom.IAtomLink.class, this, IAbapgitexternalrepoPackage.BRANCH__LINKS);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getFolderLogic() {
		return folderLogic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFolderLogic(String newFolderLogic) {
		String oldFolderLogic = folderLogic;
		folderLogic = newFolderLogic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitexternalrepoPackage.BRANCH__FOLDER_LOGIC, oldFolderLogic, folderLogic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitexternalrepoPackage.BRANCH__LINKS:
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
			case IAbapgitexternalrepoPackage.BRANCH__SHA1:
				return getSha1();
			case IAbapgitexternalrepoPackage.BRANCH__NAME:
				return getName();
			case IAbapgitexternalrepoPackage.BRANCH__IS_HEAD:
				return getIsHead();
			case IAbapgitexternalrepoPackage.BRANCH__TYPE:
				return getType();
			case IAbapgitexternalrepoPackage.BRANCH__DISPLAY_NAME:
				return getDisplayName();
			case IAbapgitexternalrepoPackage.BRANCH__LINKS:
				return getLinks();
			case IAbapgitexternalrepoPackage.BRANCH__FOLDER_LOGIC:
				return getFolderLogic();
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
			case IAbapgitexternalrepoPackage.BRANCH__SHA1:
				setSha1((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__NAME:
				setName((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__IS_HEAD:
				setIsHead((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__TYPE:
				setType((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends com.sap.adt.tools.core.model.atom.IAtomLink>)newValue);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__FOLDER_LOGIC:
				setFolderLogic((String)newValue);
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
			case IAbapgitexternalrepoPackage.BRANCH__SHA1:
				setSha1(SHA1_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__IS_HEAD:
				setIsHead(IS_HEAD_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case IAbapgitexternalrepoPackage.BRANCH__LINKS:
				getLinks().clear();
				return;
			case IAbapgitexternalrepoPackage.BRANCH__FOLDER_LOGIC:
				setFolderLogic(FOLDER_LOGIC_EDEFAULT);
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
			case IAbapgitexternalrepoPackage.BRANCH__SHA1:
				return SHA1_EDEFAULT == null ? sha1 != null : !SHA1_EDEFAULT.equals(sha1);
			case IAbapgitexternalrepoPackage.BRANCH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IAbapgitexternalrepoPackage.BRANCH__IS_HEAD:
				return IS_HEAD_EDEFAULT == null ? isHead != null : !IS_HEAD_EDEFAULT.equals(isHead);
			case IAbapgitexternalrepoPackage.BRANCH__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case IAbapgitexternalrepoPackage.BRANCH__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case IAbapgitexternalrepoPackage.BRANCH__LINKS:
				return links != null && !links.isEmpty();
			case IAbapgitexternalrepoPackage.BRANCH__FOLDER_LOGIC:
				return FOLDER_LOGIC_EDEFAULT == null ? folderLogic != null : !FOLDER_LOGIC_EDEFAULT.equals(folderLogic);
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
		result.append(" (sha1: ");
		result.append(sha1);
		result.append(", name: ");
		result.append(name);
		result.append(", isHead: ");
		result.append(isHead);
		result.append(", type: ");
		result.append(type);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", folderLogic: ");
		result.append(folderLogic);
		result.append(')');
		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.displayName == null) ? 0 : this.displayName.hashCode());
		result = prime * result + ((this.isHead == null) ? 0 : this.isHead.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.sha1 == null) ? 0 : this.sha1.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
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
		BranchImpl other = (BranchImpl) obj;
		if (this.displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!this.displayName.equals(other.displayName)) {
			return false;
		}
		if (this.isHead == null) {
			if (other.isHead != null) {
				return false;
			}
		} else if (!this.isHead.equals(other.isHead)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.sha1 == null) {
			if (other.sha1 != null) {
				return false;
			}
		} else if (!this.sha1.equals(other.sha1)) {
			return false;
		}
		if (this.type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!this.type.equals(other.type)) {
			return false;
		}
		return true;
	}

} //BranchImpl
