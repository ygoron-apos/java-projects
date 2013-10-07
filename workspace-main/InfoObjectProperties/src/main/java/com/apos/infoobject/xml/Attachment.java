/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Yuri Goron
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Attachment {

	public String Name;
	public String MimeType;
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return MimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}
}
