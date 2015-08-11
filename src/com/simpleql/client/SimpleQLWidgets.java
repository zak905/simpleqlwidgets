package com.simpleql.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.simpleql.datamodel.MyDate;
import com.simpleql.datamodel.Period;
import com.simpleql.datamodel.TreeGridModel;
import com.simpleql.table.HeaderWithButton;
import com.simpleql.table.ParentColumn;
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
		
		    //TreeGridModel model = new TreeGridModel(dates);
		    //final TreeGrid grid = new TreeGrid(model);
		    
		    final CellTable<CellTable<Period>> parentTable = new CellTable<CellTable<Period>>();
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
		    
		    ParentColumn<CellTable<Period>> parent = new ParentColumn<CellTable<Period>>(){
				@Override
				public CellTable<Period> getValue(CellTable<Period> object) {
					return object;
				}
		    };
		   
		    
		    childTable.addColumn(commentsColumn, "Comments");
		    childTable.addColumn(datesColumn, "Dates");
		    
		    childTable.setRowData(periods);
		    
		    parentTable.addColumn(parent, new HeaderWithButton("Period"));
		    
		    parentTable.setRowData(Arrays.asList(childTable));
		    
		    
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
	

	        RootPanel.get("CellTable").add(parentTable);
}
	
}
