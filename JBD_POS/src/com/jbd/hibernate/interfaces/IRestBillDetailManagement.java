package com.jbd.hibernate.interfaces;

import com.jbd.model.RestBillDetail;

public interface IRestBillDetailManagement {

	public RestBillDetail insertRestBillDetail(RestBillDetail o);
	public void updateRestBillDetail(RestBillDetail o);
	public void deleteRestBillDetail(RestBillDetail o);
	public RestBillDetail findRestBillDetail(Integer oId);

}
