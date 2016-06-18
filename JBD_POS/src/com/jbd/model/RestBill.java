package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rest_bill database table.
 *
 */
@Entity
@Table(name="rest_bill")
@NamedQuery(name="RestBill.findAll", query="SELECT r FROM RestBill r")
public class RestBill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BILL_ID")
	private int billId;

	@Column(name="BILL_SUBTOTAL")
	private float billSubtotal;

	@Column(name="BILL_TIP")
	private float billTip;

	@Column(name="BILL_TOTAL")
	private float billTotal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Column(name="SHIFT_ID")
	private int shiftId;

	//bi-directional many-to-one association to CtgDiscount
	@ManyToOne
	@JoinColumn(name="BILL_DISCOUNT_ID")
	private CtgDiscount ctgDiscount;

	//bi-directional many-to-one association to CtgPaymentMethod
	@ManyToOne
	@JoinColumn(name="PAYMENT_METHOD_ID")
	private CtgPaymentMethod ctgPaymentMethod;

	//bi-directional many-to-one association to RestTableAccount
	@ManyToOne
	@JoinColumn(name="TABLE_ACCOUNT_ID")
	private RestTableAccount restTableAccount;

	//bi-directional many-to-one association to RestBillDetail
	@OneToMany(mappedBy="restBill")
	private List<RestBillDetail> restBillDetails;

	//bi-directional many-to-one association to RestBillPayment
	@OneToMany(mappedBy="restBill")
	private List<RestBillPayment> restBillPayments;

	public RestBill() {
	}

	public int getBillId() {
		return this.billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public float getBillSubtotal() {
		return this.billSubtotal;
	}

	public void setBillSubtotal(float billSubtotal) {
		this.billSubtotal = billSubtotal;
	}

	public float getBillTip() {
		return this.billTip;
	}

	public void setBillTip(float billTip) {
		this.billTip = billTip;
	}

	public float getBillTotal() {
		return this.billTotal;
	}

	public void setBillTotal(float billTotal) {
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

	public List<RestBillDetail> getRestBillDetails() {
		return this.restBillDetails;
	}

	public void setRestBillDetails(List<RestBillDetail> restBillDetails) {
		this.restBillDetails = restBillDetails;
	}

	public RestBillDetail addRestBillDetail(RestBillDetail restBillDetail) {
		getRestBillDetails().add(restBillDetail);
		restBillDetail.setRestBill(this);

		return restBillDetail;
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