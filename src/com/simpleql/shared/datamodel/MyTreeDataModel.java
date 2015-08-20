package com.simpleql.shared.datamodel;


public class MyTreeDataModel {
	
	MyTreeNode root;
	
	
	
	public MyTreeDataModel(){
		root = new MyTreeNode();
	}
	
	public MyTreeDataModel(Object[][] elements){
		root = new MyTreeNode();
		
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
			
			//root.addItem(new MyTreeNode(element, resolution));
			MyTreeNode temp = new MyTreeNode(element, resolution);
			root.addItem(temp);
			//root.addTextItem("Test");
			break;
		case Month:
			
			String[] monthSplit = value.split("-");
			
			String year = monthSplit[0];
			
			
			for(int i = 0; i < root.getChildCount(); i++){
				MyTreeNode yearNode =  root.getChild(i);

				if(yearNode.getNodeValue().equals(year)){
					yearNode.addItem(new MyTreeNode(element, resolution));
					break;
				}
			}
		  break;
		case Day:
         String[] daySplit = value.split("-");
			
			String year2 = daySplit[0];
			String month2 = daySplit[1];
			
			
			for(int i = 0; i < root.getChildCount(); i++){
				MyTreeNode yearNode =  root.getChild(i);

				if(yearNode.getNodeValue().equals(year2)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
						MyTreeNode monthNode =  yearNode.getChild(j);

						if(monthNode.getNodeValue().equals(month2)){
							monthNode.addItem(new MyTreeNode(element, resolution));
							break;
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
					MyTreeNode yearNode =  root.getChild(i);

					if(yearNode.getNodeValue().equals(year3)){
						for(int j = 0; j < yearNode.getChildCount(); j++){
							MyTreeNode monthNode =  yearNode.getChild(j);
			
							if(monthNode.getNodeValue().equals(month3)){
								for(int k = 0; k < monthNode.getChildCount(); k++){
									MyTreeNode dayNode =  monthNode.getChild(k);

									if(dayNode.getNodeValue().equals(day)){
										dayNode.addItem(new MyTreeNode(element, resolution));
										break;
									}
							}
						}
					}
				  }
				}
				break;
		}
		
	}

public MyTreeNode findByValue(String value, DateResolution resolution){

	switch(resolution){
	case Year:
		for(int i = 0; i < root.getChildCount(); i++){
			MyTreeNode yearNode =  root.getChild(i);

			if(yearNode.getNodeData().getValue().equals(value)){
				return yearNode;
			}
		}
		break;
	case Month:
		String[] monthSplit = value.split("-");
		String year = monthSplit[0];
		String month = monthSplit[1];
		for(int i = 0; i < root.getChildCount(); i++){
			MyTreeNode yearNode =  root.getChild(i);
		
			if(yearNode.getNodeValue().equals(year)){
				for(int j = 0; j < yearNode.getChildCount(); j++){
				MyTreeNode monthNode =  yearNode.getChild(j);

				if(monthNode.getNodeValue().equals(month)){
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
			MyTreeNode yearNode =  root.getChild(i);

			if(yearNode.getNodeValue().equals(year2)){
				for(int j = 0; j < yearNode.getChildCount(); j++){
				MyTreeNode monthNode =  yearNode.getChild(j);

				if(monthNode.getNodeValue().equals(month2)){
					for(int k = 0; k < monthNode.getChildCount(); k++){
						MyTreeNode dayNode =  monthNode.getChild(k);
						if(dayNode.getNodeValue().equals(day2)){
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
				MyTreeNode yearNode =  root.getChild(i);
				if(yearNode.getNodeValue().equals(year3)){
					for(int j = 0; j < yearNode.getChildCount(); j++){
					MyTreeNode monthNode =  yearNode.getChild(j);
					if(monthNode.getNodeValue().equals(month3)){
						for(int k = 0; k < monthNode.getChildCount(); k++){
							MyTreeNode dayNode =  monthNode.getChild(k);
							if(dayNode.getNodeValue().equals(day)){
								for(int l = 0; l < dayNode.getChildCount(); l++){
									MyTreeNode hourNode =  yearNode.getChild(l);
									if(hourNode.getNodeValue().equals(hour)){
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

public MyTreeNode getRoot(){
	
	return root;
}
	

}
