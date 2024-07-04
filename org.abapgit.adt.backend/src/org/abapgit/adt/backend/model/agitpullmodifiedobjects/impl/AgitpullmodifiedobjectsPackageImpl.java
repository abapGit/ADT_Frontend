/**
 */
package org.abapgit.adt.backend.model.agitpullmodifiedobjects.impl;

import com.sap.adt.tools.core.model.adtcore.IAdtCorePackage;

import com.sap.adt.tools.core.model.atom.IAtomPackage;

import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAbapGitPullModifiedObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsFactory;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IDocumentRoot;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObject;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IOverwriteObjects;
import org.abapgit.adt.backend.model.agitpullmodifiedobjects.IPackageWarningObjects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AgitpullmodifiedobjectsPackageImpl extends EPackageImpl implements IAgitpullmodifiedobjectsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abapGitPullModifiedObjectsEClass = null;

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
	private EClass overwriteObjectsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageWarningObjectsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass overwriteObjectEClass = null;

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
	 * @see org.abapgit.adt.backend.model.agitpullmodifiedobjects.IAgitpullmodifiedobjectsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AgitpullmodifiedobjectsPackageImpl() {
		super(eNS_URI, IAgitpullmodifiedobjectsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IAgitpullmodifiedobjectsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IAgitpullmodifiedobjectsPackage init() {
		if (isInited) return (IAgitpullmodifiedobjectsPackage)EPackage.Registry.INSTANCE.getEPackage(IAgitpullmodifiedobjectsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAgitpullmodifiedobjectsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AgitpullmodifiedobjectsPackageImpl theAgitpullmodifiedobjectsPackage = registeredAgitpullmodifiedobjectsPackage instanceof AgitpullmodifiedobjectsPackageImpl ? (AgitpullmodifiedobjectsPackageImpl)registeredAgitpullmodifiedobjectsPackage : new AgitpullmodifiedobjectsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IAdtCorePackage.eINSTANCE.eClass();
		IAtomPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAgitpullmodifiedobjectsPackage.createPackageContents();

		// Initialize created meta-data
		theAgitpullmodifiedobjectsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAgitpullmodifiedobjectsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IAgitpullmodifiedobjectsPackage.eNS_URI, theAgitpullmodifiedobjectsPackage);
		return theAgitpullmodifiedobjectsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbapGitPullModifiedObjects() {
		return abapGitPullModifiedObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbapGitPullModifiedObjects_OverwriteObjects() {
		return (EReference)abapGitPullModifiedObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbapGitPullModifiedObjects_PackageWarningObjects() {
		return (EReference)abapGitPullModifiedObjectsEClass.getEStructuralFeatures().get(1);
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
	public EReference getDocumentRoot_Abapgitpullmodifiedobjects() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOverwriteObjects() {
		return overwriteObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOverwriteObjects_Abapgitobjects() {
		return (EReference)overwriteObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPackageWarningObjects() {
		return packageWarningObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageWarningObjects_Abapgitobjects() {
		return (EReference)packageWarningObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOverwriteObject() {
		return overwriteObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOverwriteObject_Action() {
		return (EAttribute)overwriteObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getOverwriteObject_ActionDescription() {
		return (EAttribute)overwriteObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IAgitpullmodifiedobjectsFactory getAgitpullmodifiedobjectsFactory() {
		return (IAgitpullmodifiedobjectsFactory)getEFactoryInstance();
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
		abapGitPullModifiedObjectsEClass = createEClass(ABAP_GIT_PULL_MODIFIED_OBJECTS);
		createEReference(abapGitPullModifiedObjectsEClass, ABAP_GIT_PULL_MODIFIED_OBJECTS__OVERWRITE_OBJECTS);
		createEReference(abapGitPullModifiedObjectsEClass, ABAP_GIT_PULL_MODIFIED_OBJECTS__PACKAGE_WARNING_OBJECTS);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ABAPGITPULLMODIFIEDOBJECTS);

		overwriteObjectsEClass = createEClass(OVERWRITE_OBJECTS);
		createEReference(overwriteObjectsEClass, OVERWRITE_OBJECTS__ABAPGITOBJECTS);

		packageWarningObjectsEClass = createEClass(PACKAGE_WARNING_OBJECTS);
		createEReference(packageWarningObjectsEClass, PACKAGE_WARNING_OBJECTS__ABAPGITOBJECTS);

		overwriteObjectEClass = createEClass(OVERWRITE_OBJECT);
		createEAttribute(overwriteObjectEClass, OVERWRITE_OBJECT__ACTION);
		createEAttribute(overwriteObjectEClass, OVERWRITE_OBJECT__ACTION_DESCRIPTION);
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
		IAdtCorePackage theAdtCorePackage = (IAdtCorePackage)EPackage.Registry.INSTANCE.getEPackage(IAdtCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		overwriteObjectEClass.getESuperTypes().add(theAdtCorePackage.getAdtObjectReference());

		// Initialize classes, features, and operations; add parameters
		initEClass(abapGitPullModifiedObjectsEClass, IAbapGitPullModifiedObjects.class, "AbapGitPullModifiedObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbapGitPullModifiedObjects_OverwriteObjects(), this.getOverwriteObjects(), null, "overwriteObjects", null, 0, 1, IAbapGitPullModifiedObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitPullModifiedObjects_PackageWarningObjects(), this.getPackageWarningObjects(), null, "packageWarningObjects", null, 0, 1, IAbapGitPullModifiedObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, IDocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Abapgitpullmodifiedobjects(), this.getAbapGitPullModifiedObjects(), null, "abapgitpullmodifiedobjects", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(overwriteObjectsEClass, IOverwriteObjects.class, "OverwriteObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOverwriteObjects_Abapgitobjects(), this.getOverwriteObject(), null, "abapgitobjects", null, 0, -1, IOverwriteObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(packageWarningObjectsEClass, IPackageWarningObjects.class, "PackageWarningObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPackageWarningObjects_Abapgitobjects(), this.getOverwriteObject(), null, "abapgitobjects", null, 0, -1, IPackageWarningObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(overwriteObjectEClass, IOverwriteObject.class, "OverwriteObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOverwriteObject_Action(), ecorePackage.getEString(), "action", null, 0, 1, IOverwriteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOverwriteObject_ActionDescription(), ecorePackage.getEString(), "actionDescription", null, 0, 1, IOverwriteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (abapGitPullModifiedObjectsEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "abapgitpullmodifiedobjects"
		   });
		addAnnotation
		  (getAbapGitPullModifiedObjects_OverwriteObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "overwriteObjects"
		   });
		addAnnotation
		  (getAbapGitPullModifiedObjects_PackageWarningObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "packageWarningObjects"
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
		  (getDocumentRoot_Abapgitpullmodifiedobjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitpullmodifiedobjects"
		   });
		addAnnotation
		  (overwriteObjectsEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "overwriteObjects"
		   });
		addAnnotation
		  (getOverwriteObjects_Abapgitobjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitobjects"
		   });
		addAnnotation
		  (packageWarningObjectsEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "packageWarningObjects"
		   });
		addAnnotation
		  (getPackageWarningObjects_Abapgitobjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitobjects"
		   });
		addAnnotation
		  (overwriteObjectEClass,
		   source,
		   new String[] {
			   "kind", "elementOnly",
			   "name", "overwriteObject"
		   });
		addAnnotation
		  (getOverwriteObject_Action(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getOverwriteObject_ActionDescription(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace"
		   });
	}

} //AgitpullmodifiedobjectsPackageImpl
