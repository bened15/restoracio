package com.jbd.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jbd.hibernate.interfaces.ICtgMenuTypeManagement;
import com.jbd.hibernate.interfaces.IRestAreaManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemManagement;
import com.jbd.hibernate.interfaces.IRestMenuItemProductManagement;
import com.jbd.hibernate.interfaces.IRestOrderManagement;
import com.jbd.hibernate.interfaces.IRestTableAccountManagement;
import com.jbd.hibernate.interfaces.IRestTableManagement;
import com.jbd.model.CtgMenuType;
import com.jbd.model.RestArea;
import com.jbd.model.RestMenuItem;
import com.jbd.model.RestMenuItemProduct;
import com.jbd.model.RestOrder;
import com.jbd.model.RestShift;
import com.jbd.model.RestTable;
import com.jbd.model.RestTableAccount;
import com.jbd.utils.Effect;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class MainController {

	private Main mainApp;

	private double priceTotal = 0;
	DecimalFormat decimFormat = new DecimalFormat("#.00");
	@FXML
	private AnchorPane principal;
	@FXML
	private AnchorPane rightSide;
	@FXML
	private AnchorPane menuTypePane;
	@FXML
	private Button confirmButton;
	private int opcionSelected = 0;

	@Autowired
	private IRestAreaManagement manageRestAreas;
	@Autowired
	private IRestOrderManagement manageRestOrders;
	@Autowired
	private IRestTableManagement manageRestTables;
	@Autowired
	private ICtgMenuTypeManagement manageCtgMenuType;
	@Autowired
	private IRestMenuItemManagement manageRestMenuItem;
	@Autowired
	private IRestMenuItemProductManagement manageRestMenuItemProduct;
	@Autowired
	private IRestTableAccountManagement manageRestTableAccount;
	@Autowired
	private RestTable restTable;
	@Autowired
	private RestArea restArea;
	@Autowired
	private RestTableAccount restTableAccount;

	@Autowired
	private Effect efe;
	private ObservableList<RestMenuItem> itemsList = FXCollections.observableArrayList();
	@FXML
	private TableView<RestMenuItem> itemsOrderTable = new TableView<RestMenuItem>();
	@FXML
	private Label totalL = new Label();

	public AnchorPane getPrincipal() {
		return principal;
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
			Button b = (Button) event.getSource();
			if (b.getId().contains("nOrder")) {
				opcionSelected = 1;
				itemsList.clear();
				refreshTable();
				disableControls(false);

			}
			if (b.getId().contains("vOrder")) {
				opcionSelected = 2;
				itemsList.clear();
				refreshTable();
				disableControls(true);

			}

			principal.getChildren().clear();
			menuTypePane.getChildren().clear();
			loadPanesForAreas(manageRestAreas.getAllAreas(), principal, "#c8b03e");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadPanesForAreas(List<RestArea> areas, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < areas.size()) {
			Button p = new Button();

			p.setPrefHeight(140);
			p.setPrefWidth(140);

			switch (pos) {
			case 1:
				p.setLayoutX(10);
				break;
			case 2:
				p.setLayoutX(160);
				break;
			case 3:
				p.setLayoutX(310);
				break;
			case 4:
				p.setLayoutX(460);
				break;

			}
			p.setLayoutY(y);
			if (pos % 4 == 0) {
				y = y + 120;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-border-radius: 10.0px;-fx-border-color:#40441e;");
			p.setId(String.valueOf(areas.get(i).getAreaId()));
			p.setText(areas.get(i).getAreaName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);

			System.out.println("valor de i:" + i);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					ap.getChildren().clear();

					Button clickeado = (Button) arg0.getSource();

					System.out.println("valor de area seleccionada:" + clickeado.getId());
					// RestArea area = new RestArea();

					restArea.setAreaId(Integer.parseInt(clickeado.getId()));
					loadPanesForTables(manageRestTables.findTablesByArea(restArea), ap, "#c4f195");

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
		int x = 120, y = 10;

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

			p.setId(String.valueOf(tables.get(i).getTableId()));
			p.setText(tables.get(i).getTableName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);

			if (manageRestTableAccount.isRestTableAccountOpen(tables.get(i))) {
				p.setStyle("-fx-background-color: #cb213d;");
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
							restTable.setTableId(Integer.parseInt(clickeado.getId()));
							List<String> op = new ArrayList<String>();
							op.add("Pagar");
							op.add("Cancelar Orden");
							op.add("Dividir Orden");
							op.add("Editar Orden");

							// en el mismo anchor pane de menuType se ponen las
							// opciones

							loadOptionButtons(op, restTable, menuTypePane, "#99d1e2");
						}

					}

				});
			} else {
				p.setStyle("-fx-background-color: " + color + ";");
				p.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						if (opcionSelected == 1) {

							ap.getChildren().clear();
							// JOptionPane.showMessageDialog(null, "Alerta" +
							// listaObjeto.getClass());
							Button clickeado = (Button) arg0.getSource();
							efe.applyFadeTransitionToButton(clickeado);

							restTable.setTableId(Integer.parseInt(clickeado.getId()));

							loadPanesForMenuType(manageCtgMenuType.loadMenuType(), menuTypePane, "#eadfff");
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

	public void loadPanesForMenuType(List<CtgMenuType> menuType, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < menuType.size()) {
			Button p = new Button();

			p.setPrefHeight(90);
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
			case 7:
				p.setLayoutX(610);
				break;
			case 8:
				p.setLayoutX(710);
				break;
			case 9:
				p.setLayoutX(810);
				break;
			case 10:
				p.setLayoutX(910);
				break;
			case 11:
				p.setLayoutX(1010);
				break;
			case 12:
				p.setLayoutX(1110);
				break;
			case 13:
				p.setLayoutX(1210);
				break;

			}
			p.setLayoutY(y);
			if (pos % 13 == 0) {
				y = y + 100;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";");
			p.setId(String.valueOf(menuType.get(i).getMenuTypeId()));
			p.setText(menuType.get(i).getMenuTypeName());
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);

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
					loadPanesForMenuItem(manageRestMenuItem.findMenuItemByTypeMenu(type), principal, "#eec6a5");

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			i++;
			pos++;
		}
	}

	public void loadPanesForMenuItem(List<RestMenuItem> menuItem, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int x = 120, y = 10;

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

			p.setId(String.valueOf(menuItem.get(i).getMenuItemId()));

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
					String[] partida = clickeado.getText().split("\n");
					item.setMenuItemId(Integer.parseInt(clickeado.getId()));
					item.setMenuItemName(partida[0]);
					item.setMenuItemPrice(Float.parseFloat(partida[1].trim().substring(1, partida[1].length())));
					itemsList.add(item);
					priceTotal = priceTotal + item.getMenuItemPrice();
					totalL.setText("Total: $ " + decimFormat.format(priceTotal) + "\n\n" + "Propina (10%) $"
							+ (decimFormat.format(priceTotal * 0.10)) + "\n" + "\n" + "----------" + "\n" + "Total: $ "
							+ decimFormat.format(priceTotal + (priceTotal * 0.10)));

					// System.out.println("Tamaño de lista" + itemsList.size());
					// rightSide.getChildren().add(refreshTable());
					refreshTable();

				}

			});

			ap.getChildren().add(p);
			efe.applyTranslateTransitionToButton(p, -500, 10);
			i++;
			pos++;
		}
	}

	public void loadPanesForMenuItemProduct(List<RestMenuItemProduct> menuItemProduct, Pane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < menuItemProduct.size()) {
			Button p = new Button();

			p.setPrefHeight(50);
			p.setPrefWidth(50);

			switch (pos) {
			case 1:
				p.setLayoutX(5);
				break;
			case 2:
				p.setLayoutX(60);
				break;
			case 3:
				p.setLayoutX(115);
				break;
			case 4:
				p.setLayoutX(170);
				break;
			case 5:
				p.setLayoutX(225);
				break;

			}
			p.setLayoutY(y);
			if (pos % 5 == 0) {
				y = y + 55;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-font-size:8px");

			p.setId(String.valueOf(menuItemProduct.get(i).getMenuItemProductId()));

			String menuItemName = "";
			if (menuItemProduct.get(i).getCtgProductType().getTypeName().length() > 20) {
				menuItemName = menuItemProduct.get(i).getCtgProductType().getTypeName().substring(0, 19) + "..";
			} else {
				menuItemName = menuItemProduct.get(i).getCtgProductType().getTypeName();

			}

			p.setText(menuItemName);
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// ap.getChildren().clear();
					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();

				}

			});

			ap.getChildren().add(p);
			i++;
			pos++;
		}
	}

	public MainController() {

		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);

	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	private void refreshTable() {

		itemsOrderTable.getColumns().clear();

		TableColumn id = new TableColumn("Id");
		TableColumn elemento = new TableColumn("Elemento");
		TableColumn precio = new TableColumn("Precio($)");
		// TableColumn total = new TableColumn("Total");

		id.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemId"));
		elemento.setCellValueFactory(new PropertyValueFactory<RestMenuItem, String>("menuItemName"));
		precio.setCellValueFactory(new PropertyValueFactory<RestMenuItem, Integer>("menuItemPrice"));
		// total.setCellValueFactory(new PropertyValueFactory<>("total"));

		// itemsLocalList.setItems(itemsList);
		// itemsLocalList.getColumns().addAll(id,elemento, precio, total);
		itemsOrderTable.setItems(itemsList);
		itemsOrderTable.getColumns().addAll(id, elemento, precio);

		itemsOrderTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					// pueda ser que ya no sea el elemento numero 3(el size se
					// cuenta desde 1, por eso 3) cuando ya se crean mas objetos
					// dentro de rightSide
					if (rightSide.getChildren().size() == 4) {
						rightSide.getChildren().remove(3);
					}
					// System.out.println(itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemName());
					priceTotal = priceTotal - itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemPrice();
					totalL.setText("Total: $ " + decimFormat.format(priceTotal) + "\n\n" + "Propina (10%) $"
							+ (decimFormat.format(priceTotal * 0.10)) + "\n" + "\n" + "----------" + "\n" + "Total: $ "
							+ decimFormat.format(priceTotal + (priceTotal * 0.10)));
					itemsList.remove(itemsOrderTable.getSelectionModel().getSelectedItem());

					refreshTable();
				}
				if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
					// pueda ser que ya no sea el elemento numero 3(el size se
					// cuenta desde 1, por eso 3) cuando ya se crean mas objetos
					// dentro de rightSide
					System.out.println(rightSide.getChildren().size());
					if (rightSide.getChildren().size() == 4) {
						rightSide.getChildren().remove(3);
					}

					TextField txtComment = new TextField();
					txtComment.setLayoutX(240.0);

					txtComment.setLayoutY(itemsOrderTable.getSelectionModel().getSelectedIndex() * 20);
					txtComment.setPrefHeight(100.0);
					txtComment.setPrefWidth(200.0);
					txtComment.setId(
							String.valueOf(itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemId()));

					txtComment.setOnKeyPressed(new EventHandler<KeyEvent>() {

						@Override
						public void handle(KeyEvent event) {
							if (event.getCode().equals(KeyCode.ENTER)) {
								// se usa la descripcion solo para guardar el
								// comentario que se inyectara a la orden, luego
								// se borra. de todos modos el item menu no se
								// va a persistir
								itemsOrderTable.getSelectionModel().getSelectedItem()
										.setMenuItemDescription(txtComment.getText());
								// si se crean mas objetos dentro de rightSide,
								// este valor podria cambiar-OJO, podria ser que
								// ya no seria el 2
								rightSide.getChildren().remove(3);
							}
							// para eliminar un comentario solo basta con abrir
							// nuevmente el item y presionar escape
							if (event.getCode().equals(KeyCode.ESCAPE)) {
								// se usa la descripcion solo para guardar el
								// comentario que se inyectara a la orden, luego
								// se borra. de todos modos el item menu no se
								// va a persistir
								itemsOrderTable.getSelectionModel().getSelectedItem().setMenuItemDescription("");
								// si se crean mas objetos dentro de rightSide,
								// este valor podria cambiar-OJO, podria ser que
								// ya no seria el 2
								rightSide.getChildren().remove(3);
							}

						}
					});
					rightSide.getChildren().add(txtComment);
					efe.applyFadeTransitionToTextField(txtComment);
					txtComment.requestFocus();

				}
				if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {
					if (rightSide.getChildren().size() == 4) {
						rightSide.getChildren().remove(3);
					}
					Pane rec = new Pane();
					rec.setStyle(
							"-fx-background-color:#c1ebff;-fx-border-radius: 10.0px;-fx-border-color: aliceblue ;");
					rec.setPrefWidth(400.0);
					rec.setPrefHeight(400.0);
					rec.setLayoutX(240.0);
					rec.setId(String.valueOf(itemsOrderTable.getSelectionModel().getSelectedItem().getMenuItemId()));
					rec.setLayoutY(20);
					rightSide.getChildren().add(rec);
					efe.applyFadeTransitionToRectangle(rec);
					rec.requestFocus();

					rec.setOnKeyPressed(new EventHandler<KeyEvent>() {

						@Override
						public void handle(KeyEvent event) {
							if (event.getCode().equals(KeyCode.ENTER)) {
								// aqui se tenria que gurdar las guarniciones
								// del plato editddas
								// si se crean mas objetos dentro de rightSide,
								// este valor podria cambiar-OJO, podria ser que
								// ya no seria el 2
								rightSide.getChildren().remove(3);
							}
							// para eliminar un comentario solo basta con abrir
							// nuevmente el item y presionar escape
							if (event.getCode().equals(KeyCode.ESCAPE)) {
								// se cancela
								rightSide.getChildren().remove(3);
							}

						}
					});
					loadPanesForMenuItemProduct(manageRestMenuItemProduct
							.findIngredientes(itemsOrderTable.getSelectionModel().getSelectedItem()), rec, "#f1eff1");

					// refreshTable();
				}
			}
		});

	}

	@FXML
	public void confirmOrder() {
		int i = 0;
		if (itemsList.size() > 0) {
			RestTableAccount tableAccount = new RestTableAccount();
			tableAccount.setRestTable(restTable);
			tableAccount.setCreatedBy("Julio");
			tableAccount.setCreatedDatetime(new Date());

			tableAccount.setRestShift1(null);
			tableAccount.setRestShift2(null);
			tableAccount.setAccountStatus("Initial");

			restTableAccount = manageRestTableAccount.insertRestTableAccount(tableAccount);

		}
		if (itemsList.size() > 0) {

			while (i < itemsList.size()) {
				try {
					RestOrder order = new RestOrder();
					order.setEntryDate(new Date());
					order.setOrderStatus("Cocina");
					order.setRestMenuItem(itemsList.get(i));
					order.setRestTableAccount(restTableAccount);
					// se utilizo para guardar el comentario de la orden, no es
					// en realidad la description del menuItem (EN ESTE
					// CONTEXTO)
					order.setOrderComment(itemsList.get(i).getMenuItemDescription());
					RestShift shift = new RestShift();
					shift.setIdShift(1);
					order.setRestShift(shift);
					manageRestOrders.insertRestOrder(order);
					i++;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error" + e.getMessage());

				}

			}
			itemsList.clear();
			// rightSide.getChildren().clear();
			menuTypePane.getChildren().clear();
			principal.getChildren().clear();
			// rightSide.getChildren().add(refreshTable());
			JOptionPane.showMessageDialog(null, "La(s) Ordene(s) se creaon exitosamente");
		} else {
			JOptionPane.showMessageDialog(null, "No hay ordenes por crear");
		}

	}

	@FXML
	public void exitApp() {
		System.exit(0);

	}

	public void loadOptionButtons(List<String> options, RestTable t, AnchorPane ap, String color) {
		int i = 0, pos = 1;
		int y = 10;

		while (i < options.size()) {
			Button p = new Button();

			p.setPrefHeight(90);
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
			case 7:
				p.setLayoutX(610);
				break;
			case 8:
				p.setLayoutX(710);
				break;
			case 9:
				p.setLayoutX(810);
				break;
			case 10:
				p.setLayoutX(910);
				break;
			case 11:
				p.setLayoutX(1010);
				break;
			case 12:
				p.setLayoutX(1110);
				break;
			case 13:
				p.setLayoutX(1210);
				break;

			}
			p.setLayoutY(y);
			if (pos % 13 == 0) {
				y = y + 100;
				// se pone cero porque se aumenta abajo
				pos = 0;
			}
			p.setStyle("-fx-background-color: " + color + ";-fx-border-color:white;-fx-border-radius: 5.0px;");

			p.setText(options.get(i));
			p.setWrapText(true);
			p.setTextAlignment(TextAlignment.CENTER);
			Label mesaSelected = new Label();
			this.restTableAccount = manageRestTableAccount.findRestTableAccountOpen(restTable);
			mesaSelected.setText("Mesa N°:" + this.restTableAccount.getTableAccountId());
			mesaSelected.setLayoutX(434.0);
			mesaSelected.setLayoutY(148.0);
			mesaSelected.prefWidth(350);
			mesaSelected.setStyle("-fx-font-weight:bold;");
			List<RestOrder> ordenes = manageRestOrders.findAllRestOrdersFromTableAccount(this.restTableAccount);
			fillTableWithOrders(ordenes);
			p.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					// JOptionPane.showMessageDialog(null, "Alerta" +
					// listaObjeto.getClass());
					Button clickeado = (Button) arg0.getSource();
					if (clickeado.getText().contains("Pagar")) {
						System.out.println("Entre a pagar");
						PaymentController payment = new PaymentController();
						payment.loadConfig(MainController.this);
					}
					if (clickeado.getText().contains("Cancelar")) {
						System.out.println("Entre a cancelar");
					}
					if (clickeado.getText().contains("Dividir")) {
						System.out.println("Entre a dividir");
					}
					if (clickeado.getText().contains("Editar")) {
						System.out.println("Entre a editar");
					}

				}

			});

			ap.getChildren().add(p);
			ap.getChildren().add(mesaSelected);
			efe.applyTranslateTransitionToButton(p, -500, 10);

			i++;
			pos++;
		}
	}

	private void fillTableWithOrders(List<RestOrder> ordenes) {
		int i = 0;
		itemsList.clear();
		while (i < ordenes.size()) {
			itemsList.add(ordenes.get(i).getRestMenuItem());
			i++;
		}
		refreshTable();

	}

	public void disableControls(boolean s) {
		itemsOrderTable.setDisable(s);
		confirmButton.setDisable(s);

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

}
