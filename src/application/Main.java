package application;


import java.io.IOException;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.controller.MainController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage primaryStage;
	private AnchorPane rootLayout;
	public static ApplicationContext context;

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;
			Main.primaryStage.setTitle("JBD POS");
			Main.primaryStage.setMaximized(true);
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
//		/*
		loader.setLocation(Main.class.getResource("../com/jbd/view/W_MainWindow_scroll.fxml"));		
		try {
			rootLayout = (AnchorPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout,1024,768);
//			Scene scene = new Scene(rootLayout);
			// inyectar al controller la main app para cada una de las ventanas

			MainController mainController = loader.getController();
			mainController.setMainApp(this);

			scene.getStylesheets().add("/com/jbd/view/style/application.css");

			// scene.getStylesheets()
			// .add(getClass().getResource("../com/jbd/view/style/application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

//*/

		/*
		loader.setLocation(Main.class.getResource("../com/jbd/FormAdministration.fxml"));
		
		try {
			AnchorPane rootl;
			rootl = loader.load();
			
			Scene scene = new Scene(rootl,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			 primaryStage.setOnCloseRequest(e -> Platform.exit());
		} catch (Exception e) {
			// TODO Auto-generated catch blockW
			e.printStackTrace();
		}
		*/
		
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
