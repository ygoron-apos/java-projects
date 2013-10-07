package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Notification")
public class Notification {

	private String AuditNotificationOption;
	private int AuditNotificationOptionCode;
	private DestinationSmtp EmailNotificationSuccess;
	private DestinationSmtp EmailNotificationFailure;

	/**
	 * @return the auditNotificationOption
	 */
	public String getAuditNotificationOption() {
		return AuditNotificationOption;
	}

	/**
	 * @param auditNotificationOption
	 *            the auditNotificationOption to set
	 */
	public void setAuditNotificationOption(String auditNotificationOption) {
		AuditNotificationOption = auditNotificationOption;
	}

	/**
	 * @return the emailNotificationSuccess
	 */
	public DestinationSmtp getEmailNotificationSuccess() {
		return EmailNotificationSuccess;
	}

	/**
	 * @param emailNotificationSuccess
	 *            the emailNotificationSuccess to set
	 */
	public void setEmailNotificationSuccess(DestinationSmtp emailNotificationSuccess) {
		EmailNotificationSuccess = emailNotificationSuccess;
	}

	/**
	 * @return the emailNotificationFailure
	 */
	public DestinationSmtp getEmailNotificationFailure() {
		return EmailNotificationFailure;
	}

	/**
	 * @param emailNotificationFailure
	 *            the emailNotificationFailure to set
	 */
	public void setEmailNotificationFailure(DestinationSmtp emailNotificationFailure) {
		EmailNotificationFailure = emailNotificationFailure;
	}

	/**
	 * @return the auditNotificationOptionCode
	 */
	public int getAuditNotificationOptionCode() {
		return AuditNotificationOptionCode;
	}

	/**
	 * @param auditNotificationOptionCode
	 *            the auditNotificationOptionCode to set
	 */
	public void setAuditNotificationOptionCode(int auditNotificationOptionCode) {
		AuditNotificationOptionCode = auditNotificationOptionCode;
	}

}
