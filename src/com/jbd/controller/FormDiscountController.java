package com.jbd.controller;


import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgDiscountManagement;
import com.jbd.model.CtgDiscount;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import application.Main;

public class FormDiscountController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgDiscount discountRecord;
	private CtgDiscount discountRecordSelected = new CtgDiscount();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	@Autowired
	private ICtgDiscountManagement manageDiscount;
	//Declaracion Labels
	@FXML
	private Label lblDiscountName, lblDiscountPercentage,lblDiscountBeginDate,lblDiscountEndDate;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField discountName,discountPercentage;	
	@FXML
	private TextArea discountDescription;	
	@FXML	
	private DatePicker discountBeginDate  = new DatePicker(), discountEndDate  = new DatePicker();

	//Declaracion Tablas
	@FXML
	private TableView<CtgDiscount> discountList = new TableView<CtgDiscount>();  ;
	@FXML
	private ObservableList <CtgDiscount> discountData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn discountNameColumn = new TableColumn("discountNameColumn");
	@FXML
	private TableColumn discountEndDateColumn = new TableColumn("discountEndDateColumn");
	@FXML
	private TableColumn discountBeginDateColumn = new TableColumn("discountBeginDateColumn");
	@FXML
	private TableColumn discountIdColumn = new TableColumn("discountIdColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int discountCodeSelected  = discountList.getSelectionModel().getSelectedItem().getDiscountId();
		System.out.println("SELECTED ROW "+discountCodeSelected);
		//discountRecordSelected = managediscount.findCtgDiscount(discountCodeSelected);
    	loadRecordInformation(discountCodeSelected);
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
					if (discountRecordSelected.getDiscountId() ==0 ){
						newRecord = true;
					}
					discountRecordSelected.setDiscountDescription(discountDescription.getText());;
					discountRecordSelected.setDiscountName(discountName.getText());
					discountRecordSelected.setDiscountPercentage(Integer.parseInt(discountPercentage.getText()));
					discountRecordSelected.setEntryDate(new Date());
					discountRecordSelected.setEntryUser(userEntry);
					discountRecordSelected.setDiscountValidSince(gf.asDate(discountBeginDate.getValue()));
					discountRecordSelected.setDiscountValidUntil(gf.asDate(discountEndDate.getValue()));
					
					discountRecord = new CtgDiscount();
					if (newRecord){
						System.out.println("NUEVO");
						discountRecord = manageDiscount.insertCtgDiscount(discountRecordSelected);						

					}else{
						System.out.println("UPDATE");
						discountRecord =  manageDiscount.updateCtgDiscount(discountRecordSelected);						
					}
					if (discountRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				}
			
		}

		

		public FormDiscountController() {
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
		discountRecordSelected = new CtgDiscount();
		 discountName.setText("");
		 discountDescription.setText("");
		 discountPercentage.setText("");
		discountBeginDate.setValue(null);
		discountEndDate.setValue(null);
	}
	
	public String validateRecord() {
		 defaultLabel();
		String errorMessage = null;	

		if (discountName.getText() == null || discountName.getText().isEmpty()){
			errorMessage = "El campo nombre es obligatorio.";
			lblDiscountName.setTextFill(Color.web("#ff0000"));
		}
		if (discountPercentage.getText()== null ||discountPercentage.getText().isEmpty() ){
			errorMessage =  "El campo porcentaje es obligatorio.";
			lblDiscountPercentage.setTextFill(Color.web("#ff0000"));
		}else{
			if (!gf.validNumber(discountPercentage.getText())){
				errorMessage =  "El campo porcentaje debe ser un numero";				
				lblDiscountPercentage.setTextFill(Color.web("#ff0000"));
			}
		}

		if (discountBeginDate.getValue()==null){
			errorMessage = "El campo de fecha de inicio es obligatorio.";
			lblDiscountBeginDate.setTextFill(Color.web("#ff0000"));
		}
		if (discountEndDate.getValue()==null){
			errorMessage = "El campo de fecha fin es obligatorio.";
			lblDiscountEndDate.setTextFill(Color.web("#ff0000"));
		}
		
		return errorMessage;			
	}
	
	public void refreshList(){
		
		
		discountData.clear();
		discountList.getColumns().clear();
		discountNameColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountName"));
		discountBeginDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidSince"));
		discountEndDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidUntil"));
		discountIdColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountId"));
		List<CtgDiscount> list = manageDiscount.findAll();
		for(CtgDiscount u : list){
			CtgDiscount u1 = new CtgDiscount();
			u1.setDiscountId(u.getDiscountId());
			u1.setDiscountName(u.getDiscountName() + " - "+u.getDiscountPercentage()+"%");
			u1.setDiscountValidSince(u.getDiscountValidSince());
			u1.setDiscountValidUntil(u.getDiscountValidUntil());
			discountData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		discountList.setItems(discountData);
		
		discountList.getColumns().addAll(discountNameColumn,discountBeginDateColumn,discountEndDateColumn,discountIdColumn);
		

	}

	public void refreshListOnSearch(){
		
		
		discountData.clear();
		discountList.getColumns().clear();
		discountNameColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountName"));
		discountBeginDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidSince"));
		discountEndDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidUntil"));
		discountIdColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountId"));
		List<CtgDiscount> list = manageDiscount.findDiscountByExample(discountName.getText(), gf.asDate(discountBeginDate.getValue()));
		for(CtgDiscount u : list){
			CtgDiscount u1 = new CtgDiscount();
			u1.setDiscountId(u.getDiscountId());
			u1.setDiscountName(u.getDiscountName() + " - "+u.getDiscountPercentage()+"%");
			u1.setDiscountValidSince(u.getDiscountValidSince());
			u1.setDiscountValidUntil(u.getDiscountValidUntil());
			discountData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		discountList.setItems(discountData);
		
		discountList.getColumns().addAll(discountNameColumn,discountBeginDateColumn,discountEndDateColumn,discountIdColumn);
		

	}


	
	public void loadRecordInformation(int discountCodePrm){
		discountRecordSelected = manageDiscount.findCtgDiscount(discountCodePrm);
		 discountName.setText(discountRecordSelected.getDiscountName());
		 discountDescription.setText(discountRecordSelected.getDiscountDescription());
		 discountPercentage.setText(discountRecordSelected.getDiscountPercentage()+"");
		 discountBeginDate.setValue(gf.asLocalDate(discountRecordSelected.getDiscountValidSince()));
		 discountEndDate.setValue(gf.asLocalDate(discountRecordSelected.getDiscountValidUntil()));
		 
			}

	public void defaultModeEnabled(){
		discountName.setEditable(true);
		discountDescription.setEditable(false);
		discountPercentage.setEditable(false);
		discountBeginDate.setDisable(false);
		discountEndDate.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		discountName.setEditable(true);
		discountDescription.setEditable(false);
		discountPercentage.setEditable(false);
		discountBeginDate.setDisable(false);
		discountEndDate.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountEndDate.setDisable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountEndDate.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountEndDate.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblDiscountName.setTextFill(Color.web("#000000"));
			lblDiscountPercentage.setTextFill(Color.web("#000000"));
			lblDiscountBeginDate.setTextFill(Color.web("#000000"));
			lblDiscountEndDate.setTextFill(Color.web("#000000"));
			
		}

		
		
}