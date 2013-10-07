/**
 * 
 */
package com.apos.scheduler;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Yuri Goron
 * 
 */
public class SchedulerMain {

	private static final Logger Log = Logger.getLogger(SchedulerMain.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				ICommandLineArgs commandLineArgs = getArgs(args);
				commandLineArgs.setMaxBOXIConnections(ICommandLineArgs.INT_DEFAULT_BOXI_CONNECTIONS);
				commandLineArgs.print();
				commandLineArgs.setBeanParameters();
				final AbstractApplicationContext context = new FileSystemXmlApplicationContext("classpath:META-INF/spring/app-context.xml");
				context.registerShutdownHook();
				IService scheduler = context.getBean(IService.class);
				scheduler.execute(commandLineArgs);
				context.close();
			} else {
				throw new Exception("Missing Command Line Arguments");
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}

	public void execute(ICommandLineArgs commandLineArgs, IService scheduler) {
		scheduler.execute(commandLineArgs);
	}

	public static ICommandLineArgs getArgs(String[] args) {
		ICommandLineArgs commandLineArgs = new CommandLineArgs();

		try {
			if (Integer.parseInt(args[0]) == ScheduleType.SCHEDULE_DEFAULT.getCode()) {
				Log.debug("Processing " + ScheduleType.SCHEDULE_DEFAULT.getDescription());
				commandLineArgs.setScheduleType(ScheduleType.SCHEDULE_DEFAULT);
				commandLineArgs.setSerializedSessionFile(args[1]);
				commandLineArgs.setObjectId(Integer.valueOf(args[2]));
				commandLineArgs.setCancelFile(args[3]);
				commandLineArgs.setSyncFile(args[4]);
				commandLineArgs.setLogFile(args[5]);
				commandLineArgs.setDeleteRecurring(Boolean.parseBoolean(args[6]));
			} else if (Integer.parseInt(args[0]) == ScheduleType.SCHEDULE_DEFAULT_AND_OVERRIDE.getCode()) {

				Log.debug("Processing " + ScheduleType.SCHEDULE_DEFAULT_AND_OVERRIDE.getDescription());
				commandLineArgs.setScheduleType(ScheduleType.SCHEDULE_DEFAULT_AND_OVERRIDE);
				commandLineArgs.setSerializedSessionFile(args[1]);
				commandLineArgs.setObjectId(Integer.valueOf(args[2]));
				commandLineArgs.setCancelFile(args[3]);
				commandLineArgs.setSyncFile(args[4]);
				commandLineArgs.setLogFile(args[5]);
				commandLineArgs.setCustomSettingsFile(args[6]);
				commandLineArgs.setDeleteRecurring(Boolean.parseBoolean(args[7]));

			} else if (Integer.parseInt(args[0]) == ScheduleType.SCHEDULE_FROM_SETTINGS_IN_INSTANCE.getCode()) {

				/**
				 * 
				 * 0. Schedule Type <br>
				 * 1. Path to file with Serialized Session <br>
				 * 2. Report ID Ð integer Ð id of the report object to be
				 * scheduled <br>
				 * 3. Cancel File Path - string Ð path to the cancel.txt file
				 * that tells java to stop<br>
				 * 4. Sync File Path Ð string Ð path to the sync.txt file java
				 * creates when finished<br>
				 * 5. Results File Path Ð string Ð path to the results.txt file
				 * java writes results to<br>
				 * 6. Instances File Path Ð string Ð path to instances.txt file
				 * for java to read. File will contain carriage return delimited
				 * list of instance ids<br>
				 * 7. Delete Recurring Ð Boolean Ð should the existing recurring
				 * schedules be removed<br>
				 * 8. Replace Instances Ð Boolean Ð should the provided instance
				 * be replaced with the new scheduled one<br>
				 */

				commandLineArgs.setScheduleType(ScheduleType.SCHEDULE_FROM_SETTINGS_IN_INSTANCE);
				commandLineArgs.setSerializedSessionFile(args[1]);
				commandLineArgs.setObjectId(Integer.valueOf(args[2]));
				commandLineArgs.setCancelFile(args[3]);
				commandLineArgs.setSyncFile(args[4]);
				commandLineArgs.setLogFile(args[5]);
				commandLineArgs.setInstanceFile(args[6]);
				commandLineArgs.setDeleteRecurring(Boolean.parseBoolean(args[7]));
				commandLineArgs.setRepaceInstances(Boolean.parseBoolean(args[8]));

			} else if (Integer.parseInt(args[0]) == ScheduleType.BATCH_SCHEDULING.getCode()) {
				/**
				 * 0. Schedule Type <br>
				 * 1. Parameter Flag<br>
				 * (0 - no warnings; 1- Warning - Report to be scheduled but you
				 * did not provide values for the parameter " 2- Report will not
				 * scheduled - not parameters <br>
				 * 2. CMS Session Ð Path to CMS Session File <br>
				 * 3. Path to XML file that has all schedule settings <br>
				 * 4. Cancel File Path Ð string Ð path to the cancel.txt file
				 * that tells java to stop <br>
				 * 5. Sync File Path Ð string Ð path to the sync.txt file java
				 * creates when finished<br>
				 * 6. Results File Path Ð string Ð path to the results.txt file
				 * java writes results to<br>
				 */

				commandLineArgs.setScheduleType(ScheduleType.BATCH_SCHEDULING);
				commandLineArgs.setParameterFlag(Integer.parseInt(args[1]));
				commandLineArgs.setSerializedSessionFile(args[2]);
				commandLineArgs.setCustomSettingsFile(args[3]);
				commandLineArgs.setCancelFile(args[4]);
				commandLineArgs.setSyncFile(args[5]);
				commandLineArgs.setLogFile(args[6]);

			}
		} catch (Exception ee) {
			ee.printStackTrace();
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return commandLineArgs;
	}
}
