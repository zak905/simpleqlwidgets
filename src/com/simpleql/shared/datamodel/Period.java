package com.simpleql.shared.datamodel;

public class Period {
	
	private MyDate date;
	
	private String remarks;
	
	
	public Period(MyDate date, String remarks){
		this.setDate(date);
		this.setRemarks(remarks);
	}


	/**
	 * @return the date
	 */
	public MyDate getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(MyDate date) {
		this.date = date;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
