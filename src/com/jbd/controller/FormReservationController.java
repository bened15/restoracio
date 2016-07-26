package com.jbd.controller;


import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.IAdmCustomerManagement;
import com.jbd.hibernate.interfaces.IAdmReservationManagement;
import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.model.AdmCustomer;
import com.jbd.model.AdmReservation;
import com.jbd.model.CtgPaymentMethod;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import application.Main;

public class FormReservationController {

	private Main mainApp;
	private ApplicationContext context;
	private AdmReservation reservationRecord;
	private AdmReservation reservationRecordSelected = new AdmReservation();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	
	@Autowired
	private IAdmReservationManagement manageReservation;
	@Autowired
	private IAdmCustomerManagement manageCustomer;
	@Autowired
	private ICtgPaymentMethodManagement managePaymentMethod;
	//Declaracion Labels
	@FXML
	private Label lblReservationCustomer,lblReservationDate,lblReservationAdvancePayment,lblReservationPaymentMethod;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn ,searchCustomerBtn  ;
	//Declaracion Campos
	@FXML
	private TextField reservationAdvancePayment,customerName,customerLastname;
	@FXML
	private TextArea reservationComments;
	@FXML
	private ComboBox reservationCustomer,reservationPaymentMethod;
	@FXML
	private ObservableList<AdmCustomer> customerData = FXCollections.observableArrayList();
	private ObservableList<CtgPaymentMethod> paymentMethodData = FXCollections.observableArrayList();

	@FXML
	private DatePicker reservationDate = new DatePicker();
	//Declaracion Tablas
	@FXML
	private TableView<AdmReservation> reservationList = new TableView<AdmReservation>();  ;
	@FXML
	private ObservableList <AdmReservation> reservationData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn reservationDateColumn = new TableColumn("reservationDateColumn");
	@FXML
	private TableColumn reservationCustomerColumn = new TableColumn("reservationCustomerColumn");
	@FXML
	private TableColumn reservationAdvancePaymentColumn = new TableColumn("reservationAdvancePaymentColumn");

	
	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {	
		System.out.println("Buscando");
			refreshListOnSearch();		
	}

