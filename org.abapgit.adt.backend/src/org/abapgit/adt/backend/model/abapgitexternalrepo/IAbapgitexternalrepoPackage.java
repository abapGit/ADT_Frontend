/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory
 * @model kind="package"
 * @generated
 */
public interface IAbapgitexternalrepoPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "abapgitexternalrepo";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sap.com/adt/abapgit/externalRepo";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "abapgitexternalrepo";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitexternalrepoPackage eINSTANCE = org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl <em>Branch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getBranch()
	 * @generated
	 */
	int BRANCH = 0;

	/**
	 * The feature id for the '<em><b>Sha1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__SHA1 = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__NAME = 1;

	/**
	 * The feature id for the '<em><b>Is Head</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__IS_HEAD = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH__DISPLAY_NAME = 4;

	/**
	 * The number of structural features of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Branch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BRANCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoImpl <em>External Repository Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoImpl
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getExternalRepositoryInfo()
	 * @generated
	 */
	int EXTERNAL_REPOSITORY_INFO = 1;

	/**
	 * The feature id for the '<em><b>Branches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO__BRANCHES = 0;

	/**
	 * The feature id for the '<em><b>Access Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO__ACCESS_MODE = 1;

	/**
	 * The number of structural features of the '<em>External Repository Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>External Repository Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getDocumentRoot()
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
	 * The feature id for the '<em><b>External Repo Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTERNAL_REPO_INFO = 3;

	/**
	 * The feature id for the '<em><b>External Repo Info Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST = 4;

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
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl <em>External Repository Info Request</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getExternalRepositoryInfoRequest()
	 * @generated
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST = 3;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST__URL = 0;

	/**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST__USER = 1;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD = 2;

	/**
	 * The number of structural features of the '<em>External Repository Info Request</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>External Repository Info Request</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_REPOSITORY_INFO_REQUEST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode <em>Access Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getAccessMode()
	 * @generated
	 */
	int ACCESS_MODE = 4;


	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Branch</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch
	 * @generated
	 */
	EClass getBranch();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getSha1 <em>Sha1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sha1</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getSha1()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_Sha1();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getName()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getIsHead <em>Is Head</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Head</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getIsHead()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_IsHead();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getType()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch#getDisplayName()
	 * @see #getBranch()
	 * @generated
	 */
	EAttribute getBranch_DisplayName();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo <em>External Repository Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Repository Info</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo
	 * @generated
	 */
	EClass getExternalRepositoryInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getBranches <em>Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Branches</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getBranches()
	 * @see #getExternalRepositoryInfo()
	 * @generated
	 */
	EReference getExternalRepositoryInfo_Branches();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getAccessMode <em>Access Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Access Mode</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo#getAccessMode()
	 * @see #getExternalRepositoryInfo()
	 * @generated
	 */
	EAttribute getExternalRepositoryInfo_AccessMode();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getExternalRepoInfo <em>External Repo Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>External Repo Info</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getExternalRepoInfo()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExternalRepoInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getExternalRepoInfoRequest <em>External Repo Info Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>External Repo Info Request</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot#getExternalRepoInfoRequest()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExternalRepoInfoRequest();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest <em>External Repository Info Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Repository Info Request</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest
	 * @generated
	 */
	EClass getExternalRepositoryInfoRequest();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUrl()
	 * @see #getExternalRepositoryInfoRequest()
	 * @generated
	 */
	EAttribute getExternalRepositoryInfoRequest_Url();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getUser()
	 * @see #getExternalRepositoryInfoRequest()
	 * @generated
	 */
	EAttribute getExternalRepositoryInfoRequest_User();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest#getPassword()
	 * @see #getExternalRepositoryInfoRequest()
	 * @generated
	 */
	EAttribute getExternalRepositoryInfoRequest_Password();

	/**
	 * Returns the meta object for enum '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode <em>Access Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Mode</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode
	 * @generated
	 */
	EEnum getAccessMode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IAbapgitexternalrepoFactory getAbapgitexternalrepoFactory();

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
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl <em>Branch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.BranchImpl
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getBranch()
		 * @generated
		 */
		EClass BRANCH = eINSTANCE.getBranch();

		/**
		 * The meta object literal for the '<em><b>Sha1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__SHA1 = eINSTANCE.getBranch_Sha1();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__NAME = eINSTANCE.getBranch_Name();

		/**
		 * The meta object literal for the '<em><b>Is Head</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__IS_HEAD = eINSTANCE.getBranch_IsHead();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__TYPE = eINSTANCE.getBranch_Type();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BRANCH__DISPLAY_NAME = eINSTANCE.getBranch_DisplayName();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoImpl <em>External Repository Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoImpl
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getExternalRepositoryInfo()
		 * @generated
		 */
		EClass EXTERNAL_REPOSITORY_INFO = eINSTANCE.getExternalRepositoryInfo();

		/**
		 * The meta object literal for the '<em><b>Branches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_REPOSITORY_INFO__BRANCHES = eINSTANCE.getExternalRepositoryInfo_Branches();

		/**
		 * The meta object literal for the '<em><b>Access Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_REPOSITORY_INFO__ACCESS_MODE = eINSTANCE.getExternalRepositoryInfo_AccessMode();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.DocumentRootImpl
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>External Repo Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXTERNAL_REPO_INFO = eINSTANCE.getDocumentRoot_ExternalRepoInfo();

		/**
		 * The meta object literal for the '<em><b>External Repo Info Request</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST = eINSTANCE.getDocumentRoot_ExternalRepoInfoRequest();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl <em>External Repository Info Request</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.ExternalRepositoryInfoRequestImpl
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getExternalRepositoryInfoRequest()
		 * @generated
		 */
		EClass EXTERNAL_REPOSITORY_INFO_REQUEST = eINSTANCE.getExternalRepositoryInfoRequest();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_REPOSITORY_INFO_REQUEST__URL = eINSTANCE.getExternalRepositoryInfoRequest_Url();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_REPOSITORY_INFO_REQUEST__USER = eINSTANCE.getExternalRepositoryInfoRequest_User();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD = eINSTANCE.getExternalRepositoryInfoRequest_Password();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode <em>Access Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode
		 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.impl.AbapgitexternalrepoPackageImpl#getAccessMode()
		 * @generated
		 */
		EEnum ACCESS_MODE = eINSTANCE.getAccessMode();

	}

} //IAbapgitexternalrepoPackage
