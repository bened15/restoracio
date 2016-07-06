package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.RestOrderDetailLess;
import com.jbd.model.RestTableAccount;

public interface IRestOrderDetailLessManagement {

	public RestOrderDetailLess insertRestOrderDetailLess(RestOrderDetailLess o);

	public void updateRestOrderDetailLess(RestOrderDetailLess o);

	public void deleteRestOrderDetailLess(RestOrderDetailLess o);

	public RestOrderDetailLess findRestOrderDetailLess(Integer oId);

	public List<RestOrderDetailLess> findAllRestOrderDetailLesssFromTableAccount(RestTableAccount account);

}
