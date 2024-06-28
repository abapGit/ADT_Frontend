/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects;

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
 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory
 * @model kind="package"
 * @generated
 */
public interface IAgitpullmodifiedobjectsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "agitpullmodifiedobjects";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sap.com/adt/abapgit/agitpullmodifiedobjects";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "agitpullmodifiedobjects";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAgitpullmodifiedobjectsPackage eINSTANCE = org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl <em>Abap Git Pull Modified Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getAbapGitPullModifiedObjects()
	 * @generated
	 */
	int ABAP_GIT_PULL_MODIFIED_OBJECTS = 0;

	/**
	 * The feature id for the '<em><b>Overwrite Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS = 0;

	/**
	 * The feature id for the '<em><b>Package Warning Objects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS = 1;

	/**
	 * The number of structural features of the '<em>Abap Git Pull Modified Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_PULL_MODIFIED_OBJECTS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Abap Git Pull Modified Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_PULL_MODIFIED_OBJECTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.DocumentRootImpl
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getDocumentRoot()
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
	 * The feature id for the '<em><b>Abapgitpullmodifiedobjects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ABAPGITPULLMODIFIEDOBJECTS = 3;

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
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectsImpl <em>Overwrite Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectsImpl
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getOverwriteObjects()
	 * @generated
	 */
	int OVERWRITE_OBJECTS = 2;

	/**
	 * The feature id for the '<em><b>Abapgitobjects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECTS__ABAPGITOBJECTS = 0;

	/**
	 * The number of structural features of the '<em>Overwrite Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Overwrite Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.PackageWarningObjectsImpl <em>Package Warning Objects</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.PackageWarningObjectsImpl
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getPackageWarningObjects()
	 * @generated
	 */
	int PACKAGE_WARNING_OBJECTS = 3;

	/**
	 * The feature id for the '<em><b>Abapgitobjects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS = 0;

	/**
	 * The number of structural features of the '<em>Package Warning Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_WARNING_OBJECTS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Package Warning Objects</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PACKAGE_WARNING_OBJECTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl <em>Overwrite Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getOverwriteObject()
	 * @generated
	 */
	int OVERWRITE_OBJECT = 4;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__EXTENSION = IAdtCorePackage.ADT_OBJECT_REFERENCE__EXTENSION;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__DESCRIPTION = IAdtCorePackage.ADT_OBJECT_REFERENCE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__NAME = IAdtCorePackage.ADT_OBJECT_REFERENCE__NAME;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__PACKAGE_NAME = IAdtCorePackage.ADT_OBJECT_REFERENCE__PACKAGE_NAME;

	/**
	 * The feature id for the '<em><b>Parent Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__PARENT_URI = IAdtCorePackage.ADT_OBJECT_REFERENCE__PARENT_URI;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__TYPE = IAdtCorePackage.ADT_OBJECT_REFERENCE__TYPE;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__URI = IAdtCorePackage.ADT_OBJECT_REFERENCE__URI;

	/**
	 * The feature id for the '<em><b>Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__ACTION = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Action Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT__ACTION_DESCRIPTION = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Overwrite Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT_FEATURE_COUNT = IAdtCorePackage.ADT_OBJECT_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Overwrite Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OVERWRITE_OBJECT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects <em>Abap Git Pull Modified Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git Pull Modified Objects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects
	 * @generated
	 */
	EClass getAbapGitPullModifiedObjects();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getOverwriteObjects <em>Overwrite Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Overwrite Objects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getOverwriteObjects()
	 * @see #getAbapGitPullModifiedObjects()
	 * @generated
	 */
	EReference getAbapGitPullModifiedObjects_OverwriteObjects();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getPackageWarningObjects <em>Package Warning Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Package Warning Objects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects#getPackageWarningObjects()
	 * @see #getAbapGitPullModifiedObjects()
	 * @generated
	 */
	EReference getAbapGitPullModifiedObjects_PackageWarningObjects();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getAbapgitpullmodifiedobjects <em>Abapgitpullmodifiedobjects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Abapgitpullmodifiedobjects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot#getAbapgitpullmodifiedobjects()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Abapgitpullmodifiedobjects();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects <em>Overwrite Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Overwrite Objects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects
	 * @generated
	 */
	EClass getOverwriteObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects#getAbapgitobjects <em>Abapgitobjects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobjects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects#getAbapgitobjects()
	 * @see #getOverwriteObjects()
	 * @generated
	 */
	EReference getOverwriteObjects_Abapgitobjects();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects <em>Package Warning Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Package Warning Objects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects
	 * @generated
	 */
	EClass getPackageWarningObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects#getAbapgitobjects <em>Abapgitobjects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobjects</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects#getAbapgitobjects()
	 * @see #getPackageWarningObjects()
	 * @generated
	 */
	EReference getPackageWarningObjects_Abapgitobjects();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject <em>Overwrite Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Overwrite Object</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject
	 * @generated
	 */
	EClass getOverwriteObject();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getAction()
	 * @see #getOverwriteObject()
	 * @generated
	 */
	EAttribute getOverwriteObject_Action();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getActionDescription <em>Action Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action Description</em>'.
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject#getActionDescription()
	 * @see #getOverwriteObject()
	 * @generated
	 */
	EAttribute getOverwriteObject_ActionDescription();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IAgitpullmodifiedobjectsFactory getAgitpullmodifiedobjectsFactory();

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
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl <em>Abap Git Pull Modified Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AbapGitPullModifiedObjectsImpl
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getAbapGitPullModifiedObjects()
		 * @generated
		 */
		EClass ABAP_GIT_PULL_MODIFIED_OBJECTS = eINSTANCE.getAbapGitPullModifiedObjects();

		/**
		 * The meta object literal for the '<em><b>Overwrite Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS = eINSTANCE.getAbapGitPullModifiedObjects_OverwriteObjects();

		/**
		 * The meta object literal for the '<em><b>Package Warning Objects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS = eINSTANCE.getAbapGitPullModifiedObjects_PackageWarningObjects();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.DocumentRootImpl
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Abapgitpullmodifiedobjects</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ABAPGITPULLMODIFIEDOBJECTS = eINSTANCE.getDocumentRoot_Abapgitpullmodifiedobjects();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectsImpl <em>Overwrite Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectsImpl
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getOverwriteObjects()
		 * @generated
		 */
		EClass OVERWRITE_OBJECTS = eINSTANCE.getOverwriteObjects();

		/**
		 * The meta object literal for the '<em><b>Abapgitobjects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OVERWRITE_OBJECTS__ABAPGITOBJECTS = eINSTANCE.getOverwriteObjects_Abapgitobjects();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.PackageWarningObjectsImpl <em>Package Warning Objects</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.PackageWarningObjectsImpl
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getPackageWarningObjects()
		 * @generated
		 */
		EClass PACKAGE_WARNING_OBJECTS = eINSTANCE.getPackageWarningObjects();

		/**
		 * The meta object literal for the '<em><b>Abapgitobjects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS = eINSTANCE.getPackageWarningObjects_Abapgitobjects();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl <em>Overwrite Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.OverwriteObjectImpl
		 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl#getOverwriteObject()
		 * @generated
		 */
		EClass OVERWRITE_OBJECT = eINSTANCE.getOverwriteObject();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OVERWRITE_OBJECT__ACTION = eINSTANCE.getOverwriteObject_Action();

		/**
		 * The meta object literal for the '<em><b>Action Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OVERWRITE_OBJECT__ACTION_DESCRIPTION = eINSTANCE.getOverwriteObject_ActionDescription();

	}

} //IAgitpullmodifiedobjectsPackage
