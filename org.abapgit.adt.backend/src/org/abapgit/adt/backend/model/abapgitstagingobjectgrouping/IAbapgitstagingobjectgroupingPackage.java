/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping;

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
 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingFactory
 * @model kind="package"
 * @generated
 */
public interface IAbapgitstagingobjectgroupingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "abapgitstagingobjectgrouping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sap.com/adt/abapgit/staging/grouping";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "abapgitstagingobjectgrouping";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IAbapgitstagingobjectgroupingPackage eINSTANCE = org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl <em>Abap Git Staging Group Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingPackageImpl#getAbapGitStagingGroupNode()
	 * @generated
	 */
	int ABAP_GIT_STAGING_GROUP_NODE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE__URI = 2;

	/**
	 * The feature id for the '<em><b>Abapgitobjects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS = 3;

	/**
	 * The number of structural features of the '<em>Abap Git Staging Group Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Abap Git Staging Group Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABAP_GIT_STAGING_GROUP_NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.DocumentRootImpl
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingPackageImpl#getDocumentRoot()
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
	 * The feature id for the '<em><b>Abapgitstaginggroupnode</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ABAPGITSTAGINGGROUPNODE = 3;

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
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode <em>Abap Git Staging Group Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abap Git Staging Group Node</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode
	 * @generated
	 */
	EClass getAbapGitStagingGroupNode();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getType()
	 * @see #getAbapGitStagingGroupNode()
	 * @generated
	 */
	EAttribute getAbapGitStagingGroupNode_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getValue()
	 * @see #getAbapGitStagingGroupNode()
	 * @generated
	 */
	EAttribute getAbapGitStagingGroupNode_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getUri()
	 * @see #getAbapGitStagingGroupNode()
	 * @generated
	 */
	EAttribute getAbapGitStagingGroupNode_Uri();

	/**
	 * Returns the meta object for the containment reference list '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getAbapgitobjects <em>Abapgitobjects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Abapgitobjects</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode#getAbapgitobjects()
	 * @see #getAbapGitStagingGroupNode()
	 * @generated
	 */
	EReference getAbapGitStagingGroupNode_Abapgitobjects();

	/**
	 * Returns the meta object for class '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getAbapgitstaginggroupnode <em>Abapgitstaginggroupnode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Abapgitstaginggroupnode</em>'.
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot#getAbapgitstaginggroupnode()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Abapgitstaginggroupnode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IAbapgitstagingobjectgroupingFactory getAbapgitstagingobjectgroupingFactory();

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
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl <em>Abap Git Staging Group Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapGitStagingGroupNodeImpl
		 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingPackageImpl#getAbapGitStagingGroupNode()
		 * @generated
		 */
		EClass ABAP_GIT_STAGING_GROUP_NODE = eINSTANCE.getAbapGitStagingGroupNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_STAGING_GROUP_NODE__TYPE = eINSTANCE.getAbapGitStagingGroupNode_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_STAGING_GROUP_NODE__VALUE = eINSTANCE.getAbapGitStagingGroupNode_Value();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABAP_GIT_STAGING_GROUP_NODE__URI = eINSTANCE.getAbapGitStagingGroupNode_Uri();

		/**
		 * The meta object literal for the '<em><b>Abapgitobjects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS = eINSTANCE.getAbapGitStagingGroupNode_Abapgitobjects();

		/**
		 * The meta object literal for the '{@link org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.DocumentRootImpl
		 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl.AbapgitstagingobjectgroupingPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Abapgitstaginggroupnode</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ABAPGITSTAGINGGROUPNODE = eINSTANCE.getDocumentRoot_Abapgitstaginggroupnode();

	}

} //IAbapgitstagingobjectgroupingPackage
