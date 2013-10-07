/**
 * 
 */
package com.apos.infoobject.properties;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.apos.infoobject.xml.WebiFormatOptions;
import com.apos.infoobject.xml.WebiPrompt;
import com.apos.infoobject.xml.WebiPromptValue;
import com.businessobjects.sdk.plugin.desktop.fullclient.IFullClientProcessingInfo;
import com.businessobjects.sdk.plugin.desktop.fullclient.IFullClientPrompt;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiProcessingInfo;
import com.businessobjects.sdk.plugin.desktop.webi.IWebiPrompt;
import com.crystaldecisions.sdk.occa.infostore.IContent;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.plugin.CeKind;

/**
 * @author Yuri Goron
 * 
 *         Process Webi 31
 * 
 */
public class Webi31Processor implements IWebiXMLProcessor {

	private static final Logger Log = Logger.getLogger(Webi31Processor.class);
	public static final String STR_PROMPT_EMPTY_VALUE = "^SKIPWEBIPROMPT^";

	public PluginInterfaceWebi getWebiPluginInterface(IInfoObject infoObject) {
		Log.debug("Processing Webi for BOXI 3.1");
		PluginInterfaceWebi pluginInterfaceWebi = new PluginInterfaceWebi();


		try {
			IWebiProcessingInfo webi = null;
			CommonThreadTools commonThreadTools = new CommonThreadTools();
			if (!infoObject.getKind().equals(CeKind.WEBI)) {
				IContent content = (IContent) infoObject;
				webi = (IWebiProcessingInfo) content.getPluginProcessingInterface(CeKind.WEBI);
			} else {
				webi = (IWebiProcessingInfo) infoObject;
			}

			if (webi.getWebiFormatOptions() != null) {
				WebiFormatOptions webiFormatOptions = new WebiFormatOptions();
				webiFormatOptions.setFormat(webi.getWebiFormatOptions().getFormat());
				webiFormatOptions.setFormatString(commonThreadTools.getWebiStringFormat(webi.getWebiFormatOptions()));
				Log.debug("Webi Format Option:" + webiFormatOptions.getFormat());
				pluginInterfaceWebi.setWebiFormatOptions(webiFormatOptions);
			}

			if (webi.getPrecacheTypes() != null) {
				if (webi.getPrecacheTypes().size() > 0) {
					try {
						pluginInterfaceWebi.setPreCacheTypes(Integer.valueOf(webi.getPrecacheTypes().get(0).toString()));
						Log.debug("PreCache Type:" + pluginInterfaceWebi.getPreCacheTypes());

					} catch (Exception ee) {
						Log.error(ee.getLocalizedMessage(), ee);
					}
				} else {
					Log.debug("Pre Cache Value Size=0");
				}
			}

			

			if (webi.hasPrompts()) {
				List<WebiPrompt> xmlPrompts = new ArrayList<WebiPrompt>();

				for (int i = 0; i < webi.getPrompts().size(); i++) {
					IWebiPrompt webiPrompt = (IWebiPrompt) webi.getPrompts().get(i);
					WebiPrompt xmlPrompt = new WebiPrompt();
					xmlPrompt.setName(webiPrompt.getName());
					List<WebiPromptValue> xmlPromptValues = new ArrayList<WebiPromptValue>();
					if (webiPrompt.getValues().size() == 0) {
						WebiPromptValue xmlPromptValue = new WebiPromptValue();
						xmlPromptValue.setValue(STR_PROMPT_EMPTY_VALUE);
						xmlPromptValues.add(xmlPromptValue);
					}
					for (int j = 0; j < webiPrompt.getValues().size(); j++) {
						WebiPromptValue xmlPromptValue = new WebiPromptValue();
						if (webiPrompt.getValues() != null) {
							String parameterValue = "";
							if (webiPrompt.getValues().get(j) != null) {
								parameterValue = webiPrompt.getValues().get(j).toString();
							} else {
								parameterValue = "NULL";
							}
							Log.debug("Webi Prompt :" + xmlPrompt.getName() + " Value:" + parameterValue);
							xmlPromptValue.setValue(parameterValue);
							xmlPromptValues.add(xmlPromptValue);

						}
					}
					xmlPrompt.setValues(xmlPromptValues);
					xmlPrompts.add(xmlPrompt);
				}

				pluginInterfaceWebi.setPrompts(xmlPrompts);
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return pluginInterfaceWebi;
	}

	public PluginInterfaceWebi getWebiPluginInterfaceFromDeski(IInfoObject infoObject) {

		Log.debug("Processing Deski for BOXI 3.1");
		PluginInterfaceWebi pluginInterfaceDeski = new PluginInterfaceWebi();

		try {

			IFullClientProcessingInfo deski = null;
			if (!infoObject.getKind().equals(CeKind.FullClient)) {
				IContent content = (IContent) infoObject;
				deski = (IFullClientProcessingInfo) content.getPluginProcessingInterface(CeKind.FullClient);
			} else {
				deski = (IFullClientProcessingInfo) infoObject;
			}

			if (deski.hasPrompts()) {
				List<WebiPrompt> xmlPrompts = new ArrayList<WebiPrompt>();

				for (int i = 0; i < deski.getPrompts().size(); i++) {
					IFullClientPrompt deskiPrompt = (IFullClientPrompt) deski.getPrompts().get(i);
					WebiPrompt xmlPrompt = new WebiPrompt();
					xmlPrompt.setName(deskiPrompt.getName());
					List<WebiPromptValue> xmlPromptValues = new ArrayList<WebiPromptValue>();
					for (int j = 0; j < deskiPrompt.getValues().size(); j++) {
						Log.debug(deskiPrompt.getValues().get(j).toString());
						WebiPromptValue xmlPromptValue = new WebiPromptValue();
						String parameterValue = deskiPrompt.getValues().get(j).toString();
						Log.debug("Deski Prompt :" + xmlPrompt.getName() + " Value:" + parameterValue);
						xmlPromptValue.setValue(parameterValue);
						xmlPromptValues.add(xmlPromptValue);
					}
					xmlPrompt.setValues(xmlPromptValues);
					xmlPrompts.add(xmlPrompt);
				}

				pluginInterfaceDeski.setPrompts(xmlPrompts);
			}

		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);
		}

		return pluginInterfaceDeski;
	}
}
