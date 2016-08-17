package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IAdmCustomerManagement;


import com.jbd.model.AdmCustomer;


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

public class FormCustomerController {

	private Main mainApp;
	private ApplicationContext context;
	private AdmCustomer customerRecord;
	private AdmCustomer customerRecordSelected = new AdmCustomer();
	private String userEntry = "Douglas";
	
	@Autowired
	private IAdmCustomerManagement manageCustomer;
	//Declaracion Labels
	@FXML
	private Label lblCustomerName,lblCustomerLastname;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField customerName,customerLastname,customerNumber,customerEmail;
	@FXML
	private TextArea customerAddress;
	
	//Declaracion Tablas
	@FXML
	private TableView<AdmCustomer> customerList = new TableView<AdmCustomer>();  ;
	@FXML
	private ObservableList <AdmCustomer> customerData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn customerNameColumn = new TableColumn("customerNameColumn");
	@FXML
	private TableColumn customerLastNameColumn = new TableColumn("customerLastNameColumn");

	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int customerCodeSelected  = customerList.getSelectionModel().getSelectedItem().getCustomerId();
		System.out.println("SELECTED ROW "+customerCodeSelected);
		//customerRecordSelected = manageCustomer.findAdmCustomer(customerCodeSelected);
    	loadRecordInformation(customerCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {				
			resetRecord();
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
					if (customerRecordSelected.getCustomerId() ==0 ){
						newRecord = true;
					}
					customerRecordSelected.setCustomerAddress1(customerAddress.getText());;
					customerRecordSelected.setCustomerLastname(customerLastname.getText());
					customerRecordSelected.setCustomerEmail(customerEmail.getText());
					customerRecordSelected.setCustomerPhone1(customerNumber.getText());
					customerRecordSelected.setEntryDate(new Date());
					customerRecordSelected.setEntryUser(userEntry);
					customerRecordSelected.setCustomerName(customerName.getText());
					
					customerRecord = new AdmCustomer();
					if (newRecord){
						System.out.println("NUEVO");
						customerRecord = manageCustomer.insertAdmCustomer(customerRecordSelected);						

					}else{
						System.out.println("UPDATE");
						customerRecord =  manageCustomer.updateAdmCustomer(customerRecordSelected);						
					}
					if (customerRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						if (newRecord) {	
							JOptionPane.showMessageDialog(null,
								"Registro almacenado exitosamente");
						}else{
							JOptionPane.showMessageDialog(null,
									"Registro actualizado exitosamente");
								
						}
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"Los campos marcados en rojo son obligatorios y presentan errores.\n "
							+ "A continuacion se muestra el detalle de errores:\n" + error);

				}
			
		}

		

		public FormCustomerController() {
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
		customerRecordSelected = new AdmCustomer();
		 customerAddress.setText("");
		 customerLastname.setText("");
		 customerEmail.setText("");
		 customerName.setText("");
		 customerNumber.setText("");
		 		 
	}

	public void resetRecord(){
		customerRecordSelected = new AdmCustomer();		 		 
	}

	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;
		if (customerName.getText() == null || customerName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre de proveedor es obligatorio.\n");
			messageErrorNumber++;
			lblCustomerName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (customerLastname.getText()==null || customerLastname.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo apellido del contacto es obligatorio.\n");
			messageErrorNumber++;
			lblCustomerLastname.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;			
	}
	
	public void refreshList(){
		
		
		customerData.clear();
		customerList.getColumns().clear();
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<AdmCustomer, String>("customerName"));
		customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<AdmCustomer, String>("customerLastname"));
		List<AdmCustomer> list = manageCustomer.findAll();
		for(AdmCustomer u : list){
			customerData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		customerList.setItems(customerData);
		
		customerList.getColumns().addAll(customerNameColumn,customerLastNameColumn);
		

	}

	public void refreshListOnSearch(){
		
		
		customerData.clear();
		customerList.getColumns().clear();
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<AdmCustomer, String>("customerName"));
		customerLastNameColumn.setCellValueFactory(new PropertyValueFactory<AdmCustomer, String>("customerLastname"));
		List<AdmCustomer> list = manageCustomer.findByCustomerExample(customerName.getText(), customerLastname.getText());
		for(AdmCustomer u : list){
			customerData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		customerList.setItems(customerData);
		
		customerList.getColumns().addAll(customerNameColumn,customerLastNameColumn);
		

	}


	
	public void loadRecordInformation(int customerCodePrm){
		customerRecordSelected = manageCustomer.findAdmCustomer(customerCodePrm);
		 customerName.setText(customerRecordSelected.getCustomerName());
		 customerAddress.setText(customerRecordSelected.getCustomerAddress1());
		 customerEmail.setText(customerRecordSelected.getCustomerEmail());
		 customerNumber.setText(customerRecordSelected.getCustomerPhone1());
		 customerLastname.setText(customerRecordSelected.getCustomerLastname());
		 
			}

	public void defaultModeEnabled(){
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		customerAddress.setEditable(false);
		customerEmail.setEditable(false);
		customerNumber.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		customerAddress.setEditable(false);
		customerEmail.setEditable(false);
		customerNumber.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		customerAddress.setEditable(true);
		customerEmail.setEditable(true);
		customerNumber.setEditable(true);
	
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		customerAddress.setEditable(true);
		customerEmail.setEditable(true);
		customerNumber.setEditable(true);
	
		
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		customerAddress.setEditable(true);
		customerEmail.setEditable(true);
		customerNumber.setEditable(true);
	
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblCustomerName.setTextFill(Color.web("#000000"));
			lblCustomerLastname.setTextFill(Color.web("#000000"));
			
		}

		
}
