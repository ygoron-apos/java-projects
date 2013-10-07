package com.apos.michael;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.businessobjects.sdk.plugin.desktop.webi.IWebi;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiPrompt;
import com.businessobjects.sdk.plugin.desktop.webi.internal.IWebiPrompts;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.IRemoteFile;

public class WebiHelper {
	private static final Logger Log = Logger.getLogger(WebiHelper.class);

	public static List<WebiPromptWrapper> getPrompts(IInfoObject object) throws APOSWebiPromptsException {
		return getPrompts((IWebi) object);
	}

	public static List<WebiPromptWrapper> getPrompts(IWebi webi) throws APOSWebiPromptsException {
		List<WebiPromptWrapper> rc = null;

		try {
			// First, check if webi has prompts
			if (webi.getPrompts() != null && webi.getPrompts().size() > 0) {
				rc = parseWebiPrompts(webi);
			} else if (webi.properties().getProperty("SI_WEBI_DOC_PROPERTIES") != null) {
				rc = parseWebiDocProperties(webi);
			}
		} catch (SDKException e) {
			throw new APOSWebiPromptsException("Error checking for prompt refresh - " + e.getMessage());
		}

		return rc;
	}

	private static List<WebiPromptWrapper> parseWebiPrompts(IWebi webi) throws SDKException {
		List<WebiPromptWrapper> rc = new ArrayList<WebiPromptWrapper>();

		IWebiPrompts webiPrompts = (IWebiPrompts) webi.getPrompts();

		for (int i = 0; i < webiPrompts.size(); i++) {
			IWebiPrompt webiPrompt = (IWebiPrompt) webiPrompts.get(i);
			WebiPromptWrapper prompt = new WebiPromptWrapper();
			prompt.setUnx(isUNX(webi));

			if (prompt.isUnx()) {
				prompt.setName(webiPrompt.getName().substring(2));
				prompt.setPrefix(webiPrompt.getName().substring(0, 2));
			} else {
				prompt.setName(webiPrompt.getName());
			}
			rc.add(prompt);
		}

		return rc;
	}

	private static List<WebiPromptWrapper> parseWebiDocProperties(IWebi webi) throws APOSWebiPromptsException {
		List<WebiPromptWrapper> rc = new ArrayList<WebiPromptWrapper>();

		// If doc properties exist, parse it.
		String xml = webi.properties().getProperty("SI_WEBI_DOC_PROPERTIES").getValue().toString();
		if (xml != null && xml.length() > 0) {
			Element root = loadXML(xml);
			if (root != null) {
				// Check if webi is using a UNX universe
				boolean isUNX = isUNX(webi);

				// Parse WEBI_PROMPT elements
				NodeList webiPrompts = root.getElementsByTagName("WEBI_PROMPT");
				if (webiPrompts != null && webiPrompts.getLength() > 0) {
					Element webiPrompt = null;
					for (int i = 0; i < webiPrompts.getLength(); i++) {
						if (webiPrompts.item(i).getNodeType() == Node.ELEMENT_NODE) {
							// Create new prompt wrapper
							WebiPromptWrapper prompt = new WebiPromptWrapper();
							prompt.setUnx(isUNX);

							// Read attributes
							webiPrompt = (Element) webiPrompts.item(i);

							if (isUNX) {
								String pid = getPIDFromUNXFile(webi, webiPrompt.getAttribute("PID"));
								prompt.setName(pid.substring(2));
								prompt.setPrefix(pid.substring(0, 2));
							} else {
								prompt.setName(webiPrompt.getAttribute("PID"));
							}

							rc.add(prompt);
						}
					}
				}
			}
		}

		return rc;
	}

