package com.apos.infoobject.properties;

public interface ICommandLineArgs {

	public static final int INT_DEFAULT_BOXI_CONNECTIONS = 10;
	public final static int INT_BUFFER_DEFAULT_CAPACITY = 10000;
	public static final int INT_DEFAULT_SMTP_PORT = 25;
	public static final int INT_DEFAULT_MAX_LOGONS = 3;
	public static final int INT_DEFAULT_MAX_PARAMETERS = 10;

	/**
	 * Sets Java System Properties to set Beans Parameters
	 */
	public abstract void setBeanParameters();

	public abstract void print();

	
	/**
	 * @return
	 */
	public abstract String getSerializedSession();
	
	/**
	 * @param serSession
	 */
	public abstract void setSerializedSession(String serSession);
	
	
	/**
	 * @return
	 */
	public abstract String getSerializedSessionFile();
	
	/**
	 * Sets File Name/patch that has serialized 
	 * 
	 * @param fileName
	 */
	public abstract void setSerializedSessionFile(String fileName);
	

	/**
	 * @return the objectsFile
	 */
	public abstract String getObjectsFile();

	/**
	 * @param objectsFile
	 *            the objectsFile to set
	 */
	public abstract void setObjectsFile(String objectsFile);

	/**
	 * @return the cancelFile
	 */
	public abstract String getCancelFile();

	/**
	 * @param cancelFile
	 *            the cancelFile to set
	 */
	public abstract void setCancelFile(String cancelFile);

	/**
	 * @return the logFile
	 */
	public abstract String getLogFile();

	/**
	 * @param logFile
	 *            the logFile to set
	 */
	public abstract void setLogFile(String logFile);

	/**
	 * @return the syncFile
	 */
	public abstract String getSyncFile();

	/**
	 * @param syncFile
	 *            the syncFile to set
	 */
	public abstract void setSyncFile(String syncFile);

	/**
	 * @return the resumeId
	 */
	public abstract int getResumeId();

	/**
	 * @param resumeId
	 *            the resumeId to set
	 */
	public abstract void setResumeId(int resumeId);

	/**
	 * @return the maxBOXIConnections
	 */
	public abstract int getMaxBOXIConnections();

	/**
	 * @param maxBOXIConnections
	 *            the maxBOXIConnections to set
	 */
	public abstract void setMaxBOXIConnections(int maxBOXIConnections);

	/**
	 * @return the smtpServerName
	 */
	public abstract String getSmtpServerName();

	/**
	 * @param smtpServerName
	 *            the smtpServerName to set
	 */
	public abstract void setSmtpServerName(String smtpServerName);

	/**
	 * @return the smptPort
	 */
	public abstract int getSmptPort();

	/**
	 * @param smptPort
	 *            the smptPort to set
	 */
	public abstract void setSmptPort(int smptPort);

	/**
	 * @return the smtpUserName
	 */
	public abstract String getSmtpUserName();

	/**
	 * @param smtpUserName
	 *            the smtpUserName to set
	 */
	public abstract void setSmtpUserName(String smtpUserName);

	/**
	 * @return the smtpUserPassword
	 */
	public abstract String getSmtpUserPassword();

	/**
	 * @param smtpUserPassword
	 *            the smtpUserPassword to set
	 */
	public abstract void setSmtpUserPassword(String smtpUserPassword);

	/**
	 * @return the smptpEmailFrom
	 */
	public abstract String getSmptpEmailFrom();

	/**
	 * @param smptpEmailFrom
	 *            the smptpEmailFrom to set
	 */
	public abstract void setSmptpEmailFrom(String smptpEmailFrom);

	/**
	 * @return the smtpEmailTo
	 */
	public abstract String getSmtpEmailTo();

	/**
	 * @param smtpEmailTo
	 *            the smtpEmailTo to set
	 */
	public abstract void setSmtpEmailTo(String smtpEmailTo);

	/**
	 * @return the smptSubjectText
	 */
	public abstract String getSmptSubjectText();

	/**
	 * @param smptSubjectText
	 *            the smptSubjectText to set
	 */
	public abstract void setSmptSubjectText(String smptSubjectText);

	/**
	 * @return the isEnableSmtpSSL
	 */
	public abstract boolean isEnableSmtpSSL();

	/**
	 * @param isEnableSmtpSSL
	 *            the isEnableSmtpSSL to set
	 */
	public abstract void setEnableSmtpSSL(boolean isEnableSmtpSSL);

	/**
	 * @return the isSendFinishedEmail
	 */
	public abstract boolean isSendFinishedEmail();

	/**
	 * @param isSendFinishedEmail
	 *            the isSendFinishedEmail to set
	 */
	public abstract void setSendFinishedEmail(boolean isSendFinishedEmail);

	/**
	 * @return the isIncludeFailureDetails
	 */
	public abstract boolean isIncludeFailureDetails();

	/**
	 * @param isIncludeFailureDetails
	 *            the isIncludeFailureDetails to set
	 */
	public abstract void setIncludeFailureDetails(boolean isIncludeFailureDetails);

	public abstract int getMaxLogons();

	public abstract void setMaxLogons(int maxLogons);

	public abstract int getMaxParameters();

	public abstract void setMaxParameters(int maxParameters);
	
	public abstract void setExportType (ExportType exportType);
	
	public abstract ExportType getExportType();

}