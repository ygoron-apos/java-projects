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
public class ProgramObject {

	private String Args;
	private String UserName;
	private String Password;

	/**
	 * @return the args
	 */
	public String getArgs() {
		return Args;
	}

	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(String args) {
		Args = args;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

}
