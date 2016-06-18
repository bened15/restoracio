package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the adm_reservation database table.
 *
 */
@Entity
@Table(name="adm_reservation")
@NamedQuery(name="AdmReservation.findAll", query="SELECT a FROM AdmReservation a")
public class AdmReservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RESERVATION_ID")
	private int reservationId;

	@Column(name="ADVANCE_PAYMENT_AMMOUNT")
	private float advancePaymentAmmount;

	@Temporal(TemporalType.DATE)
	@Column(name="ENTRY_DATE")
	private Date entryDate;

	@Column(name="ENTRY_USER")
	private String entryUser;

	@Temporal(TemporalType.DATE)
	@Column(name="RESERVATION_DATE")
	private Date reservationDate;

	//bi-directional many-to-one association to AdmCustomer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private AdmCustomer admCustomer;

	public AdmReservation() {
	}

	public int getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public float getAdvancePaymentAmmount() {
		return this.advancePaymentAmmount;
	}

	public void setAdvancePaymentAmmount(float advancePaymentAmmount) {
		this.advancePaymentAmmount = advancePaymentAmmount;
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

	public Date getReservationDate() {
		return this.reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public AdmCustomer getAdmCustomer() {
		return this.admCustomer;
	}

	public void setAdmCustomer(AdmCustomer admCustomer) {
		this.admCustomer = admCustomer;
	}

}