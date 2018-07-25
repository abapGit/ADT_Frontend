package org.abapgit.adt;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.eclipse.core.resources.IProject;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonArray;
import org.eclipse.json.provisonnal.com.eclipsesource.json.JsonObject;

import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

class REST {

	private static final String ABAPGIT_URI = "/sap/bc/adt/abapgit/repos";

	// TODO, use discovery service and proper templates/parameters
	// TODO, split class?

	private static class XMLResult {
		private Map<String, Collection<String>> map;

		XMLResult() {
			map = new HashMap<String, Collection<String>>();
		}

		public void add(String path, String value) {
			Collection<String> coll = this.map.get(path);
			if (coll == null) {
				coll = new LinkedList<String>();
				coll.add(value);
				this.map.put(path, coll);
			} else {
				coll.add(value);
			}
		}

		public String findSingle(String path) {
			return this.map.get(path).toArray(new String[0])[0];
		}

		public String[] findAll(String path) {
			return this.map.get(path).toArray(new String[0]);
		}
	}

	private static IResponse getURL(String URL) {
		return findResource(URL).get(null, IResponse.class);
	}
	
	private static JsonArray getResponse(){
		
		String jsonString = "{ \"repositories\": [ { \"package\": \"$AGIT_DEMO\", \"url\": \"https://github.com/OleksiiMalikov/ADT_Frontend\", \"branch\": \"refs/heads/master\", \"user\": \"_SAPD069613\", \"lastcommit\": \"18.08.18\" }, { \"package\": \"$AGIT_TEST\", \"url\": \"https://github.com/OleksiiMalikov/ADT_Frontend\", \"branch\": \"refs/heads/development\", \"user\": \"_SAPD069614\", \"lastcommit\": \"17.07.17\" }, { \"package\": \"$AGIT_EXAMPLE\", \"url\": \"https://github.com/OleksiiMalikov/ADT_Frontend\", \"branch\": \"refs/heads/feature3\", \"user\": \"_SAPD069615\", \"lastcommit\": \"16.06.16\" } ] }";
		JsonObject responseObject = JsonObject.readFrom(jsonString);
		JsonArray responseArray = responseObject.get( "repositories" ).asArray();
        
		return responseArray;
	}

	private static IResponse postURL(String URL, String body) {
			return findResource(URL).post(null, IResponse.class, body);

	}

	private static IRestResource findResource(String URL) {
		IAbapProject abapProject = findProject();

		String destination = abapProject.getDestinationId();
		URI abapGitUri = URI.create(URL);

		return AdtRestResourceFactory.createRestResourceFactory().createResourceWithStatelessSession(abapGitUri,
				destination);
	}

	public static Repository getRepository(String key) {
		final IResponse response = getURL(ABAPGIT_URI + "/" + key);

		Repository repo = null;
		try {
			repo = parseGetRepository(response);
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error parsing!");
		}

		return repo;
	}

	private static Repository parseGetRepository(IResponse response) throws IOException, XMLStreamException {
		int responseStatus = response.getStatus();
		if (responseStatus != HttpURLConnection.HTTP_OK) {
			// TODO, error
			System.out.println("http not ok");
		}

		InputStream responseContent = response.getBody().getContent();
		XMLResult xml = parseXML(responseContent);

		Repository repo = new Repository(xml.findSingle("/abap/values/ROOT/KEY"),
				xml.findSingle("/abap/values/ROOT/URL"), xml.findSingle("/abap/values/ROOT/BRANCH"), xml.findSingle("/abap/values/ROOT/PACKAGE"), xml.findSingle("/abap/values/ROOT/COMMITS"));
 		return repo;
	}

