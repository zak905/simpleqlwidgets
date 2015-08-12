package com.simpleql.table;



import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.web.bindery.event.shared.Event;
import com.simpleql.datamodel.Period;

public class HeaderWithButtonBuilder extends AbstractCellTableBuilder<Period> {

	public HeaderWithButtonBuilder(AbstractCellTable<Period> cellTable) {
		super(cellTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildRowImpl(Period rowValue, int absRowIndex) {
		
		
		// TODO Auto-generated method stub
		start(true);
		
		TableRowBuilder row = startRow();
		TableCellBuilder td = row.startTD();
		DivBuilder div = td.startDiv();
		
		
		
		 div.html(SafeHtmlUtils.fromTrustedString(rowValue.getRemarks()));
		 
		 div.end();
		 td.endTD();
		 
		 td = row.startTD();
			div = td.startDiv();
			
			Button button = new Button("Test");
			
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					Window.alert("test");
				}
				
				
			});
			
			System.out.println(button.getElement().toString());
			
			 div.html(SafeHtmlUtils.fromTrustedString(button.getElement().toString()));
			 
			 div.end();
			 td.endTD();
		 row.endTR();
	}

}
