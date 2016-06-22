package com.jbd.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jbd.model.RestTableAccount;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentController {

	@Autowired
	private Stage secondaryStage;
	private MainController mainAppC;
	@FXML
	private GridPane numbersArea;
	@FXML
	private Label totalPropina, totalRecibido, totalCuenta, totalCambio, totalDescuento;
	@FXML
	private AnchorPane headerAP;
	private static final ChoiceBox facturaAPagar = new ChoiceBox();
	private DecimalFormat decimFormat = new DecimalFormat("#.00");
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
	@Autowired
	private CtgPaymentMethod paymentMethod;
	private static String RestBillN = "";
	private List<RestBillDetail> bDetails = new ArrayList<RestBillDetail>();
	private static double totalAccount = 0.0;
	private static boolean actualizoLabels = false;

	public PaymentController() {

	}

	public void loadConfig() {
		try {
			System.out.println("Numero de cuenta a pagar " + MainController.getBillsDetailQuantity().get(0)
					.getRestBill().getRestTableAccount().getTableAccountId());
			// this.mainAppC = mainC;
			enableAutowireCapabilities();
			loadBillsToChoiceBox(MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount());

			this.secondaryStage.setTitle("Pago");
			this.secondaryStage.initModality(Modality.WINDOW_MODAL);
			this.secondaryStage.initOwner(Main.primaryStage);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../com/jbd/view/W_PaymentView.fxml"));
			AnchorPane paymentOverview = (AnchorPane) loader.load();

			facturaAPagar.setLayoutX(224.0);
			facturaAPagar.setLayoutY(126.0);
			facturaAPagar.setPrefHeight(46.0);
			facturaAPagar.setPrefWidth(366.0);
			paymentOverview.getChildren().add(facturaAPagar);

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
		this.facturaAPagar.getSelectionModel().selectFirst();
		fillTotalLabels();
	}

	private void loadBillsToChoiceBox(RestTableAccount account) {
		try {

			List<RestBill> bills = manageRestBill.findBillsWithRestTableAccount(account);
			int i = 0, x = 0;
			double totalBill = 0.0;

			ObservableList<String> billsList = FXCollections.observableArrayList();
			while (i < bills.size()) {

				bDetails = manageRestBillDetail.findAllRestBillDetailFromRestBill(bills.get(i));

				while (x < bDetails.size()) {

					totalBill = totalBill + bDetails.get(x).getBillDetailSubtotal();

					x++;
				}

				RestBill rb = new RestBill();
				rb = bills.get(i);
				rb.setBillSubtotal(totalBill);
				rb.setBillTotal(totalBill * 1.10);
				rb.setBillTip(totalBill * 0.10);
				manageRestBill.updateRestBill(rb);
				totalAccount = totalAccount + totalBill;
				x = 0;
				totalBill = 0;

				System.out.println("Valores" + bills.get(i).getBillName());
				billsList.add(bills.get(i).getBillName() + "--" + bills.get(i).getBillId());
				i++;
			}
			// facturaAPagar.setValue();
			facturaAPagar.setItems(billsList);

			facturaAPagar.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue ov, Number value, Number new_value) {

					RestBillN = (String) facturaAPagar.getItems().get((Integer) new_value);
					if (actualizoLabels) {
						fillTotalLabels();

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void fillTotalLabels() {
		actualizoLabels = true;
		int i = 0;
		double totalCuenta = 0;
		List<RestBillDetail> rbd;

		String[] bills = RestBillN.split("--");
		rbd = manageRestBillDetail.findAllRestBillDetailFromRestBill(new RestBill(Integer.parseInt(bills[1])));
		while (i < rbd.size()) {

			totalCuenta = totalCuenta + rbd.get(i).getBillDetailSubtotal();
			i++;
		}
		this.totalCuenta.setText(decimFormat.format(totalCuenta * 1.10));
		this.totalPropina.setText(decimFormat.format(totalCuenta * 0.10));

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

	@FXML
	public void numeroSelected(MouseEvent event) {
		Button bClicked = (Button) event.getSource();
		if (bClicked.getText().contains("ENTER")) {
			this.numbersArea.setDisable(true);
		}
		if (bClicked.getText().contains("Borrar")) {
			String valor = totalRecibido.getText();
			totalRecibido.setText(valor.substring(0, valor.length() - 1));
		}
		if (bClicked.getText().contains("Limpiar")) {
			totalRecibido.setText("");
		}

		if (bClicked.getText().contains("1") || bClicked.getText().contains("2") || bClicked.getText().contains("3")
				|| bClicked.getText().contains("4") || bClicked.getText().contains("5")
				|| bClicked.getText().contains("6") || bClicked.getText().contains("7")
				|| bClicked.getText().contains("8") || bClicked.getText().contains("9")
				|| bClicked.getText().contains("0") || bClicked.getText().contains(".")) {
			totalRecibido.setText(totalRecibido.getText() + bClicked.getText());
			totalCambio.setText(this.decimFormat
					.format(Double.parseDouble(totalRecibido.getText()) - Double.parseDouble(totalCuenta.getText())));
		}
	}

	@FXML
	public void efectivoSelected() {
		this.numbersArea.setDisable(false);

		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Efectivo");

	}

	@FXML
	public void pagoExactoSelected() {
		this.numbersArea.setDisable(false);
		totalRecibido.setText(totalCuenta.getText());
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Efectivo");
	}

	@FXML
	public void tarjetaSelected() {
		this.numbersArea.setDisable(true);
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Tarjeta");

	}

	@FXML
	public void cuponSelected() {
		this.numbersArea.setDisable(true);
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Cupon");

	}

	@FXML
	public void descuentoSelected() {
		this.numbersArea.setDisable(true);

	}

	@FXML
	public void salirSelected() {
		this.secondaryStage.close();
	}

	@FXML
	public void generarPagoSelected() {
		RestBillPayment rbp = new RestBillPayment();
		System.out.println("Esto trae billname: " + RestBillN);
		String[] bills = RestBillN.split("--");
		rbp.setAmount(Float.parseFloat(totalRecibido.getText()));
		rbp.setRestBill(new RestBill(Integer.parseInt(bills[1])));
		rbp.setCtgPaymentMethod(this.paymentMethod);
		manageRestBillPayment.insertRestBillPayment(rbp);
		System.out.println("supuestamente " + totalAccount);
		if (manageRestBillPayment.isAmmountPaymentEqualOrMoreThanAccount(totalAccount,
				MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount())) {

			System.out.println("cuenta completada");
			RestTableAccount ta = MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount();
			ta.setClosedDatetime(new Date());
			ta.setAccountStatus("Cerrada");

			manageRestTableAccount.updateRestTableAccount(ta);
			loadBillsToChoiceBox(ta);
			// se deberia de cerrar la ventana

		} else {

			// falta pagar todavia alguna alguna bill pendiente
			System.out.println("falta pagar alguna bill");
		}

	}

}
