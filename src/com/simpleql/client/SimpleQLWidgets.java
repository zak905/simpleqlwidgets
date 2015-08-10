package com.simpleql.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.simpleql.datamodel.MyDate;
import com.simpleql.datamodel.TreeGridModel;
import com.simpleql.treegrid.TreeGrid;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleQLWidgets implements EntryPoint {
	
	Object[][] dates = new Object[][] {
	        new Object[]{new MyDate("2006","04","05","15")},
	        new Object[]{new MyDate("2006","03","18",null)},
	        new Object[]{new MyDate("2007","06","20","14")},
	        new Object[]{new MyDate("2007","06","20","17")},
	        new Object[]{new MyDate("2007","08","25","03")}
	    };
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		 TreeGridModel model = new TreeGridModel(dates);
		    final TreeGrid grid = new TreeGrid(model);
		    Button button = new Button("Get Selected Values");
	        
	        RootPanel.get("TreeGrid").add(grid);
	        RootPanel.get("TreeGrid").add(button);
	        
	        
	        button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					List<MyDate> selectedDates = grid.getSelectedDates();
					StringBuffer buffer = new StringBuffer();
					for(MyDate date: selectedDates){
						buffer.append(date.toString());
						buffer.append("<br>");
					}
					
				
					RootPanel.get("Data").getElement().setInnerHTML(buffer.toString());
				}
				
	        
	});
	

}
	
}
