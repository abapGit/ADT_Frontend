/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories.impl;

import com.sap.adt.tools.core.model.atom.IAtomLink;
import java.util.Collection;

import org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesPackage;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepositories;
import org.abapgit.adt.backend.model.abapgitrepositories.IRepository;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repositories</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepositoriesImpl extends MinimalEObjectImpl.Container implements IRepositories {
	/**
	 * The cached value of the '{@link #getRepositories() <em>Repositories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositories()
	 * @generated
	 * @ordered
	 */
	protected EList<IRepository> repositories;

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
	protected RepositoriesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitrepositoriesPackage.Literals.REPOSITORIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IRepository> getRepositories() {
		if (repositories == null) {
			repositories = new EObjectContainmentEList<IRepository>(IRepository.class, this, IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES);
		}
		return repositories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IAtomLink> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<IAtomLink>(IAtomLink.class, this, IAbapgitrepositoriesPackage.REPOSITORIES__LINKS);
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
			case IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES:
				return ((InternalEList<?>)getRepositories()).basicRemove(otherEnd, msgs);
			case IAbapgitrepositoriesPackage.REPOSITORIES__LINKS:
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
			case IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES:
				return getRepositories();
			case IAbapgitrepositoriesPackage.REPOSITORIES__LINKS:
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
			case IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES:
				getRepositories().clear();
				getRepositories().addAll((Collection<? extends IRepository>)newValue);
				return;
			case IAbapgitrepositoriesPackage.REPOSITORIES__LINKS:
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
			case IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES:
				getRepositories().clear();
				return;
			case IAbapgitrepositoriesPackage.REPOSITORIES__LINKS:
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
			case IAbapgitrepositoriesPackage.REPOSITORIES__REPOSITORIES:
				return repositories != null && !repositories.isEmpty();
			case IAbapgitrepositoriesPackage.REPOSITORIES__LINKS:
				return links != null && !links.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RepositoriesImpl
