package com.simpleql.datamodel;

import com.google.gwt.user.client.ui.Widget;

/**
 * The date selection widget.
 * 
 * @author yossiv
 *
 */
public abstract class DateRangeSelector extends Widget {

	private String token;

	/**
	 * 
	 * @param token
	 *            will be used for communication with server.
	 */
	public DateRangeSelector(String token) {

	}

	/**
	 * Returns the selected date ranges.
	 * 
	 * <pre>
	 * @return returns an array of selected Date ranges. 
	 * 			Each range is continuous. 
	 * 		For example if user selected all of 2015 and December, November and October of 2014 result should be 
	 * 		[{type = Year, from="2015",to="2015"},
	 * 		 {type = Month, from="2014-10", to="2014-12"}]
	 * </pre>
	 */
	public abstract DateRangeElement[] getSelectedDates();

}
