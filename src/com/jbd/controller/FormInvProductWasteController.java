package com.jbd.controller;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.hibernate.interfaces.IInvInventoryWasteManagement;
import com.jbd.hibernate.interfaces.IInvProductItemManagement;
import com.jbd.model.RestProduct;
import com.jbd.model.CtgProductType;
import com.jbd.model.InvInventoryWaste;
import com.jbd.model.InvProductItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import application.Main;

public class FormInvProductWasteController {

	private Main mainApp;
	private ApplicationContext context;
	private InvProductItem invProductItemRecord;
	private InvProductItem invProductItemRecordSelected = new InvProductItem();
	private InvInventoryWaste invProductWasteRecord,invProductWasteNewRecord;
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();

	@Autowired
	private IInvProductItemManagement manageInvProductItem;
	@Autowired
	private IInvInventoryWasteManagement manageInvProductWaste;
	@Autowired
	private IRestProductManagement manageProduct;
	@Autowired
	private ICtgProductTypeManagement manageProductType;
	// Declaracion Labels
	@FXML
	private Label lblProductWaste;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, clearBtn;
	// Declaracion Campos
	@FXML
	private TextField productPrice, productQty, productAvailable, productPurchaseDate, transactionType,productWaste;

	// Declaracion ComboBox
	@FXML
	private ComboBox productType, productName;
	@FXML
	private ObservableList<CtgProductType> productTypeData = FXCollections.observableArrayList();
	@FXML
	private ObservableList<RestProduct> productNameData = FXCollections.observableArrayList();

	// Declaracion Tablas
	@FXML
	private TableView<InvProductItem> invProductList = new TableView<InvProductItem>();;
	@FXML
	private ObservableList<InvProductItem> productData = FXCollections.observableArrayList();
	@FXML
	private TableColumn productNameColumn = new TableColumn("productNameColumn");
	@FXML
	private TableColumn productTypeColumn = new TableColumn("productTypeColumn");
	@FXML
	private TableColumn productPriceColumn = new TableColumn("productPriceColumn");
	@FXML
	private TableColumn productQtyColumn = new TableColumn("productQtyColumn");
	@FXML
	private TableColumn productDateColumn = new TableColumn("productDateColumn");

	// Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {
		int productCodeSelected = invProductList.getSelectionModel().getSelectedItem().getInvProductId();
		System.out.println("SELECTED ROW " + productCodeSelected);
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
			invProductWasteNewRecord = new InvInventoryWaste();
			invProductWasteNewRecord.setEntryDate(new Date());
			invProductWasteNewRecord.setEntryUser(userEntry);
			invProductWasteNewRecord.setInvProductItem(invProductItemRecordSelected);
			invProductWasteNewRecord.setProductQtyWaste(Integer.parseInt(productWaste.getText()));
			
			invProductWasteRecord = new InvInventoryWaste();
				System.out.println("NUEVO");
				invProductWasteRecord = manageInvProductWaste.insertInvInventoryWaste(invProductWasteNewRecord);
				JOptionPane.showMessageDialog(null,
						"Registro almacenado exitosamente");

			if (invProductWasteRecord == null) {
				System.out.println("ERROR AL GUARDAR");
			} else {
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

	public FormInvProductWasteController() {
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
		invProductItemRecordSelected = new InvProductItem();

		productPrice.setText("");
		productQty.setText("");
		productAvailable.setText("");
		productType.getSelectionModel().select(null);
		productName.getSelectionModel().select(null);
		transactionType.setText("");
		productPurchaseDate.setText("");
		productWaste.setText("");
	}

	public String validateRecord() {
		defaultLabel();
		String errorString = null;
		StringBuilder errorMessage = new StringBuilder();
		int messageErrorNumber = 1;

		if (productWaste.getText() == null || productWaste.getText().isEmpty()) {
			
			errorMessage.append(messageErrorNumber+"-"+"El campo desperdicio es obligatorio.\n");
			messageErrorNumber++;
			lblProductWaste.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		} else {
			if (!gf.validNumber(productWaste.getText())) {
				errorMessage.append(messageErrorNumber+"-"+"El campo desperdicio debe ser un numero.\n");
				messageErrorNumber++;
				lblProductWaste.setTextFill(Color.web("#ff0000"));

			}else{
				int waste = gf.asPositiveInteger(productWaste.getText());
				if (waste>0){
					if (waste> invProductItemRecordSelected.getProductQty()){						
						errorMessage.append(messageErrorNumber+"-"+"El campo desperdicio supera la cantidad disponible del producto.\n");
						messageErrorNumber++;
						lblProductWaste.setTextFill(Color.web("#ff0000"));					
					}
				}else{
					errorMessage.append(messageErrorNumber+"-"+"El campo desperdicio debe ser un numero positivo sin decimales.\n");
					messageErrorNumber++;
					lblProductWaste.setTextFill(Color.web("#ff0000"));					
				}
			}
			
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;
	}

	public void refreshList() {
		productData.clear();
		invProductList.getColumns().clear();
		productNameColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductNameText"));
		productTypeColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductTypeText"));
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productPrice"));
		productQtyColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productQty"));
		productDateColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("entryDate"));
		List<InvProductItem> list = manageInvProductItem.findAllOpen();
		for (InvProductItem u : list) {
			u.setRestProductNameText(u.getRestProduct().getProductName());
			u.setRestProductTypeText(u.getRestProduct().getCtgProductType().getTypeName());
			productData.add(u);
		}
		invProductList.setItems(productData);
		invProductList.getColumns().addAll(productNameColumn, productTypeColumn, productPriceColumn, productQtyColumn,
				productDateColumn);
	}

	public void refreshListOnSearch() {
		productData.clear();
		invProductList.getColumns().clear();
		productNameColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductNameText"));
		productTypeColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductTypeText"));
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productPrice"));
		productQtyColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productQty"));
		productDateColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("entryDate"));
		int producTypeId =0;
		int productId =0;
		if(productType.getValue() != null){
			producTypeId = ((CtgProductType) productType.getValue()).getProductTypeId();
		}
		if(productName.getValue() != null){
			productId = ((RestProduct) productName.getValue()).getProductId();
		}
		List<InvProductItem> list = manageInvProductItem.findInvProductItemByExampleOpen(producTypeId,productId);
		for (InvProductItem u : list) {
			u.setRestProductNameText(u.getRestProduct().getProductName());
			u.setRestProductTypeText(u.getRestProduct().getCtgProductType().getTypeName());
			productData.add(u);
		}
		invProductList.setItems(productData);
		invProductList.getColumns().addAll(productNameColumn, productTypeColumn, productPriceColumn, productQtyColumn,
				productDateColumn);
	}

