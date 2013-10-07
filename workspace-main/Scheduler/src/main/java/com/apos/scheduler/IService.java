/**
 * 
 */
package com.apos.scheduler;

/**
 * @author Yuri Goron
 * 
 */
public interface IService {
	/**
	 * Runs App
	 */
	public void execute(ICommandLineArgs commandLineArgs);

	public void deleteSyncFile();

	public void startCancelThread();

	public void stopCancelThread();

	public void startOutputInserterThread(String fileName);

	public void stopOutputInserterThread();

	public void setCanceled(boolean isCanceled);

	public void printStatistics(long startTime, long endTime);

	public void createCompletedFile(ICommandLineArgs commandLineArgs);

}
