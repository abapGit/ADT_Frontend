package org.abapgit.adt.backend.internal;

public class ExternalRepositoryInfoRequest implements IExternalRepositoryInfoRequest {

	private String url;
	private String user;
	private String password;

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
