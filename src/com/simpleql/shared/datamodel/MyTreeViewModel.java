package com.simpleql.shared.datamodel;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;


public class MyTreeViewModel {
	
	TreeItem root;
	
	

	public MyTreeViewModel(){
		root = new TreeItem();
		root.setText("Dates");
		
		//Dummy element
		root.addItem(new TreeItem());
	}
	
	public MyTreeViewModel(Object[][] elements){
		root = new TreeItem();
		root.setText("Dates");
		
		//Dummy element
		MyTreeViewItem dummy = new MyTreeViewItem();
		root.addItem(dummy.getTreeItem());
		
		for(int i = 0; i < elements.length; i++){
			DateElementCounter element = (DateElementCounter) elements[i][0];
			DateResolution resolution = (DateResolution) elements[i][1];
			
			insert(element, resolution);
		}
	}
	
	
	public void insert(DateElementCounter element, DateResolution resolution){
		
		String value = element.getValue();
		
		
		switch(resolution){
		case Year:
			
			//root.addItem(new MyTreeViewItem(element, resolution));
			MyTreeViewItem temp = new MyTreeViewItem(element, resolution, this, null);
			
			//Dummy Empty element for displaying node expand button in case there is no children
			MyTreeViewItem dummy = new MyTreeViewItem();
			temp.getTreeItem().addItem(dummy.getTreeItem());
			
			root.addItem(temp.getTreeItem());
			//root.addTextItem("Test");
			break;
		case Month:
			
			String[] monthSplit = value.split("-");
			
			String year = monthSplit[0];
			
			
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				
				
			  //if this is not a dummy element
				 if(yearNode.getWidget() != null){
					 HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year)){
						MyTreeViewItem newNode = new MyTreeViewItem(element, resolution, this, yearNode);
						//Add dummy element
						newNode.getTreeItem().addItem(new TreeItem());
						
						yearNode.addItem(newNode);
						break;
					}
				 }
			}
		  break;
		case Day:
         String[] daySplit = value.split("-");
			
			String year2 = daySplit[0];
			String month2 = daySplit[1];
			
			
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				//If this is not a dummy element
				 if(yearNode.getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year2)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
							TreeItem monthNode =  yearNode.getChild(j);
							//If this is not a dummy element
							if(monthNode.getWidget() != null){
							HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
							CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
								if(checkBox2.getText().equals(month2)){
									MyTreeViewItem newNode  = new MyTreeViewItem(element, resolution, this, monthNode);
									//Dummy Element
									newNode.getTreeItem().addItem(new TreeItem());
									monthNode.addItem(newNode);
									break;
								}
						  }
						}
					
					}
				 }
			}
			break;
		case Hour:
               String[] hourSplit = value.split("-");
				
				String year3 = hourSplit[0];
				String month3 = hourSplit[1];
				
				String dayAndHour = hourSplit[2];
				
				String[] dayAndHourSplit = dayAndHour.split(" ");
				
				String day = dayAndHourSplit[0];
				
				for(int i = 0; i < root.getChildCount(); i++){
					TreeItem yearNode =  root.getChild(i);
					if(yearNode.getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
							TreeItem monthNode =  yearNode.getChild(j);
							if(monthNode.getWidget() != null){
							HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
							CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
							if(checkBox2.getText().equals(month3)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  monthNode.getChild(j);
									if(dayNode.getWidget() != null){
									HorizontalPanel container3 = (HorizontalPanel) dayNode.getWidget();
									CheckBox checkBox3 = (CheckBox) container3.getWidget(0);
										if(checkBox3.getText().equals(day)){
											dayNode.addItem(new MyTreeViewItem(element, resolution, this, dayNode));
											break;
										}
									}
							}
						}
					}
				   }
				  }
				 }
				}
				break;
		}
		
	}
	
	
	public TreeItem find(MyTreeViewItem item){
		String value = item.getData().getValue();
		DateResolution resolution = item.getDateResolution();
		
		switch(resolution){
		case Year:
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
					if(yearNode.getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(value)){
						return yearNode;
					}
			  }
			}
			break;
		case Month:
			String[] monthSplit = value.split("-");
			String year = monthSplit[0];
			String month = monthSplit[1];
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				if(yearNode.getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						if(monthNode.getWidget() != null){
							HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
							CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
							if(checkBox2.getText().equals(month)){
								return monthNode;
							}
						}
					  }
					}
			  }
			}
		  break;
		case Day:
	            String[] daySplit = value.split("-");
				
				String year2 = daySplit[0];
				String month2 = daySplit[1];
				String day2 = daySplit[2];
				
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				if(yearNode.getWidget() != null){
				HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getText().equals(year2)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					   if(monthNode.getWidget() != null){
							HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
							CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
							if(checkBox2.getText().equals(month2)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  monthNode.getChild(j);
									if(dayNode.getWidget() != null){
										HorizontalPanel container3 = (HorizontalPanel) dayNode.getWidget();
										CheckBox checkBox3 = (CheckBox) container3.getWidget(0);
										if(checkBox3.getText().equals(day2)){
											return dayNode;
										}
									}
							}
						  }
							
					   }
				}
			  }
			 }
			}
		  break;
		case Hour:
               String[] hourSplit = value.split("-");
				
				String year3 = hourSplit[0];
				String month3 = hourSplit[1];
				
				String dayAndHour = hourSplit[2];
				
				String[] dayAndHourSplit = dayAndHour.split(" ");
				
				String day = dayAndHourSplit[0];
				String hour = dayAndHourSplit[1];
				
				for(int i = 0; i < root.getChildCount(); i++){
					TreeItem yearNode =  root.getChild(i);
					if(yearNode.getWidget() != null){
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						if(monthNode.getWidget() != null){
						HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
						CheckBox checkBox2 = (CheckBox) container2.getWidget(0);		
							if(checkBox2.getText().equals(month3)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  yearNode.getChild(j);
										if(dayNode.getWidget() != null){
										HorizontalPanel container3 = (HorizontalPanel) dayNode.getWidget();
										CheckBox checkBox3 = (CheckBox) container3.getWidget(0);
										if(checkBox3.getText().equals(day)){
											for(int l = 0; l < dayNode.getChildCount(); l++){
												TreeItem hourNode =  yearNode.getChild(j);
												if(dayNode.getWidget() != null){
												HorizontalPanel container4 = (HorizontalPanel) dayNode.getWidget();
												CheckBox checkBox4 = (CheckBox) container4.getWidget(0);
												if(checkBox4.getText().equals(hour)){
													return hourNode;
												}
											 }
										}
								}
							  }
						 }
						}
				   }
				  }
				}
					}
				}
				break;
		}
		
		
		return null;
	}
	
	public TreeItem findByValue(String value, DateResolution resolution){
	
			
		switch(resolution){
		case Year:
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getText().equals(value)){
					return yearNode;
				}
			}
			break;
		case Month:
			String[] monthSplit = value.split("-");
			String year = monthSplit[0];
			String month = monthSplit[1];
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getText().equals(year)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
					CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
					if(checkBox2.getText().equals(month)){
						return monthNode;
					}
				  }
				}
			}
		  break;
		case Day:
	            String[] daySplit = value.split("-");
				
				String year2 = daySplit[0];
				String month2 = daySplit[1];
				String day2 = daySplit[2];
				
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
				CheckBox checkBox = (CheckBox) container.getWidget(0);
				if(checkBox.getText().equals(year2)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
					CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
					if(checkBox2.getText().equals(month2)){
						for(int k = 0; k < monthNode.getChildCount(); k++){
							TreeItem dayNode =  monthNode.getChild(j);
							HorizontalPanel container3 = (HorizontalPanel) dayNode.getWidget();
							CheckBox checkBox3 = (CheckBox) container3.getWidget(0);
							if(checkBox3.getText().equals(day2)){
								return dayNode;
							}
					}
				  }
				}
			  }
			}
		  break;
		case Hour:
               String[] hourSplit = value.split("-");
				
				String year3 = hourSplit[0];
				String month3 = hourSplit[1];
				
				String dayAndHour = hourSplit[2];
				
				String[] dayAndHourSplit = dayAndHour.split(" ");
				
				String day = dayAndHourSplit[0];
				String hour = dayAndHourSplit[1];
				
				for(int i = 0; i < root.getChildCount(); i++){
					TreeItem yearNode =  root.getChild(i);
					HorizontalPanel container = (HorizontalPanel) yearNode.getWidget();
					CheckBox checkBox = (CheckBox) container.getWidget(0);
					if(checkBox.getText().equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						HorizontalPanel container2 = (HorizontalPanel) monthNode.getWidget();
						CheckBox checkBox2 = (CheckBox) container2.getWidget(0);
						if(checkBox2.getText().equals(month3)){
							for(int k = 0; k < monthNode.getChildCount(); k++){
								TreeItem dayNode =  yearNode.getChild(j);
								HorizontalPanel container3 = (HorizontalPanel) dayNode.getWidget();
								CheckBox checkBox3 = (CheckBox) container3.getWidget(0);
								if(checkBox3.getText().equals(day)){
									for(int l = 0; l < dayNode.getChildCount(); l++){
										TreeItem hourNode =  yearNode.getChild(j);
										HorizontalPanel container4 = (HorizontalPanel) dayNode.getWidget();
										CheckBox checkBox4 = (CheckBox) container4.getWidget(0);
										if(checkBox4.getText().equals(hour)){
											return hourNode;
										}
								}
						}
					  }
					}
				  }
				}
				}
				break;
		}
		
		
		return null;
	}
	
	public TreeItem getRoot(){
		return root;
		
	}

}
