/**
 * 
 */
package com.apos.scheduler;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.apos.infoobject.properties.CommonConstants;
import com.apos.infoobject.properties.CommonThreadTools;
import com.apos.infoobject.properties.Type1WorkerThread;
import com.apos.infoobject.properties.Type2WorkerThread;
import com.apos.infoobject.xml.Attachment;
import com.apos.infoobject.xml.DestinationDisk;
import com.apos.infoobject.xml.DestinationFtp;
import com.apos.infoobject.xml.DestinationManaged;
import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.Inbox;
import com.apos.infoobject.xml.InfoObject;
import com.apos.infoobject.xml.PageLayoutSettings;
import com.apos.infoobject.xml.ParameterValue;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.ProgramObject;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.ReportParameter;
import com.apos.infoobject.xml.ReportPrinterOptions;
import com.apos.infoobject.xml.SchedulingInfo;
import com.apos.infoobject.xml.WebiPrompt;
import com.apos.infoobject.xml.WebiPromptValue;
import com.apos.michael.WebiRefresh;
import com.apos.xml.generic.FormatOptions;
import com.apos.xml.generic.ScheduleSettings;
import com.apos.xml.generic.ScheduleFreqType;
import com.apos.xml.generic.freq.Calendar;
import com.apos.xml.generic.freq.CalendarDay;
import com.apos.xml.generic.freq.Hourly;
import com.businessobjects.sdk.plugin.desktop.webi.IWebi;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiProcessingInfo;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiPrompt;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;
import com.crystaldecisions.sdk.occa.infostore.ICalendarDay;
import com.crystaldecisions.sdk.occa.infostore.ICalendarRunDays;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.INotifications;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.plugin.desktop.calendar.IBusinessCalendarDay;
import com.crystaldecisions.sdk.plugin.desktop.calendar.IBusinessCalendarDays;
import com.crystaldecisions.sdk.plugin.desktop.calendar.ICalendar;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportLogon;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameter;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterRangeValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterSingleValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterValues;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportPrinterOptions;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportProcessingInfo;
import com.crystaldecisions.sdk.plugin.desktop.objectpackage.IObjectPackage;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;
import com.crystaldecisions.sdk.plugin.desktop.user.IUser;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.plugin.destination.ftp.IFTPOptions;
import com.crystaldecisions.sdk.plugin.destination.managed.IManagedOptions;
import com.crystaldecisions.sdk.plugin.destination.smtp.IAttachment;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;
import com.crystaldecisions.sdk.properties.ISDKList;

/**
 * @author Yuri Goron
 * 
 */
public class Utils {

	private final static Logger Log = Logger.getLogger(Utils.class);
	


