package com.simpleql.table;

import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.dom.builder.shared.TableSectionBuilder;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.HeaderBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.simpleql.datamodel.Period;

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
		
		Button button = new Button("Options");
		
		button.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// Event not working, need to use DOM events
				Window.alert("test");
			}
			
			
		});
		
		 div.html(SafeHtmlUtils.fromTrustedString("Period  " + button.getElement().toString()));
		 
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

      return true;
    }

   
   
  }