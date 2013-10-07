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
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.apos.infoobject.properties.BOXIConnection;
import com.apos.infoobject.xml.InfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;

/**
 * @author Yuri Goron
 * 
 */
public class ProcessorThreadType3 implements Callable<Integer> {

	private static final Logger Log = Logger.getLogger(ProcessorThreadType3.class);
	private ICommandLineArgs commandLineArgs;
	private GenericObjectPool<BOXIConnection> pool;
	private String threadName;
	private Jaxb2Marshaller jaxb2Marshaller;
	private SimpleDateFormat formatter = new SimpleDateFormat(ProcessorThreadType1.CMS_DATE_FORMAT_SIMPLE);
	private int instanceId;

	public ProcessorThreadType3(String name, ICommandLineArgs commandLineArgs, GenericObjectPool<BOXIConnection> pool, Jaxb2Marshaller jaxb2Marshaller, int instanceId) {
		this.threadName = name;
		this.commandLineArgs = commandLineArgs;
		this.pool = pool;
		this.jaxb2Marshaller = jaxb2Marshaller;
		this.instanceId = instanceId;
	}

	public Integer call() throws Exception {
		BOXIConnection boxiConnection = null;
		HashMap<String, String> output = new HashMap<String, String>();

		try {

			boxiConnection = pool.borrowObject();
			Utils utils = new Utils();

			Log.info("Processing Object:" + commandLineArgs.getObjectId() + " Instance Id:" + instanceId + " Schedule Type:" + commandLineArgs.getScheduleType().getCode() + ":"
					+ commandLineArgs.getScheduleType().getDescription());
			Log.debug("Thread " + threadName + " Started. Processing " + commandLineArgs.getObjectId() + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName()
					+ " Active Connections " + pool.getNumActive());
			Log.debug("Processor Type 3 Started. Report Id:" + commandLineArgs.getObjectId() + " Instance File:" + commandLineArgs.getInstanceFile());
			IInfoObject instanceObject = (IInfoObject) boxiConnection.getInfoStore().query(ProcessorThreadType1.CMS_QUERY + instanceId).get(0);

			IInfoObjects infoObjects = boxiConnection.getInfoStore().query(ProcessorThreadType1.CMS_QUERY + commandLineArgs.getObjectId());
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			Log.debug("Continue With creating XML from the instance and applying it to the schedule");

			InfoObject xmlInstanceObject = utils.getXmlInfoObject(boxiConnection.getInfoStore(), instanceObject);
			int newInstanceId = utils.scheduleObjectTyp3Temp(boxiConnection.getInfoStore(), infoObjects, xmlInstanceObject, jaxb2Marshaller);

			output.put(Scheduler.headers.get(0), "0");
			output.put(Scheduler.headers.get(1), ProcessorThreadType1.STATUS_SUCCESS);
			output.put(Scheduler.headers.get(2), commandLineArgs.getScheduleType().getDescription());
			output.put(Scheduler.headers.get(3), String.valueOf(commandLineArgs.getScheduleType().getCode()));
			output.put(Scheduler.headers.get(4), String.valueOf(commandLineArgs.getObjectId()));
			output.put(Scheduler.headers.get(5), infoObject.getTitle());
			output.put(Scheduler.headers.get(6), String.valueOf(newInstanceId));
			output.put(Scheduler.headers.get(7), formatter.format(Calendar.getInstance().getTime()));
			Scheduler.outputQueue.put(output);

			return instanceId;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			output.put(Scheduler.headers.get(0), "0");
			output.put(Scheduler.headers.get(1), ProcessorThreadType1.STATUS_FAILURE);
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
