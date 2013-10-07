/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

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
public class DestinationSmtp {

	private static final Logger Log = Logger.getLogger(DestinationSmtp.class);
	private String Type = "Email";
	private String From;
	private String To;
	private String CC;
	private String Subject;
	private String Message;
	private String AttachmentName;
	private List<Attachment> Attachments = new ArrayList<Attachment>();

	private int Port;

	private String ServerName;
	private String BCC;
	private String AuthenticationType;
	private boolean IsAttachmentEnabled;
	private String UserName;
	private String Password;
	private boolean IsPasswordEncrypted = true;;
	private String Delimiter;
	private String Domain;
	private boolean IsUseJobServerDefaults;

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return From;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.From = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return To;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.To = to;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return Subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.Subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.Message = message;
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
		Port = port;
	}

	/**
	 * @return the cC
	 */
	public String getCC() {
		return CC;
	}

	/**
	 * @param cC
	 *            the cC to set
	 */
	public void setCC(String cC) {
		CC = cC;
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
	 * @return the bCC
	 */
	public String getBCC() {
		return BCC;
	}

	/**
	 * @param bCC
	 *            the bCC to set
	 */
	public void setBCC(String bCC) {
		BCC = bCC;
	}

	/**
	 * @return the authenticationType
	 */
	public String getAuthenticationType() {
		return AuthenticationType;
	}

	/**
	 * @param authenticationType
	 *            the authenticationType to set
	 */
	public void setAuthenticationType(String authenticationType) {
		AuthenticationType = authenticationType;
	}

	/**
	 * @return the isAttachmentEnabled
	 */
	public boolean isIsAttachmentEnabled() {
		return IsAttachmentEnabled;
	}

	/**
	 * @param isAttachmentEnabled
	 *            the isAttachmentEnabled to set
	 */
	public void setIsAttachmentEnabled(boolean isAttachmentEnabled) {
		IsAttachmentEnabled = isAttachmentEnabled;
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
	 * @return the delimiter
	 */
	public String getDelimiter() {
		return Delimiter;
	}

	/**
	 * @param delimiter
	 *            the delimiter to set
	 */
	public void setDelimiter(String delimiter) {
		Delimiter = delimiter;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return Domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		Domain = domain;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		Type = type;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return AttachmentName;
	}

	/**
	 * @param attachmentName
	 *            the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		AttachmentName = attachmentName;
	}

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return Attachments;
	}

	/**
	 * @param attachments
	 *            the attachments to set
	 */
	public void setAttachments(List<Attachment> attachments) {
		Attachments = attachments;
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
