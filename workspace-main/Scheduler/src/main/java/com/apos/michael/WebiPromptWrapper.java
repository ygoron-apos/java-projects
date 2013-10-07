package com.apos.michael;

public class WebiPromptWrapper {
	private String name;
	private String prefix;
	private boolean unx;
	
	public WebiPromptWrapper() {
		name = "";
		prefix = "";
		unx = false;
	}
	
	public String getFullName() {
		String rc;
		if (unx)
			rc = prefix + name;
		else
			rc = name;
		return rc;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * @return the unx
	 */
	public boolean isUnx() {
		return unx;
	}
	/**
	 * @param unx the unx to set
	 */
	public void setUnx(boolean unx) {
		this.unx = unx;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Name: " + name + ", Prefix: " + prefix + ", isUNX: " + unx;
	}
}
