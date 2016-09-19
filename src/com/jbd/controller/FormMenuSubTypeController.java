package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgMenuSubTypeManagement;
import com.jbd.model.CtgMenuType;
import com.jbd.model.CtgMenuSubType;

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
import javafx.stage.Stage;
import application.Main;

public class FormMenuSubTypeController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgMenuSubType menuSubTypeRecord;
	private CtgMenuSubType menuSubTypeRecordSelected = new CtgMenuSubType();
	private CtgMenuType menuTypeSelected = new CtgMenuType();
	private String userEntry;
	
	@Autowired
	private ICtgMenuSubTypeManagement manageCtgMenuSubType;
	//Declaracion Labels
	@FXML
	private Label lblMenuSubTypeNameDescription,lblMenuSubTypeName;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn   , clearBtn , deleteBtn  , closeBtn;
	//Declaracion Campos
	@FXML
	private TextField menuSubTypeName;
	
	@FXML
	private TextArea menuSubTypeDescription;


	//Declaracion Tablas

	@FXML
	private TableView<CtgMenuSubType> menuSubTypeList = new TableView<CtgMenuSubType>();  ;
	@FXML
	private ObservableList <CtgMenuSubType> menuSubTypeData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuSubTypeNameColumn = new TableColumn("menuSubTypeName");
	@FXML
	private TableColumn menuSubTypeDescriptionColumn = new TableColumn("menuSubTypeDescriptionColumn");

	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshList();		
	}

	public void setSelectedMenuType(CtgMenuType item) {	
		menuTypeSelected = item;
		//menuSubTypeRecordSelected = manageSupplier.findCtgMenuSubType(supplierCodeSelected);
		loadRecordInformationComments(menuTypeSelected);
    	resetValues();
    	initModeEnabled();
	}

	@FXML
	public void getSelectedRowProductItem(MouseEvent event) {	
		int menuSubTypeCodeSelected  = menuSubTypeList.getSelectionModel().getSelectedItem().getMenuSubTypeId();
		System.out.println("SELECTED ROW MENU ITEM"+menuSubTypeCodeSelected);
		//menuSubTypeRecordSelected = manageSupplier.findCtgMenuSubType(supplierCodeSelected);
    	loadRecordInformation(menuSubTypeCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {	
			resetRecord();
			newModeEnabled();
	}

	@FXML
	public void onClose(MouseEvent event) {	
		// get a handle to the stage
	    Stage stage = (Stage) closeBtn.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	    }

	
	@FXML
	public void onDelete(MouseEvent event) {	
		boolean withOutError = false;
		String exceptionError = null;
		try{
			withOutError = manageCtgMenuSubType.deleteCtgMenuSubType(menuSubTypeRecordSelected);						
			
		}catch (Exception e){
			//e.printStackTrace();
			exceptionError= e.getMessage();
		}

		if (!withOutError){
			JOptionPane.showMessageDialog(null,
					"El registro no puede ser eliminado debido a que existen platos asociados al tipo de submenu seleccionado.\n "
					+ exceptionError);

			System.out.println("ERROR AL ELIMINAR");
		}else{
			JOptionPane.showMessageDialog(null,
					"Registro eliminado exitosamente");

			System.out.println("EXITO AL ELIMINAR");
			resetValues();
			refreshList();
			initModeEnabled();
		}
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
					if (menuSubTypeRecordSelected.getMenuSubTypeId() ==0 ){
						newRecord = true;
					}
					menuSubTypeRecordSelected.setMenuTypeId(menuTypeSelected.getMenuTypeId());
					menuSubTypeRecordSelected.setMenuSubTypeName(menuSubTypeName.getText());
					menuSubTypeRecordSelected.setMenuSubTypeDescription(menuSubTypeDescription.getText());
					menuSubTypeRecordSelected.setEntryDate(new Date());
					menuSubTypeRecordSelected.setEntryUser(userEntry);

					menuSubTypeRecord = new CtgMenuSubType();
					if (newRecord){
						System.out.println("NUEVO");
						menuSubTypeRecord = manageCtgMenuSubType.insertCtgMenuSubType(menuSubTypeRecordSelected);						
					}else{
						System.out.println("UPDATE");
						menuSubTypeRecord =  manageCtgMenuSubType.updateCtgMenuSubType(menuSubTypeRecordSelected);						
					}
					if (menuSubTypeRecord==null){
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

		

		public FormMenuSubTypeController() {
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
//		refreshList();
//		refreshComboBoxList();
		defaultModeEnabled();
    }

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	
	public void resetValues(){
		menuSubTypeRecordSelected = new CtgMenuSubType();		
		menuSubTypeDescription.setText("");
		menuSubTypeName.setText("");
		 		 
	}
	public void resetRecord(){
		menuSubTypeRecordSelected = new CtgMenuSubType();		
		 		 
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (menuSubTypeName.getText() == null || menuSubTypeName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;				
			lblMenuSubTypeName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		
		return errorString;			
	}
	
	
	public void refreshList(){
		
		
		menuSubTypeData.clear();
		menuSubTypeList.getColumns().clear();
		menuSubTypeNameColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuSubType, String>("menuSubTypeName"));
		menuSubTypeDescriptionColumn.setCellValueFactory(new PropertyValueFactory<CtgMenuSubType, String>("menuSubTypeDescription"));
		List<CtgMenuSubType> list = manageCtgMenuSubType.findByMenuType(menuTypeSelected);
		for(CtgMenuSubType u : list){
			menuSubTypeData.add(u);
		}
		
		menuSubTypeList.setItems(menuSubTypeData);
		
		menuSubTypeList.getColumns().addAll(menuSubTypeNameColumn,menuSubTypeDescriptionColumn)		;

	}
	

	public void loadRecordInformationComments(CtgMenuType menuType){
		refreshList();
		
	}
	
	public void loadRecordInformation(int menuSubTypeCodePrm){
		menuSubTypeRecordSelected = manageCtgMenuSubType.findCtgMenuSubType(menuSubTypeCodePrm);
		menuSubTypeDescription.setText(menuSubTypeRecordSelected.getMenuSubTypeDescription()); 
		menuSubTypeName.setText(menuSubTypeRecordSelected.getMenuSubTypeName());

			}

	public void defaultModeEnabled(){
		menuSubTypeDescription.setEditable(false);
		menuSubTypeName.setEditable(false);
		 newBtn.setDisable(false);
		 clearBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		menuSubTypeDescription.setEditable(false);
		menuSubTypeName.setEditable(false);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 deleteBtn.setDisable(true);
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		menuSubTypeDescription.setEditable(true);
		menuSubTypeName.setEditable(true);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		menuSubTypeDescription.setEditable(true);
		menuSubTypeName.setEditable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 deleteBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		menuSubTypeDescription.setEditable(true);
		menuSubTypeName.setEditable(true);
		 deleteBtn.setDisable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblMenuSubTypeName.setTextFill(Color.web("#000000"));
			lblMenuSubTypeNameDescription.setTextFill(Color.web("#000000"));
			
		}

		

		public String getUserEntry() {
			return userEntry;
		}

		public void setUserEntry(String userEntry) {
			this.userEntry = userEntry;
		}

	
		

}
