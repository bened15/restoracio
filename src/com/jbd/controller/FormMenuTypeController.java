package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.model.CtgMenuType;

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

public class FormMenuTypeController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgMenuType menuTypeRecord;
	private CtgMenuType menuTypeRecordSelected = new CtgMenuType();
	private String userEntry = "Douglas";
	
	@Autowired
	private ICtgMenuTypeManagement manageMenuType;
	//Declaracion Labels
	@FXML
	private Label lblMenuTypeName;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField menuTypeName;	
	@FXML
	private TextArea menuTypeDescription;	

	//Declaracion Tablas
	@FXML
	private TableView<CtgMenuType> menuTypeList = new TableView<CtgMenuType>();  ;
	@FXML
	private ObservableList <CtgMenuType> menuTypeData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuTypeNameColumn = new TableColumn("menuTypeNameColumn");
	@FXML
	private TableColumn menuTypeDescriptionColumn = new TableColumn("menuTypeDescriptionColumn");
	@FXML
	private TableColumn menuTypeIdColumn = new TableColumn("menuTypeIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int menuTypeCodeSelected  = menuTypeList.getSelectionModel().getSelectedItem().getMenuTypeId();
		System.out.println("SELECTED ROW "+menuTypeCodeSelected);
		//menuTypeRecordSelected = manageMenuType.findCtgMenuType(discountCodeSelected);
    	loadRecordInformation(menuTypeCodeSelected);
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
					if (menuTypeRecordSelected.getMenuTypeId() ==0 ){
						newRecord = true;
					}
					menuTypeRecordSelected.setMenuTypeName(menuTypeName.getText());
					menuTypeRecordSelected.setMenuTypeDescription(menuTypeDescription.getText());
					menuTypeRecordSelected.setEntryDate(new Date());
					menuTypeRecordSelected.setEntryUser(userEntry);
					
					menuTypeRecord = new CtgMenuType();
					if (newRecord){
						System.out.println("NUEVO");
						menuTypeRecord = manageMenuType.insertCtgMenuType(menuTypeRecordSelected);						

					}else{
						System.out.println("UPDATE");
						menuTypeRecord =  manageMenuType.updateCtgMenuType(menuTypeRecordSelected);						
					}
					if (menuTypeRecord==null){
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

		

		public FormMenuTypeController() {
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
		menuTypeRecordSelected = new CtgMenuType();
		 menuTypeName.setText("");
		 menuTypeDescription.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;

		if (menuTypeName.getText() == null || menuTypeName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblMenuTypeName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorString;			
	}
	
	public void refreshList(){
		
		
		menuTypeData.clear();
		menuTypeList.getColumns().clear();
		menuTypeNameColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeName"));
		menuTypeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeDescription"));
		menuTypeIdColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeId"));
		List<CtgMenuType> list = manageMenuType.findAll();
		for(CtgMenuType u : list){
			menuTypeData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		menuTypeList.setItems(menuTypeData);
		
		menuTypeList.getColumns().addAll(menuTypeNameColumn,menuTypeIdColumn,menuTypeDescriptionColumn);		

	}
	
	public void refreshListOnSearch(){
		
		
		menuTypeData.clear();
		menuTypeList.getColumns().clear();
		menuTypeNameColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeName"));
		menuTypeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeDescription"));
		menuTypeIdColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuType, String>("menuTypeId"));
		List<CtgMenuType> list = manageMenuType.findMenyTypeByExample(menuTypeName.getText());
		for(CtgMenuType u : list){
			menuTypeData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		menuTypeList.setItems(menuTypeData);
		
		menuTypeList.getColumns().addAll(menuTypeNameColumn,menuTypeIdColumn,menuTypeDescriptionColumn);		

	}
	

	
	public void loadRecordInformation(int menuTypeCodePrm){
		menuTypeRecordSelected = manageMenuType.findCtgMenuType(menuTypeCodePrm);
		 menuTypeName.setText(menuTypeRecordSelected.getMenuTypeName());
		 menuTypeDescription.setText(menuTypeRecordSelected.getMenuTypeDescription());
		 
			}

	public void defaultModeEnabled(){
		menuTypeName.setEditable(true);
		menuTypeDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		menuTypeName.setEditable(true);
		menuTypeDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		menuTypeName.setEditable(true);
		menuTypeDescription.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		menuTypeName.setEditable(true);
		menuTypeDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		menuTypeName.setEditable(true);
		menuTypeDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblMenuTypeName.setTextFill(Color.web("#000000"));
			
		}

		
}
