/**
 * 
 */
package com.apos.infoobject.properties;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.apos.infoobject.xml.InfoObject;

/**
 * @author Yuri Goron
 * 
 */
public class Type3OutputCsvFileInserter implements Runnable {

	private static final Logger Log = Logger.getLogger(Type1OutputCsvFileInserter.class);
	private String fileName;

	public Type3OutputCsvFileInserter(String fileName) {
		this.fileName = fileName;
	}

	public void run() {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		File tempFile = null;
		try {
			Log.debug("Type 3 CSV OutputInserter Thread Started");

			tempFile = File.createTempFile("objectExport_", UUID.randomUUID().toString());
			tempFile.deleteOnExit();
			fileWriter = new FileWriter(tempFile.getAbsolutePath(), false);
			printWriter = new PrintWriter(fileWriter);

			while (true) {
				InfoObject xmlInfoObject = Extractor.type3CsvOutputQueue.take();
				Log.debug("Took Job. Queue size:" + Extractor.type3CsvOutputQueue.size());

				if (xmlInfoObject.getSI_ID() <= 0) {
					Log.debug("No More Jobs Left");
					throw new NoMoreJobsException();
				}

				Log.debug("Processing Object ID:" + xmlInfoObject.getSI_ID());

				StringBuffer outString = new StringBuffer();

				outString = getBlankValue(outString); // Blank Error Message;
				outString = getValue("0", outString); // Include;
				outString = getValue(xmlInfoObject.getSI_ID(), outString); // InfoObject
																			// ID
				outString = getValue(xmlInfoObject.getSI_LOCATION(), outString); // Location

				if (xmlInfoObject.getSchedulingInfo() != null) {
					outString = getValue(xmlInfoObject.getSchedulingInfo().getBeginDate(), outString);
					outString = getValue(xmlInfoObject.getSchedulingInfo().getTypeText(), outString);
					outString = getValue(xmlInfoObject.getSchedulingInfo().getEndDate(), outString);
					if (xmlInfoObject.getSchedulingInfo().getDestinationManaged() != null) {
						outString = getValue("Inbox", outString);
					} else if (xmlInfoObject.getSchedulingInfo().getDestinationFtp() != null) {
						outString = getValue("Ftp", outString);
					} else if (xmlInfoObject.getSchedulingInfo().getDestinationDiskUnManaged() != null) {
						outString = getValue("Disk", outString);
					} else if (xmlInfoObject.getSchedulingInfo().getDestinationSmtp() != null) {
						outString = getValue("Email", outString);
					} else {
						outString = getBlankValue(outString);
					}

				}

				if (xmlInfoObject.getPluginInterface() != null) {
					if (xmlInfoObject.getPluginInterface().getReportFormatOptions() != null) {
						outString = getValue(xmlInfoObject.getPluginInterface().getReportFormatOptions().getFormatString(), outString);
					}

				} else if (xmlInfoObject.getPluginInterfaceWebi() != null) {
					if (xmlInfoObject.getPluginInterfaceWebi().getWebiFormatOptions() != null) {
						outString = getValue(xmlInfoObject.getPluginInterfaceWebi().getWebiFormatOptions().getFormatString(), outString);
					}
				} else {
					outString = getBlankValue(outString);
				}

				String inBoxes = "";
				if (xmlInfoObject.getSchedulingInfo() != null) {
					if (xmlInfoObject.getSchedulingInfo().getDestinationManaged() != null) {
						if (xmlInfoObject.getSchedulingInfo().getDestinationManaged() != null) {
							for (int i = 0; i < xmlInfoObject.getSchedulingInfo().getDestinationManaged().getUserIdInboxes().size(); i++) {
								inBoxes += "U:" + xmlInfoObject.getSchedulingInfo().getDestinationManaged().getUserIdInboxes().get(i) + ",";
							}
						}
					}
				}
				if (inBoxes != "") {
					outString = getValue(inBoxes, outString);
				}else{
					outString = getBlankValue(outString);
				}

				// String value =
				// outputJobWrapper.get(header).replaceAll("[\\n\\r\"]", "");
				// outString.append("\"").append(value).append("\",");

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

	/**
	 * @param value
	 * @param outString
	 * @return
	 */
	private StringBuffer getBlankValue(StringBuffer outString) {
		outString.append("\"").append(Type1WorkerThread.STR_N_A).append("\",");
		return outString;
	}

	/**
	 * Strips value
	 * 
	 * @param value
	 * @return
	 */
	private StringBuffer getValue(int value, StringBuffer outString) {

		outString.append("\"").append(value).append("\",");
		return outString;

	}

	/**
	 * Strips value
	 * 
	 * @param value
	 * @return
	 */
	private StringBuffer getValue(String value, StringBuffer outString) {

		if (value != null) {
			value = value.replaceAll("[\\n\\r\"]", "");
			outString.append("\"").append(value).append("\",");
		} else {
			getBlankValue(outString);
		}
		return outString;

	}
}
