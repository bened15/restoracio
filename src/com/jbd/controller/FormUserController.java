package com.jbd.controller;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ISysRoleManagement;
import com.jbd.hibernate.interfaces.ISysUserManagement;
import com.jbd.hibernate.interfaces.ISysUserRolManagement;
import com.jbd.model.SysUser;
import com.jbd.model.SysRole;
import com.jbd.model.SysUserRol;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import application.Main;

public class FormUserController {

	private Main mainApp;
	private ApplicationContext context;
	private SysUser userRecord;
	private SysUser userRecordSelected = new SysUser();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	
	@Autowired
	private ISysUserManagement manageUser;
	@Autowired
	private ISysRoleManagement manageRole;
	@Autowired
	private ISysUserRolManagement manageUserRole;
	//Declaracion Labels
	@FXML
	private Label lblName, lblLastname,lblBegindate, lblUsercode, lblUserPassword, lblUserRole   ;
	//Declaracion ComboBox
	@FXML
	private ComboBox userRole   ;
	@FXML
	private ObservableList <SysRole> roleData =  FXCollections.observableArrayList();

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField userCode,userPassword;
	@FXML
	private TextField userName,userLastname,userAddress,userPhone1,userEmail;
	@FXML	
	private DatePicker userBirthdate  = new DatePicker(), userEmploymentBeginDate  = new DatePicker(),userEmploymentEndDate  = new DatePicker();
	
	//Declaracion Tablas
	@FXML
	private TableView<SysUser> userList = new TableView<SysUser>();  ;
	@FXML
	private ObservableList <SysUser> userData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn usercodeColumn = new TableColumn("usercodeColumn");
	@FXML
	private TableColumn usernameColumn = new TableColumn("usernameColumn");
	
	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
		refreshListOnSearch();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		String userCodeSelected  = userList.getSelectionModel().getSelectedItem().getUserCode();
		System.out.println("SELECTED ROW "+userCodeSelected);
//		userRecordSelected = manageUser.findSysUser(userCodeSelected);
		loadRecordInformation(userCodeSelected);
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
			
