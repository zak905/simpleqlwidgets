package com.simpleql.client.table;

import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.simpleql.shared.datamodel.Period;

public class MyCustomHeaderBuilder extends AbstractHeaderOrFooterBuilder<Period> {

    public MyCustomHeaderBuilder(AbstractCellTable<Period> table,
			boolean isFooter) {
		super(table, isFooter);
		// TODO Auto-generated constructor stub
	}


    @Override
    public boolean buildHeaderOrFooterImpl() {

    	
		TableRowBuilder row = startRow();
		TableCellBuilder th = row.startTH().colSpan(2);
		DivBuilder div = th.startDiv();
		
		Button button = new Button("Option");
		
		 
		button.getElement().setId("headerButton");
		 div.html(SafeHtmlUtils.fromTrustedString("Period "+ button.getElement()));
		 
		 div.end();
		 th.endTH();
		 row.endTR();
		 
		 row.startTR();
		 th = row.startTH();
			div = th.startDiv();
			 
			 div.html(SafeHtmlUtils.fromTrustedString("Comment"));
			 
			 div.end();
			 th.endTH();
		 
		 th = row.startTH();
			div = th.startDiv();
			
			 div.html(SafeHtmlUtils.fromTrustedString("Date"));
			 
			 div.end();
			 th.endTH();
		 row.endTR();
		 
		 
		 
		 GQuery.$("thead").bind("click", new Function() {
			  public boolean f(Event e) {
				 if(GQuery.$(e.getEventTarget()).id().equals("headerButton")){
				  Window.alert("header button clicked");
				 }
				    return true;
				  }
				});
		 
		
		 
		
      return true;
    }

   
   
  }