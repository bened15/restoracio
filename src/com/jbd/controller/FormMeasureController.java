package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgMeasureUnitManagement;
import com.jbd.model.CtgMeasureUnit;

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

public class FormMeasureController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgMeasureUnit measureRecord;
	private CtgMeasureUnit measureRecordSelected = new CtgMeasureUnit();
	private String userEntry = "Douglas";
	
	@Autowired
	private ICtgMeasureUnitManagement manageDiscount;
	//Declaracion Labels
	@FXML
	private Label lblMeasureName, lblMeasureUnit;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField measureName,measureUnit;	

	//Declaracion Tablas
	@FXML
	private TableView<CtgMeasureUnit> measureList = new TableView<CtgMeasureUnit>();  ;
	@FXML
	private ObservableList <CtgMeasureUnit> measureData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn measureNameColumn = new TableColumn("measureNameColumn");
	@FXML
	private TableColumn measureUnitColumn = new TableColumn("measureUnitColumn");
	@FXML
	private TableColumn measureIdColumn = new TableColumn("measureIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {	
		refreshListOnSearch();
//			refreshList();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int measureCodeSelected  = measureList.getSelectionModel().getSelectedItem().getMeasureUnitId();
		System.out.println("SELECTED ROW "+measureCodeSelected);
		//measureRecordSelected = managediscount.findCtgMeasureUnit(discountCodeSelected);
    	loadRecordInformation(measureCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {				
			resetValues();
			newModeEnabled();
	}
	@FXML
	public void onClear(MouseEvent event) {				
			initModeEnable();
			resetValues();
	}
		@FXML
		public void onSave(MouseEvent event) {
			
				String error = validateRecord();
				System.out.println(error);
				if (error == null){
					boolean newRecord = false;
					if (measureRecordSelected.getMeasureUnitId() ==0 ){
						newRecord = true;
					}
					measureRecordSelected.setMeasureName(measureName.getText());;
					measureRecordSelected.setMeasureUni(measureUnit.getText());
					measureRecordSelected.setEntryDate(new Date());
					measureRecordSelected.setEntryUser(userEntry);
					
					measureRecord = new CtgMeasureUnit();
					if (newRecord){
						System.out.println("NUEVO");
						measureRecord = manageDiscount.insertCtgMeasureUnit(measureRecordSelected);						

					}else{
						System.out.println("UPDATE");
						measureRecord =  manageDiscount.updateCtgMeasureUnit(measureRecordSelected);						
					}
					if (measureRecord==null){
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
						initModeEnable();
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"Los campos marcados en rojo son obligatorios y presentan errores.\n "
							+ "A continuacion se muestra el detalle de errores:\n" + error);

				}
			
		}

		

		public FormMeasureController() {
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
		measureRecordSelected = new CtgMeasureUnit();
		 measureName.setText("");
		 measureUnit.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
			String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (measureName.getText() == null || measureName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblMeasureName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (measureUnit.getText()== null ||measureUnit.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo unidad es obligatorio.\n");
			messageErrorNumber++;
			lblMeasureUnit.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorString;			
	}
	
	public void refreshList(){
		
		
		measureData.clear();
		measureList.getColumns().clear();
		measureNameColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureName"));
		measureUnitColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureUni"));
		measureIdColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureUnitId"));
		List<CtgMeasureUnit> list = manageDiscount.findAll();
		for(CtgMeasureUnit u : list){
			measureData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		measureList.setItems(measureData);
		
		measureList.getColumns().addAll(measureNameColumn,measureUnitColumn,measureIdColumn);		

	}

	public void refreshListOnSearch(){
		
		
		measureData.clear();
		measureList.getColumns().clear();
		measureNameColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureName"));
		measureUnitColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureUni"));
		measureIdColumn.setCellValueFactory(new PropertyValueFactory<CtgMeasureUnit, String>("measureUnitId"));
		List<CtgMeasureUnit> list = manageDiscount.findMeasureByExample(measureName.getText(), measureUnit.getText());
		for(CtgMeasureUnit u : list){
			measureData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		measureList.setItems(measureData);
		
		measureList.getColumns().addAll(measureNameColumn,measureUnitColumn,measureIdColumn);		

	}


	
	public void loadRecordInformation(int measureCodePrm){
		measureRecordSelected = manageDiscount.findCtgMeasureUnit(measureCodePrm);
		 measureName.setText(measureRecordSelected.getMeasureName());
		 measureUnit.setText(measureRecordSelected.getMeasureUni());
		 
			}

	public void defaultModeEnabled(){
		measureName.setEditable(true);
		measureUnit.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnable(){
		measureName.setEditable(true);
		measureUnit.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		measureName.setEditable(true);
		measureUnit.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		measureName.setEditable(true);
		measureUnit.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		measureName.setEditable(true);
		measureUnit.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblMeasureName.setTextFill(Color.web("#000000"));
			lblMeasureUnit.setTextFill(Color.web("#000000"));
			
		}

		
}
