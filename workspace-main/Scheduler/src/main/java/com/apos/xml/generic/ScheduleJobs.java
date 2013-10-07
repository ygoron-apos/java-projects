/**
 * 
 */
package com.apos.xml.generic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */

@XmlRootElement(name = "ScheduleJobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleJobs {
	List<ScheduleSettings> ScheduleSettings = new ArrayList<ScheduleSettings>();
	List<String> AcceptType = new ArrayList<String>();

	/**
	 * @return the scheduleSettings
	 */
	public List<ScheduleSettings> getScheduleSettings() {
		return ScheduleSettings;
	}

	/**
	 * @param scheduleSettings
	 *            the scheduleSettings to set
	 */
	public void setScheduleSettings(List<ScheduleSettings> scheduleSettings) {
		this.ScheduleSettings = scheduleSettings;
	}

	/**
	 * @return the acceptType
	 */
	public List<String> getAcceptType() {
		return AcceptType;
	}

	/**
	 * @param acceptType
	 *            the acceptType to set
	 */
	public void setAcceptType(List<String> acceptType) {
		AcceptType = acceptType;
	}

}
