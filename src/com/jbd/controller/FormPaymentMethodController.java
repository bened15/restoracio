package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.model.CtgPaymentMethod;

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

public class FormPaymentMethodController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgPaymentMethod paymentMethodRecord;
	private CtgPaymentMethod paymentMethodRecordSelected = new CtgPaymentMethod();
	private String userEntry = "Douglas";
	
	@Autowired
	private ICtgPaymentMethodManagement managePaymentMethod;
	//Declaracion Labels
	@FXML
	private Label lblPaymentMethodName;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField paymentMethodName;	
	@FXML
	private TextArea paymentMethodDescription;	

	//Declaracion Tablas
	@FXML
	private TableView<CtgPaymentMethod> paymentMethodList = new TableView<CtgPaymentMethod>();  ;
	@FXML
	private ObservableList <CtgPaymentMethod> paymentMethodData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn paymentMethodNameColumn = new TableColumn("paymentMethodNameColumn");
	@FXML
	private TableColumn paymentMethodDescriptionColumn = new TableColumn("paymentMethodDescriptionColumn");
	@FXML
	private TableColumn paymentMethodIdColumn = new TableColumn("paymentMethodIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int paymentMethodCodeSelected  = paymentMethodList.getSelectionModel().getSelectedItem().getPaymentMethodId();
		System.out.println("SELECTED ROW "+paymentMethodCodeSelected);
		//paymentMethodRecordSelected = managePaymentMethod.findCtgPaymentMethod(discountCodeSelected);
    	loadRecordInformation(paymentMethodCodeSelected);
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
					if (paymentMethodRecordSelected.getPaymentMethodId() ==0 ){
						newRecord = true;
					}
					paymentMethodRecordSelected.setName(paymentMethodName.getText());
					paymentMethodRecordSelected.setDescription(paymentMethodDescription.getText());
					paymentMethodRecordSelected.setEntryDate(new Date());
					paymentMethodRecordSelected.setEntryUser(userEntry);
					
					paymentMethodRecord = new CtgPaymentMethod();
					if (newRecord){
						System.out.println("NUEVO");
						paymentMethodRecord = managePaymentMethod.insertCtgPaymentMethod(paymentMethodRecordSelected);						

					}else{
						System.out.println("UPDATE");
						paymentMethodRecord =  managePaymentMethod.updateCtgPaymentMethod(paymentMethodRecordSelected);						
					}
					if (paymentMethodRecord==null){
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

		

		public FormPaymentMethodController() {
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
		paymentMethodRecordSelected = new CtgPaymentMethod();
		 paymentMethodName.setText("");
		 paymentMethodDescription.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (paymentMethodName.getText() == null || paymentMethodName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblPaymentMethodName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorString;			
	}
	
	public void refreshList(){
		
		
		paymentMethodData.clear();
		paymentMethodList.getColumns().clear();
		paymentMethodNameColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("name"));
		paymentMethodDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("description"));
		paymentMethodIdColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("paymentMethodId"));
		List<CtgPaymentMethod> list = managePaymentMethod.findAll();
		for(CtgPaymentMethod u : list){
			paymentMethodData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		paymentMethodList.setItems(paymentMethodData);
		
		paymentMethodList.getColumns().addAll(paymentMethodNameColumn,paymentMethodIdColumn,paymentMethodDescriptionColumn);		

	}
	
	public void refreshListOnSearch(){
		
		
		paymentMethodData.clear();
		paymentMethodList.getColumns().clear();
		paymentMethodNameColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("name"));
		paymentMethodDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("description"));
		paymentMethodIdColumn.setCellValueFactory(new PropertyValueFactory<CtgPaymentMethod, String>("paymentMethodId"));
		List<CtgPaymentMethod> list = managePaymentMethod.findCtgPaymentMethodByExample(paymentMethodName.getText());
		for(CtgPaymentMethod u : list){
			paymentMethodData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		paymentMethodList.setItems(paymentMethodData);
		
		paymentMethodList.getColumns().addAll(paymentMethodNameColumn,paymentMethodIdColumn,paymentMethodDescriptionColumn);		

	}
	

	
	public void loadRecordInformation(int paymentMethodCodePrm){
		paymentMethodRecordSelected = managePaymentMethod.findCtgPaymentMethod(paymentMethodCodePrm);
		 paymentMethodName.setText(paymentMethodRecordSelected.getName());
		 paymentMethodDescription.setText(paymentMethodRecordSelected.getDescription());
		 
			}

	public void defaultModeEnabled(){
		paymentMethodName.setEditable(true);
		paymentMethodDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		paymentMethodName.setEditable(true);
		paymentMethodDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		paymentMethodName.setEditable(true);
		paymentMethodDescription.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		paymentMethodName.setEditable(true);
		paymentMethodDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		paymentMethodName.setEditable(true);
		paymentMethodDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblPaymentMethodName.setTextFill(Color.web("#000000"));
			
		}

		
}
