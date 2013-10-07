/**
 * 
 */
package com.apos.scheduler;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * @author Yuri Goron
 * 
 *         Testing Jaxb
 * 
 */
@XmlRootElement(name = "MobileApp")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLTestPojo {

//	@XmlElement(name="name")
	private String appName;
//	@XmlElement(name="description")
	private String appDescription;

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appDescription
	 */
	public String getAppDescription() {
		return appDescription;
	}

	/**
	 * @param appDescription
	 *            the appDescription to set
	 */
	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

}
