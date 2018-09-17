package org.abapgit.adt.backend.internal;

import org.abapgit.adt.backend.IRepository;

public class Repository implements IRepository {

	private String key;
	private String remoteUser;
	private String url;
	private String branch;
	private String pckg;
	private String firstCommit;
	private String createdBy;
	private String remotePassword;
	private String transportRequest;

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
	public String getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
