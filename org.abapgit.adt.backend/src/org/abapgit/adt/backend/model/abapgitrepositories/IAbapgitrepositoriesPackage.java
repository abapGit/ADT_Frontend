/**
 */
package org.abapgit.adt.backend.model.abapgitrepositories;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Schema definition for
 * 			ABAP Development Tools core types
 * 
 * 			Author: SAP AG
 * 			Copyright (c) 2009 by
 * 			SAP AG
 * 		
 * 
 * 			Schema definition for
 * 			ATOM links
 * 
 * 			Author: SAP AG
 * 			Copyright (c) 2011 by
 * 			SAP AG
 * 		
 * 
 *    See http://www.w3.org/XML/1998/namespace.html and
 *    http://www.w3.org/TR/REC-xml for information about this namespace.
 * 
 *     This schema document describes the XML namespace, in a form
 *     suitable for import by other schema documents.  
 * 
 *     Note that local names in this namespace are intended to be defined
 *     only by the World Wide Web Consortium or its subgroups.  The
 *     following names are currently defined in this namespace and should
 *     not be used with conflicting semantics by any Working Group,
 *     specification, or document instance:
 * 
 *     base (as an attribute name): denotes an attribute whose value
 *          provides a URI to be used as the base for interpreting any
 *          relative URIs in the scope of the element on which it
 *          appears; its value is inherited.  This name is reserved
 *          by virtue of its definition in the XML Base specification.
 * 
 *     lang (as an attribute name): denotes an attribute whose value
 *          is a language code for the natural language of the content of
 *          any element; its value is inherited.  This name is reserved
 *          by virtue of its definition in the XML specification.
 *   
 *     space (as an attribute name): denotes an attribute whose
 *          value is a keyword indicating what whitespace processing
 *          discipline is intended for the content of the element; its
 *          value is inherited.  This name is reserved by virtue of its
 *          definition in the XML specification.
 * 
 *     Father (in any context at all): denotes Jon Bosak, the chair of 
 *          the original XML Working Group.  This name is reserved by 
 *          the following decision of the W3C XML Plenary and 
 *          XML Coordination groups:
 * 
 *              In appreciation for his vision, leadership and dedication
 *              the W3C XML Plenary on this 10th day of February, 2000
 *              reserves for Jon Bosak in perpetuity the XML name
 *              xml:Father
 *   
 * This schema defines attributes and an attribute group
 *         suitable for use by
 *         schemas wishing to allow xml:base, xml:lang or xml:space attributes
 *         on elements they define.
 * 
 *         To enable this, such a schema must import this schema
 *         for the XML namespace, e.g. as follows:
 *         <schema . . .>
 *          . . .
 *          <import namespace="http://www.w3.org/XML/1998/namespace"
 *                     schemaLocation="http://www.w3.org/2001/03/xml.xsd"/>
 * 
 *         Subsequently, qualified reference to any of the attributes
 *         or the group defined below will have the desired effect, e.g.
 * 
 *         <type . . .>
 *          . . .
 *          <attributeGroup ref="xml:specialAttrs"/>
 *  
 *          will define a type which will schema-validate an instance
 *          element with any of those attributes
 * In keeping with the XML Schema WG's standard versioning
 *    policy, this schema document will persist at
 *    http://www.w3.org/2001/03/xml.xsd.
 *    At the date of issue it can also be found at
 *    http://www.w3.org/2001/xml.xsd.
 *    The schema document at that URI may however change in the future,
 *    in order to remain compatible with the latest version of XML Schema
 *    itself.  In other words, if the XML Schema namespace changes, the version
 *    of this document at
 *    http://www.w3.org/2001/xml.xsd will change
 *    accordingly; the version at
 *    http://www.w3.org/2001/03/xml.xsd will not change.
 *   
 * <!-- end-model-doc -->
 * @see org.abapgit.adt.backend.model.abapgitrepositories.IAbapgitrepositoriesFactory
 * @model kind="package"
 * @generated
 */
