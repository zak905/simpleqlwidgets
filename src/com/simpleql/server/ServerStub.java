package com.simpleql.server;

import com.simpleql.datamodel.DateElementCounter;
import com.simpleql.datamodel.DateResolution;

/**
 * Interface between client and server.
 * 
 * <pre>
 * The date selector will use this interface to get the values to display at each level.
 * </pre>
 * 
 * @author yossiv
 *
 */
public interface ServerStub {

	/**
	 * Returns values to be displayed at each level.
	 * 
	 * <pre>
	 * For the top level, the type is {@value DateResolution.Year} and the value can be null. 
	 * for lower levels, client should provide parent value. 
	 * 
	 * For example, to get all months of year 2015, should call getNextLevelValues(token, DateResolution.Month , "2015").
	 * To get all days of august 2015, should call getNextLevelValues(token, DateResolution.Day , "2015-08"). etc.
	 * 
	 * @param token
	 *            - the token issued when created the date selector.
	 * @param type
	 *            - resolution of requested values.
	 * @param value
	 *            - if resolution is lower than Year, must provide parent value. 
	 *            if requested resolution is Month, value must be in format yyyy. 
	 *            if requested resolution is Day, value must be in format yyyy-MM. 
	 *            if requested resolution is Hour, value must be in format yyyy-MM-dd.
	 * 
	 * @return an array of date Elements and their respective counters.
	 * 
	 *         for example, if asked for Months of 2015, the result might be 
	 *         [ {value="2015-08", counter = 100}, {value="2015-07", counter = 800}]
	 * </pre>
	 */
	public DateElementCounter[] getNextLevelValues(String token, DateResolution type, String value);
}
