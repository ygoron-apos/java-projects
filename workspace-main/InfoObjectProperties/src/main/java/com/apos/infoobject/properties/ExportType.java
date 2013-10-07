/**
 * 
 */
package com.apos.infoobject.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuri Goron
 *
 */
public enum ExportType {


		NOT_SET(0,"Not Set"),
		EXPORT_CSV_IM(1,"Export into CSV using Instance Manager Format"), 
		EXPORT_XML_IS(2, "Export into XML using Info Scheduler format"),
		EXPORT_CSV_IS(3, "Export into CSV using Info Scheduler format");

		private int code;
		private String description;

		/**
	     * A mapping between the integer code and its corresponding Description to facilitate lookup by code.
	     */
	    private static Map<Integer, ExportType> codeToDescriptionMapping;
	    
		private ExportType(int mode, String description) {
			this.code = mode;
			this.description = description;
		}

		
		public static ExportType getStatus(int i) {
	        if (codeToDescriptionMapping == null) {
	            initMapping();
	        }
	        ExportType result = null;
//	        for (ConvertMode mode : values()) {
	            result = codeToDescriptionMapping.get(i);
//	        }
	        return result;
	    }
	 
	    private static void initMapping() {
	    	codeToDescriptionMapping = new HashMap<Integer, ExportType>();
	        for (ExportType mode : values()) {
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
