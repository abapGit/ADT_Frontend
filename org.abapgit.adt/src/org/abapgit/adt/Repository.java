package org.abapgit.adt;

import java.util.List;

public class Repository {

	private String User;
	private String URL;
	private String Branch;
	private String Package;
	private String FirstCommit;

	public static List<Repository> list() {
		return REST.listRepositories();
	}

	public static void create(String url, String branch, String devclass, String user, String pwd, String trname) {
		REST.create(url, branch, devclass, user, pwd, trname);
	}
//	
//	public static Repository get(String key) {
//		return REST.getRepository(key);
//	}

	Repository(String Package, String URL, String Branch, String User, String FirstCommit) {
		this.Package = Package;
		this.URL = URL;
		this.Branch = Branch;
		this.User = User;
		this.FirstCommit = FirstCommit;
	}

	public void pull() {
		REST.pull(this.Package);
	}
	
	public String getURL() {
		return URL;
	}

	public String getUser() {
		return User;
	}
	
	public String getBranch() {
		return Branch;
	}
	
	public String getFirstCommit() {
		return FirstCommit;
	}

	public String getPackage() {
		return Package;
	}

	public String toString() {
		return getUser() + " " + getPackage() + " " + getURL() + " " + getBranch() + " " + getFirstCommit();
	}
}
