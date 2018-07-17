package org.abapgit.adt.core;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Repository {

	private String User;
	private String URL;
	private String Package;
	private String LastCommit;

	public static List<Repository> list() {
//		return REST.listRepositories();
		
        ArrayList<Repository> repositories = new ArrayList<Repository>();
        // Image here some fancy database access to read the repositories and to
        // put them into the model
        repositories.add(new Repository("$AGIT_DEMO", "https://github.com/OleksiiMalikov/ADT_Frontend", "_SAPD069613", "18.08.18"));
        repositories.add(new Repository("$TEST1", "https://github.com/abapGit/ADT_Backend", "_SAPD069614", "12.02.17"));
        repositories.add(new Repository("$DEMO_ABAPGIT", "https://github.com/abapGit/online_installer", "_SAPD069615", "07.04.18"));
        
		return repositories;
	}


// JSON Object import
	
//
//	public static void create(String url, String branch, String Package) {
//		REST.create(url, branch, Package);
//	}
	
//	public static Repository get(String key) {
//		
//		//return REST.getRepository(key);
//		
//	}
	
	Repository(String Package, String URL, String User, String LastCommit) {
		this.Package = Package;
		this.URL = URL;
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
	
	public String getLastCommit() {
		return LastCommit;
	}

	public String getPackage() {
		return Package;
	}

	public String toString() {
		return getUser() + " " + getPackage() + " " + getURL();
	}
}
