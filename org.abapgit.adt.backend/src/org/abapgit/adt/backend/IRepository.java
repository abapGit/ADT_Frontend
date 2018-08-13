package org.abapgit.adt.backend;

public interface IRepository {
	public String getKey();

	public String getUrl();

	public String getUser();

	public String getBranch();

	public String getFirstCommit();

	public String getPackage();

	public void setKey(String key);

	public void setUrl(String url);

	public void setUser(String user);

	public void setBranch(String branch);

	public void setFirstCommit(String firstCommit);

	public void setPackage(String pckg);
}
