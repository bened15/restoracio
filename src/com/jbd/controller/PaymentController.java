package com.jbd.controller;

import java.text.DecimalFormat;
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
import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.CtgPaymentMethod;
import com.jbd.model.RestBill;
import com.jbd.model.RestBillDetail;
import com.jbd.model.RestBillPayment;
import com.jbd.model.RestTable;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentController {

	@Autowired
	private Stage secondaryStage;
	private MainController mainAppC;
	@FXML
	private GridPane numbersArea;
	@Autowired
	private IRestTableManagement manageRestTable;
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
	private int dotCounter = 0;
	@Autowired
	private Effect efe;
	private String commentForPaymentMethod = "";
	// private static boolean actualizoLabels = false;
	@FXML
	private Button btnEfectivo, btnPagoExacto, btnTarjeta, btnCupon, btnDescuento, btnPagoNormal, btnPagoParcial;

	public PaymentController() {

	}

	public void loadConfig() {
		try {
			System.out.println("Numero de cuenta a pagar " + MainController.getBillsDetailQuantity().get(0)
					.getRestBill().getRestTableAccount().getTableAccountId());
			// this.mainAppC = mainC;
			enableAutowireCapabilities();

			this.secondaryStage.setTitle("Pago");

			if (this.secondaryStage.getModality() != Modality.WINDOW_MODAL) {
				this.secondaryStage.initModality(Modality.WINDOW_MODAL);
			}
			if (this.secondaryStage.getOwner() != Main.primaryStage) {
				this.secondaryStage.initOwner(Main.primaryStage);
			}

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
		loadBillsToChoiceBox(MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount());

		fillTotalLabels();
	}

	private void loadBillsToChoiceBox(RestTableAccount account) {
		try {

			List<RestBill> bills = manageRestBill.findBillsWithRestTableAccount(account);
			int i = 0;

			ObservableList<String> billsList = FXCollections.observableArrayList();
			while (i < bills.size()) {

				System.out.println("Valores" + bills.get(i).getBillName());
				billsList.add(bills.get(i).getBillName() + "--" + bills.get(i).getBillId());
				i++;
			}
			// facturaAPagar.setValue();
			facturaAPagar.setItems(billsList);

			facturaAPagar.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue ov, Number value, Number new_value) {
					if (facturaAPagar.getItems().size() > 0) {
						RestBillN = (String) facturaAPagar.getItems().get((Integer) new_value);
						// if (actualizoLabels) {
						fillTotalLabels();

						// }
					}
				}
			});
			PaymentController.facturaAPagar.getSelectionModel().selectFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillTotalLabels() {
		// actualizoLabels = true;
		int i = 0;
		double totalCuenta = 0;
		List<RestBillDetail> rbd;

		String[] bills = RestBillN.split("--");
		rbd = manageRestBillDetail.findAllRestBillDetailFromRestBill(new RestBill(Integer.parseInt(bills[1])));
		if (rbd.size() > 0) {
			while (i < rbd.size()) {

				totalCuenta = totalCuenta + rbd.get(i).getBillDetailSubtotal();
				i++;
			}
			this.totalCuenta.setText(decimFormat.format(totalCuenta * 1.10));
			this.totalPropina.setText(decimFormat.format(totalCuenta * 0.10));
			this.totalRecibido.setText("");
			this.totalCambio.setText("");
		}
		List<RestBillPayment> bp;
		bp = manageRestBillPayment.findRestBillPayments(Integer.parseInt(bills[1]));
		i = 0;
		if (bp.size() > 0) {
			totalCuenta = (totalCuenta * 1.10);
			while (i < bp.size()) {
				totalCuenta = totalCuenta - bp.get(i).getAmount();
				i++;
			}
			this.totalCuenta.setText(decimFormat.format(totalCuenta));
			// this.totalPropina.setText(decimFormat.format(totalCuenta *
			// 0.10));
			this.totalRecibido.setText("");
			this.totalCambio.setText("");
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

	@FXML
	public void numeroSelected(MouseEvent event) {
		Button bClicked = (Button) event.getSource();
		if (bClicked.getText().contains("ENTER")) {

			this.numbersArea.setDisable(true);
		}
		if (bClicked.getText().contains("Borrar")) {
			String valor = totalRecibido.getText();
			totalRecibido.setText(valor.substring(0, valor.length() - 1));
			totalCambio.setText(this.decimFormat
					.format(Double.parseDouble(totalRecibido.getText()) - Double.parseDouble(totalCuenta.getText())));

		}
		if (bClicked.getText().contains("Limpiar")) {

			totalCambio.setText("0");
			totalRecibido.setText("");
		}

		if (bClicked.getText().contains("1") || bClicked.getText().contains("2") || bClicked.getText().contains("3")
				|| bClicked.getText().contains("4") || bClicked.getText().contains("5")
				|| bClicked.getText().contains("6") || bClicked.getText().contains("7")
				|| bClicked.getText().contains("8") || bClicked.getText().contains("9")
				|| bClicked.getText().contains("0") || bClicked.getText().contains(".")) {

			if (bClicked.getText().contains(".") && totalRecibido.getText().contains(".")) {

			} else {
				String totalRecibidoT = totalRecibido.getText() + bClicked.getText();
				if (totalRecibidoT.contains(".") && (totalRecibidoT.length() - 3) > totalRecibidoT.indexOf(".")) {
					System.out.println("tiene punto y se intneto meter mas de dos decimales");
					String t = totalRecibidoT.substring(0, totalRecibidoT.indexOf(".") + 3);
					totalRecibidoT = t;

				}
				totalRecibido.setText(totalRecibidoT);

				totalCambio.setText(this.decimFormat.format(
						Double.parseDouble(totalRecibido.getText()) - Double.parseDouble(totalCuenta.getText())));

			}
		}
	}

	@FXML
	public void efectivoSelected() {
		this.numbersArea.setDisable(false);
		this.commentForPaymentMethod = "";
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Efectivo");

	}

	@FXML
	public void pagoExactoSelected() {
		this.numbersArea.setDisable(false);
		this.totalRecibido.setText(totalCuenta.getText());
		this.totalCambio.setText("0");
		this.commentForPaymentMethod = "";
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Efectivo");

	}

	@FXML
	public void tarjetaSelected() {
		this.numbersArea.setDisable(false);
		this.totalCambio.setText("0");
		this.totalRecibido.setText(totalCuenta.getText());
		paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Tarjeta");
		this.commentForPaymentMethod = "";
		disableButtons(true);
		askForCardNumber();

	}

	private void disableButtons(boolean status) {
		this.btnEfectivo.setDisable(status);
		this.btnPagoExacto.setDisable(status);
		this.btnCupon.setDisable(status);
		this.btnDescuento.setDisable(status);
		this.btnPagoExacto.setDisable(status);
		this.btnPagoParcial.setDisable(status);
		this.btnPagoNormal.setDisable(status);
		this.btnTarjeta.setDisable(status);
	}

	private void askForCardNumber() {

		Pane p = new Pane();
		p.setStyle("-fx-background-color:#9fb9b9");
		p.setLayoutX(218.0);

		p.setLayoutY(-9.0);
		p.setPrefHeight(200.0);
		p.setPrefWidth(381.0);

		TextField txtComment = new TextField();
		txtComment.setLayoutX(10.0);

		txtComment.setLayoutY(80.0);
		txtComment.setPrefWidth(375.0);
		txtComment.setDisable(true);

		Button visa = new Button();
		Button mCard = new Button();
		Button aExpress = new Button();
		Button other = new Button();

		visa.setLayoutX(10);
		visa.setLayoutY(10);
		visa.setPrefHeight(50);
		visa.setPrefWidth(90);

		mCard.setLayoutX(110);
		mCard.setLayoutY(10);
		mCard.setPrefHeight(50);
		mCard.setPrefWidth(90);

		aExpress.setLayoutX(200);
		aExpress.setLayoutY(10);
		aExpress.setPrefHeight(50);
		aExpress.setPrefWidth(90);

		other.setLayoutX(300);
		other.setLayoutY(10);
		other.setPrefHeight(50);
		other.setPrefWidth(90);

		visa.setStyle(
				"-fx-background-image:url('/com/jbd/images/visa.png');-fx-background-repeat: stretch;-fx-background-position: top left;");
		mCard.setStyle("-fx-background-image:url('/com/jbd/images/master_card.jpg');");
		aExpress.setStyle("-fx-background-image:url('/com/jbd/images/aex.png');");

		txtComment.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					commentForPaymentMethod = commentForPaymentMethod + txtComment.getText();
					disableButtons(false);
					headerAP.getChildren().remove(p);

				}

				if (event.getCode().equals(KeyCode.ESCAPE)) {
					commentForPaymentMethod = "";
					disableButtons(false);
					headerAP.getChildren().remove(p);
				}

			}
		});
		visa.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				txtComment.setDisable(false);
				commentForPaymentMethod = "Visa ";
			}

		});
		mCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				txtComment.setDisable(false);
				commentForPaymentMethod = "MasterCard ";
			}

		});
		aExpress.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				txtComment.setDisable(false);
				commentForPaymentMethod = "American Express ";
			}

		});
		other.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				txtComment.setDisable(false);
				commentForPaymentMethod = "American Express ";
			}

		});

		p.getChildren().add(txtComment);
		p.getChildren().add(visa);
		p.getChildren().add(mCard);
		p.getChildren().add(aExpress);
		headerAP.getChildren().add(p);
		efe.applyFadeTransitionToTextField(txtComment);
		txtComment.requestFocus();

	}

	private void askForCuponNumber() {

		Pane p = new Pane();
		p.setStyle("-fx-background-color:#9fb9b9");
		p.setLayoutX(218.0);

		p.setLayoutY(-9.0);
		p.setPrefHeight(200.0);
		p.setPrefWidth(381.0);

		TextField txtComment = new TextField();
		txtComment.setLayoutX(10.0);

		txtComment.setLayoutY(80.0);
		txtComment.setPrefWidth(375.0);

		txtComment.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					commentForPaymentMethod = "Cupon # " + txtComment.getText();
					disableButtons(false);
					headerAP.getChildren().remove(p);

				}

				if (event.getCode().equals(KeyCode.ESCAPE)) {
					commentForPaymentMethod = "";
					disableButtons(false);
					headerAP.getChildren().remove(p);
				}

			}
		});

		p.getChildren().add(txtComment);

		headerAP.getChildren().add(p);
		efe.applyFadeTransitionToTextField(txtComment);
		txtComment.requestFocus();

	}

	@FXML
	public void cuponSelected() {
		this.numbersArea.setDisable(false);
		this.commentForPaymentMethod = "";
		disableButtons(true);
		askForCuponNumber();
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
		if (Double.parseDouble(this.totalCambio.getText()) >= 0 && this.totalCambio.getText() != ""
				&& this.totalRecibido.getText() != "") {

			RestBillPayment rbp = new RestBillPayment();
			System.out.println("Esto trae billname: " + RestBillN);
			String[] bills = RestBillN.split("--");
			rbp.setAmount(Float.parseFloat(totalRecibido.getText()) - Float.parseFloat(totalCambio.getText()));
			rbp.setRestBill(new RestBill(Integer.parseInt(bills[1])));
			rbp.setCtgPaymentMethod(this.paymentMethod);
			rbp.setComments(commentForPaymentMethod);
			manageRestBillPayment.insertRestBillPayment(rbp);
			RestTableAccount ta = MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount();
			// System.out.println("supuestamente " + totalAccount);
			if (manageRestBillPayment.isAmmountPaymentEqualOrMoreThanAccount(
					manageRestBill.getTotalAccountFromTable(ta),
					MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount())) {

				ta.setClosedDatetime(new Date());
				ta.setAccountStatus("Cerrada");

				manageRestTableAccount.updateRestTableAccount(ta);
				RestTable restTable = ta.getRestTable();
				restTable.setStatus("Desocupado");
				manageRestTable.updateRestTable(restTable);
				JOptionPane.showMessageDialog(null,
						"Se ha efectuado el pago completo de la cuenta total de la mesa, esta ventana se cerrará");
				this.secondaryStage.close();

			} else {

				// falta pagar todavia alguna alguna bill pendiente
				JOptionPane.showMessageDialog(null,
						"Pago efectuado, sin embargo existen todavia facturas pendientes por pagar");
			}
			loadBillsToChoiceBox(ta);
			fillTotalLabels();

		} else {
			JOptionPane.showMessageDialog(null, "No se puede efectuar un pago inferior al monto que indica la factura");

		}
	}

	@FXML
	public void generarPagoParcialSelected() {
		if (this.totalRecibido.getText() != "") {

			RestBillPayment rbp = new RestBillPayment();
			System.out.println("Esto trae billname: " + RestBillN);
			String[] bills = RestBillN.split("--");
			rbp.setAmount(Float.parseFloat(totalRecibido.getText()));
			rbp.setRestBill(new RestBill(Integer.parseInt(bills[1])));
			rbp.setCtgPaymentMethod(this.paymentMethod);
			manageRestBillPayment.insertRestBillPayment(rbp);
			RestTableAccount ta = MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount();
			// System.out.println("supuestamente " + totalAccount);
			if (manageRestBillPayment.isAmmountPaymentEqualOrMoreThanAccount(
					manageRestBill.getTotalAccountFromTable(ta),
					MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount())) {

				ta.setClosedDatetime(new Date());
				ta.setAccountStatus("Cerrada");

				manageRestTableAccount.updateRestTableAccount(ta);
				RestTable restTable = ta.getRestTable();
				restTable.setStatus("Desocupado");
				manageRestTable.updateRestTable(restTable);
				JOptionPane.showMessageDialog(null,
						"Se ha efectuado el pago completo de la cuenta total de la mesa, esta ventana se cerrará");
				this.secondaryStage.close();

			} else {

				// falta pagar todavia alguna alguna bill pendiente
				JOptionPane.showMessageDialog(null,
						"Pago efectuado, sin embargo existen todavia facturas pendientes por pagar");
			}
			loadBillsToChoiceBox(ta);
			fillTotalLabels();

		} else {
			JOptionPane.showMessageDialog(null, "No se puede efectuar un pago inferior al monto que indica la factura");

		}
	}

}
