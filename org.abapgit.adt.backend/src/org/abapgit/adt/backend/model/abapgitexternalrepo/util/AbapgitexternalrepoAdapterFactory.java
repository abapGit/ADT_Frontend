/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.util;

import org.abapgit.adt.backend.model.abapgitexternalrepo.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage
 * @generated
 */
public class AbapgitexternalrepoAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IAbapgitexternalrepoPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitexternalrepoAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IAbapgitexternalrepoPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapgitexternalrepoSwitch<Adapter> modelSwitch =
		new AbapgitexternalrepoSwitch<Adapter>() {
			@Override
			public Adapter caseBranch(IBranch object) {
				return createBranchAdapter();
			}
			@Override
			public Adapter caseExternalRepositoryInfo(IExternalRepositoryInfo object) {
				return createExternalRepositoryInfoAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(IDocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseExternalRepositoryInfoRequest(IExternalRepositoryInfoRequest object) {
				return createExternalRepositoryInfoRequestAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch
	 * @generated
	 */
	public Adapter createBranchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo <em>External Repository Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo
	 * @generated
	 */
	public Adapter createExternalRepositoryInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest <em>External Repository Info Request</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest
	 * @generated
	 */
	public Adapter createExternalRepositoryInfoRequestAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AbapgitexternalrepoAdapterFactory
