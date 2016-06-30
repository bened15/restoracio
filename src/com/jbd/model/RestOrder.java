package com.jbd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the rest_order database table.
 *
 */
@Entity
@Table(name = "rest_order")
@NamedQuery(name = "RestOrder.findAll", query = "SELECT r FROM RestOrder r")
public class RestOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private int orderId;

	private String attendant;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_DATE")
	private Date entryDate;

	@Column(name = "ORDER_COMMENT")
	private String orderComment;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	// bi-directional many-to-one association to RestBillDetail
	@OneToMany(mappedBy = "restOrder")
	private List<RestBillDetail> restBillDetails;

	// bi-directional many-to-one association to RestMenuItem
	@ManyToOne
	@JoinColumn(name = "MENU_ITEM_ID")
	private RestMenuItem restMenuItem;

	// bi-directional many-to-one association to RestShift
	@ManyToOne
	@JoinColumn(name = "ID_SHIFT")
	private RestShift restShift;

	// bi-directional many-to-one association to RestTableAccount
	@ManyToOne
	@JoinColumn(name = "TABLE_ACCOUNT_ID")
	private RestTableAccount restTableAccount;

	// bi-directional many-to-one association to RestOrderLog
	@OneToMany(mappedBy = "restOrder")
	private List<RestOrderLog> restOrderLogs;

	public RestOrder() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getAttendant() {
		return this.attendant;
	}

	public void setAttendant(String attendant) {
		this.attendant = attendant;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getOrderComment() {
		return this.orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<RestBillDetail> getRestBillDetails() {
		return this.restBillDetails;
	}

	public void setRestBillDetails(List<RestBillDetail> restBillDetails) {
		this.restBillDetails = restBillDetails;
	}

	public RestBillDetail addRestBillDetail(RestBillDetail restBillDetail) {
		getRestBillDetails().add(restBillDetail);
		restBillDetail.setRestOrder(this);

		return restBillDetail;
	}

	public RestBillDetail removeRestBillDetail(RestBillDetail restBillDetail) {
		getRestBillDetails().remove(restBillDetail);
		restBillDetail.setRestOrder(null);

		return restBillDetail;
	}

	public RestMenuItem getRestMenuItem() {
		return this.restMenuItem;
	}

	public void setRestMenuItem(RestMenuItem restMenuItem) {
		this.restMenuItem = restMenuItem;
	}

	public RestShift getRestShift() {
		return this.restShift;
	}

	public void setRestShift(RestShift restShift) {
		this.restShift = restShift;
	}

	public RestTableAccount getRestTableAccount() {
		return this.restTableAccount;
	}

	public void setRestTableAccount(RestTableAccount restTableAccount) {
		this.restTableAccount = restTableAccount;
	}

	public List<RestOrderLog> getRestOrderLogs() {
		return this.restOrderLogs;
	}

	public void setRestOrderLogs(List<RestOrderLog> restOrderLogs) {
		this.restOrderLogs = restOrderLogs;
	}

	public RestOrderLog addRestOrderLog(RestOrderLog restOrderLog) {
		getRestOrderLogs().add(restOrderLog);
		restOrderLog.setRestOrder(this);

		return restOrderLog;
	}

	public RestOrderLog removeRestOrderLog(RestOrderLog restOrderLog) {
		getRestOrderLogs().remove(restOrderLog);
		restOrderLog.setRestOrder(null);

		return restOrderLog;
	}

	@Transient
	private String menuItemName;

	@Transient
	private double menuItemPrice;

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public double getMenuItemPrice() {
		return menuItemPrice;
	}

	public void setMenuItemPrice(double menuItemPrice) {
		this.menuItemPrice = menuItemPrice;
	}

}