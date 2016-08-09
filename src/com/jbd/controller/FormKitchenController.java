package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestKitchenManagement;
import com.jbd.model.RestKitchen;

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

public class FormKitchenController {

	private Main mainApp;
	private ApplicationContext context;
	private RestKitchen kitchenRecord;
	private RestKitchen kitchenRecordSelected = new RestKitchen();
	private String userEntry = "Douglas";
	
	@Autowired
	private IRestKitchenManagement manageKitchen;
	//Declaracion Labels
	@FXML
	private Label lblKitchenName;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField kitchenName;	
	@FXML
	private TextArea kitchenDescription;	

	//Declaracion Tablas
	@FXML
	private TableView<RestKitchen> kitchenList = new TableView<RestKitchen>();  ;
	@FXML
	private ObservableList <RestKitchen> kitchenData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn kitchenNameColumn = new TableColumn("kitchenNameColumn");
	@FXML
	private TableColumn kitchenDescriptionColumn = new TableColumn("kitchenDescriptionColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int kitchenCodeSelected  = kitchenList.getSelectionModel().getSelectedItem().getKitchenId();
		System.out.println("SELECTED ROW "+kitchenCodeSelected);
		//kitchenRecordSelected = manageKitchen.findRestKitchen(discountCodeSelected);
    	loadRecordInformation(kitchenCodeSelected);
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
					if (kitchenRecordSelected.getKitchenId() ==0 ){
						newRecord = true;
					}
					kitchenRecordSelected.setKitchenName(kitchenName.getText());
					kitchenRecordSelected.setKitchenDescription(kitchenDescription.getText());

					kitchenRecord = new RestKitchen();
					if (newRecord){
						System.out.println("NUEVO");
						kitchenRecord = manageKitchen.insertRestKitchen(kitchenRecordSelected);						

					}else{
						System.out.println("UPDATE");
						kitchenRecord =  manageKitchen.updateRestKitchen(kitchenRecordSelected);						
					}
					if (kitchenRecord==null){
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

		

		public FormKitchenController() {
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
		kitchenRecordSelected = new RestKitchen();
		 kitchenName.setText("");
		 kitchenDescription.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
			String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (kitchenName.getText() == null || kitchenName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblKitchenName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;			
	}
	
	public void refreshList(){
		
		
		kitchenData.clear();
		kitchenList.getColumns().clear();
		kitchenNameColumn.setCellValueFactory(new PropertyValueFactory<RestKitchen, String>("kitchenName"));
		kitchenDescriptionColumn.setCellValueFactory(new PropertyValueFactory<RestKitchen, String>("kitchenDescription"));
		List<RestKitchen> list = manageKitchen.findAll();
		for(RestKitchen u : list){
			kitchenData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		kitchenList.setItems(kitchenData);
		
		kitchenList.getColumns().addAll(kitchenNameColumn,kitchenDescriptionColumn);		

	}
	
	public void refreshListOnSearch(){
		
		
		kitchenData.clear();
		kitchenList.getColumns().clear();
		kitchenNameColumn.setCellValueFactory(new PropertyValueFactory<RestKitchen, String>("kitchenName"));
		kitchenDescriptionColumn.setCellValueFactory(new PropertyValueFactory<RestKitchen, String>("kitchenDescription"));
		List<RestKitchen> list = manageKitchen.findMenyTypeByExample(kitchenName.getText());
		for(RestKitchen u : list){
			kitchenData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		kitchenList.setItems(kitchenData);
		
		kitchenList.getColumns().addAll(kitchenNameColumn,kitchenDescriptionColumn);		

	}
	

	
	public void loadRecordInformation(int kitchenCodePrm){
		kitchenRecordSelected = manageKitchen.findRestKitchen(kitchenCodePrm);
		 kitchenName.setText(kitchenRecordSelected.getKitchenName());
		 kitchenDescription.setText(kitchenRecordSelected.getKitchenDescription());
		 
			}

	public void defaultModeEnabled(){
		kitchenName.setEditable(true);
		kitchenDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		kitchenName.setEditable(true);
		kitchenDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		kitchenName.setEditable(true);
		kitchenDescription.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		kitchenName.setEditable(true);
		kitchenDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		kitchenName.setEditable(true);
		kitchenDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblKitchenName.setTextFill(Color.web("#000000"));
			
		}

		
}
