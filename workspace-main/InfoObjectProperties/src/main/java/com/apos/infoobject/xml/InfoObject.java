/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "Object")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = { "cluster", "SI_ID", "SI_PARENTID", "SI_LOCATION",
//"schedulingInfo" })
public class InfoObject {

	private String Cluster;
	private Integer SI_ID;
	@XmlElement(name="SchedulingInfo")
	private SchedulingInfo schedulingInfo;

	private Integer SI_PARENTID;
	private String SI_LOCATION;

	@XmlElement(name="PluginInterfaceCrystal")
	private PluginInterface pluginInterface;
	@XmlElement(name="PluginInterfaceWebi")
	private PluginInterfaceWebi pluginInterfaceWebi;
	private String Apos;

	/**
	 * @return the cluster
	 */
	public String getCluster() {
		return Cluster;
	}

	/**
	 * @param cluster
	 *            the cluster to set
	 */
	public void setCluster(String cluster) {
		this.Cluster = cluster;
	}

	/**
	 * @return the sI_ID
	 */
	public int getSI_ID() {
		return SI_ID;
	}

	/**
	 * @param sI_ID
	 *            the sI_ID to set
	 */
	public void setSI_ID(int sI_ID) {
		SI_ID = sI_ID;
	}

	/**
	 * @return the schedulingInfo
	 */
	public SchedulingInfo getSchedulingInfo() {
		return schedulingInfo;
	}

	/**
	 * @param schedulingInfo
	 *            the schedulingInfo to set
	 */
	public void setSchedulingInfo(SchedulingInfo schedulingInfo) {
		this.schedulingInfo = schedulingInfo;
	}

	/**
	 * @return the sI_PARENTID
	 */
	public int getSI_PARENTID() {
		return SI_PARENTID;
	}

	/**
	 * @param sI_PARENTID
	 *            the sI_PARENTID to set
	 */
	public void setSI_PARENTID(int sI_PARENTID) {
		SI_PARENTID = sI_PARENTID;
	}

	/**
	 * @return the sI_LOCATION
	 */
	public String getSI_LOCATION() {
		return SI_LOCATION;
	}

	/**
	 * @param sI_LOCATION
	 *            the sI_LOCATION to set
	 */
	public void setSI_LOCATION(String sI_LOCATION) {
		SI_LOCATION = sI_LOCATION;
	}

	/**
	 * @return the pluginInterface
	 */
	public PluginInterface getPluginInterface() {
		return pluginInterface;
	}

	/**
	 * @param pluginInterface
	 *            the pluginInterface to set
	 */
	public void setPluginInterface(PluginInterface pluginInterface) {
		this.pluginInterface = pluginInterface;
	}

	/**
	 * @return the apos
	 */
	public String getApos() {
		return Apos;
	}

	/**
	 * @param apos
	 *            the apos to set
	 */
	public void setApos(String apos) {
		Apos = apos;
	}

	/**
	 * @return the pluginInterfaceWebi
	 */
	public PluginInterfaceWebi getPluginInterfaceWebi() {
		return pluginInterfaceWebi;
	}

	/**
	 * @param pluginInterfaceWebi
	 *            the pluginInterfaceWebi to set
	 */
	public void setPluginInterfaceWebi(PluginInterfaceWebi pluginInterfaceWebi) {
		this.pluginInterfaceWebi = pluginInterfaceWebi;
	}

}
