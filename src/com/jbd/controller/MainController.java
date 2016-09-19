package com.jbd.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.jbd.hibernate.interfaces.ICtgMenuSubTypeManagement;
import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.hibernate.interfaces.ICtgTipManagement;
import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.hibernate.interfaces.IRestBillDetailManagement;
import com.jbd.hibernate.interfaces.IRestBillManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemProductManagement;
import com.jbd.hibernate.interfaces.IRestOrderManagement;
import com.jbd.hibernate.interfaces.IRestShiftManagement;
import com.jbd.hibernate.interfaces.IRestTableAccountManagement;
import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.CtgMenuSubType;
import com.jbd.model.CtgMenuType;
import com.jbd.model.CtgTip;
import com.jbd.model.RestArea;
import com.jbd.model.RestBill;
import com.jbd.model.RestBillDetail;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestOrder;
import com.jbd.model.RestShift;
import com.jbd.model.RestTable;
import com.jbd.model.RestTableAccount;
import com.jbd.utils.Effect;
import com.jbd.utils.Printer;
import com.jbd.utils.Util;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class MainController {

	private Main mainApp;

	private double priceTotal = 0;
	private DecimalFormat decimFormat = new DecimalFormat("#.00");
	@FXML
	private AnchorPane principal;
	public static AnchorPane principalEstatica;
	@FXML
	private AnchorPane rightSide;
	@FXML
	private AnchorPane menuTypePane;
	@FXML
	private Button confirmButton;
	@FXML
	private Button nuevaOrden;
	@FXML
	private Button ordenes;
	@FXML
	private Button paraLlevar;
	@FXML
	private Button cerrarTurno;
	private static PaymentController payment = new PaymentController();
	private static SplitOrderController splitController = new SplitOrderController();
	public static int opcionSelected = 0;
	private boolean editandoOrden = false;
	// @Autowired
	// private ISysUserRolManagement manageSysUserRol;
	UserSecurityController usSecurity = new UserSecurityController();
	@Autowired
	private IRestAreaManagement manageRestAreas;
	@Autowired
	private IRestOrderManagement manageRestOrders;
	@Autowired
	private IRestTableManagement manageRestTables;
	@Autowired
	private ICtgMenuTypeManagement manageCtgMenuType;
	@Autowired
	private ICtgMenuSubTypeManagement manageCtgSubMenuType;
	@Autowired
	private IRestMenuItemManagement manageRestMenuItem;
	@Autowired
	private IRestMenuItemProductManagement manageRestMenuItemProduct;
	@Autowired
	private IRestTableAccountManagement manageRestTableAccount;
	@Autowired
	private IRestBillManagement manageRestBill;
	@Autowired
	private IRestShiftManagement manageRestShift;
	@Autowired
	private IRestBillDetailManagement manageRestBillDetail;
	@Autowired
	private RestTable restTable;
	@Autowired
	private RestArea restArea;
	@Autowired
	private RestTableAccount restTableAccount;
	@Autowired
	private RestBillDetail restBillDetail;
	@Autowired
	private static RestShift restS;
	@Autowired
	private ICtgTipManagement manageTip;
	@Autowired
	private static CtgTip tip;

	private Button aRemoverEffect;
	@FXML
	private TextField subtotalL, ttotalL, propinaL;

	private static List<RestBill> billsQuantity = new ArrayList<RestBill>();
	private static List<RestBillDetail> billsDetailQuantity = new ArrayList<RestBillDetail>();
	private static List<RestBillDetail> billsDetailQuantityAddition = new ArrayList<RestBillDetail>();
	private static List<RestMenuItem> listItemsR = new ArrayList<RestMenuItem>();

	@Autowired
	private Effect efe;
	private ObservableList<RestMenuItem> itemsList = FXCollections.observableArrayList();
	private ObservableList<RestMenuItem> itemsListAddition = FXCollections.observableArrayList();
	private static ObservableList<RestOrder> itemsListOrders = FXCollections.observableArrayList();
	@FXML
	private TableView<RestMenuItem> itemsOrderTable = new TableView<RestMenuItem>();

	@FXML
	private Label detalleTable;
	int optionSplit = 0, inProcess = 0;

	public static CtgTip getTip() {
		return tip;
	}

	public static void setTip(CtgTip tip) {
		MainController.tip = tip;
	}

	public static RestShift getRestS() {
		return restS;
	}

	public static void setRestS(RestShift restS) {
		MainController.restS = restS;
	}

	public static ObservableList<RestOrder> getItemsListOrders() {
		return itemsListOrders;
	}

	public static void setItemsListOrders(ObservableList<RestOrder> itemsListOrders) {
		MainController.itemsListOrders = itemsListOrders;
	}

	public static List<RestBill> getBillsQuantity() {
		return billsQuantity;
	}

	public static void setBillsQuantity(List<RestBill> billsQuantity) {
		MainController.billsQuantity = billsQuantity;
	}

	public IRestAreaManagement getManageRestAreas() {
		return manageRestAreas;
	}

	public void setManageRestAreas(IRestAreaManagement manageRestAreas) {
		this.manageRestAreas = manageRestAreas;
	}

	public IRestTableManagement getManageRestTables() {
		return manageRestTables;
	}

	public void setManageRestTables(IRestTableManagement manageRestTables) {
		this.manageRestTables = manageRestTables;
	}

	public ICtgMenuTypeManagement getManageCtgMenuType() {
		return manageCtgMenuType;
	}

	public void setManageCtgMenuType(ICtgMenuTypeManagement manageCtgMenuType) {
		this.manageCtgMenuType = manageCtgMenuType;
	}

	public IRestMenuItemManagement getManageRestMenuItem() {
		return manageRestMenuItem;
	}

	public void setManageRestMenuItem(IRestMenuItemManagement manageRestMenuItem) {
		this.manageRestMenuItem = manageRestMenuItem;
	}

	public IRestMenuItemProductManagement getManageRestMenuItemProduct() {
		return manageRestMenuItemProduct;
	}

	public void setManageRestMenuItemProduct(IRestMenuItemProductManagement manageRestMenuItemProduct) {
		this.manageRestMenuItemProduct = manageRestMenuItemProduct;
	}

	public IRestBillManagement getManageRestBill() {
		return manageRestBill;
	}

	public void setManageRestBill(IRestBillManagement manageRestBill) {
		this.manageRestBill = manageRestBill;
	}

	public IRestBillDetailManagement getManageRestBillDetail() {
		return manageRestBillDetail;
	}

	public void setManageRestBillDetail(IRestBillDetailManagement manageRestBillDetail) {
		this.manageRestBillDetail = manageRestBillDetail;
	}

	public RestBillDetail getRestBillDetail() {
		return restBillDetail;
	}

	public void setRestBillDetail(RestBillDetail restBillDetail) {
		this.restBillDetail = restBillDetail;
	}

	public Effect getEfe() {
		return efe;
	}

	public void setEfe(Effect efe) {
		this.efe = efe;
	}

	public AnchorPane getPrincipal() {
		return principal;
	}

	public static AnchorPane getPrincipalEstatica() {
		return principalEstatica;
	}

	public static void setPrincipalEstatica(AnchorPane principalEstatica) {
		MainController.principalEstatica = principalEstatica;
	}

	public AnchorPane getRightSide() {
		return rightSide;
	}

	public void setRightSide(AnchorPane rightSide) {
		this.rightSide = rightSide;
	}

	public void setPrincipal(AnchorPane principal) {
		this.principal = principal;
	}

	@FXML
	public void openTableLocation(MouseEvent event) {

		try {
			if (verifyPendingOrderToComplete()) {
				Button b = (Button) event.getSource();
				if (b.getId().contains("nOrder")) {
					opcionSelected = 1;
					itemsList.clear();
					itemsListOrders.clear();
					billsDetailQuantity.clear();
					billsDetailQuantityAddition.clear();
					itemsListAddition.clear();
					billsQuantity.clear();
					editandoOrden = false;

					// refreshTable();
					disableControls(false);

				}
				if (b.getId().contains("vOrder")) {
					opcionSelected = 2;
					itemsList.clear();
					itemsListOrders.clear();
					billsDetailQuantity.clear();
					billsDetailQuantityAddition.clear();
					itemsListAddition.clear();
					billsQuantity.clear();
					editandoOrden = false;

					disableControls(true);

				}
				if (b.getId().contains("lleOrder")) {
					opcionSelected = 1;
					itemsList.clear();
					itemsListOrders.clear();
					billsDetailQuantity.clear();
					billsDetailQuantityAddition.clear();
					itemsListAddition.clear();
					billsQuantity.clear();

					editandoOrden = false;

					// refreshTable();
					disableControls(false);

				}

				principal.getChildren().clear();
				menuTypePane.getChildren().clear();
				loadPanesForAreas(manageRestAreas.getAllAreas(), principal, "#6d71a5");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadPanesForAreas(List<RestArea> areas, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < areas.size()) {
			Button p = new Button();

			p.setPrefHeight(120);
			p.setPrefWidth(120);

			switch (pos) {
			case 1:
				p.setLayoutX(10);
				break;
			case 2:
				p.setLayoutX(140);
				break;
			case 3:
				p.setLayoutX(300);
				break;
			case 4:
				p.setLayoutX(430);
				break;

			}
			p.setLayoutY(y);
			if (pos % 4 == 0) {
				y = y + 140;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color
					+ ";-fx-border-radius: 10.0px;-fx-border-color:#40441e;-fx-font-weight:bold;");
			p.setTextFill(Color.GHOSTWHITE);

			p.setId(String.valueOf(areas.get(i).getAreaId()));
			p.setText(areas.get(i).getAreaName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setEffect(new DropShadow());

			System.out.println("valor de i:" + i);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					ap.getChildren().clear();

					Button clickeado = (Button) arg0.getSource();

					System.out.println("valor de area seleccionada:" + clickeado.getId());
					// RestArea area = new RestArea();

					restArea.setAreaId(Integer.parseInt(clickeado.getId()));
					loadPanesForTables(manageRestTables.findTablesByArea(restArea), ap, "#f4efd8");

					// }

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);
			i++;
			pos++;
		}

	}

	public void loadPanesForTables(List<RestTable> tables, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;
		if (!editandoOrden) {
			menuTypePane.getChildren().clear();
			priceTotal = 0.0;

		}

		while (i < tables.size()) {
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
			// case 7:
			// p.setLayoutX(550);
			// break;

			}
			p.setLayoutY(y);
			if (pos % 6 == 0) {
				y = y + 90;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}

			p.setId(String.valueOf(tables.get(i).getTableId()));
			p.setText(tables.get(i).getTableName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setEffect(new InnerShadow());

			if (manageRestTableAccount.isRestTableAccountOpen(tables.get(i))) {
				p.setStyle("-fx-background-color: #da6377;");
				p.setEffect(new DropShadow());
				p.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {

						// JOptionPane.showMessageDialog(null, "Alerta" +
						// listaObjeto.getClass());
						System.out.println("opciton seleconadad" + opcionSelected);

						if (opcionSelected == 2) {

							menuTypePane.getChildren().clear();

							Button clickeado = (Button) arg0.getSource();

							efe.applyFadeTransitionToButton(clickeado);
							restTable = manageRestTables.findRestTable(Integer.parseInt(clickeado.getId()));
							fillTotalWithTotalBill(manageRestTableAccount.findRestTableAccountOpen(restTable));

							List<String> op = new ArrayList<String>();
							op.add("Pagar");
							op.add("Cancelar Orden");
							op.add("Dividir Orden");
							op.add("Editar Orden");
							op.add("Trasladar Cuenta-Cambio de Mesa");

							// en el mismo anchor pane de menuType se ponen las
							// opciones
							detalleTable.setText("Detalle Mesa : " + restTable.getTableName());
							detalleTable.setStyle("-fx-font-weight:bold;");
							if (aRemoverEffect != null) {
								aRemoverEffect.setStyle("-fx-background-color: #da6377;");
							}

							aRemoverEffect = clickeado;
							clickeado.setStyle("-fx-background-color: #e3cb66;");

							loadOptionButtons(op, restTable, menuTypePane, "#2eb7e1");
						}

					}

				});
			} else {
				p.setStyle("-fx-background-color: " + color + ";");
				p.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						if (opcionSelected == 1) {

							// se pregunta si seran cuentas separadas.
							priceTotal = 0.0;

							ap.getChildren().clear();
							// JOptionPane.showMessageDialog(null, "Alerta" +
							// listaObjeto.getClass());
							Button clickeado = (Button) arg0.getSource();
							efe.applyFadeTransitionToButton(clickeado);
							//
							restTable = manageRestTables.findRestTable(Integer.parseInt(clickeado.getId()));
							//
							// loadPanesForMenuType(manageCtgMenuType.loadMenuType(),
							// menuTypePane, "#eadfff");
							List<String> billsNo = new ArrayList<String>();
							billsNo.add("1");
							billsNo.add("2");
							billsNo.add("3");
							billsNo.add("4");
							billsNo.add("5");
							billsNo.add("6");
							billsNo.add("7");
							billsNo.add("8");
							billsNo.add("9");
							billsNo.add("Otra");

							Pane p = new Pane();

							p.setStyle(
									"-fx-background-color:#c1ebff;-fx-border-radius: 10.0px;-fx-border-color: aliceblue ;");
							p.setPrefHeight(500.0);
							p.setPrefWidth(500.0);
							p.setLayoutX(5.0);
							p.setLayoutY(5.0);
							ap.getChildren().add(p);
							// efe.applyFadeTransitionToRectangle(p);
							optionSplit = 0;
							detalleTable.setText("Detalle Mesa : " + restTable.getTableName());
							detalleTable.setStyle("-fx-font-weight:bold;");
							loadPanesForHowManyBills(billsNo, p, color);
						}

					}

				});
			}
			// if (p.getId().contains(areaAnterior) && (areaAnterior!="" ||
			// areaAnterior!=null )) {
			//
			// } else {
			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			// }

			i++;
			pos++;
		}
	}

	public void loadPanesForMenuSubType(List<CtgMenuSubType> menuSubType, AnchorPane ap, String color, int y, int pos) {
		int i = 0;
		// int y = 5;

		while (i < menuSubType.size()) {
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
			// case 7:
			// p.setLayoutX(610);
			// break;

			}
			p.setLayoutY(y);
			if (pos % 6 == 0) {
				y = y + 90;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}

			p.setStyle("-fx-background-color: " + color + ";");

			p.setId(String.valueOf(i));
			p.setText(menuSubType.get(i).getMenuSubTypeName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setEffect(new DropShadow());

			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					CtgMenuSubType type = new CtgMenuSubType();
					type = menuSubType.get(Integer.parseInt(clickeado.getId()));
					principal.getChildren().clear();

					if (aRemoverEffect != null) {
						aRemoverEffect.setStyle("-fx-background-color: " + color + ";");
					}

					aRemoverEffect = clickeado;
					clickeado.setStyle("-fx-background-color: #168d81;");

					// if (type.getMenuTypeId() == 0) {
					// loadPanesForMenuItem(
					// manageRestMenuItem.findMenuItemByName(
					// JOptionPane.showInputDialog("Ingrese el nombre del menu a
					// buscar:")),
					// principal, "#FEFCB8");
					// } else {
					loadPanesForMenuItem(manageRestMenuItem.findMenuItemBySubTypeMenu(type), principal, "#FEFCB8");
					// }

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			i++;
			pos++;
		}
	}

	public void loadPanesForMenuType(List<CtgMenuType> menuType, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 5;
		menuType.add(0, new CtgMenuType(0, "Buscar..."));

		while (i < menuType.size()) {
			Button p = new Button();

			p.setPrefHeight(70);
			p.setPrefWidth(90);

			switch (pos) {
			case 1:
				p.setLayoutX(5);
				break;
			case 2:
				p.setLayoutX(105);
				break;
			case 3:
				p.setLayoutX(205);
				break;
			case 4:
				p.setLayoutX(305);
				break;
			case 5:
				p.setLayoutX(405);
				break;
			case 6:
				p.setLayoutX(505);
				break;
			// case 7:
			// p.setLayoutX(610);
			// break;

			}
			p.setLayoutY(y);
			if (pos % 6 == 0) {
				y = y + 75;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			if (i == 0) {

				p.setStyle("-fx-background-color: #b2f289;");
			} else {
				p.setStyle("-fx-background-color: " + color + ";");
			}

			p.setId(String.valueOf(menuType.get(i).getMenuTypeId()));
			p.setText(menuType.get(i).getMenuTypeName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setEffect(new DropShadow());

			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					CtgMenuType type = new CtgMenuType();
					type.setMenuTypeId(Integer.parseInt(clickeado.getId()));
					principal.getChildren().clear();

					if (aRemoverEffect != null) {
						aRemoverEffect.setStyle("-fx-background-color: " + color + ";");
					}

					aRemoverEffect = clickeado;
					clickeado.setStyle("-fx-background-color: #168d81;");

					if (type.getMenuTypeId() == 0) {
						loadPanesForMenuItem(
								manageRestMenuItem.findMenuItemByName(
										JOptionPane.showInputDialog("Ingrese el nombre del menu a buscar:")),
								principal, "#FEFCB8");
					} else {
						List<Integer> value = new ArrayList<Integer>();

						value = loadPanesForMenuItem(manageRestMenuItem.findMenuItemByTypeMenu(type), principal,
								"#FEFCB8");
						loadPanesForMenuSubType(manageCtgSubMenuType.findAllByType(type), principal, "#3DA3BF",
								value.get(1), value.get(0));
					}

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			i++;
			pos++;
		}
	}

	public List<Integer> loadPanesForMenuItem(List<RestMenuItem> menuItem, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < menuItem.size()) {
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
			// case 7:
			// p.setLayoutX(550);
			// break;

			}
			p.setLayoutY(y);
			if (pos % 6 == 0) {
				y = y + 90;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-font-size:10px");
			p.setEffect(new InnerShadow());
			p.setId(String.valueOf(i));

			String menuItemName = "";
			if (menuItem.get(i).getMenuItemName().length() > 30) {
				menuItemName = menuItem.get(i).getMenuItemName().substring(0, 29) + "..";
			} else {
				menuItemName = menuItem.get(i).getMenuItemName();

			}

			p.setText(menuItemName + "\n" + "$  " + menuItem.get(i).getMenuItemPrice());

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
					RestMenuItem item = new RestMenuItem();
					item = menuItem.get(Integer.parseInt(clickeado.getId()));

					// String[] partida = clickeado.getText().split("\n");
					// item.setMenuItemId(Integer.parseInt(clickeado.getId()));
					// item.setMenuItemName(partida[0]);
					// item.setMenuItemPrice(Float.parseFloat(partida[1].trim().substring(1,
					// partida[1].length())));

					priceTotal = priceTotal + item.getMenuItemPrice();

					subtotalL.setText("$" + decimFormat.format(priceTotal));
					propinaL.setText("(" + tip.getPercentValue() + "%) $"
							+ decimFormat.format(priceTotal * tip.getPercentValue() / 100.0));
					ttotalL.setText(
							"$" + decimFormat.format(priceTotal + (priceTotal * tip.getPercentValue() / 100.0)));

					// System.out.println("Tamaño de lista" + itemsList.size());
					// rightSide.getChildren().add(refreshTable());
					// refreshTable();
					if (editandoOrden) {
						// si es editando orden no se sabe cuantas facturas hay,
						// por eso se hace esta consulta

						billsQuantity = manageRestBill.findBillsWithRestTableAccount(restTableAccount);

					}
					if (billsQuantity.size() == 1) {

						RestBillDetail billDetail = new RestBillDetail();
						RestBill bill = billsQuantity.get(0);
						// bill.setBillSubtotal(Double
						// .parseDouble(decimFormat.format(bill.getBillSubtotal()
						// + item.getMenuItemPrice())));
						// bill.setBillTip(Double.parseDouble(
						// decimFormat.format(bill.getBillSubtotal() *
						// tip.getPercentValue() / 100.0)));
						// bill.setBillTotal(Double.parseDouble(
						// decimFormat.format(bill.getBillSubtotal() * (1 +
						// (tip.getPercentValue() / 100.0)))));
						// System.out.println("la primera llamada" +
						// bill.getBillTotal());

						billDetail.setRestBill(bill);
						billDetail.setBillDetailSubtotal(item.getMenuItemPrice());
						billDetail.setBillDetailTotal(item.getMenuItemPrice() * (1 + (tip.getPercentValue() / 100.0)));

						item.setNombFactura(bill.getBillName());
						if (editandoOrden) {
							inProcess = 1;
							item.setIdentifier(itemsListAddition.size() + 1);
							itemsListAddition.add(item);
							billsDetailQuantityAddition.add(billDetail);

						} else {
							item.setIdentifier(itemsList.size() + 1);
							itemsList.add(item);
							billsDetailQuantity.add(billDetail);

						}
						// manageRestBill.updateRestBill(bill);
						refreshTable();
					} else {
						Pane p = new Pane();
						p.setStyle(
								"-fx-background-color:#c1ebff;-fx-border-radius: 10.0px;-fx-border-color: aliceblue ;");
						p.setPrefWidth(810.0);
						p.setPrefHeight(700.0);
						ap.getChildren().add(p);
						efe.applyFadeTransitionToRectangle(p);
						// fillListView(p, item);
						loadPanesForBills(MainController.getBillsQuantity(), p, color, item);

					}

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);
			i++;
			pos++;
		}
		List<Integer> posY = new ArrayList<Integer>();
		posY.add(pos);
		posY.add(y);
		return posY;
	}

	public void loadPanesForHowManyBills(List<String> bills, Pane ap, String color) {
		SplitOrderController.setMainAppC(this);
		int i = 0, pos = 1;
		int y = 30;
		Label l = new Label();
		l.setTextAlignment(TextAlignment.CENTER);
		l.setStyle("-fx-background-color:white;-fx-font-weight:bold;-fx-font-size:13px;");
		l.setPrefHeight(25);
		l.setPrefWidth(ap.getPrefWidth());
		l.setText("Cuantas Facturas");
		ap.getChildren().add(l);
		while (i < bills.size()) {
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

			}
			p.setLayoutY(y);
			if (pos % 4 == 0) {
				y = y + 90;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-font-size:8px");

			p.setId(bills.get(i));

			p.setText(bills.get(i));
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setEffect(new InnerShadow());
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					int i = 0;
					billsQuantity.clear();
					billsDetailQuantity.clear();
					if (optionSplit == 0) {
						usSecurity.loadConfig();
						String codeWaitress = usSecurity.verifyWaitress();
						if (codeWaitress != "") {

							RestTableAccount tableAccount = new RestTableAccount();
							tableAccount.setRestTable(restTable);

							tableAccount.setCreatedBy(codeWaitress);
							tableAccount.setCreatedDatetime(new Date());

							tableAccount.setRestShift1(null);
							tableAccount.setRestShift2(restS);
							tableAccount.setAccountStatus("OPEN");

							tableAccount
									.setGuestNum(Integer.parseInt(JOptionPane.showInputDialog("Numero de Personas")));
							restTableAccount = manageRestTableAccount.insertRestTableAccount(tableAccount);

							inProcess = 1;

							while (i < Integer.valueOf(clickeado.getText())) {

								RestBill bill = new RestBill();

								bill.setBillName("Cuenta-" + (i + 1));
								bill.setEntryDate(new Date());
								bill.setEntryUser(codeWaitress);
								bill.setBillTip(0);
								bill.setBillSubtotal(0);
								bill.setBillTotal(0);
								bill.setRestTableAccount(restTableAccount);
								bill.setCtgTip(tip);
								bill.setShiftId(restS.getIdShift());

								billsQuantity.add(manageRestBill.insertRestBill(bill));
								i++;

							}
							principal.getChildren().clear();

							efe.applyFadeTransitionToButton(clickeado);
							//
							// restTable.setTableId(Integer.parseInt(clickeado.getId()));
							//
							optionSplit = 0;
							loadPanesForMenuType(manageCtgMenuType.loadMenuType(), menuTypePane, "#3DA3BF");

						} else {
							JOptionPane.showMessageDialog(null, "El codigo de mesero ingresado no existe", "Alerta",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					if (optionSplit == 1) {
						usSecurity.loadConfig();
						String codeWaitress = usSecurity.verifyWaitress();
						if (codeWaitress != "") {
							while (i < Integer.valueOf(clickeado.getText())) {

								RestBill bill = new RestBill();

								bill.setBillName(
										JOptionPane.showInputDialog("Ingrese el nombre de la cuenta # " + (i + 1)));
								bill.setEntryDate(new Date());
								bill.setEntryUser(codeWaitress);
								bill.setBillTip(0);
								bill.setBillSubtotal(0);
								bill.setBillTotal(0);
								bill.setRestTableAccount(restTableAccount);
								bill.setCtgTip(manageTip.findDefaultCtgTip());
								bill.setShiftId(restS.getIdShift());

								billsQuantity.add(manageRestBill.insertRestBill(bill));
								i++;

							}
							optionSplit = 0;
							billsDetailQuantity = manageRestBillDetail
									.findAllRestBillDetailFromTableAccount(restTableAccount);

							splitController.loadConfig();
						} else {
							JOptionPane.showMessageDialog(null, "El codigo de mesero ingresado no existe", "Alerta",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}

			});

			ap.getChildren().add(p);
			i++;
			pos++;
		}

		TitledPane paneT = new TitledPane();
		paneT.setText("Cuantas Facturas");
		paneT.setContent(ap);
	}

	public MainController() {

		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);

	}

	@FXML
	public void initialize() {
		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
		restS = manageRestShift.alreadyExistShift();
		tip = manageTip.findDefaultCtgTip();
		ttotalL.setStyle("-fx-text-inner-color: #73f455;-fx-background-color:#337c6f;");
		propinaL.setStyle("-fx-text-inner-color:  #73f455;-fx-background-color:#337c6f;");
		subtotalL.setStyle("-fx-text-inner-color:  #73f455;-fx-background-color:#337c6f;");

		principalEstatica = principal;
		if (restS == null) {
			disableControls(true);
			nuevaOrden.setDisable(true);
			ordenes.setDisable(true);
			paraLlevar.setDisable(true);
			cerrarTurno.setDisable(true);
		}
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void fillTotalWithTotalBill(RestTableAccount rta) {

		priceTotal = manageRestBill.getTotalAccountFromTable(rta);
		System.out.println("diquepe precio total:" + priceTotal);

		subtotalL.setText("$" + decimFormat.format(priceTotal / (1 + (tip.getPercentValue() / 100.0))));
		propinaL.setText("(" + tip.getPercentValue() + "%) $"
				+ decimFormat.format(priceTotal - (priceTotal / (1 + (tip.getPercentValue() / 100.0)))));
		ttotalL.setText("$" + decimFormat.format(priceTotal));
		priceTotal = priceTotal / (1 + (tip.getPercentValue() / 100.0));
		billsDetailQuantity = manageRestBillDetail.findAllRestBillDetailFromTableAccount(rta);
		fillTableWithOrders(billsDetailQuantity);

	}

	private void refreshTable() {
		try {
			// int i = 0;
			// System.out.println("********************recorrido de item
			// list******************************");
			// while (i < itemsList.size()) {
			//
			// System.out.println(itemsList.get(i).getIdentifier() + "-" +
			// itemsList.get(i).getMenuItemName());
			// i++;
			//
			// }
			itemsOrderTable.getColumns().clear();

			TableColumn No = new TableColumn("#");
			TableColumn id = new TableColumn("Id");
			TableColumn elemento = new TableColumn("Elemento");
			TableColumn precio = new TableColumn("Precio($)");
			TableColumn facturaN = new TableColumn("Factura");
			// TableColumn total = new TableColumn("Total");
			No.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("identifier"));
			id.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemId"));
			elemento.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemName"));
			precio.setCellValueFactory(new PropertyValueFactory<RestMenuItem, Integer>("menuItemPrice"));
			facturaN.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("nombFactura"));
			// total.setCellValueFactory(new PropertyValueFactory<>("total"));

			// itemsLocalList.setItems(itemsList);
			// itemsLocalList.getColumns().addAll(id,elemento, precio, total);
			if (editandoOrden && !itemsListAddition.isEmpty()) {

				itemsOrderTable.setItems(itemsListAddition);
			} else {
				itemsOrderTable.setItems(itemsList);
			}
			itemsOrderTable.getColumns().addAll(No, id, elemento, precio, facturaN);

			itemsOrderTable.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						// pueda ser que ya no sea el elemento numero 9(el size
						// se
						// cuenta desde 1, por eso 9) cuando ya se crean mas
						// objetos
						// dentro de rightSide
						if (rightSide.getChildren().size() == 10) {
							rightSide.getChildren().remove(9);
						}
						// System.out.println(itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemName());
						priceTotal = priceTotal
								- itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemPrice();

						subtotalL.setText("$" + decimFormat.format(priceTotal));
						propinaL.setText("(" + tip.getPercentValue() + "%) $"
								+ decimFormat.format(priceTotal * tip.getPercentValue() / 100.0));
						ttotalL.setText(
								"$" + decimFormat.format(priceTotal + (priceTotal * tip.getPercentValue() / 100.0)));

						System.out.println("tama;o de listaadditional" + itemsListAddition.size());

						if (!editandoOrden) {

							/****************************************************/

							// RestBill rb = billsDetailQuantity
							// .get(itemsOrderTable.getSelectionModel().getSelectedIndex()).getRestBill();
							// rb.setBillSubtotal(Double.parseDouble(decimFormat.format(rb.getBillSubtotal()
							// -
							// itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemPrice())));
							// //
							// .getRestOrder().getRestMenuItem().getMenuItemPrice())));
							// rb.setBillTip(Double.parseDouble(
							// decimFormat.format(rb.getBillSubtotal() *
							// tip.getPercentValue() / 100.0)));
							// rb.setBillTotal(
							// Double.parseDouble(decimFormat.format(rb.getBillSubtotal()
							// + rb.getBillTip())));
							// System.out.println("la segunda llamada" +
							// rb.getBillTotal());
							// manageRestBill.updateRestBill(rb);

							/****************************************************/

							billsDetailQuantity.remove(itemsOrderTable.getSelectionModel().getSelectedIndex());
							itemsList.remove(itemsOrderTable.getSelectionModel().getSelectedItem());

							// fillTableWithOrders(billsDetailQuantity);

							refreshTable();
						}

						if (editandoOrden && itemsListAddition.size() == 0) {
							usSecurity.loadConfig();
							if (usSecurity.verifyIsAuto()) {
								int n = JOptionPane.showConfirmDialog(null,
										"¿Esta seguro que desea eliminar el pedido seleccionado?", "Pregunta",
										JOptionPane.YES_NO_OPTION);
								if (n == JOptionPane.YES_OPTION) {
									try {

										RestBill rb = billsDetailQuantity
												.get(itemsOrderTable.getSelectionModel().getSelectedIndex())
												.getRestBill();
										rb.setBillSubtotal(rb.getBillSubtotal() - billsDetailQuantity
												.get(itemsOrderTable.getSelectionModel().getSelectedIndex())
												.getBillDetailSubtotal());
										// .getRestOrder().getRestMenuItem().getMenuItemPrice())));
										rb.setBillTip(rb.getBillSubtotal() * tip.getPercentValue() / 100.0);
										rb.setBillTotal(rb.getBillSubtotal() + rb.getBillTip());
										System.out.println("la segunda llamada" + rb.getBillTotal());
										manageRestBill.updateRestBill(rb);
										// if (rb.getBillSubtotal() == 0) {
										// n =
										// JOptionPane.showConfirmDialog(null,
										// "¿La cuenta " + rb.getBillName()
										// + " no contiene mas ordenes
										// pendientes, desea eliminarla?",
										// "Pregunta",
										// JOptionPane.YES_NO_OPTION);
										// if (n == JOptionPane.YES_OPTION) {
										// manageRestBill.deleteRestBill(rb);
										// }
										// }
										// billsDetailQuantity.remove(itemsOrderTable.getSelectionModel().getSelectedIndex());
										// itemsList.remove(itemsOrderTable.getSelectionModel().getSelectedItem());

										manageRestOrders.deleteRestOrder(billsDetailQuantity
												.remove(itemsOrderTable.getSelectionModel().getSelectedIndex())
												.getRestOrder());

										billsDetailQuantity = manageRestBillDetail
												.findAllRestBillDetailFromTableAccount(restTableAccount);

										fillTableWithOrders(billsDetailQuantity);
										// seteo esto, porque cuando entra en el
										// refresh table, setea con
										// listaddition, ya
										// que estoy en editarorden, asi que
										// obligo
										// a que sea la orden ya tomada
										itemsOrderTable.setItems(itemsList);
										JOptionPane.showMessageDialog(null, "Pedido CANCELADO exitosamente");
										loadPanesForTables(manageRestTables.findTablesByArea(restArea), principal,
												"#f4efd8");

									} catch (Exception e) {
										StringWriter errors = new StringWriter();
										e.printStackTrace(new PrintWriter(errors));
										Util.writeIntoErrorLog(errors.toString(), "");
										e.printStackTrace();
									}

								}

							} else {
								JOptionPane.showMessageDialog(null, "Las credenciales ingresadas no estan autorizadas",
										"Alerta", JOptionPane.ERROR_MESSAGE);

							}

						}
						if (editandoOrden && itemsListAddition.size() > 0) {

							/****************************************************/

							// RestBill rb = billsDetailQuantityAddition
							// .get(itemsOrderTable.getSelectionModel().getSelectedIndex()).getRestBill();
							// rb.setBillSubtotal(Double
							// .parseDouble(decimFormat.format(rb.getBillSubtotal()
							// - billsDetailQuantityAddition
							// .get(itemsOrderTable.getSelectionModel().getSelectedIndex())
							// .getBillDetailSubtotal())));
							// //
							// .getRestOrder().getRestMenuItem().getMenuItemPrice())));
							// rb.setBillTip(Double.parseDouble(
							// decimFormat.format(rb.getBillSubtotal() *
							// tip.getPercentValue() / 100.0)));
							// rb.setBillTotal(
							// Double.parseDouble(decimFormat.format(rb.getBillSubtotal()
							// + rb.getBillTip())));
							// System.out.println("la segunda llamada" +
							// rb.getBillTotal());
							// manageRestBill.updateRestBill(rb);

							/****************************************************/

							billsDetailQuantityAddition.remove(itemsOrderTable.getSelectionModel().getSelectedIndex());
							itemsListAddition.remove(itemsOrderTable.getSelectionModel().getSelectedItem());

							refreshTable();
						}

					}
					if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
						// pueda ser que ya no sea el elemento numero 3(el size
						// se
						// cuenta desde 1, por eso 3) cuando ya se crean mas
						// objetos
						// dentro de rightSide

						System.out.println(rightSide.getChildren().size());
						if (rightSide.getChildren().size() == 10) {
							rightSide.getChildren().remove(9);
						}
						TextField txtComment = new TextField();

						txtComment.setLayoutX(240.0);

						txtComment.setLayoutY(itemsOrderTable.getSelectionModel().getSelectedIndex() * 20);
						txtComment.setPrefHeight(100.0);
						txtComment.setPrefWidth(200.0);
						txtComment.setId(
								String.valueOf(itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemId()));
						txtComment.setText(
								itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemDescription());

						txtComment.setOnKeyPressed(new EventHandler<KeyEvent>() {

							@Override
							public void handle(KeyEvent event) {
								if (event.getCode().equals(KeyCode.ENTER)) {
									// se usa la descripcion solo para guardar
									// el
									// comentario que se inyectara a la orden,
									// luego
									// se borra. de todos modos el item menu no
									// se
									// va a persistir

									itemsOrderTable.getSelectionModel().getSelectedItem()
											.setMenuItemDescription(txtComment.getText());

									rightSide.getChildren().remove(txtComment);
								}
								// para eliminar un comentario solo basta con
								// abrir
								// nuevmente el item y presionar escape
								if (event.getCode().equals(KeyCode.ESCAPE)) {
									// se usa la descripcion solo para guardar
									// el
									// comentario que se inyectara a la orden,
									// luego
									// se borra. de todos modos el item menu no
									// se
									// va a persistir
									rightSide.getChildren().remove(txtComment);
									itemsOrderTable.getSelectionModel().getSelectedItem().setMenuItemDescription("");

								}

							}
						});
						rightSide.getChildren().add(txtComment);
						efe.applyFadeTransitionToTextField(txtComment);
						txtComment.requestFocus();

					}
					if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {

					}
				}
			});
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			Util.writeIntoErrorLog(errors.toString(), "");
			e.printStackTrace();
		}
	}

	public void loadPanesForBills(List<RestBill> bills, Pane ap, String color, RestMenuItem item) {
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
			p.setEffect(new InnerShadow());
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
						// bill.setBillSubtotal(Double
						// .parseDouble(decimFormat.format(bill.getBillSubtotal()
						// + item.getMenuItemPrice())));
						// bill.setBillTip(Double.parseDouble(
						// decimFormat.format(bill.getBillSubtotal() *
						// tip.getPercentValue() / 100.0)));
						// bill.setBillTotal(Double.parseDouble(
						// decimFormat.format(bill.getBillSubtotal() * (1 +
						// tip.getPercentValue() / 100.0))));
						// System.out.println("la tercera llamada" +
						// bill.getBillTotal());
						billDetail.setRestBill(bill);
						billDetail.setBillDetailSubtotal(item.getMenuItemPrice());
						billDetail.setBillDetailTotal(item.getMenuItemPrice() * (1 + (tip.getPercentValue() / 100.0)));
						item.setNombFactura(bill.getBillName());
						if (editandoOrden) {
							item.setIdentifier(itemsListAddition.size() + 1);
							itemsListAddition.add(item);
							MainController.billsDetailQuantityAddition.add(billDetail);
						} else {
							item.setIdentifier(itemsList.size() + 1);
							itemsList.add(item);
							MainController.getBillsDetailQuantity().add(billDetail);

						}
						refreshTable();
						// manageRestBill.updateRestBill(bill);
						principal.getChildren().remove(ap);

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

	@FXML
	public void confirmOrder() {
		int i = 0;
		List<RestOrder> ordersToPrint = new ArrayList<RestOrder>();
		if (editandoOrden) {
			if (itemsListAddition.size() > 0) {

				while (i < itemsListAddition.size()) {
					try {
						RestOrder order = new RestOrder();
						order.setEntryDate(new Date());
						order.setOrderStatus("Cocina");
						order.setRestMenuItem(itemsListAddition.get(i));
						order.setRestTableAccount(restTableAccount);
						// se utilizo para guardar el comentario de la orden, no
						// es
						// en realidad la description del menuItem (EN ESTE
						// CONTEXTO)
						order.setOrderComment(itemsListAddition.get(i).getMenuItemDescription());
						// RestShift shift = new RestShift();
						// shift.setIdShift(1);
						order.setRestShift(restS);
						manageRestOrders.insertRestOrder(order);
						ordersToPrint.add(order);

						RestBillDetail det = new RestBillDetail();

						det = billsDetailQuantityAddition.get(i);
						det.setRestOrder(order);
						RestBill bill = manageRestBill
								.findRestBill(billsDetailQuantityAddition.get(i).getRestBill().getBillId());
						bill.setBillSubtotal(
								bill.getBillSubtotal() + billsDetailQuantityAddition.get(i).getBillDetailSubtotal());
						bill.setBillTip(bill.getBillSubtotal() * tip.getPercentValue() / 100.0);
						bill.setBillTotal(bill.getBillSubtotal() * (1 + tip.getPercentValue() / 100.0));

						manageRestBill.updateRestBill(bill);
						manageRestBillDetail.insertRestBillDetail(det);

						i++;
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);

						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						Util.writeIntoErrorLog(errors.toString(), "");

					}

				}
				Printer pr = new Printer();
				pr.printOrders(ordersToPrint);
				ordersToPrint.clear();

				// itemsListAddition.clear();
				// itemsListOrders.clear();
				menuTypePane.getChildren().clear();
				principal.getChildren().clear();
				restTableAccount = new RestTableAccount();

				JOptionPane.showMessageDialog(null, "La(s) Ordene(s) se adicionaron exitosamente");
				inProcess = 0;
				opcionSelected = 2;
				itemsList.clear();
				itemsListOrders.clear();
				billsDetailQuantity.clear();
				billsDetailQuantityAddition.clear();
				itemsListAddition.clear();
				billsQuantity.clear();
				editandoOrden = false;

				disableControls(true);
				loadPanesForTables(manageRestTables.findTablesByArea(restArea), principal, "#f4efd8");
			} else {
				JOptionPane.showMessageDialog(null, "No hay ordenes pendientes de crear", "Alerta",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			restTable.setStatus("OCUPADO");
			manageRestTables.updateRestTable(restTable);
			if (itemsList.size() > 0) {

				while (i < itemsList.size()) {
					try {
						RestOrder order = new RestOrder();
						order.setEntryDate(new Date());
						order.setOrderStatus("Cocina");
						order.setRestMenuItem(itemsList.get(i));
						order.setRestTableAccount(restTableAccount);
						// se utilizo para guardar el comentario de la orden, no
						// es
						// en realidad la description del menuItem (EN ESTE
						// CONTEXTO)
						order.setOrderComment(itemsList.get(i).getMenuItemDescription());
						// RestShift shift = new RestShift();
						// shift.setIdShift(1);
						order.setRestShift(restS);
						manageRestOrders.insertRestOrder(order);
						ordersToPrint.add(order);

						RestBillDetail det = new RestBillDetail();

						det = billsDetailQuantity.get(i);
						det.setRestOrder(order);
						RestBill bill = manageRestBill
								.findRestBill(billsDetailQuantity.get(i).getRestBill().getBillId());
						bill.setBillSubtotal(
								bill.getBillSubtotal() + billsDetailQuantity.get(i).getBillDetailSubtotal());
						bill.setBillTip(bill.getBillSubtotal() * tip.getPercentValue() / 100.0);
						bill.setBillTotal(bill.getBillSubtotal() * (1 + tip.getPercentValue() / 100.0));

						manageRestBill.updateRestBill(bill);
						manageRestBillDetail.insertRestBillDetail(det);

						i++;
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);

						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						Util.writeIntoErrorLog(errors.toString(), "");

					}

				}

				Printer pr = new Printer();
				pr.printOrders(ordersToPrint);
				ordersToPrint.clear();

				itemsList.clear();
				itemsListOrders.clear();
				menuTypePane.getChildren().clear();
				principal.getChildren().clear();
				restTableAccount = new RestTableAccount();

				JOptionPane.showMessageDialog(null, "La(s) Ordene(s) se crearon exitosamente");
				inProcess = 0;
			} else {
				JOptionPane.showMessageDialog(null, "No hay ordenes por crear");
			}
		}

	}

	@FXML
	public void exitApp() {
		if (verifyPendingOrderToComplete()) {
			System.exit(0);
		}

	}

	public void loadOptionButtons(List<String> options, RestTable t, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 20;
		PaymentController.setMainAppC(this);
		while (i < options.size()) {
			Button p = new Button();

			p.setPrefHeight(70);
			p.setPrefWidth(90);

			switch (pos) {
			case 1:
				p.setLayoutX(10);
				break;
			case 2:
				p.setLayoutX(110);
				break;
			case 3:
				p.setLayoutX(210);
				break;
			case 4:
				p.setLayoutX(310);
				break;
			case 5:
				p.setLayoutX(410);
				break;
			case 6:
				p.setLayoutX(510);
				break;
			// case 7:
			// p.setLayoutX(610);
			// break;
			// case 8:
			// p.setLayoutX(710);
			// break;
			// case 9:
			// p.setLayoutX(810);
			// break;
			// case 10:
			// p.setLayoutX(910);
			// break;
			// case 11:
			// p.setLayoutX(1010);
			// break;
			// case 12:
			// p.setLayoutX(1110);
			// break;
			// case 13:
			// p.setLayoutX(1210);
			// break;

			}
			p.setLayoutY(y);
			if (pos % 6 == 0) {
				y = y + 100;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-border-color: #e4e4e4;-fx-border-radius: 10.0px;");
			p.setEffect(new InnerShadow());
			p.setText(options.get(i));
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			// Label mesaSelected = new Label();
			this.restTableAccount = manageRestTableAccount.findRestTableAccountOpen(restTable);
			// mesaSelected.setText("Mesa N°:" +
			// this.restTableAccount.getTableAccountId());
			// mesaSelected.setLayoutX(434.0);
			// mesaSelected.setLayoutY(148.0);
			// mesaSelected.prefWidth(350);
			// mesaSelected.setStyle("-fx-font-weight:bold;");
			// billsDetailQuantity =
			// manageRestBillDetail.findAllRestBillDetailFromTableAccount(this.restTableAccount);

			fillTableWithOrders(billsDetailQuantity);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					if (clickeado.getText().contains("Pagar")) {
						billsDetailQuantity = manageRestBillDetail
								.findAllRestBillDetailFromTableAccount(restTableAccount);
						System.out.println("Entre a pagar");

						payment.loadConfig();

					}
					if (clickeado.getText().contains("Cancelar")) {

						usSecurity.loadConfig();

						if (usSecurity.verifyIsAuto()) {
							int n = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cancelar la orden?",
									"Pregunta", JOptionPane.YES_NO_OPTION);
							if (n == JOptionPane.YES_OPTION) {
								try {
									manageRestTableAccount.deleteRestTableAccount(restTableAccount);
									restTable.setStatus("DESOCUPADO");
									manageRestTables.updateRestTable(restTable);
									restTableAccount = new RestTableAccount();
									inProcess = 0;
									JOptionPane.showMessageDialog(null, "La(s) Ordene(s) se CANCELARON exitosamente");
									loadPanesForTables(manageRestTables.findTablesByArea(restArea), principal,
											"#f4efd8");
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						} else {
							JOptionPane.showMessageDialog(null, "Las credenciales ingresadas no estan autorizadas",
									"Alerta", JOptionPane.ERROR_MESSAGE);

						}
					}
					if (clickeado.getText().contains("Dividir")) {
						billsDetailQuantity = manageRestBillDetail
								.findAllRestBillDetailFromTableAccount(restTableAccount);
						List<String> billsNo = new ArrayList<String>();
						billsNo.add("1");
						billsNo.add("2");
						billsNo.add("3");
						billsNo.add("4");
						billsNo.add("5");
						billsNo.add("6");
						billsNo.add("7");
						billsNo.add("8");
						billsNo.add("9");
						billsNo.add("Otra");
						Pane p = new Pane();

						p.setStyle(
								"-fx-background-color:#c1ebff;-fx-border-radius: 10.0px;-fx-border-color: aliceblue ;");
						p.setPrefHeight(500.0);
						p.setPrefWidth(500.0);
						p.setLayoutX(5.0);
						p.setLayoutY(5.0);
						ap.getChildren().add(p);
						optionSplit = 1;
						loadPanesForHowManyBills(billsNo, p, "Yellow");

					}
					if (clickeado.getText().contains("Editar")) {
						// System.out.println("Entre a editar");
						editandoOrden = true;
						itemsOrderTable.setDisable(false);
						confirmButton.setDisable(false);
						menuTypePane.getChildren().clear();
						principal.getChildren().clear();
						billsDetailQuantity = manageRestBillDetail
								.findAllRestBillDetailFromTableAccount(restTableAccount);
						loadPanesForMenuType(manageCtgMenuType.loadMenuType(), menuTypePane, "#eadfff");
					}
					if (clickeado.getText().contains("Trasladar")) {
						// System.out.println("Entre a editar");
						// editandoOrden = true;
						// itemsOrderTable.setDisable(false);
						// confirmButton.setDisable(false);
						// menuTypePane.getChildren().clear();
						// principal.getChildren().clear();

						String tableNom = JOptionPane
								.showInputDialog("Ingrese el nombre de la mesa donde trasladara la cuenta:");
						RestTable rt = manageRestTables.findRestTableByNameOrId(tableNom);
						if (rt != null) {
							restTableAccount.setRestTable(rt);
							manageRestTableAccount.updateRestTableAccount(restTableAccount);
							JOptionPane.showMessageDialog(null, "El cambio de mesa se efectuo correctamente");
							loadPanesForTables(manageRestTables.findTablesByArea(restArea), principal, "#f4efd8");

						} else {
							JOptionPane.showMessageDialog(null, "La mesa ingresada no existe", "Alerta",
									JOptionPane.ERROR_MESSAGE);
						}

					}

				}

			});

			ap.getChildren().add(p);
			// ap.getChildren().add(mesaSelected);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			i++;
			pos++;
		}
	}

	private void fillTableWithOrders(List<RestBillDetail> billsDetail) {
		int i = 0;
		itemsList.clear();
		itemsListOrders.clear();

		while (i < billsDetail.size()) {
			RestMenuItem rmi = billsDetail.get(i).getRestOrder().getRestMenuItem();
			rmi.setNombFactura(billsDetail.get(i).getRestBill().getBillName());
			rmi.setIdentifier(i + 1);
			System.out.println(rmi.getNombFactura() + "*************");
			itemsList.add(rmi);

			RestOrder ro = billsDetail.get(i).getRestOrder();
			ro.setNombFactura(billsDetail.get(i).getRestBill().getBillName());
			itemsListOrders.add(ro);
			i++;
		}
		refreshTable();

	}

	public void disableControls(boolean s) {
		itemsOrderTable.setDisable(s);
		confirmButton.setDisable(s);

		setToZeroTotal();

	}

	public void setToZeroTotal() {
		subtotalL.setText("$ 0.0");
		propinaL.setText("(" + tip.getPercentValue() + "%) $ 0.0");
		ttotalL.setText("$ 0.0");

	}

	public IRestOrderManagement getManageRestOrders() {
		return manageRestOrders;
	}

	public void setManageRestOrders(IRestOrderManagement manageRestOrders) {
		this.manageRestOrders = manageRestOrders;
	}

	public IRestTableAccountManagement getManageRestTableAccount() {
		return manageRestTableAccount;
	}

	public void setManageRestTableAccount(IRestTableAccountManagement manageRestTableAccount) {
		this.manageRestTableAccount = manageRestTableAccount;
	}

	public RestTableAccount getRestTableAccount() {
		return restTableAccount;
	}

	public void setRestTableAccount(RestTableAccount restTableAccount) {
		this.restTableAccount = restTableAccount;
	}

	public static List<RestBillDetail> getBillsDetailQuantity() {
		return billsDetailQuantity;
	}

	public boolean verifyPendingOrderToComplete() {

		if (inProcess != 0) {
			int n = JOptionPane.showConfirmDialog(null, "¿No se ha terminado de procesar la orden, desea cancelarla?",
					"Pregunta", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				try {
					if (!editandoOrden) {
						manageRestTableAccount.deleteRestTableAccount(restTableAccount);
						restTable.setStatus("DESOCUPADO");
						manageRestTables.updateRestTable(restTable);
						restTableAccount = new RestTableAccount();
						inProcess = 0;
						JOptionPane.showMessageDialog(null, "La(s) Ordene(s) se CANCELARON exitosamente");
						loadPanesForTables(manageRestTables.findTablesByArea(restArea), principal, "#f4efd8");
						return true;
					} else {
						// es una edicion
						// solo se pierden los valores en la lista y ponemos la
						// bandera en 0
						inProcess = 0;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} else {

				return false;
			}
		}
		return true;

	}

	@FXML
	public void iniciarShift() {
		usSecurity.loadConfig();
		String us = usSecurity.verifyIsAutoReturnsN();

		if (us != null) {
			float iniMoney = Float.parseFloat(
					JOptionPane.showInputDialog("Ingrese el monto de dinero con el que inciara este nuevo turno"));
			RestShift newShift = new RestShift();
			newShift.setInitialMoney(iniMoney);
			newShift.setOpenedBy(us);
			newShift.setOpeningDatetime(new Date());
			newShift.setStatus("OPEN");
			restS = manageRestShift.alreadyExistShift();
			if (restS == null) {
				restS = manageRestShift.insertRestShift(newShift);

				nuevaOrden.setDisable(false);
				ordenes.setDisable(false);
				paraLlevar.setDisable(false);
				cerrarTurno.setDisable(false);
				JOptionPane.showMessageDialog(null, "Se aperturo un nuevo turno", "Exito",
						JOptionPane.INFORMATION_MESSAGE);
			} else {

				JOptionPane.showMessageDialog(null, "Ya existe un turno abierto!, cierrelo antes de aperturar otro",
						"Alerta", JOptionPane.ERROR_MESSAGE);

			}

		} else {

			JOptionPane.showMessageDialog(null, "Las credenciales ingresadas no son válidas", "Alerta",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@FXML
	public void cerrarShift() {

		usSecurity.loadConfig();
		String us = usSecurity.verifyIsAutoReturnsN();

		if (us != null) {

			if (restS != null) {
				try {
					manageRestShift.closeShift(restS.getIdShift(), us);
					// restTableAccount.setRestShift1(restS);
					// restTableAccount.setAccountStatus("CLOSED");
					// manageRestTableAccount.updateRestTableAccount(restTableAccount);
					nuevaOrden.setDisable(true);
					ordenes.setDisable(true);
					paraLlevar.setDisable(true);
					JOptionPane.showMessageDialog(null, "Se ha cerrado el Shift", "Exito",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else {
			JOptionPane.showMessageDialog(null, "Las credenciales ingresadas no son válidas", "Alerta",
					JOptionPane.ERROR_MESSAGE);

		}

	}

	public static void setBillsDetailQuantity(List<RestBillDetail> billsDetailQuantity) {
		MainController.billsDetailQuantity = billsDetailQuantity;
	}
}
