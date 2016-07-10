package com.jbd.controller;


import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ISysRoleManagement;
import com.jbd.model.SysRole;

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

public class FormRoleController {

	private Main mainApp;
	private ApplicationContext context;
	private SysRole sysRoleRecord;
	private SysRole sysRoleRecordSelected = new SysRole();
	private String userEntry = "Douglas";
	
	@Autowired
	private ISysRoleManagement manageSysRole;
	//Declaracion Labels
	@FXML
	private Label lblRoleName, lblRoleCode,lblRoleDescription;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField roleCode;	
	@FXML
	private TextField roleName;	
	@FXML
	private TextArea roleDescription;	

	//Declaracion Tablas
	@FXML
	private TableView<SysRole> sysRoleList = new TableView<SysRole>();  ;
	@FXML
	private ObservableList <SysRole> sysRoleData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn roleCodeColumn = new TableColumn("roleCodeColumn");
	@FXML
	private TableColumn roleNameColumn = new TableColumn("roleNameColumn");
	@FXML
	private TableColumn roleDescriptionColumn = new TableColumn("roleDescriptionColumn");
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		String sysRoleCodeSelected  = sysRoleList.getSelectionModel().getSelectedItem().getRolCode();
		System.out.println("SELECTED ROW "+sysRoleCodeSelected);
		//sysRoleRecordSelected = manageSysRole.findSysRole(discountCodeSelected);
    	loadRecordInformation(sysRoleCodeSelected);
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
					if (sysRoleRecordSelected.getRolCode() == null ){
						newRecord = true;
					}
					sysRoleRecordSelected.setRolCode(roleCode.getText());
					sysRoleRecordSelected.setRolName(roleName.getText());
					sysRoleRecordSelected.setRolDescription(roleDescription.getText());
					sysRoleRecordSelected.setEntryDate(new Date());
					sysRoleRecordSelected.setEntryUser(userEntry);
					
					sysRoleRecord = new SysRole();
					if (newRecord){
						System.out.println("NUEVO");
						sysRoleRecord = manageSysRole.insertSysRole(sysRoleRecordSelected);						

					}else{
						System.out.println("UPDATE");
						sysRoleRecord =  manageSysRole.updateSysRole(sysRoleRecordSelected);						
					}
					if (sysRoleRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						resetValues();
						refreshList();
						initModeEnabled();
					}
				}
			
		}

		

		public FormRoleController() {
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
		sysRoleRecordSelected = new SysRole();
		roleCode.setText("");
		 roleName.setText("");
		 roleDescription.setText("");
	}
	
	public String validateRecord() {
		 defaultLabel();
		String errorMessage = null;	

		if (roleCode.getText() == null || roleCode.getText().isEmpty()){
			errorMessage = "El campo codigo es obligatorio.";
			lblRoleCode.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (roleName.getText() == null || roleName.getText().isEmpty()){
			errorMessage = "El campo nombre es obligatorio. ";
			lblRoleName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorMessage;			
	}
	
	public void refreshList(){
		
		
		sysRoleData.clear();
		sysRoleList.getColumns().clear();
		roleNameColumn.setCellValueFactory(new PropertyValueFactory<SysRole, String>("rolName"));
		roleCodeColumn.setCellValueFactory(new PropertyValueFactory<SysRole, String>("rolCode"));
		List<SysRole> list = manageSysRole.findAll();
		for(SysRole u : list){
			sysRoleData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		sysRoleList.setItems(sysRoleData);
		
		sysRoleList.getColumns().addAll(roleCodeColumn,roleNameColumn);		

	}
	
	public void refreshListOnSearch(){
		
		
		sysRoleData.clear();
		sysRoleList.getColumns().clear();
		roleNameColumn.setCellValueFactory(new PropertyValueFactory<SysRole, String>("rolName"));
		roleCodeColumn.setCellValueFactory(new PropertyValueFactory<SysRole, String>("rolCode"));
		List<SysRole> list = manageSysRole.findRoleByExample(roleCode.getText(), roleName.getText());
		for(SysRole u : list){
			sysRoleData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		sysRoleList.setItems(sysRoleData);
		
		sysRoleList.getColumns().addAll(roleCodeColumn,roleNameColumn);		

	}
	

	
	public void loadRecordInformation(String sysRoleCodePrm){
		sysRoleRecordSelected = manageSysRole.findSysRole(sysRoleCodePrm);
		 roleCode.setText(sysRoleRecordSelected.getRolCode());
		 roleName.setText(sysRoleRecordSelected.getRolName());
		 roleDescription.setText(sysRoleRecordSelected.getRolDescription());
		 
			}

	public void defaultModeEnabled(){
		roleCode.setEditable(true);
		roleName.setEditable(true);
		roleDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		roleCode.setEditable(true);
		roleName.setEditable(true);
		roleDescription.setEditable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		roleCode.setEditable(true);
		roleName.setEditable(true);
		roleDescription.setEditable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		roleCode.setEditable(true);
		roleName.setEditable(true);
		roleDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		roleCode.setEditable(true);
		roleName.setEditable(true);
		roleDescription.setEditable(true);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblRoleName.setTextFill(Color.web("#000000"));
			lblRoleCode.setTextFill(Color.web("#000000"));
			
		}

		
}
