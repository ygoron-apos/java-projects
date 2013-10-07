/**
 * 
 */
package com.apos.infoobject.properties;

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

	public void startType1OutputInserterThread(String fileName);
	public void startType2OutputInserterThread(String fileName);
	public void startType3OutputInserterThread(String fileName);

	public void stopOutputInserterThread();

	public void setCanceled(boolean isCanceled);

	public void printStatistics(long startTime, long endTime);

	public void createCompletedFile(ICommandLineArgs commandLineArgs);

}
