package org.abapgit.adt.backend.internal;

public class ApackGitManifestRequest implements IApackGitManifestRequest {

	private String url;
	private String branch;
	private String user;
	private String password;

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getBranch() {
		return this.branch;
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
