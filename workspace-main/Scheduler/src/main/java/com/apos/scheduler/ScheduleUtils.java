/**
 * 
 */
package com.apos.scheduler;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.apos.infoobject.properties.CommonConstants;
import com.apos.infoobject.properties.Webi31Processor;
import com.apos.infoobject.xml.Attachment;
import com.apos.infoobject.xml.DestinationDisk;
import com.apos.infoobject.xml.DestinationFtp;
import com.apos.infoobject.xml.DestinationManaged;
import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.Inbox;
import com.apos.infoobject.xml.ParameterValue;
import com.apos.infoobject.xml.ProgramObject;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.ReportParameter;
import com.apos.michael.WebiRefresh;
import com.apos.xml.generic.CustomProperty;
import com.apos.xml.generic.ScheduleSettings;
import com.apos.xml.generic.freq.CalendarDay;
import com.businessobjects.sdk.plugin.desktop.webi.IWebi;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiFormatOptions;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiFormatOptions.CeWebiFormat;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiProcessingInfo;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiPrompt;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;
import com.crystaldecisions.sdk.occa.infostore.ICalendarDay;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.INotifications;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.plugin.desktop.calendar.ICalendar;
import com.crystaldecisions.sdk.plugin.desktop.common.CeFormatNames;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportFormatOptions;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportLogon;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportFormatOptions.CeReportFormat;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportProcessingInfo;
import com.crystaldecisions.sdk.plugin.desktop.objectpackage.IObjectPackage;
import com.crystaldecisions.sdk.plugin.desktop.program.IBinaryProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IJavaProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IProgram;
import com.crystaldecisions.sdk.plugin.desktop.program.IScriptProgram;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.plugin.destination.ftp.IFTPOptions;
import com.crystaldecisions.sdk.plugin.destination.managed.IManagedOptions;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;
import com.crystaldecisions.sdk.properties.IProperty;
import com.crystaldecisions.sdk.properties.ISDKList;

/**
 * @author Yuri Goron
 * 
 */
public class ScheduleUtils {

	public static final String STR_DESTINATION_FAVORITES = "Favorites";
	private static final Logger Log = Logger.getLogger(ScheduleUtils.class);

	/**
	 * Schedule Object (1st in info objects) using settings in the
	 * sheduleSettinsg object
	 * 
	 * @param infoStore
	 * @param infoObjects
	 * @param xmlSettings
	 * @param jaxb2Marshaller
	 * @return
	 * @throws Exception
	 */
	public int scheduleObject(IInfoStore infoStore, IInfoObjects infoObjects, ScheduleSettings xmlScheduleSettings, List<String> accepts, Jaxb2Marshaller jaxb2Marshaller) throws Exception {

		StringWriter result = new StringWriter();
		Log.debug("UnMarshalling:");
		jaxb2Marshaller.marshal(xmlScheduleSettings, new StreamResult(result));
		Log.debug(result.toString());
		IInfoObject infoObject = null;
		if (infoObjects.size() > 1) {
			Log.debug("Process Object Package");
			infoObject = (IInfoObject) infoObjects.get(1);
		} else {
			Log.debug("Process Single Object");
			infoObject = (IInfoObject) infoObjects.get(0);
		}

		Log.debug("Schedule Object Kind:" + infoObject.getKind());

		boolean isKindFound = false;

		if (accepts != null) {
			for (String acceptedKind : accepts) {
				Log.debug("Accepted Kind:" + acceptedKind);
				if (acceptedKind.equalsIgnoreCase(infoObject.getKind())) {
					Log.debug("Accepted");
					isKindFound = true;
					break;
				}
			}

			if (!isKindFound) {
				throw new Exception(" Not Implemented:" + infoObject.getKind());
			}
		}

		infoObject = setSchedulingInfo(infoObject, xmlScheduleSettings, infoStore);

		if (infoObject == null)
			throw new Exception("Unknown Schedule Type:" + xmlScheduleSettings.getScheduleFreqType().getType());

		infoObject = setDestinationInfo(infoObject, xmlScheduleSettings, infoStore);
		infoObject = setCrystalReportsSelectionFormulas(infoObject, xmlScheduleSettings);
		infoObject = setFormat(infoObject, xmlScheduleSettings);
		infoObject = setOnBehalf(infoObject, xmlScheduleSettings, infoStore);
		infoObject = setEvents(infoObject, xmlScheduleSettings, infoStore);
		infoObject = setNotifications(infoObject, xmlScheduleSettings, infoStore);
		infoObject = setCustomProperties(infoObject, xmlScheduleSettings);
		infoObject = setProgramObject(infoObject, xmlScheduleSettings);

		Log.debug("Printing Info Object Collection");

		for (int i = 0; i < infoObjects.size(); i++) {
			IInfoObject tempObject = (IInfoObject) infoObjects.get(i);
			Log.debug("***** Object Title:" + tempObject.getTitle() + " Cuid:" + tempObject.getCUID());

		}

		if (infoObjects.size() == 1) {
			Log.debug("Regular Object");
			Log.debug("Setting Prompts");
			infoObject = setPrompts(infoObject, xmlScheduleSettings);
			Log.debug("Setting Logons");
			setCrystalReportsLogons(infoObject, xmlScheduleSettings);
		} else {

			IObjectPackage objectPackage = (IObjectPackage) infoObjects.get(0);
			Log.debug("Process Objects Package. Size:" + objectPackage.getPackageComponents().size());

			for (int i = 0; i < objectPackage.getPackageComponents().size(); i++) {
				String cuid = (String) objectPackage.getPackageComponents().get(i);

				Log.debug("Processing Objects in Package " + i + " CUID:" + cuid);

				IInfoObject objectInPackage = null;
				for (int j = 1; j < infoObjects.size(); j++) {
					IInfoObject tempObject = (IInfoObject) infoObjects.get(j);
					Log.debug("Processing InfoObject :" + tempObject.getTitle() + " Cuid" + tempObject.getCUID());

					if (((IInfoObject) (infoObjects.get(j))).getCUID().equals(cuid)) {
						Log.debug("Object in package Found:" + cuid + " Title:" + ((IInfoObject) infoObjects.get(j)).getTitle());
						objectInPackage = (IInfoObject) infoObjects.get(j);
						break;
					}
				}
				setPrompts(objectInPackage, xmlScheduleSettings);
				setCrystalReportsLogons(objectInPackage, xmlScheduleSettings);

			}
		}
		infoObject = setReportPrinterOptions(infoObject, xmlScheduleSettings, infoStore);

		if (StringUtils.isNotBlank(xmlScheduleSettings.getInstanceName())) {
			Log.debug("Set Instance Name To:" + xmlScheduleSettings.getInstanceName());
			infoObject.setTitle(xmlScheduleSettings.getInstanceName());
		}

		infoStore.schedule(infoObjects);
		infoObject = (IInfoObject) infoObjects.get(0);
		int instanceId = Integer.parseInt(infoObject.properties().getProperty("SI_NEW_JOB_ID").toString());
		Log.info("Report Has Been Scheduled. New Instance Id:" + instanceId);
		return instanceId;

	}

