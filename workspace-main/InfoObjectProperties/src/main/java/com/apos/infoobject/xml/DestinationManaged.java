/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DestinationManaged {

	private String Type = "Managed";
	private int SendOption;
	private String SendOptionText;
	private String DestinationOption;
	private int Count;
	private String Name;
	private String TargetObjectName;

	// Returns a boolean that indicates whether an instance of the scheduled
	// object and the input files are sent to the specified location(s) when it
	// is successfully processed.
	private boolean IsIncludeInstance;

	// Returns a boolean value that indicates whether a new object of the report
	// instance will be created when the operation is successfully processed.
	private boolean IsCreateNewObject;

	// Returns a boolean value that indicates whether the instance of the
	// scheduled object is set as the parent of the input files when the object
	// is successfully processed and sent to the specified locations(s).
	private boolean IsInstanceAsParent;

	// Returns a boolean value that indicates whether the instance of the
	// scheduled object will include saved data even if the report itself has
	// the same option turned off.
	private boolean IsKeepSaveData;

	private String DistributionServerString;

	private boolean IsUseJobServerDefaults;

	private List<Integer> UserIdInboxes = new ArrayList<Integer>();
	private List<Inbox> Inboxes = new ArrayList<Inbox>();

	private List<Inbox> Favorites = new ArrayList<Inbox>();

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @return the sendOption
	 */
	public int getSendOption() {
		return SendOption;
	}

	/**
	 * @param sendOption
	 *            the sendOption to set
	 */
	public void setSendOption(int sendOption) {
		this.SendOption = sendOption;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return Count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.Count = count;
	}

	/**
	 * @return the userIdInboxes
	 */
	public List<Integer> getUserIdInboxes() {
		return UserIdInboxes;
	}

	/**
	 * @param userIdInboxes
	 *            the userIdInboxes to set
	 */
	public void setUserIdInboxes(List<Integer> userIdInboxes) {
		this.UserIdInboxes = userIdInboxes;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.Name = name;
	}

	/**
	 * @return the sendOptionText
	 */
	public String getSendOptionText() {
		return SendOptionText;
	}

	/**
	 * @param sendOptionText
	 *            the sendOptionText to set
	 */
	public void setSendOptionText(String sendOptionText) {
		SendOptionText = sendOptionText;
	}

	/**
	 * @return the isIncludeInstance
	 */
	public boolean isIsIncludeInstance() {
		return IsIncludeInstance;
	}

	/**
	 * @param isIncludeInstance
	 *            the isIncludeInstance to set
	 */
	public void setIsIncludeInstance(boolean isIncludeInstance) {
		IsIncludeInstance = isIncludeInstance;
	}

	/**
	 * @return the isCreateNewObject
	 */
	public boolean isIsCreateNewObject() {
		return IsCreateNewObject;
	}

	/**
	 * @param isCreateNewObject
	 *            the isCreateNewObject to set
	 */
	public void setIsCreateNewObject(boolean isCreateNewObject) {
		IsCreateNewObject = isCreateNewObject;
	}

	/**
	 * @return the isInstanceAsParent
	 */
	public boolean isIsInstanceAsParent() {
		return IsInstanceAsParent;
	}

	/**
	 * @param isInstanceAsParent
	 *            the isInstanceAsParent to set
	 */
	public void setIsInstanceAsParent(boolean isInstanceAsParent) {
		IsInstanceAsParent = isInstanceAsParent;
	}

	/**
	 * @return the isKeepSaveData
	 */
	public boolean isIsKeepSaveData() {
		return IsKeepSaveData;
	}

	/**
	 * @param isKeepSaveData
	 *            the isKeepSaveData to set
	 */
	public void setIsKeepSaveData(boolean isKeepSaveData) {
		IsKeepSaveData = isKeepSaveData;
	}

	/**
	 * @return the distributionServerString
	 */
	public String getDistributionServerString() {
		return DistributionServerString;
	}

	/**
	 * @param distributionServerString
	 *            the distributionServerString to set
	 */
	public void setDistributionServerString(String distributionServerString) {
		DistributionServerString = distributionServerString;
	}

	/**
	 * @return the inboxes
	 */
	public List<Inbox> getInboxes() {
		return Inboxes;
	}

	/**
	 * @param inboxes
	 *            the inboxes to set
	 */
	public void setInboxes(List<Inbox> inboxes) {
		Inboxes = inboxes;
	}

	/**
	 * @return the destinationOption
	 */
	public String getDestinationOption() {
		return DestinationOption;
	}

	/**
	 * @param destinationOption
	 *            the destinationOption to set
	 */
	public void setDestinationOption(String destinationOption) {
		DestinationOption = destinationOption;
	}

	/**
	 * @return the favorites
	 */
	public List<Inbox> getFavorites() {
		return Favorites;
	}

	/**
	 * @param favorites
	 *            the favorites to set
	 */
	public void setFavorites(List<Inbox> favorites) {
		Favorites = favorites;
	}

	/**
	 * @return the targetObjectName
	 */
	public String getTargetObjectName() {
		return TargetObjectName;
	}

	/**
	 * @param targetObjectName
	 *            the targetObjectName to set
	 */
	public void setTargetObjectName(String targetObjectName) {
		TargetObjectName = targetObjectName;
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

}
