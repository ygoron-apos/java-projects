/**
 * 
 */
package com.apos.scheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuri Goron
 *
 */
public enum ScheduleType {


		NOT_SET(0,"Not Set"),
		SCHEDULE_DEFAULT(1,"Schedule by Report ID using default settings on the Report Object"), 
		SCHEDULE_DEFAULT_AND_OVERRIDE(2, "Schedule by Report ID using default settings Ð override defaults by provided settings"),
		SCHEDULE_FROM_SETTINGS_IN_INSTANCE(3, "Schedule by Report ID using all settings from specified instance"),
		SCHEDULE_FROM_SETTINGS_IN_INSTANCE_AND_OVERRIDE(4, "Schedule by Report ID using all settings from specified instance Ð settings may then be overridden by provided settings"),
		UPDATE_DEFAULT_SETTINGS(5, "Update the default settings on an object by ID using provided settings (commit instead of schedule)"),
		BATCH_SCHEDULING(6, "6.	Schedule set of objects using provided XML File");
		

		private int code;
		private String description;

		/**
	     * A mapping between the integer code and its corresponding Description to facilitate lookup by code.
	     */
	    private static Map<Integer, ScheduleType> codeToDescriptionMapping;
	    
		private ScheduleType(int mode, String description) {
			this.code = mode;
			this.description = description;
		}

		
		public static ScheduleType getStatus(int i) {
	        if (codeToDescriptionMapping == null) {
	            initMapping();
	        }
	        ScheduleType result = null;
//	        for (ConvertMode mode : values()) {
	            result = codeToDescriptionMapping.get(i);
//	        }
	        return result;
	    }
	 
	    private static void initMapping() {
	    	codeToDescriptionMapping = new HashMap<Integer, ScheduleType>();
	        for (ScheduleType mode : values()) {
	        	codeToDescriptionMapping.put(mode.code, mode);
	        }
	    }
	 
		/**
		 * @return the mode
		 */
		public int getCode() {
			return code;
		}

		/**
		 * @param mode
		 *            the mode to set
		 */
		public void setCode(int mode) {
			this.code = mode;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description
		 *            the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
}
