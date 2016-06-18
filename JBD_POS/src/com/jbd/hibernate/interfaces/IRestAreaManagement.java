package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.RestArea;

public interface IRestAreaManagement {

	public RestArea insertRestArea(RestArea o);

	public void updateRestArea(RestArea o);

	public void deleteRestArea(RestArea o);

	public RestArea findRestArea(Integer oId);

	public List<RestArea> getAllAreas();

}
