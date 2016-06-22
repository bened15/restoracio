package com.jbd.hibernate.interfaces;

import com.jbd.model.RestProduct;

public interface IRestProductManagement {

	public RestProduct insertRestProduct(RestProduct o);

	public void updateRestProduct(RestProduct o);

	public void deleteRestProduct(RestProduct o);

	public RestProduct findRestProduct(Integer oId);

}
