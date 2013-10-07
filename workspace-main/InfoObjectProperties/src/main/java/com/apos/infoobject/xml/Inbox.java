/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Inbox {

	private String Name;
	private boolean IsDistribitionServer;
	private String DistribuitionServerString;
	private int id;
	@XmlElement(name = "IsGroup")
	private boolean isGroup;

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @return the isDistribitionServer
	 */
	public boolean isIsDistribitionServer() {
		return IsDistribitionServer;
	}

	/**
	 * @param isDistribitionServer
	 *            the isDistribitionServer to set
	 */
	public void setIsDistribitionServer(boolean isDistribitionServer) {
		IsDistribitionServer = isDistribitionServer;
	}

	/**
	 * @return the distribuitionServerString
	 */
	public String getDistribuitionServerString() {
		return DistribuitionServerString;
	}

	/**
	 * @param distribuitionServerString
	 *            the distribuitionServerString to set
	 */
	public void setDistribuitionServerString(String distribuitionServerString) {
		DistribuitionServerString = distribuitionServerString;
	}

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
	 * @return the isGroup
	 */
	public boolean isGroup() {
		return isGroup;
	}

	/**
	 * @param isGroup
	 *            the isGroup to set
	 */
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

}
