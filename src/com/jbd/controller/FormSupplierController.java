package com.jbd.controller;


import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgSupplierManagement;


import com.jbd.model.CtgSupplier;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import application.Main;

public class FormSupplierController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgSupplier supplierRecord;
	private CtgSupplier supplierRecordSelected = new CtgSupplier();
	private String userEntry = "Douglas";
	
	@Autowired
	private ICtgSupplierManagement manageSupplier;
	//Declaracion Labels
	@FXML
	private Label lblSupplierName, lblSupplierContactName,lblSupplierContactLastname;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField supplierName,supplierAddress,supplierCity,supplierPhoneNumber,supplierEmail;
	@FXML
	private TextField supplierContactName,supplierContactLastname,supplierContactNumber,supplierContactEmail;
	
	//Declaracion Tablas
	@FXML
	private TableView<CtgSupplier> supplierList = new TableView<CtgSupplier>();  ;
	@FXML
	private ObservableList <CtgSupplier> supplierData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn supplierColumn = new TableColumn("supplierColumn");
	@FXML
	private TableColumn contactColumn = new TableColumn("contactColumn");
	@FXML
	private TableColumn supplierIdColumn = new TableColumn("supplierIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int supplierCodeSelected  = supplierList.getSelectionModel().getSelectedItem().getSupplierId();
		System.out.println("SELECTED ROW "+supplierCodeSelected);
		//supplierRecordSelected = manageSupplier.findCtgSupplier(supplierCodeSelected);
    	loadRecordInformation(supplierCodeSelected);
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
					if (supplierRecordSelected.getSupplierId() ==0 ){
						newRecord = true;
					}
					supplierRecordSelected.setAddress(supplierAddress.getText());;
					supplierRecordSelected.setCity(supplierCity.getText());
					supplierRecordSelected.setContactLastname(supplierContactLastname.getText());
					supplierRecordSelected.setContactName(supplierContactName.getText());
					supplierRecordSelected.setContactEmail(supplierContactEmail.getText());
					supplierRecordSelected.setContactPhoneNumber(supplierContactNumber.getText());
					supplierRecordSelected.setEntryDate(new Date());
					supplierRecordSelected.setEntryUser(userEntry);
					supplierRecordSelected.setSupplierName(supplierName.getText());
					
					supplierRecord = new CtgSupplier();
					if (newRecord){
						System.out.println("NUEVO");
						supplierRecord = manageSupplier.insertCtgSupplier(supplierRecordSelected);						

					}else{
						System.out.println("UPDATE");
						supplierRecord =  manageSupplier.updateCtgSupplier(supplierRecordSelected);						
					}
					if (supplierRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				}
			
		}

		

		public FormSupplierController() {
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
		supplierRecordSelected = new CtgSupplier();
		 supplierAddress.setText("");
		 supplierCity.setText("");
		 supplierContactEmail.setText("");
		 supplierContactLastname.setText("");
		 supplierContactName.setText("");
		 supplierContactNumber.setText("");
		 supplierEmail.setText("");
		 supplierName.setText("");
		 supplierPhoneNumber.setText("");
		 		 
	}
	
	public String validateRecord() {
		 defaultLabel();
		String errorMessage = null;	

		if (supplierName.getText() == null || supplierName.getText().isEmpty()){
			errorMessage = "El campo nombre de proveedor es obligatorio.";
			lblSupplierName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (supplierContactName.getText()== null ||supplierContactName.getText().isEmpty()){
			errorMessage =  "El campo nombre del contacto es obligatorio.";
			lblSupplierContactName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (supplierContactLastname.getText()==null || supplierContactLastname.getText().isEmpty()){
			errorMessage = "El campo apellido del contacto es obligatorio.";
			lblSupplierContactLastname.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorMessage;			
	}
	
	public void refreshList(){
		
		
		supplierData.clear();
		supplierList.getColumns().clear();
		supplierColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("supplierName"));
		contactColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("contactName"));
		supplierIdColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("supplierId"));
		List<CtgSupplier> list = manageSupplier.findAll();
		for(CtgSupplier u : list){
			CtgSupplier u1 = new CtgSupplier();
			u1.setSupplierId(u.getSupplierId());
			u1.setContactName(u.getContactName() + " "+u.getContactLastname());
			u1.setSupplierName(u.getSupplierName());
			supplierData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		supplierList.setItems(supplierData);
		
		supplierList.getColumns().addAll(supplierColumn,contactColumn,supplierIdColumn);
		

	}

	public void refreshListOnSearch(){
		
		
		supplierData.clear();
		supplierList.getColumns().clear();
		supplierColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("supplierName"));
		contactColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("contactName"));
		supplierIdColumn.setCellValueFactory(new PropertyValueFactory<CtgSupplier, String>("supplierId"));
		List<CtgSupplier> list = manageSupplier.findBySupplierExample(supplierName.getText(), supplierContactName.getText(), supplierContactLastname.getText());
		for(CtgSupplier u : list){
			CtgSupplier u1 = new CtgSupplier();
			u1.setSupplierId(u.getSupplierId());
			u1.setContactName(u.getContactName() + " "+u.getContactLastname());
			u1.setSupplierName(u.getSupplierName());
			supplierData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		supplierList.setItems(supplierData);
		
		supplierList.getColumns().addAll(supplierColumn,contactColumn,supplierIdColumn);
		

	}


	
	public void loadRecordInformation(int supplierCodePrm){
		supplierRecordSelected = manageSupplier.findCtgSupplier(supplierCodePrm);
		 supplierName.setText(supplierRecordSelected.getSupplierName());
		 supplierAddress.setText(supplierRecordSelected.getAddress());
		 supplierCity.setText(supplierRecordSelected.getCity());
		 supplierEmail.setText(supplierRecordSelected.getSupplierEmail());
		 supplierPhoneNumber.setText(supplierRecordSelected.getSupplierPhoneNumber());
		 supplierContactName.setText(supplierRecordSelected.getContactName());
		 supplierContactLastname.setText(supplierRecordSelected.getContactLastname());
		 supplierContactNumber.setText(supplierRecordSelected.getContactPhoneNumber());
		 supplierContactEmail.setText(supplierRecordSelected.getContactEmail());
		 
			}

	public void defaultModeEnabled(){
		supplierName.setEditable(true);
		supplierAddress.setEditable(false);
		supplierCity.setEditable(false);
		supplierEmail.setEditable(false);
		supplierPhoneNumber.setEditable(false);
		supplierContactName.setEditable(true);
		supplierContactLastname.setEditable(true);
		supplierContactNumber.setEditable(false);
		supplierContactEmail.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		supplierName.setEditable(true);
		supplierAddress.setEditable(false);
		supplierCity.setEditable(false);
		supplierEmail.setEditable(false);
		supplierPhoneNumber.setEditable(false);
		supplierContactName.setEditable(true);
		supplierContactLastname.setEditable(true);
		supplierContactNumber.setEditable(false);
		supplierContactEmail.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		supplierName.setEditable(true);
		supplierAddress.setEditable(false);
		supplierCity.setEditable(false);
		supplierEmail.setEditable(false);
		supplierPhoneNumber.setEditable(false);
		supplierContactName.setEditable(true);
		supplierContactLastname.setEditable(true);
		supplierContactNumber.setEditable(false);
		supplierContactEmail.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		supplierName.setEditable(true);
		supplierAddress.setEditable(true);
		supplierCity.setEditable(true);
		supplierEmail.setEditable(true);
		supplierPhoneNumber.setEditable(true);
		supplierContactName.setEditable(true);
		supplierContactLastname.setEditable(true);
		supplierContactNumber.setEditable(true);
		supplierContactEmail.setEditable(false);
		
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		supplierName.setEditable(true);
		supplierAddress.setEditable(true);
		supplierCity.setEditable(true);
		supplierEmail.setEditable(true);
		supplierPhoneNumber.setEditable(true);
		supplierContactName.setEditable(true);
		supplierContactLastname.setEditable(true);
		supplierContactNumber.setEditable(true);
		supplierContactEmail.setEditable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblSupplierName.setTextFill(Color.web("#000000"));
			lblSupplierContactName.setTextFill(Color.web("#000000"));
			lblSupplierContactLastname.setTextFill(Color.web("#000000"));
			
		}

		
}
