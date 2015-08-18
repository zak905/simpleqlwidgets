package com.simpleql.server.impl;

import java.util.ArrayList;
import java.util.List;




import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.simpleql.client.treegrid.MyTree;
import com.simpleql.shared.datamodel.DateElementCounter;
import com.simpleql.shared.datamodel.DateResolution;
import com.simpleql.shared.datamodel.MyTreeDataModel;
import com.simpleql.shared.datamodel.MyTreeNode;
import com.simpleql.server.ServerStub;

public class ServerStubImpl extends RemoteServiceServlet implements ServerStub {
	
	Object[][] dates = new Object[][] {
	        new Object[]{new DateElementCounter("2010", 10), DateResolution.Year},
	        new Object[]{new DateElementCounter("2011", 25), DateResolution.Year},
	        new Object[]{new DateElementCounter("2010-05", 22),DateResolution.Month },
	        new Object[]{new DateElementCounter("2011-11", 14), DateResolution.Month},
	        new Object[]{new DateElementCounter("2010-05-10", 8), DateResolution.Day},
	        new Object[]{new DateElementCounter("2010-05-10 12", 8), DateResolution.Hour},
	        new Object[]{new DateElementCounter("2010-05-10 17", 8), DateResolution.Hour},
	        new Object[]{new DateElementCounter("2011-11-13", 5), DateResolution.Day}
	    };
	
	  final MyTreeDataModel model = new MyTreeDataModel(dates);


	@Override
	public DateElementCounter[] getNextLevelValues(String token,
			DateResolution type, String value) {

		
		List<DateElementCounter> list = new ArrayList<DateElementCounter>();
		
	if(type == DateResolution.Year){
		//if data is already sent, no need to resent it again, it will be duplicated
		 if(!model.getRoot().isVisited()){
			for(int i = 0; i < model.getRoot().getChildCount(); i++){
				MyTreeNode child = model.getRoot().getChild(i);
 
				
				DateElementCounter element = new DateElementCounter(child.getNodeData().getValue(), child.getNodeData().getCount());
				list.add(element);
			} 
		 }
			model.getRoot().setVisited(true);
		}else{
			
			//return to parent level to find the parent node and return its children
			 DateResolution resolution;
			    if(type == DateResolution.Month){
			    	resolution = DateResolution.Year;
			    }else if (type == DateResolution.Day){
			    	resolution = DateResolution.Month;
			    }else {
			    	resolution = DateResolution.Day;
			    }
			    
			MyTreeNode subTreeRoot = model.findByValue(value, resolution);
			
			
			//if found & if data is already sent, no need to resent it again, it will be duplicated
			if(subTreeRoot != null && !subTreeRoot.isVisited() ){
				subTreeRoot.setVisited(true);
				for(int i = 0; i < subTreeRoot.getChildCount(); i++){
					MyTreeNode child = subTreeRoot.getChild(i);

					
					DateElementCounter element = new DateElementCounter(child.getNodeData().getValue(), child.getNodeData().getCount());
					list.add(element);
				}
				
			}
			
		}
	
		
		return list.toArray(new DateElementCounter[list.size()]);
	}



}
