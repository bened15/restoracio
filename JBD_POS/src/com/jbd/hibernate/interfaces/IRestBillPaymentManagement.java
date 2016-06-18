package com.jbd.hibernate.interfaces;

import com.jbd.model.RestBillPayment;

public interface IRestBillPaymentManagement {

	public RestBillPayment insertRestBillPayment(RestBillPayment o);
	public void updateRestBillPayment(RestBillPayment o);
	public void deleteRestBillPayment(RestBillPayment o);
	public RestBillPayment findRestBillPayment(Integer oId);

}
