package org.abapgit.adt.ui.internal.util;

import java.util.List;

import org.abapgit.adt.backend.model.abapObjects.IAbapObject;
import org.abapgit.adt.backend.model.abapObjects.IAbapObjects;

public interface IAbapGitObjLogService {

	/**
	 *
	 * @param node
	 *            IAbapObject abapGit log object node
	 * @return Returns the number of children of an AbapGit Log object node
	 */
	int countChildren(IAbapObject node);

	/**
	 *
	 * @param node
	 *            IAbapObject abapGit log Object node
	 * @return Returns the children of an AbapGit log object node
	 */

	List<IAbapObject> listChildObjects(IAbapObject node);

	/**
	 *
	 * @param deserializedAbapObjects
	 *            Deserialized abap log objects retrieved
	 * @return Returns abap log objects rendered to be displayed in
	 *         AbapGitDialogObjLog
	 */

	IAbapObjects renderObjLogInput(IAbapObjects deserializedAbapObjects);
}
