package com.simpleql.client.treegrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.simpleql.shared.datamodel.MyDate;
import com.simpleql.shared.datamodel.TreeGridModel;
import com.simpleql.shared.datamodel.TreeRow;

public class TreeGrid implements IsWidget {
	
	
	private FlexTable grid;
	private TreeGridModel model;
	
	public TreeGrid(TreeGridModel model){
		grid = new FlexTable();
		this.model = model;
		
		int row = 0;
		for(String year: model.getTree().keySet()){
			List<TreeRow> months = model.getTree().get(year);
			MyDate date = new MyDate(year, "","", "");
			//grid.setWidget(row, 0, new TreeGridCell(date , months.size(), TreeLevelEnum.yearLevel, this, false));
			SetUpRow(row, date, months.size(), TreeLevelEnum.yearLevel, months.get(0).isSelectAll());
			row++;
			
			for(int i = 0; i < months.size(); i++){
				MyDate date1 = ((MyDate)months.get(i).getData());
				List<TreeRow> days = months.get(i).getSubRows();
				//grid.setWidget(row, 0, new TreeGridCell(date1, days.size(), TreeLevelEnum.monthLevel, this, false));
				SetUpRow(row, date1, days.size(), TreeLevelEnum.monthLevel, months.get(i).isSelected());
				row++;
				
				for(int j = 0; j < days.size(); j++){
					List<TreeRow> hours = days.get(j).getSubRows();
					MyDate date2 = ((MyDate)days.get(j).getData());
					//grid.setWidget(row, 0, new TreeGridCell(date2, hours.size(), TreeLevelEnum.dayLevel, this, false));
					SetUpRow(row, date2, hours.size(), TreeLevelEnum.dayLevel, days.get(j).isSelected());
					row++;
					
				
					for(int k = 0; k < hours.size(); k++){
						MyDate date3 = ((MyDate)hours.get(k).getData());
						//grid.setWidget(row, 0, new TreeGridCell(date3, 0, TreeLevelEnum.hourLevel, this, false));
						if(date3.getHour() != null){
						SetUpRow(row, date3, 0, TreeLevelEnum.hourLevel, hours.get(k).isSelected());
						row++;
						}
					}
				}
			}
			
		}
		
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return grid;
	}

	/**
	 * @return the model
	 */
	public TreeGridModel getModel() {
		return model;
	}
	
