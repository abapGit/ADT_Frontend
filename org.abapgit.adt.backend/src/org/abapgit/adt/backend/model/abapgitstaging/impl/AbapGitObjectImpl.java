/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.sap.adt.tools.core.model.adtcore.impl.AdtObjectReferenceImpl;
import com.sap.adt.tools.core.model.atom.IAtomLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Git Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl#getWbkey <em>Wbkey</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl#getFiles <em>Files</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitObjectImpl extends AdtObjectReferenceImpl implements IAbapGitObject {
	/**
	 * The default value of the '{@link #getWbkey() <em>Wbkey</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWbkey()
	 * @generated
	 * @ordered
	 */
	protected static final String WBKEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWbkey() <em>Wbkey</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWbkey()
	 * @generated
	 * @ordered
	 */
	protected String wbkey = WBKEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFiles() <em>Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbapGitFile> files;

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
	protected AbapGitObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingPackage.Literals.ABAP_GIT_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWbkey() {
		return wbkey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWbkey(String newWbkey) {
		String oldWbkey = wbkey;
		wbkey = newWbkey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_OBJECT__WBKEY, oldWbkey, wbkey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_OBJECT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAbapGitFile> getFiles() {
		if (files == null) {
			files = new EObjectContainmentEList<IAbapGitFile>(IAbapGitFile.class, this, IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES);
		}
		return files;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAtomLink> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<IAtomLink>(IAtomLink.class, this, IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS);
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
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES:
				return ((InternalEList<?>)getFiles()).basicRemove(otherEnd, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS:
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
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__WBKEY:
				return getWbkey();
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__VERSION:
				return getVersion();
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES:
				return getFiles();
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS:
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
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__WBKEY:
				setWbkey((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__VERSION:
				setVersion((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES:
				getFiles().clear();
				getFiles().addAll((Collection<? extends IAbapGitFile>)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS:
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
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__WBKEY:
				setWbkey(WBKEY_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES:
				getFiles().clear();
				return;
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS:
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
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__WBKEY:
				return WBKEY_EDEFAULT == null ? wbkey != null : !WBKEY_EDEFAULT.equals(wbkey);
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__FILES:
				return files != null && !files.isEmpty();
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT__LINKS:
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
		result.append(" (wbkey: ");
		result.append(wbkey);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IAbapGitObject) {
			IAbapGitObject object = (IAbapGitObject) obj;
			if (this.getType() != null && object.getType() != null && this.getUri() != null && object.getUri() != null) {
				if ((this.getName().equals(object.getName())) && (this.getType().equals(object.getType()))
						&& (this.getUri().equals(object.getUri()))) {
					return true;
				}
			}
			//sepcial handling for "non-code and meta files" node
			if (this.getName() != null && object.getName() != null && this.getType() == null && object.getType() == null) {
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
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		result = prime * result + ((this.uri == null) ? 0 : this.uri.hashCode());
		return result;
	}

} //AbapGitObjectImpl
