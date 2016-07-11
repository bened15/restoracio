package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ctg_discount database table.
 *
 */
@Entity
@Table(name="ctg_discount")
@NamedQuery(name="CtgDiscount.findAll", query="SELECT c FROM CtgDiscount c")
public class CtgDiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DISCOUNT_ID")
	private int discountId;

	@Column(name="DISCOUNT_DESCRIPTION")
	private String discountDescription;

	@Column(name="DISCOUNT_NAME")
	private String discountName;

	@Column(name="DISCOUNT_PERCENTAGE")
	private int discountPercentage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISCOUNT_VALID_SINCE")
	private Date discountValidSince;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISCOUNT_VALID_UNTIL")
	private Date discountValidUntil;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	//bi-directional many-to-one association to CtgMenuType
	@ManyToOne
	@JoinColumn(name="MENU_TYPE_ID")
	private CtgMenuType ctgMenuType;

	//bi-directional many-to-one association to RestBillDetailXDiscount
	@OneToMany(mappedBy="ctgDiscount")
	private List<RestBillDetailXDiscount> restBillDetailXDiscounts;

	public CtgDiscount() {
	}

	public int getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getDiscountDescription() {
		return this.discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public String getDiscountName() {
		return this.discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public int getDiscountPercentage() {
		return this.discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Date getDiscountValidSince() {
		return this.discountValidSince;
	}

	public void setDiscountValidSince(Date discountValidSince) {
		this.discountValidSince = discountValidSince;
	}

	public Date getDiscountValidUntil() {
		return this.discountValidUntil;
	}

	public void setDiscountValidUntil(Date discountValidUntil) {
		this.discountValidUntil = discountValidUntil;
	}

	public Date getEntryDate() {
		return this.entryDate;
<<<<<<< HEAD
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

	public CtgMenuType getCtgMenuType() {
		return this.ctgMenuType;
	}

	public void setCtgMenuType(CtgMenuType ctgMenuType) {
		this.ctgMenuType = ctgMenuType;
	}

	public List<RestBillDetailXDiscount> getRestBillDetailXDiscounts() {
		return this.restBillDetailXDiscounts;
	}

	public void setRestBillDetailXDiscounts(List<RestBillDetailXDiscount> restBillDetailXDiscounts) {
		this.restBillDetailXDiscounts = restBillDetailXDiscounts;
	}

	public RestBillDetailXDiscount addRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().add(restBillDetailXDiscount);
		restBillDetailXDiscount.setCtgDiscount(this);

		return restBillDetailXDiscount;
	}

	public RestBillDetailXDiscount removeRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().remove(restBillDetailXDiscount);
		restBillDetailXDiscount.setCtgDiscount(null);

		return restBillDetailXDiscount;
=======
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

	public CtgMenuType getCtgMenuType() {
		return this.ctgMenuType;
	}

	public void setCtgMenuType(CtgMenuType ctgMenuType) {
		this.ctgMenuType = ctgMenuType;
	}

	public List<RestBillDetailXDiscount> getRestBillDetailXDiscounts() {
		return this.restBillDetailXDiscounts;
	}

	public void setRestBillDetailXDiscounts(List<RestBillDetailXDiscount> restBillDetailXDiscounts) {
		this.restBillDetailXDiscounts = restBillDetailXDiscounts;
	}

	public RestBillDetailXDiscount addRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().add(restBillDetailXDiscount);
		restBillDetailXDiscount.setCtgDiscount(this);

		return restBillDetailXDiscount;
	}

	public RestBillDetailXDiscount removeRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().remove(restBillDetailXDiscount);
		restBillDetailXDiscount.setCtgDiscount(null);

		return restBillDetailXDiscount;
	}

	@Override
	public String toString(){
		return this.discountId + " - " + this.discountName;
>>>>>>> 980f17d13477260c4d2b155862ba4cf6d1b960f6
	}
}

	