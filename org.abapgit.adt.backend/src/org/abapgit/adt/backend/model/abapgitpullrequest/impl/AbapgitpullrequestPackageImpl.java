/**
 */
package org.abapgit.adt.backend.model.abapgitpullrequest.impl;

import com.sap.adt.tools.core.model.adtcore.IAdtCorePackage;

import com.sap.adt.tools.core.model.atom.IAtomPackage;

import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapGitPullRequest;
import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestFactory;
import org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage;
import org.abapgit.adt.backend.model.abapgitpullrequest.IDocumentRoot;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl.AgitpullmodifiedobjectsPackageImpl;

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
public class AbapgitpullrequestPackageImpl extends EPackageImpl implements IAbapgitpullrequestPackage {
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
	private EClass abapGitPullRequestEClass = null;

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
	 * @see org.abapgit.adt.backend.model.abapgitpullrequest.IAbapgitpullrequestPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AbapgitpullrequestPackageImpl() {
		super(eNS_URI, IAbapgitpullrequestFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IAbapgitpullrequestPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IAbapgitpullrequestPackage init() {
		if (isInited) return (IAbapgitpullrequestPackage)EPackage.Registry.INSTANCE.getEPackage(IAbapgitpullrequestPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAbapgitpullrequestPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AbapgitpullrequestPackageImpl theAbapgitpullrequestPackage = registeredAbapgitpullrequestPackage instanceof AbapgitpullrequestPackageImpl ? (AbapgitpullrequestPackageImpl)registeredAbapgitpullrequestPackage : new AbapgitpullrequestPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IAdtCorePackage.eINSTANCE.eClass();
		IAtomPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(IAgitpullmodifiedobjectsPackage.eNS_URI);
		AgitpullmodifiedobjectsPackageImpl theAgitpullmodifiedobjectsPackage = (AgitpullmodifiedobjectsPackageImpl)(registeredPackage instanceof AgitpullmodifiedobjectsPackageImpl ? registeredPackage : IAgitpullmodifiedobjectsPackage.eINSTANCE);

		// Create package meta-data objects
		theAbapgitpullrequestPackage.createPackageContents();
		theAgitpullmodifiedobjectsPackage.createPackageContents();

		// Initialize created meta-data
		theAbapgitpullrequestPackage.initializePackageContents();
		theAgitpullmodifiedobjectsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAbapgitpullrequestPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IAbapgitpullrequestPackage.eNS_URI, theAbapgitpullrequestPackage);
		return theAbapgitpullrequestPackage;
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
	public EReference getDocumentRoot_Abapgitpullrequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbapGitPullRequest() {
		return abapGitPullRequestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbapGitPullRequest_OverwriteObjects() {
		return (EReference)abapGitPullRequestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbapGitPullRequest_PackageWarningObjects() {
		return (EReference)abapGitPullRequestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitPullRequest_BranchName() {
		return (EAttribute)abapGitPullRequestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitPullRequest_TransportRequest() {
		return (EAttribute)abapGitPullRequestEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitPullRequest_User() {
		return (EAttribute)abapGitPullRequestEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbapGitPullRequest_Password() {
		return (EAttribute)abapGitPullRequestEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAbapgitpullrequestFactory getAbapgitpullrequestFactory() {
		return (IAbapgitpullrequestFactory)getEFactoryInstance();
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
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ABAPGITPULLREQUEST);

		abapGitPullRequestEClass = createEClass(ABAP_GIT_PULL_REQUEST);
		createEReference(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__OVERWRITE_OBJECTS);
		createEReference(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__PACKAGE_WARNING_OBJECTS);
		createEAttribute(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__BRANCH_NAME);
		createEAttribute(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__TRANSPORT_REQUEST);
		createEAttribute(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__USER);
		createEAttribute(abapGitPullRequestEClass, ABAP_GIT_PULL_REQUEST__PASSWORD);
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
		IAgitpullmodifiedobjectsPackage theAgitpullmodifiedobjectsPackage = (IAgitpullmodifiedobjectsPackage)EPackage.Registry.INSTANCE.getEPackage(IAgitpullmodifiedobjectsPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(documentRootEClass, IDocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Abapgitpullrequest(), this.getAbapGitPullRequest(), null, "abapgitpullrequest", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(abapGitPullRequestEClass, IAbapGitPullRequest.class, "AbapGitPullRequest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbapGitPullRequest_OverwriteObjects(), theAgitpullmodifiedobjectsPackage.getOverwriteObjects(), null, "overwriteObjects", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitPullRequest_PackageWarningObjects(), theAgitpullmodifiedobjectsPackage.getPackageWarningObjects(), null, "packageWarningObjects", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitPullRequest_BranchName(), theXMLTypePackage.getString(), "branchName", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitPullRequest_TransportRequest(), theXMLTypePackage.getString(), "transportRequest", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitPullRequest_User(), theXMLTypePackage.getString(), "user", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitPullRequest_Password(), theXMLTypePackage.getString(), "password", null, 0, 1, IAbapGitPullRequest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (getDocumentRoot_Abapgitpullrequest(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitpullrequest"
		   });
		addAnnotation
		  (abapGitPullRequestEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "abapgitpullrequest"
		   });
		addAnnotation
		  (getAbapGitPullRequest_OverwriteObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "http://www.sap.com/adt/abapgit/agitpullmodifiedobjects",
			   "name", "overwriteObjects"
		   });
		addAnnotation
		  (getAbapGitPullRequest_PackageWarningObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "http://www.sap.com/adt/abapgit/agitpullmodifiedobjects",
			   "name", "packageWarningObjects"
		   });
		addAnnotation
		  (getAbapGitPullRequest_BranchName(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "branchName"
		   });
		addAnnotation
		  (getAbapGitPullRequest_TransportRequest(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "transportRequest"
		   });
		addAnnotation
		  (getAbapGitPullRequest_User(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "user"
		   });
		addAnnotation
		  (getAbapGitPullRequest_Password(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "password"
		   });
	}

} //AbapgitpullrequestPackageImpl
