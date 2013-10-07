/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuri Goron
 * 
 */
public class PluginInterfaceWebi {

	private WebiFormatOptions webiFormatOptions;
	private int preCacheTypes;
	private List<WebiPrompt> prompts = new ArrayList<WebiPrompt>();

	/**
	 * @return the webiFormatOptions
	 */
	public WebiFormatOptions getWebiFormatOptions() {
		return webiFormatOptions;
	}

	/**
	 * @param webiFormatOptions
	 *            the webiFormatOptions to set
	 */
	public void setWebiFormatOptions(WebiFormatOptions webiFormatOptions) {
		this.webiFormatOptions = webiFormatOptions;
	}

	/**
	 * @return the preCacheTypes
	 */
	public int getPreCacheTypes() {
		return preCacheTypes;
	}

	/**
	 * @param preCacheTypes
	 *            the preCacheTypes to set
	 */
	public void setPreCacheTypes(int preCacheTypes) {
		this.preCacheTypes = preCacheTypes;
	}

	/**
	 * @return the prompts
	 */
	public List<WebiPrompt> getPrompts() {
		return prompts;
	}

	/**
	 * @param prompts
	 *            the prompts to set
	 */
	public void setPrompts(List<WebiPrompt> prompts) {
		this.prompts = prompts;
	}

}
