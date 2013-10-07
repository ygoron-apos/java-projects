/**
 * 
 */
package com.apos.infoobject.properties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.apos.infoobject.xml.DestinationDisk;
import com.apos.infoobject.xml.DestinationFtp;
import com.apos.infoobject.xml.DestinationManaged;
import com.apos.infoobject.xml.DestinationSmtp;
import com.apos.infoobject.xml.InfoObject;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.SchedulingInfo;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;
import com.crystaldecisions.sdk.occa.infostore.ICalendarDay;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.plugin.destination.ftp.IFTPOptions;
import com.crystaldecisions.sdk.plugin.destination.managed.IManagedOptions;
import com.crystaldecisions.sdk.plugin.destination.smtp.IAttachment;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;

/**
 * @author Yuri Goron
 *
 */
public class Type3WorkerThread implements Callable<Integer> {

	
	private static final Logger Log = Logger.getLogger(Type3WorkerThread.class);
	public static int INT_SUCCESS_COUNT = 0;
	public static int INT_FAILURE_COUNT = 0;
	private GenericObjectPool<BOXIConnection> pool;
	private static final String CMS_QUERY = "SELECT * FROM  CI_INFOOBJECTS  Where  SI_ID=";
	private int objectId;
	private String threadName;
	private static final String CMS_DATE_FORMAT_SIMPLE = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat formatter = new SimpleDateFormat(CMS_DATE_FORMAT_SIMPLE);

	public Type3WorkerThread(String name, ICommandLineArgs commandLineArgs, int objectId, GenericObjectPool<BOXIConnection> pool) {
		this.threadName = name;
		this.objectId = objectId;
		this.pool = pool;
	}