	public void Draw(){
		int row = 0;
		for(String year: model.getTree().keySet()){
			List<TreeRow> months = model.getTree().get(year);
			MyDate date = new MyDate(year, "","", "");
			//grid.setWidget(row, 0, new TreeGridCell(date , months.size(), TreeLevelEnum.yearLevel, this, months.get(0).isSelectAll()));
			SetUpRow(row, date, months.size(), TreeLevelEnum.yearLevel, months.get(0).isSelectAll());
			row++;
			
			for(int i = 0; i < months.size(); i++){
				MyDate date1 = ((MyDate)months.get(i).getData());
				List<TreeRow> days = months.get(i).getSubRows();
				//grid.setWidget(row, 0, new TreeGridCell(date1, days.size(), TreeLevelEnum.monthLevel, this, months.get(i).isSelected()));
				SetUpRow(row, date1, days.size(), TreeLevelEnum.monthLevel, months.get(i).isSelected());
				row++;
				
				for(int j = 0; j < days.size(); j++){
					List<TreeRow> hours = days.get(j).getSubRows();
					MyDate date2 = ((MyDate)days.get(j).getData());
					//grid.setWidget(row, 0, new TreeGridCell(date2, hours.size(), TreeLevelEnum.dayLevel, this, days.get(j).isSelected()));
					SetUpRow(row, date2, hours.size(), TreeLevelEnum.dayLevel, days.get(j).isSelected());
					row++;
					
				
					for(int k = 0; k < hours.size(); k++){
						MyDate date3 = ((MyDate)hours.get(k).getData());
						//grid.setWidget(row, 0, new TreeGridCell(date3, 0, TreeLevelEnum.hourLevel, this, hours.get(k).isSelected()));
						if(date3.getHour() != null){
						SetUpRow(row, date3, 0, TreeLevelEnum.hourLevel, hours.get(k).isSelected());
						row++;
						}
					}
				}
			}
			
		}
		
	}
	
	
	private void SetUpRow(int row, MyDate date, int count, TreeLevelEnum type, boolean isSelected){
	
	    Label countLabel;
	    
	   
	    final CheckBox checkbox = new CheckBox();
    	checkbox.setValue(isSelected);
    	final Image expandCollapseButton =  new Image("images/expanded.png");
    	expandCollapseButton.addStyleName("expanded");
    	
    	
    	if(type != TreeLevelEnum.hourLevel)
    	   countLabel = new Label("("+ count +")");
    	else
    	   countLabel = new Label("");
    	
    	countLabel.getElement().getStyle().setMarginLeft(20, Unit.PX);
    	
    	
    	    if(type == TreeLevelEnum.hourLevel || type == TreeLevelEnum.dayLevel && date.getHour() == null){
		   	     grid.setWidget(row, 1, checkbox);
		        	//grid.setWidget(row, 2, countLabel);
		   	}else{
		   		grid.setWidget(row, 0, expandCollapseButton);
		   		grid.setWidget(row, 1, checkbox);
		       	grid.setWidget(row, 2, countLabel);
		   		
		   	}
    	
    	
    	if(type == TreeLevelEnum.monthLevel){
    		//grid.getRowFormatter().addStyleName(row, "monthCell");
    		grid.getCellFormatter().getElement(row, 0).addClassName("monthCell");
    		grid.getCellFormatter().getElement(row, 1).addClassName("monthCell");
    		checkbox.getElement().setId("m-"+date.toString());
    		checkbox.setText(date.getMonth());
    	}else if(type == TreeLevelEnum.dayLevel){
    		//grid.getRowFormatter().addStyleName(row,"dayCell");
    		grid.getCellFormatter().getElement(row, 0).addClassName("dayCell");
    		grid.getCellFormatter().getElement(row, 1).addClassName("dayCell");
    		checkbox.getElement().setId("d-"+date.toString());
    		checkbox.setText(date.getDay());
    	}else if(type == TreeLevelEnum.hourLevel){
    		//grid.getRowFormatter().addStyleName(row, "hourCell");
    		grid.getCellFormatter().getElement(row, 0).addClassName("hourCell");
    		grid.getCellFormatter().getElement(row, 1).addClassName("hourCell");
    		checkbox.getElement().setId("h-"+date.toString());
    		checkbox.setText(date.getHour());
    	}else{
    		checkbox.getElement().setId("y-"+date.getYear());
    		checkbox.setText(date.getYear());
    	}
    	
    	
    	
    	
    	
    	
    	
    	checkbox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Boolean value = checkbox.getValue();
				int currentRow = grid.getCellForEvent(event).getRowIndex();
				CheckBox currentCheckBox = (CheckBox) grid.getWidget(currentRow, 1);
				String id = currentCheckBox.getElement().getId();
				currentCheckBox.setValue(value);
				
				TreeLevelEnum type;
				
				if(id.startsWith("y")){
					type = TreeLevelEnum.yearLevel;
				}else if(id.startsWith("m")){
					type = TreeLevelEnum.monthLevel;
				}else if(id.startsWith("d")){
					type = TreeLevelEnum.dayLevel;
				}else {
					type = TreeLevelEnum.hourLevel;
				}
				
				List<TreeLevelEnum> higherLevels = DefineHigherLevels(type);
				for(int i = currentRow + 1 ; i < grid.getRowCount(); i++){
						currentCheckBox = (CheckBox) grid.getWidget(i, 1);
                       id = currentCheckBox.getElement().getId();
						
						TreeLevelEnum currentType;
						
						if(id.startsWith("y")){
							currentType = TreeLevelEnum.yearLevel;
						}else if(id.startsWith("m")){
							currentType = TreeLevelEnum.monthLevel;
						}else if(id.startsWith("d")){
							currentType = TreeLevelEnum.dayLevel;
						}else {
							currentType = TreeLevelEnum.hourLevel;
						}
						
						if(higherLevels.contains(currentType))
							break;
						/*if(currentCheckBox.getElement().getId().startsWith("y") && i != currentRow)
							break;*/
						
						currentCheckBox.setValue(value);
				}
			}
			
    	});
    	
    	expandCollapseButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String style = expandCollapseButton.getStyleName();
				String[] classes = style.split(" ");
				
				String state = classes[1];
				
				if(state.equals("expanded")){
					expandCollapseButton.removeStyleName("expanded");
					expandCollapseButton.addStyleName("collapsed");
					expandCollapseButton.setUrl("images/collapsed.png");
					
					int currentRow = grid.getCellForEvent(event).getRowIndex();
					CheckBox currentCheckBox = (CheckBox) grid.getWidget(currentRow, 1);
					String id = currentCheckBox.getElement().getId();
					
					TreeLevelEnum type;
					
					if(id.startsWith("y")){
						type = TreeLevelEnum.yearLevel;
					}else if(id.startsWith("m")){
						type = TreeLevelEnum.monthLevel;
					}else if(id.startsWith("d")){
						type = TreeLevelEnum.dayLevel;
					}else {
						type = TreeLevelEnum.hourLevel;
					}
					
					List<TreeLevelEnum> higherLevels = DefineHigherLevels(type);
					
					for(int i = currentRow + 1; i < grid.getRowCount(); i++){
						currentCheckBox = (CheckBox) grid.getWidget(i, 1);
						
						id = currentCheckBox.getElement().getId();
						
						TreeLevelEnum currentType;
						
						if(id.startsWith("y")){
							currentType = TreeLevelEnum.yearLevel;
						}else if(id.startsWith("m")){
							currentType = TreeLevelEnum.monthLevel;
						}else if(id.startsWith("d")){
							currentType = TreeLevelEnum.dayLevel;
						}else {
							currentType = TreeLevelEnum.hourLevel;
						}
						
						if(higherLevels.contains(currentType))
							break;
						
						grid.getRowFormatter().getElement(i).getStyle().setDisplay(Display.NONE);
					}
					
					
						
				}else{
					
					expandCollapseButton.removeStyleName("collapsed");
					expandCollapseButton.addStyleName("expanded");
					expandCollapseButton.setUrl("images/expanded.png");
					
					int currentRow = grid.getCellForEvent(event).getRowIndex();
					CheckBox currentCheckBox = (CheckBox) grid.getWidget(currentRow, 1);
					String id = currentCheckBox.getElement().getId();
					
					TreeLevelEnum type;
					
					if(id.startsWith("y")){
						type = TreeLevelEnum.yearLevel;
					}else if(id.startsWith("m")){
						type = TreeLevelEnum.monthLevel;
					}else if(id.startsWith("d")){
						type = TreeLevelEnum.dayLevel;
					}else {
						type = TreeLevelEnum.hourLevel;
					}
					
					List<TreeLevelEnum> higherLevels = DefineHigherLevels(type);
					
					for(int i = currentRow + 1; i < grid.getRowCount(); i++){
						currentCheckBox = (CheckBox) grid.getWidget(i, 1);
						
						id = currentCheckBox.getElement().getId();
						
						TreeLevelEnum currentType;
						
						if(id.startsWith("y")){
							currentType = TreeLevelEnum.yearLevel;
						}else if(id.startsWith("m")){
							currentType = TreeLevelEnum.monthLevel;
						}else if(id.startsWith("d")){
							currentType = TreeLevelEnum.dayLevel;
						}else {
							currentType = TreeLevelEnum.hourLevel;
						}
						
						if(higherLevels.contains(currentType))
							break;
						
						grid.getRowFormatter().getElement(i).getStyle().setProperty("display", "table-row");
					}
				}
				
			}
    		
    		
    	});
	}


   private List<TreeLevelEnum> DefineHigherLevels(TreeLevelEnum type){
	   if(type == TreeLevelEnum.yearLevel){
		    return Arrays.asList(TreeLevelEnum.yearLevel);
	   }else if(type == TreeLevelEnum.monthLevel){
		   return Arrays.asList(TreeLevelEnum.yearLevel, TreeLevelEnum.monthLevel);
	   }else if(type == TreeLevelEnum.dayLevel){
		   return Arrays.asList(TreeLevelEnum.yearLevel,TreeLevelEnum.monthLevel, TreeLevelEnum.dayLevel);
	   }else {
		   return Arrays.asList(TreeLevelEnum.yearLevel,TreeLevelEnum.monthLevel, TreeLevelEnum.dayLevel, TreeLevelEnum.hourLevel);
	   }
   }
   
   
   public List<MyDate> getSelectedDates(){
	   List<MyDate> selectedDates = new ArrayList<MyDate>();
	   for(int i = 0; i < grid.getRowCount(); i++){
		   CheckBox currentCheckBox = (CheckBox) grid.getWidget(i, 1);
		   
		   if(currentCheckBox.getValue()){
			   String id = currentCheckBox.getElement().getId();
			   String[] split = id.split("-");
			   
			   String dateWithHour = split[1];
			   
			  String[] secondSplit = dateWithHour.split(" ");
			  
			  String date = secondSplit[0];
			  String hour = null;
			  
			  if(secondSplit.length > 1)
				  hour = secondSplit[1];
			  
			  if(!id.startsWith("y")){
			  
			  String[] thirdSplit = date.split("/");
			  String year = thirdSplit[0];
			  String month = thirdSplit[1];
			  String day = thirdSplit[2];
			   
			   MyDate selectedDate = new MyDate(year, month, day, hour);
			   if(!selectedDates.contains(selectedDate))
			     selectedDates.add(selectedDate);
			   
			  }
		   }
	   }	
	   return selectedDates;
   }
}
