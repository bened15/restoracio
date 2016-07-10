package com.jbd.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * The persistent class for the rest_bill_detail database table.
 *
 */
@Entity
@Table(name = "rest_bill_detail")
@NamedQuery(name = "RestBillDetail.findAll", query = "SELECT r FROM RestBillDetail r")
public class RestBillDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILL_DETAIL_ID")
	private int billDetailId;

	@Column(name = "BILL_DETAIL_SUBTOTAL")
	private double billDetailSubtotal;

	@Column(name = "BILL_DETAIL_TOTAL")
	private double billDetailTotal;

	// bi-directional many-to-one association to RestBill
	@ManyToOne
	@JoinColumn(name = "BILL_ID")
	private RestBill restBill;

	// bi-directional many-to-one association to RestOrder
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private RestOrder restOrder;

	// bi-directional many-to-one association to RestBillDetailXDiscount
	@OneToMany(mappedBy = "restBillDetail")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE })
	private List<RestBillDetailXDiscount> restBillDetailXDiscounts;

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

	public List<RestBillDetailXDiscount> getRestBillDetailXDiscounts() {
		return this.restBillDetailXDiscounts;
	}

	public void setRestBillDetailXDiscounts(List<RestBillDetailXDiscount> restBillDetailXDiscounts) {
		this.restBillDetailXDiscounts = restBillDetailXDiscounts;
	}

	public RestBillDetailXDiscount addRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().add(restBillDetailXDiscount);
		restBillDetailXDiscount.setRestBillDetail(this);

		return restBillDetailXDiscount;
	}

	public RestBillDetailXDiscount removeRestBillDetailXDiscount(RestBillDetailXDiscount restBillDetailXDiscount) {
		getRestBillDetailXDiscounts().remove(restBillDetailXDiscount);
		restBillDetailXDiscount.setRestBillDetail(null);

		return restBillDetailXDiscount;
	}

}