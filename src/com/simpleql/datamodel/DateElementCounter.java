package com.simpleql.datamodel;

/**
 * Represents an item to be returned by the server, and diaplyed in the date selector.
 * 
 * @author yossiv
 *
 */
public class DateElementCounter {
	/**
	 * <pre>
	 * date value in format matching the request.
	 * if requested Year, value is in format yyyy.
	 * if requested Month, value is in format yyyy-MM.
	 * if requested Day, value is in format yyyy-MM-dd.
	 * if requested Hour, value is in format yyyy-MM-dd HH.
	 * </pre>
	 */
	String value;

	/**
	 * number of items with this date.
	 */
	int count;
}