	@FXML
	public void onSearchClient(MouseEvent event) {				
		customerData.clear();
		List<AdmCustomer> customerList = manageCustomer.findByCustomerExample(customerName.getText(), customerLastname.getText());
		for (AdmCustomer r : customerList) {
			customerData.add(r);
		}
		reservationCustomer.setItems(customerData);
		

	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		int reservationCodeSelected  = reservationList.getSelectionModel().getSelectedItem().getReservationId();
		System.out.println("SELECTED ROW "+reservationCodeSelected);
		//reservationRecordSelected = manageReservation.findAdmReservation(reservationCodeSelected);
    	loadRecordInformation(reservationCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {				
			resetValues();
			newModeEnabled();
			refreshComboBoxList();
	}
	@FXML
	public void onClear(MouseEvent event) {				
			initModeEnabled();
			resetValues();
			refreshComboBoxList();
	}
		@FXML
		public void onSave(MouseEvent event) {
			
				String error = validateRecord();
				System.out.println(error);
				if (error == null){
					boolean newRecord = false;
					if (reservationRecordSelected.getReservationId() ==0 ){
						newRecord = true;
					}
//					reservationRecordSelected.setAdmCustomer(manageCustomer.findAdmCustomer(((AdmCustomer) reservationCustomer.getValue()).getCustomerId()));
					reservationRecordSelected.setAdmCustomer((AdmCustomer) reservationCustomer.getValue());
					reservationRecordSelected.setAdvancePaymentAmmount(gf.asFloat(reservationAdvancePayment.getText()));
					reservationRecordSelected.setReservationDate(gf.asDate(reservationDate.getValue()));
					reservationRecordSelected.setComments(reservationComments.getText());
					reservationRecordSelected.setCtgPaymentMethod((CtgPaymentMethod) reservationPaymentMethod.getValue());
					reservationRecordSelected.setEntryDate(new Date());
					reservationRecordSelected.setEntryUser(userEntry);
					reservationRecord = new AdmReservation();

					if (newRecord){
						System.out.println("NUEVO");
						reservationRecord = manageReservation.insertAdmReservation(reservationRecordSelected);						

					}else{
						System.out.println("UPDATE");
						reservationRecord =  manageReservation.updateAdmReservation(reservationRecordSelected);						
					}
					if (reservationRecord==null){
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

		

		public FormReservationController() {
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
		// loadComboBox
		// addListeners();
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
		reservationRecordSelected = new AdmReservation();
		 customerName.setText("");
		 customerLastname.setText("");

		 reservationCustomer.getSelectionModel().select(null);
		 reservationPaymentMethod.getSelectionModel().select(null);
		 reservationAdvancePayment.setText("");
		 reservationDate.setValue(null);
		 reservationComments.setText("");
		 		 
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;		

		if (reservationCustomer.getValue()==null){
			errorMessage.append(messageErrorNumber+"-"+"El campo cliente  es obligatorio.\n");
			messageErrorNumber++;
			lblReservationCustomer.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (reservationDate.getValue()==null){
			errorMessage.append(messageErrorNumber+"-"+"El campo fecha  es obligatorio.\n");
			messageErrorNumber++;
			lblReservationDate.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (reservationPaymentMethod.getValue()==null){
			errorMessage.append(messageErrorNumber+"-"+"El campo tipo de pago  es obligatorio.\n");
			messageErrorNumber++;
			lblReservationPaymentMethod.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		if (reservationAdvancePayment.getText()==null || reservationAdvancePayment.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo adelanto es obligatorio.\n");
			messageErrorNumber++;
			lblReservationAdvancePayment.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}
		
		return errorString;			
	}
	
	public void refreshList(){
		
		
		reservationData.clear();
		reservationList.getColumns().clear();
		reservationDateColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("reservationDate"));
		reservationCustomerColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("customerNameText"));
		reservationAdvancePaymentColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("advancePaymentAmmount"));
		List<AdmReservation> list = manageReservation.findAll();
		for(AdmReservation u : list){
			u.setCustomerNameText(u.getAdmCustomer().getCustomerLastname()+", " +u.getAdmCustomer().getCustomerName());
			reservationData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		reservationList.setItems(reservationData);
		
		reservationList.getColumns().addAll(reservationDateColumn,reservationCustomerColumn,reservationAdvancePaymentColumn);
		

	}

	public void refreshListOnSearch(){
		
		reservationData.clear();
		reservationList.getColumns().clear();
		reservationDateColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("reservationDate"));
		reservationCustomerColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("customerNameText"));
		reservationAdvancePaymentColumn.setCellValueFactory(new PropertyValueFactory<AdmReservation, String>("advancePaymentAmmount"));
//		List<AdmReservation> list = manageReservation.findAll();
		List<AdmReservation> list = manageReservation.findByReservationExample(customerName.getText(), customerLastname.getText(), gf.asDate(reservationDate.getValue()));
		for(AdmReservation u : list){
			u.setCustomerNameText(u.getAdmCustomer().getCustomerLastname()+", " +u.getAdmCustomer().getCustomerName());
			reservationData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		reservationList.setItems(reservationData);
		
		reservationList.getColumns().addAll(reservationDateColumn,reservationCustomerColumn,reservationAdvancePaymentColumn);
		
		
	

	}


	
	public void loadRecordInformation(int reservationCodePrm){
		reservationRecordSelected = manageReservation.findAdmReservation(reservationCodePrm);
		reservationDate.setValue(gf.asLocalDate(reservationRecordSelected.getReservationDate()));
		reservationAdvancePayment.setText(reservationRecordSelected.getAdvancePaymentAmmount()+"");
		// paymentMethod
		if (reservationRecordSelected.getCtgPaymentMethod() != null) {
			reservationPaymentMethod.getSelectionModel().select(reservationRecordSelected.getCtgPaymentMethod());
		} else {
			reservationPaymentMethod.getSelectionModel().select(null);

		}
		//Customer
		if (reservationRecordSelected.getAdmCustomer() != null) {
			reservationCustomer.getSelectionModel().select(reservationRecordSelected.getAdmCustomer());
		} else {
			reservationCustomer.getSelectionModel().select(null);

		}
		reservationComments.setText(reservationRecordSelected.getComments());
		 
			}

	public void defaultModeEnabled(){
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		reservationCustomer.setDisable(true);
		reservationDate.setDisable(false);
		reservationAdvancePayment.setEditable(false);
		reservationPaymentMethod.setDisable(true);
		reservationComments.setEditable(false);
		searchCustomerBtn.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		reservationCustomer.setDisable(true);
		reservationDate.setDisable(false);
		reservationAdvancePayment.setEditable(false);
		reservationPaymentMethod.setDisable(true);
		reservationComments.setEditable(false);
		searchCustomerBtn.setDisable(true);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		reservationCustomer.setDisable(false);
		reservationDate.setDisable(false);
		reservationAdvancePayment.setEditable(true);
		reservationPaymentMethod.setDisable(false);
		reservationComments.setEditable(true);
	
		searchCustomerBtn.setDisable(false);
		 searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 editBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		reservationCustomer.setDisable(false);
		reservationDate.setDisable(false);
		reservationAdvancePayment.setEditable(true);
		reservationPaymentMethod.setDisable(false);
		reservationComments.setEditable(true);
		
		searchCustomerBtn.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		customerName.setEditable(true);
		customerLastname.setEditable(true);
		reservationCustomer.setDisable(false);
		reservationDate.setDisable(false);
		reservationAdvancePayment.setEditable(true);
		reservationPaymentMethod.setDisable(false);
		reservationComments.setEditable(true);
	
		searchCustomerBtn.setDisable(false);
		 searchBtn.setDisable(true);
		 newBtn.setDisable(true);
		 editBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblReservationCustomer.setTextFill(Color.web("#000000"));
			lblReservationDate.setTextFill(Color.web("#000000"));
			lblReservationPaymentMethod.setTextFill(Color.web("#000000"));
			lblReservationAdvancePayment.setTextFill(Color.web("#000000"));
			
		}

		
		public void refreshComboBoxList() {

			paymentMethodData.clear();
			List<CtgPaymentMethod> list = managePaymentMethod.findAll();
			for (CtgPaymentMethod r : list) {
				paymentMethodData.add(r);
			}
			reservationPaymentMethod.setItems(paymentMethodData);

			customerData.clear();
			List<AdmCustomer> customerList = manageCustomer.findAll();
			for (AdmCustomer r : customerList) {
				customerData.add(r);
			}
			reservationCustomer.setItems(customerData);

		}

		public void alterComboBoxProperties() {
			reservationPaymentMethod.setCellFactory(new Callback<ListView<CtgPaymentMethod>, ListCell<CtgPaymentMethod>>() {
				@Override
				public ListCell<CtgPaymentMethod> call(ListView<CtgPaymentMethod> p) {

					final ListCell<CtgPaymentMethod> cell = new ListCell<CtgPaymentMethod>() {

						@Override
						protected void updateItem(CtgPaymentMethod t, boolean bln) {
							super.updateItem(t, bln);

							if (t != null) {
								setText(t.getPaymentMethodId() + " - " + t.getName());
							} else {
								setText(null);
							}
						}

					};

					return cell;
				}
			});

			reservationCustomer.setCellFactory(new Callback<ListView<AdmCustomer>, ListCell<AdmCustomer>>() {
				@Override
				public ListCell<AdmCustomer> call(ListView<AdmCustomer> p) {

					final ListCell<AdmCustomer> cell = new ListCell<AdmCustomer>() {

						@Override
						protected void updateItem(AdmCustomer t, boolean bln) {
							super.updateItem(t, bln);

							if (t != null) {
								setText(t.getCustomerLastname() + ", " + t.getCustomerName());
							} else {
								setText(null);
							}
						}

					};

					return cell;
				}
			});

		}
		
}
