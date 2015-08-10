package com.simpleql.treegrid;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasAttachHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.simpleql.datamodel.MyDate;
import com.simpleql.datamodel.TreeRow;

public class TreeGridCell implements  IsWidget {
	
	
	HorizontalPanel wrapper;
	
	CheckBox checkbox;
	
	Image expandButton;
	
    Label countLabel;
    
    TreeLevelEnum type;
    
    TreeGrid parent;
    
    MyDate date;
    
    public TreeGridCell(final MyDate date, int count, final TreeLevelEnum type, final TreeGrid parent, boolean checkBoxValue){
    	
    	wrapper = new HorizontalPanel();
    	checkbox = new CheckBox();
    	checkbox.setValue(checkBoxValue);
    	expandButton = new Image("images/expanded.png");
    	
    	this.parent = parent;
    	this.type = type;
    	this.date = date;
    	
    	if(type != TreeLevelEnum.hourLevel)
    	   countLabel = new Label("("+ count +")");
    	else
    	   countLabel = new Label("");
    	
    	countLabel.getElement().getStyle().setMarginLeft(20, Unit.PX);
    	
    	if(type != TreeLevelEnum.hourLevel)
    	   wrapper.add(expandButton);
    	
    	
    	
    	if(type == TreeLevelEnum.monthLevel){
    		wrapper.setStyleName("monthCell");
    		wrapper.getElement().setId("m-"+this.date.toString());
    		checkbox.setText(date.getMonth());
    	}else if(type == TreeLevelEnum.dayLevel){
    		wrapper.setStyleName("dayCell");
    		wrapper.getElement().setId("d-"+this.date.toString());
    		checkbox.setText(date.getDay());
    	}else if(type == TreeLevelEnum.hourLevel){
    		wrapper.setStyleName("hourCell");
    		wrapper.getElement().setId("h-"+this.date.toString());
    		checkbox.setText(date.getHour());
    	}else{
    		wrapper.getElement().setId("y-"+this.date.getYear());
    		checkbox.setText(date.getYear());
    		
    	}
    	
    	wrapper.add(checkbox);
    	wrapper.add(countLabel);
    	
    	checkbox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Boolean value = checkbox.getValue();
				
				List<TreeRow> subTree = parent.getModel().getTree().get(date.getYear());
				

					switch(type){
					case yearLevel:
						for(int i = 0; i < subTree.size(); i++){
							subTree.get(i).setSelected(value);
							subTree.get(i).setSelectAll(value);
						}
						break;
					case monthLevel:
                      for(int i = 0; i < subTree.size(); i++){
                    	  List<TreeRow> subSubTree = subTree.get(i).getSubRows();
                    	  for(int j = 0; j < subSubTree.size(); i++){
                    		  MyDate currentDate = (MyDate) subSubTree.get(j).getData();
                    		  if(currentDate.getMonth().equals(date.getMonth())){
                    			  subSubTree.get(j).setSelected(value);
                    			  break;
                    		  }
                    	  }
						}
						break;
					case dayLevel:
						 for(int i = 0; i < subTree.size(); i++){
	                    	  List<TreeRow> subSubTree = subTree.get(i).getSubRows();
	                    	  for(int j = 0; j < subSubTree.size(); i++){
	                    		  MyDate currentDate = (MyDate) subSubTree.get(j).getData();
	                    		  if(currentDate.getMonth().equals(date.getMonth()) && currentDate.getDay().equals(date.getDay())){
	                    			  subSubTree.get(j).setSelected(value);
	                    			  break;
	                    		  }
	                    	  }
							}
						break;
					case hourLevel:
						
						 for(int i = 0; i < subTree.size(); i++){
	                    	  List<TreeRow> subSubTree = subTree.get(i).getSubRows();
	                    	  for(int j = 0; j < subSubTree.size(); i++){
	                    		  MyDate currentDate = (MyDate) subSubTree.get(j).getData();
	                    		  if(currentDate.getMonth().equals(date.getMonth()) && currentDate.getDay().equals(date.getDay()) && currentDate.getHour().equals(date.getHour())){
	                    			  subSubTree.get(j).setSelected(value);
	                    			  break;
	                    		  }
	                    	  }
							}
						break;
					}
				
			}
    	});
    	
    	
    	checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				// TODO Auto-generated method stub
				parent.Draw();
			}
    		
    	});
    }
    
    
   
    
	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return wrapper;
	} 
	


}
