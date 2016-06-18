package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_product database table.
 *
 */
@Entity
@Table(name="ctg_product")
@NamedQuery(name="CtgProduct.findAll", query="SELECT c FROM CtgProduct c")
public class CtgProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_ID")
	private int productId;

	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="PRODUCT_DESCRIPTION")
	private String productDescription;

	@Column(name="PRODUCT_NAME")
	private String productName;

	@Column(name="PRODUCT_PRICE")
	private float productPrice;

	@Column(name="PRODUCT_QTY_AVAILABLE")
	private float productQtyAvailable;

	@Column(name="PRODUCT_QTY_TRESHOLD")
	private float productQtyTreshold;

	@Column(name="PRODUCT_WASTE")
	private float productWaste;

	//bi-directional many-to-one association to CtgMeasureUnit
	@ManyToOne
	@JoinColumn(name="MEASURE_UNIT_ID")
	private CtgMeasureUnit ctgMeasureUnit;

	//bi-directional many-to-one association to CtgProductType
	@ManyToOne
	@JoinColumn(name="PRODUCT_TYPE_ID")
	private CtgProductType ctgProductType;

	//bi-directional many-to-one association to CtgSupplier
	@ManyToOne
	@JoinColumn(name="SUPPLIER_ID")
	private CtgSupplier ctgSupplier;

	//bi-directional one-to-one association to RestMenuItemProduct
	@OneToOne
	@JoinColumn(name="PRODUCT_ID")
	private RestMenuItemProduct restMenuItemProduct;

	//bi-directional many-to-one association to InvProductItem
	@OneToMany(mappedBy="ctgProduct")
	private List<InvProductItem> invProductItems;

	//bi-directional many-to-one association to InvProductTransactionLog
	@OneToMany(mappedBy="ctgProduct")
	private List<InvProductTransactionLog> invProductTransactionLogs;

	public CtgProduct() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	public float getProductQtyAvailable() {
		return this.productQtyAvailable;
	}

	public void setProductQtyAvailable(float productQtyAvailable) {
		this.productQtyAvailable = productQtyAvailable;
	}

	public float getProductQtyTreshold() {
		return this.productQtyTreshold;
	}

	public void setProductQtyTreshold(float productQtyTreshold) {
		this.productQtyTreshold = productQtyTreshold;
	}

	public float getProductWaste() {
		return this.productWaste;
	}

	public void setProductWaste(float productWaste) {
		this.productWaste = productWaste;
	}

	public CtgMeasureUnit getCtgMeasureUnit() {
		return this.ctgMeasureUnit;
	}

	public void setCtgMeasureUnit(CtgMeasureUnit ctgMeasureUnit) {
		this.ctgMeasureUnit = ctgMeasureUnit;
	}

	public CtgProductType getCtgProductType() {
		return this.ctgProductType;
	}

	public void setCtgProductType(CtgProductType ctgProductType) {
		this.ctgProductType = ctgProductType;
	}

	public CtgSupplier getCtgSupplier() {
		return this.ctgSupplier;
	}

	public void setCtgSupplier(CtgSupplier ctgSupplier) {
		this.ctgSupplier = ctgSupplier;
	}

	public RestMenuItemProduct getRestMenuItemProduct() {
		return this.restMenuItemProduct;
	}

	public void setRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		this.restMenuItemProduct = restMenuItemProduct;
	}

	public List<InvProductItem> getInvProductItems() {
		return this.invProductItems;
	}

	public void setInvProductItems(List<InvProductItem> invProductItems) {
		this.invProductItems = invProductItems;
	}

	public InvProductItem addInvProductItem(InvProductItem invProductItem) {
		getInvProductItems().add(invProductItem);
		invProductItem.setCtgProduct(this);

		return invProductItem;
	}

	public InvProductItem removeInvProductItem(InvProductItem invProductItem) {
		getInvProductItems().remove(invProductItem);
		invProductItem.setCtgProduct(null);

		return invProductItem;
	}

	public List<InvProductTransactionLog> getInvProductTransactionLogs() {
		return this.invProductTransactionLogs;
	}

	public void setInvProductTransactionLogs(List<InvProductTransactionLog> invProductTransactionLogs) {
		this.invProductTransactionLogs = invProductTransactionLogs;
	}

	public InvProductTransactionLog addInvProductTransactionLog(InvProductTransactionLog invProductTransactionLog) {
		getInvProductTransactionLogs().add(invProductTransactionLog);
		invProductTransactionLog.setCtgProduct(this);

		return invProductTransactionLog;
	}

	public InvProductTransactionLog removeInvProductTransactionLog(InvProductTransactionLog invProductTransactionLog) {
		getInvProductTransactionLogs().remove(invProductTransactionLog);
		invProductTransactionLog.setCtgProduct(null);

		return invProductTransactionLog;
	}

}