package com.simpleql.shared.datamodel;

public class MyDate {

	private String year;
	private String month;
	private String day;
	private String hour;
	/**
	 * @return the year
	 */
	
	public MyDate(String year, String month, String day, String hour){
		 this.year = year;
		 this.month = month;
		 this.day = day;
		 this.hour = hour;
	}
	
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the hour
	 */
	public String getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	@Override
	public String toString(){
		return day+"/"+month+"/"+year+" "+hour;
	}
	
	@Override
	public int hashCode(){
		
		if(getHour() == null)
		  return getYear().hashCode() + getMonth().hashCode() + getDay().hashCode();
		else
			return getYear().hashCode() + getMonth().hashCode() + getDay().hashCode() + getHour().hashCode();
	}
	
	@Override
	public boolean equals(Object date){
		MyDate toComapre = (MyDate) date;
		boolean result = false;
		if(toComapre.getYear().equals(getYear()) && toComapre.getMonth().equals(getMonth()) && toComapre.getDay().equals(getDay()) && toComapre.getHour().equals(getHour()))
		 result = true;
		 
		return result;
	}
}
