package com.jbd.hibernate.interfaces;

import com.jbd.model.RestBillPayment;
import com.jbd.model.RestTableAccount;

public interface IRestBillPaymentManagement {

	public RestBillPayment insertRestBillPayment(RestBillPayment o);
	public void updateRestBillPayment(RestBillPayment o);
	public void deleteRestBillPayment(RestBillPayment o);
	public RestBillPayment findRestBillPayment(Integer oId);
	public boolean isAmmountPaymentEqualOrMoreThanAccount(double totalAccount,RestTableAccount tableAc);

}
