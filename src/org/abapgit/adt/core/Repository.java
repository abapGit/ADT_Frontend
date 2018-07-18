package org.abapgit.adt.core;

import java.util.List;

public class Repository {

	private String User;
	private String URL;
	private String Branch;
	private String Package;
	private String LastCommit;

	public static List<Repository> list() {
		return REST.listRepositories();		
	}

//
//	public static void create(String url, String branch, String Package) {
//		REST.create(url, branch, Package);
//	}
	
//	public static Repository get(String key) {
//		
//		//return REST.getRepository(key);
//		
//	}
	
	Repository(String Package, String URL, String Branch, String User, String LastCommit) {
		this.Package = Package;
		this.URL = URL;
		this.Branch = Branch;
		this.User = User;
		this.LastCommit = LastCommit;
	}

//	public void pull() {
//		REST.pull(this.User);
//	}
	
	public String getURL() {
		return URL;
	}

	public String getUser() {
		return User;
	}
	
	public String getBranch() {
		return Branch;
	}
	
	public String getLastCommit() {
		return LastCommit;
	}

	public String getPackage() {
		return Package;
	}

	public String toString() {
		return getUser() + " " + getPackage() + " " + getURL() + " " + getBranch() + " " + getLastCommit();
	}
}
