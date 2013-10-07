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

@XmlRootElement(name = "ReportFormatOptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class RichTextFormat {

	private boolean isAllPagesExported;
	private Integer EndPageNumber = 0;
	private Integer StartPageNumber = 0;

	/**
	 * @return the isAllPagesExported
	 */
	public boolean isAllPagesExported() {
		return isAllPagesExported;
	}

	/**
	 * @param isAllPagesExported
	 *            the isAllPagesExported to set
	 */
	public void setAllPagesExported(boolean isAllPagesExported) {
		this.isAllPagesExported = isAllPagesExported;
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

}
