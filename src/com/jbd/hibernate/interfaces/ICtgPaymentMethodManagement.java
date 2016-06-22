package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgPaymentMethod;

public interface ICtgPaymentMethodManagement {

	public CtgPaymentMethod insertCtgPaymentMethod(CtgPaymentMethod o);
	public void updateCtgPaymentMethod(CtgPaymentMethod o);
	public void deleteCtgPaymentMethod(CtgPaymentMethod o);
	public CtgPaymentMethod findCtgPaymentMethod(String name);

}
