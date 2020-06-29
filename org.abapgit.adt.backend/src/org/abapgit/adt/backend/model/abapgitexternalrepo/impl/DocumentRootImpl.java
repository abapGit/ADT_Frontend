/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.impl;

import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl#getExternalRepoInfo <em>External Repo Info</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl#getExternalRepoInfoRequest <em>External Repo Info Request</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocumentRootImpl extends MinimalEObjectImpl.Container implements IDocumentRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IExternalRepositoryInfo getExternalRepoInfo() {
		return (IExternalRepositoryInfo)getMixed().get(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExternalRepoInfo(IExternalRepositoryInfo newExternalRepoInfo, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO, newExternalRepoInfo, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExternalRepoInfo(IExternalRepositoryInfo newExternalRepoInfo) {
		((FeatureMap.Internal)getMixed()).set(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO, newExternalRepoInfo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IExternalRepositoryInfoRequest getExternalRepoInfoRequest() {
		return (IExternalRepositoryInfoRequest)getMixed().get(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExternalRepoInfoRequest(IExternalRepositoryInfoRequest newExternalRepoInfoRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST, newExternalRepoInfoRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExternalRepoInfoRequest(IExternalRepositoryInfoRequest newExternalRepoInfoRequest) {
		((FeatureMap.Internal)getMixed()).set(IAbapgitexternalrepoPackage.Literals.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST, newExternalRepoInfoRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO:
				return basicSetExternalRepoInfo(null, msgs);
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST:
				return basicSetExternalRepoInfoRequest(null, msgs);
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
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO:
				return getExternalRepoInfo();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST:
				return getExternalRepoInfoRequest();
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
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO:
				setExternalRepoInfo((IExternalRepositoryInfo)newValue);
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST:
				setExternalRepoInfoRequest((IExternalRepositoryInfoRequest)newValue);
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
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO:
				setExternalRepoInfo((IExternalRepositoryInfo)null);
				return;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST:
				setExternalRepoInfoRequest((IExternalRepositoryInfoRequest)null);
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
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO:
				return getExternalRepoInfo() != null;
			case IAbapgitexternalrepoPackage.DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST:
				return getExternalRepoInfoRequest() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
