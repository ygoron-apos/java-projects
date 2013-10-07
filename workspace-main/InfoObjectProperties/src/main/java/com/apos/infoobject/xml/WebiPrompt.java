/**
 * 
 */
package com.apos.infoobject.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuri Goron
 * 
 */
public class WebiPrompt {

	private List<WebiPromptValue> values = new ArrayList<WebiPromptValue>();
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the values
	 */
	public List<WebiPromptValue> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<WebiPromptValue> values) {
		this.values = values;
	}


}
