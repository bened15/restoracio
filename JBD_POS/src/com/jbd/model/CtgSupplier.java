package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_supplier database table.
 *
 */
@Entity
@Table(name="ctg_supplier")
@NamedQuery(name="CtgSupplier.findAll", query="SELECT c FROM CtgSupplier c")
public class CtgSupplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUPPLIER_ID")
	private int supplierId;

	private String address;

	private String city;

	@Column(name="CONTACT_LASTNAME")
	private String contactLastname;

	@Column(name="CONTACT_NAME")
	private String contactName;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="PHONE_NUMBER")
	private String phoneNumber;

	@Column(name="SUPPLIER_NAME")
	private String supplierName;

	//bi-directional many-to-one association to CtgProduct
	@OneToMany(mappedBy="ctgSupplier")
	private List<CtgProduct> ctgProducts;

	public CtgSupplier() {
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactLastname() {
		return this.contactLastname;
	}

	public void setContactLastname(String contactLastname) {
		this.contactLastname = contactLastname;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public List<CtgProduct> getCtgProducts() {
		return this.ctgProducts;
	}

	public void setCtgProducts(List<CtgProduct> ctgProducts) {
		this.ctgProducts = ctgProducts;
	}

	public CtgProduct addCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().add(ctgProduct);
		ctgProduct.setCtgSupplier(this);

		return ctgProduct;
	}

	public CtgProduct removeCtgProduct(CtgProduct ctgProduct) {
		getCtgProducts().remove(ctgProduct);
		ctgProduct.setCtgSupplier(null);

		return ctgProduct;
	}

}