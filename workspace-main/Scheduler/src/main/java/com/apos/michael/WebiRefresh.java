package com.apos.michael;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.businessobjects.sdk.plugin.desktop.webi.IWebi;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.properties.IProperties;
import com.crystaldecisions.sdk.properties.IProperty;

public class WebiRefresh {
	private static final Logger Log = Logger.getLogger(WebiRefresh.class);
	private static Map<String, APOSWebiPromptsPrompt> webiPromptCol = new HashMap<String, APOSWebiPromptsPrompt>();
	private static Map<String, APOSWebiPromptsProvider> webiDPCol = new HashMap<String, APOSWebiPromptsProvider>();

	public boolean updatePromptsForScheduling(IInfoObject obj) throws APOSWebiPromptsException {
		if (obj == null)
			throw new APOSWebiPromptsException("Error checking for prompt refresh - Invalid object");

		boolean rc = false;

		APOSWebiPromptsPrompt prompt = null;

		if (getPromptsProperties(obj) == true) {
			try {
				obj.getProcessingInfo().properties().add("SI_WEBI_PROMPTS", "", IProperty.BAG);

				int promptCount = 0; // Just to keep track of iteration
				int dpCount = 0;
				IProperties siWebiPromptProps = null;

				// for (Map.Entry<String, APOSWebiPromptsPrompt> entry :
				// webiPromptCol.entrySet()) {
				for (ConcurrentHashMap.Entry<String, APOSWebiPromptsPrompt> entry : webiPromptCol.entrySet()) {
					promptCount++;
					obj.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").add(promptCount, "", IProperty.BAG);
					prompt = entry.getValue();

					siWebiPromptProps = obj.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").getProperties(promptCount);
					siWebiPromptProps.add("SI_NAME", prompt.webiPrompt_PID, IProperty.ALL);
					siWebiPromptProps.add("SI_DISABLED", false, IProperty.ALL);

					siWebiPromptProps.add("SI_VALUES", "", IProperty.BAG);
					siWebiPromptProps.getProperties("SI_VALUES").add("1", "", IProperty.ALL);
					siWebiPromptProps.getProperties("SI_VALUES").add("SI_TOTAL", 1, IProperty.ALL);

					siWebiPromptProps.add("SI_INDEX", "", IProperty.BAG);
					siWebiPromptProps.getProperties("SI_INDEX").add("1", "()", IProperty.ALL);
					siWebiPromptProps.getProperties("SI_INDEX").add("SI_TOTAL", 1, IProperty.ALL);

					siWebiPromptProps.add("SI_DATAPROVIDERS", "", IProperty.BAG);

					List<String> addedProviders = new ArrayList<String>();
					dpCount = 0; // Just keep track of count
					for (String dpID : prompt.DPIDList) {
						if (addedProviders.contains(dpID) == false) {
							dpCount++;
							APOSWebiPromptsProvider dp = webiDPCol.get(dpID);
							siWebiPromptProps.getProperties("SI_DATAPROVIDERS").add(Integer.toString(dpCount), "", IProperty.BAG);
							if (dp != null) {
								// System.out.println("dp.webiDP_DPID:" +
								// dp.webiDP_DPID);
								// Log.error("dp.webiDP_DPID:" +
								// dp.webiDP_DPID);

								siWebiPromptProps.getProperties("SI_DATAPROVIDERS").getProperties(Integer.toString(dpCount)).add("SI_NAME", dp.webiDP_DPName, IProperty.ALL);
								siWebiPromptProps.getProperties("SI_DATAPROVIDERS").getProperties(Integer.toString(dpCount)).add("SI_ID", dp.webiDP_DPID, IProperty.ALL);
							} else {
								Log.error("Data Provider is Null");
							}
							addedProviders.add(dpID);
						}
					}

				}
				if (dpCount > 0)
					siWebiPromptProps.getProperties("SI_DATAPROVIDERS").add("SI_TOTAL", dpCount, IProperty.ALL);

				try {
					obj.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS").add("SI_TOTAL", Integer.toString(promptCount), IProperty.ALL);
				} catch (Exception ee) {
					Log.error(ee.getLocalizedMessage(), ee);
				}
				rc = true;
			} catch (Exception e) {
				Log.error(e.getLocalizedMessage(), e);
				throw new APOSWebiPromptsException("Error checking for prompt refresh - " + e.getMessage());
			}
		}

		return rc;
	}

	private static boolean promptsRefreshRequired(IInfoObject obj) throws APOSWebiPromptsException {
		if (obj == null)
			throw new APOSWebiPromptsException("Error checking for prompt refresh - Invalid object");

		boolean rc = false;

		try {
			boolean hasPrompts = Boolean.parseBoolean(obj.getProcessingInfo().properties().getProperty("SI_HAS_PROMPTS").getValue().toString());
			IProperties totalPromptsProp = obj.getProcessingInfo().properties().getProperties("SI_WEBI_PROMPTS");
			int totalPrompts = Integer.parseInt(totalPromptsProp.getProperty("SI_TOTAL").getValue().toString());

			if (hasPrompts && totalPrompts == 0)
				rc = true;
		} catch (Exception e) {
			throw new APOSWebiPromptsException("Error checking for prompt refresh - " + e.getMessage());
		}

		return rc;
	}

