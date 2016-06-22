package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rest_bill_detail database table.
 *
 */
@Entity
@Table(name="rest_bill_detail")
@NamedQuery(name="RestBillDetail.findAll", query="SELECT r FROM RestBillDetail r")
public class RestBillDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BILL_DETAIL_ID")
	private int billDetailId;

	@Column(name="BILL_DETAIL_SUBTOTAL")
	private double billDetailSubtotal;

	@Column(name="BILL_DETAIL_TOTAL")
	private double billDetailTotal;

	@Column(name="DISCOUNT_ID")
	private int discountId;

	//bi-directional many-to-one association to RestBill
	@ManyToOne
	@JoinColumn(name="BILL_ID")
	private RestBill restBill;

	//bi-directional many-to-one association to RestOrder
	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	private RestOrder restOrder;

	public RestBillDetail() {
	}

	public int getBillDetailId() {
		return this.billDetailId;
	}

	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}

	public double getBillDetailSubtotal() {
		return this.billDetailSubtotal;
	}

	public void setBillDetailSubtotal(double billDetailSubtotal) {
		this.billDetailSubtotal = billDetailSubtotal;
	}

	public double getBillDetailTotal() {
		return this.billDetailTotal;
	}

	public void setBillDetailTotal(double billDetailTotal) {
		this.billDetailTotal = billDetailTotal;
	}

	public int getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public RestBill getRestBill() {
		return this.restBill;
	}

	public void setRestBill(RestBill restBill) {
		this.restBill = restBill;
	}

	public RestOrder getRestOrder() {
		return this.restOrder;
	}

	public void setRestOrder(RestOrder restOrder) {
		this.restOrder = restOrder;
	}

}