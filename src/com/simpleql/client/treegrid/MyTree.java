package com.simpleql.client.treegrid;

import java.util.ArrayList;
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
					    String nodeValue = currentItem.getElement().getId();
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
	
	

    // To be finishied
	@Override
	public DateRangeElement[] getSelectedDates() {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		for(int i = 0; i < model.getRoot().getChildCount(); i++){
			TreeItem yearNode = model.getRoot().getChild(i);
			//if not dummy element
			if(yearNode.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getValue()){
					String year = yearNode.getElement().getId();
					
					DateRangeElement range = new DateRangeElement(DateResolution.Year, year, year);
					
				}else {
					
					
					
				}
				
				
			}
		}
		
		
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	public DateRangeElement[] getSelectedDates(TreeItem subTreeRoot) {
		List<DateRangeElement> list = new ArrayList<DateRangeElement>();
		
		for(int i = 0; i < subTreeRoot.getChildCount(); i++){
			TreeItem node = subTreeRoot.getChild(i);
			//if not dummy element
			if(node.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) node.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getValue()){
					String year = node.getElement().getId();
					
					DateRangeElement range = new DateRangeElement(DateResolution.Year, year, year);
					
				}else {
					
					
					
				}
				
				
			}
		}
		
		
		return list.toArray(new DateRangeElement[list.size()]);
	}
	
	
	@Override
	public Widget asWidget(){
		return tree;
		
	}

}
