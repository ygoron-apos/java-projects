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
import com.apos.infoobject.xml.Notification;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.SchedulingInfo;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.INotifications;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.plugin.destination.ftp.IFTPOptions;
import com.crystaldecisions.sdk.plugin.destination.managed.IManagedOptions;
import com.crystaldecisions.sdk.plugin.destination.smtp.IAttachment;
import com.crystaldecisions.sdk.plugin.destination.smtp.ISMTPOptions;

/**
 * @author Yuri Goron
 * 
 */
public class Type2WorkerThread implements Callable<Integer> {

	private static final Logger Log = Logger.getLogger(Type2WorkerThread.class);
	public static int INT_SUCCESS_COUNT = 0;
	public static int INT_FAILURE_COUNT = 0;
	private GenericObjectPool<BOXIConnection> pool;
	private static final String CMS_QUERY = "SELECT * FROM  CI_INFOOBJECTS  Where  SI_ID=";
	private int objectId;
	private String threadName;
	public static final String CMS_DATE_FORMAT_SIMPLE = "yyyy-MM-dd HH:mm:ss";
	public static SimpleDateFormat formatter = new SimpleDateFormat(CMS_DATE_FORMAT_SIMPLE);

	public Type2WorkerThread(String name, ICommandLineArgs commandLineArgs, int objectId, GenericObjectPool<BOXIConnection> pool) {
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
			Log.debug("Thread Type 2" + threadName + " Started. Processing " + this.objectId + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName() + " Active Connections "
					+ pool.getNumActive());

			String cmsQuery = CMS_QUERY + objectId;
			Log.debug("Tryig to Run Query:" + cmsQuery);
			infoObject = (IInfoObject) boxiConnection.getInfoStore().query(cmsQuery).get(0);

			CommonThreadTools commonThreadTools = new CommonThreadTools();

			InfoObject xmlInfoObject = new InfoObject();
			xmlInfoObject.setSI_ID(infoObject.getID());
			xmlInfoObject.setApos("");
			xmlInfoObject.setCluster(boxiConnection.getEnterpriseSession().getClusterName());
			xmlInfoObject.setSI_LOCATION(Type1WorkerThread.getObjectPath(boxiConnection.getInfoStore(), infoObject, new StringBuffer("")).toString());
			xmlInfoObject.setSI_PARENTID(infoObject.getParentID());
			SchedulingInfo xmlSchedulingInfo = new SchedulingInfo();
			xmlSchedulingInfo.setBeginDate(formatter.format(infoObject.getSchedulingInfo().getBeginDate()));

			List<Integer> dependants = new ArrayList<Integer>();
			for (int i = 0; i < infoObject.getSchedulingInfo().getDependants().size(); i++) {
				try {
					Integer eventId = (Integer) infoObject.getSchedulingInfo().getDependants().get(i);
					dependants.add(eventId);
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
				}
			}
			xmlSchedulingInfo.setDependants(dependants);

			List<Integer> dependencies = new ArrayList<Integer>();
			for (int i = 0; i < infoObject.getSchedulingInfo().getDependencies().size(); i++) {
				try {
					Integer eventId = (Integer) infoObject.getSchedulingInfo().getDependencies().get(i);
					dependencies.add(eventId);
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
				}

			}
			xmlSchedulingInfo.setDependencies(dependencies);

			xmlSchedulingInfo.setEndDate(formatter.format(infoObject.getSchedulingInfo().getEndDate()));
			xmlSchedulingInfo.setInstanceName(infoObject.getTitle());
			xmlSchedulingInfo.setIntervalMonths(infoObject.getSchedulingInfo().getIntervalMonths());
			xmlSchedulingInfo.setIntervalDays(infoObject.getSchedulingInfo().getIntervalDays());
			xmlSchedulingInfo.setIntervalMinutes(infoObject.getSchedulingInfo().getIntervalMinutes());
			xmlSchedulingInfo.setIntervalMinutes(infoObject.getSchedulingInfo().getIntervalNthDay());

			xmlSchedulingInfo.setRetriesAllowed(infoObject.getSchedulingInfo().getRetriesAllowed());
			xmlSchedulingInfo.setRetryInterval(infoObject.getSchedulingInfo().getRetryInterval());
			xmlSchedulingInfo.setRightNow(infoObject.getSchedulingInfo().isRightNow());
			xmlSchedulingInfo.setScheduleOnBehalfOf(infoObject.getSchedulingInfo().getScheduleOnBehalfOf());
			xmlSchedulingInfo.setType(infoObject.getSchedulingInfo().getType());

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
				if (xmlPluginInterface.getReportLogons().size() > 0)
					Log.debug("Table Prefix Size:" + xmlPluginInterface.getReportLogons().get(0).getTablePrefixes().size());
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

			xmlSchedulingInfo.setNotification(getNotification(infoObject, boxiConnection.getInfoStore(), commonThreadTools));
			if (xmlSchedulingInfo.getNotification().getEmailNotificationSuccess() != null)
				Log.debug("SMTP To:" + xmlSchedulingInfo.getNotification().getEmailNotificationSuccess().getTo());

			// if (infoObject.getKind().equalsIgnoreCase("Webi") ||
			// infoObject.getKind().equalsIgnoreCase("FullClient")) {
			// PluginInterfaceWebi xmlInterfaceWebi =
			// getPluginInterfaceWebi(infoObject);
			// xmlInfoObject.setPluginInterfaceWebi(xmlInterfaceWebi);
			//
			// } else {
			// PluginInterface xmlPluginInterface =
			// getPluginInterface(infoObject);
			// xmlInfoObject.setPluginInterface(xmlPluginInterface);
			//
			// }

			xmlInfoObject.setSchedulingInfo(xmlSchedulingInfo);
			Extractor.type2XmlOutputQueue.put(xmlInfoObject);
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

	private Notification getNotification(IInfoObject infoObjectTemplate, IInfoStore infoStore, CommonThreadTools commonThreadTools) {
		Notification xmlNotification = null;
		try {
			if (infoObjectTemplate.getSchedulingInfo().getNotifications() != null) {
				Log.debug("Proceed with Notifications");
				int auditOption = infoObjectTemplate.getSchedulingInfo().getNotifications().getAuditOption();

				xmlNotification = new Notification();
				xmlNotification.setAuditNotificationOptionCode(auditOption);
				Log.debug("Audit Option:" + auditOption);

				switch (auditOption) {
				case INotifications.CeAuditOnResult.BOTH:
					xmlNotification.setAuditNotificationOption(CommonConstants.STR_AUDIT_RESULT_BOTH);
					Log.debug("Audit BOTH");
					break;
				case INotifications.CeAuditOnResult.FAILURE:
					xmlNotification.setAuditNotificationOption(CommonConstants.STR_AUDIT_RESULT_FAILURE);
					Log.debug("Audit FAILURE");
					break;
				case INotifications.CeAuditOnResult.NONE:
					xmlNotification.setAuditNotificationOption(CommonConstants.STR_SMTP_AUTH_TYPE_NONE);
					Log.debug("Audit None");
					break;
				case INotifications.CeAuditOnResult.SUCCESS:
					xmlNotification.setAuditNotificationOption(CommonConstants.STR_AUDIT_RESULT_SUCCESS);
					Log.debug("Audit SUCCESS");
					break;
				default:
					xmlNotification.setAuditNotificationOption(CommonConstants.STR_SMTP_AUTH_TYPE_NONE);
					Log.debug("Audit NONE");
				}

				if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure() != null) {
					Log.debug("Size:" + infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().size());
					if (infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().size() > 0) {
						IDestination destination = (IDestination) infoObjectTemplate.getSchedulingInfo().getNotifications().getDestinationsOnFailure().get(0);
						Log.debug("Destination:" + destination.getName());

						if (destination.getName().equalsIgnoreCase(CommonConstants.STR_DESTINATION_SMTP)) {
							DestinationSmtp xmlDestinationSmtp = commonThreadTools.getDestinationSmtp(destination, infoStore);
							xmlNotification.setEmailNotificationFailure(xmlDestinationSmtp);
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
							xmlNotification.setEmailNotificationSuccess(xmlDestinationSmtp);
						}
					}

				}

			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		return xmlNotification;
	}
}
