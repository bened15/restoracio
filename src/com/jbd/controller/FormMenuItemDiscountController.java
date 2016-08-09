package com.jbd.controller;


import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.general.GeneralFunctions;
import com.jbd.hibernate.interfaces.ICtgDiscountMenuManagement;
import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemTypeManagement;
import com.jbd.hibernate.interfaces.IRestProductManagement;
import com.jbd.model.CtgDiscount;
import com.jbd.model.CtgDiscountMenu;
import com.jbd.model.CtgMenuType;
import com.jbd.model.CtgProductType;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemType;

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
import javafx.stage.Stage;
import javafx.util.Callback;
import application.Main;

public class FormMenuItemDiscountController {

	private Main mainApp;
	private ApplicationContext context;
	private CtgDiscountMenu menuItemDiscount;
	private CtgDiscountMenu menuItemDiscountSelected = new CtgDiscountMenu();
	private RestMenuItem menuItemSelected = new RestMenuItem();
	private CtgDiscount discountSelected = new CtgDiscount();
	private String userEntry;
	private GeneralFunctions gf = new GeneralFunctions();
	
	@Autowired
	private IRestMenuItemManagement manageRestMenuItem;
	@Autowired
	private ICtgDiscountMenuManagement manageMenuDiscount;
	@Autowired
	private ICtgMenuTypeManagement manageMenuType;
	//Declaracion Labels
	@FXML
	private Label lblMenuItemType, lblMenuItem;

	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn , deleteBtn  , closeBtn;
	//Declaracion Campos

	//Declaracion ComboBox
	@FXML
	private ComboBox menuItemType,menuItem;
	@FXML
	private ObservableList <CtgMenuType> menuItemTypeData =  FXCollections.observableArrayList();
	@FXML
	private ObservableList <RestMenuItem> menuItemData =  FXCollections.observableArrayList();

	//Declaracion Tablas
	@FXML
	private TableView<CtgDiscountMenu> menuItemDiscountList = new TableView<CtgDiscountMenu>();  ;
	@FXML
	private ObservableList <CtgDiscountMenu> menuItemDiscountData =  FXCollections.observableArrayList();
	@FXML
	private TableColumn menuItemTypeColumn = new TableColumn("menuItemTypeColumn");
	@FXML
	private TableColumn menuItemColumn = new TableColumn("menuItemColumn");


	
	//Declaracion de acciones


	public void setSelectedDiscountItem(CtgDiscount item) {	
		discountSelected = item;
		//menuItemDiscountSelected = manageSupplier.findRestMenuItem(supplierCodeSelected);
    	loadRecordInformationMenuItems(discountSelected);
//		editModeEnabled();
    	resetValues();
    	initModeEnabled();
	}

	@FXML
	public void getSelectedRowProductItem(MouseEvent event) {	
		int menuItemDiscountCodeSelected  = menuItemDiscountList.getSelectionModel().getSelectedItem().getMenuItemDiscountId();
		System.out.println("SELECTED ROW MENU ITEM"+menuItemDiscountCodeSelected);
		//menuItemDiscountSelected = manageSupplier.findRestMenuItem(supplierCodeSelected);
    	loadRecordInformation(menuItemDiscountCodeSelected);
		editModeEnabled();
	}

	@FXML
	public void onNew(MouseEvent event) {	
		refreshComboBoxList();
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
		withOutError = manageMenuDiscount.deleteCtgDiscountMenu(menuItemDiscountSelected);						

		if (!withOutError){
			System.out.println("ERROR AL ELIMINAR");
		}else{
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
					if (menuItemDiscountSelected.getMenuItemDiscountId() ==0 ){
						newRecord = true;
					}
					menuItemSelected = (RestMenuItem) menuItem.getValue();
					menuItemDiscountSelected.setRestMenuItem(menuItemSelected);
					menuItemDiscountSelected.setCtgDiscount(discountSelected);
					
					menuItemDiscount = new CtgDiscountMenu();
					if (newRecord){
						System.out.println("NUEVO");
						menuItemDiscount = manageMenuDiscount.insertCtgDiscountMenu(menuItemDiscountSelected);						
					}else{
						System.out.println("UPDATE");
						menuItemDiscount =  manageMenuDiscount.updateCtgDiscountMenu(menuItemDiscountSelected);						
					}
					if (menuItemDiscount==null){
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

		

		public FormMenuItemDiscountController() {
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
		menuItemDiscountSelected = new CtgDiscountMenu();
		menuItemSelected = new RestMenuItem();
		
		menuItemType.setValue(null);
		menuItem.setValue(null);
		 		 
	}
	
	public String validateRecord() {
		 defaultLabel();
		 String errorString = null;
			StringBuilder errorMessage = new StringBuilder();
			int messageErrorNumber = 1;	
		if (menuItem.getValue()== null){
			errorMessage.append(messageErrorNumber+"-"+"El campo plato es obligatorio.\n");
			messageErrorNumber++;				
			lblMenuItem.setTextFill(Color.web("#ff0000"));
			
		}
		if (errorMessage.toString().length() > 0){
			errorString = errorMessage.toString();
		}

		
		return errorString;			
	}
	
	public void refreshListMenu(){
		
		
	
	}
	
	public void refreshList(){
		
		
		menuItemDiscountData.clear();
		menuItemDiscountList.getColumns().clear();
		menuItemTypeColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscountMenu, String>("discountMenuItemTypeText"));
		menuItemColumn.setCellValueFactory(new PropertyValueFactory<CtgDiscountMenu, String>("discountMenuItemNameText"));
		List<CtgDiscountMenu> list = manageMenuDiscount.findByDiscount(discountSelected.getDiscountId());
		for(CtgDiscountMenu u : list){
			u.setDiscountMenuItemNameText(u.getRestMenuItem().getMenuItemName());
			u.setDiscountMenuItemTypeText(u.getRestMenuItem().getCtgMenuType().getMenuTypeName());
			menuItemDiscountData.add(u);
			//System.out.println(u1.getUserCode());
			//System.out.println(u1.getUserName());
		}
		
		menuItemDiscountList.setItems(menuItemDiscountData);
		
		menuItemDiscountList.getColumns().addAll(menuItemTypeColumn,menuItemColumn)		;

	}
	

	public void loadRecordInformationMenuItems(CtgDiscount menuItem){
		refreshList();
		
	}
	
	public void loadRecordInformation(int menuItemDiscountCodePrm){
		menuItemDiscountSelected = manageMenuDiscount.findCtgDiscountMenu(menuItemDiscountCodePrm);
		 //menuItemDiscountSelected
		 if (menuItemDiscountSelected.getRestMenuItem() != null && menuItemDiscountSelected.getRestMenuItem().getCtgMenuType() != null){	
			 menuItemType.getSelectionModel().select(menuItemDiscountSelected.getRestMenuItem().getCtgMenuType() );
		 }else{
			 menuItemType.getSelectionModel().select(null);
		 }
		 if (menuItemDiscountSelected.getRestMenuItem() != null ){	
			 menuItem.getSelectionModel().select(menuItemDiscountSelected.getRestMenuItem() );
		 }else{
			 menuItem.getSelectionModel().select(null);
		 }


			}

	public void defaultModeEnabled(){
		menuItem.setDisable(true);
		menuItemType.setDisable(true);
		 //searchBtn.setDisable(false);
		 newBtn.setDisable(false);
		// clearBtn.setDisable(true);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);

		 
	}
	public void initModeEnabled(){
		menuItem.setDisable(true);
		menuItemType.setDisable(true);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);
		 defaultLabel();
	}
	public void rowSelectedModeEnabled(){
		menuItem.setDisable(false);
		menuItemType.setDisable(false);
		 newBtn.setDisable(false);
		 saveBtn.setDisable(true);
		 deleteBtn.setDisable(true);
	}

	public void editModeEnabled(){
		
		menuItem.setDisable(false);
		menuItemType.setDisable(false);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
		 deleteBtn.setDisable(false);
	}
	
	public void newModeEnabled(){
		
		menuItem.setDisable(false);
		menuItemType.setDisable(false);
		 deleteBtn.setDisable(true);
		 newBtn.setDisable(true);
		 saveBtn.setDisable(false);
	}
	
		public void defaultLabel(){
			lblMenuItemType.setTextFill(Color.web("#000000"));
			lblMenuItem.setTextFill(Color.web("#000000"));
			
		}

		public void refreshComboBoxList(){
			
			
			menuItemTypeData.clear();
			List<CtgMenuType> list = manageMenuType.findAll();
			for(CtgMenuType r : list){
				menuItemTypeData.add(r);
			}
			menuItemType.setItems(menuItemTypeData);

			menuItemData.clear();
			List<RestMenuItem> list2 = manageRestMenuItem.findAll();
			for(RestMenuItem r : list2){
				menuItemData.add(r);
			}			
			menuItem.setItems(menuItemData);


		}
		
		
		public void alterComboBoxProperties(){
			menuItemType.setCellFactory(new Callback<ListView<CtgMenuType>,ListCell<CtgMenuType>>(){
			     @Override
	            public ListCell<CtgMenuType> call(ListView<CtgMenuType> p) {
	                final ListCell<CtgMenuType> cell = new ListCell<CtgMenuType>(){	 
	                    @Override
	                    protected void updateItem(CtgMenuType t, boolean bln) {
	                        super.updateItem(t, bln);	                         
	                        if(t != null){
	                            setText(t.getMenuTypeId() +" - "+t.getMenuTypeName());
	                        }else{
	                            setText(null);
	                        }
	                    }
	                };	                 
	                return cell;
	            }
	        });
			
			menuItemType.valueProperty().addListener(new ChangeListener<CtgMenuType>() {
		        @Override public void changed(ObservableValue ov, CtgMenuType t, CtgMenuType t1) {
		            if (t1!=null){
		            	menuItemData.clear();
		    			List<RestMenuItem> menuItemList = manageRestMenuItem.findMenuItemByTypeMenu(t1);
		    			for(RestMenuItem r : menuItemList){
		    				menuItemData.add(r);
		    			}
		    			menuItem.setValue(null);
		    			menuItem.setItems(menuItemData);
		            }else{
		            	menuItemData.clear();
		            	menuItem.setItems(menuItemData);
		    			menuItem.setValue(null);
		            }
		            
		        }    
		    });
			
			menuItem.setCellFactory(new Callback<ListView<RestMenuItem>,ListCell<RestMenuItem>>(){
			     @Override
	            public ListCell<RestMenuItem> call(ListView<RestMenuItem> p) {
	                final ListCell<RestMenuItem> cell = new ListCell<RestMenuItem>(){
	                    @Override
	                    protected void updateItem(RestMenuItem t, boolean bln) {
	                        super.updateItem(t, bln);
	                        if(t != null){
	                            setText(t.getMenuItemId() +" - "+t.getMenuItemName());
	                        }else{
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
