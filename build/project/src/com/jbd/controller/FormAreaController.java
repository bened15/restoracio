package com.jbd.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.model.RestArea;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import application.Main;

public class FormAreaController {

	private Main mainApp;
	private ApplicationContext context;
	private RestArea restAreaRecord;
	private RestArea restAreaRecordSelected = new RestArea();
	private String userEntry = "Douglas";

	@Autowired
	private IRestAreaManagement manageRestArea;
	// Declaracion Labels
	@FXML
	private Label lblRestArea, lblRestAreaIsSmoker, lblError;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, editBtn, clearBtn;
	// Declaracion Campos
	@FXML
	private ComboBox isSmokingArea;
	@FXML
	private ObservableList<String> smokerData = FXCollections.observableArrayList();
	@FXML
	private TextField areaName;

	// Declaracion Tablas
	@FXML
	private TableView<RestArea> areaList = new TableView<RestArea>();;
	@FXML
	private ObservableList<RestArea> restAreaData = FXCollections.observableArrayList();
	@FXML
	private TableColumn areaNameColumn = new TableColumn("areaNameColumn");
	@FXML
	private TableColumn isSmokerColumn = new TableColumn("isSmokerColumn");
	@FXML
	private TableColumn areaIdColumn = new TableColumn("areaIdColumn");

	// Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {
		int restAreaCodeSelected = areaList.getSelectionModel().getSelectedItem().getAreaId();
		// restAreaRecordSelected =
		// manageRestArea.findRestArea(discountCodeSelected);
		loadRecordInformation(restAreaCodeSelected);
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
			if (restAreaRecordSelected.getAreaId() == 0) {
				newRecord = true;
			}
			restAreaRecordSelected.setAreaName(areaName.getText());
			restAreaRecordSelected.setEntryDate(new Date());
			restAreaRecordSelected.setEntryUser(userEntry);
			if (isSmokingArea.getValue() == null || isSmokingArea.getValue() == "No") {
				restAreaRecordSelected.setIsSmokerArea(0);
			} else {
				restAreaRecordSelected.setIsSmokerArea(1);
			}
			restAreaRecord = new RestArea();
			if (newRecord) {
				System.out.println("NUEVO");
				restAreaRecord = manageRestArea.insertRestArea(restAreaRecordSelected);

			} else {
				System.out.println("UPDATE");
				restAreaRecord = manageRestArea.updateRestArea(restAreaRecordSelected);
			}
			if (restAreaRecord == null) {
				System.out.println("ERROR AL GUARDAR");
			} else {
				System.out.println("EXITO AL GUARDAR");
				resetValues();
				refreshList();
				initModeEnabled();
			}
		}

	}

	public FormAreaController() {
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

		// loadComboBox
		// addListeners();
		refreshComboBoxList();
		refreshList();
		defaultModeEnabled();
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void resetValues() {
		restAreaRecordSelected = new RestArea();
		isSmokingArea.setValue(null);
		areaName.setText("");
	}

	public String validateRecord() {
		defaultLabel();
		String errorMessage = null;

		if (isSmokingArea.getValue() == null) {
			errorMessage = "El campo area de fumadores es obligatorio";
			lblRestAreaIsSmoker.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		}
		if (areaName.getText() == null || areaName.getText().isEmpty()) {
			errorMessage = "El campo nombre es obligatorio.";
			lblRestArea.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		}

		if (errorMessage != null) {
			lblError.setText("Los campos marcados en rojo son obligatorios.");
			lblError.setTextFill(Color.web("#ff0000"));
		}
		return errorMessage;
	}

	public void refreshList() {

		restAreaData.clear();
		areaList.getColumns().clear();
		areaNameColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("areaName"));
		isSmokerColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("isSmokerAreaText"));
		areaIdColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("areaId"));

		List<RestArea> list = manageRestArea.getAllAreas();
		for (RestArea u : list) {
			if (u.getIsSmokerArea() == 0) {
				u.setIsSmokerAreaText("No");
			} else {
				u.setIsSmokerAreaText("Si");
			}
			restAreaData.add(u);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		areaList.setItems(restAreaData);

		areaList.getColumns().addAll(areaNameColumn, isSmokerColumn, areaIdColumn);

	}

	public void refreshListOnSearch() {

		restAreaData.clear();
		areaList.getColumns().clear();
		areaNameColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("areaName"));
		isSmokerColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("isSmokerAreaText"));
		areaIdColumn.setCellValueFactory(new PropertyValueFactory<RestArea, String>("areaId"));

		int smokingArea = 9;
		if (isSmokingArea.getValue() != null){
			if(isSmokingArea.getValue()=="No"){
				smokingArea=0;
			}else{
				smokingArea = 1;
			}
		}
		List<RestArea> list = manageRestArea.findAreaByExample(areaName.getText(),smokingArea);
		for (RestArea u : list) {
			if (u.getIsSmokerArea() == 0) {
				u.setIsSmokerAreaText("No");
			} else {
				u.setIsSmokerAreaText("Si");
			}
			restAreaData.add(u);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		areaList.setItems(restAreaData);

		areaList.getColumns().addAll(areaNameColumn, isSmokerColumn, areaIdColumn);

	}

	public void loadRecordInformation(int restAreaCodePrm) {
		restAreaRecordSelected = manageRestArea.findRestArea(restAreaCodePrm);
		areaName.setText(restAreaRecordSelected.getAreaName());
		if (restAreaRecordSelected.getIsSmokerArea() == 0) {
			isSmokingArea.getSelectionModel().select("No");
		} else {
			isSmokingArea.getSelectionModel().select("Si");

		}

	}

	public void defaultModeEnabled() {
		areaName.setEditable(true);
		isSmokingArea.setEditable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(true);
		saveBtn.setDisable(true);
		lblError.setText(null);

	}

	public void initModeEnabled() {
		areaName.setEditable(true);
		isSmokingArea.setEditable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(true);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
		lblError.setText(null);
	}

	public void rowSelectedModeEnabled() {

		areaName.setEditable(true);
		isSmokingArea.setEditable(true);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		editBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		lblError.setText(null);
	}

	public void editModeEnabled() {

		areaName.setEditable(true);
		isSmokingArea.setEditable(true);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		editBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
		lblError.setText(null);
	}

	public void newModeEnabled() {

		areaName.setEditable(true);
		isSmokingArea.setEditable(true);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		editBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
		lblError.setText(null);
	}

	public void defaultLabel() {
		lblRestArea.setTextFill(Color.web("#000000"));
		lblRestAreaIsSmoker.setTextFill(Color.web("#000000"));
		lblError.setTextFill(Color.web("#000000"));
	}

	public void refreshComboBoxList() {

		smokerData.clear();
		smokerData.add("No");
		smokerData.add("Si");

		isSmokingArea.setItems(smokerData);

	}

}
