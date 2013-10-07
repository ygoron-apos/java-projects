/**
 * 
 */
package com.apos.scheduler;

/**
 * @author Yuri Goron
 * 
 */
public interface ICommandLineArgs {

	public static final int INT_DEFAULT_BOXI_CONNECTIONS = 5;
	public final static int INT_BUFFER_DEFAULT_CAPACITY = 10000;
	public static final int INT_DEFAULT_SMTP_PORT = 25;
	public static final int INT_DEFAULT_MAX_LOGONS = 3;
	public static final int INT_DEFAULT_MAX_PARAMETERS = 10;

	/**
	 * Sets Java System Properties to set Beans Parameters
	 */
	public abstract void setBeanParameters();

	public abstract void print();

	public abstract ScheduleType getScheduleType();

	public abstract void setScheduleType(ScheduleType scheduleType);

	public int getObjectId();

	public abstract void setObjectId(int objectId);

	public abstract boolean isDeleteRecurring();

	public abstract void setDeleteRecurring(boolean deleteRecurring);

	public abstract boolean isRepaceInstances();

	public abstract void setRepaceInstances(boolean replaceInstances);

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
	public abstract String getInstanceFile();

	/**
	 * @param objectsFile
	 *            the objectsFile to set
	 */
	public abstract void setInstanceFile(String instanceFile);

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
	 * @return the Custom PropertiesFile
	 */
	public abstract String getCustomSettingsFile();

	/**
	 * @param Properties
	 *            File the Properties File to set
	 */
	public abstract void setCustomSettingsFile(String propertiesFile);

	/**
	 * Parameter Flag
	 * 
	 * @param parameterFlag
	 */
	public abstract void setParameterFlag(int parameterFlag);

	/**
	 * Parameter Flag
	 * 
	 * @return
	 */
	public abstract int getParameterFlag();

}
