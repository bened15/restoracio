
package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the rest_bill_detail_x_discount database table.
 *
 */
@Entity
@Table(name = "rest_bill_detail_x_discount")
@NamedQuery(name = "RestBillDetailXDiscount.findAll", query = "SELECT r FROM RestBillDetailXDiscount r")
public class RestBillDetailXDiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILL_DISCOUNT_ID")
	private int billDiscountId;
	// bi-directional many-to-one association to CtgDiscount
	@ManyToOne
	@JoinColumn(name = "discount_id")
	private CtgDiscount ctgDiscount;

	// bi-directional many-to-one association to RestBillDetail
	@ManyToOne
	@JoinColumn(name = "bill_detail_id")
	private RestBillDetail restBillDetail;

	// bi-directional many-to-one association to RestBill
	@ManyToOne
	@JoinColumn(name = "bill_id")
	private RestBill restBill;

	public RestBillDetailXDiscount() {
	}

	public int getBillDiscountId() {
		return billDiscountId;
	}

	public void setBillDiscountId(int billDiscountId) {
		this.billDiscountId = billDiscountId;
	}

	public CtgDiscount getCtgDiscount() {
		return this.ctgDiscount;
	}

	public void setCtgDiscount(CtgDiscount ctgDiscount) {
		this.ctgDiscount = ctgDiscount;
	}

	public RestBillDetail getRestBillDetail() {
		return this.restBillDetail;
	}

	public void setRestBillDetail(RestBillDetail restBillDetail) {
		this.restBillDetail = restBillDetail;
	}

	public RestBill getRestBill() {
		return this.restBill;
	}

	public void setRestBill(RestBill restBill) {
		this.restBill = restBill;
	}

}