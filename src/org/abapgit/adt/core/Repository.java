package org.abapgit.adt.core;

public class Repository {

	private String key;
	private String URL;
	private String devclass;

	public static Repository[] list() {
		return REST.listRepositories();
	}

	public static Repository get(String key) {
		return REST.getRepository(key);
	}

	Repository(String key, String URL, String devclass) {
		this.key = key;
		this.URL = URL;
		this.devclass = devclass;
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

}
