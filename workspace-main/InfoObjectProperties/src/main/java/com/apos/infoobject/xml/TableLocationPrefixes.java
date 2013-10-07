/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "TableLocationPrefixes")
public class TableLocationPrefixes {
	private int count;
	private TableLocationPrefix TableLocationPrefix;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the tableLocationPrefix
	 */
	public TableLocationPrefix getTableLocationPrefix() {
		return TableLocationPrefix;
	}

	/**
	 * @param tableLocationPrefix
	 *            the tableLocationPrefix to set
	 */
	public void setTableLocationPrefix(TableLocationPrefix tableLocationPrefix) {
		TableLocationPrefix = tableLocationPrefix;
	}
}
