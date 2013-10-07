package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ReportFormatOptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelFormat {
	private boolean isConvertDateToString;
	private boolean isCreatePageBreak;
	private boolean isExportPageHeader;
	private boolean isHasColumnHeadings;
	private boolean isTabularFormat;
	private boolean isExportAllPages;
	private boolean isUseConstColWidth;
	private Integer BaseAreaType=0;
	private Integer BaseAreaGroupNum=0;
	private Double ConstColWidth=0.0;
	private Integer StartPageNumber=0;
	private Integer EndPageNumber=0;
	private boolean isExportShowGridlines;
	private Integer ExportPageHeaderFooter=0;

	/**
	 * @return the isConvertDateToString
	 */
	public boolean isConvertDateToString() {
		return isConvertDateToString;
	}

	/**
	 * @param isConvertDateToString
	 *            the isConvertDateToString to set
	 */
	public void setConvertDateToString(boolean isConvertDateToString) {
		this.isConvertDateToString = isConvertDateToString;
	}

	/**
	 * @return the isCreatePageBreak
	 */
	public boolean isCreatePageBreak() {
		return isCreatePageBreak;
	}

	/**
	 * @param isCreatePageBreak
	 *            the isCreatePageBreak to set
	 */
	public void setCreatePageBreak(boolean isCreatePageBreak) {
		this.isCreatePageBreak = isCreatePageBreak;
	}

	/**
	 * @return the isExportPageHeader
	 */
	public boolean isExportPageHeader() {
		return isExportPageHeader;
	}

	/**
	 * @param isExportPageHeader
	 *            the isExportPageHeader to set
	 */
	public void setExportPageHeader(boolean isExportPageHeader) {
		this.isExportPageHeader = isExportPageHeader;
	}

	/**
	 * @return the isHasColumnHeadings
	 */
	public boolean isHasColumnHeadings() {
		return isHasColumnHeadings;
	}

	/**
	 * @param isHasColumnHeadings
	 *            the isHasColumnHeadings to set
	 */
	public void setHasColumnHeadings(boolean isHasColumnHeadings) {
		this.isHasColumnHeadings = isHasColumnHeadings;
	}

	/**
	 * @return the isTabularFormat
	 */
	public boolean isTabularFormat() {
		return isTabularFormat;
	}

	/**
	 * @param isTabularFormat
	 *            the isTabularFormat to set
	 */
	public void setTabularFormat(boolean isTabularFormat) {
		this.isTabularFormat = isTabularFormat;
	}

	/**
	 * @return the isExportAllPages
	 */
	public boolean isExportAllPages() {
		return isExportAllPages;
	}

	/**
	 * @param isExportAllPages
	 *            the isExportAllPages to set
	 */
	public void setExportAllPages(boolean isExportAllPages) {
		this.isExportAllPages = isExportAllPages;
	}

	/**
	 * @return the isUseConstColWidth
	 */
	public boolean isUseConstColWidth() {
		return isUseConstColWidth;
	}

	/**
	 * @param isUseConstColWidth
	 *            the isUseConstColWidth to set
	 */
	public void setUseConstColWidth(boolean isUseConstColWidth) {
		this.isUseConstColWidth = isUseConstColWidth;
	}

	/**
	 * @return the baseAreaType
	 */
	public Integer getBaseAreaType() {
		return BaseAreaType;
	}

	/**
	 * @param baseAreaType
	 *            the baseAreaType to set
	 */
	public void setBaseAreaType(Integer baseAreaType) {
		BaseAreaType = baseAreaType;
	}

	/**
	 * @return the baseAreaGroupNum
	 */
	public Integer getBaseAreaGroupNum() {
		return BaseAreaGroupNum;
	}

	/**
	 * @param baseAreaGroupNum
	 *            the baseAreaGroupNum to set
	 */
	public void setBaseAreaGroupNum(Integer baseAreaGroupNum) {
		BaseAreaGroupNum = baseAreaGroupNum;
	}

	/**
	 * @return the startPageNumber
	 */
	public Integer getStartPageNumber() {
		return StartPageNumber;
	}

	/**
	 * @param startPageNumber
	 *            the startPageNumber to set
	 */
	public void setStartPageNumber(Integer startPageNumber) {
		StartPageNumber = startPageNumber;
	}

	/**
	 * @return the endPageNumber
	 */
	public Integer getEndPageNumber() {
		return EndPageNumber;
	}

	/**
	 * @param endPageNumber
	 *            the endPageNumber to set
	 */
	public void setEndPageNumber(Integer endPageNumber) {
		EndPageNumber = endPageNumber;
	}

	/**
	 * @return the isExportShowGridlines
	 */
	public boolean isExportShowGridlines() {
		return isExportShowGridlines;
	}

	/**
	 * @param isExportShowGridlines
	 *            the isExportShowGridlines to set
	 */
	public void setExportShowGridlines(boolean isExportShowGridlines) {
		this.isExportShowGridlines = isExportShowGridlines;
	}

	/**
	 * @return the exportPageHeaderFooter
	 */
	public Integer getExportPageHeaderFooter() {
		return ExportPageHeaderFooter;
	}

	/**
	 * @param exportPageHeaderFooter
	 *            the exportPageHeaderFooter to set
	 */
	public void setExportPageHeaderFooter(Integer exportPageHeaderFooter) {
		ExportPageHeaderFooter = exportPageHeaderFooter;
	}

	/**
	 * @return the constColWidth
	 */
	public Double getConstColWidth() {
		return ConstColWidth;
	}

	/**
	 * @param constColWidth
	 *            the constColWidth to set
	 */
	public void setConstColWidth(Double constColWidth) {
		ConstColWidth = constColWidth;
	}
}
