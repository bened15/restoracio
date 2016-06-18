package com.jbd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

	public PaymentController() {

	}

	public void loadConfig(MainController mainC) {
		try {
			System.out.println("Numero de cuenta a pagar " + mainC.getRestTableAccount().getTableAccountId());
			enableAutowireCapabilities();

			this.secondaryStage.setTitle("Pago");
			this.secondaryStage.initModality(Modality.WINDOW_MODAL);
			this.secondaryStage.initOwner(Main.primaryStage);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../com/jbd/view/W_PaymentView.fxml"));
			AnchorPane paymentOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(paymentOverview);
			this.secondaryStage.setScene(scene);
			this.secondaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
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
		} else {
			totalRecibido.setText(totalRecibido.getText() + bClicked.getText());
		}
	}

	@FXML
	public void efectivoSelected() {
		this.numbersArea.setDisable(false);
	}

	@FXML
	public void pagoExactoSelected() {

	}

	@FXML
	public void tarjetaSelected() {

	}

	@FXML
	public void salirSelected() {
		this.secondaryStage.close();
	}

	@FXML
	public void generarPagoSelected() {

	}

}
