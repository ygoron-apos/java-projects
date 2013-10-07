/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.log4j.Logger;

import com.apos.encryption.Encryptor;
import com.apos.infoobject.properties.CommonConstants;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DestinationFtp {

	private static final Logger Log = Logger.getLogger(DestinationFtp.class);

	private String Type = "Ftp";
	private String UserName;
	private String Password;
	private boolean IsPasswordEncrypted = true;;
	private String ServerName;
	private String Path;
	private int Port;
	private String Account;
	private boolean IsUseJobServerDefaults;

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
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
		this.UserName = userName;
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
		this.ServerName = serverName;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return Path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.Path = path;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return Port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.Port = port;
	}

	/**
	 * @return the password
	 * @throws Exception
	 */
	public String getPassword() throws Exception {
		Encryptor encryptor = new Encryptor();
		try {
			String retString = IsPasswordEncrypted ? new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, Password.getBytes())) : Password;
			return retString;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return "";
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return Account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		Account = account;
	}

	/**
	 * @return the isUseJobServerDefaults
	 */
	public boolean isIsUseJobServerDefaults() {
		return IsUseJobServerDefaults;
	}

	/**
	 * @param isUseJobServerDefaults
	 *            the isUseJobServerDefaults to set
	 */
	public void setIsUseJobServerDefaults(boolean isUseJobServerDefaults) {
		IsUseJobServerDefaults = isUseJobServerDefaults;
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
