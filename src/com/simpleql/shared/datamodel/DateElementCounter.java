package com.simpleql.shared.datamodel;

import java.io.Serializable;

/**
 * Represents an item to be returned by the server, and diaplyed in the date selector.
 * 
 * @author yossiv
 *
 */
public class DateElementCounter implements Serializable{
	/**
	 * <pre>
	 * date value in format matching the request.
	 * if requested Year, value is in format yyyy.
	 * if requested Month, value is in format yyyy-MM.
	 * if requested Day, value is in format yyyy-MM-dd.
	 * if requested Hour, value is in format yyyy-MM-dd HH.
	 * </pre>
	 */
	private String value;

	/**
	 * number of items with this date.
	 */
	private int count;
	
	
	public DateElementCounter(String value, int count){
		
		this.value = value;
		this.count = count;
	}
	
	public DateElementCounter(){
		

	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
