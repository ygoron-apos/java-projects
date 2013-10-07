/**
 * 
 */
package com.apos.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.apos.infoobject.properties.BOXIConnection;
import com.apos.infoobject.properties.CommonConstants;
import com.apos.xml.generic.ScheduleSettings;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;

/**
 * @author Yuri Goron
 * 
 */
public class ProcessorThreadType6 implements Callable<Integer> {

	private static final Logger Log = Logger.getLogger(ProcessorThreadType6.class);
	private GenericObjectPool<BOXIConnection> pool;
	private String threadName;
	private Jaxb2Marshaller jaxb2Marshaller;
	private SimpleDateFormat formatter = new SimpleDateFormat(ProcessorThreadType1.CMS_DATE_FORMAT_SIMPLE);
	private ScheduleSettings xmlScheduleSettings;
	private List<String> accepts;

	public ProcessorThreadType6(String name, ScheduleSettings xmlScheduleSettings, List<String> accepts, GenericObjectPool<BOXIConnection> pool, Jaxb2Marshaller jaxb2Marshaller) {
		this.threadName = name;
		this.pool = pool;
		this.jaxb2Marshaller = jaxb2Marshaller;
		this.xmlScheduleSettings = xmlScheduleSettings;
		this.accepts = accepts;
	}

	public Integer call() throws Exception {
		BOXIConnection boxiConnection = null;
		HashMap<String, String> output = new HashMap<String, String>();
		IInfoObject objectsToShedule = null;
		IEnterpriseSession altEenterpriseSession = null;
		try {

			int instanceId = 0;
			try {
				boxiConnection = pool.borrowObject();
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				throw new Exception(ErrorCodes.STR_ERROR_LOGGING_IN_TO_THE_CMS);
			}
			Utils utils = new Utils();
			ScheduleUtils scheduleUtils = new ScheduleUtils();

			Log.info("Schedule Type 6. Processing Object:" + xmlScheduleSettings.getObjectId() + " Object Path:" + xmlScheduleSettings.getObjectPath());
			Log.debug("Thread " + threadName + " Started. User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName() + " Active Connections " + pool.getNumActive());

			int objectId = 0;
			if (xmlScheduleSettings.isIsUseObjectId()) {
				objectId = xmlScheduleSettings.getObjectId();
				Log.debug("Use Object Id:" + objectId);
			} else {
				try {
					Log.debug("Using Object Path:" + xmlScheduleSettings.getObjectPath());
					if (!xmlScheduleSettings.getObjectPath().startsWith("~")) {
						Log.debug("Normal Object Path");
						objectId = utils.getObjectIdFromPath(xmlScheduleSettings.getObjectPath(), boxiConnection.getInfoStore());
					} else {
						String objectName = xmlScheduleSettings.getObjectPath().substring(1);
						Log.debug("~: Searching for first report:" + objectName);
						String cmsQuery = "SELECT TOP 1 SI_ID FROM CI_INFOOBJECTS WHERE SI_INSTANCE = 0 AND SI_NAME = '" + objectName + "'";
						Log.debug("CMS Query:" + cmsQuery);
						objectId = ((IInfoObject) boxiConnection.getInfoStore().query(cmsQuery).get(0)).getID();
					}

				} catch (Exception ee) {
					throw new Exception(ErrorCodes.STR_COULD_NOT_FIND_REPORT + CommonConstants.STR_ERROR_INFO_SEPARATOR
							+ (xmlScheduleSettings.isIsUseObjectId() ? xmlScheduleSettings.getObjectId() : xmlScheduleSettings.getObjectPath()));

				}
			}
			Log.debug("Resulted Object ID:" + objectId);

			String cmsQuery;
			IInfoObjects infoObjects = null;
			if (xmlScheduleSettings.isAltUser() == false) {
				cmsQuery = "SELECT * FROM CI_INFOOBJECTS WHERE ( SI_PARENTID=" + objectId + " or SI_ID=" + objectId + ") AND SI_INSTANCE=0 ORDER by SI_COMPONENT";
				Log.debug("Running Query:" + cmsQuery);
				infoObjects = boxiConnection.getInfoStore().query(cmsQuery);
			} else {
				Log.debug("Scheduling using Alt User:" + xmlScheduleSettings.getAltUserName() + " Password:" + xmlScheduleSettings.getAltUserPassword() + " CMS Name:"
						+ boxiConnection.getEnterpriseSession().getCMSName() + " Auth Method:" + boxiConnection.getEnterpriseSession().getUserInfo().getAuthenMethod());
				try {
					altEenterpriseSession = CrystalEnterprise.getSessionMgr().logon(xmlScheduleSettings.getAltUserName(), xmlScheduleSettings.getAltUserPassword(),
							boxiConnection.getEnterpriseSession().getCMSName(), boxiConnection.getEnterpriseSession().getUserInfo().getAuthenMethod());

				} catch (Exception ee) {
					Log.error("Exception:" + ee.getLocalizedMessage(), ee);
					throw new Exception(ErrorCodes.STR_COULD_NOT_LOG_IN_USING_THE_ALTERNATIVE_USER + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getAltUserName());

				}

				IInfoStore infoStore = (IInfoStore) altEenterpriseSession.getService("", "InfoStore");

				try {

					cmsQuery = "SELECT * FROM CI_INFOOBJECTS WHERE ( SI_PARENTID=" + objectId + " or SI_ID=" + objectId + ") AND SI_INSTANCE=0 ORDER by SI_COMPONENT";
					Log.debug("Running Query:" + cmsQuery);
					infoObjects = infoStore.query(cmsQuery);

				} catch (Exception ee) {
					Log.error("Exception:" + ee.getLocalizedMessage(), ee);
					throw new Exception(ErrorCodes.STR_COULD_NOT_FIND_REPORT + CommonConstants.STR_ERROR_INFO_SEPARATOR
							+ (xmlScheduleSettings.isIsUseObjectId() ? xmlScheduleSettings.getObjectId() : xmlScheduleSettings.getObjectPath()));

				}

			}

			try {
				objectsToShedule = (IInfoObject) infoObjects.get(0);
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				throw new Exception(ErrorCodes.STR_COULD_NOT_FIND_REPORT + CommonConstants.STR_ERROR_INFO_SEPARATOR + xmlScheduleSettings.getObjectId());
			}

			Log.debug("Continue With creating XML from the instance and applying it to the schedule");

			int newInstanceId;
			try {
				newInstanceId = scheduleUtils.scheduleObject(boxiConnection.getInfoStore(), infoObjects, xmlScheduleSettings, accepts, jaxb2Marshaller);
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				throw new Exception(ee.getLocalizedMessage());
			}

			output.put(Scheduler.headers.get(0), String.valueOf(xmlScheduleSettings.getRefId()));
			output.put(Scheduler.headers.get(1), ProcessorThreadType1.STATUS_SUCCESS);
			output.put(Scheduler.headers.get(2), ScheduleType.BATCH_SCHEDULING.getDescription());
			output.put(Scheduler.headers.get(3), String.valueOf(ScheduleType.BATCH_SCHEDULING.getCode()));
			if (xmlScheduleSettings.isIsUseObjectId())
				output.put(Scheduler.headers.get(4), String.valueOf(xmlScheduleSettings.getObjectId()));
			else
				output.put(Scheduler.headers.get(4), String.valueOf(xmlScheduleSettings.getObjectPath()));

			output.put(Scheduler.headers.get(5), objectsToShedule.getTitle());
			output.put(Scheduler.headers.get(6), String.valueOf(newInstanceId));
			output.put(Scheduler.headers.get(7), formatter.format(Calendar.getInstance().getTime()));
			output.put(Scheduler.headers.get(8), objectsToShedule.getProgID());
			output.put(Scheduler.headers.get(9), ErrorCodes.STR_Scheduled);

			Scheduler.outputQueue.put(output);

			return instanceId;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			output.put(Scheduler.headers.get(0), String.valueOf(xmlScheduleSettings.getRefId()));
			output.put(Scheduler.headers.get(1), ProcessorThreadType1.STATUS_FAILURE + ":" + ee.getLocalizedMessage());
			output.put(Scheduler.headers.get(2), ScheduleType.BATCH_SCHEDULING.getDescription());
			output.put(Scheduler.headers.get(3), String.valueOf(ScheduleType.BATCH_SCHEDULING.getCode()));
			if (xmlScheduleSettings.isIsUseObjectId())
				output.put(Scheduler.headers.get(4), String.valueOf(xmlScheduleSettings.getObjectId()));
			else
				output.put(Scheduler.headers.get(4), String.valueOf(xmlScheduleSettings.getObjectPath()));
			output.put(Scheduler.headers.get(5), OutputFileInserter.STR_N_A);
			output.put(Scheduler.headers.get(6), OutputFileInserter.STR_N_A);
			output.put(Scheduler.headers.get(7), formatter.format(Calendar.getInstance().getTime()));

			output.put(Scheduler.headers.get(8), objectsToShedule != null ? objectsToShedule.getProgID() : "N/A");
			// output.put(Scheduler.headers.get(9),
			// ErrorCodes.STR_Error_Scheduling_Report);
			output.put(Scheduler.headers.get(9), ee.getLocalizedMessage());

			Scheduler.outputQueue.put(output);
		} finally {
			if (boxiConnection != null)
				pool.returnObject(boxiConnection);
			if (altEenterpriseSession != null)
				altEenterpriseSession.logoff();

		}

		return -1;
	}
}
