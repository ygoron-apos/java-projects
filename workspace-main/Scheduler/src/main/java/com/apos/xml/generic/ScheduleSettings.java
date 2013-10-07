/**
 * 
 */
package com.apos.xml.generic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import com.apos.encryption.Encryptor;
import com.apos.infoobject.properties.CommonConstants;
import com.apos.infoobject.xml.DestinationDisk;
import com.apos.infoobject.xml.DestinationFtp;
import com.apos.infoobject.xml.DestinationManaged;
import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.PageLayoutSettings;
import com.apos.infoobject.xml.ProgramObject;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.ReportParameter;
import com.apos.infoobject.xml.ReportPrinterOptions;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "SchedulingInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleSettings {

	@XmlElement(name = "ScheduleType")
	private ScheduleFreqType scheduleFreqType;
	
	private final static Logger Log=Logger.getLogger(ScheduleSettings.class);

	private int RefId;
	private boolean IsUseObjectId;
	private int ObjectId;
	private boolean IsUseObjectPath;
	private String ObjectPath = "Object Path";

	private boolean IsDeleteRecurringSchedules;
	private boolean IsCleanUpInstances;

	private Integer IntervalMonths;
	private Integer IntervalDays;
	private Integer IntervalWeeks;
	private Integer IntervalHours;
	private Integer IntervalMinutes;
	private Integer IntervalNthDay;
	private String InstanceName;

	private Date BeginDate;
	private Date EndDate;

	private Integer RetriesAllowed;
	private Integer RetryInterval;
	private boolean RightNow;
	private String ScheduleOnBehalfOf;

	private String ServerGroup;
	private String ServerGroupChoice;

	private List<String> Dependants = new ArrayList<String>();
	private List<String> Dependencies = new ArrayList<String>();
	private FormatOptions FormatOptions;

	private List<ReportParameter> Parameters = new ArrayList<ReportParameter>();

	@XmlElement(name = "DestinationManaged")
	private DestinationManaged destinationManaged;
	@XmlElement(name = "DestinationDisk")
	private DestinationDisk destinationDiskUnManaged;
	@XmlElement(name = "DestinationSmtp")
	private DestinationSmtp destinationSmtp;
	@XmlElement(name = "DestinationFtp")
	private DestinationFtp destinationFtp;
	@XmlElement(name = "DestinationFrs")
	private String destinationFrs;

	private boolean IsUseRecordFormula;
	private String GroupFormula;
	private boolean IsUseGroupFormula;
	private String RecordFormula;

	private ReportPrinterOptions ReportPrinterOptions;
	private PageLayoutSettings PageLayoutSettings;
	private List<ReportLogon> ReportLogons = new ArrayList<ReportLogon>();
	private String AuditNotificationOption;
	private DestinationSmtp EmailNotificationSuccess;
	private DestinationSmtp EmailNotificationFailure;

	private boolean IsAltUser;
	private String AltUserName = "Alt User Name";
	private String AltUserPassword = "Alt User Password";
	private boolean IsAltUserPasswordEncrypted = true;

	@XmlElement(name = "ProgramObject")
	private ProgramObject programObject;

	private List<CustomProperty> CustomProperties = new ArrayList<CustomProperty>();

	/**
	 * @return the scheduleFreqType
	 */
	public ScheduleFreqType getScheduleFreqType() {
		return scheduleFreqType;
	}

	/**
	 * @param scheduleFreqType
	 *            the scheduleFreqType to set
	 */
	public void setScheduleFreqType(ScheduleFreqType scheduleFreqType) {
		this.scheduleFreqType = scheduleFreqType;
	}

	/**
	 * @return the intervalMonths
	 */
	public Integer getIntervalMonths() {
		return IntervalMonths;
	}

	/**
	 * @param intervalMonths
	 *            the intervalMonths to set
	 */
	public void setIntervalMonths(Integer intervalMonths) {
		IntervalMonths = intervalMonths;
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
	 * @return the retriesAllowed
	 */
	public Integer getRetriesAllowed() {
		return RetriesAllowed;
	}

	/**
	 * @param retriesAllowed
	 *            the retriesAllowed to set
	 */
	public void setRetriesAllowed(Integer retriesAllowed) {
		RetriesAllowed = retriesAllowed;
	}

	/**
	 * @return the retryInterval
	 */
	public Integer getRetryInterval() {
		return RetryInterval;
	}

	/**
	 * @param retryInterval
	 *            the retryInterval to set
	 */
	public void setRetryInterval(Integer retryInterval) {
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
	public String getScheduleOnBehalfOf() {
		return ScheduleOnBehalfOf;
	}

	/**
	 * @param scheduleOnBehalfOf
	 *            the scheduleOnBehalfOf to set
	 */
	public void setScheduleOnBehalfOf(String scheduleOnBehalfOf) {
		ScheduleOnBehalfOf = scheduleOnBehalfOf;
	}

	/**
	 * @return the dependants
	 */
	public List<String> getDependants() {
		return Dependants;
	}

	/**
	 * @param dependants
	 *            the dependants to set
	 */
	public void setDependants(List<String> dependants) {
		Dependants = dependants;
	}

	/**
	 * @return the dependencies
	 */
	public List<String> getDependencies() {
		return Dependencies;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<String> dependencies) {
		Dependencies = dependencies;
	}

	/**
	 * @return the formatOptions
	 */
	public FormatOptions getFormatOptions() {
		return FormatOptions;
	}

	/**
	 * @param formatOptions
	 *            the formatOptions to set
	 */
	public void setFormatOptions(FormatOptions formatOptions) {
		FormatOptions = formatOptions;
	}

	/**
	 * @return the parameters
	 */
	public List<ReportParameter> getParameters() {
		return Parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(List<ReportParameter> parameters) {
		Parameters = parameters;
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
	 * @return the serverGroup
	 */
	public String getServerGroup() {
		return ServerGroup;
	}

	/**
	 * @param serverGroup
	 *            the serverGroup to set
	 */
	public void setServerGroup(String serverGroup) {
		ServerGroup = serverGroup;
	}

	/**
	 * @return the serverGroupChoice
	 */
	public String getServerGroupChoice() {
		return ServerGroupChoice;
	}

	/**
	 * @param serverGroupChoice
	 *            the serverGroupChoice to set
	 */
	public void setServerGroupChoice(String serverGroupChoice) {
		ServerGroupChoice = serverGroupChoice;
	}

	/**
	 * @return the groupFormula
	 */
	public String getGroupFormula() {
		return GroupFormula;
	}

	/**
	 * @param groupFormula
	 *            the groupFormula to set
	 */
	public void setGroupFormula(String groupFormula) {
		GroupFormula = groupFormula;
	}

	/**
	 * @return the recordFormula
	 */
	public String getRecordFormula() {
		return RecordFormula;
	}

	/**
	 * @param recordFormula
	 *            the recordFormula to set
	 */
	public void setRecordFormula(String recordFormula) {
		RecordFormula = recordFormula;
	}

	/**
	 * @return the reportPrinterOptions
	 */
	public ReportPrinterOptions getReportPrinterOptions() {
		return ReportPrinterOptions;
	}

	/**
	 * @param reportPrinterOptions
	 *            the reportPrinterOptions to set
	 */
	public void setReportPrinterOptions(ReportPrinterOptions reportPrinterOptions) {
		ReportPrinterOptions = reportPrinterOptions;
	}

	/**
	 * @return the pageLayoutSettings
	 */
	public PageLayoutSettings getPageLayoutSettings() {
		return PageLayoutSettings;
	}

	/**
	 * @param pageLayoutSettings
	 *            the pageLayoutSettings to set
	 */
	public void setPageLayoutSettings(PageLayoutSettings pageLayoutSettings) {
		PageLayoutSettings = pageLayoutSettings;
	}

	/**
	 * @return the reportLogons
	 */
	public List<ReportLogon> getReportLogons() {
		return ReportLogons;
	}

	/**
	 * @param reportLogons
	 *            the reportLogons to set
	 */
	public void setReportLogons(List<ReportLogon> reportLogons) {
		ReportLogons = reportLogons;
	}

	/**
	 * @return the auditOption
	 */
	public String getAuditOption() {
		return AuditNotificationOption;
	}

	/**
	 * @param auditOption
	 *            the auditOption to set
	 */
	public void setAuditOption(String auditOption) {
		AuditNotificationOption = auditOption;
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
	 * @return the isUseAltUser
	 */
	public boolean isAltUser() {
		return IsAltUser;
	}

	/**
	 * @param isUseAltUser
	 *            the isUseAltUser to set
	 */
	public void setIsAltUser(boolean isAltUser) {
		IsAltUser = isAltUser;
	}

	/**
	 * @return the altUserName
	 */
	public String getAltUserName() {
		return AltUserName;
	}

	/**
	 * @param altUserName
	 *            the altUserName to set
	 */
	public void setAltUserName(String altUserName) {
		AltUserName = altUserName;
	}

	/**
	 * @return the altPassword
	 * @throws Exception 
	 */
	public String getAltUserPassword() throws Exception {
		Encryptor encryptor= new Encryptor();
		try{
		String retString=IsAltUserPasswordEncrypted ? new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, AltUserPassword.getBytes())) : AltUserPassword;
		return retString;
		}catch (Exception ee){
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return "";
//		return IsAltUserPasswordEncrypted ? new String(Encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, AltUserPassword.getBytes())) : AltUserPassword;
		// return Password;
	}

	/**
	 * @param altPassword
	 *            the altPassword to set
	 */
	public void setAltUserPassword(String altPassword) {
		AltUserPassword = altPassword;
	}

	/**
	 * @return the isUseObjectId
	 */
	public boolean isIsUseObjectId() {
		return IsUseObjectId;
	}

	/**
	 * @param isUseObjectId
	 *            the isUseObjectId to set
	 */
	public void setIsUseObjectId(boolean isUseObjectId) {
		IsUseObjectId = isUseObjectId;
	}

	/**
	 * @return the objectId
	 */
	public int getObjectId() {
		return ObjectId;
	}

	/**
	 * @param objectId
	 *            the objectId to set
	 */
	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	/**
	 * @return the isUseObjectPath
	 */
	public boolean isIsUseObjectPath() {
		return IsUseObjectPath;
	}

	/**
	 * @param isUseObjectPath
	 *            the isUseObjectPath to set
	 */
	public void setIsUseObjectPath(boolean isUseObjectPath) {
		IsUseObjectPath = isUseObjectPath;
	}

	/**
	 * @return the objectPath
	 */
	public String getObjectPath() {
		return ObjectPath;
	}

	/**
	 * @param objectPath
	 *            the objectPath to set
	 */
	public void setObjectPath(String objectPath) {
		ObjectPath = objectPath;
	}

	/**
	 * @return the isDeleteRecurringSchedules
	 */
	public boolean isIsDeleteRecurringSchedules() {
		return IsDeleteRecurringSchedules;
	}

	/**
	 * @param isDeleteRecurringSchedules
	 *            the isDeleteRecurringSchedules to set
	 */
	public void setIsDeleteRecurringSchedules(boolean isDeleteRecurringSchedules) {
		IsDeleteRecurringSchedules = isDeleteRecurringSchedules;
	}

	/**
	 * @return the isCleanUpInstances
	 */
	public boolean isIsCleanUpInstances() {
		return IsCleanUpInstances;
	}

	/**
	 * @param isCleanUpInstances
	 *            the isCleanUpInstances to set
	 */
	public void setIsCleanUpInstances(boolean isCleanUpInstances) {
		IsCleanUpInstances = isCleanUpInstances;
	}

	/**
	 * @return the customProperties
	 */
	public List<CustomProperty> getCustomProperties() {
		return CustomProperties;
	}

	/**
	 * @param customProperties
	 *            the customProperties to set
	 */
	public void setCustomProperties(List<CustomProperty> customProperties) {
		CustomProperties = customProperties;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return BeginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		BeginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return EndDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	/**
	 * @return the isUseGroupFormula
	 */
	public boolean isIsUseGroupFormula() {
		return IsUseGroupFormula;
	}

	/**
	 * @param isUseGroupFormula
	 *            the isUseGroupFormula to set
	 */
	public void setIsUseGroupFormula(boolean isUseGroupFormula) {
		IsUseGroupFormula = isUseGroupFormula;
	}

	/**
	 * @return the isUseRecordFormula
	 */
	public boolean isIsUseRecordFormula() {
		return IsUseRecordFormula;
	}

	/**
	 * @param isUseRecordFormula
	 *            the isUseRecordFormula to set
	 */
	public void setIsUseRecordFormula(boolean isUseRecordFormula) {
		IsUseRecordFormula = isUseRecordFormula;
	}

	/**
	 * @return the refId
	 */
	public int getRefId() {
		return RefId;
	}

	/**
	 * @param refId
	 *            the refId to set
	 */
	public void setRefId(int refId) {
		RefId = refId;
	}

	/**
	 * @return the programObject
	 */
	public ProgramObject getProgramObject() {
		return programObject;
	}

	/**
	 * @param programObject
	 *            the programObject to set
	 */
	public void setProgramObject(ProgramObject programObject) {
		this.programObject = programObject;
	}

	/**
	 * @return the destinationFRS
	 */
	public String getDestinationFrs() {
		return destinationFrs;
	}

	/**
	 * @param destinationFRS
	 *            the destinationFRS to set
	 */
	public void setDestinationFrs(String destinationFrs) {
		this.destinationFrs = destinationFrs;
	}

	/**
	 * @return the isAltUserPasswordEncrypted
	 */
	public boolean isIsAltUserPasswordEncrypted() {
		return IsAltUserPasswordEncrypted;
	}

	/**
	 * @param isAltUserPasswordEncrypted the isAltUserPasswordEncrypted to set
	 */
	public void setIsAltUserPasswordEncrypted(boolean isAltUserPasswordEncrypted) {
		IsAltUserPasswordEncrypted = isAltUserPasswordEncrypted;
	}

}
