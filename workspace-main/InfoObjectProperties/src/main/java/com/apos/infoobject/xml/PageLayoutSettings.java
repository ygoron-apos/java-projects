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
@XmlRootElement(name = "PageLayoutSettings")
@XmlAccessorType(XmlAccessType.FIELD)
public class PageLayoutSettings {
	private int PageLayout;
	private String PageLayoutDescription;

	/**
	 * @return the pageLayout
	 */
	public int getPageLayout() {
		return PageLayout;
	}

	/**
	 * @param pageLayout
	 *            the pageLayout to set
	 */
	public void setPageLayout(int pageLayout) {
		PageLayout = pageLayout;
	}

	/**
	 * @return the pageLayoutDescription
	 */
	public String getPageLayoutDescription() {
		return PageLayoutDescription;
	}

	/**
	 * @param pageLayoutDescription
	 *            the pageLayoutDescription to set
	 */
	public void setPageLayoutDescription(String pageLayoutDescription) {
		PageLayoutDescription = pageLayoutDescription;
	}

}
