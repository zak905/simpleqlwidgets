package com.simpleql.shared.datamodel;

public class DateSelectedPair {
	
	private String dateRangeElement;
	
	private Boolean selected;
	
	public DateSelectedPair(String dateRangeElement, Boolean selected){
		this.setDateRangeElement(dateRangeElement);
		this.setSelected(selected);
	}

	/**
	 * @return the dateRangeElement
	 */
	public String getDateRangeElement() {
		return dateRangeElement;
	}

	/**
	 * @param dateRangeElement the dateRangeElement to set
	 */
	public void setDateRangeElement(String dateRangeElement) {
		this.dateRangeElement = dateRangeElement;
	}

	/**
	 * @return the selected
	 */
	public Boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}
