package com.jbd.controller;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.hibernate.interfaces.IRestBillDetailManagement;
import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.hibernate.interfaces.IRestBillPaymentManagement;
import com.jbd.hibernate.interfaces.IRestTableAccountManagement;
import com.jbd.model.CtgPaymentMethod;
import com.jbd.model.RestBill;
import com.jbd.model.RestBillDetail;
import com.jbd.model.RestBillPayment;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestOrder;
import com.jbd.model.RestShift;
import com.jbd.model.RestTableAccount;
import com.jbd.utils.Effect;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SplitOrderController {

	@Autowired
	private Stage secondaryStage;
	private MainController mainAppC;
	@FXML
	private GridPane numbersArea;
	@Autowired
	private Effect efe;
	private double priceTotal = 0;
	@FXML
	private Label totalL;
	@FXML
	private AnchorPane contentPane;
	private DecimalFormat decimFormat = new DecimalFormat("#.00");
	@FXML
	private TableView<RestOrder> itemsOrderTable = new TableView<RestOrder>();
	@Autowired
	private IRestBillPaymentManagement manageRestBillPayment;
	@Autowired
	private ICtgPaymentMethodManagement manageCtgPaymentMethod;
	@Autowired
	private IRestBillManagement manageRestBill;
	@Autowired
	private IRestBillDetailManagement manageRestBillDetail;
	@Autowired
	private IRestTableAccountManagement manageRestTableAccount;
	private static String RestBillN = "";
	private List<RestBillDetail> bDetails = new ArrayList<RestBillDetail>();
	private static double totalAccount = 0.0;
	private int dotCounter = 0;
	private ObservableList<RestOrder> itemsList = FXCollections.observableArrayList();

	// private static boolean actualizoLabels = false;

	public SplitOrderController() {

	}

	public void loadConfig() {
		try {
			System.out.println("Numero de cuenta a pagar " + MainController.getBillsDetailQuantity().get(0)
					.getRestBill().getRestTableAccount().getTableAccountId());
			// this.mainAppC = mainC;
			enableAutowireCapabilities();

			this.secondaryStage.setTitle("Dividir Orden");
			this.secondaryStage.initModality(Modality.WINDOW_MODAL);
			this.secondaryStage.initOwner(Main.primaryStage);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../com/jbd/view/W_SplitOrderView.fxml"));
			AnchorPane paymentOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(paymentOverview);
			this.secondaryStage.setScene(scene);
			this.secondaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {

		enableAutowireCapabilities();
		loadPanesForMenuItem(MainController.getItemsListOrders(), contentPane, "Green");

	}

	public void loadPanesForMenuItem(List<RestOrder> menuItemOrder, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int x = 120, y = 10;

		while (i < menuItemOrder.size()) {
			Button p = new Button();

			p.setPrefHeight(80);
			p.setPrefWidth(80);

			switch (pos) {
			case 1:
				p.setLayoutX(10);
				break;
			case 2:
				p.setLayoutX(100);
				break;
			case 3:
				p.setLayoutX(190);
				break;
			case 4:
				p.setLayoutX(280);
				break;
			case 5:
				p.setLayoutX(370);
				break;
			case 6:
				p.setLayoutX(460);
				break;
			case 7:
				p.setLayoutX(550);
				break;
			case 8:
				p.setLayoutX(640);
				break;
			case 9:
				p.setLayoutX(730);
				break;

			}
			p.setLayoutY(y);
			if (pos % 9 == 0) {
				y = y + 90;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-font-size:10px");

			p.setId(String.valueOf(i));

			String menuItemName = "";
			if (menuItemOrder.get(i).getRestMenuItem().getMenuItemName().length() > 30) {
				menuItemName = menuItemOrder.get(i).getRestMenuItem().getMenuItemName().substring(0, 29) + "..";
			} else {
				menuItemName = menuItemOrder.get(i).getRestMenuItem().getMenuItemName();

			}

			p.setText(menuItemName + "\n" + "$  " + menuItemOrder.get(i).getRestMenuItem().getMenuItemPrice());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					efe.applyFadeTransitionToButton(clickeado);

					RestOrder or = menuItemOrder.get(Integer.parseInt(clickeado.getId()));
					// String[] partida = clickeado.getText().split("\n");
					// item.setMenuItemId(Integer.parseInt(clickeado.getId()));
					// item.setMenuItemName(partida[0]);
					// item.setMenuItemPrice(Float.parseFloat(partida[1].trim().substring(1,
					// partida[1].length())));

					priceTotal = priceTotal + or.getRestMenuItem().getMenuItemPrice();
					totalL.setText("Total: $ " + decimFormat.format(priceTotal) + "\n\n" + "Propina (10%) $"
							+ (decimFormat.format(priceTotal * 0.10)) + "\n" + "\n" + "----------" + "\n" + "Total: $ "
							+ decimFormat.format(priceTotal + (priceTotal * 0.10)));

					// System.out.println("Tama�o de lista" + itemsList.size());
					// rightSide.getChildren().add(refreshTable());
					// refreshTable();

					Pane p = new Pane();
					p.setStyle("-fx-background-color:#c1ebff;-fx-border-radius: 10.0px;-fx-border-color: aliceblue ;");
					p.setPrefWidth(810.0);
					p.setPrefHeight(700.0);
					ap.getChildren().add(p);
					efe.applyFadeTransitionToRectangle(p);
					contentPane.getChildren().remove(clickeado);
					loadPanesForBills(MainController.getBillsQuantity(), p, color, or);

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);
			i++;
			pos++;
		}
	}

	public void loadPanesForBills(List<RestBill> bills, Pane ap, String color, RestOrder or) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < bills.size()) {
			Button p = new Button();

			p.setPrefHeight(100);
			p.setPrefWidth(100);

			switch (pos) {
			case 1:
				p.setLayoutX(10);
				break;
			case 2:
				p.setLayoutX(120);
				break;

			}
			p.setLayoutY(y);
			if (pos % 2 == 0) {
				y = y + 110;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-font-size:10px");

			p.setId(String.valueOf(i));
			// p.setTranslateZ(bills.get(i).get);

			String billsName = "";
			if (bills.get(i).getBillName().length() > 30) {
				billsName = bills.get(i).getBillName().substring(0, 29) + "..";
			} else {
				billsName = bills.get(i).getBillName();

			}

			p.setText(billsName);
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					efe.applyFadeTransitionToButton(clickeado);
					try {
						RestBillDetail billDetail = new RestBillDetail();
						RestBill bill = bills.get(Integer.parseInt(clickeado.getId()));
						bill.setBillSubtotal(bill.getBillSubtotal() + or.getRestMenuItem().getMenuItemPrice());
						bill.setBillTip(bill.getBillSubtotal() * 0.10);
						bill.setBillTotal(bill.getBillSubtotal() * 1.10);
						billDetail.setRestBill(bill);
						billDetail.setBillDetailSubtotal(or.getRestMenuItem().getMenuItemPrice());
						billDetail.setBillDetailTotal(or.getRestMenuItem().getMenuItemPrice() * 1.10);

						bDetails.add(billDetail);
						itemsList.add(or);
						refreshTable();
						manageRestBill.updateRestBill(bill);
						contentPane.getChildren().remove(ap);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);
			i++;
			pos++;
		}
	}

	private void refreshTable() {

		itemsOrderTable.getColumns().clear();

		TableColumn id = new TableColumn("Id");
		TableColumn elemento = new TableColumn("Elemento");
		TableColumn precio = new TableColumn("Precio($)");
		// TableColumn total = new TableColumn("Total");

		id.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("orderId"));
		elemento.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("restMenuItem"));
		precio.setCellValueFactory(new PropertyValueFactory<RestOrder, Double>("restMenuItem"));
		// total.setCellValueFactory(new PropertyValueFactory<>("total"));

		// itemsLocalList.setItems(itemsList);
		// itemsLocalList.getColumns().addAll(id,elemento, precio, total);
		itemsOrderTable.setItems(itemsList);
		itemsOrderTable.getColumns().addAll(id, elemento, precio);

	}

	@FXML
	public void confirmSplit() {
		int i = 0;
		// if (itemsList.size() > 0) {

		// restTableAccount =
		// manageRestTableAccount.insertRestTableAccount(restTableAccount);

		// }
		if (itemsList.size() > 0) {

			while (i < itemsList.size()) {
				try {

					RestBillDetail det = new RestBillDetail();

					det = bDetails.get(i);
					det.setRestOrder(itemsList.get(i));
//					manageRestBillDetail.deleteRestBillDetail(MainController.getBillsDetailQuantity().get(i));
					manageRestBill.deleteRestBill(MainController.getBillsDetailQuantity().get(i).getRestBill());
					manageRestBillDetail.insertRestBillDetail(det);

					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error" + e.getMessage());

				}

			}

			JOptionPane.showMessageDialog(null, "La(s) Ordene(s) y Factura(s) se creaon exitosamente");
			itemsList.clear();
			// rightSide.getChildren().clear();
			this.secondaryStage.close();
			// rightSide.getChildren().add(refreshTable());

		} else {
			JOptionPane.showMessageDialog(null, "No hay ordenes ni facturas por crear");
		}

	}

	private void enableAutowireCapabilities() {
		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	public MainController getMainAppC() {
		return mainAppC;
	}

	public void setMainAppC(MainController mainAppC) {
		this.mainAppC = mainAppC;
	}

}
