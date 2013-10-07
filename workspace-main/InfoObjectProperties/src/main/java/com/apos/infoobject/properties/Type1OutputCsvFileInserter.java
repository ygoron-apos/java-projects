/**
 * 
 */
package com.apos.infoobject.properties;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 * 
 */
public class Type1OutputCsvFileInserter implements Runnable {

	private static final Logger Log = Logger.getLogger(Type1OutputCsvFileInserter.class);
	private String fileName;
	private List<String> headers;

	// private String baseHeader =
	// "\"Status\",\"Submitter\",\"Job Server Name\",\"Creation Time\",\"Update Time\",\"Start Time\",\"End Time\",\"Duration\",\"Next Run Time\",\"Result File Name\",\"Flags\",\"Original File Name\",\"Job Description\",\"Job Title\",\"Instance Kind\",\"Object Type\",\"Schedule Type\",\"Dependants - ID\",\"Dependants - Name\",\"Dependencies - ID\",\"Dependencies - Name\",\"Destination\",\"Processing Security Extensions\",\"Format\",\"Server Group\",\"Server Group Choice\",\"Error Message\",\"Group Selection Formula\",\"Record Selection Formula\",\"ID\",\"Number of Alerts\",\"Is Recurring\",\"Locale\",\"Num Retries Allowed\",\"Retry Interval In Min\",\"Parent ID\",\"Location\",\"Component\",\"CUID\",\"Printer\",\"APOSNotification\",\"APOSDestinationEMAIL\",\"APOSDestinationDelimiterEMAIL\",\"APOSEmailSubjectEMAIL\",\"APOSEmailCCEMAIL\",\"APOSEmailBCCEMAIL\",\"APOSEmailMessageEMAIL\",\"APOSEmailFileNameEMAIL\",\"APOSFormatEMAIL\",\"APOSFormatPasswordEMAIL\",\"APOSEditPasswordEMAIL\",\"APOSPDFEditOptionsEMAIL\",\"APOSCompressFilesUsingZIPEMAIL\",\"APOSZIPPasswordEMAIL\",\"APOSCRCOMKeepHistoryEMAIL\",\"APOSDoNotDistributeEmptyReportEMAIL\",\"APOSDestinationCONTENT\",\"APOSDestinationDelimiterCONTENT\",\"APOSEmailSubjectCONTENT\",\"APOSEmailCCCONTENT\",\"APOSEmailBCCCONTENT\",\"APOSFormatCONTENT\",\"APOSDoNotDistributeEmptyReportCONTENT\",\"APOSDestinationFTP\",\"APOSDestinationDelimiterFTP\",\"APOSFormatFTP\",\"APOSFormatPasswordFTP\",\"APOSEditPasswordFTP\",\"APOSPDFEditOptionsFTP\",\"APOSCompressFilesUsingZIPFTP\",\"APOSZIPPasswordFTP\",\"APOSDoNotDistributeEmptyReportFTP\",\"APOSDestinationNETWORK\",\"APOSDestinationDelimiterNETWORK\",\"APOSFormatNETWORK\",\"APOSFormatPasswordNETWORK\",\"APOSEditPasswordNETWORK\",\"APOSPDFEditOptionsNETWORK\",\"APOSCompressFilesUsingZIPNETWORK\",\"APOSZIPPasswordNETWORK\",\"APOSDoNotDistributeEmptyReportNETWORK\",\"APOSDestinationPRINTER\",\"APOSDestinationDelimiterPRINTER\",\"APOSDoNotDistributeEmptyReportPRINTER\",\"APOSDestinationCRCOM\",\"APOSDestinationDelimiterCRCOM\",\"APOSFormatCRCOM\",\"APOSFormatPasswordCRCOM\",\"APOSEditPasswordCRCOM\",\"APOSPDFEditOptionsCRCOM\",\"APOSCRCOMKeepHistoryCRCOM\",\"APOSDoNotDistributeEmptyReportCRCOM\",\"APOSCRCOMUserNameOverrideCRCOM\",\"APOSCRCOMPasswordOverrideCRCOM\",\"APOSCompressFilesUsingZIPCRCOM\",\"APOSZIPPasswordCRCOM\",\"Server Name 1\",\"Database Name 1\",\"User Name 1\",\"Server Name 2\",\"Database Name 2\",\"User Name 2\",\"Server Name 3\",\"Database Name 3\",\"User Name 3\",\"Server Name 4\",\"Database Name 4\",\"User Name 4\",\"Server Name 5\",\"Database Name 5\",\"User Name 5\",\"Parameter Name 1\",\"Parameter Value 1\",\"Parameter Name 2\",\"Parameter Value 2\",\"Parameter Name 3\",\"Parameter Value 3\",\"Parameter Name 4\",\"Parameter Value 4\",\"Parameter Name 5\",\"Parameter Value 5\",\"Parameter Name 6\",\"Parameter Value 6\",\"Parameter Name 7\",\"Parameter Value 7\",\"Parameter Name 8\",\"Parameter Value 8\",\"Parameter Name 9\",\"Parameter Value 9\",\"Parameter Name 10\",\"Parameter Value 10\",\"Parameter Name 11\",\"Parameter Value 11\",\"Parameter Name 12\",\"Parameter Value 12\",\"Parameter Name 13\",\"Parameter Value 13\",\"Parameter Name 14\",\"Parameter Value 14\",\"Parameter Name 15\",\"Parameter Value 15\",\"Parameter Name 16\",\"Parameter Value 16\",\"Parameter Name 17\",\"Parameter Value 17\",\"Parameter Name 18\",\"Parameter Value 18\",\"Parameter Name 19\",\"Parameter Value 19\",\"Parameter Name 20\",\"Parameter Value 20\",\"Parameter Name 21\",\"Parameter Value 21\",\"Parameter Name 22\",\"Parameter Value 22\",\"Parameter Name 23\",\"Parameter Value 23\",\"Parameter Name 24\",\"Parameter Value 24\",\"Parameter Name 25\",\"Parameter Value 25\",\"Parameter Name 26\",\"Parameter Value 26\",\"Parameter Name 27\",\"Parameter Value 27\",\"Parameter Name 28\",\"Parameter Value 28\",\"Parameter Name 29\",\"Parameter Value 29\",\"Parameter Name 30\",\"Parameter Value 30\",\"Parameter Name 31\",\"Parameter Value 31\",\"Parameter Name 32\",\"Parameter Value 32\",\"Parameter Name 33\",\"Parameter Value 33\",\"Parameter Name 34\",\"Parameter Value 34\",\"Parameter Name 35\",\"Parameter Value 35\",\"Parameter Name 36\",\"Parameter Value 36\",\"Parameter Name 37\",\"Parameter Value 37\",\"Parameter Name 38\",\"Parameter Value 38\",\"Parameter Name 39\",\"Parameter Value 39\",\"Parameter Name 40\",\"Parameter Value 40\",\"Parameter Name 41\",\"Parameter Value 41\",\"Parameter Name 42\",\"Parameter Value 42\",\"Parameter Name 43\",\"Parameter Value 43\",\"Parameter Name 44\",\"Parameter Value 44\",\"Parameter Name 45\",\"Parameter Value 45\",\"Parameter Name 46\",\"Parameter Value 46\",\"Parameter Name 47\",\"Parameter Value 47\",\"Parameter Name 48\",\"Parameter Value 48\",\"Parameter Name 49\",\"Parameter Value 49\",\"Parameter Name 50\",\"Parameter Value 50\"";

