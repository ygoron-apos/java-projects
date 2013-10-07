package com.apos.scheduler;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.apos.encryption.Encryptor;
import com.apos.infoobject.properties.CommonConstants;
import com.apos.infoobject.xml.InfoObject;
import com.apos.infoobject.xml.SchedulingInfo;
import com.apos.michael.WebiRefresh;
import com.apos.xml.generic.ScheduleJobs;
import com.apos.xml.generic.ScheduleSettings;
import com.apos.xml.generic.ScheduleFreqType;
import com.businessobjects.sdk.plugin.desktop.webi.IWebi;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiProcessingInfo;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleConfigurationTests {

	private static final Logger Log = Logger.getLogger(ExampleConfigurationTests.class);

	// private static final String STR_OBJECTS_CANCEL_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/cancelfile.txt";
	// private static final String STR_SYNC_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/syncfile.txt";
	// private static final String STR_OUTPUT_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/scheduleResults.log";
	// private static final String STR_CUSTOM_SETTINGS_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/customSettings.xml";
	// private static final String STR_CUSTOM_INSTANCES_FILE_NAME =
	// "/Volumes/LionMirror/Temp/ids/instances.txt";
	// private static final String STR_CMS_SESSION_FILE_NAME =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cmsSessionfile.txt";

	private static final String STR_OBJECTS_CANCEL_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cancelfile.txt";
	private static final String STR_SYNC_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/syncfile.txt";
	private static final String STR_OUTPUT_FILE_NAME = "/Users/ygoron/Dropbox/Temp/InfoScheduler/scheduleResults.log";
	// private static final String STR_CUSTOM_SETTINGS_FILE_NAME =
	// "/Users/ygoron/Dropbox/Temp/InfoScheduler/ScheduleWithPrinterOptionsNo.xml";
	// private static final String STR_CUSTOM_SETTINGS_FILE_NAME =
	// "/Users/ygoron/Dropbox/Temp/InfoScheduler/objectpackage.xml";
//	private static final String STR_CUSTOM_SETTINGS_FILE_NAME = "/Users/ygoron/Dropbox/Temp/InfoScheduler/workingcase.xml";
	
	private static final String STR_CUSTOM_SETTINGS_FILE_NAME = "/Volumes/STORAGE1/Temp/bugs/Kevin/Table Not Found Error/Schedule.xml";
	
	
	// private static final String STR_CUSTOM_SETTINGS_FILE_NAME =
	// "/Volumes/STORAGE1/Temp/bugs/Kevin/Schedule.xml";

	// private static final String STR_CUSTOM_SETTINGS_FILE_NAME =
	// "/Users/ygoron/Dropbox/Temp/InfoScheduler/DestinationDisk.xml";

	// private static final String STR_CUSTOM_SETTINGS_JS_FILE_NAME =
	// "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/customSettings.json";
	private static final String STR_CUSTOM_INSTANCES_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/instances.txt";
	private static final String STR_CMS_SESSION_FILE_NAME = "/Users/ygoron/Dropbox/Apos/JavaBI4Projects/ids/cmsSessionfile.txt";

	private static final String STR_TEST_ZIPFILE = "/Volumes/STORAGE1/Temp/bugs/Kevin/wid1130734383867756946.zip";
	private static final String STR_TEST_UNZIPZIPFILE = "/Volumes/STORAGE1/Temp/bugs/Kevin";

	private static final String STR_TEST_XML_PARSING_FILE = "/Users/ygoron/Dropbox/Temp/Test.xml";

//	public static final String STR_CMS_USER_ID = "admin";
//	public static final String STR_CMS_USER_PASSWORD = "apos123";
//	public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
//	public static final String STR_CMS = "win-bi41rampup";
//	// public static final String STR_CMS = "win-eiggairfoum";

	 public static final String STR_CMS_USER_ID = "Administrator";
	 public static final String STR_CMS_USER_PASSWORD = "";
	 public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	 public static final String STR_CMS = "KD-W2K8DBIXI3S2";

	// Diane's system
	// public static final String STR_CMS_USER_ID = "Administrator";
	// public static final String STR_CMS_USER_PASSWORD = "Qwert4";
	// public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	// public static final String STR_CMS = "172.16.1.189";

	// Diane's system 2
	// public static final String STR_CMS_USER_ID = "Administrator";
	// public static final String STR_CMS_USER_PASSWORD = "";
	// public static final String STR_CMS_AUTH_TYPE = "secEnterprise";
	// public static final String STR_CMS = "intkitout31";

	private static String logonToken;
	private static ICommandLineArgs commandLineArgs;
	private static final int INT_MAX_THREADS = 7;
	private static final int INT_PARAM_FLAG = 0;
	// private static final int INT_REPORT_ID = 1033;
	// private static final int INT_REPORT_ID = 38755; // With 1 Prompt
	// private static final int INT_REPORT_ID = 56305; // Webi With 2 Prompts
	// private static final int INT_REPORT_ID = 58252; // Webi With 2 Prompts
	// And Two Prompt Values
	// private static final int INT_REPORT_ID = 98330; // Dianes Test Report
	// (Instance)

	// private static final int INT_REPORT_ID = 58215; // Webi With 2 Prompts
	// And Two Prompt Values (Webi Object)
	// private static final int INT_REPORT_ID = 59423; // Webi With 2 Prompts
	// And Two Prompt Values (Webi Instance Object)
	// private static final int INT_REPORT_ID = 56300; // 4.0 Webi
	// private static final int INT_REPORT_ID = 13373; // 3.1 Webi
	private static final int INT_REPORT_ID = 58215; // Report

	// private static final String STR_INSTANCE_CUID_FOR_CUSTOM_SETTINGS =
	// "AYT5.j.YspFOvkU5vrHB1PU";
	private static final String STR_INSTANCE_CUID_FOR_CUSTOM_SETTINGS = "AVr4euS7UFpGpuj.PZfDUZk";

	// private static final int INT_REPORT_ID = 76039; // Dianes Test Report
	// (Report)

	//
	private static final boolean IS_DELETE_RECURRING = true;
	private static final boolean IS_REPLACE_INSTANCES = false;

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=38606"; // Simple
	// // World
	// // Sales
	// // Report
	// // Scheduling

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=46623"; // CR With 1 Prompt

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=47210"; // CR With 2 Prompts (1
	// String and 1 Date)

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=48183"; // CR Monthly Schedule

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=48203"; // CR Hourly
	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=48233"; // CR PDF

	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=50477"; // CR Schedule to two
	// Inboxes. Default Name
	// private static final String CMS_QUERY =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=55658"; // CR
	// PDF
	// With
	// External
	// Location

	private final static String CMS_QUERY = "SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=" + INT_REPORT_ID; // Webi
																											// Object
																											// in
																											// Webi
																											// Format

	private final static String CMS_QUERY_INSTANCES = "SELECT TOP 1 * FROM CI_INFOOBJECTS WHERE SI_PARENTID=" + INT_REPORT_ID + " AND SI_INSTANCE=1 ORDER BY SI_ENDTIME DESC"; // Instance
																																												// Object

	private static IService scheduler;

	private static IEnterpriseSession enterpriseSession = null;
	@Autowired
	private Jaxb2Marshaller jaxbMarshaller;

	@BeforeClass
	public static void setProperties() throws Exception {

		boolean isSerialized = false;
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		AbstractApplicationContext context = null;

		try {
			enterpriseSession = CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID, STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			logonToken = enterpriseSession.getSerializedSession();
			String sessionEncoded = StringEscapeUtils.escapeJava(logonToken);
			Log.debug("Encoded Session:" + sessionEncoded);
			isSerialized = true;
			commandLineArgs = new CommandLineArgs();

			fileWriter = new FileWriter(STR_CMS_SESSION_FILE_NAME, false);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(logonToken);
			fileWriter.flush();
			fileWriter.close();
			commandLineArgs.setSerializedSessionFile(STR_CMS_SESSION_FILE_NAME);

			commandLineArgs.setMaxBOXIConnections(INT_MAX_THREADS);
			Log.debug("Logon Token:" + logonToken);
			commandLineArgs.setBeanParameters();

			context = new FileSystemXmlApplicationContext("classpath:META-INF/spring/app-context.xml");
			context.registerShutdownHook();
			scheduler = context.getBean(IService.class);
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (enterpriseSession != null && !isSerialized) {
				enterpriseSession.logoff();
				Log.debug("Logoff");
			}
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

	@AfterClass
	public static void logOff() {
		try {
			if (enterpriseSession != null) {
				// enterpriseSession.logoff();
				// Log.debug("Final Logoff");
			}
		} catch (Exception ee) {
			Log.error("Ignore");
		}
	}

	// @Test
	// @Ignore
	// public void testInstanceProperties() throws Exception {
	// String cmsQuery =
	// "SELECT * FROM CI_INFOOBJECTS WHERE SI_CUID='AZiGhmtnM1pNovY6Tf.cNm8'";
	// Log.debug("Running Query:" + CMS_QUERY);
	// IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("",
	// "InfoStore");
	// IInfoObject infoObject = (IInfoObject) infoStore.query(cmsQuery).get(0);
	// com.crystaldecisions.sdk.properties.IProperty property =
	// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties(1).getProperties("SI_INDEX").getProperty(1);
	// Log.debug("Prompts Size:" +
	// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").size());
	// Log.debug("1 Prompt Index Size:" +
	// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties(1).getProperties("SI_INDEX").size());
	// IProperties properties =
	// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties(1).getProperties("SI_INDEX").getProperties(1);
	// Log.debug("Property:" +
	// infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties(1).getProperties("SI_INDEX").getProperties(1).size());
	// }

	@Test
	public void encryptionTest() {
		final String STR_TEST_STRING = "C0n3$+oga";
		String decrypytedString = null;
		try {
			Encryptor encryptor= new Encryptor();
			String encryptedString = new String(Encryptor.encrypt(CommonConstants.KEY, CommonConstants.IV, STR_TEST_STRING.getBytes()));
			Log.debug("Encrypted String:" + encryptedString+"<-");
			decrypytedString = new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, encryptedString.getBytes()));
			Log.debug("Decrypted String:" + decrypytedString+"<-");
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		Log.debug("Source Length:"+STR_TEST_STRING.length());
		Log.debug("Decrypted Length:"+decrypytedString.length());
		assertNotSame("Decryptor Failed!", decrypytedString, STR_TEST_STRING);
//		assertNotSame("Decryptor Failed!",Integer.valueOf(decrypytedString.length()), Integer.valueOf(STR_TEST_STRING.length()));

	}

	@Test
	public void testDecryptionOnly() {
		String decrypytedString = null;
		final String STR_SOURCE_STRING = "TestUserPassW12345";
		final String STR_ENCRYPTED_STRING = "92K7Mz0irHcisaxAlYXvHgyB30caJiUZYJbTk2iluZQ=";
		try {
			Log.debug("Encrypted String:" + STR_ENCRYPTED_STRING);
			Encryptor encryptor= new Encryptor();
			decrypytedString = new String(encryptor.decryptEncoded(CommonConstants.KEY, CommonConstants.IV, STR_ENCRYPTED_STRING.getBytes()));
			Log.debug("Decrypted String:" + decrypytedString+"<-");

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}
		assertNotSame("Decryptor Failed!", decrypytedString, STR_SOURCE_STRING);
		assertNotSame("Decryptor Failed!", decrypytedString.length(), STR_SOURCE_STRING.length());

	}

	@Test
	@Ignore
	public void testZipFile() {
		try {

			ZipFile zipFile = new ZipFile(STR_TEST_ZIPFILE);
			assertNotNull(zipFile);
			zipFile.extractFile("Data/C3/DATAPROVIDERS/DP0/DP_Generic", STR_TEST_UNZIPZIPFILE, null, "qqqq");

			File fileToDelete = new File(zipFile.getFile().getParent() + "/qqqq");
			Log.debug("Delete:" + fileToDelete.getAbsolutePath());
			fileToDelete.deleteOnExit();

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	@Test
	@Ignore
	public void testJAXB() throws FileNotFoundException {
		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
		marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setMarshallerProperties(marshallerProperties);

		XMLTestPojo pojo = new XMLTestPojo();
		pojo.setAppDescription("My Description");
		pojo.setAppName("My Name2");
		FileOutputStream outputStream = new FileOutputStream(new File("/Volumes/LionMirror/Temp/app.xml"));
		StreamResult result = new StreamResult(outputStream);
		jaxbMarshaller.marshal(pojo, result);

		result = new StreamResult(System.out);
		jaxbMarshaller.marshal(pojo, result);

		FileInputStream inputStream = new FileInputStream(new File("/Volumes/LionMirror/Temp/app.xml"));
		StreamSource source = new StreamSource(inputStream);

		XMLTestPojo app = (XMLTestPojo) jaxbMarshaller.unmarshal(source);
		Log.debug("App Name:" + app.getAppName());

	}

	@Test
	@Ignore
	public void prepareXMLForSchedulerCase2() {
		FileOutputStream outputStream = null;
		try {

			Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
			marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setMarshallerProperties(marshallerProperties);

			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			IInfoObjects infoObjects = infoStore.query(CMS_QUERY);
			Log.debug("Found Objects:" + infoObjects.size());
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			Utils utils = new Utils();
			InfoObject xmlInfoObject = utils.getXmlInfoObject(infoStore, infoObject);
			outputStream = new FileOutputStream(new File(STR_CUSTOM_SETTINGS_FILE_NAME));
			StreamResult result = new StreamResult(outputStream);
			jaxbMarshaller.marshal(xmlInfoObject, result);

			StringWriter resultString = new StringWriter();
			Log.debug("UnMarshalling:");
			jaxbMarshaller.marshal(xmlInfoObject, new StreamResult(resultString));
			Log.debug(resultString.toString());

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (Exception ignore) {

				}
		}

	}

	@Test
	@Ignore
	public void testGetObjectIdFromPath() throws Exception {
		Utils utils = new Utils();
		IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
		String testPath = "/Scheduler/GenericXML/With 1 Prompt";
		int id = utils.getObjectIdFromPath(testPath, infoStore);
		assertNotSame(-1, id);
		Log.debug("Report Id:" + id);
	}

	@Test
	@Ignore
	public void prepareXMlForSchedulerCase6() {
		FileOutputStream outputStream = null;

		try {
			Utils utils = new Utils();
			ScheduleJobs scheduleJobs = new ScheduleJobs();

			List<String> accepts = new ArrayList<String>();
			accepts.add(CommonConstants.STR_KIND_CRYSTAL_REPORT);
			accepts.add(CommonConstants.STR_KIND_WEBI);
			accepts.add(CommonConstants.STR_KIND_PROGRAM);
			accepts.add(CommonConstants.STR_KIND_OBJECT_PACKAGE);
			scheduleJobs.setAcceptType(accepts);
			List<ScheduleSettings> scheduleSettings = new ArrayList<ScheduleSettings>();
			Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
			marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setMarshallerProperties(marshallerProperties);

			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			IInfoObjects infoObjects = infoStore.query("select * from Ci_infoobjects where si_CUID='" + STR_INSTANCE_CUID_FOR_CUSTOM_SETTINGS + "'");
			Log.debug("Found Objects:" + infoObjects.size());
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			if (infoObject.getKind().equalsIgnoreCase("Webi")) {
				IWebiProcessingInfo webiProcessingInfo = (IWebiProcessingInfo) infoObject;
				if (webiProcessingInfo.hasPrompts()) {
					WebiRefresh webiRefresh = new WebiRefresh();
					webiRefresh.updatePromptsForScheduling(infoObject);
					// WebiRefresh.updatePromptsForScheduling(infoObject);
				}

			}

			ScheduleSettings xmlSchedulingInfo = utils.getSchedulingInfo(infoObject, infoStore, INT_REPORT_ID);
			ScheduleFreqType xmlScheduleFreqType = utils.getScheduleType(infoObject.getSchedulingInfo(), infoStore);

			xmlSchedulingInfo.setScheduleFreqType(xmlScheduleFreqType);
			xmlSchedulingInfo = utils.getDestinations(infoObject, xmlSchedulingInfo, infoStore);

			// List<CustomProperty> customProperties= new
			// ArrayList<CustomProperty>();
			//
			// CustomProperty customProperty = new CustomProperty();
			// customProperty.setName("Name1");
			// customProperty.setValue("Value1");
			// customProperties.add(customProperty);
			//
			// customProperty = new CustomProperty();
			// customProperty.setName("Name2");
			// customProperty.setValue("Value2");
			// customProperties.add(customProperty);
			//
			// xmlSchedulingInfo.setCustomProperties(customProperties);

			scheduleSettings.add(xmlSchedulingInfo);

			scheduleJobs.setScheduleSettings(scheduleSettings);

			outputStream = new FileOutputStream(new File(STR_CUSTOM_SETTINGS_FILE_NAME));
			StreamResult result = new StreamResult(outputStream);
			jaxbMarshaller.marshal(scheduleJobs, result);

			StringWriter resultString = new StringWriter();
			Log.debug("UnMarshalling:");
			jaxbMarshaller.marshal(scheduleJobs, new StreamResult(resultString));
			Log.debug(resultString.toString());

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		} finally {
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (Exception ignore) {

				}

		}

	}

	/**
	 * 0. Schedule Type <br>
	 * 1. Parameter Flag<br>
	 * (0 - no warnings; 1- Warning - Report to be scheduled but you did not
	 * provide values for the parameter " 2- Report will not scheduled - not
	 * parameters <br>
	 * 2. CMS Session Ð Path to CMS Session File <br>
	 * 3. Path to XML file that has all schedule settings <br>
	 * 4. Cancel File Path Ð string Ð path to the cancel.txt file that tells
	 * java to stop <br>
	 * 5. Sync File Path Ð string Ð path to the sync.txt file java creates when
	 * finished<br>
	 * 6. Results File Path Ð string Ð path to the results.txt file java writes
	 * results to<br>
	 */
	@Test
	public void testScheduleTest6() {

		List<String> argsList = new ArrayList<String>();

		argsList.add(0, String.valueOf(ScheduleType.BATCH_SCHEDULING.getCode()));
		argsList.add(1, String.valueOf(INT_PARAM_FLAG));
		argsList.add(2, STR_CMS_SESSION_FILE_NAME);

		argsList.add(3, String.valueOf(STR_CUSTOM_SETTINGS_FILE_NAME));
		argsList.add(4, STR_OBJECTS_CANCEL_FILE_NAME);
		argsList.add(5, STR_SYNC_FILE_NAME);
		argsList.add(6, STR_OUTPUT_FILE_NAME);
		String[] args = argsList.toArray(new String[argsList.size()]);

		String commandLine = "";

		for (String arg : argsList) {
			commandLine += "\"" + arg + "\" ";
		}
		Log.info("Command Line:" + commandLine);

		SchedulerMain schedulerMain = new SchedulerMain();
		commandLineArgs = SchedulerMain.getArgs(args);
		schedulerMain.execute(commandLineArgs, scheduler);

	}

	@Test
	@Ignore
	public void testScheduleWebiWithParameters() {

		try {
			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			IInfoObjects infoObjects = infoStore.query("select * from Ci_infoobjects where si_id=" + INT_REPORT_ID);
			Log.debug("Found Objects:" + infoObjects.size());
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);
			int totalPrompts = infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getInt("SI_TOTAL");
			Log.debug("Total Prompts:" + totalPrompts);
			// assertNotSame(0, totalPrompts);
			WebiRefresh webiRefresh = new WebiRefresh();
			// WebiRefresh.updatePromptsForScheduling(infoObject);
			webiRefresh.updatePromptsForScheduling(infoObject);

			totalPrompts = infoObject.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getInt("SI_TOTAL");
			IWebi webi = (IWebi) infoObject;
			Log.debug("Total Prompts After Refresh:" + webi.getPrompts().size());

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	public void deserializerTest() {

		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
		marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setMarshallerProperties(marshallerProperties);
		Source source = new StreamSource(new File(STR_CUSTOM_SETTINGS_FILE_NAME));
		ScheduleJobs scheduleJobs = (ScheduleJobs) jaxbMarshaller.unmarshal(source);
		StreamResult result = new StreamResult(System.out);
		Log.debug("UnMarshalling:");
		jaxbMarshaller.marshal(scheduleJobs, result);

	}

	@Test
	@Ignore
	public void deserializerTest2() {

		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
		marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setMarshallerProperties(marshallerProperties);
		Source source = new StreamSource(new File(STR_TEST_XML_PARSING_FILE));
		ScheduleJobs scheduleJobs = (ScheduleJobs) jaxbMarshaller.unmarshal(source);
		StreamResult result = new StreamResult(System.out);
		Log.debug("UnMarshalling:");
		jaxbMarshaller.marshal(scheduleJobs, result);

	}

	@Test
	@Ignore
	public void scheduleType2Concept() {

		try {
			Source source = new StreamSource(new File(STR_CUSTOM_SETTINGS_FILE_NAME));
			InfoObject xmlInfoObject = (InfoObject) jaxbMarshaller.unmarshal(source);
			StreamResult result = new StreamResult(System.out);
			Log.debug("UnMarshalling:");
			jaxbMarshaller.marshal(xmlInfoObject, result);

			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");
			IInfoObjects infoObjects = infoStore.query(CMS_QUERY);
			IInfoObject infoObject = (IInfoObject) infoObjects.get(0);

			if (xmlInfoObject.getSchedulingInfo() != null) {
				SchedulingInfo xmlSchedulingInfo = xmlInfoObject.getSchedulingInfo();
				if (xmlSchedulingInfo.getInstanceName() != null) {
					infoObject.setTitle(xmlSchedulingInfo.getInstanceName());
					Log.debug("Insance name set to:" + infoObject.getTitle());
				}
			}

			infoStore.schedule(infoObjects);

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

	}

	/**
	 * 
	 * 0. Schedule Type <br>
	 * 1. Path to file with Serialized Session <br>
	 * 2. Report ID Ð integer Ð id of the report object to be scheduled <br>
	 * 3. Cancel File Path - string Ð path to the cancel.txt file that tells
	 * java to stop<br>
	 * 4. Sync File Path Ð string Ð path to the sync.txt file java creates when
	 * finished<br>
	 * 5. Results File Path Ð string Ð path to the results.txt file java writes
	 * results to<br>
	 * 6. Instances File Path Ð string Ð path to instances.txt file for java to
	 * read. File will contain carriage return delimited list of instance ids<br>
	 * 7. Delete Recurring Ð Boolean Ð should the existing recurring schedules
	 * be removed<br>
	 * 8. Replace Instances Ð Boolean Ð should the provided instance be replaced
	 * with the new scheduled one<br>
	 */
	@Test
	@Ignore
	public void testSchedulerCase3() {

		Log.debug("Create Instance File");
		// createInstanceIDList();

		List<String> argsList = new ArrayList<String>();

		argsList.add(0, String.valueOf(ScheduleType.SCHEDULE_FROM_SETTINGS_IN_INSTANCE.getCode()));
		argsList.add(1, STR_CMS_SESSION_FILE_NAME);
		argsList.add(2, String.valueOf(INT_REPORT_ID));
		argsList.add(3, STR_OBJECTS_CANCEL_FILE_NAME);
		argsList.add(4, STR_SYNC_FILE_NAME);
		argsList.add(5, STR_OUTPUT_FILE_NAME);
		argsList.add(6, STR_CUSTOM_INSTANCES_FILE_NAME);
		argsList.add(7, String.valueOf(IS_DELETE_RECURRING));
		argsList.add(8, String.valueOf(IS_REPLACE_INSTANCES));
		String[] args = argsList.toArray(new String[argsList.size()]);

		String commandLine = "";

		for (String arg : argsList) {
			commandLine += "\"" + arg + "\" ";
		}
		Log.info("Command Line:" + commandLine);

		SchedulerMain schedulerMain = new SchedulerMain();
		commandLineArgs = SchedulerMain.getArgs(args);

		schedulerMain.execute(commandLineArgs, scheduler);

	}

	/**
	 * 0. Schedule Type <br>
	 * 1. Path to file with Serialized Session <br>
	 * 2. Report ID Ð integer Ð id of the report object to be scheduled <br>
	 * 3. Cancel File Path Ð string Ð path to the cancel.txt file that tells
	 * java to stop <br>
	 * 4. Sync File Path Ð string Ð path to the sync.txt file java creates when
	 * finished <br>
	 * 5. Results File Path Ð string Ð path to the results.txt file java writes
	 * results to <br>
	 * 6. Custom Settings Path -- string Ð path to xml file for java to read.
	 * File will contain settings for scheduling<br>
	 * 7. Delete Recurring Ð Boolean Ð should the existing recurring schedules
	 * be removed
	 */
	@Test
	@Ignore
	public void testSchedulerCase2() {
		List<String> argsList = new ArrayList<String>();

		argsList.add(0, String.valueOf(ScheduleType.SCHEDULE_DEFAULT_AND_OVERRIDE.getCode()));
		argsList.add(1, STR_CMS_SESSION_FILE_NAME);
		argsList.add(2, String.valueOf(INT_REPORT_ID));
		argsList.add(3, STR_OBJECTS_CANCEL_FILE_NAME);
		argsList.add(4, STR_SYNC_FILE_NAME);
		argsList.add(5, STR_OUTPUT_FILE_NAME);
		argsList.add(6, STR_CUSTOM_SETTINGS_FILE_NAME);
		argsList.add(7, String.valueOf(IS_DELETE_RECURRING));
		String[] args = argsList.toArray(new String[argsList.size()]);

		String commandLine = "";

		for (String arg : argsList) {
			commandLine += "\"" + arg + "\" ";
		}
		Log.info("Command Line:" + commandLine);

		SchedulerMain schedulerMain = new SchedulerMain();
		commandLineArgs = SchedulerMain.getArgs(args);

		schedulerMain.execute(commandLineArgs, scheduler);

	}

	/**
	 * 1. CMS Token Ð string -- BOE login token string <br>
	 * 2. Report ID Ð integer Ð id of the report object to be scheduled <br>
	 * 3. Cancel File Path Ð string Ð path to the cancel.txt file that tells
	 * java to stop <br>
	 * 4. Sync File Path Ð string Ð path to the sync.txt file java creates when
	 * finished <br>
	 * 5. Results File Path Ð string Ð path to the results.txt file java writes
	 * results to <br>
	 * 6. Delete Recurring Ð Boolean Ð should the existing recurring schedules
	 * be removed
	 */
	@Test
	@Ignore
	public void testSchedulerCase1() {
		List<String> argsList = new ArrayList<String>();

		argsList.add(0, String.valueOf(ScheduleType.SCHEDULE_DEFAULT.getCode()));
		argsList.add(1, logonToken);
		argsList.add(2, String.valueOf(INT_REPORT_ID));
		argsList.add(3, STR_OBJECTS_CANCEL_FILE_NAME);
		argsList.add(4, STR_SYNC_FILE_NAME);
		argsList.add(5, STR_OUTPUT_FILE_NAME);
		argsList.add(6, String.valueOf(IS_DELETE_RECURRING));
		String[] args = argsList.toArray(new String[argsList.size()]);

		String commandLine = "";

		for (String arg : argsList) {
			commandLine += "\"" + arg + "\" ";
		}
		Log.info("Command Line:" + commandLine);

		SchedulerMain schedulerMain = new SchedulerMain();
		commandLineArgs = SchedulerMain.getArgs(args);

		schedulerMain.execute(commandLineArgs, scheduler);

	}

	@SuppressWarnings("unused")
	private void createInstanceIDList() {

		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		// IEnterpriseSession enterpriseSession = null;
		try {
			// enterpriseSession =
			// CrystalEnterprise.getSessionMgr().logon(STR_CMS_USER_ID,
			// STR_CMS_USER_PASSWORD, STR_CMS, STR_CMS_AUTH_TYPE);
			Log.debug("Running Query:" + CMS_QUERY);
			IInfoStore infoStore = (IInfoStore) enterpriseSession.getService("", "InfoStore");

			IInfoObjects infoObjects = infoStore.query(CMS_QUERY_INSTANCES);

			Log.debug("Found Objects:" + infoObjects.size());
			fileWriter = new FileWriter(STR_CUSTOM_INSTANCES_FILE_NAME, false);
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

}
