/**
 * 
 */
package com.apos.infoobject.properties;

/**
 * @author Yuri Goron
 * 
 */

public class BOXIConnectionInfo {

	public final static String CMS_NAME = "name";
	public final static String CMS_USER = "user";
	public final static String CMS_PASSWORD = "password";
	public final static String CMS_AUTH_TYPE = "auth";
	public final static String CMS_AUTH_DEFAULT = "secEnterprise";
	public final static String CMS_MAX_CONNECTIONS = "maxConnections";
	private boolean useSerializedSession;

	private String cmsToken;

	private String cmsName;
	private String cmsUser;
	private String cmsPassword;
	private String cmsAuthType;
	private int maxConnections;
	private String serializedSessionFile;
	private String serializedSession;

	public String getCmsName() {
		return cmsName;
	}

	public void setCmsName(String cmsName) {
		this.cmsName = cmsName;
	}

	public String getCmsUser() {
		return cmsUser;
	}

	public void setCmsUser(String cmsUser) {
		this.cmsUser = cmsUser;
	}

	public String getCmsPassword() {
		return cmsPassword;
	}

	public void setCmsPassword(String cmsPassword) {
		this.cmsPassword = cmsPassword;
	}

	public String getCmsAuthType() {
		return cmsAuthType;
	}

	public void setCmsAuthType(String cmsAuthType) {
		this.cmsAuthType = cmsAuthType;
	}

	public String toString() {
		return "CMS:" + cmsName + " User:" + cmsUser + " Password:" + cmsPassword + " Auth Type:" + cmsAuthType;

	}

	/**
	 * @return the maxConnections
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * @param maxConnections
	 *            the maxConnections to set
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * @return the cmsToken
	 */
	public String getCmsToken() {
		return cmsToken;
	}

	/**
	 * @param cmsToken
	 *            the cmsToken to set
	 */
	public void setCmsToken(String cmsToken) {
		this.cmsToken = cmsToken;
	}

	/**
	 * @return the useSerializedSession
	 */
	public boolean isUseSerializedSession() {
		return useSerializedSession;
	}

	/**
	 * @param useSerializedSession
	 *            the useSerializedSession to set
	 */
	public void setUseSerializedSession(boolean useSerializedSession) {
		this.useSerializedSession = useSerializedSession;
	}

	/**
	 * @return the serializedSessionFile
	 */
	public String getSerializedSessionFile() {
		return serializedSessionFile;
	}

	/**
	 * @param serializedSessionFile
	 *            the serializedSessionFile to set
	 */
	public void setSerializedSessionFile(String serializedSessionFile) {
		this.serializedSessionFile = serializedSessionFile;
	}

	/**
	 * @return the serializedSession
	 */
	public String getSerializedSession() {
		return serializedSession;
	}

	/**
	 * @param serializedSession the serializedSession to set
	 */
	public void setSerializedSession(String serializedSession) {
		this.serializedSession = serializedSession;
	}

}
