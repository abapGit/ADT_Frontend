/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import com.sap.adt.tools.core.model.atom.IAtomLink;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abap Git File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getName <em>Name</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getPath <em>Path</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLocalState <em>Local State</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getRemoteState <em>Remote State</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile()
 * @model extendedMetaData="name='abapgitfile' kind='elementOnly'"
 * @generated
 */
public interface IAbapGitFile extends EObject {

	public enum Status {
		ADDED("A"), DELETED("D"), MODIFIED("M"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		private final String status;

		private Status(String status) {
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' namespace='##targetNamespace' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' namespace='##targetNamespace' name='path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Local State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local State</em>' attribute.
	 * @see #setLocalState(String)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_LocalState()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' namespace='##targetNamespace' name='localState'"
	 * @generated
	 */
	String getLocalState();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLocalState <em>Local State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local State</em>' attribute.
	 * @see #getLocalState()
	 * @generated
	 */
	void setLocalState(String value);

	/**
	 * Returns the value of the '<em><b>Remote State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote State</em>' attribute.
	 * @see #setRemoteState(String)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_RemoteState()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' namespace='##targetNamespace' name='remoteState'"
	 * @generated
	 */
	String getRemoteState();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getRemoteState <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote State</em>' attribute.
	 * @see #getRemoteState()
	 * @generated
	 */
	void setRemoteState(String value);

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link com.sap.adt.tools.core.model.atom.IAtomLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_Links()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='link' namespace='http://www.w3.org/2005/Atom'"
	 * @generated
	 */
	EList<IAtomLink> getLinks();

} // IAbapGitFile
