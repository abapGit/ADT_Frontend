/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

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
 * </ul>
 *
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile()
 * @model extendedMetaData="name='abapgitfile' kind='elementOnly'"
 * @generated
 */
public interface IAbapGitFile extends EObject {

	public enum Status {
		ADDED('A'), REMOVED('D'), MODIFIED('M');

		private final char status;

		private Status(char status) {
			this.status = status;
		}

		public char getChar() {
			return status;
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
	 * @see #setLocalState(char)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_LocalState()
	 * @model extendedMetaData="kind='attribute' namespace='##targetNamespace' name='localState'"
	 * @generated
	 */
	char getLocalState();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLocalState <em>Local State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local State</em>' attribute.
	 * @see #getLocalState()
	 * @generated
	 */
	void setLocalState(char value);

	/**
	 * Returns the value of the '<em><b>Remote State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote State</em>' attribute.
	 * @see #setRemoteState(char)
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#getAbapGitFile_RemoteState()
	 * @model extendedMetaData="kind='attribute' namespace='##targetNamespace' name='remoteState'"
	 * @generated
	 */
	char getRemoteState();

	/**
	 * Sets the value of the '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getRemoteState <em>Remote State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote State</em>' attribute.
	 * @see #getRemoteState()
	 * @generated
	 */
	void setRemoteState(char value);

} // IAbapGitFile
