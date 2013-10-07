package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.apos.encryption.Encryptor;
import com.apos.infoobject.properties.CommonConstants;

/**
 * 
 */

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "ReportLogon")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportLogon {
	private static final Logger Log = Logger.getLogger(ReportLogon.class);
	@XmlElement(name = "Logon")
	private String logon;
	private String CustomDatabaseDLLName;
	private String CustomDatabaseName;
	private String CustomPassword;
	private boolean IsCustomPasswordEncrypted = true;;

	private String CustomServerName;
	private int CustomServerType;
	private String CustomUserName;
	private String DatabaseName;
	private String Password;
	private boolean IsPasswordEncrypted = true;

	private String ServerName;
	private String SubReportName;
	private boolean UseOriginalDataSource;
	private String UserName;
	@XmlElement(name = "TablePrefixes")
	private List<TableLocationPrefix> tablePrefixes = new ArrayList<TableLocationPrefix>();

	/**
	 * @return the logon
	 */
	public String getLogon() {
		return logon;
	}

	/**
	 * @param logon
	 *            the logon to set
	 */
	public void setLogon(String logon) {
		this.logon = logon;
	}

	/**
	 * @return the customDatabaseDLLName
	 */
	public String getCustomDatabaseDLLName() {
		return CustomDatabaseDLLName;
	}

	/**
	 * @param customDatabaseDLLName
	 *            the customDatabaseDLLName to set
	 */
	public void setCustomDatabaseDLLName(String customDatabaseDLLName) {
		CustomDatabaseDLLName = customDatabaseDLLName;
	}

	/**
	 * @return the customDatabaseName
	 */
	public String getCustomDatabaseName() {
		return CustomDatabaseName;
	}

	/**
	 * @param customDatabaseName
	 *            the customDatabaseName to set
	 */
	public void setCustomDatabaseName(String customDatabaseName) {
		CustomDatabaseName = customDatabaseName;
	}

	/**
	 * @return the customPassword
	 * @throws Exception
	 */
	public String getCustomPassword() throws Exception {
		Encryptor encryptor = new Encryptor();
		if (CustomPassword == null)
			return "";
		try {
			String retString = IsCustomPasswordEncrypted ? new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, CustomPassword.getBytes())) : CustomPassword;
			return retString;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return "";
	}

	/**
	 * @param customPassword
	 *            the customPassword to set
	 */
	public void setCustomPassword(String customPassword) {
		CustomPassword = customPassword;
	}

	/**
	 * @return the customServerName
	 */
	public String getCustomServerName() {
		return CustomServerName;
	}

	/**
	 * @param customServerName
	 *            the customServerName to set
	 */
	public void setCustomServerName(String customServerName) {
		CustomServerName = customServerName;
	}

	/**
	 * @return the customServerType
	 */
	public int getCustomServerType() {
		return CustomServerType;
	}

	/**
	 * @param customServerType
	 *            the customServerType to set
	 */
	public void setCustomServerType(int customServerType) {
		CustomServerType = customServerType;
	}

	/**
	 * @return the customUserName
	 */
	public String getCustomUserName() {
		return CustomUserName;
	}

	/**
	 * @param customUserName
	 *            the customUserName to set
	 */
	public void setCustomUserName(String customUserName) {
		CustomUserName = customUserName;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return DatabaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		DatabaseName = databaseName;
	}

	/**
	 * @return the password
	 * @throws Exception
	 */
	public String getPassword() throws Exception {
		Encryptor encryptor = new Encryptor();
		if (Password == null)
			return "";
		try {
			String retString = IsPasswordEncrypted ? new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, Password.getBytes())) : Password;
			return retString;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return "";
		// return Password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return ServerName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	/**
	 * @return the subReportName
	 */
	public String getSubReportName() {
		return SubReportName;
	}

	/**
	 * @param subReportName
	 *            the subReportName to set
	 */
	public void setSubReportName(String subReportName) {
		SubReportName = subReportName;
	}

	/**
	 * @return the useOriginalDataSource
	 */
	public boolean isUseOriginalDataSource() {
		return UseOriginalDataSource;
	}

	/**
	 * @param useOriginalDataSource
	 *            the useOriginalDataSource to set
	 */
	public void setUseOriginalDataSource(boolean useOriginalDataSource) {
		UseOriginalDataSource = useOriginalDataSource;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the tablePrefixes
	 */
	public List<TableLocationPrefix> getTablePrefixes() {
		return tablePrefixes;
	}

	/**
	 * @param tablePrefixes
	 *            the tablePrefixes to set
	 */
	public void setTablePrefixes(List<TableLocationPrefix> tablePrefixes) {
		this.tablePrefixes = tablePrefixes;
	}

	/**
	 * @return the isCustomPasswordEncrypted
	 */
	public boolean isIsCustomPasswordEncrypted() {
		return IsCustomPasswordEncrypted;
	}

	/**
	 * @param isCustomPasswordEncrypted
	 *            the isCustomPasswordEncrypted to set
	 */
	public void setIsCustomPasswordEncrypted(boolean isCustomPasswordEncrypted) {
		IsCustomPasswordEncrypted = isCustomPasswordEncrypted;
	}

	/**
	 * @return the isPasswordEncrypted
	 */
	public boolean isIsPasswordEncrypted() {
		return IsPasswordEncrypted;
	}

	/**
	 * @param isPasswordEncrypted
	 *            the isPasswordEncrypted to set
	 */
	public void setIsPasswordEncrypted(boolean isPasswordEncrypted) {
		IsPasswordEncrypted = isPasswordEncrypted;
	}

}
