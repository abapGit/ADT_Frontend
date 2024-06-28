/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.util;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage
 * @generated
 */
public class AgitpullmodifiedobjectsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IAgitpullmodifiedobjectsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgitpullmodifiedobjectsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IAgitpullmodifiedobjectsPackage.eINSTANCE;
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
	protected AgitpullmodifiedobjectsSwitch<Adapter> modelSwitch =
		new AgitpullmodifiedobjectsSwitch<Adapter>() {
			@Override
			public Adapter caseAbapGitPullModifiedObjects(IAbapGitPullModifiedObjects object) {
				return createAbapGitPullModifiedObjectsAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(IDocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseOverwriteObjects(IOverwriteObjects object) {
				return createOverwriteObjectsAdapter();
			}
			@Override
			public Adapter casePackageWarningObjects(IPackageWarningObjects object) {
				return createPackageWarningObjectsAdapter();
			}
			@Override
			public Adapter caseOverwriteObject(IOverwriteObject object) {
				return createOverwriteObjectAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects <em>Abap Git Pull Modified Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects
	 * @generated
	 */
	public Adapter createAbapGitPullModifiedObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects <em>Overwrite Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects
	 * @generated
	 */
	public Adapter createOverwriteObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects <em>Package Warning Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects
	 * @generated
	 */
	public Adapter createPackageWarningObjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject <em>Overwrite Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject
	 * @generated
	 */
	public Adapter createOverwriteObjectAdapter() {
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

} //AgitpullmodifiedobjectsAdapterFactory
