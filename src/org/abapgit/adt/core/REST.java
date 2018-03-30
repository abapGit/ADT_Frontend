package org.abapgit.adt.core;

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
import org.eclipse.ui.PlatformUI;

import com.sap.adt.communication.message.IResponse;
import com.sap.adt.communication.resources.AdtRestResourceFactory;
import com.sap.adt.communication.resources.IRestResource;
import com.sap.adt.destinations.ui.logon.AdtLogonServiceUIFactory;
import com.sap.adt.tools.core.project.AdtProjectServiceFactory;
import com.sap.adt.tools.core.project.IAbapProject;

class REST {

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

	private static final String ABAPGIT_URI = "/sap/bc/adt/abapgit/repos";

	private static IResponse getURL(String URL) {
		return findResource(URL).get(null, IResponse.class);
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
				xml.findSingle("/abap/values/ROOT/URL"), xml.findSingle("/abap/values/ROOT/PACKAGE"));
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

		String[] keys = xml.findAll("/abap/values/ROOT/item/KEY");
		String[] urls = xml.findAll("/abap/values/ROOT/item/URL");
		String[] packages = xml.findAll("/abap/values/ROOT/item/PACKAGE");

		List<Repository> list = new ArrayList<Repository>();
		for (int i = 0; i < keys.length; i++) {
			list.add(new Repository(keys[i], urls[i], packages[i]));
		}

		return list;
	}

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

		return list;
	}

	private static IAbapProject findProject() {
		// TODO how to find the right project?
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();
		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());

		return abapProject;
	}

	public static void create(String url, String branch, String devclass) {
		// quick and dirty
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<asx:abap xmlns:asx=\"http://www.sap.com/abapxml\" version=\"1.0\">" + "<asx:values>" + "<ROOT>"
				+ "<URL>" + url + "</URL>" + "<BRANCH_NAME>" + branch + "</BRANCH_NAME>" + "<PACKAGE>" + devclass
				+ "</PACKAGE>" + "</ROOT>" + "</asx:values></asx:abap>";

		postURL(ABAPGIT_URI, xml);
	}

	public static void pull(String key) {
		postURL(ABAPGIT_URI + "/" + key + "/pull", "");
	}

}
