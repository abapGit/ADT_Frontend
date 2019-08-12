/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getVersion <em>Version</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getFiles <em>Files</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitObject()
 * @model extendedMetaData="name='abapgitobject' kind='elementOnly'"
 * @generated
 */
public interface IAbapGitObject extends IAdtObjectReference {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitObject_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' namespace='##targetNamespace' name='version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitObject_Files()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapgitfile'"
	 * @generated
	 */
	EList<IAbapGitFile> getFiles();

} // IAbapGitObject
