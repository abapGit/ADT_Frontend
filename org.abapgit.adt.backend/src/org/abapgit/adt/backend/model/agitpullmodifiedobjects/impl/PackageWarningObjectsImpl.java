/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package Warning Objects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.PackageWarningObjectsImpl#getAbapgitobjects <em>Abapgitobjects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageWarningObjectsImpl extends MinimalEObjectImpl.Container implements IPackageWarningObjects {
	/**
	 * The cached value of the '{@link #getAbapgitobjects() <em>Abapgitobjects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbapgitobjects()
	 * @generated
	 * @ordered
	 */
	protected EList<IOverwriteObject> abapgitobjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageWarningObjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAgitpullmodifiedobjectsPackage.Literals.PACKAGE_WARNING_OBJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IOverwriteObject> getAbapgitobjects() {
		if (abapgitobjects == null) {
			abapgitobjects = new EObjectContainmentEList<IOverwriteObject>(IOverwriteObject.class, this, IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS);
		}
		return abapgitobjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS:
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
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS:
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
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS:
				getAbapgitobjects().clear();
				getAbapgitobjects().addAll((Collection<? extends IOverwriteObject>)newValue);
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
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS:
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
			case IAgitpullmodifiedobjectsPackage.PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS:
				return abapgitobjects != null && !abapgitobjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PackageWarningObjectsImpl
