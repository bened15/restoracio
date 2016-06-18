package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the inv_product_transaction_log database table.
 *
 */
@Entity
@Table(name="inv_product_transaction_log")
@NamedQuery(name="InvProductTransactionLog.findAll", query="SELECT i FROM InvProductTransactionLog i")
public class InvProductTransactionLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="INV_PRODUCT_TRNS_ID")
	private int invProductTrnsId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INV_PRODUCT_ID")
	private Date invProductId;

	@Column(name="MENU_ITEM_ID")
	private int menuItemId;

	@Column(name="ORDER_ID")
	private int orderId;

	@Column(name="PRODUCT_QTY")
	private float productQty;

	private String state;

	@Column(name="TRANSACTION_TYPE_ID")
	private String transactionTypeId;

	//bi-directional many-to-one association to CtgProduct
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private CtgProduct ctgProduct;

	public InvProductTransactionLog() {
	}

	public int getInvProductTrnsId() {
		return this.invProductTrnsId;
	}

	public void setInvProductTrnsId(int invProductTrnsId) {
		this.invProductTrnsId = invProductTrnsId;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryUser() {
		return this.entryUser;
	}

	public void setEntryUser(String entryUser) {
		this.entryUser = entryUser;
	}

	public Date getInvProductId() {
		return this.invProductId;
	}

	public void setInvProductId(Date invProductId) {
		this.invProductId = invProductId;
	}

	public int getMenuItemId() {
		return this.menuItemId;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public float getProductQty() {
		return this.productQty;
	}

	public void setProductQty(float productQty) {
		this.productQty = productQty;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTransactionTypeId() {
		return this.transactionTypeId;
	}

	public void setTransactionTypeId(String transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public CtgProduct getCtgProduct() {
		return this.ctgProduct;
	}

	public void setCtgProduct(CtgProduct ctgProduct) {
		this.ctgProduct = ctgProduct;
	}

}