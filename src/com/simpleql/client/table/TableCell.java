package com.simpleql.client.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HeaderBuilder;
import com.simpleql.shared.datamodel.Period;

public class TableCell extends AbstractCell<CellTable<Period>> implements Cell<CellTable<Period>>{
	
	
	public TableCell(){
		super();
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			CellTable<Period> value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		
		
		
        if (value != null) {
            sb.appendHtmlConstant(value.getElement().getInnerHTML());
        }
		
	}

}
