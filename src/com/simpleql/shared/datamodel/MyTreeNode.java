package com.simpleql.shared.datamodel;

import java.util.ArrayList;
import java.util.List;

public class MyTreeNode {
	
	DateElementCounter data;
	
	DateResolution resolution;
	
	boolean visited;
	
	List<MyTreeNode> children;
	
	
	public MyTreeNode(DateElementCounter data, DateResolution resolution){
		this.data = data;
		this.resolution = resolution;
		children = new ArrayList<MyTreeNode>();
		visited = false;
		
	}
	
	public MyTreeNode(){
		data = new DateElementCounter();
		resolution = DateResolution.Year;
		children = new ArrayList<MyTreeNode>();
		
	}
	
	public DateElementCounter getNodeData(){
		return data;
		
	}
	
	public void addItem(MyTreeNode treeNode){
		children.add(treeNode);
		
	}
	
	public int getChildCount(){
		
		return children.size();
	}
	
	public MyTreeNode getChild(int index){
		return children.get(index);
		
	}
	
	public String getNodeValue(){
		
		switch(resolution){
		case Year:
			return data.getValue();
		case Month:
			String[] monthSplit = data.getValue().split("-");
			String month = monthSplit[1];
			return month;
		case Day:    
			String[] daySplit = data.getValue().split("-");

			String day = daySplit[2];
			
			return day;

		case Hour:
            String[] hourSplit = data.getValue().split("-");
			
				String year3 = hourSplit[0];
				String month3 = hourSplit[1];
				
				String dayAndHour = hourSplit[2];
				
				String[] dayAndHourSplit = dayAndHour.split(" ");
				
				String hour = dayAndHourSplit[1];
				
				return hour;
		}
		
	    return "";
	}
	
	public DateResolution getDateResolution(){
		
		return resolution;
	}
	
	public boolean isVisited(){
		return visited;
		
	}
	
	public void setVisited(boolean value){
		this.visited = value;
		
	}

}
