package com.simpleql.shared.datamodel;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsTreeItem;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.simpleql.client.treegrid.MyTree;

public class MyTreeViewItem implements IsTreeItem {
	
	DateElementCounter data;
	
	TreeItem item;
	
	private CheckBox checkBox;
	
	Label counter;
	
	DateResolution resolution;
	
	HorizontalPanel wrapper;
	
	private String nodeValue;
	
	private boolean selected;
	
	boolean open;
	
	MyTreeViewModel model;
	
	TreeItem parent;
	
	

	public MyTreeViewItem(DateElementCounter data, DateResolution resolution, MyTreeViewModel model, TreeItem parent, boolean isChecked){
		this.data = data;
		this.resolution = resolution; 
		this.model = model;
		this.parent = parent;
		
		checkBox = new CheckBox();
		checkBox.setValue(isChecked);
		AddSelectEvent();
		counter = new Label("(" + String.valueOf(data.getCount()) +")");
		counter.getElement().getStyle().setMarginLeft(20, Unit.PX);
		
		String value = data.getValue();
		switch(resolution){
		case Year:
			
			String[] split = value.split("-");
			String year = split[0];
			
			nodeValue = year;
			checkBox.getElement().addClassName("year");
			break;
		case Month:
			
			String[] monthSplit = value.split("-");
			
			String month = monthSplit[1];
			
			int monthIndex = Integer.parseInt(month);
			nodeValue = getMonthName(monthIndex);
			checkBox.getElement().addClassName("month");
		  break;
		case Day:
			
            String[] daySplit = value.split("-");
			
			String day = daySplit[2];
			
			nodeValue = day;
			checkBox.getElement().addClassName("day");
			break;
		case Hour:
			
			  String[] hourSplit = value.split(" ");
				
		     String hour = hourSplit[1];
		     
		     nodeValue = hour;
		     checkBox.getElement().addClassName("hour");
             
			break;
		
		}
		
		checkBox.setText(getNodeValue());
		wrapper = new HorizontalPanel();
		wrapper.add(checkBox);
		wrapper.add(counter);
		item = new TreeItem(wrapper);
		item.getElement().setId(data.getValue());
	}
	
	public MyTreeViewItem(){
		item = new TreeItem();
	}

	@Override
	public TreeItem asTreeItem() {
		// TODO Auto-generated method stub
		return item;
	}
	
	

	/**
	 * @return the checkBox
	 */
	public CheckBox getCheckBox() {
		return checkBox;
	}

	/**
	 * @return the nodeValue
	 */
	public String getNodeValue() {
		return nodeValue;
	}

	
	public TreeItem getTreeItem(){
		
		return item;
	}
	
	private void AddSelectEvent(){
		checkBox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {

				CheckBox selectedCheckbox = (CheckBox) event.getSource();

				TreeItem selectedItem = model.find(MyTreeViewItem.this);
				
				if(selectedItem != null){
				selectSubTree(selectedItem, selectedCheckbox.getValue());
				}
			}
		});
		
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	} 
	
	
	public DateResolution getDateResolution(){
		return resolution;
	}
	
	public DateElementCounter getData(){
		
		return data;
	}
	
	private void selectSubTree(TreeItem item, boolean value){
		//if this is not a dummy element
		if(item.getWidget() != null){
			
			//apply selection
			HorizontalPanel container = (HorizontalPanel) item.getWidget();
			this.selected = value;
			CheckBox checkBox = (CheckBox) container.getWidget(0);
			checkBox.setValue(value);
			
		
			// Take care of parents 
		    	if(CheckIfParentisToBeSelected(parent, item)){
		    		setValueOfParent(parent, value);
		    	}
		    
			
		
			
			//Take Care of Children nodes
		if(item.getChildCount() > 0){
			for(int i = 0 ; i < item.getChildCount(); i++){
				selectSubTree(item.getChild(i), value);
			}
		}
		
			
		 }
		
	}
	
private void setValueOfParent(TreeItem parent, boolean value){

	
	if(parent.getWidget() != null){
		
		HorizontalPanel parentContainer = (HorizontalPanel) parent.getWidget();
		CheckBox parentCheckBox = (CheckBox) parentContainer.getWidget(0);
		parentCheckBox.setValue(value);
		 String checkBoxClass = parentCheckBox.getElement().getClassName();
		    String[] split = checkBoxClass.split(" ");
		    String resolutionClass = split[1];
		    String nodeData = parent.getElement().getId();

		    //Go one level up
		    DateResolution resolution;
		    if(resolutionClass.equals("month")){
		    	resolution = DateResolution.Year;
		    	String[] yearSplit = nodeData.split("-");
		    	nodeData = yearSplit[0];
		    }else if (resolutionClass.equals("day")){
		    	resolution = DateResolution.Month;
		    }else if (resolutionClass.equals("hour")){
		    	resolution = DateResolution.Day;
		    } else{
		    	return;
		    }
		//parents parent
		    TreeItem grandParent = model.findByValue(nodeData, resolution);
		
		 
		  if(grandParent != null){
			  if(CheckIfParentisToBeSelected(grandParent, item)){
					setValueOfParent(grandParent, value);
				}
		}
	}
	
}



private boolean CheckIfParentisToBeSelected(TreeItem parent, TreeItem child){
	boolean leftHandSide = false;
	
	boolean rightHandSideUsingOr = false;
	
	boolean rightHandSideUsingAnd = false;
	
	if(parent == null)
		return false;
	
	HorizontalPanel parentContainer = (HorizontalPanel) parent.getWidget();
	CheckBox parentCheckBox = (CheckBox) parentContainer.getWidget(0);
	leftHandSide = parentCheckBox.getValue();
	
	HorizontalPanel childContainer = (HorizontalPanel) child.getWidget();
	CheckBox childCheckBox = (CheckBox) childContainer.getWidget(0);
	rightHandSideUsingOr = childCheckBox.getValue();
	
	for(int i = 0; i < parent.getChildCount(); i++){
			if(parent.getChild(i).getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) parent.getChild(i).getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					
					
						rightHandSideUsingOr = rightHandSideUsingOr || checkBox.getValue();
						
					
						rightHandSideUsingAnd = rightHandSideUsingAnd && checkBox.getValue();
					
				
		}
	}
	
	
		if(rightHandSideUsingOr != rightHandSideUsingAnd){
			if(leftHandSide){
				if(rightHandSideUsingOr)
				   return false;
				else
					return true;
			}else {
				if(!rightHandSideUsingOr)
					  return false;
				else
					return true;
			}
			
			
		}else{
			if(leftHandSide == rightHandSideUsingOr)
			   return false;
			else
				return true;
		}

}

	
	
	


public static String getMonthName(int month){
    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return monthNames[month - 1];
}

public static int getMonthIndex(String monthName){
	Map<String, Integer> months = new HashMap<String, Integer>();
	months.put("January", 1);
	months.put("February", 2);
	months.put("March", 3);
	months.put("April", 4);
	months.put("May", 5);
	months.put("June", 6);
	months.put("July", 7);
	months.put("August", 8);
	months.put("September", 9);
	months.put("October", 10);
	months.put("November", 11);
	months.put("December", 12);
    
    return months.get(monthName);
}

}
