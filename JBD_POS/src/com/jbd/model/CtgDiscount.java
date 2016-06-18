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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DISCOUNT_ID")
	private int discountId;

	@Column(name="DISCOUNT_DESCRIPTION")
	private String discountDescription;

	@Column(name="DISCOUNT_NAME")
	private String discountName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISCOUNT_VALID_SINCE")
	private Date discountValidSince;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISCOUNT_VALID_UNTIL")
	private Date discountValidUntil;

	//bi-directional many-to-one association to RestBill
	@OneToMany(mappedBy="ctgDiscount")
	private List<RestBill> restBills;

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

	public List<RestBill> getRestBills() {
		return this.restBills;
	}

	public void setRestBills(List<RestBill> restBills) {
		this.restBills = restBills;
	}

	public RestBill addRestBill(RestBill restBill) {
		getRestBills().add(restBill);
		restBill.setCtgDiscount(this);

		return restBill;
	}

	public RestBill removeRestBill(RestBill restBill) {
		getRestBills().remove(restBill);
		restBill.setCtgDiscount(null);

		return restBill;
	}

}