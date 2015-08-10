package com.simpleql.datamodel;

import java.util.ArrayList;
import java.util.List;

public class TreeRow {
	
	
	private Object data;
	
	private List<TreeRow> subRows;
	
	private boolean selected;
	
	private boolean selectAll;
	
	public TreeRow(Object data){
		this.setData(data);
		this.subRows = new ArrayList<TreeRow>();
		this.selected = false;
		this.setSelectAll(false);
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public void addSubRow(TreeRow row){
		getSubRows().add(row);
	}

	/**
	 * @return the subRows
	 */
	public List<TreeRow> getSubRows() {
		return subRows;
	}
	
	public boolean isSelected(){
		return selected;
		
	}

    public void setSelected(boolean selected){
    	this.selected = selected;
    	for(int i = 0; i < subRows.size(); i++){
    		subRows.get(i).setSelected(selected);
    	}
    }

	/**
	 * @return the selectAll
	 */
	public boolean isSelectAll() {
		return selectAll;
	}

	/**
	 * @param selectAll the selectAll to set
	 */
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}	

}
