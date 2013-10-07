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
public class DestinationDisk {

	private static final Logger Log = Logger.getLogger(DestinationDisk.class);
	private String Type = "Disk";
	private String OutputFileName;
	private String UserName;
	private String Password;
	private boolean IsPasswordEncrypted = true;;
	private boolean IsUseJobServerDefaults;

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @return the outputFileName
	 */
	public String getOutputFileName() {
		return OutputFileName;
	}

	/**
	 * @param outputFileName
	 *            the outputFileName to set
	 */
	public void setOutputFileName(String outputFileName) {
		this.OutputFileName = outputFileName;
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
