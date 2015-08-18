package com.simpleql.shared.datamodel;

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

	public MyTreeViewItem(DateElementCounter data, DateResolution resolution, MyTreeViewModel model, TreeItem parent){
		this.data = data;
		this.resolution = resolution;
		this.model = model;
		this.parent = parent;
		
		checkBox = new CheckBox();
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
			System.out.println(value);
			
			String month = monthSplit[1];
			
			nodeValue = month;
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
			HorizontalPanel container = (HorizontalPanel) item.getWidget();
			this.selected = value;
			CheckBox checkBox = (CheckBox) container.getWidget(0);
			checkBox.setValue(value);
			
			// To be Tested
			/*if(AllSiblingsHasSameValue(item, value)){
				if(parent.getWidget() != null){
					
				HorizontalPanel parentContainer = (HorizontalPanel) parent.getWidget();
				CheckBox parentCheckBox = (CheckBox) parentContainer.getWidget(0);
				System.out.println(parentCheckBox.getText());
				parentCheckBox.setValue(value);
				}
			
			
			
		}*/
			
		if(item.getChildCount() > 0){
			for(int i = 0 ; i < item.getChildCount(); i++){
				selectSubTree(item.getChild(i), value);
			}
		}
			
		 }
		
	}


private boolean AllSiblingsHasSameValue(TreeItem item, boolean value){
	
	boolean flag = false;
	
	for(int i = 0; i < parent.getChildCount(); i++){
		if(!parent.getChild(i).equals(item)){
			if(parent.getChild(i).getWidget() != null){
			HorizontalPanel container = (HorizontalPanel) parent.getChild(i).getWidget();
			CheckBox checkBox = (CheckBox) container.getWidget(0);
			if(checkBox.getValue().equals(value))
				flag = true;
			else
				flag = false;
				
			}
			
		}
	}
	
	
	return flag;
}

}
