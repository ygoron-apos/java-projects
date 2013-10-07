/**
 * 
 */
package com.apos.infoobject.properties;

import org.apache.log4j.Logger;

import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;

/**
 * @author Yuri Goron
 *
 */
public class Webi4xProcessor implements IWebiXMLProcessor{

	private static final Logger Log=Logger.getLogger(Webi4xProcessor.class);
	public PluginInterfaceWebi getWebiPluginInterface(IInfoObject infoObject) {
		Log.debug("Get Webi Plugin for Webi 4");
		// TODO Auto-generated method stub
		return null;
	}
	public PluginInterfaceWebi getWebiPluginInterfaceFromDeski(IInfoObject infoObject) {
		// TODO Auto-generated method stub
		Log.debug("Who knows who to do here ...");
		return null;
	}

}
