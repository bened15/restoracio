package com.jbd.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.jbd.hibernate.interfaces.ICtgDiscountManagement;
import com.jbd.hibernate.interfaces.ICtgPaymentMethodManagement;
import com.jbd.hibernate.interfaces.ICtgTipManagement;
import com.jbd.hibernate.interfaces.IRestBillDetailManagement;
import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.hibernate.interfaces.IRestBillPaymentManagement;
import com.jbd.hibernate.interfaces.IRestTableAccountManagement;
import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.CtgDiscount;
import com.jbd.model.CtgPaymentMethod;
import com.jbd.model.CtgTip;
import com.jbd.model.RestBill;
import com.jbd.model.RestBillDetail;
import com.jbd.model.RestBillPayment;
import com.jbd.model.RestOrder;
import com.jbd.model.RestTable;
import com.jbd.model.RestTableAccount;
import com.jbd.utils.Util;
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
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PaymentController {

	@Autowired
	private Stage secondaryStage;
	private static MainController mainAppC;
	@FXML
	private GridPane numbersArea;
	@Autowired
	private IRestTableManagement manageRestTables;
	@Autowired
	private IRestTableManagement manageRestTable;
	@FXML
	private Label totalPropina, totalRecibido, totalCuenta, totalCambio, totalDescuento, tableName, subtotal, propL,
			discountTitle;
	@FXML
	private AnchorPane headerAP;
	@FXML
	private AnchorPane principal;
	@FXML
	private TableView<RestOrder> ordersTable = new TableView<RestOrder>();
	private static final ChoiceBox facturaAPagar = new ChoiceBox();
	private static final ChoiceBox tipsC = new ChoiceBox();
	private DecimalFormat decimFormat = new DecimalFormat("#.00");
	@Autowired
	private ICtgTipManagement manageTips;
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
	private ICtgDiscountManagement manageCtgDiscount;
	@Autowired
	private CtgPaymentMethod paymentMethod;
	private static String RestBillN = "", TipName = "";

	private ObservableList<RestOrder> bDetailsOrders = FXCollections.observableArrayList();
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
			loader.setLocation(Main.class.getResource("/com/jbd/view/W_PaymentView.fxml"));
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

	private void loadTipsToChoiceBox() {
		try {
			Stage st = new Stage();
			st.initModality(Modality.WINDOW_MODAL);
			st.initOwner(secondaryStage);
			AnchorPane p = new AnchorPane();
			Scene scene = new Scene(p);
			p.setStyle("-fx-background-color:#9fb9b9");
			p.setPrefHeight(200.0);
			p.setPrefWidth(250.0);
			List<CtgTip> tips = manageTips.findAll();
			int i = 0;

			ObservableList<String> tipList = FXCollections.observableArrayList();
			while (i < tips.size()) {

				System.out.println("Valores" + tips.get(i).getTipName());
				tipList.add(tips.get(i).getTipName() + "--" + tips.get(i).getIdCtgTip());
				i++;
			}
			// facturaAPagar.setValue();
			this.tipsC.setItems(tipList);

			tipsC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue ov, Number value, Number new_value) {
					if (tipsC.getItems().size() > 0) {
						TipName = (String) tipsC.getItems().get((Integer) new_value);

					}
				}
			});
			PaymentController.tipsC.getSelectionModel().selectFirst();
			tipsC.setLayoutX(25);
			tipsC.setLayoutY(15);
			p.getChildren().add(tipsC);
			Button b = new Button();

			b.setLayoutX(25);
			b.setLayoutY(40);
			b.setText("Guardar");
			p.getChildren().add(b);
			b.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					String[] bills = RestBillN.split("--");
					String[] tips = TipName.split("--");
					CtgTip t = manageTips.findCtgTip(Integer.parseInt(tips[1]));
					System.out.println("Tip Seleccionada" + t.getTipName() + "Percente:" + t.getPercentValue());
					RestBill r = manageRestBill.findRestBill(Integer.parseInt(bills[1]));

					r.setCtgTip(t);
					r.setBillTip(r.getBillSubtotal() * (t.getPercentValue() / 100.0));
					r.setBillTotal(r.getBillSubtotal() * (1 + t.getPercentValue() / 100.0));

					manageRestBill.updateRestBill(r);

					List<RestBillDetail> rbd = manageRestBillDetail
							.findAllRestBillDetailFromRestBill(new RestBill(Integer.parseInt(bills[1])));
					int i = 0;
					while (i < rbd.size()) {
						RestBillDetail rbdd = rbd.get(i);
						System.out.println(rbdd.getBillDetailSubtotal() + "sdfsdfsdfs");

						rbdd.setBillDetailTotal(rbdd.getBillDetailSubtotal() * (1 + t.getPercentValue() / 100.0));

						manageRestBillDetail.updateRestBillDetail(rbdd);
						i++;

					}

					fillTotalLabels();
					JOptionPane.showMessageDialog(null, "Se efectuo el cambio de la propina");
					st.close();

				}

			});

			st.setScene(scene);
			st.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fillTotalLabels() {
		// actualizoLabels = true;
		int i = 0;
		double totalCuenta = 0.0, totalDiscount = 0.0;
		List<RestBillDetail> rbd;
		List<RestOrder> roL;

		String[] bills = RestBillN.split("--");
		// RestBill rb = null;
		rbd = manageRestBillDetail.findAllRestBillDetailFromRestBill(new RestBill(Integer.parseInt(bills[1])));

		if (rbd.size() > 0) {
			tableName.setText(
					rbd.get(0).getRestBill().getRestTableAccount().getRestTable().getTableName().toUpperCase());
			bDetailsOrders.clear();
			roL = manageRestBillDetail
					.findAllRestBillDetailFromRestBillForTable(new RestBill(Integer.parseInt(bills[1])));
			while (i < roL.size()) {
				RestOrder oo = roL.get(i);
				// uso este atributo solo para guardar el campo de de X para
				// mostrarlo en la tabla
				oo.setAttendant("X");
				oo.setNombFactura(rbd.get(0).getRestBill().getBillName());
				bDetailsOrders.add(oo);
				i++;
			}
			i = 0;
			while (i < rbd.size()) {
				// RestOrder ro = rbd.get(i).getRestOrder();
				// ro.setMenuItemName(ro.getRestMenuItem().getMenuItemName());
				// ro.setMenuItemPrice(Double.parseDouble(decimFormat.format(ro.getRestMenuItem().getMenuItemPrice())));
				// ro.setNombFactura(rbd.get(i).getRestBill().getBillName());
				// bDetailsOrders.add(ro);

				if (rbd.get(i).getRestBill().getCtgDiscount() != null) {
					totalCuenta = totalCuenta
							+ (rbd.get(i).getBillDetailSubtotal() - (rbd.get(i).getBillDetailSubtotal()
									* rbd.get(i).getRestBill().getCtgDiscount().getDiscountPercentage() / 100.0));
					totalDiscount = totalDiscount + rbd.get(i).getBillDetailSubtotal()
							* rbd.get(i).getRestBill().getCtgDiscount().getDiscountPercentage() / 100.0;
					this.discountTitle
					.setText("Descuento (" + rbd.get(0).getRestBill().getCtgDiscount().getDiscountPercentage() + "%)");
				} else {
					totalCuenta = totalCuenta + rbd.get(i).getBillDetailSubtotal();
				}

				i++;
			}

			// rb = manageRestBill.findRestBill(Integer.parseInt(bills[1]));
			// if (rb.getCtgDiscount() != null) {
			// System.out.println(totalCuenta);
			// System.out.println("etnre no es nulo el
			// disocun"+(rb.getCtgDiscount().getDiscountPercentage()/100.0));
			// this.totalCuenta.setText(decimFormat
			// .format(((totalCuenta) - (totalCuenta *
			// (rb.getCtgDiscount().getDiscountPercentage() / 100.0)))));

			// } else {
			// this.totalCuenta.setText(decimFormat.format(totalCuenta * 1.10));
			// }
			this.propL.setText("Propina (" + rbd.get(0).getRestBill().getCtgTip().getPercentValue() + "%)");

			this.subtotal.setText(decimFormat.format(totalCuenta));
			this.totalCuenta.setText(decimFormat
					.format(totalCuenta * (1 + (rbd.get(0).getRestBill().getCtgTip().getPercentValue()) / 100.0)));
			this.totalPropina.setText(
					decimFormat.format(totalCuenta * (rbd.get(0).getRestBill().getCtgTip().getPercentValue()) / 100.0));
			this.totalDescuento.setText(decimFormat
					.format(totalDiscount * (1 + (rbd.get(0).getRestBill().getCtgTip().getPercentValue()) / 100.0)));
			this.totalRecibido.setText("");
			this.totalCambio.setText("");
		}
		List<RestBillPayment> bp;
		bp = manageRestBillPayment.findRestBillPayments(Integer.parseInt(bills[1]));
		i = 0;
		if (bp.size() > 0) {
			totalCuenta = totalCuenta * (1 + (rbd.get(0).getRestBill().getCtgTip().getPercentValue()) / 100.0);
			while (i < bp.size()) {
				totalCuenta = totalCuenta - bp.get(i).getAmount();
				i++;
			}
			this.subtotal.setText(decimFormat
					.format(totalCuenta / (1 + (rbd.get(0).getRestBill().getCtgTip().getPercentValue()) / 100.0)));
			this.totalCuenta.setText(decimFormat.format(totalCuenta));
			// this.totalPropina.setText(decimFormat.format(totalCuenta *
			// 0.10));
			this.totalRecibido.setText("");
			this.totalCambio.setText("");
		}
		refreshTable();

	}

	private void enableAutowireCapabilities() {
		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	public static MainController getMainAppC() {
		return mainAppC;
	}

	public static void setMainAppC(MainController mainAppC) {
		PaymentController.mainAppC = mainAppC;
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
					// System.out.println("tiene punto y se intneto meter mas de
					// dos decimales");
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
		try {

			this.numbersArea.setDisable(false);
			this.totalCambio.setText("0.0");
			this.totalRecibido.setText(totalCuenta.getText());
			paymentMethod = manageCtgPaymentMethod.findCtgPaymentMethod("Tarjeta");
			this.commentForPaymentMethod = "";
			disableButtons(true);
			askForCardNumber();
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			Util.writeIntoErrorLog(errors.toString(), "");
		}
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
		Label l = new Label();
		l.setLayoutY(10);
		l.setLayoutX(10.0);
		l.setText("Seleccione primero la tarjeta y luego ingresa la informacion correspondiente");
		Stage st = new Stage();
		st.initModality(Modality.WINDOW_MODAL);
		st.initOwner(secondaryStage);

		AnchorPane p = new AnchorPane();

		p.setStyle("-fx-background-color:#9fb9b9");
		// p.setLayoutX(218.0);

		// p.setLayoutY(40.0);
		p.setPrefHeight(200.0);
		p.setPrefWidth(450.0);

		TextField txtComment = new TextField();
		txtComment.setLayoutX(10.0);

		txtComment.setLayoutY(110.0);
		txtComment.setPrefWidth(375.0);
		txtComment.setDisable(true);

		Button visa = new Button();
		Button mCard = new Button();
		Button aExpress = new Button();
		Button other = new Button();

		visa.setLayoutX(10);
		visa.setLayoutY(35);
		visa.setPrefHeight(50);
		visa.setPrefWidth(90);
		visa.setMaxSize(90.0, 50.0);

		mCard.setLayoutX(120);
		mCard.setLayoutY(35);
		mCard.setPrefHeight(50);
		mCard.setPrefWidth(90);
		mCard.setMaxSize(90.0, 50.0);

		aExpress.setLayoutX(230);
		aExpress.setLayoutY(35);
		aExpress.setPrefHeight(50);
		aExpress.setPrefWidth(90);
		aExpress.setMaxSize(90.0, 50.0);

		other.setLayoutX(340);
		other.setLayoutY(35);
		other.setPrefHeight(50);
		other.setPrefWidth(90);
		other.setMaxSize(90.0, 50.0);
		other.setText("Otro");

		visa.setStyle("-fx-graphic:url('/com/jbd/images/visa2.png');");
		mCard.setStyle("-fx-graphic:url('/com/jbd/images/master_card.jpg');");
		aExpress.setStyle("-fx-graphic:url('/com/jbd/images/aex.png');");

		txtComment.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					commentForPaymentMethod = commentForPaymentMethod + txtComment.getText();
					disableButtons(false);
					// headerAP.getChildren().remove(p);
					st.close();

				}

				if (event.getCode().equals(KeyCode.ESCAPE)) {
					commentForPaymentMethod = "";
					disableButtons(false);
					headerAP.getChildren().remove(p);
					st.close();
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
				commentForPaymentMethod = "Other ";
			}

		});

		p.getChildren().add(l);
		p.getChildren().add(txtComment);
		p.getChildren().add(visa);
		p.getChildren().add(mCard);
		p.getChildren().add(aExpress);
		p.getChildren().add(other);
		Scene scene = new Scene(p);
		st.setScene(scene);
		st.show();
		// principal.getChildren().add(p);
		// efe.applyFadeTransitionToTextField(txtComment);
		// txtComment.requestFocus();

	}

	private void loadTypesOfDiscounts() {
		Label l = new Label();
		l.setLayoutY(10);
		l.setLayoutX(20.0);
		l.setStyle("-fx-font-size: 14.0px;");
		l.setText("Seleccione el tipo de descuento a aplicar:");
		Stage st = new Stage();
		st.initModality(Modality.WINDOW_MODAL);
		st.initOwner(secondaryStage);

		AnchorPane p = new AnchorPane();

		p.setStyle("-fx-background-color:#9fb9b9");
		// p.setLayoutX(218.0);

		// p.setLayoutY(40.0);
		p.setPrefHeight(200.0);
		p.setPrefWidth(450.0);

		Button totalFactura = new Button();
		Button porItem = new Button();
		Button other = new Button();

		totalFactura.setLayoutX(50);
		totalFactura.setLayoutY(60);
		totalFactura.setPrefHeight(100);
		totalFactura.setPrefWidth(100);
		totalFactura.setText("Total de Factura");
		totalFactura.setTextAlignment(TextAlignment.CENTER);
		totalFactura.setWrapText(true);

		porItem.setLayoutX(160);
		porItem.setLayoutY(60);
		porItem.setPrefHeight(100);
		porItem.setPrefWidth(100);
		porItem.setText("Orden en particular");
		porItem.setTextAlignment(TextAlignment.CENTER);
		porItem.setWrapText(true);

		other.setLayoutX(270);
		other.setLayoutY(60);
		other.setPrefHeight(100);
		other.setPrefWidth(100);
		other.setText("Otro");
		other.setTextAlignment(TextAlignment.CENTER);
		other.setWrapText(true);

		totalFactura.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// String totalDeDescuento =
				// JOptionPane.showInputDialog("Ingrese porcentaje de descuento
				// a aplicar(%):");
				loadTypesOfDiscountsItems(manageCtgDiscount.findAll());
				st.close();

			}

		});
		porItem.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

			}

		});
		other.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

			}

		});

		p.getChildren().add(l);
		p.getChildren().add(totalFactura);
		p.getChildren().add(porItem);
		p.getChildren().add(other);

		Scene scene = new Scene(p);
		st.setScene(scene);
		st.show();
		// principal.getChildren().add(p);
		// efe.applyFadeTransitionToTextField(txtComment);
		// txtComment.requestFocus();

	}

	private void loadTypesOfDiscountsItems(List<CtgDiscount> discountItem) {
		AnchorPane ap = new AnchorPane();

		Stage st = new Stage();
		st.initModality(Modality.WINDOW_MODAL);
		st.initOwner(secondaryStage);
		int i = 0, pos = 1;
		int y = 10;

		while (i < discountItem.size()) {
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
			p.setStyle("-fx-background-color:#fffff;-fx-font-size:10px");
			p.setEffect(new InnerShadow());
			p.setId(String.valueOf(i));

			String menuItemName = "";
			if (discountItem.get(i).getDiscountName().length() > 30) {
				menuItemName = discountItem.get(i).getDiscountName().substring(0, 29) + "..";
			} else {
				menuItemName = discountItem.get(i).getDiscountName();

			}

			p.setText(menuItemName + "\n" + "%  " + discountItem.get(i).getDiscountPercentage());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					Button clickeado = (Button) arg0.getSource();
					efe.applyFadeTransitionToButton(clickeado);
					String[] bills = RestBillN.split("--");
					RestBill rb = manageRestBill.findRestBill(Integer.parseInt(bills[1]));

					rb.setCtgDiscount(discountItem.get(Integer.parseInt(clickeado.getId())));
					manageRestBill.updateRestBill(rb);
					loadConfig();
					st.close();
					JOptionPane.showMessageDialog(null, "El descuento ha sido aplicado", "Alerta",
							JOptionPane.INFORMATION_MESSAGE);

				}
			});

			p.setStyle("-fx-background-color:#9fb9b9");
			ap.getChildren().add(p);
			i++;
			pos++;
			// p.setLayoutX(218.0);

			// p.setLayoutY(40.0);

		}
		ap.setPrefHeight(600.0);
		ap.setPrefWidth(650.0);

		Scene scene = new Scene(ap);
		st.setScene(scene);
		st.show();

	}

	private void loadTypesOfTips(List<CtgTip> tipItem) {
		AnchorPane ap = new AnchorPane();

		Stage st = new Stage();
		st.initModality(Modality.WINDOW_MODAL);
		st.initOwner(secondaryStage);
		int i = 0, pos = 1;
		int y = 10;

		while (i < tipItem.size()) {
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
			p.setStyle("-fx-background-color:#fffff;-fx-font-size:10px");
			p.setEffect(new InnerShadow());
			p.setId(String.valueOf(i));

			String item = "";
			if (tipItem.get(i).getTipName().length() > 30) {
				item = tipItem.get(i).getTipName().substring(0, 29) + "..";
			} else {
				item = tipItem.get(i).getTipName();

			}

			p.setText(item + "\n" + "%  " + tipItem.get(i).getPercentValue());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					Button clickeado = (Button) arg0.getSource();
					efe.applyFadeTransitionToButton(clickeado);
					String[] bills = RestBillN.split("--");
					RestBill r = manageRestBill.findRestBill(Integer.parseInt(bills[1]));
					CtgTip t = tipItem.get(Integer.parseInt(clickeado.getId()));
					System.out.println("Tip Seleccionada" + t.getTipName() + "Percente:" + t.getPercentValue());

					r.setCtgTip(t);
					r.setBillTip(r.getBillSubtotal() * (t.getPercentValue() / 100.0));
					r.setBillTotal(r.getBillSubtotal() * (1 + t.getPercentValue() / 100.0));

					manageRestBill.updateRestBill(r);

					List<RestBillDetail> rbd = manageRestBillDetail
							.findAllRestBillDetailFromRestBill(new RestBill(Integer.parseInt(bills[1])));
					int i = 0;
					while (i < rbd.size()) {
						RestBillDetail rbdd = rbd.get(i);
						System.out.println(rbdd.getBillDetailSubtotal() + "sdfsdfsdfs");

						rbdd.setBillDetailTotal(rbdd.getBillDetailSubtotal() * (1 + t.getPercentValue() / 100.0));

						manageRestBillDetail.updateRestBillDetail(rbdd);
						i++;

					}

					fillTotalLabels();
					JOptionPane.showMessageDialog(null, "Se efectuo el cambio de la propina");
					st.close();

				}
			});

			p.setStyle("-fx-background-color:#9fb9b9");
			ap.getChildren().add(p);
			i++;
			pos++;
			// p.setLayoutX(218.0);

			// p.setLayoutY(40.0);

		}
		ap.setPrefHeight(600.0);
		ap.setPrefWidth(650.0);

		Scene scene = new Scene(ap);
		st.setScene(scene);
		st.show();

	}

	private void askForCuponNumber() {
		String cuponNum = JOptionPane.showInputDialog("Ingrese el numero de cupon");
		commentForPaymentMethod = "Cupon # " + cuponNum;
		disableButtons(false);

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
		loadTypesOfDiscounts();

	}

	@FXML
	public void prefacturaSelected() {
		System.out.println("Aqui va el codigo de benji para imprimir pre factura");
	}

	@FXML
	public void salirSelected() {
		this.secondaryStage.close();
	}

	@FXML
	public void generarPagoSelected() {
		try {

			if (Double.parseDouble(this.totalCambio.getText()) >= 0 && this.totalCambio.getText() != ""
					&& this.totalRecibido.getText() != "") {

				RestBillPayment rbp = new RestBillPayment();
				System.out.println("Esto trae billname: " + RestBillN);
				String[] bills = RestBillN.split("--");
				rbp.setAmount(Float.parseFloat(totalRecibido.getText()) - Float.parseFloat(totalCambio.getText()));
				rbp.setRestBill(new RestBill(Integer.parseInt(bills[1])));
				rbp.setCtgPaymentMethod(this.paymentMethod);
				rbp.setComments(commentForPaymentMethod);
				rbp = manageRestBillPayment.insertRestBillPayment(rbp);
				RestTableAccount ta = MainController.getBillsDetailQuantity().get(0).getRestBill()
						.getRestTableAccount();
				RestBill rb = manageRestBill.findRestBill(Integer.parseInt(bills[1]));
				// System.out.println("supuestamente " + totalAccount);
				if (manageRestBillPayment.isAmmountPaymentEqualOrMoreThanAccount(
						Double.parseDouble(decimFormat.format(manageRestBill.getTotalAccountFromTableCheckingDiscount(ta, rb))),
						MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount())) {

					ta.setClosedDatetime(new Date());
					ta.setAccountStatus("CLOSED");
					ta.setRestShift1(MainController.getRestS());

					manageRestTableAccount.updateRestTableAccount(ta);
					RestTable restTable = ta.getRestTable();
					System.out.println(restTable.getTableName());
					restTable.setStatus("DESOCUPADO");
					manageRestTable.updateRestTable(restTable);
					JOptionPane.showMessageDialog(null,
							"Se ha efectuado el pago completo de la cuenta total de la mesa, esta ventana se cerrará");
					// MainController mc = new MainController();
					this.mainAppC.opcionSelected = 2;
					this.mainAppC.loadPanesForTables(manageRestTables.findTablesByArea(restTable.getRestArea()),
							MainController.principalEstatica, "#f4efd8");

					this.secondaryStage.close();

				} else {

					// falta pagar todavia alguna alguna bill pendiente
					JOptionPane.showMessageDialog(null,
							"Pago efectuado, sin embargo existen todavia facturas pendientes por pagar");
				}
				loadBillsToChoiceBox(ta);
				fillTotalLabels();

			} else {
				JOptionPane.showMessageDialog(null,
						"No se puede efectuar un pago inferior al monto que indica la factura");

			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			Util.writeIntoErrorLog(errors.toString(), "");
			e.printStackTrace();
		}
	}

	@FXML
	public void generarPagoParcialSelected() {
		try {

			if (this.totalRecibido.getText() != "") {

				RestBillPayment rbp = new RestBillPayment();
				System.out.println("Esto trae billname: " + RestBillN);
				String[] bills = RestBillN.split("--");
				rbp.setAmount(Float.parseFloat(totalRecibido.getText()));
				rbp.setRestBill(new RestBill(Integer.parseInt(bills[1])));
				rbp.setCtgPaymentMethod(this.paymentMethod);
				rbp = manageRestBillPayment.insertRestBillPayment(rbp);
				RestTableAccount ta = MainController.getBillsDetailQuantity().get(0).getRestBill()
						.getRestTableAccount();
				// System.out.println("supuestamente " + totalAccount);
				RestBill rb = manageRestBill.findRestBill(Integer.parseInt(bills[1]));
				if (manageRestBillPayment.isAmmountPaymentEqualOrMoreThanAccount(
						Double.parseDouble(decimFormat.format(manageRestBill.getTotalAccountFromTableCheckingDiscount(ta, rb))),
						MainController.getBillsDetailQuantity().get(0).getRestBill().getRestTableAccount())) {

					ta.setClosedDatetime(new Date());
					ta.setAccountStatus("CLOSED");
					ta.setRestShift1(MainController.getRestS());
					manageRestTableAccount.updateRestTableAccount(ta);
					RestTable restTable = ta.getRestTable();
					restTable.setStatus("DESOCUPADO");
					manageRestTable.updateRestTable(restTable);
					JOptionPane.showMessageDialog(null,
							"Se ha efectuado el pago completo de la cuenta total de la mesa, esta ventana se cerrará");

					this.mainAppC.opcionSelected = 2;
					this.mainAppC.loadPanesForTables(manageRestTables.findTablesByArea(restTable.getRestArea()),
							MainController.principalEstatica, "#f4efd8");
					this.secondaryStage.close();

				} else {

					// falta pagar todavia alguna alguna bill pendiente
					JOptionPane.showMessageDialog(null,
							"Pago efectuado, sin embargo existen todavia facturas pendientes por pagar");
				}
				loadBillsToChoiceBox(ta);
				fillTotalLabels();

			} else {
				JOptionPane.showMessageDialog(null,
						"No se puede efectuar un pago inferior al monto que indica la factura");

			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			Util.writeIntoErrorLog(errors.toString(), "");
			e.printStackTrace();
		}
	}

	// private void loadImg() {
	//
	// ByteArrayInputStream bais = new
	// ByteArrayInputStream(menuItemSelected.getMenuImage());
	// BufferedImage imageBuffer;
	// try {
	// imageBuffer = ImageIO.read(bais);
	// Image imageLoad = SwingFXUtils.toFXImage(imageBuffer, null);
	// menuItemImage.setImage(imageLoad);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	private void refreshTable() {

		ordersTable.getColumns().clear();

		TableColumn id = new TableColumn("Id");
		id.setPrefWidth(10.0);
		TableColumn elemento = new TableColumn("Elemento");
		TableColumn precio = new TableColumn("Precio($)");
		TableColumn elPor = new TableColumn("-");
		TableColumn cantidad = new TableColumn("Cantidad");
		TableColumn facturaN = new TableColumn("Factura");
		facturaN.setPrefWidth(15.0);
		// TableColumn total = new TableColumn("Total");

		cantidad.setStyle("-fx-font-weight:bold;-fx-text-alignment: center;");
		precio.setStyle("-fx-font-weight:bold;-fx-text-alignment: center;");
		elPor.setStyle("-fx-text-alignment: center;");

		id.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("idMenuItem"));
		elemento.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("menuItemName"));
		precio.setCellValueFactory(new PropertyValueFactory<RestOrder, Double>("menuItemPrice"));
		elPor.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("attendant"));
		cantidad.setCellValueFactory(new PropertyValueFactory<RestOrder, Integer>("cantidad"));
		facturaN.setCellValueFactory(new PropertyValueFactory<RestOrder, String>("nombFactura"));

		// total.setCellValueFactory(new PropertyValueFactory<>("total"));

		// itemsLocalList.setItems(itemsList);
		// itemsLocalList.getColumns().addAll(id,elemento, precio, total);
		ordersTable.setItems(bDetailsOrders);
		ordersTable.getColumns().addAll(id, elemento, precio, elPor, cantidad, facturaN);

		ordersTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

				}
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

				}
				if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {

				}
			}
		});

	}

	@FXML
	public void changeTip() {

		loadTypesOfTips(manageTips.findAll());

	}
}
