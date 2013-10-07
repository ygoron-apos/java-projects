/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ygoron
 * 
 */
@XmlRootElement(name = "TableLocationPrefix")
public class TableLocationPrefix {
	private String TableLocationPrefix;
	private String MappedTablePrefix;
	private boolean UseMappedTablePrefix;

	/**
	 * @return the tableLocationPrefix
	 */
	public String getTableLocationPrefix() {
		return TableLocationPrefix;
	}

	/**
	 * @param tableLocationPrefix
	 *            the tableLocationPrefix to set
	 */
	public void setTableLocationPrefix(String tableLocationPrefix) {
		TableLocationPrefix = tableLocationPrefix;
	}

	/**
	 * @return the mappedTablePrefix
	 */
	public String getMappedTablePrefix() {
		return MappedTablePrefix;
	}

	/**
	 * @param mappedTablePrefix
	 *            the mappedTablePrefix to set
	 */
	public void setMappedTablePrefix(String mappedTablePrefix) {
		MappedTablePrefix = mappedTablePrefix;
	}

	/**
	 * @return the useMappedTablePrefix
	 */
	public boolean isUseMappedTablePrefix() {
		return UseMappedTablePrefix;
	}

	/**
	 * @param useMappedTablePrefix
	 *            the useMappedTablePrefix to set
	 */
	public void setUseMappedTablePrefix(boolean useMappedTablePrefix) {
		UseMappedTablePrefix = useMappedTablePrefix;
	}

}