	public Type1OutputCsvFileInserter(String fileName, List<String> headers) {
		this.fileName = fileName;
		this.headers = headers;
	}

	public void run() {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		File tempFile = null;
		try {
			Log.debug("Type 1 CSV OutputInserter Thread Started");

			tempFile = File.createTempFile("objectExport_", UUID.randomUUID().toString());
			tempFile.deleteOnExit();
			fileWriter = new FileWriter(tempFile.getAbsolutePath(), false);
			printWriter = new PrintWriter(fileWriter);
			StringBuffer headerString = new StringBuffer();
			for (String header : headers) {
				headerString.append("\"").append(header).append("\",");
			}

			printWriter.print(headerString.substring(0, headerString.length() - 1) + "\n");
			while (true) {
				HashMap<String, String> outputJobWrapper = Extractor.type1CsvOtputQueue.take();
				Log.debug("Took Job");
				if (outputJobWrapper.containsKey("")) {
					Log.debug("No More Jobs Left");
					throw new NoMoreJobsException();
				}
				StringBuffer outString = new StringBuffer();
				for (String header : headers) {
					if (outputJobWrapper.containsKey(header)) {
						String value = outputJobWrapper.get(header).replaceAll("[\\n\\r\"]", "");
						outString.append("\"").append(value).append("\",");
					} else {
						outString.append("\"").append(Type1WorkerThread.STR_N_A).append("\",");
					}
				}

				// String
				// resultString=outString.substring(0,outString.length()-1).replaceAll("[\\n\\r]","");
				Log.debug("Printed:" + outString.substring(0, outString.length() - 1) + "\n");
				printWriter.print(outString.substring(0, outString.length() - 1) + "\n");

				// printWriter.print(resultString+ "\n");
			}

		} catch (NoMoreJobsException endofJob) {
			Log.debug("Output Inserter Thread will be finished-Copy temp File to the location");
			try {
				if (printWriter != null) {
					printWriter.flush();
					printWriter.close();
				}
				FileUtils.copyFile(tempFile, new File(fileName));
				Log.debug("File :" + tempFile.getAbsolutePath() + " copied to:" + fileName);
			} catch (Exception ee) {
				Log.error(ee.getLocalizedMessage(), ee);
				Log.error("Failed to copy File :" + tempFile.getAbsolutePath() + " to:" + fileName);
			}
		} catch (Throwable ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			Log.debug("Output Inserter Thread finished");
			try {
				if (fileWriter != null)
					fileWriter.close();
			} catch (Exception ee) {
			}
			try {
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (Exception ee) {

			}

		}

	}

}
