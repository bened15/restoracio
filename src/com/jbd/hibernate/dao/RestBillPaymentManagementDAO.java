package com.jbd.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.jbd.hibernate.interfaces.IRestBillPaymentManagement;
import com.jbd.model.RestBillPayment;
import com.jbd.model.RestTableAccount;

public class RestBillPaymentManagementDAO implements IRestBillPaymentManagement {

	@PersistenceContext
	public EntityManager em;

	public RestBillPaymentManagementDAO() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public RestBillPayment insertRestBillPayment(RestBillPayment o) {
		try {
			em.persist(o);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void updateRestBillPayment(RestBillPayment o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRestBillPayment(RestBillPayment o) {
		// TODO Auto-generated method stub

	}

	@Override
	public RestBillPayment findRestBillPayment(Integer oId) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAmmountPaymentEqualOrMoreThanAccount(double totalAccount,RestTableAccount tableAc) {
		try {
			Query q = em.createQuery("select sum(p.amount) from RestBillPayment p where p.restBill.restTableAccount=:tableAc",
					Double.class);
			q.setParameter("tableAc", tableAc);
			double result = (Double) q.getSingleResult();
			if (result >= totalAccount) {
				return true;

			} else {

				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
