/**
 * 
 */
package com.apos.scheduler;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 * 
 */
public class OutputFileInserter implements Runnable {

	private Logger Log = Logger.getLogger(OutputFileInserter.class);
	private String file;
	private List<String> headers;
	private boolean isFileOpen;
	public static final String STR_N_A = "";

	public OutputFileInserter(String file, List<String> headers) {
		this.file = file;
		this.headers = headers;
	}

	public void run() {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;

		try {
			Log.debug("OutputInserter Thread Started");
			try {
				fileWriter = new FileWriter(file, false);
				printWriter = new PrintWriter(fileWriter);
				isFileOpen = true;
			} catch (Exception ee) {
				Log.error("Can't Open file:" + " file :" + ee.getLocalizedMessage(), ee);
				isFileOpen = false;
			}
			StringBuffer headerString = new StringBuffer();
			for (String header : headers) {
				headerString.append("\"").append(header).append("\",");
			}
			if (isFileOpen)
				printWriter.print(headerString.substring(0, headerString.length() - 1) + "\n");

			while (true) {
				HashMap<String, String> outputJobWrapper = Scheduler.outputQueue.take();
				Log.debug("Took Job");
				if (outputJobWrapper.containsKey("")) {
					Log.debug("No More Jobs Left");
					throw new NoMoreJobsException();
				}
				StringBuffer outString = new StringBuffer();
				try {
					for (String header : headers) {
						Log.debug("Processing Header:" + header);
						if (outputJobWrapper.containsKey(header)) {

							String value = "";
							if (outputJobWrapper.get(header) != null) {
								value = outputJobWrapper.get(header).replaceAll("[\\n\\r\"]", "");
								outString.append("\"").append(value).append("\",");
							} else {
								outString.append("\"").append(STR_N_A).append("\",");
							}

						} else {
							outString.append("\"").append(STR_N_A).append("\",");
						}
					}
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
				}

				// String
				// resultString=outString.substring(0,outString.length()-1).replaceAll("[\\n\\r]","");
				if (isFileOpen)
					printWriter.print(outString.substring(0, outString.length() - 1) + "\n");
				// printWriter.print(resultString+ "\n");
			}

		} catch (NoMoreJobsException endofJob) {
			Log.debug("Output Inserter Thread will be finished");
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
