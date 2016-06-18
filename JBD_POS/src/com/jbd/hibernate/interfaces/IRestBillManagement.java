package com.jbd.hibernate.interfaces;

import com.jbd.model.RestBill;

public interface IRestBillManagement {

	public RestBill insertRestBill(RestBill o);
	public void updateRestBill(RestBill o);
	public void deleteRestBill(RestBill o);
	public RestBill findRestBill(Integer oId);

}
