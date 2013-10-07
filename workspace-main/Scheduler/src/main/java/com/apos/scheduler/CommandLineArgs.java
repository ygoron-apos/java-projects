/**
 * 
 */
package com.apos.scheduler;

import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 * 
 */
public class CommandLineArgs implements ICommandLineArgs {
	private static final Logger Log = Logger.getLogger(CommandLineArgs.class);

	/**
	 * 1. Serialized Session File
	 * 
	 */
	private String cmsSerializedSessionFile;
	private int objectId;
	private String cancelFile;
	private String syncFile;
	private String logFile;
	private boolean isDeleteRecurring;
	private boolean isReplaceInstances;

	private String customSettingsFile;
	private String instanceFile;

	private ScheduleType scheduleType;

	private int parameterFlag;

	/**
	 * 6 Maximum BOXI Connections Count
	 */
	private int maxBOXIConnections = INT_DEFAULT_BOXI_CONNECTIONS;

	private String serSession;

	public void setBeanParameters() {
		Log.debug("Set BOXI Pool Bean Property");

		System.getProperties().put("cms.Token", "");
		Log.debug("SerializedSession File:" + this.cmsSerializedSessionFile);
		System.getProperties().put("cms.serSessionFile", this.cmsSerializedSessionFile);
		System.getProperties().put("cms.Name", "");
		System.getProperties().put("cms.UserName", "");
		System.getProperties().put("cms.Password", "");
		System.getProperties().put("cms.AuthType", "");
		System.getProperties().put("cms.AuthType", "");
		System.getProperties().put("cmsPool.MaxConnections", String.valueOf(this.getMaxBOXIConnections()));
	}

	public void print() {
		Log.info("Object Id:" + this.objectId);
		Log.info("Parameter Flag:" + this.parameterFlag);
		Log.info("cancelFile:" + cancelFile);
		Log.info("logFile:" + logFile);
		Log.info("syncFile:" + syncFile);
		Log.info("dbPassword:*****");
		Log.info("is Delete Recurring:" + this.isDeleteRecurring);
		Log.info("Max Boxi Connections:" + maxBOXIConnections);

	}

	public ScheduleType getScheduleType() {
		return this.scheduleType;
	}

	public void setScheduleType(ScheduleType scheduleType) {
		this.scheduleType = scheduleType;

	}

	public String getInstanceFile() {
		return this.instanceFile;
	}

	public void setInstanceFile(String instanceFile) {
		this.instanceFile = instanceFile;

	}

	public String getCancelFile() {
		return this.cancelFile;
	}

	public void setCancelFile(String cancelFile) {
		this.cancelFile = cancelFile;

	}

	public String getLogFile() {
		return this.logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;

	}

	public String getSyncFile() {
		return this.syncFile;
	}

	public void setSyncFile(String syncFile) {
		this.syncFile = syncFile;

	}

	public String getCustomSettingsFile() {
		return this.customSettingsFile;
	}

	public void setCustomSettingsFile(String propertiesFile) {
		this.customSettingsFile = propertiesFile;

	}

	public int getMaxBOXIConnections() {
		return this.maxBOXIConnections;
	}

	public void setMaxBOXIConnections(int maxBOXIConnections) {
		this.maxBOXIConnections = maxBOXIConnections;

	}

	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;

	}

	public boolean isDeleteRecurring() {
		return this.isDeleteRecurring;
	}

	public void setDeleteRecurring(boolean deleteRecurring) {
		this.isDeleteRecurring = deleteRecurring;

	}

	public String getSerializedSessionFile() {
		return this.cmsSerializedSessionFile;
	}

	public void setSerializedSessionFile(String fileName) {
		this.cmsSerializedSessionFile = fileName;

	}

	public String getSerializedSession() {
		return this.serSession;
	}

	public void setSerializedSession(String serSession) {
		this.serSession = serSession;

	}

	public boolean isRepaceInstances() {
		return this.isReplaceInstances;
	}

	public void setRepaceInstances(boolean replaceInstances) {
		this.isReplaceInstances = replaceInstances;

	}

	public void setParameterFlag(int parameterFlag) {
		this.parameterFlag = parameterFlag;

	}

	public int getParameterFlag() {
		return this.parameterFlag;
	}

}
