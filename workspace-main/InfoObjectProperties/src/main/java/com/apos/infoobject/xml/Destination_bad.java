package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Destination")
public class Destination_bad {

	private String DestinationType;
	private int DestinationSendOption;
	private String DestinationName;
	private int DestinationCount;
	private UserIDInbox userIDInbox;
	
	private String From;
	private String To;
	private String CC;
	private String Subject;
	private String Message;
	private String AttachmentName;

	
	/**
	 * @return the destinationType
	 */
	public String getDestinationType() {
		return DestinationType;
	}
	/**
	 * @param destinationType the destinationType to set
	 */
	public void setDestinationType(String destinationType) {
		DestinationType = destinationType;
	}
	/**
	 * @return the destinationSendOption
	 */
	public int getDestinationSendOption() {
		return DestinationSendOption;
	}
	/**
	 * @param destinationSendOption the destinationSendOption to set
	 */
	public void setDestinationSendOption(int destinationSendOption) {
		DestinationSendOption = destinationSendOption;
	}
	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return DestinationName;
	}
	/**
	 * @param destinationName the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		DestinationName = destinationName;
	}
	/**
	 * @return the destinationCount
	 */
	public int getDestinationCount() {
		return DestinationCount;
	}
	/**
	 * @param destinationCount the destinationCount to set
	 */
	public void setDestinationCount(int destinationCount) {
		DestinationCount = destinationCount;
	}
	/**
	 * @return the userIDInbox
	 */
	public UserIDInbox getUserIDInbox() {
		return userIDInbox;
	}
	/**
	 * @param userIDInbox the userIDInbox to set
	 */
	public void setUserIDInbox(UserIDInbox userIDInbox) {
		this.userIDInbox = userIDInbox;
	}
	/**
	 * @return the from
	 */
	public String getFrom() {
		return From;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		From = from;
	}
	/**
	 * @return the to
	 */
	public String getTo() {
		return To;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		To = to;
	}
	/**
	 * @return the cC
	 */
	public String getCC() {
		return CC;
	}
	/**
	 * @param cC the cC to set
	 */
	public void setCC(String cC) {
		CC = cC;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return Subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		Subject = subject;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}
	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return AttachmentName;
	}
	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		AttachmentName = attachmentName;
	}
	
	
	
}
