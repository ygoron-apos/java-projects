/**
 * 
 */
package com.apos.infoobject.properties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.WebiPrompt;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;








//"Status",
//"Submitter",
//"Job Server Name",
//"Creation Time",
//"Update Time",
//"Start Time",
//"End Time",
//"Duration",
//"Next Run Time",
//"Result File Name",
//"Flags",
//"Original File Name",
//"Job Description",
//"Job Title",
//"Instance Kind",
//"Object Type",
//"Schedule Type",
//"Dependants - ID",
//"Dependants - Name",
//"Dependencies - ID",
//"Dependencies - Name",
//"Destination",
//"Processing Security Extensions",
//"Format",
//"Server Group",
//"Server Group Choice",
//"Error Message",
//"Group Selection Formula",
//"Record Selection Formula",
//"ID",
//"Number of Alerts",
//"Is Recurring",
//"Locale",
//"Num Retries Allowed",
//"Retry Interval In Min",
//"Parent ID",
//"Location",
//"Component",
//"CUID",
//"Printer",
//"APOSNotification",
//"APOSDestinationEMAIL",
//"APOSDestinationDelimiterEMAIL",
//"APOSEmailSubjectEMAIL",
//"APOSEmailCCEMAIL",
//"APOSEmailBCCEMAIL",
//"APOSEmailMessageEMAIL",
//"APOSEmailFileNameEMAIL",
//"APOSFormatEMAIL",
//"APOSFormatPasswordEMAIL",
//"APOSEditPasswordEMAIL",
//"APOSPDFEditOptionsEMAIL",
//"APOSCompressFilesUsingZIPEMAIL",
//"APOSZIPPasswordEMAIL",
//"APOSCRCOMKeepHistoryEMAIL",
//"APOSDoNotDistributeEmptyReportEMAIL",
//"APOSDestinationCONTENT",
//"APOSDestinationDelimiterCONTENT",
//"APOSEmailSubjectCONTENT",
//"APOSEmailCCCONTENT",
//"APOSEmailBCCCONTENT",
//"APOSFormatCONTENT",
//"APOSDoNotDistributeEmptyReportCONTENT",
//"APOSDestinationFTP",
//"APOSDestinationDelimiterFTP",
//"APOSFormatFTP",
//"APOSFormatPasswordFTP",
//"APOSEditPasswordFTP",
//"APOSPDFEditOptionsFTP",
//"APOSCompressFilesUsingZIPFTP",
//"APOSZIPPasswordFTP",
//"APOSDoNotDistributeEmptyReportFTP",
//"APOSDestinationNETWORK",
//"APOSDestinationDelimiterNETWORK",
//"APOSFormatNETWORK",
//"APOSFormatPasswordNETWORK",
//"APOSEditPasswordNETWORK",
//"APOSPDFEditOptionsNETWORK",
//"APOSCompressFilesUsingZIPNETWORK",
//"APOSZIPPasswordNETWORK",
//"APOSDoNotDistributeEmptyReportNETWORK",
//"APOSDestinationPRINTER",
//"APOSDestinationDelimiterPRINTER",
//"APOSDoNotDistributeEmptyReportPRINTER",
//"APOSDestinationCRCOM",
//"APOSDestinationDelimiterCRCOM",
//"APOSFormatCRCOM",
//"APOSFormatPasswordCRCOM",
//"APOSEditPasswordCRCOM",
//"APOSPDFEditOptionsCRCOM",
//"APOSCRCOMKeepHistoryCRCOM",
//"APOSDoNotDistributeEmptyReportCRCOM",
//"APOSCRCOMUserNameOverrideCRCOM",
//"APOSCRCOMPasswordOverrideCRCOM",
//"APOSCompressFilesUsingZIPCRCOM",
//"APOSZIPPasswordCRCOM",
//"Server Name 1","Database Name 1","User Name 1","Server Name 2","Database Name 2","User Name 2","Server Name 3","Database Name 3","User Name 3","Server Name 4","Database Name 4","User Name 4","Server Name 5","Database Name 5","User Name 5","Parameter Name 1","Parameter Value 1","Parameter Name 2","Parameter Value 2","Parameter Name 3","Parameter Value 3","Parameter Name 4","Parameter Value 4","Parameter Name 5","Parameter Value 5","Parameter Name 6","Parameter Value 6","Parameter Name 7","Parameter Value 7","Parameter Name 8","Parameter Value 8","Parameter Name 9","Parameter Value 9","Parameter Name 10","Parameter Value 10","Parameter Name 11","Parameter Value 11","Parameter Name 12","Parameter Value 12","Parameter Name 13","Parameter Value 13","Parameter Name 14","Parameter Value 14","Parameter Name 15","Parameter Value 15","Parameter Name 16","Parameter Value 16","Parameter Name 17","Parameter Value 17","Parameter Name 18","Parameter Value 18","Parameter Name 19","Parameter Value 19","Parameter Name 20","Parameter Value 20","Parameter Name 21","Parameter Value 21","Parameter Name 22","Parameter Value 22","Parameter Name 23","Parameter Value 23","Parameter Name 24","Parameter Value 24","Parameter Name 25","Parameter Value 25","Parameter Name 26","Parameter Value 26","Parameter Name 27","Parameter Value 27","Parameter Name 28","Parameter Value 28","Parameter Name 29","Parameter Value 29","Parameter Name 30","Parameter Value 30","Parameter Name 31","Parameter Value 31","Parameter Name 32","Parameter Value 32","Parameter Name 33","Parameter Value 33","Parameter Name 34","Parameter Value 34","Parameter Name 35","Parameter Value 35","Parameter Name 36","Parameter Value 36","Parameter Name 37","Parameter Value 37","Parameter Name 38","Parameter Value 38","Parameter Name 39","Parameter Value 39","Parameter Name 40","Parameter Value 40","Parameter Name 41","Parameter Value 41","Parameter Name 42","Parameter Value 42","Parameter Name 43","Parameter Value 43","Parameter Name 44","Parameter Value 44","Parameter Name 45","Parameter Value 45","Parameter Name 46","Parameter Value 46","Parameter Name 47","Parameter Value 47","Parameter Name 48","Parameter Value 48","Parameter Name 49","Parameter Value 49","Parameter Name 50","Parameter Value 50"

