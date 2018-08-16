package org.abapgit.adt.backend;

public interface IRepository {
	public String getKey();

	public String getUrl();

	public String getUser();

	public String getBranch();

	public String getFirstCommit();

	public String getPackage();

	public String getPassword();

	public String getTransportRequest();

	public void setKey(String key);

	public void setUrl(String url);

	public void setUser(String user);

	public void setBranch(String branch);

	public void setFirstCommit(String firstCommit);

	public void setPackage(String pckg);

	public void setPassword(String password);

	public void setTransportRequest(String transportRequest);
}
