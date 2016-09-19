package com.jbd.controller;


import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestMenuItemCommentManagement;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemComment;

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

public class FormMenuItemCommentController {

	private Main mainApp;
	private ApplicationContext context;
	private RestMenuItemComment menuItemCommentRecord;
	private RestMenuItemComment menuItemCommentRecordSelected = new RestMenuItemComment();
	private RestMenuItem menuItemSelected = new RestMenuItem();
	private String userEntry;
	
	@Autowired
	private IRestMenuItemCommentManagement manageRestMenuItemComment;
	//Declaracion Labels
	@FXML
	private Label lblMenuItemCommentDescription,lblMenuItemComment;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn   , clearBtn , deleteBtn  , closeBtn;
	//Declaracion Campos
	@FXML
	private TextField menuItemComment;
	
	@FXML
	private TextArea menuItemCommentDescription;


	//Declaracion Tablas

	@FXML
	private TableView<RestMenuItemComment> menuItemCommentList = new TableView<RestMenuItemComment>();  ;
	@FXML
	private ObservableList <RestMenuItemComment> menuItemCommentData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuItemCommentColumn = new TableColumn("menuItemCommentColumn");
	@FXML
	private TableColumn menuItemCommentDescriptionColumn = new TableColumn("menuItemCommentDescriptionColumn");

	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshList();		
	}

	public void setSelectedMenuItem(RestMenuItem item) {	
		menuItemSelected = item;
		//menuItemCommentRecordSelected = manageSupplier.findRestMenuItemComment(supplierCodeSelected);
		loadRecordInformationComments(menuItemSelected);
    	resetValues();
    	initModeEnabled();
	}

	@FXML
	public void getSelectedRowProductItem(MouseEvent event) {	
		int menuItemCommentCodeSelected  = menuItemCommentList.getSelectionModel().getSelectedItem().getMenuItemCommentId();
		System.out.println("SELECTED ROW MENU ITEM"+menuItemCommentCodeSelected);
		//menuItemCommentRecordSelected = manageSupplier.findRestMenuItemComment(supplierCodeSelected);
    	loadRecordInformation(menuItemCommentCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {	
			resetValues();
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
		withOutError = manageRestMenuItemComment.deleteRestMenuItemComment(menuItemCommentRecordSelected);						

		if (!withOutError){
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
					if (menuItemCommentRecordSelected.getMenuItemCommentId() ==0 ){
						newRecord = true;
					}
					menuItemCommentRecordSelected.setRestMenuItem(menuItemSelected);
					menuItemCommentRecordSelected.setComment(menuItemComment.getText());
					menuItemCommentRecordSelected.setCommentDescription(menuItemCommentDescription.getText());
					
					menuItemCommentRecord = new RestMenuItemComment();
					if (newRecord){
						System.out.println("NUEVO");
						menuItemCommentRecord = manageRestMenuItemComment.insertRestMenuItemComment(menuItemCommentRecordSelected);						
					}else{
						System.out.println("UPDATE");
						menuItemCommentRecord =  manageRestMenuItemComment.updateRestMenuItemComment(menuItemCommentRecordSelected);						
					}
					if (menuItemCommentRecord==null){
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

		

		public FormMenuItemCommentController() {
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
		menuItemCommentRecordSelected = new RestMenuItemComment();		
		menuItemCommentDescription.setText("");
		menuItemComment.setText("");
		 		 
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (menuItemComment.getText() == null || menuItemComment.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo comentario es obligatorio.\n");
			messageErrorNumber++;				
			lblMenuItemComment.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		
		return errorString;			
	}
	
	
	public void refreshList(){
		
		
		menuItemCommentData.clear();
		menuItemCommentList.getColumns().clear();
		menuItemCommentColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItemComment, String>("comment"));
		menuItemCommentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItemComment, String>("commentDescription"));
		List<RestMenuItemComment> list = manageRestMenuItemComment.findByMenuItem(menuItemSelected);
		for(RestMenuItemComment u : list){
			menuItemCommentData.add(u);
		}
		
		menuItemCommentList.setItems(menuItemCommentData);
		
		menuItemCommentList.getColumns().addAll(menuItemCommentColumn,menuItemCommentDescriptionColumn)		;

	}
	

	public void loadRecordInformationComments(RestMenuItem menuItem){
		refreshList();
		
	}
	
	public void loadRecordInformation(int menuItemCommentCodePrm){
		menuItemCommentRecordSelected = manageRestMenuItemComment.findRestMenuItemComment(menuItemCommentCodePrm);
		menuItemCommentDescription.setText(menuItemCommentRecordSelected.getCommentDescription()); 
		menuItemComment.setText(menuItemCommentRecordSelected.getComment());

			}

	public void defaultModeEnabled(){
		menuItemCommentDescription.setEditable(false);
		menuItemComment.setEditable(false);
		 newBtn.setDisable(false);
		 clearBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		menuItemCommentDescription.setEditable(false);
		menuItemComment.setEditable(false);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 deleteBtn.setDisable(true);
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		menuItemCommentDescription.setEditable(true);
		menuItemComment.setEditable(true);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		menuItemCommentDescription.setEditable(true);
		menuItemComment.setEditable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 deleteBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		menuItemCommentDescription.setEditable(true);
		menuItemComment.setEditable(true);
		 deleteBtn.setDisable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblMenuItemComment.setTextFill(Color.web("#000000"));
			lblMenuItemCommentDescription.setTextFill(Color.web("#000000"));
			
		}

		

		public String getUserEntry() {
			return userEntry;
		}

		public void setUserEntry(String userEntry) {
			this.userEntry = userEntry;
		}

	
		

}
