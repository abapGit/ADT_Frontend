/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.util;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

import org.abapgit.adt.backend.model.abapgitstaging.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage
 * @generated
 */
public class AbapgitstagingAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IAbapgitstagingPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IAbapgitstagingPackage.eINSTANCE;
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
	protected AbapgitstagingSwitch<Adapter> modelSwitch =
		new AbapgitstagingSwitch<Adapter>() {
			@Override
			public Adapter caseAbapGitStaging(IAbapGitStaging object) {
				return createAbapGitStagingAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(IDocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseAbapGitCommitMessage(IAbapGitCommitMessage object) {
				return createAbapGitCommitMessageAdapter();
			}
			@Override
			public Adapter caseAbapGitObject(IAbapGitObject object) {
				return createAbapGitObjectAdapter();
			}
			@Override
			public Adapter caseAbapGitFile(IAbapGitFile object) {
				return createAbapGitFileAdapter();
			}
			@Override
			public Adapter caseAuthor(IAuthor object) {
				return createAuthorAdapter();
			}
			@Override
			public Adapter caseCommitter(ICommitter object) {
				return createCommitterAdapter();
			}
			@Override
			public Adapter caseUnstagedObjects(IUnstagedObjects object) {
				return createUnstagedObjectsAdapter();
			}
			@Override
			public Adapter caseStagedObjects(IStagedObjects object) {
				return createStagedObjectsAdapter();
			}
			@Override
			public Adapter caseIgnoredObjects(IIgnoredObjects object) {
				return createIgnoredObjectsAdapter();
			}
			@Override
			public Adapter caseTransport(ITransport object) {
				return createTransportAdapter();
			}
			@Override
			public Adapter caseAdtObjectReference(IAdtObjectReference object) {
				return createAdtObjectReferenceAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging <em>Abap Git Staging</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging
	 * @generated
	 */
	public Adapter createAbapGitStagingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage <em>Abap Git Commit Message</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage
	 * @generated
	 */
	public Adapter createAbapGitCommitMessageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject <em>Abap Git Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject
	 * @generated
	 */
	public Adapter createAbapGitObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile <em>Abap Git File</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile
	 * @generated
	 */
	public Adapter createAbapGitFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAuthor
	 * @generated
	 */
	public Adapter createAuthorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.ICommitter <em>Committer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ICommitter
	 * @generated
	 */
	public Adapter createCommitterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects <em>Unstaged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects
	 * @generated
	 */
	public Adapter createUnstagedObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects <em>Staged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects
	 * @generated
	 */
	public Adapter createStagedObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects <em>Ignored Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects
	 * @generated
	 */
	public Adapter createIgnoredObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.abapgitstaging.ITransport <em>Transport</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ITransport
	 * @generated
	 */
	public Adapter createTransportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.sap.adt.tools.core.model.adtcore.IAdtObjectReference <em>Adt Object Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.sap.adt.tools.core.model.adtcore.IAdtObjectReference
	 * @generated
	 */
	public Adapter createAdtObjectReferenceAdapter() {
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

} //AbapgitstagingAdapterFactory
