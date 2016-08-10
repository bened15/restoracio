package com.jbd.hibernate.interfaces;

import com.jbd.model.RestInformation;

public interface IRestInformationManagement {

	public RestInformation insertRestInformation(RestInformation o);
	public void updateRestInformation(RestInformation o);
	public void deleteRestInformation(RestInformation o);
	public RestInformation findRestInformation(Integer oId);

}
