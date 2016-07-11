<<<<<<< HEAD
package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.RestBill;
import com.jbd.model.RestTableAccount;

public interface IRestBillManagement {

	public RestBill insertRestBill(RestBill o);

	public void updateRestBill(RestBill o);

	public void deleteRestBill(RestBill o);

	public RestBill findRestBill(Integer oId);

	public List<RestBill> findBillsWithRestTableAccount(RestTableAccount account);

	public Double getTotalAccountFromTable(RestTableAccount ta);

}
=======
package com.jbd.hibernate.interfaces;

import java.util.List;

import com.jbd.model.RestBill;
import com.jbd.model.RestTableAccount;

public interface IRestBillManagement {

	public RestBill insertRestBill(RestBill o);

	public void updateRestBill(RestBill o);

	public void deleteRestBill(RestBill o);

	public RestBill findRestBill(Integer oId);

	public List<RestBill> findBillsWithRestTableAccount(RestTableAccount account);

	public Double getTotalAccountFromTable(RestTableAccount ta);

}
>>>>>>> 980f17d13477260c4d2b155862ba4cf6d1b960f6
