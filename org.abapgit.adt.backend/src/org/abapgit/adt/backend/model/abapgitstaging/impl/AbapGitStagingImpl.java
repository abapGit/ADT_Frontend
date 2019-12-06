/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import com.sap.adt.tools.core.model.atom.IAtomLink;

import java.util.Collection;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitStaging;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.abapgit.adt.backend.model.abapgitstaging.IIgnoredObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IStagedObjects;
import org.abapgit.adt.backend.model.abapgitstaging.IUnstagedObjects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Git Staging</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl#getUnstagedObjects <em>Unstaged Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl#getStagedObjects <em>Staged Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl#getIgnoredObjects <em>Ignored Objects</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl#getCommitMessage <em>Commit Message</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitStagingImpl#getLinks <em>Links</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitStagingImpl extends MinimalEObjectImpl.Container implements IAbapGitStaging {
	/**
	 * The cached value of the '{@link #getUnstagedObjects() <em>Unstaged Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnstagedObjects()
	 * @generated
	 * @ordered
	 */
	protected IUnstagedObjects unstagedObjects;

	/**
	 * The cached value of the '{@link #getStagedObjects() <em>Staged Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStagedObjects()
	 * @generated
	 * @ordered
	 */
	protected IStagedObjects stagedObjects;

	/**
	 * The cached value of the '{@link #getIgnoredObjects() <em>Ignored Objects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIgnoredObjects()
	 * @generated
	 * @ordered
	 */
	protected IIgnoredObjects ignoredObjects;

	/**
	 * The cached value of the '{@link #getCommitMessage() <em>Commit Message</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitMessage()
	 * @generated
	 * @ordered
	 */
	protected IAbapGitCommitMessage commitMessage;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<IAtomLink> links;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitStagingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingPackage.Literals.ABAP_GIT_STAGING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IUnstagedObjects getUnstagedObjects() {
		return unstagedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnstagedObjects(IUnstagedObjects newUnstagedObjects, NotificationChain msgs) {
		IUnstagedObjects oldUnstagedObjects = unstagedObjects;
		unstagedObjects = newUnstagedObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS, oldUnstagedObjects, newUnstagedObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnstagedObjects(IUnstagedObjects newUnstagedObjects) {
		if (newUnstagedObjects != unstagedObjects) {
			NotificationChain msgs = null;
			if (unstagedObjects != null)
				msgs = ((InternalEObject)unstagedObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS, null, msgs);
			if (newUnstagedObjects != null)
				msgs = ((InternalEObject)newUnstagedObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS, null, msgs);
			msgs = basicSetUnstagedObjects(newUnstagedObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS, newUnstagedObjects, newUnstagedObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IStagedObjects getStagedObjects() {
		return stagedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStagedObjects(IStagedObjects newStagedObjects, NotificationChain msgs) {
		IStagedObjects oldStagedObjects = stagedObjects;
		stagedObjects = newStagedObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS, oldStagedObjects, newStagedObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStagedObjects(IStagedObjects newStagedObjects) {
		if (newStagedObjects != stagedObjects) {
			NotificationChain msgs = null;
			if (stagedObjects != null)
				msgs = ((InternalEObject)stagedObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS, null, msgs);
			if (newStagedObjects != null)
				msgs = ((InternalEObject)newStagedObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS, null, msgs);
			msgs = basicSetStagedObjects(newStagedObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS, newStagedObjects, newStagedObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IIgnoredObjects getIgnoredObjects() {
		return ignoredObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIgnoredObjects(IIgnoredObjects newIgnoredObjects, NotificationChain msgs) {
		IIgnoredObjects oldIgnoredObjects = ignoredObjects;
		ignoredObjects = newIgnoredObjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS, oldIgnoredObjects, newIgnoredObjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIgnoredObjects(IIgnoredObjects newIgnoredObjects) {
		if (newIgnoredObjects != ignoredObjects) {
			NotificationChain msgs = null;
			if (ignoredObjects != null)
				msgs = ((InternalEObject)ignoredObjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS, null, msgs);
			if (newIgnoredObjects != null)
				msgs = ((InternalEObject)newIgnoredObjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS, null, msgs);
			msgs = basicSetIgnoredObjects(newIgnoredObjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS, newIgnoredObjects, newIgnoredObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAbapGitCommitMessage getCommitMessage() {
		return commitMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommitMessage(IAbapGitCommitMessage newCommitMessage, NotificationChain msgs) {
		IAbapGitCommitMessage oldCommitMessage = commitMessage;
		commitMessage = newCommitMessage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE, oldCommitMessage, newCommitMessage);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommitMessage(IAbapGitCommitMessage newCommitMessage) {
		if (newCommitMessage != commitMessage) {
			NotificationChain msgs = null;
			if (commitMessage != null)
				msgs = ((InternalEObject)commitMessage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE, null, msgs);
			if (newCommitMessage != null)
				msgs = ((InternalEObject)newCommitMessage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE, null, msgs);
			msgs = basicSetCommitMessage(newCommitMessage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE, newCommitMessage, newCommitMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IAtomLink> getLinks() {
		if (links == null) {
			links = new EObjectContainmentEList<IAtomLink>(IAtomLink.class, this, IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS:
				return basicSetUnstagedObjects(null, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS:
				return basicSetStagedObjects(null, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS:
				return basicSetIgnoredObjects(null, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE:
				return basicSetCommitMessage(null, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS:
				return getUnstagedObjects();
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS:
				return getStagedObjects();
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS:
				return getIgnoredObjects();
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE:
				return getCommitMessage();
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS:
				return getLinks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS:
				setUnstagedObjects((IUnstagedObjects)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS:
				setStagedObjects((IStagedObjects)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS:
				setIgnoredObjects((IIgnoredObjects)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE:
				setCommitMessage((IAbapGitCommitMessage)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends IAtomLink>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS:
				setUnstagedObjects((IUnstagedObjects)null);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS:
				setStagedObjects((IStagedObjects)null);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS:
				setIgnoredObjects((IIgnoredObjects)null);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE:
				setCommitMessage((IAbapGitCommitMessage)null);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS:
				getLinks().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__UNSTAGED_OBJECTS:
				return unstagedObjects != null;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__STAGED_OBJECTS:
				return stagedObjects != null;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__IGNORED_OBJECTS:
				return ignoredObjects != null;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__COMMIT_MESSAGE:
				return commitMessage != null;
			case IAbapgitstagingPackage.ABAP_GIT_STAGING__LINKS:
				return links != null && !links.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AbapGitStagingImpl