import com.crystaldecisions.celib.properties.Property;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.occa.infostore.CePropertyID;
import com.crystaldecisions.sdk.occa.infostore.ICalendarDay;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo.ScheduleFlags;
import com.crystaldecisions.sdk.plugin.desktop.common.IProcessingExtension;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.plugin.destination.ftp.IFTPOptions;
import com.crystaldecisions.sdk.plugin.destination.managed.IManagedOptions;
import com.crystaldecisions.sdk.plugin.destination.smtp.IAttachment;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;

/**
 * @author Yuri Goron
 * 
 */
public class Type1WorkerThread implements Callable<Integer> {

	public static final String STR_COMMA_SPACE = ", ";
	public static final String STR_N_A = "";
	private String threadName;
	private static final Logger Log = Logger.getLogger(Type1WorkerThread.class);
	private ICommandLineArgs commandLineArgs;
	public static int INT_SUCCESS_COUNT = 0;
	public static int INT_FAILURE_COUNT = 0;
	private GenericObjectPool<BOXIConnection> pool;
	private static final String CMS_QUERY = "SELECT * FROM  CI_INFOOBJECTS  Where  SI_ID=";
	private int objectId;
	private List<String> headers;
	// private static final String CMS_DATE_FORMAT_SIMPLE =
	// "yyyy/MM/dd HH:mm:ss";
	private static final String CMS_DATE_FORMAT_SIMPLE = "yyyy/MM/dd HH:mm:ss";
	private static final String CMS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
	// private static final String DURATION_FORMAT = "HH:mm:ss";

	private static final String CMS_QUERY_OBJECT_NAME = "SELECT TOP 1 SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID = ";
	public static final String CMS_QUERY_DISK_UNMANAGED = "Select Top 1* from CI_SYSTEMOBJECTS WHERE SI_PARENTID=29 AND SI_NAME='CrystalEnterprise.DiskUnmanaged'";
	public static final String CMS_QUERY_SMTP = "Select Top 1* from CI_SYSTEMOBJECTS WHERE SI_PARENTID=29 AND SI_NAME='CrystalEnterprise.Smtp'";
	public static final String CMS_QUERY_FTP = "Select Top 1* from CI_SYSTEMOBJECTS WHERE SI_PARENTID=29 AND SI_NAME='CrystalEnterprise.Ftp'";
	public static final String CMS_QUERY_MANAGED = "Select Top 1* from CI_SYSTEMOBJECTS WHERE SI_PARENTID=29 AND SI_NAME='CrystalEnterprise.Managed'";
	private static final String CMS_QUERY_IMANAGED = "SELECT TOP 1 SI_NAME FROM CI_SYSTEMOBJECTS WHERE SI_ID=";
	// private static final String CMS_QUERY_PATH =
	// "SELECT TOP 1 SI_ID, SI_PARENTID,SI_NAME FROM CI_INFOOBJECTS WHERE SI_ID=";

	private static SimpleDateFormat formatter = new SimpleDateFormat(CMS_DATE_FORMAT_SIMPLE);
	private SimpleDateFormat cmsDateformatter = new SimpleDateFormat(CMS_DATE_FORMAT);

	public Type1WorkerThread(String name, ICommandLineArgs commandLineArgs, int objectId, GenericObjectPool<BOXIConnection> pool, List<String> headers) {
		this.threadName = name;
		this.commandLineArgs = commandLineArgs;
		this.objectId = objectId;
		this.pool = pool;
		this.headers = headers;
	}

