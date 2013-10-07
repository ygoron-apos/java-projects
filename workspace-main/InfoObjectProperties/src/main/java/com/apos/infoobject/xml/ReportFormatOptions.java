/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "ReportFormatOptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportFormatOptions {

	@XmlElement(name = "FormatString")
	private String formatString; // PDF , XLS etc
	private int Format;
	private ExcelFormat excelFormat;
	private ExcelDataOnlyFormat excelDataOnlyFormat;
	private RichTextFormat richTextFormat;
	private WordFormat wordFormat;

	/**
	 * @return the format
	 */
	public int getFormat() {
		return Format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(int format) {
		Format = format;
	}

	/**
	 * @return the formatString
	 */
	public String getFormatString() {
		return formatString;
	}

	/**
	 * @param formatString
	 *            the formatString to set
	 */
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	/**
	 * @return the excelFormat
	 */
	public ExcelFormat getExcelFormat() {
		return excelFormat;
	}

	/**
	 * @param excelFormat
	 *            the excelFormat to set
	 */
	public void setExcelFormat(ExcelFormat excelFormat) {
		this.excelFormat = excelFormat;
	}

	/**
	 * @return the excelDataOnlyFormat
	 */
	public ExcelDataOnlyFormat getExcelDataOnlyFormat() {
		return excelDataOnlyFormat;
	}

	/**
	 * @param excelDataOnlyFormat
	 *            the excelDataOnlyFormat to set
	 */
	public void setExcelDataOnlyFormat(ExcelDataOnlyFormat excelDataOnlyFormat) {
		this.excelDataOnlyFormat = excelDataOnlyFormat;
	}

	/**
	 * @return the richTextFormat
	 */
	public RichTextFormat getRichTextFormat() {
		return richTextFormat;
	}

	/**
	 * @param richTextFormat
	 *            the richTextFormat to set
	 */
	public void setRichTextFormat(RichTextFormat richTextFormat) {
		this.richTextFormat = richTextFormat;
	}

	/**
	 * @return the wordFormat
	 */
	public WordFormat getWordFormat() {
		return wordFormat;
	}

	/**
	 * @param wordFormat
	 *            the wordFormat to set
	 */
	public void setWordFormat(WordFormat wordFormat) {
		this.wordFormat = wordFormat;
	}
}
