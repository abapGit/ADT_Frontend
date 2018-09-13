package org.abapgit.adt.backend;

public interface IRepository {
	public String getKey();

	public String getUrl();

	public String getRemoteUser();

	public String getBranch();

	public String getFirstCommit();

	public String getCreatedBy();

	public String getPackage();

	public String getRemotePassword();

	public String getTransportRequest();

	public void setKey(String key);

	public void setUrl(String url);

	public void setRemoteUser(String user);

	public void setBranch(String branch);

	public void setFirstCommit(String firstCommit);

	public void setCreatedBy(String createdBy);

	public void setPackage(String pckg);

	public void setPassword(String password);

	public void setTransportRequest(String transportRequest);
}
