/**
 */
package org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.impl;

import com.sap.adt.tools.core.model.adtcore.IAdtCorePackage;

import com.sap.adt.tools.core.model.atom.IAtomPackage;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;

import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapGitStagingGroupNode;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingFactory;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage;
import org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IDocumentRoot;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitstagingobjectgroupingPackageImpl extends EPackageImpl implements IAbapgitstagingobjectgroupingPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abapGitStagingGroupNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.abapgit.adt.backend.model.abapgitstagingobjectgrouping.IAbapgitstagingobjectgroupingPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AbapgitstagingobjectgroupingPackageImpl() {
		super(eNS_URI, IAbapgitstagingobjectgroupingFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link IAbapgitstagingobjectgroupingPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IAbapgitstagingobjectgroupingPackage init() {
		if (isInited) return (IAbapgitstagingobjectgroupingPackage)EPackage.Registry.INSTANCE.getEPackage(IAbapgitstagingobjectgroupingPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAbapgitstagingobjectgroupingPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AbapgitstagingobjectgroupingPackageImpl theAbapgitstagingobjectgroupingPackage = registeredAbapgitstagingobjectgroupingPackage instanceof AbapgitstagingobjectgroupingPackageImpl ? (AbapgitstagingobjectgroupingPackageImpl)registeredAbapgitstagingobjectgroupingPackage : new AbapgitstagingobjectgroupingPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IAbapgitstagingPackage.eINSTANCE.eClass();
		IAdtCorePackage.eINSTANCE.eClass();
		IAtomPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAbapgitstagingobjectgroupingPackage.createPackageContents();

		// Initialize created meta-data
		theAbapgitstagingobjectgroupingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAbapgitstagingobjectgroupingPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IAbapgitstagingobjectgroupingPackage.eNS_URI, theAbapgitstagingobjectgroupingPackage);
		return theAbapgitstagingobjectgroupingPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbapGitStagingGroupNode() {
		return abapGitStagingGroupNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitStagingGroupNode_Type() {
		return (EAttribute)abapGitStagingGroupNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitStagingGroupNode_Value() {
		return (EAttribute)abapGitStagingGroupNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitStagingGroupNode_Uri() {
		return (EAttribute)abapGitStagingGroupNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbapGitStagingGroupNode_Abapgitobjects() {
		return (EReference)abapGitStagingGroupNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_Abapgitstaginggroupnode() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapgitstagingobjectgroupingFactory getAbapgitstagingobjectgroupingFactory() {
		return (IAbapgitstagingobjectgroupingFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		abapGitStagingGroupNodeEClass = createEClass(ABAP_GIT_STAGING_GROUP_NODE);
		createEAttribute(abapGitStagingGroupNodeEClass, ABAP_GIT_STAGING_GROUP_NODE__TYPE);
		createEAttribute(abapGitStagingGroupNodeEClass, ABAP_GIT_STAGING_GROUP_NODE__VALUE);
		createEAttribute(abapGitStagingGroupNodeEClass, ABAP_GIT_STAGING_GROUP_NODE__URI);
		createEReference(abapGitStagingGroupNodeEClass, ABAP_GIT_STAGING_GROUP_NODE__ABAPGITOBJECTS);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ABAPGITSTAGINGGROUPNODE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		IAbapgitstagingPackage theAbapgitstagingPackage = (IAbapgitstagingPackage)EPackage.Registry.INSTANCE.getEPackage(IAbapgitstagingPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(abapGitStagingGroupNodeEClass, IAbapGitStagingGroupNode.class, "AbapGitStagingGroupNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbapGitStagingGroupNode_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, IAbapGitStagingGroupNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitStagingGroupNode_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, IAbapGitStagingGroupNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitStagingGroupNode_Uri(), theXMLTypePackage.getString(), "uri", null, 0, 1, IAbapGitStagingGroupNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitStagingGroupNode_Abapgitobjects(), theAbapgitstagingPackage.getAbapGitObject(), null, "abapgitobjects", null, 0, -1, IAbapGitStagingGroupNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, IDocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Abapgitstaginggroupnode(), this.getAbapGitStagingGroupNode(), null, "abapgitstaginggroupnode", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (abapGitStagingGroupNodeEClass,
		   source,
		   new String[] {
			   "name", "abapgitstaginggroupnode",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAbapGitStagingGroupNode_Type(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getAbapGitStagingGroupNode_Value(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getAbapGitStagingGroupNode_Uri(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getAbapGitStagingGroupNode_Abapgitobjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "abapgitobjects",
			   "namespace", "http://www.w3.org/2005/Atom"
		   });
		addAnnotation
		  (documentRootEClass,
		   source,
		   new String[] {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getDocumentRoot_Mixed(),
		   source,
		   new String[] {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getDocumentRoot_Abapgitstaginggroupnode(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "abapgitstaginggroupnode",
			   "namespace", "##targetNamespace"
		   });
	}

} //AbapgitstagingobjectgroupingPackageImpl
