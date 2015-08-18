package com.simpleql.client.table;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.simpleql.shared.datamodel.Period;

public abstract class ParentColumn<T> extends Column<T, CellTable<Period>> {

   
    public ParentColumn() {
		super(new TableCell());
		
		// TODO Auto-generated constructor stub
	}


     
    @Override    
    public CellTable<Period> getValue(T object) {    
        return null;         
    }  
    
}
