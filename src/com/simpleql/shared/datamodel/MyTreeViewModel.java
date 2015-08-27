package com.simpleql.shared.datamodel;


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
			
			insert(element, resolution, false);
		}
	}
	
	
	public void insert(DateElementCounter element, DateResolution resolution, boolean isChecked){
		
		String value = element.getValue();
		
		
		switch(resolution){
		case Year:
			MyTreeViewItem temp = new MyTreeViewItem(element, resolution, this, null, isChecked);
			
			//Dummy Empty element for displaying node expand button in case there is no children
			MyTreeViewItem dummy = new MyTreeViewItem();
			temp.getTreeItem().addItem(dummy.getTreeItem());
			
			root.addItem(temp.getTreeItem());
			break;
		case Month:
			
			String[] monthSplit = value.split("-");
			
			String year = monthSplit[0];
			
			
			for(int i = 0; i < root.getChildCount(); i++){
				TreeItem yearNode =  root.getChild(i);
				
				
			  //if this is not a dummy element
				 if(IfNodeIsNotDummy(yearNode)){
					if(getCheckBoxValueFromTreeItem(yearNode).equals(year)){
						MyTreeViewItem newNode = new MyTreeViewItem(element, resolution, this, yearNode, isChecked);
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
				 if(IfNodeIsNotDummy(yearNode)){
					if(getCheckBoxValueFromTreeItem(yearNode).equals(year2)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
							TreeItem monthNode =  yearNode.getChild(j);
							//If this is not a dummy element
							if(IfNodeIsNotDummy(monthNode)){
		
							String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month2));
								if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
									MyTreeViewItem newNode  = new MyTreeViewItem(element, resolution, this, monthNode, isChecked);
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
					if(IfNodeIsNotDummy(yearNode)){
					  if(getCheckBoxValueFromTreeItem(yearNode).equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
							TreeItem monthNode =  yearNode.getChild(j);
							if(IfNodeIsNotDummy(monthNode)){
							String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month3));
							  if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  monthNode.getChild(k);
									if(IfNodeIsNotDummy(dayNode)){
										if(getCheckBoxValueFromTreeItem(dayNode).equals(day)){
											dayNode.addItem(new MyTreeViewItem(element, resolution, this, dayNode, isChecked));
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
					if(IfNodeIsNotDummy(yearNode)){
					    if(getCheckBoxValueFromTreeItem(yearNode).equals(value)){
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
				if(IfNodeIsNotDummy(yearNode)){
					if(getCheckBoxValueFromTreeItem(yearNode).equals(year)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						if(IfNodeIsNotDummy(monthNode)){
							String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month));
							if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
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
				if(IfNodeIsNotDummy(yearNode)){
				if(getCheckBoxValueFromTreeItem(yearNode).equals(year2)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					   if(IfNodeIsNotDummy(monthNode)){
							String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month2));
							if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  monthNode.getChild(k);
									if(IfNodeIsNotDummy(dayNode)){
										if(getCheckBoxValueFromTreeItem(dayNode).equals(day2)){
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
					if(IfNodeIsNotDummy(yearNode)){
					if(getCheckBoxValueFromTreeItem(yearNode).equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						if(IfNodeIsNotDummy(monthNode)){	
						String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month3));
							if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									TreeItem dayNode =  monthNode.getChild(k);
										if(IfNodeIsNotDummy(dayNode)){
										if(getCheckBoxValueFromTreeItem(dayNode).equals(day)){
											for(int l = 0; l < dayNode.getChildCount(); l++){
												TreeItem hourNode =  dayNode.getChild(l);
												if(IfNodeIsNotDummy(hourNode)){
												   if(getCheckBoxValueFromTreeItem(hourNode).equals(hour)){
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
					if(IfNodeIsNotDummy(yearNode)){
					  if(getCheckBoxValueFromTreeItem(yearNode).equals(value)){
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
				if(IfNodeIsNotDummy(yearNode)){
				  if(getCheckBoxValueFromTreeItem(yearNode).equals(year)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					if(IfNodeIsNotDummy(monthNode)){
						String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month));
							if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
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
				if(IfNodeIsNotDummy(yearNode)){
				 if(getCheckBoxValueFromTreeItem(yearNode).equals(year2)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					TreeItem monthNode =  yearNode.getChild(j);
					if(IfNodeIsNotDummy(monthNode)){
						String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month2));
						if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
							for(int k = 0; k < monthNode.getChildCount(); k++){
								TreeItem dayNode =  monthNode.getChild(k);
								if(IfNodeIsNotDummy(dayNode)){
									if(getCheckBoxValueFromTreeItem(dayNode).equals(day2)){
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
					if(IfNodeIsNotDummy(yearNode)){
					 if(getCheckBoxValueFromTreeItem(yearNode).equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
						TreeItem monthNode =  yearNode.getChild(j);
						if(IfNodeIsNotDummy(monthNode)){
						String monthName = MyTreeViewItem.getMonthName(Integer.parseInt(month3));
						if(getCheckBoxValueFromTreeItem(monthNode).equals(monthName)){
							for(int k = 0; k < monthNode.getChildCount(); k++){
								TreeItem dayNode =  monthNode.getChild(k);
								if(IfNodeIsNotDummy(dayNode)){
									if(getCheckBoxValueFromTreeItem(dayNode).equals(day)){
										for(int l = 0; l < dayNode.getChildCount(); l++){
											TreeItem hourNode =  dayNode.getChild(l);
											if(IfNodeIsNotDummy(hourNode)){
												if(getCheckBoxValueFromTreeItem(hourNode).equals(hour)){
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
	
	public TreeItem getRoot(){
		return root;
		
	}
	
	private String getCheckBoxValueFromTreeItem(TreeItem node){
		
		HorizontalPanel container = (HorizontalPanel) node.getWidget();
		CheckBox checkBox = (CheckBox) container.getWidget(0);
		
		return checkBox.getText();
	}
	
	
	private boolean IfNodeIsNotDummy(TreeItem item){
		
		return item.getWidget() != null;
		
	}

}
