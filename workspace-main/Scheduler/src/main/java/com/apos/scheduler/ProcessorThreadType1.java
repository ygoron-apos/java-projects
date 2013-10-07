/**
 * 
 */
package com.apos.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.apos.infoobject.properties.BOXIConnection;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;

/**
 * @author Yuri Goron
 * 
 */
public class ProcessorThreadType1 implements Callable<Integer> {

	private static final Logger Log = Logger.getLogger(ProcessorThreadType1.class);
	private ICommandLineArgs commandLineArgs;
	private GenericObjectPool<BOXIConnection> pool;
	private String threadName;
	public static final String CMS_QUERY = "SELECT TOP 1 * FROM  CI_INFOOBJECTS  Where  SI_ID=";
	public static final String CMS_QUERY_DELETE_RECURRING = "SELECT * FROM CI_INFOOBJECTS WHERE SI_INSTANCE=1 AND SI_RECURRING=1 AND SI_PARENTID=";
	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILURE = "Failed";

	public static final String CMS_DATE_FORMAT_SIMPLE = "yyyy/MM/dd HH:mm:ss";
	private SimpleDateFormat formatter = new SimpleDateFormat(CMS_DATE_FORMAT_SIMPLE);

	public ProcessorThreadType1(String name, ICommandLineArgs commandLineArgs, GenericObjectPool<BOXIConnection> pool) {
		this.threadName = name;
		this.commandLineArgs = commandLineArgs;
		this.pool = pool;
	}

	public Integer call() throws Exception {
		BOXIConnection boxiConnection = null;
		HashMap<String, String> output = new HashMap<String, String>();
		try {

			boxiConnection = pool.borrowObject();
			Log.info("Processing Object:" + commandLineArgs.getObjectId() + " Schedule Type:" + commandLineArgs.getScheduleType().getCode() + ":" + commandLineArgs.getScheduleType().getDescription());
			Log.debug("Thread " + threadName + " Started. Processing " + commandLineArgs.getObjectId() + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName()
					+ " Active Connections " + pool.getNumActive());
			Log.debug("Processor Type 1 Started. Report Id:" + commandLineArgs.getObjectId());
			IInfoObjects infoObjects = boxiConnection.getInfoStore().query(CMS_QUERY + commandLineArgs.getObjectId());
			boxiConnection.getInfoStore().schedule(infoObjects);
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);
			int instanceId = Integer.parseInt(infoObject.properties().getProperty("SI_NEW_JOB_ID").toString());
			Log.info("Object " + commandLineArgs.getObjectId() + " with title:" + infoObject.getTitle() + " Scheduled. Instance Id:" + instanceId);

			if (commandLineArgs.isDeleteRecurring()) {
				IInfoObjects recurringObjects = boxiConnection.getInfoStore().query(CMS_QUERY_DELETE_RECURRING + commandLineArgs.getObjectId());
				for (int i = 0; i < recurringObjects.size(); i++) {
					IInfoObject objectToDelete = (IInfoObject) recurringObjects.get(i);
					objectToDelete.deleteNow();
					Log.debug("Recurring Object Id:" + objectToDelete.getID() + " Deleted");
				}
			}

			output.put(Scheduler.headers.get(0), "0");
			output.put(Scheduler.headers.get(1), STATUS_SUCCESS);
			output.put(Scheduler.headers.get(2), commandLineArgs.getScheduleType().getDescription());
			output.put(Scheduler.headers.get(3), String.valueOf(commandLineArgs.getScheduleType().getCode()));
			output.put(Scheduler.headers.get(4), String.valueOf(commandLineArgs.getObjectId()));
			output.put(Scheduler.headers.get(5), infoObject.getTitle());
			output.put(Scheduler.headers.get(6), String.valueOf(instanceId));
			output.put(Scheduler.headers.get(7), formatter.format(Calendar.getInstance().getTime()));
			Scheduler.outputQueue.put(output);
			return instanceId;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			output.put(Scheduler.headers.get(0), "0");
			output.put(Scheduler.headers.get(1), STATUS_FAILURE);
			output.put(Scheduler.headers.get(2), commandLineArgs.getScheduleType().getDescription());
			output.put(Scheduler.headers.get(3), String.valueOf(commandLineArgs.getScheduleType().getCode()));
			output.put(Scheduler.headers.get(4), String.valueOf(commandLineArgs.getObjectId()));
			output.put(Scheduler.headers.get(5), OutputFileInserter.STR_N_A);
			output.put(Scheduler.headers.get(6), OutputFileInserter.STR_N_A);
			output.put(Scheduler.headers.get(7), formatter.format(Calendar.getInstance().getTime()));
			Scheduler.outputQueue.put(output);
		} finally {
			if (boxiConnection != null)
				pool.returnObject(boxiConnection);

		}
		return -1;

	}

}