public interface IAbapgitrepositoriesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "abapgitrepositories";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sap.com/adt/abapgit/repositories";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "abapgitrepo";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitrepositoriesPackage eINSTANCE = org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__LINKS = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__KEY = 1;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__PACKAGE = 2;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__URL = 3;

	/**
	 * The feature id for the '<em><b>Branch Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__BRANCH_NAME = 4;

	/**
	 * The feature id for the '<em><b>Created By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__CREATED_BY = 5;

	/**
	 * The feature id for the '<em><b>Created Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__CREATED_EMAIL = 6;

	/**
	 * The feature id for the '<em><b>Created At</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__CREATED_AT = 7;

	/**
	 * The feature id for the '<em><b>Deserialized At</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__DESERIALIZED_AT = 8;

	/**
	 * The feature id for the '<em><b>Deserialized Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__DESERIALIZED_EMAIL = 9;

	/**
	 * The feature id for the '<em><b>Deserialized By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__DESERIALIZED_BY = 10;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__STATUS = 11;

	/**
	 * The feature id for the '<em><b>Status Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__STATUS_TEXT = 12;

	/**
	 * The feature id for the '<em><b>Remote User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__REMOTE_USER = 13;

	/**
	 * The feature id for the '<em><b>Remote Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__REMOTE_PASSWORD = 14;

	/**
	 * The feature id for the '<em><b>First Commit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__FIRST_COMMIT = 15;

	/**
	 * The feature id for the '<em><b>Transport Request</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__TRANSPORT_REQUEST = 16;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = 17;

	/**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl <em>Repositories</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getRepositories()
	 * @generated
	 */
	int REPOSITORIES = 1;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORIES__REPOSITORIES = 0;

	/**
	 * The number of structural features of the '<em>Repositories</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORIES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Repositories</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.DocumentRootImpl
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Repositories</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__REPOSITORIES = 3;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__REPOSITORY = 4;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository
	 * @generated
	 */
	EClass getRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getLinks()
	 * @see #getRepository()
	 * @generated
	 */
	EReference getRepository_Links();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getKey()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getPackage()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Package();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getUrl()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getBranchName <em>Branch Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Branch Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getBranchName()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_BranchName();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedBy <em>Created By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created By</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedBy()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_CreatedBy();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedEmail <em>Created Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created Email</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedEmail()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_CreatedEmail();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedAt <em>Created At</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created At</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getCreatedAt()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_CreatedAt();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedAt <em>Deserialized At</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deserialized At</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedAt()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_DeserializedAt();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedEmail <em>Deserialized Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deserialized Email</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedEmail()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_DeserializedEmail();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedBy <em>Deserialized By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deserialized By</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getDeserializedBy()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_DeserializedBy();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatus()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatusText <em>Status Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status Text</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getStatusText()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_StatusText();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemoteUser <em>Remote User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remote User</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemoteUser()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_RemoteUser();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemotePassword <em>Remote Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remote Password</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getRemotePassword()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_RemotePassword();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getFirstCommit <em>First Commit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Commit</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getFirstCommit()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_FirstCommit();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getTransportRequest <em>Transport Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transport Request</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepository#getTransportRequest()
	 * @see #getRepository()
	 * @generated
	 */
	EAttribute getRepository_TransportRequest();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repositories</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepositories
	 * @generated
	 */
	EClass getRepositories();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitrepositories.IRepositories#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Repositories</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IRepositories#getRepositories()
	 * @see #getRepositories()
	 * @generated
	 */
	EReference getRepositories_Repositories();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getRepositories <em>Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Repositories</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getRepositories()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Repositories();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Repository</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitrepositories.IDocumentRoot#getRepository()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Repository();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IAbapgitrepositoriesFactory getAbapgitrepositoriesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoryImpl
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__LINKS = eINSTANCE.getRepository_Links();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__KEY = eINSTANCE.getRepository_Key();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__PACKAGE = eINSTANCE.getRepository_Package();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__URL = eINSTANCE.getRepository_Url();

		/**
		 * The meta object literal for the '<em><b>Branch Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__BRANCH_NAME = eINSTANCE.getRepository_BranchName();

		/**
		 * The meta object literal for the '<em><b>Created By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__CREATED_BY = eINSTANCE.getRepository_CreatedBy();

		/**
		 * The meta object literal for the '<em><b>Created Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__CREATED_EMAIL = eINSTANCE.getRepository_CreatedEmail();

		/**
		 * The meta object literal for the '<em><b>Created At</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__CREATED_AT = eINSTANCE.getRepository_CreatedAt();

		/**
		 * The meta object literal for the '<em><b>Deserialized At</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__DESERIALIZED_AT = eINSTANCE.getRepository_DeserializedAt();

		/**
		 * The meta object literal for the '<em><b>Deserialized Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__DESERIALIZED_EMAIL = eINSTANCE.getRepository_DeserializedEmail();

		/**
		 * The meta object literal for the '<em><b>Deserialized By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__DESERIALIZED_BY = eINSTANCE.getRepository_DeserializedBy();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__STATUS = eINSTANCE.getRepository_Status();

		/**
		 * The meta object literal for the '<em><b>Status Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__STATUS_TEXT = eINSTANCE.getRepository_StatusText();

		/**
		 * The meta object literal for the '<em><b>Remote User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__REMOTE_USER = eINSTANCE.getRepository_RemoteUser();

		/**
		 * The meta object literal for the '<em><b>Remote Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__REMOTE_PASSWORD = eINSTANCE.getRepository_RemotePassword();

		/**
		 * The meta object literal for the '<em><b>First Commit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__FIRST_COMMIT = eINSTANCE.getRepository_FirstCommit();

		/**
		 * The meta object literal for the '<em><b>Transport Request</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPOSITORY__TRANSPORT_REQUEST = eINSTANCE.getRepository_TransportRequest();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl <em>Repositories</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.RepositoriesImpl
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getRepositories()
		 * @generated
		 */
		EClass REPOSITORIES = eINSTANCE.getRepositories();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORIES__REPOSITORIES = eINSTANCE.getRepositories_Repositories();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitrepositories.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.DocumentRootImpl
		 * @see org.abapgit.adt.backend.model.abapgitrepositories.impl.AbapgitrepositoriesPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Repositories</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__REPOSITORIES = eINSTANCE.getDocumentRoot_Repositories();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__REPOSITORY = eINSTANCE.getDocumentRoot_Repository();

	}

} //IAbapgitrepositoriesPackage