			System.out.println(userRole.getValue());
				String error = validateRecord();
				System.out.println(error);
				if (error == null){
					boolean newRecord = false;
					if (userRecordSelected.getUserCode() == null ){
						newRecord = true;
					}
					userRecordSelected.setUserCode(userCode.getText());;
					userRecordSelected.setUserPassword(userPassword.getText());
					userRecordSelected.setUserName(userName.getText());
					userRecordSelected.setUserName(userName.getText());
					userRecordSelected.setUserLastname(userLastname.getText());
					userRecordSelected.setUserAddress(userAddress.getText());
					userRecordSelected.setUserPhone1(userPhone1.getText());
					userRecordSelected.setUserEmail(userEmail.getText());
					userRecordSelected.setUserBirthDate(gf.asDate( userBirthdate.getValue()));
					userRecordSelected.setEntryDate(new Date());
					userRecordSelected.setEntryUser(userEntry);
					userRecordSelected.setEmploymentBegin(gf.asDate( userEmploymentBeginDate.getValue()));
					userRecordSelected.setEmploymentBegin(gf.asDate( userEmploymentBeginDate.getValue()));
					userRecordSelected.setEmploymentEnd(gf.asDate( userEmploymentEndDate.getValue()));					
					userRecord = new SysUser();
					if (newRecord){
						System.out.println("NUEVO");
						userRecord = manageUser.insertSysUser(userRecordSelected);						
						SysUserRol item = new SysUserRol();
						item.setEntryDate(new Date());
						item.setEntryUser(userEntry);
						item.setSysRole(manageRole.findSysRole(((SysRole) userRole.getValue()).getRolCode()));
						item.setSysUser(userRecord);
						manageUserRole.insertSysUserRol(item);

					}else{
						System.out.println("UPDATE");

						userRecord = manageUser.updateSysUser(userRecordSelected);
						List<SysUserRol> itemList = userRecordSelected.getSysUserRols();
						SysUserRol item ; 
						if(itemList.size()>0){
							item = userRecordSelected.getSysUserRols().get(0);
						}else{
							item = new SysUserRol();
						}
						item.setEntryDate(new Date());
						item.setEntryUser(userEntry);
						SysRole role = null;
						System.out.println("ASIGNANDO ROL");
						role = manageRole.findSysRole(userRole.getValue().toString());
						System.out.println(role);
						item.setSysRole(role);
						item.setSysUser(userRecord);
						manageUserRole.updateSysUserRol(item);

					}
					if (userRecord==null){
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

		

		public FormUserController() {
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
        alterComboBoxProperties(); 
        //loadComboBox
        //addListeners();
		refreshList();
		refreshComboBoxList();
		defaultModeEnabled();
    }

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}


	public void resetValues(){
		userRecordSelected = new SysUser();
		 userCode.setText("");
		 userPassword.setText("");
		 userName.setText("");
		 userLastname.setText("");
		 userAddress.setText("");
		 userPhone1.setText("");
		 userEmail.setText("");
		 userBirthdate.setValue(null);
		 userEmploymentBeginDate.setValue(null);
		 userEmploymentEndDate.setValue(null);
		 userRole.getSelectionModel().select(null);

	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (userCode.getText() == null || userCode.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo de usuario es obligatorio.\n");
			messageErrorNumber++;
			lblUsercode.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (userPassword.getText()== null ||userPassword.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+ "El campo de contraseña es obligatorio.\n");
			messageErrorNumber++;
			lblUserPassword.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (userName.getText()==null || userName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (userLastname.getText()==null || userLastname.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo apellido es obligatorio.\n");
			messageErrorNumber++;
			lblLastname.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (userEmploymentBeginDate.getValue() == null){
			errorMessage.append(messageErrorNumber+"-"+"El campo fecha de contratacion es obligatorio.\n");
			messageErrorNumber++;
			lblBegindate.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (userRole.getValue()== null){
			errorMessage.append(messageErrorNumber+"-"+"El campo rol es obligatorio.\n");
			messageErrorNumber++;
			lblUserRole.setTextFill(Color.web("#ff0000"));
			
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;			
	}
	
	public void refreshList(){
		
		
		userData.clear();
		userList.getColumns().clear();
		usercodeColumn.setCellValueFactory(new PropertyValueFactory<SysUser, String>("userCode"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<SysUser, String>("userName"));
		List<SysUser> list = manageUser.findAll();
		for(SysUser u : list){
			SysUser u1 = new SysUser();
			u1.setUserCode(u.getUserCode());
			u1.setUserName(u.getUserName()+ " "+u.getUserLastname());
			userData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		userList.setItems(userData);
		
		userList.getColumns().addAll(usercodeColumn,usernameColumn);
		

	}

	public void refreshListOnSearch(){
		
		
		userData.clear();
		userList.getColumns().clear();
		usercodeColumn.setCellValueFactory(new PropertyValueFactory<SysUser, String>("userCode"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<SysUser, String>("userName"));
		List<SysUser> list = manageUser.findByUserExample(userName.getText(), userLastname.getText(), userCode.getText());
		for(SysUser u : list){
			SysUser u1 = new SysUser();
			u1.setUserCode(u.getUserCode());
			u1.setUserName(u.getUserName()+ " "+u.getUserLastname());
			userData.add(u1);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		userList.setItems(userData);
		
		userList.getColumns().addAll(usercodeColumn,usernameColumn);
		

	}

	
public void refreshComboBoxList(){
		
		
		roleData.clear();
		List<SysRole> list = manageRole.findAll();
		for(SysRole r : list){
			roleData.add(r);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		userRole.setItems(roleData);
		//userRole.getSelectionModel().selectFirst(); //select the first element		
		
		

	}

	
	public void loadRecordInformation(String userCodePrm){
		userRecordSelected = manageUser.findSysUser(userCodePrm);
		 userCode.setText(userRecordSelected.getUserCode());
		 userPassword.setText(userRecordSelected.getUserPassword());
		 userName.setText(userRecordSelected.getUserName());
		 userLastname.setText(userRecordSelected.getUserLastname());
		 userAddress.setText(userRecordSelected.getUserAddress());
		 userPhone1.setText(userRecordSelected.getUserPhone1());
		 userEmail.setText(userRecordSelected.getUserEmail());
		 userBirthdate.setValue(gf.asLocalDate(userRecordSelected.getUserBirthDate()));
		 userEmploymentBeginDate.setValue(gf.asLocalDate(userRecordSelected.getEmploymentBegin()));
		 userEmploymentEndDate.setValue(gf.asLocalDate(userRecordSelected.getEmploymentEnd()));
		 
		 //
		 List<SysUserRol> userRoles = userRecordSelected.getSysUserRols();
		 if (userRoles.size() > 0){
			 System.out.println("Roles asignados = "+userRoles.size());
			 SysRole userRoleValue = userRoles.get(0).getSysRole();
			 
			 userRole.getSelectionModel().select(userRoleValue.getRolCode());
		 }else{
			 System.out.println("Roles asignados = "+userRoles.size());
			 userRole.getSelectionModel().select(null);
			 
		 }
	}

	public void defaultModeEnabled(){
		 userCode.setEditable(true);
		 userPassword.setEditable(false);
		 userName.setEditable(true);
		 userLastname.setEditable(true);
		 userAddress.setEditable(false);
		 userPhone1.setEditable(false);
		 userEmail.setEditable(false);
//		 userBirthdate.setEditable(false);
//		 userEmploymentBeginDate.setEditable(false);
//		 userEmploymentEndDate.setEditable(false);
		 userBirthdate.setDisable(true);
		 userEmploymentBeginDate.setDisable(true);
		 userEmploymentEndDate.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		 userCode.setEditable(true);
		 userPassword.setEditable(false);
		 userName.setEditable(true);
		 userLastname.setEditable(true);
		 userAddress.setEditable(false);
		 userPhone1.setEditable(false);
		 userEmail.setEditable(false);
//		 userBirthdate.setEditable(false);
//		 userEmploymentBeginDate.setEditable(false);
//		 userEmploymentEndDate.setEditable(false);
		 userBirthdate.setDisable(true);
		 userEmploymentBeginDate.setDisable(true);
		 userEmploymentEndDate.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		userCode.setEditable(true);
		 userPassword.setEditable(true);
		 userName.setEditable(true);
		 userLastname.setEditable(true);
		 userAddress.setEditable(true);
		 userPhone1.setEditable(true);
		 userEmail.setEditable(true);
//		 userBirthdate.setEditable(true);
//		 userEmploymentBeginDate.setEditable(true);
//		 userEmploymentEndDate.setEditable(true);
		 userBirthdate.setDisable(false);
		 userEmploymentBeginDate.setDisable(false);
		 userEmploymentEndDate.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}

	public void editModeEnabled(){
		userCode.setEditable(true);
		 userPassword.setEditable(true);
		 userName.setEditable(true);
		 userLastname.setEditable(true);
		 userAddress.setEditable(true);
		 userPhone1.setEditable(true);
		 userEmail.setEditable(true);
//		 userBirthdate.setEditable(true);
//		 userEmploymentBeginDate.setEditable(true);
//		 userEmploymentEndDate.setEditable(true);
		 userBirthdate.setDisable(false);
		 userEmploymentBeginDate.setDisable(false);
		 userEmploymentEndDate.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
		 }
	
	public void newModeEnabled(){
		 userCode.setEditable(true);
		 userPassword.setEditable(true);
		 userName.setEditable(true);
		 userLastname.setEditable(true);
		 userAddress.setEditable(true);
		 userPhone1.setEditable(true);
		 userEmail.setEditable(true);
//		 userBirthdate.setEditable(true);
//		 userEmploymentBeginDate.setEditable(true);
//		 userEmploymentEndDate.setEditable(true);
		 userBirthdate.setDisable(false);
		 userEmploymentBeginDate.setDisable(false);
		 userEmploymentEndDate.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblName.setTextFill(Color.web("#000000"));
			lblLastname.setTextFill(Color.web("#000000"));
			lblBegindate.setTextFill(Color.web("#000000"));
			lblUsercode.setTextFill(Color.web("#000000"));
			lblUserPassword.setTextFill(Color.web("#000000"));
			lblUserRole.setTextFill(Color.web("#000000"));
		}

		public void alterComboBoxProperties(){
			userRole.setCellFactory(new Callback<ListView<SysRole>,ListCell<SysRole>>(){
			     @Override
	            public ListCell<SysRole> call(ListView<SysRole> p) {
	                 
	                final ListCell<SysRole> cell = new ListCell<SysRole>(){
	 
	                    @Override
	                    protected void updateItem(SysRole t, boolean bln) {
	                        super.updateItem(t, bln);
	                         
	                        if(t != null){
	                            setText(t.getRolCode() +" - "+t.getRolName());
	                        }else{
	                            setText(null);
	                        }
	                    }
	  
	                };
	                 
	                return cell;
	            }
	        });
		}
}
