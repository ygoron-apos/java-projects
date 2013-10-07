/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "ReportPrinterOptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportPrinterOptions {
	private boolean Enabled;
	private boolean IsLandScapeMode;
	private Integer Copies;
	private Integer FromPage;
	private Integer PageHeight;
	private Integer PageLayout;
	private Integer PageSize;
	private Integer PageWidth;
	private Integer PrintCollationType;
	private String PrinterName;
	private Integer ToPage;

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return Enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	/**
	 * @return the copies
	 */
	public Integer getCopies() {
		return Copies;
	}

	/**
	 * @param copies
	 *            the copies to set
	 */
	public void setCopies(Integer copies) {
		Copies = copies;
	}

	/**
	 * @return the fromPage
	 */
	public Integer getFromPage() {
		return FromPage;
	}

	/**
	 * @param fromPage
	 *            the fromPage to set
	 */
	public void setFromPage(Integer fromPage) {
		FromPage = fromPage;
	}

	/**
	 * @return the pageHeight
	 */
	public Integer getPageHeight() {
		return PageHeight;
	}

	/**
	 * @param pageHeight
	 *            the pageHeight to set
	 */
	public void setPageHeight(Integer pageHeight) {
		PageHeight = pageHeight;
	}

	/**
	 * @return the pageLayout
	 */
	public Integer getPageLayout() {
		return PageLayout;
	}

	/**
	 * @param pageLayout
	 *            the pageLayout to set
	 */
	public void setPageLayout(Integer pageLayout) {
		PageLayout = pageLayout;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return PageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}

	/**
	 * @return the pageWidth
	 */
	public Integer getPageWidth() {
		return PageWidth;
	}

	/**
	 * @param pageWidth
	 *            the pageWidth to set
	 */
	public void setPageWidth(Integer pageWidth) {
		PageWidth = pageWidth;
	}

	/**
	 * @return the printCollationType
	 */
	public Integer getPrintCollationType() {
		return PrintCollationType;
	}

	/**
	 * @param printCollationType
	 *            the printCollationType to set
	 */
	public void setPrintCollationType(Integer printCollationType) {
		PrintCollationType = printCollationType;
	}

	/**
	 * @return the printerName
	 */
	public String getPrinterName() {
		return PrinterName;
	}

	/**
	 * @param printerName
	 *            the printerName to set
	 */
	public void setPrinterName(String printerName) {
		PrinterName = printerName;
	}

	/**
	 * @return the toPage
	 */
	public Integer getToPage() {
		return ToPage;
	}

	/**
	 * @param toPage
	 *            the toPage to set
	 */
	public void setToPage(Integer toPage) {
		ToPage = toPage;
	}

	/**
	 * @return the isLandScapeMode
	 */
	public boolean isIsLandScapeMode() {
		return IsLandScapeMode;
	}

	/**
	 * @param isLandScapeMode the isLandScapeMode to set
	 */
	public void setIsLandScapeMode(boolean isLandScapeMode) {
		IsLandScapeMode = isLandScapeMode;
	}
}
