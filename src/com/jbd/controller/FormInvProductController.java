package com.jbd.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.hibernate.interfaces.ICtgTransactionTypeManagement;
import com.jbd.hibernate.interfaces.IInvProductItemManagement;
import com.jbd.model.RestProduct;
import com.jbd.model.CtgProductType;
import com.jbd.model.CtgTransactionType;
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

public class FormInvProductController {

	private Main mainApp;
	private ApplicationContext context;
	private InvProductItem invProductItem;
	private InvProductItem invProductItemSelected = new InvProductItem();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();

	@Autowired
	private IInvProductItemManagement manageInvProductItem;
	@Autowired
	private IRestProductManagement manageProduct;
	@Autowired
	private ICtgProductTypeManagement manageProductType;
	@Autowired
	private ICtgTransactionTypeManagement manageTransactionType;
	// Declaracion Labels
	@FXML
	private Label lblProduct, lblProductPrice, lblProductQty, lblTransactionType;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, clearBtn;
	// Declaracion Campos
	@FXML
	private TextField productPrice, productQty, productAvailable;

	// Declaracion ComboBox
	@FXML
	private ComboBox productType, productName, transactionType;
	@FXML
	private ObservableList<CtgProductType> productTypeData = FXCollections.observableArrayList();
	@FXML
	private ObservableList<RestProduct> productNameData = FXCollections.observableArrayList();
	@FXML
	private ObservableList<CtgTransactionType> transactionTypeData = FXCollections.observableArrayList();

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
			boolean newRecord = false;
			if (invProductItemSelected.getInvProductId() == 0) {
				newRecord = true;
			}
			invProductItemSelected.setEntryDate(new Date());
			invProductItemSelected.setEntryUser(userEntry);
			invProductItemSelected.setRestProduct((RestProduct) productName.getValue());
			invProductItemSelected.setState("OPEN");
			invProductItemSelected.setProductPrice(Float.parseFloat(productPrice.getText()));
			invProductItemSelected.setProductQty(Integer.parseInt(productQty.getText()));
			invProductItemSelected.setRestProduct((RestProduct) productName.getValue());
			invProductItemSelected.setTransactionTypeId((String) transactionType.getValue());

			invProductItem = new InvProductItem();
			if (newRecord) {
				System.out.println("NUEVO");
				invProductItem = manageInvProductItem.insertInvProductItem(invProductItemSelected);

			} else {
				System.out.println("UPDATE");
				invProductItem = manageInvProductItem.updateInvProductItem(invProductItemSelected);
			}
			if (invProductItem == null) {
				System.out.println("ERROR AL GUARDAR");
			} else {
				System.out.println("EXITO AL GUARDAR");
				resetValues();
				refreshList();
				initModeEnabled();
			}
		}

	}

	public FormInvProductController() {
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
		invProductItemSelected = new InvProductItem();

		productPrice.setText("");
		productQty.setText("");
		productAvailable.setText("");
		productType.getSelectionModel().select(null);
		productName.getSelectionModel().select(null);
		transactionType.getSelectionModel().select(null);

	}

	public String validateRecord() {
		defaultLabel();
		String errorMessage = null;

		if (productPrice.getText() == null || productPrice.getText().isEmpty()) {
			errorMessage = "El campo precio es obligatorio.";
			lblProductPrice.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		} else {
			if (!gf.validNumber(productPrice.getText())) {
				errorMessage = "El campo precio debe ser un numero.";
				lblProductPrice.setTextFill(Color.web("#ff0000"));

			}
		}
		if (productName.getValue() == null) {
			errorMessage = "El campo product es obligatorio.";
			lblProduct.setTextFill(Color.web("#ff0000"));

		}
		if (transactionType.getValue() == null) {
			errorMessage = "El campo Transaccion es obligatorio.";
			lblTransactionType.setTextFill(Color.web("#ff0000"));

		}

		return errorMessage;
	}

	public void refreshList() {
		productData.clear();
		invProductList.getColumns().clear();
		productNameColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductNameText"));
		productTypeColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("restProductTypeText"));
		productPriceColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productPrice"));
		productQtyColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("productQty"));
		productDateColumn.setCellValueFactory(new PropertyValueFactory<InvProductItem, String>("entryDate"));
		List<InvProductItem> list = manageInvProductItem.findAll();
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
		List<InvProductItem> list = manageInvProductItem.findInvProductItemByExample(producTypeId,productId);
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
		invProductItemSelected = manageInvProductItem.findInvProductItem(productCodePrm);
		productPrice.setText(invProductItemSelected.getProductPrice() + "");
		productQty.setText(invProductItemSelected.getProductQty() + "");
		productAvailable.setText(invProductItemSelected.getProductQtyAvailability() + "");

		// invProductItemSelected
		if (invProductItemSelected.getRestProduct() != null) {
			productType.setValue(invProductItemSelected.getRestProduct().getCtgProductType());
		} else {
			productType.getSelectionModel().select(null);

		}

		if (invProductItemSelected.getRestProduct() != null) {
			productName.setValue(invProductItemSelected.getRestProduct());
		} else {
			productName.getSelectionModel().select(null);

		}
		if (invProductItemSelected.getTransactionTypeId() != null) {
			transactionType.setValue(invProductItemSelected.getTransactionTypeId());
		} else {
			transactionType.getSelectionModel().select(null);

		}

	}

	public void defaultModeEnabled() {
		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		productType.setDisable(false);
		productName.setDisable(false);
		transactionType.setDisable(true);
		productQty.setEditable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		saveBtn.setDisable(true);

	}

	public void initModeEnabled() {
		productPrice.setEditable(false);
		productAvailable.setEditable(false);
		productType.setDisable(false);
		productName.setDisable(false);
		transactionType.setDisable(true);
		productQty.setEditable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
	}

	public void rowSelectedModeEnabled() {
		productPrice.setEditable(true);
		productAvailable.setEditable(true);
		productType.setDisable(false);
		productName.setDisable(false);
		transactionType.setDisable(false);
		productQty.setEditable(true);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
	}

	public void editModeEnabled() {

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
		lblProductQty.setTextFill(Color.web("#000000"));
		lblProduct.setTextFill(Color.web("#000000"));
		lblProductPrice.setTextFill(Color.web("#000000"));

	}

	public void refreshComboBoxList() {

		productTypeData.clear();
		List<CtgProductType> list = manageProductType.findAll();
		for (CtgProductType r : list) {
			productTypeData.add(r);
		}
		productType.setItems(productTypeData);

		productNameData.clear();
		productName.setItems(productNameData);
		transactionTypeData.clear();
		List<CtgTransactionType> transactionList = manageTransactionType.findbyAdittion(1);
		for (CtgTransactionType r : transactionList) {
			transactionTypeData.add(r);
		}
		transactionType.setItems(transactionTypeData);

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

		transactionType.setCellFactory(new Callback<ListView<CtgTransactionType>, ListCell<CtgTransactionType>>() {
			@Override
			public ListCell<CtgTransactionType> call(ListView<CtgTransactionType> p) {
				final ListCell<CtgTransactionType> cell = new ListCell<CtgTransactionType>() {
					@Override
					protected void updateItem(CtgTransactionType t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getTransactionTypeId() + " - " + t.getTransactionTypeName());
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
