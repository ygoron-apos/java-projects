/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "SchedulingInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SchedulingInfo {

	private Integer Type;
	private String typeText;

	private Integer IntervalMonths;
	private Integer IntervalDays;
	private Integer IntervalWeeks;
	private Integer IntervalHours;
	private Integer IntervalMinutes;
	private Integer IntervalNthDay;
	private String InstanceName;
	private String BeginDate;
	private String EndDate;
	private Integer RetriesAllowed;
	private Integer RetryInterval;
	private boolean RightNow;
	private Integer ScheduleOnBehalfOf;

	private List<Integer> Dependants = new ArrayList<Integer>();
	private List<Integer> Dependencies = new ArrayList<Integer>();

	@XmlElement(name = "DestinationManaged")
	private DestinationManaged destinationManaged;
	@XmlElement(name = "DestinationDisk")
	private DestinationDisk destinationDiskUnManaged;
	@XmlElement(name = "DestinationSmtp")
	private DestinationSmtp destinationSmtp;
	@XmlElement(name = "DestinationFtp")
	private DestinationFtp destinationFtp;

	private Notification notification;

	/**
	 * @return the type
	 */
	public int getType() {
		return Type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		Type = type;
	}

	/**
	 * @return the intervalMonths
	 */
	public int getIntervalMonths() {
		return IntervalMonths;
	}

	/**
	 * @param intervalMonths
	 *            the intervalMonths to set
	 */
	public void setIntervalMonths(int intervalMonths) {
		IntervalMonths = intervalMonths;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return BeginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		BeginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return EndDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	/**
	 * @return the retriesAllowed
	 */
	public int getRetriesAllowed() {
		return RetriesAllowed;
	}

	/**
	 * @param retriesAllowed
	 *            the retriesAllowed to set
	 */
	public void setRetriesAllowed(int retriesAllowed) {
		RetriesAllowed = retriesAllowed;
	}

	/**
	 * @return the retryInterval
	 */
	public int getRetryInterval() {
		return RetryInterval;
	}

	/**
	 * @param retryInterval
	 *            the retryInterval to set
	 */
	public void setRetryInterval(int retryInterval) {
		RetryInterval = retryInterval;
	}

	/**
	 * @return the rightNow
	 */
	public boolean isRightNow() {
		return RightNow;
	}

	/**
	 * @param rightNow
	 *            the rightNow to set
	 */
	public void setRightNow(boolean rightNow) {
		RightNow = rightNow;
	}

	/**
	 * @return the scheduleOnBehalfOf
	 */
	public int getScheduleOnBehalfOf() {
		return ScheduleOnBehalfOf;
	}

	/**
	 * @param scheduleOnBehalfOf
	 *            the scheduleOnBehalfOf to set
	 */
	public void setScheduleOnBehalfOf(int scheduleOnBehalfOf) {
		ScheduleOnBehalfOf = scheduleOnBehalfOf;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return InstanceName;
	}

	/**
	 * @param instanceName
	 *            the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	/**
	 * @return the notification
	 */
	public Notification getNotification() {
		return notification;
	}

	/**
	 * @param notification
	 *            the notification to set
	 */
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	/**
	 * @return the destinationManaged
	 */
	public DestinationManaged getDestinationManaged() {
		return destinationManaged;
	}

	/**
	 * @param destinationManaged
	 *            the destinationManaged to set
	 */
	public void setDestinationManaged(DestinationManaged destinationManaged) {
		this.destinationManaged = destinationManaged;
	}

	/**
	 * @return the destinationDiskUnManaged
	 */
	public DestinationDisk getDestinationDiskUnManaged() {
		return destinationDiskUnManaged;
	}

	/**
	 * @param destinationDiskUnManaged
	 *            the destinationDiskUnManaged to set
	 */
	public void setDestinationDiskUnManaged(DestinationDisk destinationDiskUnManaged) {
		this.destinationDiskUnManaged = destinationDiskUnManaged;
	}

	/**
	 * @return the destinationSmtp
	 */
	public DestinationSmtp getDestinationSmtp() {
		return destinationSmtp;
	}

	/**
	 * @param destinationSmtp
	 *            the destinationSmtp to set
	 */
	public void setDestinationSmtp(DestinationSmtp destinationSmtp) {
		this.destinationSmtp = destinationSmtp;
	}

	/**
	 * @return the destinationFtp
	 */
	public DestinationFtp getDestinationFtp() {
		return destinationFtp;
	}

	/**
	 * @param destinationFtp
	 *            the destinationFtp to set
	 */
	public void setDestinationFtp(DestinationFtp destinationFtp) {
		this.destinationFtp = destinationFtp;
	}

	/**
	 * @return the typeText
	 */
	public String getTypeText() {
		return typeText;
	}

	/**
	 * @param typeText
	 *            the typeText to set
	 */
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	/**
	 * @return the intervalDays
	 */
	public Integer getIntervalDays() {
		return IntervalDays;
	}

	/**
	 * @param intervalDays
	 *            the intervalDays to set
	 */
	public void setIntervalDays(Integer intervalDays) {
		IntervalDays = intervalDays;
	}

	/**
	 * @return the intervalHours
	 */
	public Integer getIntervalHours() {
		return IntervalHours;
	}

	/**
	 * @param intervalHours
	 *            the intervalHours to set
	 */
	public void setIntervalHours(Integer intervalHours) {
		IntervalHours = intervalHours;
	}

	/**
	 * @return the intervalMinutes
	 */
	public Integer getIntervalMinutes() {
		return IntervalMinutes;
	}

	/**
	 * @param intervalMinutes
	 *            the intervalMinutes to set
	 */
	public void setIntervalMinutes(Integer intervalMinutes) {
		IntervalMinutes = intervalMinutes;
	}

	/**
	 * @return the intervalNthDay
	 */
	public Integer getIntervalNthDay() {
		return IntervalNthDay;
	}

	/**
	 * @param intervalNthDay
	 *            the intervalNthDay to set
	 */
	public void setIntervalNthDay(Integer intervalNthDay) {
		IntervalNthDay = intervalNthDay;
	}

	/**
	 * @return the intervalWeeks
	 */
	public Integer getIntervalWeeks() {
		return IntervalWeeks;
	}

	/**
	 * @param intervalWeeks
	 *            the intervalWeeks to set
	 */
	public void setIntervalWeeks(Integer intervalWeeks) {
		IntervalWeeks = intervalWeeks;
	}

	/**
	 * @return the dependants
	 */
	public List<Integer> getDependants() {
		return Dependants;
	}

	/**
	 * @param dependants
	 *            the dependants to set
	 */
	public void setDependants(List<Integer> dependants) {
		Dependants = dependants;
	}

	/**
	 * @return the dependencies
	 */
	public List<Integer> getDependencies() {
		return Dependencies;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<Integer> dependencies) {
		Dependencies = dependencies;
	}

}
