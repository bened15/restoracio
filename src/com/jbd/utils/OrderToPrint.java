package com.jbd.utils;

public class OrderToPrint {
	private String printerName 		= new String();
	private String itemName 		= new String();
	private String comments	 		= new String();
	private String orderDateTime 	= new String();
	private int quantity			= 0;

	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(String orderTime) {
		this.orderDateTime = orderTime;
	}

	public String getClassValue(){
		return this.printerName+this.itemName+this.comments;
	}

	public void addQuantity(){
		this.quantity++;
	}

	public int getQuantity(){
		return this.quantity;
	}
}
