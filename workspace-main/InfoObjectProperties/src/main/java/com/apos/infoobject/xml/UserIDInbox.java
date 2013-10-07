package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

@XmlRootElement(name = "UserIDInbox")
public class UserIDInbox {
	private Map<String, Integer> UserIDInboxes;

	/**
	 * @return the attributes
	 */
	// @XmlAnyElement
	// public Map<QName, String> getAttributes() {
	// return attributes;
	// }
	@XmlAnyElement
	public List<JAXBElement<Integer>> getUserIDInboxes() {
	    List<JAXBElement<Integer>> elements = new ArrayList<JAXBElement<Integer>>();
	    for (Map.Entry<String, Integer> id: UserIDInboxes.entrySet()) 
	        elements.add(new JAXBElement<Integer>(new QName(id.getKey()), 
	                                     Integer.class, id.getValue()));
	    
//	    for (int i=1;i<=5;i++) 
//	        elements.add(new JAXBElement(new QName("UserID"+String.valueOf(i)),Integer.class,i));

	    return elements;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setUserIDInboxes(Map<String, Integer> userIDInboxes) {
		this.UserIDInboxes = userIDInboxes;
	}
}