	public Integer call() throws Exception {

		BOXIConnection boxiConnection = null;
		IInfoObject infoObject;
		try {

			boxiConnection = pool.borrowObject();
			Log.info("Processing Object:" + objectId);
			Log.debug("Thread Type1" + threadName + " Started. Processing " + this.objectId + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName() + " Active Connections "
					+ pool.getNumActive());

			String cmsQuery = CMS_QUERY + objectId;
			Log.debug("Tryig to Run Query:" + cmsQuery);
			infoObject = (IInfoObject) boxiConnection.getInfoStore().query(cmsQuery).get(0);
			HashMap<String, String> output = new HashMap<String, String>();

			CommonThreadTools commonThreadTools = new CommonThreadTools();

			String instanceProgId = infoObject.getProgID();
			if (infoObject.properties().getProperty("SI_PROGID_MACHINE") != null) {
				Log.debug("Machine Based Prog ID(Real Prog ID:" + infoObject.properties().getProperty("SI_PROGID_MACHINE").toString());
				instanceProgId = infoObject.properties().getProperty("SI_PROGID_MACHINE").toString();
			}

			// int scheduleStatus = getScheduleStatus(infoObject.properties(),
			// headers.get(0), "SI_SCHEDULE_STATUS");

			output = getInfoScheduleStatus(output, infoObject.properties(), headers.get(0), "SI_SCHEDULE_STATUS");
			output = getInfoSchedulePropertyString(output, infoObject, headers.get(1), "SI_SUBMITTER");
			output = getInfoSchedulePropertyString(output, infoObject, headers.get(2), "SI_MACHINE_USED");
			output = getInfoPropertyDate(output, infoObject.properties(), headers.get(3), "SI_CREATION_TIME");
			output = getInfoPropertyDate(output, infoObject.properties(), headers.get(4), "SI_UPDATE_TS");
			boolean isRecurring = getBoolean(infoObject.properties(), headers.get(31), "SI_RECURRING");
			if (isRecurring) {
				output = getInfoPropertyDate(output, infoObject.getSchedulingInfo().properties(), headers.get(5), "SI_STARTTIME");
				output = getInfoPropertyDate(output, infoObject.getSchedulingInfo().properties(), headers.get(6), "SI_ENDTIME");
			} else {
				output = getInfoPropertyDate(output, infoObject.properties(), headers.get(5), "SI_STARTTIME");
				output = getInfoPropertyDate(output, infoObject.properties(), headers.get(6), "SI_ENDTIME");

			}

			if (isRecurring)
				output.put(headers.get(7), "0");
			else
				output.put(headers.get(7), getDuration(infoObject));
			output = getInfoPropertyDate(output, infoObject.properties(), headers.get(8), "SI_NEXTRUNTIME");

			Property property = (Property) infoObject.properties().get("SI_FILES");

			if (property != null) {
				if (property.getPropertyBag().get("SI_FILE1") != null) {
					output.put(headers.get(9), property.getPropertyBag().get("SI_PATH").toString() + property.getPropertyBag().get("SI_FILE1").toString());
				} else {
					output.put(headers.get(9), STR_N_A);
				}
			} else {
				output.put(headers.get(9), STR_N_A);
			}

			// output = getInfoPropertyString(output, infoObject.properties(),
			// headers.get(10), "SI_FLAGS");
			output.put(headers.get(10), translateFlags(getInfoPropertyInteger(infoObject.properties(), "SI_FLAGS")));

			if (infoObject.getProcessingInfo() != null) {
				property = (Property) infoObject.getProcessingInfo().properties().get("SI_FILES");

				if (property != null) {
					if (property.getPropertyBag().get("SI_FILE1") != null) {
						output.put(headers.get(11), property.getPropertyBag().get("SI_PATH").toString() + property.getPropertyBag().get("SI_FILE1").toString());
					} else {
						output.put(headers.get(11), STR_N_A);
					}
				} else {
					output.put(headers.get(11), STR_N_A);
				}
			} else {
				output.put(headers.get(11), STR_N_A);
			}

			output = getInfoPropertyString(output, infoObject.properties(), headers.get(12), "SI_DESCRIPTION");
			// output = getInfoPropertyString(output, infoObject.properties(),
			// headers.get(13), "SI_TITLE");
			output.put(headers.get(13), infoObject.getTitle());

			output = getInfoPropertyString(output, infoObject.properties(), headers.get(14), "SI_KIND");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(15), "SI_OBTYPE");

			output.put(headers.get(16), getScheduleType(infoObject.getSchedulingInfo()));

			output.put(headers.get(17), getDependants(infoObject.getSchedulingInfo(), boxiConnection.getInfoStore(), false));
			output.put(headers.get(18), getDependants(infoObject.getSchedulingInfo(), boxiConnection.getInfoStore(), true));

			output.put(headers.get(19), getDependencies(infoObject.getSchedulingInfo(), boxiConnection.getInfoStore(), false));
			output.put(headers.get(20), getDependencies(infoObject.getSchedulingInfo(), boxiConnection.getInfoStore(), true));

			output.put(headers.get(21), getDestinations(infoObject.getSchedulingInfo(), boxiConnection.getInfoStore()));
			output.put(headers.get(22), getProcessingExtensionsInfo(infoObject));

			output.put(headers.get(23), infoObject.getKind());

			// String serverName=getObjectName(CMS_QUERY_OBJECT_NAME+
			// infoObject.getSchedulingInfo().getServerGroup(),
			// boxiConnection.getInfoStore());
			// String serverName = getObjectName(CMS_QUERY_OBJECT_NAME +
			// infoObject.getSchedulingInfo().getServerGroup(),
			// boxiConnection.getInfoStore());
			output.put(headers.get(24), String.valueOf(infoObject.getSchedulingInfo() != null ? infoObject.getSchedulingInfo().getServerGroup() : STR_N_A));
			output.put(headers.get(25), String.valueOf(infoObject.getSchedulingInfo() != null ? translateServerGroupChoice(infoObject.getSchedulingInfo().getServerGroupChoice()) : STR_N_A));
			output.put(headers.get(26), String.valueOf(infoObject.getSchedulingInfo() != null ? infoObject.getSchedulingInfo().getErrorMessage() : STR_N_A));
			output = getInfoProcessingPropertyString(output, infoObject, headers.get(27), "SI_GROUP_FORMULA");
			output = getInfoProcessingPropertyString(output, infoObject, headers.get(28), "SI_RECORD_FORMULA");
			output.put(headers.get(29), String.valueOf(infoObject.getID()));
			if (infoObject.getProcessingInfo() != null)
				output = getInfoPropertyString(output, infoObject.getProcessingInfo().properties().getProperties("SI_ALERTS"), headers.get(30), "SI_NUM_ALERTS");
			else
				output.put(headers.get(30), STR_N_A);

			if (isRecurring)
				output.put(headers.get(31), "Yes");
			else
				output.put(headers.get(31), "No");

			// output = getInfoPropertyBoolean(output, infoObject.properties(),
			// headers.get(31), "SI_RECURRING");
			// TODO
			output.put(headers.get(32), Locale.getDefault().toString());

			if (infoObject.getSchedulingInfo() != null)
				output.put(headers.get(33), String.valueOf(infoObject.getSchedulingInfo().getRetriesAllowed()));
			else
				output.put(headers.get(33), STR_N_A);

			if (infoObject.getSchedulingInfo() != null)
				output.put(headers.get(34), String.valueOf(infoObject.getSchedulingInfo().getRetryInterval()));
			else
				output.put(headers.get(34), STR_N_A);
			output.put(headers.get(35), String.valueOf(infoObject.getParentID()));
			output.put(headers.get(36), getObjectPath(boxiConnection.getInfoStore(), infoObject, new StringBuffer("")).toString());
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(37), "SI_COMPONENT");
			output.put(headers.get(38), String.valueOf(infoObject.getCUID()));
			// TODO PRINTERS
			output.put(headers.get(39), STR_N_A);

