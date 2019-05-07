package org.abapgit.adt.backend.internal;

//import java.net.URI;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.abapgit.adt.backend.IObject;


public class AbapObject implements IObject {

	private String type;
	private String name;
	private String status;
	private String pckg;
	private String msg_type;
	private String msg_text;
//	private final Map<String, URI> links = new HashMap<>();

	private final List<IObject> abapLogObjectChildren = new ArrayList<IObject>();

	@Override
	public void addChild(IObject abapLogObject) {
		this.abapLogObjectChildren.add(abapLogObject);
	}

	@Override
	public String getObjType() {
		return this.type;
	}

	@Override
	public String getObjName() {
		return this.name;
	}

	@Override
	public String getObjStatus() {
		return this.status;
	}

	@Override
	public String getPackage() {
		return this.pckg;
	}

	@Override
	public String getMsgType() {
		return this.msg_type;
	}

	@Override
	public String getMsgText() {
		return this.msg_text;
	}

	@Override
	public void setObjType(String type) {
		this.type = type;
	}

	@Override
	public void setObjName(String name) {
		this.name = name;
	}

	@Override
	public void setObjStatus(String status) {
		this.status = status;
	}

	@Override
	public void setPackage(String pckg) {
		this.pckg = pckg;
	}

	@Override
	public void setMsgType(String msg_type) {
		this.msg_type = msg_type;
	}


	@Override
	public void setMsgText(String msg_text) {
		this.msg_text = msg_text;
	}

	@Override
	public List<IObject> listMessages() {
		return this.abapLogObjectChildren;
	}

	@Override
	public Object getParent() {

		return null;
	}

	@Override
	public void resetChildren() {
		this.abapLogObjectChildren.clear();
	}

}
