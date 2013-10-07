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
@XmlRootElement(name = "ExcelDataOnlyFormat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelDataOnlyFormat {

	private boolean isColAlignmentMaintained;
	private boolean isConstColWidthUsed;
	private boolean isFormatUsed;
	private boolean isImageExported;
	private boolean isPageHeaderExported;
	private boolean isPageHeaderSimplified;
	private boolean isRelativeObjPositionMaintained;
	private boolean isShowGroupOutlines;
	private boolean isWorksheetFuncUsed;
	private Integer BaseAreaGroupNum = 0;
	private Integer BaseAreaType = 0;
	private Double ConstColWidth = 0.0;

	/**
	 * @return the isColAlignmentMaintained
	 */
	public boolean isColAlignmentMaintained() {
		return isColAlignmentMaintained;
	}

	/**
	 * @param isColAlignmentMaintained
	 *            the isColAlignmentMaintained to set
	 */
	public void setColAlignmentMaintained(boolean isColAlignmentMaintained) {
		this.isColAlignmentMaintained = isColAlignmentMaintained;
	}

	/**
	 * @return the isConstColWidthUsed
	 */
	public boolean isConstColWidthUsed() {
		return isConstColWidthUsed;
	}

	/**
	 * @param isConstColWidthUsed
	 *            the isConstColWidthUsed to set
	 */
	public void setConstColWidthUsed(boolean isConstColWidthUsed) {
		this.isConstColWidthUsed = isConstColWidthUsed;
	}

	/**
	 * @return the isFormatUsed
	 */
	public boolean isFormatUsed() {
		return isFormatUsed;
	}

	/**
	 * @param isFormatUsed
	 *            the isFormatUsed to set
	 */
	public void setFormatUsed(boolean isFormatUsed) {
		this.isFormatUsed = isFormatUsed;
	}

	/**
	 * @return the isImageExported
	 */
	public boolean isImageExported() {
		return isImageExported;
	}

	/**
	 * @param isImageExported
	 *            the isImageExported to set
	 */
	public void setImageExported(boolean isImageExported) {
		this.isImageExported = isImageExported;
	}

	/**
	 * @return the isPageHeaderExported
	 */
	public boolean isPageHeaderExported() {
		return isPageHeaderExported;
	}

	/**
	 * @param isPageHeaderExported
	 *            the isPageHeaderExported to set
	 */
	public void setPageHeaderExported(boolean isPageHeaderExported) {
		this.isPageHeaderExported = isPageHeaderExported;
	}

	/**
	 * @return the isPageHeaderSimplified
	 */
	public boolean isPageHeaderSimplified() {
		return isPageHeaderSimplified;
	}

	/**
	 * @param isPageHeaderSimplified
	 *            the isPageHeaderSimplified to set
	 */
	public void setPageHeaderSimplified(boolean isPageHeaderSimplified) {
		this.isPageHeaderSimplified = isPageHeaderSimplified;
	}

	/**
	 * @return the isRelativeObjPositionMaintained
	 */
	public boolean isRelativeObjPositionMaintained() {
		return isRelativeObjPositionMaintained;
	}

	/**
	 * @param isRelativeObjPositionMaintained
	 *            the isRelativeObjPositionMaintained to set
	 */
	public void setRelativeObjPositionMaintained(boolean isRelativeObjPositionMaintained) {
		this.isRelativeObjPositionMaintained = isRelativeObjPositionMaintained;
	}

	/**
	 * @return the isShowGroupOutlines
	 */
	public boolean isShowGroupOutlines() {
		return isShowGroupOutlines;
	}

	/**
	 * @param isShowGroupOutlines
	 *            the isShowGroupOutlines to set
	 */
	public void setShowGroupOutlines(boolean isShowGroupOutlines) {
		this.isShowGroupOutlines = isShowGroupOutlines;
	}

	/**
	 * @return the isWorksheetFuncUsed
	 */
	public boolean isWorksheetFuncUsed() {
		return isWorksheetFuncUsed;
	}

	/**
	 * @param isWorksheetFuncUsed
	 *            the isWorksheetFuncUsed to set
	 */
	public void setWorksheetFuncUsed(boolean isWorksheetFuncUsed) {
		this.isWorksheetFuncUsed = isWorksheetFuncUsed;
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