	@SuppressWarnings("unchecked")
	public Integer call() throws Exception {
		BOXIConnection boxiConnection = null;
		IInfoObject infoObject = null;
		try {

			boxiConnection = pool.borrowObject();
			Log.info("Processing Object:" + objectId);
			Log.debug("Thread Type 3" + threadName + " Started. Processing " + this.objectId + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName() + " Active Connections "
					+ pool.getNumActive());

			String cmsQuery = CMS_QUERY + objectId;
			Log.debug("Tryig to Run Query:" + cmsQuery);
			infoObject = (IInfoObject) boxiConnection.getInfoStore().query(cmsQuery).get(0);
			CommonThreadTools commonThreadTools = new CommonThreadTools();
			InfoObject xmlInfoObject = new InfoObject();
			
			xmlInfoObject.setSI_ID(infoObject.getID());
			xmlInfoObject.setSI_LOCATION(Type1WorkerThread.getObjectPath(boxiConnection.getInfoStore(), infoObject, new StringBuffer("")).toString());
			SchedulingInfo xmlSchedulingInfo = new SchedulingInfo();
			xmlSchedulingInfo.setBeginDate(formatter.format(infoObject.getSchedulingInfo().getBeginDate()));
			xmlSchedulingInfo.setType(infoObject.getSchedulingInfo().getType());
			xmlSchedulingInfo.setTypeText(getScheduleTypeText(infoObject.getSchedulingInfo()));
			xmlSchedulingInfo.setEndDate(formatter.format(infoObject.getSchedulingInfo().getEndDate()));
			
			if (infoObject.getSchedulingInfo().getDestinations() != null) {
				for (int i = 0; i < infoObject.getSchedulingInfo().getDestinations().size(); i++) {
					IDestination destination = (IDestination) infoObject.getSchedulingInfo().getDestinations().get(i);
					Log.debug("Destination:" + destination.getName());
					if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Managed")) {
						DestinationManaged xmlDestinationManaged = new DestinationManaged();
						Log.debug("Destination Managed");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) boxiConnection.getInfoStore().query(Type1WorkerThread.CMS_QUERY_MANAGED).get(0);
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
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) boxiConnection.getInfoStore().query(Type1WorkerThread.CMS_QUERY_DISK_UNMANAGED).get(0);
						destination.copyToPlugin(destinationPlugin);
						IDiskUnmanagedOptions scheduleOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();
						if (scheduleOptions.getDestinationFiles().size() > 0)
							xmlDestinationDisk.setOutputFileName(scheduleOptions.getDestinationFiles().get(0).toString());
						xmlDestinationDisk.setUserName(scheduleOptions.getUserName());
						xmlSchedulingInfo.setDestinationDiskUnManaged(xmlDestinationDisk);

					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Smtp")) {

						DestinationSmtp xmlDestinationSmtp = new DestinationSmtp();
						Log.debug("Destination Smtp");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) boxiConnection.getInfoStore().query(Type1WorkerThread.CMS_QUERY_SMTP).get(0);
						destination.copyToPlugin(destinationPlugin);
						ISMTPOptions smtpOptions = (ISMTPOptions) destinationPlugin.getScheduleOptions();
						if (smtpOptions.getAttachments() != null) {
							String result = "";
							for (int j = 0; j < smtpOptions.getAttachments().size(); j++) {
								IAttachment attachment = (IAttachment) smtpOptions.getAttachments().get(j);
								result += attachment.getEmbedName() + ",";
							}
							if (result.endsWith(","))
								result = result.substring(0, result.length() - 1);
							xmlDestinationSmtp.setAttachmentName(result);
						}
						xmlDestinationSmtp.setCC(commonThreadTools.stringFromArray(smtpOptions.getCCAddresses()));
						if (smtpOptions.getSenderAddress() != null)
							xmlDestinationSmtp.setFrom(smtpOptions.getSenderAddress());

						if (smtpOptions.getMessage() != null)
							xmlDestinationSmtp.setMessage(smtpOptions.getMessage());

						if (smtpOptions.getSubject() != null)
							xmlDestinationSmtp.setSubject(smtpOptions.getSubject());

						xmlDestinationSmtp.setTo(commonThreadTools.stringFromArray(smtpOptions.getToAddresses()));
						xmlSchedulingInfo.setDestinationSmtp(xmlDestinationSmtp);

					} else if (destination.getName().equalsIgnoreCase("CrystalEnterprise.Ftp")) {

						DestinationFtp xmlDestinationFtp = new DestinationFtp();

						Log.debug("Destination Ftp");
						IDestinationPlugin destinationPlugin = (IDestinationPlugin) boxiConnection.getInfoStore().query(Type1WorkerThread.CMS_QUERY_FTP).get(0);
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
			Extractor.type3CsvOutputQueue.put(xmlInfoObject);
			INT_SUCCESS_COUNT++;
			return 0;
		} catch (Exception ee) {
			if (infoObject != null)
				Log.error(ee.getLocalizedMessage() + " for Object id:" + infoObject.getID() + " name:" + infoObject.getTitle());
			Log.error(ee.getLocalizedMessage(), ee);
			INT_FAILURE_COUNT++;
		} finally {
			if (boxiConnection != null)
				pool.returnObject(boxiConnection);

		}
		return -1;

	}
	
	
	/**
	 * Gets String for schedule type
	 * @return
	 */
	private String getScheduleTypeText(ISchedulingInfo schedulingInfo){
		String resultValue="";
		
		Log.debug("Schedule Type:" + schedulingInfo.properties().get("SI_SCHEDULE_TYPE"));
		switch (Integer.valueOf(schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString())) {
		case CeScheduleType.HOURLY:
			resultValue = String.format("%02d", schedulingInfo.getIntervalHours()) + ":" + String.format("%02d", schedulingInfo.getIntervalMinutes()) + " Hourly";
			break;
		case CeScheduleType.DAILY:
			resultValue =  schedulingInfo.getIntervalDays() + "Daily";
			break;
		case CeScheduleType.MONTHLY:
			resultValue = schedulingInfo.getIntervalMonths() + "Monthly";
			break;
		case CeScheduleType.NTH_DAY:
			resultValue = schedulingInfo.getIntervalNthDay() + "th Day of Month";
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
		default:
			resultValue = "Other" + schedulingInfo.properties().get("SI_SCHEDULE_TYPE").toString();
		}
		
		return resultValue;
	}

}
