package org.abapgit.adt.backend;

import java.net.URI;

public interface IRepository {
	public String getKey();

	public String getUrl();

	public String getRemoteUser();

	public String getBranch();

	public String getFirstCommit();

	public String getCreatedBy();

	public String getCreatedEmail();

	public String getDeserializedBy();

	public String getDeserializedEmail();

	public String getDeserializedAt();

	public String getPackage();

	/**
	 * Returns a flag which indicates the repository status. </br>
	 * </br>
	 * <b> S : Success </br>
	 * W : Warning </br>
	 * E : Error </br>
	 * A : Abort </br>
	 * R : Running </b>
	 */
	public String getStatusFlag();

	public String getStatusText();

	public String getRemotePassword();

	public String getTransportRequest();

	public void setKey(String key);

	public void setUrl(String url);

	public void setRemoteUser(String user);

	public void setBranch(String branch);

	public void setFirstCommit(String firstCommit);

	public void setCreatedBy(String createdBy);

	public void setCreatedEmail(String createdEmail);

	public void setDeserializedBy(String deserializedBy);

	public void setDeserializedEmail(String deserializedEmail);

	public void setDeserializedAt(String deserializedAt);

	public void setPackage(String pckg);

	public void setStatusFlag(String statusFlag);

	public void setStatusText(String statusText);

	public void setPassword(String password);

	public void setTransportRequest(String transportRequest);

	public URI getPullLink(String relation);

	public URI getLogLink(String relation);

	public URI getStatusLink(String relation);

	public URI getStageLink(String relation);

	public URI getPushLink(String relation);

	public URI getChecksLink(String relation);

}
