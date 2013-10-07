/**
 * 
 */
package com.apos.xml.generic.freq;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Calendar {

	private String Name;

	private List<CalendarDay> CalendarDays = new ArrayList<CalendarDay>();

	/**
	 * @return the calendarDays
	 */
	public List<CalendarDay> getCalendarDays() {
		return CalendarDays;
	}

	/**
	 * @param calendarDays
	 *            the calendarDays to set
	 */
	public void setCalendarDays(List<CalendarDay> calendarDays) {
		this.CalendarDays = calendarDays;
	}

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
}
