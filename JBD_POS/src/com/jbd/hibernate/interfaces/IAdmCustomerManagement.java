package com.jbd.hibernate.interfaces;

import com.jbd.model.AdmCustomer;

public interface IAdmCustomerManagement {

	public AdmCustomer insertAdmCustomer(AdmCustomer o);
	public void updateAdmCustomer(AdmCustomer o);
	public void deleteAdmCustomer(AdmCustomer o);
	public AdmCustomer findAdmCustomer(Integer oId);

}
