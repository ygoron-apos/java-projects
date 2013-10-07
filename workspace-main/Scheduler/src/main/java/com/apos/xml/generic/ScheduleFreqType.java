/**
 * 
 */
package com.apos.xml.generic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.apos.xml.generic.freq.Calendar;
import com.apos.xml.generic.freq.Hourly;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleFreqType {

	@XmlElement(name = "Type")
	private String type;

	@XmlElement(name = "Hourly")
	private Hourly hourly;

	@XmlElement(name = "Interval")
	private int interVal;

	private Calendar Calendar;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the hourly
	 */
	public Hourly getHourly() {
		return hourly;
	}

	/**
	 * @param hourly
	 *            the hourly to set
	 */
	public void setHourly(Hourly hourly) {
		this.hourly = hourly;
	}

	/**
	 * @return the interVal
	 */
	public int getInterVal() {
		return interVal;
	}

	/**
	 * @param interVal
	 *            the interVal to set
	 */
	public void setInterVal(int interVal) {
		this.interVal = interVal;
	}

	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return Calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(Calendar calendar) {
		Calendar = calendar;
	}

}
