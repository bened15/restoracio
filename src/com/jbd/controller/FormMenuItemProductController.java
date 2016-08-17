package com.jbd.controller;


import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgProductTypeManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemProductManagement;
import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.model.CtgProductType;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemProduct;
import com.jbd.model.RestProduct;

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

public class FormMenuItemProductController {

	private Main mainApp;
	private ApplicationContext context;
	private RestMenuItemProduct menuItemProduct;
	private RestMenuItemProduct menuItemProductSelected = new RestMenuItemProduct();
	private RestMenuItem menuItemSelected = new RestMenuItem();
	private String userEntry = "Douglas";
	private GeneralFunctions gf = new GeneralFunctions();
	
	@Autowired
	private IRestMenuItemManagement manageRestMenuItem;
	@Autowired
	private IRestProductManagement manageRestProduct;
	@Autowired
	private IRestMenuItemProductManagement manageRestMenuItemProduct;
	@Autowired
	private ICtgProductTypeManagement manageProductType;
	//Declaracion Labels
	@FXML
	private Label lblMenuItemProductType, lblMenuItemProduct,lblMenuItemProductMeasure,lblMenuItemProductQty;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn   , clearBtn , deleteBtn  ;
	//Declaracion Campos
	@FXML
	private TextField menuItemProductMeasure,menuItemProductQty;

	//Declaracion ComboBox
	@FXML
	private ComboBox menuItemProductType,menuItemRestProduct;
	@FXML
	private ObservableList <CtgProductType> menuItemProductTypeData =  FXCollections.observableArrayList();
	@FXML
	private ObservableList <RestProduct> menuItemRestProductData =  FXCollections.observableArrayList();

	//Declaracion Tablas
	@FXML
	private TableView<RestMenuItem> menuItemList = new TableView<RestMenuItem>();  ;
	@FXML
	private ObservableList <RestMenuItem> menuItemData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuItemTypeColumn = new TableColumn("menuItemTypeColumn");
	@FXML
	private TableColumn menuItemNameColumn = new TableColumn("menuItemNameColumn");

	@FXML
	private TableView<RestMenuItemProduct> menuItemProductList = new TableView<RestMenuItemProduct>();  ;
	@FXML
	private ObservableList <RestMenuItemProduct> menuItemProductData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuItemProductTypeColumn = new TableColumn("menuItemProductTypeColumn");
	@FXML
	private TableColumn menuItemProductColumn = new TableColumn("menuItemProductColumn");
	@FXML
	private TableColumn menuItemProductQtyColumn = new TableColumn("menuItemProductQtyColumn");

	
	//Declaracion de acciones

	@FXML
	public void onSearch(MouseEvent event) {				
			refreshList();		
	}

	@FXML
	public void getSelectedRow(MouseEvent event) {	
		menuItemSelected = menuItemList.getSelectionModel().getSelectedItem();
		System.out.println("SELECTED ROW MENU "+menuItemSelected.getMenuItemId());
		//menuItemProductSelected = manageSupplier.findRestMenuItemProduct(supplierCodeSelected);
    	loadRecordInformationIngredients(menuItemSelected);
//		editModeEnabled();
    	resetValues();
    	initModeEnabled();
	}

	@FXML
	public void getSelectedRowProductItem(MouseEvent event) {	
		int menuItemProductCodeSelected  = menuItemProductList.getSelectionModel().getSelectedItem().getMenuItemProductId();
		System.out.println("SELECTED ROW MENU ITEM"+menuItemProductCodeSelected);
		//menuItemProductSelected = manageSupplier.findRestMenuItemProduct(supplierCodeSelected);
    	loadRecordInformation(menuItemProductCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {	
		refreshComboBoxList();
			resetRecord();
			newModeEnabled();
	}

	
	@FXML
	public void onDelete(MouseEvent event) {	
		boolean withOutError = false;
		withOutError = manageRestMenuItemProduct.deleteRestMenuItemProduct(menuItemProductSelected);						

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
					if (menuItemProductSelected.getMenuItemProductId() ==0 ){
						newRecord = true;
					}
					menuItemProductSelected.setRestMenuItem(menuItemSelected);
					menuItemProductSelected.setRestProduct((RestProduct) menuItemRestProduct.getValue());
					menuItemProductSelected.setCtgMeasureUnit(((RestProduct) menuItemRestProduct.getValue()).getCtgMeasureUnit());
					menuItemProductSelected.setQuantity(Float.parseFloat(menuItemProductQty.getText()));
					
					menuItemProduct = new RestMenuItemProduct();
					if (newRecord){
						System.out.println("NUEVO");
						menuItemProduct = manageRestMenuItemProduct.insertRestMenuItemProduct(menuItemProductSelected);						

					}else{
						System.out.println("UPDATE");
						menuItemProduct =  manageRestMenuItemProduct.updateRestMenuItemProduct(menuItemProductSelected);						
					}
					if (menuItemProduct==null){
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

		

		public FormMenuItemProductController() {
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
        //loadComboBox
        //addListeners();
		refreshListMenu();
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
		menuItemProductSelected = new RestMenuItemProduct();
		
		
		menuItemProductType.setValue(null);
		menuItemRestProduct.setValue(null);
		menuItemProductQty.setText("");
		menuItemProductMeasure.setText("");
		 		 
	}

	public void resetRecord(){
		menuItemProductSelected = new RestMenuItemProduct();
				 		 
	}

	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;		

		if (menuItemProductQty.getText() == null || menuItemProductQty.getText().isEmpty()){
			errorMessage.append(messageErrorNumber+"-"+"El campo cantidad es obligatorio.\n");
			messageErrorNumber++;								
			lblMenuItemProductQty.setTextFill(Color.web("#ff0000"));
			//return errorMessage;
		}else{
			if (!gf.validNumber(menuItemProductQty.getText())){
				lblMenuItemProductQty.setTextFill(Color.web("#ff0000"));
				errorMessage.append(messageErrorNumber+"-"+"El campo cantidad debe ser un numero.\n");
			messageErrorNumber++;				
			}
		}
		if (menuItemRestProduct.getValue()== null){
			errorMessage.append(messageErrorNumber+"-"+"El campo producto es obligatorio.\n");
			messageErrorNumber++;				
			lblMenuItemProduct.setTextFill(Color.web("#ff0000"));
			
		}
		
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		return errorString;			
	}
	
	public void refreshListMenu(){
		
		
		menuItemData.clear();
		menuItemList.getColumns().clear();
		menuItemTypeColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemTypeText"));
		menuItemNameColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemName"));
		List<RestMenuItem> list = manageRestMenuItem.findAll();
		for(RestMenuItem u : list){
			u.setMenuItemTypeText(u.getCtgMenuType().getMenuTypeName());
			menuItemData.add(u);
		}
		
		menuItemList.setItems(menuItemData);
		
		menuItemList.getColumns().addAll(menuItemTypeColumn,menuItemNameColumn)		;

	}
	
	public void refreshList(){
		
		
		menuItemProductData.clear();
		menuItemProductList.getColumns().clear();
		menuItemProductTypeColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItemProduct, String>("restMenuItemProductTypeText"));
		menuItemProductColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItemProduct, String>("restMenuItemProductNameText"));
		menuItemProductQtyColumn.setCellValueFactory(new PropertyValueFactory<RestMenuItemProduct, String>("quantity"));
		List<RestMenuItemProduct> list = manageRestMenuItemProduct.findIngredientes(menuItemSelected);
		for(RestMenuItemProduct u : list){
			u.setRestMenuItemProductNameText(u.getRestProduct().getProductName());
			u.setRestMenuItemProductTypeText(u.getRestProduct().getCtgProductType().getTypeName());
			menuItemProductData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		menuItemProductList.setItems(menuItemProductData);
		
		menuItemProductList.getColumns().addAll(menuItemProductTypeColumn,menuItemProductColumn,menuItemProductQtyColumn)		;

	}
	

	public void loadRecordInformationIngredients(RestMenuItem menuItem){
		refreshList();
		
	}
	
	public void loadRecordInformation(int menuItemProductCodePrm){
		menuItemProductSelected = manageRestMenuItemProduct.findRestMenuItemProduct(menuItemProductCodePrm);
		menuItemProductQty.setText(menuItemProductSelected.getQuantity()+""); 
		menuItemProductMeasure.setText(menuItemProductSelected.getRestProduct().getCtgMeasureUnit().getMeasureName());
		 //menuItemProductSelected
		 if (menuItemProductSelected.getRestProduct() != null && menuItemProductSelected.getRestProduct().getCtgProductType() != null){	
			 menuItemProductType.getSelectionModel().select(menuItemProductSelected.getRestProduct().getCtgProductType() );
		 }else{
			 menuItemProductType.getSelectionModel().select(null);
		 }
		 if (menuItemProductSelected.getRestProduct() != null ){	
			 menuItemRestProduct.getSelectionModel().select(menuItemProductSelected.getRestProduct() );
		 }else{
			 menuItemRestProduct.getSelectionModel().select(null);
		 }


			}

	public void defaultModeEnabled(){
		menuItemProductQty.setEditable(false);
		menuItemProductMeasure.setEditable(false);
		menuItemRestProduct.setDisable(true);
		menuItemProductType.setDisable(true);
		 //searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		 clearBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		menuItemProductQty.setEditable(false);
		menuItemProductMeasure.setEditable(false);
		menuItemRestProduct.setDisable(true);
		menuItemProductType.setDisable(true);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
		 deleteBtn.setDisable(true);
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		menuItemProductQty.setEditable(true);
		menuItemProductMeasure.setEditable(false);
		menuItemRestProduct.setDisable(false);
		menuItemProductType.setDisable(false);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Limpiar");
	}

	public void editModeEnabled(){
		
		menuItemProductQty.setEditable(true);
		menuItemProductMeasure.setEditable(false);
		menuItemRestProduct.setDisable(false);
		menuItemProductType.setDisable(false);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 deleteBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
	public void newModeEnabled(){
		
		menuItemProductQty.setEditable(true);
		menuItemProductMeasure.setEditable(false);
		menuItemRestProduct.setDisable(false);
		menuItemProductType.setDisable(false);
		 deleteBtn.setDisable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 clearBtn.setDisable(false);
		 clearBtn.setText("Cancelar");
	}
	
		public void defaultLabel(){
			lblMenuItemProductMeasure.setTextFill(Color.web("#000000"));
			lblMenuItemProductType.setTextFill(Color.web("#000000"));
			lblMenuItemProduct.setTextFill(Color.web("#000000"));
			
		}

		public void refreshComboBoxList(){
			
			
			menuItemProductTypeData.clear();
			List<CtgProductType> list = manageProductType.findAll();
			for(CtgProductType r : list){
				menuItemProductTypeData.add(r);
			}
			menuItemProductType.setItems(menuItemProductTypeData);

			menuItemRestProductData.clear();
			List<RestProduct> list2 = manageRestProduct.findAll();
			for(RestProduct r : list2){
				menuItemRestProductData.add(r);
			}			
			menuItemRestProduct.setItems(menuItemRestProductData);


		}
		
		
		public void alterComboBoxProperties(){
			menuItemProductType.setCellFactory(new Callback<ListView<CtgProductType>,ListCell<CtgProductType>>(){
			     @Override
	            public ListCell<CtgProductType> call(ListView<CtgProductType> p) {
	                final ListCell<CtgProductType> cell = new ListCell<CtgProductType>(){	 
	                    @Override
	                    protected void updateItem(CtgProductType t, boolean bln) {
	                        super.updateItem(t, bln);	                         
	                        if(t != null){
	                            setText(t.getProductTypeId() +" - "+t.getTypeName());
	                        }else{
	                            setText(null);
	                        }
	                    }
	                };	                 
	                return cell;
	            }
	        });
			
			menuItemProductType.valueProperty().addListener(new ChangeListener<CtgProductType>() {
		        @Override public void changed(ObservableValue ov, CtgProductType t, CtgProductType t1) {
		            if (t1!=null){
		            	menuItemRestProductData.clear();
		    			List<RestProduct> measureList = manageRestProduct.findByProductType(t1.getProductTypeId());
		    			for(RestProduct r : measureList){
		    				menuItemRestProductData.add(r);
		    			}
		    			menuItemRestProduct.setValue(null);
		    			menuItemRestProduct.setItems(menuItemRestProductData);
		            }else{
		            	menuItemRestProductData.clear();
		            	menuItemRestProduct.setItems(menuItemRestProductData);
		    			menuItemRestProduct.setValue(null);
		            }
		            
		        }    
		    });
			
			menuItemRestProduct.setCellFactory(new Callback<ListView<RestProduct>,ListCell<RestProduct>>(){
			     @Override
	            public ListCell<RestProduct> call(ListView<RestProduct> p) {
	                final ListCell<RestProduct> cell = new ListCell<RestProduct>(){
	                    @Override
	                    protected void updateItem(RestProduct t, boolean bln) {
	                        super.updateItem(t, bln);
	                        if(t != null){
	                            setText(t.getProductId() +" - "+t.getProductName());
	                        }else{
	                            setText(null);
	                        }
	                    }
	  
	                };  
	                return cell;
	            }
	        });
			
			menuItemRestProduct.valueProperty().addListener(new ChangeListener<RestProduct>() {
		        @Override public void changed(ObservableValue ov, RestProduct t, RestProduct t1) {
		            if (t1!=null){
		            	menuItemProductMeasure.setText(t1.getCtgMeasureUnit().getMeasureName());
		            }else{
		            	menuItemProductMeasure.setText("");
		            }
		            
		        }    
		    });
		}

	

}
