/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import java.util.Collection;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Staged Objects</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.StagedObjectsImpl#getAbapgitobject <em>Abapgitobject</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StagedObjectsImpl extends MinimalEObjectImpl.Container implements IStagedObjects {
	/**
	 * The cached value of the '{@link #getAbapgitobject() <em>Abapgitobject</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbapgitobject()
	 * @generated
	 * @ordered
	 */
	protected EList<IAbapGitObject> abapgitobject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StagedObjectsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingPackage.Literals.STAGED_OBJECTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAbapGitObject> getAbapgitobject() {
		if (abapgitobject == null) {
			abapgitobject = new EObjectContainmentEList<IAbapGitObject>(IAbapGitObject.class, this, IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT);
		}
		return abapgitobject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT:
				return ((InternalEList<?>)getAbapgitobject()).basicRemove(otherEnd, msgs);
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
			case IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT:
				return getAbapgitobject();
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
			case IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT:
				getAbapgitobject().clear();
				getAbapgitobject().addAll((Collection<? extends IAbapGitObject>)newValue);
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
			case IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT:
				getAbapgitobject().clear();
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
			case IAbapgitstagingPackage.STAGED_OBJECTS__ABAPGITOBJECT:
				return abapgitobject != null && !abapgitobject.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StagedObjectsImpl
