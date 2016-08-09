package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestInformationManagement;


import com.jbd.model.RestInformation;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import application.Main;

public class FormRestaurantController {

	private Main mainApp;
	private ApplicationContext context;
	private RestInformation restaurantRecord;
	private RestInformation restaurantRecordSelected = new RestInformation();
	private String userEntry = "Douglas";
	
	@Autowired
	private IRestInformationManagement manageRestaurant;
	//Declaracion Labels
	@FXML
	private Label lblRestaurantName,lblRestaurantNIT;

	//Declaracion Botones
	@FXML
	private Button editBtn , clearBtn   ;
	//Declaracion Campos
	@FXML
	private TextField restaurantName,restaurantNIT,restaurantPhone1,restaurantEmail;
	@FXML
	private TextArea restaurantAddress;
	

	
	
	//Declaracion de acciones



	@FXML
	public void onClear(MouseEvent event) {				
		loadRecordInformation();
	}
		@FXML
		public void onSave(MouseEvent event) {
			
				String error = validateRecord();
				System.out.println(error);
				if (error == null){
					boolean newRecord = false;
					if (restaurantRecordSelected.getInformationId() ==0 ){
						newRecord = true;
					}
					restaurantRecordSelected.setRestAddress(restaurantAddress.getText());;
					restaurantRecordSelected.setRestNIT(restaurantNIT.getText());
					restaurantRecordSelected.setRestEmail(restaurantEmail.getText());
					restaurantRecordSelected.setRestPhone1(restaurantPhone1.getText());
					restaurantRecordSelected.setRestName(restaurantName.getText());
					
					restaurantRecord = new RestInformation();
					if (newRecord){
						System.out.println("NUEVO");
						restaurantRecord = manageRestaurant.insertRestInformation(restaurantRecordSelected);						

					}else{
						System.out.println("UPDATE");
						restaurantRecord =  manageRestaurant.updateRestInformation(restaurantRecordSelected);						
					}
					if (restaurantRecord==null){
						System.out.println("ERROR AL GUARDAR");
					}else{
						System.out.println("EXITO AL GUARDAR");
						if (newRecord) {	
							JOptionPane.showMessageDialog(null,
								"Registro almacenado exitosamente");
						}else{
							JOptionPane.showMessageDialog(null,
									"Registro actualizado exitosamente");
								
						}
					}
				}else{
					JOptionPane.showMessageDialog(null,
							"Los campos marcados en rojo son obligatorios y presentan errores.\n "
							+ "A continuacion se muestra el detalle de errores:\n" + error);

				}
			
		}

		

		public FormRestaurantController() {
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
        
		loadRecordInformation();
    }

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	

		if (restaurantName.getText() == null || restaurantName.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre de proveedor es obligatorio.\n");
			messageErrorNumber++;
			lblRestaurantName.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (restaurantNIT.getText()==null || restaurantNIT.getText().isEmpty()){
			errorMessage = errorMessage.append(messageErrorNumber+"-"+"El campo apellido del contacto es obligatorio.\n");
			messageErrorNumber++;
			lblRestaurantNIT.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;			
	}
	

	
	public void loadRecordInformation(){
		restaurantRecordSelected = manageRestaurant.findFirst();
		 restaurantName.setText(restaurantRecordSelected.getRestName());
		 restaurantAddress.setText(restaurantRecordSelected.getRestAddress());
		 restaurantEmail.setText(restaurantRecordSelected.getRestEmail());
		 restaurantPhone1.setText(restaurantRecordSelected.getRestPhone1());
		 restaurantNIT.setText(restaurantRecordSelected.getRestNIT());
		 
			}

	
		public void defaultLabel(){
			lblRestaurantName.setTextFill(Color.web("#000000"));
			lblRestaurantNIT.setTextFill(Color.web("#000000"));
			
		}

		
}