	/**
	 * Sets Crystal Report Group and Record Selection Formulas
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 * @throws SDKException
	 */
	private IInfoObject setCrystalReportsSelectionFormulas(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings) throws SDKException {

		if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_CRYSTAL_REPORT)) {
			IReport crystalReport = (IReport) infoObject;
			if (xmlScheduleSettings.isIsUseGroupFormula()) {
				crystalReport.setGroupFormula(StringUtils.isNotBlank(xmlScheduleSettings.getGroupFormula()) ? xmlScheduleSettings.getGroupFormula() : "");
				Log.debug("Crystal Report Group Formula Set to:" + crystalReport.getGroupFormula());
			}
			if (xmlScheduleSettings.isIsUseRecordFormula()) {
				crystalReport.setRecordFormula(StringUtils.isNotBlank(xmlScheduleSettings.getRecordFormula()) ? xmlScheduleSettings.getRecordFormula() : "");
				Log.debug("Crystal Report Record Selection Formula Set to:" + crystalReport.getRecordFormula());

			}
		}
		return infoObject;
	}

	/**
	 * 
	 * Sets Logins for Crystal Reports
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setCrystalReportsLogons(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings) throws Exception {
		if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_CRYSTAL_REPORT)) {
			if (xmlScheduleSettings.getReportLogons() != null) {
				Log.debug("Number of reportLogons:" + xmlScheduleSettings.getReportLogons().size());
				IReport crystalReport = (IReport) infoObject;
				for (ReportLogon xmlReportLogon : xmlScheduleSettings.getReportLogons()) {
					Log.debug("Processing Report Logon:" + xmlReportLogon.getServerName() + " Is Original Data Source:" + xmlReportLogon.isUseOriginalDataSource());

					ISDKList dbLogons = crystalReport.getReportLogons();
					for (int i = 0; i < dbLogons.size(); i++) {
						IReportLogon dbLogon = (IReportLogon) dbLogons.get(i);
						dbLogon.setOriginalDataSource(xmlReportLogon.isUseOriginalDataSource());

						if (dbLogon.getServerName().equalsIgnoreCase(xmlReportLogon.getServerName())) {
							Log.debug("Db Logon Match Found:" + dbLogon.getServerName());

							if (xmlReportLogon.isUseOriginalDataSource()) {
								dbLogon.setPassword(xmlReportLogon.getPassword());
								if (StringUtils.isNotBlank(xmlReportLogon.getUserName())) {
									dbLogon.setUserName(xmlReportLogon.getUserName());
									Log.debug("Original User Name Set to:" + dbLogon.getUserName());
								}

							} else {
								if (StringUtils.isNotBlank(xmlReportLogon.getCustomDatabaseDLLName())) {
									dbLogon.setCustomDatabaseDLLName(xmlReportLogon.getCustomDatabaseDLLName());
									Log.debug("Custom Database DLL Name Set To:" + dbLogon.getCustomDatabaseDLLName());
								}

								if (StringUtils.isNotBlank(xmlReportLogon.getCustomDatabaseName())) {
									dbLogon.setCustomDatabaseName(xmlReportLogon.getCustomDatabaseName());
									Log.debug("Custom Database Name Name Set To:" + dbLogon.getCustomDatabaseName());
								}
								dbLogon.setCustomPassword(xmlReportLogon.getCustomPassword());

								if (StringUtils.isNotBlank(xmlReportLogon.getCustomServerName())) {
									dbLogon.setCustomServerName(xmlReportLogon.getCustomServerName());
									Log.debug("Custom Server Name Name Set To:" + dbLogon.getCustomServerName());
								}

								if (xmlReportLogon.getCustomServerType() > 0) {
									dbLogon.setCustomServerType(xmlReportLogon.getCustomServerType());
									Log.debug("Custom Server Type:" + dbLogon.getCustomServerType());
								}

								if (StringUtils.isNotBlank(xmlReportLogon.getCustomUserName())) {
									dbLogon.setCustomUserName(xmlReportLogon.getCustomUserName());
									Log.debug("Custom User Name Name Set To:" + dbLogon.getCustomUserName());
								}

							}
						}
					}

				}
			}
		}

		return infoObject;
	}

	/**
	 * Sets Program Object
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 */
	private IInfoObject setProgramObject(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings) throws Exception {
		if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_PROGRAM) && xmlScheduleSettings.getProgramObject() != null) {
			ProgramObject xmlProgramObject = xmlScheduleSettings.getProgramObject();
			IProgram program = (IProgram) infoObject;
			switch (program.getProgramType()) {
			case IProgram.CeProgramType.BINARY:
				IBinaryProgram binaryProgram = (IBinaryProgram) program.getProgramInterface();
				if (StringUtils.isNotBlank(xmlProgramObject.getArgs())) {
					binaryProgram.setArgs(xmlProgramObject.getArgs());
				}
				Log.debug("Binary Program Args:" + xmlProgramObject.getArgs());
				if (StringUtils.isNotBlank(xmlProgramObject.getUserName())) {
					binaryProgram.setUserName(xmlProgramObject.getUserName());
					binaryProgram.setPassword(xmlProgramObject.getPassword());
				}

				break;

			case IProgram.CeProgramType.JAVA:
				IJavaProgram javaProgram = (IJavaProgram) program.getProgramInterface();
				if (StringUtils.isNotBlank(xmlProgramObject.getArgs())) {
					javaProgram.setArgs(xmlProgramObject.getArgs());
				}
				Log.debug("Binary Program Args:" + xmlProgramObject.getArgs());
				if (StringUtils.isNotBlank(xmlProgramObject.getUserName())) {
					javaProgram.setUserName(xmlProgramObject.getUserName());
					javaProgram.setPassword(xmlProgramObject.getPassword());
				}
				break;

			case IProgram.CeProgramType.SCRIPT:
				IScriptProgram scriptProgram = (IScriptProgram) program.getProgramInterface();

				if (StringUtils.isNotBlank(xmlProgramObject.getArgs())) {
					scriptProgram.setArgs(xmlProgramObject.getArgs());
				}
				Log.debug("Binary Program Args:" + xmlProgramObject.getArgs());
				if (StringUtils.isNotBlank(xmlProgramObject.getUserName())) {
					scriptProgram.setUserName(xmlProgramObject.getUserName());
					scriptProgram.setPassword(xmlProgramObject.getPassword());
				}
				break;

			}

		}
		return infoObject;
	}

	/**
	 * Sets Schedule on Behalf
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setOnBehalf(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {

		if (StringUtils.isNotBlank(xmlScheduleSettings.getScheduleOnBehalfOf())) {
			try {
				String cmsQuery = "SELECT SI_ID,SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + xmlScheduleSettings.getScheduleOnBehalfOf() + "'" + " AND SI_PROGID='"
						+ CommonConstants.STR_DESTINATION_USER + "'";
				Log.debug("Running CMS Query:" + cmsQuery);
				IInfoObject userObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
				infoObject.getSchedulingInfo().setScheduleOnBehalfOf(userObject.getID());
				Log.debug("Schedule on Behalf of user:" + xmlScheduleSettings.getScheduleOnBehalfOf());
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				throw new Exception(ErrorCodes.STR_ERROR_RESOLVING_USERS_AND_GROUPS_NAMES_FAILED_TO_FIND + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getScheduleOnBehalfOf());
			}
		}

		return infoObject;
	}

	/**
	 * Sets Instance Format
	 * 
	 * @param infoObject
	 * @param scheduleSettings
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setFormat(IInfoObject infoObject, ScheduleSettings scheduleSettings) throws Exception {

		if (scheduleSettings.getFormatOptions() != null) {
			if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_WEBI)) {
				IWebi webiProcessingInfo = (IWebi) infoObject;
				IWebiFormatOptions webiFormatOptions = webiProcessingInfo.getWebiFormatOptions();
				if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.PDF)) {
					webiFormatOptions.setFormat(IWebiFormatOptions.CeWebiFormat.PDF);
					Log.debug("Schedule in PDF Format");

				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase("XLS")) {
					webiFormatOptions.setFormat(IWebiFormatOptions.CeWebiFormat.EXCEL);
					Log.debug("Schedule in Excel Format");

				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase("CSV")) {
					webiFormatOptions.setFormat(IWebiFormatOptions.CeWebiFormat.CSV);
					webiFormatOptions.setFormatOptions(CeWebiFormat.CSV, "", "", "UTF-8", true);
					Log.debug("Schedule in Excel Format");

				}

			} else if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_CRYSTAL_REPORT)) {
				IReport crysatlReport = (IReport) infoObject;
				IReportFormatOptions reportFormatOptions = crysatlReport.getReportFormatOptions();
				if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.EXCEL)) {
					reportFormatOptions.setFormat(IReportFormatOptions.CeReportFormat.EXCEL);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.HTML)) {
					reportFormatOptions.setFormat(CeReportFormat.MHTML);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.PDF)) {
					reportFormatOptions.setFormat(CeReportFormat.PDF);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.RTF)) {
					reportFormatOptions.setFormat(CeReportFormat.RTF);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.WORD)) {
					reportFormatOptions.setFormat(CeReportFormat.WORD);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.XML)) {
					reportFormatOptions.setFormat(CeReportFormat.XML);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.RTF)) {
					reportFormatOptions.setFormat(CeReportFormat.RTF);
				} else if (scheduleSettings.getFormatOptions().getFormat().equalsIgnoreCase(CeFormatNames.TEXT)) {
					reportFormatOptions.setFormat(CeReportFormat.TEXT_PLAIN);
				}
			}
		}
		return infoObject;
	}

	/**
	 * Sets Audit and Email Notifications
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setNotifications(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {
		if (xmlScheduleSettings.getAuditNotificationOption() != null) {
			if (xmlScheduleSettings.getAuditNotificationOption().equalsIgnoreCase(CommonConstants.STR_AUDIT_RESULT_BOTH)) {
				infoObject.getSchedulingInfo().getNotifications().setAuditOption(INotifications.CeAuditOnResult.BOTH);
				Log.debug("Adudit Notification is Set to Both");
			} else if (xmlScheduleSettings.getAuditNotificationOption().equalsIgnoreCase(CommonConstants.STR_AUDIT_RESULT_SUCCESS)) {
				infoObject.getSchedulingInfo().getNotifications().setAuditOption(INotifications.CeAuditOnResult.SUCCESS);
				Log.debug("Audit Notification is Set to Success");
			} else if (xmlScheduleSettings.getAuditNotificationOption().equalsIgnoreCase(CommonConstants.STR_AUDIT_RESULT_FAILURE)) {
				infoObject.getSchedulingInfo().getNotifications().setAuditOption(INotifications.CeAuditOnResult.FAILURE);
				Log.debug("Audit Notification is Set to Failure");
			}

		}

		if (xmlScheduleSettings.getEmailNotificationSuccess() != null) {
			Log.debug("Setting Email Notification on Success");
			DestinationSmtp xmlDestinationSmtp = xmlScheduleSettings.getEmailNotificationSuccess();
			IDestinationPlugin destinationPlugin = getSmtpDestinationPlugin(infoObject, xmlDestinationSmtp, infoStore);
			IDestination destination = infoObject.getSchedulingInfo().getNotifications().getDestinationsOnSuccess().add(CommonConstants.STR_DESTINATION_SMTP);
			destination.setFromPlugin(destinationPlugin);
			Log.debug("Email notification on Success is Set");

		}

		if (xmlScheduleSettings.getEmailNotificationFailure() != null) {
			Log.debug("Setting Email Notification on Failure");
			DestinationSmtp xmlDestinationSmtp = xmlScheduleSettings.getEmailNotificationFailure();
			IDestinationPlugin destinationPlugin = getSmtpDestinationPlugin(infoObject, xmlDestinationSmtp, infoStore);
			IDestination destination = infoObject.getSchedulingInfo().getNotifications().getDestinationsOnFailure().add(CommonConstants.STR_DESTINATION_SMTP);
			destination.setFromPlugin(destinationPlugin);
			Log.debug("Email notification on Failure is Set");
		}

		return infoObject;
	}

	/**
	 * Sets Printer Options
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setReportPrinterOptions(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {

		if (infoObject.getProgID().equalsIgnoreCase(CommonConstants.STR_PROGID_CRYSTAL_ENTERPRISE_REPORT)) {
			Log.debug("Processing CR Based Report");

			IReportProcessingInfo procesingInfo = (IReportProcessingInfo) infoObject;

			if (xmlScheduleSettings.getReportPrinterOptions() != null) {
				procesingInfo.getReportPrinterOptions().setEnabled(xmlScheduleSettings.getReportPrinterOptions().isEnabled());
				Log.debug("Set Printer Options :" + procesingInfo.getReportPrinterOptions().isEnabled());

				if (xmlScheduleSettings.getPageLayoutSettings() != null) {
					procesingInfo.getReportPrinterOptions().setPageLayout(xmlScheduleSettings.getPageLayoutSettings().getPageLayout());
					Log.debug("Set Page Layout:" + procesingInfo.getReportPrinterOptions().getPageLayout());
				}
			}

		}

		return infoObject;

	}

	/**
	 * Add Events
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setEvents(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {

		if (xmlScheduleSettings.getDependants() != null) {
			Log.debug("Processing Dependants. Size:" + xmlScheduleSettings.getDependants().size());
			for (String eventName : xmlScheduleSettings.getDependants()) {
				Log.debug("Processing Event Name:" + eventName);
				String cmsString = "SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_PROGID='" + CommonConstants.STR_ENTERPRISE_EVENT + "' AND SI_NAME='" + eventName + "'";
				try {
					IInfoObject event = (IInfoObject) infoStore.query(cmsString).get(0);
					Log.debug("Event Found:" + event.getTitle());
					infoObject.getSchedulingInfo().getDependants().add(event.getID());

					// infoObject.getSchedulingInfo().getNotifications().
				} catch (Exception ee) {
					Log.error("CMS Query:" + cmsString);
					throw new Exception(ErrorCodes.STR_ERROR_SCHEDULING_REPORT_CANNOT_FIND_EVENT_INFO + CommonConstants.STR_ERROR_INFO_SEPARATOR + eventName);
				}

			}
		}

		if (xmlScheduleSettings.getDependencies() != null) {

			Log.debug("Processing Dependencies. Size:" + xmlScheduleSettings.getDependencies().size());
			for (String eventName : xmlScheduleSettings.getDependencies()) {
				Log.debug("Processing Event Name:" + eventName);
				String cmsString = "SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_PROGID='" + CommonConstants.STR_ENTERPRISE_EVENT + "' AND SI_NAME='" + eventName + "'";
				try {
					IInfoObject event = (IInfoObject) infoStore.query(cmsString).get(0);
					Log.debug("Event Found:" + event.getTitle());
					infoObject.getSchedulingInfo().getDependencies().add(event.getID());

					// infoObject.getSchedulingInfo().getNotifications().
				} catch (Exception ee) {
					Log.error("CMS Query:" + cmsString);
					Log.error(ee.getLocalizedMessage(), ee);
					throw new Exception(ErrorCodes.STR_ERROR_SCHEDULING_REPORT_CANNOT_FIND_EVENT_INFO + CommonConstants.STR_ERROR_INFO_SEPARATOR + eventName);
				}

			}

		}
		return infoObject;

	}

	/**
	 * Set Parameters
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 * @throws Exception
	 * @throws SDKException
	 */
	@SuppressWarnings("unchecked")
	private IInfoObject setPrompts(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings) throws SDKException, Exception {
		if (infoObject.getKind().equalsIgnoreCase(CommonConstants.STR_KIND_CRYSTAL_REPORT)) {
			Utils utils = new Utils();
			if (xmlScheduleSettings.getParameters() != null) {
				List<ReportParameter> reportParameters = xmlScheduleSettings.getParameters();
				for (ReportParameter reportParameter : reportParameters) {
					Log.debug("Setting Crystal Report Parameter :" + reportParameter.getName());
					utils.setCrystalReportParameter(reportParameter, infoObject);
				}

			}

		} else if (infoObject.getKind().equals(CommonConstants.STR_KIND_WEBI)) {
			IWebiProcessingInfo webiProcessingInfo;
			WebiRefresh webiRefresh = new WebiRefresh();
			if (xmlScheduleSettings.getParameters() != null) {
				if (xmlScheduleSettings.getParameters().size() > 0) {
					// WebiRefresh.updatePromptsForScheduling(infoObject);
					webiRefresh.updatePromptsForScheduling(infoObject);
					webiProcessingInfo = (IWebiProcessingInfo) infoObject;
					Log.debug("Webi Prompts Refreshed.Number of Prompts:" + webiProcessingInfo.getPrompts().size());
					boolean isSkipPrompt = false;
					for (int i = 0; i < webiProcessingInfo.getPrompts().size(); i++) {
						IWebiPrompt webiPrompt = (IWebiPrompt) webiProcessingInfo.getPrompts().get(i);
						Log.debug("Processing Prompt:" + webiPrompt.getName());
						ReportParameter xmlParameter = getReportParameter(xmlScheduleSettings.getParameters(), webiPrompt.getName());
						if (xmlParameter != null) {
							webiPrompt.getValues().clear();
							int valueCount = 0;
							for (ParameterValue xmlParameterValue : xmlParameter.getCurrentValues()) {
								if (!xmlParameterValue.getValue().equalsIgnoreCase(Webi31Processor.STR_PROMPT_EMPTY_VALUE)) {
									valueCount++;
									webiPrompt.getValues().add(xmlParameterValue.getValue());
									Log.debug("Added Value:" + webiPrompt.getValues().get(webiPrompt.getValues().size() - 1));
									// curWebi.ProcessingInfo.Properties("SI_WEBI_PROMPTS").Properties(CStr(i)).Properties("SI_INDEX").Properties.Add(l.ToString,
									// sIndex(1).ToString,
									// CrystalDecisions.Enterprise.CePropFlags.cePropFlagNone)
									if (i == 1 && valueCount == 1) {
										try {
											infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).getProperties("SI_INDEX")
													.add(valueCount, null, com.crystaldecisions.sdk.properties.IProperty.ALL);
										} catch (Exception ee) {
											Log.error(ee.getLocalizedMessage(), ee);
										}
									} else
										try {
											infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).getProperties("SI_INDEX")
													.add(valueCount, "", com.crystaldecisions.sdk.properties.IProperty.ALL);
										} catch (Exception ee) {
											Log.error(ee.getLocalizedMessage(), ee);
										}
								} else {
									try {
										infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).add("SI_DISABLED", true, IProperty.ALL);
									} catch (Exception ee) {
										Log.error(ee.getLocalizedMessage(), ee);
									}
									isSkipPrompt = true;
								}

								// IObject.ProcessingInfo.Properties("SI_WEBI_PROMPTS").Properties(PromptIndex.ToString).Properties("SI_INDEX").Properties("SI_TOTAL").Value
								// = UBound(promptValues, 2)

								// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i
								// +
								// 1)).getProperties("SI_INDEX").getProperty("SI_TOTAL")
								// .setValue(xmlParameter.getCurrentValues().size());
								if (!isSkipPrompt) {
									try {
										infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).getProperties("SI_INDEX").getProperty("SI_TOTAL")
												.setValue(valueCount);
									} catch (Exception ee) {
										Log.error(ee.getLocalizedMessage(), ee);
									}
								} else {
									try {
										infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).remove("SI_VALUE");
										infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties((i + 1)).remove("SI_INDEX");
									} catch (Exception ee) {
										Log.error(ee.getLocalizedMessage(), ee);
									}
								}

								webiProcessingInfo.setUserInputLocaleName(Locale.getDefault().toString());
								Log.debug("SI_INDEX Propery Value is Set");
							}

						} else
							Log.error("Parameter " + webiPrompt.getName() + " Not found in XML. Parameter value will be ignored");
					}

				}
			}
		}

		return infoObject;
	}

	/**
	 * Find XML Report Parameter by Name
	 * 
	 * @param reportParameters
	 * @param parameterName
	 * @return
	 */
	private ReportParameter getReportParameter(List<ReportParameter> reportParameters, String parameterName) {

		for (ReportParameter xmlReportParameter : reportParameters) {
			if (xmlReportParameter.getName().equalsIgnoreCase(parameterName)) {
				return xmlReportParameter;
			}
		}

		return null;
	}

	/**
	 * Set Instnace's Custom Property
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 */
	private IInfoObject setCustomProperties(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings) {
		if (xmlScheduleSettings.getCustomProperties() != null) {
			Log.debug("Set Custom Properties");
			for (CustomProperty customProperty : xmlScheduleSettings.getCustomProperties()) {
				infoObject.properties().add(customProperty.getName(), customProperty.getValue(), 1);
				Log.debug("Set Custom property:" + customProperty.getName() + " Value:" + customProperty.getValue());
			}
		}

		return infoObject;
	}

	/**
	 * Creates and sets SMTP Destination Plug-in
	 * 
	 * @param infoObject
	 * @param xmlDestinationSmtp
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public IDestinationPlugin getSmtpDestinationPlugin(IInfoObject infoObject, DestinationSmtp xmlDestinationSmtp, IInfoStore infoStore) throws Exception {

		IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query("SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_SMTP + "'").get(0);
		ISMTPOptions smtpOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();

		smtpOptions.setAttachmentsEnabled(xmlDestinationSmtp.isIsAttachmentEnabled());
		Log.debug("Is Attachment Enabled:" + smtpOptions.isAttachmentsEnabled());

		for (Attachment xmlAttachment : xmlDestinationSmtp.getAttachments()) {
			if (StringUtils.isNotBlank(xmlAttachment.getName())) {
				smtpOptions.getAttachments().add(xmlAttachment.getMimeType(), xmlAttachment.getName());
				Log.debug("Added Attachment:" + xmlAttachment.getName() + " Mime Type:" + xmlAttachment.getMimeType());

			}
		}

		// TODO - Enable in 4.0
		// String[] bcc = StringUtils.split(xmlDestinationSmtp.getBCC(), ",");
		// if (bcc != null) {
		// for (int i = 0; i < bcc.length; i++) {
		// smtpOptions.getBCCAddresses().add(bcc[i]);
		// Log.debug("Added BCC:" + bcc[i]);
		// }
		// }

		String[] cc = StringUtils.split(xmlDestinationSmtp.getCC(), ",");
		if (cc != null) {
			for (int i = 0; i < cc.length; i++) {
				smtpOptions.getCCAddresses().add(cc[i]);
				Log.debug("Added CC:" + cc[i]);
			}
		}

		String[] to = StringUtils.split(xmlDestinationSmtp.getTo(), ",");
		if (to != null) {
			for (int i = 0; i < to.length; i++) {
				smtpOptions.getToAddresses().add(to[i]);
				Log.debug("Added TO:" + to[i]);
			}
		}

		if (StringUtils.isNotBlank(xmlDestinationSmtp.getDelimiter())) {
			smtpOptions.setDelimiter(xmlDestinationSmtp.getDelimiter());
			Log.debug("Set Delimeter to:" + smtpOptions.getDelimiter());
		}

		if (StringUtils.isNotBlank(xmlDestinationSmtp.getDomain())) {
			smtpOptions.setDomainName(xmlDestinationSmtp.getDomain());
			Log.debug("Domain Set to:" + smtpOptions.getDomainName());
		}
		if (StringUtils.isNotBlank(xmlDestinationSmtp.getFrom())) {
			smtpOptions.setSenderAddress(xmlDestinationSmtp.getFrom());
			Log.debug("Send From<Sender Address>:" + smtpOptions.getSenderAddress());
		}

		if (StringUtils.isNotBlank(xmlDestinationSmtp.getMessage())) {
			smtpOptions.setMessage(xmlDestinationSmtp.getMessage());
			Log.debug("Message Set to:" + smtpOptions.getMessage());
		}

		if (StringUtils.isNotBlank(xmlDestinationSmtp.getPassword())) {
			smtpOptions.setSMTPPassword(xmlDestinationSmtp.getPassword());
			Log.debug("Password set to ******");
		}

		if (StringUtils.isNotBlank(xmlDestinationSmtp.getServerName())) {
			smtpOptions.setServerName(xmlDestinationSmtp.getServerName());
			Log.debug("SMTP Server is set to:" + smtpOptions.getServerName());
		}
		if (StringUtils.isNotBlank(xmlDestinationSmtp.getSubject())) {
			smtpOptions.setSubject(xmlDestinationSmtp.getSubject());
			Log.debug("Subject is set to:" + smtpOptions.getSubject());
		}

		if (xmlDestinationSmtp.getAuthenticationType() != null) {
			if (xmlDestinationSmtp.getAuthenticationType().equalsIgnoreCase(CommonConstants.STR_SMTP_AUTH_TYPE_LOGIN)) {
				smtpOptions.setSMTPAuthenticationType(ISMTPOptions.CeSMTPAuthentication.LOGIN);
				Log.debug("SMTP Authentication is set to Login");
			} else if (xmlDestinationSmtp.getAuthenticationType().equalsIgnoreCase(CommonConstants.STR_SMTP_AUTH_TYPE_NONE)) {
				smtpOptions.setSMTPAuthenticationType(ISMTPOptions.CeSMTPAuthentication.NONE);
				Log.debug("SMTP Authentication is set to None");
			} else if (xmlDestinationSmtp.getAuthenticationType().equalsIgnoreCase(CommonConstants.STR_SMTP_AUTH_TYPE_PLAIN)) {
				smtpOptions.setSMTPAuthenticationType(ISMTPOptions.CeSMTPAuthentication.PLAIN);
				Log.debug("SMTP Authentication is set to Plain");
			}
		}

		return destinationPlugin;

	}

	/**
	 * Set Instance Destination
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @param infoStore
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IInfoObject setDestinationInfo(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {

		ISchedulingInfo schedulingInfo = infoObject.getSchedulingInfo();

		if (xmlScheduleSettings.getDestinationFrs() != null) {
			Log.debug("Destination FRS");
			schedulingInfo.getDestinations().clear();

		} else if (xmlScheduleSettings.getDestinationSmtp() != null) {
			Log.debug("Destination SMTP");
			schedulingInfo.getDestinations().clear();

			DestinationSmtp xmlSmtpDestination = xmlScheduleSettings.getDestinationSmtp();
			IDestinationPlugin destinationPlugin = getSmtpDestinationPlugin(infoObject, xmlScheduleSettings.getDestinationSmtp(), infoStore);
			ISMTPOptions smtpOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();

			if (!xmlSmtpDestination.isIsUseJobServerDefaults()) {
				smtpOptions.setAttachmentsEnabled(xmlSmtpDestination.isIsAttachmentEnabled());
				smtpOptions.setDelimiter(xmlSmtpDestination.getDelimiter());
				smtpOptions.setMessage(xmlSmtpDestination.getMessage());
				smtpOptions.setSenderAddress(xmlSmtpDestination.getFrom());
				smtpOptions.setSubject(xmlSmtpDestination.getSubject());
				IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_SMTP);
				destination.setFromPlugin(destinationPlugin);
			} else {
				schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_SMTP);
			}

		} else if (xmlScheduleSettings.getDestinationManaged() != null) {

			Log.debug("Destinaion Managed");
			schedulingInfo.getDestinations().clear();

			DestinationManaged xmlDestinationManaged = xmlScheduleSettings.getDestinationManaged();
			IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query("SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_MANAGED + "'").get(0);
			IManagedOptions managedOptions = (IManagedOptions) destinationPlugin.getScheduleOptions();

			if (!xmlDestinationManaged.isIsUseJobServerDefaults()) {
				if (xmlDestinationManaged.getDestinationOption().equalsIgnoreCase(STR_DESTINATION_FAVORITES)) {
					managedOptions.setDestinationOption(IManagedOptions.CeDestinationOption.ceFavoritesFolder);
					Log.debug("Schedule To Favorites");
					@SuppressWarnings("rawtypes")
					Set favoritesSet = managedOptions.getDestinations();
					favoritesSet.clear();
					for (Inbox favorite : xmlDestinationManaged.getFavorites()) {
						try {
							String cmsQuery = "SELECT SI_ID,SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + favorite.getName() + "'" + " AND SI_PROGID='"
									+ (favorite.isGroup() ? CommonConstants.STR_DESTINATION_GROUP : CommonConstants.STR_DESTINATION_USER) + "'";
							Log.debug("Running CMS Query:" + cmsQuery);
							IInfoObject userObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
							favoritesSet.add(userObject.getID());
							Log.debug("Adding ID:" + userObject.getID());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage(), ee);
							throw new Exception(ErrorCodes.STR_ERROR_RESOLVING_USERS_AND_GROUPS_NAMES_FAILED_TO_FIND + CommonConstants.STR_ERROR_INFO_SEPARATOR + favorite.getName());
						}
					}

				} else {

					managedOptions.setDestinationOption(IManagedOptions.CeDestinationOption.ceInbox);

					Log.debug("Schedule To Inboxes");

					@SuppressWarnings("rawtypes")
					Set userSet = managedOptions.getDestinations();
					userSet.clear();
					for (Inbox inbox : xmlDestinationManaged.getInboxes()) {
						try {
							String cmsQuery = "SELECT SI_ID,SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + inbox.getName() + "'" + " AND SI_PROGID='"
									+ (inbox.isGroup() ? CommonConstants.STR_DESTINATION_GROUP : CommonConstants.STR_DESTINATION_USER) + "'";
							Log.debug("Running CMS Query:" + cmsQuery);
							IInfoObject userObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
							userSet.add(userObject.getID());
							Log.debug("Adding ID:" + userObject.getID());
						} catch (Exception ee) {
							Log.error(ee.getLocalizedMessage(), ee);
							throw new Exception(ErrorCodes.STR_ERROR_RESOLVING_USERS_AND_GROUPS_NAMES_FAILED_TO_FIND + CommonConstants.STR_ERROR_INFO_SEPARATOR + inbox.getName());
						}
					}
				}

				Log.debug("Send option Text:" + xmlDestinationManaged.getSendOptionText());
				if (xmlDestinationManaged.getSendOptionText().equalsIgnoreCase(CommonConstants.STR_MANAGED_SEND_OPTION_COPY)) {
					managedOptions.setSendOption(IManagedOptions.CeManagedSendOption.ceCopy);
					Log.debug("By Copy");
				} else if (xmlDestinationManaged.getSendOptionText().equalsIgnoreCase(CommonConstants.STR_MANAGED_SEND_OPTION_SHORCUT)) {
					managedOptions.setSendOption(IManagedOptions.CeManagedSendOption.ceShortcut);
					Log.debug("By Shortcut");
				}

				if (StringUtils.isNotBlank(xmlDestinationManaged.getTargetObjectName())) {
					managedOptions.setTargetObjectName(xmlDestinationManaged.getTargetObjectName());
					Log.debug("Target Object Name:" + managedOptions.getTargetObjectName());
				}

				managedOptions.setCreateNewObject(xmlDestinationManaged.isIsCreateNewObject());
				Log.debug("Is Create New Object:" + xmlDestinationManaged.isIsCreateNewObject());

				managedOptions.setIncludeInstance(xmlDestinationManaged.isIsIncludeInstance());
				Log.debug("Is Include Instance:" + managedOptions.isIncludeInstance());

				managedOptions.setInstanceAsParent(xmlDestinationManaged.isIsInstanceAsParent());
				Log.debug(" Is Instance As a parent:" + managedOptions.isInstanceAsParent());

				managedOptions.setKeepSavedData(xmlDestinationManaged.isIsKeepSaveData());
				Log.debug("Is Keep Saved Data");

				IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_MANAGED);
				destination.setFromPlugin(destinationPlugin);
			} else {
				schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_MANAGED);
			}

		} else if (xmlScheduleSettings.getDestinationFtp() != null) {
			Log.debug("Destination FTP");
			schedulingInfo.getDestinations().clear();

			DestinationFtp xmlDestinationFtp = xmlScheduleSettings.getDestinationFtp();
			IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query("SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_FTP + "'").get(0);
			IFTPOptions ftpOptions = (IFTPOptions) destinationPlugin.getScheduleOptions();
			if (!xmlDestinationFtp.isIsUseJobServerDefaults()) {

				if (StringUtils.isNotBlank(xmlDestinationFtp.getPath())) {
					ftpOptions.getDestinationFiles().add(xmlDestinationFtp.getPath());
					Log.debug("Path Set To:" + xmlDestinationFtp.getPath());
				}
				if (StringUtils.isNotBlank(xmlDestinationFtp.getAccount())) {
					ftpOptions.setAccount(xmlDestinationFtp.getAccount());
					Log.debug("Ftp Account:" + ftpOptions.getAccount());
				}

				if (StringUtils.isNotBlank(xmlDestinationFtp.getPassword())) {
					ftpOptions.setPassword(xmlDestinationFtp.getPassword());
					Log.debug("Ftp Password:*****");
				}

				ftpOptions.setPort(xmlDestinationFtp.getPort());
				ftpOptions.setServerName(xmlDestinationFtp.getServerName());

				if (StringUtils.isNotBlank(xmlDestinationFtp.getUserName())) {
					ftpOptions.setUserName(xmlDestinationFtp.getUserName());
					Log.debug("FTP User:" + xmlDestinationFtp.getUserName());
				}

				IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_FTP);
				destination.setFromPlugin(destinationPlugin);

			} else {
				schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_FTP);
			}

		} else if (xmlScheduleSettings.getDestinationDiskUnManaged() != null) {
			Log.debug("Destination UnManaged");
			schedulingInfo.getDestinations().clear();

			DestinationDisk xmlDestinationDisk = xmlScheduleSettings.getDestinationDiskUnManaged();
			IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query("SELECT TOP 1 * FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + CommonConstants.STR_DESTINATION_UNMANAGED + "'")
					.get(0);
			if (!xmlDestinationDisk.isIsUseJobServerDefaults()) {
				IDiskUnmanagedOptions diskUnmanagedOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();

				if (StringUtils.isNotBlank(xmlDestinationDisk.getUserName())) {
					diskUnmanagedOptions.setUserName(xmlDestinationDisk.getUserName());
					Log.debug("UnManaged User Name:" + diskUnmanagedOptions.getUserName());
				}
				if (StringUtils.isNotBlank(xmlDestinationDisk.getPassword())) {
					diskUnmanagedOptions.setPassword(xmlDestinationDisk.getPassword());
					Log.debug("UnManged Password:****");
				}
				if (StringUtils.isNotBlank(xmlDestinationDisk.getOutputFileName())) {
					diskUnmanagedOptions.getDestinationFiles().add(xmlDestinationDisk.getOutputFileName());
					Log.debug("Unmanaged File Name:" + diskUnmanagedOptions.getDestinationFiles().get(0));
				}
				IDestination destination = schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_UNMANAGED);
				destination.setFromPlugin(destinationPlugin);

			} else {
				schedulingInfo.getDestinations().add(CommonConstants.STR_DESTINATION_UNMANAGED);
			}

		}

		return infoObject;
	}

	/**
	 * Handling Scheduling Info
	 * 
	 * @param infoObject
	 * @param xmlScheduleSettings
	 * @return
	 * @throws Exception
	 */
	private IInfoObject setSchedulingInfo(IInfoObject infoObject, ScheduleSettings xmlScheduleSettings, IInfoStore infoStore) throws Exception {

		Log.debug("Scheduling Info Type:" + xmlScheduleSettings.getScheduleFreqType().getType());

		if (xmlScheduleSettings.isIsCleanUpInstances()) {
			Log.debug("Setting Cleanup Instance");
			infoObject.getSchedulingInfo().properties().add("SI_CLEANUP", true, IProperty.ALL);
		}

		if (xmlScheduleSettings.getRetryInterval() > 0) {
			infoObject.getSchedulingInfo().setRetryInterval(xmlScheduleSettings.getRetryInterval());
			Log.debug("Setting Retries Interval:" + infoObject.getSchedulingInfo().getRetryInterval());
		}
		if (xmlScheduleSettings.getRetriesAllowed() > 0) {
			infoObject.getSchedulingInfo().setRetriesAllowed(xmlScheduleSettings.getRetriesAllowed());
			Log.debug("Setting Retries Allowed:" + infoObject.getSchedulingInfo().getRetriesAllowed());
		}
		if (xmlScheduleSettings.isRightNow()) {
			infoObject.getSchedulingInfo().setRightNow(true);
			Log.debug("Schedulue Right Now");
			return infoObject;
		}

		if (xmlScheduleSettings.getBeginDate() != null) {
			infoObject.getSchedulingInfo().setBeginDate(xmlScheduleSettings.getBeginDate());
			Log.debug("Beging Date is Set To:" + infoObject.getSchedulingInfo().getBeginDate());
		} else {
			Log.debug("Beging Date is null - set to current date/time");
			infoObject.getSchedulingInfo().setBeginDate(Calendar.getInstance().getTime());
			Log.debug("Begin Date Set to:" + infoObject.getSchedulingInfo().getBeginDate());
		}

		if (xmlScheduleSettings.getEndDate() != null) {
			infoObject.getSchedulingInfo().setEndDate(xmlScheduleSettings.getEndDate());
			Log.debug("Set End Date:" + infoObject.getSchedulingInfo().getEndDate());
		}

		if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_ONCE)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.ONCE);
			Log.debug("Schedule Type Once");
			return infoObject;
		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_HOURLY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.HOURLY);
			if (xmlScheduleSettings.getScheduleFreqType().getHourly().getMinutes() >= 0 && xmlScheduleSettings.getScheduleFreqType().getHourly().getMinutes() <= 59) {
				infoObject.getSchedulingInfo().setIntervalHours(xmlScheduleSettings.getScheduleFreqType().getHourly().getHours());
				infoObject.getSchedulingInfo().setIntervalMinutes(xmlScheduleSettings.getScheduleFreqType().getHourly().getMinutes());
				Log.debug("Interval Hours:" + infoObject.getSchedulingInfo().getIntervalHours());
				Log.debug("Interval Minutes:" + infoObject.getSchedulingInfo().getIntervalMinutes());
			} else {
				throw new Exception(ErrorCodes.STR_THE_RANGE_OF_MINUTES_FOR_AN_HOURLY_RECURRING_REPORT_IS_BETWEEN_0_AND_59 + CommonConstants.STR_ERROR_INFO_SEPARATOR
						+ xmlScheduleSettings.getScheduleFreqType().getHourly().getMinutes());
			}
			return infoObject;
		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_DAILY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.DAILY);
			infoObject.getSchedulingInfo().setIntervalDays(xmlScheduleSettings.getScheduleFreqType().getInterVal());
			Log.debug("Interval Daily:" + infoObject.getSchedulingInfo().getIntervalDays());
			return infoObject;
		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_FIRST_MONDAY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.FIRST_MONDAY);
			Log.debug("First Monday");
			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_LAST_DAY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.LAST_DAY);
			Log.debug("Last Day of Month");
			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_MONTHLY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.MONTHLY);
			Log.debug("Monthly. Interval:" + xmlScheduleSettings.getScheduleFreqType().getInterVal());
			if (xmlScheduleSettings.getScheduleFreqType().getInterVal() >= 1 && xmlScheduleSettings.getScheduleFreqType().getInterVal() <= 12) {
				infoObject.getSchedulingInfo().setIntervalMonths(xmlScheduleSettings.getScheduleFreqType().getInterVal());
			} else {
				throw new Exception(ErrorCodes.STR_THE_RANGE_OF_MONTHS_FOR_A_MONTHLY_RECURRING + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getScheduleFreqType().getInterVal());
			}
			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_NTH_DAY_OF_MONTH)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.NTH_DAY);
			if (xmlScheduleSettings.getScheduleFreqType().getInterVal() >= 1 && xmlScheduleSettings.getScheduleFreqType().getInterVal() <= 31) {
				infoObject.getSchedulingInfo().setIntervalNthDay(xmlScheduleSettings.getScheduleFreqType().getInterVal());
				Log.debug("Interval:" + CommonConstants.STR_FREQ_NTH_DAY_OF_MONTH + " " + infoObject.getSchedulingInfo().getIntervalDays());
			} else {
				throw new Exception(ErrorCodes.STR_THE_RANGE_OF_DAYS_FOR_A_NTH_DAY_MONTHLY_RECURRING_REPORT_IS_BETWEEN_1_AND_31 + CommonConstants.STR_ERROR_INFO_SEPARATOR
						+ xmlScheduleSettings.getScheduleFreqType().getInterVal());
			}

			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_FIRST_MONDAY)) {
			infoObject.getSchedulingInfo().setType(CeScheduleType.FIRST_MONDAY);
			Log.debug("First Monday of the month");
			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_CALENDAR)) {

			infoObject.getSchedulingInfo().setType(CeScheduleType.CALENDAR);

			List<CalendarDay> xmlCalendarDays = xmlScheduleSettings.getScheduleFreqType().getCalendar().getCalendarDays();

			for (int i = 0; i < xmlCalendarDays.size(); i++) {
				CalendarDay xmlCalendarDay = xmlCalendarDays.get(i);
				ICalendarDay calendarDay = (ICalendarDay) infoObject.getSchedulingInfo().getCalendarRunDays().add();
				if (xmlCalendarDay.getWeekNumber() != null) {
					calendarDay.setWeekNumber(xmlCalendarDay.getWeekNumber());
					Log.debug("Day Index:" + i + " Week Number:" + calendarDay.getWeekNumber());
				}
				if (xmlCalendarDay.getDayOfWeek() != null) {
					calendarDay.setDayOfWeek(xmlCalendarDay.getDayOfWeek());
					Log.debug("Day Index " + i + " Value:" + xmlCalendarDay.getDayOfWeek());
				}
				if (xmlCalendarDay.getStartMonth() != null) {
					calendarDay.setStartMonth(xmlCalendarDay.getStartMonth());
					Log.debug("Start Month Index " + i + " Value:" + xmlCalendarDay.getStartMonth());
				}
				if (xmlCalendarDay.getStartDay() != null) {
					calendarDay.setStartDay(xmlCalendarDay.getStartDay());
					Log.debug("Start Day Index " + i + " Value:" + xmlCalendarDay.getStartDay());
				}

				if (xmlCalendarDay.getStartYear() != null) {
					calendarDay.setStartYear(xmlCalendarDay.getStartYear());
					Log.debug("Start Year Index " + i + " Value:" + xmlCalendarDay.getStartYear());
				}

				if (xmlCalendarDay.getEndMonth() != null) {
					calendarDay.setEndMonth(xmlCalendarDay.getEndMonth());
					Log.debug("End Month Index " + i + " Value:" + xmlCalendarDay.getEndMonth());
				}

				if (xmlCalendarDay.getEndDay() != null) {
					calendarDay.setEndDay(xmlCalendarDay.getEndDay());
					Log.debug("End Day Index " + i + " Value:" + xmlCalendarDay.getEndDay());
				}

				if (xmlCalendarDay.getEndYear() != null) {
					calendarDay.setEndYear(xmlCalendarDay.getEndYear());
					Log.debug("End Year Index" + i + " Value:" + xmlCalendarDay.getEndYear());
				}

			}

			return infoObject;

		} else if (xmlScheduleSettings.getScheduleFreqType().getType().equals(CommonConstants.STR_FREQ_CALENDAR_TEMPLATE)) {

			infoObject.getSchedulingInfo().setType(CeScheduleType.CALENDAR_TEMPLATE);
			if (StringUtils.isNotBlank(xmlScheduleSettings.getScheduleFreqType().getCalendar().getName())) {
				try {
					String cmsQuery = "SELECT SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_NAME='" + xmlScheduleSettings.getScheduleFreqType().getCalendar().getName() + "'";
					Log.debug("Run CMS Query:" + cmsQuery);
					ICalendar calendarObject = (ICalendar) infoStore.query(cmsQuery).get(0);
					Log.debug("Calendar Name:" + calendarObject.getTitle() + " Calendar Id:" + calendarObject.getID());
					infoObject.getSchedulingInfo().setCalendarTemplate(calendarObject.getID());
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
					throw new Exception(ErrorCodes.STR_CANNOT_FIND_CALENDAR + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getScheduleFreqType().getCalendar().getName());
				}
				return infoObject;
			}

		} else {
			throw new Exception(ErrorCodes.STR_INVALID_RECURRENCE_TYPE + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getScheduleFreqType().getType());
		}

		return null;

	}
}
