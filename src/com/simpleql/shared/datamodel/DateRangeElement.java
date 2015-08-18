package com.simpleql.shared.datamodel;

/**
 * Represents a countinuous Date range. range can be specified in one of 4 resolutions - Year, Month, Day, Hour.
 *
 * <code>
 *  from and to values will be specified in :
 *  if resolution is Year - format is yyyy.
 *  if resolution is Month - format is yyyy-MM.
 *  if resolution is Day - format is yyyy-MM-dd.
 *  if resolution is Hour - format is yyyy-MM-dd HH.
 * </code>
 * 
 * @author yossiv
 *
 */
public class DateRangeElement {
	DateResolution type;
	String from;
	String to;
	
	
	public DateRangeElement(DateResolution type, String from, String to){
		this.type = type;
		this.from = from;
		this.to = to;
	}

	public DateResolution getType() {
		return type;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
