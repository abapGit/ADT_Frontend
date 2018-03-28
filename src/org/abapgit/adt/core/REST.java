package org.abapgit.adt.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
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

	private static class XMLResult {
		private Map<String, String> map;

		XMLResult() {
			// Note that this only contains one value per path
			map = new HashMap<String, String>();
		}

		public void add(String path, String value) {
			this.map.put(path, value);
		}

		public String find(String path) {
			return this.map.get(path);
		}
	}

	private static final String ABAPGIT_URI = "/sap/bc/adt/abapgit/repos/";

	public static Repository getRepository(String key) {
		IAbapProject abapProject = findProject();

		String destination = abapProject.getDestinationId();
		URI abapGitUri = URI.create(ABAPGIT_URI + key);

		IRestResource abapGitResource = AdtRestResourceFactory.createRestResourceFactory()
				.createResourceWithStatelessSession(abapGitUri, destination);

		final IResponse response = abapGitResource.get(null, IResponse.class);

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

		Repository repo = new Repository(xml.find("/abap/values/ROOT/KEY"), xml.find("/abap/values/ROOT/URL"),
				xml.find("/abap/values/ROOT/PACKAGE"));
		return repo;
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

	public static Repository[] listRepositories() {
		// TODO
		Repository[] list = null;
		return list;
	}

	private static IAbapProject findProject() {
		// todo, how to find the right project?
		IProject[] abapProjects = AdtProjectServiceFactory.createProjectService().getAvailableAbapProjects();
		IAbapProject abapProject = (IAbapProject) abapProjects[0].getAdapter(IAbapProject.class);

		AdtLogonServiceUIFactory.createLogonServiceUI().ensureLoggedOn(abapProject.getDestinationData(),
				PlatformUI.getWorkbench().getProgressService());

		return abapProject;
	}

}
