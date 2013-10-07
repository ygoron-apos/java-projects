/**
 * 
 */
package com.apos.scheduler;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * @author Yuri Goron
 *
 */
public class CancelFileMonitorThread extends Thread implements Runnable{
	private static Logger Log = Logger.getLogger(CancelFileMonitorThread.class
			.getName());
	private static final long LONG_DEFAULT_POOL_TIMEOUT = 5000;
	private IService scanner;
	private String fileName;
	private boolean isStopped;

	public CancelFileMonitorThread(IService scanner, String fileName) {
		this.scanner = scanner;
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		File cancelFile = null;
		try {
			cancelFile = new File(fileName);
			cancelFile.delete();

			while (!cancelFile.exists()) {
				Thread.sleep(LONG_DEFAULT_POOL_TIMEOUT);
				if (isStopped)
					break;
			}

			if (!isStopped) {
				Log.info("Cancel Thread - File " + fileName + " was created!");
				scanner.setCanceled(true);
				cancelFile.delete();
			}

		} catch (InterruptedException ee) {
			Log.error(ee.getLocalizedMessage(), ee);
			Thread.currentThread().interrupt();
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	/**
	 * @return the isStopped
	 */
	public boolean isStopped() {
		return isStopped;
	}

	/**
	 * @param isStopped
	 *            the isStopped to set
	 */
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}
}
