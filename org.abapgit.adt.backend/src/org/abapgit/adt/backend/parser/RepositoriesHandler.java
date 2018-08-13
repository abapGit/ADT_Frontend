package org.abapgit.adt.backend.parser;

import java.util.ArrayList;
import java.util.List;

import org.abapgit.adt.backend.IRepository;
import org.abapgit.adt.backend.Repository;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RepositoriesHandler extends DefaultHandler {
	private List<IRepository> repositories = null;
	private Repository repository = null;

	public List<IRepository> getRepositories() {
		return repositories;
	}

	boolean isKey = false;
	boolean isPackage = false;
	boolean isUser = false;
	boolean isUrl = false;
	boolean isFirstCommit = false;
	boolean isBranch = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("repository")) {
			// String id = attributes.getValue("id");
			// initialize Employee object and set id attribute
			repository = new Repository();
			// repository.setKey(Integer.parseInt(id));
			if (repositories == null) {
				repositories = new ArrayList<IRepository>();
			}
		} else if (qName.equalsIgnoreCase("key")) {
			isKey = true;
		} else if (qName.equalsIgnoreCase("url")) {
			isUrl = true;
		} else if (qName.equalsIgnoreCase("branch_name")) {
			isBranch = true;
		} else if (qName.equalsIgnoreCase("created_by")) {
			isUser = true;
		} else if (qName.equalsIgnoreCase("created_at")) {
			isFirstCommit = true;
		} else if (qName.equalsIgnoreCase("package")) {
			isPackage = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("repository")) {
			repositories.add(repository);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch, start, length);
		if (isKey) {
			repository.setKey(value);
			isKey = false;
		} else if (isUrl) {
			repository.setUrl(value);
			isUrl = false;
		} else if (isBranch) {
			repository.setBranch(value);
			isBranch = false;
		} else if (isUser) {
			repository.setUser(value);
			isUser = false;
		} else if (isFirstCommit) {
			repository.setFirstCommit(value);
			isFirstCommit = false;
		} else if (isPackage) {
			repository.setPackage(value);
			isPackage = false;
		}
	}

}
