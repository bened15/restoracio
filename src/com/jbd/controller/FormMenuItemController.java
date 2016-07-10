package com.jbd.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.model.CtgMenuType;
import com.jbd.model.RestMenuItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.Main;

public class FormMenuItemController {

	private Main mainApp;
	private ApplicationContext context;
	private RestMenuItem menuItem;
	private RestMenuItem menuItemSelected = new RestMenuItem();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	private FileChooser fileChooser = new FileChooser();
	private File imageSelected = null;
	private byte[] imageBytes;
	@Autowired
	private IRestMenuItemManagement manageRestMenuItem;
	@Autowired
	private ICtgMenuTypeManagement manageMenuItemType;
	// Declaracion Labels
	@FXML
	private Label lblMenuItemName, lblMenuItemType, lblMenuItemPrice, lblMenuItemAvailable;

	// Declaracion Botones
	@FXML
	private Button newBtn, saveBtn, searchBtn, clearBtn, openFileBtn;
	// Declaracion Campos
	@FXML
	private TextField menuItemName, menuItemPrice;
	@FXML
	private TextArea menuItemDescription;

	// Declaracion ImageView
	@FXML
	private ImageView menuItemImage;
	// Declaracion ComboBox
	@FXML
	private ComboBox menuItemType, menuItemAvailable;
	@FXML
	private ObservableList<CtgMenuType> menuItemTypeData = FXCollections.observableArrayList();

	// Declaracion Tablas
	@FXML
	private TableView<RestMenuItem> menuItemList = new TableView<RestMenuItem>();;
	@FXML
	private ObservableList<RestMenuItem> menuItemData = FXCollections.observableArrayList();
	@FXML
	private TableColumn menuItemNameColumn = new TableColumn("menuItemNameColumn");
	@FXML
	private TableColumn menuItemTypeColumn = new TableColumn("menuItemTypeColumn");

	// Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {
		refreshListOnSearch();
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {
		int menuItemCodeSelected = menuItemList.getSelectionModel().getSelectedItem().getMenuItemId();
		System.out.println("SELECTED ROW " + menuItemCodeSelected);
		// menuItemSelected =
		// manageSupplier.findRestMenuItem(supplierCodeSelected);
		loadRecordInformation(menuItemCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {
		resetValues();
		newModeEnabled();
	}

	@FXML
	public void onOpenfile(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		imageSelected = fileChooser.showOpenDialog(new Stage());
		if (imageSelected != null) {
			String imagepath = imageSelected.getPath();
			// System.out.println("file:"+imagepath);

			try {
				BufferedImage bufferedImage = ImageIO.read(imageSelected);

				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				menuItemImage.setImage(image);
				ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
				ImageIO.write(bufferedImage, "jpg", baos);
				baos.flush();
				imageBytes = baos.toByteArray();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// Image image = new Image(imagepath);
			// menuItemImage.setImage(image);

			// mainStage.display(selectedFile);
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
		System.out.println("error:" + error);
		if (error == null) {
			boolean newRecord = false;
			if (menuItemSelected.getMenuItemId() == 0) {
				newRecord = true;
			}
			menuItemSelected.setMenuItemName(menuItemName.getText());
			menuItemSelected.setMenuItemDescription(menuItemDescription.getText());
			menuItemSelected.setCtgMenuType((CtgMenuType) menuItemType.getValue());
			if (imageSelected != null) {
				menuItemSelected.setMenuImage(imageBytes);
			}
			// menuItemSelected.setCtgMenuType((CtgMenuType)
			// menuItemType.getValue());

			menuItem = new RestMenuItem();
			if (newRecord) {
				System.out.println("NUEVO");
				menuItem = manageRestMenuItem.insertRestMenuItem(menuItemSelected);

			} else {
				System.out.println("UPDATE");
				menuItem = manageRestMenuItem.updateRestMenuItem(menuItemSelected);
			}
			if (menuItem == null) {
				System.out.println("ERROR AL GUARDAR");
			} else {
				System.out.println("EXITO AL GUARDAR");
				resetValues();
				refreshList();
				initModeEnabled();
			}
		}

	}

	public FormMenuItemController() {
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
		menuItemSelected = new RestMenuItem();

		menuItemDescription.setText("");
		menuItemName.setText("");
		menuItemPrice.setText("");
		menuItemDescription.setText("");
		menuItemType.getSelectionModel().select(null);
		menuItemImage.setImage(null);

	}

	public String validateRecord() {
		defaultLabel();
		String errorMessage = null;

		if (menuItemName.getText() == null || menuItemName.getText().isEmpty()) {
			errorMessage = "El campo nombre es obligatorio.";
			lblMenuItemName.setTextFill(Color.web("#ff0000"));
			// return errorMessage;
		}
		if (menuItemPrice.getText() == null || menuItemPrice.getText().isEmpty()) {
			errorMessage = "El campo precio es obligatorio.";
			lblMenuItemName.setTextFill(Color.web("#ff0000"));
			if (!gf.validNumber(menuItemPrice.getText())) {
				errorMessage = "El campo precio debe ser un numero.";

			}
			// return errorMessage;
		}
		if (menuItemType.getValue() == null) {
			errorMessage = "El campo tipo es obligatorio.";
			lblMenuItemType.setTextFill(Color.web("#ff0000"));

		}

		return errorMessage;
	}

	public void refreshList() {

		menuItemData.clear();
		menuItemList.getColumns().clear();
		menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemName"));
		menuItemTypeColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemTypeText"));
		List<RestMenuItem> list = manageRestMenuItem.findAll();
		for (RestMenuItem u : list) {
			u.setMenuItemTypeText(u.getCtgMenuType().getMenuTypeName());
			menuItemData.add(u);
			// System.out.println(u1.getUserCode());
			// System.out.println(u1.getUserName());
		}

		menuItemList.setItems(menuItemData);

		menuItemList.getColumns().addAll(menuItemNameColumn, menuItemTypeColumn);

	}

	public void refreshListOnSearch(){
		
		
		menuItemData.clear();
		menuItemList.getColumns().clear();
		menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemName"));
		menuItemTypeColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemTypeText"));

		int menuItemTypeId = 0;
		if(menuItemType.getValue()!= null){
			menuItemTypeId = ((CtgMenuType) menuItemType.getValue()).getMenuTypeId();
		}
		
		List<RestMenuItem> list = manageRestMenuItem.findMenuItemByExample(menuItemName.getText(), menuItemTypeId);
		for(RestMenuItem u : list){
			u.setMenuItemTypeText(u.getCtgMenuType().getMenuTypeName());
			menuItemData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		menuItemList.setItems(menuItemData);
		
		menuItemList.getColumns().addAll(menuItemNameColumn,menuItemTypeColumn)		;

	}
	

	
	public void loadRecordInformation(int menuItemCodePrm) {
		menuItemSelected = manageRestMenuItem.findRestMenuItem(menuItemCodePrm);
		menuItemName.setText(menuItemSelected.getMenuItemName());
		menuItemDescription.setText(menuItemSelected.getMenuItemDescription());
		menuItemPrice.setText(Float.toString(menuItemSelected.getMenuItemPrice()));

		// menuItemSelected
		if (menuItemSelected.getCtgMenuType() != null) {
			menuItemType.getSelectionModel().select(menuItemSelected.getCtgMenuType());
		} else {
			menuItemType.getSelectionModel().select(null);

		}

		if (menuItemSelected.getMenuImage() != null) {

			ByteArrayInputStream bais = new ByteArrayInputStream(menuItemSelected.getMenuImage());
			BufferedImage imageBuffer;
			try {
				imageBuffer = ImageIO.read(bais);
				Image imageLoad = SwingFXUtils.toFXImage(imageBuffer, null);
				menuItemImage.setImage(imageLoad);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			menuItemImage.setImage(null);
		}

	}

	public void defaultModeEnabled() {
		menuItemName.setEditable(true);
		menuItemDescription.setEditable(false);
		menuItemPrice.setEditable(false);
		menuItemType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		clearBtn.setDisable(true);
		saveBtn.setDisable(true);

	}

	public void initModeEnabled() {
		menuItemName.setEditable(true);
		menuItemDescription.setEditable(false);
		menuItemPrice.setEditable(false);
		menuItemType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
		defaultLabel();
	}

	public void rowSelectedModeEnabled() {
		menuItemName.setEditable(true);
		menuItemPrice.setEditable(true);
		menuItemDescription.setEditable(true);
		menuItemType.setDisable(false);
		searchBtn.setDisable(false);
		newBtn.setDisable(false);
		saveBtn.setDisable(true);
		clearBtn.setDisable(false);
		clearBtn.setText("Limpiar");
	}

	public void editModeEnabled() {

		menuItemName.setEditable(true);
		menuItemDescription.setEditable(true);
		menuItemDescription.setEditable(true);
		menuItemType.setDisable(false);

		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void newModeEnabled() {

		menuItemName.setEditable(true);
		menuItemPrice.setEditable(true);
		menuItemDescription.setEditable(true);
		menuItemType.setDisable(false);
		searchBtn.setDisable(true);
		newBtn.setDisable(true);
		saveBtn.setDisable(false);
		clearBtn.setDisable(false);
		clearBtn.setText("Cancelar");
	}

	public void defaultLabel() {
		lblMenuItemPrice.setTextFill(Color.web("#000000"));
		lblMenuItemName.setTextFill(Color.web("#000000"));
		lblMenuItemType.setTextFill(Color.web("#000000"));

	}

	public void refreshComboBoxList() {

		menuItemTypeData.clear();
		List<CtgMenuType> list = manageMenuItemType.findAll();
		for (CtgMenuType r : list) {
			menuItemTypeData.add(r);
		}

		menuItemType.setItems(menuItemTypeData);

	}

	public void alterComboBoxProperties() {
		menuItemType.setCellFactory(new Callback<ListView<CtgMenuType>, ListCell<CtgMenuType>>() {
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
