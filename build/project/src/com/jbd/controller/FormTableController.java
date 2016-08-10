package com.jbd.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.RestArea;
import com.jbd.model.RestTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import application.Main;

public class FormTableController {

	private Main mainApp;
	private ApplicationContext context;
	private RestTable restTableRecord;
	private RestTable restTableRecordSelected = new RestTable();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	
	@Autowired
	private IRestTableManagement manageRestTable;
	@Autowired
	private IRestAreaManagement manageRestArea;
	//Declaracion Labels
	@FXML
	private Label lblRestTable, lblRestTableArea, lblRestTableSeats;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private ComboBox tableArea;	
	@FXML
	private ObservableList <RestArea> tableAreaData =  FXCollections.observableArrayList();
	@FXML
	private TextField tableName;	
	@FXML
	private TextField tableSeatsAvailable;	

	//Declaracion Tablas
	@FXML
	private TableView<RestTable> tableList = new TableView<RestTable>();  ;
	@FXML
	private ObservableList <RestTable> restTableData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn tableAreaNameColumn = new TableColumn("tableAreaNameColumn");
	@FXML
	private TableColumn tableNameColumn = new TableColumn("tableNameColumn");
	@FXML
	private TableColumn tableIdColumn = new TableColumn("tableIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
//			refreshList();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int restTableCodeSelected  = tableList.getSelectionModel().getSelectedItem().getTableId();
		//restTableRecordSelected = manageRestTable.findRestTable(discountCodeSelected);
    	loadRecordInformation(restTableCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {				
			resetValues();
			newModeEnabled();
	}
	@FXML
	public void onClear(MouseEvent event) {				
			initModeEnabled();
			resetValues();
	}
		@FXML
		public void onSave(MouseEvent event) {

				String error = validateRecord();
				System.out.println(error);
				if (error == null){
					boolean newRecord = false;
					if (restTableRecordSelected.getTableId() == 0 ){
						newRecord = true;
					}
								
					restTableRecordSelected.setTableName(tableName.getText());
					restTableRecordSelected.setChairAmmount(gf.asInteger(tableSeatsAvailable.getText()));
					restTableRecordSelected.setRestArea(manageRestArea.findRestArea(Integer.parseInt(gf.getId(tableArea.getValue().toString()))));
					
					restTableRecord = new RestTable();
					if (newRecord){
						System.out.println("NUEVO");
						restTableRecord = manageRestTable.insertRestTable(restTableRecordSelected);						

					}else{
						System.out.println("UPDATE");
						restTableRecord =  manageRestTable.updateRestTable(restTableRecordSelected);						
					}
					if (restTableRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				} else{
				}
			
		}

		

		public FormTableController() {
		try {
			context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
		
		
		} catch (Exception e){
			e.printStackTrace();
		}

	}
		

	@FXML
	public void exitApp() {
		System.exit(0);

	}

	
	@FXML
    public void initialize() {
        System.out.println("second");
        alterComboBoxProperties();
        //loadComboBox
        //addListeners();
 		refreshList();
        refreshComboBoxList();
		defaultModeEnabled();
    }

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	
	public void resetValues(){
		restTableRecordSelected = new RestTable();
		tableArea.getSelectionModel().select(null);
		 tableName.setText("");
		 tableSeatsAvailable.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
		String errorMessage = null;	

		if (tableArea.getValue() == null ){
			errorMessage = "El campo area es obligatorio";
			lblRestTableArea.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (tableName.getText() == null || tableName.getText().isEmpty()){
			errorMessage = "El campo nombre es obligatorio.";
			lblRestTable.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}

		if (tableSeatsAvailable.getText() == null || tableSeatsAvailable.getText().isEmpty()){
			errorMessage = "El numero de sillas disponible es obligatorio.";
			if (!gf.validNumber(tableSeatsAvailable.getText()) || gf.asInteger(tableSeatsAvailable.getText())==0){
				errorMessage = "El numero de sillas debe ser un numero valido";
				
			}
			lblRestTableSeats.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}

		return errorMessage;			
	}
	
	public void refreshList(){
		
		
		restTableData.clear();
		tableList.getColumns().clear();
		tableNameColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableName"));
		tableAreaNameColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableAreaName"));
		tableIdColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableId"));
		
		List<RestTable> list = manageRestTable.findAll();
		for(RestTable u : list){
			u.setTableAreaName(u.getRestArea().getAreaName());
			restTableData.add(u);
		}
		
		tableList.setItems(restTableData);
		
		tableList.getColumns().addAll(tableAreaNameColumn,tableNameColumn,tableIdColumn);		

	}
	
public void refreshListOnSearch(){
		
		
		restTableData.clear();
		tableList.getColumns().clear();
		tableNameColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableName"));
		tableAreaNameColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableAreaName"));
		tableIdColumn.setCellValueFactory(new PropertyValueFactory<RestTable, String>("tableId"));
		
		 int areaId = 0;
		 System.out.println("SE OBTIENE OBJETO on search");
		 if (tableArea.getValue()!=null){
			 areaId = Integer.parseInt(gf.getId(tableArea.getValue().toString())); 
		 }
		 System.out.println("FIN");

		
		List<RestTable> list = manageRestTable.findTablesByExample( areaId);
		for(RestTable u : list){
			u.setTableAreaName(u.getRestArea().getAreaName());
			restTableData.add(u);
		}
		
		tableList.setItems(restTableData);
		
		tableList.getColumns().addAll(tableAreaNameColumn,tableNameColumn,tableIdColumn);		

	}
	
	
	public void loadRecordInformation(int restTableCodePrm){
		restTableRecordSelected = manageRestTable.findRestTable(restTableCodePrm);
		 tableName.setText(restTableRecordSelected.getTableName());
		 tableSeatsAvailable.setText(restTableRecordSelected.getChairAmmount()+"");
		 //productRecordSelected
		 if (restTableRecordSelected.getRestArea() != null){	
			 tableArea.getSelectionModel().select(restTableRecordSelected.getRestArea());
		 }else{
			 tableArea.getSelectionModel().select(null);
			 
		 }

			}

	public void defaultModeEnabled(){
		tableArea.setEditable(true);
		tableName.setEditable(false);
		tableSeatsAvailable.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		tableArea.setEditable(true);
		tableName.setEditable(false);
		tableSeatsAvailable.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		tableArea.setEditable(true);
		tableName.setEditable(true);
		tableSeatsAvailable.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		tableArea.setEditable(true);
		tableName.setEditable(true);
		tableSeatsAvailable.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		tableArea.setEditable(true);
		tableName.setEditable(true);
		tableSeatsAvailable.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblRestTable.setTextFill(Color.web("#000000"));
			lblRestTableArea.setTextFill(Color.web("#000000"));
			lblRestTableSeats.setTextFill(Color.web("#000000"));
		}

		public void refreshComboBoxList(){
			
			
			tableAreaData.clear();
			List<RestArea> list = manageRestArea.findAll();
			for(RestArea r : list){
				tableAreaData.add(r);
			}
			
			tableArea.setItems(tableAreaData);
		
		}
		
		public void alterComboBoxProperties(){
			tableArea.setCellFactory(new Callback<ListView<RestArea>,ListCell<RestArea>>(){
			     @Override
	            public ListCell<RestArea> call(ListView<RestArea> p) {
	                 
	                final ListCell<RestArea> cell = new ListCell<RestArea>(){
	 
	                    @Override
	                    protected void updateItem(RestArea t, boolean bln) {
	                        super.updateItem(t, bln);
	                         
	                        if(t != null){
	                            setText(t.getAreaId() +" - "+t.getAreaName());
	                        }else{
	                            setText(null);
	                        }
	                    }
	  
	                };
	                 
	                return cell;
	            }
	        });
		}
}
