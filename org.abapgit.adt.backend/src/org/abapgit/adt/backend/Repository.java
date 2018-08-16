package org.abapgit.adt.backend;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Repository implements IRepository {

	private String key;

	private String user;

	private String url;

	private String branch;

	private String pckg;
	private String firstCommit;

	private String password;

	private String transportRequest;

	public Repository() {

	}

	public Repository(String pckg, String url, String branch, String user, String firstCommit) {
		// this.key = key;
		this.pckg = pckg;
		this.url = url;
		this.branch = branch;
		this.user = user;
		this.firstCommit = firstCommit;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String getBranch() {
		return this.branch;
	}

	@Override
	public String getFirstCommit() {
		return this.firstCommit;
	}

	@Override
	public String getPackage() {
		return this.pckg;
	}

	@Override
	public String toString() {
		return "Repository [key=" + key + ", user=" + user + ", url=" + url + ", branch=" + branch + ", pckg=" + pckg
				+ ", firstCommit=" + firstCommit + ", password=" + password + ", transportRequest=" + transportRequest
				+ "]";
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public void setFirstCommit(String firstCommit) {
		this.firstCommit = firstCommit;
	}

	@Override
	public void setPackage(String pckg) {
		this.pckg = pckg;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setTransportRequest(String transportRequest) {
		this.transportRequest = transportRequest;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getTransportRequest() {
		return this.transportRequest;
	}
}
