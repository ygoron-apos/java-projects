/**
 * 
 */
package com.apos.infoobject.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yuri Goron
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ParameterValue {

	@XmlElement(name = "Value")
	private String value;

	@XmlElement(name = "IsNullValue")
	private boolean isNullValue;

	private String RangeValueFrom;
	private boolean IsIncludeValueFrom;
	private boolean IsNoLowerValue;
	private boolean IsRangeValueFromNull;

	private String RangeValueTo;
	private boolean IsIncludeValueTo;
	private boolean IsNoUpperValue;
	private boolean IsRangeValueToNull;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the rangeValueFrom
	 */
	public String getRangeValueFrom() {
		return RangeValueFrom;
	}

	/**
	 * @param rangeValueFrom
	 *            the rangeValueFrom to set
	 */
	public void setRangeValueFrom(String rangeValueFrom) {
		RangeValueFrom = rangeValueFrom;
	}

	/**
	 * @return the isIncludeValueFrom
	 */
	public boolean isIsIncludeValueFrom() {
		return IsIncludeValueFrom;
	}

	/**
	 * @param isIncludeValueFrom
	 *            the isIncludeValueFrom to set
	 */
	public void setIsIncludeValueFrom(boolean isIncludeValueFrom) {
		IsIncludeValueFrom = isIncludeValueFrom;
	}

	/**
	 * @return the isNoLowerValue
	 */
	public boolean isIsNoLowerValue() {
		return IsNoLowerValue;
	}

	/**
	 * @param isNoLowerValue
	 *            the isNoLowerValue to set
	 */
	public void setIsNoLowerValue(boolean isNoLowerValue) {
		IsNoLowerValue = isNoLowerValue;
	}

	/**
	 * @return the rangeValueTo
	 */
	public String getRangeValueTo() {
		return RangeValueTo;
	}

	/**
	 * @param rangeValueTo
	 *            the rangeValueTo to set
	 */
	public void setRangeValueTo(String rangeValueTo) {
		RangeValueTo = rangeValueTo;
	}

	/**
	 * @return the isIncludeValueTo
	 */
	public boolean isIsIncludeValueTo() {
		return IsIncludeValueTo;
	}

	/**
	 * @param isIncludeValueTo
	 *            the isIncludeValueTo to set
	 */
	public void setIsIncludeValueTo(boolean isIncludeValueTo) {
		IsIncludeValueTo = isIncludeValueTo;
	}

	/**
	 * @return the isRangeValueFromNull
	 */
	public boolean isIsRangeValueFromNull() {
		return IsRangeValueFromNull;
	}

	/**
	 * @param isRangeValueFromNull
	 *            the isRangeValueFromNull to set
	 */
	public void setIsRangeValueFromNull(boolean isRangeValueFromNull) {
		IsRangeValueFromNull = isRangeValueFromNull;
	}

	/**
	 * @return the isRangeValueToNull
	 */
	public boolean isIsRangeValueToNull() {
		return IsRangeValueToNull;
	}

	/**
	 * @param isRangeValueToNull
	 *            the isRangeValueToNull to set
	 */
	public void setIsRangeValueToNull(boolean isRangeValueToNull) {
		IsRangeValueToNull = isRangeValueToNull;
	}

	/**
	 * @return the isNoUpperValue
	 */
	public boolean isIsNoUpperValue() {
		return IsNoUpperValue;
	}

	/**
	 * @param isNoUpperValue
	 *            the isNoUpperValue to set
	 */
	public void setIsNoUpperValue(boolean isNoUpperValue) {
		IsNoUpperValue = isNoUpperValue;
	}

	/**
	 * @return the isNull
	 */
	public boolean isNullValue() {
		return isNullValue;
	}

	/**
	 * @param isNull
	 *            the isNull to set
	 */
	public void setNullValue(boolean isNull) {
		this.isNullValue = isNull;
	}

}
