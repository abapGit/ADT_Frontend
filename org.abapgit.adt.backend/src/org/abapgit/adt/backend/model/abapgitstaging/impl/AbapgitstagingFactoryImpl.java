/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import org.abapgit.adt.backend.model.abapgitstaging.*;

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
public class AbapgitstagingFactoryImpl extends EFactoryImpl implements IAbapgitstagingFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IAbapgitstagingFactory init() {
		try {
			IAbapgitstagingFactory theAbapgitstagingFactory = (IAbapgitstagingFactory)EPackage.Registry.INSTANCE.getEFactory(IAbapgitstagingPackage.eNS_URI);
			if (theAbapgitstagingFactory != null) {
				return theAbapgitstagingFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AbapgitstagingFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingFactoryImpl() {
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
			case IAbapgitstagingPackage.ABAP_GIT_STAGING: return createAbapGitStaging();
			case IAbapgitstagingPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE: return createAbapGitCommitMessage();
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT: return createAbapGitObject();
			case IAbapgitstagingPackage.ABAP_GIT_FILE: return createAbapGitFile();
			case IAbapgitstagingPackage.AUTHOR: return createAuthor();
			case IAbapgitstagingPackage.COMMITTER: return createCommitter();
			case IAbapgitstagingPackage.UNSTAGED_OBJECTS: return createUnstagedObjects();
			case IAbapgitstagingPackage.STAGED_OBJECTS: return createStagedObjects();
			case IAbapgitstagingPackage.IGNORED_OBJECTS: return createIgnoredObjects();
			case IAbapgitstagingPackage.TRANSPORT: return createTransport();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapGitStaging createAbapGitStaging() {
		AbapGitStagingImpl abapGitStaging = new AbapGitStagingImpl();
		return abapGitStaging;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapGitCommitMessage createAbapGitCommitMessage() {
		AbapGitCommitMessageImpl abapGitCommitMessage = new AbapGitCommitMessageImpl();
		return abapGitCommitMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapGitObject createAbapGitObject() {
		AbapGitObjectImpl abapGitObject = new AbapGitObjectImpl();
		return abapGitObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapGitFile createAbapGitFile() {
		AbapGitFileImpl abapGitFile = new AbapGitFileImpl();
		return abapGitFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAuthor createAuthor() {
		AuthorImpl author = new AuthorImpl();
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICommitter createCommitter() {
		CommitterImpl committer = new CommitterImpl();
		return committer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IUnstagedObjects createUnstagedObjects() {
		UnstagedObjectsImpl unstagedObjects = new UnstagedObjectsImpl();
		return unstagedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IStagedObjects createStagedObjects() {
		StagedObjectsImpl stagedObjects = new StagedObjectsImpl();
		return stagedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IIgnoredObjects createIgnoredObjects() {
		IgnoredObjectsImpl ignoredObjects = new IgnoredObjectsImpl();
		return ignoredObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITransport createTransport() {
		TransportImpl transport = new TransportImpl();
		return transport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapgitstagingPackage getAbapgitstagingPackage() {
		return (IAbapgitstagingPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IAbapgitstagingPackage getPackage() {
		return IAbapgitstagingPackage.eINSTANCE;
	}

} //AbapgitstagingFactoryImpl
