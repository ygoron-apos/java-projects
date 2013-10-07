/**
 * 
 */
package com.apos.scheduler;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Callable;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.apos.infoobject.properties.BOXIConnection;
import com.apos.xml.generic.ScheduleSettings;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;

/**
 * @author Yuri Goron
 * 
 */
public class ProcessorThreadType2 implements Callable<Integer> {

	private static final Logger Log = Logger.getLogger(ProcessorThreadType2.class);
	private ICommandLineArgs commandLineArgs;
	private GenericObjectPool<BOXIConnection> pool;
	private String threadName;
	private Jaxb2Marshaller jaxb2Marshaller;
	private SimpleDateFormat formatter = new SimpleDateFormat(ProcessorThreadType1.CMS_DATE_FORMAT_SIMPLE);
	public static final String CMS_DATE_FORMAT_SCHEDULE = "yyyy-MM-dd HH:mm:ss";

	public ProcessorThreadType2(String name, ICommandLineArgs commandLineArgs, GenericObjectPool<BOXIConnection> pool, Jaxb2Marshaller jaxb2Marshaller) {
		this.threadName = name;
		this.commandLineArgs = commandLineArgs;
		this.pool = pool;
		this.jaxb2Marshaller = jaxb2Marshaller;
	}

	public Integer call() throws Exception {
		BOXIConnection boxiConnection = null;
		HashMap<String, String> output = new HashMap<String, String>();
		try {

			boxiConnection = pool.borrowObject();
			Log.info("Processing Object:" + commandLineArgs.getObjectId() + " Schedule Type:" + commandLineArgs.getScheduleType().getCode() + ":" + commandLineArgs.getScheduleType().getDescription());
			Log.debug("Thread " + threadName + " Started. Processing " + commandLineArgs.getObjectId() + " User:" + boxiConnection.getEnterpriseSession().getUserInfo().getUserName()
					+ " Active Connections " + pool.getNumActive());
			Log.debug("Processor Type 2 Started. Report Id:" + commandLineArgs.getObjectId());
			IInfoObjects infoObjects = boxiConnection.getInfoStore().query(ProcessorThreadType1.CMS_QUERY + commandLineArgs.getObjectId());
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			Source source = new StreamSource(new File(commandLineArgs.getCustomSettingsFile()));
//			InfoObject xmlInfoObject = (InfoObject) jaxb2Marshaller.unmarshal(source);
			ScheduleSettings xmlSettings = (ScheduleSettings) jaxb2Marshaller.unmarshal(source);
			StringWriter result = new StringWriter();
			Log.debug("UnMarshalling:");
			jaxb2Marshaller.marshal(xmlSettings, new StreamResult(result));
			Log.debug(result.toString());

			ScheduleUtils scheduleUtils = new ScheduleUtils();
//			int instanceId = utils.scheduleObject(boxiConnection.getInfoStore(), infoObjects, xmlInfoObject, jaxb2Marshaller);
			int instanceId = scheduleUtils.scheduleObject(boxiConnection.getInfoStore(), infoObjects, xmlSettings, null,jaxb2Marshaller);
			output.put(Scheduler.headers.get(0), String.valueOf(xmlSettings.getRefId()));
			output.put(Scheduler.headers.get(1), ProcessorThreadType1.STATUS_SUCCESS);
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
			output.put(Scheduler.headers.get(0), String.valueOf(0));
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
