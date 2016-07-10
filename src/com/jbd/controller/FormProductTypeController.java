package com.jbd.controller;


import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.model.CtgProductType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import application.Main;

public class FormProductTypeController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgProductType productTypeRecord;
	private CtgProductType productTypeRecordSelected = new CtgProductType();
	private String userEntry = "Douglas";
	
	@Autowired
	private ICtgProductTypeManagement manageProductType;
	//Declaracion Labels
	@FXML
	private Label lblProductTypeName;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField productTypeName;	
	@FXML
	private TextArea productTypeDescription;	

	//Declaracion Tablas
	@FXML
	private TableView<CtgProductType> productTypeList = new TableView<CtgProductType>();  ;
	@FXML
	private ObservableList <CtgProductType> productTypeData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn productTypeNameColumn = new TableColumn("productTypeNameColumn");
	@FXML
	private TableColumn productTypeDescriptionColumn = new TableColumn("productTypeDescriptionColumn");
	@FXML
	private TableColumn productTypeIdColumn = new TableColumn("productTypeIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
		refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int productTypeCodeSelected  = productTypeList.getSelectionModel().getSelectedItem().getProductTypeId();
		//productTypeRecordSelected = manageProductType.findCtgProductType(discountCodeSelected);
    	loadRecordInformation(productTypeCodeSelected);
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
					if (productTypeRecordSelected.getProductTypeId() ==0 ){
						newRecord = true;
					}
					productTypeRecordSelected.setTypeName(productTypeName.getText());
					productTypeRecordSelected.setTypeDescription(productTypeDescription.getText());
					productTypeRecordSelected.setEntryDate(new Date());
					productTypeRecordSelected.setEntryUser(userEntry);
					
					productTypeRecord = new CtgProductType();
					if (newRecord){
						System.out.println("NUEVO");
						productTypeRecord = manageProductType.insertCtgProductType(productTypeRecordSelected);						

					}else{
						System.out.println("UPDATE");
						productTypeRecord =  manageProductType.updateCtgProductType(productTypeRecordSelected);						
					}
					if (productTypeRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				}
			
		}

		

		public FormProductTypeController() {
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
        
        //loadComboBox
        //addListeners();
		refreshList();
		defaultModeEnabled();
    }

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	
	public void resetValues(){
		productTypeRecordSelected = new CtgProductType();
		 productTypeName.setText("");
		 productTypeDescription.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
		String errorMessage = null;	

		if (productTypeName.getText() == null || productTypeName.getText().isEmpty()){
			errorMessage = "El campo nombre es obligatorio.";
			lblProductTypeName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorMessage;			
	}
	
	public void refreshList(){
		
		
		productTypeData.clear();
		productTypeList.getColumns().clear();
		productTypeNameColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("typeName"));
		productTypeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("typeDescription"));
		productTypeIdColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("productTypeId"));
		List<CtgProductType> list = manageProductType.findAll();
		for(CtgProductType u : list){
			productTypeData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		productTypeList.setItems(productTypeData);
		
		productTypeList.getColumns().addAll(productTypeNameColumn,productTypeIdColumn,productTypeDescriptionColumn);		

	}
	
	public void refreshListOnSearch(){
		
		
		productTypeData.clear();
		productTypeList.getColumns().clear();
		productTypeNameColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("typeName"));
		productTypeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("typeDescription"));
		productTypeIdColumn.setCellValueFactory(new PropertyValueFactory<CtgProductType, String>("productTypeId"));
		List<CtgProductType> list = manageProductType.findProductTypeByExample(productTypeName.getText());
		for(CtgProductType u : list){
			productTypeData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		productTypeList.setItems(productTypeData);
		
		productTypeList.getColumns().addAll(productTypeNameColumn,productTypeIdColumn,productTypeDescriptionColumn);		

	}

	
	public void loadRecordInformation(int productTypeCodePrm){
		productTypeRecordSelected = manageProductType.findCtgProductType(productTypeCodePrm);
		 productTypeName.setText(productTypeRecordSelected.getTypeName());
		 productTypeDescription.setText(productTypeRecordSelected.getTypeDescription());
		 
			}

	public void defaultModeEnabled(){
		productTypeName.setEditable(true);
		productTypeDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		productTypeName.setEditable(true);
		productTypeDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		productTypeName.setEditable(true);
		productTypeDescription.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		productTypeName.setEditable(true);
		productTypeDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		productTypeName.setEditable(true);
		productTypeDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblProductTypeName.setTextFill(Color.web("#000000"));
			
		}

		
}
