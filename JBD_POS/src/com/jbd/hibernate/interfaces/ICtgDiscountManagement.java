package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgDiscount;

public interface ICtgDiscountManagement {

	public CtgDiscount insertCtgDiscount(CtgDiscount o);
	public void updateCtgDiscount(CtgDiscount o);
	public void deleteCtgDiscount(CtgDiscount o);
	public CtgDiscount findCtgDiscount(Integer oId);

}