	private static List<Repository> parseListRepositories(IResponse response) throws IOException, XMLStreamException {
		int responseStatus = response.getStatus();
		if (responseStatus != HttpURLConnection.HTTP_OK) {
			// TODO, error
			System.out.println("http not ok");
		}

		InputStream responseContent = response.getBody().getContent();

		XMLResult xml = parseXML(responseContent);

		String[] keys = xml.findAll("/repositories/repository/key");
		String[] packages = xml.findAll("/repositories/repository/package");
		String[] urls = xml.findAll("/repositories/repository/url");
		String[] branches = xml.findAll("/repositories/repository/branch_name");
		String[] users = xml.findAll("/repositories/repository/created_by");
		String[] timestamps = xml.findAll("/repositories/repository/created_at");
		

		List<Repository> list = new ArrayList<Repository>();
 		for (int i = 0; i < keys.length; i++) {
			list.add(new Repository(packages[i], urls[i], branches[i], users[i], timestamps[i]));
 		}

		return list;
	}
	
//	private static List<Repository> parseJsonListRepositories(JsonArray responseObject){
//
//		List<Repository> list = new ArrayList<Repository>();
//		for (int i=0; i<responseObject.size(); i++) {
//								
//			JsonObject nestedObject = responseObject.get( i ).asObject();
//			String o_package = nestedObject.get( "package" ).asString();
//			String o_url = nestedObject.get( "url" ).asString();
//			String o_branch = nestedObject.get( "branch" ).asString();
//			String o_user = nestedObject.get( "user" ).asString();
//			String o_lastcommit = nestedObject.get( "lastcommit" ).asString();
//			
//			Repository Repo = new Repository(o_package, o_url, o_branch, o_user, o_lastcommit);
//			list.add(Repo);
//			
//		}
//
//		return list;
//	}

	private static XMLResult parseXML(InputStream stream) throws XMLStreamException {
		XMLResult result = new XMLResult();
		Stack<String> stack = new Stack<String>();

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = inputFactory.createXMLEventReader(stream);

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			int eventType = event.getEventType();

			switch (eventType) {
			case XMLStreamConstants.START_ELEMENT:
				StartElement startElement = event.asStartElement();
				stack.push(startElement.getName().getLocalPart());
				break;
			case XMLStreamConstants.END_ELEMENT:
				stack.pop();
				break;
			case XMLStreamConstants.CHARACTERS:
				String value = event.asCharacters().getData();

				String path = "";
				for (int i = 0; i < stack.size(); i++) {
					path = path + "/" + stack.get(i);
				}

				result.add(path, value);
				break;
			default:
				break;
			}
		}

		return result;
	}

	public static List<Repository> listRepositories() {

		
		final IResponse response = getURL(ABAPGIT_URI);
	
		List<Repository> list = null;
		try {
			list = parseListRepositories(response);
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error parsing!");
		}
		
//		JsonArray responseObject = null;
//		responseObject = getResponse();
//		
//		List<Repository> list = null;		
//		list = parseJsonListRepositories(responseObject);	
		
		return list;
	}

	private static IAbapProject findProject() {
		// TODO how to find the right project?
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();
		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

//		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(abapProject.getDestinationData(),
//				PlatformUI.getWorkbench().getProgressService());

		return abapProject;
	}

	public static void create(String url, String branch, String devclass, String user, String pwd, String trname) {
		// quick and dirty
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<asx:abap xmlns:asx=\"http://www.sap.com/abapxml\" version=\"1.0\">" + "<asx:values>" + "<ROOT>"
				+ "<URL>" + url + "</URL>" + "<BRANCH_NAME>" + branch + "</BRANCH_NAME>" + "<PACKAGE>" + devclass
				+ "</PACKAGE>" + "<USER>" + user + "</USER>" + "<PWD>" + pwd + "</PWD>" + "<TR_NAME>" + trname + "</TR_NAME>" + "</ROOT>" + "</asx:values></asx:abap>";

//		postURL(ABAPGIT_URI, xml);
		
//		System.out.print(xml);
		IResponse resp = postURL(ABAPGIT_URI, xml);
//		resp.getBody();
		System.out.print(resp.getHeaders());
		System.out.print(resp.getStatus());
		System.out.print(resp.getStatusLine());
		System.out.print(resp.getErrorInfo());
		System.out.print(resp.getReasonPhrase());
		
	}

	public static void pull(String key) {
//		postURL(ABAPGIT_URI + "/" + key + "/pull", "");
	}

}
