/**
 * 
 */
package com.apos.infoobject.properties;

import java.util.Properties;

/**
 * @author Yuri Goron
 * 
 */
public class OutputJobWrapper {
	private int id;
	private Properties properties;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
