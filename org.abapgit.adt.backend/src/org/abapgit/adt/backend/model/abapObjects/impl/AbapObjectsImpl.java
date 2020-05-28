/**
 */
package org.abapgit.adt.backend.model.abapObjects.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjectsPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Objects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapObjects.impl.AbapObjectsImpl#getAbapObjects <em>Abap Objects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapObjectsImpl extends MinimalEObjectImpl.Container implements IAbapObjects {
	/**
	 * The cached value of the '{@link #getAbapObjects() <em>Abap Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbapObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbapObject> abapObjects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapObjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapObjectsPackage.Literals.ABAP_OBJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAbapObject> getAbapObjects() {
		if (abapObjects == null) {
			abapObjects = new EObjectContainmentEList<IAbapObject>(IAbapObject.class, this, IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS);
		}
		return abapObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS:
				return ((InternalEList<?>)getAbapObjects()).basicRemove(otherEnd, msgs);
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
			case IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS:
				return getAbapObjects();
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
			case IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS:
				getAbapObjects().clear();
				getAbapObjects().addAll((Collection<? extends IAbapObject>)newValue);
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
			case IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS:
				getAbapObjects().clear();
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
			case IAbapObjectsPackage.ABAP_OBJECTS__ABAP_OBJECTS:
				return abapObjects != null && !abapObjects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbapObjectsImpl
