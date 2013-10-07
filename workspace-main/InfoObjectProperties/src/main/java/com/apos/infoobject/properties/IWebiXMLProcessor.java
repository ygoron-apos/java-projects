/**
 * 
 */
package com.apos.infoobject.properties;

import com.apos.infoobject.xml.PluginInterfaceWebi;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;

/**
 * @author Yuri Goron
 *
 */
public interface IWebiXMLProcessor {
	public abstract PluginInterfaceWebi getWebiPluginInterface(IInfoObject infoObject);
	public abstract PluginInterfaceWebi getWebiPluginInterfaceFromDeski(IInfoObject infoObject);
}
