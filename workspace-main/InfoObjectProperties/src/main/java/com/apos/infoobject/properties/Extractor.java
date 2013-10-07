/**
 * 
 */
package com.apos.infoobject.properties;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apos.infoobject.xml.InfoObject;

/**
 * @author Yuri Goron
 * 
 */
@Component("PropertyExtractor")
public class Extractor implements IService {

	private static final Logger Log = Logger.getLogger(Extractor.class);
	private static final String STR_VERSION = "1.0.24"; // Add Excel, Word, RTF formats
	public static int BI_VERSION = 3;
	// Changed to serialized session
	// "Status","Submitter","Job Server Name","Creation Time","Update Time","Start Time","End Time","Duration","Next Run Time","Result File Name","Flags","Original File Name","Job Description","Job Title","Instance Kind","Object Type","Schedule Type","Dependants - ID","Dependants - Name","Dependencies - ID","Dependencies - Name","Destination","Processing Security Extensions","Format","Server Group","Server Group Choice","Error Message","Group Selection Formula","Record Selection Formula","ID","Number of Alerts","Is Recurring","Locale","Num Retries Allowed","Retry Interval In Min","Parent ID","Location","Component","CUID","Printer","APOSNotification","APOSDestinationEMAIL","APOSDestinationDelimiterEMAIL","APOSEmailSubjectEMAIL","APOSEmailCCEMAIL","APOSEmailBCCEMAIL","APOSEmailMessageEMAIL","APOSEmailFileNameEMAIL","APOSFormatEMAIL","APOSFormatPasswordEMAIL","APOSEditPasswordEMAIL","APOSPDFEditOptionsEMAIL","APOSCompressFilesUsingZIPEMAIL","APOSZIPPasswordEMAIL","APOSCRCOMKeepHistoryEMAIL","APOSDoNotDistributeEmptyReportEMAIL","APOSDestinationCONTENT","APOSDestinationDelimiterCONTENT","APOSEmailSubjectCONTENT","APOSEmailCCCONTENT","APOSEmailBCCCONTENT","APOSFormatCONTENT","APOSDoNotDistributeEmptyReportCONTENT","APOSDestinationFTP","APOSDestinationDelimiterFTP","APOSFormatFTP","APOSFormatPasswordFTP","APOSEditPasswordFTP","APOSPDFEditOptionsFTP","APOSCompressFilesUsingZIPFTP","APOSZIPPasswordFTP","APOSDoNotDistributeEmptyReportFTP","APOSDestinationNETWORK","APOSDestinationDelimiterNETWORK","APOSFormatNETWORK","APOSFormatPasswordNETWORK","APOSEditPasswordNETWORK","APOSPDFEditOptionsNETWORK","APOSCompressFilesUsingZIPNETWORK","APOSZIPPasswordNETWORK","APOSDoNotDistributeEmptyReportNETWORK","APOSDestinationPRINTER","APOSDestinationDelimiterPRINTER","APOSDoNotDistributeEmptyReportPRINTER","APOSDestinationCRCOM","APOSDestinationDelimiterCRCOM","APOSFormatCRCOM","APOSFormatPasswordCRCOM","APOSEditPasswordCRCOM","APOSPDFEditOptionsCRCOM","APOSCRCOMKeepHistoryCRCOM","APOSDoNotDistributeEmptyReportCRCOM","APOSCRCOMUserNameOverrideCRCOM","APOSCRCOMPasswordOverrideCRCOM","APOSCompressFilesUsingZIPCRCOM","APOSZIPPasswordCRCOM","Server Name 1","Database Name 1","User Name 1","Server Name 2","Database Name 2","User Name 2","Server Name 3","Database Name 3","User Name 3","Server Name 4","Database Name 4","User Name 4","Server Name 5","Database Name 5","User Name 5","Parameter Name 1","Parameter Value 1","Parameter Name 2","Parameter Value 2","Parameter Name 3","Parameter Value 3","Parameter Name 4","Parameter Value 4","Parameter Name 5","Parameter Value 5","Parameter Name 6","Parameter Value 6","Parameter Name 7","Parameter Value 7","Parameter Name 8","Parameter Value 8","Parameter Name 9","Parameter Value 9","Parameter Name 10","Parameter Value 10","Parameter Name 11","Parameter Value 11","Parameter Name 12","Parameter Value 12","Parameter Name 13","Parameter Value 13","Parameter Name 14","Parameter Value 14","Parameter Name 15","Parameter Value 15","Parameter Name 16","Parameter Value 16","Parameter Name 17","Parameter Value 17","Parameter Name 18","Parameter Value 18","Parameter Name 19","Parameter Value 19","Parameter Name 20","Parameter Value 20","Parameter Name 21","Parameter Value 21","Parameter Name 22","Parameter Value 22","Parameter Name 23","Parameter Value 23","Parameter Name 24","Parameter Value 24","Parameter Name 25","Parameter Value 25","Parameter Name 26","Parameter Value 26","Parameter Name 27","Parameter Value 27","Parameter Name 28","Parameter Value 28","Parameter Name 29","Parameter Value 29","Parameter Name 30","Parameter Value 30","Parameter Name 31","Parameter Value 31","Parameter Name 32","Parameter Value 32","Parameter Name 33","Parameter Value 33","Parameter Name 34","Parameter Value 34","Parameter Name 35","Parameter Value 35","Parameter Name 36","Parameter Value 36","Parameter Name 37","Parameter Value 37","Parameter Name 38","Parameter Value 38","Parameter Name 39","Parameter Value 39","Parameter Name 40","Parameter Value 40","Parameter Name 41","Parameter Value 41","Parameter Name 42","Parameter Value 42","Parameter Name 43","Parameter Value 43","Parameter Name 44","Parameter Value 44","Parameter Name 45","Parameter Value 45","Parameter Name 46","Parameter Value 46","Parameter Name 47","Parameter Value 47","Parameter Name 48","Parameter Value 48","Parameter Name 49","Parameter Value 49","Parameter Name 50","Parameter Value 50"
	private static final String[] baseHeader = { "Status", "Submitter", "Job Server Name", "Creation Time", "Update Time", "Start Time", "End Time", "Duration", "Next Run Time", "Result File Name",
			"Flags", "Original File Name", "Job Description", "Job Title", "Instance Kind", "Object Type", "Schedule Type", "Dependants - ID", "Dependants - Name", "Dependencies - ID",
			"Dependencies - Name", "Destination", "Processing Security Extensions", "Format", "Server Group", "Server Group Choice", "Error Message", "Group Selection Formula",
			"Record Selection Formula", "ID", "Number of Alerts", "Is Recurring", "Locale", "Num Retries Allowed", "Retry Interval In Min", "Parent ID", "Location", "Component", "CUID", "Printer",
			"APOSNotification", "APOSDestinationEMAIL", "APOSDestinationDelimiterEMAIL", "APOSEmailSubjectEMAIL", "APOSEmailCCEMAIL", "APOSEmailBCCEMAIL", "APOSEmailMessageEMAIL",
			"APOSEmailFileNameEMAIL", "APOSFormatEMAIL", "APOSFormatPasswordEMAIL", "APOSEditPasswordEMAIL", "APOSPDFEditOptionsEMAIL", "APOSCompressFilesUsingZIPEMAIL", "APOSZIPPasswordEMAIL",
			"APOSCRCOMKeepHistoryEMAIL", "APOSDoNotDistributeEmptyReportEMAIL", "APOSDestinationCONTENT", "APOSDestinationDelimiterCONTENT", "APOSEmailSubjectCONTENT", "APOSEmailCCCONTENT",
			"APOSEmailBCCCONTENT", "APOSFormatCONTENT", "APOSDoNotDistributeEmptyReportCONTENT", "APOSDestinationFTP", "APOSDestinationDelimiterFTP", "APOSFormatFTP", "APOSFormatPasswordFTP",
			"APOSEditPasswordFTP", "APOSPDFEditOptionsFTP", "APOSCompressFilesUsingZIPFTP", "APOSZIPPasswordFTP", "APOSDoNotDistributeEmptyReportFTP", "APOSDestinationNETWORK",
			"APOSDestinationDelimiterNETWORK", "APOSFormatNETWORK", "APOSFormatPasswordNETWORK", "APOSEditPasswordNETWORK", "APOSPDFEditOptionsNETWORK", "APOSCompressFilesUsingZIPNETWORK",
			"APOSZIPPasswordNETWORK", "APOSDoNotDistributeEmptyReportNETWORK", "APOSDestinationPRINTER", "APOSDestinationDelimiterPRINTER", "APOSDoNotDistributeEmptyReportPRINTER",
			"APOSDestinationCRCOM", "APOSDestinationDelimiterCRCOM", "APOSFormatCRCOM", "APOSFormatPasswordCRCOM", "APOSEditPasswordCRCOM", "APOSPDFEditOptionsCRCOM", "APOSCRCOMKeepHistoryCRCOM",
			"APOSDoNotDistributeEmptyReportCRCOM", "APOSCRCOMUserNameOverrideCRCOM", "APOSCRCOMPasswordOverrideCRCOM", "APOSCompressFilesUsingZIPCRCOM", "APOSZIPPasswordCRCOM" };

