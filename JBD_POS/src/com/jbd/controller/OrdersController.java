package com.jbd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.dao.RestAreaManagementDAO;
import com.jbd.hibernate.dao.RestOrderManagementDAO;
import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.hibernate.interfaces.IRestOrderManagement;
import com.jbd.model.RestArea;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemType;
import com.jbd.model.RestOrder;
import com.jbd.model.RestTable;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class OrdersController {

	private MainController mainAppC;
	private ObservableList<RestOrder> ordenes = FXCollections.observableArrayList();
	@Autowired
	private IRestOrderManagement manageOrder;
	@Autowired
	private IRestAreaManagement manageRestArea;
	private ApplicationContext context;

	public OrdersController() {

		context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
		AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);

	}

	public MainController getMainAppC() {
		return mainAppC;
	}

	@FXML
	public void openNewOrderTables() {

		try {

			// Load tableLocation overview.
			// FXMLLoader loader = new FXMLLoader();
			// loader.setLocation(Main.class.getResource("../com/jbd/view/W_NewOrder.fxml"));
			AnchorPane newOrderTables = new AnchorPane();

			OrdersController oController = new OrdersController();
			// aca se tendria que consultar la tabla de mesas para iterar sobre
			// ellas y mostrarlas segun la zona seleccionada
			List<String> list = new ArrayList<String>();
			list.add("Tables");
			list.add("t1");
			list.add("t2");
			list.add("t3");
			list.add("t4");
			list.add("t5");
			list.add("t6");
			list.add("t7");
			list.add("t8");
			list.add("t9");
			setAnchorPaneForPrincipal(newOrderTables);
			loadPanesForTables(list, newOrderTables, "#ebe887;");
			oController.setMainAppC(mainAppC);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void selectNumberOfPersons() {
		try {
			// Load tableLocation overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../com/jbd/view/W_QuantityPersons.fxml"));
			AnchorPane newOrder = (AnchorPane) loader.load();

			setAnchorPaneForPrincipal(newOrder);
			OrdersController oc = loader.getController();
			oc.setMainAppC(mainAppC);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// al final este metodo se puede utilizar para cuando se quiera cargar una
	// lista de paneles para los tipos de comida y comida
	public void loadPanesDependingOnTypes(List<String> listaObjeto, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int x = 120, y = 10;

		while (i < listaObjeto.size()) {
			Pane p = new Pane();
			Label title = new Label();
			p.setPrefHeight(100);
			p.setPrefWidth(100);
			title.setPrefHeight(100);
			title.setPrefWidth(100);
			switch (pos) {
			case 1:
				p.setLayoutX(20);
				break;
			case 2:
				p.setLayoutX(140);
				break;
			case 3:
				p.setLayoutX(260);
				break;
			case 4:
				p.setLayoutX(380);
				break;

			}
			p.setLayoutY(y);
			if (pos % 4 == 0) {
				y = y + 120;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";");
			title.setText(listaObjeto.get(i));
			title.setWrapText(true);
			title.setTextAlignment(TextAlignment.CENTER);
			title.setStyle("-fx-font-weight: bold;-fx-alignment: center ;");
			title.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					List<String> list = new ArrayList<String>();
					ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					// aqui tendriamos que preguntar por el tipo de objeto asi:
					if (listaObjeto.getClass().equals(RestMenuItemType.class)) {
						// if (listaObjeto.get(0).contains("MenuType")) {
						list.clear();

						list.add("Food");
						list.add("Papas con vegetales");
						list.add("Ensalada Rusa");
						list.add("Ensalada Cesar");
						list.add("Tomate picado");
						list.add("Ensalda de pollo y hongos");
						loadPanesDependingOnTypes(list, ap, "#ebe813;");

					}
					if (listaObjeto.get(0).contains("Food")) {

						// double tot =
						// Double.parseDouble(mainAppC.getTotal().getText());
						// mainAppC.getTotal().setText(String.valueOf(tot = tot
						// + 2));
						// RestOrder order = new RestOrder();
						// order.setAttendant("2");
						// order.setOrderComment("Comentario");
						// ordenes.add(order);
						//// mainAppC.getListaOrden().setItems(ordenes);
						// loadPanesDependingOnTypes(listaObjeto, ap,
						// "#ebe813;");
						//
						// RestArea ra=new RestArea();
						// ra.setAreaName("new area");
						// ra.setEntryDate(new Date());
						// ra.setEntryUser("Yo");
						// ra.setIsSmokerArea(1);

						// manageRestArea.insertRestArea(ra);

					}

					// hacer tantos if como sea necesario para agregar
					// funcionalidad a los panes ya creados para cada tipo de
					// dato(para cada entity creado dinamicamente)

				}

			});
			p.getChildren().add(title);
			ap.getChildren().add(p);
			i++;
			pos++;
		}
	}

	public void loadPanesForTables(List<String> tables, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int x = 120, y = 10;

		while (i < tables.size()) {
			Pane p = new Pane();
			Label title = new Label();
			p.setPrefHeight(80);
			p.setPrefWidth(80);
			title.setPrefHeight(80);
			title.setPrefWidth(80);
			switch (pos) {
			case 1:
				p.setLayoutX(20);
				break;
			case 2:
				p.setLayoutX(140);
				break;
			case 3:
				p.setLayoutX(260);
				break;
			case 4:
				p.setLayoutX(380);
				break;

			}
			p.setLayoutY(y);
			if (pos % 4 == 0) {
				y = y + 120;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";");
			title.setText(tables.get(i));
			title.setWrapText(true);
			title.setTextAlignment(TextAlignment.CENTER);
			title.setStyle("-fx-font-weight: bold;-fx-alignment: center ;");
			title.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					// aqui tendriamos que preguntar por el tipo de objeto asi:
					if (tables.getClass().equals(RestTable.class)) {
						// if (tables.get(0).contains("Tables")) {

						// List<String> list = new ArrayList<String>();
						// list.add("MenuType");
						// list.add("Ensaladas");
						// list.add("Mariscos");
						// list.add("Carnes");
						// list.add("Bebidas sin Alcohol");
						// list.add("Bebidas con Alcohol");
						// list.add("Entradas");
						// list.add("Pastas");
						// list.add("Otro tipo de comida");
						// list.add("Tipo de comida extra");
						// loadPanesDependingOnTypes(list, ap, "#ebe813;");

						selectNumberOfPersons();

					}
					if (tables.get(0).contains("Food")) {

						List<String> listFood = new ArrayList<String>();

					}

					// hacer tantos if como sea necesario para agregar
					// funcionalidad a los panes ya creados para cada tipo de
					// dato(para cada entity creado dinamicamente)

				}

			});
			p.getChildren().add(title);
			ap.getChildren().add(p);
			i++;
			pos++;
		}
	}

	public void loadMenuTypes() {

		List<RestMenuItem> list = new ArrayList<RestMenuItem>();
		AnchorPane tipoMenu = new AnchorPane();
		setAnchorPaneForPrincipal(tipoMenu);
		// loadPanesDependingOnTypes(list, tipoMenu, "#ebe813");

	}

	public void setAnchorPaneForPrincipal(AnchorPane ap) {
		mainAppC.getPrincipal().getChildren().clear();
		mainAppC.getPrincipal().getChildren().add(ap);

	}

	public void setMainAppC(MainController mainAppC) {
		this.mainAppC = mainAppC;
	}

}
