package com.jbd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.utils.Crypter;
import com.jbd.utils.JBDModule;
import com.jbd.hibernate.interfaces.ISysUserManagement;
import com.jbd.model.SysRole;
import com.jbd.model.SysUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import application.Main;

public class FormLoginController {

	private Main mainApp;
	private ApplicationContext context;
	private JBDModule selectedModule;
	@Autowired
	private ISysUserManagement manageUser;


	//Declaracion Labels
	@FXML
	private Label lblUser, lblPassword, lblErrorMessage;
	//Declaracion ComboBox
	@FXML
	private ComboBox<JBDModule> cmbModulex;
	@FXML
	private ChoiceBox<JBDModule> cmbModules;
	//Declaracion Botones
	@FXML
	private Button btnAccept , btnCancel;
	//Declaracion Campos
	@FXML
	private TextField txtUser;
	@FXML
	private PasswordField txtPassword;

	//Declaracion de acciones
	@FXML
	public void onCancel(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	public void onAccept(MouseEvent event) {
		String localUser  		= this.txtUser.getText();
		String localPassword	= this.txtPassword.getText();

		SysUser user			= null;
		Crypter crypter			= new Crypter();

		try{
			user = manageUser.findSysUser(localUser);

			if(user == null){
				lblErrorMessage.setText("El usuario no existe");
			}else{

//				System.out.println("User: " + user.getUserCode());
//				System.out.println("Pass: " + user.getUserPassword() + "  ["+crypter.decrypt(user.getUserPassword())+"]");
//				System.out.println("Name: " + user.getUserName());
//				System.out.println("Lastname: " + user.getUserLastname());

				if(crypter.crypt(localPassword).equals(user.getUserPassword())){
//					lblErrorMessage.setText("Exito");
					this.selectedModule = this.cmbModules.getSelectionModel().getSelectedItem();
					Stage stage = (Stage) btnCancel.getScene().getWindow();
					stage.fireEvent(
		                    new WindowEvent(
		                    		stage,
		                            WindowEvent.WINDOW_CLOSE_REQUEST
		                        ));
				}else{
					lblErrorMessage.setText("El usuario y/o contraseña ingresado es invalido");
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}


	}


	public FormLoginController() {
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
		setData();
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void setCmbModule(ChoiceBox<JBDModule> cmb){
		this.cmbModules = cmb;
	}

	public ChoiceBox<JBDModule> getCmbModule(){
		return this.cmbModules;
	}

	public JBDModule getSelectedModule() {
		return selectedModule;
	}

	public Button getBtnAccept() {
		return btnAccept;
	}

	public void setBtnAccept(Button btnAccept) {
		this.btnAccept = btnAccept;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public void setSelectedModule(JBDModule selectedModule) {
		this.selectedModule = selectedModule;
	}

	@SuppressWarnings("rawtypes")
	public void setData(){

//		if(this.cmbModule.getItems().size() > 0){
//			this.cmbModule.getItems().clear();
//		}
//		this.cmbModule.getItems().addAll("JBDPos Admin","JBDPos Restaurante");

		List<JBDModule> list = new ArrayList<JBDModule>();
		JBDModule moduleA = new JBDModule();
		JBDModule moduleB = new JBDModule();

		/** Change when a list of options/modules exists from de DB **/
		moduleA.setModuleId(1);
		moduleA.setModuleName("JBDPos Admin");
		list.add(moduleA);

		moduleB.setModuleId(2);
		moduleB.setModuleName("JBDPos Restaurante");
		list.add(moduleB);

        ObservableList<JBDModule> obList = FXCollections.observableList(list);
        this.cmbModules.setConverter(new JBDModuleConverter());
        this.cmbModules.getItems().clear();
        this.cmbModules.setItems(obList);
        this.cmbModules.getSelectionModel().selectFirst();


	}

//	public void alterComboBoxProperties(){
//		this.cmbModules.setCellFactory(new Callback<ListView<JBDModule>,ListCell<JBDModule>>(){
//		     @Override
//            public ListCell<JBDModule> call(ListView<JBDModule> p) {
//
//                final ListCell<JBDModule> cell = new ListCell<JBDModule>(){
//
//                    @Override
//                    protected void updateItem(JBDModule t, boolean bln) {
//                        super.updateItem(t, bln);
//
//                        if(t != null){
//                            setText(t.getModuleName());
//                        }else{
//                            setText(null);
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
//	}

	class JBDModuleConverter extends StringConverter<JBDModule> {

		public JBDModule fromString(String string) {
			// convert from a string to a myClass instance
			return (new JBDModule());
		}

		public String toString(JBDModule module) {
			// convert a myClass instance to the text displayed in the choice box
			return module.getModuleName();
		}
	}

}
