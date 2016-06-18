package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgProduct;

public interface ICtgProductManagement {

	public CtgProduct insertCtgProduct(CtgProduct o);
	public void updateCtgProduct(CtgProduct o);
	public void deleteCtgProduct(CtgProduct o);
	public CtgProduct findCtgProduct(Integer oId);

}
