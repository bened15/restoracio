package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.CtgMenuType;

public interface ICtgMenuTypeManagement {

	public CtgMenuType insertCtgMenuType(CtgMenuType o);

	public void updateCtgMenuType(CtgMenuType o);

	public void deleteCtgMenuType(CtgMenuType o);

	public CtgMenuType findCtgMenuType(Integer oId);

	public List<CtgMenuType> loadMenuType();

}
