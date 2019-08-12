/**
 */
package org.abapgit.adt.backend.model.abapgitstaging.impl;

import org.abapgit.adt.backend.model.abapgitstaging.IAbapGitCommitMessage;
import org.abapgit.adt.backend.model.abapgitstaging.IAbapgitstagingPackage;
import org.abapgit.adt.backend.model.abapgitstaging.IAuthor;
import org.abapgit.adt.backend.model.abapgitstaging.ICommitter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abap Git Commit Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.abapgit.adt.backend.model.abapgitstaging.impl.AbapGitCommitMessageImpl#getCommitter <em>Committer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbapGitCommitMessageImpl extends MinimalEObjectImpl.Container implements IAbapGitCommitMessage {
	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected IAuthor author;

	/**
	 * The cached value of the '{@link #getCommitter() <em>Committer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitter()
	 * @generated
	 * @ordered
	 */
	protected ICommitter committer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbapGitCommitMessageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAbapgitstagingPackage.Literals.ABAP_GIT_COMMIT_MESSAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = this.message;
		this.message = newMessage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__MESSAGE, oldMessage, this.message));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAuthor getAuthor() {
		return this.author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthor(IAuthor newAuthor, NotificationChain msgs) {
		IAuthor oldAuthor = this.author;
		this.author = newAuthor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR, oldAuthor, newAuthor);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(IAuthor newAuthor) {
		if (newAuthor != this.author) {
			NotificationChain msgs = null;
			if (this.author != null) {
				msgs = ((InternalEObject)this.author).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR, null, msgs);
			}
			if (newAuthor != null) {
				msgs = ((InternalEObject)newAuthor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR, null, msgs);
			}
			msgs = basicSetAuthor(newAuthor, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR, newAuthor, newAuthor));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICommitter getCommitter() {
		return this.committer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommitter(ICommitter newCommitter, NotificationChain msgs) {
		ICommitter oldCommitter = this.committer;
		this.committer = newCommitter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER, oldCommitter, newCommitter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommitter(ICommitter newCommitter) {
		if (newCommitter != this.committer) {
			NotificationChain msgs = null;
			if (this.committer != null) {
				msgs = ((InternalEObject)this.committer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER, null, msgs);
			}
			if (newCommitter != null) {
				msgs = ((InternalEObject)newCommitter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER, null, msgs);
			}
			msgs = basicSetCommitter(newCommitter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER, newCommitter, newCommitter));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR:
				return basicSetAuthor(null, msgs);
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER:
				return basicSetCommitter(null, msgs);
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
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__MESSAGE:
				return getMessage();
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR:
				return getAuthor();
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER:
				return getCommitter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__MESSAGE:
				setMessage((String)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR:
				setAuthor((IAuthor)newValue);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER:
				setCommitter((ICommitter)newValue);
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
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR:
				setAuthor((IAuthor)null);
				return;
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER:
				setCommitter((ICommitter)null);
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
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__MESSAGE:
				return MESSAGE_EDEFAULT == null ? this.message != null : !MESSAGE_EDEFAULT.equals(this.message);
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__AUTHOR:
				return this.author != null;
			case IAbapgitstagingPackage.ABAP_GIT_COMMIT_MESSAGE__COMMITTER:
				return this.committer != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (message: "); //$NON-NLS-1$
		result.append(this.message);
		result.append(')');
		return result.toString();
	}

} //AbapGitCommitMessageImpl