	/**
	 * Schedule Object (1st in info objects) using settings in the
	 * sheduleSettinsg object
	 * 
	 * @param infoStore
	 * @param infoObjects
	 * @param xmlScheduleSettings
	 * @param jaxb2Marshaller
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int scheduleObjectTyp3Temp(IInfoStore infoStore, IInfoObjects infoObjects, InfoObject xmlScheduleSettings, Jaxb2Marshaller jaxb2Marshaller) {
		try {

			StringWriter result = new StringWriter();
			Log.debug("UnMarshalling:");
			jaxb2Marshaller.marshal(xmlScheduleSettings, new StreamResult(result));
			Log.debug(result.toString());

			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			if (xmlScheduleSettings.getSchedulingInfo() != null) {
				SchedulingInfo xmlSchedulingInfo = xmlScheduleSettings.getSchedulingInfo();

				if (xmlSchedulingInfo.getInstanceName() != null) {
					infoObject.setTitle(xmlSchedulingInfo.getInstanceName());
					Log.debug("Instance name set to:" + infoObject.getTitle());
				}
				if (xmlScheduleSettings.getSchedulingInfo().getDestinationManaged() != null) {

					if (xmlSchedulingInfo.getDestinationManaged().getUserIdInboxes() != null) {
						Log.debug("Schedule to Inboxes");
						DestinationManaged xmlDestinationManaged = xmlSchedulingInfo.getDestinationManaged();
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(
								"SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_MANAGED + "'").get(0);
						IManagedOptions scheduleOptions = (IManagedOptions) destinationPlugin.getScheduleOptions();
						scheduleOptions.setSendOption(xmlDestinationManaged.getSendOption());
						@SuppressWarnings("rawtypes")
						Set userSet = scheduleOptions.getDestinations();
						userSet.clear();
						for (Integer inboxId : xmlDestinationManaged.getUserIdInboxes()) {
							userSet.add(inboxId);
						}
						ISchedulingInfo schedulingInfo = infoObject.getSchedulingInfo();
						IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_MANAGED);
						destination.setFromPlugin(destinationPlugin);
					}
				} else if (xmlScheduleSettings.getSchedulingInfo().getDestinationDiskUnManaged() != null) {
					Log.debug("Schedule to Disk Unmanaged");
					DestinationDisk xmlDestinationDisk = xmlSchedulingInfo.getDestinationDiskUnManaged();
					IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(
							"SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_UNMANAGED + "'").get(0);

					IDiskUnmanagedOptions diskUnmanagedOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();
					diskUnmanagedOptions.getDestinationFiles().clear();
					diskUnmanagedOptions.getDestinationFiles().add(xmlDestinationDisk.getOutputFileName());

					ISchedulingInfo schedulingInfo = infoObject.getSchedulingInfo();
					IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_UNMANAGED);
					destination.setFromPlugin(destinationPlugin);
				}

				if (xmlScheduleSettings.getPluginInterface() != null) {
					if (xmlScheduleSettings.getPluginInterface().getReportParameters() != null) {
						List<ReportParameter> reportParameters = xmlScheduleSettings.getPluginInterface().getReportParameters();
						for (ReportParameter reportParameter : reportParameters) {
							Log.debug("Setting Crystal Report Parameter :" + reportParameter.getName());
							setCrystalReportParameter(reportParameter, infoObject);
						}
					}

					if (xmlScheduleSettings.getPluginInterface().getReportLogons() != null) {
						List<ReportLogon> xmlReportLogon = xmlScheduleSettings.getPluginInterface().getReportLogons();
						for (ReportLogon reportLogon : xmlReportLogon) {
							if (!reportLogon.isUseOriginalDataSource()) {
								setReportLogon(reportLogon, infoObject);
							}
						}
					}

					if (xmlScheduleSettings.getPluginInterface().getReportFormatOptions() != null) {
						IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;
						Log.debug("Setting Crystal Report Format Options To:" + xmlScheduleSettings.getPluginInterface().getReportFormatOptions().getFormat());
						procesingInfo.getReportFormatOptions().setFormat(xmlScheduleSettings.getPluginInterface().getReportFormatOptions().getFormat());
					}
					if (xmlScheduleSettings.getPluginInterface().getGroupFormula() != null) {
						IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;
						Log.debug("Setting Group Selection Formula To:" + xmlScheduleSettings.getPluginInterface().getGroupFormula());
						procesingInfo.setGroupFormula(xmlScheduleSettings.getPluginInterface().getGroupFormula());

					}
					if (xmlScheduleSettings.getPluginInterface().getRecordFormula() != null) {
						IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;
						Log.debug("Setting Record Selection Formula To:" + xmlScheduleSettings.getPluginInterface().getRecordFormula());
						procesingInfo.setRecordFormula(xmlScheduleSettings.getPluginInterface().getRecordFormula());

					}

				} else if (xmlScheduleSettings.getPluginInterfaceWebi() != null) {

					if (xmlScheduleSettings.getPluginInterfaceWebi().getWebiFormatOptions() != null) {
						IWebiProcessingInfo procesingInfo = (IWebiProcessingInfo) infoObject;
						Log.debug("Setting Webi Format Options To:" + xmlScheduleSettings.getPluginInterfaceWebi().getWebiFormatOptions().getFormat());
						procesingInfo.getWebiFormatOptions().setFormat(xmlScheduleSettings.getPluginInterfaceWebi().getWebiFormatOptions().getFormat());
					}
					if (xmlScheduleSettings.getPluginInterfaceWebi().getPrompts() != null) {
						// WebiRefresh.updatePromptsForScheduling(infoObject);
						List<WebiPrompt> prompts = xmlScheduleSettings.getPluginInterfaceWebi().getPrompts();
						for (WebiPrompt webiPrompt : prompts) {
							Log.debug("Setting Webi Prompt:" + webiPrompt.getName());
							setWebiPrompt(webiPrompt, infoObject);
						}
					}
				}

			}

			switch (xmlScheduleSettings.getSchedulingInfo().getType()) {
			case 0:
				Log.debug("Scheduling Right Now");
				infoObject.getSchedulingInfo().setRightNow(true);
				break;
			case 1:
				Log.debug("Scheduling Hourly");
				setStartEndDate(infoObject, xmlScheduleSettings);
				infoObject.getSchedulingInfo().setType(CeScheduleType.HOURLY);
				infoObject.getSchedulingInfo().setIntervalHours(xmlScheduleSettings.getSchedulingInfo().getIntervalHours());
				break;

			case 2:
				Log.debug("Scheduling Daily");
				setStartEndDate(infoObject, xmlScheduleSettings);
				infoObject.getSchedulingInfo().setType(CeScheduleType.DAILY);
				infoObject.getSchedulingInfo().setIntervalDays(xmlScheduleSettings.getSchedulingInfo().getIntervalDays());
				break;

			case 4:
				Log.debug("Scheduling Monthly");
				infoObject.getSchedulingInfo().setType(CeScheduleType.MONTHLY);
				setStartEndDate(infoObject, xmlScheduleSettings);
				infoObject.getSchedulingInfo().setIntervalMonths(xmlScheduleSettings.getSchedulingInfo().getIntervalMonths());
				break;

			case 5:
				Log.debug("Scheduling NthDay");
				infoObject.getSchedulingInfo().setType(CeScheduleType.NTH_DAY);
				setStartEndDate(infoObject, xmlScheduleSettings);
				infoObject.getSchedulingInfo().setIntervalNthDay(xmlScheduleSettings.getSchedulingInfo().getIntervalNthDay());
				break;

			case 8:
				Log.debug("Scheduling Calendar");
				infoObject.getSchedulingInfo().setType(CeScheduleType.CALENDAR);
				setStartEndDate(infoObject, xmlScheduleSettings);
				// infoObject.getSchedulingInfo().setCalendarTemplate(xmlInfoObject.getSchedulingInfo().get);
				break;

			}

			infoStore.schedule(infoObjects);
			infoObject = (IInfoObject) infoObjects.get(0);
			int instanceId = Integer.parseInt(infoObject.properties().getProperty("SI_NEW_JOB_ID").toString());
			Log.info("Report Has Been Scheduled. New Instance Id:" + instanceId);
			return instanceId;

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return -1;

	}

	/**
	 * Sets Begin And End Dates
	 * 
	 * @param infoObject
	 * @param xmlInfoObject
	 * @return
	 */
	private boolean setStartEndDate(IInfoObject infoObject, InfoObject xmlInfoObject) {

		try {
			Log.debug("Beging Date String:" + xmlInfoObject.getSchedulingInfo().getBeginDate());
			Date beginDate = new SimpleDateFormat(CommonConstants.CMS_DATE_FORMAT_SCHEDULE).parse(xmlInfoObject.getSchedulingInfo().getBeginDate());
			infoObject.getSchedulingInfo().setBeginDate(beginDate);
			Log.debug("Begin Date:" + infoObject.getSchedulingInfo().getBeginDate().toString());
			Date endDate = new SimpleDateFormat(CommonConstants.CMS_DATE_FORMAT_SCHEDULE).parse(xmlInfoObject.getSchedulingInfo().getEndDate());
			infoObject.getSchedulingInfo().setEndDate(endDate);
			Log.debug("End Date:" + infoObject.getSchedulingInfo().getEndDate());
			return true;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return false;

	}

	/**
	 * Sets Report Logons
	 * 
	 * @param xmlReportLogon
	 * @param infoObject
	 * @return
	 */
	public boolean setReportLogon(ReportLogon xmlReportLogon, IInfoObject infoObject) {
		try {
			IReport report = (IReport) infoObject;
			ISDKList dbLogons = report.getReportLogons();
			for (int i = 0; i < dbLogons.size(); i++) {
				IReportLogon dbLogon = (IReportLogon) dbLogons.get(i);

				if (dbLogon.getServerName().equalsIgnoreCase(xmlReportLogon.getServerName())) {
					dbLogon.setCustomDatabaseName(xmlReportLogon.getCustomDatabaseName());
					dbLogon.setUserName(xmlReportLogon.getCustomUserName());
					dbLogon.setPassword(xmlReportLogon.getCustomPassword());
					Log.debug("User is set to:" + xmlReportLogon.getCustomUserName());
				}

			}
			return true;
		} catch (Exception ee) {

		}
		return false;
	}

	/**
	 * Setting Webi
	 * 
	 * @param xmlWebiPrompt
	 * @param infoObject
	 * @return
	 */
	private void setWebiPrompt(WebiPrompt xmlWebiPrompt, IInfoObject infoObject) {
		if (infoObject.isInstance())
			setWebiPromptsForInstanceObject(xmlWebiPrompt, infoObject);
		else
			setWebiPromptsForObject(xmlWebiPrompt, infoObject);
	}

	private boolean setWebiPromptsForObject(WebiPrompt xmlWebiPrompt, IInfoObject infoObject) {
		try {

			return true;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return false;
	}

	/**
	 * Sets Webi Prompts for Instance Object
	 * 
	 * @param xmlWebiPrompt
	 * @param infoObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean setWebiPromptsForInstanceObject(WebiPrompt xmlWebiPrompt, IInfoObject infoObject) {
		try {
			IWebiProcessingInfo webiProcessingInfo = (IWebiProcessingInfo) infoObject;

			Log.debug("Is Webi Has Prompts:" + webiProcessingInfo.hasPrompts());
			Log.debug("Webi Prompts for report " + infoObject.getID() + " " + webiProcessingInfo.getPrompts().size());

			IWebi webi = (IWebi) infoObject;

			Log.debug("Number of prompts in iWebi:" + webi.getPrompts().size());

			for (int i = 0; i < webiProcessingInfo.getPrompts().size(); i++) {
				IWebiPrompt webiPrompt = (IWebiPrompt) webiProcessingInfo.getPrompts().get(i);
				if (webiPrompt.getName().equals(xmlWebiPrompt.getName())) {
					Log.debug("Webi Prompt is Found:" + webiPrompt.getName());
					webiPrompt.getValues().clear();
					for (WebiPromptValue xmlPromptValue : xmlWebiPrompt.getValues()) {
						webiPrompt.getValues().add(xmlPromptValue.getValue());
						Log.debug("Added Value:" + xmlPromptValue.getValue());
					}
				}
			}
			return true;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return false;

	}

	/**
	 * Set Crystal Report Parameter
	 * 
	 * @param xmlReportParameter
	 * @param infoObject
	 * @return
	 */
	public boolean setCrystalReportParameter(ReportParameter xmlReportParameter, IInfoObject infoObject) {
		try {

			IReportProcessingInfo processingInfo = (IReportProcessingInfo) infoObject;
			// Log.debug("Parameters for report " + infoObject.getID() + " " +
			// processingInfo.getReportParameters().size());
			for (int i = 0; i < processingInfo.getReportParameters().size(); i++) {
				IReportParameter reportParameter = (IReportParameter) processingInfo.getReportParameters().get(i);
				Log.debug(" Processing Report Name (InfoObject):" + infoObject.getTitle() + " XML Report Name:" + xmlReportParameter.getReportName() + "| Sub Report Name (XML):"
						+ xmlReportParameter.getSubReportName() + "| Parameter Name:" + reportParameter.getParameterName());
				if (reportParameter.getParameterName().equalsIgnoreCase(xmlReportParameter.getName()) && reportParameter.getReportName().equalsIgnoreCase(xmlReportParameter.getSubReportName())
						&& xmlReportParameter.getReportName().equalsIgnoreCase(infoObject.getTitle())) {
					Log.debug("Setting Report Paramater " + xmlReportParameter.getName() + "| For "
							+ (StringUtils.isBlank(xmlReportParameter.getSubReportName()) ? "Main Report" : "SubReport:" + xmlReportParameter.getSubReportName()));

					if (reportParameter.getCurrentValues() != null) {
						IReportParameterValues currentValues = reportParameter.getCurrentValues();
						currentValues.clear();
						List<ParameterValue> xmlCurrentValues = xmlReportParameter.getCurrentValues();

						for (ParameterValue parameterValue : xmlCurrentValues) {

							if (!reportParameter.isRangeValueSupported()) {

								if (reportParameter.isDiscreteValueSupported()) {

									IReportParameterSingleValue currentValue;
									currentValue = reportParameter.getCurrentValues().addSingleValue();
									currentValue.setValue(parameterValue.getValue());
									Log.debug("Parameter :" + reportParameter.getParameterName() + " Set To:" + parameterValue.getValue());
								}
							} else {

								IReportParameterRangeValue rangeValue;
								rangeValue = reportParameter.getCurrentValues().addRangeValue();
								rangeValue.setLowerBoundIncluded(parameterValue.isIsIncludeValueFrom());
								rangeValue.setLowerBoundNotAvailable(parameterValue.isIsNoLowerValue());
								rangeValue.setUpperBoundIncluded(parameterValue.isIsIncludeValueTo());
								rangeValue.setUpperBoundNotAvailable(parameterValue.isIsNoUpperValue());

								if (rangeValue.getFromValue() != null && !parameterValue.isIsRangeValueFromNull()) {
									rangeValue.getFromValue().setValue(parameterValue.getRangeValueFrom());
									Log.debug("Range Value From Set:" + rangeValue.getFromValue().getValue());
								}

								if (rangeValue.getToValue() != null && !parameterValue.isIsRangeValueToNull()) {
									rangeValue.getToValue().setValue(parameterValue.getRangeValueTo());
									Log.debug("Range Value To Set:" + rangeValue.getToValue().getValue());
								}
								rangeValue.getFromValue().setNull(parameterValue.isIsRangeValueFromNull());
								rangeValue.getToValue().setNull(parameterValue.isIsRangeValueToNull());

							}

							Log.debug("Current Values Size:" + currentValues.size());

						}

					}

				}
			}

			return true;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return false;
	}

	/**
	 * Create Sample XML
	 * 
	 * @param schedulingInfo
	 * @param infoStore
	 * @return
	 */
	public ScheduleSettings getSchedulingInfo(IInfoObject infoObjectTemplate, IInfoStore infoStore, int objectId) {
		ScheduleSettings xmlSchedulingInfo = new ScheduleSettings();
		xmlSchedulingInfo.setRefId(92341);
		Log.debug("Begin Date:" + infoObjectTemplate.getSchedulingInfo().getBeginDate());
		Log.debug("End Date:" + infoObjectTemplate.getSchedulingInfo().getEndDate());
		// xmlSchedulingInfo.setBeginDate(Type2WorkerThread.formatter.format(infoObject.getSchedulingInfo().getBeginDate()));
		// xmlSchedulingInfo.setEndDate(Type2WorkerThread.formatter.format(infoObject.getSchedulingInfo().getEndDate()));

		xmlSchedulingInfo.setBeginDate(infoObjectTemplate.getSchedulingInfo().getBeginDate());
		xmlSchedulingInfo.setEndDate(infoObjectTemplate.getSchedulingInfo().getEndDate());

		xmlSchedulingInfo.setInstanceName(infoObjectTemplate.getTitle());
		xmlSchedulingInfo.setIntervalMonths(infoObjectTemplate.getSchedulingInfo().getIntervalMonths());
		xmlSchedulingInfo.setIntervalDays(infoObjectTemplate.getSchedulingInfo().getIntervalDays());
		xmlSchedulingInfo.setIntervalMinutes(infoObjectTemplate.getSchedulingInfo().getIntervalMinutes());
		xmlSchedulingInfo.setIntervalNthDay(infoObjectTemplate.getSchedulingInfo().getIntervalNthDay());
		xmlSchedulingInfo.setObjectId(objectId);
		xmlSchedulingInfo.setIsUseObjectId(true);

		Log.debug("Server Group Id:" + infoObjectTemplate.getSchedulingInfo().getServerGroup());
		Log.debug("Server Group Choice:" + infoObjectTemplate.getSchedulingInfo().getServerGroupChoice());
		CommonThreadTools commonThreadTools = new CommonThreadTools();

		String cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID=" + infoObjectTemplate.getSchedulingInfo().getServerGroup();

		if (infoObjectTemplate.getSchedulingInfo().getServerGroup() > 0) {
			try {
				Log.debug("Running Query:" + cmsQuery);
				IInfoObject serverGroup = (IInfoObject) infoStore.query(cmsQuery).get(0);
				xmlSchedulingInfo.setServerGroup(serverGroup.getTitle());
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
			}
		}

		if (infoObjectTemplate.getSchedulingInfo().getServerGroupChoice() > 0) {
			cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID=" + infoObjectTemplate.getSchedulingInfo().getServerGroupChoice();

			try {
				Log.debug("Running Query:" + cmsQuery);
				IInfoObject serverGroupChoice = (IInfoObject) infoStore.query(cmsQuery).get(0);
				xmlSchedulingInfo.setServerGroupChoice(serverGroupChoice.getTitle());
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
			}
		}

		xmlSchedulingInfo.setRetriesAllowed(infoObjectTemplate.getSchedulingInfo().getRetriesAllowed());
		xmlSchedulingInfo.setRetryInterval(infoObjectTemplate.getSchedulingInfo().getRetryInterval());
		xmlSchedulingInfo.setRightNow(infoObjectTemplate.getSchedulingInfo().isRightNow());
		Log.debug("Getting On behalf");
		try {
			cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_KIND='User' and SI_ID=" + infoObjectTemplate.getSchedulingInfo().getScheduleOnBehalfOf();
			Log.debug("Running Query:" + cmsQuery);
			IUser user = (IUser) infoStore.query(cmsQuery).get(0);
			xmlSchedulingInfo.setScheduleOnBehalfOf(user.getTitle());
		} catch (Exception ee) {
			Log.error("User Not Found");
			Log.error(ee.getLocalizedMessage(), ee);
		}

		Log.debug("Getting Dependants");
		try {

			List<String> dependants = new ArrayList<String>();
			xmlSchedulingInfo.setDependants(dependants);
			for (int i = 0; i < infoObjectTemplate.getSchedulingInfo().getDependants().size(); i++) {
				cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE  SI_ID=" + infoObjectTemplate.getSchedulingInfo().getDependants().get(i);
				Log.debug("Running Query:" + cmsQuery);
				IInfoObject dependandObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
				Log.debug("Object Found:" + dependandObject.getID());
				dependants.add(dependandObject.getTitle());

			}
		} catch (Exception ee) {
			Log.error("Object Not Found");
			Log.error(ee.getLocalizedMessage(), ee);
		}
		Log.debug("Getting Dependancies");
		try {

			List<String> dependancies = new ArrayList<String>();
			xmlSchedulingInfo.setDependencies(dependancies);
			for (int i = 0; i < infoObjectTemplate.getSchedulingInfo().getDependencies().size(); i++) {
				cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE  SI_ID=" + infoObjectTemplate.getSchedulingInfo().getDependencies().get(i);
				Log.debug("Running Query:" + cmsQuery);
				IInfoObject depInfoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
				Log.debug("Object Found:" + depInfoObject.getID());
				dependancies.add(depInfoObject.getTitle());

			}
		} catch (Exception ee) {
			Log.error("Object Not Found");
			Log.error(ee.getLocalizedMessage(), ee);
		}

		try {
			FormatOptions formatOptions = new FormatOptions();
			xmlSchedulingInfo.setFormatOptions(formatOptions);
			formatOptions.setFormat(infoObjectTemplate.getKind());
			formatOptions.setOptions("Options Comma Delimited. Ext:Y,N,N,N,N,N,N,WholeReport,0,0,1,1,Y,Once");

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		try {
			if (infoObjectTemplate.getSchedulingInfo().getNotifications() != null) {
				Log.debug("Proceed with Notifications");
				int auditOption = infoObjectTemplate.getSchedulingInfo().getNotifications().getAuditOption();

				Log.debug("Audit Option:" + auditOption);

				switch (auditOption) {
				case INotifications.CeAuditOnResult.BOTH:
					xmlSchedulingInfo.setAuditOption(CommonConstants.STR_AUDIT_RESULT_BOTH);
					break;
				case INotifications.CeAuditOnResult.FAILURE:
					xmlSchedulingInfo.setAuditOption(CommonConstants.STR_AUDIT_RESULT_FAILURE);
					break;
				case INotifications.CeAuditOnResult.NONE:
					xmlSchedulingInfo.setAuditOption(CommonConstants.STR_SMTP_AUTH_TYPE_NONE);
					break;
				case INotifications.CeAuditOnResult.SUCCESS:
					xmlSchedulingInfo.setAuditOption(CommonConstants.STR_AUDIT_RESULT_SUCCESS);
					break;
				default:
					xmlSchedulingInfo.setAuditOption(CommonConstants.STR_SMTP_AUTH_TYPE_NONE);
				}

				if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure() != null) {
					Log.debug("Size:" + infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().size());
					if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().size() > 0) {
						IDestination destination = (IDestination) infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().get(0);
						Log.debug("Destination:" + destination.getName());

						if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_SMTP)) {
							DestinationSmtp xmlDestinationSmtp = commonThreadTools.getDestinationSmtp(destination, infoStore);
							xmlSchedulingInfo.setEmailNotificationFailure(xmlDestinationSmtp);
						}
					}

				}

				if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnSuccess() != null) {
					Log.debug("Size:" + infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnSuccess().size());
					if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnSuccess().size() > 0) {
						IDestination destination = (IDestination) infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnSuccess().get(0);
						Log.debug("Destination:" + destination.getName());

						if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_SMTP)) {
							DestinationSmtp xmlDestinationSmtp = commonThreadTools.getDestinationSmtp(destination, infoStore);
							xmlSchedulingInfo.setEmailNotificationSuccess(xmlDestinationSmtp);
						}
					}

				}

			}

			ProgramObject xmlProgramObject = commonThreadTools.getProgramObject(infoObjectTemplate);
			if (xmlProgramObject != null)
				xmlSchedulingInfo.setProgramObject(xmlProgramObject);

			List<ReportLogon> reportLogons = new ArrayList<ReportLogon>();
			if (!infoObjectTemplate.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_OBJECT_PACKAGE)) {
				reportLogons = commonThreadTools.getLogonInfo(infoObjectTemplate, reportLogons);
			} else {

				Log.debug("Process Objects Package");
				IObjectPackage objectPackage = (IObjectPackage) infoObjectTemplate;
				for (int i = 0; i < objectPackage.getPackageComponents().size(); i++) {
					String cuid = (String) objectPackage.getPackageComponents().get(i);
					Log.debug("Processing Objects in Package " + i + " CUID:" + cuid);
					cmsQuery = CommonConstants.STR_CMS_QUERY_BY_CUID + "'" + cuid + "'";
					Log.debug("Running CMS Query:" + cmsQuery);
					IInfoObject objectInPackage = (IInfoObject) infoStore.query(cmsQuery).get(0);
					reportLogons = commonThreadTools.getLogonInfo(objectInPackage, reportLogons);
					Log.debug("Get Prompts for Object Name:" + objectInPackage.getTitle());

				}

			}

			xmlSchedulingInfo.setReportLogons(reportLogons);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		List<ReportParameter> xmlReportParameters = new ArrayList<ReportParameter>();
		try {

			Log.debug("Getting Prompts");
			if (!infoObjectTemplate.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_OBJECT_PACKAGE)) {
				if (infoObjectTemplate.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_WEBI)) {
					WebiRefresh webiRefresh = new WebiRefresh();
					webiRefresh.updatePromptsForScheduling(infoObjectTemplate);
				}

				xmlReportParameters = commonThreadTools.getReportParameters(infoObjectTemplate, xmlReportParameters);

			} else {

				Log.debug("Process Objects Package");
				IObjectPackage objectPackage = (IObjectPackage) infoObjectTemplate;
				for (int i = 0; i < objectPackage.getPackageComponents().size(); i++) {
					String cuid = (String) objectPackage.getPackageComponents().get(i);
					Log.debug("Processing Objects in Package " + i + " CUID:" + cuid);
					cmsQuery = CommonConstants.STR_CMS_QUERY_BY_CUID + "'" + cuid + "'";
					Log.debug("Running CMS Query:" + cmsQuery);
					IInfoObject objectInPackage = (IInfoObject) infoStore.query(cmsQuery).get(0);

					if (objectInPackage.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_WEBI)) {
						WebiRefresh webiRefresh = new WebiRefresh();
						webiRefresh.updatePromptsForScheduling(objectInPackage);
					}

					Log.debug("Get Prompts for Object Name:" + objectInPackage.getTitle());
					xmlReportParameters = commonThreadTools.getReportParameters(objectInPackage, xmlReportParameters);

				}
			}
			xmlSchedulingInfo.setParameters(xmlReportParameters);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return xmlSchedulingInfo;

	}

	/**
	 * 
	 * To create sample XML
	 * 
	 * @param infoObject
	 * @param xmlSchedulingInfo
	 * @param infoStore
	 */
	@SuppressWarnings("unchecked")
	public ScheduleSettings getDestinations(IInfoObject infoObject, ScheduleSettings xmlSchedulingInfo, IInfoStore infoStore) {

		Log.debug("Get Destinations Started");
		try {
			CommonThreadTools commonThreadTools = new CommonThreadTools();

			if (infoObject.getSchedulingInfo().getDestinations() != null) {
				for (int i = 0; i < infoObject.getSchedulingInfo().getDestinations().size(); i++) {
					IDestination destination = (IDestination) infoObject.getSchedulingInfo().getDestinations().get(i);
					Log.debug("Destination:" + destination.getName());

					if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_MANAGED)) {
						DestinationManaged xmlDestinationManaged = new DestinationManaged();
						Log.debug("Destination Managed");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_MANAGED).get(0);
						destination.copyToPlugin(destinationPlugin);

						IManagedOptions scheduleOptions = (IManagedOptions) destinationPlugin.getScheduleOptions();
						xmlDestinationManaged.setIsCreateNewObject(scheduleOptions.isCreateNewObject());
						xmlDestinationManaged.setIsIncludeInstance(scheduleOptions.isIncludeInstance());
						xmlDestinationManaged.setIsInstanceAsParent(scheduleOptions.isInstanceAsParent());
						xmlDestinationManaged.setIsKeepSaveData(scheduleOptions.isKeepSavedData());

						xmlDestinationManaged.setSendOption(scheduleOptions.getSendOption());
						if (scheduleOptions.getSendOption() == IManagedOptions.CeManagedSendOption.ceCopy) {
							xmlDestinationManaged.setSendOptionText(CommonConstants.STR_MANAGED_SEND_OPTION_COPY);
						} else {
							xmlDestinationManaged.setSendOptionText(CommonConstants.STR_MANAGED_SEND_OPTION_SHORCUT);
						}
						switch (scheduleOptions.getDestinationOption()) {

						case IManagedOptions.CeDestinationOption.ceFavoritesFolder:
							xmlDestinationManaged.setDestinationOption(CommonConstants.STR_DESTINATION_OPTION_FAVORITES);
							break;
						case IManagedOptions.CeDestinationOption.ceInbox:
							xmlDestinationManaged.setDestinationOption(CommonConstants.STR_DESTINATION_OPTION_INBOX);
							break;

						}
						xmlDestinationManaged.setTargetObjectName(scheduleOptions.getTargetObjectName());

						xmlDestinationManaged.setCount(scheduleOptions.getDestinations().size());
						xmlDestinationManaged.setName(infoObject.getTitle());
						Log.debug("Destination Size:" + scheduleOptions.getDestinations().size());
						List<Integer> ids = new ArrayList<Integer>();
						List<Inbox> inboxes = new ArrayList<Inbox>();
						for (Iterator<Integer> id = scheduleOptions.getDestinations().iterator(); id.hasNext();) {
							int userId = id.next();
							ids.add(userId);
							String cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID=" + userId + " AND (SI_PROGID='" + CommonConstants.STR_DESTINATION_USER + "' OR SI_PROGID='"
									+ CommonConstants.STR_DESTINATION_GROUP + "')";
							Log.debug("Running CMS Query:" + cmsQuery);
							IInfoObject userObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
							Inbox inbox = new Inbox();
							inbox.setDistribuitionServerString("String to distribution server");
							inbox.setIsDistribitionServer(false);
							inbox.setId(userId);
							inbox.setName(userObject.getTitle());
							inbox.setGroup(userObject.getProgID().equalsIgnoreCase(CommonConstants.STR_DESTINATION_GROUP) ? true : false);
							inboxes.add(inbox);

						}

						xmlDestinationManaged.setUserIdInboxes(ids);
						xmlDestinationManaged.setInboxes(inboxes);
						if (inboxes.size() <= 0)
							xmlDestinationManaged.setIsUseJobServerDefaults(true);
						else
							xmlDestinationManaged.setIsUseJobServerDefaults(false);

						xmlSchedulingInfo.setDestinationManaged(xmlDestinationManaged);
					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.DiskUnmanaged")) {
						DestinationDisk xmlDestinationDisk = new DestinationDisk();
						Log.debug("Destination Disk UNManaged");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_DISK_UNMANAGED).get(0);
						destination.copyToPlugin(destinationPlugin);
						IDiskUnmanagedOptions scheduleOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();
						if (scheduleOptions.getDestinationFiles().size() > 0)
							xmlDestinationDisk.setOutputFileName(scheduleOptions.getDestinationFiles().get(0).toString());

						Log.debug("Input Files Size:" + scheduleOptions.getInputFiles().size());
						xmlDestinationDisk.setUserName(scheduleOptions.getUserName());
						xmlDestinationDisk.setPassword("Password Goes Here");
						xmlSchedulingInfo.setDestinationDiskUnManaged(xmlDestinationDisk);

					} else if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_SMTP)) {

						Log.debug("Destination Smtp");

						DestinationSmtp xmlDestinationSmtp = commonThreadTools.getDestinationSmtp(destination, infoStore);
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_SMTP).get(0);
						destination.copyToPlugin(destinationPlugin);
						ISMTPOptions smtpOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();

						if (smtpOptions.getAttachments() != null) {
							String result = "";
							List<Attachment> xmlAttachments = new ArrayList<Attachment>();
							for (int j = 0; j < smtpOptions.getAttachments().size(); j++) {
								Attachment xmlAttachment = new Attachment();
								IAttachment attachment = (IAttachment) smtpOptions.getAttachments().get(j);
								xmlAttachment.setName(attachment.getEmbedName());
								xmlAttachment.setMimeType(attachment.getMimeType());
								xmlAttachments.add(xmlAttachment);
								result += attachment.getEmbedName() + ",";
							}
							if (result.endsWith(","))
								result = result.substring(0, result.length() - 1);
							xmlDestinationSmtp.setAttachmentName(result);
							xmlDestinationSmtp.setAttachments(xmlAttachments);
						}
						xmlDestinationSmtp.setCC(commonThreadTools.stringFromArray(smtpOptions.getCCAddresses()));
						if (smtpOptions.getSenderAddress() != null)
							xmlDestinationSmtp.setFrom(smtpOptions.getSenderAddress());

						if (smtpOptions.getMessage() != null)
							xmlDestinationSmtp.setMessage(smtpOptions.getMessage());

						if (smtpOptions.getSubject() != null)
							xmlDestinationSmtp.setSubject(smtpOptions.getSubject());

						// TODO - Enable in 4.0
						// xmlDestinationSmtp.setBCC(commonThreadTools.stringFromArray(smtpOptions.getBCCAddresses()));
						xmlDestinationSmtp.setDelimiter(smtpOptions.getDelimiter() == null ? "" : smtpOptions.getDelimiter());
						xmlDestinationSmtp.setDomain(smtpOptions.getDomainName() == null ? "" : smtpOptions.getDomainName());
						xmlDestinationSmtp.setIsAttachmentEnabled(smtpOptions.isAttachmentsEnabled());

						xmlDestinationSmtp.setServerName(smtpOptions.getServerName() == null ? "SMTP Server Name" : smtpOptions.getServerName());

						switch (smtpOptions.getSMTPAuthenticationType()) {
						case ISMTPOptions.CeSMTPAuthentication.LOGIN:
							xmlDestinationSmtp.setAuthenticationType(CommonConstants.STR_SMTP_AUTH_TYPE_LOGIN);
							break;
						case ISMTPOptions.CeSMTPAuthentication.NONE:
							xmlDestinationSmtp.setAuthenticationType(CommonConstants.STR_SMTP_AUTH_TYPE_NONE);
							break;

						case ISMTPOptions.CeSMTPAuthentication.PLAIN:
							xmlDestinationSmtp.setAuthenticationType(CommonConstants.STR_SMTP_AUTH_TYPE_PLAIN);
							break;
						}

						xmlDestinationSmtp.setAuthenticationType(CommonConstants.STR_SMTP_AUTH_TYPE_PLAIN);
						xmlDestinationSmtp.setPassword("Password Goes Here");
						xmlDestinationSmtp.setPort(smtpOptions.getPort());
						xmlDestinationSmtp.setTo(commonThreadTools.stringFromArray(smtpOptions.getToAddresses()));
						xmlSchedulingInfo.setDestinationSmtp(xmlDestinationSmtp);

					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Ftp")) {

						DestinationFtp xmlDestinationFtp = new DestinationFtp();

						Log.debug("Destination Ftp");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_FTP).get(0);
						destination.copyToPlugin(destinationPlugin);
						IFTPOptions ftpOptions = (IFTPOptions) destinationPlugin.getScheduleOptions();

						if (ftpOptions.getDestinationFiles() != null)
							if (ftpOptions.getDestinationFiles().size() > 0)
								xmlDestinationFtp.setPath((ftpOptions.getDestinationFiles().get(0).toString()));
						xmlDestinationFtp.setPassword("Password goes here");
						xmlDestinationFtp.setPort(ftpOptions.getPort());
						xmlDestinationFtp.setServerName(ftpOptions.getServerName());
						xmlDestinationFtp.setUserName(ftpOptions.getUserName());
						xmlSchedulingInfo.setDestinationFtp(xmlDestinationFtp);

					}

				}

			}

			String instanceProgId = infoObject.getProgID();

			if (instanceProgId.equalsIgnoreCase("CrystalEnterprise.Report")) {
				Log.debug("Processing CR Based Instance");

				IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;
				Log.debug("Report Kind:" + infoObject.getKind());
				xmlSchedulingInfo.setGroupFormula(procesingInfo.getGroupFormula());
				xmlSchedulingInfo.setRecordFormula(procesingInfo.getRecordFormula());

				if (procesingInfo.getReportPrinterOptions() != null) {
					ReportPrinterOptions xmlReportPrinterOptions = new ReportPrinterOptions();
					xmlReportPrinterOptions.setEnabled(procesingInfo.getReportPrinterOptions().isEnabled());
					xmlSchedulingInfo.setReportPrinterOptions(xmlReportPrinterOptions);

				}

				if (procesingInfo.getReportPrinterOptions() != null) {
					PageLayoutSettings xmlPageLayoutSettings = new PageLayoutSettings();
					xmlPageLayoutSettings.setPageLayout(procesingInfo.getReportPrinterOptions().getPageLayout());
					switch (procesingInfo.getReportPrinterOptions().getPageLayout()) {
					case IReportPrinterOptions.CeReportLayout.CUSTOM_SETTING:
						xmlPageLayoutSettings.setPageLayoutDescription("Custom Settings");
						break;
					case IReportPrinterOptions.CeReportLayout.DEFAULT_PRINTER_SETTNG:
						xmlPageLayoutSettings.setPageLayoutDescription("Default Printer Settings");
						break;

					case IReportPrinterOptions.CeReportLayout.NO_PRINTER_SETTING:
						xmlPageLayoutSettings.setPageLayoutDescription("No Printer Settings");
						break;

					case IReportPrinterOptions.CeReportLayout.REPORT_FILE_SETTING:
						xmlPageLayoutSettings.setPageLayoutDescription("Report File Settings");
						break;

					case IReportPrinterOptions.CeReportLayout.USE_SPECIFIED_PRINTER_SETTING:
						xmlPageLayoutSettings.setPageLayoutDescription("Specified Printer Settings");
						break;

					default:
						xmlPageLayoutSettings.setPageLayoutDescription("N/A");
						break;

					}
					xmlSchedulingInfo.setPageLayoutSettings(xmlPageLayoutSettings);
				}

			}

			return xmlSchedulingInfo;

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return null;
	}

	/**
	 * Process Schedule Type
	 * 
	 * @param schedulingInfo
	 * @return
	 */
	public ScheduleFreqType getScheduleType(ISchedulingInfo schedulingInfo, IInfoStore infoStore) {
		try {

			ScheduleFreqType scheduleFreqType = new ScheduleFreqType();
			if (schedulingInfo != null) {
				if (schedulingInfo.properties().get("SI_SCHEDULE_TYPE") != null) {
					Log.debug("Schedule Type:" + schedulingInfo.getType());
					// Log.debug("Schedule Type:" +
					// schedulingInfo.properties().get("SI_SCHEDULE_TYPE"));
					// switch
					// (Integer.valueOf(schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString()))
					// {
					switch (schedulingInfo.getType()) {
					case CeScheduleType.HOURLY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_HOURLY);
						Hourly hourly = new Hourly();
						hourly.setHours(schedulingInfo.getIntervalHours());
						hourly.setMinutes(schedulingInfo.getIntervalMinutes());
						scheduleFreqType.setHourly(hourly);
						break;
					case CeScheduleType.DAILY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_DAILY);
						scheduleFreqType.setInterVal(schedulingInfo.getIntervalDays());
						break;
					case CeScheduleType.MONTHLY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_MONTHLY);
						scheduleFreqType.setInterVal(schedulingInfo.getIntervalMonths());
						break;
					case CeScheduleType.NTH_DAY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_NTH_DAY_OF_MONTH);
						scheduleFreqType.setInterVal(schedulingInfo.getIntervalNthDay());
						break;
					case CeScheduleType.ONCE:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_ONCE);
						break;
					case CeScheduleType.LAST_DAY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_LAST_DAY);
						break;
					// scheduleFreqType.setType("CalendarTemplate");
					// break;
					case CeScheduleType.WEEKLY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_WEEKLY);
						break;
					case CeScheduleType.FIRST_MONDAY:
						scheduleFreqType.setType(CommonConstants.STR_FREQ_FIRST_MONDAY);
						break;
					case CeScheduleType.CALENDAR:
						Log.debug("Freq Calendar");
						scheduleFreqType.setType(CommonConstants.STR_FREQ_CALENDAR);
						Calendar xmlCalendar = new Calendar();
						scheduleFreqType.setCalendar(xmlCalendar);

						ICalendarRunDays calendarRunDays = schedulingInfo.getCalendarRunDays();
						Log.debug("Calendar Run Days.Size" + calendarRunDays.size());
						Log.debug("Nth Day:" + schedulingInfo.getIntervalNthDay());
						List<CalendarDay> calendarDays = new ArrayList<CalendarDay>();
						xmlCalendar.setCalendarDays(calendarDays);

						for (int i = 0; i < calendarRunDays.size(); i++) {

							ICalendarDay calDay = (ICalendarDay) schedulingInfo.getCalendarRunDays().get(i);
							CalendarDay xmlCalendarDay = new CalendarDay();
							calendarDays.add(xmlCalendarDay);
							if (calDay.getStartMonth() != -1)
								xmlCalendarDay.setStartMonth(calDay.getStartMonth());
							if (calDay.getStartDay() != -1)
								xmlCalendarDay.setStartDay(calDay.getStartDay());
							if (calDay.getStartYear() != -1)
								xmlCalendarDay.setStartYear(calDay.getStartYear());

							if (calDay.getEndMonth() != -1)
								xmlCalendarDay.setEndMonth(calDay.getEndMonth());

							if (calDay.getEndDay() != -1)
								xmlCalendarDay.setEndDay(calDay.getEndDay());
							if (calDay.getEndYear() != -1)
								xmlCalendarDay.setEndYear(calDay.getEndYear());

							if (calDay.getDayOfWeek() != -1)
								xmlCalendarDay.setDayOfWeek(calDay.getDayOfWeek());
							if (calDay.getWeekNumber() != -1)
								xmlCalendarDay.setWeekNumber(calDay.getWeekNumber());
						}

						break;
					case CeScheduleType.CALENDAR_TEMPLATE:
						Log.debug("Fred:Calendar Template");
						scheduleFreqType.setType(CommonConstants.STR_FREQ_CALENDAR_TEMPLATE);

						String cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID=" + schedulingInfo.getCalendarTemplate();
						Log.debug("Run CMS Query:" + cmsQuery);
						ICalendar calendarObject = (ICalendar) infoStore.query(cmsQuery).get(0);

						Log.debug("Calendar Name:" + calendarObject.getTitle());
						Calendar calendar = new Calendar();
						calendar.setName(calendarObject.getTitle());
						scheduleFreqType.setCalendar(calendar);
						calendarDays = new ArrayList<CalendarDay>();
						calendar.setCalendarDays(calendarDays);
						Log.debug("Calendar Id:" + schedulingInfo.getCalendarTemplate());
						Log.debug("Calendar Run Days Count:" + calendarObject.getDays().size());
						for (int i = 0; i < calendarObject.getDays().size(); i++) {
							Log.debug("Run Day: " + i);
							IBusinessCalendarDays businessCalendarDays = calendarObject.getDays();
							IBusinessCalendarDay businessCalendarDay = (IBusinessCalendarDay) businessCalendarDays.get(i);
							Log.debug("Start Day:" + businessCalendarDay.getStartDay());
						}
						break;
					default:
						scheduleFreqType.setType("Other:" + schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString());
					}
				}
				return scheduleFreqType;
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return null;
	}

	/**
	 * Creates XML Settings from InfoObject
	 * 
	 * @param infoStore
	 * @param infoObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InfoObject getXmlInfoObject(IInfoStore infoStore, IInfoObject infoObject) {

		try {
			InfoObject xmlInfoObject = new InfoObject();
			CommonThreadTools commonThreadTools = new CommonThreadTools();

			xmlInfoObject.setSI_ID(infoObject.getID());
			xmlInfoObject.setApos("");
			xmlInfoObject.setSI_LOCATION(Type1WorkerThread.getObjectPath(infoStore, infoObject, new StringBuffer("")).toString());
			xmlInfoObject.setSI_PARENTID(infoObject.getParentID());
			SchedulingInfo xmlSchedulingInfo = new SchedulingInfo();
			xmlSchedulingInfo.setBeginDate(Type2WorkerThread.formatter.format(infoObject.getSchedulingInfo().getBeginDate()));
			
			
			
			
			
			xmlSchedulingInfo.setEndDate(Type2WorkerThread.formatter.format(infoObject.getSchedulingInfo().getEndDate()));
			xmlSchedulingInfo.setInstanceName(infoObject.getTitle());
			xmlSchedulingInfo.setIntervalMonths(infoObject.getSchedulingInfo().getIntervalMonths());
			xmlSchedulingInfo.setIntervalDays(infoObject.getSchedulingInfo().getIntervalDays());
			xmlSchedulingInfo.setIntervalHours(infoObject.getSchedulingInfo().getIntervalHours());
			xmlSchedulingInfo.setIntervalMinutes(infoObject.getSchedulingInfo().getIntervalMinutes());
			xmlSchedulingInfo.setIntervalNthDay(infoObject.getSchedulingInfo().getIntervalNthDay());
			xmlSchedulingInfo.setRetriesAllowed(infoObject.getSchedulingInfo().getRetriesAllowed());
			xmlSchedulingInfo.setRetryInterval(infoObject.getSchedulingInfo().getRetryInterval());
			xmlSchedulingInfo.setRightNow(infoObject.getSchedulingInfo().isRightNow());
			xmlSchedulingInfo.setScheduleOnBehalfOf(infoObject.getSchedulingInfo().getScheduleOnBehalfOf());
			xmlSchedulingInfo.setType(infoObject.getSchedulingInfo().getType());
			if (infoObject.getSchedulingInfo().getDestinations() != null) {
				for (int i = 0; i < infoObject.getSchedulingInfo().getDestinations().size(); i++) {
					IDestination destination = (IDestination) infoObject.getSchedulingInfo().getDestinations().get(i);
					Log.debug("Destination:" + destination.getName());
					if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_MANAGED)) {
						DestinationManaged xmlDestinationManaged = new DestinationManaged();
						Log.debug("Destination Managed");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_MANAGED).get(0);
						destination.copyToPlugin(destinationPlugin);
						IManagedOptions scheduleOptions = (IManagedOptions) destinationPlugin.getScheduleOptions();
						xmlDestinationManaged.setSendOption(scheduleOptions.getSendOption());
						xmlDestinationManaged.setCount(scheduleOptions.getDestinations().size());
						xmlDestinationManaged.setName(infoObject.getTitle());
						Log.debug("Destination Size:" + scheduleOptions.getDestinations().size());
						List<Integer> ids = new ArrayList<Integer>();
						for (Iterator<Integer> id = scheduleOptions.getDestinations().iterator(); id.hasNext();) {
							ids.add(id.next());

						}
						xmlDestinationManaged.setUserIdInboxes(ids);
						xmlSchedulingInfo.setDestinationManaged(xmlDestinationManaged);
					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.DiskUnmanaged")) {
						DestinationDisk xmlDestinationDisk = new DestinationDisk();
						Log.debug("Destination Disk UNManaged");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_DISK_UNMANAGED).get(0);
						destination.copyToPlugin(destinationPlugin);
						IDiskUnmanagedOptions scheduleOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();
						if (scheduleOptions.getDestinationFiles().size() > 0)
							xmlDestinationDisk.setOutputFileName(scheduleOptions.getDestinationFiles().get(0).toString());
						xmlDestinationDisk.setUserName(scheduleOptions.getUserName());
						xmlSchedulingInfo.setDestinationDiskUnManaged(xmlDestinationDisk);

					} else if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_SMTP)) {

						Log.debug("Destination Smtp");
						DestinationSmtp xmlDestinationSmtp = commonThreadTools.getDestinationSmtp(destination, infoStore);
						// IDestinationPlugin destinationPlugin =
						// (IDestinationPlugin)
						// infoStore.query(Type1WorkerThread.CMS_QUERY_SMTP).get(0);
						// destination.copyToPlugin(destinationPlugin);
						// ISMTPOptions smtpOptions = (ISMTPOptions)
						// destinationPlugin.getScheduleOptions();
						// if (smtpOptions.getAttachments() != null) {
						// String result = "";
						// for (int j = 0; j <
						// smtpOptions.getAttachments().size(); j++) {
						// IAttachment attachment = (IAttachment)
						// smtpOptions.getAttachments().get(j);
						// result += attachment.getEmbedName() + ",";
						// }
						// if (result.endsWith(","))
						// result = result.substring(0, result.length() - 1);
						// xmlDestinationSmtp.setAttachmentName(result);
						// }
						// xmlDestinationSmtp.setCc(commonThreadTools.stringFromArray(smtpOptions.getCCAddresses()));
						// if (smtpOptions.getSenderAddress() != null)
						// xmlDestinationSmtp.setFrom(smtpOptions.getSenderAddress());
						//
						// if (smtpOptions.getMessage() != null)
						// xmlDestinationSmtp.setMessage(smtpOptions.getMessage());
						//
						// if (smtpOptions.getSubject() != null)
						// xmlDestinationSmtp.setSubject(smtpOptions.getSubject());
						//
						// xmlDestinationSmtp.setTo(commonThreadTools.stringFromArray(smtpOptions.getToAddresses()));
						xmlSchedulingInfo.setDestinationSmtp(xmlDestinationSmtp);

					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Ftp")) {

						DestinationFtp xmlDestinationFtp = new DestinationFtp();

						Log.debug("Destination Ftp");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(Type1WorkerThread.CMS_QUERY_FTP).get(0);
						destination.copyToPlugin(destinationPlugin);
						IFTPOptions ftpOptions = (IFTPOptions) destinationPlugin.getScheduleOptions();
						if (ftpOptions.getDestinationFiles() != null)
							if (ftpOptions.getDestinationFiles().size() > 0)
								xmlDestinationFtp.setPath((ftpOptions.getDestinationFiles().get(0).toString()));

						xmlDestinationFtp.setPort(ftpOptions.getPort());
						xmlDestinationFtp.setServerName(ftpOptions.getServerName());
						xmlDestinationFtp.setUserName(ftpOptions.getUserName());
						xmlSchedulingInfo.setDestinationFtp(xmlDestinationFtp);

					}

				}

			}

			String instanceProgId = infoObject.getProgID();

			if (infoObject.properties().getProperty("SI_PROGID_MACHINE") != null) {
				Log.debug("Machine Based Prog ID(Real Prog ID:" + infoObject.properties().getProperty("SI_PROGID_MACHINE").toString());
				instanceProgId = infoObject.properties().getProperty("SI_PROGID_MACHINE").toString();
			}
			if (instanceProgId.equalsIgnoreCase("CrystalEnterprise.Report")) {
				Log.debug("Processing CR Based Instance");
				PluginInterface xmlPluginInterface = commonThreadTools.getCRBasedPluginInterface(infoObject);
				xmlInfoObject.setPluginInterface(xmlPluginInterface);

			} else if (instanceProgId.equalsIgnoreCase("CrystalEnterprise.Webi")) {
				Log.debug("Processing Webi Based Instance");
				PluginInterfaceWebi xmlInterfaceWebi = commonThreadTools.getPluginInterfaceWebi(infoObject);
				xmlInfoObject.setPluginInterfaceWebi(xmlInterfaceWebi);

			} else if (instanceProgId.equalsIgnoreCase("CrystalEnterprise.FullClient")) {
				Log.debug("Processing Deski Based Instance");
				PluginInterfaceWebi xmlInterfaceWebi = commonThreadTools.getPluginInterfaceDeski(infoObject);
				xmlInfoObject.setPluginInterfaceWebi(xmlInterfaceWebi);
			}

			xmlInfoObject.setSchedulingInfo(xmlSchedulingInfo);

			return xmlInfoObject;
		} catch (Exception ee) {

		}
		return null;
	}

	/**
	 * Gets Object id using provided path
	 * 
	 * @param path
	 * @param infoStore
	 * @return
	 */
	public int getObjectIdFromPath(String path, IInfoStore infoStore) throws Exception {
		Log.debug("Processing Path:" + path);
		int objectId = 0;
		String[] parts = StringUtils.split(path, '/');
		int parentId = 0;
		for (int i = 0; i < parts.length; i++) {
			String cmsQuery = " SELECT SI_ID, SI_NAME,SI_PARENTID FROM CI_INFOOBJECTS WHERE ";
			String part = parts[i];
			cmsQuery += " SI_NAME='" + part + "' AND SI_PARENTID=" + parentId;
			Log.debug("Running CMS Query:" + cmsQuery);
			IInfoObject infoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
			objectId = infoObject.getID();
			parentId = objectId;
			Log.debug("ParentID:" + parentId);
		}
		return objectId;
	}
}
