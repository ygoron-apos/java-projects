/**
 * 
 */
package com.apos.xml.generic.freq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarDay {

	private Integer StartDay;
	private Integer StartMonth;
	private Integer StartYear;

	private Integer EndDay;
	private Integer EndMonth;
	private Integer EndYear;

	private Integer DayOfWeek;
	private Integer WeekNumber;

	/**
	 * @return the startDay
	 */
	public Integer getStartDay() {
		return StartDay;
	}

	/**
	 * @param startDay
	 *            the startDay to set
	 */
	public void setStartDay(Integer startDay) {
		StartDay = startDay;
	}

	/**
	 * @return the startMonth
	 */
	public Integer getStartMonth() {
		return StartMonth;
	}

	/**
	 * @param startMonth
	 *            the startMonth to set
	 */
	public void setStartMonth(Integer startMonth) {
		StartMonth = startMonth;
	}

	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return StartYear;
	}

	/**
	 * @param startYear
	 *            the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		StartYear = startYear;
	}

	/**
	 * @return the endDay
	 */
	public Integer getEndDay() {
		return EndDay;
	}

	/**
	 * @param endDay
	 *            the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		EndDay = endDay;
	}

	/**
	 * @return the endMonth
	 */
	public Integer getEndMonth() {
		return EndMonth;
	}

	/**
	 * @param endMonth
	 *            the endMonth to set
	 */
	public void setEndMonth(Integer endMonth) {
		EndMonth = endMonth;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return EndYear;
	}

	/**
	 * @param endYear
	 *            the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		EndYear = endYear;
	}

	/**
	 * @return the dayOfWeek
	 */
	public Integer getDayOfWeek() {
		return DayOfWeek;
	}

	/**
	 * @param dayOfWeek
	 *            the dayOfWeek to set
	 */
	public void setDayOfWeek(Integer dayOfWeek) {
		DayOfWeek = dayOfWeek;
	}

	/**
	 * @return the weekNumber
	 */
	public Integer getWeekNumber() {
		return WeekNumber;
	}

	/**
	 * @param weekNumber
	 *            the weekNumber to set
	 */
	public void setWeekNumber(Integer weekNumber) {
		WeekNumber = weekNumber;
	}

}
