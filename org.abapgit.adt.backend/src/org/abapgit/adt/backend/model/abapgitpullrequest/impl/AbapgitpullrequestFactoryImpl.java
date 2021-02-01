/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest.impl;

import org.abapgit.adt.backend.model.abapgitpullrequest.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitpullrequestFactoryImpl extends EFactoryImpl implements IAbapgitpullrequestFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAbapgitpullrequestFactory init() {
		try {
			IAbapgitpullrequestFactory theAbapgitpullrequestFactory = (IAbapgitpullrequestFactory)EPackage.Registry.INSTANCE.getEFactory(IAbapgitpullrequestPackage.eNS_URI);
			if (theAbapgitpullrequestFactory != null) {
				return theAbapgitpullrequestFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AbapgitpullrequestFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitpullrequestFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IAbapgitpullrequestPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case IAbapgitpullrequestPackage.ABAP_GIT_PULL_REQUEST: return createAbapGitPullRequest();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapGitPullRequest createAbapGitPullRequest() {
		AbapGitPullRequestImpl abapGitPullRequest = new AbapGitPullRequestImpl();
		return abapGitPullRequest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapgitpullrequestPackage getAbapgitpullrequestPackage() {
		return (IAbapgitpullrequestPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAbapgitpullrequestPackage getPackage() {
		return IAbapgitpullrequestPackage.eINSTANCE;
	}

} //AbapgitpullrequestFactoryImpl
