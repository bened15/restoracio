package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_product_type database table.
 *
 */
@Entity
@Table(name="ctg_product_type")
@NamedQuery(name="CtgProductType.findAll", query="SELECT c FROM CtgProductType c")
public class CtgProductType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_TYPE_ID")
	private int productTypeId;

	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="TYPE_DESCRIPTION")
	private String typeDescription;

	@Column(name="TYPE_NAME")
	private String typeName;

	//bi-directional many-to-one association to CtgProduct
	@OneToMany(mappedBy="ctgProductType")
	private List<CtgProduct> ctgProducts;

	//bi-directional many-to-one association to RestMenuItemProduct
	@OneToMany(mappedBy="ctgProductType")
	private List<RestMenuItemProduct> restMenuItemProducts;

	public CtgProductType() {
	}

	public int getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
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

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<CtgProduct> getCtgProducts() {
		return this.ctgProducts;
	}

	public void setCtgProducts(List<CtgProduct> ctgProducts) {
		this.ctgProducts = ctgProducts;
	}

	public CtgProduct addCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().add(ctgProduct);
		ctgProduct.setCtgProductType(this);

		return ctgProduct;
	}

	public CtgProduct removeCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().remove(ctgProduct);
		ctgProduct.setCtgProductType(null);

		return ctgProduct;
	}

	public List<RestMenuItemProduct> getRestMenuItemProducts() {
		return this.restMenuItemProducts;
	}

	public void setRestMenuItemProducts(List<RestMenuItemProduct> restMenuItemProducts) {
		this.restMenuItemProducts = restMenuItemProducts;
	}

	public RestMenuItemProduct addRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().add(restMenuItemProduct);
		restMenuItemProduct.setCtgProductType(this);

		return restMenuItemProduct;
	}

	public RestMenuItemProduct removeRestMenuItemProduct(RestMenuItemProduct restMenuItemProduct) {
		getRestMenuItemProducts().remove(restMenuItemProduct);
		restMenuItemProduct.setCtgProductType(null);

		return restMenuItemProduct;
	}

}