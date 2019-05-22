package org.abapgit.adt.backend;

import java.util.List;

public interface IObject {

	public String getObjType();

	public String getObjName();

	public String getObjStatus();

	public String getPackage();

	public String getMsgType();

	public String getMsgText();

	public List<IObject> listChildObjects();

	public void resetChildren();

	public Object getParent();

	public void setObjType(String type);

	public void setObjName(String name);

	public void setObjStatus(String status);

	public void setPackage(String pckg);

	public void setMsgType(String msg_type);

	public void setMsgText(String msg_text);

	public void addChild(IObject abapLogObject);

	public int countChildren();

}
