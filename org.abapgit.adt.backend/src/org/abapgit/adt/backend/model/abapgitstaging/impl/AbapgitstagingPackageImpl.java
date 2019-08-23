/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import com.sap.adt.tools.core.model.adtcore.IAdtCorePackage;

import com.sap.adt.tools.core.model.atom.IAtomPackage;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitFile;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitObject;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingFactory;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.abapgit.adt.backend.model.abapgitstaging.IDocumentRoot;
import org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.namespace.XMLNamespacePackage;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AbapgitstagingPackageImpl extends EPackageImpl implements IAbapgitstagingPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abapGitStagingEClass = null;

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
	private EClass abapGitCommitMessageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abapGitObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abapGitFileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass committerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unstagedObjectsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stagedObjectsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ignoredObjectsEClass = null;

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
	 * @see org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AbapgitstagingPackageImpl() {
		super(eNS_URI, IAbapgitstagingFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IAbapgitstagingPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IAbapgitstagingPackage init() {
		if (isInited) return (IAbapgitstagingPackage)EPackage.Registry.INSTANCE.getEPackage(IAbapgitstagingPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAbapgitstagingPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AbapgitstagingPackageImpl theAbapgitstagingPackage = registeredAbapgitstagingPackage instanceof AbapgitstagingPackageImpl ? (AbapgitstagingPackageImpl)registeredAbapgitstagingPackage : new AbapgitstagingPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IAdtCorePackage.eINSTANCE.eClass();
		IAtomPackage.eINSTANCE.eClass();
		XMLNamespacePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAbapgitstagingPackage.createPackageContents();

		// Initialize created meta-data
		theAbapgitstagingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAbapgitstagingPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IAbapgitstagingPackage.eNS_URI, theAbapgitstagingPackage);
		return theAbapgitstagingPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbapGitStaging() {
		return abapGitStagingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitStaging_Link() {
		return (EReference)abapGitStagingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitStaging_UnstagedObjects() {
		return (EReference)abapGitStagingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitStaging_StagedObjects() {
		return (EReference)abapGitStagingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitStaging_IgnoredObjects() {
		return (EReference)abapGitStagingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitStaging_CommitMessage() {
		return (EReference)abapGitStagingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Abapgitstaging() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbapGitCommitMessage() {
		return abapGitCommitMessageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitCommitMessage_Message() {
		return (EAttribute)abapGitCommitMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitCommitMessage_Author() {
		return (EReference)abapGitCommitMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitCommitMessage_Committer() {
		return (EReference)abapGitCommitMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbapGitObject() {
		return abapGitObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitObject_Wbkey() {
		return (EAttribute)abapGitObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitObject_Version() {
		return (EAttribute)abapGitObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbapGitObject_Files() {
		return (EReference)abapGitObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbapGitFile() {
		return abapGitFileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitFile_Name() {
		return (EAttribute)abapGitFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitFile_Path() {
		return (EAttribute)abapGitFileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitFile_LocalState() {
		return (EAttribute)abapGitFileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbapGitFile_RemoteState() {
		return (EAttribute)abapGitFileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthor() {
		return authorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthor_Name() {
		return (EAttribute)authorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthor_Email() {
		return (EAttribute)authorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommitter() {
		return committerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommitter_Name() {
		return (EAttribute)committerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommitter_Email() {
		return (EAttribute)committerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnstagedObjects() {
		return unstagedObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnstagedObjects_Abapgitobject() {
		return (EReference)unstagedObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStagedObjects() {
		return stagedObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStagedObjects_Abapgitobject() {
		return (EReference)stagedObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIgnoredObjects() {
		return ignoredObjectsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIgnoredObjects_Abapgitobject() {
		return (EReference)ignoredObjectsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapgitstagingFactory getAbapgitstagingFactory() {
		return (IAbapgitstagingFactory)getEFactoryInstance();
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
		abapGitStagingEClass = createEClass(ABAP_GIT_STAGING);
		createEReference(abapGitStagingEClass, ABAP_GIT_STAGING__LINK);
		createEReference(abapGitStagingEClass, ABAP_GIT_STAGING__UNSTAGED_OBJECTS);
		createEReference(abapGitStagingEClass, ABAP_GIT_STAGING__STAGED_OBJECTS);
		createEReference(abapGitStagingEClass, ABAP_GIT_STAGING__IGNORED_OBJECTS);
		createEReference(abapGitStagingEClass, ABAP_GIT_STAGING__COMMIT_MESSAGE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ABAPGITSTAGING);

		abapGitCommitMessageEClass = createEClass(ABAP_GIT_COMMIT_MESSAGE);
		createEAttribute(abapGitCommitMessageEClass, ABAP_GIT_COMMIT_MESSAGE__MESSAGE);
		createEReference(abapGitCommitMessageEClass, ABAP_GIT_COMMIT_MESSAGE__AUTHOR);
		createEReference(abapGitCommitMessageEClass, ABAP_GIT_COMMIT_MESSAGE__COMMITTER);

		abapGitObjectEClass = createEClass(ABAP_GIT_OBJECT);
		createEAttribute(abapGitObjectEClass, ABAP_GIT_OBJECT__WBKEY);
		createEAttribute(abapGitObjectEClass, ABAP_GIT_OBJECT__VERSION);
		createEReference(abapGitObjectEClass, ABAP_GIT_OBJECT__FILES);

		abapGitFileEClass = createEClass(ABAP_GIT_FILE);
		createEAttribute(abapGitFileEClass, ABAP_GIT_FILE__NAME);
		createEAttribute(abapGitFileEClass, ABAP_GIT_FILE__PATH);
		createEAttribute(abapGitFileEClass, ABAP_GIT_FILE__LOCAL_STATE);
		createEAttribute(abapGitFileEClass, ABAP_GIT_FILE__REMOTE_STATE);

		authorEClass = createEClass(AUTHOR);
		createEAttribute(authorEClass, AUTHOR__NAME);
		createEAttribute(authorEClass, AUTHOR__EMAIL);

		committerEClass = createEClass(COMMITTER);
		createEAttribute(committerEClass, COMMITTER__NAME);
		createEAttribute(committerEClass, COMMITTER__EMAIL);

		unstagedObjectsEClass = createEClass(UNSTAGED_OBJECTS);
		createEReference(unstagedObjectsEClass, UNSTAGED_OBJECTS__ABAPGITOBJECT);

		stagedObjectsEClass = createEClass(STAGED_OBJECTS);
		createEReference(stagedObjectsEClass, STAGED_OBJECTS__ABAPGITOBJECT);

		ignoredObjectsEClass = createEClass(IGNORED_OBJECTS);
		createEReference(ignoredObjectsEClass, IGNORED_OBJECTS__ABAPGITOBJECT);
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
		IAtomPackage theAtomPackage = (IAtomPackage)EPackage.Registry.INSTANCE.getEPackage(IAtomPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		IAdtCorePackage theAdtCorePackage = (IAdtCorePackage)EPackage.Registry.INSTANCE.getEPackage(IAdtCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		abapGitObjectEClass.getESuperTypes().add(theAdtCorePackage.getAdtObjectReference());

		// Initialize classes, features, and operations; add parameters
		initEClass(abapGitStagingEClass, IAbapGitStaging.class, "AbapGitStaging", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAbapGitStaging_Link(), theAtomPackage.getAtomLink(), null, "link", null, 1, 1, IAbapGitStaging.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitStaging_UnstagedObjects(), this.getUnstagedObjects(), null, "unstagedObjects", null, 0, 1, IAbapGitStaging.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitStaging_StagedObjects(), this.getStagedObjects(), null, "stagedObjects", null, 0, 1, IAbapGitStaging.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitStaging_IgnoredObjects(), this.getIgnoredObjects(), null, "ignoredObjects", null, 0, 1, IAbapGitStaging.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitStaging_CommitMessage(), this.getAbapGitCommitMessage(), null, "commitMessage", null, 1, 1, IAbapGitStaging.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, IDocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Abapgitstaging(), this.getAbapGitStaging(), null, "abapgitstaging", null, 0, 1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(abapGitCommitMessageEClass, IAbapGitCommitMessage.class, "AbapGitCommitMessage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbapGitCommitMessage_Message(), theXMLTypePackage.getString(), "message", null, 0, 1, IAbapGitCommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitCommitMessage_Author(), this.getAuthor(), null, "author", null, 1, 1, IAbapGitCommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitCommitMessage_Committer(), this.getCommitter(), null, "committer", null, 1, 1, IAbapGitCommitMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abapGitObjectEClass, IAbapGitObject.class, "AbapGitObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbapGitObject_Wbkey(), theXMLTypePackage.getString(), "wbkey", null, 0, 1, IAbapGitObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitObject_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, IAbapGitObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbapGitObject_Files(), this.getAbapGitFile(), null, "files", null, 0, -1, IAbapGitObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abapGitFileEClass, IAbapGitFile.class, "AbapGitFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbapGitFile_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, IAbapGitFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitFile_Path(), theXMLTypePackage.getString(), "path", null, 0, 1, IAbapGitFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitFile_LocalState(), ecorePackage.getEChar(), "localState", null, 0, 1, IAbapGitFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbapGitFile_RemoteState(), ecorePackage.getEChar(), "remoteState", null, 0, 1, IAbapGitFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authorEClass, IAuthor.class, "Author", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthor_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, IAuthor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthor_Email(), theXMLTypePackage.getString(), "email", null, 0, 1, IAuthor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(committerEClass, ICommitter.class, "Committer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommitter_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, ICommitter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommitter_Email(), theXMLTypePackage.getString(), "email", null, 0, 1, ICommitter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unstagedObjectsEClass, IUnstagedObjects.class, "UnstagedObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnstagedObjects_Abapgitobject(), this.getAbapGitObject(), null, "abapgitobject", null, 0, -1, IUnstagedObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stagedObjectsEClass, IStagedObjects.class, "StagedObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStagedObjects_Abapgitobject(), this.getAbapGitObject(), null, "abapgitobject", null, 0, -1, IStagedObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ignoredObjectsEClass, IIgnoredObjects.class, "IgnoredObjects", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIgnoredObjects_Abapgitobject(), this.getAbapGitObject(), null, "abapgitobject", null, 0, -1, IIgnoredObjects.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (abapGitStagingEClass,
		   source,
		   new String[] {
			   "name", "abapgitstaging",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAbapGitStaging_Link(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "link"
		   });
		addAnnotation
		  (getAbapGitStaging_UnstagedObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "unstaged_objects"
		   });
		addAnnotation
		  (getAbapGitStaging_StagedObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "staged_objects"
		   });
		addAnnotation
		  (getAbapGitStaging_IgnoredObjects(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "ignored_objects"
		   });
		addAnnotation
		  (getAbapGitStaging_CommitMessage(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgit_comment"
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
		  (getDocumentRoot_Abapgitstaging(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "abapgitstaging",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (abapGitCommitMessageEClass,
		   source,
		   new String[] {
			   "name", "abapgit_comment",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAbapGitCommitMessage_Message(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "comment"
		   });
		addAnnotation
		  (getAbapGitCommitMessage_Author(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "author"
		   });
		addAnnotation
		  (getAbapGitCommitMessage_Committer(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "committer"
		   });
		addAnnotation
		  (abapGitObjectEClass,
		   source,
		   new String[] {
			   "name", "abapgitobject",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAbapGitObject_Wbkey(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "wbkey"
		   });
		addAnnotation
		  (getAbapGitObject_Version(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "version"
		   });
		addAnnotation
		  (getAbapGitObject_Files(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitfile"
		   });
		addAnnotation
		  (abapGitFileEClass,
		   source,
		   new String[] {
			   "name", "abapgitfile",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAbapGitFile_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "name"
		   });
		addAnnotation
		  (getAbapGitFile_Path(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "path"
		   });
		addAnnotation
		  (getAbapGitFile_LocalState(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "localState"
		   });
		addAnnotation
		  (getAbapGitFile_RemoteState(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "remoteState"
		   });
		addAnnotation
		  (authorEClass,
		   source,
		   new String[] {
			   "name", "author",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAuthor_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "name"
		   });
		addAnnotation
		  (getAuthor_Email(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "email"
		   });
		addAnnotation
		  (committerEClass,
		   source,
		   new String[] {
			   "name", "committer",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getCommitter_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "name"
		   });
		addAnnotation
		  (getCommitter_Email(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "namespace", "##targetNamespace",
			   "name", "email"
		   });
		addAnnotation
		  (unstagedObjectsEClass,
		   source,
		   new String[] {
			   "name", "unstaged_objects",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getUnstagedObjects_Abapgitobject(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitobject"
		   });
		addAnnotation
		  (stagedObjectsEClass,
		   source,
		   new String[] {
			   "name", "staged_objects",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getStagedObjects_Abapgitobject(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitobject"
		   });
		addAnnotation
		  (ignoredObjectsEClass,
		   source,
		   new String[] {
			   "name", "ignored_objects",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getIgnoredObjects_Abapgitobject(),
		   source,
		   new String[] {
			   "kind", "element",
			   "namespace", "##targetNamespace",
			   "name", "abapgitobject"
		   });
	}

} //AbapgitstagingPackageImpl
