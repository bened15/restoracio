package com.jbd.controller;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgDiscountManagement;
import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.model.CtgDiscount;
import com.jbd.model.CtgMenuType;

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

public class FormDiscountController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgDiscount discountRecord;
	private CtgDiscount discountRecordSelected = new CtgDiscount();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	@Autowired
	private ICtgDiscountManagement manageDiscount;
	@Autowired
	private ICtgMenuTypeManagement manageMenuItemType;
	// Declaracion Labels
	@FXML
	private Label lblDiscountName, lblDiscountPercentage, lblDiscountBeginDate, lblDiscountBeginTime, lblDiscountEndDate,lblDiscountEndTime, lblDiscountMenuType;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, editBtn, clearBtn;
	// Declaracion Campos
	@FXML
	private TextField discountName, discountPercentage,discountBeginTime,discountEndTime;
	@FXML
	private TextArea discountDescription;
	@FXML
	private DatePicker discountBeginDate = new DatePicker(), discountEndDate = new DatePicker();
	// Declaracion ComboBox
	@FXML
	private ComboBox discountMenuType;
	@FXML
	private ObservableList<CtgMenuType> menuItemTypeData = FXCollections.observableArrayList();

	// Declaracion Tablas
	@FXML
	private TableView<CtgDiscount> discountList = new TableView<CtgDiscount>();;
	@FXML
	private ObservableList<CtgDiscount> discountData = FXCollections.observableArrayList();
	@FXML
	private TableColumn discountNameColumn = new TableColumn("discountNameColumn");
	@FXML
	private TableColumn discountEndDateColumn = new TableColumn("discountEndDateColumn");
	@FXML
	private TableColumn discountBeginDateColumn = new TableColumn("discountBeginDateColumn");
	@FXML
	private TableColumn discountIdColumn = new TableColumn("discountIdColumn");

	// Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {
		int discountCodeSelected = discountList.getSelectionModel().getSelectedItem().getDiscountId();
		System.out.println("SELECTED ROW " + discountCodeSelected);
		// discountRecordSelected =
		// managediscount.findCtgDiscount(discountCodeSelected);
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
		if (error == null) {
			boolean newRecord = false;
			if (discountRecordSelected.getDiscountId() == 0) {
				newRecord = true;
			}
			System.out.println("Inicia");
			discountRecordSelected.setDiscountDescription(discountDescription.getText());
			discountRecordSelected.setDiscountName(discountName.getText());
			discountRecordSelected.setDiscountPercentage(Integer.parseInt(discountPercentage.getText()));
			discountRecordSelected.setEntryDate(new Date());
			discountRecordSelected.setEntryUser(userEntry);
//			discountRecordSelected.setDiscountValidSince(gf.asDate(discountBeginDate.getValue()));
			discountRecordSelected.setDiscountValidSince(manageDiscount.convertToDate(gf.asDate(discountBeginDate.getValue()),gf.leftPadZero(discountBeginTime.getText())));
			discountRecordSelected.setDiscountValidSinceTime(gf.leftPadZero(discountBeginTime.getText()));
//			discountRecordSelected.setDiscountValidUntil(gf.asDate(discountEndDate.getValue()));
			discountRecordSelected.setDiscountValidUntil(manageDiscount.convertToDate(gf.asDate(discountEndDate.getValue()),gf.leftPadZero(discountEndTime.getText())));
			discountRecordSelected.setDiscountValidUntilTime(gf.leftPadZero(discountEndTime.getText()));
			discountRecordSelected.setCtgMenuType((CtgMenuType) discountMenuType.getValue());
			discountRecord = new CtgDiscount();
			System.out.println(gf.asDate(discountBeginDate.getValue()));
			System.out.println(manageDiscount.convertToDate(gf.asDate(discountBeginDate.getValue()),"1200"));
			if (newRecord) {
				System.out.println("NUEVO");
				discountRecord = manageDiscount.insertCtgDiscount(discountRecordSelected);

			} else {
				System.out.println("UPDATE");
				discountRecord = manageDiscount.updateCtgDiscount(discountRecordSelected);
			}
			if (discountRecord == null) {
				System.out.println("ERROR AL GUARDAR");
			} else {
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

	public FormDiscountController() {
		try {
			context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
			AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
			acbFactory.autowireBean(this);

		} catch (Exception e) {
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

	public void resetValues() {
		discountRecordSelected = new CtgDiscount();
		discountMenuType.getSelectionModel().select(null);
		discountName.setText("");
		discountDescription.setText("");
		discountPercentage.setText("");
		discountBeginDate.setValue(null);
		discountBeginTime.setText("");
		discountEndDate.setValue(null);
		discountEndTime.setText("");
	}

	public String validateRecord() {
		defaultLabel();
		String errorString = null;
		StringBuilder errorMessage = new StringBuilder();
		int messageErrorNumber = 1;		
		int begin_time =0;
		int end_time =0;
		Date begin_date = null;
		Date end_date = null;
		
		if (discountName.getText() == null || discountName.getText().isEmpty()) {
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountName.setTextFill(Color.web("#ff0000"));
		}
		if (discountPercentage.getText() == null || discountPercentage.getText().isEmpty()) {
			errorMessage.append(messageErrorNumber+"-"+"El campo porcentaje es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountPercentage.setTextFill(Color.web("#ff0000"));
		} else {
			if (!gf.validNumber(discountPercentage.getText())) {
				errorMessage.append(messageErrorNumber+"-"+"El campo porcentaje debe ser un numero.\n");
			messageErrorNumber++;
				lblDiscountPercentage.setTextFill(Color.web("#ff0000"));
			}
		}

		if (discountBeginDate.getValue() == null) {
			errorMessage.append(messageErrorNumber+"-"+"El campo de fecha de inicio es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountBeginDate.setTextFill(Color.web("#ff0000"));
		}
		if (discountBeginTime.getText() == null || discountBeginTime.getText().isEmpty()) {
			errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountBeginTime.setTextFill(Color.web("#ff0000"));
		} else {
			if (!gf.validNumber(discountBeginTime.getText())) {
				errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio debe ser un numero.\n");
			messageErrorNumber++;
				lblDiscountBeginTime.setTextFill(Color.web("#ff0000"));
			}else{
				begin_time = gf.asInteger(discountBeginTime.getText());
				if ( begin_time < 0 || begin_time >2359){
					errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio no es una hora valida (Formato HH24Mi).\n");
			messageErrorNumber++;
					lblDiscountBeginTime.setTextFill(Color.web("#ff0000"));					
				}else{
					if (discountBeginDate.getValue() != null){
						begin_date = manageDiscount.convertToDate(gf.asDate(discountBeginDate.getValue()), discountBeginTime.getText());
						if (begin_date == null){
							errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio no es una hora valida (Formato HH24Mi).\n");
			messageErrorNumber++;
							lblDiscountBeginTime.setTextFill(Color.web("#ff0000"));												
						}
					}
				}
			}
		}


		if (discountEndDate.getValue() == null) {
			errorMessage.append(messageErrorNumber+"-"+"El campo de fecha fin es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountEndDate.setTextFill(Color.web("#ff0000"));
		}
		if (discountEndTime.getText() == null || discountEndTime.getText().isEmpty()) {
			errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio es obligatorio.\n");
			messageErrorNumber++;
			lblDiscountEndTime.setTextFill(Color.web("#ff0000"));
		} else {
			if (!gf.validNumber(discountEndTime.getText())) {
				errorMessage.append(messageErrorNumber+"-"+"El campo hora inicio debe ser un numero.\n");
			messageErrorNumber++;
				lblDiscountEndTime.setTextFill(Color.web("#ff0000"));
			}else{
				end_time = gf.asInteger(discountEndTime.getText());
				if ( end_time < 0 || end_time >2359){
					errorMessage.append(messageErrorNumber+"-"+"El campo hora fin no es una hora valida (Formato HH24Mi).\n");
			messageErrorNumber++;
					lblDiscountEndTime.setTextFill(Color.web("#ff0000"));					
				}else{
					if (discountEndDate.getValue() != null){
						end_date = manageDiscount.convertToDate(gf.asDate(discountEndDate.getValue()), discountEndTime.getText());
						if (end_date == null){
							errorMessage.append(messageErrorNumber+"-"+"El campo hora fin no es una hora valida (Formato HH24Mi).\n");
			messageErrorNumber++;
							lblDiscountEndTime.setTextFill(Color.web("#ff0000"));												
						}
					}

				}
			}
		}
		if (begin_date != null && end_date != null){
			if(begin_date.after(end_date)) {
				errorMessage.append(messageErrorNumber+"-"+"La fecha y hora de fin del descuento debe ser mayor que la fecha y hora de inicio del descuento.\n");
			messageErrorNumber++;
				lblDiscountEndDate.setTextFill(Color.web("#ff0000"));
				lblDiscountEndTime.setTextFill(Color.web("#ff0000"));												
				
			}
			
		}

		return errorString;
	}

	public void refreshList() {

		discountData.clear();
		discountList.getColumns().clear();
		discountNameColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountName"));
		discountBeginDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidSince"));
		discountEndDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidUntil"));
		discountIdColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountId"));
		List<CtgDiscount> list = manageDiscount.findAll();
		for (CtgDiscount u : list) {
			CtgDiscount u1 = new CtgDiscount();
			u1.setDiscountId(u.getDiscountId());
			u1.setDiscountName(u.getDiscountName() + " - " + u.getDiscountPercentage() + "%");
			u1.setDiscountValidSince(u.getDiscountValidSince());
			u1.setDiscountValidUntil(u.getDiscountValidUntil());
			discountData.add(u1);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		discountList.setItems(discountData);

		discountList.getColumns().addAll(discountNameColumn, discountBeginDateColumn, discountEndDateColumn,
				discountIdColumn);

	}

	public void refreshListOnSearch() {

		discountData.clear();
		discountList.getColumns().clear();
		discountNameColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountName"));
		discountBeginDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidSince"));
		discountEndDateColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, Date>("discountValidUntil"));
		discountIdColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscount, String>("discountId"));
		List<CtgDiscount> list = manageDiscount.findDiscountByExample(discountName.getText(),
				gf.asDate(discountBeginDate.getValue()));
		for (CtgDiscount u : list) {
			CtgDiscount u1 = new CtgDiscount();
			u1.setDiscountId(u.getDiscountId());
			u1.setDiscountName(u.getDiscountName() + " - " + u.getDiscountPercentage() + "%");
			u1.setDiscountValidSince(u.getDiscountValidSince());
			u1.setDiscountValidUntil(u.getDiscountValidUntil());
			discountData.add(u1);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		discountList.setItems(discountData);

		discountList.getColumns().addAll(discountNameColumn, discountBeginDateColumn, discountEndDateColumn,
				discountIdColumn);

	}

	public void loadRecordInformation(int discountCodePrm) {
		discountRecordSelected = manageDiscount.findCtgDiscount(discountCodePrm);
		discountName.setText(discountRecordSelected.getDiscountName());
		discountDescription.setText(discountRecordSelected.getDiscountDescription());
		discountPercentage.setText(discountRecordSelected.getDiscountPercentage() + "");
		discountBeginDate.setValue(gf.asLocalDate(discountRecordSelected.getDiscountValidSince()));
		discountEndDate.setValue(gf.asLocalDate(discountRecordSelected.getDiscountValidUntil()));
		discountBeginTime.setText(discountRecordSelected.getDiscountValidSinceTime());
		discountEndTime.setText(discountRecordSelected.getDiscountValidUntilTime());
		// menuItemSelected
				if (discountRecordSelected.getCtgMenuType() != null) {
					discountMenuType.getSelectionModel().select(discountRecordSelected.getCtgMenuType());
				} else {
					discountMenuType.getSelectionModel().select(null);

				}

	}

	public void defaultModeEnabled() {
		discountName.setEditable(true);
		discountDescription.setEditable(false);
		discountPercentage.setEditable(false);
		discountBeginDate.setDisable(false);
		discountBeginTime.setEditable(false);
		discountEndDate.setDisable(true);
		discountEndTime.setEditable(false);
		discountMenuType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(true);
		saveBtn.setDisable(true);

	}

	public void initModeEnabled() {
		discountName.setEditable(true);
		discountDescription.setEditable(false);
		discountPercentage.setEditable(false);
		discountBeginDate.setDisable(false);
		discountBeginTime.setEditable(false);
		discountEndDate.setDisable(true);
		discountEndTime.setEditable(false);
		discountMenuType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(true);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
	}

	public void rowSelectedModeEnabled() {

		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountBeginTime.setEditable(true);
		discountEndDate.setDisable(false);
		discountEndTime.setEditable(true);
		discountMenuType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
	}

	public void editModeEnabled() {

		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountBeginTime.setEditable(true);
		discountEndDate.setDisable(false);
		discountEndTime.setEditable(true);
		discountMenuType.setDisable(false);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		editBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void newModeEnabled() {

		discountName.setEditable(true);
		discountDescription.setEditable(true);
		discountPercentage.setEditable(true);
		discountBeginDate.setDisable(false);
		discountBeginTime.setEditable(true);
		discountEndDate.setDisable(false);
		discountEndTime.setEditable(true);
		discountMenuType.setDisable(false);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		editBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void defaultLabel() {
		lblDiscountName.setTextFill(Color.web("#000000"));
		lblDiscountPercentage.setTextFill(Color.web("#000000"));
		lblDiscountBeginDate.setTextFill(Color.web("#000000"));
		lblDiscountEndDate.setTextFill(Color.web("#000000"));
		lblDiscountBeginTime.setTextFill(Color.web("#000000"));
		lblDiscountEndTime.setTextFill(Color.web("#000000"));
		
		//lblDiscountMenuType.setTextFill(Color.web("#000000"));

	}
	

	public void refreshComboBoxList() {

		menuItemTypeData.clear();
		List<CtgMenuType> list = manageMenuItemType.findAll();
		for (CtgMenuType r : list) {
			menuItemTypeData.add(r);
		}
		menuItemTypeData.add(0, null);
		discountMenuType.setItems(menuItemTypeData);

	}

	public void alterComboBoxProperties() {
		discountMenuType.setCellFactory(new Callback<ListView<CtgMenuType>, ListCell<CtgMenuType>>() {
			@Override
			public ListCell<CtgMenuType> call(ListView<CtgMenuType> p) {

				final ListCell<CtgMenuType> cell = new ListCell<CtgMenuType>() {

					@Override
					protected void updateItem(CtgMenuType t, boolean bln) {
						super.updateItem(t, bln);

						if (t != null) {
							setText(t.getMenuTypeId() + " - " + t.getMenuTypeName());
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
