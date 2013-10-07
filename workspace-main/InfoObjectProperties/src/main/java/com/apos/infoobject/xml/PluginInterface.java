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
@XmlRootElement(name = "PluginInterface")
@XmlAccessorType(XmlAccessType.FIELD)
public class PluginInterface {

	private String GroupFormula;
	private String RecordFormula;
	private ReportFormatOptions ReportFormatOptions;
	@XmlElement(name="ReportLogons")
	private List<ReportLogon> reportLogons = new ArrayList<ReportLogon>();
	@XmlElement(name="ReportParameters")
	private List<ReportParameter> reportParameters= new ArrayList<ReportParameter>();
	
	private ReportPrinterOptions ReportPrinterOptions;
	private PageLayoutSettings PageLayoutSettings;

	/**
	 * @return the groupFormula
	 */
	public String getGroupFormula() {
		return GroupFormula;
	}

	/**
	 * @param groupFormula
	 *            the groupFormula to set
	 */
	public void setGroupFormula(String groupFormula) {
		GroupFormula = groupFormula;
	}

	/**
	 * @return the recordFormula
	 */
	public String getRecordFormula() {
		return RecordFormula;
	}

	/**
	 * @param recordFormula
	 *            the recordFormula to set
	 */
	public void setRecordFormula(String recordFormula) {
		RecordFormula = recordFormula;
	}

	/**
	 * @return the reportFormatOptions
	 */
	public ReportFormatOptions getReportFormatOptions() {
		return ReportFormatOptions;
	}

	/**
	 * @param reportFormatOptions
	 *            the reportFormatOptions to set
	 */
	public void setReportFormatOptions(ReportFormatOptions reportFormatOptions) {
		ReportFormatOptions = reportFormatOptions;
	}

	/**
	 * @return the reportPrinterOptions
	 */
	public ReportPrinterOptions getReportPrinterOptions() {
		return ReportPrinterOptions;
	}

	/**
	 * @param reportPrinterOptions
	 *            the reportPrinterOptions to set
	 */
	public void setReportPrinterOptions(ReportPrinterOptions reportPrinterOptions) {
		ReportPrinterOptions = reportPrinterOptions;
	}

	/**
	 * @return the pageLayoutSettings
	 */
	public PageLayoutSettings getPageLayoutSettings() {
		return PageLayoutSettings;
	}

	/**
	 * @param pageLayoutSettings
	 *            the pageLayoutSettings to set
	 */
	public void setPageLayoutSettings(PageLayoutSettings pageLayoutSettings) {
		PageLayoutSettings = pageLayoutSettings;
	}

	/**
	 * @return the reportLogons
	 */
	public List<ReportLogon> getReportLogons() {
		return reportLogons;
	}

	/**
	 * @param reportLogons
	 *            the reportLogons to set
	 */
	public void setReportLogons(List<ReportLogon> reportLogons) {
		this.reportLogons = reportLogons;
	}

	/**
	 * @return the reportParameters
	 */
	public List<ReportParameter> getReportParameters() {
		return reportParameters;
	}

	/**
	 * @param reportParameters
	 *            the reportParameters to set
	 */
	public void setReportParameters(List<ReportParameter> reportParameters) {
		this.reportParameters = reportParameters;
	}

}
