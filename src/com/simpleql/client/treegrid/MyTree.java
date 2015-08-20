package com.simpleql.client.treegrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.simpleql.shared.datamodel.DateElementCounter;
import com.simpleql.shared.datamodel.DateRangeElement;
import com.simpleql.shared.datamodel.DateRangeSelector;
import com.simpleql.shared.datamodel.DateResolution;
import com.simpleql.shared.datamodel.MyTreeViewModel;
import com.simpleql.server.ServerStub;
import com.simpleql.server.ServerStubAsync;


public class MyTree extends DateRangeSelector {
	
	Tree tree;
	
	final MyTreeViewModel model = new MyTreeViewModel();
	
	private final ServerStubAsync serverStub = GWT.create(ServerStub.class);

	public MyTree(String token) {
		super(token);
		tree = new Tree();
		tree.addItem(model.getRoot());
	}
	
	public void InitializeEvents(){
		
		tree.addOpenHandler(new OpenHandler<TreeItem>(){
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				
				TreeItem currentItem = event.getTarget();
				//currentItem.
				
				if(!currentItem.equals(model.getRoot())){
						HorizontalPanel container = (HorizontalPanel) currentItem.getWidget();
					    CheckBox checkbox = (CheckBox) container.getWidget(0);
					    final String nodeValue = currentItem.getElement().getId();
					    String checkBoxClass = checkbox.getElement().getClassName();
					    String[] split = checkBoxClass.split(" ");
					    final String resolutionClass = split[1];
					    
					    DateResolution resolution;
					    if(resolutionClass.equals("year")){
					    	resolution = DateResolution.Month;
					    }else if (resolutionClass.equals("month")){
					    	resolution = DateResolution.Day;
					    }else if (resolutionClass.equals("day")){
					    	resolution = DateResolution.Hour;
					    } else{
					    	//Leaf level, so there is no children
					    	return;
					    }

					           
						serverStub.getNextLevelValues("", resolution, nodeValue, new AsyncCallback<DateElementCounter[]>(){
							@Override
							public void onFailure(Throwable caught) {
								//Do something on failure
								System.out.println("[INFO] Call to server failed");
							}
							@Override
							public void onSuccess(com.simpleql.shared.datamodel.DateElementCounter[] result) {
								  DateResolution resolution;
								    if(resolutionClass.equals("year")){
								    	resolution = DateResolution.Month;
								    }else if (resolutionClass.equals("month")){
								    	resolution = DateResolution.Day;
								    }else {
								    	resolution = DateResolution.Hour;
								    }
								    
								for(int i = 0; i < result.length; i++){
									model.insert(result[i], resolution);
								}
								
								System.out.println("[INFO] Call to ServerStub successful: received "+resolution.toString()+" level values");
								
							}
							
							
						});
						
					}else{
						serverStub.getNextLevelValues("", DateResolution.Year, "", new AsyncCallback<DateElementCounter[]>(){
							@Override
							public void onFailure(Throwable caught) {

								//Do something on failure
								System.out.println("[INFO] Call to server failed");
							}
							@Override
							public void onSuccess(DateElementCounter[] result) {
								  DateResolution resolution = DateResolution.Year;
								    
								for(int i = 0; i < result.length; i++){
									model.insert(result[i], resolution);
								}
								System.out.println("[INFO] Call to ServerStub successful: received Root level values");
							}
							
							
						});
						
						
					}
					
			     }
				});
	}
	
	

    
	@Override
	public DateRangeElement[] getSelectedDates() {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		if(isRangeContinuous(model.getRoot())){
			
			TreeItem firstItem = getFirstElementOfTheRange(model.getRoot());
			TreeItem lastItem = getLastElementOfTheRange(model.getRoot());
			
			if(firstItem != null && lastItem != null){
				
				String begining = firstItem.getElement().getId();

				String end = lastItem.getElement().getId();

				
			DateRangeElement rangeElement = new DateRangeElement(DateResolution.Year, begining, end);
			
			list.add(rangeElement);
		
			}
			
			
		}else{
		
				for(int i = 0; i < model.getRoot().getChildCount(); i++){
					TreeItem yearNode = model.getRoot().getChild(i);
					//if not dummy element
					if(yearNode.getWidget() != null){
						HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
						CheckBox checkBox = (CheckBox) container.getWidget(0);
						if(checkBox.getValue()){
							String year = yearNode.getElement().getId();
							DateRangeElement rangeElement = new DateRangeElement(DateResolution.Year, year, year);
							list.add(rangeElement);
							
						}else {
							DateRangeElement[] selectedMonths = getSelectedDatesForMonths(yearNode);
							List<DateRangeElement> monthsAsList = Arrays.asList(selectedMonths);
							list.addAll(monthsAsList);
							
						}
						
						
					}
				}
		
		}
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForMonths(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		
		 if(isRangeContinuous(subTreeRoot)){
				
				TreeItem firstItem = getFirstElementOfTheRange(subTreeRoot);
				TreeItem lastItem = getLastElementOfTheRange(subTreeRoot);
				
				
				if(firstItem != null && lastItem != null){
					String begining = firstItem.getElement().getId();

					String end = lastItem.getElement().getId();

					
				DateRangeElement rangeElement = new DateRangeElement(DateResolution.Month, begining, end);
				
				list.add(rangeElement);
			
				}
				
				
			}else{
		
				for(int i = 0; i < subTreeRoot.getChildCount(); i++){
					TreeItem montNode = subTreeRoot.getChild(i);
					
					//if not dummy element
					if(montNode.getWidget() != null){
						HorizontalPanel container = (HorizontalPanel) montNode.getWidget();
						CheckBox checkBox = (CheckBox) container.getWidget(0);
						if(checkBox.getValue()){
							String month = montNode.getElement().getId();
							
							DateRangeElement rangeElement = new DateRangeElement(DateResolution.Month, month, month);
							list.add(rangeElement);
							
						}else {
							DateRangeElement[] selectedDays = getSelectedDatesForDays(montNode);
							List<DateRangeElement> daysAsList = Arrays.asList(selectedDays);
							list.addAll(daysAsList);
							
						}
						
						
					}
				}
		
	     }
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForDays(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		
         if(isRangeContinuous(subTreeRoot)){
			
			TreeItem firstItem = getFirstElementOfTheRange(subTreeRoot);
			TreeItem lastItem = getLastElementOfTheRange(subTreeRoot);
			
			
			if(firstItem != null && lastItem != null){
				String begining = firstItem.getElement().getId();

				String end = lastItem.getElement().getId();

				
			DateRangeElement rangeElement = new DateRangeElement(DateResolution.Day, begining, end);
			
			list.add(rangeElement);
			}
			
			
		}else{
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem dayNode = subTreeRoot.getChild(i);
			//if not dummy element
			if(dayNode.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) dayNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getValue()){
					String dayResolution = dayNode.getElement().getId();
					
					DateRangeElement rangeElement = new DateRangeElement(DateResolution.Day, dayResolution, dayResolution);
					list.add(rangeElement);
				}else {
					DateRangeElement[] selectedHours = getSelectedDatesForHours(dayNode);
					List<DateRangeElement> hoursAsList = Arrays.asList(selectedHours);
					list.addAll(hoursAsList);
				}
				
				
			}
		}
		
		}
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForHours(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		if(isRangeContinuous(subTreeRoot)){
			
			TreeItem firstItem = getFirstElementOfTheRange(subTreeRoot);
			TreeItem lastItem = getLastElementOfTheRange(subTreeRoot);
			
			
			if(firstItem != null && lastItem != null){
				String begining = firstItem.getElement().getId();
				String end = lastItem.getElement().getId();
			    DateRangeElement rangeElement = new DateRangeElement(DateResolution.Hour, begining, end);
			
			list.add(rangeElement);
		
			}
			
			
		}else{
				for(int i = 0; i < subTreeRoot.getChildCount(); i++){
					TreeItem node = subTreeRoot.getChild(i);
					//if not dummy element
					if(node.getWidget() != null){
						HorizontalPanel container = (HorizontalPanel) node.getWidget();
						CheckBox checkBox = (CheckBox) container.getWidget(0);
						if(checkBox.getValue()){
							String hourResolution = node.getElement().getId();
							DateRangeElement rangeElement = new DateRangeElement(DateResolution.Hour, hourResolution, hourResolution);
							list.add(rangeElement);
						}
					}
				}
		
		}
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	
	
	private boolean isRangeContinuous(TreeItem subTreeRoot){
		
		boolean flag = false;
		
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem node = subTreeRoot.getChild(i);
			
			if(node.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) node.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getValue()){
					flag = true;
				}else{
					flag = false;
					break;
				}
		    }
		}
		
		return flag;
	}
	
	private TreeItem getFirstElementOfTheRange(TreeItem subTreeRoot){
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem node = subTreeRoot.getChild(i);
			//return first non null element
			if(node.getWidget() != null){
				return node;
		    }
		}
		return null;
		
	}
	
	private TreeItem getLastElementOfTheRange(TreeItem subTreeRoot){
		for(int i = subTreeRoot.getChildCount() - 1; i > 0 ; i++){
			TreeItem node = subTreeRoot.getChild(i);
			//return first to last non null element
			if(node.getWidget() != null){
				return node;
		    }
		}
		return null;
	}
	
	
	@Override
	public Widget asWidget(){
		return tree;
	}

}
