package com.simpleql.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.RootPanel;
import com.simpleql.client.table.HeaderWithButton;
import com.simpleql.client.table.MyCustomHeaderBuilder;
import com.simpleql.client.table.ParentColumn;
import com.simpleql.client.treegrid.MyTree;
import com.simpleql.client.treegrid.TreeGrid;
import com.simpleql.shared.datamodel.MyDate;
import com.simpleql.shared.datamodel.Period;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SimpleQLWidgets implements EntryPoint {
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		   // MyTreeViewModel model = new MyTreeViewModel(dates);
		   final MyTree tree = new MyTree("");
		   tree.InitializeEvents();
		   

		    final CellTable<Period> childTable = new CellTable<Period>();
		    
		    List<Period> periods = new ArrayList<Period>();
		    periods.add(new Period(new MyDate("2006","04","05","15"), "Hiring 3 developpers"));
		    periods.add(new Period(new MyDate("2006","04","05","15"), "Project Kick off"));
		    periods.add(new Period(new MyDate("2006","04","05","15"), "1st milestone due"));
		    periods.add(new Period(new MyDate("2006","04","05","15"), "Three test"));
		    periods.add(new Period(new MyDate("2006","04","05","15"), "another test"));
		    
		    TextColumn<Period> commentsColumn = new TextColumn<Period>(){
				@Override
				public String getValue(Period object) {
					return object.getRemarks();
				}
		    };
		    
		    TextColumn<Period> datesColumn = new TextColumn<Period>(){
				@Override
				public String getValue(Period object) {
					return object.getDate().toString();
				}
		    };
		    

		   
		    
		    childTable.addColumn(commentsColumn, "Comments");
		    childTable.addColumn(datesColumn, "Dates");
		    
		    childTable.setRowData(periods);
		    childTable.setHeaderBuilder(new MyCustomHeaderBuilder(childTable, false));
		    
	
		    
		    
		    /// Unused code for now
		  /*  Button button = new Button("Get Selected Values");
	        
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
				
	        
	});*/
	
		    RootPanel.get("TreeGrid").add(tree.asWidget());
	        //RootPanel.get("CellTable").add(childTable);
}
	
}
