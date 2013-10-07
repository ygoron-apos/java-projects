/**
 * 
 */
package com.apos.infoobject.properties;

import java.net.URL;

import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 * 
 */

public class CommandLineArgs implements ICommandLineArgs {

	private static Logger Log = Logger.getLogger(CommandLineArgs.class);

	/**
	 * 0. Export Type
	 * 
	 */
	private ExportType exportType;

	/**
	 * 1. Serialized Session File
	 * 
	 */
	private String cmsSerializedSessionFile;

	/**
	 * 2. Objects Id File Full Name
	 */

	private String objectsFile;
	/**
	 * 3. Cancel File Full Name
	 */
	private String cancelFile;

	/**
	 * 4. Output File Full Name
	 */
	private String logFile;

	/**
	 * 5. Sync File
	 * 
	 */
	private String syncFile;

	/**
	 * 6. Continue with Object Id
	 */
	private int resumeId;

	/**
	 * 7 Maximum BOXI Connections Count
	 */
	private int maxBOXIConnections = INT_DEFAULT_BOXI_CONNECTIONS;

	/**
	 * 8 Maximum DB Logons
	 * 
	 */
	private int maxLogons;

	/**
	 * 9 Maximum Parameters
	 */
	private int maxParameters;

	/**
	 * 10 SMTP Server/IP
	 */
	private String smtpServerName;

	/**
	 * 11 SMTP Port
	 * 
	 */
	private int smptPort = 25;
	/**
	 * 12 SMTP User Name
	 */
	private String smtpUserName;

	/**
	 * 13 SMTP Password
	 */
	private String smtpUserPassword;

	/**
	 * 14 SMTP Email From
	 */
	private String smptpEmailFrom;

	/**
	 * 15 Email To
	 * 
	 * This could contain multiples separated by a comma or semi-colons, I am
	 * pre-formating this string to ensure that there are no spaces when this
	 * list of email addresses are passed into you.
	 */
	private String smtpEmailTo;
	/**
	 * 16 Email Subject
	 */
	private String smptSubjectText;

	/**
	 * 17 Use SSL
	 */
	private boolean isEnableSmtpSSL;

	/**
	 * 18 Send Finished Email
	 */
	private boolean isSendFinishedEmail;

	/**
	 * 19 Include Failure Details
	 */
	private boolean isIncludeFailureDetails;
	
	private String serSession;

