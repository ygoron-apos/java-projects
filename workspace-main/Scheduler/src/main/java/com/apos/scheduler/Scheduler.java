/**
 * 
 */
package com.apos.scheduler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.apos.infoobject.properties.BOXIConnection;
import com.apos.xml.generic.ScheduleJobs;
import com.apos.xml.generic.ScheduleSettings;

/**
 * @author Yuri Goron
 * 
 */
@Component("Scheduler")
public class Scheduler implements IService {

	private static final String[] baseHeader = { "RefId", "Status", "Schedule Type", "Schedule Type Code", "ReportId", "Report Title", "Instance Id", "Schedule Time", "ProgId", "ErrorCode" };

	private static final Logger Log = Logger.getLogger(Scheduler.class);
	private static final String STR_VERSION = "1.0.21"; // Fixed errors in
														// Decryption
	private boolean isCanceled;

	private CancelFileMonitorThread cancelFileMonitorThread;

	private ExecutorService executor;
	@Autowired
	private GenericObjectPool<BOXIConnection> pool;
	@Autowired
	private Jaxb2Marshaller jaxbMarshaller;
	public static ArrayBlockingQueue<HashMap<String, String>> outputQueue;
	private ICommandLineArgs commandLineArgs;
	public static List<String> headers = new ArrayList<String>(Arrays.asList(baseHeader));
	private static int INT_SUCCESS_COUNT = 0;
	private static int INT_FAILURE_COUNT = 0;

	public void execute(ICommandLineArgs commandLineArgs) {
		Log.info("Scheduler Version: " + STR_VERSION);
		Log.info("Number of Threads:" + commandLineArgs.getMaxBOXIConnections());
		Log.info("File With Serialized Session:" + commandLineArgs.getSerializedSessionFile());
		// String logonToken = commandLineArgs.getCmsToken();
		// Log.info("SDK Token:" + logonToken);
		this.commandLineArgs = commandLineArgs;
		long startTime = Calendar.getInstance().getTime().getTime();

		deleteSyncFile();
		startCancelThread();
		boolean isShutdown = false;
		try {
			outputQueue = new ArrayBlockingQueue<HashMap<String, String>>(ICommandLineArgs.INT_BUFFER_DEFAULT_CAPACITY);
			BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(commandLineArgs.getMaxBOXIConnections());
			RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
			executor = new ThreadPoolExecutor(commandLineArgs.getMaxBOXIConnections(), commandLineArgs.getMaxBOXIConnections(), 0L, TimeUnit.MILLISECONDS, blockingQueue, rejectedExecutionHandler);
			startOutputInserterThread(commandLineArgs.getLogFile());

			Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
			marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setMarshallerProperties(marshallerProperties);

			int lineCount = 0;
			lineCount++;
			List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
			Log.debug("Processign Schedule Type:" + commandLineArgs.getScheduleType().getDescription());

			switch (commandLineArgs.getScheduleType()) {
			case SCHEDULE_DEFAULT:
				Callable<Integer> worker = new ProcessorThreadType1("Thread " + lineCount, commandLineArgs, pool);
				futures.add(executor.submit(worker));
				break;

			case SCHEDULE_DEFAULT_AND_OVERRIDE:
				Callable<Integer> worker2 = new ProcessorThreadType2("Thread " + lineCount, commandLineArgs, pool, jaxbMarshaller);
				futures.add(executor.submit(worker2));
				break;

			case SCHEDULE_FROM_SETTINGS_IN_INSTANCE:
				FileInputStream inputStream = null;
				DataInputStream dataInputStream = null;
				BufferedReader bufferedReader = null;

				try {
					inputStream = new FileInputStream(commandLineArgs.getInstanceFile());
					dataInputStream = new DataInputStream(inputStream);
					bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
					String strLine;

					int instanceId = 0;
					while ((strLine = bufferedReader.readLine()) != null) {
						try {
							instanceId = Integer.valueOf(strLine);

						} catch (Exception ee) {
							Log.error("Error getting instance id:" + ee.getLocalizedMessage(), ee);
							continue;
						}
						Log.debug("Processing Instance id" + instanceId);
						Callable<Integer> worker3 = new ProcessorThreadType3("Thread " + lineCount, commandLineArgs, pool, jaxbMarshaller, instanceId);
						futures.add(executor.submit(worker3));

					}
				} finally {
					try {
						if (inputStream != null)
							inputStream.close();
					} catch (Exception ee) {
					}
					try {
						if (dataInputStream != null)
							dataInputStream.close();
					} catch (Exception ee) {
					}

					try {
						if (bufferedReader != null)
							bufferedReader.close();
					} catch (Exception ee) {
					}
				}

				break;

			case BATCH_SCHEDULING:
				StreamSource source = null;
				InputStream inputFileStream = null;
				try {
					inputFileStream = new FileInputStream(commandLineArgs.getCustomSettingsFile());
					// source = new StreamSource(new
					// File(commandLineArgs.getCustomSettingsFile()));
					// ScheduleJobs scheduleJobs = (ScheduleJobs)
					// jaxbMarshaller.unmarshal(source);
					ScheduleJobs scheduleJobs = (ScheduleJobs) jaxbMarshaller.unmarshal(new StreamSource(inputFileStream));
					if (inputFileStream != null) {
						inputFileStream.close();
						Log.debug("XMl Input Stream Closed!-0");
					}
					for (ScheduleSettings scheduleSettings : scheduleJobs.getScheduleSettings()) {
						Log.debug("Processing Schedule Settings with Id:" + scheduleSettings.getObjectId() + " Path:" + scheduleSettings.getObjectPath());
						Callable<Integer> worker6 = new ProcessorThreadType6("Thread " + lineCount, scheduleSettings, scheduleJobs.getAcceptType(), pool, jaxbMarshaller);
						futures.add(executor.submit(worker6));
					}
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
					HashMap<String, String> output = new HashMap<String, String>();
					output.put(Scheduler.headers.get(0), ProcessorThreadType1.STATUS_FAILURE + ":" + ee.getLocalizedMessage());
					Scheduler.outputQueue.put(output);
				} finally {

					if (inputFileStream != null) {
						inputFileStream.close();
						Log.debug("XMl Input Stream Closed!-1");
					}
					if (source != null)
						if (source.getInputStream() != null) {
							source.getInputStream().close();
							Log.debug("XMl Source Stream Closed!-1");
						}

				}

				break;
			default:
				break;
			}
			int futureCount = 0;
			for (Future<Integer> future : futures) {
				if (isCanceled)
					break;
				futureCount++;
				Log.debug("Returned from Thread " + futureCount + " Result:" + future.get());
				if (future.get() >= 0)
					INT_SUCCESS_COUNT++;
				else
					INT_FAILURE_COUNT++;
			}

			shutdown(startTime);
			isShutdown = true;

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (pool != null)
				try {
					pool.close();
				} catch (Exception e) {
					Log.debug(e.getLocalizedMessage(), e);
				}
			if (!isShutdown)
				shutdown(startTime);

		}

	}

