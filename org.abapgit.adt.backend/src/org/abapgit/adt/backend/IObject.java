package org.abapgit.adt.backend;

import java.util.List;

import com.sap.adt.tools.core.model.adtcore.IAdtObjectReference;

public interface IObject {

	public String getObjType();

	public String getObjName();

	public String getObjStatus();

	public String getPackage();

	public String getMsgType();

	public String getMsgText();

	public IAdtObjectReference getAdtObjRef();

	public List<IObject> listChildObjects();

	public void resetChildren();

	public Object getParent();

	public void setObjType(String type);

	public void setObjName(String name);

	public void setObjStatus(String status);

	public void setPackage(String pckg);

	public void setMsgType(String msg_type);

	public void setMsgText(String msg_text);

	public void setAdtObjRef(IAdtObjectReference adtObjRef);

	public void addChild(IObject abapLogObject);

	public int countChildren();

}
