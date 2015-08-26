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
import com.simpleql.shared.datamodel.MyTreeViewItem;
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
				   final  boolean isChecked = checkbox.getValue();
					    final String nodeValue = currentItem.getElement().getId();
					    String checkBoxClass = checkbox.getElement().getClassName();
					    String[] split = checkBoxClass.split(" ");
					    final String resolutionClass = split[1];
					    
					    //System.out.println("Value " + value + "Resolution " + resolutionClass);
					   
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
									model.insert(result[i], resolution, isChecked);
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
									model.insert(result[i], resolution, false);
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
		
				/*for(int i = 0; i < model.getRoot().getChildCount(); i++){
					TreeItem yearNode = model.getRoot().getChild(i);
					//if not dummy element
					if(yearNode.getWidget() != null){
						HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
						CheckBox checkBox = (CheckBox) container.getWidget(0);
						if(checkBox.getValue()){
							String year = yearNode.getElement().getId();
							DateRangeElement rangeElement = new DateRangeElement(DateResolution.Year, year, year);
							
							DateRangeElement[] selectedMonths = getSelectedDatesForMonths(yearNode);
							List<DateRangeElement> monthsAsList = Arrays.asList(selectedMonths);
							
							
							
							if(monthsAsList.size() > 0 && monthsAsList.size() < yearNode.getChildCount()){
								List<DateRangeElement> mergedSelection = MergeSubRanges(yearNode);
							   list.addAll(mergedSelection);
							  // System.out.println("case 1");
							}else if(monthsAsList.size() == model.getRoot().getChildCount()){
								List<DateRangeElement> mergedSelection = MergeSubRanges(model.getRoot());
								   list.addAll(mergedSelection);
								   //System.out.println("case 2");
							}else{
								List<DateRangeElement> mergedSelection = MergeSubRanges(model.getRoot());
								  list.addAll(mergedSelection);
								//list.addAll(monthsAsList);
								//System.out.println("case 3");
							}
							 	
						}
						
						
					}
				}*/
				
	       
			List<DateRangeElement> mergedSelection = MergeSubRanges(model.getRoot());
		return mergedSelection.toArray(new DateRangeElement[mergedSelection.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForMonths(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();

		
		
				for(int i = 0; i < subTreeRoot.getChildCount(); i++){
					TreeItem montNode = subTreeRoot.getChild(i);
					
					//if not dummy element
					if(montNode.getWidget() != null){
						HorizontalPanel container = (HorizontalPanel) montNode.getWidget();
						CheckBox checkBox = (CheckBox) container.getWidget(0);
						if(checkBox.getValue()){
							String month = montNode.getElement().getId();
							
							DateRangeElement rangeElement = new DateRangeElement(DateResolution.Month, month, month);
							
							
							DateRangeElement[] selectedDays = getSelectedDatesForDays(montNode);
							List<DateRangeElement> daysAsList = Arrays.asList(selectedDays);
							if(daysAsList.size() > 0 && daysAsList.size() < montNode.getChildCount()){
								List<DateRangeElement> mergedSelection = MergeSubRanges(montNode);
							    list.addAll(mergedSelection);
							}else{
								list.add(rangeElement);
							}
							
						}
						
						
						
					}
				}
				

		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForDays(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();

		
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem dayNode = subTreeRoot.getChild(i);
			//if not dummy element
			if(dayNode.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) dayNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getValue()){
					String dayResolution = dayNode.getElement().getId();
					
					DateRangeElement rangeElement = new DateRangeElement(DateResolution.Day, dayResolution, dayResolution);
					DateRangeElement[] selectedHours = getSelectedDatesForHours(dayNode);
					List<DateRangeElement> hoursAsList = Arrays.asList(selectedHours);
					if(hoursAsList.size() > 0 && hoursAsList.size() < dayNode.getChildCount()){
					List<DateRangeElement> mergedSelection = MergeSubRanges(dayNode);
					  list.addAll(mergedSelection);
					}else{
					  list.add(rangeElement);
					}
					
				}
				
				
			}
		}
		
		
		
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDatesForHours(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		
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
				
				

		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	
	private List<DateRangeElement> MergeSubRanges(TreeItem subTreeRoot){
		int subrangeCount = 0;
		List<DateRangeElement> currentRange = new ArrayList<DateRangeElement>();
		List<DateRangeElement> range = new ArrayList<DateRangeElement>();
		 List<DateRangeElement> childRanges = new ArrayList<DateRangeElement>();
		
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem child = subTreeRoot.getChild(i);
			
			if(child.getWidget() != null){
				
				
				HorizontalPanel container = (HorizontalPanel) child.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				
				String checkBoxClass = checkBox.getElement().getClassName();
			    String[] split = checkBoxClass.split(" ");
			    String resolutionClass = split[1];

			    DateResolution resolution;
			    if(resolutionClass.equals("year")){
			    	resolution = DateResolution.Year;
			    }else if (resolutionClass.equals("month")){
			    	resolution = DateResolution.Month;
			    }else if (resolutionClass.equals("day")){
			    	resolution = DateResolution.Day;
			    } else{
			    	resolution = DateResolution.Hour;
			    }
				
				
			   childRanges = MergeSubRanges(child);
			   //System.out.println("children count" + subTreeRoot.getChildCount() + " current Range " + currentRange.size());
				range.addAll(childRanges);
				
				
				if(checkBox.getValue()){
					 
					
					String value = child.getElement().getId();
					DateRangeElement rangeElement = new DateRangeElement(resolution, value, value);
					
					subrangeCount++;
					
					
					   currentRange.add(rangeElement);
					
				}else{
					
						if(currentRange.size() > 0){
						DateRangeElement firstElement = currentRange.get(0);
						DateRangeElement lastElement = currentRange.get(currentRange.size() - 1);
						
						DateRangeElement rangeElement = new DateRangeElement(resolution, firstElement.getFrom(), lastElement.getTo());
						range.add(rangeElement);
						
						
				    }
					subrangeCount = 0;
					currentRange.clear();
				}
				
			}
			
		}
		
		//One last check after exiting the loop
	
	 if(!currentRange.isEmpty()){
		
				
		 if(currentRange.size() > 0){
			//System.out.println("first case" + currentRange.size() + " " + subTreeRoot.getChildCount());
		
			
			DateRangeElement firstElement = currentRange.get(0);
			DateRangeElement lastElement = currentRange.get(currentRange.size() - 1);
			//System.out.println("from " + firstElement.getFrom() + "to " + lastElement.getTo());
			DateRangeElement rangeElement = new DateRangeElement(firstElement.getType(), firstElement.getFrom(), lastElement.getTo());
			/*if(currentRange.size()  == subTreeRoot.getChildCount() - 1){
				 DateResolution resolutionForFather;
				    if(firstElement.getType() == DateResolution.Month){
				    	resolutionForFather = DateResolution.Year;
				    }else if (firstElement.getType() == DateResolution.Day){
				    	resolutionForFather = DateResolution.Month;
				    }else if (firstElement.getType() == DateResolution.Hour){
				    	resolutionForFather = DateResolution.Day;
				    } else{
				    	range.clear();
				    	range.add(rangeElement);
				    	System.out.println("last case");
				    	return range;
				    }
				    
				DateRangeElement parentElement = new DateRangeElement(resolutionForFather, subTreeRoot.getElement().getId(), subTreeRoot.getElement().getId());
				range.clear();
				range.add(parentElement);
			}else{*/
			   range.add(rangeElement);
			  // range.addAll(childRanges);
			//}
			
		 }
	 }
	
		return range;
		
	}
	
	
	@Override
	public Widget asWidget(){
		return tree;
	}

}
