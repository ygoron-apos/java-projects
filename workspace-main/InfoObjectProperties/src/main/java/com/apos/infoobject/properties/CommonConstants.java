/**
 * 
 */
package com.apos.infoobject.properties;

/**
 * @author Yuri Goron
 * 
 */
public class CommonConstants {

	public static final String STR_PROGID_CRYSTAL_ENTERPRISE_REPORT = "CrystalEnterprise.Report";
	public static final String STR_CMS_QUERY_BY_CUID = "SELECT * FROM CI_INFOOBJECTS WHERE SI_CUID=";
	public static final String STR_DESTINATION_OPTION_INBOX = "Inbox";
	public static final String STR_DESTINATION_OPTION_FAVORITES = "Favorites";
	public static final String STR_DESTINATION_FTP = "CrystalEnterprise.Ftp";
	public static final String STR_DESTINATION_MANAGED = "CrystalEnterprise.Managed";
	public static final String STR_DESTINATION_UNMANAGED = "CrystalEnterprise.DiskUnmanaged";
	public static final String STR_DESTINATION_USER = "CrystalEnterprise.User";
	public static final String STR_DESTINATION_GROUP = "CrystalEnterprise.UserGroup";
	public static final String STR_ENTERPRISE_EVENT = "CrystalEnterprise.Event";
	public static final String STR_SMTP_AUTH_TYPE_PLAIN = "Plain";
	public static final String STR_SMTP_AUTH_TYPE_NONE = "None";
	public static final String STR_SMTP_AUTH_TYPE_LOGIN = "Login";
	public static final String STR_DESTINATION_SMTP = "CrystalEnterprise.Smtp";
	public static final String STR_FREQ_FIRST_MONDAY = "1stMondayOfMonth";
	public static final String STR_FREQ_CALENDAR_TEMPLATE = "CalendarTemplate";
	public static final String STR_FREQ_CALENDAR = "Calendar";
	public static final String STR_FREQ_WEEKLY = "Weekly";
	public static final String STR_FREQ_LAST_DAY = "LastDay";
	public static final String STR_FREQ_ONCE = "Once";
	public static final String STR_FREQ_NTH_DAY_OF_MONTH = "NthDayOfMonth";
	public static final String STR_FREQ_MONTHLY = "Monthly";
	public static final String STR_FREQ_DAILY = "Daily";
	public static final String STR_FREQ_HOURLY = "Hourly";
	public static final String STR_MANAGED_SEND_OPTION_SHORCUT = "Shortcut";
	public static final String STR_MANAGED_SEND_OPTION_COPY = "Copy";
	public static final String STR_AUDIT_RESULT_SUCCESS = "Success";
	public static final String STR_AUDIT_RESULT_FAILURE = "Failure";
	public static final String STR_AUDIT_RESULT_BOTH = "Both";
	public static final String CMS_DATE_FORMAT_SCHEDULE = "yyyy-MM-dd HH:mm:ss";
	public static final String STR_ERROR_INFO_SEPARATOR = ";";
	public static final String STR_KIND_CRYSTAL_REPORT = "CrystalReport";
	public static final String STR_KIND_OBJECT_PACKAGE = "ObjectPackage";

	public static final String STR_KIND_WEBI = "Webi";
	public static final String STR_KIND_PROGRAM = "Program";

	public static byte[] KEY = new byte[] { (byte) 0xd2, (byte) 0x53, (byte) 0x1b, (byte) 0x58, (byte) 0x73, (byte) 0xd1, (byte) 0xb1, (byte) 0x61, (byte) 0x2e, (byte) 0x33, (byte) 0x58, (byte) 0x26,
			(byte) 0x56, (byte) 0x43, (byte) 0x59, (byte) 0xa6 };

	public static byte[] IV = new byte[] { (byte) 0x2a, (byte) 0x5f, (byte) 0x81, (byte) 0x7f, (byte) 0xfa, (byte) 0xc2, (byte) 0xe8, (byte) 0xd2, (byte) 0x03, (byte) 0xf4, (byte) 0x22, (byte) 0x53,
			(byte) 0x23, (byte) 0x1f, (byte) 0x34, (byte) 0xb7 };

}