	public static String getPIDFromUNXFile(IInfoObject obj, String parameterName) throws APOSWebiPromptsException {
		String rc = "";

		try {
			if (obj.getFiles().size() > 0 && obj.getFiles().get(0) != null) {
				IRemoteFile wid = (IRemoteFile) obj.getFiles().get(0);

				// Create a temporary file to store the WID to.
				File tempFile = File.createTempFile("wid", ".zip");
				tempFile.deleteOnExit();
				Log.debug("File Locaton:" + tempFile.getAbsolutePath());
				// Write the WID to the temporary file
				InputStream in = null;
				OutputStream out = null;
				try {
					in = wid.getInputStream();
					out = new FileOutputStream(tempFile);
					int len;
					byte buf[] = new byte[1024];
					while ((len = in.read(buf)) > 0)
						out.write(buf, 0, len);
				} finally {
					if (in != null)
						in.close();
					if (out != null) {
						out.flush();
						out.close();
					}
				}

				// ZipFile zipFile = new ZipFile(tempFile);
				// Enumeration<? extends ZipEntry> e = zipFile.entries();
				// StringBuffer widString = new StringBuffer();
				// while (e.hasMoreElements()) {
				// ZipEntry zipEntry = (ZipEntry) e.nextElement();
				// if
				// (zipEntry.getName().endsWith("Data/C3/DATAPROVIDERS/DP0/DP_Generic"))
				// {
				// InputStream inputStream = zipFile.getInputStream(zipEntry);
				// int b;
				// while ((b = inputStream.read()) != -1) {
				// widString.append((char) b);
				// }
				// }
				// }
				//

				ZipFile zipFile = new ZipFile(tempFile);
				Log.debug("OutputFolder:" + tempFile.getParent());
				zipFile.extractFile("Data/C3/DATAPROVIDERS/DP0/DP_Generic", tempFile.getParent(), null, "DP_Generic");
				File fileDPGeneric = new File(tempFile.getParent() + "/DP_Generic");
				Log.debug("Open File:" + fileDPGeneric.getAbsolutePath());
				fileDPGeneric.deleteOnExit();

				StringBuffer widString = new StringBuffer();
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(fileDPGeneric);
					int b;
					while ((b = inputStream.read()) != -1) {
						widString.append((char) b);
					}
				} finally {
					if (inputStream != null)
						inputStream.close();
				}

				Log.debug("widString:" + widString);
				String xml = widString.substring(widString.indexOf("<PARAMSERVER>"), widString.indexOf("</PARAMSERVER>") + 14);

				Element root = loadXML(xml);

				if (root != null) {
					boolean matchFound = false;
					NodeList parameterNodes = root.getElementsByTagName("PARAMETER");
					Log.debug("Searching for Parameter:" + parameterName);
					for (int i = 0; i < parameterNodes.getLength() && !matchFound; i++) {
						Element parameterNode = (Element) parameterNodes.item(i);
						if (parameterNode.getAttribute("NAME") != null && parameterNode.getAttribute("NAME").equals(parameterName)) {
							NodeList promptInfoNodes = parameterNode.getElementsByTagName("PROMPTINFO");
							for (int x = 0; x < promptInfoNodes.getLength() && !matchFound; x++) {
								Element promptInfoNode = (Element) promptInfoNodes.item(x);
								if (promptInfoNode.getAttribute("ACTIVE") != null && promptInfoNode.getAttribute("ACTIVE").equals("true")) {
									rc = parameterNode.getAttribute("ID");
									matchFound = true;
									Log.debug("Match Found:" + rc);
								}
							}
						}
					}
				}
			}
		} catch (SDKException e) {
			Log.error(e.getLocalizedMessage(), e);
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			Log.error(e.getLocalizedMessage(), e);
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (IOException e) {
			Log.error(e.getLocalizedMessage(), e);
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (FactoryConfigurationError e) {
			Log.error(e.getLocalizedMessage(), e);
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (ZipException e) {
			Log.error(e.getLocalizedMessage(), e);
			throw new APOSWebiPromptsException(e.getMessage(), e);
		}

		return rc;
	}

	public static boolean isUNX(IWebi webi) {
		boolean rc = false;

		if (webi.properties().getProperties("SI_UNIVERSE") != null && webi.properties().getProperties("SI_UNIVERSE").size() > 0
				&& webi.properties().getProperties("SI_UNIVERSE").getProperty("SI_TOTAL") != null
				&& Integer.parseInt(webi.properties().getProperties("SI_UNIVERSE").getProperty("SI_TOTAL").getValue().toString()) == 0) {
			rc = true;
		}

		return rc;
	}

	public static WebiPromptWrapper findPrompt(List<WebiPromptWrapper> list, String name) {
		WebiPromptWrapper rc = null;

		for (WebiPromptWrapper prompt : list) {
			if (prompt.getName().equals(name)) {
				rc = prompt;
				break;
			}
		}

		return rc;
	}

	public static Element loadXML(String xml) throws APOSWebiPromptsException {
		Element root = null;

		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;
		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(new InputSource(new StringReader(xml)));
		} catch (ParserConfigurationException e) {
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new APOSWebiPromptsException(e.getMessage(), e);
		} catch (IOException e) {
			throw new APOSWebiPromptsException(e.getMessage(), e);
		}

		if (dom != null) {
			root = dom.getDocumentElement();
			if (root == null)
				throw new APOSWebiPromptsException("Unable to parse xml for webiRefresh.");
		} else
			throw new APOSWebiPromptsException("Unable to parse xml for webiRefresh.");

		return root;
	}

	public static void main(String[] s) {
		try {
			ISessionMgr sm = CrystalEnterprise.getSessionMgr();
			IEnterpriseSession es = sm.logon("Administrator", "admin1", "bi4n", "secEnterprise");
			IInfoStore iStore = (IInfoStore) es.getService("", "InfoStore");

			// 31013 - StatesReportNonRefreshed
			// 30915 - StatesReportRefreshed
			// 35967 - UNXReportNonRefreshed
			// 41050 - UNXReportRefreshed
			String query = "SELECT TOP 1 * FROM CI_INFOOBJECTS WHERE SI_ID = 31013";
			IInfoObjects objs = iStore.query(query);
			IInfoObject obj = (IInfoObject) objs.get(0);
			System.out.println("StatesReportNonRefreshed" + getPrompts(obj));

			query = "SELECT TOP 1 * FROM CI_INFOOBJECTS WHERE SI_ID = 30915";
			objs = iStore.query(query);
			obj = (IInfoObject) objs.get(0);
			System.out.println("StatesReportRefreshed" + getPrompts(obj));

			query = "SELECT TOP 1 * FROM CI_INFOOBJECTS WHERE SI_ID = 35967";
			objs = iStore.query(query);
			obj = (IInfoObject) objs.get(0);
			System.out.println("UNXReportNonRefreshed" + getPrompts(obj));

			query = "SELECT TOP 1 * FROM CI_INFOOBJECTS WHERE SI_ID = 41050";
			objs = iStore.query(query);
			obj = (IInfoObject) objs.get(0);
			System.out.println("UNXReportRefreshed" + getPrompts(obj));

		} catch (SDKException e) {
			e.printStackTrace();
		} catch (APOSWebiPromptsException e) {
			e.printStackTrace();
		}
	}
}
