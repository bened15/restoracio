package application;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.controller.FormLoginController;
import com.jbd.controller.MainController;

/********************************************************************/
import com.jbd.hibernate.interfaces.IRestOrderManagement;
import com.jbd.model.RestOrder;
import com.jbd.utils.Printer;
/********************************************************************/

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	public static Stage primaryStage;
	private AnchorPane rootLayout;
	public static ApplicationContext context;

	/********************************************************************/
	@Autowired
	private IRestOrderManagement manageOrder;

	private RestOrder orders = new RestOrder();
	/********************************************************************/

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;
			Main.primaryStage.setTitle("JBD Login");
//			Main.primaryStage.setMaximized(true);
			Main.primaryStage.setResizable(false);
			// Load root layout from fxml file.

			// primaryStage.setScene(scene);
			// primaryStage.show();

			context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
			AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
			acbFactory.autowireBean(this);
			initPrincipalLayout();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initPrincipalLayout() {

		FXMLLoader loader = new FXMLLoader();

		/********************************************************************/
		List<RestOrder> orderList;
		Printer printer = new Printer();
		boolean printingResult = true;
		orderList = manageOrder.findAllRestOrders();

//		printingResult = printer.printOrders(orderList);

//		System.out.println(">Printing Result: " + printingResult);
		/********************************************************************/
//		loader.setLocation(Main.class.getResource("../com/jbd/view/W_MainWindow_scroll.fxml"));
//		try {
//			rootLayout = (AnchorPane) loader.load();
//			// Show the scene containing the root layout.
//			Scene scene = new Scene(rootLayout,1024,768);
////			Scene scene = new Scene(rootLayout);
//			// inyectar al controller la main app para cada una de las ventanas
//
//			MainController mainController = loader.getController();
//			mainController.setMainApp(this);
//
//			scene.getStylesheets().add("/com/jbd/view/style/application.css");
//
//			// scene.getStylesheets()
//			// .add(getClass().getResource("../com/jbd/view/style/application.css").toExternalForm());
//
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}





//		loader.setLocation(Main.class.getResource("../com/jbd/FormAdministration.fxml"));
		loader.setLocation(Main.class.getResource("../com/jbd/view/FormLogin.fxml")); //login

		try {
			AnchorPane rootl;
			FormLoginController frmLogin;
			rootl 		= loader.load();
			frmLogin 	= (FormLoginController) loader.getController();

			Scene scene = new Scene(rootl);

			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setOnCloseRequest(e -> {
				if(frmLogin.getSelectedModule() != null){
					FXMLLoader loaderMain 	= new FXMLLoader();
					Stage stageMain			= new Stage();
					Scene sceneMain			= null;
					AnchorPane rootMain 	= null;

					switch (frmLogin.getSelectedModule().getModuleId()){
						case 1:		loaderMain.setLocation(Main.class.getResource("../com/jbd/view/W_MainWindow_scroll.fxml"));
									try {
										rootMain = (AnchorPane) loaderMain.load();
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									// Show the scene containing the root layout.
									sceneMain = new Scene(rootMain,1024,768);

									MainController mainController = loaderMain.getController();
									mainController.setMainApp(this);

									sceneMain.getStylesheets().add("/com/jbd/view/style/application.css");

									// scene.getStylesheets()
									// .add(getClass().getResource("../com/jbd/view/style/application.css").toExternalForm());

									stageMain.setScene(sceneMain);
									stageMain.setTitle("JBD Restaurant");
									stageMain.show();

									break;
						case 2:  	loaderMain.setLocation(Main.class.getResource("../com/jbd/FormAdministration.fxml"));

									try {
										rootMain 	= loaderMain.load();
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									sceneMain 	= new Scene(rootMain);

									stageMain.setScene(sceneMain);
									stageMain.setMaximized(true);
									stageMain.setTitle("JBD Admin");
									stageMain.show();
									break;

						default:	System.exit(0);
									break;
					}


				}
			});


		} catch (Exception e) {
			// TODO Auto-generated catch blockW
			e.printStackTrace();
		}


	}

	public AnchorPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(AnchorPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}
