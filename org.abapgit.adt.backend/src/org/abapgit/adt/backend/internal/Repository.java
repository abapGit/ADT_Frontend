package org.abapgit.adt.backend.internal;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.abapgit.adt.backend.IRepository;

public class Repository implements IRepository {

	private String key;
	private String remoteUser;
	private String url;
	private String branch;
	private String pckg;
	private String firstCommit;
	private String createdBy;
	private String deserializedBy;
	private String deserializedAt;
	private String remotePassword;
	private String statusFlag;
	private String statusText;
	private String transportRequest;
	private final Map<String, URI> pullLink = new HashMap<>();
	private final Map<String, URI> statusLink = new HashMap<>();
	private final Map<String, URI> logLink = new HashMap<>();

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getRemoteUser() {
		return this.remoteUser;
	}

	@Override
	public String getBranch() {
		return this.branch;
	}

	@Override
	public String getFirstCommit() {
		return this.firstCommit;
	}

	@Override
	public String getPackage() {
		return this.pckg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Repository [key="); //$NON-NLS-1$
		builder.append(this.key);
		builder.append(", remoteUser="); //$NON-NLS-1$
		builder.append(this.remoteUser);
		builder.append(", url="); //$NON-NLS-1$
		builder.append(this.url);
		builder.append(", branch="); //$NON-NLS-1$
		builder.append(this.branch);
		builder.append(", pckg="); //$NON-NLS-1$
		builder.append(this.pckg);
		builder.append(", firstCommit="); //$NON-NLS-1$
		builder.append(this.firstCommit);
		builder.append(", createdBy="); //$NON-NLS-1$
		builder.append(this.createdBy);
		builder.append(", remotePassword="); //$NON-NLS-1$
		builder.append(this.remotePassword);
		builder.append(", transportRequest="); //$NON-NLS-1$
		builder.append(this.transportRequest);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	@Override
	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public void setFirstCommit(String firstCommit) {
		this.firstCommit = firstCommit;
	}

	@Override
	public void setPackage(String pckg) {
		this.pckg = pckg;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void setPassword(String remotePassword) {
		this.remotePassword = remotePassword;
	}

	@Override
	public void setTransportRequest(String transportRequest) {
		this.transportRequest = transportRequest;
	}

	@Override
	public String getRemotePassword() {
		return this.remotePassword;
	}

	@Override
	public String getTransportRequest() {
		return this.transportRequest;
	}

	@Override
	public String getStatusFlag() {
		return this.statusFlag;
	}

	@Override
	public String getStatusText() {
		return this.statusText;
	}

	@Override
	public String getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String getDeserializedBy() {
		return this.deserializedBy;
	}

	@Override
	public void setDeserializedBy(String deserializedBy) {
		this.deserializedBy = deserializedBy;
	}

	@Override
	public String getDeserializedAt() {
		return this.deserializedAt;
	}

	@Override
	public void setDeserializedAt(String deserializedAt) {
		this.deserializedAt = deserializedAt;
	}

	@Override
	public URI getPullLink(String relation) {
		return this.pullLink.get(relation);
	}

	public void addPullLink(String relation, URI uri) {
		this.pullLink.put(relation, uri);
	}

	@Override
	public URI getLogLink(String relation) {
		return this.logLink.get(relation);
	}

	public void addLogLink(String relation, URI uri) {
		this.logLink.put(relation, uri);
	}

	@Override
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	@Override
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	@Override
	public URI getStatusLink(String relation) {
		return this.statusLink.get(relation);
	}

	public void addStatusLink(String relation, URI uri) {
		this.statusLink.put(relation, uri);
	}

	@Override
	public int hashCode() { // NOPMD
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
		result = prime * result + ((this.branch == null) ? 0 : this.branch.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { // NOPMD
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Repository other = (Repository) obj;
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.equals(other.url)) {
			return false;
		}
		if (this.branch == null) {
			if (other.branch != null) {
				return false;
			}
		} else if (!this.branch.equals(other.branch)) {
			return false;
		}
		return true;
	}

}
