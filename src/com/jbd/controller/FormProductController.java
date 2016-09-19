package com.jbd.controller;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgMeasureUnitManagement;
import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.hibernate.interfaces.ICtgSupplierManagement;
import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.model.CtgMeasureUnit;
import com.jbd.model.CtgProductType;
import com.jbd.model.CtgSupplier;
import com.jbd.model.RestProduct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class FormProductController {

	private Main mainApp;
	private ApplicationContext context;
	private RestProduct productRecord;
	private RestProduct productRecordSelected = new RestProduct();
	private String userEntry ;
	private GeneralFunctions gf = new GeneralFunctions();

	@Autowired
	private IRestProductManagement manageRestProduct;
	@Autowired
	private ICtgSupplierManagement manageSupplier;
	@Autowired
	private ICtgProductTypeManagement manageProductType;
	@Autowired
	private ICtgMeasureUnitManagement manageMeasureUnit;
	// Declaracion Labels
	@FXML
	private Label lblProductName, lblProductType, lblProductMeasure;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, clearBtn;
	// Declaracion Campos
	@FXML
	private TextField productName, productAvailability, productTreshold, productWaste;
	@FXML
	private TextArea productDescription;

	// Declaracion ComboBox
	@FXML
	private ComboBox productType, productMeasure, productSupplier;
	@FXML
	private ObservableList<CtgProductType> productTypeData = FXCollections.observableArrayList();
	@FXML
	private ObservableList<CtgMeasureUnit> productMeasureData = FXCollections.observableArrayList();
	@FXML
	private ObservableList<CtgSupplier> supplierData = FXCollections.observableArrayList();

	// Declaracion Tablas
	@FXML
	private TableView<RestProduct> productList = new TableView<RestProduct>();;
	@FXML
	private ObservableList<RestProduct> productData = FXCollections.observableArrayList();
	@FXML
	private TableColumn productNameColumn = new TableColumn("productNameColumn");
	@FXML
	private TableColumn productTypeColumn = new TableColumn("productTypeColumn");
	@FXML
	private TableColumn productIdColumn = new TableColumn("productIdColumn");

	// Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {
		int productCodeSelected = productList.getSelectionModel().getSelectedItem().getProductId();
		// productRecordSelected =
		// manageSupplier.findRestProduct(supplierCodeSelected);
		loadRecordInformation(productCodeSelected);
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
			if (productRecordSelected.getProductId() == 0) {
				newRecord = true;
			}
			productRecordSelected.setEntryDate(new Date());
			productRecordSelected.setEntryUser(userEntry);
			productRecordSelected.setProductDescription(productDescription.getText());
			;
			productRecordSelected.setProductName(productName.getText());
			productRecordSelected.setProductQtyTreshold(Float.parseFloat(productTreshold.getText()));
			productRecordSelected.setProductWaste(Float.parseFloat(productWaste.getText()));
			productRecordSelected.setCtgProductType((CtgProductType) productType.getValue());
			productRecordSelected.setCtgMeasureUnit((CtgMeasureUnit) productMeasure.getValue());
			productRecordSelected.setCtgSupplier((CtgSupplier) productSupplier.getValue());

			productRecord = new RestProduct();
			if (newRecord) {
				System.out.println("NUEVO");
				productRecord = manageRestProduct.insertRestProduct(productRecordSelected);

			} else {
				System.out.println("UPDATE");
				productRecord = manageRestProduct.updateRestProduct(productRecordSelected);
			}
			if (productRecord == null) {
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

	public FormProductController() {
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
		productRecordSelected = new RestProduct();

		productDescription.setText("");
		productName.setText("");
		productAvailability.setText("");
		productTreshold.setText("");
		productWaste.setText("");
		productType.getSelectionModel().select(null);
		productMeasure.getSelectionModel().select(null);
		productSupplier.getSelectionModel().select(null);

	}

	public String validateRecord() {
		defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	


		if (productName.getText() == null || productName.getText().isEmpty()) {
			errorMessage.append(messageErrorNumber+"-"+"El campo nombre es obligatorio.\n");
			messageErrorNumber++;
			lblProductName.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		}
		if (productType.getValue() == null) {
			errorMessage.append(messageErrorNumber+"-"+"El campo tipo es obligatorio.\n");
			messageErrorNumber++;
			lblProductType.setTextFill(Color.web("#ff0000"));

		}
		if (productMeasure.getValue() == null) {
			errorMessage.append(messageErrorNumber+"-"+"El campo unidad de medida es obligatorio.\n");
			messageErrorNumber++;
			lblProductMeasure.setTextFill(Color.web("#ff0000"));

		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;
	}

	public void refreshList() {

		productData.clear();
		productList.getColumns().clear();
		productNameColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productName"));
		productTypeColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productTypeText"));
		productIdColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productIdColumn"));
		List<RestProduct> list = manageRestProduct.findAll();
		for (RestProduct u : list) {
			u.setProductTypeText(u.getCtgProductType().getTypeName());
			productData.add(u);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		productList.setItems(productData);

		productList.getColumns().addAll(productNameColumn, productTypeColumn, productIdColumn);

	}

	public void refreshListOnSearch() {

		productData.clear();
		productList.getColumns().clear();
		productNameColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productName"));
		productTypeColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productTypeText"));
		productIdColumn.setCellValueFactory(new PropertyValueFactory<RestProduct, String>("productIdColumn"));
		int productTypeId = 0;
		int productSupplierId = 0;
		if (productType.getValue() != null) {
			productTypeId = ((CtgProductType) productType.getValue()).getProductTypeId();
		}
		if (productSupplier.getValue() != null) {
			productSupplierId = ((CtgSupplier) productSupplier.getValue()).getSupplierId();

		}

		List<RestProduct> list = manageRestProduct.findProductByExample(productName.getText(), productTypeId,
				productSupplierId);
		for (RestProduct u : list) {
			u.setProductTypeText(u.getCtgProductType().getTypeName());
			productData.add(u);

		}

		productList.setItems(productData);

		productList.getColumns().addAll(productNameColumn, productTypeColumn, productIdColumn);

	}

	public void loadRecordInformation(int productCodePrm) {
		productRecordSelected = manageRestProduct.findRestProduct(productCodePrm);
		productName.setText(productRecordSelected.getProductName());
		productDescription.setText(productRecordSelected.getProductDescription());
		productAvailability.setText(Float.toString(productRecordSelected.getProductQtyAvailability()));
		productTreshold.setText(Float.toString(productRecordSelected.getProductQtyTreshold()));
		productWaste.setText(Float.toString(productRecordSelected.getProductWaste()));

		// productRecordSelected
		if (productRecordSelected.getCtgProductType() != null) {
			productType.getSelectionModel().select(productRecordSelected.getCtgProductType());
		} else {
			productType.getSelectionModel().select(null);

		}

		if (productRecordSelected.getCtgMeasureUnit() != null) {
			productMeasure.getSelectionModel().select(productRecordSelected.getCtgMeasureUnit());
		} else {
			productMeasure.getSelectionModel().select(null);

		}
		if (productRecordSelected.getCtgSupplier() != null) {
			productSupplier.getSelectionModel().select(productRecordSelected.getCtgSupplier());
		} else {
			productSupplier.getSelectionModel().select(null);

		}

	}

	public void defaultModeEnabled() {
		productName.setEditable(true);
		productDescription.setEditable(false);
		productTreshold.setEditable(false);
		productWaste.setEditable(false);
		productType.setDisable(false);
		productMeasure.setDisable(true);
		productSupplier.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		clearBtn.setDisable(true);
		saveBtn.setDisable(true);

	}

	public void initModeEnabled() {
		productName.setEditable(true);
		productDescription.setEditable(false);
		productTreshold.setEditable(false);
		productWaste.setEditable(false);
		productType.setDisable(false);
		productMeasure.setDisable(true);
		productSupplier.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
	}

	public void rowSelectedModeEnabled() {
		productName.setEditable(true);
		productDescription.setEditable(true);
		productTreshold.setEditable(true);
		productWaste.setEditable(true);
		productType.setDisable(false);
		productMeasure.setDisable(false);
		productSupplier.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
	}

	public void editModeEnabled() {

		productName.setEditable(true);
		productDescription.setEditable(true);
		productTreshold.setEditable(true);
		productWaste.setEditable(true);
		productType.setDisable(false);
		productMeasure.setDisable(false);
		productSupplier.setDisable(false);

		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void newModeEnabled() {

		productName.setEditable(true);
		productDescription.setEditable(true);
		productTreshold.setEditable(true);
		productWaste.setEditable(true);
		productType.setDisable(false);
		productMeasure.setDisable(false);
		productSupplier.setDisable(false);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void defaultLabel() {
		lblProductMeasure.setTextFill(Color.web("#000000"));
		lblProductName.setTextFill(Color.web("#000000"));
		lblProductType.setTextFill(Color.web("#000000"));

	}

	public void refreshComboBoxList() {

		productTypeData.clear();
		List<CtgProductType> list = manageProductType.findAll();
		for (CtgProductType r : list) {
			productTypeData.add(r);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		productType.setItems(productTypeData);

		productMeasureData.clear();
		List<CtgMeasureUnit> measureList = manageMeasureUnit.findAll();
		for (CtgMeasureUnit r : measureList) {
			productMeasureData.add(r);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		productMeasure.setItems(productMeasureData);

		supplierData.clear();
		List<CtgSupplier> supplierList = manageSupplier.findAll();
		for (CtgSupplier r : supplierList) {
			supplierData.add(r);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		productSupplier.setItems(supplierData);

	}

	public void alterComboBoxProperties() {
		productType.setCellFactory(new Callback<ListView<CtgProductType>, ListCell<CtgProductType>>() {
			@Override
			public ListCell<CtgProductType> call(ListView<CtgProductType> p) {

				final ListCell<CtgProductType> cell = new ListCell<CtgProductType>() {

					@Override
					protected void updateItem(CtgProductType t, boolean bln) {
						super.updateItem(t, bln);

						if (t != null) {
							setText(t.getProductTypeId() + " - " + t.getTypeName());
						} else {
							setText(null);
						}
					}

				};

				return cell;
			}
		});

		productMeasure.setCellFactory(new Callback<ListView<CtgMeasureUnit>, ListCell<CtgMeasureUnit>>() {
			@Override
			public ListCell<CtgMeasureUnit> call(ListView<CtgMeasureUnit> p) {

				final ListCell<CtgMeasureUnit> cell = new ListCell<CtgMeasureUnit>() {

					@Override
					protected void updateItem(CtgMeasureUnit t, boolean bln) {
						super.updateItem(t, bln);

						if (t != null) {
							setText(t.getMeasureUni() + " - " + t.getMeasureName());
						} else {
							setText(null);
						}
					}

				};

				return cell;
			}
		});

		productSupplier.setCellFactory(new Callback<ListView<CtgSupplier>, ListCell<CtgSupplier>>() {
			@Override
			public ListCell<CtgSupplier> call(ListView<CtgSupplier> p) {

				final ListCell<CtgSupplier> cell = new ListCell<CtgSupplier>() {

					@Override
					protected void updateItem(CtgSupplier t, boolean bln) {
						super.updateItem(t, bln);

						if (t != null) {
							setText(t.getSupplierId() + " - " + t.getSupplierName());
						} else {
							setText(null);
						}
					}

				};

				return cell;
			}
		});

	}

	public String getUserEntry() {
		return userEntry;
	}

	public void setUserEntry(String userEntry) {
		this.userEntry = userEntry;
	}

		
}
