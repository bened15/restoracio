package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rest_menu_item_product database table.
 *
 */
@Entity
@Table(name="rest_menu_item_product")
@NamedQuery(name="RestMenuItemProduct.findAll", query="SELECT r FROM RestMenuItemProduct r")
public class RestMenuItemProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MENU_ITEM_PRODUCT_ID")
	private int menuItemProductId;

	private float quantity;

	//bi-directional one-to-one association to CtgProduct
	@OneToOne(mappedBy="restMenuItemProduct")
	private CtgProduct ctgProduct;

	//bi-directional many-to-one association to CtgMeasureUnit
	@ManyToOne
	@JoinColumn(name="MEASURE_UNIT_ID")
	private CtgMeasureUnit ctgMeasureUnit;

	//bi-directional many-to-one association to RestMenuItem
	@ManyToOne
	@JoinColumn(name="MENU_ITEM_ID")
	private RestMenuItem restMenuItem;

	//bi-directional many-to-one association to CtgProductType
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private CtgProductType ctgProductType;

	public RestMenuItemProduct() {
	}

	public int getMenuItemProductId() {
		return this.menuItemProductId;
	}

	public void setMenuItemProductId(int menuItemProductId) {
		this.menuItemProductId = menuItemProductId;
	}

	public float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public CtgProduct getCtgProduct() {
		return this.ctgProduct;
	}

	public void setCtgProduct(CtgProduct ctgProduct) {
		this.ctgProduct = ctgProduct;
	}

	public CtgMeasureUnit getCtgMeasureUnit() {
		return this.ctgMeasureUnit;
	}

	public void setCtgMeasureUnit(CtgMeasureUnit ctgMeasureUnit) {
		this.ctgMeasureUnit = ctgMeasureUnit;
	}

	public RestMenuItem getRestMenuItem() {
		return this.restMenuItem;
	}

	public void setRestMenuItem(RestMenuItem restMenuItem) {
		this.restMenuItem = restMenuItem;
	}

	public CtgProductType getCtgProductType() {
		return this.ctgProductType;
	}

	public void setCtgProductType(CtgProductType ctgProductType) {
		this.ctgProductType = ctgProductType;
	}

}