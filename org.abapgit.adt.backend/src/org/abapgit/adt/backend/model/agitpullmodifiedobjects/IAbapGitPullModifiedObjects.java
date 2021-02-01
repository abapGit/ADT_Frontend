/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git Pull Modified Objects</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getOverwriteObjects <em>Overwrite Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getPackageWarningObjects <em>Package Warning Objects</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getAbapGitPullModifiedObjects()
 * @model extendedMetaData="kind='elementOnly' name='abapgitpullmodifiedobjects'"
 * @generated
 */
public interface IAbapGitPullModifiedObjects extends EObject {
	/**
	 * Returns the value of the '<em><b>Overwrite Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overwrite Objects</em>' containment reference.
	 * @see #setOverwriteObjects(IOverwriteObjects)
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getAbapGitPullModifiedObjects_OverwriteObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='overwriteObjects'"
	 * @generated
	 */
	IOverwriteObjects getOverwriteObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getOverwriteObjects <em>Overwrite Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overwrite Objects</em>' containment reference.
	 * @see #getOverwriteObjects()
	 * @generated
	 */
	void setOverwriteObjects(IOverwriteObjects value);

	/**
	 * Returns the value of the '<em><b>Package Warning Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Warning Objects</em>' containment reference.
	 * @see #setPackageWarningObjects(IPackageWarningObjects)
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getAbapGitPullModifiedObjects_PackageWarningObjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='packageWarningObjects'"
	 * @generated
	 */
	IPackageWarningObjects getPackageWarningObjects();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getPackageWarningObjects <em>Package Warning Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Warning Objects</em>' containment reference.
	 * @see #getPackageWarningObjects()
	 * @generated
	 */
	void setPackageWarningObjects(IPackageWarningObjects value);

} // IAbapGitPullModifiedObjects
