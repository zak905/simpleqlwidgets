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
		List<DateRangeElement> mergedSelection = MergeSubRanges(model.getRoot());
		return mergedSelection.toArray(new DateRangeElement[mergedSelection.size()]);
	}
	
	
	
	private List<DateRangeElement> MergeSubRanges(TreeItem subTreeRoot){
		List<DateRangeElement> currentRange = new ArrayList<DateRangeElement>();
		List<DateRangeElement> range = new ArrayList<DateRangeElement>();
		 List<DateRangeElement> childRanges = new ArrayList<DateRangeElement>();
		 boolean resultOfSubTreeUsingAnd = true;
		
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem child = subTreeRoot.getChild(i);
			
			if(child.getWidget() != null){
				
				
				HorizontalPanel container = (HorizontalPanel) child.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				
				String checkBoxClass = checkBox.getElement().getClassName();
			    String[] split = checkBoxClass.split(" ");
			    String resolutionClass = split[1];
			    resultOfSubTreeUsingAnd = resultOfSubTreeUsingAnd && checkBox.getValue();
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

				  range.addAll(childRanges);
				  
				if(checkBox.getValue()){
					String value = child.getElement().getId();
					DateRangeElement rangeElement = new DateRangeElement(resolution, value, value);
					
					
					
					if(childRanges.size() == 0)
					   currentRange.add(rangeElement);
				}else{
					
						if(currentRange.size() > 0){
						DateRangeElement firstElement = currentRange.get(0);
						DateRangeElement lastElement = currentRange.get(currentRange.size() - 1);
						
						DateRangeElement rangeElement = new DateRangeElement(resolution, firstElement.getFrom(), lastElement.getTo());
						range.add(rangeElement);
						
						
				    }
					
					currentRange.clear();
				}
				
			}
			
		}
		
		//One last check after exiting the loop
	 
		 if(currentRange.size() > 0){			
			DateRangeElement firstElement = currentRange.get(0);
			DateRangeElement lastElement = currentRange.get(currentRange.size() - 1);
			DateRangeElement rangeElement = new DateRangeElement(firstElement.getType(), firstElement.getFrom(), lastElement.getTo());
			
			if(currentRange.size()  == subTreeRoot.getChildCount() - 1){
				 DateResolution resolutionForFather;
				    if(firstElement.getType() == DateResolution.Month){
				    	resolutionForFather = DateResolution.Year;
				    }else if (firstElement.getType() == DateResolution.Day){
				    	resolutionForFather = DateResolution.Month;
				    }else if (firstElement.getType() == DateResolution.Hour){
				    	resolutionForFather = DateResolution.Day;
				    } else{
				    	resolutionForFather = DateResolution.Year;
				    	 range.add(rangeElement);
				    	return range;
				    }
				    
				DateRangeElement parentElement = new DateRangeElement(resolutionForFather, subTreeRoot.getElement().getId(), subTreeRoot.getElement().getId());
				range.add(parentElement);
			}else{
			    range.add(rangeElement);
			}
			
		 }
	 
	
		return range;
		
	}
	
	
	@Override
	public Widget asWidget(){
		return tree;
	}
	
	

}
