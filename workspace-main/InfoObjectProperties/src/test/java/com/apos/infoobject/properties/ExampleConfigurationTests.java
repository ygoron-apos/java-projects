package com.apos.infoobject.properties;

import static org.junit.Assert.assertNotNull;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.apos.infoobject.properties.Service;
import com.apos.infoobject.xml.Destination_bad;
import com.apos.infoobject.xml.InfoObject;
import com.apos.infoobject.xml.PluginInterface;
import com.apos.infoobject.xml.ReportLogon;
import com.apos.infoobject.xml.SchedulingInfo;
import com.apos.infoobject.xml.UserIDInbox;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.CePropertyID;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleConfigurationTests {

	// private static final String STR_OBJECTS_CANCEL_FILE_NAME =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cancelfile.txt";
	// private static final String STR_SYNC_FILE_NAME =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/syncfile.txt";
	// private static final String STR_OUTPUT_FILE_NAME =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/propertiesfile.csv";
	// private static final String STR_ID_LIST =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/instanceId.list";

	private static final String STR_OBJECTS_CANCEL_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cancelfile.txt";
	private static final String STR_SYNC_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/syncfile.txt";
	private static final String STR_ID_LIST = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/instanceId.list";
	private static final String STR_CMS_SESSION_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cmsSessionfile.txt";

	// private static final String STR_OUTPUT_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/propertiesfile.csv";
	// private static final String STR_OUTPUT_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/is_export.csv";

	// private static final int INT_EXPORT_TYPE =
	// ExportType.EXPORT_CSV_IM.getCode();
	// private static final int INT_EXPORT_TYPE =
	// ExportType.EXPORT_CSV_IS.getCode();

	private static final String STR_OUTPUT_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/is_export.xml";
	private static final int INT_EXPORT_TYPE = ExportType.EXPORT_XML_IS.getCode();

	private static final Logger Log = Logger.getLogger(ExampleConfigurationTests.class);
	private static String serSession;

	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS  Where  SI_INSTANCE=1 AND SI_ID=11839";
	// // Crystal
	// Report
	// PDF
	// Instance
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE SI_ID = 2766435 OR SI_ID=2472";
//	private static final String CMS_QUERY = "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE   SI_INSTANCE=1 ";
//	private static final String CMS_QUERY = "SELECT  SI_ID FROM  CI_INFOOBJECTS WHERE   SI_CUID='ASj7aq6YDdNPkp_guIJ5x04' "; //Notifications
	private static final String CMS_QUERY = "SELECT  SI_ID FROM  CI_INFOOBJECTS WHERE   SI_CUID='AfUq3x7u0ndNh1xXkOMO89U' "; //Notifications
	
	
	
//	private static final String CMS_QUERY = "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE   SI_ID=43022 "; 4.0 SP5
//	private static final String CMS_QUERY = "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE   SI_ID=12057 "; // 3.1
	 
	// //Crystal Reports PDF Destination Disk, Parameters
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE   SI_ID=11467 ";
	// //Crystal Reports PDF Destination SMTP, Parameters
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE   SI_ID=11492 "; //
	// Crystal
	// Reports
	// PDF
	// Destination
	// FTP,
	// Parameters

	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_ID=11287 ";
	// //Crystal Reports PDF. Destination Disk
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_ID=11310 "; // Webi
	// PDF. Destination Disk
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_ID=11756 "; // Webi
	// PDF. Destination FTP

	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_ID=11343 "; //Deski
	// PDF. Destination Disk

	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_KIND='Webi'  AND SI_INSTANCE=1 ";
	// private static final String CMS_QUERY =
	// "SELECT TOP 1000 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_KIND='FullClient'  AND SI_INSTANCE=1 AND SI_ID=11095";

	// private static final String CMS_QUERY =
	// "SELECT TOP 1 SI_ID FROM  CI_INFOOBJECTS WHERE  SI_KIND='Webi'  AND SI_INSTANCE=1 AND SI_ID=10997 ";

	// private static final String CMS_QUERY =
	// "SELECT TOP 500 SI_ID FROM  CI_INFOOBJECTS WHERE SI_INSTANCE=1 AND SI_KIND='CrystalReport' AND SI_ID in (1330956,1330991,1331084,1331024,1331082)";

	// public static final String STR_CMS_USER_ID = "Administrator";
	// public static final String STR_CMS_USER_PASSWORD = "";
	// public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	// public static final String STR_CMS = "172.16.0.126";

	public static final String STR_CMS_USER_ID = "admin";
	public static final String STR_CMS_USER_PASSWORD = "apos123";
	public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	public static final String STR_CMS = "win-bi41rampup";
//	public static final String STR_CMS = "win-eiggairfoum";
	// public static final String STR_CMS = "192.168.1.119";

	// public static final String STR_CMS_USER_ID = "Administrator";
	// public static final String STR_CMS_USER_PASSWORD = "CEPower";
	// public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	// public static final String STR_CMS = "172.16.0.154:6601";

	// public static final String STR_CMS = "192.168.1.124";

	public static final String STR_SMTP_SERVER = "smtp.gmail.com";
	public static final int INT_SMTP_PORT = 465;
	public static final String STR_SMTP_USER = "ygoron@gmail.com";
	public static final String STR_SMTP_PASSWORD = "Moscow361";
	public static final String STR_SMTP_EMAIL_FROM = "ygoron@gmail.com";
	public static final String STR_SMTP_EMAIL_TO = "ygoron@apos.com;ygoron@gmail.com";
	public static final String STR_SMTP_EMAIL_SUBJECT = "Email Form Report Info Scanner";
	private static final int MAX_CMS_CONNECTIONS = 30;
	public static final boolean isSMTPSSL = true;
	public static final boolean isSendFinishedEmail = false;
	public static final boolean isIncludeFailureDetail = true;

	private static final int INT_RESUME_ID = 0;
	private static ICommandLineArgs commandLineArgs;
	private static IService propertyExtractor;

	@Autowired
	private Service service;
	private static IEnterpriseSession enterpriseSession = null;

	@BeforeClass
	public static void setProperties() throws Exception {
		// IEnterpriseSession enterpriseSession = null;
		boolean isSerialized = false;
		try {
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			serSession = enterpriseSession.getSerializedSession();
			isSerialized = true;
			// CrystalEnterprise.getSessionMgr().ge
			// logonToken =
			// enterpriseSession.getLogonTokenMgr().createLogonToken("", 99,
			// -1);
			// logonToken =
			// enterpriseSession.getLogonTokenMgr().createWCAToken("", 480, -1);
			String[] args = getArgs(serSession);
			commandLineArgs = new CommandLineArgs(args);
			commandLineArgs.print();
			commandLineArgs.setBeanParameters();
			Log.debug("Logon Token:" + serSession);

			final AbstractApplicationContext context = new FileSystemXmlApplicationContext("classpath:META-INF/spring/app-context.xml");
			context.registerShutdownHook();
			propertyExtractor = context.getBean(IService.class);
			Log.debug("Exit");
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (enterpriseSession != null && !isSerialized) {
				enterpriseSession.logoff();
				Log.debug("Logoff");
			}
		}

	}

	@AfterClass
	public static void logOff() {
		try {
			if (enterpriseSession != null) {
				enterpriseSession.logoff();
				Log.debug("Final Logoff");
			}
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
	}

	@Test
	@Ignore
	public void testSimpleProperties() throws Exception {
		assertNotNull(service);
	}

	@Test
	public void createIDList() {

		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		// IEnterpriseSession enterpriseSession = null;
		try {
			// enterpriseSession =
			// CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID,
			// STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");

			IInfoObjects infoObjects = infoStore.query(CMS_QUERY);

			Log.debug("Found Objects:" + infoObjects.size());
			fileWriter = new FileWriter(STR_ID_LIST, false);
			printWriter = new PrintWriter(fileWriter);

			for (int i = 0; i < infoObjects.size(); i++) {
				// Log.debug(" SI_DOC_COMMON_CONNECTION:" + ((IInfoObject)
				// infoObjects.get(i)).properties().getProperties("SI_DOC_COMMON_CONNECTION").size());
				String strLine = String.valueOf(((IInfoObject) infoObjects.get(i)).getID());
				Log.debug(strLine);
				printWriter.println(strLine);
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);

		} finally {
			// if (enterpriseSession != null)
			// enterpriseSession.logoff();
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

	@Test
	@Ignore
	public void testParameterProperties() {

		IEnterpriseSession enterpriseSession = null;
		try {
			// 9206
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			// IInfoStore infoStore = (IInfoStore)
			// enterpriseSession.getService("", "InfoStore");
			String cmsQuery = CMS_QUERY + " AND SI_ID=10072";
			Log.debug("Running Query:" + cmsQuery);
			// IInfoObject infoObject = (IInfoObject)
			// infoStore.query(cmsQuery).get(0) ;
			// HashMap<String, String> test = new HashMap<String, String>();
			// WorkerThread.getParametersInfo(test, infoObject,
			// commandLineArgs.getMaxParameters());

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);

		} finally {
			if (enterpriseSession != null)
				enterpriseSession.logoff();
		}

	}

	@Test
	@Ignore
	public void testLogonPropreties() {

		IEnterpriseSession enterpriseSession = null;
		try {
			// 9206
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			String cmsQuery = CMS_QUERY + " AND SI_ID=7045";
			Log.debug("Running Query:" + cmsQuery);
			IInfoObject infoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
			Log.debug(infoObject.getProcessingInfo().properties().size());

			IProperties propertyBag = infoObject.getProcessingInfo().properties();
			IProperty property = propertyBag.getProperty(CePropertyID.SI_LOGON_INFO);
			if (null != property && property.isContainer()) {
				propertyBag = (IProperties) property.getValue();
				property = propertyBag.getProperty("SI_NUM_LOGONS");
				int numLogons = ((Integer) property.getValue()).intValue();

				// Check if any of the connections are from a Universe
				for (int i = 1; i <= numLogons; i++) {
					property = propertyBag.getProperty("SI_LOGON" + String.valueOf(i));
					if (null != property && property.isContainer()) {
						IProperties logonBag = (IProperties) property.getValue();
						Log.debug("Server:" + logonBag.getProperty("SI_SERVER"));
						Log.debug("Server:" + logonBag.getProperty("SI_DB"));
						Log.debug("Server:" + logonBag.getProperty("SI_USER"));
					}
				}
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);

		} finally {
			if (enterpriseSession != null)
				enterpriseSession.logoff();
		}

	}

	@Test
	@Ignore
	public void testRegEx() {
		String sourceString = "\rLine1\nLine2\n\nLine\"3";
		String result = sourceString.replaceAll("[\\n\\r\"]", "");
		Log.debug("Source:" + sourceString);
		Log.debug("Result:" + result);
	}

	@Test
	@Ignore
	public void testPath() {
		IEnterpriseSession enterpriseSession = null;
		try {
			// 9206
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			String cmsQuery = CMS_QUERY + " AND SI_ID=10072";
			Log.debug("Running Query:" + cmsQuery);

			IInfoObject infoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
			String path = Type1WorkerThread.getObjectPath(infoStore, infoObject, new StringBuffer("")).toString();
			Log.debug("Path:" + path);

		} catch (Exception ee) {

		} finally {
			if (enterpriseSession != null)
				enterpriseSession.logoff();
		}
	}

	@Test
	@Ignore
	public void jaxb() {
		try {
			// JAXBContext context =
			// JAXBContext.newInstance("tool.csvreformat");
			JAXBContext context = JAXBContext.newInstance(InfoObject.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			InfoObject scheduleObject = new InfoObject();
			scheduleObject.setCluster("cluster101");
			scheduleObject.setSI_ID(50);
			scheduleObject.setSI_PARENTID(1105);
			scheduleObject.setSI_LOCATION("/Report Samples/Demonstration/World Sales Report");

			SchedulingInfo schedulingInfo = new SchedulingInfo();
			schedulingInfo.setIntervalMonths(4);
			schedulingInfo.setType(5);
			scheduleObject.setSchedulingInfo(schedulingInfo);

			Destination_bad destination = new Destination_bad();
			destination.setDestinationName("Destination Name1");
			UserIDInbox userIDInbox = new UserIDInbox();
			Map<String, Integer> inboxes = new HashMap<String, Integer>();
			inboxes.put("UserID1", 2437);
			inboxes.put("UserID2", 2438);
			inboxes.put("UserID3", 2439);
			userIDInbox.setUserIDInboxes(inboxes);
			destination.setUserIDInbox(userIDInbox);
			// schedulingInfo.setDestination(destination);

			PluginInterface pluginInterface = new PluginInterface();
			pluginInterface.setGroupFormula("Group Selection Formula1");
			// ReportLogons reportLogons = new ReportLogons();
			Map<String, ReportLogon> map = new HashMap<String, ReportLogon>();
			ReportLogon reportLogon = new ReportLogon();
			reportLogon.setDatabaseName("Database Name1");
			map.put("RepportLogons-1", reportLogon);
			// reportLogons.setReportLogon(map);
			// pluginInterface.setReportLogons(reportLogons);
			// scheduleObject.setPluginInterface(pluginInterface);

			System.out.print("Started");
			marshaller.marshal(scheduleObject, System.out);

			scheduleObject = new InfoObject();
			scheduleObject.setCluster("cluster101");
			scheduleObject.setSI_ID(50);

			scheduleObject.setSI_PARENTID(1107);
			scheduleObject.setSI_LOCATION("/Report Samples/Demonstration/World Sales Report2");

			schedulingInfo = new SchedulingInfo();
			schedulingInfo.setIntervalMonths(5);
			schedulingInfo.setType(6);
			scheduleObject.setSchedulingInfo(schedulingInfo);
			destination = new Destination_bad();
			destination.setDestinationName("Destination Name2");
			// schedulingInfo.setDestination(destination);

			marshaller.marshal(scheduleObject, System.out);

			System.out.print("Finished");
		} catch (Exception e) {
			Log.error(e.getLocalizedMessage(), e);
		}

	}

	@Test
	public void testMain() {

		try {
			System.out.print("Started!");
			ExtractorMain propertyExtractorMain = new ExtractorMain();
			propertyExtractorMain.execute(commandLineArgs, propertyExtractor);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	/**
	 * @param cmsToken
	 * @return
	 */
	public static String[] getArgs(String cmsSerializedSession) {

		FileWriter fileWriter = null;
		PrintWriter printWriter = null;

		try {

			List<String> argsList = new ArrayList<String>();

			fileWriter = new FileWriter(STR_CMS_SESSION_FILE_NAME, false);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(cmsSerializedSession);

			argsList.add(0, String.valueOf(INT_EXPORT_TYPE));
			argsList.add(1, STR_CMS_SESSION_FILE_NAME);
			argsList.add(2, STR_ID_LIST);
			argsList.add(3, STR_OBJECTS_CANCEL_FILE_NAME);
			argsList.add(4, STR_OUTPUT_FILE_NAME);
			argsList.add(5, STR_SYNC_FILE_NAME);
			argsList.add(6, String.valueOf(INT_RESUME_ID));
			argsList.add(7, String.valueOf(MAX_CMS_CONNECTIONS));

			argsList.add(8, String.valueOf(ICommandLineArgs.INT_DEFAULT_MAX_LOGONS));
			argsList.add(9, String.valueOf(ICommandLineArgs.INT_DEFAULT_MAX_PARAMETERS));

			argsList.add(10, STR_SMTP_SERVER);
			argsList.add(11, String.valueOf(INT_SMTP_PORT));

			argsList.add(12, STR_SMTP_USER);
			argsList.add(13, STR_SMTP_PASSWORD);
			argsList.add(14, STR_SMTP_EMAIL_FROM);
			argsList.add(15, STR_SMTP_EMAIL_TO);
			argsList.add(16, STR_SMTP_EMAIL_SUBJECT);
			argsList.add(17, isSMTPSSL ? "true" : "false");
			argsList.add(18, isSendFinishedEmail ? "true" : "false");
			argsList.add(19, isIncludeFailureDetail ? "true" : "false");

			String[] args = argsList.toArray(new String[argsList.size()]);

			String commandLine = "";

			for (String arg : argsList) {
				commandLine += "\"" + arg + "\" ";
			}
			Log.info("Command Line:" + commandLine);
			return args;

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
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
		return null;

	}

	@Test
	@Ignore
	public void createLogonToken() {
		IEnterpriseSession enterpriseSession = null;
		try {
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			serSession = enterpriseSession.getLogonTokenMgr().createLogonToken("", 99, 99);
			assertNotNull(serSession);
			Log.debug("Logon Token:" + serSession);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (enterpriseSession != null)
				enterpriseSession.logoff();
		}
	}

}
