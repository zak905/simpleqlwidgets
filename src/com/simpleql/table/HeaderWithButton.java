package com.simpleql.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.simpleql.datamodel.Period;

public class HeaderWithButton extends AbstractCell<CellTable<Period>> implements SafeHtml {
	
	
	  interface Template extends SafeHtmlTemplates {
		  
		  @Template("<div style=\"\">{0}</div>")
	        SafeHtml header(String columnName);

	        @Template("<div style=\"\"><button> {0}</button></div>")
	        SafeHtml input(String value);
	    }
	
    private static Template template;
	Button button;
	String title;
	Label titleLabel;
	
	
	public HeaderWithButton(String title){
		super("click");
		this.title = title;
		button = new Button("Options");
		titleLabel = new Label(title);
		if(template == null)
			 template = GWT.create(Template.class);
		
		
	} 

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			CellTable<Period> value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		
		
		sb.append(template.header(title));
		sb.append(template.input("Options"));
	}
	
	
	 @Override
	    public void onBrowserEvent(Context context,Element parent, CellTable<Period> value, NativeEvent event, ValueUpdater<CellTable<Period>> valueUpdater) {
		 
		 super.onBrowserEvent(context, parent, value, event, valueUpdater);
		 System.out.println("test");
		 if("click".equals(event.getType())){
			 
			 System.out.println("test");
		 }
	     
		 //Implement click events Here
		 
	     }

	@Override
	public String asString() {
		// TODO Auto-generated method stub
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		sb.append(template.header(title));
		sb.append(template.input("Options"));
		return sb.toSafeHtml().asString();
	}



}
