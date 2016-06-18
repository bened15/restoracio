package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgSupplier;

public interface ICtgSupplierManagement {

	public CtgSupplier insertCtgSupplier(CtgSupplier o);
	public void updateCtgSupplier(CtgSupplier o);
	public void deleteCtgSupplier(CtgSupplier o);
	public CtgSupplier findCtgSupplier(Integer oId);

}
