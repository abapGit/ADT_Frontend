/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage
 * @generated
 */
public interface IAbapgitstagingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitstagingFactory eINSTANCE = org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Abap Git Staging</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Staging</em>'.
	 * @generated
	 */
	IAbapGitStaging createAbapGitStaging();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	IDocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Abap Git Commit Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Commit Message</em>'.
	 * @generated
	 */
	IAbapGitCommitMessage createAbapGitCommitMessage();

	/**
	 * Returns a new object of class '<em>Abap Git Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Object</em>'.
	 * @generated
	 */
	IAbapGitObject createAbapGitObject();

	/**
	 * Returns a new object of class '<em>Abap Git File</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git File</em>'.
	 * @generated
	 */
	IAbapGitFile createAbapGitFile();

	/**
	 * Returns a new object of class '<em>Author</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Author</em>'.
	 * @generated
	 */
	IAuthor createAuthor();

	/**
	 * Returns a new object of class '<em>Committer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Committer</em>'.
	 * @generated
	 */
	ICommitter createCommitter();

	/**
	 * Returns a new object of class '<em>Unstaged Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unstaged Objects</em>'.
	 * @generated
	 */
	IUnstagedObjects createUnstagedObjects();

	/**
	 * Returns a new object of class '<em>Staged Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Staged Objects</em>'.
	 * @generated
	 */
	IStagedObjects createStagedObjects();

	/**
	 * Returns a new object of class '<em>Ignored Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ignored Objects</em>'.
	 * @generated
	 */
	IIgnoredObjects createIgnoredObjects();

	/**
	 * Returns a new object of class '<em>Transport</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transport</em>'.
	 * @generated
	 */
	ITransport createTransport();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IAbapgitstagingPackage getAbapgitstagingPackage();

} //IAbapgitstagingFactory
