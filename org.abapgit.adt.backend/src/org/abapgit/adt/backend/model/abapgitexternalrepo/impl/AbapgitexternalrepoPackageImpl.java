/**
 */
package org.abapgit.adt.backend.model.abapgitexternalrepo.impl;

import org.abapgit.adt.backend.model.abapgitexternalrepo.AccessMode;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoFactory;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IBranch;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IDocumentRoot;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfo;
import org.abapgit.adt.backend.model.abapgitexternalrepo.IExternalRepositoryInfoRequest;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class AbapgitexternalrepoPackageImpl extends EPackageImpl implements IAbapgitexternalrepoPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass branchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalRepositoryInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalRepositoryInfoRequestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessModeEEnum = null;

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
	 * @see org.abapgit.adt.backend.model.abapgitexternalrepo.IAbapgitexternalrepoPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AbapgitexternalrepoPackageImpl() {
		super(eNS_URI, IAbapgitexternalrepoFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IAbapgitexternalrepoPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IAbapgitexternalrepoPackage init() {
		if (isInited) return (IAbapgitexternalrepoPackage)EPackage.Registry.INSTANCE.getEPackage(IAbapgitexternalrepoPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAbapgitexternalrepoPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AbapgitexternalrepoPackageImpl theAbapgitexternalrepoPackage = registeredAbapgitexternalrepoPackage instanceof AbapgitexternalrepoPackageImpl ? (AbapgitexternalrepoPackageImpl)registeredAbapgitexternalrepoPackage : new AbapgitexternalrepoPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAbapgitexternalrepoPackage.createPackageContents();

		// Initialize created meta-data
		theAbapgitexternalrepoPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAbapgitexternalrepoPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IAbapgitexternalrepoPackage.eNS_URI, theAbapgitexternalrepoPackage);
		return theAbapgitexternalrepoPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBranch() {
		return branchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranch_Sha1() {
		return (EAttribute)branchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranch_Name() {
		return (EAttribute)branchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranch_IsHead() {
		return (EAttribute)branchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranch_Type() {
		return (EAttribute)branchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBranch_DisplayName() {
		return (EAttribute)branchEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExternalRepositoryInfo() {
		return externalRepositoryInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalRepositoryInfo_Branches() {
		return (EReference)externalRepositoryInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalRepositoryInfo_AccessMode() {
		return (EAttribute)externalRepositoryInfoEClass.getEStructuralFeatures().get(1);
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
	public EReference getDocumentRoot_ExternalRepoInfo() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentRoot_ExternalRepoInfoRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExternalRepositoryInfoRequest() {
		return externalRepositoryInfoRequestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalRepositoryInfoRequest_Url() {
		return (EAttribute)externalRepositoryInfoRequestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalRepositoryInfoRequest_User() {
		return (EAttribute)externalRepositoryInfoRequestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalRepositoryInfoRequest_Password() {
		return (EAttribute)externalRepositoryInfoRequestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAccessMode() {
		return accessModeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapgitexternalrepoFactory getAbapgitexternalrepoFactory() {
		return (IAbapgitexternalrepoFactory)getEFactoryInstance();
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
		branchEClass = createEClass(BRANCH);
		createEAttribute(branchEClass, BRANCH__SHA1);
		createEAttribute(branchEClass, BRANCH__NAME);
		createEAttribute(branchEClass, BRANCH__IS_HEAD);
		createEAttribute(branchEClass, BRANCH__TYPE);
		createEAttribute(branchEClass, BRANCH__DISPLAY_NAME);

		externalRepositoryInfoEClass = createEClass(EXTERNAL_REPOSITORY_INFO);
		createEReference(externalRepositoryInfoEClass, EXTERNAL_REPOSITORY_INFO__BRANCHES);
		createEAttribute(externalRepositoryInfoEClass, EXTERNAL_REPOSITORY_INFO__ACCESS_MODE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXTERNAL_REPO_INFO);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXTERNAL_REPO_INFO_REQUEST);

		externalRepositoryInfoRequestEClass = createEClass(EXTERNAL_REPOSITORY_INFO_REQUEST);
		createEAttribute(externalRepositoryInfoRequestEClass, EXTERNAL_REPOSITORY_INFO_REQUEST__URL);
		createEAttribute(externalRepositoryInfoRequestEClass, EXTERNAL_REPOSITORY_INFO_REQUEST__USER);
		createEAttribute(externalRepositoryInfoRequestEClass, EXTERNAL_REPOSITORY_INFO_REQUEST__PASSWORD);

		// Create enums
		accessModeEEnum = createEEnum(ACCESS_MODE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(branchEClass, IBranch.class, "Branch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBranch_Sha1(), theXMLTypePackage.getString(), "sha1", null, 0, 1, IBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranch_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, IBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranch_IsHead(), theXMLTypePackage.getString(), "isHead", null, 0, 1, IBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranch_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, IBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBranch_DisplayName(), theXMLTypePackage.getString(), "displayName", null, 0, 1, IBranch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(externalRepositoryInfoEClass, IExternalRepositoryInfo.class, "ExternalRepositoryInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalRepositoryInfo_Branches(), this.getBranch(), null, "branches", null, 0, -1, IExternalRepositoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalRepositoryInfo_AccessMode(), this.getAccessMode(), "accessMode", null, 0, 1, IExternalRepositoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, IDocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExternalRepoInfo(), this.getExternalRepositoryInfo(), null, "externalRepoInfo", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExternalRepoInfoRequest(), this.getExternalRepositoryInfoRequest(), null, "externalRepoInfoRequest", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(externalRepositoryInfoRequestEClass, IExternalRepositoryInfoRequest.class, "ExternalRepositoryInfoRequest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalRepositoryInfoRequest_Url(), theXMLTypePackage.getString(), "url", null, 0, 1, IExternalRepositoryInfoRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalRepositoryInfoRequest_User(), theXMLTypePackage.getString(), "user", null, 0, 1, IExternalRepositoryInfoRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalRepositoryInfoRequest_Password(), theXMLTypePackage.getString(), "password", null, 0, 1, IExternalRepositoryInfoRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(accessModeEEnum, AccessMode.class, "AccessMode");
		addEEnumLiteral(accessModeEEnum, AccessMode.PUBLIC);
		addEEnumLiteral(accessModeEEnum, AccessMode.PRIVATE);

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
		  (branchEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "branch"
		   });
		addAnnotation
		  (getBranch_Sha1(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "sha1"
		   });
		addAnnotation
		  (getBranch_Name(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "name"
		   });
		addAnnotation
		  (getBranch_IsHead(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "isHead"
		   });
		addAnnotation
		  (getBranch_Type(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "type"
		   });
		addAnnotation
		  (getBranch_DisplayName(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "displayName"
		   });
		addAnnotation
		  (externalRepositoryInfoEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "externalRepoInfo"
		   });
		addAnnotation
		  (getExternalRepositoryInfo_Branches(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "branch"
		   });
		addAnnotation
		  (getExternalRepositoryInfo_AccessMode(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (documentRootEClass,
		   source,
		   new String[] {
			   "kind", "mixed",
			   "name", ""
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
		  (getDocumentRoot_ExternalRepoInfo(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "externalRepoInfo"
		   });
		addAnnotation
		  (getDocumentRoot_ExternalRepoInfoRequest(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "externalRepoInfoRequest"
		   });
		addAnnotation
		  (accessModeEEnum,
		   source,
		   new String[] {
			   "name", "accessMode"
		   });
		addAnnotation
		  (externalRepositoryInfoRequestEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "externalRepoInfoRequest"
		   });
		addAnnotation
		  (getExternalRepositoryInfoRequest_Url(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getExternalRepositoryInfoRequest_User(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getExternalRepositoryInfoRequest_Password(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace"
		   });
	}

} //AbapgitexternalrepoPackageImpl
