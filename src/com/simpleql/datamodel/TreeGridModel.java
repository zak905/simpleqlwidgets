package com.simpleql.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreeGridModel {
	
	private Map<String, List<TreeRow>> tree;
	

	
	public TreeGridModel(Object[][] data){
		tree = new HashMap<String, List<TreeRow>>();
		
		for(int i = 0; i < data.length; i++){
			MyDate date = (MyDate)data[i][0];
			TreeRow yearRow = new TreeRow(data[i][0]);
			TreeRow monthSubRow = new TreeRow(data[i][0]);
			TreeRow daySubRow = new TreeRow(data[i][0]);
			    if(date.getHour() != null){
			    	TreeRow hourSubRow = new TreeRow(data[i][0]);
			    	daySubRow.addSubRow(hourSubRow);
			    }
			    monthSubRow.addSubRow(daySubRow);
			    yearRow.addSubRow(monthSubRow);
			    
			    if(getTree().get(date.getYear()) != null){
			    	List<TreeRow> subTree  = tree.get(date.getYear());
			    	   Insert(yearRow, subTree);
			    }else{
			    	List<TreeRow> rows = new ArrayList<TreeRow>();
			    	rows.add(yearRow);
			    	tree.put(date.getYear(), rows);
			    }
			
		}
	}

	/**
	 * @return the tree
	 */
	public Map<String, List<TreeRow>> getTree() {
		return tree;
	}
	
	private void Insert(TreeRow row, List<TreeRow> subTree){
		MyDate date = (MyDate) row.getData();
		for(int i = 0; i < subTree.size(); i++){
			MyDate subTreeDate = (MyDate) subTree.get(i).getData();
			      if(!subTreeDate.getMonth().equals(date.getMonth())){
			    	  subTree.add(row);
			    	  break;
			      } else if(subTreeDate.getMonth().equals(date.getMonth()) && !subTreeDate.getDay().equals(date.getDay()) ){
			    	  List<TreeRow> secondLevelSubTree = subTree.get(i).getSubRows();
			    	  for(int j = 0; j < secondLevelSubTree.size(); j++){
			    		  subTreeDate = (MyDate) secondLevelSubTree.get(j).getData();
			    		  if(!subTreeDate.getDay().equals(date.getDay())){
			    			  secondLevelSubTree.add(row);
			    			  break;
			    		  }
			    		  
			    	  }
			      }else if(subTreeDate.getMonth().equals(date.getMonth()) && subTreeDate.getDay().equals(date.getDay()) && !subTreeDate.getHour().equals(date.getHour())){
			    	  List<TreeRow> secondLevelSubTree = subTree.get(i).getSubRows();
			    	  for(int j = 0; j < secondLevelSubTree.size(); j++){
			    		  subTreeDate = (MyDate) secondLevelSubTree.get(j).getData();
			    		  if(subTreeDate.getDay().equals(date.getDay())){
			    			  List<TreeRow> thirdLevelSubTree = secondLevelSubTree.get(j).getSubRows();
			    			  for(int k = 0; k < thirdLevelSubTree.size(); k++){
			    				  subTreeDate = (MyDate) thirdLevelSubTree.get(k).getData();
			    				  if(!subTreeDate.getHour().equals(date.getHour())){
			    					  thirdLevelSubTree.add(row);
					    			  break;
					    		  }
			    			  }
			    			 
			    		  }
			    		  
			    	  }
			      }
			
		}
		
	}


}
