/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "ReportParameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportParameter {
	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "ReportName")
	private String reportName;

	@XmlElement(name = "SubReportName")
	private String subReportName;


	@XmlElement(name = "IsRangeValue")
	private boolean isRangeValue;

	@XmlElement(name = "IsSingleValue")
	private boolean isSingleValue;

	@XmlElement(name = "CurrentValues")
	private List<ParameterValue> currentValues = new ArrayList<ParameterValue>();

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the reportName
	 */
	public String getSubReportName() {
		return subReportName;
	}

	/**
	 * @param subReportName
	 *            the reportName to set
	 */
	public void setSubReportName(String subReportName) {
		this.subReportName = subReportName;
	}

	/**
	 * @return the currentValues
	 */
	public List<ParameterValue> getCurrentValues() {
		return currentValues;
	}

	/**
	 * @param currentValues
	 *            the currentValues to set
	 */
	public void setCurrentValues(List<ParameterValue> currentValues) {
		this.currentValues = currentValues;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName
	 *            the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	/**
	 * @return the isRangeValue
	 */
	public boolean isRangeValue() {
		return isRangeValue;
	}

	/**
	 * @param isRangeValue
	 *            the isRangeValue to set
	 */
	public void setIsRangeValue(boolean isRangeValue) {
		this.isRangeValue = isRangeValue;
	}

	/**
	 * @return the isSingleValue
	 */
	public boolean isSingleValue() {
		return isSingleValue;
	}

	/**
	 * @param isSingleValue
	 *            the isSingleValue to set
	 */
	public void setSingleValue(boolean isSingleValue) {
		this.isSingleValue = isSingleValue;
	}

}
