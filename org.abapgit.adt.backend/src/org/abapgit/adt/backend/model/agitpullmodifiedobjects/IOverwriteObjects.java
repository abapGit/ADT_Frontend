/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Overwrite Objects</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects#getAbapgitobjects <em>Abapgitobjects</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getOverwriteObjects()
 * @model extendedMetaData="kind='elementOnly' name='overwriteObjects'"
 * @generated
 */
public interface IOverwriteObjects extends EObject {
	/**
	 * Returns the value of the '<em><b>Abapgitobjects</b></em>' containment reference list.
	 * The list contents are of type {@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abapgitobjects</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#getOverwriteObjects_Abapgitobjects()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' namespace='##targetNamespace' name='abapgitobjects'"
	 * @generated
	 */
	EList<IOverwriteObject> getAbapgitobjects();

} // IOverwriteObjects
