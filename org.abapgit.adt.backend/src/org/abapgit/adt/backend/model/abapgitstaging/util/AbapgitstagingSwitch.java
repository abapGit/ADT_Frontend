/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.util;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

import org.abapgit.adt.backend.model.abapgitstaging.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage
 * @generated
 */
public class AbapgitstagingSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IAbapgitstagingPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbapgitstagingSwitch() {
		if (modelPackage == null) {
			modelPackage = IAbapgitstagingPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING: {
				IAbapGitStaging abapGitStaging = (IAbapGitStaging)theEObject;
				T result = caseAbapGitStaging(abapGitStaging);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.DOCUMENT_ROOT: {
				IDocumentRoot documentRoot = (IDocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE: {
				IAbapGitCommitMessage abapGitCommitMessage = (IAbapGitCommitMessage)theEObject;
				T result = caseAbapGitCommitMessage(abapGitCommitMessage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.ABAP_GIT_OBJECT: {
				IAbapGitObject abapGitObject = (IAbapGitObject)theEObject;
				T result = caseAbapGitObject(abapGitObject);
				if (result == null) result = caseAdtObjectReference(abapGitObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.ABAP_GIT_FILE: {
				IAbapGitFile abapGitFile = (IAbapGitFile)theEObject;
				T result = caseAbapGitFile(abapGitFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.AUTHOR: {
				IAuthor author = (IAuthor)theEObject;
				T result = caseAuthor(author);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.COMMITTER: {
				ICommitter committer = (ICommitter)theEObject;
				T result = caseCommitter(committer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.UNSTAGED_OBJECTS: {
				IUnstagedObjects unstagedObjects = (IUnstagedObjects)theEObject;
				T result = caseUnstagedObjects(unstagedObjects);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.STAGED_OBJECTS: {
				IStagedObjects stagedObjects = (IStagedObjects)theEObject;
				T result = caseStagedObjects(stagedObjects);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.IGNORED_OBJECTS: {
				IIgnoredObjects ignoredObjects = (IIgnoredObjects)theEObject;
				T result = caseIgnoredObjects(ignoredObjects);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IAbapgitstagingPackage.TRANSPORT: {
				ITransport transport = (ITransport)theEObject;
				T result = caseTransport(transport);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abap Git Staging</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abap Git Staging</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbapGitStaging(IAbapGitStaging object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(IDocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abap Git Commit Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abap Git Commit Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbapGitCommitMessage(IAbapGitCommitMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abap Git Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abap Git Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbapGitObject(IAbapGitObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abap Git File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abap Git File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbapGitFile(IAbapGitFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Author</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Author</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthor(IAuthor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Committer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Committer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommitter(ICommitter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unstaged Objects</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unstaged Objects</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnstagedObjects(IUnstagedObjects object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Staged Objects</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Staged Objects</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStagedObjects(IStagedObjects object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ignored Objects</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ignored Objects</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIgnoredObjects(IIgnoredObjects object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transport</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transport</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransport(ITransport object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adt Object Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adt Object Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdtObjectReference(IAdtObjectReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //AbapgitstagingSwitch
