package com.jbd.controller;


import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Main;

public class FormAdministrationController {

	private Main mainApp;
	private ApplicationContext context;
	
	
	//Declaracion Labels

	//Declaracion Botones Administracion
	@FXML
	private MenuItem  menuItemUsuario , menuItemRol;
	//Declaracion Botones Catalogos
	@FXML
	private MenuItem  menuItemCustomer, menuItemSupplier , menuItemMeasure, menuItemPaymentMethod, menuItemProduct,menuItemProductType   ;
	//Declaracion Botones Restaurante
	@FXML
	private MenuItem  menuItemRestaurant , menuItemArea , menuItemKitchen, menuItemTable, menuItemMenu, menuItemMenuType, menuItemMenuProduct, menuItemDiscount , menuItemReservation  ;
	//Declaracion Botones Inventario
	@FXML
	private MenuItem  menuItemInvProduct , menuItemInvProductWaste   ;
	//Declaracion Botones
	@FXML
	private Button newBtn  ,saveBtn,searchBtn  ,editBtn , clearBtn   ;
	
	
	//Declaracion de acciones
	@FXML
	public void onMenuItemArea() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormArea.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de areas");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}


	@FXML
	public void onMenuItemCustomer() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormCustomer.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de clientes");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}
	@FXML
	public void onMenuItemDiscount() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormDiscount.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de descuentos");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}


	@FXML
	public void onMenuItemInvProduct() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormInvProduct3.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                FormInvProductController controller = fxmlLoader.<FormInvProductController>getController();
		                controller.setUserEntry("admin");
		                stage.setTitle("Registro de compras");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemInvProductWaste() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormInvProductWaste.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de desperdicios");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemKitchen() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormKitchen.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de cocinas");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemMeasure() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormMeasure.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de unidades de medida");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemMenu() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormMenu3.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                FormMenuItemController2 controller = fxmlLoader.<FormMenuItemController2>getController();
		                controller.setUserEntry("admin");
		                stage.setTitle("Registro de platos");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemMenuProduct() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormMenuProduct.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de ingredientes");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemReservation() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormReservation.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de reservaciones");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemRestaurant() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormRestaurant.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Datos generales del restaurante");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	
	@FXML
	public void onMenuItemMenuType() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormMenuType.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de tipo de menu");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemPaymentMethod() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormPaymentMethod.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de tipos de pago");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemProduct() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormProduct.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                FormProductController controller = fxmlLoader.<FormProductController>getController();
		                controller.setUserEntry("admin");
		                stage.setTitle("Registro de productos");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemProductType() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormProductType.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de tipo de producto");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemRol() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormRole.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de rol");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}
	
	@FXML
	public void onMenuItemSupplier() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormSupplier.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de proveedores");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemTable() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormTable.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de mesas");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}

	@FXML
	public void onMenuItemUsuario() {
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(Main.class.getResource("../com/jbd/FormUser.fxml"));
		                Parent root1 = (Parent) fxmlLoader.load();
		                Stage stage = new Stage();
		                stage.initModality(Modality.APPLICATION_MODAL);
		                //stage.initStyle(StageStyle.UNDECORATED);
		                stage.setTitle("Registro de usuarios");
		                stage.setScene(new Scene(root1));  
		                stage.show();
		        } catch(Exception e) {
		           e.printStackTrace();
		          }
	}



		

		public FormAdministrationController() {
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

	
	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	

		
}
