package com.jbd.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

/**
 * The persistent class for the rest_bill database table.
 *
 */
@Entity
@Table(name = "rest_bill")
@NamedQuery(name = "RestBill.findAll", query = "SELECT r FROM RestBill r")
public class RestBill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILL_ID")
	private int billId;

	@Column(name = "BILL_SUBTOTAL")
	private double billSubtotal;

	@Column(name = "BILL_TIP")
	private double billTip;

	@Column(name = "BILL_TOTAL")
	private double billTotal;
	@Column(name = "BILL_NAME")
	private String billName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_DATE")
	private Date entryDate;

	@Column(name = "ENTRY_USER")
	private String entryUser;

	@Column(name = "SHIFT_ID")
	private int shiftId;

	// bi-directional many-to-one association to CtgDiscount
	@ManyToOne
	@JoinColumn(name = "BILL_DISCOUNT_ID")
	private CtgDiscount ctgDiscount;

	// bi-directional many-to-one association to CtgPaymentMethod
	@ManyToOne
	@JoinColumn(name = "PAYMENT_METHOD_ID")
	private CtgPaymentMethod ctgPaymentMethod;

	// bi-directional many-to-one association to RestTableAccount
	@ManyToOne
	@JoinColumn(name = "TABLE_ACCOUNT_ID")
	private RestTableAccount restTableAccount;

	// bi-directional many-to-one association to RestBillDetail
	@OneToMany(mappedBy = "restBill")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE })
	private List<RestBillDetail> restBillDetails;

	// bi-directional many-to-one association to RestBillPayment
	@OneToMany(mappedBy = "restBill")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE })
	private List<RestBillPayment> restBillPayments;

	public RestBill() {
	}

	public RestBill(int billId) {
		this.billId = billId;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public int getBillId() {
		return this.billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public double getBillSubtotal() {
		return this.billSubtotal;
	}

	public void setBillSubtotal(double billSubtotal) {
		this.billSubtotal = billSubtotal;
	}

	public double getBillTip() {
		return this.billTip;
	}

	public void setBillTip(double billTip) {
		this.billTip = billTip;
	}

	public double getBillTotal() {
		return this.billTotal;
	}

	public void setBillTotal(double billTotal) {
		this.billTotal = billTotal;
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

	public int getShiftId() {
		return this.shiftId;
	}

	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}

	public CtgDiscount getCtgDiscount() {
		return this.ctgDiscount;
	}

	public void setCtgDiscount(CtgDiscount ctgDiscount) {
		this.ctgDiscount = ctgDiscount;
	}

	public CtgPaymentMethod getCtgPaymentMethod() {
		return this.ctgPaymentMethod;
	}

	public void setCtgPaymentMethod(CtgPaymentMethod ctgPaymentMethod) {
		this.ctgPaymentMethod = ctgPaymentMethod;
	}

	public RestTableAccount getRestTableAccount() {
		return this.restTableAccount;
	}

	public void setRestTableAccount(RestTableAccount restTableAccount) {
		this.restTableAccount = restTableAccount;
	}

	// public List<RestBillDetail> getRestBillDetails() {
	// return this.restBillDetails;
	// }
	//
	// public void setRestBillDetails(List<RestBillDetail> restBillDetails) {
	// this.restBillDetails = restBillDetails;
	// }

	public RestBillDetail addRestBillDetail(RestBillDetail restBillDetail) {
		getRestBillDetails().add(restBillDetail);
		restBillDetail.setRestBill(this);

		return restBillDetail;
	}

	public List<RestBillDetail> getRestBillDetails() {
		return restBillDetails;
	}

	public void setRestBillDetails(List<RestBillDetail> restBillDetails) {
		this.restBillDetails = restBillDetails;
	}

	public RestBillDetail removeRestBillDetail(RestBillDetail restBillDetail) {
		getRestBillDetails().remove(restBillDetail);
		restBillDetail.setRestBill(null);

		return restBillDetail;
	}

	public List<RestBillPayment> getRestBillPayments() {
		return this.restBillPayments;
	}

	public void setRestBillPayments(List<RestBillPayment> restBillPayments) {
		this.restBillPayments = restBillPayments;
	}

	public RestBillPayment addRestBillPayment(RestBillPayment restBillPayment) {
		getRestBillPayments().add(restBillPayment);
		restBillPayment.setRestBill(this);

		return restBillPayment;
	}

	public RestBillPayment removeRestBillPayment(RestBillPayment restBillPayment) {
		getRestBillPayments().remove(restBillPayment);
		restBillPayment.setRestBill(null);

		return restBillPayment;
	}

}