	public void loadRecordInformation(int productCodePrm) {
		invProductItemRecordSelected = manageInvProductItem.findInvProductItem(productCodePrm);
		productPrice.setText(invProductItemRecordSelected.getProductPrice() + "");
		productQty.setText(invProductItemRecordSelected.getProductQty() + "");
		productAvailable.setText(invProductItemRecordSelected.getProductQtyAvailability() + "");
		transactionType.setText(invProductItemRecordSelected.getTransactionTypeId());
		productPurchaseDate.setText(invProductItemRecordSelected.getEntryDate().toString());
		// invProductItemRecordSelected
		if (invProductItemRecordSelected.getRestProduct() != null) {
			productType.setValue(invProductItemRecordSelected.getRestProduct().getCtgProductType());
		} else {
			productType.getSelectionModel().select(null);

		}

		if (invProductItemRecordSelected.getRestProduct() != null) {
			productName.setValue(invProductItemRecordSelected.getRestProduct());
		} else {
			productName.getSelectionModel().select(null);

		}

	}

	public void defaultModeEnabled() {
		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		transactionType.setEditable(false);
		productQty.setEditable(false);
		productPurchaseDate.setEditable(false);
		productWaste.setEditable(false);

		productType.setDisable(false);
		productName.setDisable(false);
		searchBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		saveBtn.setDisable(true);

	}

	public void initModeEnabled() {
		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		transactionType.setEditable(false);
		productQty.setEditable(false);
		productPurchaseDate.setEditable(false);
		productWaste.setEditable(false);

		productType.setDisable(false);
		productName.setDisable(false);
		searchBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
	}

	public void rowSelectedModeEnabled() {
		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		transactionType.setEditable(false);
		productQty.setEditable(false);
		productPurchaseDate.setEditable(false);
		productWaste.setEditable(true);

		productType.setDisable(true);
		productName.setDisable(true);
		searchBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
	}

	public void editModeEnabled() {

		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		transactionType.setEditable(false);
		productQty.setEditable(false);
		productPurchaseDate.setEditable(false);
		productWaste.setEditable(true);

		productType.setDisable(true);
		productName.setDisable(true);
		searchBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void newModeEnabled() {

		productPrice.setEditable(true);
		productAvailable.setEditable(true);
		productType.setDisable(false);
		productName.setDisable(false);
		transactionType.setDisable(false);
		productQty.setEditable(true);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void defaultLabel() {
		lblProductWaste.setTextFill(Color.web("#000000"));

	}

	public void refreshComboBoxList() {

		productTypeData.clear();
		List<CtgProductType> list = manageProductType.findAll();
		for (CtgProductType r : list) {
			productTypeData.add(r);
		}
		productTypeData.add(0, null);
		productType.setItems(productTypeData);

		productNameData.clear();
		productName.setItems(productNameData);
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

		productType.valueProperty().addListener(new ChangeListener<CtgProductType>() {
			@Override
			public void changed(ObservableValue ov, CtgProductType t, CtgProductType t1) {
				if (t1 != null) {
					productNameData.clear();
					List<RestProduct> measureList = manageProduct.findByProductType(t1.getProductTypeId());
					for (RestProduct r : measureList) {
						productNameData.add(r);
					}
					productNameData.add(0, null);
					productName.setValue(null);
					productName.setItems(productNameData);
				} else {
					productNameData.clear();
					productName.setItems(productNameData);
					productName.setValue(null);
				}

			}
		});

		productName.setCellFactory(new Callback<ListView<RestProduct>, ListCell<RestProduct>>() {
			@Override
			public ListCell<RestProduct> call(ListView<RestProduct> p) {
				final ListCell<RestProduct> cell = new ListCell<RestProduct>() {
					@Override
					protected void updateItem(RestProduct t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getProductId() + " - " + t.getProductName());
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
