package com.jbd.hibernate.interfaces;

import com.jbd.model.CtgMeasureUnit;

public interface ICtgMeasureUnitManagement {

	public CtgMeasureUnit insertCtgMeasureUnit(CtgMeasureUnit o);
	public void updateCtgMeasureUnit(CtgMeasureUnit o);
	public void deleteCtgMeasureUnit(CtgMeasureUnit o);
	public CtgMeasureUnit findCtgMeasureUnit(Integer oId);

}
