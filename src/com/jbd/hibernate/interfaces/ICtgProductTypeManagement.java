package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgProductType;

public interface ICtgProductTypeManagement {

	public CtgProductType insertCtgProductType(CtgProductType o);
	public void updateCtgProductType(CtgProductType o);
	public void deleteCtgProductType(CtgProductType o);
	public CtgProductType findCtgProductType(Integer oId);

}