	public CommandLineArgs(String[] args) {

		try {
			switch (Integer.parseInt(args[0])) {
			case 1:
				exportType = ExportType.EXPORT_CSV_IM;
				break;
			case 2:
				exportType = ExportType.EXPORT_XML_IS;
				break;
			case 3:
				exportType = ExportType.EXPORT_CSV_IS;
				break;
	
			}

		} catch (Exception ee) {
			Log.error("Wrong parameters structure. First Parameter must be Integer");
			Log.error(ee.getLocalizedMessage(), ee);
		}

		this.cmsSerializedSessionFile = args[1];
		objectsFile = args[2];
		cancelFile = args[3];
		logFile = args[4];
		syncFile = args[5];
		try {
			resumeId = Integer.parseInt(args[6]);
		}

		catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			resumeId = 0;
		}
		try {
			maxBOXIConnections = Integer.parseInt(args[7]);
		} catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			maxBOXIConnections = ICommandLineArgs.INT_DEFAULT_BOXI_CONNECTIONS;
		}

		try {
			maxLogons = Integer.parseInt(args[8]);
		} catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			maxLogons = ICommandLineArgs.INT_DEFAULT_MAX_LOGONS;
		}

		try {
			maxParameters = Integer.parseInt(args[9]);
		} catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			maxParameters = ICommandLineArgs.INT_DEFAULT_MAX_PARAMETERS;
		}

		Log.info("SMTP Server" + args[10]);
		setSmtpServerName(args[10]);
		try {
			Log.info("SMTP Port:" + args[11]);
			setSmptPort(Integer.parseInt(args[11]));
		} catch (Exception ee) {
			setSmptPort(INT_DEFAULT_SMTP_PORT);
			Log.error(ee.getLocalizedMessage(), ee);
		}

		Log.info("SMTP User Name" + args[12]);
		setSmtpUserName(args[12]);

		Log.info("SMTP User Password" + args[13]);
		setSmtpUserPassword(args[13]);

		Log.info("SMTP Email From" + args[14]);
		setSmptpEmailFrom(args[14]);

		Log.info("SMTP Email To" + args[15]);
		setSmtpEmailTo(args[15]);

		Log.info("SMTP Subject" + args[16]);
		setSmptSubjectText(args[16]);

		try {
			Log.info("Is SSL Enabled:" + args[17]);
			setEnableSmtpSSL(Boolean.parseBoolean(args[17]));
		} catch (Exception ee) {
			Log.error("Error Parsing Is SSL Enabled:" + args[17]);
			Log.error(ee.getLocalizedMessage(), ee);
			setEnableSmtpSSL(false);
		}

		try {
			Log.info("Is Send Finished Email:" + args[18]);
			setSendFinishedEmail(Boolean.parseBoolean(args[18]));
		} catch (Exception ee) {
			Log.error("Error Parsing Is Send Finished Email:" + args[18]);
			Log.error(ee.getLocalizedMessage(), ee);
			setSendFinishedEmail(false);
		}

		try {
			Log.info("Is Include Failure Details:" + args[19]);
			setIncludeFailureDetails(Boolean.parseBoolean(args[19]));
		} catch (Exception ee) {
			Log.error("Error Is Include Failure Details:" + args[19]);
			Log.error(ee.getLocalizedMessage(), ee);
			setIncludeFailureDetails(false);
		}

	}

	public static String getBaseUrl(boolean isHttps, String host, int port) {
		try {
			URL url = new URL(isHttps ? "https" : "http", host, port, "");
			Log.debug("Created URL:" + url.toString());
			return url.toString();
		} catch (Exception ee) {

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setBeanParameters()
	 */
	public void setBeanParameters() {
		Log.debug("Set BOXI Pool Bean Property");

		System.getProperties().put("cms.Token", "");
		System.getProperties().put("cms.serSessionFile", this.cmsSerializedSessionFile);
		System.getProperties().put("cms.Name", "");
		System.getProperties().put("cms.UserName", "");
		System.getProperties().put("cms.Password", "");
		System.getProperties().put("cms.AuthType", "");
		System.getProperties().put("cms.AuthType", "");
		System.getProperties().put("cmsPool.MaxConnections", String.valueOf(this.getMaxBOXIConnections()));

		Log.debug("Set Service Bean Properties");
		System.getProperties().put("command.ObjectsFile", this.getObjectsFile());
		System.getProperties().put("command.CancelFile", this.getCancelFile());
		System.getProperties().put("command.LogFile", this.getLogFile());
		System.getProperties().put("command.SyncFile", this.getSyncFile());
		System.getProperties().put("command.ResumeId", String.valueOf(resumeId));
		Log.debug("Set Resume To:" + System.getProperties().getProperty("command.ResumeId"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#print()
	 */
	public void print() {
		Log.info("cmsToken File:" + this.cmsSerializedSessionFile);
		Log.info("objectsFile:" + objectsFile);
		Log.info("cancelFile:" + cancelFile);
		Log.info("logFile:" + logFile);
		Log.info("syncFile:" + syncFile);
		Log.info("dbPassword:*****");
		Log.info("resumeId:" + resumeId);
		Log.info("Max Boxi Connections:" + maxBOXIConnections);

		// Log.debug("Command Line: WebiScan.jar "+cmsToken+" "+tablePrefix+" "+cancelFile+" "+logFile+" "+
		// syncFile+" "+ updateDate+" "+systemName+" "+dbConnectionUrl+" ")
	}

	/**
	 * Creates class Name - Source copied from security scanner
	 * 
	 * @param dbtype
	 * @return
	 */
	public static String getDBClassName(String dbtype) {

		if (dbtype.equalsIgnoreCase("SQL")) {
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} else if (dbtype.equalsIgnoreCase("Oracle")) {
			return "oracle.jdbc.driver.OracleDriver";
		} else if (dbtype.equalsIgnoreCase("MySQL")) {
			return "com.mysql.jdbc.Driver";
		} else if (dbtype.equalsIgnoreCase("ODBC")) {
			return "sun.jdbc.odbc.JdbcOdbcDriver";
		}

		return null;

	}

	/**
	 * 
	 * Constructs DB URL
	 * 
	 * @param dbType
	 * @param serverName
	 * @param port
	 * @param dbName
	 * @param userName
	 * @param password
	 * @param isWindAuth
	 * @return
	 */
	public static String getDBURL(String dbType, String serverName, String port, String dbName, String userName, String password, boolean isWindAuth) {
		StringBuffer conurl = new StringBuffer();

		if (dbType.equalsIgnoreCase("Oracle")) {
			conurl.append("jdbc:oracle:thin:").append(userName).append("/").append(password).append("@//");
			conurl.append(serverName);
			if (!port.equalsIgnoreCase("0") && !port.equals("")) {
				conurl.append(":").append(port);
			}
			conurl.append("/").append(dbName);
		} else if (dbType.equalsIgnoreCase("MySQL")) {
			// "jdbc:mysql://server/database?user=root&password=root"
			conurl.append("jdbc:mysql://").append(serverName).append(":" + port).append("/").append(dbName).append("?user=").append(userName).append("&password=").append(password);
		} else {
			// "jdbc:sqlserver://server:port;databaseName=mydb;user=usr;password=pwd;integratedSecurity=false;"
			conurl.append("jdbc:sqlserver://").append(serverName);
			if (!port.equalsIgnoreCase("0"))
				conurl.append(":").append(port);
			conurl.append(";databaseName=").append(dbName).append(";");
			if (!isWindAuth) {
				conurl.append("user=").append(userName).append(";password=").append(password).append(";integratedSecurity=false;");
			} else {
				conurl.append(";integratedSecurity=true;");
			}
		}

		return conurl.toString();

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getObjectsFile()
	 */
	public String getObjectsFile() {
		return objectsFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setObjectsFile(java.lang.String)
	 */
	public void setObjectsFile(String objectsFile) {
		this.objectsFile = objectsFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getCancelFile()
	 */
	public String getCancelFile() {
		return cancelFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setCancelFile(java.lang.String)
	 */
	public void setCancelFile(String cancelFile) {
		this.cancelFile = cancelFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getLogFile()
	 */
	public String getLogFile() {
		return logFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setLogFile(java.lang.String)
	 */
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSyncFile()
	 */
	public String getSyncFile() {
		return syncFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setSyncFile(java.lang.String)
	 */
	public void setSyncFile(String syncFile) {
		this.syncFile = syncFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getResumeId()
	 */
	public int getResumeId() {
		return resumeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setResumeId(int)
	 */
	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getMaxBOXIConnections()
	 */
	public int getMaxBOXIConnections() {
		return maxBOXIConnections;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setMaxBOXIConnections(int)
	 */
	public void setMaxBOXIConnections(int maxBOXIConnections) {
		this.maxBOXIConnections = maxBOXIConnections;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmtpServerName()
	 */
	public String getSmtpServerName() {
		return smtpServerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmtpServerName(java.lang.String
	 * )
	 */
	public void setSmtpServerName(String smtpServerName) {
		this.smtpServerName = smtpServerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmptPort()
	 */
	public int getSmptPort() {
		return smptPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setSmptPort(int)
	 */
	public void setSmptPort(int smptPort) {
		this.smptPort = smptPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmtpUserName()
	 */
	public String getSmtpUserName() {
		return smtpUserName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmtpUserName(java.lang.String)
	 */
	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmtpUserPassword()
	 */
	public String getSmtpUserPassword() {
		return smtpUserPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmtpUserPassword(java.lang.
	 * String)
	 */
	public void setSmtpUserPassword(String smtpUserPassword) {
		this.smtpUserPassword = smtpUserPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmptpEmailFrom()
	 */
	public String getSmptpEmailFrom() {
		return smptpEmailFrom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmptpEmailFrom(java.lang.String
	 * )
	 */
	public void setSmptpEmailFrom(String smptpEmailFrom) {
		this.smptpEmailFrom = smptpEmailFrom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmtpEmailTo()
	 */
	public String getSmtpEmailTo() {
		return smtpEmailTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmtpEmailTo(java.lang.String)
	 */
	public void setSmtpEmailTo(String smtpEmailTo) {
		this.smtpEmailTo = smtpEmailTo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#getSmptSubjectText()
	 */
	public String getSmptSubjectText() {
		return smptSubjectText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setSmptSubjectText(java.lang.String
	 * )
	 */
	public void setSmptSubjectText(String smptSubjectText) {
		this.smptSubjectText = smptSubjectText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#isEnableSmtpSSL()
	 */
	public boolean isEnableSmtpSSL() {
		return isEnableSmtpSSL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setEnableSmtpSSL(boolean)
	 */
	public void setEnableSmtpSSL(boolean isEnableSmtpSSL) {
		this.isEnableSmtpSSL = isEnableSmtpSSL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#isSendFinishedEmail()
	 */
	public boolean isSendFinishedEmail() {
		return isSendFinishedEmail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#setSendFinishedEmail(boolean)
	 */
	public void setSendFinishedEmail(boolean isSendFinishedEmail) {
		this.isSendFinishedEmail = isSendFinishedEmail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apos.webi40.scan.ICommandLineArgs2#isIncludeFailureDetails()
	 */
	public boolean isIncludeFailureDetails() {
		return isIncludeFailureDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.apos.webi40.scan.ICommandLineArgs2#setIncludeFailureDetails(boolean)
	 */
	public void setIncludeFailureDetails(boolean isIncludeFailureDetails) {
		this.isIncludeFailureDetails = isIncludeFailureDetails;
	}

	/**
	 * @return the maxLogons
	 */
	public int getMaxLogons() {
		return maxLogons;
	}

	/**
	 * @param maxLogons
	 *            the maxLogons to set
	 */
	public void setMaxLogons(int maxLogons) {
		this.maxLogons = maxLogons;
	}

	/**
	 * @return the maxParameters
	 */
	public int getMaxParameters() {
		return maxParameters;
	}

	/**
	 * @param maxParameters
	 *            the maxParameters to set
	 */
	public void setMaxParameters(int maxParameters) {
		this.maxParameters = maxParameters;
	}

	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}

	public ExportType getExportType() {
		return this.exportType;
	}

	public String getSerializedSessionFile() {
		return this.cmsSerializedSessionFile;
	}

	public void setSerializedSessionFile(String fileName) {
		this.cmsSerializedSessionFile=fileName;
		
	}

	public String getSerializedSession() {
		return this.serSession;
	}

	public void setSerializedSession(String serSession) {
		this.serSession=serSession;
		
	}

}
