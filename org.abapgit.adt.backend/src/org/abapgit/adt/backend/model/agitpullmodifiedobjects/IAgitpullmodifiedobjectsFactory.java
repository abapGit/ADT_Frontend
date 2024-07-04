/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage
 * @generated
 */
public interface IAgitpullmodifiedobjectsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAgitpullmodifiedobjectsFactory eINSTANCE = org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Abap Git Pull Modified Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abap Git Pull Modified Objects</em>'.
	 * @generated
	 */
	IAbapGitPullModifiedObjects createAbapGitPullModifiedObjects();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	IDocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Overwrite Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Overwrite Objects</em>'.
	 * @generated
	 */
	IOverwriteObjects createOverwriteObjects();

	/**
	 * Returns a new object of class '<em>Package Warning Objects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Warning Objects</em>'.
	 * @generated
	 */
	IPackageWarningObjects createPackageWarningObjects();

	/**
	 * Returns a new object of class '<em>Overwrite Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Overwrite Object</em>'.
	 * @generated
	 */
	IOverwriteObject createOverwriteObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IAgitpullmodifiedobjectsPackage getAgitpullmodifiedobjectsPackage();

} //IAgitpullmodifiedobjectsFactory