	/**
	 * Shutdown all Threads
	 * 
	 * @param startTime
	 */
	private void shutdown(long startTime) {

		executor.shutdown();

		stopCancelThread();
		stopOutputInserterThread();
		long endTime = Calendar.getInstance().getTime().getTime();
		printStatistics(startTime, endTime);
		createCompletedFile(commandLineArgs);

	}

	public void deleteSyncFile() {
		Log.debug("Delete Sync File If Exists");
		try {
			File file = new File(commandLineArgs.getSyncFile());
			file.delete();
			Log.info("File:" + file.getAbsolutePath() + " Deleted");
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	public void startCancelThread() {
		try {
			Log.debug("Start Cancel Thread");

			cancelFileMonitorThread = new CancelFileMonitorThread(this, commandLineArgs.getCancelFile());
			cancelFileMonitorThread.start();
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	public void stopCancelThread() {
		try {
			Log.debug("Stop Cancel Thread");
			if (cancelFileMonitorThread != null)
				cancelFileMonitorThread.setStopped(true);
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	public void startOutputInserterThread(String fileName) {
		executor.execute(new OutputFileInserter(fileName, headers));
	}

	public void stopOutputInserterThread() {
		try {

			HashMap<String, String> stopJob = new HashMap<String, String>();
			stopJob.put("", null);
			outputQueue.put(stopJob);
			while (outputQueue.size() != 0) {
				Log.debug("Waiting Output Queue to finish. Current Size:" + outputQueue.size());
				try {
					Thread.sleep(2000);
					if (isCanceled)
						break;
				} catch (InterruptedException e) {
					Log.error(e.getLocalizedMessage(), e);
				}
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;

	}

	public void printStatistics(long startTime, long endTime) {
		Log.info("Scheduler Finished. Duration:" + (endTime - startTime));
		Log.info("Success Objects:" + INT_SUCCESS_COUNT);
		Log.info("Failed Objects:" + INT_FAILURE_COUNT);

	}

	public void createCompletedFile(ICommandLineArgs commandLineArgs) {
		Log.debug("Creating Completed File");
		try {
			File file = new File(commandLineArgs.getSyncFile());
			file.createNewFile();
			Log.info("File:" + file.getAbsolutePath() + " Created");
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

}
