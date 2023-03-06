/**
 */
package org.abapgit.adt.backend.model.abapgitstaging;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.sap.adt.tools.core.model.adtcore.IAdtCorePackage;

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
 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory
 * @model kind="package"
 * @generated
 */
public interface IAbapgitstagingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "abapgitstaging"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sap.com/adt/abapgit/staging"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "abapgitstaging"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitstagingPackage eINSTANCE = org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl <em>Abap Git Staging</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitStaging()
	 * @generated
	 */
	int ABAP_GIT_STAGING = 0;

	/**
	 * The feature id for the '<em><b>Unstaged Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING__UNSTAGED_OBJECTS = 0;

	/**
	 * The feature id for the '<em><b>Staged Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING__STAGED_OBJECTS = 1;

	/**
	 * The feature id for the '<em><b>Ignored Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING__IGNORED_OBJECTS = 2;

	/**
	 * The feature id for the '<em><b>Commit Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING__COMMIT_MESSAGE = 3;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING__LINKS = 4;

	/**
	 * The number of structural features of the '<em>Abap Git Staging</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Abap Git Staging</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.DocumentRootImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 1;

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
	 * The feature id for the '<em><b>Abapgitstaging</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ABAPGITSTAGING = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl <em>Abap Git Commit Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitCommitMessage()
	 * @generated
	 */
	int ABAP_GIT_COMMIT_MESSAGE = 2;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_COMMIT_MESSAGE__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>Author</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_COMMIT_MESSAGE__AUTHOR = 1;

	/**
	 * The feature id for the '<em><b>Committer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_COMMIT_MESSAGE__COMMITTER = 2;

	/**
	 * The number of structural features of the '<em>Abap Git Commit Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_COMMIT_MESSAGE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Abap Git Commit Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_COMMIT_MESSAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl <em>Abap Git Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitObject()
	 * @generated
	 */
	int ABAP_GIT_OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__EXTENSION = IAdtCorePackage.ADT_OBJECT_REFERENCE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__DESCRIPTION = IAdtCorePackage.ADT_OBJECT_REFERENCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__NAME = IAdtCorePackage.ADT_OBJECT_REFERENCE__NAME;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__PACKAGE_NAME = IAdtCorePackage.ADT_OBJECT_REFERENCE__PACKAGE_NAME;

	/**
	 * The feature id for the '<em><b>Parent Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__PARENT_URI = IAdtCorePackage.ADT_OBJECT_REFERENCE__PARENT_URI;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__TYPE = IAdtCorePackage.ADT_OBJECT_REFERENCE__TYPE;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__URI = IAdtCorePackage.ADT_OBJECT_REFERENCE__URI;

	/**
	 * The feature id for the '<em><b>Wbkey</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__WBKEY = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__VERSION = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__FILES = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__LINKS = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Transport</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT__TRANSPORT = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Abap Git Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT_FEATURE_COUNT = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Abap Git Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl <em>Abap Git File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitFile()
	 * @generated
	 */
	int ABAP_GIT_FILE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE__PATH = 1;

	/**
	 * The feature id for the '<em><b>Local State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE__LOCAL_STATE = 2;

	/**
	 * The feature id for the '<em><b>Remote State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE__REMOTE_STATE = 3;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE__LINKS = 4;

	/**
	 * The number of structural features of the '<em>Abap Git File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Abap Git File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_FILE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AuthorImpl <em>Author</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AuthorImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAuthor()
	 * @generated
	 */
	int AUTHOR = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__NAME = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR__EMAIL = 1;

	/**
	 * The number of structural features of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Author</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.CommitterImpl <em>Committer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.CommitterImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getCommitter()
	 * @generated
	 */
	int COMMITTER = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITTER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITTER__EMAIL = 1;

	/**
	 * The number of structural features of the '<em>Committer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITTER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Committer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMITTER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.UnstagedObjectsImpl <em>Unstaged Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.UnstagedObjectsImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getUnstagedObjects()
	 * @generated
	 */
	int UNSTAGED_OBJECTS = 7;

	/**
	 * The feature id for the '<em><b>Abapgitobject</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNSTAGED_OBJECTS__ABAPGITOBJECT = 0;

	/**
	 * The number of structural features of the '<em>Unstaged Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNSTAGED_OBJECTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Unstaged Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNSTAGED_OBJECTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.StagedObjectsImpl <em>Staged Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.StagedObjectsImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getStagedObjects()
	 * @generated
	 */
	int STAGED_OBJECTS = 8;

	/**
	 * The feature id for the '<em><b>Abapgitobject</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAGED_OBJECTS__ABAPGITOBJECT = 0;

	/**
	 * The number of structural features of the '<em>Staged Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAGED_OBJECTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Staged Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STAGED_OBJECTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.IgnoredObjectsImpl <em>Ignored Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.IgnoredObjectsImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getIgnoredObjects()
	 * @generated
	 */
	int IGNORED_OBJECTS = 9;

	/**
	 * The feature id for the '<em><b>Abapgitobject</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IGNORED_OBJECTS__ABAPGITOBJECT = 0;

	/**
	 * The number of structural features of the '<em>Ignored Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IGNORED_OBJECTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Ignored Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IGNORED_OBJECTS_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.TransportImpl <em>Transport</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.TransportImpl
	 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getTransport()
	 * @generated
	 */
	int TRANSPORT = 10;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT__LINKS = 1;

	/**
	 * The number of structural features of the '<em>Transport</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Transport</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSPORT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging <em>Abap Git Staging</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git Staging</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging
	 * @generated
	 */
	EClass getAbapGitStaging();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getUnstagedObjects <em>Unstaged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Unstaged Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getUnstagedObjects()
	 * @see #getAbapGitStaging()
	 * @generated
	 */
	EReference getAbapGitStaging_UnstagedObjects();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getStagedObjects <em>Staged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Staged Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getStagedObjects()
	 * @see #getAbapGitStaging()
	 * @generated
	 */
	EReference getAbapGitStaging_StagedObjects();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getIgnoredObjects <em>Ignored Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ignored Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getIgnoredObjects()
	 * @see #getAbapGitStaging()
	 * @generated
	 */
	EReference getAbapGitStaging_IgnoredObjects();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getCommitMessage <em>Commit Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Commit Message</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getCommitMessage()
	 * @see #getAbapGitStaging()
	 * @generated
	 */
	EReference getAbapGitStaging_CommitMessage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging#getLinks()
	 * @see #getAbapGitStaging()
	 * @generated
	 */
	EReference getAbapGitStaging_Links();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getAbapgitstaging <em>Abapgitstaging</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Abapgitstaging</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot#getAbapgitstaging()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Abapgitstaging();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage <em>Abap Git Commit Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git Commit Message</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage
	 * @generated
	 */
	EClass getAbapGitCommitMessage();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getMessage()
	 * @see #getAbapGitCommitMessage()
	 * @generated
	 */
	EAttribute getAbapGitCommitMessage_Message();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Author</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getAuthor()
	 * @see #getAbapGitCommitMessage()
	 * @generated
	 */
	EReference getAbapGitCommitMessage_Author();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getCommitter <em>Committer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Committer</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage#getCommitter()
	 * @see #getAbapGitCommitMessage()
	 * @generated
	 */
	EReference getAbapGitCommitMessage_Committer();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject <em>Abap Git Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git Object</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject
	 * @generated
	 */
	EClass getAbapGitObject();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getWbkey <em>Wbkey</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wbkey</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getWbkey()
	 * @see #getAbapGitObject()
	 * @generated
	 */
	EAttribute getAbapGitObject_Wbkey();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getVersion()
	 * @see #getAbapGitObject()
	 * @generated
	 */
	EAttribute getAbapGitObject_Version();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Files</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getFiles()
	 * @see #getAbapGitObject()
	 * @generated
	 */
	EReference getAbapGitObject_Files();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getLinks()
	 * @see #getAbapGitObject()
	 * @generated
	 */
	EReference getAbapGitObject_Links();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getTransport <em>Transport</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transport</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject#getTransport()
	 * @see #getAbapGitObject()
	 * @generated
	 */
	EReference getAbapGitObject_Transport();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile <em>Abap Git File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git File</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile
	 * @generated
	 */
	EClass getAbapGitFile();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getName()
	 * @see #getAbapGitFile()
	 * @generated
	 */
	EAttribute getAbapGitFile_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getPath()
	 * @see #getAbapGitFile()
	 * @generated
	 */
	EAttribute getAbapGitFile_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLocalState <em>Local State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local State</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLocalState()
	 * @see #getAbapGitFile()
	 * @generated
	 */
	EAttribute getAbapGitFile_LocalState();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getRemoteState <em>Remote State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remote State</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getRemoteState()
	 * @see #getAbapGitFile()
	 * @generated
	 */
	EAttribute getAbapGitFile_RemoteState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile#getLinks()
	 * @see #getAbapGitFile()
	 * @generated
	 */
	EReference getAbapGitFile_Links();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Author</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAuthor
	 * @generated
	 */
	EClass getAuthor();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAuthor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAuthor#getName()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.IAuthor#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAuthor#getEmail()
	 * @see #getAuthor()
	 * @generated
	 */
	EAttribute getAuthor_Email();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.ICommitter <em>Committer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Committer</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ICommitter
	 * @generated
	 */
	EClass getCommitter();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.ICommitter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ICommitter#getName()
	 * @see #getCommitter()
	 * @generated
	 */
	EAttribute getCommitter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.ICommitter#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ICommitter#getEmail()
	 * @see #getCommitter()
	 * @generated
	 */
	EAttribute getCommitter_Email();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects <em>Unstaged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unstaged Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects
	 * @generated
	 */
	EClass getUnstagedObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects#getAbapgitobject <em>Abapgitobject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobject</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects#getAbapgitobject()
	 * @see #getUnstagedObjects()
	 * @generated
	 */
	EReference getUnstagedObjects_Abapgitobject();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects <em>Staged Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Staged Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects
	 * @generated
	 */
	EClass getStagedObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects#getAbapgitobject <em>Abapgitobject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobject</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects#getAbapgitobject()
	 * @see #getStagedObjects()
	 * @generated
	 */
	EReference getStagedObjects_Abapgitobject();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects <em>Ignored Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ignored Objects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects
	 * @generated
	 */
	EClass getIgnoredObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects#getAbapgitobject <em>Abapgitobject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobject</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects#getAbapgitobject()
	 * @see #getIgnoredObjects()
	 * @generated
	 */
	EReference getIgnoredObjects_Abapgitobject();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstaging.ITransport <em>Transport</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transport</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ITransport
	 * @generated
	 */
	EClass getTransport();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstaging.ITransport#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ITransport#getNumber()
	 * @see #getTransport()
	 * @generated
	 */
	EAttribute getTransport_Number();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstaging.ITransport#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstaging.ITransport#getLinks()
	 * @see #getTransport()
	 * @generated
	 */
	EReference getTransport_Links();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IAbapgitstagingFactory getAbapgitstagingFactory();

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
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl <em>Abap Git Staging</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitStaging()
		 * @generated
		 */
		EClass ABAP_GIT_STAGING = eINSTANCE.getAbapGitStaging();

		/**
		 * The meta object literal for the '<em><b>Unstaged Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING__UNSTAGED_OBJECTS = eINSTANCE.getAbapGitStaging_UnstagedObjects();

		/**
		 * The meta object literal for the '<em><b>Staged Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING__STAGED_OBJECTS = eINSTANCE.getAbapGitStaging_StagedObjects();

		/**
		 * The meta object literal for the '<em><b>Ignored Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING__IGNORED_OBJECTS = eINSTANCE.getAbapGitStaging_IgnoredObjects();

		/**
		 * The meta object literal for the '<em><b>Commit Message</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING__COMMIT_MESSAGE = eINSTANCE.getAbapGitStaging_CommitMessage();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING__LINKS = eINSTANCE.getAbapGitStaging_Links();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.DocumentRootImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Abapgitstaging</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ABAPGITSTAGING = eINSTANCE.getDocumentRoot_Abapgitstaging();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl <em>Abap Git Commit Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitCommitMessage()
		 * @generated
		 */
		EClass ABAP_GIT_COMMIT_MESSAGE = eINSTANCE.getAbapGitCommitMessage();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_COMMIT_MESSAGE__MESSAGE = eINSTANCE.getAbapGitCommitMessage_Message();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_COMMIT_MESSAGE__AUTHOR = eINSTANCE.getAbapGitCommitMessage_Author();

		/**
		 * The meta object literal for the '<em><b>Committer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_COMMIT_MESSAGE__COMMITTER = eINSTANCE.getAbapGitCommitMessage_Committer();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl <em>Abap Git Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitObjectImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitObject()
		 * @generated
		 */
		EClass ABAP_GIT_OBJECT = eINSTANCE.getAbapGitObject();

		/**
		 * The meta object literal for the '<em><b>Wbkey</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_OBJECT__WBKEY = eINSTANCE.getAbapGitObject_Wbkey();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_OBJECT__VERSION = eINSTANCE.getAbapGitObject_Version();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_OBJECT__FILES = eINSTANCE.getAbapGitObject_Files();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_OBJECT__LINKS = eINSTANCE.getAbapGitObject_Links();

		/**
		 * The meta object literal for the '<em><b>Transport</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_OBJECT__TRANSPORT = eINSTANCE.getAbapGitObject_Transport();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl <em>Abap Git File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitFileImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAbapGitFile()
		 * @generated
		 */
		EClass ABAP_GIT_FILE = eINSTANCE.getAbapGitFile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_FILE__NAME = eINSTANCE.getAbapGitFile_Name();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_FILE__PATH = eINSTANCE.getAbapGitFile_Path();

		/**
		 * The meta object literal for the '<em><b>Local State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_FILE__LOCAL_STATE = eINSTANCE.getAbapGitFile_LocalState();

		/**
		 * The meta object literal for the '<em><b>Remote State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_FILE__REMOTE_STATE = eINSTANCE.getAbapGitFile_RemoteState();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_FILE__LINKS = eINSTANCE.getAbapGitFile_Links();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AuthorImpl <em>Author</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AuthorImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getAuthor()
		 * @generated
		 */
		EClass AUTHOR = eINSTANCE.getAuthor();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__NAME = eINSTANCE.getAuthor_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTHOR__EMAIL = eINSTANCE.getAuthor_Email();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.CommitterImpl <em>Committer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.CommitterImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getCommitter()
		 * @generated
		 */
		EClass COMMITTER = eINSTANCE.getCommitter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMITTER__NAME = eINSTANCE.getCommitter_Name();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMITTER__EMAIL = eINSTANCE.getCommitter_Email();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.UnstagedObjectsImpl <em>Unstaged Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.UnstagedObjectsImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getUnstagedObjects()
		 * @generated
		 */
		EClass UNSTAGED_OBJECTS = eINSTANCE.getUnstagedObjects();

		/**
		 * The meta object literal for the '<em><b>Abapgitobject</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNSTAGED_OBJECTS__ABAPGITOBJECT = eINSTANCE.getUnstagedObjects_Abapgitobject();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.StagedObjectsImpl <em>Staged Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.StagedObjectsImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getStagedObjects()
		 * @generated
		 */
		EClass STAGED_OBJECTS = eINSTANCE.getStagedObjects();

		/**
		 * The meta object literal for the '<em><b>Abapgitobject</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STAGED_OBJECTS__ABAPGITOBJECT = eINSTANCE.getStagedObjects_Abapgitobject();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.IgnoredObjectsImpl <em>Ignored Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.IgnoredObjectsImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getIgnoredObjects()
		 * @generated
		 */
		EClass IGNORED_OBJECTS = eINSTANCE.getIgnoredObjects();

		/**
		 * The meta object literal for the '<em><b>Abapgitobject</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IGNORED_OBJECTS__ABAPGITOBJECT = eINSTANCE.getIgnoredObjects_Abapgitobject();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstaging.impl.TransportImpl <em>Transport</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.TransportImpl
		 * @see org.abapgit.adt.backend.model.abapgitstaging.impl.AbapgitstagingPackageImpl#getTransport()
		 * @generated
		 */
		EClass TRANSPORT = eINSTANCE.getTransport();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSPORT__NUMBER = eINSTANCE.getTransport_Number();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSPORT__LINKS = eINSTANCE.getTransport_Links();

	}

} //IAbapgitstagingPackage