			output = getInfoPropertyString(output, infoObject.properties(), headers.get(40), "APOSNotification");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(41), "APOSDestinationEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(42), "APOSDestinationDelimiterEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(43), "APOSEmailSubjectEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(44), "APOSEmailCCEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(45), "APOSEmailBCCEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(46), "APOSEmailMessageEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(47), "APOSEmailFileNameEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(48), "APOSFormatEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(49), "APOSFormatPasswordEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(50), "APOSEditPasswordEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(51), "APOSPDFEditOptionsEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(52), "APOSCompressFilesUsingZIPEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(53), "APOSZIPPasswordEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(54), "APOSCRCOMKeepHistoryEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(55), "APOSDoNotDistributeEmptyReportEMAIL");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(56), "APOSDestinationCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(57), "APOSDestinationDelimiterCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(58), "APOSEmailSubjectCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(59), "APOSEmailCCCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(60), "APOSEmailBCCCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(61), "APOSFormatCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(62), "APOSDoNotDistributeEmptyReportCONTENT");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(63), "APOSDestinationFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(64), "APOSDestinationDelimiterFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(65), "APOSFormatFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(66), "APOSFormatPasswordFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(67), "APOSEditPasswordFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(68), "APOSPDFEditOptionsFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(69), "APOSCompressFilesUsingZIPFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(70), "APOSZIPPasswordFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(71), "APOSDoNotDistributeEmptyReportFTP");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(72), "APOSDestinationNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(73), "APOSDestinationDelimiterNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(74), "APOSFormatNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(75), "APOSFormatPasswordNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(76), "APOSEditPasswordNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(77), "APOSPDFEditOptionsNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(78), "APOSCompressFilesUsingZIPNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(79), "APOSZIPPasswordNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(80), "APOSDoNotDistributeEmptyReportNETWORK");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(81), "APOSDestinationPRINTER");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(82), "APOSDestinationDelimiterPRINTER");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(83), "APOSDoNotDistributeEmptyReportPRINTER");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(84), "APOSDestinationCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(85), "APOSDestinationDelimiterCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(86), "APOSFormatCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(87), "APOSFormatPasswordCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(88), "APOSEditPasswordCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(89), "APOSPDFEditOptionsCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(90), "APOSCRCOMKeepHistoryCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(91), "APOSDoNotDistributeEmptyReportCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(92), "APOSCRCOMUserNameOverrideCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(93), "APOSCRCOMPasswordOverrideCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(94), "APOSCompressFilesUsingZIPCRCOM");
			output = getInfoPropertyString(output, infoObject.properties(), headers.get(95), "APOSZIPPasswordCRCOM");

			for (int i = 0; i < commandLineArgs.getMaxLogons(); i++) {
				output = getLogonInfo(output, infoObject, commandLineArgs.getMaxLogons());
			}

			if (instanceProgId.equalsIgnoreCase("CrystalEnterprise.Webi")) {
				Log.debug("Processing Webi Based Instance");
				PluginInterfaceWebi xmlInterfaceWebi = commonThreadTools.getPluginInterfaceWebi(infoObject);
				output = getWebiBasedParameters(output, xmlInterfaceWebi, commandLineArgs.getMaxParameters());
			} else {
				for (int i = 0; i < commandLineArgs.getMaxParameters(); i++) {
					output = getParametersInfo(output, infoObject, commandLineArgs.getMaxParameters());
				}

			}

			Extractor.type1CsvOtputQueue.put(output);
			INT_SUCCESS_COUNT++;
			return 0;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			INT_FAILURE_COUNT++;
		} finally {
			if (boxiConnection != null)
				pool.returnObject(boxiConnection);

		}
		return -1;
	}

	private String translateServerGroupChoice(int flag) {
		switch (flag) {
		case ISchedulingInfo.GroupChoice.FIRST_AVAILABLE:
			return "ceFirstAvailable";
		case ISchedulingInfo.GroupChoice.PREFERRED:
			return "cePreferred";
		case ISchedulingInfo.GroupChoice.SPECIFIED:
			return "ceSpecified";
		}
		return STR_N_A;
	}

	/**
	 * Translates Flags
	 * 
	 * @param flag
	 * @return
	 */
	private String translateFlags(int flag) {

		if (flag == ScheduleFlags.PAUSE)
			return "Pause";
		else if (flag == ScheduleFlags.RESUME)
			return "Resume";
		else
			return "Other" + flag;
	}

	private HashMap<String, String> getWebiBasedParameters(HashMap<String, String> result, PluginInterfaceWebi pluginInterfaceWebi, int maxParameters) {
		int maxParams = 0;
		try {

			int numPrompts = pluginInterfaceWebi.getPrompts().size();
			maxParams = maxParameters < numPrompts ? maxParameters : numPrompts;
			Log.debug("Initialize. Max Parameters:" + maxParams);
			for (int i = 1; i <= maxParams; i++) {
				result.put("Parameter Name " + i, STR_N_A);
				result.put("Parameter Value " + i, STR_N_A);
			}

			for (int i = 0; i < maxParams; i++) {
				String prompValues = "";
				WebiPrompt webiPrompt = pluginInterfaceWebi.getPrompts().get(i);
				 
				for (int j = 0; j < webiPrompt.getValues().size(); j++) {
					Log.debug("Prompt Name:" + webiPrompt.getName() + " Prompt Value:" + webiPrompt.getValues().get(j).getValue());
					prompValues += webiPrompt.getValues().get(j).getValue() + STR_COMMA_SPACE;

				}

				if (prompValues.endsWith(STR_COMMA_SPACE))
					prompValues = StringUtils.removeEnd(prompValues, STR_COMMA_SPACE);

				Log.debug("Result for Prompt:" + webiPrompt.getName() + ":" + prompValues);
				result.put("Parameter Name " + (i + 1), webiPrompt.getName());
				result.put("Parameter Value " + (i + 1), prompValues);

			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return result;

	}

	/**
	 * Get Parameters Info
	 * 
	 * @param result
	 * @param infoObject
	 * @param maxParameters
	 * @return
	 */
	public HashMap<String, String> getParametersInfo(HashMap<String, String> result, IInfoObject infoObject, int maxParameters) {

		int maxParams = 0;
		try {

			IProperties propertyBag = null;
			IProperty property = null;

			if (infoObject.getProcessingInfo() != null) {
				propertyBag = infoObject.getProcessingInfo().properties();
				property = propertyBag.getProperty("SI_PROMPTS");

				if (property != null) {
					propertyBag = (IProperties) property.getValue();
					property = propertyBag.getProperty("SI_NUM_PROMPTS");
					int numPrompts = ((Integer) property.getValue()).intValue();

					maxParams = maxParameters < numPrompts ? maxParameters : numPrompts;
				} else {
					maxParams = maxParameters;
				}
			} else {
				maxParams = maxParameters;
			}
			Log.debug("Initialize. Max Parameters:" + maxParams);
			for (int i = 1; i <= maxParams; i++) {
				result.put("Parameter Name " + i, STR_N_A);
				result.put("Parameter Value " + i, STR_N_A);
			}

			if (property != null) {
				for (int i = 1; i <= maxParams; i++) {
					try {
						IProperties propPromts = propertyBag.getProperties("SI_PROMPT" + String.valueOf(i));
						String promptName = propPromts.getProperty("SI_NAME").toString();
						String prompValues = "";
						IProperties propCurrentValues = propPromts.getProperties("SI_CURRENT_VALUES");
						Log.debug("Prompt Name:" + promptName + " Number of Values:" + propCurrentValues.getProperty("SI_NUM_VALUES"));
						int numberOfValues = ((Integer) propCurrentValues.getProperty("SI_NUM_VALUES").getValue()).intValue();
						for (int j = 1; j <= numberOfValues; j++) {
							String maximum = "";
							String minumum = "";

							IProperties propValues = propCurrentValues.getProperties("SI_VALUE" + j);

							if (propValues.getProperty("SI_DATA") != null) {
								Log.debug("Prompt Name:" + promptName + " Prompt Value:" + propValues.getProperty("SI_DATA").toString());
								prompValues += propValues.getProperty("SI_DATA").toString() + STR_COMMA_SPACE;

							}

							if (propValues.getProperties("SI_MIN") != null) {
								minumum = propValues.getProperties("SI_MIN").getProperty("SI_DATA").toString();
								Log.debug("There is a SI Min for " + promptName + " Value:" + minumum);
							}

							if (propValues.getProperties("SI_MAX") != null) {
								maximum = propValues.getProperties("SI_MAX").getProperty("SI_DATA").toString();
								Log.debug("There is a SI Max for " + promptName + " Value:" + maximum);
							}
							if (minumum != "" || maximum != "") {
								prompValues += minumum + "..." + maximum;
							}

						}

						if (prompValues.endsWith(STR_COMMA_SPACE))
							prompValues = StringUtils.removeEnd(prompValues, STR_COMMA_SPACE);

						Log.debug("Result for Prompt:" + promptName + ":" + prompValues);
						result.put("Parameter Name " + i, promptName);
						result.put("Parameter Value " + i, prompValues);
						;

					} catch (Exception ee) {
						Log.error(ee.getLocalizedMessage(), ee);
						result.put("Parameter Name " + i, STR_N_A);
						result.put("Parameter Value " + i, STR_N_A);
					}
				}
			}

		} catch (Exception ee) {
			Log.error("Object Id:" + infoObject.getID() + " Max DB Logon " + maxParams + ee.getLocalizedMessage(), ee);
		}

		return result;

	}

	/**
	 * Get Logon Info
	 * 
	 * @param result
	 * @param infoObject
	 * @param maxDbLogons
	 * @return
	 */
	private HashMap<String, String> getLogonInfo(HashMap<String, String> result, IInfoObject infoObject, int maxDbLogons) {
		int maxDBLogon = 0;
		try {

			IProperty property = null;
			IProperties propertyBag = null;
			if (infoObject.getProcessingInfo() != null) {
				propertyBag = infoObject.getProcessingInfo().properties();
				property = propertyBag.getProperty(CePropertyID.SI_LOGON_INFO);

				if (property != null) {
					propertyBag = (IProperties) property.getValue();
					property = propertyBag.getProperty("SI_NUM_LOGONS");
					int numLogons = ((Integer) property.getValue()).intValue();
					maxDBLogon = maxDbLogons < numLogons ? maxDbLogons : numLogons;
				} else {
					maxDBLogon = maxDbLogons;
				}
			} else {
				maxDBLogon = maxDbLogons;
			}
			Log.debug("Initialize. Max DB:" + maxDBLogon);
			for (int i = 1; i <= maxDBLogon; i++) {
				result.put("Server Name " + i, STR_N_A);
				result.put("Database Name " + i, STR_N_A);
				result.put("User Name " + i, STR_N_A);
			}

			if (property != null) {

				for (int i = 1; i <= maxDBLogon; i++) {
					property = propertyBag.getProperty("SI_LOGON" + String.valueOf(i));
					Log.debug("Value:" + property.getValue().toString());
					IProperties logonBag = (IProperties) property.getValue();
					result.put("Server Name " + i, logonBag.getProperty("SI_SERVER").toString());
					result.put("Database Name " + i, logonBag.getProperty("SI_DB").toString());
					result.put("User Name " + i, logonBag.getProperty("SI_USER").toString());
				}
			}

		} catch (Exception ee) {
			Log.error("Object Id:" + infoObject.getID() + " Max DB Logon " + maxDBLogon + ee.getLocalizedMessage(), ee);
		}

		return result;
	}

	private String getProcessingExtensionsInfo(IInfoObject infoObject) {
		String resultString = "";
		try {
			if (infoObject.getProcessingInfo() != null) {
				if (infoObject.getProcessingInfo().properties().get("SI_PROCESSING_EXTENSIONS") != null) {
					if (StringUtils.isNotBlank(infoObject.getProcessingInfo().properties().get("SI_PROCESSING_EXTENSIONS").toString())) {

						if (infoObject.getProgID().equalsIgnoreCase("CrystalEnterprise.Report")) {
							IReport report = (IReport) infoObject;

							Log.debug("Processing Extension Property Found. Size:" + report.getProcessingSecurityExtensions().size());

							for (int i = 0; i < report.getProcessingSecurityExtensions().size(); i++) {
								for (@SuppressWarnings("rawtypes")
								Iterator iterator = report.getProcessingSecurityExtensions().iterator(); iterator.hasNext();) {
									IProcessingExtension pe = (IProcessingExtension) iterator.next();
									Log.debug("Name:" + pe.getName());
									resultString += "[" + pe.getName() + ("]" + STR_COMMA_SPACE);
								}

							}

							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

						}
					}
				}

				return resultString;
			}
		} catch (Exception ee) {
			try {
				Log.error(infoObject.getProgID() + ":" + ee.getLocalizedMessage(), ee);
			} catch (SDKException e) {
				e.printStackTrace();
			}
		}
		return STR_N_A;
	}

	/**
	 * @param schedulingInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getDestinations(ISchedulingInfo schedulingInfo, IInfoStore infoStore) {
		String resultString = "";
		try {
			if (schedulingInfo != null)
				if (schedulingInfo.getDestinations() != null) {
					for (int i = 0; i < schedulingInfo.getDestinations().size(); i++) {
						IDestination destination = (IDestination) schedulingInfo.getDestinations().get(i);
						Log.debug("Destination Name:" + destination.getName());
						resultString += destination.getName() + ":";
						if (destination.getName().equalsIgnoreCase("CrystalEnterprise.DiskUnmanaged")) {
							IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(CMS_QUERY_DISK_UNMANAGED).get(0);
							destination.copyToPlugin(destinationPlugin);
							IDiskUnmanagedOptions scheduleOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();

							for (int j = 0; j < scheduleOptions.getDestinationFiles().size(); j++) {
								Log.debug("Destination file:" + scheduleOptions.getDestinationFiles().get(j).toString());
								resultString += scheduleOptions.getDestinationFiles().get(j).toString() + STR_COMMA_SPACE;
							}
							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

							Log.debug("Result String:" + resultString);

						} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Smtp")) {
							Log.debug("Destination Smtp");
							IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(CMS_QUERY_SMTP).get(0);

							destination.copyToPlugin(destinationPlugin);
							ISMTPOptions scheduleOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();
							for (Object toAddress : scheduleOptions.getToAddresses()) {
								resultString += " To:" + toAddress.toString() + STR_COMMA_SPACE;
							}

							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

							for (Object ccAddress : scheduleOptions.getCCAddresses()) {
								resultString += " CC:" + ccAddress.toString() + STR_COMMA_SPACE;
							}

							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

							resultString += " - From: " + scheduleOptions.getSenderAddress();
							resultString += " - Subject: " + scheduleOptions.getSubject();
							resultString += " - Message: " + scheduleOptions.getMessage();

							for (Object object : scheduleOptions.getAttachments()) {
								IAttachment attachment = (IAttachment) object;
								resultString += " File:" + attachment.getEmbedName() + STR_COMMA_SPACE;
							}

							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

						} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Ftp")) {
							Log.debug("Destination FTP");
							IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(CMS_QUERY_FTP).get(0);
							destination.copyToPlugin(destinationPlugin);
							IFTPOptions scheduleOptions = (IFTPOptions) destinationPlugin.getScheduleOptions();
							Log.debug("FTP Server Name:" + scheduleOptions.getServerName());
							for (int j = 0; j < scheduleOptions.getDestinationFiles().size(); j++) {
								Log.debug("Destination file:" + scheduleOptions.getDestinationFiles().get(j).toString());
								resultString += scheduleOptions.getDestinationFiles().get(j).toString() + STR_COMMA_SPACE;
							}

							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);
						} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Managed")) {
							Log.debug("Destination Managed");
							IDestinationPlugin destinationPlugin = (IDestinationPlugin) infoStore.query(CMS_QUERY_MANAGED).get(0);
							destination.copyToPlugin(destinationPlugin);
							IManagedOptions scheduleOptions = (IManagedOptions) destinationPlugin.getScheduleOptions();

							Log.debug("Destination Size:" + scheduleOptions.getDestinations().size());
							for (Iterator<Integer> id = scheduleOptions.getDestinations().iterator(); id.hasNext();) {
								String name = getObjectName(CMS_QUERY_IMANAGED + id.next(), infoStore);
								Log.debug("Name:" + name);
								resultString += name + STR_COMMA_SPACE;

							}
							if (resultString.endsWith(STR_COMMA_SPACE))
								resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);

						}

					}
					return resultString;
				}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return STR_N_A;
	}

	/**
	 * Build Dependencies
	 * 
	 * @param schedulingInfo
	 * @param isNames
	 * @return
	 */
	private String getDependencies(ISchedulingInfo schedulingInfo, IInfoStore infoStore, boolean isNames) {
		String resultString = "";
		try {
			if (schedulingInfo != null)
				if (schedulingInfo.getDependencies() != null) {
					for (int i = 0; i < schedulingInfo.getDependencies().size(); i++) {
						Integer eventId = (Integer) schedulingInfo.getDependencies().get(i);
						if (isNames)
							resultString = resultString + getObjectName(CMS_QUERY_OBJECT_NAME + eventId, infoStore) + STR_COMMA_SPACE;
						else
							resultString = resultString + eventId + STR_COMMA_SPACE;
					}
					if (resultString.endsWith(STR_COMMA_SPACE))
						resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);
					Log.debug("Dependencies String:" + resultString);
					return resultString;
				}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			return STR_N_A;
		}
		return STR_N_A;
	}

	/**
	 * Build Events
	 * 
	 * @param schedulingInfo
	 * @param isNames
	 * @return
	 */
	private String getDependants(ISchedulingInfo schedulingInfo, IInfoStore infoStore, boolean isNames) {
		String resultString = "";
		try {
			if (schedulingInfo != null) {
				if (schedulingInfo.getDependants() != null) {
					Log.debug("Dependants:" + schedulingInfo.getDependants().size());
					for (int i = 0; i < schedulingInfo.getDependants().size(); i++) {
						Integer eventId = (Integer) schedulingInfo.getDependants().get(i);
						if (isNames) {
							String dependantName = getObjectName(CMS_QUERY_OBJECT_NAME + eventId, infoStore);
							resultString = resultString + dependantName + STR_COMMA_SPACE;
						} else {
							resultString = resultString + eventId + STR_COMMA_SPACE;
						}
					}
					if (resultString.endsWith(STR_COMMA_SPACE))
						resultString = StringUtils.removeEnd(resultString, STR_COMMA_SPACE);
					Log.debug("Dependants String:" + resultString);
					return resultString;
				}
			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			return STR_N_A;
		}

		return STR_N_A;
	}

	/**
	 * Process Schedule Type
	 * 
	 * @param schedulingInfo
	 * @return
	 */
	private String getScheduleType(ISchedulingInfo schedulingInfo) {
		try {

			String resultValue = "";
			if (schedulingInfo != null) {
				if (schedulingInfo.properties().get("SI_SCHEDULE_TYPE") != null) {
					Log.debug("Schedule Type:" + schedulingInfo.properties().get("SI_SCHEDULE_TYPE"));
					switch (Integer.valueOf(schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString())) {
					case CeScheduleType.HOURLY:
						resultValue = String.format("%02d", schedulingInfo.getIntervalHours()) + ":" + String.format("%02d", schedulingInfo.getIntervalMinutes()) + " Hourly";
						break;
					case CeScheduleType.DAILY:
						resultValue = schedulingInfo.getIntervalDays() + " Daily";
						break;
					case CeScheduleType.MONTHLY:
						resultValue = schedulingInfo.getIntervalMonths() + " Monthly";
						break;
					case CeScheduleType.NTH_DAY:
						resultValue = schedulingInfo.getIntervalNthDay() + " th Day of Month";
						break;
					case CeScheduleType.ONCE:
						resultValue = "Once";
						break;
					case CeScheduleType.LAST_DAY:
						resultValue = "Last Day of Month";
						break;
					case CeScheduleType.CALENDAR_TEMPLATE:
						resultValue = "Calendar Template";
						break;
					case CeScheduleType.WEEKLY:
						resultValue = "Weekly";
						break;
					case CeScheduleType.CALENDAR:
						resultValue = "By Calendar: ";
						for (int i = 0; i < schedulingInfo.getCalendarRunDays().size(); i++) {
							ICalendarDay calDay = (ICalendarDay) schedulingInfo.getCalendarRunDays().get(0);
							resultValue += "(" + calDay.getStartMonth() + "/" + calDay.getStartDay() + "/" + calDay.getStartYear() + " - " + calDay.getEndMonth() + "/" + calDay.getEndDay() + "/"
									+ calDay.getEndYear() + "; Day Of Week - " + calDay.getDayOfWeek() + "; Week# " + calDay.getWeekNumber() + "), ";
						}
						if (resultValue.endsWith(STR_COMMA_SPACE))
							resultValue = StringUtils.removeEnd(resultValue, STR_COMMA_SPACE);
						break;
					default:
						resultValue = "Other" + schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString();
					}
				}
				return resultValue;
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return Type1WorkerThread.STR_N_A;
	}

	/**
	 * Lookup for Object Name by Id
	 * 
	 * @param cmsQuery
	 * @param infoStore
	 * @return
	 */
	private String getObjectName(String cmsQuery, IInfoStore infoStore) {
		try {
			Log.debug("Running CMS QUERY:" + cmsQuery);
			IInfoObject infoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
			return infoObject.getTitle();

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage() + "\n CMS Query:" + cmsQuery, ee);
		}
		return STR_N_A;
	}

	/**
	 * Returns Instance Duration
	 * 
	 * @param infoObject
	 * @return
	 */
	private String getDuration(IInfoObject infoObject) {
		try {

			Date dateStart;
			Date dateEnd;

			if (infoObject.getSchedulingInfo() != null) {
				if (infoObject.properties().get("SI_STARTTIME") != null)
					dateStart = getDateFromBOXIString(infoObject.properties().get("SI_STARTTIME").toString());
				else if (infoObject.getSchedulingInfo().properties().get("SI_STARTTIME") != null) {
					dateStart = getDateFromBOXIString(infoObject.getSchedulingInfo().properties().get("SI_STARTTIME").toString());
				} else {
					return "00:00:00";
				}

				Log.debug("Start Date:" + dateStart);
				if (infoObject.properties().get("SI_ENDTIME") != null)
					dateEnd = getDateFromBOXIString(infoObject.properties().get("SI_ENDTIME").toString());
				else if (infoObject.getSchedulingInfo().properties().get("SI_ENDTIME") != null) {
					dateEnd = getDateFromBOXIString(infoObject.getSchedulingInfo().properties().get("SI_ENDTIME").toString());
				} else {
					return "00:00:00";
				}
				Log.debug("End Date:" + dateEnd);

				String duration = DurationFormatUtils.formatDuration(dateEnd.getTime() - dateStart.getTime(), "HH:mm:ss");
				Log.debug("Formatted Duration:" + duration);
				return duration;
			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return "00:00:00";
	}

	/**
	 * Process Property Date
	 * 
	 * @param result
	 * @param properties
	 * @param key
	 * @param property
	 * @return
	 */
	private HashMap<String, String> getInfoPropertyDate(HashMap<String, String> result, IProperties properties, String key, String property) {
		try {
			if (properties.get(property) != null) {
				result.put(key, formatter.format(getDateFromBOXIString(properties.get(property).toString())));
				Log.debug("Propety:" + property + " Value:" + properties.get(property).toString() + " Stored Value:" + result.get(key));
			} else {
				Log.debug("Key " + key + " Not Found");
				result.put(key, STR_N_A);
			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put(key, STR_N_A);
		}
		return result;
	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param key
	 * @param property
	 * @return
	 */
	private HashMap<String, String> getInfoProcessingPropertyString(HashMap<String, String> result, IInfoObject infoObject, String key, String property) {
		try {

			if (infoObject.getProcessingInfo() != null) {

				if (infoObject.getProcessingInfo().properties() != null) {
					if (infoObject.getProcessingInfo().properties().get(property) != null) {
						if (StringUtils.isNotBlank(infoObject.getProcessingInfo().properties().get(property).toString())) {
							result.put(key, infoObject.getProcessingInfo().properties().get(property).toString());
							return result;
						}

					}
				}
			}
			Log.debug("Key " + key + " Not Found");
			result.put("Not Found", STR_N_A);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put("Not Found", STR_N_A);
		}
		return result;
	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param key
	 * @param property
	 * @return
	 */
	private HashMap<String, String> getInfoSchedulePropertyString(HashMap<String, String> result, IInfoObject infoObject, String key, String property) {
		try {

			if (infoObject.getSchedulingInfo() != null) {

				if (infoObject.getSchedulingInfo().properties() != null) {
					if (infoObject.getSchedulingInfo().properties().get(property) != null) {
						if (StringUtils.isNotBlank(infoObject.getSchedulingInfo().properties().get(property).toString())) {
							result.put(key, infoObject.getSchedulingInfo().properties().get(property).toString());
							return result;
						}

					}
				}
			}
			Log.debug("Key " + key + " Not Found");
			result.put("Not Found", STR_N_A);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put("Not Found", STR_N_A);
		}
		return result;
	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param property
	 * @return
	 */
	private int getInfoPropertyInteger(IProperties properties, String property) {
		try {

			if (properties != null) {
				if (properties.get(property) != null) {
					if (StringUtils.isNotBlank(properties.get(property).toString())) {
						return Integer.parseInt(properties.get(property).toString());
					}

				}
			}
			Log.debug("Property " + property + " Not Found");
			return -1;

		} catch (Exception ee) {
			return -1;
		}
	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param key
	 * @param property
	 * @return
	 */
	@SuppressWarnings("unused")
	private HashMap<String, String> getInfoPropertyBoolean(HashMap<String, String> result, IProperties properties, String key, String property) {
		try {

			if (properties != null) {
				if (properties.get(property) != null) {
					if (StringUtils.isNotBlank(properties.get(property).toString())) {
						if (properties.get(property).toString().equalsIgnoreCase("True"))
							result.put(key, "Yes");
						else
							result.put(key, "No");

						return result;
					}

				}
			}
			Log.debug("Key " + key + " Not Found");
			result.put("Not Found", STR_N_A);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put("Not Found", STR_N_A);
		}
		return result;
	}

	private boolean getBoolean(IProperties properties, String key, String property) {
		if (properties != null) {
			if (properties.get(property) != null) {
				if (StringUtils.isNotBlank(properties.get(property).toString())) {
					if (properties.get(property).toString().equalsIgnoreCase("True"))
						return true;
				}
			}
		}

		return false;

	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param key
	 * @param property
	 * @return
	 */
	private HashMap<String, String> getInfoScheduleStatus(HashMap<String, String> result, IProperties properties, String key, String property) {
		try {

			if (properties != null) {
				if (properties.get(property) != null) {
					switch (getScheduleStatus(properties, key, property)) {
					case ISchedulingInfo.ScheduleStatus.RUNNING:
						result.put(key, "Running");
						break;

					case ISchedulingInfo.ScheduleStatus.COMPLETE:
						result.put(key, "Success");
						break;

					case ISchedulingInfo.ScheduleStatus.FAILURE:
						result.put(key, "Failure");
						break;

					case ISchedulingInfo.ScheduleStatus.PAUSED:
						result.put(key, "Paused");
						break;

					case ISchedulingInfo.ScheduleStatus.PENDING:
						result.put(key, "Pending");
						break;

					default:
						result.put(key, STR_N_A);

					}

				}
			}
			Log.debug("Key " + key + " Not Found");
			result.put("Not Found", STR_N_A);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put("Not Found", STR_N_A);
		}
		return result;
	}

	private int getScheduleStatus(IProperties properties, String key, String property) {
		if (properties != null) {
			if (properties.get(property) != null) {
				return Integer.valueOf(properties.get(property).toString());
			}
		}

		return 0;

	}

	/**
	 * Process Property String
	 * 
	 * @param result
	 * @param infoObject
	 * @param key
	 * @param property
	 * @return
	 */
	private HashMap<String, String> getInfoPropertyString(HashMap<String, String> result, IProperties properties, String key, String property) {
		try {

			if (properties != null) {
				if (properties.get(property) != null) {
					if (StringUtils.isNotBlank(properties.get(property).toString())) {
						result.put(key, properties.get(property).toString());
						return result;
					}

				}
			}
			Log.debug("Key " + key + " Not Found");
			result.put("Not Found", STR_N_A);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			result.put("Not Found", STR_N_A);
		}
		return result;
	}

	/**
	 * Build Path To the Report
	 * 
	 * @param infoStore
	 * @param infoObject
	 * @param startPath
	 * @return
	 */
	public static StringBuffer getObjectPath(IInfoStore infoStore, IInfoObject infoObject, StringBuffer startPath) {
		StringBuffer path = new StringBuffer(startPath);

		try {
			if (infoObject.getParentID() > 0) {
				String cmsQuery = CMS_QUERY + infoObject.getParentID();
				IInfoObjects infoObjects = infoStore.query(cmsQuery);
				if (infoObjects.size() > 0) {
					infoObject = (IInfoObject) infoObjects.get(0);
					path.insert(0, "/" + infoObject.getTitle());
					path = getObjectPath(infoStore, infoObject, path);
				}
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return path;
	}

	/**
	 * Converts text BOXI date into java date
	 * 
	 * @param dateString
	 * @return
	 */
	public Date getDateFromBOXIString(String dateString) {

		if (StringUtils.isNotBlank(dateString)) {

			Log.debug("Create Date Started for:" + dateString + " Formatter:" + cmsDateformatter.toPattern());
			try {
				Date date = cmsDateformatter.parse(dateString);
				Log.debug("Date Set To:" + date.toString());
				return date;
			} catch (Exception e) {
				Log.error(e.getLocalizedMessage() + " :" + dateString);

			}

		}

		return null;
	}

}