	private static boolean getPromptsProperties(IInfoObject obj) throws APOSWebiPromptsException {
		if (obj == null)
			throw new APOSWebiPromptsException("Error checking for prompt refresh - Invalid object");

		boolean rc = false;

		if (promptsRefreshRequired(obj)) {
			try {
				boolean isUNX = WebiHelper.isUNX((IWebi) obj);

				String xml = obj.properties().getProperty("SI_WEBI_DOC_PROPERTIES").getValue().toString();
				if (xml != null && xml.length() > 0) {
					Element root = WebiHelper.loadXML(xml);

					if (root != null) {
						// Refresh hashmaps
						webiDPCol.clear();
						webiPromptCol.clear();

						// Parse WEBI_DP elements
						NodeList webiDPs = root.getElementsByTagName("WEBI_DP");
						if (webiDPs != null && webiDPs.getLength() > 0) {
							Element webiDP = null;
							APOSWebiPromptsProvider dp = null;
							for (int i = 0; i < webiDPs.getLength(); i++) {
								if (webiDPs.item(i).getNodeType() == Node.ELEMENT_NODE) {
									// Read attributes
									webiDP = (Element) webiDPs.item(i);
									dp = new APOSWebiPromptsProvider();
									dp.webiDP_DPID = webiDP.getAttribute("DPID");
									dp.webiDP_DPName = webiDP.getAttribute("DPNAME");
									dp.webiDP_DSID = webiDP.getAttribute("DSID");
									dp.webiDP_DSName = webiDP.getAttribute("DSNAME");

									// Split and read each part of DSID
									// attribute
									if (dp.webiDP_DSID.length() > 0) {
										for (String dsID : dp.webiDP_DSID.split(";")) {
											if (dsID.startsWith("UnivCUID") && dsID.contains("="))
												dp.webiDP_DSID_UnivCUID = dsID.substring(dsID.indexOf("=") + 1);
											if (dsID.startsWith("UnivID") && dsID.contains("="))
												dp.webiDP_DSID_UnivID = dsID.substring(dsID.indexOf("=") + 1);
											if (dsID.startsWith("ShortName") && dsID.contains("="))
												dp.webiDP_DSID_ShortName = dsID.substring(dsID.indexOf("=") + 1);
											if (dsID.startsWith("UnivName") && dsID.contains("="))
												dp.webiDP_DSID_UnivName = dsID.substring(dsID.indexOf("=") + 1);
										}
									}
									// Add to member HashMap
									webiDPCol.put(dp.webiDP_DPID, dp);
								}
							}
						}

						// Parse WEBI_PROMPT elements
						NodeList webiPrompts = root.getElementsByTagName("WEBI_PROMPT");
						if (webiPrompts != null && webiPrompts.getLength() > 0) {
							Element webiPrompt = null;
							APOSWebiPromptsPrompt prompt = null;
							for (int i = 0; i < webiPrompts.getLength(); i++) {
								if (webiPrompts.item(i).getNodeType() == Node.ELEMENT_NODE) {
									// Read attributes
									webiPrompt = (Element) webiPrompts.item(i);
									prompt = new APOSWebiPromptsPrompt();

									if (isUNX)
										prompt.webiPrompt_PID = WebiHelper.getPIDFromUNXFile(obj, webiPrompt.getAttribute("PID"));
									else
										prompt.webiPrompt_PID = webiPrompt.getAttribute("PID");

									prompt.webiPrompt_Question = webiPrompt.getAttribute("QUESTION");
									prompt.webiPrompt_Locale = webiPrompt.getAttribute("LOCALE");
									prompt.webiPrompt_Optional = webiPrompt.getAttribute("OPTIONAL");
									prompt.webiPrompt_Persistent = webiPrompt.getAttribute("PERSISTENT");
									prompt.webiPrompt_IndexAware = webiPrompt.getAttribute("INDEXAWARE");
									prompt.webiPrompt_Order = webiPrompt.getAttribute("ORDER");
									prompt.webiPrompt_Response = webiPrompt.getAttribute("RESPONSE");
									prompt.webiPrompt_Origin = webiPrompt.getAttribute("ORIGIN");
									prompt.webiPrompt_Type = webiPrompt.getAttribute("TYPE");

									// Loop through Element's DPID elements
									NodeList dpIDs = webiPrompt.getElementsByTagName("DPID");
									if (dpIDs != null && dpIDs.getLength() > 0) {
										prompt.DPIDList = new ArrayList<String>();
										for (int j = 0; j < dpIDs.getLength(); j++) {
											// Add Element's value to array
											if (dpIDs.item(j).getNodeType() == Node.ELEMENT_NODE)
												prompt.DPIDList.add(dpIDs.item(j).getFirstChild().getNodeValue().trim());
										}
									}

									// Add to member HashMap
									webiPromptCol.put(prompt.webiPrompt_PID, prompt);
								}
							}
						}
						rc = true;
					}
				}
			} catch (Exception e) {
				Log.error(e.getLocalizedMessage(), e);
				throw new APOSWebiPromptsException("Error checking for prompt refresh - " + e.getMessage());
			}
		}

		return rc;
	}
}
