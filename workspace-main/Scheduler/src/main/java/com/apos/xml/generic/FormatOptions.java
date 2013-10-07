/**
 * 
 */
package com.apos.xml.generic;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlRootElement(name = "FormatOptions")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatOptions {

	private String Format;
	private String Options;

	/**
	 * @return the format
	 */
	public String getFormat() {
		return Format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}

	/**
	 * @return the options
	 */
	public String getOptions() {
		return Options;
	}

	/**
	 * @param options
	 *            the options to set
	 */
	public void setOptions(String options) {
		Options = options;
	}

}
