package org.abapgit.adt.core;

import java.util.List;

public class Repository {

	private String key;
	private String URL;
	private String devclass;

	public static List<Repository> list() {
		return REST.listRepositories();
	}

	public static void create(String url, String branch, String devclass) {
		REST.create(url, branch, devclass);
	}
	
	public static Repository get(String key) {
		return REST.getRepository(key);
	}

	Repository(String key, String URL, String devclass) {
		this.key = key;
		this.URL = URL;
		this.devclass = devclass;
	}

	public void pull() {
		REST.pull(this.key);
	}
	
	public String getURL() {
		return URL;
	}

	public String getKey() {
		return key;
	}

	public String getDevclass() {
		return devclass;
	}

	public String toString() {
		return getKey() + " " + getDevclass() + " " + getURL();
	}
}