	// @Autowired
	// private ThreadPoolTaskExecutor executor;

	private ExecutorService executor;
	@Autowired
	private GenericObjectPool<BOXIConnection> pool;
	@Autowired
	private EmailProcessor emailProcessor;

	public static ArrayBlockingQueue<HashMap<String, String>> type1CsvOtputQueue;
	public static ArrayBlockingQueue<InfoObject> type2XmlOutputQueue;
	public static ArrayBlockingQueue<InfoObject> type3CsvOutputQueue;

	private String objectsFile;
	private String cancelFile;
	private String syncFile;
	private int resumeId;
	private CancelFileMonitorThread cancelFileMonitorThread;
	private List<String> headers = new ArrayList<String>(Arrays.asList(baseHeader));
	private ICommandLineArgs commandLineArgs;

	private boolean isCanceled;

	public void execute(ICommandLineArgs commandLineArgs) {
		Log.info("Property Extractor Version: " + STR_VERSION);
		Log.info("Number of Threads:" + commandLineArgs.getMaxBOXIConnections());
		Log.info("File With Serialized Session:" + commandLineArgs.getSerializedSessionFile());
		this.commandLineArgs = commandLineArgs;
		long startTime = Calendar.getInstance().getTime().getTime();

		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(commandLineArgs.getMaxBOXIConnections());
		RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
		executor = new ThreadPoolExecutor(commandLineArgs.getMaxBOXIConnections(), commandLineArgs.getMaxBOXIConnections(), 0L, TimeUnit.MILLISECONDS, blockingQueue, rejectedExecutionHandler);

		// executor.setCorePoolSize(commandLineArgs.getMaxBOXIConnections());

		deleteSyncFile();
		startCancelThread();
		FileInputStream inputStream = null;
		DataInputStream dataInputStream = null;
		BufferedReader bufferedReader = null;

		try {
			type1CsvOtputQueue = new ArrayBlockingQueue<HashMap<String, String>>(ICommandLineArgs.INT_BUFFER_DEFAULT_CAPACITY);
			type2XmlOutputQueue = new ArrayBlockingQueue<InfoObject>(ICommandLineArgs.INT_BUFFER_DEFAULT_CAPACITY);
			type3CsvOutputQueue = new ArrayBlockingQueue<InfoObject>(ICommandLineArgs.INT_BUFFER_DEFAULT_CAPACITY);

			Log.debug("Apppending Base Header. Max Logons:" + commandLineArgs.getMaxLogons() + " Max Parameters:" + commandLineArgs.getMaxParameters());

			for (int i = 0; i < commandLineArgs.getMaxLogons(); i++) {
				headers.add("Server Name " + (i + 1));
				headers.add("Database Name " + (i + 1));
				headers.add("User Name " + (i + 1));
			}
			for (int i = 0; i < commandLineArgs.getMaxParameters(); i++) {
				headers.add("Parameter Name " + (i + 1));
				headers.add("Parameter Value " + (i + 1));
			}
			Log.debug("Export Type:" + commandLineArgs.getExportType());
			Log.debug("Code:" + commandLineArgs.getExportType().getCode());
			if (commandLineArgs.getExportType().getCode() == ExportType.EXPORT_CSV_IM.getCode()) {
				Log.debug("Start Thread Type 1 for file:" + commandLineArgs.getLogFile());
				startType1OutputInserterThread(commandLineArgs.getLogFile());
			} else if (commandLineArgs.getExportType().getCode() == ExportType.EXPORT_XML_IS.getCode()) {
				Log.debug("Start Thread Type 2 for file:" + commandLineArgs.getLogFile());
				startType2OutputInserterThread(commandLineArgs.getLogFile());
			}
			else if (commandLineArgs.getExportType().getCode() == ExportType.EXPORT_CSV_IS.getCode()) {
				Log.debug("Start Thread Type 3 for file:" + commandLineArgs.getLogFile());
				startType3OutputInserterThread(commandLineArgs.getLogFile());
			}

			inputStream = new FileInputStream(objectsFile);
			dataInputStream = new DataInputStream(inputStream);
			bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			String strLine;

			List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
			int lineCount = 0;

			while ((strLine = bufferedReader.readLine()) != null) {
				if (isCanceled) {
					Log.warn("Cancel Submiting Jobs");
					break;
				}

				int documentId = 0;
				try {
					documentId = Integer.valueOf(strLine);
					if (documentId > resumeId) {
						lineCount++;
						if (commandLineArgs.getExportType().equals(ExportType.EXPORT_CSV_IM)) {
							Callable<Integer> worker = new Type1WorkerThread("Thread " + lineCount, commandLineArgs, documentId, pool, headers);
							futures.add(executor.submit(worker));
						} else if (commandLineArgs.getExportType().equals(ExportType.EXPORT_XML_IS)) {
							Callable<Integer> worker = new Type2WorkerThread("Thread " + lineCount, commandLineArgs, documentId, pool);
							futures.add(executor.submit(worker));
						} else if (commandLineArgs.getExportType().equals(ExportType.EXPORT_CSV_IS)) {
							Callable<Integer> worker = new Type3WorkerThread("Thread " + lineCount, commandLineArgs, documentId, pool);
							futures.add(executor.submit(worker));
						}

						Log.debug("Object " + documentId + " Submited");
					}
				} catch (Exception e) {
					Log.error(e.getLocalizedMessage(), e);
				}
			}

			int futureCount = 0;
			for (Future<Integer> future : futures) {
				if (isCanceled) {
					Log.warn("Cancel Wating for results");
					break;
				}
				futureCount++;
				Log.debug("Returned from Thread " + futureCount + " Result:" + future.get());
			}

			if (isCanceled) {
				Log.info("Clearing Job Queue. Number Jobs in the Queue:" + Extractor.type1CsvOtputQueue.size());
				Extractor.type1CsvOtputQueue.clear();
				Extractor.type2XmlOutputQueue.clear();
				Extractor.type3CsvOutputQueue.clear();
			}
			stopCancelThread();
			stopOutputInserterThread();
			long endTime = Calendar.getInstance().getTime().getTime();
			printStatistics(startTime, endTime);

			createCompletedFile(commandLineArgs);

			if (commandLineArgs.isSendFinishedEmail()) {
				Log.debug("Sending Email");

				String emailMessage = "Successful Documents Scanned: " + Type1WorkerThread.INT_SUCCESS_COUNT + "\n";

				emailMessage += "Failed Documents: " + Type1WorkerThread.INT_SUCCESS_COUNT + "\n\n";

				emailProcessor.setConfigurationSettings(commandLineArgs);
				emailProcessor.setEmailText(emailMessage);
				if (emailProcessor.sendEmail()) {
					Log.info("Email Sent");
				} else {
					Log.error("Error Sending Email");
				}
			}

			executor.shutdown();

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (pool != null)
				try {
					pool.close();
				} catch (Exception e) {
					Log.debug(e.getLocalizedMessage(), e);
				}
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

		Log.info("Completed!");

	}

	public void deleteSyncFile() {
		Log.debug("Delete Sync File If Exists");
		try {
			File file = new File(this.syncFile);
			file.delete();
			Log.info("File:" + file.getAbsolutePath() + " Deleted");
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

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

	public void startCancelThread() {
		try {
			Log.debug("Start Cancel Thread");

			cancelFileMonitorThread = new CancelFileMonitorThread(this, this.cancelFile);
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

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;

	}

	public void printStatistics(long startTime, long endTime) {
		Log.info("Export Properties Finished. Duration:" + (endTime - startTime));
		Log.info("Success Objects:" + (Type1WorkerThread.INT_SUCCESS_COUNT + Type2WorkerThread.INT_SUCCESS_COUNT));
		Log.info("Failed Objects:" + (Type1WorkerThread.INT_FAILURE_COUNT + Type2WorkerThread.INT_FAILURE_COUNT));

	}

	/**
	 * @param objectsFile
	 *            the objectsFile to set
	 */
	public void setObjectsFile(String objectsFile) {
		this.objectsFile = objectsFile;
	}

	/**
	 * @param cancelFile
	 *            the cancelFile to set
	 */
	public void setCancelFile(String cancelFile) {
		this.cancelFile = cancelFile;
	}

	/**
	 * @param syncFile
	 *            the syncFile to set
	 */
	public void setSyncFile(String syncFile) {
		this.syncFile = syncFile;
	}

	/**
	 * @param resumeId
	 *            the resumeId to set
	 */
	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	/**
	 * @param cancelFileMonitorThread
	 *            the cancelFileMonitorThread to set
	 */
	public void setCancelFileMonitorThread(CancelFileMonitorThread cancelFileMonitorThread) {
		this.cancelFileMonitorThread = cancelFileMonitorThread;
	}

	public void startType1OutputInserterThread(String fileName) {
		executor.execute(new Type1OutputCsvFileInserter(fileName, headers));

	}

	public void startType2OutputInserterThread(String fileName) {
		executor.execute(new Type2OutputXmlFileInserter(fileName));

	}

	public void startType3OutputInserterThread(String fileName) {
		executor.execute(new Type3OutputCsvFileInserter(fileName));

	}

	public void stopOutputInserterThread() {
		try {

			if (commandLineArgs.getExportType() == ExportType.EXPORT_CSV_IM) {
				HashMap<String, String> stopJob = new HashMap<String, String>();
				stopJob.put("", null);
				type1CsvOtputQueue.put(stopJob);
				while (type1CsvOtputQueue.size() != 0) {
					Log.debug("Waiting Output Queue to finish. Current Size:" + type1CsvOtputQueue.size());
					try {
						Thread.sleep(2000);
						if (isCanceled)
							break;
					} catch (InterruptedException e) {
						Log.error(e.getLocalizedMessage(), e);
					}
				}
			} else if (commandLineArgs.getExportType() == ExportType.EXPORT_XML_IS) {
				InfoObject infoObject = new InfoObject();
				infoObject.setSI_ID(-1);
				type2XmlOutputQueue.put(infoObject);
				while (type2XmlOutputQueue.size() != 0) {
					Log.debug("Waiting Output Queue to finish. Current Size:" + type2XmlOutputQueue.size());
					try {
						Thread.sleep(2000);
						if (isCanceled)
							break;
					} catch (InterruptedException e) {
						Log.error(e.getLocalizedMessage(), e);
					}
				}

			} else if (commandLineArgs.getExportType() == ExportType.EXPORT_CSV_IS) {
				InfoObject stopJob = new InfoObject();
				stopJob.setSI_ID(-1);
				type3CsvOutputQueue.put(stopJob);
				while (type1CsvOtputQueue.size() != 0) {
					Log.debug("Waiting Output Queue to finish. Current Size:" + type3CsvOutputQueue.size());
					try {
						Thread.sleep(2000);
						if (isCanceled)
							break;
					} catch (InterruptedException e) {
						Log.error(e.getLocalizedMessage(), e);
					}
				}
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

}
